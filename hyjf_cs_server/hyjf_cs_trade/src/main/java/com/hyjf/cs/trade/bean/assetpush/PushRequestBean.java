package com.hyjf.cs.trade.bean.assetpush;

import com.hyjf.am.resquest.assetpush.InfoBean;
import com.hyjf.cs.trade.bean.BaseBean;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 资产推送请求参数
 */
public class PushRequestBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 155405841080295756L;

	@ApiModelProperty(value = "资产类别")
    private Integer assetType;

	@ApiModelProperty(value = "资产推送信息")
	private List<PushBean> reqData;

	@ApiModelProperty(value = "商铺信息")
	private List<InfoBean> riskInfo;

	public Integer getAssetType() {
		return assetType;
	}

	public void setAssetType(Integer assetType) {
		this.assetType = assetType;
	}

	public List<PushBean> getReqData() {
		return reqData;
	}

	public void setReqData(List<PushBean> reqData) {
		this.reqData = reqData;
	}

	public List<InfoBean> getRiskInfo() {
		return riskInfo;
	}

	public void setRiskInfo(List<InfoBean> riskInfo) {
		this.riskInfo = riskInfo;
	}
}
