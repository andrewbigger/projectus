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
     * Loads file from disk.
     * 
     * By default empty or null values are to be omitted from the parser.
     * 
     * This will then attempt to deserialize a locale from a YAML locale file  
     * from disk. This may fail throwing an IO Exception which will be thrown to
     * the calling method.
     * 
     * The deserialized Locale will then be returned to the caller.
     * 
     * @param f
     * @return 
     * @throws IOException
     */
    public static Document load(File f) throws IOException {
        ObjectMapper oMap = new ObjectMapper();
        
        oMap.setSerializationInclusion(Include.NON_NULL);
        oMap.setSerializationInclusion(Include.NON_EMPTY);
        
        Document doc = oMap.readValue(f, Document.class);
        doc.setFile(f);
        
        return doc;
    }
    
    /**
     * Creates a project of the given name.
     * 
     * @param name 
     */
    public void createEpic(String name) {
        addEpic(
                new Epic(name)
        );
    }
    
    /**
     * Adds a project to the document.
     * 
     * @param p 
     */
    public void addEpic(Epic p) {
        if (epics == null) {
            epics = new ArrayList<>();
        }

        epics.add(p);
    }
    
    /**
     * Removes a project from the document.
     * 
     * @param p 
     */
    public void removeEpic(Epic p) {
        epics.remove(p);
    }
    
    /**
     * Saves document to disk.
     * 
     * @throws IOException 
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
     * @return 
     */
    public File getFile() {
        return file;
    }
    
    /**
     * Getter for preferences.
     * 
     * @return
     */
    public Preferences getPreferences() {
        if (preferences == null) {
            return Preferences.defaultPreferences();
        }

        return preferences;
    }
    
    /**
     * Getter for project title.
     * 
     * @return 
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
     * @return 
     */
    public long getStart() {
        return start;
    }
    
    /**
     * Getter for project end date.
     * 
     * @return 
     */
    public long getEnd() {
        return end;
    }
    
    /**
     * Getter for project epics.
     * 
     * @return 
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
     * @param value 
     */
    public void setFile(File value) {
        file = value;
    }
    
    /**
     * Setter for preferences.
     * 
     * @param value
     */
    public void setPreferences(Preferences value) {
        preferences = value;
    }
    
    /**
     * Setter for project title.
     * 
     * @param value 
     */
    public void setTitle(String value) {
        title = value;
    }
    
    /**
     * Setter for project start date.
     * 
     * @param value 
     */
    public void setStart(long value) {
        start = value;
    }
    
    /**
     * Setter for project end date.
     * 
     * @param value 
     */
    public void setEnd(long value) {
        end = value;
    }
    
    /**
     * Setter for project epics.
     * 
     * @param value 
     */
    public void setEpics(ArrayList<Epic> value) {
        epics = value;
    }

}
