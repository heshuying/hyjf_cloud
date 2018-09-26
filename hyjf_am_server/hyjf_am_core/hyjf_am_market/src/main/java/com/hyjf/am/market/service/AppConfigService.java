package com.hyjf.am.market.service;

import com.hyjf.am.market.dao.model.auto.Ads;
import com.hyjf.am.market.dao.model.auto.AdsType;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.vo.market.AdsVO;
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
     List<Ads> getRecordList(AppBannerRequest bean, int limitStart, int limitEnd);

    /**
     * 根据id查询广告
     * @param id
     * @return
     */
     Ads  getRecordById(Integer id);

     Integer countRecordList(AppBannerRequest bean);

    /**
     * 获取广告类型（手机端）
     * @return
     */
     List<AdsType> getAdsTypeList();

    /**
     * 活动列表插入
     *
     * @param record
     */
     boolean insertRecord(Ads record);

    /**
     * 活动列表更新
     *
     * @param record
     */
     boolean updateRecord(AdsVO adsVO);


    /**
     * 获取单个活动列表维护
     *
     * @return
     */
     Ads getRecord(Integer record);

    /**
     * 活动列表删除
     */
     boolean deleteRecord(Integer id);




}
