package com.hyjf.am.user.service.front.user.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.resquest.api.WrbRegisterRequest;
import com.hyjf.am.user.dao.mapper.auto.UserInfoMapper;
import com.hyjf.am.user.dao.mapper.auto.UserLogMapper;
import com.hyjf.am.user.dao.mapper.auto.UserMapper;
import com.hyjf.am.user.dao.mapper.auto.UtmRegMapper;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.mq.base.MessageContent;
import com.hyjf.am.user.mq.producer.SmsProducer;
import com.hyjf.am.user.service.front.user.WrbService;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author lisheng
 * @version WrbServiceImpl, v0.1 2018/9/27 10:21
 */
@Service
public class WrbServiceImpl implements WrbService {

    Logger log = LoggerFactory.getLogger(WrbServiceImpl.class);

    @Autowired
    protected UtmRegMapper utmRegMapper;
    @Autowired
    private UserMapper userMapper;
   /// @Autowired
   // protected AppChannelStatisticsDetailMapper appChannelStatisticsDetailMapper;
    @Autowired
    protected UserInfoMapper usersInfoMapper;
   // @Autowired
   // protected AccountMapper accountMapper;
   @Autowired
   private SmsProducer smsProcesser;

    @Autowired
    protected UserLogMapper usersLogMapper;
    // 根据电话号码和模版号给某电话发短信
    public static final String SMSSENDFORMOBILE = "smsSendForMobile";

    @Value("${hyjf.third.party.user.password}")
    private String HYJF_THIRD_PARTY_USER_PASSWORD;

    @Override
    public Integer insertUserAction(WrbRegisterRequest wrbRegisterRequest) {
        int userId = 0;
        try {
            User user = new User();
            user.setIsInstFlag(1);
            user.setInstCode(wrbRegisterRequest.getInstCode());
            // 插入huiyingdai_users表
            this.insertUser(wrbRegisterRequest.getMobile(), user, wrbRegisterRequest.getIpAddr(),wrbRegisterRequest.getPlatform(),wrbRegisterRequest.getInstCode());
            userId = user.getUserId();
            // 插入huiyingdai_users_info
            this.insertUsersInfo(userId, wrbRegisterRequest.getInstType());
            // 插入huiyingdai_account表
            this.insertAccount(userId);
            // 插入注册渠道
            UtmPlatVO utmPlat = wrbRegisterRequest.getUtmPlat();
            UtmPlat utm = CommonUtils.convertBean(utmPlat, UtmPlat.class);
            this.insertUtmInfo(userId,user.getUsername(),utm);
            // 插入注册记录表
            this.insertUsersLog(userId, wrbRegisterRequest.getIpAddr());
        } catch (Exception e) {
            return userId;
        }
        return userId;
    }



    /**
     * 插入用户渠道信息
     * @param userId
     * @param userName
     * @param utmPlat
     */
    private void insertUtmInfo(int userId,String userName, UtmPlat utmPlat) {
        // 来源为PC统计
        if (utmPlat.getSourceType() == 0) {
            UtmReg utmReg = new UtmReg();
            utmReg.setCreateTime(new Date());
            utmReg.setUtmId(utmPlat.getSourceId());
            utmReg.setUserId(userId);
            utmReg.setOpenAccount(0);
            utmReg.setBindCard(0);
            utmRegMapper.insertSelective(utmReg);
            System.out.println("updateRegistUser***********************************预插入utmReg：" + JSON.toJSONString(utmReg));
        }
        // 来源为APP统计
        if (utmPlat.getSourceType() == 1) {
/*
            AppChannelStatisticsDetail detail = new AppChannelStatisticsDetail();
            detail.setSourceId(utmPlat.getSourceId());
            detail.setSourceName(utmPlat.getSourceName() != null ? utmPlat.getSourceName() : "");
            detail.setUserId(userId);
            detail.setUserName(userName);
            detail.setRegisterTime(new Date());
            detail.setCumulativeInvest(BigDecimal.ZERO);
            appChannelStatisticsDetailMapper.insertSelective(detail);
*/
        }
    }

