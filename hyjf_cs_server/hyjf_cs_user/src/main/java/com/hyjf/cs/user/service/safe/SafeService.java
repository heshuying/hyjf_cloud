/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.safe;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.UserNoticeSetRequest;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.user.result.ContractSetResultBean;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.cs.user.vo.BindEmailVO;

/**
 * @author zhangqingqing
 * @version SafeService, v0.1 2018/6/11 15:55
 */
public interface SafeService  extends BaseUserService {

    /**
     * 修登录密码
     * @param userId
     * @param oldPW
     * @param newPW
     * @return
     */
    JSONObject updatePassWd(Integer userId, String oldPW, String newPW);

    /**
     * 获取用戶通知配置信息
     * @param userId
     * @return
     */
    UserVO queryUserByUserId(Integer userId);

    /**
     * 账户设置信息查询
     * @param
     * @return
     */
    Map<String,Object> safeInit(UserVO user);

    /**
     * 绑定邮箱发送激活邮件
     * @param userId
     * @param token
     * @param email
     * @param wjtClient
     * @return
     * @throws MQException
     */
    boolean sendEmailActive(Integer userId, String token, String email, String wjtClient) throws MQException;

    /**
     * 发送激活邮件条件校验
     * @param email
     * @param userId
     */
    void checkForEmailSend(String email, Integer userId);

    /**
     * 校验绑卡参数
     * @param bindEmailVO
     * @param userId
     */
    void checkForEmailBind(BindEmailVO bindEmailVO);

    /**
     * 修改邮箱
     * @param userId
     * @param email
     * @return
     * @throws MQException
     */
    boolean updateEmail(Integer userId, String email) throws MQException;

    /**
     * 紧急联系人参数校验
     * @param relationId
     * @param rlName
     * @param rlPhone
     */
    void checkForContractSave(String relationId, String rlName, String rlPhone);

    /**
     * 紧急联系人保存
     * @param relationId
     * @param rlName
     * @param rlPhone
     * @param userId
     * @return
     * @throws MQException
     */
    boolean saveContract(String relationId, String rlName, String rlPhone, int userId) throws MQException;

    /**
     * 更新用户通知设置
     * @param requestBean
     * @return
     */
    int updateUserNoticeSet(UserNoticeSetRequest requestBean);

    /**
     * 获取紧急联系人信息
     * @param userId
     * @return
     */
	ContractSetResultBean queryContractInfo(Integer userId);

    /**
     * 更新sms信息
     * @param userId
     * @param smsKey
     * @param smsValue
     */
    boolean updateSmsConfig(Integer userId, String smsKey,Integer smsValue,UserVO user);

    /**
     * 用户上传头像
     * @param userId
     */
    String uploadAvatar( UserVO user,Integer userId,String image) throws IOException;

    /**
     * app上传头像接口检查参数
     * @param multipartRequest
     * @param key
     * @param token
     */
    MultipartFile checkParam(MultipartHttpServletRequest multipartRequest, String key, String token);

    /**
     * app端上传头像
     * @param userId
     * @param iconImg
     * @return
     * @throws Exception
     */
    String updateAvatar(Integer userId ,MultipartFile iconImg) throws Exception;
}
