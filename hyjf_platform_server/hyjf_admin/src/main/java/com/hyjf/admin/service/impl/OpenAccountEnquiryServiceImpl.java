package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.OpenAccountEnquiryDefineResultBean;
import com.hyjf.admin.beans.request.OpenAccountEnquiryDefineRequestBean;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.service.OpenAccountEnquiryService;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.vo.admin.BankOpenAccountLogVO;
import com.hyjf.am.vo.admin.OpenAccountEnquiryDefineResultBeanVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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
    private AmConfigClient amConfigClient;

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
                result.setPlatform(user.getBankAccountEsb()+"");
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
       OpenAccountEnquiryDefineResultBeanVO openAccountEnquiryDefineRequestBeanVO =  amUserClient.updateAccount(requestBean);
        if(openAccountEnquiryDefineRequestBeanVO !=null){
            ////同步保存user信息成
            if(BankCallConstant.BANKOPEN_USER_ACCOUNT_Y.equals(openAccountEnquiryDefineRequestBeanVO.getStatus())){
                //同步保存user信息
                openAccountEnquiryDefineRequestBeanVO =  amUserClient.updateUser(requestBean);
                logger.info("==========保存开户掉单user的数据openAccountEnquiryDefineRequestBeanVO：" +JSONObject.toJSONString(openAccountEnquiryDefineRequestBeanVO));
                BeanUtils.copyProperties(requestBean, openAccountEnquiryDefineRequestBeanVO);
                logger.info("==========保存开户掉单user的数据requestBean：" +JSONObject.toJSONString(requestBean));
                if(openAccountEnquiryDefineRequestBeanVO !=null){
                    if(BankCallConstant.BANKOPEN_USER_ACCOUNT_Y.equals(openAccountEnquiryDefineRequestBeanVO.getStatus())) {
                        UserVO user = amUserClient.findUserById(Integer.valueOf(userid));
                        BankCallBean bean = new BankCallBean();
                        bean.setAccountId(requestBean.getAccountId());
                        bean.setLogUserId(requestBean.getUserid());
                        bean.setMobile(requestBean.getMobile());
                        updateCardNoToBank(bean, user);
                    }
                }
            }
        }

        return requestBean;
    }
    private void updateCardNoToBank(BankCallBean bean,UserVO user) {
        Integer userId = Integer.parseInt(bean.getLogUserId());
        logger.info("保存用户银行卡信息  userId {}   ",userId);
        // 调用江西银行接口查询用户绑定的银行卡
        BankCallBean cardBean = new BankCallBean(userId, BankCallConstant.TXCODE_CARD_BIND_DETAILS_QUERY, bean.getLogClient());
        cardBean.setChannel(bean.getChannel());// 交易渠道
        cardBean.setAccountId(bean.getAccountId());// 存管平台分配的账号
        cardBean.setState("1"); // 查询状态 0-所有（默认） 1-当前有效的绑定卡
        cardBean.setLogRemark("银行卡关系查询");
        // 调用江西银行查询银行卡
        BankCallBean call = BankCallUtils.callApiBg(cardBean);
        String respCode = call == null ? "" : call.getRetCode();
        logger.info("保存用户银行卡信息  银行返回码:",respCode);
        // 如果调用成功
        if (BankCallConstant.RESPCODE_SUCCESS.equals(respCode)) {
            String usrCardInfolist = call.getSubPacks();
            logger.info("保存用户银行卡信息  usrCardInfolist:{} ",JSONObject.toJSONString(usrCardInfolist));
            if (!StringUtils.isEmpty(usrCardInfolist)) {
                JSONArray array = JSONObject.parseArray(usrCardInfolist);
                if (array != null && array.size() > 0) {
                    logger.info("保存用户银行卡信息  array:{}",JSONObject.toJSONString(array));
                    // 查询有结果  取第一条
                    JSONObject obj = null;
                    obj = array.getJSONObject(0);
                    logger.info("保存用户银行卡信息  obj:{}",JSONObject.toJSONString(obj));
                    BankCardRequest bank = new BankCardRequest();
                    bank.setUserId(userId);
                    // 设置绑定的手机号
                    bank.setMobile(bean.getMobile());
                    bank.setUserName(user.getUsername());
                    bank.setStatus(1);// 默认都是1
                    bank.setCardNo(obj.getString("cardNo"));
                    // 银行联号
                    String payAllianceCode = "";
                    // 调用江西银行接口查询银行联号
                    logger.info("调用江西银行接口查询银行联号  cardNo:{},  userId:{} ",obj.getString("cardNo"),userId);
                    BankCallBean payAllianceCodeQueryBean = this.payAllianceCodeQuery(obj.getString("cardNo"), userId);
                    if (payAllianceCodeQueryBean != null && BankCallConstant.RESPCODE_SUCCESS.equals(payAllianceCodeQueryBean.getRetCode())) {
                        payAllianceCode = payAllianceCodeQueryBean.getPayAllianceCode();
                    } else {
                        logger.error("调用江西银行查询联行号失败，userId:{}", userId);
                    }
                    logger.info("------------------------------保存用户银行卡信息  payAllianceCode:{}",payAllianceCode);
                    SimpleDateFormat sdf = GetDate.yyyymmddhhmmss;
                    try {
                        bank.setCreateTime(sdf.parse(obj.getString("txnDate") + obj.getString("txnTime")));
                    } catch (ParseException e) {
                        logger.error("银行返回日期格式化失败，userId:{}", userId);
                    }
                    logger.info("-------------保存用户银行卡信息  setCreateTime:{}",bank.getCreateTime());
                    bank.setCreateUserId(userId);
                    logger.info("---------------------------保存用户银行卡信息  userId  {}   ",userId);
                    // 根据银行卡号查询所  bankId
                    // 调用config原子层
                    String bankId = amConfigClient.queryBankIdByCardNo(obj.getString("cardNo"));
                    logger.info("--------------保存银行卡信息，插入用户银行卡,bankId:",bankId);
                    if (!StringUtils.isEmpty(bankId)) {
                        bank.setBankId(Integer.parseInt(bankId));
                        JxBankConfigVO banksConfigVO = amConfigClient.selectJxBankConfigByBankId(Integer.valueOf(bankId));
                        if (banksConfigVO != null) {
                            bank.setBank(banksConfigVO.getBankName());
                            logger.info("保存用户银行卡所属银行  banksConfigVO.getBankName()  {}   ",banksConfigVO.getBankName());
                            // 如果联行号为空  则更新联行号
                            if (StringUtils.isEmpty(payAllianceCode)) {
                                payAllianceCode = banksConfigVO.getPayAllianceCode();
                            }
                        }
                    }else {
                        logger.error("根据银行卡号查询所  bankId失败，bankId:{}", bankId);
                    }
                    // 更新联行号
                    bank.setPayAllianceCode(payAllianceCode);
                    logger.info("--------------保存银行卡信息，插入用户银行卡,bank:"+JSONObject.toJSONString(bank));
                    boolean bankFlag = amUserClient.insertUserCard(bank) > 0 ? true : false;
                    if (!bankFlag) {
                        logger.error("插入用户银行卡失败！");
                    }
                } else {
                    logger.error("更新银行卡信息出错，转换array失败，userId:{}", userId);
                }
            } else {
                logger.error("更新银行卡信息出错，返回空，userId:{}", userId);
            }
        } else {
            logger.error("更新银行卡信息出错，银行返回码  {},  BankCallConstant.RESPCODE_SUCCESS{}", respCode,BankCallConstant.RESPCODE_SUCCESS);
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

}
