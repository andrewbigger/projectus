package com.biggerconcept.projectus.reports;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.appengine.serializers.helpers.Paragraphs;
import com.biggerconcept.projectus.State;
import com.biggerconcept.projectus.domain.Epic;
import java.io.IOException;
import java.util.HashMap;

/**
 * Inserts a selected epic summary into a report
 * 
 * @author Andrew Bigger
 */
public class SelectedEpicSummaryElement extends Element {
    /**
     * Default constructor
     */
    public SelectedEpicSummaryElement() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public SelectedEpicSummaryElement(State state) {
        super(state);
    }
    
    /**
     * Inserts a selecteed epic summary into a report document.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to write file
     */
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        try {
            Epic selectedEpic = getState().getOpenEpic();
            Paragraphs.insert(document, selectedEpic.getSummary());
        } catch (Exception ex) {
            // skip when unable to retrieve selected epic
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
