package com.biggerconcept.projectus.reports;

import com.biggerconcept.sdk.reports.IReport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import com.biggerconcept.sdk.reports.elements.IElement;
import com.biggerconcept.projectus.State;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 *
 * @author Andrew Bigger
 */
public class Report extends com.biggerconcept.sdk.reports.Report implements IReport {
    /**
     * List of elements
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
    private ArrayList<IElement> elements;
    
    /**
     * Default constructor
     */
    public Report() {
        super();
        this.elements = new ArrayList<>();
    }
    
    /**
     * Report with name setting constructor.
     * 
     * @param name report name
     */
    public Report(String name) {
        super(name);
        this.elements = new ArrayList<>();
    }
    
    /**
     * Report with name and description setting constructor.
     * 
     * @param name report name
     * @param description report description
     */
    public Report(String name, String description) {
        super(name, description);
        this.elements = new ArrayList<>();
    }
    
    /**
     * Getter for report elements.
     * 
     * @return report elements
     */
    @JsonDeserialize(contentAs=Element.class)
    public ArrayList<IElement> getElements() {
       if (elements == null) {
           elements = new ArrayList<>();
       }
       
       return elements;
    }
    
    /**
     * Setter for application state.
     * 
     * @param value new state
     */
    @JsonIgnore
    public void setState(State value) {
        for (IElement e : elements) {
            Element target = (Element) e;
            
            target.setState(value);
        }
    }

    /**
     * Setter for report elements
     * 
     * @param value new list of elements
     */
    public void setElements(ArrayList<IElement> value) {
       elements = value;
   }
   
    /**
     * Find element by ID.
     * 
     * This will iterate over all report elements. If an element matches the
     * given ID, it will be returned to the caller. Otherwise null will be
     * returned.
     * 
     * @param id ID to search for
     * 
     * @return any found element
     */
   public IElement findElement(UUID id) {
       for (IElement el : getElements()) {
           if (el.getId().equals(id)) {
               return el;
           }
       }
       
       return null;
   }
   
   /**
    * Element check.
    * 
    * When an element with the given ID is in the report, true will 
    * be returned to the caller. Otherwise false will be returned.
    * 
    * @param id element ID
    * 
    * @return whether the element is in the report
    */
   public boolean hasElement(UUID id) {
       IElement found = findElement(id);
       
       return found != null;
   }

   /**
    * Adds an element to the report.
    * 
    * @param element element to add.
    */
   public void addElement(IElement element) {
       getElements().add(element);
   }
   
   /**
    * Removes an element from the report.
    * 
    * @param element element to remove
    */
   public void removeElement(IElement element) {
       getElements().remove(element);
   }

   /**
    * Move element up one position in the report.
    * 
    * When the element is the first element, the function will bail out
    * making no change to the report.
    * 
    * Otherwise, the previous element will be swapped with the given element
    * having the effect of moving the element one position up in the report.
    * 
    * @param element element to move
    */
   public void moveElementUp(IElement element) {
       if (element == getElements().get(0)) {
           return;
       }
       
       ArrayList<IElement> elements = getElements();
       
       IElement prev = elements.get(elements.indexOf(element) - 1);
       
       Collections.swap(
                elements,
                elements.indexOf(element),
                elements.indexOf(prev)
        );
   }
   
   /**
    * Move element down one position in the report.
    * 
    * If the element is the last element in the list, the function will bail
    * out making no change to the report.
    * 
    * Otherwise, the following element will be swapped with the given element
    * having the effect of moving the selected element down one position.
    * 
    * @param element element to move
    */
   public void moveElementDown(IElement element) {
       ArrayList<IElement> elements = getElements();
       
        if (element == elements.get(elements.size() - 1)) {
           return;
       }
       
       IElement next = elements.get(elements.indexOf(element) + 1);
       
       Collections.swap(
                elements,
                elements.indexOf(next),
                elements.indexOf(element)
        );
   }
}
