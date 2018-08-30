/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import java.util.List;

import com.hyjf.am.bean.admin.BorrowCommonBean;
import com.hyjf.am.bean.admin.BorrowWithBLOBs;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.config.LinkVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowCommonVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectRepayVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;


/**
 * @author dongzeshan
 * @version BorrowFirstCustomizeResponse, v0.1 2018/7/3 15:16
 */
public class BorrowCommonResponse extends AdminResponse<BorrowCommonBean> {
	private List<HjhInstConfigVO> instList;
	private List<HjhInstConfigVO> instConfigList;
	private List<BorrowProjectTypeVO> borrowProjectType;
	private List<BorrowProjectRepayVO> borrowStyleList;
	private List<HjhAssetTypeVO> hjhAssetTypeList;
	private List<LinkVO> linkList;
	private List<ParamNameVO> housesTypeList;
	private List<ParamNameVO> companySizeList;
	private List<ParamNameVO> levelList;
	private List<ParamNameVO> currencyList;
	private BorrowWithBLOBs bo;

	
	public BorrowWithBLOBs getBo() {
		return bo;
	}
	public void setBo(BorrowWithBLOBs bo) {
		this.bo = bo;
	}
	public List<ParamNameVO> getHousesTypeList() {
		return housesTypeList;
	}
	public void setHousesTypeList(List<ParamNameVO> housesTypeList) {
		this.housesTypeList = housesTypeList;
	}
	public List<ParamNameVO> getCompanySizeList() {
		return companySizeList;
	}
	public void setCompanySizeList(List<ParamNameVO> companySizeList) {
		this.companySizeList = companySizeList;
	}
	public List<ParamNameVO> getLevelList() {
		return levelList;
	}
	public void setLevelList(List<ParamNameVO> levelList) {
		this.levelList = levelList;
	}
	public List<ParamNameVO> getCurrencyList() {
		return currencyList;
	}
	public void setCurrencyList(List<ParamNameVO> currencyList) {
		this.currencyList = currencyList;
	}
	public List<LinkVO> getLinkList() {
		return linkList;
	}
	public void setLinkList(List<LinkVO> linkList) {
		this.linkList = linkList;
	}

	public List<HjhAssetTypeVO> getHjhAssetTypeList() {
		return hjhAssetTypeList;
	}
	public void setHjhAssetTypeList(List<HjhAssetTypeVO> hjhAssetTypeList) {
		this.hjhAssetTypeList = hjhAssetTypeList;
	}
	public List<HjhInstConfigVO> getInstList() {
		return instList;
	}
	public void setInstList(List<HjhInstConfigVO> instList) {
		this.instList = instList;
	}
	public List<HjhInstConfigVO> getInstConfigList() {
		return instConfigList;
	}
	public void setInstConfigList(List<HjhInstConfigVO> instConfigList) {
		this.instConfigList = instConfigList;
	}
	public List<BorrowProjectTypeVO> getBorrowProjectType() {
		return borrowProjectType;
	}
	public void setBorrowProjectType(List<BorrowProjectTypeVO> borrowProjectType) {
		this.borrowProjectType = borrowProjectType;
	}

	public List<BorrowProjectRepayVO> getBorrowStyleList() {
		return borrowStyleList;
	}
	public void setBorrowStyleList(List<BorrowProjectRepayVO> borrowStyleList) {
		this.borrowStyleList = borrowStyleList;
	}
	
}
