/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.safe.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.BindEmailLogRequest;
import com.hyjf.am.resquest.user.UserNoticeSetRequest;
import com.hyjf.am.resquest.user.UsersContractRequest;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.constants.UserConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.result.ContractSetResultBean;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.safe.SafeService;
import com.hyjf.cs.user.vo.BindEmailVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;


/**
 * @author zhangqingqing
 * @version SafeServiceImpl, v0.1 2018/6/11 15:55
 */
@Service
public class SafeServiceImpl extends BaseUserServiceImpl implements SafeService {

    private static final Logger logger = LoggerFactory.getLogger(SafeServiceImpl.class);

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private CommonProducer commonProducer;

    /**
     * 修改登陆密码
     *
     * @param userId
     * @param oldPW
     * @param newPW
     * @return
     */
    @Override
    public JSONObject updatePassWd(Integer userId, String oldPW, String newPW) {
        logger.info("UserService.updatePassWd run...userId is :{}, oldPW is :{}, newPW is :{}", userId, oldPW, newPW);
        return amUserClient.updatePassWd(userId, oldPW, newPW);
    }


    /**
     * 获取用户对象
     *
     * @param userId
     * @return
     */
    @Override
    public UserVO queryUserByUserId(Integer userId) {
        UserVO user = amUserClient.findUserById(userId);
        return user;
    }

    /**
     * 账户设置信息查询
     *
     * @param
     * @return
     */
    @Override
    public Map<String, Object> safeInit(UserVO user) {
        Map<String, Object> resultMap = new HashMap<>();
        // 用户角色
        UserInfoVO userInfo = this.amUserClient.findUserInfoById(user.getUserId());

        resultMap.put("webViewUser", user);
        resultMap.put("truename", "");
        if(userInfo!=null){
            if (userInfo.getTruename() != null && userInfo.getTruename().length() >= 1) {
                resultMap.put("truename", userInfo.getTruename().substring(0, 1) + "**");
            }
            if (userInfo.getIdcard() != null && userInfo.getIdcard().length() >= 15) {
                resultMap.put("idcard", userInfo.getIdcard().substring(0, 3) + "***********" + userInfo.getIdcard().substring(userInfo.getIdcard().length() - 4));
            }
            resultMap.put("roleId", userInfo.getRoleId());
        }

        if (user.getMobile() != null && user.getMobile().length() == 11) {
            resultMap.put("mobile", user.getMobile().substring(0, 3) + "****" + user.getMobile().substring(user.getMobile().length() - 4));
        }else {
            resultMap.put("mobile","");
        }
        if (user.getEmail() != null && user.getEmail().length() >= 2) {
            String emails[] = user.getEmail().split("@");
            resultMap.put("email", AsteriskProcessUtil.getAsteriskedValue(emails[0], 2, emails[0].length() - 2) + "@" + emails[1]);
        }else {
            resultMap.put("email","");
        }

        UserLoginLogVO userLogin = amUserClient.getUserLoginById(user.getUserId());
        // 是否设置交易密码
        resultMap.put("isSetPassword", user.getIsSetPassword());
        resultMap.put("lastTime", userLogin.getLastTime());
        UsersContactVO usersContactVO = amUserClient.selectUserContact(user.getUserId());
        resultMap.put("usersContract", usersContactVO==null?"":usersContactVO);
        // 紧急联系人类型
        Map<String, String> result = CacheUtil.getParamNameMap("USER_RELATION");
        resultMap.put("userRelation", result);
        // 根据用户Id查询用户银行卡号 add by tyy 2018-6-27
        List<BankCardVO> bankCards = this.amUserClient.getTiedCardForBank(user.getUserId());
        BankCardVO bankCard = new BankCardVO();
        if (bankCards != null && !bankCards.isEmpty()) {
            bankCard = bankCards.get(0);
            bankCard.setCardNoNotEncrypt(bankCard.getCardNo());
            bankCard.setCardNo(BankCardUtil.getCardNo(bankCard.getCardNo()));
        }
        resultMap.put("bankCard", bankCard);
        BankOpenAccountVO bankOpenAccount = amUserClient.selectById(user.getUserId());
        AccountChinapnrVO chinapnr = amUserClient.getAccountChinapnr(user.getUserId());
        resultMap.put("bankOpenAccount", bankOpenAccount == null ? new BankOpenAccountVO() : bankOpenAccount);
        resultMap.put("chinapnr", chinapnr);

        UserEvalationResultVO userEvalationResult = amUserClient.selectUserEvalationResultByUserId(user.getUserId());
        if (userEvalationResult != null && userEvalationResult.getId() != null && userEvalationResult.getId() != 0) {
            // 获取评测时间加一年的毫秒数18.2.2评测 19.2.2
            Long lCreate = user.getEvaluationExpiredTime().getTime();
            //获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
            Long lNow = System.currentTimeMillis();
            if (lCreate <= lNow) {
                //已过期需要重新评测 2是已过期
                resultMap.put("ifEvaluation", 2);
            } else {
                // ifEvaluation是否已经调查表示 1是已调查，0是未调查
                resultMap.put("ifEvaluation", 1);
                // userEvalationResult 测评结果
                resultMap.put("userEvalationResult", userEvalationResult);
            }
        } else {
            resultMap.put("ifEvaluation", 0);
        }
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthByUserId(user.getUserId());
        resultMap.put("hjhUserAuth", getUserAuthState(hjhUserAuth));

        // 获得是否授权
        // 获取用户上传头像
        String imghost = UploadFileUtils.getDoPath(systemConfig.getFileDomainUrl());
        imghost = imghost.substring(0, imghost.length() - 1);
        // 实际物理路径前缀2
        String fileUploadTempPath = UploadFileUtils.getDoPath(systemConfig.getFileUpload());
        if (StringUtils.isNotEmpty(user.getIconUrl())) {
            resultMap.put("iconUrl", imghost + fileUploadTempPath + user.getIconUrl());
        }else {
            resultMap.put("iconUrl", "");
        }
        return resultMap;
    }

