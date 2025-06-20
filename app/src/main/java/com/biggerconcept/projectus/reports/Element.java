package com.biggerconcept.projectus.reports;

import com.biggerconcept.projectus.reports.sections.SelectedEpicStoriesTableElement;
import com.biggerconcept.projectus.reports.sections.RisksOutlineElement;
import com.biggerconcept.projectus.reports.sections.RisksTableElement;
import com.biggerconcept.projectus.reports.sections.SelectedEpicOutlookTableElement;
import com.biggerconcept.projectus.reports.sections.TableOfContentsElement;
import com.biggerconcept.projectus.reports.sections.SelectedEpicTasksOutlineElement;
import com.biggerconcept.projectus.reports.sections.SelectedEpicTasksTableElement;
import com.biggerconcept.projectus.reports.sections.SelectedEpicRisksOutlineElement;
import com.biggerconcept.projectus.reports.sections.SelectedEpicRisksTableElement;
import com.biggerconcept.projectus.reports.sections.StoriesTableElement;
import com.biggerconcept.projectus.reports.sections.ReferenceSprintTableElement;
import com.biggerconcept.projectus.reports.sections.EpicsTableElement;
import com.biggerconcept.projectus.reports.sections.SelectedEpicSummaryElement;
import com.biggerconcept.projectus.reports.sections.StoriesOutlineElement;
import com.biggerconcept.projectus.reports.sections.SelectedEpicScopeOutlineElement;
import com.biggerconcept.projectus.reports.sections.SelectedEpicStoriesOutlineElement;
import com.biggerconcept.projectus.reports.paragraphs.Heading2Element;
import com.biggerconcept.projectus.reports.paragraphs.ParagraphElement;
import com.biggerconcept.projectus.reports.paragraphs.Heading3Element;
import com.biggerconcept.projectus.reports.paragraphs.StrongParagraphElement;
import com.biggerconcept.projectus.reports.paragraphs.Heading4Element;
import com.biggerconcept.projectus.reports.paragraphs.Heading1Element;
import com.biggerconcept.projectus.reports.paragraphs.TitleElement;
import com.biggerconcept.projectus.reports.paragraphs.NewLineElement;
import com.biggerconcept.projectus.reports.paragraphs.SubtitleParagraphElement;
import com.biggerconcept.projectus.reports.paragraphs.PageBreakElement;
import com.biggerconcept.sdk.reports.IReport;
import com.biggerconcept.sdk.reports.elements.Content;
import com.biggerconcept.sdk.reports.elements.IElement;
import com.biggerconcept.sdk.reports.ui.dialogs.IElementEditorDialog;
import com.biggerconcept.sdk.serializers.documents.Doc;
import com.biggerconcept.sdk.doctree.domain.Node;
import com.biggerconcept.projectus.State;
import com.biggerconcept.projectus.domain.Document;
import com.biggerconcept.projectus.reports.paragraphs.CodeElement;
import com.biggerconcept.projectus.reports.paragraphs.MarkdownElement;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Element is a portion of a report.
 * 
 * @author Andrew Bigger
 */
