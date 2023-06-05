package com.biggerconcept.appengine.serializers.documents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;

/**
 * Class for building Microsoft Word Documents.
 * 
 * @author Andrew Bigger
 */
public class Docx {
    /**
     * ID of title paragraph style in template.
     */
    public static final String TITLE_ID = "Title";
    
    /**
     * ID of heading 1 paragraph style in template.
     */
    public static final String H1_ID = "Heading1";
    
    /**
     * ID of heading 2 paragraph style in template.
     */
    public static final String H2_ID = "Heading2";
    
    /**
     * ID of heading 3 paragraph style in template.
     */
    public static final String H3_ID = "Heading3";
    
    /**
     * ID of heading 4 paragraph style in template.
     */
    public static final String H4_ID = "Heading4";
    
    /**
     * ID of heading 5 paragraph style in template.
     */
    public static final String H5_ID = "Heading5";
    
    /**
     * ID of heading 6 paragraph style in template.
     */
    public static final String H6_ID = "Heading6";
    
    /**
     * ID of heading 7 paragraph style in template.
     */
    public static final String H7_ID = "Heading7";
    
    /**
     * ID of heading 8 paragraph style in template.
     */
    public static final String H8_ID = "Heading8";
    
    /**
     * ID of heading 9 paragraph style in template.
     */
    public static final String H9_ID = "Heading9";
    
    /**
     * ID of normal paragraph style in template.
     */
    public static final String NORMAL_ID = "Normal";
    
    /**
     * ID of subtitle paragraph style in template.
     */
    public static final String SUBTITLE_ID = "Subtitle";
    
    /**
     * ID of strong paragraph style in template.
     */
    public static final String STRONG_ID = "StrongParagraph";
    
    /**
     * Color for table header row background.
     */
    public static final String DEFAULT_HEADER_BG_COLOR = "DAEDED";
    
    /**
     * Pointer to output file on disk.
     */
    private final File file;
    
    /**
     * Word Document.
     */
    private final XWPFDocument doc;
    
    /**
     * Template document.
     */
    private final XWPFDocument template;
    
    /**
     * Template styles.
     */
    private final XWPFStyles styles;
    
    /**
     * Tracker for orderered lists.
     * 
     * This is used to reset numbering between calls of ol.
     */
    private int listNumber;
    
    /**
     * Constructor for Docx file.
     * 
     * Sets pointers to files and then reads styles from template
     * to apply them to the generated document.
     * 
     * @param file on disk file for document
     * 
     * @throws IOException
     * @throws XmlException 
     */
    public Docx(File file) throws IOException, XmlException {
        this.file = file;
        this.doc = new XWPFDocument();
        this.template = template();
        
        this.styles = doc.createStyles();
        this.styles.setStyles(template.getStyle());
        
        this.listNumber = 0;
    }
    
    /**
     * Builds a table of contents.
     */
    public void toc() {
        doc.createTOC();
        
        XWPFParagraph paragraph = h(H1_ID, "Table of Contents");

        CTP ctP = paragraph.getCTP();
        CTSimpleField toc = ctP.addNewFldSimple();
        toc.setInstr("TOC \\h");
        toc.setDirty(true);
    }
    
    /**
     * Method for creating a heading.
     * 
     * The style ID and text for the heading need to be supplied
     * to add the heading to the document.
     * 
     * @param id id for heading paragraph style
     * @param text text for heading
     * 
     * @return heading paragraph
     */
    public XWPFParagraph h(String id, String text) {
        XWPFParagraph h = doc.createParagraph();
        h.setStyle(id);
        
        XWPFRun hRun = h.createRun();
        hRun.setText(text);
        hRun.setStyle(id);
        
        return h;
    }
    
    /**
     * Adds a title paragraph with the specified text.
     * 
     * @param text text for heading
     */
    public void title(String text) {
        h(TITLE_ID, text);
    }
    
    /**
     * Adds a h1 paragraph with the specified text.
     * 
     * @param text text for heading
     */
    public void h1(String text) {
        h(H1_ID, text);
    }
    
    /**
     * Adds a h2 paragraph with the specified text.
     * 
     * @param text text for heading
     */
    public void h2(String text) {
        h(H2_ID, text);
    }
    
    /**
     * Adds a h3 paragraph with the specified text.
     * 
     * @param text text for heading
     */
    public void h3(String text) {
        h(H3_ID, text);
    }
    
