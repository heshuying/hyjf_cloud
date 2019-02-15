package com.hyjf.am.user.dao.mapper.customize.hgreportdata.bifa;

import com.hyjf.am.user.dao.model.bifa.BifaIndexUserInfoBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BifaUserCustomizeMapper {

    /**
     * 获取最近七天开户的用户
     * @param startDate
     * @param endDate
     * @return
     */
    List<BifaIndexUserInfoBean> getBankOpenedAccountUsers(@Param("startDate") Integer startDate,@Param("endDate") Integer endDate);

    /**
     * 获取借款人信息
     * @param userId
     * @return
     */
    BifaIndexUserInfoBean selectUserCorpInfoByUserId(Integer userId);
}