    /**
     * 插入huiyingdai_user表
     *
     * @param mobile
     * @param user
=     * @param platform
     * @throws Exception
     */
    private void insertUser(String mobile, User user, String ipAddr,String platform,String instCode) throws Exception {
        // 写入用户信息表
        String userName = getUniqueUsername(mobile);
        user.setUsername(userName);// 用户名
        user.setMobile(mobile); // 手机号
        user.setRechargeSms(0); // 充值成功短信 0发送 1不发送
        user.setWithdrawSms(0); // withdraw_sms
        //user.setInvestflag(0);// 新手标志位：0新手 1老手
        user.setInvestSms(0);// 投资成功短信 0发送 1不发送
        user.setRecieveSms(0);// 回收成功短信 0发送 1不发送
        //user.setVersion(BigDecimal.ZERO);// 版本号
        user.setUserType(0);// 用户类型 0普通用户 1企业用户
        user.setIsSetPassword(0);// 是否设置了交易密码 0未设置 1已设置
        user.setBankOpenAccount(0);// 是否银行开户,0未开户,1已开户
        user.setRegEsb(Integer.parseInt(platform));// 账户开通平台 0pc 1微信 2安卓 3IOS 4其他
        String codeSalt = GetCode.getRandomCode(6);

        //处理纳觅财富注册用户数据密码随机生成6位字母数字组合并发送短信通知用户
        if (instCode.equals("11000002") || instCode.equals("11000003")) {
            final String password = StringRandomUtil.getStringRandom(6);
            user.setPassword(MD5Utils.MD5(MD5Utils.MD5(password) + codeSalt)); // 登陆密码
            final Integer userId = user.getUserId();
            final String mobiles = mobile;
            Thread thread = new Thread(){
                @Override
                public void run(){
                    log.info("线程启动，开始发短信");
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("val_password", password);
                    SmsMessage smsMessage = new SmsMessage(userId, param, mobiles, null, SMSSENDFORMOBILE, null, CustomConstants.THIRD_PARTY_REGIEST_PASSWORD, CustomConstants.CHANNEL_TYPE_NORMAL);
                    try {
                        smsProcesser.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(),JSON.toJSONBytes(smsMessage)));
                    } catch (MQException e) {
                        e.printStackTrace();
                    }

                }
            };
            thread.start();


