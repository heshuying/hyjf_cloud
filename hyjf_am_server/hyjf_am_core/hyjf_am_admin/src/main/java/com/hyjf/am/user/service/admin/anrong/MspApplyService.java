package com.hyjf.am.user.service.admin.anrong;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.service.BaseService;

import java.util.List;


public interface MspApplyService extends BaseService{

	  /**
     * 获取手续费列表列表
     * 
     * @return
     */
    public List<MspApply> getRecordList(MspApply mspApply, int limitStart, int limitEnd,int createStart,int createEnd);

    /**
     * 获取单个手续费列表维护
     * 
     * @return
     */
    public MspApply getRecord(Integer record);

    /**
     * 根据主键判断手续费列表中数据是否存在
     * 
     * @return
     */
    public boolean isExistsRecord(MspApply record);

    /**
     * 手续费列表插入
     * 
     * @param record
     */
    public void insertRecord(MspApply record);

    /**
     * 手续费列表更新
     * 
     * @param record
     */
    public void updateRecord(MspApply record);
    
    /**
     * 配置删除
     * 
     * @param record
     */
    public void deleteRecord(List<Integer> recordList);



    /**
     * 获取城市列表
     * 
     * @return
     */
    public List<MspRegion> getRegionList();
    
    /**
     * 获取配置列表 
     * 
     * @return
     */
	public List<MspConfigure> getConfigureList();
    /**
     * 获取单个配置 
     * 
     * @return
     */
	
	public MspConfigure getConfigure(int id);

    /**
     * 获取反欺诈 
     * 
     * @return
     */
	
	public MspFqz getFqz(String applyId);
	
    /**
     * 获取反欺诈 
     * 
     * @return
     */
	
	public List<MspAnliinfos> getAnliInfos(String applyId);
	
	
	public List<MspShixininfos> getShixinInfos(String applyId);
	
	
	public List<MspZhixinginfos> getZhixingInfos(String applyId);
	
	public MspTitle getTitle(String applyId);
	public List<MspNormalcreditdetail> getNormalCreditDetail(String applyId);
	public List<MspApplydetails> getApplyDetails(String applyId);
	public List<MspQuerydetail> getQueryDetail(String applyId);	
	public List<MspBlackdata> getBlackData(String applyId);
	public List<MspAbnormalcreditdetail> getAbnormalCreditDetail(String applyId);
	public List<MspAbnormalcredit> getAbnormalCredit(String applyId);
	
	 public int countByExample(MspApply record);

	void insertResult(JSONObject resultJson, String appid);
}
