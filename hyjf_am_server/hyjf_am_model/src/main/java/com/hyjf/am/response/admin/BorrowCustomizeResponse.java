/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import java.util.List;
import java.util.Map;

import com.hyjf.am.response.AdminResponse;

import com.hyjf.am.vo.admin.BorrowCustomizeVO;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
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
	private Map<String, String> st;
	private Map<String, String> bs;
	private List<BorrowCommonCustomizeVO> borrowCommonCustomizeList;
	
	public List<BorrowCommonCustomizeVO> getBorrowCommonCustomizeList() {
		return borrowCommonCustomizeList;
	}
	public void setBorrowCommonCustomizeList(List<BorrowCommonCustomizeVO> borrowCommonCustomizeList) {
		this.borrowCommonCustomizeList = borrowCommonCustomizeList;
	}
	public Map<String, String> getSt() {
		return st;
	}
	public void setSt(Map<String, String> st) {
		this.st = st;
	}
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
	public Map<String, String> getBs() {
		return bs;
	}
	public void setBs(Map<String, String> bs) {
		this.bs = bs;
	}
	
}
