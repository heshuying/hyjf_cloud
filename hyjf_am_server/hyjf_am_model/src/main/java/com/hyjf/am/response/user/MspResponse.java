package com.hyjf.am.response.user;


import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.vo.user.MspConfigureVO;
import com.hyjf.am.vo.user.MspRegionVO;

import java.util.List;


public class MspResponse extends AdminResponse<MspConfigureVO>  {

	private List<MspRegionVO> regionList;

	public List<MspRegionVO> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<MspRegionVO> regionList) {
		this.regionList = regionList;
	}

}
