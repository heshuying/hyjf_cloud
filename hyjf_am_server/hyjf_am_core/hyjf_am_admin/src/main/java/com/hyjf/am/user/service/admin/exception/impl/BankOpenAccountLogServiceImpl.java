package com.hyjf.am.user.service.admin.exception.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.admin.config.SystemConfig;
import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.response.user.OpenAccountEnquiryResponse;
import com.hyjf.am.resquest.admin.OpenAccountEnquiryDefineRequest;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.resquest.user.BankOpenAccountLogRequest;
import com.hyjf.am.resquest.user.BankOpenAccountRequest;
import com.hyjf.am.trade.service.admin.account.AccountDetailService;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.dao.model.customize.OpenAccountEnquiryCustomize;
import com.hyjf.am.user.service.admin.exception.BankOpenAccountLogService;
import com.hyjf.am.user.service.admin.membercentre.UserManagerService;
import com.hyjf.am.user.service.front.user.AppUtmRegService;
import com.hyjf.am.user.service.front.user.UserInfoService;
import com.hyjf.am.user.service.front.user.UserService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.OpenAccountEnquiryDefineResultBeanVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.*;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @version BankOpenAccountLogSrviceImpl, v0.1 2018/8/21 14:41
 * @Author: Zha Daojian
 */
