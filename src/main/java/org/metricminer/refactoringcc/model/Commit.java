package org.metricminer.refactoringcc.model;

import java.util.Calendar;
import java.util.List;

public class Commit {
    private String message;
    private Calendar date;
    private List<SourceCode> sourcesModified;
    private Commit priorCommit;

    public Commit(String message, Calendar date,
            List<SourceCode> sourcesModified, Commit priorCommit) {
        this.message = message;
        this.date = date;
        this.sourcesModified = sourcesModified;
        this.priorCommit = priorCommit;
    }

    public Commit(String message, Calendar date, List<SourceCode> sourcesModified) {
        this(message, date, sourcesModified, null);
    }

    public String getMessage() {
        return message;
    }
}
