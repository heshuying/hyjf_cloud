/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.bean.admin.DuibaOrderBean;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;


/**
 * @author dongzeshan
 * @version BorrowFirstCustomizeResponse, v0.1 2018/7/3 15:16
 */
public class DuibaOrderResponse extends AdminResponse<DuibaOrderBean> {
	private List<ParamNameVO> productTypeList;
	private List<ParamNameVO> orderStatusList;
	private List<ParamNameVO> deliveryStatusList;
	private List<ParamNameVO> processingStateList;
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	public List<ParamNameVO> getProductTypeList() {
		return productTypeList;
	}

	public void setProductTypeList(List<ParamNameVO> productTypeList) {
		this.productTypeList = productTypeList;
	}

	public List<ParamNameVO> getOrderStatusList() {
		return orderStatusList;
	}

	public void setOrderStatusList(List<ParamNameVO> orderStatusList) {
		this.orderStatusList = orderStatusList;
	}

	public List<ParamNameVO> getDeliveryStatusList() {
		return deliveryStatusList;
	}

	public void setDeliveryStatusList(List<ParamNameVO> deliveryStatusList) {
		this.deliveryStatusList = deliveryStatusList;
	}

	public List<ParamNameVO> getProcessingStateList() {
		return processingStateList;
	}

	public void setProcessingStateList(List<ParamNameVO> processingStateList) {
		this.processingStateList = processingStateList;
	}
}
