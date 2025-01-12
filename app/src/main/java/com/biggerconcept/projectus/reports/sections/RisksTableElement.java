package com.biggerconcept.projectus.reports.sections;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.projectus.State;
import com.biggerconcept.projectus.domain.Risk;
import com.biggerconcept.projectus.reports.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Inserts a risk table into a report
 * 
 * @author Andrew Bigger
 */
public class RisksTableElement extends Element {
    /**
     * Default constructor
     */
    public RisksTableElement() {
        super();
    }
    
    /**
     * State based constructor.
     * 
     * @param state application state.
     */
    public RisksTableElement(State state) {
        super(state);
    }
    
    /**
     * Inserts risks table into report document.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to read file
     */
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
         ArrayList<Risk> risks = getDocument().getRisks();
        
        document.table(
                headers(getState().bundle()), 
                body(risks)
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
                    .getString("reports.elements.risksTable.number")
        );
        
        headers.add(
                bundle
                    .getString("reports.elements.risksTable.name")
        );
        
        headers.add(
                bundle
                    .getString("reports.elements.risksTable.likelihood")
        );
        
        headers.add(
                bundle
                    .getString("reports.elements.risksTable.impact")
        );
        
        headers.add(
                bundle
                    .getString("reports.elements.risksTable.status")
        );
        
        return headers;
    }
    
    /**
     * Creates a table body for document risks.
     * 
     * @param risks risks to include in table body
     * 
     * @return table body as array list of array list.
    */
    public static ArrayList<ArrayList<String>> body(
            ArrayList<Risk> risks
    ) {
        ArrayList<ArrayList<String>> rows = new ArrayList();
        
        for (Risk r : risks) {
            ArrayList<String> row = new ArrayList<>();
            
            row.add(String.valueOf(r.getIdentifier()));
            row.add(r.getName());
            row.add(String.valueOf(r.getLikelihood()));
            row.add(String.valueOf(r.getImpact()));
            row.add(String.valueOf(r.getStatus()));

            rows.add(row);
        }
        
        return rows;
    }

}
