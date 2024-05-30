package com.biggerconcept.projectus;

import com.biggerconcept.appengine.reports.elements.Content;
import com.biggerconcept.projectus.domain.Document;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.reports.Element;
import java.util.ResourceBundle;

/**
 * Application State
 * 
 * @author Andrew Bigger
 */
public class State {
   private MainController mainController;
   private Epic openEpic;
   private Document openDocument;
   private ResourceBundle bundle;
   
   public State(MainController controller, ResourceBundle rb) {
       this.mainController = controller;
       this.bundle = rb;
       this.openDocument = new Document();
   }
   
   public Document getOpenDocument() {
       return openDocument;
   }
   
   public Epic getOpenEpic() {
       return openEpic;
   }
   
   public void setOpenDocument(Document value) {
       openDocument = value;
   }
   
   public void setOpenEpic(Epic value) {
       openEpic = value;
   }
   
   public Content getReportContent() {
       return Element.availableContent(this);
   }
   
   public MainController mainController() {
       return mainController;
   }
   
   public ResourceBundle bundle() {
       return bundle;
   }
   
   public void reset() {
       openDocument = new Document();
       openEpic = null;
   }
   
   public void mapDocumentToWindow() {
       mainController().mapDocumentToWindow();
   }

}
