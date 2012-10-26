package org.metricminer.refactoringcc.model;

import java.util.Calendar;
import java.util.List;

public class Commit {
    private String message;
    private Calendar date;
    private List<SourceCodeData> sourcesModified;
    private Commit priorCommit;
    private int totalCC;

    public Commit(String message, Calendar date,
            List<SourceCodeData> sourcesModified, Commit priorCommit, int totalCC) {
        this.message = message;
        this.date = date;
        this.sourcesModified = sourcesModified;
        this.priorCommit = priorCommit;
        this.totalCC = totalCC;
    }

    public Commit(String message, Calendar date, List<SourceCodeData> sourcesModified) {
        this(message, date, sourcesModified, null, 1);
    }

    public String getMessage() {
        return message;
    }
    
    public int getTotalCC() {
        return totalCC;
    }
    
    public Commit getPriorCommit() {
        return priorCommit;
    }
    
    public enum Effect {
        DECREASE, INCREASE, EQUAL
    }

    public Effect effect() {
        if (this.getTotalCC() < this.getPriorCommit().getTotalCC()) {
            return Effect.DECREASE;
        }
        if (this.getTotalCC() == this.getPriorCommit().getTotalCC()) {
            return Effect.EQUAL;
        }
        return Effect.INCREASE;
    }

    public boolean decreasedCC() {
        return priorCommit != null && this.priorCommit.getTotalCC() > totalCC;
    }
    
    public boolean increasedCC() {
        return priorCommit != null && this.priorCommit.getTotalCC() < totalCC;
    }
    
    public boolean equalCC() {
        return priorCommit != null && this.priorCommit.getTotalCC() < totalCC;
    }
}
