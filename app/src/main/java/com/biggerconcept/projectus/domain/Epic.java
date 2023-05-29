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
    /**
     * Epic name
     */
    @JsonInclude(Include.NON_NULL)
    private String name;
    
    /**
     * Epic tasks
     */
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
     * @param name name of epic
     */
    public Epic(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
    }
    
    /**
     * Counts the estimates of all of the tasks in the epic.
     * 
     * @param preferences document preferences
     * 
     * @return total size of all epics
     */
    public int getSize(Preferences preferences) {
        int size = 0;
        
        for (Task t: tasks) {
            size += preferences.estimateFor(t.getSize());
        }
        
        return size;
    }
    
    /**
     * Creates a new task with given name.
     * 
     * @param name name of task
     */
    public void createTask(String name) {
        addTask(
                new Task(name)
        );
    }
    
    /**
     * Returns true if task is in epic.
     * 
     * @param task task to search for
     * 
     * @return result
     */
    public boolean hasTask(Task task) {
        return tasks.contains(task);
    }
    
    /**
     * Adds a task to this project.
     * 
     * @param task task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }
    
    /**
     * Removes a task from this project.
     * 
     * @param task task to remove
     */
    public void removeTask(Task task) {
        tasks.remove(task);
    }
    
    /**
     * Getter for name.
     * 
     * @return epic name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Getter for project tasks.
     * 
     * @return project tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
    
    /**
     * Setter for project name.
     * 
     * @param value value for project name
     */
    public void setName(String value) {
        name = value;
    }
    
    /**
     * Setter for project tasks.
     * 
     * @param value list of tasks
     */
    public void setTasks(ArrayList<Task> value) {
        tasks = value;
    }
    
}
