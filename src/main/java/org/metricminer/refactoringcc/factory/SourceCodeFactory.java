package org.metricminer.refactoringcc.factory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.metricminer.refactoringcc.model.SourceCode;

import au.com.bytecode.opencsv.CSVReader;

public class SourceCodeFactory {

    private CSVReader csvReader;

    public SourceCodeFactory(InputStream is) {
        csvReader = new CSVReader(new InputStreamReader(is), ';', '"');
    }

    public List<SourceCode> build() {
        try {
            List<SourceCode> sources = new ArrayList<SourceCode>();
            List<String[]> lines = csvReader.readAll();
            for (int i = 1; i < lines.size(); i++) {
                String[] line = lines.get(i);
                String message = line[0];
                String date = line[1];
                String kind = line[2];
                String cc = line[3];
                String className = line[4];
                sources.add(new SourceCode(message, date, kind, cc, className));
            }
            return sources;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
