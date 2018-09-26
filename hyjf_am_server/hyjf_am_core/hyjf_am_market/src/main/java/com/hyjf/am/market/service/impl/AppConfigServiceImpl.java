package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.auto.AdsMapper;
import com.hyjf.am.market.dao.mapper.auto.AdsTypeMapper;
import com.hyjf.am.market.dao.model.auto.Ads;
import com.hyjf.am.market.dao.model.auto.AdsExample;
import com.hyjf.am.market.dao.model.auto.AdsType;
import com.hyjf.am.market.dao.model.auto.AdsTypeExample;
import com.hyjf.am.market.service.AppConfigService;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.vo.market.AdsWithBLOBsVO;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lisheng
 * @version AppConfigServiceImpl, v0.1 2018/7/11 15:15
 */

@Service
public class AppConfigServiceImpl implements AppConfigService {

    @Autowired
    AdsMapper adsMapper;

    @Autowired
    AdsTypeMapper adsTypeMapper;

    @Override
    public List<Ads> getRecordList(AppBannerRequest bean, int limitStart, int limitEnd) {
        AdsExample example = new AdsExample();
        AdsExample.Criteria criteria = example.createCriteria();
        criteria.andClientTypeEqualTo(1);//手机端广告
        // 条件查询
        if (bean.getTypeid() != null) {
            criteria.andTypeIdEqualTo(bean.getTypeid());
        }
        if (bean.getPlatformType() != null) {
            criteria.andPlatformTypeEqualTo(bean.getPlatformType());
        }
        if (StringUtils.isNotEmpty(bean.getName())) {
            criteria.andNameLike("%" + bean.getName() + "%");
        }
        if (bean.getStatus() != null) {
            criteria.andStatusEqualTo(bean.getStatus());
        }
        if (StringUtils.isNotEmpty(bean.getStartCreate()) && StringUtils.isNotEmpty(bean.getEndCreate())) {
            criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.stringToDate(GetDate.getDayStart(bean.getStartCreate())));
        }
        if(StringUtils.isNotEmpty(bean.getEndCreate())){
            criteria.andCreateTimeLessThanOrEqualTo(GetDate.stringToDate(GetDate.getDayEnd(bean.getEndCreate())));
        }
        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        example.setOrderByClause("create_time Desc");
        return adsMapper.selectByExample(example);
    }

    @Override
    public Ads getRecordById(Integer id) {
        return adsMapper.selectByPrimaryKey(id);
    }


    /**
     * 获取列表数
     * @param bean
     * @return
     * @author Michael
     */
    @Override
    public Integer  countRecordList(AppBannerRequest bean) {
        AdsExample example = new AdsExample();
        AdsExample.Criteria criteria = example.createCriteria();
        criteria.andClientTypeEqualTo(1);//手机端广告
        // 条件查询
        if (bean.getTypeid() != null) {
            criteria.andTypeIdEqualTo(bean.getTypeid());
        }
        if (bean.getPlatformType() != null) {
            criteria.andPlatformTypeEqualTo(bean.getPlatformType());
        }
        if (StringUtils.isNotEmpty(bean.getName())) {
            criteria.andNameLike("%" + bean.getName() + "%");
        }
        if (bean.getStatus() != null) {
            criteria.andStatusEqualTo(bean.getStatus());
        }
        if (StringUtils.isNotBlank(bean.getStartCreate()) && StringUtils.isNotBlank(bean.getEndCreate())) {
            criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.stringToDate(GetDate.getDayStart(bean.getStartCreate())));
        }
        if(StringUtils.isNotBlank(bean.getEndCreate())){
            criteria.andCreateTimeLessThanOrEqualTo(GetDate.stringToDate(GetDate.getDayEnd(bean.getEndCreate())));
        }

        int cnt = adsMapper.countByExample(example);
        if(cnt > 0){
            return cnt;
        }
        return 0;
    }

    /**
     * 获取广告类型列表
     *
     * @return
     */
    @Override
    public List<AdsType> getAdsTypeList() {
        AdsTypeExample example = new AdsTypeExample();
        AdsTypeExample.Criteria cra =  example.createCriteria();
        cra.andClientTypeEqualTo(1);
        return adsTypeMapper.selectByExample(example);
    }

    @Override
    public boolean insertRecord(Ads record) {
        if(record.getIsIndex()==null){
            record.setIsIndex(new Integer("0"));
        }
        record.setClientType(1);
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        return adsMapper.insertSelective(record)>0?true:false;
    }

    /**
     * 广告维护更新
     *
     * @param record
     */
    @Override
    public boolean updateRecord(AdsWithBLOBsVO record) {
        Ads ads = new Ads();
        BeanUtils.copyProperties(record, ads);
        if(ads.getIsIndex()==null){
            ads.setIsIndex(0);
        }
        ads.setUpdateTime(new Date());
        return adsMapper.updateByPrimaryKeySelective(ads)>0?true:false;
    }

    @Override
    public Ads getRecord(Integer record) {
        return adsMapper.selectByPrimaryKey(record);
    }

    /**
     * 根据主键删除环境
     * @param recordList
     */
    @Override
    public boolean deleteRecord(Integer id) {
        int i = adsMapper.deleteByPrimaryKey(id);
        return i>0?true:false;
    }


}
