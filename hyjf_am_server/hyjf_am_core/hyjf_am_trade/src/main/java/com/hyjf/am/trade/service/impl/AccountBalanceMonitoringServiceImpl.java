package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.admin.AdminAccountBalanceMonitoringRequest;
import com.hyjf.am.trade.dao.mapper.auto.MerchantAccountMapper;
import com.hyjf.am.trade.dao.model.auto.MerchantAccount;
import com.hyjf.am.trade.dao.model.auto.MerchantAccountExample;
import com.hyjf.am.trade.service.admin.AccountBalanceMonitoringService;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/13.
 */
@Service
public class AccountBalanceMonitoringServiceImpl implements AccountBalanceMonitoringService {

    @Autowired
    private MerchantAccountMapper merchantAccountMapper;

    /**
     * 分页查询平台账户配置 余额监控列表 条数
     * @param adminRequest
     * @return
     */
    @Override
    public int getAccountBalanceMonitoringCount(AdminAccountBalanceMonitoringRequest adminRequest){
        MerchantAccountExample example = new MerchantAccountExample();
        MerchantAccountExample.Criteria cra = example.createCriteria();
        // 子账户名称
        if (StringUtils.isNotEmpty(adminRequest.getSubAccountNameSear())) {
            cra.andSubAccountNameLike("%" + adminRequest.getSubAccountNameSear() + "%");
        }
        // 子账户类型
        if (StringUtils.isNotEmpty(adminRequest.getSubAccountTypeSear())) {
            cra.andSubAccountTypeEqualTo(adminRequest.getSubAccountTypeSear());
        }
        return merchantAccountMapper.countByExample(example);
    }


    /**
     * 分页查询平台账户配置 余额监控列表
     * @param form
     * @return
     */
    @Override
    public List<MerchantAccount> getAccountBalanceMonitoringByPage(AdminAccountBalanceMonitoringRequest form, int limitStart, int limitEnd){
        MerchantAccountExample example = new MerchantAccountExample();
        MerchantAccountExample.Criteria cra = example.createCriteria();
        // 子账户名称
        if (StringUtils.isNotEmpty(form.getSubAccountNameSear())) {
            cra.andSubAccountNameLike("%" + form.getSubAccountNameSear() + "%");
        }
        // 子账户类型
        if (StringUtils.isNotEmpty(form.getSubAccountTypeSear())) {
            cra.andSubAccountTypeEqualTo(form.getSubAccountTypeSear());
        }

        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }

        example.setOrderByClause("`update_time` DESC, `id` ASC");

        return merchantAccountMapper.selectByExample(example);
    }
    /**
     * 平台账户配置 余额监控详情页面
     * @param adminRequest
     * @return
     */
    @Override
    public List<MerchantAccount> selectAccountBalanceMonitoringById(AdminAccountBalanceMonitoringRequest adminRequest){
        MerchantAccountExample example = new MerchantAccountExample();
        example.setOrderByClause("`update_time` DESC , `id` ASC");
        return merchantAccountMapper.selectByExample(example);
    }

    /**
     * 分页查询平台账户配置 余额监控列表 条数
     * @param updateList
     * @return
     */
    @Override
    public int updateMerchantAccountList(List<AdminAccountBalanceMonitoringRequest> updateList){
        int ret = 0;
        int nowTime = GetDate.getNowTime10();
        if (updateList != null && updateList.size() > 0) {
            for (int i = 0; i < updateList.size(); i++) {
                AdminAccountBalanceMonitoringRequest record = updateList.get(i);
                MerchantAccount merchantAccount = new MerchantAccount();
                // 如果数据有更新
                if (record.isUpdateFlg()) {
                    BeanUtils.copyProperties(record, merchantAccount);
                    merchantAccount.setUpdateTime(nowTime);
                    merchantAccount.setBalanceLowerLimit(merchantAccount.getBalanceLowerLimit() == null ? 0
                            : merchantAccount.getBalanceLowerLimit());
                    merchantAccount.setTransferIntoRatio(merchantAccount.getTransferIntoRatio() == null ? 0
                            : merchantAccount.getTransferIntoRatio());
                    ret += this.merchantAccountMapper.updateByPrimaryKeySelective(merchantAccount);
                }
            }
        }
        return ret;
    }


}
