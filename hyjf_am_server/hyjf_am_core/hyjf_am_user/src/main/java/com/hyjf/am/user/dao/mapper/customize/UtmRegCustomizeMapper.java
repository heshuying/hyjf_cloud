/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.customize.crm.UserCrmInfoCustomize;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author sunss
 * @Date 2018/6/23 11:08
 */
public interface UtmRegCustomizeMapper {

    /**
     * 更新huiyingdai_utm_reg相应的用户信息
     *
     * @param params
     */
    void updateFirstUtmReg(Map<String, Object> params);
}
