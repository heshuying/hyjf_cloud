package com.hyjf.data.bean.jinchuang;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 保存统计的数据
 * 
 * @author zx
 *
 */
@Document(collection = "ht_total_invest_and_interest")
public class TotalInvestAndInterestEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	// 累计出借总额
	private BigDecimal totalInvestAmount;
	// 累计出借收益
	private BigDecimal totalInterestAmount;
	// 累计出借总数
	private int totalInvestNum;
	// 计划累计出借总额
	private BigDecimal hjhTotalInvestAmount;
	// 计划累计出借收益
	private BigDecimal hjhTotalInterestAmount;
	// 计划累计出借总数
	private int hjhTotalInvestNum;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getTotalInvestAmount() {
		return totalInvestAmount;
	}

	public void setTotalInvestAmount(BigDecimal totalInvestAmount) {
		this.totalInvestAmount = totalInvestAmount;
	}

	public BigDecimal getTotalInterestAmount() {
		return totalInterestAmount;
	}

	public void setTotalInterestAmount(BigDecimal totalInterestAmount) {
		this.totalInterestAmount = totalInterestAmount;
	}

	public int getTotalInvestNum() {
		return totalInvestNum;
	}

	public void setTotalInvestNum(int totalInvestNum) {
		this.totalInvestNum = totalInvestNum;
	}

	public BigDecimal getHjhTotalInvestAmount() {
		return hjhTotalInvestAmount;
	}

	public void setHjhTotalInvestAmount(BigDecimal hjhTotalInvestAmount) {
		this.hjhTotalInvestAmount = hjhTotalInvestAmount;
	}

	public BigDecimal getHjhTotalInterestAmount() {
		return hjhTotalInterestAmount;
	}

	public void setHjhTotalInterestAmount(BigDecimal hjhTotalInterestAmount) {
		this.hjhTotalInterestAmount = hjhTotalInterestAmount;
	}

	public int getHjhTotalInvestNum() {
		return hjhTotalInvestNum;
	}

	public void setHjhTotalInvestNum(int hjhTotalInvestNum) {
		this.hjhTotalInvestNum = hjhTotalInvestNum;
	}

	@Override
	public String toString() {
		return "TotalInvestAndInterestEntity{" +
				"id='" + id + '\'' +
				", totalInvestAmount=" + totalInvestAmount +
				", totalInterestAmount=" + totalInterestAmount +
				", totalInvestNum=" + totalInvestNum +
				", hjhTotalInvestAmount=" + hjhTotalInvestAmount +
				", hjhTotalInterestAmount=" + hjhTotalInterestAmount +
				", hjhTotalInvestNum=" + hjhTotalInvestNum +
				'}';
	}
}
