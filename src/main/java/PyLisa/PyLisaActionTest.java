package PyLisa;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import it.unive.lisa.AnalysisException;
import it.unive.pylisa.analysis.dataframes.structure.DataframeStructureDomain;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PyLisaActionTest extends AnAction {
    public PyLisaActionTest() {}

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        //Instance of the Project
        Project cProject = event.getProject();

        //User Chosen File has to be Jupyter Notebook
        FileChooserDescriptor fDescriptor = new FileChooserDescriptor(true, false, false, false, false, false);
        VirtualFile cFileSystem = LocalFileSystem.getInstance().findFileByPath(System.getProperty("user.home"));
        VirtualFile vFile = FileChooser.chooseFile(fDescriptor, cProject, cFileSystem);

        //The Analyzing Tool
        NotebookTestPub nTB = new NotebookTestPub();
        assert vFile != null;
        try {
            nTB.perform(vFile.getPath(), "ipynb", new DataframeStructureDomain());
        } catch (IOException | AnalysisException e) {
            e.printStackTrace();
        }

        //Message
        Messages.showMessageDialog("GOOD", "GOOD", null);
    }
}
