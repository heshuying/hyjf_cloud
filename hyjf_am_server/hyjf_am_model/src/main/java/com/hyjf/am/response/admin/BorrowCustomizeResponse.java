/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import java.util.List;

import com.hyjf.am.response.AdminResponse;

import com.hyjf.am.vo.admin.BorrowCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;


/**
 * @author dongzeshan
 * @version BorrowFirstCustomizeResponse, v0.1 2018/7/3 15:16
 */
public class BorrowCustomizeResponse extends AdminResponse<BorrowCustomizeVO> {
	private List<HjhInstConfigVO> hjhInstConfig;
	private List<BorrowProjectTypeVO> borrowProjectType;
	private List<BorrowStyleVO> borrowStyle;
	public List<HjhInstConfigVO> getHjhInstConfig() {
		return hjhInstConfig;
	}
	public void setHjhInstConfig(List<HjhInstConfigVO> hjhInstConfig) {
		this.hjhInstConfig = hjhInstConfig;
	}
	public List<BorrowProjectTypeVO> getBorrowProjectType() {
		return borrowProjectType;
	}
	public void setBorrowProjectType(List<BorrowProjectTypeVO> borrowProjectType) {
		this.borrowProjectType = borrowProjectType;
	}
	public List<BorrowStyleVO> getBorrowStyle() {
		return borrowStyle;
	}
	public void setBorrowStyle(List<BorrowStyleVO> borrowStyle) {
		this.borrowStyle = borrowStyle;
	}
}
