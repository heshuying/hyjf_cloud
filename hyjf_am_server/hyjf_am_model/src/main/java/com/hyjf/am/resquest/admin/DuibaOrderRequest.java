package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * @package com.hyjf.am.resquest.admin.DuibaOrderRequest;
 * @author wenxin
 * @date 2019/05/29 14:45
 * @version V1.0  
 */
public class DuibaOrderRequest extends BasePage implements Serializable {


	/**
	 * 兑吧订单号
	 *
	 * @mbggenerated
	 */
	@ApiModelProperty(value = "兑吧订单号(检索用)")
	private String duibaOrderIdSerach;

	/**
	 * 汇盈订单号
	 *
	 * @mbggenerated
	 */
	@ApiModelProperty(value = "汇盈订单号(检索用)")
	private String hyOrderIdSerach;

	/**
	 * 订单兑换人用户名
	 *
	 * @mbggenerated
	 */
	@ApiModelProperty(value = "订单兑换人用户名(检索用)")
	private String userNameSerach;

	/**
	 * 姓名
	 *
	 * @mbggenerated
	 */
	@ApiModelProperty(value = "姓名(检索用)")
	private String trueNameSerach;

	/**
	 * 兑换内容
	 *
	 * @mbggenerated
	 */
	@ApiModelProperty(value = "兑换内容(检索用)")
	private String exchangeContentSerach;

	/**
	 * 商品类型
	 *
	 * @mbggenerated
	 */
	@ApiModelProperty(value = "商品类型(检索用)")
	private String productTypeSerach;

	/**
	 * 订单状态
	 *
	 * @mbggenerated
	 */
	@ApiModelProperty(value = "订单状态(检索用)")
	private String orderStatusSerach;

	/**
	 * 下单时间（开始）
	 *
	 * @mbggenerated
	 */
	@ApiModelProperty(value = "下单时间(检索用开始)")
	private String orderTimeSerachStart;

	/**
	 * 下单时间（结束）
	 *
	 * @mbggenerated
	 */
	@ApiModelProperty(value = "下单时间(检索用结束)")
	private String orderTimeSerachEnd;

	/**
	 * 完成时间（开始）
	 *
	 * @mbggenerated
	 */
	@ApiModelProperty(value = "完成时间(检索用开始)")
	private String completionTimeSerachStart;

	/**
	 * 完成时间（结束）
	 *
	 * @mbggenerated
	 */
	@ApiModelProperty(value = "完成时间(检索用结束)")
	private String completionTimeSerachEnd;

	/**
	 * 发货状态
	 *
	 * @mbggenerated
	 */
	@ApiModelProperty(value = "发货状态(检索用)")
	private String deliveryStatusSerach;

	/**
	 * 虚拟商品充值状态
	 *
	 * @mbggenerated
	 */
	@ApiModelProperty(value = "虚拟商品充值状态(检索用)")
	private String rechargeStateSerach;

	/**
	 * 处理状态
	 *
	 * @mbggenerated
	 */
	@ApiModelProperty(value = "处理状态(检索用)")
	private String processingStateSerach;

	/**
	 * 页签状态
	 *
	 * @mbggenerated
	 */
	@ApiModelProperty(value = "页签状态(检索用)1：订单查询，2.订单发货，3.异常订单")
	private String orderTypeTab;

	private static final long serialVersionUID = 1L;

	public String getDuibaOrderIdSerach() {
		return duibaOrderIdSerach;
	}

	public void setDuibaOrderIdSerach(String duibaOrderIdSerach) {
		this.duibaOrderIdSerach = duibaOrderIdSerach;
	}

	public String getHyOrderIdSerach() {
		return hyOrderIdSerach;
	}

	public void setHyOrderIdSerach(String hyOrderIdSerach) {
		this.hyOrderIdSerach = hyOrderIdSerach;
	}

	public String getUserNameSerach() {
		return userNameSerach;
	}

	public void setUserNameSerach(String userNameSerach) {
		this.userNameSerach = userNameSerach;
	}

	public String getTrueNameSerach() {
		return trueNameSerach;
	}

	public void setTrueNameSerach(String trueNameSerach) {
		this.trueNameSerach = trueNameSerach;
	}

	public String getExchangeContentSerach() {
		return exchangeContentSerach;
	}

	public void setExchangeContentSerach(String exchangeContentSerach) {
		this.exchangeContentSerach = exchangeContentSerach;
	}

	public String getProductTypeSerach() {
		return productTypeSerach;
	}

	public void setProductTypeSerach(String productTypeSerach) {
		this.productTypeSerach = productTypeSerach;
	}

	public String getOrderStatusSerach() {
		return orderStatusSerach;
	}

	public void setOrderStatusSerach(String orderStatusSerach) {
		this.orderStatusSerach = orderStatusSerach;
	}

	public String getDeliveryStatusSerach() {
		return deliveryStatusSerach;
	}

	public void setDeliveryStatusSerach(String deliveryStatusSerach) {
		this.deliveryStatusSerach = deliveryStatusSerach;
	}

	public String getRechargeStateSerach() {
		return rechargeStateSerach;
	}

	public void setRechargeStateSerach(String rechargeStateSerach) {
		this.rechargeStateSerach = rechargeStateSerach;
	}

	public String getProcessingStateSerach() {
		return processingStateSerach;
	}

	public void setProcessingStateSerach(String processingStateSerach) {
		this.processingStateSerach = processingStateSerach;
	}

	public String getOrderTypeTab() {
		return orderTypeTab;
	}

	public void setOrderTypeTab(String orderTypeTab) {
		this.orderTypeTab = orderTypeTab;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getOrderTimeSerachStart() {
		return orderTimeSerachStart;
	}

	public void setOrderTimeSerachStart(String orderTimeSerachStart) {
		this.orderTimeSerachStart = orderTimeSerachStart;
	}

	public String getOrderTimeSerachEnd() {
		return orderTimeSerachEnd;
	}

	public void setOrderTimeSerachEnd(String orderTimeSerachEnd) {
		this.orderTimeSerachEnd = orderTimeSerachEnd;
	}

	public String getCompletionTimeSerachStart() {
		return completionTimeSerachStart;
	}

	public void setCompletionTimeSerachStart(String completionTimeSerachStart) {
		this.completionTimeSerachStart = completionTimeSerachStart;
	}

	public String getCompletionTimeSerachEnd() {
		return completionTimeSerachEnd;
	}

	public void setCompletionTimeSerachEnd(String completionTimeSerachEnd) {
		this.completionTimeSerachEnd = completionTimeSerachEnd;
	}
}