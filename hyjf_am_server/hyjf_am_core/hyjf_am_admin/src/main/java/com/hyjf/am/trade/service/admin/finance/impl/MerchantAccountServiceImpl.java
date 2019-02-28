/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance.impl;

import com.hyjf.am.resquest.admin.AdminMerchantAccountRequest;
import com.hyjf.am.resquest.admin.MerchantAccountListRequest;
import com.hyjf.am.resquest.admin.MerchantTransferListRequest;
import com.hyjf.am.trade.dao.model.auto.MerchantAccount;
import com.hyjf.am.trade.dao.model.auto.MerchantAccountExample;
import com.hyjf.am.trade.dao.model.auto.MerchantTransfer;
import com.hyjf.am.trade.dao.model.auto.MerchantTransferExample;
import com.hyjf.am.trade.service.admin.finance.MerchantAccountService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
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
            cra.andSubAccountNameLike(form.getSubAccountNameSear() + "%");
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
            cra.andSubAccountNameLike( request.getSubAccountNameSear() + "%");
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
//        record.setCreateTime(nowTime);
//        record.setUpdateTime(nowTime);
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
//        record.setUpdateTime(nowTime);
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

    @Override
    public List<MerchantAccount> selectMerchantAccountList(Integer status) {
        MerchantAccountExample example = new MerchantAccountExample();
        if(Validator.isNotNull(status)){
            //查询转出账户列表
            if(status.intValue() == 0){
                example.createCriteria().andTransferOutFlgEqualTo(1);
            }else{//查询转入账户列表
                example.createCriteria().andTransferIntoFlgEqualTo(1);
            }
        }
        example.setOrderByClause("sort ASC");
        return this.merchantAccountMapper.selectByExample(example);
    }

    @Override
    public int queryRecordTotal(MerchantTransferListRequest form) {
        MerchantTransferExample example = new MerchantTransferExample();
        MerchantTransferExample.Criteria cra = example.createCriteria();
        if(StringUtils.isNotEmpty(form.getOrderId())){
            cra.andOrderIdLike(form.getOrderId()+"%");
        }
        if(StringUtils.isNotEmpty(form.getCreateUserName())){
            cra.andCreateUserNameLike(form.getCreateUserName()+"%");
        }
        if(Validator.isNotNull(form.getOutAccountId())){
            cra.andOutAccountIdEqualTo(form.getOutAccountId());
        }
        if(Validator.isNotNull(form.getInAccountId())){
            cra.andInAccountIdEqualTo(form.getInAccountId());
        }
        if(Validator.isNotNull(form.getStatus())){
            cra.andStatusEqualTo(form.getStatus());
        }
        if(StringUtils.isNotEmpty(form.getTransferTimeStart())){
            cra.andTransferTimeGreaterThanOrEqualTo(GetDate.stringToDate(GetDate.getDayStart(form.getTransferTimeStart())));
        }
        if(StringUtils.isNotEmpty(form.getTransferTimeEnd())){
            cra.andTransferTimeLessThanOrEqualTo(GetDate.stringToDate(GetDate.getDayEnd(form.getTransferTimeEnd())));
        }
        if(Validator.isNotNull(form.getTransferType())){
            cra.andTransferTypeEqualTo(form.getTransferType());
        }
        return merchantTransferMapper.countByExample(example);
    }

    @Override
    public List<MerchantTransfer> selectMerchantTransfer(MerchantTransferListRequest form, int offset, int limit) {
        MerchantTransferExample example = new MerchantTransferExample();
        MerchantTransferExample.Criteria cra = example.createCriteria();
        if(StringUtils.isNotEmpty(form.getOrderId())){
            cra.andOrderIdLike(form.getOrderId()+"%");
        }
        if(StringUtils.isNotEmpty(form.getCreateUserName())){
            cra.andCreateUserNameLike(form.getCreateUserName()+"%");
        }
        if(Validator.isNotNull(form.getOutAccountId())){
            cra.andOutAccountIdEqualTo(form.getOutAccountId());
        }
        if(Validator.isNotNull(form.getInAccountId())){
            cra.andInAccountIdEqualTo(form.getInAccountId());
        }
        if(Validator.isNotNull(form.getStatus())){
            cra.andStatusEqualTo(form.getStatus());
        }
        if(StringUtils.isNotEmpty(form.getTransferTimeStart())){
            cra.andTransferTimeGreaterThanOrEqualTo(GetDate.stringToDate(GetDate.getDayStart(form.getTransferTimeStart())));
        }
        if(StringUtils.isNotEmpty(form.getTransferTimeEnd())){
            cra.andTransferTimeLessThanOrEqualTo(GetDate.stringToDate(GetDate.getDayEnd(form.getTransferTimeEnd())));
        }
        if(Validator.isNotNull(form.getTransferType())){
            cra.andTransferTypeEqualTo(form.getTransferType());
        }
        example.setOrderByClause("transfer_time desc");
        if (offset != -1) {
            example.setLimitStart(offset);
            example.setLimitEnd(limit);
        }
        return merchantTransferMapper.selectByExample(example);
    }

    @Override
    public MerchantAccount selectMerchantAccountById(Integer id) {
        return merchantAccountMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean insertTransfer(MerchantTransferListRequest form) {
        Date nowTime = new Date();
        MerchantTransfer merchantTransfer = new MerchantTransfer();
        merchantTransfer.setOrderId(form.getOrderId());
        merchantTransfer.setOutAccountId(form.getOutAccountId());
        merchantTransfer.setOutAccountCode(form.getOutAccountCode());
        merchantTransfer.setOutAccountName(form.getOutAccountName());
        merchantTransfer.setInAccountId(form.getInAccountId());
        merchantTransfer.setInAccountCode(form.getInAccountCode());
        merchantTransfer.setInAccountName(form.getInAccountName());
        merchantTransfer.setTransferAmount(form.getTransferAmount());
        merchantTransfer.setTransferType(0);
        merchantTransfer.setStatus(0);
        merchantTransfer.setRemark(form.getRemark());
        merchantTransfer.setCreateUserId(form.getCreateUserId());
        merchantTransfer.setCreateTime(nowTime);
        merchantTransfer.setCreateUserName(form.getCreateUserName());
        try {
            boolean flag = this.merchantTransferMapper.insertSelective(merchantTransfer) > 0 ? true : false;
            if (flag) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int updateMerchantTransfer(String orderId, Integer status, String message) {
        Date nowTime = new Date();
        MerchantTransferExample transferExample = new MerchantTransferExample();
        MerchantTransferExample.Criteria crt = transferExample.createCriteria();
        crt.andOrderIdEqualTo(orderId);
        MerchantTransfer merchantTransfer = new MerchantTransfer();
        merchantTransfer.setStatus(status);
        merchantTransfer.setUpdateTime(nowTime);
        merchantTransfer.setTransferTime(nowTime);
        if(StringUtils.isNotBlank(message)){
            merchantTransfer.setMessage(message);
        }
        return merchantTransferMapper.updateByExampleSelective(merchantTransfer, transferExample);
    }
}
