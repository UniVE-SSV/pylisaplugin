package PyLisa.ForAnalysis;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class JsonReport {

    private final Set<JsonWarning> warnings;

    private final Set<String> files;


    public JsonReport() {
        this(Collections.emptyList(), Collections.emptyList());
    }


    public JsonReport(Collection<PyLisa.ForAnalysis.Warning> warnings, Collection<String> files) {
        this.warnings = new TreeSet<>();
        this.files = new TreeSet<>(files);
        for (PyLisa.ForAnalysis.Warning warn : warnings)
            this.warnings.add(new JsonWarning(warn));
    }


    public Collection<JsonWarning> getWarnings() {
        return warnings;
    }


    public Collection<String> getFiles() {
        return files;
    }


    public void dump(Writer writer) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.writeValue(writer, this);
    }


    public static JsonReport read(Reader reader) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(reader, JsonReport.class);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((files == null) ? 0 : files.hashCode());
        result = prime * result + ((warnings == null) ? 0 : warnings.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JsonReport other = (JsonReport) obj;
        if (files == null) {
            if (other.files != null)
                return false;
        } else if (!files.equals(other.files))
            return false;
        if (warnings == null) {
            if (other.warnings != null)
                return false;
        } else if (!warnings.equals(other.warnings))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "JsonAnalysisReport [findings=" + warnings + ", files=" + files + "]";
    }


    public static class JsonWarning implements Comparable<JsonWarning> {

        private String message;


        /*public JsonWarning(PyLisa.ForAnalysis.Warning warn) {
            this.message = null;
        }*/


        public JsonWarning(Warning warning) {
            this.message = warning.toString();
        }


        public String getMessage() {
            return message;
        }


        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return getMessage();
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((message == null) ? 0 : message.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            JsonWarning other = (JsonWarning) obj;
            if (message == null) {
                if (other.message != null)
                    return false;
            } else if (!message.equals(other.message))
                return false;
            return true;
        }

        @Override
        public int compareTo(JsonWarning o) {
            return message.compareTo(o.message);
        }
    }
}

