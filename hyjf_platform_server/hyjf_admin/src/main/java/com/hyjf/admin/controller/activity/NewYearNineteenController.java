package com.hyjf.admin.controller.activity;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.NewYearNineteenService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.NewYearActivityResponse;
import com.hyjf.am.resquest.admin.NewYearNineteenRequestBean;
import com.hyjf.am.vo.admin.NewYearActivityVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiehuili on 2019/3/25.
 */
@Api(tags = "活动中心-春节活动")
@RestController
@RequestMapping("/hyjf-admin/manager/activity/newyearnineteen2019")
public class NewYearNineteenController extends BaseController {

    /**
     * 权限关键字
     */
    public static final String PERMISSIONS = "activitylist";
    @Autowired
    private NewYearNineteenService newYearNineteenService;
    /**
     * 累计年化出借金额列表 画面初始化
     * @param newYearNineteenRequestBean
     * @return
     */
    @ApiOperation(value = "累计年化出借金额列表", notes = "累计年化出借金额列表")
    @PostMapping("/investInit")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<NewYearActivityVO>> init(@RequestBody NewYearNineteenRequestBean newYearNineteenRequestBean) {
        NewYearActivityResponse response = null;
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<NewYearActivityVO> resultList = response.getResultList();
        return new AdminResult<ListResult<NewYearActivityVO>>(ListResult.build(resultList, response.getTotal()));
    }
}
