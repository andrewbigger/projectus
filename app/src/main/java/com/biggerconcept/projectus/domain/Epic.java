package com.biggerconcept.projectus.domain;

import com.biggerconcept.projectus.domain.Size.TaskSize;
import com.biggerconcept.projectus.domain.Task.TaskStatus;
import com.biggerconcept.projectus.exceptions.DuplicateItemException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Representation of a project.
 * 
 * @author Andrew Bigger
 */
public class Epic {
    /**
     * Epic id.
     */
    @JsonInclude(Include.NON_NULL)
    private UUID id;
    
    /**
     * Epic identifier.
     */
    @JsonInclude(Include.NON_NULL)
    private int identifier;
    
    /**
     * Epic name.
     */
    @JsonInclude(Include.NON_NULL)
    private String name;
    
    /**
     * Epic Scope.
     */
    @JsonInclude(Include.NON_NULL)
    private Scope scope;
    
    /**
     * Epic summary.
     */
    @JsonInclude(Include.NON_NULL)
    private String summary;
    
    /**
     * Epic tasks.
     */
    @JsonInclude(Include.NON_NULL)
    private ArrayList<Task> tasks;
    
    /**
     * Epic stories.
     */
    @JsonInclude(Include.NON_NULL)
    private ArrayList<UUID> stories;
    
    /**
     * Epic risks.
     */
    @JsonInclude(Include.NON_NULL)
    private ArrayList<UUID> risks;
    
    /**
     * Epic outlook.
     */
    @JsonInclude(Include.NON_NULL)
    private Outlook outlook;
    
    /**
     * Start sprint number
     */
    @JsonInclude(Include.NON_NULL)
    private int startSprint;
    
    /**
     * End sprint number
     */
    @JsonInclude(Include.NON_NULL)
    private int endSprint;
    
    /**
     * Parent document.
     */
    @JsonIgnore
    private Document parent;
    
    /**
     * Default constructor.
     */
    public Epic() {
        this.id = UUID.randomUUID();
        this.identifier = -1;
        this.name = "";
        this.summary = "";
        this.tasks = new ArrayList<>();
        this.scope = new Scope();
        this.stories = new ArrayList<>();
        this.risks = new ArrayList<>();
    }
    
    /**
     * Constructor for epic with name.
     * 
     * @param name name of epic
     */
    public Epic(String name) {
        this.id = UUID.randomUUID();
        this.identifier = -1;
        this.name = name;
        this.summary = "";
        this.tasks = new ArrayList<>();
        this.scope = new Scope();
        this.stories = new ArrayList<>();
        this.risks = new ArrayList<>();
    }
    
    /**
     * Constructor for epic with identifier 
     * 
     * @param identifier identifier
     */
    public Epic(int identifier) {
        this.id = UUID.randomUUID();
        this.identifier = identifier;
        this.name = "";
        this.summary = "";
        this.tasks = new ArrayList<>();
        this.scope = new Scope();
        this.stories = new ArrayList<>();
        this.risks = new ArrayList<>();
    }
    
