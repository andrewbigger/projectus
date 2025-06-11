package com.biggerconcept.projectus;

import com.biggerconcept.sdk.reports.IReport;
import com.biggerconcept.sdk.reports.elements.Content;
import com.biggerconcept.projectus.domain.Document;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.reports.Element;
import com.biggerconcept.projectus.reports.Report;
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
       setReportDocument();
   }
   
   public void mapWindowToDocument() {
       mainController().mapWindowToDocument();
       setReportDocument();
   }
   
   public void setReportDocument() {
       for (IReport r : openDocument.getPreferences().getReports()) {
            Report rpt = (Report) r;
            rpt.setState(this);
        }
   }

}
