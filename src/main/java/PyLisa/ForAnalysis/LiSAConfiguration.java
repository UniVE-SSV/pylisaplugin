package PyLisa.ForAnalysis;



import it.unive.lisa.analysis.AbstractState;
import it.unive.lisa.analysis.Lattice;
import it.unive.lisa.checks.semantic.SemanticCheck;
import it.unive.lisa.checks.syntactic.SyntacticCheck;
import it.unive.lisa.checks.warnings.Warning;
import it.unive.lisa.interprocedural.InterproceduralAnalysis;
import it.unive.lisa.interprocedural.OpenCallPolicy;
import it.unive.lisa.interprocedural.WorstCasePolicy;
import it.unive.lisa.interprocedural.callgraph.CallGraph;
import it.unive.lisa.program.cfg.CFG;
import it.unive.lisa.program.cfg.statement.Statement;
import it.unive.lisa.program.cfg.statement.call.OpenCall;
import it.unive.lisa.util.collections.workset.FIFOWorkingSet;
import it.unive.lisa.util.collections.workset.WorkingSet;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;


public class LiSAConfiguration {


    public static final int DEFAULT_WIDENING_THRESHOLD = 5;


    private final Collection<SyntacticCheck> syntacticChecks;


    private final Collection<SemanticCheck<?, ?, ?, ?>> semanticChecks;


    private CallGraph callGraph;


    private InterproceduralAnalysis<?, ?, ?, ?> interproceduralAnalysis;


    private AbstractState<?, ?, ?, ?> abstractState;


    private boolean dumpCFGs;


    private boolean dumpTypeInference;

    private boolean dumpAnalysis;


    private boolean jsonOutput;


    private String workdir;


    private int wideningThreshold;


    private Class<?> fixpointWorkingSet;


    private OpenCallPolicy openCallPolicy;


    public LiSAConfiguration() {
        this.syntacticChecks = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.semanticChecks = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.workdir = Paths.get(".").toAbsolutePath().normalize().toString();
        this.wideningThreshold = DEFAULT_WIDENING_THRESHOLD;
        this.fixpointWorkingSet = FIFOWorkingSet.class;
        this.openCallPolicy = WorstCasePolicy.INSTANCE;
    }


    public LiSAConfiguration addSyntacticCheck(SyntacticCheck check) {
        syntacticChecks.add(check);
        return this;
    }


    public LiSAConfiguration addSyntacticChecks(Collection<SyntacticCheck> checks) {
        syntacticChecks.addAll(checks);
        return this;
    }


    public LiSAConfiguration addSemanticCheck(SemanticCheck<?, ?, ?, ?> check) {
        semanticChecks.add(check);
        return this;
    }



    public LiSAConfiguration addSemanticChecks(Collection<SemanticCheck<?, ?, ?, ?>> checks) {
        semanticChecks.addAll(checks);
        return this;
    }


    public LiSAConfiguration setCallGraph(CallGraph callGraph) {
        this.callGraph = callGraph;
        return this;
    }


    public LiSAConfiguration setInterproceduralAnalysis(InterproceduralAnalysis<?, ?, ?, ?> analysis) {
        this.interproceduralAnalysis = analysis;
        return this;
    }


    public LiSAConfiguration setAbstractState(AbstractState<?, ?, ?, ?> state) {
        this.abstractState = state;
        return this;
    }

    public LiSAConfiguration setDumpCFGs(boolean dumpCFGs) {
        this.dumpCFGs = dumpCFGs;
        return this;
    }



    public LiSAConfiguration setDumpTypeInference(boolean dumpTypeInference) {
        this.dumpTypeInference = dumpTypeInference;
        return this;
    }


    public LiSAConfiguration setDumpAnalysis(boolean dumpAnalysis) {
        this.dumpAnalysis = dumpAnalysis;
        return this;
    }



    public LiSAConfiguration setJsonOutput(boolean jsonOutput) {
        this.jsonOutput = jsonOutput;
        return this;
    }


    public LiSAConfiguration setWorkdir(String workdir) {
        this.workdir = Paths.get(workdir).toAbsolutePath().normalize().toString();
        return this;
    }


    public LiSAConfiguration setFixpointWorkingSet(Class<? extends WorkingSet<Statement>> fixpointWorkingSet) {
        this.fixpointWorkingSet = fixpointWorkingSet;
        return this;
    }



    public LiSAConfiguration setWideningThreshold(int wideningThreshold) {
        this.wideningThreshold = wideningThreshold;
        return this;
    }


    public LiSAConfiguration setOpenCallPolicy(OpenCallPolicy openCallPolicy) {
        this.openCallPolicy = openCallPolicy;
        return this;
    }


    public CallGraph getCallGraph() {
        return callGraph;
    }


    public InterproceduralAnalysis<?, ?, ?, ?> getInterproceduralAnalysis() {
        return interproceduralAnalysis;
    }


    public AbstractState<?, ?, ?, ?> getAbstractState() {
        return abstractState;
    }


