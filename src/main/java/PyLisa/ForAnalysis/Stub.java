package PyLisa.ForAnalysis;

import java.util.Collection;
import java.util.HashSet;

public class Stub {
    public Stub() {
    }

    public static Collection<Warning> analyze(String filePath) {
        Collection<Warning> result = new HashSet();
        result.add(new Stub.PyWarningWithLocation(new SourceCodeLocation(filePath, 1, 10), "bad warning at line 1!"));
        result.add(new Stub.PyWarningWithLocation(new SourceCodeLocation(filePath, 2, 4), "bad warning at line 2!"));
        result.add(new Stub.PyWarningWithLocation(new SourceCodeLocation(filePath, 3, 12), "bad warning at line 3!"));
        result.add(new Stub.PyWarningWithLocation(new SourceCodeLocation(filePath, 4, 18), "bad warning at line 4!"));
        result.add(new Stub.PyWarningWithLocation(new SourceCodeLocation(filePath, 5, 3), "bad warning at line 5!"));
        //result.add(new Stub.PyWarningWithLocation(new SourceCodeLocation(filePath, 6, 9), "bad warning at line 6!"));
        //result.add(new Stub.PyWarningWithLocation(new SourceCodeLocation(filePath, 7, 18), "bad warning at line 7!"));
        return result;
    }

    private static class PyWarningWithLocation extends WarningWithLocation {
        protected PyWarningWithLocation(CodeLocation location, String message) {
            super(location, message);
        }

        //public String toString() {
        //  String var10000 = super.getLocation();
        //  return var10000 + ": " + super.getMessage();
        //}
        public String toString() {
            String var10000 = super.getLocation().toString();
            return var10000 + ": " + super.getMessage();
        }
    }
}