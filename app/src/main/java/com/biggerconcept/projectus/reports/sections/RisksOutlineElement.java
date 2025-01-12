package com.biggerconcept.projectus.reports.sections;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.projectus.State;
import com.biggerconcept.projectus.domain.Document;
import com.biggerconcept.projectus.domain.Risk;
import com.biggerconcept.projectus.reports.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Inserts a risks outline into a report
 * 
 * @author Andrew Bigger
 */
public class RisksOutlineElement extends Element {
    /**
     * Default constructor
     */
    public RisksOutlineElement() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public RisksOutlineElement(State state) {
        super(state);
    }
    
    /**
     * Inserts a risks outline into a report document.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to write file
     */
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        try {
            Document openDocument = getState().getOpenDocument();

            ArrayList<Risk> risks = 
                    openDocument.getRisks();
            
            insertOutline(document, getState().bundle(), risks);
        } catch (Exception ex) {
            // skip story documentation when unable to construct timeline
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
    
    /**
     * Inserts risk outline into document
     * 
     * @param doc report document
     * @param bundle application resource bundle
     * @param risks risks to outline
     */
    public static void insertOutline(
            Doc doc,
            ResourceBundle bundle,
            ArrayList<Risk> risks
    ) {
        for (Risk r : risks) {
            doc.h3(r.getName());
            
            doc.table(detailTableHeader(
                    bundle), 
                    detailTableBody(r)
            );
            
            doc.nl();
                
            doc.strong(
                    bundle
                        .getString(
                                "reports.elements.risksOutline.details"
                        )
            );
            
            doc.md(r.getDetail());
            doc.nl();

            doc.strong(
                    bundle
                        .getString(
                                "reports.elements.risksOutline.mitigation"
                        )
            );
            
            doc.md(r.getMitigationStrategy());

            doc.br();
        }
    }
    
    /**
     * Creates header for risk status table
     * 
     * @param bundle application resource bundle
     * 
     * @return table headers
     */
    private static ArrayList<String> detailTableHeader(ResourceBundle bundle) {
        ArrayList<String> headers = new ArrayList<>();
        
        headers.add(
                bundle.getString("reports.elements.risksOutline.likelihood")
        );
        
        headers.add(
                bundle.getString("reports.elements.risksOutline.impact")
        );
        
        headers.add(
                bundle.getString("reports.elements.risksOutline.status")
        );
        
        return headers;
    }
    
    /**
     * Constructs body of risk detail table.
     * 
     * @param risk risk to summarize
     * 
     * @return table body
     */
    private static ArrayList<ArrayList<String>> detailTableBody(Risk risk) {
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        ArrayList<String> row = new ArrayList<>();
        
        row.add(String.valueOf(risk.getLikelihood()));
        row.add(String.valueOf(risk.getImpact()));
        row.add(String.valueOf(risk.getStatus()));
        
        rows.add(row);
        
        return rows;
    }

}
