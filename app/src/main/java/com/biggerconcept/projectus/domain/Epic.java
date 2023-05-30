package com.biggerconcept.projectus.domain;

import com.biggerconcept.projectus.domain.Size.TaskSize;
import com.biggerconcept.projectus.domain.Task.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
     * Counts total number of tasks in epic.
     * 
     * @return task count
     */
    @JsonIgnore
    public int calculateTaskCount() {
        return tasks.size();
    }
    
    /**
     * Counts the number of tasks with sizes.
     * 
     * @return number of tasks with sizes
     */
    @JsonIgnore
    public int calculateSizedCount() {
        int count = 0;
        
        for (Task t : tasks) {
            if (t.getSize() != TaskSize.ZERO) {
                count += 1;
            }
        }
        
        return count;
    }
    
    /**
     * Returns sized number in proportion to the total number of
     * tasks.
     * 
     * @return progress of sized cards
     */
    @JsonIgnore
    public double calculateSizedProgress() {
        return calculateSizedCount() / calculateTaskCount();
    }
    
    /**
     * Counts the number of tasks with descriptions.
     * 
     * @return number of cards with a description
     */
    @JsonIgnore
    public int calculateDescribedCount() {
        int count = 0;
        
        for (Task t : tasks) {
            if (!"".equals(t.getDescription().trim())) {
                count += 1;
            }
        }
        
        return count;
    }
    
    /**
     * Returns the number of described tasks in proportion to the total
     * number of tasks.
     * 
     * @return progress of described cards
     */
    @JsonIgnore
    public double calculateDescribedProgress() {
        return calculateDescribedCount() / calculateTaskCount();
    }
    
    /**
     * Returns count of completed tasks.
     * 
     * @return number of complete tasks
     */
    @JsonIgnore
    public int calculateCompleteCount() {
        int count = 0;
        
        for (Task t : tasks) {
            if (t.getStatus() == TaskStatus.COMPLETE) {
                count += 1;
            }
        }
        
        return count;
    }
    
    /**
     * Returns the number of completed tasks in proportion to the total
     * number of tasks.
     * 
     * @return progress of complete tasks
     */
    @JsonIgnore
    public double calculateCompleteProgress() {
        double prog = (double) calculateCompleteCount() / calculateTaskCount();
        
        return prog;
    }
    
    /**
     * Returns the number of completed points.
     * 
     * @param preferences document preferences
     * 
     * @return 
     */
    @JsonIgnore
    public int calculateCompletePointCount(Preferences preferences) {
        int count = 0;
        
        for (Task t : tasks) {
            if (t.getStatus() == TaskStatus.COMPLETE) {
                count += preferences.estimateFor(t.getSize());
            }
        }
        
        return count;
    }
    
    /**
     * Returns completed point count in proportion to the total number of
     * points.
     * 
     * @param preferences document preferences
     * 
     * @return 
     */
    @JsonIgnore
    public double calculateCompletePointProgress(Preferences preferences) {
        double prog = (double) calculateCompletePointCount(preferences) / 
                getSize(preferences);
        
        return prog;
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
