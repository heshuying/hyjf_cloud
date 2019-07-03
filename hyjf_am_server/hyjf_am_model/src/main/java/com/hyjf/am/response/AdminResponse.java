package com.hyjf.am.response;


import com.hyjf.am.vo.admin.AdminVO;

import java.util.List;

/**
 * @author dongzeshan
 * @version Response, v0.1 2018/1/21 22:18
 */
public class AdminResponse<T> extends Response<T>{
	
	//admin增加返回总数
		private int recordTotal;

		public List<AdminVO> adminVOList;

		public int getRecordTotal() {
			return recordTotal;
		}

		public void setRecordTotal(int recordTotal) {
			this.recordTotal = recordTotal;
		}

	public List<AdminVO> getAdminVOList() {
		return adminVOList;
	}

	public void setAdminVOList(List<AdminVO> adminVOList) {
		this.adminVOList = adminVOList;
	}
}
