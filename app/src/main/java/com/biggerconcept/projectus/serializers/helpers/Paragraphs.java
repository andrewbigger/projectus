package com.biggerconcept.projectus.serializers.helpers;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Outlook;
import com.biggerconcept.projectus.domain.Preferences;
import java.util.ResourceBundle;

/**
 * Document paragraph helper.
 * 
 * @author Andrew Bigger
 */
public class Paragraphs {
    /**
     * Formats given string as paragraphs.
     * 
     * @param text text to format
     * @param doc document to append to
     */
    public static void format(String text, Doc doc) {
        String[] paragraphs;
        
        paragraphs = text.split("\n");
        
        for (String s : paragraphs) {
            doc.p(s);
        }
    }
    
    /**
     * Builder for outlook description paragraph.
     * 
     * @param bundle application resource bundle
     * @param prefs document preferences
     * @param epic document epic
     * 
     * @return outlook description paragraph.
     */
    public static String outlookDescriptionText(
            ResourceBundle bundle,
            Preferences prefs,
            Epic epic
    ) {
        String desc = "";
        
        Outlook outlook = epic.getOutlook();
        outlook.calculate(prefs, epic, false);
        
        desc += getSummaryString(bundle, "totalPoints");
        desc += String.valueOf(outlook.getEstimateWithBuffer());
        desc += ". ";
        desc += String.valueOf(outlook.getBuffer());
        desc += " ";
        desc += getSummaryString(bundle, "bufferPoints");
        desc += " ";
        desc += getSummaryString(bundle, "deliveryAssumption");
        desc += " ";
        desc += String.valueOf(outlook.getAveragePoints());
        desc += " ";
        desc += getSummaryString(bundle, "points");
        desc += ", ";
        desc += getSummaryString(bundle, "likelySprints");
        desc += String.valueOf(outlook.getO().getSprints());
        desc += " ";
        desc += getSummaryString(bundle, "likelySprintsQualifier");
        desc += " ";
        desc += String.valueOf(outlook.getO().getWeeks());
        desc += " ";
        desc += getSummaryString(bundle, "weeksQualifier");
        
        return desc;
    }
    
    /**
     * Constructs assumption summary
     * 
     * @param bundle application resource bundle.
     * @param prefs document preferences
     * @param epic epic to summarize
     * @return assumption summary paragraph
     */
    public static String assumptionSummaryText(
            ResourceBundle bundle,
            Preferences prefs,
            Epic epic
    ) {
        String summary = "";
        
        Outlook outlook = epic.getOutlook();
        outlook.calculate(prefs, epic, false);
        
        summary += getAssumptionString(bundle, "calcAssumption");
        summary += " ";
        summary += String.valueOf(outlook.getO().getPointsPerSprint());
        summary += " ";
        summary += getAssumptionString(bundle, "dedication");
        summary += " ";
        
        int dedicatedPoints = outlook.getPointsPerSprint();
        int averagePoints = outlook.getAveragePoints();
        
        summary += qualifierFor(bundle, dedicatedPoints, averagePoints);
        summary += " ";
        summary += getAssumptionString(bundle, "averageDelivery");
        summary += " ";
        summary += averagePoints;
        summary += " ";
        summary += getAssumptionString(bundle, "deliveredReferenceSprints");
        
        return summary;
    }
    
    public static String bufferSummaryText(
            ResourceBundle bundle,
            Preferences prefs,
            Epic epic
    ) {
        String summary = "";
        
        Outlook outlook = epic.getOutlook();
        outlook.calculate(prefs, epic, false);
        
        summary += String.valueOf(outlook.getBuffer());
        summary += " ";
        summary += getAssumptionString(bundle, "buffer");
        
        return summary;
    }
    
    /**
     * Retrieves a summary string from resource bundle.
     * 
     * @param bundle application resource bundle
     * @param name name of string
     * 
     * @return found string
     */
    private static String getSummaryString(ResourceBundle bundle, String name) {
        return bundle.getString("documents.discovery.outlook.summary." + name);
    }
    
    /**
     * Retrieves assumption string from resource bundle.
     * 
     * @param bundle application resource bundle
     * @param name name of string
     * 
     * @return found string
     */
    private static String getAssumptionString(ResourceBundle bundle, String name) {
        return bundle.getString("documents.discovery.outlook.assumptions." + name);
    }
    
    /**
     * Returns qualifier string based on points and average points
     * 
     * @param bundle application resource bundle
     * @param points dedicated points
     * @param averagePoints average points
     * 
     * @return qualifier string
     */
    private static String qualifierFor(
            ResourceBundle bundle,
            int points,
            int averagePoints
    ) {
        String name = "";
        
        if (points == averagePoints) {
            name = "equalTo";
        } else if (points < averagePoints) {
            name = "lessThan";
        } else {
            name = "greaterThan";
        }
        
        return getAssumptionString(bundle, name);
    }
}
