/**
 * Description:银行卡绑定列表前端显示所用PO
 * Copyright: Copyright (HYJF Corporation) 2015
 */

package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 */

public class BankcardInitCustomizeVO extends BaseVO {

	@ApiModelProperty(value = "是否默认")
	public  Map<String, String> bankcardType;
	@ApiModelProperty(value = "银行卡属性")
	public Map<String, String> bankcardProperty;

	@ApiModelProperty(value = "所属银行")
	private List<BankConfigVO> listBanksConfigVO;

	public Map<String, String> getBankcardType() {
		return bankcardType;
	}

	public void setBankcardType(Map<String, String> bankcardType) {
		this.bankcardType = bankcardType;
	}

	public Map<String, String> getBankcardProperty() {
		return bankcardProperty;
	}

	public void setBankcardProperty(Map<String, String> bankcardProperty) {
		this.bankcardProperty = bankcardProperty;
	}

	public List<BankConfigVO> getListBanksConfigVO() {
		return listBanksConfigVO;
	}

	public void setListBanksConfigVO(List<BankConfigVO> listBanksConfigVO) {
		this.listBanksConfigVO = listBanksConfigVO;
	}
}
