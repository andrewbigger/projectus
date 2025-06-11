package com.biggerconcept.projectus.ui.tables;

import com.biggerconcept.sdk.ui.tables.StandardTable;
import com.biggerconcept.sdk.ui.tables.StandardTableColumn;
import com.biggerconcept.projectus.domain.Risk;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * View model for the currentRisks table.
 * 
 * @author Andrew Bigger
 */
public class RiskTable {
    /**
     * Sortable constant.
     * 
     * This should be false as risks are presented in user specified order.
     * view.
     */
    public static final boolean SORTABLE = false;
    
    /**
     * Default width of ID column.
     */
    public static final int ID_COL_MIN_WIDTH = 100;
    
    /**
     * Default width of name column.
     */
    public static final int NAME_COL_MIN_WIDTH = 200;
    
    /**
     * Default width of likelihood column.
     */
    public static final int LIKELIHOOD_COL_MIN_WIDTH = 150;
    
    /**
     * Default width of impact column.
     */
    public static final int IMPACT_COL_MIN_WIDTH = 150;
    
    /**
     * Default width of status column.
     */
    public static final int STATUS_COL_MIN_WIDTH = 150;
    
    /**
     * Application resource bundle.
     */
    private final ResourceBundle bundle;
    
    /**
     * List of currentRisks.
     */
    private final ArrayList<Risk> currentRisks;
    
    /**
     * Constructor for the currentRisks table view model.
     * 
     * @param rb application resource bundle
     * @param risks list of currentRisks
     */
    public RiskTable(
            ResourceBundle rb,
            ArrayList<Risk> risks
    ) {
        bundle = rb;
        currentRisks = risks;
    }
    
    /**
     * Binds data to story table view.
     * 
     * @param view story table view
     */
    public void bind(TableView view) {
        StandardTable.bind(
                view,
                bundle.getString("risks.table.empty"),
                FXCollections.observableArrayList(currentRisks),
                Arrays.asList(
                    idCol(),
                    nameCol(),
                    likelihoodCol(),
                    impactCol(),
                    statusCol()
                ),
                false
        );

    }
    
    /**
     * ID attribute column builder.
     * 
     * @return ID column
     */
    private TableColumn idCol() {
        TableColumn<Risk, String> idCol = new TableColumn<>(
               bundle.getString("risks.table.id")
       );
        
        StandardTableColumn.apply(idCol, ID_COL_MIN_WIDTH);
        
        idCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(
                    "R" +
                    String.valueOf(data.getValue().getIdentifier())
            );
        });
        
        return idCol;
    }
    
    /**
     * Actor attribute column builder.
     * 
     * @return name column
     */
    private TableColumn nameCol() {
        TableColumn<Risk, String> nameCol = new TableColumn<>(
               bundle.getString("risks.table.name")
       );
        
        StandardTableColumn.apply(nameCol, NAME_COL_MIN_WIDTH);
        
        nameCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(
                    data.getValue().getName()
            );
        });
        
        return nameCol;
    }
    
    /**
     * Likelihood attribute column builder.
     * 
     * @return Likelihood column
     */
    private TableColumn likelihoodCol() {
      TableColumn<Risk, String> likelihoodCol = new TableColumn<>(
             bundle.getString("risks.table.likelihood")
     );
      
      StandardTableColumn.apply(likelihoodCol, LIKELIHOOD_COL_MIN_WIDTH);
      
      likelihoodCol.setCellValueFactory(data -> {
          return new SimpleStringProperty(
                  String.valueOf(data.getValue().getLikelihood())
          );
      });
      
      return likelihoodCol;
  }

  /**
     * Impact attribute column builder.
     * 
     * @return Impact column
     */
    private TableColumn impactCol() {
      TableColumn<Risk, String> impactCol = new TableColumn<>(
             bundle.getString("risks.table.impact")
     );
      
      StandardTableColumn.apply(impactCol, IMPACT_COL_MIN_WIDTH);
      
      impactCol.setCellValueFactory(data -> {
          return new SimpleStringProperty(
                  String.valueOf(data.getValue().getImpact())
          );
      });
      
      return impactCol;
  }
    
    /**
     * Status attribute column builder.
     * 
     * @return Impact column
     */
    private TableColumn statusCol() {
      TableColumn<Risk, String> statusCol = new TableColumn<>(
             bundle.getString("risks.table.status")
     );
      
      StandardTableColumn.apply(statusCol, STATUS_COL_MIN_WIDTH);
      
      statusCol.setCellValueFactory(data -> {
          return new SimpleStringProperty(
                  String.valueOf(data.getValue().getStatus())
          );
      });
      
      return statusCol;
  }
}
