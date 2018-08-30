/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance.impl;

import com.hyjf.am.resquest.admin.AdminMerchantAccountRequest;
import com.hyjf.am.resquest.admin.MerchantAccountListRequest;
import com.hyjf.am.trade.dao.model.auto.MerchantAccount;
import com.hyjf.am.trade.dao.model.auto.MerchantAccountExample;
import com.hyjf.am.trade.service.admin.finance.MerchantAccountService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author zhangqingqing
 * @version MerchantAccountServiceImpl, v0.1 2018/7/5 13:44
 */
@Service
public class MerchantAccountServiceImpl extends BaseServiceImpl implements MerchantAccountService {

    /**
     * 获取商户子账户列表
     * @return
     * @param request
     * @param offset
     * @param limit
     */
    @Override
    public List<MerchantAccount> selectRecordList(MerchantAccountListRequest request, int offset, int limit) {
        MerchantAccountExample example = new MerchantAccountExample();
        example.setLimitStart(offset);
        example.setLimitEnd(limit);
        example.setOrderByClause("sort ASC");
        return merchantAccountMapper.selectByExample(example);
    }

    @Override
    public Integer updateByPrimaryKeySelective(MerchantAccount merchantAccount) {
        int cnt = merchantAccountMapper.updateByPrimaryKeySelective(merchantAccount);
        return cnt;
    }

    @Override
    public int countByExample(){
        MerchantAccountExample example = new MerchantAccountExample();
        return merchantAccountMapper.countByExample(example);
    }
    /*
       * 查询平台账户记录总数
       * */
    @Override
    public Integer getMerchantAccountListCountByPage(AdminMerchantAccountRequest form){
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
        return merchantAccountMapper.countByExample(example);
    }
    /*
    * 分页查询平台账户记录
    * */
    @Override
    public List<MerchantAccount> getMerchantAccountListByPage(AdminMerchantAccountRequest request, int limitStart, int limitEnd){
        MerchantAccountExample example = new MerchantAccountExample();
        MerchantAccountExample.Criteria cra = example.createCriteria();
        // 子账户名称
        if (StringUtils.isNotEmpty(request.getSubAccountNameSear())) {
            cra.andSubAccountNameLike("%" + request.getSubAccountNameSear() + "%");
        }
        // 子账户类型
        if (StringUtils.isNotEmpty(request.getSubAccountTypeSear())) {
            cra.andSubAccountTypeEqualTo(request.getSubAccountTypeSear());
        }
        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }

        example.setOrderByClause("`update_time` DESC , `id` ASC ");

        return merchantAccountMapper.selectByExample(example);
    }
    /*
    * 根据id查询平台账户记录
    * */
    @Override
    public MerchantAccount selectAccountConfigInfo(Integer id){
        return merchantAccountMapper.selectByPrimaryKey(id);
    }
    /**
     * 插入平台账户设置操作
     * @param adminRequest
     * @return
     */
    @Override
    public int saveAccountConfig(AdminMerchantAccountRequest adminRequest){
        MerchantAccount record = new MerchantAccount();
        BeanUtils.copyProperties(adminRequest, record);
        int nowTime = GetDate.getNowTime10();
        record.setCreateTime(nowTime);
        record.setUpdateTime(nowTime);
        return merchantAccountMapper.insertSelective(record);
    }
    /**
     * 修改平台账户设置操作
     * @param adminRequest
     * @return
     */
    @Override
    public int updateAccountConfig(AdminMerchantAccountRequest adminRequest){
        MerchantAccount record = new MerchantAccount();
        BeanUtils.copyProperties(adminRequest, record);
        int nowTime = GetDate.getNowTime10();
        record.setUpdateTime(nowTime);
        if (StringUtils.isNotEmpty(adminRequest.getIds())) {
            record.setId(Integer.parseInt(adminRequest.getIds()));
        }
        return merchantAccountMapper.updateByPrimaryKeySelective(record);
    }
    /**
     *
     * 根据子账户名称检索
     * @return
     */
    @Override
    public int countAccountListInfoBySubAccountName(HashMap<String,String> map){
        MerchantAccountExample example = new MerchantAccountExample();
        MerchantAccountExample.Criteria cra = example.createCriteria();
        cra.andSubAccountNameEqualTo(map.get("subAccountName"));
        if (StringUtils.isNotEmpty(map.get("ids"))) {
            cra.andIdNotEqualTo(Integer.parseInt(map.get("ids")));
        }
        return merchantAccountMapper.countByExample(example);
    }
    /**
     *
     * 根据子账户代号检索
     * @return
     */
    @Override
    public int countAccountListInfoBySubAccountCode(HashMap<String,String> map){
        MerchantAccountExample example = new MerchantAccountExample();
        MerchantAccountExample.Criteria cra = example.createCriteria();
        cra.andSubAccountCodeEqualTo(map.get("subAccountCode") );
        if (StringUtils.isNotEmpty(map.get("ids"))) {
            cra.andIdNotEqualTo(Integer.parseInt(map.get("ids")));
        }
        return merchantAccountMapper.countByExample(example);
    }
}
