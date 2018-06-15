/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.trans;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUser;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.cs.user.result.ApiResult;
import com.hyjf.cs.user.result.MobileModifyResultBean;
import com.hyjf.cs.user.service.trans.MobileModifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version MobileModifyController, v0.1 2018/6/14 16:46
 */
@Api(value = "web修改手机号")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/web/user")
public class MobileModifyController {
    private static final Logger logger = LoggerFactory.getLogger(MobileModifyController.class);
    @Autowired
    MobileModifyService mobileModifyService;
    /**
     * 用户手机号码修改
     */
    @ApiOperation(value = "手机号码修改", notes = "手机号码修改")
    @ApiImplicitParam(name = "param",value = "{newMobile: string,smsCode: string}", dataType = "Map")
    @PostMapping(value = "/mobileModify", produces = "application/json; charset=utf-8")
    public ApiResult<UserVO> mobileModify(@RequestHeader(value = "token", required = true) String token, @RequestBody Map<String, String> paraMap) {
        logger.info("用户手机号码修改, paraMap :{}",paraMap);
        ApiResult<UserVO> result = new ApiResult<UserVO>();

        WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
        boolean checkRet = mobileModifyService.checkForMobileModify(paraMap.get("newMobile"), paraMap.get("smsCode"));
        if(checkRet) {
            UserVO userVO = new UserVO();
            userVO.setUserId(user.getUserId());
            userVO.setMobile(paraMap.get("newMobile"));
            mobileModifyService.updateUserByUserId(userVO);
        }

        return result;
    }

    /**
     * 用户手机号修改基础信息获取
     * @param token
     * @param request
     * @return
     */
    @PostMapping("/mobileModifyInit")
    public ApiResult<MobileModifyResultBean> mobileModifyInit(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
        ApiResult<MobileModifyResultBean> result = new ApiResult<MobileModifyResultBean>();

        WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
        MobileModifyResultBean resultBean = mobileModifyService.queryForMobileModify(user.getUserId());
        result.setResult(resultBean);

        return result;
    }

    /**
     * @author: zhangqingqing
     * @desc :判断是否开户
     * @param: * @param token
     * @date: 9:36 2018/6/15
     */
    @ApiOperation(value = "判断是否开户", notes = "判断是否开户")
    @PostMapping(value = "/checkOpenAccount", produces = "application/json; charset=utf-8")
    public ApiResult<Map<String,Object>> initMobile(@RequestHeader(value = "token") String token) {
        logger.info("web端获取开户未开户接口");
        ApiResult<Map<String,Object>> result = new ApiResult<Map<String,Object>>();
        Map<String,Object> resultMap = new HashMap<>();
        UserVO user = mobileModifyService.getUsers(token);
        Integer accountFlag = user.getBankOpenAccount();
        resultMap.put("bankOpenAccount", accountFlag);
        String mobile = user.getMobile();
        String hideMobile = "";
        if (StringUtils.isNotBlank(mobile)) {
            hideMobile = mobile.substring(0,mobile.length()-(mobile.substring(3)).length())+"****"+mobile.substring(7);
        }
        resultMap.put("mobile", mobile);
        resultMap.put("hideMobile", hideMobile);
        result.setResult(resultMap);
        return result;
    }
}
