package PyLisa.ForAnalysis;

import java.util.Objects;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

public class SourceCodeLocation implements CodeLocation {
    private final String sourceFile;
    private final int line;
    private final int col;

    public SourceCodeLocation(String sourceFile, int line, int col) {
        Objects.requireNonNull(sourceFile, "The source file cannot be null");
        if (line == -1) {
            throw new IllegalArgumentException("Line number cannot be negative");
        } else if (col == -1) {
            throw new IllegalArgumentException("Column number cannot be negative");
        } else {
            this.sourceFile = FilenameUtils.separatorsToUnix(sourceFile);
            this.line = line;
            this.col = col;
        }
    }

    //public String getCodeLocation() {
    //  return "'" + this.sourceFile + "':" + this.line + ":" + this.col;
    //}
    public CodeLocation getCodeLocation() {
        return this;
    }

    public final String getSourceFile() {
        return String.valueOf(this.sourceFile);
    }

    public final int getLine() {
        return this.line;
    }

    public final int getCol() {
        return this.col;
    }

    public int hashCode() {
        boolean prime = true;
        int result = 1;
        result = 31 * result + this.col;
        result = 31 * result + this.line;
        result = 31 * result + (this.sourceFile == null ? 0 : this.sourceFile.hashCode());
        return result;
    }

    public String toString() {
        return "'" + this.sourceFile + "':" + this.line + ":" + this.col;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            SourceCodeLocation other = (SourceCodeLocation)obj;
            if (this.col != other.col) {
                return false;
            } else if (this.line != other.line) {
                return false;
            } else {
                if (this.sourceFile == null) {
                    if (other.sourceFile != null) {
                        return false;
                    }
                } else if (!this.sourceFile.equals(other.sourceFile)) {
                    return false;
                }

                return true;
            }
        }
    }

    public int compareTo(CodeLocation other) {
        if (!(other instanceof SourceCodeLocation)) {
            return -1;
        } else {
            SourceCodeLocation o = (SourceCodeLocation)other;
            int cmp;
            if ((cmp = StringUtils.compare(this.getSourceFile(), o.getSourceFile())) != 0) {
                return cmp;
            } else {
                return (cmp = Integer.compare(this.getLine(), o.getLine())) != 0 ? cmp : Integer.compare(this.getCol(), o.getCol());
            }
        }
    }
}