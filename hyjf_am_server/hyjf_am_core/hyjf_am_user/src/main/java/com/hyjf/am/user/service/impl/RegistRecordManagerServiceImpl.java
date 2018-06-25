/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import com.hyjf.am.resquest.user.RegistRcordRequest;
import com.hyjf.am.user.dao.mapper.customize.RegistRecordCustomizeMapper;
import com.hyjf.am.user.dao.model.customize.*;
import com.hyjf.am.user.service.RegistRecordManagerService;
import com.hyjf.common.cache.CacheUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserManagerServiceImpl, v0.1 2018/6/20 9:49
 *          后台管理系统 ：会员中心->会员管理 接口实现
 */
@Service
public class RegistRecordManagerServiceImpl implements RegistRecordManagerService {

    @Autowired
    public RegistRecordCustomizeMapper registRecordCustomizeMapper;

    private static Logger logger = LoggerFactory.getLogger(RegistRecordManagerServiceImpl.class);


    /**
     * 根据筛选条件查找注册信息
     *
     * @param userRequest 筛选条件
     * @return
     */
    @Override
    public List<RegistRecordCustomize> selectRegistList(RegistRcordRequest userRequest) {
        //参数设置
        Map<String, Object> mapParam = paramSet(userRequest);
        List<RegistRecordCustomize> listRegistRecord = registRecordCustomizeMapper.selectRegistList(mapParam);
        if (CollectionUtils.isNotEmpty(listRegistRecord)) {
            //
            Map<String, String> userProperty = CacheUtil.getParamNameMap("USER_PROPERTY");
            Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
            for (RegistRecordCustomize registRecordCustomize : listRegistRecord) {
                registRecordCustomize.setUserProperty(userProperty.getOrDefault(registRecordCustomize.getUserProperty(), null));
                registRecordCustomize.setRegistPlat(client.getOrDefault(registRecordCustomize.getRegistPlat(), null));
            }
        }
        return listRegistRecord;
    }

    /**
     * 查询条件设置
     *
     * @param userRequest
     * @return
     */
    private Map<String, Object> paramSet(RegistRcordRequest userRequest) {
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("regTimeStart", userRequest.getRegTimeStart());
        mapParam.put("regTimeEnd", userRequest.getRegTimeEnd());
        mapParam.put("userName", userRequest.getUserName());
        mapParam.put("mobile", userRequest.getMobile());
        mapParam.put("recommendName", userRequest.getRecommendName());
        mapParam.put("registPlat",userRequest.getRegistPlat());
        mapParam.put("limitEnd",String.valueOf(userRequest.getLimitEnd()));
        mapParam.put("limitStart",String.valueOf(userRequest.getLimitStart()));
        return mapParam;
    }


    /**
     * 根据条件获取列表总数
     *
     * @param userRequest
     * @return
     */
    @Override
    public int countRecordTotal(RegistRcordRequest userRequest) {
        Map<String, Object> mapParam = paramSet(userRequest);
        Integer integerCount = registRecordCustomizeMapper.countRecordTotal(mapParam);
        int intUserCount = integerCount.intValue();
        return intUserCount;
    }

}
