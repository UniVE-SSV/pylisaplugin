package PyLisa;

import PyLisa.ForAnalysis.SourceCodeLocation;
import PyLisa.ForAnalysis.Stub;
import PyLisa.ForAnalysis.Warning;
import PyLisa.ForAnalysis.WarningWithLocation;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.*;
import com.jetbrains.python.psi.PyExpression;
import com.jetbrains.python.psi.PyStatement;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class PyLisaInspection extends LocalInspectionTool {
    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder pHolder, boolean isOnTheFly) {
        return new PsiElementVisitor() {
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
                                super.visitElement(pElement);

                                StringBuilder s = new StringBuilder();

                                assert(dFile!=null);
                                if(pElement instanceof PyStatement || pElement instanceof PyExpression && wLine == dFile.getLineNumber(pElement.getTextOffset())) {
                                    pHolder.registerProblem(pElement, s.append("PsiElement: " + pElement + " " + e.getMessage()).toString(), (LocalQuickFix) null);
                                }
                            }
                        });
                    }
                }
            }
        };
    }
}