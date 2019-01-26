package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.resquest.trade.HjhDebtCreditTenderRequest;
import com.hyjf.am.resquest.user.CertificateAuthorityRequest;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.common.util.ApiSignUtil;
import com.hyjf.cs.trade.bean.BaseBean;
import com.hyjf.cs.trade.bean.BaseDefine;
import com.hyjf.cs.trade.bean.UserDirectRechargeRequestBean;
import com.hyjf.cs.trade.bean.assetpush.UserWithdrawRequestBean;
import com.hyjf.cs.trade.client.AmConfigClient;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.client.CsMessageClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.BaseTradeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

public class BaseTradeServiceImpl extends BaseServiceImpl implements BaseTradeService {
    protected Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    public AmUserClient amUserClient;

    @Autowired
    public AmTradeClient amTradeClient;

    @Autowired
    public AmConfigClient amConfigClient;

    @Autowired
    public CsMessageClient csMessageClient;

    /**
     * @Description 根据token查询user
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/12 10:34
     */
    @Override
    public WebViewUserVO getUserFromCache(int userId) {
        WebViewUserVO user = RedisUtils.getObj(RedisConstants.USERID_KEY+userId, WebViewUserVO.class);
        return user;
    }

    /**
     * 根据userid查询用户
     * @auther: hesy
     * @date: 2018/7/10
     */
    @Override
    public UserVO getUserByUserId(Integer userId){
        return amUserClient.findUserById(userId);
    }
    @Override
    public HjhPlanVO getPlanByNid(String borrowNid){
        return  amTradeClient.getPlanByNid(borrowNid);
    }
    /**
     * 获取用户在银行的开户信息
     * @param userId
     * @return
     */
    @Override
    public BankOpenAccountVO getBankOpenAccount(Integer userId) {
        BankOpenAccountVO bankAccount = this.amUserClient.selectBankAccountById(userId);
        return bankAccount;
    }

    /**
     * @Description 获得江西银行的余额  调用江西银行接口
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/20 9:16
     */
    @Override
    public BigDecimal getBankBalancePay(Integer userId, String accountId) {
        BigDecimal balance = BigDecimal.ZERO;
        BankCallBean bean = new BankCallBean();
        bean.setTxCode(BankCallMethodConstant.TXCODE_BALANCE_QUERY);
        // 交易渠道
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        // 电子账号
        bean.setAccountId(accountId);
        // 订单号
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        // 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogRemark("电子账户余额查询");
        // 平台
        bean.setLogClient(0);
        try {
            BankCallBean resultBean = BankCallUtils.callApiBg(bean);
            if (resultBean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
                balance = new BigDecimal(resultBean.getAvailBal().replace(",", ""));
                return balance;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new BigDecimal(0);
    }

    /**
     * @Description 风险测评校验
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/20 9:55
     */
    @Override
    public void checkEvaluation(UserVO user) {
        Integer userEvaluationResultFlag = user.getIsEvaluationFlag();
        if (0 == userEvaluationResultFlag) {
            throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_NEED_RISK_ASSESSMENT);
        } else {
            //是否完成风险测评2
            if (user.getIsEvaluationFlag()==1) {
                //测评到期日
                Long lCreate = user.getEvaluationExpiredTime().getTime();
                //当前日期
                Long lNow = System.currentTimeMillis();
                if (lCreate <= lNow) {
                    //已过期需要重新评测
                    throw new ReturnMessageException(MsgEnum.STATUS_EV000004);
                }
            } else {
                //未获取到评测数据或者评测时间
                throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_NEED_RISK_ASSESSMENT);
            }
        }
    }

    /**
     * 检查用户是否是新手 true 是  false 不是
     *
     * @param userId
     * @return
     */
    @Override
    public boolean checkIsNewUserCanInvest(Integer userId) {
        // 新的判断是否为新用户方法
        try {
            int total = amTradeClient.countNewUserTotal(userId);
            logger.info("获取用户出借数量 userID {} 数量 {} ",userId,total);
            if (total == 0) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e) {
            throw e;
        }
    }
    /**
     * 检查用户是否是新手 true 是  false 不是
     *
     * @param userId
     * @return
     */
    @Override
    public boolean checkIsNewUserCanInvest2(Integer userId) {
        // 新的判断是否为新用户方法
        try {
            int total = amTradeClient.countNewUserTotal(userId);
            logger.info("获取用户出借数量 userID {} 数量 {} ",userId,total);
            if (total <= 1) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e) {
            throw e;
        }
    }
    /**
     * 获取account信息
     * @auther: hesy
     * @date: 2018/7/10
     */
    @Override
    public AccountVO getAccountByUserId(Integer userId){
        return amTradeClient.getAccountByUserId(userId);
    }