    /**
     * Returns true if story has been added to collection.
     * 
     * @param story to look for
     * 
     * @return whether the story is in the list
     */
    public boolean hasStory(Story story) {       
        for (Story s : getDocumentStories()) {
            if (s.match(story) == true) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Adds story link to epic.
     * 
     * @param story to add
     * 
     * @throws com.biggerconcept.projectus.exceptions.DuplicateItemException when item is duplicated
     */
    public void addStory(Story story) throws DuplicateItemException {
        if (hasStory(story) == true) {
            throw new DuplicateItemException();
        }
        
        stories.add(story.getId());
    }
    
    /**
     * Remove story from epic.
     * 
     * @param story to remove
     */
    public void removeStory(Story story) {
        stories.remove(story.getId());
    }
    
    /**
     * Returns true if risk has been added to collection.
     * 
     * @param risk risk to look for
     * 
     * @return whether risk exists in the list
     */
    public boolean hasRisk(Risk risk) {
        for (Risk r : getDocumentRisks()) {
            if (r.match(risk) == true) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Adds risk to epic.
     * 
     * @param risk to add
     * @throws com.biggerconcept.projectus.exceptions.DuplicateItemException when item is duplicated
     */
    public void addRisk(Risk risk) throws DuplicateItemException {
        if (hasRisk(risk) == true) {
            throw new DuplicateItemException();
        }
        
        risks.add(risk.getId());
    }
    
    /**
     * Removes risk from epic.
     * 
     * @param risk to remove
     */
    public void removeRisk(Risk risk) {
        risks.remove(risk.getId());
    }
    
    /**
     * Find task by id.
     * 
     * If not found, null will be returned.
     * 
     * @param id task UUID
     * @return found task
     */
    public Task findTask(UUID id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                return t;
            }
        }
        
        return null;
    }
    
    /**
     * Getter for ID.
     * 
     * @return epic UUID
     */
    public UUID getId() {
        return id;
    }
    
    /**
     * Getter for identifier.
     * 
     * @return epic identifier
     */
    public int getIdentifier() {
        return identifier;
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
     * Getter for name with default.
     * 
     * @param bundle application resource bundle
     * @return epic name
     */
    @JsonIgnore
    public String getName(ResourceBundle bundle) {
        if (getName() == "") {
            return bundle.getString("dialogs.epic.new");
        }
        
        return getName();
    }
    
    /**
     * Getter for summary.
     * 
     * @return epic summary
     */
    public String getSummary() {
        return summary;
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
     * Returns previous task in list from given task.
     * 
     * When at the start of the epic, the last task will be returned.
     * 
     * @param t task in list
     * @return task previous to given task
     */
    public Task getPrevTask(Task t) {
        ArrayList<Task> tasks = getTasks();
        
        int idx = tasks.indexOf(t);
        
        if (idx == 0) {
            return tasks.get(tasks.size() - 1);
        }
        
        return tasks.get(idx - 1);
    }
    
    /**
     * Returns next task in list from given task.
     * 
     * When at end of the epic, the first task will be returned.
     * 
     * @param t task in list
     * @return next task in list
     */
    public Task getNextTask(Task t) {
        ArrayList<Task> tasks = getTasks();
        
        int idx = tasks.indexOf(t);
        
        if (idx + 1 > tasks.size() - 1) {
            return tasks.get(0);
        }
        
        return tasks.get(idx + 1);
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
     * Getter for epic story ids.
     * 
     * @return epic story ids
     */
    public ArrayList<UUID> getStories() {
        return stories;
    }
    
    /**
     * Returns document story models.
     * 
     * @return list of stories
     */
    @JsonIgnore
    public ArrayList<Story> getDocumentStories() {
        return parent.findStories(stories);
    }
    
    /**
     * Getter for epic risk ids.
     * 
     * @return epic risk ids
     */
    public ArrayList<UUID> getRisks() {
        return risks;
    }
    
    /**
     * Getter for epic outlook.
     * 
     * @return epic outlook
     */
    public Outlook getOutlook() {
        if (parent == null) {
            return null;
        }
        
        if (outlook == null) {
            outlook = new Outlook(this, parent.getPreferences());
        }
        
        return outlook;
    }
    
    /**
     * Return document risk models.
     * 
     * @return list of risks
     */
    @JsonIgnore
    public ArrayList<Risk> getDocumentRisks() {
        return parent.findRisks(risks);
    }
    
    /**
     * Returns start sprint number
     * 
     * @return sprint number
     */
    public int getStartSprint() {
        return startSprint;
    }
    
    /**
     * Returns end sprint number
     * 
     * @return sprint number
     */
    public int getEndSprint() {
        return endSprint;
    }
    
    /**
     * Returns array of assigned sprints
     * 
     * @return assigned sprints
     */
    @JsonIgnore
    public ArrayList<Integer> getAssignedSprints() {
        ArrayList<Integer> assignedSprints = new ArrayList<>();
        
        if (hasAssignedSprints() == false) {
            return assignedSprints;
        }
        
        int start = getStartSprint();
        int end = getEndSprint();

        for (int i = start; i < end + 1; i++) {
            assignedSprints.add(i);
        }
        
        return assignedSprints;
    }
    
    /**
     * Setter for ID.
     * 
     * @param value UUID to set as ID
     */
    public void setId(UUID value) {
        id = value;
    }
    
    /**
     * String based setter for ID.
     * 
     * @param value epic id
     */
    public void setId(String value) {
        id = UUID.fromString(value);
    }
    
    /**
     * Setter for parent.
     * 
     * @param value document to set as parent
     */
    public void setParent(Document value) {
        parent = value;
    }
    
    /**
     * Setter for identifier.
     * 
     * @param value new identifier
     */
    public void setIdentifier(int value) {
        identifier = value;
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
     * Setter for epic summary.
     * 
     * @param value value for epic summary.
     */
    public void setSummary(String value) {
        summary = value;
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
     * Setter for epic stories
     * 
     * @param value to set as stories
     */
    public void setStories(ArrayList<UUID> value) {
       stories = value;
    }
    
    /**
     * Setter for epic risks
     * 
     * @param value to set as risks
     */
    public void setRisks(ArrayList<UUID> value) {
        risks = value;
    }
    
    /**
     * Setter for epic outlook.
     * 
     * @param value to set as outlook
     */
    public void setOutlook(Outlook value) {
        outlook = value;
    }
    
    /**
     * Setter for start sprint
     * 
     * @param value start sprint number
     */
    public void setStartSprint(int value) {
        startSprint = value;
    }
    
    /**
     * Setter for end sprint
     * 
     * @param value end sprint number
     */
    public void setEndSprint(int value) {
        endSprint = value;
    }
    
    /**
     * Assigned sprint check
     * 
     * If the start and end sprints are defined this will return
     * true. Otherwise we will return false.
     * 
     * @return has assigned sprints
     */
    public boolean hasAssignedSprints() {
        if (startSprint > 0 && endSprint > 0) {
            return true;
        }
        
        return false;
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
        
        if (tasks.size() > 1 && completeCount == tasks.size()) {
            return TaskStatus.COMPLETE;
        } 
        
        if (completeCount > 0) {
            return TaskStatus.IN_PROGRESS;
        }
        
        return TaskStatus.NOT_STARTED;
    }
    
    /**
     * Returns summary progress.
     * 
     * This is true when there is content in the epic summary.
     * 
     * @return summary progress
     */
    @JsonIgnore
    public double calculateSummaryProgress() {
        if ("".equals(summary.trim())) {
            return 0;
        }
        
        return 1;
    }
    
    /**
     * Returns scope definition progress.
     * 
     * This is true when there are items in the scope and out of scope.
     * 
     * @return scope definition progress
     */
    @JsonIgnore
    public double calculateScopeProgress() {
        if (scope.getIncluded().isEmpty()) {
            return 0;
        }
        
        if (scope.getExcluded().isEmpty()) {
            return 0;
        }
        
        return 1;
    }
    
    /**
     * Returns task definition progress.
     * 
     * This returns true if there are tasks defined in the epic.
     * 
     * @return scope definition progress
     */
    public double calculateDefinitionProgress() {
        if (tasks.isEmpty()) {
            return 0;
        }
        
        return 1;
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
     * Returns the total number of points for epic.
     * 
     * @param preferences document preferences
     * 
     * @return number of points
     */
    public int calculateTotalPoints(Preferences preferences) {
        int count = 0;
        
        for (Task t : getTasks()) {
            count += preferences.estimateFor(t.getSize());
        }
        
        return count;
    }
    
    /**
     * Returns the number of sprints required to complete the epic
     * 
     * @param preferences document preferences
     * @return number of sprints
     */
    public int calculateTotalSprints(Preferences preferences) {
        int total = calculateTotalPoints(preferences);
        int availablePointsPerSprint = preferences.calculateAvailablePointsPerSprint();

        return (int) total / availablePointsPerSprint;
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
     * @return calculated complete point total
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
     * @return complete number of points as a proportion to the total points
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
    
    /**
     * To String override for UI components
     * 
     * @return name of epic
     */
    @Override
    public String toString() {
      return getName();  
    }
    
}
