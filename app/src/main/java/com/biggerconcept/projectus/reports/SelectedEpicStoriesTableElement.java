package com.biggerconcept.projectus.reports;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.projectus.State;
import com.biggerconcept.projectus.domain.Story;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Inserts a selected epic stories table into a report
 * 
 * @author Andrew Bigger
 */
public class SelectedEpicStoriesTableElement extends Element {
    /**
     * Default constructor
     */
    public SelectedEpicStoriesTableElement() {
        super();
    }
    
    /**
     * State based constructor.
     * 
     * @param state application state.
     */
    public SelectedEpicStoriesTableElement(State state) {
        super(state);
    }
    
    /**
     * Inserts selected epic stories table into report document.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to read file
     */
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        ArrayList<Story> stories = getState()
                .getOpenEpic()
                .getDocumentStories();
        
        document.table(
                StoriesTableElement.headers(getState().bundle()), 
                StoriesTableElement.body(stories)
        );
    }
    
    public boolean modifiable() {
        return false;
    }
    
}
