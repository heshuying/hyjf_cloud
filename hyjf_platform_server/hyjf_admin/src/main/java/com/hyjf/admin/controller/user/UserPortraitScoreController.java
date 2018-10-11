/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.UserPortraitScoreSerivce;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.UserPortraitScoreResponse;
import com.hyjf.am.resquest.admin.UserPortraitScoreRequest;
import com.hyjf.am.vo.admin.UserPortraitScoreCustomizeVO;
import com.hyjf.am.vo.config.ParamNameVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yaoyong
 * @version UserPortraitScoreController, v0.1 2018/8/9 11:06
 */
@Api(tags = "会员中心-用户画像评分")
@RestController
@RequestMapping("/hyjf-admin/userPortrait")
public class UserPortraitScoreController extends BaseController {

    private static final String PERMISSIONS = "userPortrait";

    @Autowired
    private UserPortraitScoreSerivce userPortraitScoreService;

    @ApiOperation(value = "用户画像评分列表", notes = "用户画像评分列表")
    @PostMapping(value = "/seletPortraitScoreList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<UserPortraitScoreCustomizeVO>> seletPortraitScoreList(@RequestBody UserPortraitScoreRequest request) {

        String other1 = "1";
        List<ParamNameVO> paramNameVOList = userPortraitScoreService.getParamNameList(other1);
        request.setParamNameVOList(paramNameVOList);

        UserPortraitScoreResponse response = userPortraitScoreService.selectRecordList(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        List<UserPortraitScoreCustomizeVO> list = response.getResultList();

        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<ListResult<UserPortraitScoreCustomizeVO>>(ListResult.build(list, response.getCount()));
    }
}
