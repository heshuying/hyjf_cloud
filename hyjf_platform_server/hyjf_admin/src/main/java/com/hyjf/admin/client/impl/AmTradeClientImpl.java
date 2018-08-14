/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.*;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.admin.AccountRechargeResponse;
import com.hyjf.am.response.admin.CouponUserCustomizeResponse;
import com.hyjf.am.response.admin.HjhPlanDetailResponse;
import com.hyjf.am.response.admin.HjhPlanResponse;
import com.hyjf.am.response.config.ParamNameResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.trade.HjhRepayResponse;
import com.hyjf.am.response.trade.account.AccountListResponse;
import com.hyjf.am.response.trade.account.AccountResponse;
import com.hyjf.am.response.trade.account.AccountTradeResponse;
import com.hyjf.am.response.trade.account.AccountWithdrawResponse;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.resquest.trade.*;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.vo.admin.*;
import com.hyjf.am.vo.admin.BorrowCreditVO;
import com.hyjf.am.vo.admin.TenderCommissionVO;
import com.hyjf.am.vo.admin.coupon.CouponBackMoneyCustomize;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.admin.coupon.DataCenterCouponCustomizeVO;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.admin.AccountRechargeVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.hjh.*;
import com.hyjf.am.vo.trade.repay.BankRepayFreezeLogVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AmTradeClientImpl, v0.1 2018/7/5 10:48
 */
