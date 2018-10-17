/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.membercentre;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.service.BankConfigService;
import com.hyjf.am.config.service.JxBankConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.customize.*;
import com.hyjf.am.user.service.admin.membercentre.UserPayAuthService;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * @author nxl
 * @version UserMemberController, v0.1 2018/6/20 9:13
 *          后台会员中心-缴费授权
 */

@RestController
@RequestMapping("/am-user/userPayAuth")
public class UserPayAuthController extends BaseController {
    @Autowired
    private UserPayAuthService userPayAuthService;
    @Autowired
    private JxBankConfigService jxBankConfigService;
    @Autowired
    private BankConfigService bankConfigService;

    /**
     * 根据用户的查询条件查询用户授权列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/userPayAuthList")
    public UserPayAuthResponse selectUserPayAuthList(@RequestBody @Valid UserPayAuthRequest request) {
        logger.info("---selectUserPayAuthList by param---  " + JSONObject.toJSON(request));
        UserPayAuthResponse response = new UserPayAuthResponse();
        Map<String, Object> mapParam = paramSet(request);
        int usesrCount = userPayAuthService.countRecordTotalPay(mapParam);
        Paginator paginator = new Paginator(request.getCurrPage(), usesrCount, request.getPageSize());
        if (request.getPageSize() == 0) {
            paginator = new Paginator(request.getCurrPage(), usesrCount);
        }
        int limitStart = paginator.getOffset();
        int limitEnd = paginator.getLimit();
        if (request.isLimitFlg()) {
            limitEnd = 0;
            limitStart = 0;
        }
        response.setCount(usesrCount);
        if (usesrCount > 0) {
            List<AdminUserPayAuthCustomize> adminUserPayAuthCustomizeList = userPayAuthService.selectUserPayAuthList(mapParam, limitStart, limitEnd);
            if (!CollectionUtils.isEmpty(adminUserPayAuthCustomizeList)) {
                List<UserPayListAuthCustomizeVO> userVoList = CommonUtils.convertBeanList(adminUserPayAuthCustomizeList, UserPayListAuthCustomizeVO.class);
                response.setResultList(userVoList);
                response.setRtn(Response.SUCCESS);
                response.setMessage(Response.SUCCESS_MSG);
            }
        }
        return response;
    }

    /**
     * 查询条件设置
     *
     * @param userRequest
     * @return
     */
    private Map<String, Object> paramSet(UserPayAuthRequest userRequest) {
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("authTimeStart", userRequest.getAuthTimeStart());
        mapParam.put("authTimeEnd", userRequest.getAuthTimeEnd());
        mapParam.put("userName", userRequest.getUserName());
        mapParam.put("authType", userRequest.getAuthType());
        mapParam.put("bankid", userRequest.getBankCode());
        mapParam.put("signTimeStart", userRequest.getSignTimeStart());
        mapParam.put("signTimeEnd", userRequest.getSignTimeEnd());
        /*mapParam.put("limitStart", userRequest.getSignTimeEnd());
        mapParam.put("limitEnd", userRequest.getSignTimeEnd());*/
        return mapParam;
    }

}