public class Element extends com.biggerconcept.sdk.reports.elements.Element 
        implements Cloneable, IElement {
    
    /**
     * Constructs set of paragraphs, sections and variables for
     * the report builder.
     * 
     * @param state application state
     * 
     * @return source content
     */
    public static Content availableContent(State state) {
        Content content = new Content();
        
        content.addParagraph(new TitleElement(state));
        content.addParagraph(new Heading1Element(state));
        content.addParagraph(new Heading2Element(state));
        content.addParagraph(new Heading3Element(state));
        content.addParagraph(new Heading4Element(state));
        content.addParagraph(new SubtitleParagraphElement(state));
        content.addParagraph(new StrongParagraphElement(state));
        content.addParagraph(new ParagraphElement(state));
        content.addParagraph(new CodeElement(state));
        content.addParagraph(new MarkdownElement(state));
        content.addParagraph(new NewLineElement(state));
        content.addParagraph(new PageBreakElement(state));
        
        content.addSection(new TableOfContentsElement(state));
        content.addSection(new ReferenceSprintTableElement(state));
        content.addSection(new EpicsTableElement(state));
        content.addSection(new StoriesTableElement(state));
        content.addSection(new StoriesOutlineElement(state));
        content.addSection(new RisksTableElement(state));
        content.addSection(new RisksOutlineElement(state));
        
        content.addSection(new SelectedEpicSummaryElement(state));
        content.addSection(new SelectedEpicScopeOutlineElement(state));
        content.addSection(new SelectedEpicStoriesTableElement(state));
        content.addSection(new SelectedEpicStoriesOutlineElement(state));
        content.addSection(new SelectedEpicRisksTableElement(state));
        content.addSection(new SelectedEpicRisksOutlineElement(state));
        content.addSection(new SelectedEpicTasksTableElement(state));
        content.addSection(new SelectedEpicTasksOutlineElement(state));
        content.addSection(new SelectedEpicOutlookTableElement(state));
        
        content.addVariable(
                "project_name", 
                Variables.projectName(state)
        );
        
        content.addVariable(
                "project_start_date", 
                Variables.projectStartDate(state)
        );
        
        content.addVariable(
                "project_end_date", 
                Variables.projectEndDate(state)
        );
        
        content.addVariable(
                "weeks_elapsed", 
                Variables.weeksElapsed(state)
        );
        
        content.addVariable(
                "weeks_total", 
                Variables.weeksTotal(state)
        );
        
        content.addVariable(
                "weeks_remaining", 
                Variables.weeksRemaining(state)
        );
        
        content.addVariable(
                "sprints_elapsed", 
                Variables.sprintsElapsed(state)
        );
        
        content.addVariable(
                "sprints_total", 
                Variables.sprintsTotal(state)
        );
        
        content.addVariable(
                "sprints_remaining", 
                Variables.sprintsRemaining(state)
        );
        
        content.addVariable(
                "points_completed", 
                Variables.pointsCompleted(state)
        );
        
        content.addVariable(
                "points_total", 
                Variables.pointsTotal(state)
        );
        
        content.addVariable(
                "points_remaining", 
                Variables.pointsRemaining(state)
        );
        
        content.addVariable(
                "velocity", 
                Variables.velocity(state)
        );
        
        content.addVariable(
                "tracking_status", 
                Variables.trackingStatus(state)
        );
        
        content.addVariable(
                "committed_points", 
                Variables.committedPoints(state)
        );
        
        content.addVariable(
                "available_points", 
                Variables.availablePoints(state)
        );
        
        content.addVariable(
                "xs_task_size", 
                Variables.xsTaskSize(state)
        );
        
        content.addVariable(
                "s_task_size", 
                Variables.sTaskSize(state)
        );
        
        content.addVariable(
                "m_task_size", 
                Variables.mTaskSize(state)
        );
        
        content.addVariable(
                "l_task_size", 
                Variables.lTaskSize(state)
        );
        
        content.addVariable(
                "xl_task_size", 
                Variables.xlTaskSize(state)
        );
        
        content.addVariable(
                "sprint_length", 
                Variables.sprintLength(state)
        );
        
        content.addVariable(
                "estimate_buffer", 
                Variables.estimateBuffer(state)
        );
        
        content.addVariable(
                "epic_count", 
                Variables.epicCount(state)
        );
        
        content.addVariable(
                "selected_epic_name", 
                Variables.selectedEpicName(state)
        );
        
        content.addVariable(
                "selected_epic_identifier", 
                Variables.selectedEpicIdentifier(state)
        );
        
        content.addVariable(
                "selected_epic_sized_task_count", 
                Variables.selectedEpicSizedTaskCount(state)
        );
        
        content.addVariable(
                "selected_epic_completed_task_count", 
                Variables.selectedEpicCompleteTaskCount(state)
        );
        
        content.addVariable(
                "selected_epic_task_count", 
                Variables.selectedEpicTaskCount(state)
        );
        
        content.addVariable(
                "selected_epic_total_points", 
                Variables.selectedEpicTotalPoints(state)
        );
        
        content.addVariable(
                "selected_epic_complete_points", 
                Variables.selectedEpicCompletePoints(state)
        );
        
        content.addVariable(
                "selected_epic_estimate_points", 
                Variables.selectedEpicEstimatePoints(state)
        );
        
        content.addVariable(
                "selected_epic_status", 
                Variables.selectedEpicStatus(state)
        );
        
        content.addVariable(
                "selected_epic_risk_count", 
                Variables.selectedEpicRiskCount(state)
        );
        
        content.addVariable(
                "selected_epic_story_count", 
                Variables.selectedEpicStoryCount(state)
        );
        
        content.addVariable(
                "selected_epic_outlook_buffer", 
                Variables.selectedEpicOutlookBuffer(state)
        );
        
        content.addVariable(
                "selected_epic_outlook_buffer_excl_completed_points", 
                Variables.selectedEpicOutlookBufferExclCompletedPoints(state)
        );
        
        content.addVariable(
                "selected_epic_estimate_points_with_buffer", 
                Variables.selectedEpicOutlookEstimatePointsWithBuffer(state)
        );
        
        content.addVariable(
                "selected_epic_outlook_plus_three_points_per_sprint", 
                Variables.selectedEpicOutlookPointsPerSprint(state, 3, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_plus_three_sprints", 
                Variables.selectedEpicOutlookSprints(state, 3, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_plus_three_weeks", 
                Variables.selectedEpicOutlookWeeks(state, 3, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_plus_two_points_per_sprint", 
                Variables.selectedEpicOutlookPointsPerSprint(state, 2, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_plus_two_sprints", 
                Variables.selectedEpicOutlookSprints(state, 2, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_plus_two_weeks", 
                Variables.selectedEpicOutlookWeeks(state, 2, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_plus_one_points_per_sprint", 
                Variables.selectedEpicOutlookPointsPerSprint(state, 1, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_plus_one_sprints", 
                Variables.selectedEpicOutlookSprints(state, 1, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_plus_one_weeks", 
                Variables.selectedEpicOutlookWeeks(state, 1, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_points_per_sprint", 
                Variables.selectedEpicOutlookPointsPerSprint(state, 0, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_sprints", 
                Variables.selectedEpicOutlookSprints(state, 0, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_weeks", 
                Variables.selectedEpicOutlookWeeks(state, 0, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_minus_one_points_per_sprint", 
                Variables.selectedEpicOutlookPointsPerSprint(state, -1, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_minus_one_sprints", 
                Variables.selectedEpicOutlookSprints(state, -1, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_minus_one_weeks", 
                Variables.selectedEpicOutlookWeeks(state, -1, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_minus_two_points_per_sprint", 
                Variables.selectedEpicOutlookPointsPerSprint(state, -2, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_minus_two_sprints", 
                Variables.selectedEpicOutlookSprints(state, -2, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_minus_two_weeks", 
                Variables.selectedEpicOutlookWeeks(state, -2, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_minus_three_points_per_sprint", 
                Variables.selectedEpicOutlookPointsPerSprint(state, -3, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_minus_three_sprints", 
                Variables.selectedEpicOutlookSprints(state, -3, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_minus_three_weeks", 
                Variables.selectedEpicOutlookWeeks(state, -3, false)
        );
        
        content.addVariable(
                "selected_epic_outlook_plus_three_points_per_sprint_excl_completed_points", 
                Variables.selectedEpicOutlookPointsPerSprint(state, 3, true)
        );
        
        content.addVariable(
                "selected_epic_outlook_plus_three_sprints_excl_completed_points", 
                Variables.selectedEpicOutlookSprints(state, 3, true)
        );
        
        content.addVariable(
                "selected_epic_outlook_plus_three_weeks_excl_completed_points", 
                Variables.selectedEpicOutlookWeeks(state, 3, true)
        );
        
        content.addVariable(
                "selected_epic_outlook_plus_two_points_per_sprint_excl_completed_points", 
                Variables.selectedEpicOutlookPointsPerSprint(state, 2, true)
        );
        
        content.addVariable(
                "selected_epic_outlook_plus_two_sprints_excl_completed_points", 
                Variables.selectedEpicOutlookSprints(state, 2, true)
        );
        
        content.addVariable(
                "selected_epic_outlook_plus_two_weeks_excl_completed_points", 
                Variables.selectedEpicOutlookWeeks(state, 2, true)
        );
        
        content.addVariable(
                "selected_epic_outlook_plus_one_points_per_sprint_excl_completed_points", 
                Variables.selectedEpicOutlookPointsPerSprint(state, 1, true)
        );
        
        content.addVariable(
                "selected_epic_outlook_plus_one_sprints_excl_completed_points", 
                Variables.selectedEpicOutlookSprints(state, 1, true)
        );
        
        content.addVariable(
                "selected_epic_outlook_plus_one_weeks_excl_completed_points", 
                Variables.selectedEpicOutlookWeeks(state, 1, true)
        );
        
        content.addVariable(
                "selected_epic_outlook_points_per_sprint_excl_completed_points", 
                Variables.selectedEpicOutlookPointsPerSprint(state, 0, true)
        );
        
        content.addVariable(
                "selected_epic_outlook_sprints_excl_completed_points", 
                Variables.selectedEpicOutlookSprints(state, 0, true)
        );
        
        content.addVariable(
                "selected_epic_outlook_weeks_excl_completed_points", 
                Variables.selectedEpicOutlookWeeks(state, 0, true)
        );
        
        content.addVariable(
                "selected_epic_outlook_minus_one_points_per_sprint_excl_completed_points", 
                Variables.selectedEpicOutlookPointsPerSprint(state, -1, true)
        );
        
        content.addVariable(
                "selected_epic_outlook_minus_one_sprints_excl_completed_points", 
                Variables.selectedEpicOutlookSprints(state, -1, true)
        );
        
        content.addVariable(
                "selected_epic_outlook_minus_one_weeks_excl_completed_points", 
                Variables.selectedEpicOutlookWeeks(state, -1, true)
        );
        
        content.addVariable(
                "selected_epic_outlook_minus_two_points_per_sprint_excl_completed_points", 
                Variables.selectedEpicOutlookPointsPerSprint(state, -2, true)
        );
        
        content.addVariable(
                "selected_epic_outlook_minus_two_sprints_excl_completed_points", 
                Variables.selectedEpicOutlookSprints(state, -2, true)
        );
        
        content.addVariable(
                "selected_epic_outlook_minus_two_weeks_excl_completed_points", 
                Variables.selectedEpicOutlookWeeks(state, -2, true)
        );
        
        content.addVariable(
                "selected_epic_outlook_minus_three_points_per_sprint_excl_completed_points", 
                Variables.selectedEpicOutlookPointsPerSprint(state, -3, true)
        );
        
        content.addVariable(
                "selected_epic_outlook_minus_three_sprints_excl_completed_points", 
                Variables.selectedEpicOutlookSprints(state, -3, true)
        );
        
        content.addVariable(
                "selected_epic_outlook_minus_three_weeks_excl_completed_points", 
                Variables.selectedEpicOutlookWeeks(state, -3, true)
        );
        
        return content;
    }
    
    /**
     * Pointer to application state.
     */
    @JsonIgnore
    private State state;
    
    /**
     * Default constructor.
     */
    public Element() {
        super();
    }
    
    /**
     * State based constructor.
     * 
     * @param state application state
     */
    public Element(State state) {
        super();
        this.state = state;
    }

    /**
     * Getter for state.
     * 
     * This is not to be serialized to document as it
     * will create circular dependencies.
     * 
     * @return application state
     */
    @JsonIgnore
    public State getState() {        
        return state;
    }
    
    /**
     * Getter for open document.
     * 
     * This will retrieve the open document from application state.
     * 
     * @return current document.
     */
    @JsonIgnore
    public Document getDocument() {
        return state.getOpenDocument();
    }
    
    /**
     * Setter for state.
     * 
     * @param value new application state
     */
    @JsonIgnore
    public void setState(State value) {
        state = value;
    }
    
    /**
     * Constructs and returns an editor dialog for the current element.
     *
     * This will throw an unsupported operation exception if not overridden
     * in the child element.
     * 
     * @param rb Application resource bundle
     * @param report Active report
     * @param content Report content
     * 
     * @return Editor dialog
     * 
     * @throws IOException when unable to read from file
     */
    @Override
    public IElementEditorDialog editorDialog(
            ResourceBundle rb, 
            IReport report,
            Content content
    ) throws IOException {
        throw new UnsupportedOperationException("Not supported.");
    }

    /**
     * Insertion callback.
     * 
     * This will insert the element into the report document.
     * 
     * This will throw an UnsupportedOperationException if it is not
     * overridden in the child element.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException if unable to read file
     */
    @Override
    public void insertInto(Doc document, HashMap<String, String> vars, Node root) 
            throws IOException {}
}
