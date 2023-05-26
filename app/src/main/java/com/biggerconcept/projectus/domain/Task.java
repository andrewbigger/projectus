package com.biggerconcept.projectus.domain;

import com.biggerconcept.projectus.domain.Size.TaskSize;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Represents a task in a project.
 * 
 * @author Andrew Bigger
 */
public class Task {
    // Task name
    @JsonInclude(Include.NON_NULL)
    private String name;
    
    // Task description
    @JsonInclude(Include.NON_NULL)
    private String description;
    
    // Task acceptance criteria
    @JsonInclude(Include.NON_NULL)
    private String acceptanceCriteria;
    
    // Task size
    private TaskSize size;
    
    /**
     * Default constructor for task.
     * 
     * @param name
     * @param description
     * @param acceptanceCriteria
     * @param size 
     */
    public Task(
            String name,
            String description,
            String acceptanceCriteria,
            TaskSize size
    ) {
        this.name = name;
        this.description = description;
        this.acceptanceCriteria = acceptanceCriteria;
        this.size = size;
    }
    
    public Task(String name) {
        this.name = name;
        this.description = "";
        this.acceptanceCriteria = "";
        this.size = TaskSize.ZERO;
    }
    
    /**
     * Getter for task name.
     * 
     * @return 
     */
    public String getName() {
        return name;
    }
    
    /**
     * Getter for task description.
     * 
     * @return 
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Getter for task acceptance criteria.
     * 
     * @return 
     */
    public String getAcceptanceCriteria() {
        return acceptanceCriteria;
    }
    
    /**
     * Getter for task size.
     * 
     * @return 
     */
    public TaskSize getSize() {
        return size;
    }
    
    /**
     * Setter for task name.
     * 
     * @param value 
     */
    public void setName(String value) {
        name = value;
    }
    
    /**
     * Setter for task description.
     * 
     * @param value 
     */
    public void setDescription(String value) {
        description = value;
    }
    
    /**
     * Setter for task acceptance criteria.
     * 
     * @param value 
     */
    public void setAcceptanceCriteria(String value) {
        acceptanceCriteria = value;
    }
    
    /**
     * Setter for task size.
     * 
     * @param value
     */
    public void setSize(TaskSize value) {
        size = value;
    }
}