    /**
     * 获得用户授权状态信息
     * 自动投标状态          缴费授权状态      还款授权状态
     *
     * @param auth
     * @return
     */
    public HjhUserAuthVO getUserAuthState(HjhUserAuthVO auth) {
        if(auth==null){
            auth = new HjhUserAuthVO();
            auth.setAutoInvesStatus(0);
            auth.setAutoCreditStatus(0);
        }
        // 缴费授权
        int paymentAuth = valdateAuthState(auth.getAutoPaymentStatus(), auth.getAutoPaymentEndTime());
        auth.setAutoPaymentStatus(paymentAuth);
        // 还款授权
        int repayAuth = valdateAuthState(auth.getAutoRepayStatus(), auth.getAutoRepayEndTime());
        auth.setAutoRepayStatus(repayAuth);
        // 自动出借授权
        int invesAuth = valdateAuthState(auth.getAutoInvesStatus(), auth.getAutoBidEndTime());
        auth.setAutoInvesStatus(invesAuth);
        return auth;
    }

    /**
     * @param endTime
     * @Author: zhangqingqing
     * @Desc :检查是否授权  0未授权  1已授权
     * @Param: * @param status
     * @Date: 16:47 2018/5/30
     * @Return: int
     */
    private int valdateAuthState(Integer status, String endTime) {
        String nowTime = GetDate.date2Str(new Date(), GetDate.yyyyMMdd);
        if (endTime == null || status == null) {
            return 0;
        }
        if (status - 0 == 0 || Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
            return 0;
        }
        return 1;
    }

