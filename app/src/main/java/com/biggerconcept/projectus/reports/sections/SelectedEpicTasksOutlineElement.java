package com.biggerconcept.projectus.reports.sections;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.appengine.serializers.helpers.Paragraphs;
import com.biggerconcept.projectus.State;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Preferences;
import com.biggerconcept.projectus.domain.Task;
import com.biggerconcept.projectus.reports.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Inserts a selected epic tasks outline into a report
 * 
 * @author Andrew Bigger
 */
public class SelectedEpicTasksOutlineElement extends Element {
    /**
     * Default constructor
     */
    public SelectedEpicTasksOutlineElement() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public SelectedEpicTasksOutlineElement(State state) {
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
     * Inserts selected epic tasks outline into a report document.
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

            ArrayList<Task> tasks = 
                    openEpic.getTasks();
            
            insertOutline(
                    document, 
                    getState().bundle(), 
                    getState().getOpenDocument().getPreferences(),
                    tasks
            );
        } catch (Exception ex) {
            // skip task documentation when unable to access selected epic
        }
        
    }
    
    /**
     * Inserts outline into given document
     * 
     * @param doc report document
     * @param bundle application resource bundle
     * @param prefs application preferences
     * @param tasks tasks to insert
     */
    public static void insertOutline(
            Doc doc,
            ResourceBundle bundle,
            Preferences prefs,
            ArrayList<Task> tasks
    ) {
        for (Task t : tasks) {
            doc.h3(t.getName());
            
            insertSizeTable(doc, bundle, prefs, t);
            insertDescription(doc, bundle, prefs, t);
            insertAcceptanceCriteria(doc, bundle, t);
            
            doc.br();
        }
    }
    
    /**
     * Inserts task size table into document.
     * 
     * @param document report document
     * @param bundle application resource bundle
     * @param prefs application preferences
     * @param task current task
     */
    private static void insertSizeTable(
            Doc document,
            ResourceBundle bundle,
            Preferences prefs,
            Task task
    ) {
        document.table(
                sizeTableHeaders(bundle), 
                sizeTableBody(bundle, prefs, task)
        );
    }
    
    /**
     * Inserts task description into document.
     * 
     * @param document report document
     * @param bundle application resource bundle
     * @param prefs application preferences
     * @param task current task
     */
    private static void insertDescription(
            Doc document,
            ResourceBundle bundle,
            Preferences prefs,
            Task task
    ) {
        document.strong(
                bundle.getString("reports.elements.tasksOutline.description")
        );

        Paragraphs.insert(document, task.getDescription());
        
        document.nl();
    }
    
    /**
     * Inserts acceptance criteria into document.
     * 
     * @param document report document
     * @param bundle application resource bundle
     * @param task current task
     */
    private static void insertAcceptanceCriteria(
            Doc document,
            ResourceBundle bundle,
            Task task
    ) {
        document.strong(
                bundle.getString("reports.elements.tasksOutline.acceptanceCriteria")
        );

        Paragraphs.insert(document, task.getAcceptanceCriteria());
        
        document.nl();
    }
    
    /**
     * Creates headers for size table
     * 
     * @param bundle application resource bundle
     * 
     * @return size table headers
     */
    private static ArrayList<String> sizeTableHeaders(ResourceBundle bundle) {
        ArrayList<String> headers = new ArrayList<>();
        
        headers.add(
               bundle.getString("reports.elements.tasksOutline.size") 
        );
        
        headers.add(
                bundle.getString("reports.elements.tasksOutline.estimate")
        );
        
        return headers;
    }
    
    /**
     * Produces size table body
     * 
     * @param bundle application resource bundle
     * @param prefs document preferences
     * @param task current task
     * 
     * @return size table body
     */
    private static ArrayList<ArrayList<String>> sizeTableBody(
            ResourceBundle bundle,
            Preferences prefs,
            Task task
    ) {
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        ArrayList<String> row = new ArrayList<>();
        
        row.add(String.valueOf(task.getSize()));
        row.add(
                String.valueOf(
                            prefs.estimateFor(
                                    task.getSize()
                            )
                    )
        );
        
        rows.add(row);
        
        return rows;
    }

}
