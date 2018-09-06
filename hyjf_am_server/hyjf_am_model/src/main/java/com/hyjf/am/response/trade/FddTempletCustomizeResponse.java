/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.FddTempletCustomizeVO;

import java.util.List;

/**
 * @author fuqiang
 * @version FddTempletCustomizeResponse, v0.1 2018/7/10 16:43
 */
public class FddTempletCustomizeResponse extends Response<FddTempletCustomizeVO> {
	private int count;

	private FddTempletCustomizeVO protocolsForm;

	private List<ParamNameVO> protocolTypeList;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public FddTempletCustomizeVO getProtocolsForm() {
		return protocolsForm;
	}

	public void setProtocolsForm(FddTempletCustomizeVO protocolsForm) {
		this.protocolsForm = protocolsForm;
	}

	public List<ParamNameVO> getProtocolTypeList() {
		return protocolTypeList;
	}

	public void setProtocolTypeList(List<ParamNameVO> protocolTypeList) {
		this.protocolTypeList = protocolTypeList;
	}
}
