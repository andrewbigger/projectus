package com.biggerconcept.projectus.serializers;

import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Risk;
import com.biggerconcept.projectus.domain.Task;
import com.biggerconcept.appengine.serializers.documents.Docx;
import com.biggerconcept.projectus.serializers.helpers.Paragraphs;
import com.biggerconcept.projectus.serializers.helpers.Tables;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import org.apache.xmlbeans.XmlException;

/**
 * Epic discovery document serialize-er.
 * 
 * @author Andrew Bigger
 */
public class DiscoveryDocumentSerializer implements ISerializer {
    private final ResourceBundle bundle;
    private final Epic epic;
    private final Docx docx;
    
    public DiscoveryDocumentSerializer(
            ResourceBundle rb,
            Epic epic, 
            File outputFile
    ) throws IOException, XmlException {
        this.bundle = rb;
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
        
        docx.nl();
        docx.subtitle(bundle.getString("documents.discovery.title"));
        docx.nl();
        
        LocalDate today = LocalDate.now(ZoneId.of("Australia/Sydney"));
        
        docx.subtitle(
                        String.valueOf(today.getMonth()) 
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
        docx.h1(bundle.getString("documents.discovery.headings.overview"));
        
        Paragraphs.format(
            epic.getSummary(),
            docx
        );
        
        docx.h2(bundle.getString("documents.discovery.headings.scope"));
        docx.ol(epic.getScope().getIncluded());
        docx.h2(bundle.getString("documents.discovery.headings.outOfScope"));
        docx.ol(epic.getScope().getExcluded());
        docx.br();
    }
    
    /**
     * Builds story summary page.
     */
    private void storiesPage() {
        docx.h1(bundle.getString("documents.discovery.headings.stories"));
        docx.nl();
        docx.table(
                Tables.storyTableHeaders(bundle),
                Tables.storyTableBody(epic)
        );
        docx.nl();
        docx.br();
    }
    
    /**
     * Builds task summary page.
     */
    private void taskSummaryPage() {
        docx.h1(bundle.getString("documents.discovery.headings.tasks"));
        docx.nl();
        docx.table(
                Tables.taskTableHeaders(bundle),
                Tables.taskTableBody(epic)
        );
        docx.nl();
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
        docx.nl();
        docx.strong(
                bundle.getString(
                        "documents.discovery.headings.task.description"
                )
        );
        
        Paragraphs.format(
            t.getDescription(),
            docx
        );
        
        docx.nl();
        docx.strong(
                bundle.getString(
                        "documents.discovery.headings.task.acceptanceCriteria"
                )
        );
        
        Paragraphs.format(
            t.getAcceptanceCriteria(),
            docx
        );
        
        docx.br();
    }
    
    /**
     * Builds a risk summary page.
     */
    private void riskSummaryPage() {
        docx.h1(bundle.getString("documents.discovery.headings.risks"));
        docx.nl();
        docx.table(
                Tables.riskTableHeaders(bundle), 
                Tables.riskTableBody(epic)
        );
        docx.nl();
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
        docx.nl();
        docx.table(
                Tables.riskSummaryTableHeaders(bundle),
                Tables.riskSummaryTableBody(r)
        );
        
        docx.nl();
        docx.strong(
                bundle.getString(
                        "documents.discovery.headings.risk.details"
                )
        );
        
        Paragraphs.format(r.getDetail(), docx);
        
        docx.nl();
        docx.strong(
                bundle.getString(
                        "documents.discovery.headings.risk.mitigation"
                )
        );
        
        Paragraphs.format(r.getMitigationStrategy(), docx);
        
        docx.br();
    }
    
    
}