    /**
     * 绑定邮箱发送激活邮件
     *
     * @param userId
     * @param email
     * @return
     * @throws MQException
     */
    @Override
    public boolean sendEmailActive(Integer userId, String token, String email) throws MQException {
        UserVO user = amUserClient.findUserById(userId);
        UserInfoVO userInfoVO = amUserClient.findUserInfoById(userId);
        String activeCode = GetCode.getRandomCode(6);

        // 保存发送的激活邮件记录
        BindEmailLogRequest request = new BindEmailLogRequest();
        request.setCreatetime(new Date());
        request.setEmailActiveCode(activeCode);
        request.setEmailActiveUrlDeadtime(GetDate.getSomeDayBeforeOrAfter(new Date(), 1));
        request.setUserEmail(email);
        request.setUserEmailStatus(UserConstant.EMAIL_ACTIVE_STATUS_1);
        request.setUserId(userId);
        amUserClient.insertBindEmailLog(request);

        // 发送激活邮件
        activeCode = MD5Utils.MD5(MD5Utils.MD5(activeCode));
        String url = systemConfig.webUIBindEmail + "?key=" + user.getUserId() + "&value=" + activeCode + "&email=" + email;
        Map<String, String> replaceMap = new HashMap<String, String>();
        replaceMap.put("url_name", url);
        if (StringUtils.isNotBlank(userInfoVO.getNickname())) {
            replaceMap.put("username_name", userInfoVO.getNickname());
        } else {
            replaceMap.put("username_name", user.getUsername());
        }

        MailMessage mailMessage = new MailMessage(null, replaceMap, "绑定邮箱激活", null, null, new String[]{email},
                CustomConstants.EMAILPARAM_TPL_BINDEMAIL, MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS);
        // 发送邮件
        commonProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(), mailMessage));

        return true;
    }

    /**
     * 发送激活邮件条件校验
     */
    @Override
    public void checkForEmailSend(String email, Integer userId) {
        // 邮箱为空校验
        if (StringUtils.isBlank(email)) {
            throw new ReturnMessageException(MsgEnum.ERR_EMAIL_REQUIRED);
        }
        // 邮箱格式校验
        if (!Validator.isEmailAddress(email)) {
            throw new ReturnMessageException(MsgEnum.ERR_FMT_EMAIL);
        }
        // 邮箱地址使用校验
        boolean isExist = amUserClient.checkEmailUsed(email);
        if (isExist) {
            throw new ReturnMessageException(MsgEnum.ERR_EMAIL_USED);
        }

    }

    /**
     * 绑定邮箱激活条件校验
     *
     * @param
     * @param
     * @param
     */
    @Override
    public void checkForEmailBind(BindEmailVO bindEmailVO) {
        // 邮箱为空校验
        if (StringUtils.isBlank(bindEmailVO.getEmail()) || StringUtils.isBlank(bindEmailVO.getValue()) || StringUtils.isBlank(bindEmailVO.getKey())) {
            throw new ReturnMessageException(MsgEnum.ERR_PARAM);
        }

        // 激活邮件存在性校验
        BindEmailLogVO log = amUserClient.getBindEmailLog(Integer.parseInt(bindEmailVO.getKey()));
        if (log == null) {
            throw new ReturnMessageException(MsgEnum.ERR_EMAIL_ACTIVE_NOT_EXIST);
        }

        // 激活邮件过期校验
        if (new Date().after(log.getEmailActiveUrlDeadtime())) {
            throw new ReturnMessageException(MsgEnum.ERR_EMAIL_ACTIVE_OVERDUE);
        }
        String validValue = MD5Utils.MD5(MD5Utils.MD5(log.getEmailActiveCode()));
        // 激活校验
        if (!bindEmailVO.getKey().equals(String.valueOf(log.getUserId())) || !bindEmailVO.getEmail().equals(log.getUserEmail()) || !bindEmailVO.getValue().equals(validValue)) {
            throw new ReturnMessageException(MsgEnum.ERR_EMAIL_ACTIVE);
        }
    }

    /**
     * 绑定邮箱更新
     *
     * @param userId
     * @param email
     * @return
     * @throws MQException
     */
    @Override
    public boolean updateEmail(Integer userId, String email) throws MQException {
        BindEmailLogVO vo = amUserClient.getBindEmailLog(userId);
        BindEmailLogRequest requestBean = new BindEmailLogRequest();
        BeanUtils.copyProperties(vo, requestBean);
        amUserClient.updateBindEmail(requestBean);
        return true;
    }

    /**
     * 紧急联系人参数校验
     */
    @Override
    public void checkForContractSave(String relationId, String rlName, String rlPhone) {
        // 请求参数空值校验
        if (StringUtils.isBlank(relationId) || StringUtils.isBlank(rlName) || StringUtils.isBlank(rlPhone)) {
            throw new ReturnMessageException(MsgEnum.ERR_PARAM);
        }
        // 姓名格式校验
        if (rlName.length() < 2 || rlName.length() > 4) {
            throw new ReturnMessageException(MsgEnum.ERR_FMT_NAME);
        }
        // 手机号码格式校验
        if (rlPhone.length() != 11 || !Validator.isMobile(rlPhone)) {
            throw new ReturnMessageException(MsgEnum.ERR_FMT_PHONE);
        }
    }

    /**
     * 保存紧急联系人
     */
    @Override
    public boolean saveContract(String relationId, String rlName, String rlPhone, int userId) throws MQException {
        UsersContractRequest requestBean = new UsersContractRequest();
        requestBean.setRelation(Integer.parseInt(relationId));
        requestBean.setRlName(rlName);
        requestBean.setRlPhone(rlPhone);
        requestBean.setUserId(userId);
        int cnt = amUserClient.updateUserContract(requestBean);

        if (cnt <= 0) {
            throw new ReturnMessageException(MsgEnum.ERR_CONTACT_SAVE);
        }

        return true;
    }

    /**
     * 获取紧急联系人信息
     *
     * @author hesy
     */
    @Override
    public ContractSetResultBean queryContractInfo(Integer userId) {
        ContractSetResultBean resultBean = new ContractSetResultBean();

        // 获取紧急联系人关系信息
        Map<String, String> relationMap = CacheUtil.getParamNameMap("USER_RELATION");
        if (relationMap == null || relationMap.isEmpty()) {
            throw new ReturnMessageException(MsgEnum.ERR_CONTACT_RELATIONSHIP_NOT_EXIST);
        }

        resultBean.setRelationMap(relationMap);

        // 获取当前紧急联系人信息
        UsersContactVO usersContactVO = amUserClient.selectUserContact(userId);
        if (usersContactVO != null) {
            resultBean.setData(usersContactVO);

            for (Entry<String, String> entry : relationMap.entrySet()) {
                if (usersContactVO.getRelation() != null && entry.getKey().equals(String.valueOf(usersContactVO.getRelation()))) {
                    resultBean.setCheckRelationId(entry.getKey());
                    resultBean.setCheckRelationName(entry.getValue());
                    break;
                }
            }
        }

        return resultBean;

    }

    @Override
    public boolean updateSmsConfig(Integer userId, String smsKey, Integer smsValue, UserVO user) {
        if ("rechargeSms".equals(smsKey)) {
            //充值成功短信
            user.setRechargeSms(smsValue);
        }
        if ("withdrawSms".equals(smsKey)) {
            //提现成功短信
            user.setWithdrawSms(smsValue);
        }
        if ("investSms".equals(smsKey)) {
            //投标成功短信
            user.setInvestSms(smsValue);
        }
        if ("recieveSms".equals(smsKey)) {
            //回收成功短信
            user.setRecieveSms(smsValue);
        }
        amUserClient.updateUserById(user);
        return true;
    }

    @Override
    public String uploadAvatar(UserVO user, Integer userId, String image) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        // 将字符串格式的image转为二进制流（biye[])的decodedBytes
        byte[] decodedBytes = decoder.decodeBuffer(image);
        logger.info("头像转成二进制大小:[{}]",decodedBytes.length);
        String filePhysicalPath = UploadFileUtils.getDoPath(systemConfig.getPhysicalPath());
        // 实际物理路径前缀2
        String fileUploadTempPath = UploadFileUtils.getDoPath(systemConfig.getFileUpload());
        // 如果文件夹(前缀+后缀)不存在,则新建文件夹
        String logoRealPathDir = filePhysicalPath + fileUploadTempPath;
        File logoSaveFile = new File(logoRealPathDir);
        if (!logoSaveFile.exists()) {
            logoSaveFile.mkdirs();
        }
        // 生成图片文件名
        String fileRealName = String.valueOf(System.currentTimeMillis());
        fileRealName = "appIconImg_" + userId + fileRealName + ".png";
        // 指定图片要存放的位置
        String imgFilePath = logoSaveFile + File.separator + fileRealName;
        // 新建一个文件输出器，并为它指定输出位置imgFilePath
        try{
            logger.info("开始将头像写入硬盘 -> fileName:[{}]",fileRealName);
            FileOutputStream out = new FileOutputStream(imgFilePath);
            // 利用文件输出器将二进制格式decodedBytes输出
            out.write(decodedBytes);
            out.close(); // 关闭文件输出器
            // 保存到数据库的路径=上传文件的CDNURL+图片的文件名
            String iconUrl = fileRealName;
            user.setIconUrl(iconUrl);
            // 保存到数据库
            amUserClient.updateUserById(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        imgFilePath = systemConfig.getFileDomainUrl()+fileUploadTempPath+fileRealName;
        return imgFilePath;
    }

    @Override
    public MultipartFile checkParam(MultipartHttpServletRequest multipartRequest, String key, String token) {
        CheckUtil.check(token != null && null != key, MsgEnum.STATUS_CE000001);
        // 版本号
        String version = multipartRequest.getParameter("version");
        // 网络状态
        String netStatus = multipartRequest.getParameter("netStatus");
        // 平台
        String platform = multipartRequest.getParameter("platform");
        // 随机字符串
        String randomString = multipartRequest.getParameter("randomString");
        // Order
        String order = multipartRequest.getParameter("order");
        // 获得第1张图片（根据前台的name名称得到上传的文件）
        MultipartFile iconImg = multipartRequest.getFile("iconImg");

        CheckUtil.check(StringUtils.isNotBlank(version) && StringUtils.isNotBlank(netStatus) && StringUtils.isNotBlank(platform) && StringUtils.isNotBlank(randomString) && StringUtils.isNotBlank(order), MsgEnum.STATUS_CE000001);
        CheckUtil.check(SecretUtil.checkOrder(key, token, randomString, order), MsgEnum.STATUS_CE000001);
        // 业务逻辑
        CheckUtil.check(Validator.isNotNull(iconImg), MsgEnum.ERR_OBJECT_REQUIRED, "上传图片");
        return iconImg;
    }

    @Override
    public String updateAvatar(Integer userId, MultipartFile iconImg) throws Exception {
        // 单位字节
        Long allowFileLength = 5000000L;
        UserVO userVO = this.getUsersById(userId);
        // 取得用户ID
        CheckUtil.check(null != userId, MsgEnum.STATUS_CE000006);
        // 从配置文件获取上传文件的各个URL
        // 上传文件的CDNURL
        // String fileDomainUrl =
        // UploadFileUtils.getDoPath(PropUtils.getSystem("file.domain.url"));
        // 实际物理路径前缀1
        String filePhysicalPath = UploadFileUtils.getDoPath(systemConfig.getPhysicalPath());
        // 实际物理路径前缀2
        String fileUploadTempPath = UploadFileUtils.getDoPath(systemConfig.getFileUpload());
        // 如果文件夹(前缀+后缀)不存在,则新建文件夹
        String logoRealPathDir = filePhysicalPath + fileUploadTempPath;
        File logoSaveFile = new File(logoRealPathDir);
        if (!logoSaveFile.exists()) {
            logoSaveFile.mkdirs();
        }
        // 生成图片文件名
        String fileRealName = String.valueOf(System.currentTimeMillis());
        fileRealName = "appIconImg_" + userId + fileRealName + UploadFileUtils.getSuffix(iconImg.getOriginalFilename());
        // 上传至服务器
        String returnMessage = UploadFileUtils.upload4Stream(fileRealName, logoRealPathDir, iconImg.getInputStream(), allowFileLength);
        if (!"上传文件成功！".equals(returnMessage)) {
            throw new CheckException(returnMessage);
        }
        // 保存到数据库的路径=上传文件的CDNURL+图片的文件名
        String iconUrl = fileRealName;
        // 保存到数据库
        userVO.setIconUrl(iconUrl);
        // 保存到数据库
        amUserClient.updateUserById(userVO);
        // 测试环境要加 + request.getContextPath().replace("//", "");
        String imghost = UploadFileUtils.getDoPath(systemConfig.getFileDomainUrl());
        imghost = imghost.substring(0, imghost.length() - 1);
        iconUrl = imghost + fileUploadTempPath + fileRealName;
        return iconUrl;
    }

    /**
     * 更新用户信息
     *
     * @param requestBean
     * @return
     */
    @Override
    public int updateUserNoticeSet(UserNoticeSetRequest requestBean) {
        return amUserClient.updateUserNoticeSet(requestBean);
    }

}
