package com.biggerconcept.projectus.serializers.helpers;

import com.biggerconcept.projectus.domain.Task;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Risk;
import com.biggerconcept.projectus.domain.Story;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Helper methods for generating tables.
 * 
 * @author Andrew Bigger
 */
public class Tables {
    /**
     * Creates a header row array for the task table.The task table is used in 
     * the discovery document serialize-er.
     * 
     * @param bundle application resource bundle
     * 
     * @return headers as an array list
     */
    public static ArrayList<String> taskTableHeaders(ResourceBundle bundle) {
        ArrayList<String> headers = new ArrayList<>();
        
        headers.add(bundle.getString("epic.tasks.table.id"));
        headers.add(bundle.getString("epic.tasks.table.name"));
        headers.add(bundle.getString("epic.tasks.table.size"));
        
        return headers;
    }
    
    /**
     * Creates a table body for an epic's tasks.
     * 
     * This is used by the discovery document serialize-er.
     * 
     * @param epic epic to build table body for
     * 
     * @return epic body table as array list of array list.
    */
    public static ArrayList<ArrayList<String>> taskTableBody(Epic epic) {
        ArrayList<ArrayList<String>> rows = new ArrayList();
        
        int taskCount = 0;

        for (Task t : epic.getTasks()) {
            taskCount += 1;
            
            ArrayList<String> row = new ArrayList<>();
            row.add(String.valueOf(taskCount));
            row.add(t.getName());
            row.add(String.valueOf(t.getSize()));
            
            rows.add(row);
        }
        
        return rows;
    }
    
    /**
     * Creates headers for story table.
     * 
     * This is used by the discovery document serializer.
     * 
     * @param bundle application resource bundle
     * 
     * @return story table headers
     */
    public static ArrayList<String> storyTableHeaders(ResourceBundle bundle) {
        ArrayList<String> headers = new ArrayList<>();
        
        headers.add(bundle.getString("stories.table.id"));
        headers.add(bundle.getString("stories.table.actor"));
        headers.add(bundle.getString("stories.table.intention"));
        headers.add(bundle.getString("stories.table.expectation"));
        
        return headers;
    }
    
    /**
     * Creates a table body for an epic's stories.
     * 
     * This is used by the discovery document serializer.
     * 
     * @param epic epic to retrieve stories from
     * 
     * @return story details as array list of array list
    */
    public static ArrayList<ArrayList<String>> storyTableBody(Epic epic) {
        ArrayList<ArrayList<String>> rows = new ArrayList();
        
        int storyCount = 0;

        for (Story s : epic.getDocumentStories()) {
            storyCount += 1;
            
            ArrayList<String> row = new ArrayList<>();
            row.add("S" + String.valueOf(storyCount));
            row.add(s.getActor().getName());
            row.add(s.getIntention());
            row.add(s.getExpectation());
            
            rows.add(row);
        }
        
        return rows;
    }
    
    /**
     * Creates headers for risk table.
     * 
     * This is used by the discovery document serialize-er.
     * 
     * @param bundle application resource bundle
     * 
     * @return risk table headers
     */
    public static ArrayList<String> riskTableHeaders(ResourceBundle bundle) {
        ArrayList<String> headers = new ArrayList<>();
        
        headers.add(bundle.getString("risks.table.id"));
        headers.add(bundle.getString("risks.table.name"));
        headers.add(bundle.getString("risks.table.likelihood"));
        headers.add(bundle.getString("risks.table.impact"));
        headers.add(bundle.getString("risks.table.status"));
        
        return headers;
    }
    
    /**
     * Creates a table body for an epic's risks.
     * 
     * This is used by the discovery document serialize-er.
     * 
     * @param epic epic to retrieve risks from
     * 
     * @return risk details as array list of array list
    */
    public static ArrayList<ArrayList<String>> riskTableBody(Epic epic) {
        ArrayList<ArrayList<String>> rows = new ArrayList();
        
        int riskCount = 0;

        for (Risk r : epic.getDocumentRisks()) {
            riskCount += 1;
            
            ArrayList<String> row = new ArrayList<>();
            row.add("R" + String.valueOf(riskCount));
            row.add(r.getName());
            row.add(String.valueOf(r.getLikelihood()));
            row.add(String.valueOf(r.getImpact()));
            row.add(String.valueOf(r.getStatus()));
            
            rows.add(row);
        }
        
        return rows;
    }
    
    /**
     * Creates headers for risk table.
     * 
     * This is used by the discovery document serialize-er.
     * 
     * @param bundle application resource bundle
     * 
     * @return headers for risk summary table
     */
    public static ArrayList<String> riskSummaryTableHeaders(
            ResourceBundle bundle
    ) {
        ArrayList<String> headers = new ArrayList<>();
        
        headers.add(bundle.getString("risks.table.likelihood"));
        headers.add(bundle.getString("risks.table.impact"));
        headers.add(bundle.getString("risks.table.status"));
        
        return headers;
    }
    
    /**
     * Creates a table body for an epic's risks.This is used by the discovery
     * document serialize-er.
     * 
     *
     * @param risk risk to summarize
     * 
     * @return risk status as array list of array list
    */
    public static ArrayList<ArrayList<String>> riskSummaryTableBody(Risk risk) {       
        ArrayList<ArrayList<String>> rows = new ArrayList();
        
        ArrayList<String> row = new ArrayList<>();
        
        row.add(String.valueOf(risk.getLikelihood()));
        row.add(String.valueOf(risk.getImpact()));
        row.add(String.valueOf(risk.getStatus()));
        
        rows.add(row);
        
        return rows;
    }
}
