package com.biggerconcept.projectus.serializers.helpers;

import com.biggerconcept.appengine.serializers.documents.Doc;

/**
 *
 * @author Andrew Bigger
 */
public class Paragraphs {
    public static void format(String text, Doc doc) {
        String[] paragraphs;
        
        paragraphs = text.split("\n");
        
        for (String s : paragraphs) {
            doc.p(s);
        }
    }
}