    public Collection<SyntacticCheck> getSyntacticChecks() {
        return syntacticChecks;
    }


    public Collection<SemanticCheck<?, ?, ?, ?>> getSemanticChecks() {
        return semanticChecks;
    }


    public boolean isDumpCFGs() {
        return dumpCFGs;
    }


    public boolean isDumpTypeInference() {
        return dumpTypeInference;
    }


    public boolean isDumpAnalysis() {
        return dumpAnalysis;
    }


    public boolean isJsonOutput() {
        return jsonOutput;
    }


    public String getWorkdir() {
        return workdir;
    }


    @SuppressWarnings("unchecked")
    public Class<? extends WorkingSet<Statement>> getFixpointWorkingSet() {
        return (Class<? extends WorkingSet<Statement>>) fixpointWorkingSet;
    }


    public int getWideningThreshold() {
        return wideningThreshold;
    }


    public OpenCallPolicy getOpenCallPolicy() {
        return openCallPolicy;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((callGraph == null) ? 0 : callGraph.hashCode());
        result = prime * result + (dumpAnalysis ? 1231 : 1237);
        result = prime * result + (dumpCFGs ? 1231 : 1237);
        result = prime * result + (dumpTypeInference ? 1231 : 1237);
        result = prime * result + ((fixpointWorkingSet == null) ? 0 : fixpointWorkingSet.hashCode());
        result = prime * result + ((interproceduralAnalysis == null) ? 0 : interproceduralAnalysis.hashCode());
        result = prime * result + (jsonOutput ? 1231 : 1237);
        result = prime * result + ((semanticChecks == null) ? 0 : semanticChecks.hashCode());
        result = prime * result + ((abstractState == null) ? 0 : abstractState.hashCode());
        result = prime * result + ((syntacticChecks == null) ? 0 : syntacticChecks.hashCode());
        result = prime * result + wideningThreshold;
        result = prime * result + ((workdir == null) ? 0 : workdir.hashCode());
        result = prime * result + ((openCallPolicy == null) ? 0 : openCallPolicy.hashCode());
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
        LiSAConfiguration other = (LiSAConfiguration) obj;
        if (callGraph == null) {
            if (other.callGraph != null)
                return false;
        } else if (!callGraph.equals(other.callGraph))
            return false;
        if (dumpAnalysis != other.dumpAnalysis)
            return false;
        if (dumpCFGs != other.dumpCFGs)
            return false;
        if (dumpTypeInference != other.dumpTypeInference)
            return false;
        if (fixpointWorkingSet == null) {
            if (other.fixpointWorkingSet != null)
                return false;
        } else if (!fixpointWorkingSet.equals(other.fixpointWorkingSet))
            return false;
        if (interproceduralAnalysis == null) {
            if (other.interproceduralAnalysis != null)
                return false;
        } else if (!interproceduralAnalysis.equals(other.interproceduralAnalysis))
            return false;
        if (jsonOutput != other.jsonOutput)
            return false;
        if (semanticChecks == null) {
            if (other.semanticChecks != null)
                return false;
        } else if (!semanticChecks.equals(other.semanticChecks))
            return false;
        if (abstractState == null) {
            if (other.abstractState != null)
                return false;
        } else if (!abstractState.equals(other.abstractState))
            return false;
        if (syntacticChecks == null) {
            if (other.syntacticChecks != null)
                return false;
        } else if (!syntacticChecks.equals(other.syntacticChecks))
            return false;
        if (wideningThreshold != other.wideningThreshold)
            return false;
        if (workdir == null) {
            if (other.workdir != null)
                return false;
        } else if (!workdir.equals(other.workdir))
            return false;
        if (openCallPolicy == null) {
            if (other.openCallPolicy != null)
                return false;
        } else if (!openCallPolicy.equals(other.openCallPolicy))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("LiSA configuration:")
                .append("\n  workdir: ")
                .append(String.valueOf(workdir))
                .append("\n  dump input cfgs: ")
                .append(dumpCFGs)
                .append("\n  dump inferred types: ")
                .append(dumpTypeInference)
                .append("\n  dump analysis results: ")
                .append(dumpAnalysis)
                .append("\n  dump json report: ")
                .append(jsonOutput)
                .append("\n  ")
                .append(syntacticChecks.size())
                .append(" syntactic checks to execute")
                .append((syntacticChecks.isEmpty() ? "" : ":"));
        // TODO automatic way to keep this updated?
        for (SyntacticCheck check : syntacticChecks)
            res.append("\n      ")
                    .append(check.getClass().getSimpleName());
        res.append("\n  ")
                .append(semanticChecks.size())
                .append(" semantic checks to execute")
                .append((semanticChecks.isEmpty() ? "" : ":"));
        for (SemanticCheck<?, ?, ?, ?> check : semanticChecks)
            res.append("\n      ")
                    .append(check.getClass().getSimpleName());
        return res.toString();
    }
}
