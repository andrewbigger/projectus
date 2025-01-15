package com.biggerconcept.projectus.reports.sections;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.doctree.domain.Node;
import com.biggerconcept.projectus.State;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Outlook;
import com.biggerconcept.projectus.domain.Preferences;
import com.biggerconcept.projectus.domain.Projection;
import com.biggerconcept.projectus.reports.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Inserts a selected epic outlook table into a report
 * 
 * @author Andrew Bigger
 */
public class SelectedEpicOutlookTableElement extends Element {
    /**
     * Default constructor
     */
    public SelectedEpicOutlookTableElement() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public SelectedEpicOutlookTableElement(State state) {
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
     * Inserts selected epic outlook table into a report document.
     * 
     * @param document report document
     * @param vars content variables
     * @param root resources root
     * 
     * @throws IOException when unable to write file
     */
    public void insertInto(Doc document, HashMap<String, String> vars, Node root) 
            throws IOException {
        try {
            ResourceBundle bundle = getState().bundle();
            Preferences prefs = getState().getOpenDocument().getPreferences();
            Epic openEpic = getState().getOpenEpic();
            
            Outlook outlook = openEpic.getOutlook();
            outlook.calculate(prefs, openEpic, false);
            
            insertOutlookTable(document, bundle, prefs, outlook);
        } catch (Exception ex) {
            // skip task documentation when unable to access selected epic
        }
        
    }
    
    /**
     * Inserts outlook table into document.
     * 
     * @param document report document
     * @param bundle application resource bundle
     * @param prefs application preferences
     * @param outlook current epic outlook
     */
    private static void insertOutlookTable(
            Doc document,
            ResourceBundle bundle,
            Preferences prefs,
            Outlook outlook
    ) {
        document.table(
                headers(bundle), 
                body(bundle, prefs, outlook)
        );
    }
    
    /**
     * Creates headers for outlook table
     * 
     * @param bundle application resource bundle
     * 
     * @return outlook table headers
     */
    private static ArrayList<String> headers(ResourceBundle bundle) {
        ArrayList<String> headers = new ArrayList<>();
        
        headers.add(
               bundle.getString("reports.elements.outlookTable.outlook") 
        );
        
        headers.add(
               bundle.getString("reports.elements.outlookTable.pointsPerSprint") 
        );
        
        headers.add(
               bundle.getString("reports.elements.outlookTable.sprints") 
        );
        
        headers.add(
               bundle.getString("reports.elements.outlookTable.weeks") 
        );
        
        return headers;
    }
    
    /**
     * Produces outlook table body
     * 
     * @param bundle application resource bundle
     * @param prefs document preferences
     * @param outlook current outlook
     * 
     * @return size table body
     */
    private static ArrayList<ArrayList<String>> body(
            ResourceBundle bundle,
            Preferences prefs,
            Outlook outlook
    ) {
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        
        for (Projection p : outlook.projections()) {
            ArrayList<String> row = new ArrayList<>();
        
            row.add(String.valueOf(p.getName()));
            row.add(String.valueOf(p.getPointsPerSprint()));
            row.add(String.valueOf(p.getSprints()));
            row.add(String.valueOf(p.getWeeks()));
            
            rows.add(row);
        }
        
        return rows;
    }

}
