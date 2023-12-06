package com.biggerconcept.projectus.serializers;

import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Risk;
import com.biggerconcept.projectus.domain.Task;
import com.biggerconcept.appengine.serializers.documents.Doc;
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
    private final Doc doc;
    
    public DiscoveryDocumentSerializer(
            ResourceBundle rb,
            Epic epic, 
            File outputFile,
            Doc document
    ) throws IOException, XmlException {
        this.bundle = rb;
        this.epic = epic;
        this.doc = document;
    }

    /**
     * Saves epic as DOCX.
     * 
     * @throws IOException when unable to save document to disk
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

        doc.save();
    }

    private void titlePage() {
        doc.title(epic.getName());
        
        doc.nl();
        doc.subtitle(bundle.getString("documents.discovery.title"));
        doc.nl();
        
        LocalDate today = LocalDate.now(ZoneId.of("Australia/Sydney"));
        
        doc.subtitle(
                        String.valueOf(today.getMonth()) 
                        + " " 
                        + String.valueOf(today.getYear())
        );
        
        doc.br();
    }
    
    /**
     * Builds Table of contents page.
     */
    private void tocPage() {
        doc.toc();
        doc.br();
    }
    
    /**
     * Builds epic overview page.
     */
    private void overviewPage() {
        doc.h1(bundle.getString("documents.discovery.headings.overview"));
        
        Paragraphs.format(epic.getSummary(),
            doc
        );
        
        doc.h2(bundle.getString("documents.discovery.headings.scope"));
        doc.ol(epic.getScope().getIncluded());
        doc.h2(bundle.getString("documents.discovery.headings.outOfScope"));
        doc.ol(epic.getScope().getExcluded());
        doc.br();
    }
    
    /**
     * Builds story summary page.
     */
    private void storiesPage() {
        doc.h1(bundle.getString("documents.discovery.headings.stories"));
        doc.nl();
        doc.table(
                Tables.storyTableHeaders(bundle),
                Tables.storyTableBody(epic)
        );
        doc.nl();
        doc.br();
    }
    
    /**
     * Builds task summary page.
     */
    private void taskSummaryPage() {
        doc.h1(bundle.getString("documents.discovery.headings.tasks"));
        doc.nl();
        doc.table(
                Tables.taskTableHeaders(bundle),
                Tables.taskTableBody(epic)
        );
        doc.nl();
        doc.br();
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
        doc.h2(t.getName());
        doc.nl();
        doc.strong(
                bundle.getString(
                        "documents.discovery.headings.task.description"
                )
        );
        
        Paragraphs.format(t.getDescription(),
            doc
        );
        
        doc.nl();
        doc.strong(
                bundle.getString(
                        "documents.discovery.headings.task.acceptanceCriteria"
                )
        );
        
        Paragraphs.format(t.getAcceptanceCriteria(),
            doc
        );
        
        doc.br();
    }
    
    /**
     * Builds a risk summary page.
     */
    private void riskSummaryPage() {
        doc.h1(bundle.getString("documents.discovery.headings.risks"));
        doc.nl();
        doc.table(
                Tables.riskTableHeaders(bundle), 
                Tables.riskTableBody(epic)
        );
        doc.nl();
        doc.br();
    }
    
    /**
     * Iterates over each risk and builds a page for each of them.
     */
    private void riskPages() {
        for (Risk r : epic.getDocumentRisks()) {
            riskPage(r);
        }
    }
    
    /**
     * Builds a risk page for a risk.
     * 
     * @param r 
     */
    private void riskPage(Risk r) {
        doc.h2(r.getName());
        doc.nl();
        doc.table(
                Tables.riskSummaryTableHeaders(bundle),
                Tables.riskSummaryTableBody(r)
        );
        
        doc.nl();
        doc.strong(
                bundle.getString(
                        "documents.discovery.headings.risk.details"
                )
        );
        
        Paragraphs.format(r.getDetail(), doc);
        
        doc.nl();
        doc.strong(
                bundle.getString(
                        "documents.discovery.headings.risk.mitigation"
                )
        );
        
        Paragraphs.format(r.getMitigationStrategy(), doc);
        
        doc.br();
    }
    
    
}
