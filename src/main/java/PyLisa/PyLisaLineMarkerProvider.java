package PyLisa;

import PyLisa.ForAnalysis.SourceCodeLocation;
import PyLisa.ForAnalysis.Stub;
import PyLisa.ForAnalysis.Warning;
import PyLisa.ForAnalysis.WarningWithLocation;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.psi.PsiElement;
import com.jetbrains.python.psi.PyExpression;
import com.jetbrains.python.psi.PyStatement;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class PyLisaLineMarkerProvider extends RelatedItemLineMarkerProvider {
    @Override
    public void collectNavigationMarkers(@NotNull List<? extends PsiElement> elements, @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result, boolean forNavigation) {
        Collection<Warning> warnings = Stub.analyze(elements.get(0).getContainingFile().getVirtualFile().getPath());

        for(Warning e : warnings) {
            if ((e instanceof WarningWithLocation) && (((WarningWithLocation) e).getLocation() instanceof SourceCodeLocation)) {
                final int wLine = ((SourceCodeLocation) ((WarningWithLocation) e).getLocation()).getLine();
                final int wColumn = ((SourceCodeLocation) ((WarningWithLocation) e).getLocation()).getCol();

                for (PsiElement pElement : elements) {
                    if (pElement instanceof PyStatement || pElement instanceof PyExpression && wLine == 1) {
                        //result.add(new RelatedItemLineMarkerInfo<PsiElement>());
                    }
                }
            }
        }
    }
}