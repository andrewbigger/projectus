package com.biggerconcept.projectus.reports.sections;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.doctree.domain.Node;
import com.biggerconcept.projectus.State;
import com.biggerconcept.projectus.domain.Preferences;
import com.biggerconcept.projectus.domain.Sprint;
import com.biggerconcept.projectus.reports.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Inserts a reference sprint table into a report
 * 
 * @author Andrew Bigger
 */
public class ReferenceSprintTableElement extends Element {
    /**
     * Default constructor
     */
    public ReferenceSprintTableElement() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public ReferenceSprintTableElement(State state) {
        super(state);
    }
    
    /**
     * Inserts a reference sprint table into a report document.
     * 
     * @param document report document
     * @param vars content variables
     * @param root resources root
     * 
     * @throws IOException when unable to write file
     */
    public void insertInto(Doc document, HashMap<String, String> vars, Node root) 
            throws IOException {        
         document.table(headers(getState().bundle()), body());
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
     * Creates a header row array for the reference sprint table.
     * 
     * @param bundle application resource bundle
     * 
     * @return headers as an array list
     */
    private ArrayList<String> headers(ResourceBundle bundle) {
        ArrayList<String> headers = new ArrayList<>();
        
        headers.add(
                bundle
                    .getString("reports.elements.referenceSprintTable.name")
        );
        
        headers.add(
                bundle
                    .getString(
                            "reports.elements.referenceSprintTable.points"
                    )
        );
        
        return headers;
    }
    
    /**
     * Creates a table body for reference sprint table
     * 
     * @return release table body table as array list of array list.
    */
    public ArrayList<ArrayList<String>> body() {
        ArrayList<ArrayList<String>> rows = new ArrayList();
        
        try {
            Preferences prefs = getState().getOpenDocument().getPreferences();

            rows.add(row(prefs.getRefSprintOne()));
            rows.add(row(prefs.getRefSprintTwo()));
            rows.add(row(prefs.getRefSprintThree()));
            rows.add(row(prefs.getRefSprintFour()));
        } catch (Exception ex) {
            return rows;
        }
        
        return rows;
    }
    
    private ArrayList<String> row(Sprint sprint) {
        ArrayList<String> row = new ArrayList<>();
        
        row.add(sprint.getName());
        row.add(String.valueOf(sprint.getCompletedPoints()));
        
        return row;
    }

}
