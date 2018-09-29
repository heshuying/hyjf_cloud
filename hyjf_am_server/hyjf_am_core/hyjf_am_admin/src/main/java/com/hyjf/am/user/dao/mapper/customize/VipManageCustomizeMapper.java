/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.customize.VipManageListCustomize;
import com.hyjf.am.vo.user.VipAuthVO;

import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version VipManageCustomizeMapper, v0.1 2018/7/2 18:08
 */
public interface VipManageCustomizeMapper {

    /**
     * 根据条件查询vip用户条数
     * @param mapParam
     * @return
     */
    Integer countRecord(Map<String, Object> mapParam);

    /**
     * 查询vip用户列表
     * @param mapParam
     * @return
     */
    List<VipManageListCustomize> selectUserList(Map<String, Object> mapParam);

    /**
     * @Author walter.limeng
     * @Description  根据vipID获取所有vipauth对象
     * @Date 17:03 2018/7/16
     * @Param vipId
     * @return
     */
    List<VipAuthVO> getVipAuthList(Map<String, Object> mapParam);
}
