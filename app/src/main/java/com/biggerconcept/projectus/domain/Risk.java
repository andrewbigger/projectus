package com.biggerconcept.projectus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.UUID;

/**
 * Representation of a risk.
 * 
 * @author Andrew Bigger
 */
public class Risk {
  /**
   * ENUM of risk likelihood.
   */
  public static enum RiskLikelihood {
      UNLIKELY,
      LIKELY,
      CERTAIN
  }

  /**
   * ENUM of risk impact.
   */
  public static enum RiskImpact {
      NONE,
      LOW,
      MEDIUM,
      HIGH,
      CRITICAL
  }
  
  /**
   * ENUM of risk status.
   */
  public static enum RiskStatus {
      PRESENT,
      MITIGATING,
      MITIGATED,
      REALISED
  }
  
  /**
   * Risk ID.
   */
  @JsonInclude(Include.NON_NULL)
  private UUID id;
  
  /**
   * Identifier for risk.
   */
  @JsonInclude(Include.NON_NULL)
  private int identifier;

  /**
   * Name of risk.
   */
  @JsonInclude(Include.NON_NULL)
  private String name;

  /**
   * Likelihood of risk.
   */
  @JsonInclude(Include.NON_NULL)
  private RiskLikelihood likelihood;

  /**
   * Impact of risk.
   */
  @JsonInclude(Include.NON_NULL)
  private RiskImpact impact;

  /**
   * Status of risk.
   */
  @JsonInclude(Include.NON_NULL)
  private RiskStatus status;
  
  /**
   * Detail of risk.
   */
  @JsonInclude(Include.NON_NULL)
  private String detail;
  
  /**
   * Mitigation details of risk.
   */
  @JsonInclude(Include.NON_NULL)
  private String mitigation;
  
  /**
   * Parent document pointer.
   */
  @JsonIgnore
  private Document parent;

  /**
   * Default likelihood.
   */
  public static final RiskLikelihood DEFAULT_LIKELIHOOD = RiskLikelihood.UNLIKELY;

  /**
   * Default impact.
   */
  public static final RiskImpact DEFAULT_IMPACT = RiskImpact.NONE;

  /**
   * Default status.
   */
  public static final RiskStatus DEFAULT_STATUS = RiskStatus.PRESENT;

  /**
   * Constructor for risk that accepts all risk parameters.
   * 
   * @param name name of risk
   * @param likelihood likelihood of risk
   * @param impact impact of risk
   * @param status status of risk
   */
  public Risk(
    String name,
    RiskLikelihood likelihood,
    RiskImpact impact,
    RiskStatus status
  ) {
      this.id = UUID.randomUUID();
      this.identifier = -1;
      this.name = name;
      this.likelihood = likelihood;
      this.impact = impact;
      this.status = status;
      this.detail = "";
      this.mitigation = "";
  }

  /**
   * Default constructor for risk.
   */
  public Risk() {
      this.id = UUID.randomUUID();
      this.identifier = -1;
      this.name = "";
      this.likelihood = DEFAULT_LIKELIHOOD;
      this.impact = DEFAULT_IMPACT;
      this.status = DEFAULT_STATUS;
      this.detail = "";
      this.mitigation = "";
  }
  
  /**
   * Getter for ID.
   * 
   * @return ID as UUID
   */
  public UUID getId() {
      return id;
  }
  
  /**
   * Getter for risk identifier.
   * 
   * @return risk identifier
   */
  public int getIdentifier() {
      return identifier;
  }

  /**
   * Getter for risk name.
   * 
   * @return risk name
   */
  public String getName() {
      return name;
  }

  /**
   * Getter for risk likelihood.
   * 
   * @return risk likelihood
   */
  public RiskLikelihood getLikelihood() {
      return likelihood;
  }

  /**
   * Getter for impact.
   * 
   * @return risk impact
   */
  public RiskImpact getImpact() {
      return impact;
  }

  /**
   * Getter for status.
   * 
   * @return risk status
   */
  public RiskStatus getStatus() {
      return status;
  }
  
  /**
   * Getter for risk detail.
   * 
   * @return further details about risk
   */
  public String getDetail() {
      return detail;
  }
  
  /**
   * Getter for risk mitigation strategy.
   * 
   * @return risk mitigation strategy
   */
  public String getMitigationStrategy() {
      return mitigation;
  }
  
  /**
   * Setter for parent document pointer.
   * 
   * @param value parent document
   */
  public void setParent(Document value) {
      parent = value;
  }
  
  /**
   * Setter for ID.
   * 
   * @param value UUID to set as ID
   */
  public void setId(UUID value) {
      id = value;
  }
  
  /**
   * String based setter for ID.
   * 
   * @param value String to set as ID
   */
   public void setId(String value) {
      id = UUID.fromString(value);
   }
  
  /**
   * Setter for identifier.
   * 
   * @param value new identifier
   */
  public void setIdentifier(int value) {
      identifier = value;
  }
  
  /**
   * Setter for name.
   * 
   * @param value new name
   */
  public void setName(String value) {
      name = value;
  }

  /**
   * Setter for likelihood.
   * 
   * @param value likelihood to set
   */
  public void setLikelihood(RiskLikelihood value) {
      likelihood = value;
  }

  /**
   * Setter for impact.
   * 
   * @param value impact to set
   */
  public void setImpact(RiskImpact value) {
      impact = value;
  }

  /**
   * Setter for status.
   * 
   * @param value status to set
   */
  public void setStatus(RiskStatus value) {
      status = value;
  }
  
  /**
   * Setter for detail.
   * 
   * @param value details for risk
   */
  public void setDetail(String value) {
      detail = value;
  }
  
  /**
   * Setter for mitigation strategy.
   * 
   * @param value mitigation strategy for risk
   */
  public void setMitigationStrategy(String value) {
      mitigation = value;
  }
  
  /**
     * Returns true if given risk matches.
     * 
     * @param r risk to match
     * @return whether risk matches
     */
    public boolean match(Risk r) {
        if (r.getIdentifier() == identifier) {
            return true;
        }
        
        return false;
    }
}
