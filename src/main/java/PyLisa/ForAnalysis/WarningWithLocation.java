package PyLisa.ForAnalysis;

public abstract class WarningWithLocation extends Warning {
    private final CodeLocation location;

    protected WarningWithLocation(CodeLocation location, String message) {
        super(message);
        this.location = location;
    }

    //public final String getLocation() {
    //  return this.location.toString();
    //}
    public final CodeLocation getLocation() {
        return this.location;
    }

    public final String getLocationWithBrackets() {
        return "[" + this.getLocation() + "]";
    }

    public int compareTo(Warning o) {
        if (!(o instanceof WarningWithLocation)) {
            return super.compareTo(o);
        } else {
            WarningWithLocation other = (WarningWithLocation)o;
            int cmp;
            return (cmp = this.location.compareTo(other.location)) != 0 ? cmp : super.compareTo(other);
        }
    }

    public int hashCode() {
        boolean prime = true;
        int result = super.hashCode();
        result = 31 * result + (this.location == null ? 0 : this.location.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!super.equals(obj)) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            WarningWithLocation other = (WarningWithLocation)obj;
            if (this.location == null) {
                if (other.location != null) {
                    return false;
                }
            } else if (!this.location.equals(other.location)) {
                return false;
            }

            return true;
        }
    }

    public abstract String toString();
}
