package com.hyjf.am.response.user;

import java.math.BigDecimal;
import java.util.List;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.MspAbnormalBeanVO;
import com.hyjf.am.vo.user.MspAnliinfosVO;
import com.hyjf.am.vo.user.MspApplyVO;
import com.hyjf.am.vo.user.MspApplydetailsVO;
import com.hyjf.am.vo.user.MspBlackdataVO;
import com.hyjf.am.vo.user.MspConfigureVO;
import com.hyjf.am.vo.user.MspFqzVO;
import com.hyjf.am.vo.user.MspNormalcreditdetailVO;
import com.hyjf.am.vo.user.MspQuerydetailVO;
import com.hyjf.am.vo.user.MspRegionVO;
import com.hyjf.am.vo.user.MspShixininfosVO;
import com.hyjf.am.vo.user.MspTitleVO;
import com.hyjf.am.vo.user.MspZhixinginfosVO;

public class MspApplytResponse extends AdminResponse<MspApplyVO>  {


	private int recordTotal;

	private List<MspConfigureVO> configureList;
    private List<MspRegionVO> regionList;
    private MspFqzVO mspFqz;
    private List<MspShixininfosVO> mspShixinInfosList;
    private List<MspZhixinginfosVO> mspZhixingInfosList;
    private List<MspAnliinfosVO> mspAnliInfosList;
    private MspTitleVO mspTitle;
    private List<MspNormalcreditdetailVO> mspNormalCreditDetailList;
    private List<MspBlackdataVO> mspBlackDataList;
    private List<MspAbnormalBeanVO> mspAbnormalBeanList;
    private List<MspApplydetailsVO> mspApplydetailsList;
    private List<MspQuerydetailVO> mspQuerydetailList;
    
	BigDecimal tongguo;
	BigDecimal jujue;
	BigDecimal  zongshu;
	BigDecimal  quxiao;
	int quxiaoshu;
    int sangeyue;
    int liugeyue;
    int zongji;
    

	public List<MspAbnormalBeanVO> getMspAbnormalBeanList() {
		return mspAbnormalBeanList;
	}

	public void setMspAbnormalBeanList(List<MspAbnormalBeanVO> mspAbnormalBeanList) {
		this.mspAbnormalBeanList = mspAbnormalBeanList;
	}

	public List<MspApplydetailsVO> getMspApplydetailsList() {
		return mspApplydetailsList;
	}

	public void setMspApplydetailsList(List<MspApplydetailsVO> mspApplydetailsList) {
		this.mspApplydetailsList = mspApplydetailsList;
	}

	public List<MspQuerydetailVO> getMspQuerydetailList() {
		return mspQuerydetailList;
	}

	public void setMspQuerydetailList(List<MspQuerydetailVO> mspQuerydetailList) {
		this.mspQuerydetailList = mspQuerydetailList;
	}

	public MspTitleVO getMspTitle() {
		return mspTitle;
	}

	public void setMspTitle(MspTitleVO mspTitle) {
		this.mspTitle = mspTitle;
	}

	public MspFqzVO getMspFqz() {
		return mspFqz;
	}

	public void setMspFqz(MspFqzVO mspFqz) {
		this.mspFqz = mspFqz;
	}

	public List<MspShixininfosVO> getMspShixinInfosList() {
		return mspShixinInfosList;
	}

	public void setMspShixinInfosList(List<MspShixininfosVO> mspShixinInfosList) {
		this.mspShixinInfosList = mspShixinInfosList;
	}

	public List<MspZhixinginfosVO> getMspZhixingInfosList() {
		return mspZhixingInfosList;
	}

	public void setMspZhixingInfosList(List<MspZhixinginfosVO> mspZhixingInfosList) {
		this.mspZhixingInfosList = mspZhixingInfosList;
	}

	public List<MspAnliinfosVO> getMspAnliInfosList() {
		return mspAnliInfosList;
	}

	public void setMspAnliInfosList(List<MspAnliinfosVO> mspAnliInfosList) {
		this.mspAnliInfosList = mspAnliInfosList;
	}


	public List<MspNormalcreditdetailVO> getMspNormalCreditDetailList() {
		return mspNormalCreditDetailList;
	}

	public void setMspNormalCreditDetailList(List<MspNormalcreditdetailVO> mspNormalCreditDetailList) {
		this.mspNormalCreditDetailList = mspNormalCreditDetailList;
	}

	public List<MspBlackdataVO> getMspBlackDataList() {
		return mspBlackDataList;
	}

	public void setMspBlackDataList(List<MspBlackdataVO> mspBlackDataList) {
		this.mspBlackDataList = mspBlackDataList;
	}


	public BigDecimal getTongguo() {
		return tongguo;
	}

	public void setTongguo(BigDecimal tongguo) {
		this.tongguo = tongguo;
	}

	public BigDecimal getJujue() {
		return jujue;
	}

	public void setJujue(BigDecimal jujue) {
		this.jujue = jujue;
	}

	public BigDecimal getZongshu() {
		return zongshu;
	}

	public void setZongshu(BigDecimal zongshu) {
		this.zongshu = zongshu;
	}

	public BigDecimal getQuxiao() {
		return quxiao;
	}

	public void setQuxiao(BigDecimal quxiao) {
		this.quxiao = quxiao;
	}

	public int getQuxiaoshu() {
		return quxiaoshu;
	}

	public void setQuxiaoshu(int quxiaoshu) {
		this.quxiaoshu = quxiaoshu;
	}

	public int getSangeyue() {
		return sangeyue;
	}

	public void setSangeyue(int sangeyue) {
		this.sangeyue = sangeyue;
	}

	public int getLiugeyue() {
		return liugeyue;
	}

	public void setLiugeyue(int liugeyue) {
		this.liugeyue = liugeyue;
	}

	public int getZongji() {
		return zongji;
	}

	public void setZongji(int zongji) {
		this.zongji = zongji;
	}

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



	@Override
    public int getRecordTotal() {
		return recordTotal;
	}

	@Override
    public void setRecordTotal(int recordTotal) {
		this.recordTotal = recordTotal;
	}
}
