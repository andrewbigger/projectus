package com.biggerconcept.projectus.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * In memory representation of a sprint.
 * 
 * @author Andrew Bigger
 */
public class Sprint {
   @JsonInclude(Include.NON_NULL)
   private String name;
    
   @JsonInclude(Include.NON_NULL)
   private Integer completedPoints;
   
   /**
    * Constructor for sprint.
    * 
    * @param name of sprint
    * @param completedPoints completed number of points
    */
   public Sprint(String name, Integer completedPoints) {
       this.name = name;
       this.completedPoints = completedPoints;
   }
   
   /**
    * Default constructor for sprint.
    */
   public Sprint() {
       name = "";
       completedPoints = 0;
   }
   
   /**
    * Getter for name
    * 
    * @return name of sprint
    */
   public String getName() {
       return name;
   }
   
   /**
    * Getter for completed points.
    * 
    * @return completed points
    */
   public Integer getCompletedPoints() {
       return completedPoints;
   }
   
   /**
    * Setter for name
    * 
    * @param value new name value
    */
   public void setName(String value) {
       name = value;
   }
   
   /**
    * Setter for completed points.
    * 
    * @param value new completed points value
    */
   public void setCompletedPoints(Integer value) {
       completedPoints = value;
   }
}
