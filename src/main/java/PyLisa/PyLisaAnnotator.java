package PyLisa;

import PyLisa.ForAnalysis.SourceCodeLocation;
import PyLisa.ForAnalysis.Stub;
import PyLisa.ForAnalysis.Warning;
import PyLisa.ForAnalysis.WarningWithLocation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.jetbrains.python.psi.PyExpression;
import com.jetbrains.python.psi.PyStatement;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class PyLisaAnnotator implements Annotator{
    @Override
    public void annotate(@NotNull PsiElement pElement, @NotNull AnnotationHolder aHolder) {
        //Collection of Warnings
        Collection<Warning> warnings;

        //The Analyzing Tool
        Document dFile = PsiDocumentManager.getInstance(pElement.getProject()).getDocument(pElement.getContainingFile());
        VirtualFile vFile = pElement.getContainingFile().getVirtualFile();
        warnings = Stub.analyze(vFile.getPath());

        for (Warning e : warnings) {
            if((e instanceof WarningWithLocation) && (((WarningWithLocation) e).getLocation() instanceof SourceCodeLocation)) {
                final int wLine = ((SourceCodeLocation) ((WarningWithLocation) e).getLocation()).getLine();
                final int wColumn = ((SourceCodeLocation) ((WarningWithLocation) e).getLocation()).getCol();

                StringBuilder s = new StringBuilder();

                assert dFile != null;
                if(pElement instanceof PyStatement || pElement instanceof PyExpression && wLine == 3)
                    aHolder.newAnnotation(HighlightSeverity.WARNING, s.append("Name: " + pElement).toString()).create();
            }
        }
    }
}