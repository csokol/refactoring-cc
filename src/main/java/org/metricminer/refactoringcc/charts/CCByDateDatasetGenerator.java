package org.metricminer.refactoringcc.charts;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.metricminer.refactoringcc.model.ProjectHistory;
import org.metricminer.refactoringcc.model.SourceCode;

public class CCByDateDatasetGenerator implements DatasetGenerator {

    @Override
    public Map<Comparable, Number> computeDatasetFor(ProjectHistory history) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh-mm");
        Map<String, Number> ccByClass = new HashMap<String, Number>();
        Map<Comparable, Number> ccByDate = new TreeMap<Comparable, Number>();
        List<Calendar> dates = history.getVersionDates();
        Collections.sort(dates);
        Integer currentCC = 0;

        for (Calendar date : dates) {
            Integer oldCC = currentCC;
            List<SourceCode> sourcesFrom = history.getSourcesFrom(date);
            int deleted = 0;
            for (SourceCode sc : sourcesFrom) {
                if (ccByClass.containsKey(sc.getClassName())) {
                    Integer oldThisCC = ccByClass.get(sc.getClassName()).intValue();
                    currentCC -= oldThisCC;
                }
                if (!sc.getKind().equals("DELETED")) {
                    currentCC += sc.getCC();
                    ccByClass.put(sc.getClassName(), sc.getCC());
                } else {
                    deleted++;
                }
            }
            ccByDate.put(date, currentCC);

            System.out.println("(" + deleted + " deleted) "
                    + sourcesFrom.get(0).getMessage());
            System.out.println(format.format(date.getTime()) + " - " + oldCC
                    + " -> " + currentCC + "\n");
        }
        return ccByDate;
    }

}