    @Override
    public BankCardVO getBankCardVOByUserId(Integer userId) {
        BankCardVO bankCard = this.amUserClient.selectBankCardByUserId(userId);
        return bankCard;
    }

    /**
     * 获取前端的地址
     * @param sysConfig
     * @param platform
     * @return
     */
    public String getFrontHost(SystemConfig sysConfig, String platform) {

        Integer client = Integer.parseInt(platform);
        if (ClientConstants.WEB_CLIENT == client) {
            return sysConfig.getFrontHost();
        }
        if (ClientConstants.APP_CLIENT_IOS == client || ClientConstants.APP_CLIENT == client) {
            return sysConfig.getAppFrontHost();
        }
        if (ClientConstants.WECHAT_CLIENT == client) {
            return sysConfig.getWeiFrontHost();
        }
        return null;
    }

    /**
     * 根据borrowNid获取borrow
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowAndInfoVO getBorrowByNid(String borrowNid) {
        return amTradeClient.selectBorrowByNid(borrowNid);
    }

    /**
     * BorrowNid获取borrowInfo
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowInfoVO getBorrowInfoByNid(String borrowNid){
        return amTradeClient.getBorrowInfoByNid(borrowNid);
    }


    @Override
    public BankCardVO selectBankCardByUserId(Integer userId) {
        BankCardVO bankCard = amUserClient.selectBankCardByUserId(userId);
        return bankCard;
    }

    /**
     * 根据用户ID取得用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfoVO getUsersInfoByUserId(Integer userId) {
        if (userId != null) {
            UserInfoVO usersInfo = this.amUserClient.findUsersInfoById(userId);
            return usersInfo;
        }
        return null;
    }

    @Override
    public UserVO getUsers(Integer userId) {
        UserVO users = amUserClient.findUserById(userId);
        return users;
    }
    /**
     * 验证外部请求签名
     *
     * @param paramBean
     * @return
     */
    @Override
    public boolean verifyRequestSign(BaseBean paramBean, String methodName) {

        String sign = StringUtils.EMPTY;

        // 机构编号必须参数
        String instCode = paramBean.getInstCode();
        if (StringUtils.isEmpty(instCode)) {
            return false;
        }

        if(BaseDefine.METHOD_SERVER_WITHDRAW.equals(methodName)){
            // 用户提现
            UserWithdrawRequestBean bean = (UserWithdrawRequestBean)paramBean;
            sign = bean.getChannel() + bean.getAccountId() + bean.getAccount() + bean.getCardNo() + bean.getRetUrl() + bean.getBgRetUrl() + bean.getTimestamp();
        }else if(BaseDefine.METHOD_SERVER_RECHARGE.equals(methodName)){
            // 页面充值
            UserDirectRechargeRequestBean bean = (UserDirectRechargeRequestBean) paramBean;
            sign = bean.getInstCode() + bean.getAccountId() + bean.getMobile() + bean.getIdNo() + bean.getCardNo()
                    + bean.getTxAmount() + bean.getName() + bean.getRetUrl() + bean.getBgRetUrl() + bean.getTimestamp();
        }

        return ApiSignUtil.verifyByRSA(instCode, paramBean.getChkValue(), sign);
    }
    /**
     * 获取标的还款信息
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowRepayVO selectBorrowRepay(String borrowNid) {
        BorrowRepayVO borrowRepayVO = amTradeClient.getBorrowRepay(borrowNid);
        if (null != borrowRepayVO) {
            return borrowRepayVO;
        }
        return null;
    }

    /**
     * 获取标的放款信息
     *
     * @param borrowNid
     * @return
     */
    @Override
    public List<BorrowRecoverVO> selectBorrowRecoverListByBorrowNid(String borrowNid) {
        List<BorrowRecoverVO> borrowRecoverVOS = amTradeClient.selectBorrowRecoverByBorrowNid(borrowNid);
        if (CollectionUtils.isEmpty(borrowRecoverVOS)) {
            return null;
        }
        return borrowRecoverVOS;
    }

