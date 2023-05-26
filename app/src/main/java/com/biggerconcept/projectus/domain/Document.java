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
    
    @JsonInclude(Include.NON_NULL)
    private ArrayList<Project> projects;
    
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
    public void createProject(String name) {
        addProject(
                new Project(name)
        );
    }
    
    /**
     * Adds a project to the document.
     * 
     * @param p 
     */
    public void addProject(Project p) {
        projects.add(p);
    }
    
    /**
     * Removes a project from the document.
     * 
     * @param p 
     */
    public void removeProject(Project p) {
        projects.remove(p);
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
     * Getter for projects.
     * 
     * @return 
     */
    public ArrayList<Project> getProjects() {
        return projects;
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
     * Setter for projects.
     * 
     * @param value 
     */
    public void setProjects(ArrayList<Project> value) {
        projects = value;
    }

}