            log.info("账号："+mobile+"，密码："+password);
        }else{
            String password = HYJF_THIRD_PARTY_USER_PASSWORD;
            user.setPassword(MD5Utils.MD5(MD5Utils.MD5(password) + codeSalt)); // 登陆密码
            log.info("账号："+mobile+"，密码："+password);
        }

        user.setRegIp(ipAddr); // 注册IP
        user.setRegTime(new Date());// 注册时间
        //user.setLoginIp(ipAddr);// 登陆ip
        //user.setLoginTime(nowTime);// 当期时间
       // user.setLastIp(ipAddr);// 上次登陆IP
        //user.setLastTime(nowTime);// 上次登陆时间
       // user.setLogintime(1);// 登录次数
        user.setStatus(0);
        user.setSalt(codeSalt);
        //user.setBorrowSms(0);
        //user.setPid(0);// 第三方平台ID
        //user.setUsernamep("");// 第三方平台用户名
        //user.setPtype(0); // 是否新注册 0为新 1为关联
        user.setOpenAccount(0);// 是否开户,0未开户,1已开户
        boolean isInsertFlag = userMapper.insertSelective(user) > 0 ? true : false;
        if (!isInsertFlag) {
            throw new Exception("插入User表失败~");
        }
    }
    /**
     * 插入huiyingdai_usersInfo
     *
     * @param userId
     */
    private void insertUsersInfo(Integer userId, Integer instType) throws Exception {
        // 当期时间
        int nowTime = GetDate.getNowTime10();
        UserInfo userInfo = new UserInfo();
        userInfo.setAttribute(0);// 默认为无主单
        userInfo.setUserId(userId);
        if (instType == 0) {
            userInfo.setRoleId(2);// 用户角色1投资人2借款人3垫付机构
            userInfo.setBorrowerType(2);// 借款人类型 1：内部机构 2：外部机构
        } else {
            userInfo.setRoleId(1);
        }
        userInfo.setMobileIsapprove(1);
        userInfo.setTruenameIsapprove(0);
        userInfo.setEmailIsapprove(0);
       // userInfo.setPromoteway(0);
        //userInfo.setIs51(0);
       // userInfo.setIsStaff(0);
        //userInfo.setDepartmentId(0);
       // userInfo.setNickname("");// 昵称
        userInfo.setBirthday("");// 生日
        userInfo.setSex(1);// 性别
        userInfo.setIdcard("");// 身份证号
       // userInfo.setEducation("");// 学历
        userInfo.setAddress("");// 地址
       // userInfo.setIntro("");// 个人简介
      //  userInfo.setInterest("");// 兴趣爱好
        userInfo.setUpdateTime(new Date());// 更新时间
        //userInfo.setParentId(0);
        userInfo.setIsContact(0);
        boolean isInsertFlag = usersInfoMapper.insertSelective(userInfo) > 0 ? true : false;
        if (!isInsertFlag) {
            throw new Exception("插入用户信息表失败~,用户ID:[" + userId + "].");
        }
    }

    /**
     * 插入huiyingdai_account表
     *
     * @param userId
     */
    private void insertAccount(Integer userId) throws Exception {
        /*Account account = new Account();
        account.setUserId(userId);
        account.setTotal(BigDecimal.ZERO);
        account.setIncome(BigDecimal.ZERO);
        account.setExpend(BigDecimal.ZERO);
        account.setBalance(BigDecimal.ZERO);
        account.setBalanceCash(BigDecimal.ZERO);
        account.setBalanceFrost(BigDecimal.ZERO);
        account.setFrost(BigDecimal.ZERO);
        account.setAwait(BigDecimal.ZERO);
        account.setRepay(BigDecimal.ZERO);
        account.setFrostCash(BigDecimal.ZERO);
        account.setRecMoney(BigDecimal.ZERO);
        account.setFee(BigDecimal.ZERO);
        account.setInMoney(BigDecimal.ZERO);
        account.setInMoneyFlag(0);
        // 计划账户相关
        account.setPlanAccedeTotal(BigDecimal.ZERO);// 计划累计加入总额
        account.setPlanBalance(BigDecimal.ZERO);// 计划可用余额
        account.setPlanFrost(BigDecimal.ZERO);// 计划冻结金额
        account.setPlanAccountWait(BigDecimal.ZERO);// 计划待收总额
        account.setPlanCapitalWait(BigDecimal.ZERO);// 计划待收本金
        account.setPlanInterestWait(BigDecimal.ZERO);// 计划待收利息
        account.setPlanRepayInterest(BigDecimal.ZERO);// 计划累计收益
        account.setPlanAccedeBalance(BigDecimal.ZERO);// 计划加入可用余额
        account.setPlanAccedeFrost(BigDecimal.ZERO);// 投资汇添金标的投资的未放款金额
        account.setVersion(BigDecimal.ZERO);
        // 江西银行账户相关
        account.setBankBalance(BigDecimal.ZERO);// 江西银行可用余额
        account.setBankBalanceCash(BigDecimal.ZERO);// 江西银行可提现金额(银行电子账户余额)
        account.setBankFrost(BigDecimal.ZERO);// 江西银行冻结金额
        account.setBankFrostCash(BigDecimal.ZERO);// 江西银行冻结金额(银行电子账户冻结金额)
        account.setBankInterestSum(BigDecimal.ZERO);// 银行累计收益
        account.setBankInvestSum(BigDecimal.ZERO);// 银行累计投资
        account.setBankWaitCapital(BigDecimal.ZERO);// 银行待还本金
        account.setBankWaitInterest(BigDecimal.ZERO);// 银行待还利息
        account.setBankWaitRepay(BigDecimal.ZERO);// 银行待还本息
        account.setBankTotal(BigDecimal.ZERO);// 银行总资产
        account.setBankAwaitCapital(BigDecimal.ZERO);// 银行待收本金
        account.setBankAwaitInterest(BigDecimal.ZERO);// 银行待收利息
        account.setBankAwait(BigDecimal.ZERO);// 银行待收总额
        account.setBankWaitRepayOrg(BigDecimal.ZERO);// 待还垫付机构金额
        account.setBankAwaitOrg(BigDecimal.ZERO);// 银行垫付机构待收垫付总额
        boolean isInsertFlag = accountMapper.insertSelective(account) > 0 ? true : false;
        // 插入账户表失败
        if (!isInsertFlag) {
            throw new Exception("插入用户账户表失败~,用户ID:[" + userId + "]");
        }*/
    }

    /**
     * 插入注册记录表
     *
     * @param userId
     * @throws Exception
     */
    private void insertUsersLog(Integer userId, String ipAddr) throws Exception {
        UserLog userLog = new UserLog();
        userLog.setUserId(userId);
        userLog.setIp(ipAddr);
        userLog.setEvent("register");
        userLog.setContent("注册成功");
        boolean isInsertFlag = usersLogMapper.insertSelective(userLog) > 0 ? true : false;
        if (!isInsertFlag) {
            throw new Exception("插入注册记录表失败~,用户ID:[" + userId + "].");
        }
    }

    /**
     * 获取唯一username
     *
     * @param mobile
     * @return
     */
    private String getUniqueUsername(String mobile) {
        String username = "hyjf" + mobile.substring(mobile.length() - 6, mobile.length());
        // 第一规则
        UserExample ue = new UserExample();
        UserExample.Criteria cr = ue.createCriteria();
        cr.andUsernameEqualTo(username);
        int cn1 = userMapper.countByExample(ue);
        if (cn1 > 0) {
            // 第二规则
            UserExample ue2 = new UserExample();
            UserExample.Criteria cr2 = ue2.createCriteria();
            username = "hyjf" + mobile;
            cr2.andUsernameEqualTo(username);
            int cn2 = userMapper.countByExample(ue2);
            if (cn2 > 0) {
                // 第三规则
                int i = 0;
                while (true) {
                    i++;
                    UserExample ue3 = new UserExample();
                    UserExample.Criteria cr3 = ue3.createCriteria();
                    username = "hyjf" + mobile.substring(mobile.length() - 6, mobile.length()) + i;
                    cr3.andUsernameEqualTo(username);
                    int cn3 = userMapper.countByExample(ue3);
                    if (cn3 == 0) {
                        break;
                    }
                }
            }
        }
        return username;
    }
}
