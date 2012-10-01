package org.metricminer.refactoringcc.model;

import java.util.Calendar;

public class SourceCode implements Comparable<SourceCode> {

    private final String message;
    private final Calendar date;
    private final String kind;
    private final Integer cc;
    private final String className;
    private final String projectName;

    public SourceCode(String message, Calendar date, String kind, Integer cc,
            String className, String projectName) {
                this.message = message;
                this.date = date;
                this.kind = kind;
                this.cc = cc;
                this.className = className;
                this.projectName = projectName;
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

    public Integer getCC() {
        return cc;
    }

    public String getKind() {
        return kind;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getProjectName() {
        return projectName;
    }
}
