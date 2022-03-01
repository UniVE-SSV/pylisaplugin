package PyLisa.ForAnalysis.test.java.it.unive.pylisa.notebooks;

import it.unive.lisa.AnalysisException;
import it.unive.pylisa.analysis.dataframes.transformation.DataframeDomain;
import java.io.IOException;
import org.junit.Test;

public class PyTransformationsTest extends NotebookTest {

	private void runTest(String file) throws IOException, AnalysisException {
		perform(getClass().getClassLoader().getResource(file).getFile(), "py-transformations",
				new DataframeDomain());
	}

	@Test
	public void testCovid19() throws IOException, AnalysisException {
		runTest("pyTest/data/covid-19.py");
	}

	@Test
	public void testCreditFraud() throws IOException, AnalysisException {
		runTest("pyTest/data/credit-fraud.py");
	}

	@Test
	public void testDataExploration() throws IOException, AnalysisException {
		runTest("pyTest/data/data-exploration.py");
	}

	@Test
	public void testGuide() throws IOException, AnalysisException {
		runTest("pyTest/data/guide.py");
	}

	@Test
	public void testTitanic() throws IOException, AnalysisException {
		runTest("pyTest/data/titanic.py");
	}
}