    /**
     * 获取标的投资人信息
     *
     * @param borrowNid
     * @return
     */
    @Override
    public List<BorrowTenderVO> selectBorrowTenderByBorrowNid(String borrowNid) {
        List<BorrowTenderVO> borrowTenderVOList = amTradeClient.getBorrowTenderListByBorrowNid(borrowNid);
        if (CollectionUtils.isEmpty(borrowTenderVOList)) {
            return null;
        }
        return borrowTenderVOList;
    }

    /**
     * 获取借款人信息（企业）
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowUserVO selectBorrowUsersByBorrowNid(String borrowNid) {
        BorrowUserVO borrowUserVO = amTradeClient.getBorrowUser(borrowNid);
        if (null != borrowUserVO) {
            return borrowUserVO;
        }
        return null;
    }

    /**
     * 获取借款人信息（个人）
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowManinfoVO selectBorrowMainfo(String borrowNid) {
        BorrowManinfoVO borrowManinfoVO = amTradeClient.getBorrowManinfo(borrowNid);
        if (null != borrowManinfoVO) {
            return borrowManinfoVO;
        }
        return null;
    }

    /**
     * 获取用户CA认证信息
     *
     * @param username
     * @param cardNo
     * @return
     */
    @Override
    public CertificateAuthorityVO selectCAInfoByUsername(String username, String cardNo, Integer idType) {
        CertificateAuthorityRequest request = new CertificateAuthorityRequest();
        request.setTrueName(username);
        request.setIdNo(cardNo);
        request.setIdType(idType);
        List<CertificateAuthorityVO> list = amUserClient.getCertificateAuthorityList(request);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 根据平台借款等级变换中互金借款等级
     *
     * @param borrowLevel
     * @return
     */
    @Override
    public Integer getBorrowLevel(String borrowLevel) {
        switch (borrowLevel) {
            case "BBB":
                return 0;
            case "A":
                return 1;
            case "AA-":
                return 2;
            case "AA":
                return 3;
            case "AA+":
                return 4;
            case "AAA":
                return 5;
            default:
                return -1;
        }
    }

    /**
     * 获取散标债转信息表
     *
     * @param creditNid
     * @return
     */
    @Override
    public BorrowCreditVO selectBorrowCreditByCreditNid(String creditNid) {
        BorrowCreditVO borrowCreditVO = amTradeClient.getBorrowCreditByCreditNid(creditNid);
        if (null != borrowCreditVO) {
            return borrowCreditVO;
        }
        return null;
    }

    /**
     * 获取散标债转承接人的承接信息
     *
     * @param creditNid
     * @return
     */
    @Override
    public List<CreditTenderVO> selectCreditTenderByCreditNid(String creditNid) {
        CreditTenderRequest request =new CreditTenderRequest();
        request.setCreditNid(creditNid);
        List<CreditTenderVO> creditTenderVOS = amTradeClient.getCreditTenderList(request);
        if (CollectionUtils.isEmpty(creditTenderVOS)) {
            return null;
        }
        return creditTenderVOS;
    }

    /**
     * 承接人承接记录
     *
     * @param creditNid
     * @return
     */
    @Override
    public List<HjhDebtCreditTenderVO> selectHjhDebtCreditTenderByCreditNid(String creditNid) {
        HjhDebtCreditTenderRequest request = new HjhDebtCreditTenderRequest();
        request.setCreditNid(creditNid);
        List<HjhDebtCreditTenderVO> hjhDebtCreditTenderVOS = amTradeClient.getHjhDebtCreditTenderList(request);
        if (CollectionUtils.isEmpty(hjhDebtCreditTenderVOS)) {
            return null;
        }
        return hjhDebtCreditTenderVOS;
    }

    /**
     * 汇计划债转表
     *
     * @param creditNid
     * @return
     */
    @Override
    public HjhDebtCreditVO selectHjhDebtCreditByCreditNid(String creditNid) {
        HjhDebtCreditVO hjhDebtCreditVO = amTradeClient.selectHjhDebtCreditByCreditNid(creditNid);
        if (null != hjhDebtCreditVO) {
            return hjhDebtCreditVO;
        }
        return null;
    }

}
