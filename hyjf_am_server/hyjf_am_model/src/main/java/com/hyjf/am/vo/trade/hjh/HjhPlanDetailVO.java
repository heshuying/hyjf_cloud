/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.hjh;

import java.io.Serializable;

/**
 * @author Albert
 * @version HjhPlanDetailVO.java, v0.1 2018年7月6日 下午4:06:03
 */
public class HjhPlanDetailVO extends HjhPlanVO implements Serializable{

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String planConcept;

    private String planPrinciple;

    private String safeguardMeasures;

    private String marginMeasures;

    private String normalQuestions;

	public String getPlanConcept() {
		return planConcept;
	}

	public void setPlanConcept(String planConcept) {
		this.planConcept = planConcept;
	}

	public String getPlanPrinciple() {
		return planPrinciple;
	}

	public void setPlanPrinciple(String planPrinciple) {
		this.planPrinciple = planPrinciple;
	}

	public String getSafeguardMeasures() {
		return safeguardMeasures;
	}

	public void setSafeguardMeasures(String safeguardMeasures) {
		this.safeguardMeasures = safeguardMeasures;
	}

	public String getMarginMeasures() {
		return marginMeasures;
	}

	public void setMarginMeasures(String marginMeasures) {
		this.marginMeasures = marginMeasures;
	}

	public String getNormalQuestions() {
		return normalQuestions;
	}

	public void setNormalQuestions(String normalQuestions) {
		this.normalQuestions = normalQuestions;
	}
}
