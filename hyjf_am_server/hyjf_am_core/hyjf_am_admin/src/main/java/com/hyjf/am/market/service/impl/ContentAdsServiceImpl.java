package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.auto.AdsMapper;
import com.hyjf.am.market.dao.mapper.auto.AdsTypeMapper;
import com.hyjf.am.market.dao.model.auto.Ads;
import com.hyjf.am.market.dao.model.auto.AdsExample;
import com.hyjf.am.market.dao.model.auto.AdsType;
import com.hyjf.am.market.dao.model.auto.AdsTypeExample;
import com.hyjf.am.market.service.ContentAdsService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.ContentAdsResponse;
import com.hyjf.am.resquest.admin.ContentAdsRequest;
import com.hyjf.am.vo.admin.AdsVO;
import com.hyjf.am.vo.config.ContentAdsBeanVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 内容中心-广告管理
 * @author：yinhui
 * @Date: 2018/7/19  14:50
 */
@Service
public class ContentAdsServiceImpl implements ContentAdsService {

    private static final Logger logger = LoggerFactory.getLogger(ContentAdsServiceImpl.class);

    @Autowired
    private AdsMapper adsMapper;

    @Autowired
    private AdsTypeMapper adsTypeMapper;

    /**
     * 条件查找
     * @param request
     * @return
     */
    @Override
    public ContentAdsResponse searchActionPage(ContentAdsRequest request) {
        ContentAdsBeanVO contentAdsBeanVO = new ContentAdsBeanVO();
        ContentAdsResponse response = new ContentAdsResponse();
        int count = countRecordList(request);
        response.setCount(count);
        if(count > 0){
            Paginator paginator = new Paginator(request.getCurrPage(), count,request.getPageSize()==0?10:request.getPageSize());
            List<Ads> asdList = getRecordList(request,paginator.getOffset(), paginator.getLimit());
            // 获取广告类型列表
            List<AdsType> adsTypeList = getAdsTypeList();
            List<AdsVO> recordList = CommonUtils.convertBeanList(asdList, AdsVO.class);

            for(AdsVO dto : recordList){

                for(AdsType adsType : adsTypeList){

                    if(dto.getTypeId().intValue() == adsType.getTypeId().intValue()){
                        dto.setTypeName(adsType.getTypeName());
                    }
                }
            }

            contentAdsBeanVO.setRecordList(recordList);

//            List<AdsTypeVO> towList = CommonUtils.convertBeanList(asdList, AdsTypeVO.class);
//            contentAdsBeanVO.setAdsTypeList(towList);
//            contentAdsBeanVOList.add(contentAdsBeanVO);

            response.setResult(contentAdsBeanVO);
        }

        return response;
    }

    /**
     * 获取广告类型列表
     *
     * @return
     */
    public List<AdsType> getAdsTypeList() {
        AdsTypeExample example = new AdsTypeExample();
        example.createCriteria().andClientTypeEqualTo(0);// pc端广告
        return adsTypeMapper.selectByExample(example);
    }

