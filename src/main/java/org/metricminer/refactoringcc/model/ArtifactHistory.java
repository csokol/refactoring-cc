package org.metricminer.refactoringcc.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArtifactHistory {

    private Map<Calendar, List<SourceCode>> sourcesByDate;
    private String name;

    public ArtifactHistory(String className) {
        this.name = className;
        this.sourcesByDate = new HashMap<Calendar, List<SourceCode>>();
    }

    public void addSource(SourceCode sc) {
        if (sourcesByDate.containsKey(sc.getDate())) {
            List<SourceCode> sources = sourcesByDate.get(sc.getDate());
            sources.add(sc);
        } else {
            List<SourceCode> sources = new ArrayList<SourceCode>();
            sources.add(sc);
            sourcesByDate.put(sc.getDate(), sources);
        }
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
        return sourcesByDate.keySet();
    }

    public Collection<? extends SourceCode> getSourcesFrom(Calendar date) {
        List<SourceCode> sources = sourcesByDate.get(date);
        if (sources == null) {
            sources = new ArrayList<SourceCode>();
        }
        return sources;
    }

}
