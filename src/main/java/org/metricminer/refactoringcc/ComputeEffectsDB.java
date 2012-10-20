package org.metricminer.refactoringcc;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.metricminer.refactoringcc.factory.ConnectionFactory;
import org.metricminer.refactoringcc.factory.EntryDao;
import org.metricminer.refactoringcc.factory.SourceCodeDataDBFactory;
import org.metricminer.refactoringcc.model.SourceCodeData;

public class ComputeEffectsDB {
    private static Logger logger = Logger.getLogger(ComputeEffectsDB.class);

    public static void main(String[] args) throws FileNotFoundException {
        List<SourceCodeData> allSources = new ArrayList<SourceCodeData>();
        Connection connection = new ConnectionFactory().openConnection("jdbc:mysql://localhost/refactoring-cc-test");
        EntryDao entryDao = new EntryDao(connection);
        SourceCodeDataDBFactory factory = new SourceCodeDataDBFactory(entryDao);
        
        List<String> projects = entryDao.projects();
        for (String project : projects) {
            List<SourceCodeData> build = factory.build(project);
            logger.debug("starting to compute effects");
            computeEffects(build);
        }
        
    }

    private static void computeEffects(List<SourceCodeData> allSources) {
        RefactoringEffects refactoringEffects = new RefactoringEffects(
                allSources);
        refactoringEffects.compute();
    }
}
