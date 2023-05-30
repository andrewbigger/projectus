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
     * Epic Scope
     */
    @JsonInclude(Include.NON_NULL)
    private Scope scope;
    
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
        this.scope = new Scope();
    }
    
    /**
     * Constructor for epic with name.
     * 
     * @param name name of epic
     */
    public Epic(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
        this.scope = new Scope();
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
     * Getter for name.
     * 
     * @return epic name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Getter for epic tasks.
     * 
     * @return epic tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
    
    /**
     * Getter for epic scope.
     * 
     * @return epic scope
     */
    public Scope getScope() {
        return scope;
    }
    
    /**
     * Setter for epic name.
     * 
     * @param value value for project name
     */
    public void setName(String value) {
        name = value;
    }
    
    /**
     * Setter for epic tasks.
     * 
     * @param value list of tasks
     */
    public void setTasks(ArrayList<Task> value) {
        tasks = value;
    }
    
    /**
     * Setter for epic scope.
     * 
     * @param value to set as scope
     */
    public void setScope(Scope value) {
        scope = value;
    }
    
     /**
     * Returns epic status.
     * 
     * If any task is in progress then the epic is in progress.
     * 
     * If any task is complete then the epic is in progress.
     * 
     * If all tasks are complete, then the epic is complete.
     * 
     * Otherwise it's not started.
     * 
     * @return epic status as task status
     */
    @JsonIgnore
    public TaskStatus calculateStatus() {
        int completeCount = 0;
        
        for (Task t : tasks) {            
            if (t.getStatus() == TaskStatus.IN_PROGRESS) {
                return TaskStatus.IN_PROGRESS;
            }
            
            if (t.getStatus() == TaskStatus.COMPLETE) {
                completeCount += 1;
            }
        }
        
        if (completeCount > 0) {
            return TaskStatus.IN_PROGRESS;
        }
        
        if (completeCount == tasks.size() - 1) {
            return TaskStatus.COMPLETE;
        } 
        
        return TaskStatus.NOT_STARTED;
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
        return (double) calculateSizedCount() / calculateTaskCount();
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
        return (double) calculateDescribedCount() / calculateTaskCount();
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
        return (double) calculateCompleteCount() / calculateTaskCount();
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
        return (double) calculateCompletePointCount(preferences) / 
                getSize(preferences);
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
    
}
