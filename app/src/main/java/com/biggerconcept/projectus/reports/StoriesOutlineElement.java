package com.biggerconcept.projectus.reports;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.appengine.serializers.helpers.Paragraphs;
import com.biggerconcept.projectus.State;
import com.biggerconcept.projectus.domain.Document;
import com.biggerconcept.projectus.domain.Story;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
            
            for (Story s : stories) {
                document.h3(
                        String.valueOf(s.getIdentifier())
                );
                
                document.strong(
                        getState()
                                .bundle()
                                .getString(
                                        "reports.elements.storiesOutline.actor"
                                )
                );

                document.p(s.getActor().getName());
                document.nl();
                
                document.strong(
                        getState()
                                .bundle()
                                .getString(
                                        "reports.elements.storiesOutline.intent"
                                )
                );
                Paragraphs.insert(document, s.getIntention());
                document.nl();
                
                document.strong(
                        getState()
                                .bundle()
                                .getString(
                                        "reports.elements.storiesOutline.expectation"
                                )
                );
                Paragraphs.insert(document, s.getExpectation());
                document.br();
            }

        } catch (Exception ex) {
            // skip story documentation when unable to construct timeline
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

}
