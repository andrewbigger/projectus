package com.biggerconcept.projectus.reports.sections;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.doctree.domain.Node;
import com.biggerconcept.projectus.State;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Risk;
import com.biggerconcept.projectus.reports.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Inserts a selected epic risks outline into a report
 * 
 * @author Andrew Bigger
 */
public class SelectedEpicRisksOutlineElement extends Element {
    /**
     * Default constructor
     */
    public SelectedEpicRisksOutlineElement() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public SelectedEpicRisksOutlineElement(State state) {
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
     * Inserts selected epic risks outline into a report document.
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
            Epic openEpic = getState().getOpenEpic();

            ArrayList<Risk> risks = 
                    openEpic.getDocumentRisks();
            
            RisksOutlineElement.insertOutline(
                    document, 
                    getState().bundle(), 
                    risks
            );
        } catch (Exception ex) {
            // skip story documentation when unable to access selected epic
        }
        
    }

}
