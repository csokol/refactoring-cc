package org.metricminer.refactoringcc.model;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class ArtifactHistory {

    private Set<SourceCode> sources;
    private String name;

    public ArtifactHistory(String className) {
        name = className;
        sources = new TreeSet<SourceCode>();
    }

    public void addSource(SourceCode sc) {
        sources.add(sc);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        ArtifactHistory other = (ArtifactHistory) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    public String getName() {
        return name;
    }

    public Set<Calendar> getVersionDates() {
        HashSet<Calendar> dates = new HashSet<Calendar>();
        for (SourceCode sc : sources) {
            dates.add(sc.getDate());
        }
        return dates;
    }

}
