package com.biggerconcept.projectus.reports.sections;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.projectus.State;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Story;
import com.biggerconcept.projectus.reports.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Inserts a selected epic stories outline into a report
 * 
 * @author Andrew Bigger
 */
public class SelectedEpicStoriesOutlineElement extends Element {
    /**
     * Default constructor
     */
    public SelectedEpicStoriesOutlineElement() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public SelectedEpicStoriesOutlineElement(State state) {
        super(state);
    }
    
    /**
     * Controls whether element is modifiable. This element is not modifiable.
     * 
     * @return false
     */
    public boolean modifiable() {
        return false;
    }
    
    /**
     * Inserts selected epic stories outline into a report document.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to write file
     */
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        try {
            Epic openEpic = getState().getOpenEpic();

            ArrayList<Story> stories = 
                    openEpic.getDocumentStories();
            
            StoriesOutlineElement.insertOutline(
                    document, 
                    getState().bundle(), 
                    stories
            );
        } catch (Exception ex) {
            // skip story documentation when unable to access selected epic
        }
        
    }

}
