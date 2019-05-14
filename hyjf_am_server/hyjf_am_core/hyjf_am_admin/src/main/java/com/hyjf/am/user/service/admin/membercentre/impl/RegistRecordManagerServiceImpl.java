/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.membercentre.impl;

import com.hyjf.am.user.dao.model.customize.RegistRecordCustomize;
import com.hyjf.am.user.service.admin.membercentre.RegistRecordManagerService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.common.cache.CacheUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserManagerServiceImpl, v0.1 2018/6/20 9:49
 *          后台管理系统 ：会员中心->会员管理 接口实现
 */
@Service
public class RegistRecordManagerServiceImpl extends BaseServiceImpl implements RegistRecordManagerService {

    /**
     * 根据筛选条件查找注册信息
     *
     * @param mapParam 筛选条件
     * @return
     */
    @Override
    public List<RegistRecordCustomize> selectRegistList(Map<String, Object> mapParam,int limitStart, int limitEnd) {
        // 封装查询条件
        if (limitStart == 0 || limitStart > 0) {
            mapParam.put("limitStart", limitStart);
        }
        if (limitEnd > 0) {
            mapParam.put("limitEnd", limitEnd);
        }
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
     * 根据条件获取列表总数
     *
     * @param mapParam
     * @return
     */
    @Override
    public int countRecordTotal(Map<String, Object> mapParam ) {
        Integer integerCount = registRecordCustomizeMapper.countRecordTotal(mapParam);
        int intUserCount = integerCount.intValue();
        return intUserCount;
    }


    /**
     * 根据筛选条件查找注册信息
     *
     * @param userId 筛选条件
     * @return
     */
    @Override
    public RegistRecordCustomize selectRegistOne(Integer userId) {
        RegistRecordCustomize listRegistRecord = registRecordCustomizeMapper.selectRegistOne(userId);
        if (listRegistRecord!=null) {
            Map<String, String> userProperty = CacheUtil.getParamNameMap("USER_PROPERTY");
            Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
            listRegistRecord.setUserProperty(userProperty.getOrDefault(listRegistRecord.getUserProperty(), null));
            listRegistRecord.setRegistPlat(client.getOrDefault(listRegistRecord.getRegistPlat(), null));
            listRegistRecord.setRegistPlatCode(listRegistRecord.getRegistPlat());
        }
        return listRegistRecord;
    }
}
