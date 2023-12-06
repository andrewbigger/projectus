package com.biggerconcept.projectus.domain;

import com.biggerconcept.projectus.domain.Size.TaskSize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.UUID;

/**
 * Represents a task in a project.
 * 
 * @author Andrew Bigger
 */
public class Task {
    /**
     * ENUM of task status.
     */
    public static enum TaskStatus {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETE,
    }
    
    /**
     * Task ID.
     */
    @JsonInclude(Include.NON_NULL)
    private UUID id;
    
    /**
     * Task identifier.
     */
    @JsonInclude(Include.NON_NULL)
    private int identifier;
    
    /**
     * Task name.
     */
    @JsonInclude(Include.NON_NULL)
    private String name;
    
    /**
     * Task description.
     */
    @JsonInclude(Include.NON_NULL)
    private String description;
    
    /**
     * Task acceptance criteria.
     */
    @JsonInclude(Include.NON_NULL)
    private String acceptanceCriteria;
    
    /**
     * Task status.
     */
    @JsonInclude(Include.NON_NULL)
    private TaskStatus status;
    
    /**
     * Task size.
     */
    private TaskSize size;
    
    /**
     * Constructor fo task that accepts all task parameters.
     * 
     * @param name name of task
     * @param description description for task
     * @param acceptanceCriteria acceptance criteria of task
     * @param size size of task
     * @param status status of the task
     */
    public Task(
            String name,
            String description,
            String acceptanceCriteria,
            TaskSize size,
            TaskStatus status
    ) {
        this.id = UUID.randomUUID();
        this.identifier = -1;
        this.name = name;
        this.description = description;
        this.acceptanceCriteria = acceptanceCriteria;
        this.size = size;
        this.status = status;
    }
    
    /**
     * Constructor for a task when the name of the task is known.
     * 
     * Accepts the task, and sets the other attributes to empty.
     * 
     * @param name name of task
     */
    public Task(String name) {
        this.id = UUID.randomUUID();
        this.identifier = -1;
        this.name = name;
        this.description = "";
        this.acceptanceCriteria = "";
        this.size = TaskSize.ZERO;
        this.status = TaskStatus.NOT_STARTED;
    }
    
    /**
     * Default constructor for task.
     * 
     * This is used by the de-serialize operation.
     */
    public Task() {
        this.id = UUID.randomUUID();
        this.identifier = -1;
        this.name = "";
        this.description = "";
        this.acceptanceCriteria = "";
        this.size = TaskSize.ZERO;
        this.status = TaskStatus.NOT_STARTED;
    }
    
    /**
     * Getter for ID.
     * 
     * @return ID as UUID
     */
    public UUID getId() {
        return id;
    }
    
    /**
     * Getter for identifier.
     * 
     * @return identifier
     */
    public int getIdentifier() {
        return identifier;
    }
    
    /**
     * Getter for task name.
     * 
     * @return task name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Getter for task description.
     * 
     * @return task description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Getter for task acceptance criteria.
     * 
     * @return acceptance criteria for task
     */
    public String getAcceptanceCriteria() {
        return acceptanceCriteria;
    }
    
    /**
     * Getter for task size.
     * 
     * @return size for task
     */
    public TaskSize getSize() {
        return size;
    }
    
    /**
     * Getter for task status.
     * 
     * @return status for task
     */
    public TaskStatus getStatus() {
        return status;
    }
    
    /**
     * Setter for ID.
     * 
     * @param value ID as UUID
     */
    public void setId(UUID value) {
        id = value;
    }
    
    /**
     * String based setter for ID.
     * 
     * @param value ID as String
     */
    public void setId(String value) {
        id = UUID.fromString(value);
    }

    /**
     * Setter for identifier.
     * 
     * @param value identifier to set
     */
    public void setIdentifier(int value) {
        identifier = value;
    }
    
    /**
     * Setter for task name.
     * 
     * @param value task name value
     */
    public void setName(String value) {
        name = value;
    }
    
    /**
     * Setter for task description.
     * 
     * @param value task description value
     */
    public void setDescription(String value) {
        description = value;
    }
    
    /**
     * Setter for task acceptance criteria.
     * 
     * @param value acceptance criteria value
     */
    public void setAcceptanceCriteria(String value) {
        acceptanceCriteria = value;
    }
    
    /**
     * Setter for task size.
     * 
     * @param value task size value
     */
    public void setSize(TaskSize value) {
        size = value;
    }
    
    /**
     * Setter for task status.
     * 
     * @param value task status value
     */
    public void setStatus(TaskStatus value) {
        status = value;
    }
    
    /**
     * Returns true if task is complete.
     * 
     * @return task complete
     */
    @JsonIgnore
    public boolean isComplete() {
        return status == TaskStatus.COMPLETE;
    }
}
