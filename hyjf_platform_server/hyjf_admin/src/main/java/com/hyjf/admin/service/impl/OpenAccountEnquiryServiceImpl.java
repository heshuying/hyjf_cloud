package com.hyjf.admin.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.am.vo.admin.OpenAccountEnquiryDefineResultBeanVO;
import com.hyjf.common.exception.ReturnMessageException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.OpenAccountEnquiryDefineResultBean;
import com.hyjf.admin.beans.request.OpenAccountEnquiryDefineRequestBean;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.OpenAccountEnquiryService;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.resquest.user.BankOpenAccountRequest;
import com.hyjf.am.vo.admin.BankOpenAccountLogVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.IdCard15To18;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;

/**
 * @version OpenAccountEnquiryServiceImpl, v0.1 2018/8/20 16:36
 * @Author: Zha Daojian
 */
@Service
public class OpenAccountEnquiryServiceImpl extends BaseServiceImpl implements OpenAccountEnquiryService {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    AmUserClient amUserClient;
    @Autowired
    AmTradeClient amTradeClient;
    @Autowired
    private CommonProducer commonProducer;

    /**
     * 用户按照手机号和身份证号查询开户掉单
     *
     * @param requestBean
     * @return com.hyjf.admin.beans.OpenAccountEnquiryDefineResultBean
     * @author Zha Daojian
     * @date 2018/8/20 16:26
     **/
    @Override
    public OpenAccountEnquiryDefineResultBean openAccountEnquiry(AdminSystemVO currUser, OpenAccountEnquiryDefineRequestBean requestBean) {
        OpenAccountEnquiryDefineResultBean resultBean= new OpenAccountEnquiryDefineResultBean();
        logger.info("请求开户掉单查询接口  参数为：{}",JSONObject.toJSONString(requestBean));
        // 根据num来判断  选择1为手机号查询，2为身份证号查询
        String num = requestBean.getNum();
        if (Integer.parseInt(num)==1){
            // 手机号
            return seachByMobile(requestBean);
        }
        if (Integer.parseInt(num)==2){

        }
        return resultBean;
    }

