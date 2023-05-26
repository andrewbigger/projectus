package com.biggerconcept.projectus.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;

/**
 * Representation of a project.
 * 
 * @author Andrew Bigger
 */
public class Epic {
    // Epic name
    @JsonInclude(Include.NON_NULL)
    private String name;
    
    // Epic tasks
    @JsonInclude(Include.NON_NULL)
    private ArrayList<Task> tasks;
    
    /**
     * Default constructor.
     */
    public Epic() {
        this.name = "";
        this.tasks = new ArrayList<>();
    }
    
    /**
     * Constructor for epic with name.
     * 
     * @param name 
     */
    public Epic(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
    }
    
    /**
     * Creates a new task with given name.
     * 
     * @param name 
     */
    public void createTask(String name) {
        addTask(
                new Task(name)
        );
    }
    
    /**
     * Adds a task to this project.
     * 
     * @param t 
     */
    public void addTask(Task t) {
        tasks.add(t);
    }
    
    /**
     * Removes a task from this project.
     * 
     * @param t 
     */
    public void removeTask(Task t) {
        tasks.remove(t);
    }
    
    /**
     * Getter for name.
     * 
     * @return 
     */
    public String getName() {
        return name;
    }
    
    /**
     * Getter for project tasks.
     * 
     * @return 
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
    
    /**
     * Setter for project name.
     * 
     * @param value 
     */
    public void setName(String value) {
        name = value;
    }
    
    /**
     * Setter for project tasks.
     * 
     * @param value 
     */
    public void setTasks(ArrayList<Task> value) {
        tasks = value;
    }
    
}
