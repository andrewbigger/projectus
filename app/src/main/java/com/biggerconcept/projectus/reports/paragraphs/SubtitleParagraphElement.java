package com.biggerconcept.projectus.reports.paragraphs;

import com.biggerconcept.sdk.reports.IReport;
import com.biggerconcept.sdk.reports.elements.Content;
import com.biggerconcept.sdk.reports.ui.dialogs.IElementEditorDialog;
import com.biggerconcept.sdk.reports.ui.dialogs.ParagraphDialog;
import com.biggerconcept.sdk.serializers.documents.Doc;
import com.biggerconcept.sdk.doctree.domain.Node;
import com.biggerconcept.projectus.State;
import com.biggerconcept.projectus.reports.Element;
import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Inserts a subtitle paragraph into a report
 * 
 * @author Andrew Bigger
 */
public class SubtitleParagraphElement extends Element {
    /**
     * Default constructor
     */
    public SubtitleParagraphElement() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public SubtitleParagraphElement(State state) {
        super(state);
        this.type = Doc.ParagraphType.subtitle;
    }
    
    /**
     * Inserts a subtitle paragraph into a report document.
     * 
     * @param document report document
     * @param vars content variables
     * @param root resources root
     * 
     * @throws IOException when unable to write file
     */
    public void insertInto(Doc document, HashMap<String, String> vars, Node root) 
            throws IOException {
        document.subtitle(compile(getArgs().toString(), vars));
    }
    
    /**
     * Constructs and instantiates an editor dialog for subtitle paragraph.
     * 
     * @param rb application resource bundle
     * @param report current report
     * @param content content variables
     * 
     * @return editor dialog
     * 
     * @throws IOException when unable to read file from disk
     */
    public IElementEditorDialog editorDialog(
            ResourceBundle rb, 
            IReport report,
            Content content
    ) throws IOException {
        return ParagraphDialog.create(rb, report, this, content);
    }
}
