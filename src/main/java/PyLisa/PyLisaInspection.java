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
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PyLisaInspection extends LocalInspectionTool {
    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder pHolder, boolean isOnTheFly) {
        return new PsiElementVisitor() {
            @Override
            public void visitFile(@NotNull PsiFile pFile) {
                Document dFile = PsiDocumentManager.getInstance(pFile.getProject()).getDocument(pFile);
                Collection<Warning> warnings = Stub.analyze(pFile.getVirtualFile().getPath());
                List<Pair<PsiElement, Integer>> pPairs = new ArrayList<>();
                List<Pair<PsiElement, Integer>> pUsefulPairs;

                assert (dFile != null);

                //Adding the PsiElement useful for the analysis
                pFile.accept(new PsiRecursiveElementWalkingVisitor() {
                    @Override
                    public void visitElement(@NotNull PsiElement pElement) {
                        super.visitElement(pElement);

                        //pElement deve iniziare e finire nella stessa riga per essere sottolineato
                        final int sLine = dFile.getLineNumber(pElement.getTextRange().getStartOffset());
                        final int eLine = dFile.getLineNumber(pElement.getTextRange().getEndOffset());

                        if ((pElement instanceof PyStatement || pElement instanceof PyExpression) && sLine == eLine)
                            pPairs.add(new Pair<>(pElement, sLine));
                    }
                });

                pUsefulPairs = new ArrayList<>(pPairs);
                Collections.copy(pUsefulPairs, pPairs);

                for (Pair<PsiElement, Integer> p : pPairs) {
                    //se i text length sono uguali li sottolineo tutti e due
                    pUsefulPairs.removeIf(a -> a.getSecond().equals(p.getSecond()) && a.getFirst().getTextLength() < p.getFirst().getTextLength());
                }

                //For each Warning
                for (Warning e : warnings) {
                    if ((e instanceof WarningWithLocation) && (((WarningWithLocation) e).getLocation() instanceof SourceCodeLocation)) {
                        final int wLine = ((SourceCodeLocation) ((WarningWithLocation) e).getLocation()).getLine();

                        for (Pair<PsiElement, Integer> p : pUsefulPairs)
                            if (wLine == p.getSecond())
                                pHolder.registerProblem(p.getFirst(), e.getMessage(), (LocalQuickFix) null);
                    }
                }
            }
        };
    };
}