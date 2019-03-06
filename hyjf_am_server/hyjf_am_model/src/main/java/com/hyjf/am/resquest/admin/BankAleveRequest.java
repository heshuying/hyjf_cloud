/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author zdj
 * @version BankAleveRequest, v0.1 2018/7/10 19:26
 */
public class BankAleveRequest extends BasePage implements Serializable {

	@ApiModelProperty(value = "自动同步用生成订单id")
	private String orderId;
	@ApiModelProperty(value = "电子账号")
	private String cardnbr;
	@ApiModelProperty(value = "系统跟单号")
	private String seqno;
	@ApiModelProperty(value = "交易类型")
	private String transtype;
	@ApiModelProperty(value = "用户")
	private Integer userId;

	@ApiModelProperty(value = "入账日期开始")
	private String startValdate;
	@ApiModelProperty(value = "入账日期结束")
	private String endValdate;

	@ApiModelProperty(value = "交易日期开始")
	private String startInpdate;
	@ApiModelProperty(value = "交易日期结束")
	private String endInpdate;

	@ApiModelProperty(value = "自然日期开始")
	private String startReldate;
	@ApiModelProperty(value = "自然日期结束")
	private String endReldate;

	@ApiModelProperty(value = "历史数据处理日期")
	private String dualDate;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getStartValdate() {
		return startValdate;
	}

	public void setStartValdate(String startValdate) {
		this.startValdate = startValdate;
	}

	public String getEndValdate() {
		return endValdate;
	}

	public void setEndValdate(String endValdate) {
		this.endValdate = endValdate;
	}

	public String getStartInpdate() {
		return startInpdate;
	}

	public void setStartInpdate(String startInpdate) {
		this.startInpdate = startInpdate;
	}

	public String getEndInpdate() {
		return endInpdate;
	}

	public void setEndInpdate(String endInpdate) {
		this.endInpdate = endInpdate;
	}

	public String getStartReldate() {
		return startReldate;
	}

	public void setStartReldate(String startReldate) {
		this.startReldate = startReldate;
	}

	public String getEndReldate() {
		return endReldate;
	}

	public void setEndReldate(String endReldate) {
		this.endReldate = endReldate;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	public String getCardnbr() {
		return cardnbr;
	}

	public void setCardnbr(String cardnbr) {
		this.cardnbr = cardnbr;
	}

	public String getSeqno() {
		return seqno;
	}

	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}

	public String getTranstype() {
		return transtype;
	}

	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	public String getDualDate() {
		return dualDate;
	}

	public void setDualDate(String dualDate) {
		this.dualDate = dualDate;
	}

	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;

	public int limit;

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * 列表画面自定义标签上的用翻页对象：paginator
	 */
	private com.hyjf.common.paginator.Paginator paginator;

	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}
}
