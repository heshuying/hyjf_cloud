/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import com.hyjf.am.resquest.user.BankCardManagerRequest;
import com.hyjf.am.user.dao.mapper.customize.BankCardManagerCustomizeMapper;
import com.hyjf.am.user.dao.model.customize.BankcardManagerCustomize;
import com.hyjf.am.user.service.BankCardManagerRecordService;
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
public class BankCardManagerRecordServiceImpl implements BankCardManagerRecordService {

    @Autowired
    public BankCardManagerCustomizeMapper bankCardManagerCustomizeMapper;

    private static Logger logger = LoggerFactory.getLogger(BankCardManagerRecordServiceImpl.class);


    /**
     *  根据筛选条件查找汇付银行卡信息列表
     * @param request 筛选条件
     * @return
     */
    @Override
    public List<BankcardManagerCustomize> selectBankCardList(BankCardManagerRequest request) {
        //参数设置
        Map<String, Object> mapParam = paramSet(request);
        List<BankcardManagerCustomize> listBankCard = bankCardManagerCustomizeMapper.selectBankCardList(mapParam);
        return listBankCard;
    }

    /**
     * 根据条件统计汇付银行卡信息
     * @param request
     * @return
     */
    @Override
    public int countUserRecord(BankCardManagerRequest request) {
        Map<String, Object> mapParam = paramSet(request);
        Integer integerCount = bankCardManagerCustomizeMapper.countRecordTotal(mapParam);
        int intUserCount = integerCount.intValue();
        return intUserCount;
    }

    /**
     * 根据筛选条件查找江西银行卡信息列表
     * @return
     */
    @Override
    public List<BankcardManagerCustomize> selectNewBankCardList (BankCardManagerRequest request) {
        Map<String, Object> mapParam = paramSet(request);
        List<BankcardManagerCustomize> bankcardManagerCustomizesList = bankCardManagerCustomizeMapper.selectNewBankCardList(mapParam);
        return bankcardManagerCustomizesList;
    }


    /**
     *根据条件统计江西银行卡信息
     * @return
     */
    @Override
    public int countRecordTotalNew(BankCardManagerRequest request) {
        Map<String, Object> mapParam = paramSet(request);
        Integer integerCount = bankCardManagerCustomizeMapper.countRecordTotalNew(mapParam);
        int intUserCount = integerCount.intValue();
        return intUserCount;
    }

    /**
     * 查询条件设置
     *
     * @param request
     * @return
     */
    private Map<String, Object> paramSet(BankCardManagerRequest request) {
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("userName", request.getUserName());
        mapParam.put("bank", request.getBank());
        mapParam.put("account", request.getAccount());
        mapParam.put("cardProperty", request.getCardProperty());
        mapParam.put("cardType", request.getCardType());
        mapParam.put("addTimeStart", request.getAddTimeStart());
        mapParam.put("addTimeEnd", request.getAddTimeEnd());
        mapParam.put("limitStart", request.getLimitStart());
        mapParam.put("limitEnd", request.getLimitEnd());
        mapParam.put("mobile",request.getMobile());
        mapParam.put("realName",request.getRealName());
        return mapParam;
    }


}
