/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.service.AccountExceptionService;
import com.hyjf.am.resquest.admin.AccountExceptionRequest;
import com.hyjf.am.vo.admin.AccountExceptionVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.chinapnr.ChinapnrBean;
import com.hyjf.pay.lib.chinapnr.util.ChinapnrUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: AccountExceptionServiceImpl, v0.1 2018/7/11 15:14
 */
@Service
public class AccountExceptionServiceImpl extends BaseAdminServiceImpl implements AccountExceptionService {

    @Value("${hyjf.chinapnr.mercustid}")
    private String PROP_MERCUSTID;

    /**
     * 根据筛选条件查询汇付对账count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public Integer getAccountExceptionCount(AccountExceptionRequest request) {
        return amTradeClient.getAccountExceptionCount(request);
    }

    /**
     * 根据筛选条件查询汇付对账列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public List<AccountExceptionVO> searchAccountExceptionList(AccountExceptionRequest request) {
        return amTradeClient.searchAccountExceptionList(request);
    }

    /**
     * 根据id更新账户信息
     * @auth sunpeikai
     * @param id 主键
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncAccount(Integer id) {
        AccountExceptionVO accountExceptionVO = amTradeClient.searchAccountExceptionById(id);
        //获取账户实时余额信息
        List<AccountVO> accountVOList = amTradeClient.searchAccountByUserId(accountExceptionVO.getUserId());
        AccountVO account = null;
        if(!accountVOList.isEmpty()){
            account = accountVOList.get(0);
        }
        ChinapnrBean bean = new ChinapnrBean();
        bean.setVersion("10");
        bean.setCmdId("QueryBalanceBg");
        bean.setMerCustId(PROP_MERCUSTID);
        bean.setUsrCustId(String.valueOf(accountExceptionVO.getCustomId()));
        ChinapnrBean result = ChinapnrUtil.callApiBg(bean);
        if (result != null && StringUtils.equals("000", result.RespCode)){
            // 可用余额或冻结余额不一致的话更新account_exception表
            String balance_huifu = result.getAvlBal() == null ? "0.00" : result.getAvlBal();
            String frz_huifu = result.getFrzBal()== null ? "0.00" : result.getFrzBal();
            balance_huifu = StringUtils.replace(balance_huifu,",",StringUtils.EMPTY);
            frz_huifu = StringUtils.replace(frz_huifu,",",StringUtils.EMPTY);
            BigDecimal balance = Validator.isNotNull(account.getBalance()) ? account.getBalance() : BigDecimal.ZERO;
            BigDecimal planBalance = Validator.isNotNull(account.getPlanBalance()) ? account.getPlanBalance() : BigDecimal.ZERO;
            BigDecimal frost = Validator.isNotNull(account.getFrost()) ? account.getFrost() : BigDecimal.ZERO;
            BigDecimal planFrost = Validator.isNotNull(account.getPlanFrost()) ? account.getPlanFrost() : BigDecimal.ZERO;
            BigDecimal planBalanceHF = new BigDecimal(balance_huifu);
            BigDecimal planFrostHF = new BigDecimal(frz_huifu);
            if((planBalanceHF.compareTo(balance.add(planBalance))!=0) || (planFrostHF.compareTo(frost.add(planFrost))!=0)){
                accountExceptionVO.setBalancePlat(balance.add(planBalance));
                accountExceptionVO.setFrostPlat(frost.add(planFrost));
                accountExceptionVO.setBalanceHuifu(new BigDecimal(balance_huifu));
                accountExceptionVO.setFrostHuifu(new BigDecimal(frz_huifu));
                amTradeClient.updateAccountException(accountExceptionVO);
            }else{
                //可用余额或冻结余额一致的话删除异常数据
                amTradeClient.deleteAccountExceptionById(id);
            }
        }
    }
}