    /**
     * 根据手机号查询开户掉单
     * @author Zha Daojian
     * @date 2019/1/22 9:44
     * @param requestBean
     * @return com.hyjf.admin.beans.OpenAccountEnquiryDefineResultBean
     **/
    private OpenAccountEnquiryDefineResultBean seachByMobile(OpenAccountEnquiryDefineRequestBean requestBean) {
        OpenAccountEnquiryDefineResultBean result = new OpenAccountEnquiryDefineResultBean();
        String phone =  requestBean.getLastname().trim();
        //手机号格式check
        CheckUtil.check(Validator.isNotNull(phone) && Validator.isMobile(phone), MsgEnum.ERR_FMT_MOBILE);
        UserVO user = amUserClient.getUserByMobile(phone);
        // 用户是否存在check
        CheckUtil.check(Validator.isNotNull(user) , MsgEnum.STATUS_CE000006);
        if(user!=null){
            // 先查询平台的开户状态
            BankOpenAccountVO bankOpenAccountVO = amUserClient.searchBankOpenAccount(user.getUserId());
            UserInfoVO userInfoVO = amUserClient.findUsersInfoById(user.getUserId());
            if (bankOpenAccountVO != null) {
                // 已经开户了
                result.setIsOpen("1");
                result.setUsername(user.getUsername());
                result.setAccountId(bankOpenAccountVO.getAccount());
                result.setMobile(phone);
                result.setRegTimeEnd(GetDate.date2Str(bankOpenAccountVO.getCreateTime(),new SimpleDateFormat("yyyyMMdd")));
                result.setRoleId(userInfoVO.getRoleId()+"");
                result.setName(userInfoVO.getTruename());
                result.setIdcard(userInfoVO.getIdcard());
                result.setUserid(user.getUserId()+"");
            } else {
                // 未查询到该用户  允许操作开户掉单
                result.setIsOpen("0");
                // 调用查询电子账户
                BankCallBean selectbean = new BankCallBean();
                selectbean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
                selectbean.setTxCode(BankCallConstant.TXCODE_ACCOUNT_QUERY_BY_MOBILE_PLUS);
                selectbean.setInstCode(systemConfig.getBANK_INSTCODE());// 机构代码
                selectbean.setBankCode(systemConfig.getBANK_BANKCODE());
                selectbean.setTxDate(GetOrderIdUtils.getTxDate());
                selectbean.setTxTime(GetOrderIdUtils.getTxTime());
                selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
                selectbean.setChannel("000002");
                selectbean.setMobile(requestBean.getLastname());
                // 操作者ID
                selectbean.setLogUserId(String.valueOf(user.getUserId()));
                selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(user.getUserId()));
                selectbean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
                selectbean.setLogClient(0);
                // 返回参数
                BankCallBean retBean =  BankCallUtils.callApiBg(selectbean);
                // 调用接口
                if (retBean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode())) {
                    {
                        JSONArray jsa = JSONArray.parseArray(retBean.getSubPacks());
                        if (jsa != null && jsa.size() > 0) {
                            // 如果返回的结果大于0条
                            result.setStatus("y");
                            JSONObject jso = jsa.getJSONObject(0);
                            result.setAccountId((String) jso.get("accountId"));
                            result.setRegTimeEnd((String) jso.get("issDate"));
                            result.setIdcard((String) jso.get("idNo"));
                            result.setName((String) jso.get("name"));
                            result.setAddr((String) jso.get("addr"));
                            result.setRoleId((String) jso.get("identity"));
                            result.setUsername(user.getUsername());
                            result.setMobile(phone);
                            List<BankOpenAccountLogVO> log = amUserClient.getBankOpenAccountLogVOByUserId(user.getUserId());
                            Integer platform = 1;
                            if(log!=null && log.size()>0){
                                platform = log.get(0).getClient();
                            }
                            if(platform==null){
                                platform = 1;
                            }
                            result.setPlatform(platform+"");
                            result.setUserid(user.getUserId()+"");
                            result.setChannel(BankCallConstant.CHANNEL_PC);
                            return result;
                        } else {
                            throw new ReturnMessageException(MsgEnum.STATUS_CE000017);
                        }
                    }
                }else {
                    throw new ReturnMessageException(MsgEnum.STATUS_CE000017);
                }
            }
        }
        return result;
    }

    /**
     * 用户按照手机号和身份证号查询开户掉单后同步系统掉单信息，更改用户状态
     *
     * @param requestBean
     * @return com.hyjf.admin.beans.OpenAccountEnquiryDefineResultBean
     * @author Zha Daojian
     * @date 2018/8/20 16:35
     **/
    @Override
    public OpenAccountEnquiryDefineResultBean openAccountEnquiryUpdate(OpenAccountEnquiryDefineResultBean requestBean) {
        OpenAccountEnquiryDefineResultBean resultBean= new OpenAccountEnquiryDefineResultBean();
        String userid = requestBean.getUserid();
        String channel =requestBean.getChannel();
        String accountId = requestBean.getAccountId();
        if(StringUtils.isEmpty(userid)){
            resultBean.setStatus(BankCallConstant.BANKOPEN_USER_ACCOUNT_N);
            resultBean.setResult("用户id不能为空");
            return resultBean;
        }
        if(StringUtils.isEmpty(channel)){
            resultBean.setStatus(BankCallConstant.BANKOPEN_USER_ACCOUNT_N);
            resultBean.setResult("交易渠道不能为空");
            return resultBean;
        }
        if(StringUtils.isEmpty(accountId)){
            resultBean.setStatus(BankCallConstant.BANKOPEN_USER_ACCOUNT_N);
            resultBean.setResult("电子账号不能为空");
            return resultBean;
        }
        //同步保存Account信息
       /* OpenAccountEnquiryDefineResultBeanVO openAccountEnquiryDefineRequestBeanVO =  amUserClient.updateAccount(requestBean);
        if(openAccountEnquiryDefineRequestBeanVO !=null){
            ////同步保存user信息成
            if(BankCallConstant.BANKOPEN_USER_ACCOUNT_Y.equals(openAccountEnquiryDefineRequestBeanVO.getStatus())){
                //同步保存user信息
                openAccountEnquiryDefineRequestBeanVO =  amUserClient.updateUser(requestBean);
            }
        }*/
        OpenAccountEnquiryDefineResultBeanVO openAccountEnquiryDefineRequestBeanVO =  amUserClient.updateUser(requestBean);
        logger.info("==========保存开户掉单user的数据openAccountEnquiryDefineRequestBeanVO：" +JSONObject.toJSONString(openAccountEnquiryDefineRequestBeanVO));
        BeanUtils.copyProperties(requestBean, openAccountEnquiryDefineRequestBeanVO);
        logger.info("==========保存开户掉单user的数据requestBean：" +JSONObject.toJSONString(requestBean));
        return requestBean;
    }
    /**
     * 保存开户的数据
     * 此处事务不起作用，此方法已经挪到 am-admin,Zhadaojian 2019-01-22
     */
  /*  public OpenAccountEnquiryDefineResultBean updateUserAccount(OpenAccountEnquiryDefineResultBean requestBean) {
        OpenAccountEnquiryDefineResultBean resultBean= new OpenAccountEnquiryDefineResultBean();
        String idCard = requestBean.getIdcard();
        String platform = requestBean.getPlatform();//开户平台
        if(StringUtils.isEmpty(idCard)){
            resultBean.setStatus("n");
            resultBean.setResult("身份不能为空");
            return resultBean;
        }
        if(StringUtils.isEmpty(platform)){
            resultBean.setStatus("n");
            resultBean.setResult("开户平台platform不能为空");
            return resultBean;
        }
        Integer userId = Integer.parseInt(requestBean.getUserid());
      boolean deleteLogFlag = this.amUserClient.deleteBankOpenAccountLogByUserId(userId);
        if (!deleteLogFlag) {
            logger.error("删除用户开户日志表失败，用户userId:" + userId);
            resultBean.setStatus("n");
            resultBean.setResult("删除用户开户日志表失败");
            return resultBean;
        }
        // 查询返回的电子账号是否已开户
        boolean result = amUserClient.checkAccountByAccountId(requestBean.getAccountId());
        if (result) {
            // 校验未通过
            logger.error("==========该电子账号已被用户关联,无法完成掉单修复!============关联电子账号: " + requestBean.getAccountId());
            resultBean.setStatus("n");
            resultBean.setResult("该电子账号已被用户关联,无法完成掉单修复!");
            return resultBean;
        }
        // 获取用户信息
        UserVO user = amUserClient.getUserByUserId(userId);
        String trueName = requestBean.getName();
        if (idCard.length() < 18) {
            try {
                idCard = IdCard15To18.getEighteenIDCard(idCard);
            } catch (Exception e) {
                logger.error("===========身份证转换异常!=============");
                resultBean.setStatus("n");
                resultBean.setResult("身份证转换异常");
                return resultBean;
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
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO );
        boolean usersFlag = amUserClient.updateUserSelective(userVO)> 0 ? true : false;
        if (!usersFlag) {
            logger.error("更新用户表失败！");
            resultBean.setStatus("n");
            resultBean.setResult("更新用户表失败!");
            return resultBean;

        }
        UserInfoVO userInfo = amUserClient.selectUsersInfoByUserId(userId);
        if (userInfo == null) {
            logger.error("用户详情表数据错误，用户id：" + user.getUserId());
            resultBean.setStatus("n");
            resultBean.setResult("用户详情表数据错误!");
            return resultBean;
        }
        userInfo.setTruename(trueName);
        userInfo.setIdcard(idCard);
        userInfo.setSex(sexInt);
        userInfo.setBirthday(birthDay);
        userInfo.setTruenameIsapprove(1);
        userInfo.setMobileIsapprove(1);
        // 更新用户详细信息表
        boolean userInfoFlag = amUserClient.updateUserInfoByUserInfoSelective(userInfo) > 0 ? true : false;
        if (!userInfoFlag) {
            logger.error("更新用户详情表失败！");
            resultBean.setStatus("n");
            resultBean.setResult("更新用户详情表失败!");
            return resultBean;
        }
        // 更新trade  的  account表的电子帐户号
        boolean tradeAccountFlag = amTradeClient.updateAccountNumberByUserId(userId,requestBean.getAccountId()) ;
        logger.info("开户掉单  更新 trade 的account 结果{}",tradeAccountFlag);
        // 插入江西银行关联表
        BankOpenAccountRequest openAccount = new BankOpenAccountRequest();
        openAccount.setUserId(userId);
        openAccount.setUserName(user.getUsername());
        openAccount.setAccount(requestBean.getAccountId());
        //openAccount.setCreateTime(GetDate.stringToDate(requestBean.getRegTimeEnd()));
        openAccount.setCreateUserId(userId);
        boolean openAccountFlag = amUserClient.insertBankOpenAccount(openAccount)> 0 ? true : false;
        if (!openAccountFlag) {
            logger.error("插入用户开户表失败！");
            resultBean.setStatus("n");
            resultBean.setResult("插入用户开户表失败!");
            return resultBean;
       }

       BankCardVO card = amUserClient.getBankCardByUserId(userId);
        if(card==null){
            logger.info("开始保存银行卡信息。。。");
            BankCallBean bean = new BankCallBean();
            bean.setAccountId(requestBean.getAccountId());
            bean.setLogUserId(requestBean.getUserid());
            bean.setMobile(requestBean.getMobile());
            updateCardNoToBank(bean,user);
        }

        // 开户更新开户渠道统计开户时间
        AppUtmRegVO appUtmRegVO = amUserClient.getAppChannelStatisticsDetailByUserId(userId);
        if (appUtmRegVO != null) {
            appUtmRegVO.setOpenAccountTime(GetDate.stringToDate(requestBean.getRegTimeEnd()));
            amUserClient.updateByPrimaryKeySelective(appUtmRegVO);
        }
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
        resultBean.setStatus("y");
        resultBean.setResult("开户掉单同步成功!");
        return resultBean;

    }*/

    /**
     * 保存银行卡信息
     * @param bean
     */
    private void updateCardNoToBank(BankCallBean bean,UserVO user) {
        Integer userId = Integer.parseInt(bean.getLogUserId());
        // 调用银行接口(4.2.2 用户绑卡接口)
        BankCallBean cardBean = new BankCallBean();
        cardBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        cardBean.setTxCode(BankCallMethodConstant.TXCODE_CARD_BIND_DETAILS_QUERY);
        cardBean.setInstCode(systemConfig.getBANK_INSTCODE());// 机构代码
        cardBean.setBankCode(systemConfig.getBANK_BANKCODE());
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
            boolean bankFlag = amUserClient.insertUserCard(bankCard) > 0 ? true : false;
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
        selectbean.setInstCode(systemConfig.getBANK_INSTCODE());// 机构代码
        selectbean.setBankCode(systemConfig.getBANK_BANKCODE());
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

    //reg_esb   hyjf_user表
    private String accountEsbStates(int string) {
        if (string==0) {
            return "PC";
        }
        if (string==1) {
            return "微信";
        }
        if (string==2) {
            return "Android";
        }
        if (string==3) {
            return "iOS";
        }
        if (string==4) {
            return "其他";
        }
        return null;

    }
    private String accountState(String string) {
        if (string.isEmpty()) {
            return "正常";
        }
        if (string.equals("A")) {
            return "待激活";
        }
        if (string.equals("C")) {
            return "止付";
        }
        if (string.equals("Z")) {
            return "注销";
        }
        return null;

    }
}
