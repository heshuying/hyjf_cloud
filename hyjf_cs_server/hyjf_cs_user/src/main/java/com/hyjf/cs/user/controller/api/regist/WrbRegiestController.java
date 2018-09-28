package com.hyjf.cs.user.controller.api.regist;

import com.hyjf.am.response.WrbResponse;
import com.hyjf.am.resquest.api.WrbRegisterRequest;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.util.WrbCommonDateUtil;
import com.hyjf.common.util.WrbParseParamUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.WrbRegisterRequestBean;
import com.hyjf.cs.user.bean.WrbRegisterResultBean;
import com.hyjf.cs.user.service.wrb.UserRegisterService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lisheng
 * @version WrbRegiestController, v0.1 2018/9/19 11:32
 */
@RestController
@Api(tags = "风车理财用户注册")
@RequestMapping(value = "/server/wrb/register")
public class WrbRegiestController {

    Logger log = LoggerFactory.getLogger(WrbRegiestController.class);
    public static final String WRB = "wrb";
    public static final String RETCODE = "3";
    public static final String RETCODE1 = "99";
    public static final String RETCODE2 = "0";
    public static final String RETMSG = "注册平台异常，用户注册失败！";
    @Autowired
    UserRegisterService userRegisterService;

    @PostMapping("/register")
    public WrbRegisterResultBean userRegister(@RequestParam String param,
                                              @RequestParam(value = "sign", required = false) String sign, HttpServletRequest request) {
        log.info("风车理财注册, param is :{}, sign is :{}", param, sign);
        Map<String, String> paramMap = WrbParseParamUtil.parseParam(param);
        WrbRegisterResultBean resultBean = new WrbRegisterResultBean();
        resultBean.setRetcode(RETCODE1);
        resultBean.setRetmsg(RETMSG);
        if (CollectionUtils.isEmpty(paramMap)) {
            resultBean.setRetcode(WrbResponse.FAIL_RETCODE);
            resultBean.setRetmsg(WrbResponse.FAIL_RETMESSAGE);
            return resultBean;
        }
        WrbRegisterRequestBean wrbRegisterRequestBean = null;
        try {
            wrbRegisterRequestBean = WrbParseParamUtil.mapToBean(paramMap, WrbRegisterRequestBean.class);
            if (wrbRegisterRequestBean == null) {
                return resultBean;
            }
        } catch (Exception e) {
            log.error("参数解析失败, paramMap is :" + paramMap, e);
            return resultBean;
        }
        // 手机号
        String mobile = wrbRegisterRequestBean.getMobile();
        // 注册平台
        String from = wrbRegisterRequestBean.getFrom();
        if (!(StringUtils.isNotBlank(from) && WRB.equals(from))) {
            return resultBean;
        }
        // 机构编号
        String instCode = WrbCommonDateUtil.FCLC_INSTCODE + "";
        // 注册平台
        String platform = WrbCommonDateUtil.FCLC_PLATFORM;
        // 注册渠道
        String utmId = WrbCommonDateUtil.FCLC_UTMID;
        try {
            // 验证请求参数
            // 手机号
            if (Validator.isNull(mobile)) {
                resultBean.setRetcode(RETCODE);
                resultBean.setRetmsg("手机号不能为空");
                return resultBean;
            }
            // 手机号合法性校验
            if (!Validator.isMobile(mobile)) {
                resultBean.setRetcode(RETCODE);
                resultBean.setRetmsg("请输入您的真实手机号码");
                return resultBean;
            }
            // 根据机构编号检索机构信息
            HjhInstConfigVO instConfig = this.userRegisterService.selectHjhInstConfig(instCode);
            // 机构编号
            if (instConfig == null) {
                log.info("获取机构信息为空,机构编号:{}", instCode);
                resultBean.setRetcode(RETCODE);
                resultBean.setRetmsg("机构编号错误");
                return resultBean;
            }
            // 根据渠道号检索推广渠道是否存在
            UtmPlatVO utmPlat = this.userRegisterService.selectUtmPlatByUtmId(utmId);
            if (utmPlat == null) {
                log.info("根据渠道号获取推广渠道为空,渠道号:{}", utmId);
                resultBean.setRetcode(RETCODE);
                resultBean.setRetmsg("推广渠道号非法");
                return resultBean;
            }
            // 根据手机号检索用户是否存在
            UserVO user = this.userRegisterService.findUserByMobile(mobile);
            String user_id = String.valueOf(user.getUserId());
            // 如果用户已经存在,表示该手机号已经注册
            if (user != null) {
                Integer bindUsers = userRegisterService.selectByUserId(user.getUserId(), instCode);
                if (bindUsers == null) {
                    log.info("用户手机号已在平台注册:用户名:{},用户手机号:{}", user.getUsername(), mobile);
                    // 合作平台的老用户
                    resultBean.setRetcode("1");
                    resultBean.setRetmsg("手机号已在平台注册");
                    resultBean.setPf_user_id(user_id);
                    return resultBean;
                } else {
                    if (user_id.equals(wrbRegisterRequestBean.getWrb_user_id())) {
                        resultBean.setRetcode(RETCODE2);
                        resultBean.setRetmsg("注册成功");
                        // 用户Id
                        resultBean.setPf_user_id(user_id);
                        resultBean.setPf_user_name(user.getUsername());
                        return resultBean;
                    } else {
                        resultBean.setRetcode(RETCODE);
                        resultBean.setRetmsg("注册失败, 风车理财id已被绑定");
                        // 用户Id
                        resultBean.setPf_user_id(user_id);
                        resultBean.setPf_user_name(user.getUsername());
                        return resultBean;
                    }
                }
            } else {
                // 手机号未注册
                log.info("手机号在平台未注册,手机号:{}", mobile);
                String ipAddr = GetCilentIP.getIpAddr(request);
                WrbRegisterRequest wrbRegisterRequest = new WrbRegisterRequest(mobile, instCode, ipAddr, instConfig.getInstType(), utmPlat, platform);

                Integer userId = this.userRegisterService.insertUserAction(wrbRegisterRequest);
                if (utmPlat.getSourceType() == 1) {
                    wrbRegisterRequest.setUserId(userId);
                    userRegisterService.insertAppChannelStatisticsDetail(wrbRegisterRequest);
                }
                if (userId == null || userId == 0) {
                    log.info("用户注册失败,手机号:{}", mobile);
                    resultBean.setRetcode(RETCODE);
                    resultBean.setRetmsg("注册失败");
                    return resultBean;
                } else {
                    UserVO users = this.userRegisterService.checkUserByUserId(userId);
                    if (users == null) {
                        log.info("根据用户ID获取用户信息表失败,用户ID:{}", userId);
                        resultBean.setRetcode(RETCODE);
                        resultBean.setRetmsg("注册失败");
                        return resultBean;
                    }

                    //处理用户基本信息
                    UserInfoVO userInfo = userRegisterService.getUserInfoByUserId(users.getUserId());
                    userInfo.setIdcard(wrbRegisterRequestBean.getId_no());
                    userInfo.setTruename(wrbRegisterRequestBean.getTrue_name());
                    userRegisterService.updateUserInfoByUserInfo(userInfo);

                    //插入用户绑定表
                    userRegisterService.bindThirdUser(userId, Integer.valueOf(wrbRegisterRequestBean.getWrb_user_id()), Integer.valueOf(instCode));
                    log.info("汇盈金福用户：{} 跟风车理财用户：{}已经绑定！", userId, userId);

                    String userName = users.getUsername();
                    // 用户注册成功
                    log.info("用户注册成功,手机号:{},用户ID:{},用户名:{}", mobile, userId, userName);
                    resultBean.setRetcode(RETCODE2);
                    resultBean.setRetmsg("注册成功");
                    // 用户Id
                    resultBean.setPf_user_id(user_id);
                    resultBean.setPf_user_name(userName);
                    return resultBean;
                }
            }
        } catch (Exception e) {
            log.info("用户注册失败~,手机号:{},失败原因:{}", mobile, e.getMessage());
            resultBean.setRetcode(RETCODE);
            resultBean.setRetmsg("注册失败");
            return resultBean;
        }

    }
}
