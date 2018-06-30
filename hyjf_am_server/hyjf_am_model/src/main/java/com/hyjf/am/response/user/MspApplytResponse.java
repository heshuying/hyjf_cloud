package com.hyjf.am.response.user;

import java.util.List;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.MspApplyVO;
import com.hyjf.am.vo.user.MspConfigureVO;
import com.hyjf.am.vo.user.MspRegionVO;

public class MspApplytResponse extends Response<MspApplyVO>  {


	private int recordTotal;

	private List<MspConfigureVO> configureList;
    private List<MspRegionVO> regionList;
    public List<MspRegionVO> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<MspRegionVO> regionList) {
		this.regionList = regionList;
	}

	public List<MspConfigureVO> getConfigureList() {
		return configureList;
	}

	public void setConfigureList(List<MspConfigureVO> configureList) {
		this.configureList = configureList;
	}



	public int getRecordTotal() {
		return recordTotal;
	}

	public void setRecordTotal(int recordTotal) {
		this.recordTotal = recordTotal;
	}
}
