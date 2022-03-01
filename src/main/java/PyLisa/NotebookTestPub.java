package PyLisa;

import PyLisa.ForAnalysis.test.java.it.unive.pylisa.notebooks.NotebookTest;
import it.unive.lisa.AnalysisException;
import it.unive.lisa.analysis.nonrelational.value.NonRelationalValueDomain;
import it.unive.pylisa.analysis.dataframes.DataframeAwareDomain;

import java.io.IOException;

public class NotebookTestPub extends NotebookTest {
    public NotebookTestPub(){}

    @Override
    public <T extends NonRelationalValueDomain<T> & DataframeAwareDomain<T, D>, D extends NonRelationalValueDomain<D>> void perform(String file, String kind, T value) throws IOException, AnalysisException {
        super.perform(file, kind, value);
    }
}