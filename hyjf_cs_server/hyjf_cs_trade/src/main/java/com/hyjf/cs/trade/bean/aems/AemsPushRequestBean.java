package com.hyjf.cs.trade.bean.aems;

import com.hyjf.cs.trade.bean.BaseBean;

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
	 * 资产列表请求明细
	 */
	private List<AemsPushBean> reqData;
	/**
	 * 商铺信息
	 */
	private List<AemsInfoBean> riskInfo;

	public List<AemsPushBean> getReqData() {
		return reqData;
	}

	public void setReqData(List<AemsPushBean>  reqData) {
		this.reqData = reqData;
	}

	public Integer getAssetType() {
		return assetType;
	}

	public void setAssetType(Integer assetType) {
		this.assetType = assetType;
	}

	/**
	 * riskInfo
	 * @return the riskInfo
	 */
		
	public List<AemsInfoBean> getRiskInfo() {
		return riskInfo;
			
	}

	/**
	 * @param riskInfo the riskInfo to set
	 */
		
	public void setRiskInfo(List<AemsInfoBean> riskInfo) {
		this.riskInfo = riskInfo;
			
	}
    
	
	
}
