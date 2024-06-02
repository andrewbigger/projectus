package com.biggerconcept.projectus.reports;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.projectus.State;
import com.biggerconcept.projectus.domain.Risk;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Inserts a selected epic risks table into a report
 * 
 * @author Andrew Bigger
 */
public class SelectedEpicRisksTableElement extends Element {
    /**
     * Default constructor
     */
    public SelectedEpicRisksTableElement() {
        super();
    }
    
    /**
     * State based constructor.
     * 
     * @param state application state.
     */
    public SelectedEpicRisksTableElement(State state) {
        super(state);
    }
    
    /**
     * Inserts selected epic risks table into report document.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to read file
     */
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
         try {
            ArrayList<Risk> risks = getState()
                    .getOpenEpic()
                    .getDocumentRisks();

            document.table(
                    RisksTableElement.headers(getState().bundle()), 
                    RisksTableElement.body(risks)
            );
         } catch (Exception ex) {
             // ignore risks when epic is not selected
         }
    }
    
    public boolean modifiable() {
        return false;
    }
    
}
