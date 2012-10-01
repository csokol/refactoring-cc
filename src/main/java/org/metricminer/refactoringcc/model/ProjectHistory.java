package org.metricminer.refactoringcc.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class ProjectHistory {
    
    private List<ArtifactHistory> artifacts;
    private String name;

    public ProjectHistory(Collection<ArtifactHistory> artifacts) {
        this.artifacts = new ArrayList<ArtifactHistory>(artifacts);
    }

    public List<ArtifactHistory> getArtifacts() {
        return Collections.unmodifiableList(artifacts);
    }
    
    public List<Calendar> getVersionDates() {
        HashSet<Calendar> dates = new HashSet<Calendar>();
        for (ArtifactHistory artifact : artifacts) {
            dates.addAll(artifact.getVersionDates());
        }
        return new ArrayList<Calendar>(dates);
    }
    
    public List<SourceCode> getSourcesFrom(Calendar date) {
        List<SourceCode> sources = new ArrayList<SourceCode>();
        for (ArtifactHistory artifact : artifacts) {
            sources.addAll(artifact.getSourcesFrom(date));
        }
        return sources;
    }
}
