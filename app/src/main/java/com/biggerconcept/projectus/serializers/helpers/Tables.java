package com.biggerconcept.projectus.serializers.helpers;

import com.biggerconcept.projectus.domain.Task;
import com.biggerconcept.projectus.domain.Epic;
import java.util.ArrayList;

/**
 * Helper methods for generating tables.
 * 
 * @author Andrew Bigger
 */
public class Tables {
    /**
     * Creates a header row array for the task table.
     * 
     * The task table is used in the discovery document serializer.
     * 
     * @return 
     */
    public static ArrayList<String> taskTableHeaders() {
        ArrayList<String> headers = new ArrayList<>();
        
        headers.add("#"); // TODO: strings
        headers.add("Description");
        headers.add("Story");
        headers.add("Estimate");
        
        return headers;
    }
    
    /**
     * Creates a table body for an epic's tasks.
     * 
     * This is used by the discovery document serializer.
     * 
     * @param epic
     * 
     * @return
    */
    public static ArrayList<ArrayList<String>> taskTableBody(Epic epic) {
        ArrayList<ArrayList<String>> rows = new ArrayList();
        
        int taskCount = 0;

        for (Task t : epic.getTasks()) {
            taskCount += 1;
            
            ArrayList<String> row = new ArrayList<>();
            row.add(String.valueOf(taskCount)); // TODO: Epic Number
            row.add(t.getName());
            row.add(""); // TODO: Stories
            row.add(""); // TODO: size
            
            rows.add(row);
        }
        
        return rows;
    }
}
