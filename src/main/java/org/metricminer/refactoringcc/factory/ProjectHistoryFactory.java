package org.metricminer.refactoringcc.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.metricminer.refactoringcc.model.ArtifactHistory;
import org.metricminer.refactoringcc.model.ProjectHistory;
import org.metricminer.refactoringcc.model.SourceCode;

public class ProjectHistoryFactory {

    public ProjectHistory build(List<SourceCode> sources) {
        Map<String, ArtifactHistory> artifacts = new HashMap<String, ArtifactHistory>();
        
        for (SourceCode source : sources) {
            String sourceName = source.getClassName();
            if (artifacts.containsKey(sourceName)) {
                ArtifactHistory artifactHistory = artifacts.get(sourceName);
                artifactHistory.addSource(source);
            } else {
                ArtifactHistory artifactHistory = new ArtifactHistory(sourceName);
                artifactHistory.addSource(source);
                artifacts.put(sourceName, artifactHistory);
            }
        }
        
        return new ProjectHistory(artifacts.values());
    }
    
}
