package org.metricminer.refactoringcc.model;

public class SourceCode {

    private final String message;
    private final String date;
    private final String kind;
    private final String cc;
    private final String className;

    public SourceCode(String message, String date, String kind, String cc,
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

    
    
}
