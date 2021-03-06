package com.hyjf.cs.trade.bean;

import com.hyjf.am.resquest.assetpush.InfoBean;

import java.util.List;

/**
 * 资产推送请求参数
 */
public class AemsPushRequestBean extends BaseBean {

	/**
	 * 结构产品类型
	 */
    private Integer assetType;
	/**
	 * 商铺信息
	 */
	private List<InfoBean> riskInfo;
	/**
	 * 资产列表请求明细
	 */
	private List<AemsPushBean> reqData;

	public Integer getAssetType() {
		return assetType;
	}

	public void setAssetType(Integer assetType) {
		this.assetType = assetType;
	}

	public List<AemsPushBean> getReqData() {
		return reqData;
	}

	public void setReqData(List<AemsPushBean> reqData) {
		this.reqData = reqData;
	}

	public List<InfoBean> getRiskInfo() {
		return riskInfo;
	}

	public void setRiskInfo(List<InfoBean> riskInfo) {
		this.riskInfo = riskInfo;
	}
}
