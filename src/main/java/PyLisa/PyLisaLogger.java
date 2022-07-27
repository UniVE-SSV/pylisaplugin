package PyLisa;


import PyLisa.ForAnalysis.JsonReport;
import PyLisa.ForAnalysis.LiSAConfiguration;
import PyLisa.ForAnalysis.Warning;
import com.intellij.codeInspection.ProblemDescriptor;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import java.util.function.Supplier;
import java.util.logging.*;

import static PyLisa.PyLisaInspection.*;


public class PyLisaLogger {
    private final static Logger LOGGER= Logger.getLogger(PyLisaLogger.class.getName());
    public static FileHandler fh=null;
    public static String filePath;

    //create the log file
    public PyLisaLogger(){
        try{
            int i=0;
            //check if log file already exists
            while(new File(System.getProperty("user.home")+"/pyLisaLogFile_" +PyLisaInspection.fileName +i+".log").exists()){
                i++;
            }
            filePath=System.getProperty("user.home")+"/pyLisaLogFile_" +PyLisaInspection.fileName +i+".log";
            fh=new FileHandler(filePath,false);
            fh.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    SimpleDateFormat logTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                    Calendar cal = new GregorianCalendar();
                    cal.setTimeInMillis(record.getMillis());
                    return logTime.format(cal.getTime())
                            +" || "
                            +record.getLevel()
                            + ": "
                            + record.getMessage() + "\n";
                }
            });
            LOGGER.addHandler(fh);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //used to make mac id
    protected static String formatMac(byte[] mac) {
        if (mac == null)
            return null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        return sb.toString();
    }



    //write the log file
    public static void doLogging() throws SocketException {
        LOGGER.info("<begin>");
        for(Enumeration<NetworkInterface> e
            = NetworkInterface.getNetworkInterfaces();
            e.hasMoreElements(); )
        {
            NetworkInterface ni = e.nextElement();
            String mac=formatMac(ni.getHardwareAddress());
            if(mac!=null){
                LOGGER.info("Mac ID: "+mac);
            }

        }

        liSAConfiguration=new LiSAConfiguration();
        liSAConfiguration.addSyntacticChecks(liSAConfiguration.getSyntacticChecks());
        liSAConfiguration.addSemanticChecks(liSAConfiguration.getSemanticChecks());
        liSAConfiguration.setInterproceduralAnalysis(liSAConfiguration.getInterproceduralAnalysis());
        liSAConfiguration.setCallGraph(liSAConfiguration.getCallGraph());
        liSAConfiguration.setAbstractState(liSAConfiguration.getAbstractState());
        liSAConfiguration.setDumpCFGs(liSAConfiguration.isDumpCFGs());
        liSAConfiguration.setDumpTypeInference(liSAConfiguration.isDumpTypeInference());
        liSAConfiguration.setDumpAnalysis(liSAConfiguration.isDumpAnalysis());
        liSAConfiguration.setJsonOutput(liSAConfiguration.isJsonOutput());
        liSAConfiguration.setWorkdir(liSAConfiguration.getWorkdir());

        LOGGER.info(liSAConfiguration.toString());

        LOGGER.info("PyLisa Inspection results:");
        JsonReport jsonReport=new JsonReport(war, Collections.emptyList());

       StringWriter result=new StringWriter();
                try {
                    jsonReport.dump(result);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        LOGGER.warning(result.toString());

        LOGGER.info("Notebook analyzed: \n\n"+PyLisaInspection.notebook.getText());
        LOGGER.info("<end> \n\n");

        fh.close();

    }
}
