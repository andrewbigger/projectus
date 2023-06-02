package com.biggerconcept.projectus.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Representation of a risk.
 * 
 * @author Andrew Bigger
 */
public class Risk {
  /**
   * ENUM of risk likelihoood.
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
      this.name = "";
      this.likelihood = DEFAULT_LIKELIHOOD;
      this.impact = DEFAULT_IMPACT;
      this.status = DEFAULT_STATUS;
      this.detail = "";
      this.mitigation = "";
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
   * @return 
   */
  public String getDetail() {
      return detail;
  }
  
  /**
   * Getter for risk mitigation strategy.
   * 
   * @return 
   */
  public String getMitigationStrategy() {
      return mitigation;
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
   * @param value 
   */
  public void setDetail(String value) {
      detail = value;
  }
  
  /**
   * Setter for mitigation strategy.
   * 
   * @param value 
   */
  public void setMitigationStrategy(String value) {
      mitigation = value;
  }
  
  /**
     * Returns true if given risk matches.
     * 
     * TODO: Things could have IDs for matching instead
     * 
     * @param r
     * @return 
     */
    public boolean match(Risk r) {
        if (r.getName() == name) {
            return true;
        }
        
        return false;
    }
}
