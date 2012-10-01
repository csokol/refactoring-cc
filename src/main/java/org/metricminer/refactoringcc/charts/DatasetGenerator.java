package org.metricminer.refactoringcc.charts;

import java.util.Map;

import org.metricminer.refactoringcc.model.ProjectHistory;

public interface DatasetGenerator {
    public Map<Comparable, Number> computeDatasetFor(ProjectHistory history);
}
