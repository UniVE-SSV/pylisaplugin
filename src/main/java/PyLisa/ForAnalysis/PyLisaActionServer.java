package PyLisa.ForAnalysis;

import PyLisa.PyLisaLogger;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.apache.commons.net.ftp.FTPClient;
import org.jetbrains.annotations.NotNull;
import java.io.*;

public class PyLisaActionServer extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        String server="ftp.dlptest.com"; // to test the code
        int port=21;
        String user="dlpuser"; //will be changed to anonymous
        String pass="rNrKYTX9g7z3RgJRmxWuGHbeu";//will be changed with empty password
        FTPClient ftpClient=new FTPClient();
        try {
            ftpClient.connect(server,port);
            ftpClient.login(user,pass);
            ftpClient.enterLocalPassiveMode();

            File file= new File(PyLisaLogger.filePath);
            String fileName= file.getName();
            InputStream inputStream=new FileInputStream(file);
            Messages.showInfoMessage("start uploading "+fileName,"Pylisa");
            boolean done=ftpClient.storeFile(fileName,inputStream);
            inputStream.close();
            if(done){
                Messages.showInfoMessage("The file "+fileName+" is uploaded successfully","PyLisa");
            }
        }catch (IOException ex){
            Messages.showInfoMessage("Error "+ex.getMessage(),"PyLisa");
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()){
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
     }


}
