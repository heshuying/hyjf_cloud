package com.hyjf.am.user.dao.mapper.customize.hgreportdata.bifa;

import com.hyjf.am.user.dao.model.bifa.BifaIndexUserInfoBean;

import java.util.List;

public interface BifaUserCustomizeMapper {

    /**
     * 获取最近七天开户的用户
     * @param startDate
     * @param endDate
     * @return
     */
    List<BifaIndexUserInfoBean> getBankOpenedAccountUsers(Integer startDate, Integer endDate);

    /**
     * 获取借款人信息
     * @param userId
     * @return
     */
    BifaIndexUserInfoBean selectUserCorpInfoByUserId(Integer userId);
}