@Service
public class AmTradeClientImpl implements AmTradeClient {
    private static Logger logger = LoggerFactory.getLogger(AmTradeClientImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${am.trade.service.name}")
    private String tradeService;

    @Override
    public Integer updateByPrimaryKeySelective(MerchantAccountVO merchantAccount) {
        int cnt = restTemplate.postForEntity(tradeService + "/merchantAccount/updateByPrimaryKeySelective", merchantAccount, Integer.class).getBody();
        return cnt;
    }

    @Override
    public MerchantAccountResponse selectRecordList(MerchantAccountListRequest request) {
        MerchantAccountResponse response = restTemplate
                .postForEntity(tradeService + "/merchantAccount/selectRecordList", request, MerchantAccountResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 查询定向转账列表count
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer getDirectionalTransferCount(DirectionalTransferListRequest request) {
        Integer count = restTemplate
                .postForEntity(tradeService + "/accountdirectionaltransfer/getdirectionaltransfercount", request, Integer.class)
                .getBody();

        return count;
    }

    /**
     * 根据筛选条件查询list
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<AccountDirectionalTransferVO> searchDirectionalTransferList(DirectionalTransferListRequest request) {
        AccountDirectionalTransferResponse response = restTemplate
                .postForEntity(tradeService + "/accountdirectionaltransfer/searchdirectionaltransferlist", request, AccountDirectionalTransferResponse.class)
                .getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据筛选条件查询绑定日志count
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer getBindLogCount(BindLogListRequest request) {
        Integer count = restTemplate
                .postForEntity(tradeService + "/associatedlog/getbindlogcount", request, Integer.class)
                .getBody();

        return count;
    }

    /**
     * 根据筛选条件查询绑定日志list
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<BindLogVO> searchBindLogList(BindLogListRequest request) {
        BindLogResponse response = restTemplate
                .postForEntity(tradeService + "/associatedlog/searchbindloglist", request, BindLogResponse.class)
                .getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据userId查询Account列表，按理说只能取出来一个Account，但是service需要做个数判断，填写不同的msg，所以返回List
     *
     * @param userId 用户id
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<AccountVO> searchAccountByUserId(Integer userId) {
        AccountResponse response = restTemplate
                .getForEntity(tradeService + "/customertransfer/searchaccountbyuserid/" + userId, AccountResponse.class)
                .getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 向数据库的ht_user_transfer表中插入数据
     *
     * @param request 用户转账-发起转账的参数
     * @return
     * @auth sunpeikai
     */
    @Override
    public Boolean insertUserTransfer(CustomerTransferRequest request) {
        String url = tradeService + "/customertransfer/insertusertransfer";
        Boolean response = restTemplate.postForEntity(url, request, Boolean.class).getBody();
        return response;
    }

    /**
     * 根据筛选条件查询ht_user_transfer的数据总数
     *
     * @param request 筛选条件
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer getUserTransferCount(CustomerTransferListRequest request) {
        Integer count = restTemplate
                .postForEntity(tradeService + "/customertransfer/getusertransfercount", request, Integer.class)
                .getBody();
        return count;
    }

    /**
     * 根据筛选条件查询UserTransfer列表
     *
     * @param request 筛选条件
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<UserTransferVO> searchUserTransferList(CustomerTransferListRequest request) {
        UserTransferResponse response = restTemplate
                .postForEntity(tradeService + "/customertransfer/searchusertransferlist", request, UserTransferResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据transferId查询UserTransfer
     *
     * @param transferId ht_user_transfer表的主键id
     * @return
     * @auth sunpeikai
     */
    @Override
    public UserTransferVO searchUserTransferById(Integer transferId) {
        String url = tradeService + "/customertransfer/searchusertransferbyid/" + transferId;
        UserTransferResponse response = restTemplate.getForEntity(url, UserTransferResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }


    @Override
    public List<AccountTradeVO> selectTradeTypes() {
        String url = tradeService + "/accountTrade/selectTradeTypes";
        AccountTradeResponse response = restTemplate.getForEntity(url, AccountTradeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询汇计划转让列表
     *
     * @param request
     * @return
     */
    @Override
    public HjhDebtCreditReponse queryHjhDebtCreditList(HjhDebtCreditListRequest request) {

        HjhDebtCreditReponse response = restTemplate.
                postForEntity(tradeService + "/adminHjhDebtCredit/getList", request, HjhDebtCreditReponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public BatchBorrowRecoverReponse getBatchBorrowRecoverList(BatchBorrowRecoverRequest request) {
        BatchBorrowRecoverReponse response = restTemplate.
                postForEntity(tradeService + "/adminBatchBorrowRecover/getList", request, BatchBorrowRecoverReponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public List<AdminCouponRepayMonitorCustomizeVO> selectRecordList(CouponRepayRequest form) {
        String url = tradeService + "/couponRepayMonitor/selectCouponRepayMonitorPage";
        AdminCouponRepayMonitorCustomizeResponse response = restTemplate.postForEntity(url, form, AdminCouponRepayMonitorCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public AdminCouponRepayMonitorCustomizeResponse couponRepayMonitorCreatePage(CouponRepayRequest form) {
        String url = tradeService + "/couponRepayMonitor/CouponRepayMonitorCreatePage";
        AdminCouponRepayMonitorCustomizeResponse response = restTemplate.postForEntity(url, form, AdminCouponRepayMonitorCustomizeResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }


    @Override
    public List<AdminCouponRepayMonitorCustomizeVO> selectInterestSum(CouponRepayRequest form) {
        String url = tradeService + "/couponRepayMonitor/selectInterestSum";
        AdminCouponRepayMonitorCustomizeResponse response = restTemplate.postForEntity(url, form, AdminCouponRepayMonitorCustomizeResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public UserTransferResponse getRecordList(TransferListRequest form) {
        UserTransferResponse response = restTemplate
                .postForEntity(tradeService + "/customertransfer/getRecordList", form, UserTransferResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response;
        }
        return null;
    }

    /**
     * 根据筛选条件查询平台转账count
     *
     * @param request 筛选条件
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer getPlatformTransferCount(PlatformTransferListRequest request) {
        Integer count = restTemplate.postForEntity(tradeService + "/platformtransfer/getplatformtransfercount", request, Integer.class).getBody();
        return count;
    }

    /**
     * 根据筛选条件查询平台转账list
     *
     * @param request 筛选条件
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<AccountRechargeVO> searchPlatformTransferList(PlatformTransferListRequest request) {
        PlatformTransferResponse response = restTemplate
                .postForEntity(tradeService + "/platformtransfer/searchplatformtransferlist", request, PlatformTransferResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取项目类型list,用于筛选条件展示
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<BorrowProjectTypeVO> selectBorrowProjectList() {
        String url = "http://AM-TRADE/am-trade/borrow_regist_exception/select_borrow_project";
        BorrowProjectTypeResponse response = restTemplate.getForEntity(url, BorrowProjectTypeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取还款方式list,用于筛选条件展示
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<BorrowStyleVO> selectBorrowStyleList() {
        String url = "http://AM-TRADE/am-trade/borrow_regist_exception/select_borrow_style";
        BorrowStyleResponse response = restTemplate.getForEntity(url, BorrowStyleResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取标的列表count,用于前端分页显示条数
     *
     * @param borrowRegistListRequest 筛选条件
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer getRegistCount(BorrowRegistListRequest borrowRegistListRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_regist_exception/get_regist_count";
        return restTemplate.postForEntity(url, borrowRegistListRequest, Integer.class).getBody();
    }

    /**
     * 获取标的备案异常列表
     *
     * @param borrowRegistListRequest 筛选条件
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<BorrowRegistCustomizeVO> selectBorrowRegistList(BorrowRegistListRequest borrowRegistListRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_regist_exception/select_borrow_regist_list";
        BorrowRegistCustomizeResponse response = restTemplate.postForEntity(url, borrowRegistListRequest, BorrowRegistCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据borrowNid查询出来异常标
     *
     * @param borrowNid 借款编号
     * @return
     * @auth sunpeikai
     */
    @Override
    public BorrowVO searchBorrowByBorrowNid(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrow_regist_exception/search_borrow_by_borrownid/" + borrowNid;
        BorrowResponse response = restTemplate.getForEntity(url, BorrowResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }


    /**
     * 根据受托支付userId查询stAccountId
     *
     * @param entrustedUserId 受托支付userId
     * @return stAccountId
     * @auth sunpeikai
     */
    @Override
    public String getStAccountIdByEntrustedUserId(Integer entrustedUserId) {
        String url = "http://AM-TRADE/am-trade/borrow_regist_exception/get_staccountid_by_entrusteduserid/" + entrustedUserId;
        String response = restTemplate.getForEntity(url, String.class).getBody();
        return response;
    }

    /**
     * 更新标
     *
     * @param borrowVO 标信息
     * @param type     1更新标的备案 2更新受托支付标的备案
     * @return
     * @auth sunpeikai
     */
    @Override
    public boolean updateBorrowRegist(BorrowVO borrowVO, Integer type) {
        String url = "http://AM-TRADE/am-trade/borrow_regist_exception/update_borrowregist_by_type/" + type;
        Boolean response = restTemplate.postForEntity(url, borrowVO, Boolean.class).getBody();
        return response;
    }

    /**
     * 备案成功看标的是否关联计划，如果关联则更新对应资产表
     *
     * @param borrowVO 标信息
     * @return
     * @auth sunpeikai
     */
    @Override
    public boolean updateBorrowAsset(BorrowVO borrowVO, Integer status) {
        String url = "http://AM-TRADE/am-trade/borrow_regist_exception/update_borrowasset/" + status;
        Boolean response = restTemplate.postForEntity(url, borrowVO, Boolean.class).getBody();
        return response;
    }

    /**
     * 更新账户信息
     *
     * @param accountVO 账户信息
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer updateAccount(AccountVO accountVO) {
        String url = "http://AM-TRADE/am-trade/platformtransfer/updateaccount";
        Integer response = restTemplate.postForEntity(url, accountVO, Integer.class).getBody();
        return response;
    }

    /**
     * 插入数据
     *
     * @param accountRechargeVO 充值表
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer insertAccountRecharge(AccountRechargeVO accountRechargeVO) {
        String url = "http://AM-TRADE/am-trade/platformtransfer/insertaccountrecharge";
        Integer response = restTemplate.postForEntity(url, accountRechargeVO, Integer.class).getBody();
        return response;
    }

    /**
     * 插入数据
     *
     * @param accountListVO 收支明细
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer insertAccountList(AccountListVO accountListVO) {
        String url = "http://AM-TRADE/am-trade/platformtransfer/insertaccountlist";
        Integer response = restTemplate.postForEntity(url, accountListVO, Integer.class).getBody();
        return response;
    }

    /**
     * 根据账户id查询BankMerchantAccount
     *
     * @param accountId 账户id
     * @return
     * @auth sunpeikai
     */
    @Override
    public BankMerchantAccountVO searchBankMerchantAccountByAccountId(Integer accountId) {
        String url = "http://AM-TRADE/am-trade/platformtransfer/searchbankmerchantaccount/" + accountId;
        BankMerchantAccountResponse response = restTemplate.getForEntity(url, BankMerchantAccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 更新红包账户信息
     *
     * @param bankMerchantAccountVO 红包账户信息
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer updateBankMerchantAccount(BankMerchantAccountVO bankMerchantAccountVO) {
        String url = "http://AM-TRADE/am-trade/platformtransfer/updatebankmerchantaccount";
        Integer response = restTemplate.postForEntity(url, bankMerchantAccountVO, Integer.class).getBody();
        return response;
    }

    @Override
    public BankMerchantAccountResponse selectBankMerchantAccount(BankMerchantAccountListRequest form) {
        BankMerchantAccountResponse response = restTemplate
                .postForEntity(tradeService + "/bankMerchantAccount/selectBankMerchantAccount", form, BankMerchantAccountResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response;
        }
        return null;
    }

    @Override
    public BankMerchantAccountListCustomizeResponse selectBankMerchantAccountList(BankRedPacketAccountListRequest request) {
        BankMerchantAccountListCustomizeResponse response = restTemplate
                .postForEntity(tradeService + "/bankRedPacketAccount/selectBankMerchantAccountList", request, BankMerchantAccountListCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response;
        }
        return null;
    }


    /**
     * 插入数据
     *
     * @param bankMerchantAccountListVO 红包明细表
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer insertBankMerchantAccountList(BankMerchantAccountListVO bankMerchantAccountListVO) {
        String url = "http://AM-TRADE/am-trade/platformtransfer/insertbankmerchantaccountlist";
        Integer response = restTemplate.postForEntity(url, bankMerchantAccountListVO, Integer.class).getBody();
        return response;
    }

    /**
     * 银行转账异常
     *
     * @param request
     * @return
     * @auth jijun
     */
    @Override
    public AdminTransferExceptionLogResponse getAdminTransferExceptionLogCustomizeList(AdminTransferExceptionLogRequest request) {
        String url = "http://AM-TRADE/am-trade/transferExceptionLog/getRecordList";
        AdminTransferExceptionLogResponse response = restTemplate.postForEntity(url, request, AdminTransferExceptionLogResponse.class).getBody();
        return response;
    }

    /**
     * 银行转账异常
     *
     * @param request
     * @return
     * @auth jijun
     */
    @Override
    public Integer getAdminTransferExceptionLogCustomizeCountRecord(AdminTransferExceptionLogRequest request) {
        String url = "http://AM-TRADE/am-trade/transferExceptionLog/getCountRecord";
        return restTemplate.postForEntity(url, request, Integer.class).getBody();
    }

    /**
     * 更新银行转账信息
     *
     * @param request
     * @return
     * @auth jijun
     */
    @Override
    public int updateTransferExceptionLogByUUID(AdminTransferExceptionLogRequest request) {
        String url = "http://AM-TRADE/am-trade/transferExceptionLog/updateTransferExceptionLogByUUID";
        return restTemplate.postForEntity(url, request, Integer.class).getBody();
    }

    /**
     * 更新银行转账信息
     *
     * @param transferExceptionLog
     * @return
     * @auth jijun
     */
    @Override
    public int updateTransferExceptionLogByUUID(TransferExceptionLogVO transferExceptionLog) {
        String url = "http://AM-TRADE/am-trade/transferExceptionLog/updateTransferExceptionLogByUUID1";
        return restTemplate.postForEntity(url, transferExceptionLog, Integer.class).getBody();
    }

    /**
     * 获取银行转账异常通过uuid
     *
     * @param uuid
     * @return
     * @auth jijun
     */
    @Override
    public TransferExceptionLogVO getTransferExceptionLogByUUID(String uuid) {
        String url = "http://AM-TRADE/am-trade/transferExceptionLog/getTransferExceptionLogByUUID/" + uuid;
        TransferExceptionLogResponse response = restTemplate.getForEntity(url, TransferExceptionLogResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取发起账户分佣所需的详细信息
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<SubCommissionListConfigVO> searchSubCommissionListConfig() {
        String url = "http://AM-TRADE/am-trade/subcommission/searchsubcommissionlistconfig";
        SubCommissionListConfigResponse response = restTemplate.getForEntity(url, SubCommissionListConfigResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 插入数据
     *
     * @param subCommissionVO 平台账户分佣
     * @return
     * @auth sunpeikai
     */
    @Override
    public boolean insertSubCommission(SubCommissionVO subCommissionVO) {
        String url = "http://AM-TRADE/am-trade/subcommission/insertsubcommission";
        Boolean response = restTemplate.postForEntity(url, subCommissionVO, Boolean.class).getBody();
        return response;
    }

    /**
     * 根据订单号查询分佣数据
     *
     * @param orderId 订单号
     * @return
     * @auth sunpeikai
     */
    @Override
    public SubCommissionVO searchSubCommissionByOrderId(String orderId) {
        String url = "http://AM-TRADE/am-trade/subcommission/searchsubcommissionbyorderid/" + orderId;
        SubCommissionResponse response = restTemplate.getForEntity(url, SubCommissionResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 更新分佣数据
     *
     * @param subCommissionVO 待更新的数据参数
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer updateSubCommission(SubCommissionVO subCommissionVO) {
        String url = "http://AM-TRADE/am-trade/subcommission/updatesubcommission";
        Integer response = restTemplate.postForEntity(url, subCommissionVO, Integer.class).getBody();
        return response;
    }

    /**
     * 根据筛选条件查询分佣数据count
     *
     * @param request 筛选条件
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer getSubCommissionCount(SubCommissionRequest request) {
        String url = "http://AM-TRADE/am-trade/subcommission/getsubcommissioncount";
        Integer response = restTemplate.postForEntity(url, request, Integer.class).getBody();
        return response;
    }

    /**
     * 根据筛选条件查询分佣数据list
     *
     * @param request 筛选条件
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<SubCommissionVO> searchSubCommissionList(SubCommissionRequest request) {
        String url = "http://AM-TRADE/am-trade/subcommission/searchsubcommissionlist";
        SubCommissionResponse response = restTemplate.postForEntity(url, request, SubCommissionResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据id删除冻结记录
     *
     * @auther: hesy
     * @date: 2018/7/11
     */
    @Override
    public Integer deleteFreezeLogById(Integer id) {
        String url = "http://AM-TRADE/am-trade/repayfreezelog/deleteby_id/" + id;
        return restTemplate.getForEntity(url, Integer.class).getBody();
    }

    /**
     * 根据orderId获取冻结记录
     *
     * @auther: hesy
     * @date: 2018/7/11
     */
    @Override
    public BankRepayFreezeLogVO getBankFreezeLogByOrderId(String orderId) {
        String url = "http://AM-TRADE/am-trade/repayfreezelog/get_logvalid_byorderid/" + orderId;
        BankRepayFreezeLogResponse response = restTemplate.getForEntity(url, BankRepayFreezeLogResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 分页获取所有有效的冻结记录
     *
     * @auther: hesy
     * @date: 2018/7/11
     */
    @Override
    public List<BankRepayFreezeLogVO> getFreezeLogValidAll(Integer limitStart, Integer limitEnd) {
        Map<String, Integer> params = new HashMap<>();
        params.put("limitStart", limitStart);
        params.put("limitEnd", limitEnd);
        String url = "http://AM-TRADE/am-trade/repayfreezelog/get_logvalid_all";
        BankRepayFreezeLogResponse response = restTemplate.postForEntity(url, params, BankRepayFreezeLogResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 有效冻结记录总数
     *
     * @auther: hesy
     * @date: 2018/7/11
     */
    @Override
    public Integer getFreezeLogValidAllCount() {
        String url = "http://AM-TRADE/am-trade/repayfreezelog/get_logvalid_all_count";
        return restTemplate.getForEntity(url, Integer.class).getBody();
    }


    /**
     * 根据筛选条件查询银行投资撤销异常的数据count
     *
     * @param request 筛选条件
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer getTenderCancelExceptionCount(TenderCancelExceptionRequest request) {
        String url = "http://AM-TRADE/am-trade/tendercancelexception/gettendercancelexceptioncount";
        Integer response = restTemplate.postForEntity(url, request, Integer.class).getBody();
        return response;
    }

    /**
     * 根据筛选条件查询银行投资撤销异常list
     *
     * @param request 筛选条件
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<BorrowTenderTmpVO> searchTenderCancelExceptionList(TenderCancelExceptionRequest request) {
        String url = "http://AM-TRADE/am-trade/tendercancelexception/searchtendercancelexceptionlist";
        BorrowTenderTmpResponse response = restTemplate.postForEntity(url, request, BorrowTenderTmpResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据orderId查询BorrowTender
     *
     * @param orderId 订单号
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<BorrowTenderVO> searchBorrowTenderByOrderId(String orderId) {
        String url = "http://AM-TRADE/am-trade/tendercancelexception/searchborrowtenderbyorderid/" + orderId;
        BorrowTenderResponse response = restTemplate.getForEntity(url, BorrowTenderResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据orderId查询BorrowTenderTmp
     *
     * @param orderId 订单号
     * @return
     * @auth sunpeikai
     */
    @Override
    public BorrowTenderTmpVO searchBorrowTenderTmpByOrderId(String orderId) {
        String url = "http://AM-TRADE/am-trade/tendercancelexception/searchborrowtendertmpbyorderid/" + orderId;
        BorrowTenderTmpResponse response = restTemplate.getForEntity(url, BorrowTenderTmpResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据id删除BorrowTenderTmp
     *
     * @param id 主键
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer deleteBorrowTenderTmpById(Integer id) {
        String url = "http://AM-TRADE/am-trade/tendercancelexception/deleteborrowtendertmpbyid/" + id;
        Integer response = restTemplate.getForEntity(url, Integer.class).getBody();
        return response;
    }

    /**
     * 插入数据
     *
     * @param freezeHistoryVO 冻结历史
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer insertFreezeHistory(FreezeHistoryVO freezeHistoryVO) {
        String url = "http://AM-TRADE/am-trade/tendercancelexception/insertfreezehistory";
        Integer response = restTemplate.postForEntity(url, freezeHistoryVO, Integer.class).getBody();
        return response;
    }


    /**
     * 转账成功后续处理
     *
     * @param jsonObject
     * @return
     * @auth jijun
     */
    @Override
    public boolean transferAfter(JSONObject jsonObject) {
        String url = "http://AM-TRADE/am-trade/transferExceptionLog/transferAfter";
        return restTemplate.postForEntity(url, jsonObject, Boolean.class).getBody();
    }

    @Override
    public ChannelStatisticsDetailResponse searchChannelStatisticsDetail(ChannelStatisticsDetailRequest request) {
        ChannelStatisticsDetailResponse amTradeResponse = restTemplate.postForObject("http://AM-TRADE/am-trade/extensioncenter/channelstatisticsdetail/searchaction",
                request, ChannelStatisticsDetailResponse.class);
        return amTradeResponse;
    }

    /**
     * 根据主键获取优惠券还款记录
     *
     * @return
     * @auth jijun
     */
    @Override
    public CouponRecoverVO getCouponRecoverByPrimaryKey(Integer id) {
        String url = "http://AM-TRADE/am-trade/coupon/getCouponRecoverByPrimaryKey/" + id;
        CouponRecoverResponse response = restTemplate.getForEntity(url, CouponRecoverResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 取得优惠券投资信息
     *
     * @param nid
     * @return
     * @auth jijun
     */
    @Override
    public BorrowTenderCpnVO getCouponTenderInfoByNid(String nid) {
        String url = "http://AM-TRADE/am-trade/coupon/getCouponTenderInfoByNid/" + nid;
        BorrowTenderCpnResponse response = restTemplate.getForEntity(url, BorrowTenderCpnResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }


    /**
     * 查询批次中心的批次列表求和
     *
     * @param request
     * @return
     */
    @Override
    public BatchBorrowRecoverReponse getBatchBorrowCenterListSum(BatchBorrowRecoverRequest request) {
        BatchBorrowRecoverReponse response = restTemplate.
                postForEntity(tradeService + "/adminBatchBorrowRecover/getListSum", request, BatchBorrowRecoverReponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 根据筛选条件查询汇付对账count
     *
     * @param request 筛选条件
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer getAccountExceptionCount(AccountExceptionRequest request) {
        String url = "http://AM-TRADE/am-trade/accountexception/getaccountexceptioncount";
        Integer count = restTemplate.postForEntity(url, request, Integer.class).getBody();
        return count;
    }

    /**
     * 根据筛选条件查询汇付对账列表
     *
     * @param request 筛选条件
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<AccountExceptionVO> searchAccountExceptionList(AccountExceptionRequest request) {
        String url = "http://AM-TRADE/am-trade/accountexception/searchaccountexceptionlist";
        AccountExceptionResponse response = restTemplate.postForEntity(url, request, AccountExceptionResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据id查询AccountException
     *
     * @param id 主键
     * @return
     * @auth sunpeikai
     */
    @Override
    public AccountExceptionVO searchAccountExceptionById(Integer id) {
        String url = "http://AM-TRADE/am-trade/accountexception/searchaccountexceptionbyid/" + id;
        AccountExceptionResponse response = restTemplate.getForEntity(url, AccountExceptionResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 更新AccountException
     *
     * @param accountExceptionVO 更新参数
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer updateAccountException(AccountExceptionVO accountExceptionVO) {
        String url = "http://AM-TRADE/am-trade/accountexception/updateaccountexception";
        Integer response = restTemplate.postForEntity(url, accountExceptionVO, Integer.class).getBody();
        return response;
    }

    /**
     * 根据id删除AccountException
     *
     * @param id 主键
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer deleteAccountExceptionById(Integer id) {
        String url = "http://AM-TRADE/am-trade/accountexception/deleteaccountexceptionbyid/" + id;
        Integer response = restTemplate.getForEntity(url, Integer.class).getBody();
        return response;
    }


    /**
     * 获取提现列表数量
     *
     * @param request
     * @return
     */
    @Override
    public int getWithdrawRecordCount(WithdrawBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/accountWithdraw/getWithdrawRecordCount";
        return restTemplate.postForEntity(url, request, Integer.class).getBody();
    }

    /**
     * 获取提现列表
     *
     * @param request
     * @return
     */
    @Override
    public WithdrawCustomizeResponse getWithdrawRecordList(WithdrawBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/accountWithdraw/getWithdrawRecordList";
        WithdrawCustomizeResponse response = restTemplate.postForEntity(url, request, WithdrawCustomizeResponse.class).getBody();
        return response;
    }

    /**
     * 结束债权列表
     *
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public List<BankCreditEndVO> getCreditEndList(BankCreditEndListRequest requestBean) {
        String url = "http://AM-TRADE/am-trade/bankCreditEndController/getlist";
        BankCreditEndResponse response = restTemplate.postForEntity(url, requestBean, BankCreditEndResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            response.getResultList();
        }
        return null;
    }

    /**
     * 结束债权总记录数
     *
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public int getCreditEndCount(BankCreditEndListRequest requestBean) {
        String url = "http://AM-TRADE/am-trade/bankCreditEndController/getcount";
        return restTemplate.postForEntity(url, requestBean, Integer.class).getBody();
    }

    /**
     * 根据orderId获取
     *
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public BankCreditEndVO getCreditEndByOrderId(String orderId) {
        String url = "http://AM-TRADE/am-trade/bankCreditEndController/getby_orderid" + orderId;
        BankCreditEndResponse response = restTemplate.getForEntity(url, BankCreditEndResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            response.getResult();
        }
        return null;
    }

    /**
     * 更新结束债权记录
     *
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public int updateBankCreditEnd(BankCreditEndVO requestBean) {
        String url = "http://AM-TRADE/am-trade/bankCreditEndController/update";
        return restTemplate.postForEntity(url, requestBean, Integer.class).getBody();
    }

    /**
     * 批次恢复为初始状态
     *
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public int updateCreditEndForInitial(BankCreditEndVO requestBean) {
        String url = "http://AM-TRADE/am-trade/bankCreditEndController/update_initial";
        return restTemplate.postForEntity(url, requestBean, Integer.class).getBody();
    }

    /**
     * yangchangwei
     * 根据Borrownid 获取放款任务表
     *
     * @param id
     * @return
     */
    @Override
    public BorrowApicronResponse getBorrowApicronByID(String id) {
        String url = tradeService + "/adminBatchBorrowRecover/getRecoverApicronByID" + id;
        BorrowApicronResponse response = restTemplate.getForEntity(url,  BorrowApicronResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 根据creditNid查询债转信息
     *
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public HjhDebtCreditVO selectHjhDebtCreditByCreditNid(String creditNid) {
        String url = tradeService + "hjhDebtCredit/selectHjhDebtCreditByCreditNid/" + creditNid;
        HjhDebtCreditResponse response = restTemplate.getForEntity(url, HjhDebtCreditResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 银行结束债权后，更新债权表为完全承接
     *
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public int updateHjhDebtCreditForEnd(HjhDebtCreditVO hjhDebtCreditVO) {
        String url = tradeService + "hjhDebtCredit/updateHjhDebtCreditByPK";
        hjhDebtCreditVO.setCreditStatus(2);//转让状态 2完全承接
        hjhDebtCreditVO.setIsLiquidates(1);
        Response<Integer> response = restTemplate.postForEntity(url, hjhDebtCreditVO, Response.class).getBody();
        if (!Response.isSuccess(response)) {
            return 0;
        }
        return response.getResult().intValue();
    }

    /**
     * 请求结束债权（追加结束债权任务记录）
     *
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public int requestDebtEnd(HjhDebtCreditVO credit, String tenderAccountId, String tenderAuthCode) {
        String url = tradeService + "bankCreditEndController/insertBankCreditEndForCreditEnd";
        InsertBankCreditEndForCreditEndRequest request = new InsertBankCreditEndForCreditEndRequest(credit, tenderAccountId, tenderAuthCode);
        Response<Integer> response = restTemplate.postForEntity(url, request, Response.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResult().intValue();
    }

    /**
     * 根据nid获取BorrowTender
     *
     * @auther: hesy
     * @date: 2018/7/13
     */
    @Override
    public BorrowTenderVO getBorrowTenderByNid(String nid) {
        String url = "http://AM-TRADE/am-trade/borrowTender/getBorrowTenderByNid/" + nid;
        BorrowTenderResponse response = restTemplate.getForEntity(url, BorrowTenderResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据assignOrderId获取
     *
     * @auther: hesy
     * @date: 2018/7/13
     */
    @Override
    public HjhDebtCreditTenderVO getByAssignOrderId(String assignOrderId) {
        String url = "http://AM-TRADE/am-trade/hjhDebtCreditTender/getby_assignorderid/" + assignOrderId;
        HjhDebtCreditTenderResponse response = restTemplate.getForEntity(url, HjhDebtCreditTenderResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 检索汇计划加入明细列表
     *
     * @param request
     * @return
     */
    @Override
    public AutoTenderExceptionResponse selectAccedeRecordList(AutoTenderExceptionRequest request) {
        String url = "http://AM-TRADE/am-trade/autotenderexception/selectAccedeRecordList";
        AutoTenderExceptionResponse response = restTemplate.
                postForEntity(url, request, AutoTenderExceptionResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 查询计划加入明细
     *
     * @param tenderExceptionSolveRequest
     * @return
     * @auther: nxl
     * @date: 2018/7/12
     */
    @Override
    public HjhAccedeResponse selectHjhAccedeByParam(TenderExceptionSolveRequest tenderExceptionSolveRequest) {
        String url = "http://AM-TRADE/am-trade/autotenderexception/selectHjhAccedeByParam";
        HjhAccedeResponse response = restTemplate.
                postForEntity(url, tenderExceptionSolveRequest, HjhAccedeResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 查询计划加入明细临时表
     *
     * @param tenderExceptionSolveRequest
     * @return
     * @auther: nxl
     * @date: 2018/7/12
     */
    @Override
    public HjhPlanBorrowTmpResponse selectBorrowJoinList(TenderExceptionSolveRequest tenderExceptionSolveRequest) {
        String url = "http://AM-TRADE/am-trade/autotenderexception/selectBorrowJoinList";
        HjhPlanBorrowTmpResponse response = restTemplate.
                postForEntity(url, tenderExceptionSolveRequest, HjhPlanBorrowTmpResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 更改加入明细状态
     *
     * @param status
     * @param accedeId
     * @return
     */
    @Override
    public boolean updateTenderByParam(int status, int accedeId) {
        String url = "http://AM-TRADE/am-trade/autotenderexception/updateTenderByParam/" + status + "/" + accedeId;
        Response<Boolean> response = restTemplate.getForEntity(url, Response.class).getBody();
        if (response != null && response.getResult()) {
            return true;
        }
        return false;
    }

    /**
     * 更新投资数据
     *
     * @return
     * @author nxl
     */
    @Override
    public boolean updateBorrowForAutoTender(String borrowNid, String accedeOrderId, BankCallBean bean) {
        String url = "http://AM-TRADE/am-trade/autoTenderController/updateBorrowForAutoTender";
        BankCallBeanVO bankCallBeanVO = new BankCallBeanVO();
        BeanUtils.copyProperties(bean, bankCallBeanVO);
        UpdateBorrowForAutoTenderRequest request = new UpdateBorrowForAutoTenderRequest(borrowNid, accedeOrderId, bankCallBeanVO);
        Response response = restTemplate.postForEntity(url, request, Response.class).getBody();
        if (!Response.isSuccess(response)) {
            logger.error("[" + accedeOrderId + "] 银行自动投资成功后，更新投资数据失败。");
            throw new RuntimeException("银行自动投资成功后，更新投资数据失败。");
        }
        return true;
    }

    /**
     * 结束债权列表
     *
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public List<ManualReverseCustomizeVO> getManualReverseList(ManualReverseCustomizeRequest requestBean) {
        String url = "http://AM-TRADE/am-trade/manualreverse/getlist";
        ManualReverseCustomizeResponse response = restTemplate.postForEntity(url, requestBean, ManualReverseCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            response.getResultList();
        }
        return null;
    }

    /**
     * 结束债权总记录数
     *
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public int getManualReverseCount(ManualReverseCustomizeRequest requestBean) {
        String url = "http://AM-TRADE/am-trade/manualreverse/getcount";
        return restTemplate.postForEntity(url, requestBean, Integer.class).getBody();
    }

    /**
     * 手动冲正更新
     *
     * @auther: hesy
     * @date: 2018/7/14
     */
    @Override
    public Boolean updateManualReverse(ManualReverseAddRequest requestBean) {
        String url = "http://AM-TRADE/am-trade/manualreverse/update_manualreverse";
        return restTemplate.postForEntity(url, requestBean, Boolean.class).getBody();
    }

    /**
     * 计算实际金额 保存creditTenderLog表
     *
     * @return
     * @author nxl
     */
    @Override
    public Map<String, Object> saveCreditTenderLogNoSave(HjhDebtCreditVO credit, HjhAccedeVO hjhAccede, String orderId, String orderDate, BigDecimal yujiAmoust, boolean isLast) {
        String url = "http://AM-TRADE/am-trade/autoTenderController/saveCreditTenderLogNoSave";
        SaveCreditTenderLogRequest request = new SaveCreditTenderLogRequest(credit, hjhAccede, orderId, orderDate, yujiAmoust, isLast);
        Response<Map<String, Object>> response = restTemplate.postForEntity(url, request, Response.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 汇计划自动承接成功后数据库更新操作
     *
     * @return
     * @author nxl
     */
    @Override
    public boolean updateCreditForAutoTender(String creditNid, String accedeOrderId, String planNid, BankCallBean bean,
                                             String tenderUsrcustid, String sellerUsrcustid, Map<String, Object> resultMap) {
        String url = "http://AM-TRADE/am-trade/autoTenderController/updateCreditForAutoTender";
        BankCallBeanVO bankCallBeanVO = new BankCallBeanVO();
        BeanUtils.copyProperties(bean, bankCallBeanVO);
        UpdateCreditForAutoTenderRequest request = new UpdateCreditForAutoTenderRequest(creditNid, accedeOrderId, planNid, bankCallBeanVO, tenderUsrcustid, sellerUsrcustid, resultMap);
        Response response = restTemplate.postForEntity(url, request, Response.class).getBody();
        if (!Response.isSuccess(response)) {
            logger.error("[" + accedeOrderId + "] 银行自动债转成功后，更新债转数据失败。");
            throw new RuntimeException("银行自动债转成功后，更新债转数据失败。");
        }
        return true;
    }


    /**
     * 分页查询平台设置账户列表
     *
     * @return
     */
    @Override
    public MerchantAccountResponse selectMerchantAccountListByPage(AdminMerchantAccountRequest request) {
        String url = "http://AM-TRADE/am-trade/config/accountconfig/selectMerchantAccountListByPage";
        MerchantAccountResponse response = restTemplate.
                postForEntity(url, request, MerchantAccountResponse.class).
                getBody();
        List<ParamNameVO> paramList = getParamNameList(CustomConstants.SUB_ACCOUNT_CLASS);
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            if (!CollectionUtils.isEmpty(paramList)) {
                response.setParamNameList(paramList);
            }
            return response;
        }
        return null;
    }

    /**
     * 根据id查询账户平台设置
     *
     * @return
     */
    @Override
    public MerchantAccountResponse searchAccountConfigInfo(Integer id) {
        String url = "http://AM-TRADE/am-trade/config/accountconfig/searchAccountConfigInfo";
        AdminMerchantAccountRequest request = new AdminMerchantAccountRequest();
        request.setId(id);
        MerchantAccountResponse response = restTemplate.postForEntity(url, request, MerchantAccountResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 添加账户平台设置
     *
     * @return
     */
    @Override
    public MerchantAccountResponse saveAccountConfig(AdminMerchantAccountRequest request) {
        String url = "http://AM-TRADE/am-trade/config/accountconfig/saveAccountConfig";
        MerchantAccountResponse response = restTemplate.
                postForEntity(url, request, MerchantAccountResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 修改账户平台设置
     *
     * @return
     */
    @Override
    public MerchantAccountResponse updateAccountConfig(AdminMerchantAccountRequest request) {
        String url = "http://AM-TRADE/am-trade/config/accountconfig/updateAccountConfig";
        MerchantAccountResponse response = restTemplate.
                postForEntity(url, request, MerchantAccountResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 子账户类型 查询
     *
     * @return
     */
    @Override
    public List<ParamNameVO> getParamNameList(String code) {
        ParamNameResponse amResponse = restTemplate.getForEntity("http://AM-CONFIG/am-config/accountconfig/getNameCd/" + CustomConstants.SUB_ACCOUNT_CLASS, ParamNameResponse.class)
                .getBody();
        if (amResponse != null && Response.isSuccess(amResponse)) {
            return amResponse.getResultList();
        }
        return null;
    }

    /**
     * 根据子账户名称检索
     *
     * @param subAccountName
     * @return
     */
    @Override
    public int countAccountListInfoBySubAccountName(String ids, String subAccountName) {
        Map map = new HashMap();
        map.put("ids", ids);
        map.put("subAccountName", subAccountName);
        return restTemplate.
                postForEntity("http://AM-TRADE/am-trade/config/accountconfig/countAccountListInfoBySubAccountName", map, Integer.class).getBody();
    }

    /**
     * 根据子账户代号检索
     *
     * @param subAccountCode
     * @return
     */
    @Override
    public int countAccountListInfoBySubAccountCode(String ids, String subAccountCode) {
        Map map = new HashMap();
        map.put("ids", ids);
        map.put("subAccountName", subAccountCode);
        return restTemplate.
                postForEntity("http://AM-TRADE/am-trade/config/accountconfig/countAccountListInfoBySubAccountCode", map, Integer.class).getBody();
    }

    /**
     * 根据机构编号获取机构列表
     *
     * @return
     * @author nxl
     */
    @Override
    public List<HjhInstConfigVO> selectInstConfigAll() {
        HjhInstConfigResponse response = restTemplate.
                getForEntity("http://AM-TRADE/am-trade/hjhInstConfig/selectInstConfigAll", HjhInstConfigResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 借款初审总条数
     *
     * @param borrowFirstRequest
     * @return
     */
    @Override
    public Integer countBorrowFirst(BorrowFirstRequest borrowFirstRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_first/count_borrow_first";
        BorrowFirstCustomizeResponse response =
                restTemplate.postForEntity(url, borrowFirstRequest, BorrowFirstCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return 0;
    }

    /**
     * 借款初审列表
     *
     * @param borrowFirstRequest
     * @return
     */
    @Override
    public List<BorrowFirstCustomizeVO> selectBorrowFirstList(BorrowFirstRequest borrowFirstRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_first/select_borrow_first_list";
        BorrowFirstCustomizeResponse response =
                restTemplate.postForEntity(url, borrowFirstRequest, BorrowFirstCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 统计页面值总和
     *
     * @param borrowFirstRequest
     * @return
     */
    @Override
    public String sumBorrowFirstAccount(BorrowFirstRequest borrowFirstRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_first/sum_borrow_first_account";
        BorrowFirstCustomizeResponse response =
                restTemplate.postForEntity(url, borrowFirstRequest, BorrowFirstCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getSumAccount();
        }
        return null;
    }

    /**
     * 根据code获取配置
     *
     * @param configCd
     * @return
     */
    @Override
    public BorrowConfigVO getBorrowConfig(String configCd) {
        String url = "http://AM-TRADE/am-trade/borrow/getBorrowConfig/" + configCd;
        BorrowConfigResponse response = restTemplate.getForEntity(url, BorrowConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据标的编号查询详细信息
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowVO selectBorrowByNid(String borrowNid) {
        BorrowResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrow/getBorrow/" + borrowNid, BorrowResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据标的编号查询borrowInfo
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowInfoVO selectBorrowInfoByNid(String borrowNid) {
        BorrowInfoResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrow/getBorrowInfoByNid/" + borrowNid, BorrowInfoResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 交保证金
     *
     * @param borrowNid
     * @return
     */
    @Override
    public boolean insertBorrowBail(String borrowNid, String currUserId) {
        String url = "http://AM-TRADE/am-trade/borrow_first/insert_borrow_bail/" + borrowNid + "/" + currUserId;
        BorrowFirstCustomizeResponse response =
                restTemplate.getForEntity(url, BorrowFirstCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getFlag();
        }
        return false;
    }

    /**
     * 更新-发标
     *
     * @param borrowFireRequest
     */
    @Override
    public boolean updateOntimeRecord(BorrowFireRequest borrowFireRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_first/update_ontime_record";
        BorrowFirstCustomizeResponse response =
                restTemplate.postForEntity(url, borrowFireRequest, BorrowFirstCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getFlag();
        }
        return false;
    }

    /**
     * 借款复审总条数
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public Integer countBorrowFull(BorrowFullRequest borrowFullRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_full/count_borrow_full";
        BorrowFullCustomizeResponse response =
                restTemplate.postForEntity(url, borrowFullRequest, BorrowFullCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return 0;
    }

    /**
     * 借款复审列表
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public List<BorrowFullCustomizeVO> selectBorrowFullList(BorrowFullRequest borrowFullRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_full/select_borrow_full_list";
        BorrowFullCustomizeResponse response =
                restTemplate.postForEntity(url, borrowFullRequest, BorrowFullCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 借款复审合计
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public BorrowFullCustomizeVO sumAccount(BorrowFullRequest borrowFullRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_full/sum_account";
        BorrowFullCustomizeResponse response =
                restTemplate.postForEntity(url, borrowFullRequest, BorrowFullCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 复审详细信息
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowFullCustomizeVO getFullInfo(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrow_full/get_full_info/" + borrowNid;
        BorrowFullCustomizeResponse response =
                restTemplate.getForEntity(url, BorrowFullCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 复审详细列表条数
     *
     * @param borrowNid
     * @return
     */
    @Override
    public Integer countFullList(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrow_full/count_full_list/" + borrowNid;
        BorrowFullCustomizeResponse response =
                restTemplate.getForEntity(url, BorrowFullCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return 0;
    }

    /**
     * 复审详细列表
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public List<BorrowFullCustomizeVO> getFullList(BorrowFullRequest borrowFullRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_full/get_full_list";
        BorrowFullCustomizeResponse response =
                restTemplate.postForEntity(url, borrowFullRequest, BorrowFullCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 复审详细列表合计
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowFullCustomizeVO sumAmount(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrow_full/sum_amount/" + borrowNid;
        BorrowFullCustomizeResponse response =
                restTemplate.getForEntity(url, BorrowFullCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据UserID查询账户信息
     *
     * @param userId
     * @return
     */
    @Override
    public AccountVO getAccountByUserId(int userId) {
        String url = "http://AM-TRADE/am-trade/account/getAccountByUserId/" + userId;
        AccountResponse response = restTemplate.getForEntity(url, AccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 标的复审数据更新
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public AdminResult updateBorrowFull(BorrowFullRequest borrowFullRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_full/update_borrow_full";
        Response response = restTemplate.postForEntity(url, borrowFullRequest, Response.class).getBody();
        if (response != null) {
            AdminResult adminResult = new AdminResult();
            if (!Response.isSuccess(response)) {
                adminResult.setStatus(BaseResult.FAIL);
                adminResult.setStatusDesc(response.getMessage());
            }
            return adminResult;
        }
        return null;
    }

    /**
     * 流标
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public AdminResult updateBorrowOver(BorrowFullRequest borrowFullRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_full/update_borrow_over";
        Response response = restTemplate.postForEntity(url, borrowFullRequest, Response.class).getBody();
        if (response != null) {
            AdminResult adminResult = new AdminResult();
            if (!Response.isSuccess(response)) {
                adminResult.setStatus(BaseResult.FAIL);
                adminResult.setStatusDesc(response.getMessage());
            }
            return adminResult;
        }
        return null;
    }

    /**
     * 投资明细记录 总数COUNT
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public Integer countBorrowInvest(BorrowInvestRequest borrowInvestRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/count_borrow_invest";
        BorrowInvestCustomizeResponse response =
                restTemplate.postForEntity(url, borrowInvestRequest, BorrowInvestCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return 0;
    }

    /**
     * 投资明细列表
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public List<BorrowInvestCustomizeVO> selectBorrowInvestList(BorrowInvestRequest borrowInvestRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/select_borrow_invest_list";
        BorrowInvestCustomizeResponse response =
                restTemplate.postForEntity(url, borrowInvestRequest, BorrowInvestCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 投资明细列表合计
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public String selectBorrowInvestAccount(BorrowInvestRequest borrowInvestRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/select_borrow_invest_account";
        BorrowInvestCustomizeResponse response =
                restTemplate.postForEntity(url, borrowInvestRequest, BorrowInvestCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getSumAccount();
        }
        return null;
    }

    /**
     * 投资明细导出列表
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public List<BorrowInvestCustomizeVO> getExportBorrowInvestList(BorrowInvestRequest borrowInvestRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/export_borrow_invest_list";
        BorrowInvestCustomizeResponse response =
                restTemplate.postForEntity(url, borrowInvestRequest, BorrowInvestCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取用户投资协议
     *
     * @param nid
     * @return
     */
    @Override
    public TenderAgreementVO selectTenderAgreement(String nid) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/tender_agreement/" + nid;
        TenderAgreementResponse response = restTemplate.getForEntity(url, TenderAgreementResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 标的放款记录
     *
     * @param userId
     * @param borrowNid
     * @param nid
     * @return
     */
    @Override
    public BorrowRecoverVO selectBorrowRecover(Integer userId, String borrowNid, String nid) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/select_borrow_recover/" + userId + "/" + borrowNid + "/" + nid;
        BorrowRecoverResponse response = restTemplate.getForEntity(url, BorrowRecoverResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取借款列表
     *
     * @param borrowNid
     * @return
     */
    @Override
    public List<BorrowListCustomizeVO> selectBorrowList(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/select_borrow_list/" + borrowNid;
        BorrowListCustomizeResponse response = restTemplate.getForEntity(url, BorrowListCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 标的投资信息
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public List<WebUserInvestListCustomizeVO> selectUserInvestList(BorrowInvestRequest borrowInvestRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/select_user_invest_list";
        WebUserInvestListCustomizeResponse response =
                restTemplate.postForEntity(url, borrowInvestRequest, WebUserInvestListCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 标的放款记录分期count
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public Integer countProjectRepayPlanRecordTotal(BorrowInvestRequest borrowInvestRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/count_project_repay";
        WebProjectRepayListCustomizeResponse response =
                restTemplate.postForEntity(url, borrowInvestRequest, WebProjectRepayListCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return 0;
    }

    /**
     * 标的放款记录分期
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public List<WebProjectRepayListCustomizeVO> selectProjectRepayPlanList(BorrowInvestRequest borrowInvestRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/select_project_repay";
        WebProjectRepayListCustomizeResponse response =
                restTemplate.postForEntity(url, borrowInvestRequest, WebProjectRepayListCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 更新标的放款记录
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public Integer updateBorrowRecover(BorrowInvestRequest borrowInvestRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/update_borrow_recover";
        WebProjectRepayListCustomizeResponse response =
                restTemplate.postForEntity(url, borrowInvestRequest, WebProjectRepayListCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return null;
    }

    /**
     * 获取标的备案列表count
     *
     * @param borrowRegistListRequest
     * @return
     */
    @Override
    public Integer getRegistListCount(BorrowRegistListRequest borrowRegistListRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_regist/get_regist_count";
        BorrowRegistCustomizeResponse response =
                restTemplate.postForEntity(url, borrowRegistListRequest, BorrowRegistCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return 0;
    }

    /**
     * 获取标的备案列表
     *
     * @param borrowRegistListRequest
     * @return
     */
    @Override
    public List<BorrowRegistCustomizeVO> selectRegistList(BorrowRegistListRequest borrowRegistListRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_regist/select_borrow_regist_list";
        BorrowRegistCustomizeResponse response = restTemplate.postForEntity(url, borrowRegistListRequest, BorrowRegistCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 标的备案列表统计
     *
     * @param borrowRegistListRequest
     * @return
     */
    @Override
    public String sumBorrowRegistAccount(BorrowRegistListRequest borrowRegistListRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_regist/sum_borrow_regist_account";
        BorrowRegistCustomizeResponse response =
                restTemplate.postForEntity(url, borrowRegistListRequest, BorrowRegistCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getSumAccount();
        }
        return null;
    }

    /**
     * 标的备案
     *
     * @param request
     * @return
     */
    @Override
    public AdminResult updateBorrowRegist(BorrowRegistUpdateRequest request) {
        String url = "http://AM-TRADE/am-trade/borrow_regist/update_borrow_regist";
        Response response = restTemplate.postForEntity(url, request, Response.class).getBody();
        if (Response.isSuccess(response)) {
            return new AdminResult(BaseResult.SUCCESS, "备案成功！");
        } else {
            return new AdminResult(BaseResult.FAIL, response.getMessage());
        }
    }

    /**
     * 资产来源
     *
     * @return
     */
    @Override
    public List<HjhInstConfigVO> selectHjhInstConfigList() {
        String url = "http://AM-TRADE/am-trade/hjhInstConfig/selectInstConfigAll";
        HjhInstConfigResponse response = restTemplate.getForEntity(url, HjhInstConfigResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    /*资产中心 start*/

    /**
     * 获取资金来源
     *
     * @param
     * @return List<HjhInstConfigVO>
     */
    @Override
    public List<HjhInstConfigVO> findHjhInstConfigList() {
        HjhInstConfigResponse response = restTemplate.
                getForEntity("http://AM-TRADE/am-trade/hjhInstConfig/selectInstConfigAll", HjhInstConfigResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 产品类型下拉联动
     *
     * @param instCodeSrch
     * @return List<HjhAssetTypeVO>
     */
    @Override
    public List<HjhAssetTypeVO> findHjhAssetTypeList(String instCodeSrch) {
        HjhAssetTypeResponse response = restTemplate.
                getForEntity("http://AM-TRADE/am-trade/hjhAssetType/selectAssetTypeAll/" + instCodeSrch, HjhAssetTypeResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询ParamName
     *
     * @return
     */
    @Override
    public Map<String, String> findParamNameMap(String param) {
        Map<String, String> resultMap = CacheUtil.getParamNameMap(param);
        return resultMap;
    }

    /**
     * 查询资产列表
     *
     * @param request
     * @return
     */
    @Override
    public AssetListCustomizeResponse findAssetList(AssetListRequest request) {
        AssetListCustomizeResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/assetList/findAssetList", request,
                        AssetListCustomizeResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 查询详情
     *
     * @return 查询详情
     */
    @Override
    public AssetDetailCustomizeVO findDetailById(AssetListRequest assetListRequest) {
        AssetDetailCustomizeResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/assetList/findDetailById", assetListRequest,
                        AssetDetailCustomizeResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 查询记录总数
     *
     * @param request
     * @return 查询详情
     */
    @Override
    public Integer getRecordCount(AssetListRequest request) {
        AssetListCustomizeResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/assetList/findRecordCount", request,
                        AssetListCustomizeResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getCount();
        }
        return null;
    }

    /**
     * 查询列总计
     *
     * @param request
     * @return 查询详情
     */
    @Override
    public BigDecimal sumAccount(AssetListRequest request) {
        AssetListCustomizeResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/assetList/sumAccount", request,
                        AssetListCustomizeResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getSum();
        }
        return null;
    }

    @Override
    public void updateCashDepositeStatus(String assetId, String menuHide) {
        String url = "http://AM-TRADE/am-trade/assetList/updateCashDepositeStatus/" + assetId + "/" + menuHide;
        restTemplate.getForEntity(url, String.class).getBody();

    }
    /*资产中心 end*/

    /*标签配置中心 start*/
    @Override
    public List<BorrowProjectTypeVO> findBorrowProjectTypeList() {
        // 复用
        BorrowProjectTypeResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/hjhLabel/selectBorrowProjectByBorrowCd", BorrowProjectTypeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<BorrowStyleVO> findBorrowStyleList() {
        // 复用
        BorrowStyleResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/hjhLabel/selectBorrowStyleList", BorrowStyleResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public HjhLabelCustomizeResponse findHjhLabelList(HjhLabelRequest request) {
        HjhLabelCustomizeResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/hjhLabel/selectHjhLabelList", request, HjhLabelCustomizeResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public List<HjhLabelCustomizeVO> findHjhLabelListById(HjhLabelRequest request) {
        HjhLabelCustomizeResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/hjhLabel/selectHjhLabelListById", request, HjhLabelCustomizeResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<HjhLabelCustomizeVO> findHjhLabelListLabelName(HjhLabelRequest request) {
        HjhLabelCustomizeResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/hjhLabel/selectHjhLabelListLabelName", request, HjhLabelCustomizeResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public int insertHjhLabelRecord(HjhLabelInfoRequest request) {
        String url = "http://AM-TRADE/am-trade/hjhLabel/insertHjhLabelRecord";
        Integer updateFlag = restTemplate.postForEntity(url, request, Integer.class).getBody();
        if (updateFlag > 0) {
            return updateFlag;
        }
        return 0;
    }

    @Override
    public int updateHjhLabelRecord(HjhLabelInfoRequest request) {
        String url = "http://AM-TRADE/am-trade/hjhLabel/updateHjhLabelRecord";
        Integer updateFlag = restTemplate.postForEntity(url, request, Integer.class).getBody();
        if (updateFlag > 0) {
            return updateFlag;
        }
        return 0;
    }

    @Override
    public int updateAllocationRecord(HjhLabelInfoRequest request) {
        String url = "http://AM-TRADE/am-trade/hjhLabel/updateAllocationRecord";
        Integer updateFlag = restTemplate.postForEntity(url, request, Integer.class).getBody();
        if (updateFlag > 0) {
            return updateFlag;
        }
        return 0;
    }
    /*标签配置中心 end*/

    /*计划列表 start*/
    @Override
    public HjhPlanResponse getHjhPlanListByParam(PlanListRequest form) {
        HjhPlanResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/planList/getHjhPlanListByParam", form, HjhPlanResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public HjhPlanSumVO getCalcSumByParam(PlanListRequest form) {
        HjhPlanSumResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/planList/getCalcSumByParam", form, HjhPlanSumResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<HjhPlanDetailVO> getHjhPlanDetailByPlanNid(PlanListRequest form) {
        HjhPlanDetailResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/planList/getHjhPlanDetailByPlanNid", form, HjhPlanDetailResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public HjhPlanResponse getPlanNameAjaxCheck(PlanListRequest form) {
        HjhPlanResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/planList/getPlanNameAjaxCheck", form, HjhPlanResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public HjhPlanResponse getPlanNidAjaxCheck(PlanListRequest form) {
        HjhPlanResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/planList/getPlanNidAjaxCheck", form, HjhPlanResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public HjhPlanResponse updatePlanStatusByPlanNid(PlanListRequest form) {
        HjhPlanResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/planList/updatePlanStatusByPlanNid", form, HjhPlanResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public HjhPlanResponse updatePlanDisplayByPlanNid(PlanListRequest form) {
        HjhPlanResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/planList/updatePlanDisplayByPlanNid", form, HjhPlanResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public boolean isExistsRecord(String planNid) {
        String url = "http://AM-TRADE/am-trade/planList/isExistsRecord/" + planNid;
        boolean Flag = restTemplate.getForEntity(url, Boolean.class).getBody();
        return Flag;
    }

    @Override
    public int countByPlanName(String planName) {
        String url = "http://AM-TRADE/am-trade/planList/countByPlanName/" + planName;
        Integer count = restTemplate.getForEntity(url, Integer.class).getBody();
        return count;
    }

    @Override
    public int isLockPeriodExist(String lockPeriod, String borrowStyle, String isMonth) {
        String url = "http://AM-TRADE/am-trade/planList/isLockPeriodExist/" + lockPeriod + "/" + borrowStyle + "/" + isMonth;
        Integer count = restTemplate.getForEntity(url, Integer.class).getBody();
        return count;
    }

    @Override
    public int updateRecord(PlanListRequest form) {
        String url = "http://AM-TRADE/am-trade/planList/updateRecord";
        Integer Flag = restTemplate.postForEntity(url, form, Integer.class).getBody();
        return Flag;
    }

    @Override
    public int insertRecord(PlanListRequest form) {
        String url = "http://AM-TRADE/am-trade/planList/insertRecord";
        Integer Flag = restTemplate.postForEntity(url, form, Integer.class).getBody();
        return Flag;
    }
    /*计划列表 end*/

    /*加入明细 start*/

    /**
     * 检索加入明细列表
     *
     * @param form
     * @return
     * @Title selectAccedeRecordList
     */
    @Override
    public AccedeListResponse getAccedeListByParam(AccedeListRequest form) {
        AccedeListResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/accedeList/getAccedeListByParam", form, AccedeListResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public List<AccedeListCustomizeVO> getAccedeListByParamWithoutPage(AccedeListRequest form) {
        AccedeListResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/accedeList/getAccedeListByParamWithoutPage", form, AccedeListResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public HjhAccedeSumVO getCalcSumByParam(AccedeListRequest form) {
        HjhAccedeSumResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/accedeList/getCalcSumByParam", form, HjhAccedeSumResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<TenderAgreementVO> selectTenderAgreementByNid(String planOrderId) {
        // 原 selectTenderAgreementByNid 方法返回的是 List<CurrentHoldObligatoryRightListCustomizeVO> 不能共用，换新方法
    	/*String url = "http://AM-TRADE/am-trade/tenderagreement/selectTenderAgreementByNid/" + planOrderId;*/
    	String url = "http://AM-TRADE/am-trade/tenderagreement/selectTenderAgreementByTenderNid/" + planOrderId;
        TenderAgreementResponse response = restTemplate.getForEntity(url, TenderAgreementResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public int updateSendStatusByParam(AccedeListRequest request) {
        String url = "http://AM-TRADE/am-trade/accedeList/updateSendStatusByParam";
        Integer Flag = restTemplate.postForEntity(url, request, Integer.class).getBody();
        return Flag;
    }

    @Override
    public UserHjhInvistDetailVO selectUserHjhInvistDetail(AccedeListRequest request) {
        UserHjhInvistDetailResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/accedeList/selectUserHjhInvistDetail", request, UserHjhInvistDetailResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }
    /*加入明细 end*/

    /*承接记录 start*/
    @Override
    public HjhCreditTenderResponse getHjhCreditTenderListByParam(HjhCreditTenderRequest form) {
        HjhCreditTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/hjhcredittender/getHjhCreditTenderListByParam", form, HjhCreditTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public List<HjhCreditTenderCustomizeVO> getHjhCreditTenderListByParamWithOutPage(HjhCreditTenderRequest form) {
        HjhCreditTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/hjhcredittender/getHjhCreditTenderListByParamWithOutPage", form, HjhCreditTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public HjhDebtCreditTenderVO selectHjhCreditTenderRecord(HjhCreditTenderRequest form) {
        HjhDebtCreditTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/hjhcredittender/selectHjhCreditTenderRecord", form, HjhDebtCreditTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }
    /*承接记录 end*/

    /*计划引擎 start*/

    /**
     * 查询计划专区列表
     *
     * @return
     */
    @Override
    public HjhRegionResponse getHjhRegionList(AllocationEngineRuquest form) {
        HjhRegionResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/allocation/selectHjhRegionList", form, HjhRegionResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public String getPlanNameByPlanNid(AllocationEngineRuquest form) {
        HjhRegionResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/allocation/selectPlanNameByPlanNid", form, HjhRegionResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getPlanName();
        }
        return null;
    }

    @Override
    public int insertRecord(HjhRegionVO request) {
        String url = "http://AM-TRADE/am-trade/allocation/insertRecord";
        Integer Flag = restTemplate.postForEntity(url, request, Integer.class).getBody();
        return Flag;
    }

    @Override
    public HjhRegionResponse getPlanNidAjaxCheck(String planNid) {
        HjhRegionResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/allocation/getPlanNidAjaxCheck/" + planNid, HjhRegionResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public HjhRegionVO getHjhRegionVOById(String id) {
        HjhRegionResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/allocation/getHjhRegionVOById/" + id, HjhRegionResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public int updateHjhRegionRecord(HjhRegionVO vo) {
        String url = "http://AM-TRADE/am-trade/allocation/updateHjhRegionRecord";
        Integer Flag = restTemplate.postForEntity(url, vo, Integer.class).getBody();
        return Flag;
    }

    @Override
    public HjhRegionResponse updateAllocationEngineRecord(HjhRegionVO vo) {
        HjhRegionResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/allocation/updateAllocationEngineRecord", vo, HjhRegionResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public List<HjhRegionVO> getHjhRegionListWithOutPage(AllocationEngineRuquest request) {
        HjhRegionResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/allocation/getHjhRegionListWithOutPage", request, HjhRegionResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public HjhAllocationEngineResponse getHjhAllocationEngineList(AllocationEngineRuquest request) {
        HjhAllocationEngineResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/allocation/getHjhAllocationEngineList", request, HjhAllocationEngineResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public List<HjhAllocationEngineVO> getAllocationList(AllocationEngineRuquest form) {
        HjhAllocationEngineResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/allocation/getAllocationList", form, HjhAllocationEngineResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public HjhAllocationEngineVO getPlanConfigRecord(Integer engineId) {
        HjhAllocationEngineResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/allocation/getPlanConfigRecord/" + engineId, HjhAllocationEngineResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public int updateHjhAllocationEngineRecord(HjhAllocationEngineVO vo) {
        String url = "http://AM-TRADE/am-trade/allocation/updateHjhAllocationEngineRecord";
        Integer Flag = restTemplate.postForEntity(url, vo, Integer.class).getBody();
        return Flag;
    }

    @Override
    public HjhAllocationEngineVO getPlanConfigRecordByParam(AllocationEngineRuquest form) {
        HjhAllocationEngineResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/allocation/getPlanConfigRecordByParam", form, HjhAllocationEngineResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public boolean checkRepeat(String labelName, String planNid) {
        String url = "http://AM-TRADE/am-trade/allocation/checkRepeat/" + labelName + "/" + planNid;
        boolean Flag = restTemplate.getForEntity(url, Boolean.class).getBody();
        return Flag;
    }

    @Override
    public String getPlanBorrowStyle(String planNid) {
        String url = "http://AM-TRADE/am-trade/allocation/getPlanBorrowStyle/" + planNid;
        String borrowStyle = restTemplate.getForEntity(url, String.class).getBody();
        return borrowStyle;
    }

    @Override
    public HjhRegionVO getHjhRegionRecordByPlanNid(String planNid) {
        HjhRegionResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/allocation/getHjhRegionRecordByPlanNid/" + planNid, HjhRegionResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public int insertHjhAllocationEngineRecord(HjhAllocationEngineVO request) {
        String url = "http://AM-TRADE/am-trade/allocation/insertHjhAllocationEngineRecord";
        Integer Flag = restTemplate.postForEntity(url, request, Integer.class).getBody();
        return Flag;
    }

    @Override
    public List<HjhPlanVO> getHjhPlanByPlanNid(String planNid) {
        HjhPlanResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/allocation/selectHjhPlanByPlanNid/" + planNid, HjhPlanResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<HjhRegionVO> getHjhRegioByPlanNid(String planNid) {
        HjhRegionResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/allocation/getHjhRegioByPlanNid/" + planNid, HjhRegionResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }
    /*计划引擎 end*/

    /**
     * @Description 获取admin产品中心-汇直投-放款明细列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public Integer countBorrowRecover(BorrowRecoverRequest request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRecover/countBorrowRecover";
        AdminBorrowRecoverResponse response = restTemplate.postForEntity(url, request, AdminBorrowRecoverResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return 0;
    }

    /**
     * @Description 获取admin产品中心-汇直投-放款明细列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public List<BorrowRecoverCustomizeVO> selectBorrowRecoverList(BorrowRecoverRequest request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRecover/selectBorrowRecoverList";
        AdminBorrowRecoverResponse response = restTemplate.postForEntity(url, request, AdminBorrowRecoverResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * @Description 获取admin产品中心-汇直投-放款明细统计
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public BorrowRecoverCustomizeVO sumBorrowRecoverList(BorrowRecoverRequest request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRecover/sumBorrowRecoverList";
        AdminBorrowRecoverResponse response = restTemplate.postForEntity(url, request, AdminBorrowRecoverResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * @Description 获取admin借款操作日志列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public Integer countBorrowLog(BorrowLogRequset request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowLog/countBorrowLog";
        AdminBorrowLogResponse response = restTemplate.postForEntity(url, request, AdminBorrowLogResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return 0;
    }

    /**
     * @Description 获取admin借款操作日志列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public List<BorrowLogCustomizeVO> selectBorrowLogList(BorrowLogRequset request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowLog/selectBorrowLogList";
        AdminBorrowLogResponse response = restTemplate.postForEntity(url, request, AdminBorrowLogResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * @Description 获取admin借款操作日志列表导出
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public List<BorrowLogCustomizeVO> exportBorrowLogList(BorrowLogRequset request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowLog/exportBorrowLogList";
        AdminBorrowLogResponse response = restTemplate.postForEntity(url, request, AdminBorrowLogResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * @Description 获取admin产品中心-汇直投-还款信息列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public Integer countBorrowRepayment(BorrowRepaymentRequest request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepayment/countBorrowRepayment";
        AdminBorrowRepaymentResponse response = restTemplate.postForEntity(url, request, AdminBorrowRepaymentResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return 0;
    }

    /**
     * @Description 获取admin产品中心-汇直投-还款信息列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public List<BorrowRepaymentCustomizeVO> selectBorrowRepaymentList(BorrowRepaymentRequest request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepayment/selectBorrowRepaymentList";
        AdminBorrowRepaymentResponse response = restTemplate.postForEntity(url, request, AdminBorrowRepaymentResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * @Description 获取admin产品中心-汇直投-还款信息列表统计
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public BorrowRepaymentCustomizeVO sumBorrowRepaymentInfo(BorrowRepaymentRequest request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepayment/sumBorrowRepaymentInfo";
        AdminBorrowRepaymentResponse response = restTemplate.postForEntity(url, request, AdminBorrowRepaymentResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * @Description 获取admin数据导出--还款计划导出
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public List<BorrowRepaymentPlanCustomizeVO> exportRepayClkActBorrowRepaymentInfoList(BorrowRepaymentPlanRequest request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepayment/exportRepayClkActBorrowRepaymentInfoList";
        AdminBorrowRepaymentResponse response = restTemplate.postForEntity(url, request, AdminBorrowRepaymentResponse.class).getBody();
        if (response != null) {
            return response.getBorrowRepaymentPlanList();
        }
        return null;
    }

    /**
     * @Description 获取admin查询延期数据
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public AdminRepayDelayCustomizeVO selectBorrowInfo(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepayment/selectBorrowInfo/" + borrowNid;
        AdminRepayDelayResponse response = restTemplate.getForEntity(url, AdminRepayDelayResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * @Description 获取admin查询延期还款计划（不分期）
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public BorrowRepayVO getBorrowRepayDelay(String borrowNid, String borrowApr, String borrowStyle) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepayment/getBorrowRepayDelay/" + borrowNid + "/" + borrowApr + "/" + borrowStyle;
        BorrowRepayResponse response = restTemplate.getForEntity(url, BorrowRepayResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * @Description 获取admin查询延期还款计划（分期）
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public BorrowRepayPlanVO getBorrowRepayPlanDelay(String borrowNid, String borrowApr, String borrowStyle) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepayment/getBorrowRepayPlanDelay/" + borrowNid + "/" + borrowApr + "/" + borrowStyle;
        BorrowRepayPlanResponse response = restTemplate.getForEntity(url, BorrowRepayPlanResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * @Description 延期操作
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public Integer updateBorrowRepayDelayDays(String borrowNid, String delayDays) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepayment/updateBorrowRepayDelayDays/" + borrowNid + "/" + delayDays;
        int intUpdFlg = restTemplate.getForEntity(url, Integer.class).getBody();
        return intUpdFlg;
    }

    /**
     * @Description 获取admin产品中心-汇直投-还款明细列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public Integer countBorrowRepaymentInfo(BorrowRepaymentInfoRequset request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepaymentInfo/countBorrowRepaymentInfo";
        AdminBorrowRepaymentInfoResponse response = restTemplate.postForEntity(url, request, AdminBorrowRepaymentInfoResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return 0;
    }

    /**
     * @Description 获取admin产品中心-汇直投-还款明细列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public List<BorrowRepaymentInfoCustomizeVO> selectBorrowRepaymentInfoListForView(BorrowRepaymentInfoRequset request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepaymentInfo/selectBorrowRepaymentInfoListForView";
        AdminBorrowRepaymentInfoResponse response = restTemplate.postForEntity(url, request, AdminBorrowRepaymentInfoResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * @Description 获取admin产品中心-汇直投-还款明细统计
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public BorrowRepaymentInfoCustomizeVO sumBorrowRepaymentInfo(BorrowRepaymentInfoRequset request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepaymentInfo/sumBorrowRepaymentInfo";
        AdminBorrowRepaymentInfoResponse response = restTemplate.postForEntity(url, request, AdminBorrowRepaymentInfoResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * @Description 获取admin产品中心-汇直投-还款明细导出列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public List<BorrowRepaymentInfoCustomizeVO> selectBorrowRepaymentInfoList(BorrowRepaymentInfoRequset request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepaymentInfo/selectBorrowRepaymentInfoList";
        AdminBorrowRepaymentInfoResponse response = restTemplate.postForEntity(url, request, AdminBorrowRepaymentInfoResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * @Description 获取admin产品中心-汇直投-还款明细列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public Integer countBorrowRepaymentInfoList(BorrowRepaymentInfoListRequset request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepaymentInfoList/countBorrowRepaymentInfoList";
        AdminBorrowRepaymentInfoListResponse response = restTemplate.postForEntity(url, request, AdminBorrowRepaymentInfoListResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return 0;
    }

    /**
     * @Description 获取admin产品中心-汇直投-还款明细列表查询
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public List<BorrowRepaymentInfoListCustomizeVO> selectBorrowRepaymentInfoListList(BorrowRepaymentInfoListRequset request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepaymentInfoList/selectBorrowRepaymentInfoListList";
        AdminBorrowRepaymentInfoListResponse response = restTemplate.postForEntity(url, request, AdminBorrowRepaymentInfoListResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * @Description 获取admin产品中心-汇直投-还款明细列表统计
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public BorrowRepaymentInfoListCustomizeVO sumBorrowRepaymentInfoList(BorrowRepaymentInfoListRequset request) {
        String url = "http://AM-TRADE/am-trade/adminBorrowRepaymentInfoList/sumBorrowRepaymentInfoList";
        AdminBorrowRepaymentInfoListResponse response = restTemplate.postForEntity(url, request, AdminBorrowRepaymentInfoListResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 查找资金明细列表
     *
     * @param request
     * @return
     * @author nixiaoling
     */
    @Override
    public AccountDetailResponse findAccountDetailList(AccountDetailRequest request) {
        AccountDetailResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/adminaccountdetail/accountdetaillist", request, AccountDetailResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 查询交易明细最小的id
     *
     * @param userId
     * @return
     * @author nixiaoling
     */
    @Override
    public AdminAccountDetailDataRepairResponse accountdetailDataRepair(int userId) {
        AdminAccountDetailDataRepairResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/adminaccountdetail/queryaccountdetailidbyuserid/" + userId, AdminAccountDetailDataRepairResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 查询出还款后,交易明细有问题的用户ID
     *
     * @return
     * @author nixiaoling
     */
    @Override
    public AdminAccountDetailDataRepairResponse queryAccountDetailErrorUserList() {
        AdminAccountDetailDataRepairResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/adminaccountdetail/queryaccountdetailerroruserlist", AdminAccountDetailDataRepairResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 根据Id查询此条交易明细
     *
     * @param accountId
     * @return
     * @author nixiaoling
     */
    @Override
    public AccountListResponse selectAccountById(int accountId) {
        AccountListResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/adminaccountdetail/selectaccountbyid/" + accountId, AccountListResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 查询此用户的下一条交易明细
     *
     * @param accountId
     * @param userId
     * @return
     * @author nixiaoling
     */
    @Override
    public AccountListResponse selectNextAccountList(int accountId, int userId) {
        AccountListResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/adminaccountdetail/selectnextaccountlist/" + userId + "/" + accountId, AccountListResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 根据查询用交易类型查询用户操作金额
     *
     * @param tradeValue
     * @return
     * @author nixiaoling
     */
    @Override
    public AccountTradeResponse selectAccountTradeByValue(String tradeValue) {
        AccountTradeResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/adminaccountdetail/selectaccounttradebyvalue/" + tradeValue, AccountTradeResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 更新用户的交易明细
     *
     * @param accountListRequest
     * @return
     * @author nixiaoling
     */
    @Override
    public int updateAccountList(AccountListRequest accountListRequest) {
        int intUpdFlg = restTemplate.
                postForEntity("http://AM-TRADE/am-trade/adminaccountdetail/updateaccountlist", accountListRequest, Integer.class).
                getBody();
        return intUpdFlg;
    }

    /**
     * 查找汇付银行开户记录列表
     *
     * @param request
     * @return
     */
    @Override
    public PushMoneyResponse findPushMoneyList(PushMoneyRequest request) {
        PushMoneyResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/pushMoneyRecord/findPushMoneyRecordList", request, PushMoneyResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 计划退出查询判断标的是否还款
     * BorrowNidEqualTo(borrowNid)
     * ApiTypeEqualTo(1)
     * StatusNotEqualTo(6);
     *
     * @param borrowNid
     * @return
     */
    @Override
    public List<BorrowApicronVO> selectBorrowApicronListByBorrowNid(String borrowNid) {
        BorrowApicronResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrowApicron/selectBorrowApicronListByBorrowNid/" + borrowNid,
                BorrowApicronResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据项目编号取得borrowTender表
     *
     * @param nid
     * @return
     */
    @Override
    public List<BorrowTenderVO> getBorrowTenderListByNid(String nid) {
        String url = "http://AM-TRADE/am-trade/borrowTender/getBorrowTenderListByNid/" + nid;
        BorrowTenderResponse response = restTemplate.getForEntity(url, BorrowTenderResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查找汇付银行开户记录列表
     *
     * @param request
     * @return
     */
    @Override
    public Integer getCountTenderCommissionByTenderIdAndTenderType(TenderCommissionRequest request) {

        TenderCommissionResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/tenderCommission/countTenderCommissionByTenderIdAndTenderType/",
                        request, TenderCommissionResponse.class).getBody();
        if (response != null) {
            return response.getCount();
        }
        return null;
    }

    /**
     * 添加提成数据
     *
     * @param request
     * @return
     */
    @Override
    public int saveTenderCommission(TenderCommissionRequest request) {
        String url = "http://AM-MARKET/am-trade/tenderCommission/insertTenderCommission/";
        TenderCommissionResponse response = restTemplate.postForEntity(url, request, TenderCommissionResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getFlag();
        }
        return 0;
    }

    /**
     * 更新借款API表
     *
     * @param apicornId
     * @return
     */
    @Override
    public Integer updateBorrowApicronByPrimaryKeySelective(String apicornId) {
        String url = "http://AM-MARKET/am-market/activity/updateBorrowApicronByPrimaryKeySelective/";
        BorrowApicronResponse response = restTemplate.postForEntity(url, apicornId, BorrowApicronResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getFlag();
        }
        return 0;
    }

    /**
     * 獲取銀行開戶信息
     *
     * @param userId
     * @return
     */
    @Override
    public BankOpenAccountVO getBankOpenAccount(Integer userId) {
        BankOpenAccountResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/bankopen/selectById/" + userId, BankOpenAccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据筛选条件查询银行账务明细list
     *
     * @param
     * @return
     */
    @Override
    public List<BankAleveVO> queryBankAleveList(BankAleveRequest request) {
        BankAleveResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/bankaleve/selectBankAleveInfoList/", request, BankAleveResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据筛选条件查询银行账务明细list
     *
     * @param
     * @return
     */
    @Override
    public List<BankEveVO> queryBankEveList(BankEveRequest request) {
        BankEveResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/bankaleve/selectBankEveInfoList/", request, BankEveResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public DataCenterCouponResponse getDataCenterCouponList(DadaCenterCouponRequestBean requestBean, String type) {
        if (requestBean != null) {
            requestBean.setType(type);
        }
        return restTemplate.postForObject("http://AM-TRADE/am-trade/datacenter/coupon/getdatacentercouponlist",
                requestBean, DataCenterCouponResponse.class);
    }

    @Override
    public BorrowCommonResponse moveToInfoAction(BorrowCommonRequest borrowCommonRequest) {
        BorrowCommonResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/borrowcommon/infoAction", borrowCommonRequest, BorrowCommonResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public BorrowCommonResponse insertAction(BorrowCommonRequest borrowCommonRequest) throws Exception {
        BorrowCommonResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/borrowcommon/insertAction", borrowCommonRequest, BorrowCommonResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }


    @Override
    public int isExistsUser(String userId) {
        int response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/borrowcommon/isExistsUser/" + userId, Integer.class)
                .getBody();
        return response;
    }


    @Override
    public String getBorrowPreNid() {
        String response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/borrowcommon/getBorrowPreNid", String.class)
                .getBody();
        return response;
    }

    @Override
    public String getXJDBorrowPreNid() {
        String response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/borrowcommon/getXJDBorrowPreNid", String.class)
                .getBody();
        return response;
    }

    @Override
    public boolean isExistsBorrowPreNidRecord(String borrowPreNid) {
        boolean response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/borrowcommon/isExistsBorrowPreNidRecord/" + borrowPreNid, boolean.class)
                .getBody();
        return response;
    }

    @Override
    public String getBorrowServiceScale(BorrowCommonRequest borrowCommonRequest) {
        String response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/borrowcommon/isExistsBorrowPreNidRecord", borrowCommonRequest, String.class)
                .getBody();
        return response;
    }

    @Override
    public BorrowCommonResponse getProductTypeAction(String instCode) {
        BorrowCommonResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/borrowcommon/getProductTypeAction/" + instCode, BorrowCommonResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public int isEntrustedExistsUser(String userName) {
        int response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/borrowcommon/isEntrustedExistsUser/" + userName, int.class)
                .getBody();
        return response;
    }

    @Override
    public List<DataCenterCouponCustomizeVO> getRecordListJX(DataCenterCouponCustomizeVO dataCenterCouponCustomize) {
        DataCenterCouponCustomizeResponse response = restTemplate.postForObject(
                "http://AM-TRADE/am-trade/couponUser/get_record_list_jx", dataCenterCouponCustomize,
                DataCenterCouponCustomizeResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public HjhPlanResponse getHjhPlanListByParamWithoutPage(PlanListRequest form) {
        HjhPlanResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/planList/getHjhPlanListByParamWithoutPage", form, HjhPlanResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public HjhAccedeResponse canCancelAuth(Integer userId) {
        HjhAccedeResponse response = restTemplate.
                getForEntity("http://AM-TRADE/am-trade/hjhAccede/canCancelAuth/" + userId, HjhAccedeResponse.class).
                getBody();
        return response;
    }

    @Override
    public BankMerchantAccountVO getBankMerchantAccount(String accountCode) {
        BankMerchantAccountResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/account/getbankmerchantaccount/" + accountCode,
                BankMerchantAccountResponse.class).getBody();
        if (response == null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<DataCenterCouponCustomizeVO> getRecordListDJ(DataCenterCouponCustomizeVO dataCenterCouponCustomize) {
        DataCenterCouponCustomizeResponse response = restTemplate.postForObject(
                "http://AM-TRADE/am-trade/couponUser/get_record_list_dj", dataCenterCouponCustomize,
                DataCenterCouponCustomizeResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public BankMerchantAccountInfoVO getBankMerchantAccountInfoByCode(String accountCode) {
        BankMerchantAccountInfoResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/account/getBankMerchantAccountInfo/" + accountCode,
                BankMerchantAccountInfoResponse.class).getBody();
        if (response == null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public void updateBankMerchantAccountIsSetPassword(String accountId, int flag) {
        Integer response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/account/updateBankMerchantAccountIsSetPassword/" + accountId + "/" + flag, Integer.class)
                .getBody();
    }


    /**
     * 汇计划 - 计划还款 - 统计
     *
     * @param repayRequest
     * @return
     * @Author : huanghui
     */
    @Override
    public Integer getRepayCount(HjhRepayRequest repayRequest) {
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/hjhRepay/getRepayCount", repayRequest, Integer.class).getBody();
    }

    /**
     * 获取汇计划 -  计划还款(计划退出)列表
     *
     * @param request
     * @return
     * @Author : huanghui
     */
    @Override
    public List<HjhRepayVO> selectByExample(HjhRepayRequest request) {
        HjhRepayResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/hjhRepay/hjhRepayList", request, HjhRepayResponse.class).getBody();

        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 转让详情列表
     *
     * @param request
     * @return
     * @Author : huanghui
     */
    @Override
    public DayCreditDetailResponse hjhDayCreditDetailList(DayCreditDetailRequest request) {
        DayCreditDetailResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/hjhDayCreditDetail/hjhDayCreditList/", request, DayCreditDetailResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 汇计划 -> 资金计划 -> 复投原始标的 列表
     * @param data
     * @param planNid
     * @return
     * @Author : huanghui
     */
    @Override
    public List<HjhReInvestDetailVO> getHjhReInvestDetailList(String data, String planNid) {
        HjhReInvestDetailResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/hjhPlanCapital/hjhPlanCapitalReinvestInfo/" + data + "/"+ planNid, HjhReInvestDetailResponse.class).getBody();

        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 汇计划 -> 资金计划 -> 复投原始标的 总数
     * @param data
     * @param planNid
     * @return
     */
    @Override
    public Integer getHjhReInvestDetailListCount(String data, String planNid) {
        return restTemplate.getForEntity("http://AM-TRADE/am-trade/hjhPlanCapital/hjhPlanCapitalReinvestCount/" + data + "/"+ planNid, Integer.class).getBody();
    }

    @Override
    public AdminBorrowFlowResponse selectBorrowFlowList(AdminBorrowFlowRequest adminRequest) {
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/borrowflow/selectBorrowFlowList", adminRequest, AdminBorrowFlowResponse.class)
                .getBody();
    }

    @Override
    public Integer getBankMerchantAccountListByOrderId(String orderId) {
        String url = "http://AM-TRADE/am-trade/platformtransfer/getBnakMerchantAccountList/" + orderId;
        BankMerchantAccountResponse response = restTemplate.getForEntity(url, BankMerchantAccountResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getRecordTotal();
        }
        return null;
    }

    /**
     * 查询配置中心平台账户配置 余额监控
     *
     * @param adminRequest
     * @return
     */
    @Override
    public AdminAccountBalanceMonitoringResponse selectAccountBalanceMonitoringByPage(AdminAccountBalanceMonitoringRequest adminRequest) {
        String url = "http://AM-TRADE/am-trade/config/accountbalance/selectAccountBalanceMonitoringByPage";
        AdminAccountBalanceMonitoringResponse response = restTemplate.postForEntity(url, adminRequest, AdminAccountBalanceMonitoringResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response;
        }
        return null;
    }

    /**
     * 编辑画面检索列表
     *
     * @param adminRequest
     * @return
     */
    @Override
    public AdminAccountBalanceMonitoringResponse selectaccountBalanceMonitoringById(AdminAccountBalanceMonitoringRequest adminRequest) {
        String url = "http://AM-TRADE/am-trade/config/accountbalance/selectaccountBalanceMonitoringById";
        AdminAccountBalanceMonitoringResponse response = restTemplate.postForEntity(url, adminRequest, AdminAccountBalanceMonitoringResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response;
        }
        return null;
    }

    /**
     * 查询配置中心平台账户配置 余额监控
     *
     * @param adminRequest
     * @return
     */
    @Override
    public List<AccountBalanceMonitoringRequestBean> searchMerchantAccountList(AdminAccountBalanceMonitoringRequest adminRequest) {
        String url = "http://AM-TRADE/am-trade/config/accountbalance/selectAccountBalanceMonitoringByPage";
        AdminAccountBalanceMonitoringResponse response = restTemplate.postForEntity(url, adminRequest, AdminAccountBalanceMonitoringResponse.class).getBody();
        List<AccountBalanceMonitoringRequestBean> res = new ArrayList<AccountBalanceMonitoringRequestBean>();
        if (Validator.isNotNull(response)) {
            List<MerchantAccountVO> merchantAccountVOList = response.getResultList();
            BeanUtils.copyProperties(merchantAccountVOList, res);
            return res;
        }
        return null;
    }

    /**
     * 数据更新 平台账户设置
     *
     * @param adminRequest
     * @return
     */
    @Override
    public AdminAccountBalanceMonitoringResponse updateMerchantAccountList(List<AccountBalanceMonitoringRequestBean> adminRequest) {
        String url = "http://AM-TRADE/am-trade/config/accountbalance/updateMerchantAccountList";
        AdminAccountBalanceMonitoringResponse response = restTemplate.postForEntity(url, adminRequest, AdminAccountBalanceMonitoringResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response;
        }
        return null;
    }

    /**
     * 查询债转列表
     *
     * @author zhangyk
     * @date 2018/7/10 16:26
     */
    @Override
    public List<BorrowCreditVO> getBorrowCreditList(BorrowCreditAmRequest request) {
        AdminBorrowCreditResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/borrowCredit/getBorrowCreditList4admin", request, AdminBorrowCreditResponse.class).getBody();
        if (AdminResponse.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询债转count
     *
     * @author zhangyk
     * @date 2018/7/10 16:26
     */
    @Override
    public Integer getBorrowCreditCount(BorrowCreditAmRequest request) {
        AdminBorrowCreditResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/borrowCredit/countBorrowCreditList4admin", request, AdminBorrowCreditResponse.class).getBody();
        if (AdminResponse.isSuccess(response)) {
            return response.getRecordTotal();
        }
        return null;
    }

    /**
     * 查询合计数据
     *
     * @author zhangyk
     * @date 2018/7/10 16:27
     */
    @Override
    public BorrowCreditSumVO getBorrwoCreditTotalSum(BorrowCreditAmRequest request) {
        AdminBorrowCreditResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/borrowCredit/getBorrowCreditTotalCount", request, AdminBorrowCreditResponse.class).getBody();
        if (AdminResponse.isSuccess(response)) {
            return response.getSumVO();
        }
        return null;
    }


    @Override
    public BorrowCreditInfoSumVO sumBorrowCreditInfoData(BorrowCreditAmRequest request) {
        AdminBorrowCreditInfoResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/borrowCredit/sumBorrowCreditInfo4admin", request, AdminBorrowCreditInfoResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getSumData();
        }
        return null;
    }

    @Override
    public Integer updateAccountManage(AccountVO accountVO) {
        String url = "http://AM-TRADE/am-trade/bankAccountmanage/updateaccount";
        Integer result = restTemplate.postForEntity(url, accountVO, Integer.class).getBody();
        return result;
    }

    @Override
    public String updateAccountCheck(Integer userId, String startTime, String endTime) {
        AdminBankAccountCheckCustomizeVO adminBankAccountCheckCustomizeVO = new AdminBankAccountCheckCustomizeVO();
        adminBankAccountCheckCustomizeVO.setUserId(userId);
        adminBankAccountCheckCustomizeVO.setStartDate(startTime);
        adminBankAccountCheckCustomizeVO.setEndDate(endTime);
        String url = "http://AM-TRADE/am-trade/bankaccountmanage/updateAccountCheck/";
        String result = restTemplate.postForEntity(url, adminBankAccountCheckCustomizeVO, String.class).getBody();
        if (result != null) {
            return result;
        }
        return null;
    }

    /**
     * 项目类型
     *
     * @return
     */
    @Override
    public List<BorrowProjectTypeVO> borrowProjectTypeList(String borrowTypeCd) {
        return restTemplate.getForEntity("http://AM-TRADE/am-trade/config/borrowflow/borrowProjectTypeList/" + borrowTypeCd, List.class)
                .getBody();
    }

    /**
     * 资金来源
     *
     * @param instCode
     * @return
     */
    @Override
    public List<HjhInstConfigVO> hjhInstConfigList(String instCode) {
        return restTemplate.getForEntity("http://AM-TRADE/am-trade/config/borrowflow/hjhInstConfigList", List.class)
                .getBody();
    }

    /**
     * 根据表的类型,期数,项目类型检索管理费件数
     *
     * @param instCode assetType
     * @return
     */
    @Override
    public int countRecordByPK(String instCode, Integer assetType) {
        return restTemplate.getForEntity("http://AM-TRADE/am-trade/config/borrowflow/countRecordByPK/" + instCode + "/" + assetType, int.class)
                .getBody();
    }

    /**
     * 根据资金来源查询产品类型
     *
     * @param instCode
     * @return
     */
    @Override
    public List<HjhAssetTypeVO> hjhAssetTypeList(String instCode) {
        HjhAssetTypeResponse response= restTemplate.getForEntity("http://AM-TRADE/am-trade/config/borrowflow/hjhAssetTypeList/" + instCode, HjhAssetTypeResponse.class)
                .getBody();
        if(response == null  ){
            return null;
        }
        return response.getResultList();
    }

    /**
     * 详情
     *
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBorrowFlowResponse selectBorrowFlowInfo(AdminBorrowFlowRequest adminRequest) {
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/borrowflow/info", adminRequest, AdminBorrowFlowResponse.class)
                .getBody();
    }

    /**
     * 添加
     *
     * @param adminRequest
     * @return
     */
    @Override
    public void insertRecord(AdminBorrowFlowRequest adminRequest) {
        restTemplate.postForEntity("http://AM-TRADE/am-trade/config/borrowflow/insertRecord", adminRequest, AdminBorrowFlowResponse.class)
                .getBody();
    }

    /**
     * 修改
     *
     * @param adminRequest
     * @return
     */
    @Override
    public void updateRecord(AdminBorrowFlowRequest adminRequest) {
        restTemplate.postForEntity("http://AM-TRADE/am-trade/config/borrowflow/updateRecord", adminRequest, AdminBorrowFlowResponse.class)
                .getBody();
    }

    /**
     * 删除
     *
     * @param adminRequest
     * @return
     */
    @Override
    public void deleteRecord(AdminBorrowFlowRequest adminRequest) {
        restTemplate.postForEntity("http://AM-TRADE/am-trade/config/borrowflow/deleteRecord", adminRequest, AdminBorrowFlowResponse.class)
                .getBody();
    }

    /**
     * 查询列表
     *
     * @param adminRequest
     * @return
     */
    @Override
    public BorrowProjectTypeResponse selectProjectTypeList(BorrowProjectTypeRequest adminRequest) {
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/projecttype/selectProjectTypeList", adminRequest, BorrowProjectTypeResponse.class)
                .getBody();
    }

    /**
     * 查询项目类型 详情
     *
     * @param adminRequest
     * @return
     */
    @Override
    public BorrowProjectTypeResponse selectProjectTypeRecord(BorrowProjectTypeRequest adminRequest) {
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/projecttype/selectProjectTypeRecord", adminRequest, BorrowProjectTypeResponse.class)
                .getBody();
    }

    /**
     * 根据主键判断汇直投项目类型维护中数据是否存在
     *
     * @return
     */
    @Override
    public boolean isExistsRecord(BorrowProjectTypeVO record) {
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/projecttype/isExistsRecord", record, boolean.class)
                .getBody();
    }

    /**
     * 获取单个汇直投项目类型维护
     *
     * @return
     */
    @Override
    public BorrowProjectTypeVO getRecord(BorrowProjectTypeVO record) {
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/projecttype/getRecord", record, BorrowProjectTypeVO.class)
                .getBody();
    }

    /**
     * 根据项目编号查询还款方式
     *
     * @param str
     */
    @Override
    public List<BorrowProjectRepayVO> selectRepay(String str) {
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/projecttype/selectRepay", str, List.class)
                .getBody();
    }

    /**
     * 查询类型表
     */
    @Override
    public List<BorrowStyleVO> selectStyles() {
        return restTemplate.getForEntity("http://AM-TRADE/am-trade/config/projecttype/selectStyles", List.class)
                .getBody();
    }

    /**
     * 汇直投项目类型维护插入
     *
     * @param record
     */
    @Override
    public BorrowProjectTypeResponse insertRecord(BorrowProjectTypeRequest record) {
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/projecttype/insertRecord", record, BorrowProjectTypeResponse.class)
                .getBody();
    }

    /**
     * 汇直投项目类型维护插入
     *
     * @param record
     */
    @Override
    public BorrowProjectTypeResponse updateRecord(BorrowProjectTypeRequest record) {
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/projecttype/updateRecord", record, BorrowProjectTypeResponse.class)
                .getBody();
    }

    /**
     * 汇直投项目类型维护删除
     *
     * @param adminRequest
     */
    @Override
    public BorrowProjectTypeResponse deleteProjectType(BorrowProjectTypeRequest adminRequest) {
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/projecttype/deleteProjectType", adminRequest, BorrowProjectTypeResponse.class)
                .getBody();
    }

    /**
     * 检查项目名称唯一性
     *
     * @param borrowCd
     * @return
     */
    @Override
    public int borrowCdIsExists(BorrowProjectTypeRequest borrowCd) {
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/projecttype/borrowCdIsExists", borrowCd, Integer.class)
                .getBody();
    }

    /**
     * 查询配置中心还款方式
     *
     * @return
     */
    @Override
    public AdminBorrowStyleResponse borrowStyelInit(AdminBorrowStyleRequest adminRequest) {
        String url = "http://AM-TRADE/am-trade/config/borrowstyle/selectBorrowStyleListByPage";
        return restTemplate.postForEntity(url, adminRequest, AdminBorrowStyleResponse.class).getBody();
    }

    /**
     * 根据id查询还款方式
     *
     * @return
     */
    @Override
    public AdminBorrowStyleResponse searchBorrowStyleInfo(AdminBorrowStyleRequest adminRequest) {
        String url = "http://AM-TRADE/am-trade/config/borrowstyle/searchBorrowStyleInfo";
        return restTemplate.postForEntity(url, adminRequest, AdminBorrowStyleResponse.class).getBody();
    }

    /**
     * 保存还款方式
     *
     * @return
     */
    @Override
    public AdminBorrowStyleResponse insertBorrowStyle(AdminBorrowStyleRequest adminRequest) {
        String url = "http://AM-TRADE/am-trade/config/borrowstyle/insertBorrowStyle";
        return restTemplate.postForEntity(url, adminRequest, AdminBorrowStyleResponse.class).getBody();
    }

    /**
     * 修改还款方式
     *
     * @return
     */
    @Override
    public AdminBorrowStyleResponse updateBorrowStyle(AdminBorrowStyleRequest adminRequest) {
        String url = "http://AM-TRADE/am-trade/config/borrowstyle/updateBorrowStyle";
        return restTemplate.postForEntity(url, adminRequest, AdminBorrowStyleResponse.class).getBody();
    }

    /**
     * 根据id删除还款方式
     *
     * @return
     */
    @Override
    public AdminBorrowStyleResponse deleteBorrowStyle(Integer id) {
        String url = "http://AM-TRADE/am-trade/config/borrowstyle/deleteBorrowStyle";
        return restTemplate.postForEntity(url, id, AdminBorrowStyleResponse.class).getBody();
    }

    /**
     * 修改还款方式状态
     *
     * @return
     */
    @Override
    public AdminBorrowStyleResponse modifyBorrowStyle(Integer id) {
        String url = "http://AM-TRADE/am-trade/config/borrowstyle/modifyBorrowStyle";
        return restTemplate.postForEntity(url, id, AdminBorrowStyleResponse.class).getBody();
    }

    /**
     * 根据主键判断权限维护中权限是否存在
     *
     * @return
     */
    @Override
    public boolean validatorFieldCheck(AdminBorrowStyleRequest adminRequest) {
        String url = "http://AM-TRADE/am-trade/config/borrowstyle/validatorFieldCheck";
        return restTemplate.postForEntity(url, adminRequest, boolean.class).getBody();
    }

    /**
     * 获取优惠券发行列表
     *
     * @param couponConfigRequest
     * @return
     */
    @Override
    public CouponConfigCustomizeResponse getRecordList(CouponConfigRequest couponConfigRequest) {
        String url = "http://AM-TRADE/am-trade/couponConfig/getRecordList";
        CouponConfigCustomizeResponse response = restTemplate.postForEntity(url, couponConfigRequest, CouponConfigCustomizeResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 获取编辑页信息
     *
     * @param couponConfigRequest
     * @return
     */
    @Override
    public CouponConfigResponse getCouponConfig(CouponConfigRequest couponConfigRequest) {
        String url = "http://AM-TRADE/am-trade/couponConfig/getCouponConfig";
        CouponConfigResponse response = restTemplate.postForEntity(url, couponConfigRequest, CouponConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 保存修改信息
     *
     * @param request
     * @return
     */
    @Override
    public CouponConfigResponse saveCouponConfig(CouponConfigRequest request) {
        String url = "http://AM-TRADE/am-trade/couponConfig/saveCouponConfig";
        CouponConfigResponse response = restTemplate.postForEntity(url, request, CouponConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 添加发行信息
     *
     * @param couponConfigRequest
     * @return
     */
    @Override
    public CouponConfigResponse insertAction(CouponConfigRequest couponConfigRequest) {
        String url = "http://AM-TRADE/am-trade/couponConfig/insertCouponConfig";
        CouponConfigResponse response = restTemplate.postForEntity(url, couponConfigRequest, CouponConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 删除发行信息
     *
     * @param couponConfigRequest
     * @return
     */
    @Override
    public CouponConfigResponse deleteAction(CouponConfigRequest couponConfigRequest) {
        String url = "http://AM-TRADE/am-trade/couponConfig/deleteCouponConfig";
        CouponConfigResponse response = restTemplate.postForEntity(url, couponConfigRequest, CouponConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 获取审核信息
     *
     * @param ccfr
     * @return
     */
    @Override
    public CouponConfigResponse getAuditInfo(CouponConfigRequest ccfr) {
        String url = "http://AM-TRADE/am-trade/couponConfig/getAuditInfo";
        CouponConfigResponse response = restTemplate.postForEntity(url, ccfr, CouponConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 修改审核信息
     *
     * @param couponConfigRequest
     * @return
     */
    @Override
    public CouponConfigResponse updateAuditInfo(CouponConfigRequest couponConfigRequest) {
        String url = "http://AM-TRADE/am-trade/couponConfig/updateAuditInfo";
        CouponConfigResponse response = restTemplate.postForEntity(url, couponConfigRequest, CouponConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 根据优惠券编号查询
     *
     * @param cur
     * @return
     */
    @Override
    public CouponUserResponse getIssueNumber(CouponUserRequest cur) {
        String url = "http://AM-TRADE/am-trade/couponUser/getIssueNumber";
        CouponUserResponse response = restTemplate.postForEntity(url, cur, CouponUserResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse countRecordHzt(CouponTenderRequest couponTenderRequest) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hztcountcoupontender", couponTenderRequest, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse queryInvestTotalHzt(CouponTenderRequest couponTenderRequest) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hztquerynvesttotal", couponTenderRequest, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getRecordListHzt(CouponTenderRequest couponTenderRequest) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hztgetrecordlist", couponTenderRequest, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getCouponTenderDetailCustomize(Map<String, Object> paramMap) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hztcoupontenderdetail", paramMap, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getCouponRecoverCustomize(Map<String, Object> paramMap) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hztcouponrecover", paramMap, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse countRecordHjh(CouponTenderRequest couponTenderRequest) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hjhcountcoupontender", couponTenderRequest, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse queryInvestTotalHjh(CouponTenderRequest couponTenderRequest) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hjhquerynvesttotal", couponTenderRequest, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getRecordListHjh(CouponTenderRequest couponTenderRequest) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hjhgetrecordlist", couponTenderRequest, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getHjhCouponTenderDetailCustomize(Map<String, Object> paramMap) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hjhcoupontenderdetail", paramMap, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getHjhCouponRecoverCustomize(Map<String, Object> paramMap) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hjhcouponrecover", paramMap, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse countRecordHztDJ(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/countrecordhztdj", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getRecordListHztDJ(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/getrecordlisthztdj", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse queryHztInvestTotal(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/queryhztinvesttotal", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse queryHztRecoverInterestTotle(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/queryhztrecoverinteresttotle", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse countRecordHztTY(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/countrecordhztty", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getRecordListHztTY(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/getrecordlisthztty", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse countRecordHztJX(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/countrecordhztjx", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getRecordListHztJX(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/getrecordlisthztjx", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse countRecordHjhDJ(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/countrecordhjhdj", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getRecordListHjhDJ(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/getrecordlisthjhdj", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse queryHjhInvestTotal(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/queryhjhinvesttotal", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse queryHjhRecoverInterestTotle(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/queryhjhrecoverinteresttotle", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse countRecordHjhTY(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/countrecordhjhty", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getRecordListHjhTY(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/getrecordlisthjhty", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse countRecordHjhJX(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/countrecordhjhjx", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public CouponTenderResponse getRecordListHjhJX(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/getrecordlisthjhjx", couponBackMoneyCustomize, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 指定指端检索 计划还款列表
     *
     * @param accedeOrderId
     * @return
     * @Author : huanghui
     */
    @Override
    public List<HjhRepayVO> selectByAccedeOrderId(String accedeOrderId) {
        HjhRepayResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/hjhRepay/hjhRepaymentDetails/" + accedeOrderId, HjhRepayResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }


    @Override
    public Boolean updateAccountCallbackRecharge(Map<String, Object> params) {
        String url = "http://AM-TRADE/am-trade/platformtransfer/updataAccountByRechargeCallback";
        logger.info("圈存调用原子层req = [{}]", JSON.toJSONString(params));
        BankMerchantAccountResponse response = restTemplate.postForEntity(url, params, BankMerchantAccountResponse.class).getBody();
        logger.info("圈存调用原子层res = [{}]", JSON.toJSONString(response));
        if (Response.isSuccess(response)) {
            return response.getSuccessFlag();
        }
        return false;
    }

    @Override
    public List<HjhInstConfigVO> selectHjhInstConfigByInstCode(String instCode) {
        HjhInstConfigResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/hjhPlan/selectHjhInstConfigByInstCode/" + instCode, HjhInstConfigResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }


    /**
     * 更新明细表为失败状态
     *
     * @author zhangyk
     * @date 2018/8/8 10:22
     */
    @Override
    public Boolean updateBankAccountListFailByOrderId(String orderId) {
        String url = "http://AM-TRADE/am-trade/platformtransfer/updateMerchantAccountListFail/" + orderId;
        BankMerchantAccountResponse response = restTemplate.getForEntity(url, BankMerchantAccountResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getSuccessFlag();
        }
        return false;
    }

    /**
     * 汇计划提成列表查询
     *
     * @param form
     * @return HjhCommissionResponse
     */
    @Override
    public HjhCommissionResponse selectHjhCommissionList(HjhCommissionRequest form) {
        HjhCommissionResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/hjhCommission/selectHjhCommissionList", form,
                        HjhCommissionResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public HjhReInvestDebtResponse hjhReInvestDebtList(HjhReInvestDebtRequest request) {
        HjhReInvestDebtResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/hjhReInvestDebt/hjhReInvestDebtList", request, HjhReInvestDebtResponse.class).getBody();

        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public HjhReInvestDetailResponse getHjhReInvestDetailList(HjhReInvestDetailRequest request) {
        HjhReInvestDetailResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/hjhPlanCapital/hjhPlanCapitalReinvestInfo", request, HjhReInvestDetailResponse.class).getBody();

        if (response != null) {
            return response;
        }
        return null;
    }


    /**
     * 查询配置中心保证金配置
     *
     * @return
     */
    @Override
    public AdminInstConfigDetailResponse instConfigInit(AdminInstConfigListRequest adminRequest) {
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/instconfig/list", adminRequest, AdminInstConfigDetailResponse.class)
                .getBody();
    }

    /**
     * 查询详情
     *
     * @param adminRequest
     * @return
     */
    @Override
    public AdminInstConfigDetailResponse searchInstConfigInfo(AdminInstConfigListRequest adminRequest) {
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/instconfig/info", adminRequest, AdminInstConfigDetailResponse.class)
                .getBody();
    }

    /**
     * 编辑保存保证金配置
     *
     * @return
     */
    @Override
    public AdminInstConfigListResponse saveInstConfig(AdminInstConfigListRequest req) {
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/instconfig/insert", req, AdminInstConfigListResponse.class)
                .getBody();
    }

    /**
     * 修改保证金配置
     *
     * @return
     */
    @Override
    public AdminInstConfigListResponse updateInstConfig(AdminInstConfigListRequest req) {
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/instconfig/update", req, AdminInstConfigListResponse.class)
                .getBody();
    }

    /**
     * 删除保证金配置
     *
     * @return
     */
    @Override
    public AdminInstConfigListResponse deleteInstConfig(AdminInstConfigListRequest req) {
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/instconfig/delete", req, AdminInstConfigListResponse.class)
                .getBody();
    }

    @Override
    public FddTempletCustomizeResponse selectFddTempletList(ProtocolsRequestBean request) {
        return restTemplate.postForObject("http://AM-TRADE/am-trade/protocol/selectfddtempletlist", request, FddTempletCustomizeResponse.class);
    }

    @Override
    public FddTempletCustomizeResponse insertAction(ProtocolsRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-TRADE/am-trade/protocol/insertaction", requestBean, FddTempletCustomizeResponse.class);
    }

    @Override
    public FddTempletCustomizeResponse updateAction(ProtocolsRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-TRADE/am-trade/protocol/updateaction", requestBean, FddTempletCustomizeResponse.class);
    }

    @Override
    public PushMoneyResponse getRecordList() {
        return restTemplate.getForObject("http://AM-TRADE/am-trade/pushmoney/getrecordlist", PushMoneyResponse.class);
    }

    @Override
    public PushMoneyResponse insertPushMoney(PushMoneyRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-TRADE/am-trade/pushmoney/insertpushmoney", requestBean,
                PushMoneyResponse.class);
    }

    @Override
    public PushMoneyResponse updatePushMoney(PushMoneyRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-TRADE/am-trade/pushmoney/updatepushmoney", requestBean,
                PushMoneyResponse.class);
    }

    /**
     * 查询流程配置中的发标/复审
     *
     * @param adminRequest
     * @return
     */
    @Override
    public BorrowSendTypeResponse selectBorrowSendList(BorrowSendTypeRequest adminRequest) {
        String url = "http://AM-TRADE/am-trade/config/sendtype/list";
        BorrowSendTypeResponse response = restTemplate.postForEntity(url, adminRequest, BorrowSendTypeResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 查询流程配置中的发标/复审页面
     *
     * @param sendCd
     * @return
     */
    @Override
    public BorrowSendTypeVO getBorrowSendInfo(String sendCd) {
        String url = "http://AM-TRADE/am-trade/config/sendtype/info/" + sendCd;
        BorrowSendTypeVO response = restTemplate.getForEntity(url, BorrowSendTypeVO.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 数据插入
     *
     * @param adminRequest
     */
    @Override
    public BorrowSendTypeResponse insertBorrowSend(BorrowSendTypeRequest adminRequest) {
        String url = "http://AM-TRADE/am-trade/config/sendtype/insert";
        BorrowSendTypeResponse response = restTemplate.postForEntity(url, adminRequest, BorrowSendTypeResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 数据修改
     *
     * @param adminRequest
     */
    @Override
    public BorrowSendTypeResponse updateBorrowSend(BorrowSendTypeRequest adminRequest) {
        String url = "http://AM-TRADE/am-trade/config/sendtype/update";
        BorrowSendTypeResponse response = restTemplate.postForEntity(url, adminRequest, BorrowSendTypeResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 删除
     *
     * @param sendCd
     */
    @Override
    public BorrowSendTypeResponse daleteBorrowSend(String sendCd) {
        String url = "http://AM-TRADE/am-trade/config/sendtype/delete/" + sendCd;
        BorrowSendTypeResponse response = restTemplate.getForEntity(url, BorrowSendTypeResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public STZHWhiteListResponse selectSTZHWhiteList(STZHWhiteListRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-TRADE/am-trade/stzfwhiteconfig/selectSTZHWhiteList", requestBean,
                STZHWhiteListResponse.class);
    }

    @Override
    public STZHWhiteListResponse insertSTZHWhiteList(STZHWhiteListRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-TRADE/am-trade/stzfwhiteconfig/insertSTZHWhiteList", requestBean,
                STZHWhiteListResponse.class);
    }

    @Override
    public STZHWhiteListResponse updateSTZHWhiteList(STZHWhiteListRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-TRADE/am-trade/stzfwhiteconfig/updateSTZHWhiteList", requestBean,
                STZHWhiteListResponse.class);
    }


    @Override
    public HjhInstConfigVO selectHjhInstConfig(String instcode) {
        HjhInstConfigResponse response = restTemplate.getForObject(
                "http://AM-TRADE/am-trade/hjhInstConfig/selectInstConfigByInstCode" + instcode,
                HjhInstConfigResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }


    @Override
    public int updateBankMerchantAccountByCode(BankMerchantAccountVO bankMerchantAccount) {
        Integer response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/account/updateBankMerchantAccountByCode", bankMerchantAccount, Integer.class)
                .getBody();
        return response;
    }

    /**
	 * 查询金额总计
	 * @param form
	 * @return
	 */
	@Override
	public HjhCommissionResponse selecthjhCommissionTotal(HjhCommissionRequest form) {
		HjhCommissionResponse response = restTemplate
				.postForEntity("http://AM-TRADE/am-trade/hjhCommission/selecthjhCommissionTotal" ,form,
						HjhCommissionResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

    /**
     * 发起平台账户分佣
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public JSONObject subCommission(SubCommissionRequest request) {
        String url = tradeService + "/subcommission/subCommission";
        SubCommissionResponse response = restTemplate.postForEntity(url, request, SubCommissionResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getJsonObject();
        }
        return null;
    }

    /**
	 * 查询汇计划提成是否已经发放
	 * @param ids
	 * @return
	 */
	@Override
	public TenderCommissionVO queryTenderCommissionByPrimaryKey(int ids) {
		TenderCommissionResponse response = restTemplate
	            .getForEntity("http://AM-TRADE/am-trade/hjhCommission/queryTenderCommissionByPrimaryKey/" + ids, TenderCommissionResponse.class).getBody();
	    if (response != null) {
	        return response.getResult();
	    }
		return null;
	}


    /**
     * 圈提异步回调业务处理
     *
     * @author zhangyk
     * @date 2018/8/8 16:41
     */
    @Override
    public Boolean updateAccountCallbackWithdraw(Map<String, Object> params) {
        String url = "http://AM-TRADE/am-trade/platformtransfer/updataAccountByWithdrawCallback";
        logger.info("圈提调用原子层req = [{}]", JSON.toJSONString(params));
        BankMerchantAccountResponse response = restTemplate.postForEntity(url, params, BankMerchantAccountResponse.class).getBody();
        logger.info("圈提调用原子层res = [{}]", JSON.toJSONString(response));
        if (Response.isSuccess(response)) {
            return response.getSuccessFlag();
        }
        return false;
    }

    /**
     * 批量上传发放优惠券
     *
     * @param params
     * @return
     */
    @Override
    public JSONObject getBatchCoupons(Map<String, String> params) {
        String url = "http://AM-TRADE/am-user/checkCoupon/getBatchCoupons";
        return restTemplate.postForEntity(url, params, JSONObject.class).getBody();
    }

    /**
     * 根据订单号查询提现订单
     *
     * @param nid
     * @param userId
     * @return
     */
    @Override
    public AccountWithdrawVO queryAccountwithdrawByNid(String nid, Integer userId) {
        String url = "http://AM-TRADE/am-trade/account/queryAccountwithdrawByNid/" + nid + "/" + userId;
        AccountWithdrawResponse response = restTemplate.getForEntity(url, AccountWithdrawResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 提现成功后,更新用户账户信息,提现记录
     *
     * @param param
     * @return
     */
    @Override
    public boolean updateAccountAfterWithdraw(Map<String, String> param) {
        String url = "http://AM-TRADE/am-trade/account/updateAccountAfterWithdraw";
        return restTemplate.postForEntity(url, param, Boolean.class).getBody();
    }

    @Override
    public boolean updateAccountAfterWithdrawFail(Integer userId, String nid) {
        String url = "http://AM-TRADE/am-trade/account/updateAccountAfterWithdrawFail/" + userId + "/" + nid;
        return restTemplate.getForEntity(url, Boolean.class).getBody();
    }
    
	/**
	 * 获取部门列表
	 * 此方法后期可以做成基类的方法
	 * @return
	 */
	@Override
	public OADepartmentResponse getCrmDepartmentList(HjhCommissionRequest form) {
		OADepartmentResponse response = restTemplate
				.postForEntity("http://AM-TRADE/am-trade/hjhCommission/getCrmDepartmentList" ,form,
						OADepartmentResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	/**
	 * 直投提成列表count
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
    @Override
    public int getPushMoneyListCount(PushMoneyRequest request) {
        PushMoneyResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/pushMoneyRecord/getPushMoneyListCount" ,request,
                        PushMoneyResponse.class)
                .getBody();
        if (Response.isSuccess(response)) {
            return response.getCount();
        }
        return 0;
    }

    /**
     * 直投提成列表list
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<PushMoneyVO> searchPushMoneyList(PushMoneyRequest request) {
        PushMoneyResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/pushMoneyRecord/searchPushMoneyList" ,request,
                        PushMoneyResponse.class)
                .getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 直投提成列表查询总金额
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public Map<String, Object> queryPushMoneyTotle(PushMoneyRequest request) {
        PushMoneyResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/pushMoneyRecord/queryPushMoneyTotle" ,request,
                        PushMoneyResponse.class)
                .getBody();
        if (Response.isSuccess(response)) {
            return response.getMap();
        }
        return null;
    }

    /**
     * 根据userid查询 crm  cuttype
     * @auth sunpeikai
     * @param
     * @return
     */
/*    @Override
    public int queryCrmCuttype(Integer userId) {
        String url = tradeService + "/pushMoneyRecord/queryCrmCuttype/" + userId;
        PushMoneyResponse response = restTemplate.getForEntity(url,PushMoneyResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCuttype();
        }
        return 0;
    }*/

    /**
     * 发提成包含参数：TenderCommissionVO tenderCommissionVO, BankCallBean resultBean
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int updateTenderCommissionRecord(PushMoneyRequest pushMoneyRequest) {
        String url = tradeService + "/pushMoneyRecord/updateTenderCommissionRecord";
        PushMoneyResponse response = restTemplate.postForEntity(url,pushMoneyRequest,PushMoneyResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCnt();
        }
        return 0;
    }
    /**
     * 分页查询列表
     * @param request
     * @return
     */
    @Override
    public AdminSubConfigResponse selectSubConfigListByParam(AdminSubConfigRequest request){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/subconfig/list",request, AdminSubConfigResponse.class)
                .getBody();
    }
    /**
     * 页面详情
     * @param request
     * @return
     */
    @Override
    public AdminSubConfigResponse selectSubConfigInfo(AdminSubConfigRequest request){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/subconfig/info",request, AdminSubConfigResponse.class)
                .getBody();
    }

    /**
     *  分账名单配置添加
     * @param request
     * @return
     */
    @Override
    public AdminSubConfigResponse insertSubConfig(AdminSubConfigRequest request){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/subconfig/insert",request, AdminSubConfigResponse.class)
                .getBody();
    }
    /**
     * 分账名单配置修改
     * @param request
     * @return
     */
    @Override
    public AdminSubConfigResponse updateSubConfig(AdminSubConfigRequest request){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/subconfig/update",request, AdminSubConfigResponse.class)
                .getBody();
    }
    /**
     * 分账名单配置删除
     * @param request
     * @return
     */
    @Override
    public AdminSubConfigResponse deleteSubConfig(AdminSubConfigRequest request){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/subconfig/delete",request, AdminSubConfigResponse.class)
                .getBody();
    }
    /**
     * 查询配置中心操作日志配置
     * @return
     */
    @Override
    public List<FeerateModifyLogVO> selectInstAndAssertType(AdminOperationLogRequest adminRequest){
        return restTemplate.postForEntity("http://AM-TRADE/am-trade/config/operationlog/selectInstAndAssertType", adminRequest, AdminOperationLogResponse.class).getBody().getResultList();
    }
    /**
     * 产品类型   asset_type  asset_type_name资产类型名称
     *
     * @param
     * @return List<HjhAssetTypeVO>
     */
    @Override
    public List<HjhAssetTypeVO> getHjhAssetType(){
        String url = "http://AM-TRADE/am-trade/config/operationlog/getHjhAssetType";
        return restTemplate.getForEntity(url,List.class).getBody();
    }

    @Override
	    public List<AccountRechargeVO> getAccountRecharge(int userId) {
	        String url = "http://AM-TRADE/am-trade/accountRecharge/getAccountRechargeByUserId/" + userId;
	        AccountRechargeResponse response = restTemplate.getForEntity(url,AccountRechargeResponse.class).getBody();
	        if (response != null) {
	            return response.getResultList();
	        }
	        return null;
	    }

	@Override
	public Integer queryCrmCuttype(Integer userId) {
		TenderCommissionResponse response = restTemplate
	            .getForEntity("http://AM-TRADE/am-trade/hjhCommission/queryCrmCuttype/" + userId, TenderCommissionResponse.class).getBody();
	    if (response != null) {
	        return response.getType();
	    }
		return null;
	}

	@Override
	public Integer updateTenderCommissionRecord(CommissionComboRequest request) {
		String url = "http://AM-TRADE/am-trade/hjhCommission/updateTenderCommissionRecord";
		Integer response = restTemplate.postForEntity(url,request,Integer.class).getBody();
		return response;
	}

	/**
     * 获取优惠券用户列表
     *
     * @param couponUserBeanRequest
     * @return
     */
    @Override
    public CouponUserCustomizeResponse searchList(CouponUserBeanRequest couponUserBeanRequest) {
        String url = "http://AM-TRADE/am-trade/adminCouponUser/getCouponUserList";
        CouponUserCustomizeResponse response = restTemplate.postForEntity(url, couponUserBeanRequest, CouponUserCustomizeResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 根据id删除一条优惠券
     *
     * @param id
     * @return
     */
    @Override
    public CouponUserCustomizeResponse deleteById(int id, String remark, String userId) {
        String url = "http://AM-TRADE/am-trade/adminCouponUser/deleteCouponUser/" + id + remark + userId;
        CouponUserCustomizeResponse response = restTemplate.getForEntity(url, CouponUserCustomizeResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }


    /**
     * 获取优惠券配置列表
     *
     * @param request
     * @return
     */
    @Override
    public List<CouponConfigCustomizeVO> getCouponConfigCustomize(CouponConfigRequest request) {
        String url = "http://AM-TRADE/am-trade/couponConfig/adminCouponConfig";
        CouponConfigCustomizeResponse response = restTemplate.postForEntity(url, request, CouponConfigCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据优惠券编码查询优惠券
     *
     * @param couponCode
     * @return
     */
    @Override
    public CouponConfigResponse selectCouponConfig(String couponCode) {
        String url = "http://AM-TRADE/am-trade/couponConfig/selectCouponConfig/" + couponCode;
        CouponConfigResponse configResponse = restTemplate.getForEntity(url, CouponConfigResponse.class).getBody();
        if (configResponse != null) {
            return configResponse;
        }
        return null;
    }

    /**
     * 发放一条优惠券
     *
     * @param couponUserRequest
     * @return
     */
    @Override
    public CouponUserResponse insertCouponUser(CouponUserRequest couponUserRequest) {
        String url = "http://AM-TRADE/am-trade/adminCouponUser/insertCouponUser";
        CouponUserResponse response = restTemplate.postForEntity(url, couponUserRequest, CouponUserResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 根据优惠券编码查询用户优惠券
     * @param couponCode
     * @return
     */
    @Override
    public CouponUserResponse getCouponUserByCouponCode(String couponCode) {
        String url = "http://AM-TRADE/am-trade/adminCouponUser/getCouponUsrByCouponCode/" + couponCode;
        CouponUserResponse response = restTemplate.getForEntity(url,CouponUserResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 根据用户优惠券id查询用户优惠券详情
     * @param couponUserId
     * @return
     */
    @Override
    public CouponUserCustomizeResponse selectCouponUserById(Integer couponUserId) {
        String url = "http://AM-TRADE/am-trade/adminCouponUser/selectCouponUserById/"+couponUserId;
        CouponUserCustomizeResponse response = restTemplate.getForEntity(url,CouponUserCustomizeResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 用户优惠券审批
     * @param adminCouponUserRequestBean
     * @return
     */
    @Override
    public CouponUserCustomizeResponse auditRecord(AdminCouponUserRequestBean adminCouponUserRequestBean) {
        String url = "http://AM-TRADE/am-trade/adminCouponUser/auditRecord";
        CouponUserCustomizeResponse response = restTemplate.postForEntity(url,adminCouponUserRequestBean,CouponUserCustomizeResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 查询优惠券已发行数量
     * @return
     */
    @Override
    public CouponRecoverCustomizeResponse checkCouponSendExcess(String couponCode) {
        String url = "http://AM-TRADE/am-trade/couponConfig/checkCouponSendExcess/" + couponCode;
        CouponRecoverCustomizeResponse response = restTemplate.getForEntity(url,CouponRecoverCustomizeResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
	@Override
	public BorrowCustomizeResponse selectBorrowAllList(BorrowBeanRequest form) {
        String url = "http://AM-TRADE/am-trade/borrow/selectBorrowStyleList";
        BorrowCustomizeResponse response = restTemplate.postForEntity(url,form,BorrowCustomizeResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
	}

    /**
     * 查询列表
     * @param adminRequest
     * @return
     */
    @Override
    public FinmanChargeNewResponse selectFinmanChargeList(FinmanChargeNewRequest adminRequest){
        String url = "http://AM-TRADE/am-trade/config/finmanchargenew/list";
        FinmanChargeNewResponse response = restTemplate.postForEntity(url,adminRequest,FinmanChargeNewResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
    /**
     * 根据manChargeCd查询费率配置 详情
     * @author xiehuili
     * @param manChargeCd
     * @return
     */
    @Override
    public FinmanChargeNewResponse getRecordInfo(String manChargeCd){
        String url = "http://AM-TRADE/am-trade/config/finmanchargenew/getRecordInfo/"+manChargeCd;
        FinmanChargeNewResponse response = restTemplate.getForEntity(url,FinmanChargeNewResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
    /**
     * 插入费率配置
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    @Override
    public FinmanChargeNewResponse insertFinmanChargeNewRecord(FinmanChargeNewRequest adminRequest){
        String url = "http://AM-TRADE/am-trade/config/finmanchargenew/insert";
        FinmanChargeNewResponse response = restTemplate.postForEntity(url,adminRequest,FinmanChargeNewResponse.class).getBody();
        if (response != null) {
            int count =restTemplate.postForEntity("http://AM-CONFIG/am-config/feerateModifyLog/insert",adminRequest,int.class).getBody();
            if(count > 0){
                response.setRtn(Response.SUCCESS);
                return response;
            }
            response.setRtn(Response.FAIL);
            return response;
        }
        return null;
    }
    /**
     * 修改费率配置
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    @Override
    public FinmanChargeNewResponse updateFinmanChargeNewRecord(FinmanChargeNewRequest adminRequest){
        String url = "http://AM-TRADE/am-trade/config/finmanchargenew/update";
        FinmanChargeNewResponse response = restTemplate.postForEntity(url,adminRequest,FinmanChargeNewResponse.class).getBody();
        if (response != null) {
            int count =restTemplate.postForEntity("http://AM-CONFIG/am-config/feerateModifyLog/update",adminRequest,int.class).getBody();
            if(count > 0){
                response.setRtn(Response.SUCCESS);
                return response;
            }
            response.setRtn(Response.FAIL);
            return response;
        }
        return null;
    }
    /**
     * 删除费率配置
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    @Override
    public FinmanChargeNewResponse deleteFinmanChargeNewRecord(FinmanChargeNewRequest adminRequest){
        FinmanChargeNewResponse response = null;
        //根据manChargeCd查询费率配置
        FinmanChargeNewResponse selectResponse = restTemplate.getForEntity("http://AM-TRADE/am-trade/config/finmanchargenew/getRecordInfo/"+adminRequest.getManChargeCd(),FinmanChargeNewResponse.class)
                .getBody();
        if (selectResponse != null) {
            BorrowFinmanNewChargeVO vo = selectResponse.getResult();
            adminRequest = CommonUtils.convertBean(vo,FinmanChargeNewRequest.class);
        }
        //此处删除是插入日志，记录新动作而不是修改原动作
         int count =restTemplate.postForEntity("http://AM-CONFIG/am-config/feerateModifyLog/delete",adminRequest,int.class).getBody();
        if(count > 0){
            response = restTemplate.postForEntity("http://AM-TRADE/am-trade/config/finmanchargenew/delete",adminRequest,FinmanChargeNewResponse.class).getBody();
            if (response != null) {
                response.setRtn(Response.SUCCESS);
                return response;
            }
            return response;
        }
        return null;
    }
    /**
     *
     * 根据表的类型,期数,项目类型检索管理费件数
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    @Override
    public int countRecordByProjectType(FinmanChargeNewRequest adminRequest){
        return restTemplate.postForEntity( "http://AM-TRADE/am-trade/config/finmanchargenew/countRecordByProjectType",adminRequest,Integer.class).getBody();
    }
}