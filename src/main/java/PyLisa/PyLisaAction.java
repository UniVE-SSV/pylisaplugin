package PyLisa;
//group = 'it.unive'
//version = '0.1b3'
import PyLisa.ForAnalysis.SourceCodeLocation;
import PyLisa.ForAnalysis.Stub;
import PyLisa.ForAnalysis.Warning;
import PyLisa.ForAnalysis.WarningWithLocation;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VFileProperty;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class PyLisaAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        //Instance of the Project
        Project cProject = event.getProject();

        //Collection of Warnings
        Collection<Warning> warnings;

        //String to visualize the Warnings
        StringBuilder sWarnings = new StringBuilder();

        //User Chosen File has to be Jupyter Notebook
        FileChooserDescriptor fDescriptor = new FileChooserDescriptorJupyterNotebook();
        VirtualFile cFileSystem = LocalFileSystem.getInstance().findFileByPath(System.getProperty("user.home"));
        VirtualFile vFile = FileChooser.chooseFile(fDescriptor, cProject, cFileSystem);

        //The Analyzing Tool
        assert vFile != null;
        warnings = Stub.analyze(vFile.getPath());

        //For each Warning
        for (Warning e : warnings) {
            if (e instanceof WarningWithLocation) {
                if (((WarningWithLocation) e).getLocation() instanceof SourceCodeLocation) {
                    //Line and Column for the warnings
                    final int wLine = ((SourceCodeLocation) ((WarningWithLocation) e).getLocation()).getLine();
                    final int wColumn = ((SourceCodeLocation) ((WarningWithLocation) e).getLocation()).getCol();

                    sWarnings.append("Warning at Line: ").append(wLine).append(" and Column: ").append(wColumn).append("\n");
                }
            }
        }

        //To show the Warnings as a Dialog
        Messages.showMessageDialog(sWarnings.toString(), "File selected: " + vFile.getName(), null);
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        e.getPresentation().setEnabledAndVisible(project != null);
    }

    //ChooserDescriptor for JupyetNotebook files only
    private static class FileChooserDescriptorJupyterNotebook extends FileChooserDescriptor {
        public FileChooserDescriptorJupyterNotebook() {
            super(true, false, false, false, false, false);
        }

        @Override
        public boolean isFileSelectable(VirtualFile vFile) {
            if (vFile == null) {
                return false;
            } else if (vFile.is(VFileProperty.SYMLINK) && vFile.getCanonicalPath() == null) {
                return false;
            } else {
                if(vFile.getExtension() == null)
                    return false;
                else
                    return (vFile.getExtension().equals("ipynb"));
            }
        }
    }
}