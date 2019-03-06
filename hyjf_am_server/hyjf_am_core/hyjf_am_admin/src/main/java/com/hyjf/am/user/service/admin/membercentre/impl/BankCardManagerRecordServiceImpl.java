/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.membercentre.impl;

import com.hyjf.am.resquest.user.BankCardLogRequest;
import com.hyjf.am.user.dao.model.auto.BankCardLog;
import com.hyjf.am.user.dao.model.auto.BankCardLogExample;
import com.hyjf.am.user.dao.model.customize.BankcardManagerCustomize;
import com.hyjf.am.user.service.admin.membercentre.BankCardManagerRecordService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserManagerServiceImpl, v0.1 2018/6/20 9:49
 *          后台管理系统 ：会员中心->会员管理 接口实现
 */
@Service
public class BankCardManagerRecordServiceImpl extends BaseServiceImpl implements BankCardManagerRecordService {

    /**
     * 根据筛选条件查找汇付银行卡信息列表
     *
     * @param mapParam 筛选条件
     * @return
     */
    @Override
    public List<BankcardManagerCustomize> selectBankCardList(Map<String, Object> mapParam, int limitStart, int limitEnd) {
        //参数设置
        // 封装查询条件
        if (limitStart == 0 || limitStart > 0) {
            mapParam.put("limitStart", limitStart);
        }
        if (limitEnd == 0 || limitEnd > 0) {
            mapParam.put("limitEnd", limitEnd);
        }
//        Map<String, Object> mapParam = paramSet(request);
        List<BankcardManagerCustomize> listBankCard = bankCardManagerCustomizeMapper.selectBankCardList(mapParam);
        return listBankCard;
    }

    /**
     * 根据条件统计汇付银行卡信息
     *
     * @param mapParam
     * @return
     */
    @Override
    public int countUserRecord(Map<String, Object> mapParam) {
        Integer integerCount = bankCardManagerCustomizeMapper.countRecordTotal(mapParam);
        int intUserCount = integerCount.intValue();
        return intUserCount;
    }

    /**
     * 根据筛选条件查找江西银行卡信息列表
     *
     * @return
     */
    @Override
    public List<BankcardManagerCustomize> selectNewBankCardList(Map<String, Object> mapParam, int limitStart, int limitEnd) {
        // 封装查询条件
        if (limitStart == 0 || limitStart > 0) {
            mapParam.put("limitStart", limitStart);
        }
        if (limitEnd > 0) {
            mapParam.put("limitEnd", limitEnd);
        }
//        Map<String, Object> mapParam = paramSet(request);
        List<BankcardManagerCustomize> bankcardManagerCustomizesList = bankCardManagerCustomizeMapper.selectNewBankCardList(mapParam);
        return bankcardManagerCustomizesList;
    }


    /**
     * 根据条件统计江西银行卡信息
     *
     * @return
     */
    @Override
    public int countRecordTotalNew(Map<String, Object> mapParam) {
//        Map<String, Object> mapParam = paramSet(request);
        Integer integerCount = bankCardManagerCustomizeMapper.countRecordTotalNew(mapParam);
        int intUserCount = integerCount.intValue();
        return intUserCount;
    }

    /**
     * 根据筛选条件查找用户银行卡操作记录表
     *
     * @param request
     * @return
     */
    @Override
    public List<BankCardLog> selectBankCardLogByExample(BankCardLogRequest request, int limitStart, int limitEnd) {
        BankCardLogExample example = new BankCardLogExample();
        BankCardLogExample.Criteria criteria = example.createCriteria();
        // 条件查询
        if (StringUtils.isNotEmpty(request.getBankName())) {
//            criteria.andBankCodeEqualTo(request.getBankCode());
            String bankName =request.getBankName();
            if(request.getBankName().contains("中国")){
                bankName = request.getBankName().split("中国")[1];
            }
            criteria.andBankNameLike("%" + bankName.trim());
        }
        if (StringUtils.isNotEmpty(request.getUserName())) {
            criteria.andUserNameLike(request.getUserName() + "%");
        }
        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateStart = null;
        Date dateEnd = null;
        if (StringUtils.isNotEmpty(request.getStartTime())) {
            try {
                dateStart = smp.parse(request.getStartTime()+" 00:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            criteria.andCreateTimeGreaterThanOrEqualTo(dateStart);
        }
        if (StringUtils.isNotEmpty(request.getEndTime())) {
            try {
                dateEnd = smp.parse(request.getEndTime()+" 23:59:59");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            criteria.andCreateTimeLessThanOrEqualTo(dateEnd);
        }
        if (limitStart != -1 && limitEnd != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        example.setOrderByClause("create_time Desc");
        List<BankCardLog> listBank = bankCardLogMapper.selectByExample(example);
        return listBank;
    }

    /**
     * 根据筛选条件统计用户银行卡操作记录表
     * @param request
     * @return
     */
    @Override
    public int countBankCardLog(BankCardLogRequest request){
        BankCardLogExample example = new BankCardLogExample();
        BankCardLogExample.Criteria criteria = example.createCriteria();
        // 条件查询
        if (StringUtils.isNotEmpty(request.getBankName())) {
//            criteria.andBankCodeEqualTo(request.getBankCode());
            String bankName =request.getBankName();
            if(request.getBankName().contains("中国")){
                bankName = request.getBankName().split("中国")[1];
            }
            criteria.andBankNameLike("%" + bankName.trim());
        }
        if (StringUtils.isNotEmpty(request.getUserName())) {
            criteria.andUserNameLike(request.getUserName() + "%");
        }
        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateStart = null;
        Date dateEnd = null;
        if (StringUtils.isNotEmpty(request.getStartTime())) {
            try {
                dateStart = smp.parse(request.getStartTime()+" 00:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            criteria.andCreateTimeGreaterThanOrEqualTo(dateStart);
        }
        if (StringUtils.isNotEmpty(request.getEndTime())) {
            try {
                dateEnd = smp.parse(request.getEndTime()+" 23:59:59");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            criteria.andCreateTimeLessThanOrEqualTo(dateEnd);
        }
        int intCount = bankCardLogMapper.countByExample(example);
        return intCount;
    }

}
