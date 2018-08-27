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
	public  List<DropDownVO> bankcardType;
	@ApiModelProperty(value = "银行卡属性")
	public List<DropDownVO> bankcardProperty;

	@ApiModelProperty(value = "所属银行")
	private List<DropDownVO> listBanksConfigVO;

	public List<DropDownVO> getBankcardType() {
		return bankcardType;
	}

	public void setBankcardType(List<DropDownVO> bankcardType) {
		this.bankcardType = bankcardType;
	}

	public List<DropDownVO> getBankcardProperty() {
		return bankcardProperty;
	}

	public void setBankcardProperty(List<DropDownVO> bankcardProperty) {
		this.bankcardProperty = bankcardProperty;
	}

	public List<DropDownVO> getListBanksConfigVO() {
		return listBanksConfigVO;
	}

	public void setListBanksConfigVO(List<DropDownVO> listBanksConfigVO) {
		this.listBanksConfigVO = listBanksConfigVO;
	}
}
