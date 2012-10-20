package org.metricminer.refactoringcc.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ProjectHistory {
    
    private List<ArtifactHistory> artifacts;
    private String projectName;

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
    
    public List<SourceCodeData> getSourcesFrom(Calendar date) {
        List<SourceCodeData> sources = new ArrayList<SourceCodeData>();
        for (ArtifactHistory artifact : artifacts) {
            sources.addAll(artifact.getSourcesFrom(date));
        }
        return sources;
    }
    
    public List<Commit> commits() {
        HashMap<String, Integer> ccBySource = new HashMap<String, Integer>();
        List<Commit> commits = new ArrayList<Commit>();
        List<Calendar> versionDates = getVersionDates();
        Collections.sort(versionDates);
        int totalCC = 0;
        Commit priorCommit = null;
        for (Calendar date : versionDates) {
            List<SourceCodeData> sourcesFrom = getSourcesFrom(date);
            totalCC = updateCC(sourcesFrom, ccBySource, totalCC);
            String message = sourcesFrom.get(0).getMessage();
            Commit commit = new Commit(message, date, sourcesFrom, priorCommit, totalCC);
            commits.add(commit);
            priorCommit = commit;
        }
        return commits;
    }
    
    private int updateCC(List<SourceCodeData> sourcesFrom,
            HashMap<String, Integer> ccBySource, int currentCC) {
        for (SourceCodeData sourceCodeData : sourcesFrom) {
            String name = sourceCodeData.getClassName();
            if (ccBySource.containsKey(name)) {
                currentCC -= ccBySource.get(name);
            }
            if (!sourceCodeData.isDelete()) {
                currentCC += sourceCodeData.getCC();
            }
            ccBySource.put(name, sourceCodeData.getCC());
        }
        return currentCC;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return this.projectName;
    }
}
