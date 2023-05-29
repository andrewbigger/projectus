package com.biggerconcept.projectus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * In memory representation of file.
 * 
 * @author Andrew Bigger
 */
public class Document {
    /**
     * Document file.
     */
    @JsonIgnore
    private File file;
    
    /**
     * Document preferences.
     */
    @JsonInclude(Include.NON_NULL)
    private Preferences preferences;
    
    /**
     * Project title
     */
    @JsonInclude(Include.NON_NULL)
    private String title;
    
    /**
     * Project start date
     */
    @JsonInclude(Include.NON_NULL)
    private long start;
    
    /**
     * Project end date
     */
    @JsonInclude(Include.NON_NULL)
    private long end;
    
    /**
     * Document epics
     */
    @JsonInclude(Include.NON_NULL)
    private ArrayList<Epic> epics;
    
    /**
     * Document actors.
     */
    @JsonInclude(Include.NON_NULL)
    private ArrayList<Actor> actors;
    
    /**
     * Document stories.
     */
    @JsonInclude(Include.NON_NULL)
    private ArrayList<Story> stories;
    
    /**
     * Loads file from disk.
     * 
     * By default empty or null values are to be omitted from the parser.
     * 
     * This will then attempt to de-serialize a locale from a YAML locale file  
     * from disk. This may fail throwing an IO Exception which will be thrown to
     * the calling method.
     * 
     * The de-serialized Locale will then be returned to the caller.
     * 
     * @param file file to load from disk
     * 
     * @return loaded document
     * 
     * @throws IOException when unable to load file from disk
     */
    public static Document load(File file) throws IOException {
        ObjectMapper oMap = new ObjectMapper();
        
        oMap.setSerializationInclusion(Include.NON_NULL);
        oMap.setSerializationInclusion(Include.NON_EMPTY);
        
        Document doc = oMap.readValue(file, Document.class);
        doc.setFile(file);
        
        return doc;
    }
    
    /**
     * Creates an actor.
     * 
     * @param name name of actor
     */
    public void createActor(String name) {
        addActor(
                new Actor(name)
        );
    }
    
    /**
     * Creates a story for the given actor.
     * 
     * @param actor actor for story
     */
    public void createStory(Actor actor) {
        addStory(
                new Story(actor)
        );
    }
    
    /**
     * Creates a project of the given name.
     * 
     * @param name name of epic
     */
    public void createEpic(String name) {
        addEpic(
                new Epic(name)
        );
    }
    
    /**
     * Returns true if document has actor.
     * 
     * @param actor actor to look for
     * 
     * @return result
     */
    public boolean hasActor(Actor actor) {
        return actors.contains(actor);
    }
    
    /**
     * Returns true if document has story.
     * 
     * @param story story to look for
     * @return result
     */
    public boolean hasStory(Story story) {
        return stories.contains(story);
    }
    
    /**
     * Adds actor story actor to the document.
     * 
     * @param actor actor to add
     */
    public void addActor(Actor actor) {
        if (actors == null) {
            actors = new ArrayList<>();
        }
        
        actors.add(actor);
    }
    
    /**
     * Adds a story to the document.
     * 
     * @param story story to add
     */
    public void addStory(Story story) {
        if (stories == null) {
            stories = new ArrayList<>();
        }
        
        stories.add(story);
    }
    
    /**
     * Adds a project to the document.
     * 
     * @param epic project to add
     */
    public void addEpic(Epic epic) {
        if (epics == null) {
            epics = new ArrayList<>();
        }

        epics.add(epic);
    }
    
    /**
     * Removes an actor from the document.
     * 
     * @param actor actor to remove
     */
    public void removeActor(Actor actor) {
        actors.remove(actor);
    }
    
    /**
     * Removes a story from the document.
     * 
     * @param story story to remove
     */
    public void removeStory(Story story) {
        stories.remove(story);
    }
    
    /**
     * Removes an epic from the document.
     * 
     * @param epic epic to remove
     */
    public void removeEpic(Epic epic) {
        epics.remove(epic);
    }
    
    /**
     * Saves document to disk.
     * 
     * @throws IOException when unable to save document to disk
     */
    public void save() throws IOException {       
        ObjectMapper oMap = new ObjectMapper();
        
        oMap.setSerializationInclusion(Include.NON_NULL);
        oMap.setSerializationInclusion(Include.NON_EMPTY);
        
        oMap.writeValue(file, this);
    }
    
    /**
     * Getter for file.
     * 
     * @return document file
     */
    public File getFile() {
        return file;
    }
    
    /**
     * Getter for preferences.
     * 
     * @return document preferences
     */
    public Preferences getPreferences() {
        if (preferences == null) {
            return Preferences.defaultPreferences();
        }

        return preferences;
    }
    
    /**
     * Getter for actors.
     * 
     * @return list of actors
     */
    public ArrayList<Actor> getActors() {
        if (actors == null) {
            actors = new ArrayList<>();
        }
        
        return actors;
    }
    
    /**
     * Getter for stories.
     * 
     * @return list of stories
     */
    public ArrayList<Story> getStories() {
        if (stories == null) {
            stories = new ArrayList<>();
        }
        
        return stories;
    }
    
    /**
     * Getter for project title.
     * 
     * @return project title
     */
    public String getTitle() {
        if (title == null) {
            return "";
        }

        return title;
    }
    
    /**
     * Getter for project start date.
     * 
     * @return project start date
     */
    public long getStart() {
        return start;
    }
    
    /**
     * Getter for project end date.
     * 
     * @return project end date
     */
    public long getEnd() {
        return end;
    }
    
    /**
     * Getter for project epics.
     * 
     * @return project epics
     */
    public ArrayList<Epic> getEpics() {
        if (epics == null) {
            return new ArrayList<>();
        }
        
        return epics;
    }

    /**
     * Setter for file.
     * 
     * @param value file to set
     */
    public void setFile(File value) {
        file = value;
    }
    
    /**
     * Setter for preferences.
     * 
     * @param value preferences to set
     */
    public void setPreferences(Preferences value) {
        preferences = value;
    }
    
    /**
     * Setter for actors.
     * 
     * @param value list of actors to set
     */
    public void setActors(ArrayList<Actor> value) {
        actors = value;
    }
    
    /**
     * Setter for stories.
     * 
     * @param value list of stories to set
     */
    public void setStories(ArrayList<Story> value) {
        stories = value;
    }
    
    /**
     * Setter for project title.
     * 
     * @param value title to set
     */
    public void setTitle(String value) {
        title = value;
    }
    
    /**
     * Setter for project start date.
     * 
     * @param value date to set as start date
     */
    public void setStart(long value) {
        start = value;
    }
    
    /**
     * Setter for project end date.
     * 
     * @param value date to set as end date
     */
    public void setEnd(long value) {
        end = value;
    }
    
    /**
     * Setter for project epics.
     * 
     * @param value list of epics
     */
    public void setEpics(ArrayList<Epic> value) {
        epics = value;
    }

}
