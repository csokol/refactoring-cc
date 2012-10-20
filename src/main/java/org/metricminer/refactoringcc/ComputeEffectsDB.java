package org.metricminer.refactoringcc;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.metricminer.refactoringcc.factory.EntryDao;
import org.metricminer.refactoringcc.factory.SourceCodeDataDBFactory;
import org.metricminer.refactoringcc.model.SourceCodeData;

public class ComputeEffectsDB {
    private static Logger logger = Logger.getLogger(ComputeEffectsDB.class);

    public static void main(String[] args) throws FileNotFoundException {
        List<SourceCodeData> allSources = new ArrayList<SourceCodeData>();
        EntryDao entryDao = new EntryDao();
        SourceCodeDataDBFactory factory = new SourceCodeDataDBFactory(entryDao);
        allSources = factory.build();
        logger.debug("starting to compute effects");
        computeEffects(allSources);
    }

    private static void computeEffects(List<SourceCodeData> allSources) {
        RefactoringEffects refactoringEffects = new RefactoringEffects(
                allSources);
        refactoringEffects.compute();
    }
}
