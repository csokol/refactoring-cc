package org.metricminer.refactoringcc.finder;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.PorterStemFilter;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;

public class IsRefactoring {

    public boolean isRefactoring(String message) {
        StringReader reader = new StringReader(message);
        TokenFilter tokenizer = buildTokenizer(reader);
        try {
            Token next = tokenizer.next();
            while (next != null) {
                if (next.termText().equals("refactor")) {
                    return true;
                }
                next = tokenizer.next();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private TokenFilter buildTokenizer(StringReader reader) {
        TokenStream tok = new StandardTokenizer(reader);
        PorterStemFilter steamFilter = new PorterStemFilter(tok);
        LowerCaseFilter tokenizer = new LowerCaseFilter(steamFilter);
        return tokenizer;
    }
}
