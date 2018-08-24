/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance;

import java.util.HashMap;
import java.util.List;

import com.hyjf.am.resquest.admin.AdminMerchantAccountRequest;
import com.hyjf.am.resquest.admin.MerchantAccountListRequest;
import com.hyjf.am.trade.dao.model.auto.MerchantAccount;
import com.hyjf.am.trade.service.BaseService;

/**
 * @author zhangqingqing
 * @version MerchantAccountService, v0.1 2018/7/5 13:43
 */
public interface MerchantAccountService extends BaseService {

    /**
     * 获取商户子账户列表
     * @return
     * @param request
     * @param offset
     * @param limit
     */
    List<MerchantAccount> selectRecordList(MerchantAccountListRequest request, int offset, int limit);

    Integer updateByPrimaryKeySelective(MerchantAccount merchantAccount);

    int countByExample();
    /*
    * 查询平台账户记录总数
    * */
    public Integer getMerchantAccountListCountByPage(AdminMerchantAccountRequest request);
    /*
    * 分页查询平台账户记录
    * */
    public List<MerchantAccount> getMerchantAccountListByPage(AdminMerchantAccountRequest request, int limitStart, int limitEnd);
    /*
    * 根据id查询平台账户记录
    * */
    public MerchantAccount selectAccountConfigInfo(Integer id);
    /**
     * 插入平台账户设置操作
     * @param adminRequest
     * @return
     */
    public int saveAccountConfig(AdminMerchantAccountRequest adminRequest);
    /**
     * 修改平台账户设置操作
     * @param adminRequest
     * @return
     */
    public int updateAccountConfig(AdminMerchantAccountRequest adminRequest);

    /**
     *
     * 根据子账户名称检索
     * @return
     */
    public int countAccountListInfoBySubAccountName(HashMap<String,String> map);
    /**
     *
     * 根据子账户代号检索
     * @return
     */
    public int countAccountListInfoBySubAccountCode(HashMap<String,String> map);
}
