package PyLisa;



import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;
import java.net.SocketException;


public class PyLisaActionLog  extends AnAction {



    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {

        //Project project=anActionEvent.getProject();

        //to be tested when there is a working checker
        if(PyLisaInspection.war.isEmpty()){
            Messages.showErrorDialog("No warnings or errors found.\n" +
                    "LogFile not created","Pylisa");
        }
        else {
            new PyLisaLogger();
            try {
                PyLisaLogger.doLogging();
            } catch (SocketException e) {
               Messages.showErrorDialog(e.getMessage(),"PyLisa");
            }
            Messages.showInfoMessage("LogFile created","Pylisa");

        }

    }
    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);

    }
}