    /**
     * 获取广告列表
     *
     * @return
     */
    public List<Ads> getRecordList(ContentAdsRequest bean, int limitStart, int limitEnd) {
        AdsExample example = new AdsExample();
        AdsExample.Criteria criteria = example.createCriteria();
        criteria.andClientTypeEqualTo(0);// 手机端广告
        // 条件查询
        if (bean.getTypeid() != null) {
            criteria.andTypeIdEqualTo(bean.getTypeid());
        }
        if (StringUtils.isNotEmpty(bean.getName())) {
            criteria.andNameLike("%" + bean.getName() + "%");
        }
        if (bean.getStatus() != null) {
            criteria.andStatusEqualTo(bean.getStatus());
        }
        if (StringUtils.isNotEmpty(bean.getStartCreate()) && StringUtils.isNotEmpty(bean.getEndCreate())) {
            try {
                criteria.andCreateTimeGreaterThanOrEqualTo
                        (GetDate.parseDate(GetDate.getDayStart(bean.getStartCreate()),GetDate.datetimeFormat_key));
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(bean.getEndCreate())) {
            try {
                criteria.andCreateTimeLessThanOrEqualTo
                        (GetDate.parseDate(GetDate.getDayEnd(bean.getEndCreate()),GetDate.datetimeFormat_key));
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }
        }
        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        example.setOrderByClause("create_time Desc");
        return adsMapper.selectByExample(example);
    }

    /**
     * 获取列表数
     *
     * @param bean
     * @return
     * @author Michael
     */
    public Integer countRecordList(ContentAdsRequest bean) {
        AdsExample example = new AdsExample();
        AdsExample.Criteria criteria = example.createCriteria();
        criteria.andClientTypeEqualTo(0);// 手机端广告
        // 条件查询
        if (bean.getTypeid() != null) {
            criteria.andTypeIdEqualTo(bean.getTypeid());
        }
        if (StringUtils.isNotEmpty(bean.getName())) {
            criteria.andNameLike("%" + bean.getName() + "%");
        }
        if (bean.getStatus() != null) {
            criteria.andStatusEqualTo(bean.getStatus());
        }
        if (StringUtils.isNotEmpty(bean.getStartCreate()) && StringUtils.isNotEmpty(bean.getEndCreate())) {
            try {
                criteria.andCreateTimeGreaterThanOrEqualTo
                        (GetDate.parseDate(GetDate.getDayStart(bean.getStartCreate()),GetDate.datetimeFormat_key));
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(bean.getEndCreate())) {
            try {
                criteria.andCreateTimeLessThanOrEqualTo
                        (GetDate.parseDate(GetDate.getDayEnd(bean.getEndCreate()),GetDate.datetimeFormat_key));
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }
        }

        int cnt = adsMapper.countByExample(example);
        if (cnt > 0) {
            return cnt;
        }
        return 0;
    }

    @Override
    public boolean insertAction(ContentAdsRequest request) {
        Integer now = GetDate.getMyTimeInMillis();
        AdsVO vo = request.getAds();
        if(vo == null){
            return false;
        }
        Ads record = new Ads();
        BeanUtils.copyProperties(vo, record);

        if(record.getCode() == null){
            record.setCode("");
        }

        // 结束时间<当前时间
        if (GetDate.dateString2Timestamp(record.getEndTime()) <= now) {
            // 已结束
            record.setIsEnd(1);
        } else {
            // 未结束
            record.setIsEnd(0);
        }

        if(record.getIsIndex()==null){
            record.setIsIndex(new Integer("0"));
        }
        record.setClientType(0);
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        return adsMapper.insertSelective(record)>0?true:false;
    }

    @Override
    public ContentAdsResponse infoaction(Integer id) {
        ContentAdsResponse response = new ContentAdsResponse();
        Ads ads = adsMapper.selectByPrimaryKey(id);

        if(ads == null){
            response.setRtn(Response.FAIL);
            response.setMessage(Response.FAIL_MSG);
            return response;
        }

        ContentAdsBeanVO contentAdsBeanVO = new ContentAdsBeanVO();
        List<AdsVO> listAds = new ArrayList<>();
        AdsVO vo = new AdsVO();
        BeanUtils.copyProperties(ads,vo);

        listAds.add(vo);
        contentAdsBeanVO.setRecordList(listAds);
        response.setResult(contentAdsBeanVO);
        return response;
    }

    @Override
    public boolean updateAction(ContentAdsRequest request) {
        AdsVO vo = request.getAds();
        if(vo == null){
            return false;
        }
        Ads record = new Ads();
        BeanUtils.copyProperties(vo, record);

        Integer now = GetDate.getMyTimeInMillis();
        if (record.getIsIndex() == null) {
            record.setIsIndex(0);
        }
        // 结束时间<当前时间
        if (GetDate.dateString2Timestamp(record.getEndTime()) <= now) {
            // 已结束
            record.setIsEnd(1);
        } else {
            // 未结束
            record.setIsEnd(0);
        }
        record.setUpdateTime(new Date());
        return adsMapper.updateByPrimaryKeySelective(record)>0?true:false;
    }

    @Override
    public boolean deleteById(Integer id) {
        return adsMapper.deleteByPrimaryKey(id)>0?true:false;
    }
}
