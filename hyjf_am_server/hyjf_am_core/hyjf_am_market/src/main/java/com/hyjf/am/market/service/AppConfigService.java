package com.hyjf.am.market.service;

import com.hyjf.am.market.dao.model.auto.Ads;
import com.hyjf.am.market.dao.model.auto.AdsType;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.vo.market.AdsWithBLOBsVO;

import java.util.List;

/**
 * @author lisheng
 * @version AppConfigService, v0.1 2018/7/11 15:14
 */

public interface AppConfigService {
    /**
     * 获取列表
     * @return
     */
    public List<Ads> getRecordList(AppBannerRequest bean, int limitStart, int limitEnd);

    public Integer countRecordList(AppBannerRequest bean);

    /**
     * 获取广告类型（手机端）
     * @return
     */
    public List<AdsType> getAdsTypeList();

    /**
     * 活动列表插入
     *
     * @param record
     */
    public boolean insertRecord(Ads record);

    /**
     * 活动列表更新
     *
     * @param record
     */
    public boolean updateRecord(AdsWithBLOBsVO record);


    /**
     * 获取单个活动列表维护
     *
     * @return
     */
    public Ads getRecord(Integer record);

    /**
     * 活动列表删除
     */
    public boolean deleteRecord(List<Integer> recordList);




}
