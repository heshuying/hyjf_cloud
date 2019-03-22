package com.hyjf.am.user.service.admin.exception.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.admin.config.SystemConfig;
import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.response.user.OpenAccountEnquiryResponse;
import com.hyjf.am.resquest.admin.OpenAccountEnquiryDefineRequest;
import com.hyjf.am.resquest.user.BankOpenAccountLogRequest;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.dao.model.customize.OpenAccountEnquiryCustomize;
import com.hyjf.am.user.service.admin.exception.BankOpenAccountLogService;
import com.hyjf.am.user.service.admin.membercentre.UserManagerService;
import com.hyjf.am.user.service.front.user.AppUtmRegService;
import com.hyjf.am.user.service.front.user.UserInfoService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.OpenAccountEnquiryDefineResultBeanVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.IdCard15To18;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        logger.info("==========保存开户(User)的数据requestBean:"+JSONObject.toJSONString(requestBean) );
        OpenAccountEnquiryDefineResultBeanVO resultBean= new OpenAccountEnquiryDefineResultBeanVO();
        OpenAccountEnquiryResponse response =  new OpenAccountEnquiryResponse();
        String idCard = requestBean.getIdcard();
        String platform = requestBean.getPlatform();//开户平台
        try {
            if (StringUtils.isEmpty(idCard)) {
                resultBean.setStatus(BankCallConstant.BANKOPEN_USER_ACCOUNT_N);
                resultBean.setResult("身份不能为空");
                response.setOpenAccountEnquiryDefineResultBeanVO(resultBean);
                return response;
            }
            if (StringUtils.isEmpty(platform)) {
                resultBean.setStatus(BankCallConstant.BANKOPEN_USER_ACCOUNT_N);
                resultBean.setResult("开户平台platform不能为空");
                response.setOpenAccountEnquiryDefineResultBeanVO(resultBean);
                return response;
            }
            Integer userId = Integer.valueOf(requestBean.getUserid());
            // 获取用户信息
            User user = userService.findUserByUserId(userId);
            // 开户更新开户渠道统计开户时间
            AppUtmReg appUtmReg = appUtmRegService.findByUserId(userId);
            logger.info("----------开户更新开户渠道统计开户时间。。。appUtmReg:"+JSONObject.toJSONString(appUtmReg));
            logger.info("----------开户更新开户渠道统计开户时间。。。,userId:"+userId);
            if (appUtmReg != null) {
                AppUtmReg appUtmRegUser = new AppUtmReg();
                BeanUtils.copyProperties(appUtmReg,appUtmRegUser);
                appUtmRegUser.setOpenAccountTime(GetDate.str2Date(requestBean.getRegTimeEnd(), GetDate.yyyyMMdd));
                logger.info("开户更新开户渠道统计开户时间。。。appUtmRegUser："+JSONObject.toJSONString(appUtmRegUser));
                int appUtmRegFlag =  appUtmRegService.updateByPrimaryKeySelective(appUtmRegUser);
                logger.info("开户更新开户渠道统计开户时间。。。appUtmRegFlag："+appUtmRegFlag);
                if(appUtmRegFlag <1){
                    throw new RuntimeException("开户更新开户渠道统计开户时间失败");
                }
            }
            logger.info("----------删除用户开户日志表start---------------------");
            boolean deleteLogFlag = this.deleteBankOpenAccountLogByUserId(userId);
            if (!deleteLogFlag) {
                throw new RuntimeException("删除用户开户日志表失败");
            }
            logger.info("----------删除用户开户日志表end---------------------");
            // 查询返回的电子账号是否已开户
            boolean result = checkAccountByAccountId(requestBean.getAccountId());
            if (result) {
                // 校验未通过
                logger.error("==========该电子账号已被用户关联,无法完成掉单修复!============关联电子账号: " + requestBean.getAccountId());
                throw new Exception("该电子账号已被用户关联,无法完成掉单修复!");
            }

            String trueName = requestBean.getName();
            if (idCard.length() < 18) {
                try {
                    idCard = IdCard15To18.getEighteenIDCard(idCard);
                } catch (Exception e) {
                    logger.error("===========身份证转换异常!=============");
                    throw new RuntimeException("身份证转换异常!");
                }
            }
            logger.info("===========身份证转换---------------------"+idCard);
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
            user.setIsSetPassword(getIsSetPassword(requestBean.getAccountId(), userId));
            user.setMobile(requestBean.getMobile());
            // 更新相应的用户表
            logger.info("===========更新用户表---------------------user:"+JSONObject.toJSONString(user));
            boolean usersFlag = userService.updateUserSelective(user) > 0 ? true : false;
            if (!usersFlag) {
                logger.error("更新用户表失败！");
                throw new RuntimeException("更新用户表失败！");

            }
            UserInfo userInfo = userInfoService.findUsersInfo(userId);
            if (userInfo == null) {
                logger.error("用户详情表数据错误，用户id：" + user.getUserId());
                throw new RuntimeException("用户详情表数据错误！");
            }
            userInfo.setTruename(trueName);
            userInfo.setIdcard(idCard);
            userInfo.setSex(sexInt);
            userInfo.setBirthday(birthDay);
            userInfo.setTruenameIsapprove(1);
            userInfo.setMobileIsapprove(1);
            // 更新用户详细信息表
            logger.info("===========更新用户详细信息表---------------------user:"+JSONObject.toJSONString(userInfo));
            boolean userInfoFlag = userService.updateUserInfoByUserInfoSelective(userInfo) > 0 ? true : false;
            if (!userInfoFlag) {
                logger.error("更新用户详情表失败！");
                throw new RuntimeException("更新用户详情表失败！");
            }
            // 插入江西银行关联表
            BankOpenAccount openAccount = new BankOpenAccount();
            openAccount.setUserId(userId);
            openAccount.setUserName(user.getUsername());
            openAccount.setAccount(requestBean.getAccountId());
            //openAccount.setCreateTime(GetDate.stringToDate(requestBean.getRegTimeEnd()));
            openAccount.setCreateUserId(userId);
            logger.info("===========插入江西银行关联表---------------------user:"+JSONObject.toJSONString(openAccount));
            boolean openAccountFlag = userService.insertBankOpenAccount(openAccount) > 0 ? true : false;
            if (!openAccountFlag) {
                logger.error("插入江西银行关联表失败！");
                throw new RuntimeException("插入江西银行关联表失败！");
            }

            // add 合规数据上报 埋点 liubin 20181122 start
            // 推送数据到MQ 开户 出借人
            if (requestBean.getRoleId().equals("1")) {
                JSONObject params = new JSONObject();
                params.put("userId", userId);
                commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.OPEN_ACCOUNT_SUCCESS_TAG, UUID.randomUUID().toString(), params),
                        MQConstant.HG_REPORT_DELAY_LEVEL);
            }
            // add by liuyang 20180227 开户掉单处理成功之后 发送法大大CA认证MQ  start
            // 加入到消息队列
            Map<String, String> params = new HashMap<String, String>();
            params.put("mqMsgId", GetCode.getRandomCode(10));
            params.put("userId", String.valueOf(userId));
            try {
                logger.info("开户异步处理，发送MQ，userId:[{}],mqMgId:[{}]", userId, params.get("mqMsgId"));

                commonProducer.messageSend(new MessageContent(MQConstant.FDD_CERTIFICATE_AUTHORITY_TOPIC, UUID.randomUUID().toString(), params));
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("开户掉单处理成功之后 发送法大大CA认证MQ消息失败！userId:[{}]", userId);
                throw new RuntimeException("开户掉单处理成功之后 发送法大大CA认证MQ消息失败！");
            }
        }catch (Exception e) {
            logger.error("开户掉单同步用户失败!");
            e.printStackTrace();
            throw new RuntimeException("开户掉单同步用户失败！");
        }
        resultBean.setStatus(BankCallConstant.BANKOPEN_USER_ACCOUNT_Y);
        resultBean.setResult("开户掉单同步用户成功!");
        response.setOpenAccountEnquiryDefineResultBeanVO(resultBean);
        return response;
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
    /**
     * 插入用户绑定的银行卡
     * @param bankCard
     * @return
     */
    @Override
    public int insertUserCard(BankCard bankCard) {
        return this.bankCardMapper.insertSelective(bankCard);
    }

}
