package org.metricminer.refactoringcc.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.metricminer.refactoringcc.model.SourceCodeData;

public class SourceCodeDataSplitter {

    public List<List<SourceCodeData>> splitProjects(List<SourceCodeData> sources) {
        HashMap<String, List<SourceCodeData>> map = new HashMap<String, List<SourceCodeData>>();
        for (SourceCodeData source : sources) {
            String projectName = source.getProjectName();
            if (!map.containsKey(projectName)) {
                map.put(projectName, new ArrayList<SourceCodeData>());
            }
            map.get(projectName).add(source);
        }
        return new ArrayList<List<SourceCodeData>>(map.values());
    }

}
