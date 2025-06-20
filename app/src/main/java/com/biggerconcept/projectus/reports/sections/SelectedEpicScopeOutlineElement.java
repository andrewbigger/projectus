package com.biggerconcept.projectus.reports.sections;

import com.biggerconcept.sdk.serializers.documents.Doc;
import com.biggerconcept.sdk.doctree.domain.Node;
import com.biggerconcept.projectus.State;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.reports.Element;
import java.io.IOException;
import java.util.HashMap;

/**
 * Inserts a selected epic scope outline into a report
 * 
 * @author Andrew Bigger
 */
public class SelectedEpicScopeOutlineElement extends Element {
    /**
     * Default constructor
     */
    public SelectedEpicScopeOutlineElement() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public SelectedEpicScopeOutlineElement(State state) {
        super(state);
    }
    
    /**
     * Inserts a selected epic scope outline into a report document.
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
            Epic selectedEpic = getState().getOpenEpic();
            
            document.h3(
                    getState()
                        .bundle()
                        .getString("reports.elements.scopeOutline.included")
            );
            
            document.ol(selectedEpic.getScope().getIncluded());
            
            document.h3(
                    getState()
                        .bundle()
                        .getString("reports.elements.scopeOutline.excluded")
            );
            
            document.ol(selectedEpic.getScope().getExcluded());
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
