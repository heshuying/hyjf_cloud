/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.customize.VipUpdateGradeListCustomize;

import java.util.List;
import java.util.Map;
/**
 * @author yaoyong
 * @version VipUpdateGradeListCustomizeMapper, v0.1 2018/7/4 9:51
 */
public interface VipUpdateGradeListCustomizeMapper {
    /**
     * 查询vip用户升级详情条数
     * @param mapParam
     * @return
     */
    Integer countRecordTotal(Map<String, Object> mapParam);

    /**
     * 查询vip用户升级详情列表
     * @param mapParam
     * @return
     */
    List<VipUpdateGradeListCustomize> selectRecordList(Map<String, Object> mapParam);
}
