package com.hyjf.admin.service;


import com.hyjf.am.response.user.MspApplytResponse;
import com.hyjf.am.response.user.MspResponse;
import com.hyjf.am.resquest.user.MspApplytRequest;
import com.hyjf.am.resquest.user.MspRequest;
/**
 * @author by dongzeshan on 2018/8/14.
 */
public interface MspApplyService {
    /**
     * 获取安融集合
     * @return
     */
	public MspApplytResponse getRecordList(MspApplytRequest mspApplytRequest);
    /**
     * 获取单个安融
     * @return
     */
	public MspApplytResponse infoAction();
    /**
     * 插入安融信息
     * @return
     */
	public MspApplytResponse insertAction(MspApplytRequest mspApplytRequest);
    /**
     * 修改安融
     * @return
     */
	public MspApplytResponse updateAction(MspApplytRequest mspApplytRequest);
    /**
     * 删除安融
     * @return
     */
	public MspApplytResponse deleteRecordAction(MspApplytRequest mspApplytRequest);
    /**
     * 验证
     * @return
     */
	public MspApplytResponse validateBeforeAction(MspApplytRequest mspApplytRequest);
    /**
     * 共享页面载入
     * @return
     */
	public MspApplytResponse applyInfo(MspApplytRequest mspApplytRequest);
    /**
     * 共享
     * @return
     */
	public MspApplytResponse shareUser(MspApplytRequest mspApplytRequest);
    /**
     * 下载数据
     * @return
     */
	public MspApplytResponse download(MspApplytRequest mspApplytRequest);
    /**
     * 查询配置
     * @return
     */
	public MspResponse searchAction(MspRequest mspRequest);
    /**
     * 查询配置
     * @return
     */
	public MspResponse infoAction(MspRequest mspRequest);
    /**
     * 插入配置
     * @return
     */
	public MspResponse insertAction(MspRequest mspRequest);
    /**
     * 修改配置
     * @return
     */
	public MspResponse updateAction(MspRequest mspRequest);
    /**
     * 查询配置名
     * @return
     */
	public MspResponse configureNameError(MspRequest mspRequest);
    /**
     * 删除配置
     * @return
     */
	public MspResponse deleteAction(MspRequest mspRequest);
    /**
     * 检查配置
     * @return
     */
	public MspResponse checkAction(MspRequest mspRequest);
}
