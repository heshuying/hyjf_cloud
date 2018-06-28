package com.hyjf.cs.trade.bean.assetpush;

import com.hyjf.am.resquest.assetpush.InfoBean;
import com.hyjf.cs.trade.bean.BaseBean;

import java.util.List;

/**
 * 资产推送请求参数
 */
public class PushRequestBean extends BaseBean {
	

    private Integer assetType;
	
	private List<PushBean> reqData;
	
	private List<InfoBean> riskInfo;

	public List<PushBean> getReqData() {
		return reqData;
	}

	public void setReqData(List<PushBean>  reqData) {
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
		
	public List<InfoBean> getRiskInfo() {
		return riskInfo;
			
	}

	/**
	 * @param riskInfo the riskInfo to set
	 */
		
	public void setRiskInfo(List<InfoBean> riskInfo) {
		this.riskInfo = riskInfo;
			
	}
    
	
	
}
