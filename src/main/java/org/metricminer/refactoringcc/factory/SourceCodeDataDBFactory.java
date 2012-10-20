package org.metricminer.refactoringcc.factory;

import java.util.ArrayList;
import java.util.List;

import org.metricminer.refactoringcc.model.SourceCodeData;

public class SourceCodeDataDBFactory extends SourceCodeDataFactory {
    
    private EntryDao dao;
    
    public SourceCodeDataDBFactory(EntryDao dao) {
        this.dao = dao;
    }

    public List<SourceCodeData> build() {
        ArrayList<SourceCodeData> result = new ArrayList<SourceCodeData>();
        List<String[]> list = dao.list();
        for (String[] row : list) {
            result.add(buildData(row));
        }
        return result;
    }

}
