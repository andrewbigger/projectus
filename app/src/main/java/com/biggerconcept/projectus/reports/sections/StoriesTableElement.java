package com.biggerconcept.projectus.reports.sections;

import com.biggerconcept.sdk.serializers.documents.Doc;
import com.biggerconcept.projectus.State;
import com.biggerconcept.projectus.domain.Story;
import com.biggerconcept.projectus.reports.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Inserts a stories table into a report
 * 
 * @author Andrew Bigger
 */
public class StoriesTableElement extends Element {
    /**
     * Default constructor
     */
    public StoriesTableElement() {
        super();
    }
    
    /**
     * State based constructor.
     * 
     * @param state application state.
     */
    public StoriesTableElement(State state) {
        super(state);
    }
    
    /**
     * Inserts stories table into report document.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to read file
     */
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
         ArrayList<Story> stories = getDocument().getStories();
        
        document.table(
                headers(getState().bundle()), 
                body(stories)
        );
    }
    
    public boolean modifiable() {
        return false;
    }
    
    /**
     * Creates a header row array for the stories table.
     * 
     * @param bundle application resource bundle
     * 
     * @return headers as an array list
     */
    public static ArrayList<String> headers(ResourceBundle bundle) {
        ArrayList<String> headers = new ArrayList<>();
        
        headers.add(
                bundle
                    .getString("reports.elements.storiesTable.actor")
        );
        
        headers.add(
                bundle
                    .getString("reports.elements.storiesTable.intent")
        );
        
        headers.add(
                bundle
                    .getString("reports.elements.storiesTable.expectation")
        );
        
        return headers;
    }
    
    /**
     * Creates a table body for document stories.
     * 
     * @param stories stories to include in table body
     * 
     * @return epic body table as array list of array list.
    */
    public static ArrayList<ArrayList<String>> body(
            ArrayList<Story> stories
    ) {
        ArrayList<ArrayList<String>> rows = new ArrayList();
        
        for (Story s : stories) {
            ArrayList<String> row = new ArrayList<>();
            
            row.add(s.getActor().getName());
            row.add(s.getIntention());
            row.add(s.getExpectation());

            rows.add(row);
        }
        
        return rows;
    }

}
