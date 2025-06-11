package com.biggerconcept.projectus.reports.sections;

import com.biggerconcept.sdk.serializers.documents.Doc;
import com.biggerconcept.projectus.State;
import com.biggerconcept.projectus.domain.Preferences;
import com.biggerconcept.projectus.domain.Task;
import com.biggerconcept.projectus.reports.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Inserts a tasks table into a report
 * 
 * @author Andrew Bigger
 */
public class SelectedEpicTasksTableElement extends Element {
    /**
     * Default constructor
     */
    public SelectedEpicTasksTableElement() {
        super();
    }
    
    /**
     * State based constructor.
     * 
     * @param state application state.
     */
    public SelectedEpicTasksTableElement(State state) {
        super(state);
    }
    
    /**
     * Inserts tasks table into report document.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to read file
     */
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        try {
            ArrayList<Task> tasks = getState().getOpenEpic().getTasks();
        
            document.table(
                    headers(getState().bundle()), 
                    body(getState().getOpenDocument().getPreferences(), tasks)
            );
        } catch (Exception ex) {
            // skip table when selected epic is not set
        }
    }
    
    public boolean modifiable() {
        return false;
    }
    
    /**
     * Creates a header row array for the tasks table.
     * 
     * @param bundle application resource bundle
     * 
     * @return headers as an array list
     */
    public static ArrayList<String> headers(ResourceBundle bundle) {
        ArrayList<String> headers = new ArrayList<>();
        
        headers.add(
                bundle
                    .getString("reports.elements.tasksTable.number")
        );
        
        headers.add(
                bundle
                    .getString("reports.elements.tasksTable.name")
        );
        
        headers.add(
                bundle
                    .getString("reports.elements.tasksTable.size")
        );
        
        headers.add(
                bundle
                    .getString("reports.elements.tasksTable.estimate")
        );
        
        headers.add(
                bundle
                    .getString("reports.elements.tasksTable.status")
        );
        
        return headers;
    }
    
    /**
     * Creates a table body for tasks table.
     * 
     * @param prefs document preferences
     * @param tasks tasks to include in table body
     * 
     * @return epic body table as array list of array list.
    */
    public static ArrayList<ArrayList<String>> body(
            Preferences prefs,
            ArrayList<Task> tasks
    ) {
        ArrayList<ArrayList<String>> rows = new ArrayList();
        
        for (Task t : tasks) {
            ArrayList<String> row = new ArrayList<>();
            
            row.add(String.valueOf(t.getIdentifier()));
            row.add(t.getName());
            
            row.add(String.valueOf(t.getSize()));
            
            row.add(String.valueOf(
                    prefs.estimateFor(t.getSize())
            ));
            
            row.add(String.valueOf(t.getStatus()));
            
            rows.add(row);
        }
        
        return rows;
    }

}
