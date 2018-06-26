/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service;

import com.hyjf.am.resquest.user.BankCardManagerRequest;
import com.hyjf.am.user.dao.model.customize.BankcardManagerCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserManagerService, v0.1 2018/6/20 9:47
 *          后台管理系统：会员中心->银行卡管理
 */
public interface BankCardManagerRecordService {


    /**
     * 根据筛选条件查找汇付银行卡信息列表
     *
     * @param request 筛选条件
     * @return
     */
    List<BankcardManagerCustomize> selectBankCardList(Map<String,Object> mapParam,int limitStart, int limitEnd );

    /**
     * 根据条件统计汇付银行卡信息
     *
     * @param request
     * @return
     */
    int countUserRecord(Map<String,Object> mapParam);

    /**
     * 根据筛选条件查找江西银行卡信息列表
     *
     * @return
     */
    List<BankcardManagerCustomize> selectNewBankCardList(Map<String,Object> mapParam,int limitStart, int limitEnd);

    /**
     * 根据条件统计江西银行卡信息
     *
     * @return
     */
    int countRecordTotalNew(Map<String,Object> mapParam);
}
