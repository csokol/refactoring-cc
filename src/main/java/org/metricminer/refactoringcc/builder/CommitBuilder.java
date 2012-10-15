package org.metricminer.refactoringcc.builder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.metricminer.refactoringcc.model.Commit;
import org.metricminer.refactoringcc.model.SourceCodeData;

public class CommitBuilder {
    private String message;
    private Calendar date;
    private List<SourceCodeData> sourcesModified;
    private Commit priorCommit;
    private int totalCC;

    public CommitBuilder() {
        this.message = "default message";
        this.date = Calendar.getInstance();
        this.sourcesModified = new ArrayList<SourceCodeData>();
        this.priorCommit = null;
        this.totalCC = 0;
    }

    public Commit build() {
        return new Commit(message, date, sourcesModified, priorCommit, totalCC);
    }
    
    public CommitBuilder withTotalCC(int totalCC) {
        this.totalCC = totalCC;
        return this;
    }
    
    public CommitBuilder withPriorCommit(Commit priorCommit) {
        this.priorCommit = priorCommit;
        return this;
    }
    
    public CommitBuilder withMessage(String message) {
        this.message = message;
        return this;
    }
}
