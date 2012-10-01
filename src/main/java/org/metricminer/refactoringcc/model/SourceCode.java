package org.metricminer.refactoringcc.model;

import java.util.Calendar;

public class SourceCode implements Comparable<SourceCode> {

    private final String message;
    private final Calendar date;
    private final String kind;
    private final String cc;
    private final String className;

    public SourceCode(String message, Calendar date, String kind, String cc,
            String className) {
                this.message = message;
                this.date = date;
                this.kind = kind;
                this.cc = cc;
                this.className = className;
    }
    
    public String getClassName() {
        return className;
    }

    @Override
    public String toString() {
        return "SourceCode [message=" + message + ", date=" + date + ", kind="
                + kind + ", cc=" + cc + ", className=" + className + "]";
    }
    
    public Calendar getDate() {
        return date;
    }

    @Override
    public int compareTo(SourceCode o) {
        return date.compareTo(o.getDate());
    }

    
    
}
