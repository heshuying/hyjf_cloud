/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.customize.UserCrmInfoCustomize;
import com.hyjf.am.vo.market.SellDailyVO;

/**
 * @Description 用户CRM信息查询
 * @Date 2018/6/21 17:34
 */
public interface UserCrmInfoCustomizeMapper {

    List<UserCrmInfoCustomize> findUserCrmInfoByUserId(@Param("userId")Integer userId);

    /**
     *根据一级部门查询二级部门
     * @param primaryDivision
     * @return
     */
    List<String> selectTwoDivisionByPrimaryDivision(String primaryDivision);
}
