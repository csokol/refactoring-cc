package org.metricminer.refactoringcc.charts;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.metricminer.refactoringcc.model.Commit;
import org.metricminer.refactoringcc.model.ProjectHistory;

public class CCByDateDatasetGenerator implements DatasetGenerator {
    
    private static Logger log = Logger.getLogger(CCByDateDatasetGenerator.class);

    @SuppressWarnings("rawtypes")
    @Override
    public Map<Comparable, Number> computeDatasetFor(ProjectHistory history) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Map<Comparable, Number> ccByDate = new TreeMap<Comparable, Number>();
        List<Calendar> dates = history.getVersionDates();
        Collections.sort(dates);
        
        int commitCount = 1;
        List<Commit> commits = history.commits();
        for (Commit commit : commits) {
            ccByDate.put(commit.getDate(), commit.getTotalCC());
            commitCount++;
            
        }

        return ccByDate;
    }

}