    /**
     * Adds a h4 paragraph with the specified text.
     * 
     * @param text text for heading
     */
    public void h4(String text) {
        h(H4_ID, text);
    }
   
    /**
     * Adds a paragraph to the document.The style will be set to that specified by the id.
     * 
     * The content of the paragraph will be the text supplied in the
     * text parameter.
     * 
     * @param id id of paragraph style
     * @param text text for paragraph
     */
    public void p(String id, String text) {
        XWPFParagraph p = doc.createParagraph();
        p.setStyle(id);
        
        XWPFRun pRun = p.createRun();
        pRun.setText(text);
    }
    
    /**
     * Adds a normal paragraph to the document.
     * 
     * @param text text for paragraph
     */
    public void p(String text) {
        p(NORMAL_ID, text);
    }
    
    /**
     * Adds a subtitle paragraph to the document.
     * 
     * @param text text for paragraph
     */
    public void subtitle(String text) {
        p(SUBTITLE_ID, text);
    }
    
    /**
     * Adds a strong paragraph to the document.
     * 
     * @param text text for paragraph
     */
    public void strong(String text) {
        p(STRONG_ID, text);
    }
    
    /**
     * Adds an ordered list to the document.
     * 
     * The list number attribute is incremented to differentiate the 
     * numbering between calls of this function.
     * 
     * @param items items for ordered list
     */
    public void ol(ArrayList<String> items) {      
        listNumber += 1;
        
        for (String i : items) {
            li(i);
        }
    }
    
    /**
     * Adds a table to the document.
     * 
     * It accepts an array of strings for headers and
     * an array of arrays for the body.
     * 
     * The header is rendered first. The first cell is created
     * when the table is created. The remaining header cells are
     * added after that.
     * 
     * Then the body is created by iterating over the array of arrays.
     * 
     * Each array within the body array represents a row.
     * 
     * Header width needs to match the width of the cells provided in the
     * body.
     * 
     * @param headers headers for table
     * @param body body for table
     */
    public void table(
            ArrayList<String> headers,
            ArrayList<ArrayList<String>> body
    ) {
        XWPFTable table = doc.createTable();
        
        XWPFTableRow headerRow = table.getRow(0);
        
        XWPFTableCell hc = headerRow.getCell(0);
        hc.setText(headers.get(0));
        hc.setColor(DEFAULT_HEADER_BG_COLOR);
        
        for (String s : headers) {
            if (s == headers.get(0)) {
                continue;
            }
            
            XWPFTableCell c = headerRow.addNewTableCell();
            c.setColor(DEFAULT_HEADER_BG_COLOR);
            c.setText(s);
        }
        
        for (ArrayList<String> row : body) {
            XWPFTableRow bodyRow = table.createRow();
            
            int pos = 0;
            
            for (String s : row) {
                bodyRow.getCell(pos).setText(s);
                pos += 1;
            }
        }

    }
    
    /**
     * Adds a page break to the document.
     */
    public void br() {
        nl();

        XWPFParagraph p = doc.createParagraph();
        p.setPageBreak(true);
    }
    
    /**
     * Adds a new line to the document by adding a blank paragraph.
     */
    public void nl() {
        p("");
    }
    
    /**
     * Saves the document to disk.
     * 
     * @throws IOException 
     */
    public void save() throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        doc.write(fos);
        fos.close();
    }
    
    /**
     * Gets the document template from application resources.
     * 
     * The default template is loaded as a XWPFDocument.
     * 
     * This is used to set the styles for the document.
     * 
     * To modify styles, open the docx.dotx file in word and edit.
     * 
     * @return template document
     * 
     * @throws IOException 
     */
    private XWPFDocument template() throws IOException {
        InputStream template = getClass()
                .getResourceAsStream("/docs/docx.dotx");
        
        return new XWPFDocument(
                template
        );
    }
    
    /**
     * Adds a list item to the document.
     * 
     * This can only be called from the ol method.
     * 
     * @param text for the list item
     */
    private void li(String text) {
        XWPFParagraph para = doc.createParagraph();
        
        para.setVerticalAlignment(TextAlignment.CENTER);
        para.setNumID(BigInteger.valueOf(listNumber));
        para
                .getCTP()
                .getPPr()
                .getNumPr()
                .addNewIlvl()
                .setVal(BigInteger.valueOf(1));
        
        XWPFRun run = para.createRun();
        run.setText(text);
    }
}
