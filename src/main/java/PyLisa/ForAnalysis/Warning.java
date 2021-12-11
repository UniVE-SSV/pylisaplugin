package PyLisa.ForAnalysis;

import org.apache.commons.lang3.StringUtils;

public class Warning implements Comparable<Warning> {
    private final String message;

    public Warning(String message) {
        this.message = message;
    }

    public final String getMessage() {
        return this.message;
    }

    public String getTag() {
        return "GENERIC";
    }

    public final String getTaggedMessage() {
        String var10000 = this.getTag();
        return "[" + var10000 + "] " + this.getMessage();
    }

    public int compareTo(Warning o) {
        return StringUtils.compare(this.message, o.message);
    }

    public int hashCode() {
        boolean prime = true;
        int result = 1;
        result = 31 * result + (this.message == null ? 0 : this.message.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            Warning other = (Warning)obj;
            if (this.message == null) {
                if (other.message != null) {
                    return false;
                }
            } else if (!this.message.equals(other.message)) {
                return false;
            }

            return true;
        }
    }

    public String toString() {
        return this.getTaggedMessage();
    }
}