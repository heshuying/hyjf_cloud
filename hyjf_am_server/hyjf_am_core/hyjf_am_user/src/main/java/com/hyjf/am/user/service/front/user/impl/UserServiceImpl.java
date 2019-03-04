package com.hyjf.am.user.service.front.user.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.user.config.SystemConfig;
import com.hyjf.am.user.dao.mapper.auto.LockedUserInfoMapper;
import com.hyjf.am.user.dao.mapper.customize.QianleUserCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.dao.model.bifa.BifaIndexUserInfoBean;
import com.hyjf.am.user.dao.model.customize.UserDepartmentInfoCustomize;
import com.hyjf.am.user.dao.model.customize.UserUtmInfoCustomize;
import com.hyjf.am.user.mq.base.CommonProducer;
import com.hyjf.am.user.mq.base.MessageContent;
import com.hyjf.am.user.service.front.user.UserService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.constants.UserConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xiasq
 * @version UserServiceImpl, v0.1 2018/1/21 22:42
 */

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    QianleUserCustomizeMapper qianleUserCustomizeMapper;
    @Autowired
    protected LockedUserInfoMapper lockedUserInfoMapper;

    @Autowired
    private CommonProducer smsProducer;

    @Autowired
    SystemConfig systemConfig;
    /**
     * 注册
     *
     * @param userRequest
     * @return
     * @throws MQException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public User register(RegisterUserRequest userRequest) throws MQException {

        String mobile = userRequest.getMobile();
        String loginIp = userRequest.getLoginIp();
        String reffer = userRequest.getReffer();
        String platform = userRequest.getPlatform();
        String password = userRequest.getPassword();
        String utmId = userRequest.getUtmId();
        Integer instType = userRequest.getInstType();
        Integer userType = userRequest.getUserType();

        Integer attribute = null;
        // 获取推荐人表
        User refferUser = getRefferUsers(mobile, reffer);
        if (refferUser != null) {
            UserInfo refferUserInfo = findUsersInfo(refferUser.getUserId());
            if (refferUserInfo != null) {
                // 如果该用户的上级不为空
                if (Validator.isNotNull(refferUserInfo.getAttribute())) {
                    if (Arrays.asList(2, 3).contains(refferUserInfo.getAttribute())) {
                        // 有推荐人且推荐人为员工(Attribute=2或3)时才设置为有主单
                        attribute = 1;
                    }
                }
            }
        }

        // 1. 写入用户信息表
        User user = this.insertUser(mobile, password, loginIp, platform, userRequest.getInstCode(), userType);
        logger.info("写入用户...user is :{}", JSONObject.toJSONString(user));
        int userId = user.getUserId();

        // 2. 写入用户详情表
        this.insertUserInfo(userId, loginIp, attribute,instType,userRequest.getProvince(),userRequest.getCity());

        // 3. 写入用户账户表 迁移到组合层发送mq消息 避免连接mq超时引起长事务
        // this.insertAccount(userId);

        // 4. 有推荐人，保存推荐人信息
        if (refferUser != null) {
            this.insertSpreadsUser(userId, refferUser.getUserId(), loginIp);
        }

        // 5. 有推广，插入utmReg表
        if (StringUtils.isNotEmpty(utmId) && Validator.isNumber(utmId)&&!(platform.equals(ClientConstants.APP_CLIENT)||platform.equals(ClientConstants.APP_CLIENT_IOS))) {
            this.insertUtmReg(userId, utmId);
        }

        // 6. 保存用户注册日志
        this.insertRegLog(userId, loginIp);

        // 7. 注册成功默认登录
        this.updateLoginUser(userId, loginIp);

        return user;
    }


    /** 获取唯一username */
    @Override
    public String generateUniqueUsername(String mobile) {
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

    @Override
    public User findUserByMobile(String mobile) {
        UserExample usersExample = new UserExample();
        usersExample.createCriteria().andMobileEqualTo(mobile);
        List<User> usersList = userMapper.selectByExample(usersExample);
        if (!CollectionUtils.isEmpty(usersList)) {
            return usersList.get(0);
        }
        return null;
    }

    @Override
    public User findUserByUsernameOrMobile(String condition) {
        UserExample mobileExample = new UserExample();
        mobileExample.createCriteria().andMobileEqualTo(condition);
        List<User> usersList1 = userMapper.selectByExample(mobileExample);
        if (!CollectionUtils.isEmpty(usersList1)) {
            return usersList1.get(0);
        }

        UserExample usernameExample = new UserExample();
        usernameExample.createCriteria().andUsernameEqualTo(condition);
        List<User> usersList2 = userMapper.selectByExample(usernameExample);
        if (!CollectionUtils.isEmpty(usersList2)) {
            return usersList2.get(0);
        }
        return null;
    }

    @Override
    public User updateUserByUsernameOrMobile(String condition) {
        UserExample mobileExample = new UserExample();
        mobileExample.createCriteria().andMobileEqualTo(condition);
        List<User> usersList1 = userMapper.selectByExample(mobileExample);
        if (!CollectionUtils.isEmpty(usersList1)) {
            return usersList1.get(0);
        }

        UserExample usernameExample = new UserExample();
        usernameExample.createCriteria().andUsernameEqualTo(condition);
        List<User> usersList2 = userMapper.selectByExample(usernameExample);
        if (!CollectionUtils.isEmpty(usersList2)) {
            return usersList2.get(0);
        }
        return null;
    }

    @Override
    public User findUserByRecommendName(String reffer) {
        UserExample usersExample = new UserExample();
        UserExample.Criteria criteria = usersExample.createCriteria();
        if (Validator.isMobile(reffer)) {
            criteria.andMobileEqualTo(reffer);
        } else if(Validator.isNumber(reffer)){
            criteria.andUserIdEqualTo(Integer.valueOf(reffer));
        }else{
            logger.error("参数reffer:[{}] -> 既不是手机号,又不是userId");
            return null;
        }
        List<User> usersList = userMapper.selectByExample(usersExample);
        if (!CollectionUtils.isEmpty(usersList)) {
            return usersList.get(0);
        }
        return null;
    }


    @Override
    public User findReffer(Integer userId) {
        //查推荐人ID
        SpreadsUserExample spreadsUsersExample = new SpreadsUserExample();
        SpreadsUserExample.Criteria spreadsUsersExampleCriteria = spreadsUsersExample.createCriteria();
        spreadsUsersExampleCriteria.andUserIdEqualTo(userId);
        List<SpreadsUser> sList = spreadsUserMapper.selectByExample(spreadsUsersExample);
        //根据推荐人ID查用户
        if(!CollectionUtils.isEmpty(sList)){
            UserExample usersExample = new UserExample();
            UserExample.Criteria criteria = usersExample.createCriteria();
            criteria.andUserIdEqualTo(Integer.valueOf(sList.get(0).getSpreadsUserId()));
            List<User> usersList = userMapper.selectByExample(usersExample);
            if (!CollectionUtils.isEmpty(usersList)) {
                return usersList.get(0);
            }
        }
        return null;
    }

    @Override
    public void updateLoginUser(int userId, String ip) {
        UserLoginLog userLoginLog = findUserLoginLogByUserId(userId);
        if (userLoginLog == null) {
            userLoginLog = new UserLoginLog();
            userLoginLog.setUserId(userId);
            userLoginLog.setLoginIp(ip);
            userLoginLog.setLoginTime(new Date());
            userLoginLog.setLastIp(userLoginLog.getLoginIp());
            userLoginLog.setLastTime(userLoginLog.getLoginTime());
            userLoginLog.setLoginTimes(1);
            userLoginLog.setCreateTime(new Date());
            userLoginLogMapper.insertSelective(userLoginLog);
        } else {
            if (userLoginLog.getLoginIp() != null) {
                userLoginLog.setLastIp(userLoginLog.getLoginIp());
            }
            if (userLoginLog.getLoginTime() != null) {
                userLoginLog.setLastTime(userLoginLog.getLoginTime());
            }
            userLoginLog.setLoginIp(ip);
            userLoginLog.setLoginTime(new Date());
            // 登录次数
            userLoginLog.setLoginTimes(userLoginLog.getLoginTimes() + 1);
            userLoginLog.setUpdateTime(new Date());
            userLoginLogMapper.updateByPrimaryKeySelective(userLoginLog);
        }
    }

    @Override
    public void updateUser(int userId, String ip,User user) {
        UserLoginLog userLoginLog = findUserLoginLogByUserId(userId);
        if (userLoginLog == null) {
            userLoginLog = new UserLoginLog();
            userLoginLog.setUserId(userId);
            userLoginLog.setLoginIp(ip);
            userLoginLog.setLoginTime(new Date());
            userLoginLog.setLastIp(userLoginLog.getLoginIp());
            userLoginLog.setLastTime(userLoginLog.getLoginTime());
            userLoginLog.setLoginTimes(1);
            userLoginLog.setCreateTime(new Date());
            userLoginLogMapper.insertSelective(userLoginLog);
        } else {
            if (userLoginLog.getLoginIp() != null) {
                userLoginLog.setLastIp(userLoginLog.getLoginIp());
            }
            if (userLoginLog.getLoginTime() != null) {
                userLoginLog.setLastTime(userLoginLog.getLoginTime());
            }
            userLoginLog.setLoginIp(ip);
            userLoginLog.setLoginTime(new Date());
            // 登录次数
            userLoginLog.setLoginTimes(userLoginLog.getLoginTimes() + 1);
            userLoginLog.setUpdateTime(new Date());
            userLoginLogMapper.updateByPrimaryKeySelective(userLoginLog);
        }
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 注册查询推荐人信息
     *
     * @param mobile
     * @param reffer
     * @return
     */
    private User getRefferUsers(String mobile, String reffer) {
        List<User> recommends = new ArrayList<User>();
        // 以下语句用来设置用户有无主单开始 2015年12月30日18:28:34 孙亮
        // 1.注册成功时，推荐人关联
        // B1、用户在注册时，没有填写推荐人，也没有预注册，或预注册时没有关联推荐人，则推荐人为空；
        // B2、用户注册时，填写了推荐人，忽略关联推荐人，以填写的推荐人为准；
        // B3、用户注册时，没有填写推荐人，预注册表中，有关联推荐人，以关联的推荐人为准；
        PreRegistExample preRegistExample = new PreRegistExample();
        preRegistExample.createCriteria().andMobileEqualTo(mobile);
        List<PreRegist> preRegistList = preRegistMapper.selectByExample(preRegistExample);
        if (preRegistList != null && preRegistList.size() != 0) {
            for (int i = 0; i < preRegistList.size(); i++) {
                PreRegist model = preRegistList.get(i);
                model.setRegistFlag(1);
                model.setRegistTime(GetDate.getMyTimeInMillis());
                preRegistMapper.updateByPrimaryKey(model);
            }
        }

        if (StringUtils.isEmpty(reffer)) {
            if (preRegistList != null && preRegistList.size() != 0) {
                reffer = preRegistList.get(0).getReferrer();
            }
        }
        if (StringUtils.isNotEmpty(reffer)) {
            boolean isRefferValid = false;
            UserExample exampleUser = new UserExample();
            if (Validator.isMobile(reffer)) {
                UserExample.Criteria criteria = exampleUser.createCriteria();
                criteria.andMobileEqualTo(reffer);
                isRefferValid = true;
            } else {
                if(Validator.isNumber(reffer) && Long.valueOf(reffer)<=Integer.MAX_VALUE) {
                    UserExample.Criteria criteria1 = exampleUser.createCriteria();
                    Integer recommend = Integer.valueOf(reffer);
                    criteria1.andUserIdEqualTo(recommend);
                    isRefferValid = true;
                }
            }
            if(isRefferValid) {
                recommends = userMapper.selectByExample(exampleUser);
            }
        }
        if (recommends!= null && !CollectionUtils.isEmpty(recommends)) {
            return recommends.get(0);
        }
        return null;
    }

    /**
     * 注册写用户表
     *
     * @param mobile
     * @param password
     * @param loginIp
     * @param platform
     * @param
     * @param
     * @param userType 0:普通用户;1:企业用户;
     * @return
     */
    private User insertUser(String mobile, String password, String loginIp, String platform, String instCode, Integer userType) {
        User user = new User();
        String userName = generateUniqueUsername(mobile);
        user.setInstCode(StringUtils.isBlank(instCode) ? CommonConstant.HYJF_INST_CODE : instCode);
        user.setIsInstFlag(0);
        user.setUsername(userName);
        user.setMobile(mobile);
        user.setRechargeSms(0);
        user.setWithdrawSms(0);
        user.setInvestSms(0);
        user.setRecieveSms(0);
        user.setUserType(userType);
        user.setIsSetPassword(0);
        String codeSalt = GetCode.getRandomCode(6);
        //处理纳觅财富注册用户数据密码随机生成6位字母数字组合并发送短信通知用户
        if (instCode.equals("11000002") || instCode.equals("11000003")) {
            final String passwords = StringRandomUtil.getStringRandom(6);
            // 登陆密码
            user.setPassword(MD5Utils.MD5(MD5Utils.MD5(passwords) + codeSalt));
            final Integer userId = user.getUserId();
            final String mobiles = mobile;
            Thread thread = new Thread(){
                public void run(){
                    logger.info("线程启动，开始发短信");
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("val_password", passwords);
                    SmsMessage smsMessage = new SmsMessage(userId, param, mobiles, null, MessageConstant.SMS_SEND_FOR_MOBILE, null,
                            CustomConstants.THIRD_PARTY_REGIEST_PASSWORD, CustomConstants.CHANNEL_TYPE_NORMAL);
                    // 发送
                    try {
                        smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
                    } catch (MQException e) {
                        logger.error("短信发送失败...", e);
                    }
                }
            };
            thread.start();
            logger.info("账号："+mobile+"，密码："+passwords);
        }else {
            user.setPassword(MD5Utils.MD5(MD5Utils.MD5(password) + codeSalt));
        }
        user.setRegIp(loginIp);
        user.setRegTime(new Date());
        user.setStatus(0);
        user.setSalt(codeSalt);

        user.setOpenAccount(0);
        user.setBankOpenAccount(0);

        if (StringUtils.isNotBlank(platform)) {
            user.setRegEsb(Integer.parseInt(platform));
            // 账户开通平台 0pc 1微信 2安卓 3IOS 4其他
        }


        userMapper.insertSelective(user);
        return user;
    }

    /**
     * 注册写用户信息表
     *
     * @param userId
     * @param loginIp
     * @param attribute
     */
    private void insertUserInfo(int userId, String loginIp, Integer attribute,Integer instType,String province,String city ) {
        UserInfo userInfo = new UserInfo();
        userInfo.setAttribute(0);
        // 默认为无主单
        // 根据ip获取注册地址
        if (StringUtils.isNotEmpty(loginIp)) {
            //getAddress(loginIp, userInfo);
        }
        userInfo.setUserId(userId);
        // 默认出借人角色
        if (instType!=null&&instType == 0) {
            //用户角色1出借人2借款人3垫付机构
            userInfo.setRoleId(2);
            //借款人类型 1：内部机构 2：外部机构
            userInfo.setBorrowerType(2);
        } else {
            userInfo.setRoleId(1);
        }
        userInfo.setMobileIsapprove(1);
        userInfo.setTruenameIsapprove(0);
        userInfo.setEmailIsapprove(0);
        userInfo.setUpdateTime(new Date());
        userInfo.setBirthday("");
        userInfo.setSex(0);
        userInfo.setIdcard("");
        userInfo.setAddress("");
        userInfo.setTruenameIsapprove(0);
        userInfo.setEmailIsapprove(0);
        userInfo.setIsContact(0);
        userInfo.setAttribute(attribute);
		// add by libin 20181030
		userInfo.setProvince(province);
		userInfo.setCity(city);
		userInfo.setArea("");//目前不获取县区
		// add by libin 20181030
        logger.info("注册插入userInfo：{}", JSON.toJSONString(userInfo));
        userInfoMapper.insertSelective(userInfo);
    }

    /**
     * 注册保存推荐人
     *
     * @param userId
     *            注册用户
     * @param referer
     *            推荐人
     * @param loginIp
     */
    private void insertSpreadsUser(int userId, int referer, String loginIp) {
        SpreadsUser spreadUser = new SpreadsUser();
        spreadUser.setUserId(userId);
        spreadUser.setSpreadsUserId(referer);
        spreadUser.setCreateIp(loginIp);
        spreadUser.setCreateTime(new Date());
        spreadUser.setType("reg");
        spreadUser.setOpernote("reg");
        spreadUser.setOperation(userId + "");
        logger.info("注册插入spreadUser：{}", JSON.toJSONString(spreadUser));
        spreadsUserMapper.insertSelective(spreadUser);
    }

    /**
     * 注册保存推广信息
     *
     * @param userId
     * @param utmId
     */
    private void insertUtmReg(int userId, String utmId) {
        UtmReg utmReg = new UtmReg();
        utmReg.setCreateTime(new Date());
        utmReg.setUtmId(Integer.parseInt(utmId));
        utmReg.setUserId(userId);
        utmReg.setOpenAccount(0);
        utmReg.setBindCard(0);
        utmRegMapper.insertSelective(utmReg);
        logger.info("注册插入utmReg：{}", JSON.toJSONString(utmReg));
    }

    @Override
    public UtmPlat selectUtmPlatByUtmId(String utmId) {
        UtmPlatExample example = new UtmPlatExample();
        UtmPlatExample.Criteria cra = example.createCriteria();
        cra.andSourceIdEqualTo(Integer.parseInt(utmId));
        cra.andDelFlagEqualTo(0);
        List<UtmPlat> list = this.utmPlatMapper.selectByExample(example);
        if(list!=null&& list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 注册保存日志
     *
     * @param userId
     * @param loginIp
     */
    private void insertRegLog(int userId, String loginIp) {
        UserLog userLog = new UserLog();
        userLog.setUserId(userId);
        userLog.setIp(loginIp);
        userLog.setEvent("register");
        userLog.setContent("注册成功");
        logger.info("注册插入userLog：{}", JSON.toJSONString(userLog));
        userLogMapper.insertSelective(userLog);
    }

    /**
     * 根据userId查询登录日志
     *
     * @param userId
     * @return
     */
    private UserLoginLog findUserLoginLogByUserId(int userId) {
        UserLoginLogExample example = new UserLoginLogExample();
        UserLoginLogExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<UserLoginLog> list = userLoginLogMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }


    /**
     *
     * 根据用户id查询用户签约授权信息
     * @param userId
     * @return
     */
    @Override
    public HjhUserAuth getHjhUserAuthByUserId(Integer userId) {
        HjhUserAuthExample example = new HjhUserAuthExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<HjhUserAuth> list = hjhUserAuthMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            HjhUserAuth hjhUserAuth=new HjhUserAuth();
            hjhUserAuth.setAutoInvesStatus(0);
            hjhUserAuth.setAutoCreditStatus(0);
            hjhUserAuth.setAutoPaymentStatus(0);
            hjhUserAuth.setAutoRepayStatus(0);
            hjhUserAuth.setAutoConsumeStatus(0);
            return hjhUserAuth;
        }
    }

    /**
     *
     * 根据用户id查询用户签约授权信息
     * @param userId
     * @return
     */
    @Override
    public HjhUserAuth getHjhUserAuth(Integer userId) {
        HjhUserAuthExample example = new HjhUserAuthExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<HjhUserAuth> list = hjhUserAuthMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void insertSelective(HjhUserAuthLog hjhUserAuthLog){
        hjhUserAuthLogMapper.insertSelective(hjhUserAuthLog);
    }

    @Override
    public HjhUserAuthLog selectByExample(String orderId){
        HjhUserAuthLogExample example=new HjhUserAuthLogExample();
        example.setOrderByClause("id desc");
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<HjhUserAuthLog>  hjhUserAuthLogList = hjhUserAuthLogMapper.selectByExample(example);
        if(hjhUserAuthLogList!=null&&hjhUserAuthLogList.size()>0) {
            return hjhUserAuthLogList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public int updateByPrimaryKeySelective(HjhUserAuthLog record){

        return hjhUserAuthLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insertSelective(HjhUserAuth record){
        return hjhUserAuthMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(HjhUserAuth record){
        return hjhUserAuthMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 修改用户表By主键
     * @param record
     * @return int
     */
    @Override
    public int updateUserById(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 通过当前用户ID 查询用户所在一级分部,从而关联用户所属渠道
     * @param userId
     * @return
     */
    @Override
    public UserUtmInfoCustomize getUserUtmInfo(Integer userId) {
        return userCustomizeMapper.getUserUtmInfo(userId);
    }

    @Override
    public void updateUserAuthInves( BankRequest bean) {
        Integer userId = Integer.parseInt(bean.getLogUserId());
        Date nowTime= GetDate.getNowTime();
        HjhUserAuthLog hjhUserAuthLog=this.selectByExample(bean.getOrderId());
        //更新用户签约授权日志表
        if(hjhUserAuthLog!=null){
            hjhUserAuthLog.setUpdateTime(nowTime);
            hjhUserAuthLog.setUpdateUserId(userId);
            hjhUserAuthLog.setOrderStatus(1);
            hjhUserAuthLog.setAuthCreateTime(GetDate.getNowTime10());
            this.updateByPrimaryKeySelective(hjhUserAuthLog);
        }
        // 这里同步异步一起进来会导致重复插入的异常，加一个同步锁
        HjhUserAuth hjhUserAuth=this.getHjhUserAuth(userId);
        // 更新用户签约授权状态信息表
        if (hjhUserAuth == null) {
            User user= this.fUserByUserId(userId);
            hjhUserAuth = new HjhUserAuth();
            // 设置状态
            setAuthType(hjhUserAuth, bean);
            hjhUserAuth.setAutoWithdrawStatus(0);
            hjhUserAuth.setAutoConsumeStatus(0);
            hjhUserAuth.setUserId(user.getUserId());
            hjhUserAuth.setUserName(user.getUsername());
            hjhUserAuth.setCreateUserId(user.getUserId());
            hjhUserAuth.setCreateTime(nowTime);
            hjhUserAuth.setUpdateTime(nowTime);
            hjhUserAuth.setUpdateUserId(userId);
            hjhUserAuth.setDelFlag(0);
            this.insertSelective(hjhUserAuth);
        } else {
            HjhUserAuth updateHjhUserAuth = new HjhUserAuth();
            // 设置状态
            setAuthType(hjhUserAuth, bean);
            updateHjhUserAuth.setId(hjhUserAuth.getId());
            updateHjhUserAuth.setUpdateTime(nowTime);
            updateHjhUserAuth.setUpdateUserId(userId);
            this.updateByPrimaryKeySelective(hjhUserAuth);
        }
    }
    /**
     * 设置状态
     * @param hjhUserAuth
     * @param bean
     */
    private void setAuthType(HjhUserAuth hjhUserAuth, BankRequest bean) {
        // 授权类型
        String txcode = bean.getTxCode();
        if(ClientConstants.TXCODE_AUTO_BID_AUTH_PLUS.equals(txcode)){
            hjhUserAuth.setAutoInvesStatus(1);
            hjhUserAuth.setAutoOrderId(bean.getOrderId());
            hjhUserAuth.setAutoBidTime(GetDate.getNowTime10());
            hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
            hjhUserAuth.setAutoBidEndTime(bean.getDeadline());
        }else if(ClientConstants.TXCODE_AUTO_CREDIT_INVEST_AUTH_PLUSS.equals(txcode)){
            hjhUserAuth.setAutoCreditStatus(1);
            hjhUserAuth.setAutoCreditOrderId(bean.getOrderId());
            hjhUserAuth.setAutoCreditTime(GetDate.getNowTime10());
            hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
        }else if(ClientConstants.TXCODE_CREDIT_AUTH_QUERY.equals(txcode)){
            //根据银行查询出借人签约状态
            if(ClientConstants.QUERY_TYPE_1.equals(bean.getType())){
                hjhUserAuth.setAutoInvesStatus(1);
                hjhUserAuth.setAutoOrderId(bean.getOrderId());
                hjhUserAuth.setAutoBidTime(GetDate.getNowTime10());
                hjhUserAuth.setAutoBidEndTime(bean.getBidDeadline());
            }else if(ClientConstants.QUERY_TYPE_2.equals(bean.getType())){
                hjhUserAuth.setAutoCreditStatus(1);
                hjhUserAuth.setAutoCreditOrderId(bean.getOrderId());
                hjhUserAuth.setAutoCreditTime(GetDate.getNowTime10());
            }
        }
        // 新增缴费授权和还款授权
        else if(ClientConstants.TXCODE_PAYMENT_AUTH_PAGE.equals(txcode)){
            hjhUserAuth.setAutoPaymentStatus(1);
            hjhUserAuth.setAutoPaymentEndTime(bean.getDeadline());
            hjhUserAuth.setAutoPaymentTime(GetDate.getNowTime10());
        }else if(ClientConstants.TXCODE_REPAY_AUTH_PAGE.equals(txcode)){
            hjhUserAuth.setAutoRepayStatus(1);
            hjhUserAuth.setAutoRepayEndTime(bean.getDeadline());
            hjhUserAuth.setAutoRepayTime(GetDate.getNowTime10());
        }

        // 客户授权功能查询接口
        else if(ClientConstants.TXCODE_TERMS_AUTH_QUERY.equals(txcode)){
            //自动投标功能开通标志
            String autoBidStatus = bean.getAutoBid();
            //自动债转功能开通标志
            String autoTransfer = bean.getAutoTransfer();
            //缴费授权
            String paymentAuth = bean.getPaymentAuth();
            //还款授权
            String repayAuth = bean.getRepayAuth();
            if(StringUtils.isNotBlank(autoBidStatus)){
                hjhUserAuth.setAutoInvesStatus(Integer.parseInt(autoBidStatus));
            }
            if(StringUtils.isNotBlank(autoTransfer)){
                hjhUserAuth.setAutoCreditStatus(Integer.parseInt(autoTransfer));
            }
            if(StringUtils.isNotBlank(paymentAuth)){
                hjhUserAuth.setAutoPaymentStatus(Integer.parseInt(paymentAuth));
                hjhUserAuth.setAutoPaymentEndTime(bean.getPaymentDeadline());
            }
            if(StringUtils.isNotBlank(repayAuth)){
                hjhUserAuth.setAutoRepayStatus(Integer.parseInt(repayAuth));
                hjhUserAuth.setAutoRepayEndTime(bean.getRepayDeadline());
            }
            //自动投标到期日
            if(org.apache.commons.lang3.StringUtils.isNotBlank(bean.getAutoBidDeadline())) {
                hjhUserAuth.setAutoBidEndTime(bean.getAutoBidDeadline());
            }
        }

    }

    @Override
    public UserEvalationResult selectUserEvalationResultByUserId(Integer userId) {
        UserEvalationResultExample example = new UserEvalationResultExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<UserEvalationResult> userEvalationResult = userEvalationResultMapper.selectByExample(example);
        if (userEvalationResult != null && userEvalationResult.size() > 0) {
            return userEvalationResult.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void deleteUserEvalationResultByUserId(Integer userId) {
        UserEvalationResultExample userEvalationResultExample=new UserEvalationResultExample();
        userEvalationResultExample.createCriteria().andUserIdEqualTo(userId);
        List<UserEvalationResult> userEvalationResults=userEvalationResultMapper.selectByExample(userEvalationResultExample);
        UserEvalationResult userEvalationResult=null;
        if(userEvalationResults!=null&&userEvalationResults.size()!=0){
            userEvalationResult=userEvalationResults.get(0);
        }else{
            return;
        }
        UserEvalationExample userEvalationExample=new UserEvalationExample();
        userEvalationExample.createCriteria().andErIdEqualTo(userEvalationResult.getId());
        userEvalationMapper.deleteByExample(userEvalationExample);
        userEvalationResultMapper.deleteByPrimaryKey(userEvalationResult.getId());


    }

    @Override
    public AccountChinapnr getAccountChinapnr(Integer userId) {
        AccountChinapnr accountChinapnr = null;
        if (userId == null) {
            return new AccountChinapnr();
        }
        AccountChinapnrExample example = new AccountChinapnrExample();
        AccountChinapnrExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<AccountChinapnr> list = accountChinapnrMapper.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            accountChinapnr = list.get(0);
        }
        return accountChinapnr;
    }

    /**
     * 保存紧急联系人信息
     * @auther: hesy
     * @date: 2018/6/20
     */
    @Override
    public int updateUserContact(UsersContractRequest record){
        if(record.getUserId() == null) {
            return 0;
        }
        UserContact contact = new UserContact();
        BeanUtils.copyProperties(record, contact);
        contact.setCreateTime(GetDate.getNowTime());
        contact.setUpdateTime(GetDate.getNowTime());
        userContactMapper.deleteByPrimaryKey(record.getUserId());
        return userContactMapper.insert(contact);
    }

    /**
     * @Author: zhangqingqing
     * @Desc :查询紧急联系人
     * @Param: * @param userId
     * @Date: 14:09 2018/6/4
     * @Return: UserContact
     */
    @Override
    public UserContact selectUserContact(Integer userId){
        if(userId == null) {
            return null;
        }
        UserContact result = userContactMapper.selectByPrimaryKey(userId);
        return result;
    }

    /**
     * 检查邮箱是否已使用
     * @auther: hesy
     * @date: 2018/6/20
     */
    @Override
    public boolean checkEmailUsed(String email) {
        UserExample example1 = new UserExample();
        example1.createCriteria().andEmailEqualTo(email);
        int size = userMapper.countByExample(example1);
        if (size > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 插入绑定邮箱日志
     * @auther: hesy
     * @date: 2018/6/20
     */
    @Override
    public Integer insertEmailBindLog(UserBindEmailLog log) {
        // 将之前的邮件失效
        UserBindEmailLogExample example = new UserBindEmailLogExample();
        example.createCriteria().andUserIdEqualTo(log.getUserId()).andUserEmailStatusEqualTo(UserConstant.EMAIL_ACTIVE_STATUS_1);
        List<UserBindEmailLog> bingEmailLogList = userBindEmailLogMapper.selectByExample(example);
        if (bingEmailLogList != null && bingEmailLogList.size() > 0) {
            for (int i = 0; i < bingEmailLogList.size(); i++) {
                bingEmailLogList.get(i).setUserEmailStatus(UserConstant.EMAIL_ACTIVE_STATUS_3);
                userBindEmailLogMapper.updateByPrimaryKeySelective(bingEmailLogList.get(i));
            }
        }
        // 插入新的邮件
        log.setCreateTime(new Date());
        log.setEmailActiveUrlDeadtime(GetDate.getSomeDayBeforeOrAfter(new Date(), 1));
        log.setUserEmailStatus(UserConstant.EMAIL_ACTIVE_STATUS_1);
        return userBindEmailLogMapper.insertSelective(log);
    }

    /**
     * 查询绑定邮箱日志
     * @auther: hesy
     * @date: 2018/6/20
     */
    @Override
    public UserBindEmailLog getUserBindEmail(Integer userid) {
        UserBindEmailLogExample example = new UserBindEmailLogExample();
        example.createCriteria().andUserIdEqualTo(userid).andUserEmailStatusEqualTo(UserConstant.EMAIL_ACTIVE_STATUS_1);
        List<UserBindEmailLog> userBindEmailLogList = userBindEmailLogMapper.selectByExample(example);
        if (userBindEmailLogList != null && userBindEmailLogList.size() > 0) {
            return userBindEmailLogList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 绑定邮箱更新
     * @auther: hesy
     * @date: 2018/6/20
     */
    @Override
    public Integer updateBindEmail(Integer userId, String email) {
        UserExample example = new UserExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<User> usersList = userMapper.selectByExample(example);
        User u = usersList.get(0);
        u.setEmail(email);
        int result = userMapper.updateByPrimaryKeySelective(u);

        UserBindEmailLog log = this.getUserBindEmail(userId);
        if(log != null) {
            log.setUserEmailStatus(UserConstant.EMAIL_ACTIVE_STATUS_2);
            userBindEmailLogMapper.updateByPrimaryKey(log);
        }

        return result;
    }

    @Override
    public UserLoginLog selectByPrimaryKey(Integer userId){
        UserLoginLog userLoginLog = userLoginLogMapper.selectByPrimaryKey(userId);
        return userLoginLog;

    }

    @Override
    public List<User> selectUserByUsername(String repayOrgName) {
        // 根据垫付机构用户名检索垫付机构用户ID
        UserExample usersExample = new UserExample();
        UserExample.Criteria userCri = usersExample.createCriteria();
        userCri.andUsernameEqualTo(repayOrgName);
        userCri.andBankOpenAccountEqualTo(1);// 汇付已开户
        List<User> ulist = this.userMapper.selectByExample(usersExample);
        if (!CollectionUtils.isEmpty(ulist)) {
            return ulist;
        }
        return null;
    }

    @Override
    public Evalation getEvalationByCountScore(short countScore) {
        EvalationExample example = new EvalationExample();
        example.createCriteria().andScoreUpLessThanOrEqualTo(countScore).andScoreDownGreaterThanOrEqualTo(countScore).andStatusEqualTo(0);
        List<Evalation> list=evalationMapper.selectByExample(example);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public UserEvalationResult insertUserEvalationResult(Integer userId,String userAnswer,Integer countScore,String behaviorId) {
        UserEvalationResult userEvalationResult = selectUserEvalationResultByUserId(userId);
        if(null==userEvalationResult){
            userEvalationResult = new UserEvalationResult();
        }
        deleteUserEvalationResultByUserId(userId);
        List<String> answerList = new ArrayList<String>();
        List<String> questionList = new ArrayList<String>();
        if (!Strings.isNullOrEmpty(userAnswer)) {
            String[] answer = userAnswer.split(",");
            for (String string : answer) {
                if (string.split("_").length == 2) {
                    questionList.add(string.split("_")[0]);
                    answerList.add(string.split("_")[1]);
                }
            }
        }
        AnswerRequest answerRequest = new AnswerRequest();
        answerRequest.setResultList(answerList);
        Evalation evalation = getEvalationByCountScore(countScore.shortValue());
        userEvalationResult.setUserId(userId);
        userEvalationResult.setScoreCount(countScore);
        userEvalationResult.setEvalType(evalation.getEvalType());
        userEvalationResult.setSummary(evalation.getSummary());
        userEvalationResult.setCreateTime(new Date());
        if(userEvalationResult!=null){
            userEvalationResult.setLasttime(userEvalationResult.getCreateTime());
        }
        int i=userEvalationResultMapper.insertSelective(userEvalationResult);
        if(i>0){
            // 更新用户信息
            User user = userMapper.selectByPrimaryKey(userId);
            if (user != null){
                // 已测评
                user.setIsEvaluationFlag(1);
                user.setEvaluationExpiredTime(GetDate.countDate(GetDate.countDate(new Date(),1,1), 5,-1));
                // 更新用户是否测评标志位
                this.userMapper.updateByPrimaryKeySelective(user);
            }
            for (int j = 0; j < answerList.size(); j++) {
                UserEvalation userEvalation=new UserEvalation();
                userEvalation.setErId(userEvalationResult.getId());
                userEvalation.setQuestionId(new Integer(questionList.get(j)));
                userEvalation.setAnswerId(new Integer(answerList.get(j)));
                userEvalation.setSort(j);
                userEvalationMapper.insertSelective(userEvalation);
            }
        }
        //1_1,2_8
        if(behaviorId!=null&&behaviorId.length()!=0){
            UserEvalationBehavior userEvalationBehavior=new UserEvalationBehavior();
            userEvalationBehavior.setId(Integer.parseInt(behaviorId));
            userEvalationBehavior.setEndTime(new Date());
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
            userEvalationBehavior.setBehavior(sdf.format(new Date())+"用户完成答题");
            UserEvalationBehavior behavior=userEvalationBehaviorMapper.selectByPrimaryKey(userEvalationBehavior.getId());
            userEvalationBehavior.setBehavior(behavior.getBehavior()+","+userEvalationBehavior.getBehavior());
            userEvalationBehaviorMapper.updateByPrimaryKeySelective(userEvalationBehavior);
        }
        return userEvalationResult;
    }

    @Override
    public UserEvalationBehavior insertUserEvalationBehavior(Integer userId, String behavior) {
        UserEvalationBehavior userEvalationBehavior=new UserEvalationBehavior();
        userEvalationBehavior.setUserId(userId);
        userEvalationBehavior.setStatrTime(new Date());
        userEvalationBehavior.setBehavior(behavior);
        userEvalationBehaviorMapper.insertSelective(userEvalationBehavior);
        return userEvalationBehavior;
    }

    @Override
    public Integer updateUserEvalationBehavior(UserEvalationBehavior userEvalationBehavior) {
        UserEvalationBehavior behavior=userEvalationBehaviorMapper.selectByPrimaryKey(userEvalationBehavior.getId());
        userEvalationBehavior.setBehavior(behavior.getBehavior()+","+userEvalationBehavior.getBehavior());
        int cnt = userEvalationBehaviorMapper.updateByPrimaryKeySelective(userEvalationBehavior);
        return cnt;
    }


    /**
     * 获取评分标准列表
     * @return
     * @author Michael
     */
    @Override
    public List<Evalation> getEvalationRecord() {
        EvalationExample example = new EvalationExample();
        example.createCriteria().andStatusEqualTo(0);
        return evalationMapper.selectByExample(example);
    }

    /**
     * 根据evalationType获取评分标准列表
     * @return
     * @author Michael
     */
    @Override
    public Evalation getEvalationByEvalationType(String evalationType) {
        EvalationExample example = new EvalationExample();
        example.createCriteria().andEvalTypeEqualTo(evalationType).andStatusEqualTo(0);
        List<Evalation> list=evalationMapper.selectByExample(example);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }



    /**
     * 企业用户是否已开户
     *
     * @param userId
     * @return 0 未开户 1已开户
     * @author Michael
     */

    @Override
    public int isCompAccount(Integer userId) {
        CorpOpenAccountRecordExample example = new CorpOpenAccountRecordExample();
        CorpOpenAccountRecordExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        cra.andStatusEqualTo(6);// 已开户成功
        return corpOpenAccountRecordMapper.countByExample(example);
    }

    /**
     * 根据userId查询推广链接注册
     *
     * @param userId
     * @return
     */
    @Override
    public UtmReg findUtmRegByUserId(Integer userId) {
        UtmRegExample utmRegExample = new UtmRegExample();
        UtmRegExample.Criteria utmRegCra = utmRegExample.createCriteria();
        utmRegCra.andUserIdEqualTo(userId);
        List<UtmReg> utmRegList = this.utmRegMapper.selectByExample(utmRegExample);
        if (utmRegList != null && utmRegList.size() > 0) {
            return utmRegList.get(0);
        }
        return null;
    }

    /**
     * 更新渠道用户首次出借信息
     *
     * @param bean
     * @return
     */
    @Override
    public void updateFirstUtmReg(Map<String, Object> bean) {
        utmRegCustomizeMapper.updateFirstUtmReg(bean);
    }

    /**
     *  插入vip user
     * @param para
     */
    @Override
    public boolean insertVipUserTender(JSONObject para) {
        UserInfoVO userInfo= (UserInfoVO) para.get("userInfo");
        int nowTime= (int) para.get("nowTime");
        Integer userId = (Integer) para.get("userId");
        String orderId = (String) para.get("orderId");

        boolean result = false;
        if (Validator.isNotNull(userInfo) && Validator.isNotNull(userInfo.getVipId())){
            VipUserTender vt = new VipUserTender();
            // 出借用户编号
            vt.setUserId(userId);
            // 出借用户vip编号
            vt.setVipId(userInfo.getVipId());
            // 出借编号
            vt.setTenderNid(orderId);
            vt.setSumVipValue(userInfo.getVipValue());
            vt.setCreateTime(GetDate.getDate(nowTime));
            vt.setCreateUserId(userId);
            vt.setUpdateTime(GetDate.getDate(nowTime));
            vt.setUpdateUserId(userId);
            vt.setDelFlag(0);
            int ret=this.vipUserTenderMapper.insertSelective(vt);
            if (ret>0){
                result = true;
            }else{
                result = false;
            }
        }

        return result;
    }

    /**
     * 查询用户出借次数
     *
     * @param userId
     * @return
     */
    @Override
    public Integer selectTenderCount(Integer userId) {
        return userManagerCustomizeMapper.selectTenderCount(userId);
    }





    /**
     *
     * @param request
     * @return
     */
    @Override
    public List<CertificateAuthority> getCertificateAuthorityList(CertificateAuthorityRequest request) {
        CertificateAuthorityExample example = new CertificateAuthorityExample();
        CertificateAuthorityExample.Criteria cra = example.createCriteria();
        cra.andTrueNameEqualTo(request.getTrueName());
        cra.andIdNoEqualTo(request.getIdNo());
        cra.andIdTypeEqualTo(request.getIdType());
        return this.certificateAuthorityMapper.selectByExample(example);
    }

    /**
     * 借款主体CA认证记录表
     * @param request
     * @return
     */
    @Override
    public List<LoanSubjectCertificateAuthority> getLoanSubjectCertificateAuthorityList(LoanSubjectCertificateAuthorityRequest request) {
        LoanSubjectCertificateAuthorityExample loanSubjectCertificateAuthorityExample = new LoanSubjectCertificateAuthorityExample();
        LoanSubjectCertificateAuthorityExample.Criteria  loanSubjectCra = loanSubjectCertificateAuthorityExample.createCriteria();
        loanSubjectCra.andNameEqualTo(request.getName());
        loanSubjectCra.andIdTypeEqualTo(request.getIdType());
        loanSubjectCra.andIdNoEqualTo(request.getIdNo());
        return this.loanSubjectCertificateAuthorityMapper.selectByExample(loanSubjectCertificateAuthorityExample);

    }

    /**
     * 通过userID获得CA认证的客户ID
     * @param userId
     * @param code
     * @return
     */
    @Override
    public String getCustomerIDByUserID(Integer userId, String code) {
        if(userId == null){
            return null;
        }
        CertificateAuthorityExample cerExample = new CertificateAuthorityExample();
        cerExample.createCriteria().andUserIdEqualTo(userId).andCodeEqualTo(code);
        String customerID = null;
        List<CertificateAuthority> authorities = this.certificateAuthorityMapper.selectByExample(cerExample);
        if (authorities != null && authorities.size() > 0) {
            CertificateAuthority certificateAuthority = authorities.get(0);
            customerID = certificateAuthority.getCustomerId();
        }
        return customerID;
    }

    /**
     * 根据userId查询渠道
     *
     * @param userId
     * @return
     */
    @Override
    public UtmPlat selectUtmPlatByUserId(Integer userId) {
        return utmPlatCustomizeMapper.selectUtmPlatByUserId(userId);
    }

    @Override
    public int saveUserEvaluation(UserEvalationResult userEvalationResult) {
        deleteUserEvalationResultByUserId(userEvalationResult.getUserId());
        int insertCount = userEvalationResultMapper.insertSelective(userEvalationResult);
        if (insertCount > 0) {
            User user = userMapper.selectByPrimaryKey(userEvalationResult.getUserId());
            if (user != null) {
                user.setIsEvaluationFlag(1);
                // 获取测评到期日期
                Date evaluationExpiredTime = selectEvaluationExpiredTime(new Date());
                user.setEvaluationExpiredTime(evaluationExpiredTime);
                this.userMapper.updateByPrimaryKey(user);
            }
        }
        return insertCount;
    }

    /**
     * redis获取测评有效时间计算测评到期时间
     * redis获取到有效日用redis的、redis获取不到默认有效180天
     *
     * @param beginTime
     * @return
     */
    private Date selectEvaluationExpiredTime(Date beginTime) {
        // 测评过期时间key
        boolean isExist = RedisUtils.exists(RedisConstants.REVALUATION_EXPIRED_DAY);
        if (!isExist) {
            logger.error("redis未设定测评有效日！key：" + RedisConstants.REVALUATION_EXPIRED_DAY);
            return GetDate.countDate(beginTime, 5, 180);
        }

        // 从redis获取测评有效日
        String evaluationExpiredDayStr = RedisUtils.get(RedisConstants.REVALUATION_EXPIRED_DAY);
        if (org.apache.commons.lang3.StringUtils.isBlank(evaluationExpiredDayStr)) {
            logger.error("redis测评有效日设置为空！key：" + RedisConstants.REVALUATION_EXPIRED_DAY);
            return GetDate.countDate(beginTime, 5, 180);
        }

        // redis设定为非数字报错
        if (!NumberUtils.isNumber(evaluationExpiredDayStr)) {
            logger.error("redis测评有效日含非数字！key：" + RedisConstants.REVALUATION_EXPIRED_DAY + "========value:" + evaluationExpiredDayStr);
            return GetDate.countDate(beginTime, 5, 180);
        }

        // redis测评到期日计算
        try {
            Integer evaluationExpiredDay = Integer.parseInt(evaluationExpiredDayStr);
            return GetDate.countDate(beginTime, 5, evaluationExpiredDay);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("redis测评有效日格式化失败！key：" + RedisConstants.REVALUATION_EXPIRED_DAY + "========value:" + evaluationExpiredDayStr);
            return GetDate.countDate(beginTime, 5, 180);
        }
    }

    @Override
    public UserVO getUser(String utmReferrer, String userId) {
        UserVO userVO = new UserVO();
        UserExample example = new UserExample();
        UserExample.Criteria cra = example.createCriteria();
        if (StringUtils.isNotEmpty(utmReferrer)) {
            cra.andUsernameEqualTo(utmReferrer);
        }
        if (StringUtils.isNotEmpty(userId)) {
            cra.andUserIdEqualTo(Integer.valueOf(userId));
        }
        List<User> userList = this.userMapper.selectByExample(example);
        if (userList != null && userList.size() > 0) {
            userVO.setUsername(userList.get(0).getUsername());
        }
        return userVO;
    }

    @Override
    public User surongRegister(RegisterUserRequest userRequest){

        String mobile=userRequest.getMobile();
        String password=userRequest.getPassword();
        String loginIp=userRequest.getLoginIp();
        String platform=userRequest.getPlatform();
        Integer userType = userRequest.getUserType();

        // 1. 写入用户信息表
        User user = this.insertUser(mobile, password, loginIp, platform, userRequest.getInstCode(), userType);
        logger.info("写入用户...user is :{}", JSONObject.toJSONString(user));
        int userId = user.getUserId();

        // 2. 写入用户详情表
        this.insertUserInfo(userId, loginIp, 2,1,userRequest.getProvince(),userRequest.getCity());

        // 4. 保存用户注册日志
        this.insertRegLog(userId, loginIp);
        return user;
    }

    @Override
    public List<SpreadsUserVO> selectByUserId(String userId) {
        if(StringUtils.isNotEmpty(userId)){
            SpreadsUserExample spreadsUsersExample = new SpreadsUserExample();
            SpreadsUserExample.Criteria spreadsUsersExampleCriteria = spreadsUsersExample.createCriteria();
            spreadsUsersExampleCriteria.andUserIdEqualTo(Integer.parseInt(userId));
            List<SpreadsUser> sList = spreadsUserMapper.selectByExample(spreadsUsersExample);
            return CommonUtils.convertBeanList(sList,SpreadsUserVO.class);
        }
        return null;
    }

    /**
     * 获取用户表总记录数
     * @return
     */
    @Override
    public Integer countAll(){
        return userMapper.countByExample(new UserExample());
    }

    @Override
    public UserEvalationResult skipEvaluate(Integer userId, Integer countScore) {
        UserEvalationResult userEvalationResult = selectUserEvalationResultByUserId(userId);
        if(null==userEvalationResult){
            userEvalationResult = new UserEvalationResult();
        }
        deleteUserEvalationResultByUserId(userId);
        Evalation evalation = getEvalationByCountScore(countScore.shortValue());
        userEvalationResult.setUserId(userId);
        userEvalationResult.setScoreCount(countScore);
        userEvalationResult.setEvalType(evalation.getEvalType());
        userEvalationResult.setSummary(evalation.getSummary());
        userEvalationResult.setCreateTime(new Date());
        if(userEvalationResult!=null){
            userEvalationResult.setLasttime(userEvalationResult.getCreateTime());
        }
        int i=userEvalationResultMapper.insertSelective(userEvalationResult);
        if(i>0){
            return userEvalationResult;
        }else {
            return null;
        }
    }

    /**
     * 通过用户id获得借款人的开户电子账号
     *
     * @param userId
     * @return
     */
    @Override
    public BankOpenAccount selectBankAccountById(Integer userId) {
        BankOpenAccountExample example = new BankOpenAccountExample();
        BankOpenAccountExample.Criteria crt = example.createCriteria();
        crt.andUserIdEqualTo(userId);
        List<BankOpenAccount> list = this.bankOpenAccountMapper.selectByExample(example);
        if(list.size() > 0){
            return list.get(0);
        }else{return null;}
    }


    /**
     * 查看用户对应的企业编号
     *
     * @param userName
     * @return
     */
    @Override
    public CorpOpenAccountRecord selectUserBusiNameByUsername(String userName) {
        CorpOpenAccountRecordExample example = new CorpOpenAccountRecordExample();
        CorpOpenAccountRecordExample.Criteria crt = example.createCriteria();
        crt.andUsernameEqualTo(userName);
        List<CorpOpenAccountRecord> list = corpOpenAccountRecordMapper.selectByExample(example);
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     * 修改短信与邮件是否开启状态
     * @param userId
     * @param smsOpenStatus
     * @param emailOpenStatus
     * @return
     */
    @Override
    public Integer updateStatusByUserId(Integer userId, String smsOpenStatus, String emailOpenStatus) {
        User user = usersMapper.selectByPrimaryKey(userId);
        if (user != null) {
            if("0".equals(emailOpenStatus)){
                user.setIsSmtp(1);
            }else{
                user.setIsSmtp(0);
            }
            if("0".equals(smsOpenStatus)){
                user.setWithdrawSms(1);
                user.setInvestSms(1);
                user.setRechargeSms(1);
                user.setRecieveSms(1);
            }else{
                user.setWithdrawSms(0);
                user.setInvestSms(0);
                user.setRechargeSms(0);
                user.setRecieveSms(0);
            }
        }
        return usersMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 查询千乐渠道的用户
     * @return
     */
    @Override
    public List<Integer> getQianleUser(String sourceId) {
        return qianleUserCustomizeMapper.queryQianleUser(sourceId);
    }

    /**
     * 更新ht_hjh_user_auth_log
     * @param hjhUserAuthLog
     * @return
     */
    @Override
    public int updateHjhUserAuthLog(HjhUserAuthLog hjhUserAuthLog){
        return hjhUserAuthLogMapper.updateByPrimaryKeySelective(hjhUserAuthLog);
    }


    @Override
    public int isExistsUser(String condition) {
        if (StringUtils.isNotEmpty(condition)) {

            UserExample example=new UserExample();
            example.or().andUsernameEqualTo(condition);
            List<User> user = userMapper.selectByExample(example);

            if (user == null ) {
                // 借款人用户名不存在。
                return 1;
            }
            if (user.isEmpty() ) {
                // 借款人用户名不存在。
                return 1;
            }
            if (Validator.isNull(user.get(0).getBankOpenAccount()==0)) {
                // 借款人用户名必须已在银行开户
                return 2;
            }
            UserInfo info = userInfoMapper.selectByPrimaryKey(user.get(0).getUserId());
            if(info.getRoleId()==1) {
                return 4;
            }

            return 0;
        }
        return 1;
    }

    /**
	 * 通过用户名获得用户的详细信息
	 *
	 * @param userName
	 * @return
	 */
	@Override
	public User selectUserInfoByUsername(String userName) {
		UserExample example = new UserExample();
		UserExample.Criteria crt = example.createCriteria();
		crt.andUsernameEqualTo(userName);
		List<User> list = this.usersMapper.selectByExample(example);
		if(list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
    /**
     * 插入密码错误超限用户信息
     * @param lockedUserInfoVO
     * @return
     */
    public int inserLockedUser(LockedUserInfoVO lockedUserInfoVO){
        LockedUserInfo lockedUserInfo=new LockedUserInfo();
        BeanUtils.copyProperties(lockedUserInfoVO,lockedUserInfo);
        int insertLockedUser=lockedUserInfoMapper.insert(lockedUserInfo);
        return insertLockedUser;
    }

	/**
	 * 根据用户ID查询用户部门信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public UserDepartmentInfoCustomizeVO queryUserDepartmentInfoByUserId(Integer userId) {
		UserDepartmentInfoCustomizeVO userDepartmentInfoCustomizeVO = new UserDepartmentInfoCustomizeVO();
		UserDepartmentInfoCustomize userDepartmentInfoCustomize = this.userDepartmentInfoCustomizeMapper.selectUserDepartmentInfo(userId);
		if (userDepartmentInfoCustomize != null) {
			BeanUtils.copyProperties(userDepartmentInfoCustomize, userDepartmentInfoCustomizeVO);
			return userDepartmentInfoCustomizeVO;
		}
		return null;
	}

    @Override
    public User updateUserByUserId(Integer userId) {
        UserExample usersExample = new UserExample();
        usersExample.createCriteria().andUserIdEqualTo(userId);
        List<User> usersList = userMapper.selectByExample(usersExample);
        if (!CollectionUtils.isEmpty(usersList)) {
            return usersList.get(0);
        }
        return null;
    }

    /**
     * 获取最近七天开户的用户
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<BifaIndexUserInfoBean> getBankOpenedAccountUsers(Integer startDate, Integer endDate) {
        return bifaUserCustomizeMapper.getBankOpenedAccountUsers(startDate, endDate);
    }

    /**
     *  获取借款人信息
     * @param userId
     * @return
     */
    @Override
    public BifaIndexUserInfoBean getBifaIndexUserInfo(Integer userId) {
        return bifaUserCustomizeMapper.selectUserCorpInfoByUserId(userId);
    }

    @Override
    public WebViewUserVO getWebViewUserByUserId(Integer userId) {
        //主从延迟查询主库
        User user = this.findUserByUserId(userId);
        WebViewUserVO result = new WebViewUserVO();
        result.setUserId(user.getUserId());
        result.setUsername(user.getUsername());
        if (StringUtils.isNotBlank(user.getMobile())) {
            result.setMobile(user.getMobile());
        }
        if (StringUtils.isNotBlank(user.getIconUrl())) {
            String imghost = UploadFileUtils.getDoPath(systemConfig.getFileDomainUrl());
            imghost = imghost.substring(0, imghost.length() - 1);
            String fileUploadTempPath = UploadFileUtils.getDoPath(systemConfig.getFileUpload());
            if(StringUtils.isNotEmpty(user.getIconUrl())){
                result.setIconUrl(imghost + fileUploadTempPath + user.getIconUrl());
            }
        }
        if (StringUtils.isNotBlank(user.getEmail())) {
            result.setEmail(user.getEmail());
        }
        if (user.getOpenAccount() != null) {
            if (user.getOpenAccount().intValue() == 1) {
                result.setOpenAccount(true);
            } else {
                result.setOpenAccount(false);
            }
        }
        if (user.getBankOpenAccount() != null) {
            if (user.getBankOpenAccount() == 1) {
                result.setBankOpenAccount(true);
            } else {
                result.setBankOpenAccount(false);
            }
        }
        result.setRechargeSms(user.getRechargeSms());
        result.setWithdrawSms(user.getWithdrawSms());
        result.setInvestSms(user.getInvestSms());
        result.setRecieveSms(user.getRecieveSms());
        result.setIsSetPassword(user.getIsSetPassword());
        try {
            if(user.getIsEvaluationFlag()==1 && null != user.getEvaluationExpiredTime()){
                //测评到期日
                Long lCreate = user.getEvaluationExpiredTime().getTime();
                //当前日期
                Long lNow = System.currentTimeMillis();
                if (lCreate <= lNow) {
                    //已过期需要重新评测
                    result.setIsEvaluationFlag(2);
                    result.setEvaluationExpiredTime(user.getEvaluationExpiredTime());
                } else {
                    //未到一年有效期
                    result.setIsEvaluationFlag(1);
                    result.setEvaluationExpiredTime(user.getEvaluationExpiredTime());
                }
            }else{
                result.setIsEvaluationFlag(0);
            }
            // 用户是否完成风险测评标识 end
        } catch (Exception e) {
            result.setIsEvaluationFlag(0);
        }
        result.setIsSmtp(user.getIsSmtp());
        result.setUserType(user.getUserType());
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if (userInfo != null && userInfo.getSex() != null) {
            result.setSex(userInfo.getSex());
            if (StringUtils.isNotBlank(userInfo.getTruename())) {
                result.setTruename(userInfo.getTruename());
            }
            if (StringUtils.isNotBlank(userInfo.getIdcard())) {
                result.setIdcard(userInfo.getIdcard());
            }
            result.setBorrowerType(userInfo.getBorrowerType());
            result.setRoleId(userInfo.getRoleId() + "");
        }
        BankOpenAccount bankOpenAccount = selectBankAccountById(userId);
        if (bankOpenAccount != null && StringUtils.isNotEmpty(bankOpenAccount.getAccount())) {
            if (result.isBankOpenAccount()) {
                result.setBankAccount(bankOpenAccount.getAccount());
            }
        }
        // 用户紧急联系人
        UserContact usersContact = selectUserContact(userId);
        if(null!=usersContact){
            UsersContactVO userContactVO = new UsersContactVO();
            BeanUtils.copyProperties(usersContact, userContactVO);
            if (usersContact != null) {
                result.setUsersContact(userContactVO);
            }
        }

        return result;
    }
}
