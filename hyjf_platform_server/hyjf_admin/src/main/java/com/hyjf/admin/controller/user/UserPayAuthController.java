/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import com.hyjf.admin.beans.request.UserPayAuthRequestBean;
import com.hyjf.admin.beans.vo.UserManagerCustomizeVO;
import com.hyjf.admin.beans.vo.UserPayAuthCustomizeVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.UserPayAuthService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.UserPayAuthResponse;
import com.hyjf.am.resquest.user.UserPayAuthRequest;
import com.hyjf.am.vo.user.UserPayListAuthCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nxl
 * @version UserPayAuthController, v0.1 2018/10/15 9:23
 */
@Api(value = "会员中心-缴费授权接口", tags = "会员中心-缴费授权接口")
@RestController
@RequestMapping("/hyjf-admin/userPayAuth")
public class UserPayAuthController extends BaseController {
    @Autowired
    private UserPayAuthService userPayAuthService;

    /**
     * 缴费授权列表查询
     * @param userPayAuthRequestBean
     * @return
     */
    @ApiOperation(value = "缴费授权列表查询", notes = "缴费授权列表查询")
    @PostMapping(value = "/userPayAuthList")
    @ResponseBody
    public AdminResult<ListResult<UserPayAuthCustomizeVO>> getUserslist(@RequestBody UserPayAuthRequestBean userPayAuthRequestBean) {
        UserPayAuthRequest userPayAuthRequest = new UserPayAuthRequest();
        BeanUtils.copyProperties(userPayAuthRequestBean, userPayAuthRequest);
        UserPayAuthResponse userManagerResponse = userPayAuthService.selectUserMemberList(userPayAuthRequest);
        if (userManagerResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        List<UserPayListAuthCustomizeVO> userPayAuthCustomizeVOList = userManagerResponse.getResultList();
        List<UserPayAuthCustomizeVO> userManagerCustomizeList = new ArrayList<UserPayAuthCustomizeVO>();
        if (null != userPayAuthCustomizeVOList && userPayAuthCustomizeVOList.size() > 0) {
            userManagerCustomizeList = CommonUtils.convertBeanList(userPayAuthCustomizeVOList, UserPayAuthCustomizeVO.class);
        }
        if (!Response.isSuccess(userManagerResponse)) {
            return new AdminResult<>(FAIL, userManagerResponse.getMessage());
        }
        return new AdminResult<ListResult<UserPayAuthCustomizeVO>>(ListResult.build(userManagerCustomizeList, userManagerResponse.getCount()));
    }
    @ApiOperation(value = "缴费授权查询", notes = "缴费授权查询")
    @PostMapping(value = "/userPayAuthQuery")
    @ResponseBody
    public AdminResult userPayAuthQuery(@RequestParam Integer userId) {
        logger.info("查询开始，查询用户：{}", userId);
        return null;
    }
}
