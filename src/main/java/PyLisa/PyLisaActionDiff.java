package PyLisa;

import PyLisa.ForAnalysis.JsonReport;
import PyLisa.ForAnalysis.LiSAConfiguration;
import PyLisa.ForAnalysis.Warning;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static java.lang.Math.abs;


public class PyLisaActionDiff extends AnAction {

   //recreate LISAConfiguration by reading the logfile
   public LiSAConfiguration recreateConf(String filePath){
       LiSAConfiguration lc=new LiSAConfiguration();
       try {
           BufferedReader reader = new BufferedReader(new FileReader(filePath));
           String line = reader.readLine();


           while(line!=null) {
               if (line.contains("workdir:")) {
                   lc.setWorkdir(line.substring(10));
               }
               if (line.contains("dump input cfgs:")) {
                   if (line.contains("true")) {
                       lc.setDumpCFGs(true);
                   } else lc.setDumpCFGs(false);
               }
               if (line.contains("dump inferred types:")) {
                   if (line.contains("true")) {
                       lc.setDumpTypeInference(true);
                   } else lc.setDumpTypeInference(false);
               }
               if (line.contains("dump analysis result:")) {
                   if (line.contains("true")) {
                       lc.setDumpAnalysis(true);
                   } else lc.setDumpAnalysis(false);
               }
               if (line.contains("dump json report:")) {
                   if (line.contains("true")) {
                       lc.setJsonOutput(true);
                   } else lc.setJsonOutput(false);
               }
               if (line.contains("syntactic checks to execute")) {
                   if (line.contains(":")) {
                       while (!(line.contains("semantic checks to execute"))) {
                           line = reader.readLine();
                           //TODO find a way to retrive SyntacticCheck from string
                           //lc1.addSyntacticCheck(?)
                       }
                   }
               }
               if (line.contains("semantic checks to execute")) {
                   if (line.contains(":")) {
                       while (!(line.contains("PyLisa Inspection results:"))) {
                           line = reader.readLine();
                           //TODO find a way to retrive SemanticCheck from string
                           //lc1.SemanticCheck(?);
                       }

                   }
               }
               line= reader.readLine();
           }
       } catch (FileNotFoundException e) {
           Messages.showErrorDialog(e.getMessage(), "PyLisa");
       } catch (IOException e) {
           Messages.showErrorDialog(e.getMessage(), "PyLisa");
       }
       return lc;
   }

   //recreate JsonReport by reading the logfile
   public JsonReport recreateWarn(String filePath){
       Collection<String> files= Collections.emptyList();
       Collection<Warning> warn = new ArrayList<>(Collections.emptyList());
       try {
           BufferedReader reader = new BufferedReader(new FileReader(filePath));
           String line = reader.readLine();
           while(line!=null){
               if(line.contains("\"message\" :")){
                   //substring containing the warning
                   warn.add(new Warning(line.substring(18,line.length()-1)));
               }
               line=reader.readLine();
           }
       } catch (FileNotFoundException e) {
           Messages.showErrorDialog(e.getMessage(), "PyLisa");
       } catch (IOException e) {
           Messages.showErrorDialog(e.getMessage(), "PyLisa");
       }
       JsonReport jr=new JsonReport(warn,files);
       /*StringWriter result=new StringWriter();
       try {
           jr.dump(result);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       Messages.showInfoMessage(result.toString(),"warn");
       Messages.showInfoMessage(jr.toString(),"warn");*/
       return jr;
   }

   @Override
   public void update(@NotNull AnActionEvent e) {
      super.update(e);

   }

   @Override
   public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
       //check if the logfile exists
       int j=0;
       if(!(new File(System.getProperty("user.home")+"/pyLisaLogFile_" +PyLisaInspection.fileName +j+".log").exists())){
           Messages.showErrorDialog("Log file does not exist", "ERROR");
       }
       else{
           while(new File(System.getProperty("user.home")+"/pyLisaLogFile_" +PyLisaInspection.fileName +j+".log").exists()){
               j++;
           }
           //check if there are at least two log files
           if(j<=1) {
               Messages.showErrorDialog("You need at least two log files", "ERROR");
           } else {
               //take the last log file created
               String filePath1 = System.getProperty("user.home") + "/pyLisaLogFile_" + PyLisaInspection.fileName + (j - 1) + ".log";
               JsonReport jr1=recreateWarn(filePath1);
               LiSAConfiguration l1=recreateConf(filePath1);
               int n_warn1 = jr1.getWarnings().size();
               //take the penultimate log file created
               String filePath2 = System.getProperty("user.home") + "/pyLisaLogFile_" + PyLisaInspection.fileName + (j - 2) + ".log";
               JsonReport jr2=recreateWarn(filePath2);
               LiSAConfiguration l2=recreateConf(filePath2);
               int n_warn2 = jr2.getWarnings().size();
               //difference number of warnings
               int diff_warn = n_warn1 - n_warn2;
               //compare JsonReport
               boolean equals_jsonReport=jr1.equals(jr2);
               if(equals_jsonReport){
                   Messages.showInfoMessage("Same warnings", "DIFF");
               }
               else{
                   Messages.showInfoMessage("Warnings in the analysis \n"+filePath1+":\n"+n_warn1+"\n"+
                           "Warnings in the analysis\n"+filePath2+":\n"+n_warn2+"\n"+"Diff Warnings:" + diff_warn, "DIFF");
               }
               //compare LisaConfiguration
               boolean equals_LISAConfiguration=l1.equals(l2);
               if (equals_LISAConfiguration) {
                   Messages.showInfoMessage("uguali conf","diff");
               }

           }

       }
   }
}
