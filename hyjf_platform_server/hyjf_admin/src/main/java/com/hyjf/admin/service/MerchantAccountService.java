/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.MerchantAccountResponse;
import com.hyjf.am.resquest.admin.AdminMerchantAccountRequest;
import com.hyjf.am.resquest.admin.MerchantAccountListRequest;
import com.hyjf.am.vo.admin.MerchantAccountVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author zhangqingqing
 * @version MerchantAccountService, v0.1 2018/7/5 10:24
 */
public interface MerchantAccountService {
    /**
     * 更新商户子账户的金额信息
     * @param merchantAccounts
     * @return
     */
    boolean updateMerchantAccount( List<MerchantAccountVO> merchantAccounts);

    /**
     * 查询商户配置表相应的账户配置
     * @return
     */
    MerchantAccountResponse selectRecordList(MerchantAccountListRequest request);

    /**
     * 分页查询账户平台设置列表
     * @return
     */
    MerchantAccountResponse selectMerchantAccountListByPage(AdminMerchantAccountRequest request);
    /**
     * 根据id查询账户平台设置
     * @return
     */
//    MerchantAccountResponse searchAccountConfigInfo(AdminMerchantAccountRequest request);
    MerchantAccountResponse searchAccountConfigInfo(Integer id);
    /**
     * 添加 账户平台设置
     * @return
     */
    MerchantAccountResponse saveAccountConfig(AdminMerchantAccountRequest request);
    /**
     * 修改账户平台设置
     * @return
     */
    MerchantAccountResponse updateAccountConfig(AdminMerchantAccountRequest request);
    /**
     * 子账户类型
     * @return
     */
    List<ParamNameVO> getParamNameList(String code);
    /**
     *
     * 根据子账户名称检索
     * @param subAccountName
     * @return
     */
    public int countAccountListInfoBySubAccountName(String ids, String subAccountName);

    /**
     *
     * 根据子账户代号检索
     * @param subAccountCode
     * @return
     */
    public int countAccountListInfoBySubAccountCode(String ids, String subAccountCode);
}
