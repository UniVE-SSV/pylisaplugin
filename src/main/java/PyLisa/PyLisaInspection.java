package PyLisa;

import PyLisa.ForAnalysis.SourceCodeLocation;
import PyLisa.ForAnalysis.Stub;
import PyLisa.ForAnalysis.Warning;
import PyLisa.ForAnalysis.WarningWithLocation;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.*;
import com.jetbrains.python.psi.PyExpression;
import com.jetbrains.python.psi.PyStatement;
import org.jetbrains.annotations.NotNull;
import java.util.*;
import java.util.List;



public class PyLisaInspection extends LocalInspectionTool {

    static String fileName;

    static List<ProblemDescriptor> warnings_res;

    static Document notebook;


    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder pHolder, boolean isOnTheFly) {
        return new PsiElementVisitor() {
            @Override
            public void visitFile(@NotNull PsiFile pFile) {
                Document dFile = PsiDocumentManager.getInstance(pFile.getProject()).getDocument(pFile);
                Collection<Warning> warnings = Stub.analyze(pFile.getVirtualFile().getPath());

                //Map that links a line of the document with a Set of PsiElements
                Map<Integer, Set<PsiElement>> pMap = new HashMap<>();

                //List of the pElements with the longest TextLenght for each line of the document
                List<PsiElement> pElements = new ArrayList<>();

                assert (dFile != null);

                //Adding the PsiElements useful for the analysis
                pFile.accept(new PsiRecursiveElementWalkingVisitor() {
                    @Override
                    public void visitElement(@NotNull PsiElement pElement) {
                        super.visitElement(pElement);

                        //The pElement has to start and end in the same line
                        final int sLine = dFile.getLineNumber(pElement.getTextRange().getStartOffset());
                        final int eLine = dFile.getLineNumber(pElement.getTextRange().getEndOffset());

                        if ((pElement instanceof PyStatement || pElement instanceof PyExpression) && sLine == eLine){
                            pMap.computeIfAbsent(sLine, k -> new HashSet<>());
                            pMap.get(sLine).add(pElement);
                        }
                    }
                });


                //For each key in the Map
                for(Integer key : pMap.keySet()) {
                    PsiElement p = null;

                    for(PsiElement e : pMap.get(key)) {
                        if(p == null || p.getTextLength() < e.getTextLength())
                            p = e;
                    }

                    if(p != null)
                        pElements.add(p);
                }

                //For each Warning
                for (Warning e : warnings) {
                    if ((e instanceof WarningWithLocation) && (((WarningWithLocation) e).getLocation() instanceof SourceCodeLocation)) {
                        final int wLine = ((SourceCodeLocation) ((WarningWithLocation) e).getLocation()).getLine();

                        for (PsiElement pElement : pElements)
                            if (wLine == dFile.getLineNumber(pElement.getTextRange().getStartOffset()))
                                pHolder.registerProblem(pElement, e.getMessage(), (LocalQuickFix) null);




                    }
                }
                //get the name of the file analyzed and the results of the inspection
                if(!pHolder.getResults().isEmpty()){
                    fileName=pFile.getName();
                    warnings_res=pHolder.getResults();
                    //get the whole file analyzed
                    notebook=dFile;



                }

            }




        };


    }






    //prima parte: introduzione con spiegazione di cosa devo fare: analizzo codice python tramite un plugin su pycharm etc
    //seconda parte: componenti tecniche che precedono lo sviluppo del mio plugin, pylisa con errori e warning
    //terza parte: come ho sviluppato il plugin con trials and errors ed i miei approcci e limiti tecnici
    //quarta parte: risultati sperimentali

    //Febbraio 14 call 10:00-11:00

    //TODO Javadoc e Commenti
}