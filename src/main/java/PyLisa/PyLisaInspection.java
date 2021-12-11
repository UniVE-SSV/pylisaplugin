package PyLisa;

import PyLisa.ForAnalysis.SourceCodeLocation;
import PyLisa.ForAnalysis.Stub;
import PyLisa.ForAnalysis.Warning;
import PyLisa.ForAnalysis.WarningWithLocation;
import com.intellij.codeInspection.*;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class PyLisaInspection extends LocalInspectionTool {
    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder pHolder, boolean isOnTheFly) {
        return new PsiElementVisitor() {
            /*@Override
            public void visitElement(@NotNull PsiElement pElement) {

                Document dFile = PsiDocumentManager.getInstance(pElement.getProject()).getDocument(pElement.getContainingFile());
                Collection<Warning> warnings = Stub.analyze(pElement.getContainingFile().getVirtualFile().getPath());

                //For each Warning
                for (Warning e : warnings) {
                    if((e instanceof WarningWithLocation) && (((WarningWithLocation) e).getLocation() instanceof SourceCodeLocation)) {
                        final int wLine = ((SourceCodeLocation) ((WarningWithLocation) e).getLocation()).getLine();
                        final int wColumn = ((SourceCodeLocation) ((WarningWithLocation) e).getLocation()).getCol();

                        StringBuilder s = new StringBuilder();

                        if(pElement.toString().startsWith("Py") && wLine == dFile.getLineNumber(pElement.getTextOffset()))
                            pHolder.registerProblem(pElement, s.append("Name: " + pElement + " " + e.getMessage()).toString(), (LocalQuickFix) null);
                        }
                    }
                }
            }*/

            //group = 'it.unive'
            //version = '0.1b3'

            @Override
            public void visitFile(@NotNull PsiFile pFile) {

                Document dFile = PsiDocumentManager.getInstance(pFile.getProject()).getDocument(pFile);
                Collection<Warning> warnings = Stub.analyze(pFile.getVirtualFile().getPath());

                //For each Warning
                for (Warning e : warnings) {
                    if((e instanceof WarningWithLocation) && (((WarningWithLocation) e).getLocation() instanceof SourceCodeLocation)) {
                        final int wLine = ((SourceCodeLocation) ((WarningWithLocation) e).getLocation()).getLine();
                        final int wColumn = ((SourceCodeLocation) ((WarningWithLocation) e).getLocation()).getCol();

                        pFile.accept(new PsiRecursiveElementWalkingVisitor() {
                            @Override
                            public void visitElement(@NotNull PsiElement pElement) {
                                StringBuilder s = new StringBuilder();
                                //(wLine == (dFile.getLineNumber(pElement.getTextOffset()) + 1))

                                pHolder.registerProblem(pElement, s.append("Name: " + pElement + " " + e.getMessage()).toString(), (LocalQuickFix) null);

                                super.visitElement(pElement);
                            }
                        });
                    }
                }
            }
        };
    }
}