@Service
public class BankOpenAccountLogServiceImpl extends BaseServiceImpl implements BankOpenAccountLogService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private UserManagerService userService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AccountDetailService accountDetailService;

    @Autowired
    private AppUtmRegService appUtmRegService;

    @Autowired
    private CommonProducer commonProducer;


    /**
     * 通过手机号和身份证查询用户信息
    * @author Zha Daojian
    * @date 2018/8/21 18:53
    * @param request
    * @return com.hyjf.am.user.dao.model.customize.OpenAccountEnquiryCustomize
    **/
    @Override
    public OpenAccountEnquiryCustomize accountEnquiry(BankOpenAccountLogRequest request) {
         return this.adminAccountCustomizeQuiryMapper.selectAccountEnquiry(request);
    }


    /**
     * 获取掉单用户信息
     *
     * @param mobile,idcard
     * @return java.util.List<com.hyjf.admin.beans.vo.BankOpenAccountLogVO>
     * @author Zha Daojian
     * @date 2018/8/21 13:54
     **/
    @Override
    public List<BankOpenAccountLog> bankOpenAccountLogSelect(String mobile,String idcard ) {
        logger.info("bankOpenAccountLogSelect:::::::::");
        // 借款人用户
        BankOpenAccountLogExample openAccoutLogExample = new BankOpenAccountLogExample();
        BankOpenAccountLogExample.Criteria crt = openAccoutLogExample.createCriteria();
        if(StringUtils.isNoneEmpty(idcard)){
            crt.andIdNoEqualTo(idcard);
        }
        if(StringUtils.isNoneEmpty(mobile)){
            crt.andMobileEqualTo(mobile);
        }
        List<BankOpenAccountLog> openAccountLogs = this.bankOpenAccountLogMapper.selectByExample(openAccoutLogExample);
        if (openAccountLogs != null && openAccountLogs.size()> 0) {
            return openAccountLogs;
        }
        return null;
    }

    /**
     * 获取掉单用户信息
     *
     * @param orderId
     * @return java.util.List<com.hyjf.admin.beans.vo.BankOpenAccountLogVO>
     * @author Zha Daojian
     * @date 2018/8/21 13:54
     **/
    @Override
    public BankOpenAccountLog selectBankOpenAccountLogByOrderId(String orderId) {

        logger.info("selectBankOpenAccountLogByOrderId:::::::::userId=[{}]",orderId);
        // 借款人用户
        BankOpenAccountLogExample openAccoutLogExample = new BankOpenAccountLogExample();
        BankOpenAccountLogExample.Criteria crt = openAccoutLogExample.createCriteria();
        crt.andOrderIdEqualTo(orderId);
        List<BankOpenAccountLog> openAccountLogs = this.bankOpenAccountLogMapper.selectByExample(openAccoutLogExample);
        if (openAccountLogs != null && openAccountLogs.size() == 1) {
            return openAccountLogs.get(0);
        }
        return null;
    }

    /**
     * 查询返回的电子账号是否已开户
     *
     * @param accountId
     * @return java.lang.Boolean
     * @author Zha Daojian
     * @date 2018/8/23 9:36
     **/
    @Override
    public Boolean checkAccountByAccountId(String accountId) {
        // 根据account查询用户是否开户
        BankOpenAccountExample example = new BankOpenAccountExample();
        example.createCriteria().andAccountEqualTo(accountId);
        List<BankOpenAccount> bankOpenList = this.bankOpenAccountMapper.selectByExample(example);
        if (bankOpenList != null && bankOpenList.size() > 0) {
            for (int i = 0; i < bankOpenList.size(); i++) {
                Integer userId = bankOpenList.get(i).getUserId();
                UserExample userExample = new UserExample();
                userExample.createCriteria().andUserIdEqualTo(userId);
                List<User> user = this.userMapper.selectByExample(userExample);
                if (user != null && user.size() > 0) {
                    for (int j = 0; j < user.size(); j++) {
                        User info = user.get(j);
                        Integer bankOpenFlag = info.getBankOpenAccount();
                        if (bankOpenFlag == 1) {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    /**
     * 删除用户开户日志
     *
     * @param userId
     * @return java.lang.Boolean
     * @author Zha Daojian
     * @date 2018/8/23 9:36
     **/
    @Override
    public Boolean deleteBankOpenAccountLogByUserId(Integer userId) {
        BankOpenAccountLogExample accountLogExample = new BankOpenAccountLogExample();
        accountLogExample.createCriteria().andUserIdEqualTo(userId);
        boolean deleteLogFlag = this.bankOpenAccountLogMapper.deleteByExample(accountLogExample) > 0 ? true : false;
        return  deleteLogFlag;
    }

    /**
     * 根据USERID查询开户掉单
     *
     * @param userId
     * @return
     */
    @Override
    public List<BankOpenAccountLog> selectBankOpenAccountLogByUserId(Integer userId) {
        // 借款人用户
        BankOpenAccountLogExample openAccoutLogExample = new BankOpenAccountLogExample();
        BankOpenAccountLogExample.Criteria crt = openAccoutLogExample.createCriteria();
        crt.andUserIdEqualTo(userId);
        crt.andStatusEqualTo(0);
        openAccoutLogExample.setOrderByClause("id asc");
        List<BankOpenAccountLog> openAccountLogs = this.bankOpenAccountLogMapper.selectByExample(openAccoutLogExample);
        if (openAccountLogs != null && openAccountLogs.size()> 0) {
            return openAccountLogs;
        }
        return null;
    }

    /**
     * 保存开户(User)的数据
     */
    public OpenAccountEnquiryResponse updateUser(OpenAccountEnquiryDefineRequest requestBean) {
        logger.info("==========保存开户(User)的数据" );
        OpenAccountEnquiryDefineResultBeanVO resultBean= new OpenAccountEnquiryDefineResultBeanVO();
        OpenAccountEnquiryResponse response =  new OpenAccountEnquiryResponse();
        String idCard = requestBean.getIdcard();
        String platform = requestBean.getPlatform();//开户平台
        if(StringUtils.isEmpty(idCard)){
            resultBean.setStatus(BankCallConstant.BANKOPEN_USER_ACCOUNT_N);
            resultBean.setResult("身份不能为空");
            response.setOpenAccountEnquiryDefineResultBeanVO(resultBean);
            return response;
        }
        if(StringUtils.isEmpty(platform)){
            resultBean.setStatus(BankCallConstant.BANKOPEN_USER_ACCOUNT_N);
            resultBean.setResult("开户平台platform不能为空");
            response.setOpenAccountEnquiryDefineResultBeanVO(resultBean);
            return response;
        }
        Integer userId = Integer.parseInt(requestBean.getUserid());
        boolean deleteLogFlag = this.deleteBankOpenAccountLogByUserId(userId);
        if (!deleteLogFlag) {
            logger.error("删除用户开户日志表失败，用户userId:" + userId);
            resultBean.setStatus(BankCallConstant.BANKOPEN_USER_ACCOUNT_N);
            resultBean.setResult("删除用户开户日志表失败");
            response.setOpenAccountEnquiryDefineResultBeanVO(resultBean);
            return response;
        }
        // 查询返回的电子账号是否已开户
        boolean result = checkAccountByAccountId(requestBean.getAccountId());
        if (result) {
            // 校验未通过
            logger.error("==========该电子账号已被用户关联,无法完成掉单修复!============关联电子账号: " + requestBean.getAccountId());
            resultBean.setStatus(BankCallConstant.BANKOPEN_USER_ACCOUNT_N);
            resultBean.setResult("该电子账号已被用户关联,无法完成掉单修复!");
            response.setOpenAccountEnquiryDefineResultBeanVO(resultBean);
            return response;
        }
        // 获取用户信息
        User user = userService.findUserByUserId(userId);
        String trueName = requestBean.getName();
        if (idCard.length() < 18) {
            try {
                idCard = IdCard15To18.getEighteenIDCard(idCard);
            } catch (Exception e) {
                logger.error("===========身份证转换异常!=============");
                resultBean.setStatus(BankCallConstant.BANKOPEN_USER_ACCOUNT_N);
                resultBean.setResult("身份证转换异常");
                response.setOpenAccountEnquiryDefineResultBeanVO(resultBean);
                return response;
            }
        }
        int sexInt = Integer.parseInt(idCard.substring(16, 17));// 性别
        if (sexInt % 2 == 0) {
            sexInt = 2;
        } else {
            sexInt = 1;
        }
        String birthDayTemp = idCard.substring(6, 14);// 出生日期
        String birthDay = StringUtils.substring(birthDayTemp, 0, 4) + "-" + StringUtils.substring(birthDayTemp, 4, 6) + "-" + StringUtils.substring(birthDayTemp, 6, 8);
        user.setBankOpenAccount(1);
        user.setBankAccountEsb(Integer.parseInt(requestBean.getPlatform()));
        user.setRechargeSms(0);
        user.setWithdrawSms(0);
        user.setUserType(0);
        user.setIsSetPassword(getIsSetPassword(requestBean.getAccountId(),userId));
        user.setMobile(requestBean.getMobile());
        // 更新相应的用户表
        boolean usersFlag = userService.updateUserSelective(user)> 0 ? true : false;
        if (!usersFlag) {
            logger.error("更新用户表失败！");
            resultBean.setStatus(BankCallConstant.BANKOPEN_USER_ACCOUNT_N);
            resultBean.setResult("更新用户表失败!");
            response.setOpenAccountEnquiryDefineResultBeanVO(resultBean);
            return response;

        }
        UserInfo userInfo = userInfoService.findUsersInfo(userId);
        if (userInfo == null) {
            logger.error("用户详情表数据错误，用户id：" + user.getUserId());
            resultBean.setStatus(BankCallConstant.BANKOPEN_USER_ACCOUNT_N);
            resultBean.setResult("用户详情表数据错误!");
            response.setOpenAccountEnquiryDefineResultBeanVO(resultBean);
            return response;
        }
        userInfo.setTruename(trueName);
        userInfo.setIdcard(idCard);
        userInfo.setSex(sexInt);
        userInfo.setBirthday(birthDay);
        userInfo.setTruenameIsapprove(1);
        userInfo.setMobileIsapprove(1);
        // 更新用户详细信息表
        boolean userInfoFlag = userService.updateUserInfoByUserInfoSelective(userInfo) > 0 ? true : false;
        if (!userInfoFlag) {
            logger.error("更新用户详情表失败！");
            resultBean.setStatus(BankCallConstant.BANKOPEN_USER_ACCOUNT_N);
            resultBean.setResult("更新用户详情表失败!");
            response.setOpenAccountEnquiryDefineResultBeanVO(resultBean);
            return response;
        }
        // 插入江西银行关联表
        BankOpenAccount openAccount = new BankOpenAccount();
        openAccount.setUserId(userId);
        openAccount.setUserName(user.getUsername());
        openAccount.setAccount(requestBean.getAccountId());
        //openAccount.setCreateTime(GetDate.stringToDate(requestBean.getRegTimeEnd()));
        openAccount.setCreateUserId(userId);
        boolean openAccountFlag = userService.insertBankOpenAccount(openAccount)> 0 ? true : false;
        if (!openAccountFlag) {
            logger.error("插入用户开户表失败！");
            resultBean.setStatus(BankCallConstant.BANKOPEN_USER_ACCOUNT_N);
            resultBean.setResult("插入用户开户表失败!");
            response.setOpenAccountEnquiryDefineResultBeanVO(resultBean);
            return response;
        }

        BankCard card = userService.getBankCardByUserId(userId);
        if(card==null){
            logger.info("开始保存银行卡信息。。。");
            BankCallBean bean = new BankCallBean();
            bean.setAccountId(requestBean.getAccountId());
            bean.setLogUserId(requestBean.getUserid());
            bean.setMobile(requestBean.getMobile());
            updateCardNoToBank(bean,user);
        }

        // 开户更新开户渠道统计开户时间
//        AppUtmReg appUtmReg = appUtmRegService.findByUserId(userId);
//        if (appUtmReg != null) {
//            User appUtmRegUser =  new User();
//            BeanUtils.copyProperties(appUtmRegUser,appUtmReg);
//            BeanUtils.copyProperties(appUtmReg,appUtmRegUser);
//            appUtmReg.setOpenAccountTime(GetDate.stringToDate(requestBean.getRegTimeEnd()));
//            userService.updateUser(appUtmRegUser);
//        }
        // add by liuyang 20180227 开户掉单处理成功之后 发送法大大CA认证MQ  start
        // 加入到消息队列
        Map<String, String> params = new HashMap<String, String>();
        params.put("mqMsgId", GetCode.getRandomCode(10));
        params.put("userId", String.valueOf(userId));
        try {
            logger.info("开户异步处理，发送MQ，userId:[{}],mqMgId:[{}]",userId,params.get("mqMsgId"));

            commonProducer.messageSend(new MessageContent(MQConstant.FDD_CERTIFICATE_AUTHORITY_TOPIC, UUID.randomUUID().toString(),params));
        } catch (Exception e) {
            logger.error("开户掉单处理成功之后 发送法大大CA认证MQ消息失败！userId:[{}]",userId);
        }
        resultBean.setStatus(BankCallConstant.BANKOPEN_USER_ACCOUNT_Y);
        resultBean.setResult("开户掉单同步用户成功!");
        response.setOpenAccountEnquiryDefineResultBeanVO(resultBean);
        return response;

    }

    /**
     * 保存银行卡信息
     * @param bean
     */
    private void updateCardNoToBank(BankCallBean bean,User user) {
        Integer userId = Integer.parseInt(bean.getLogUserId());
        // 调用银行接口(4.2.2 用户绑卡接口)
        BankCallBean cardBean = new BankCallBean();
        cardBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        cardBean.setTxCode(BankCallMethodConstant.TXCODE_CARD_BIND_DETAILS_QUERY);
        cardBean.setInstCode(systemConfig.getBankInstcode());// 机构代码
        cardBean.setBankCode(systemConfig.getBankBankcode());
        cardBean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
        cardBean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
        cardBean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
        cardBean.setChannel(BankCallConstant.CHANNEL_PC);// 交易渠道
        cardBean.setAccountId(bean.getAccountId());// 存管平台分配的账号
        cardBean.setState("1"); // 查询状态 0-所有（默认） 1-当前有效的绑定卡
        cardBean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        cardBean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        cardBean.setLogUserId(String.valueOf(userId));
        // 调用银行接口 4.4.11 银行卡查询接口
        BankCallBean call = BankCallUtils.callApiBg(bean);
        String respCode = call == null ? "" : call.getRetCode();
        // 如果接口调用成功
        if (BankCallConstant.RESPCODE_SUCCESS.equals(respCode)) {
            String usrCardInfolist = call.getSubPacks();
            JSONArray array = JSONObject.parseArray(usrCardInfolist);
            JSONObject obj = null;
            if (array != null && array.size() != 0) {
                obj = array.getJSONObject(0);
            }
            BankCardRequest bankCard = new BankCardRequest();
            bankCard.setUserId(userId);
            bankCard.setUserName(user.getUsername());
            // 设置银行卡
            String bankId = "";//amConfigClient.getBankIdByCardNo(obj.getString("cardNo"));
            JxBankConfigVO banksConfigVO = null;//amConfigClient.getJxBankConfigById(Integer.parseInt(bankId));

            bankCard.setCardNo(obj.getString("cardNo"));
            bankCard.setBank(banksConfigVO.getBankName());
            bankCard.setStatus(1);
            bankCard.setCreateTime(new Date());
            bankCard.setCreateUserId(userId);
            bankCard.setBankId(Integer.parseInt(bankId));

            bankCard.setUserId(userId);
            // 设置绑定的手机号
            bankCard.setMobile(bean.getMobile());
            bankCard.setUserName(user.getUsername());
            bankCard.setStatus(1);// 默认都是1
            // 银行联号
            String payAllianceCode = "";
            // 调用江西银行接口查询银行联号
            BankCallBean payAllianceCodeQueryBean = this.payAllianceCodeQuery(obj.getString("cardNo"), userId);
            if (payAllianceCodeQueryBean != null && BankCallConstant.RESPCODE_SUCCESS.equals(payAllianceCodeQueryBean.getRetCode())) {
                payAllianceCode = payAllianceCodeQueryBean.getPayAllianceCode();
            }
            // 如果此时银联行号还是为空,根据bankId查询本地存的银联行号
            if (StringUtils.isBlank(payAllianceCode)) {
                if (!StringUtils.isBlank(payAllianceCode)) {
                    payAllianceCode = banksConfigVO.getPayAllianceCode();
                    bankCard.setPayAllianceCode(payAllianceCode);
                }
            }
            BankCard bankCardbean = new BankCard();
            BeanUtils.copyProperties(bankCardbean, bankCard);
            boolean bankFlag = bankCardMapper.insertSelective(bankCardbean) > 0 ? true : false;
            if (!bankFlag) {
                logger.error("插入用户银行卡失败！");
            }
        }
    }


    private BankCallBean payAllianceCodeQuery(String cardNo, Integer userId) {
        BankCallBean bean = new BankCallBean();
        String channel = BankCallConstant.CHANNEL_PC;
        String orderDate = GetOrderIdUtils.getOrderDate();
        String txDate = GetOrderIdUtils.getTxDate();
        String txTime = GetOrderIdUtils.getTxTime();
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        bean.setVersion(BankCallConstant.VERSION_10);// 版本号
        bean.setTxCode(BankCallConstant.TXCODE_PAY_ALLIANCE_CODE_QUERY);// 交易代码
        bean.setTxDate(txDate);
        bean.setTxTime(txTime);
        bean.setSeqNo(seqNo);
        bean.setChannel(channel);
        bean.setAccountId(cardNo);
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        bean.setLogOrderDate(orderDate);
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogRemark("联行号查询");
        bean.setLogClient(0);
        return BankCallUtils.callApiBg(bean);
    }

    /**
     * 查看是否设置交易密码
     * @param account
     * @param userId
     * @return
     */
    private Integer getIsSetPassword(String account,Integer userId) {
        // 调用查询电子账户密码是否设置
        BankCallBean selectbean = new BankCallBean();
        selectbean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        selectbean.setTxCode(BankCallConstant.TXCODE_PASSWORD_SET_QUERY);
        selectbean.setInstCode(systemConfig.getBankInstcode());// 机构代码
        selectbean.setBankCode(systemConfig.getBankBankcode());
        selectbean.setTxDate(GetOrderIdUtils.getTxDate());
        selectbean.setTxTime(GetOrderIdUtils.getTxTime());
        selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        selectbean.setChannel(BankCallConstant.CHANNEL_PC);
        // 电子账号
        selectbean.setAccountId(account);

        // 操作者ID
        selectbean.setLogUserId(userId+"");
        selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        selectbean.setLogClient(0);
        // 返回参数
        BankCallBean retBean = BankCallUtils.callApiBg(selectbean);

        if(retBean==null){
            return 0;
        }
        if("1".equals(retBean.getPinFlag())){
            return 1;
        }
        return 0;
    }

}
