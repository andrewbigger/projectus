package com.biggerconcept.projectus.reports.sections;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.projectus.State;
import com.biggerconcept.projectus.domain.Document;
import com.biggerconcept.projectus.domain.Story;
import com.biggerconcept.projectus.reports.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Inserts a stories outline into a report
 * 
 * @author Andrew Bigger
 */
public class StoriesOutlineElement extends Element {
    /**
     * Default constructor
     */
    public StoriesOutlineElement() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public StoriesOutlineElement(State state) {
        super(state);
    }
    
    /**
     * Inserts a story outline into a report document.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to write file
     */
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        try {
            Document openDocument = getState().getOpenDocument();

            ArrayList<Story> stories = 
                    openDocument.getStories();
            
            insertOutline(document, getState().bundle(), stories);
        } catch (Exception ex) {
            // skip story documentation when unable to construct timeline
        }
        
    }
    
    /**
     * Inserts outline into given document
     * 
     * @param doc report document
     * @param bundle application resource bundle
     * @param stories stories to insert
     */
    public static void insertOutline(
            Doc doc,
            ResourceBundle bundle,
            ArrayList<Story> stories
    ) {
        for (Story s : stories) {
            doc.h3(
                String.valueOf(s.getIdentifier())
            );

            insertActor(doc, bundle, s);
            insertIntent(doc, bundle, s);
            insertExpectation(doc, bundle, s);

            doc.br();
        }
    }
        
    /**
     * Element modifiable. Indicates whether to allow the presentation of a
     * editor dialog in the report builder.
     * 
     * This element is not modifiable, so no editor dialog will be called for.
     * 
     * @return false
     */
    public boolean modifiable() {
        return false;
    }
    
        /**
     * Inserts story actor outline into document.
     * 
     * @param document report document
     * @param bundle application bundle
     * @param story story
     */
    private static void insertActor(
            Doc document, 
            ResourceBundle bundle, 
            Story story
    ) {
        document.strong(
               bundle
                    .getString(
                            "reports.elements.storiesOutline.actor"
                    )
        );

        document.p(story.getActor().getName());
        document.nl();
    }
    
    /**
     * Inserts story intent outline into document.
     * 
     * @param document report document
     * @param bundle application bundle
     * @param story story
     */
    private static void insertIntent(
            Doc document, 
            ResourceBundle bundle, 
            Story story
    ) {
        document.strong(
               bundle
                    .getString(
                            "reports.elements.storiesOutline.intent"
                    )
        );

        document.p(story.getIntention());
        document.nl();
    }
    
    /**
     * Inserts story expectation outline into document.
     * 
     * @param document report document
     * @param bundle application bundle
     * @param story story
     */
    private static void insertExpectation(
            Doc document, 
            ResourceBundle bundle, 
            Story story
    ) {
        document.strong(
               bundle
                    .getString(
                            "reports.elements.storiesOutline.expectation"
                    )
        );

        document.p(story.getExpectation());
        document.nl();
    }

}
