package br.com.caelum.tubaina.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Slugged {
    private String title;

    public Slugged(String title) {
        // The pattern must accept any char except for digits or numbers
        Pattern pattern = Pattern.compile("(?i)(?s)(\\W)+");

        title = title.toLowerCase();

        Matcher matcher = pattern.matcher(title);
        title = matcher.replaceAll("-");

        title = title.replaceFirst("-$", "");
        title = title.replaceFirst("^-", "");
        this.title = title;
    }
    
    @Override
    public String toString() {
        return title;
    }
}