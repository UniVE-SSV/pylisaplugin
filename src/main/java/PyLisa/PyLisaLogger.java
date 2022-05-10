package PyLisa;


import com.intellij.codeInspection.ProblemDescriptor;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.logging.*;


public class PyLisaLogger {
    private final static Logger LOGGER= Logger.getLogger(PyLisaLogger.class.getName());
    public static FileHandler fh=null;
    public static String filePath;

    //create the log file
    public PyLisaLogger(){
        try{
            filePath=System.getProperty("user.home")+"/pyLisaLogFile_"+PyLisaInspection.fileName+".log";
            fh=new FileHandler(filePath,true);
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


        LOGGER.info("PyLisa Inspection results:");

        for (ProblemDescriptor s:
             PyLisaInspection.warnings_res) {
            LOGGER.warning(s.toString());
        }

        LOGGER.info("Notebook analyzed: \n\n"+PyLisaInspection.notebook.getText());
        LOGGER.info("<end> \n\n");

        fh.close();


    }
}
