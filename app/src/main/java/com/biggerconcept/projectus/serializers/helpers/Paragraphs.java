package com.biggerconcept.projectus.serializers.helpers;

import com.biggerconcept.appengine.serializers.documents.Docx;

/**
 *
 * @author Andrew Bigger
 */
public class Paragraphs {
    public static void format(String text, Docx docx) {
        String[] paragraphs;
        
        paragraphs = text.split("\n");
        
        for (String s : paragraphs) {
            docx.p(s);
        }
    }
}
