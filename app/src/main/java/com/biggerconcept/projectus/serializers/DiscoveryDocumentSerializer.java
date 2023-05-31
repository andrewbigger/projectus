package com.biggerconcept.projectus.serializers;

import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Risk;
import com.biggerconcept.projectus.domain.Task;
import com.biggerconcept.projectus.serializers.documents.Docx;
import com.biggerconcept.projectus.serializers.helpers.Tables;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import org.apache.xmlbeans.XmlException;

/**
 * Epic discovery document serialize-er.
 * 
 * @author Andrew Bigger
 */
public class DiscoveryDocumentSerializer implements ISerializer {
    private final Epic epic;
    private final Docx docx;
    
    public DiscoveryDocumentSerializer(Epic epic, File outputFile) throws IOException, XmlException {
        this.epic = epic;
        this.docx = new Docx(outputFile);
    }

    /**
     * Saves epic as DOCX.
     * 
     * @throws IOException
     */
    @Override
    public void save() throws IOException {      
        titlePage();
        tocPage();
        overviewPage();
        storiesPage();
        taskSummaryPage();
        tasksPages();
        riskSummaryPage();
        riskPages();

        docx.save();
    }

    private void titlePage() {
        docx.title(epic.getName());
        
        LocalDate today = LocalDate.now(ZoneId.of("Australia/Sydney"));
        
        docx.subtitle(
                "Discovery - " 
                        + String.valueOf(today.getMonth()) 
                        + " " 
                        + String.valueOf(today.getYear())
        );
        
        docx.br();
    }
    
    /**
     * Builds Table of contents page.
     */
    private void tocPage() {
        docx.toc();
        docx.br();
    }
    
    /**
     * Builds epic overview page.
     */
    private void overviewPage() {
        docx.h1("Overview"); // TODO: String
        docx.p(epic.getSummary());
        docx.h2("Scope");
        docx.ol(epic.getScope().getIncluded());
        docx.h2("Out of Scope");
        docx.ol(epic.getScope().getExcluded());
        docx.br();
    }
    
    /**
     * Builds story summary page.
     */
    private void storiesPage() {
        docx.h1("Stories"); // TODO: String
        docx.table(Tables.storyTableHeaders(), Tables.storyTableBody(epic));
        docx.br();
    }
    
    /**
     * Builds task summary page.
     */
    private void taskSummaryPage() {
        docx.h1("Tasks"); // TODO: String
        docx.table(Tables.taskTableHeaders(), Tables.taskTableBody(epic));
        docx.br();
    }
    
    /**
     * Iterates over epic tasks and builds a task page for each one.
     */
    private void tasksPages() {
        for (Task t : epic.getTasks()) {
            taskPage(t);
        }
    }
    
    /**
     * Builds a task page.
     * 
     * @param t 
     */
    private void taskPage(Task t) {
        docx.h2(t.getName());
        docx.strong("Description"); // TODO: String
        docx.p(t.getDescription());
        docx.strong("Acceptance Criteria");
        docx.p(t.getAcceptanceCriteria());
        docx.br();
    }
    
    /**
     * Builds a risk summary page.
     */
    private void riskSummaryPage() {
        docx.h1("Risks"); // TODO: String
        docx.table(Tables.riskTableHeaders(), Tables.riskTableBody(epic));
        docx.br();
    }
    
    /**
     * Iterates over each risk and builds a page for each of them.
     */
    private void riskPages() {
        for (Risk r : epic.getRisks()) {
            riskPage(r);
        }
    }
    
    /**
     * Builds a risk page for a risk.
     * 
     * @param r 
     */
    private void riskPage(Risk r) {
        docx.h2(r.getName());
        docx.table(
                Tables.riskSummaryTableHeaders(),
                Tables.riskSummaryTableBody(r)
        );
        
        docx.strong("Details");
        // TODO: Details
        
        docx.br();
    }
    
    
}
