package com.hyjf.admin.controller.activity;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.NaMiMarketingService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.NaMiMarketingResponse;
import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.resquest.admin.NaMiMarketingRequest;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.admin.NaMiMarketingVO;
import com.hyjf.am.vo.market.ActivityListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lisheng
 * @version NaMiMarketingController, v0.1 2018/12/26 11:00
 */
@Api(tags = "活动中心-活动列表-详情")
@RestController
@RequestMapping("/hyjf-admin/manager/activity/namimarketing")
public class NaMiMarketingController  extends BaseController {
    /**
     * 权限关键字
     */
    public static final String PERMISSIONS = "activitylist";
    @Autowired
    NaMiMarketingService naMiMarketingService;

    @ApiOperation(value = "查询列表", notes = "查询列表")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<NaMiMarketingVO>> selectActivityList(@RequestBody NaMiMarketingRequest naMiMarketingRequest) {
        NaMiMarketingResponse response = naMiMarketingService.getRecordList(naMiMarketingRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<NaMiMarketingVO> resultList = response.getResultList();
        return new AdminResult<ListResult<NaMiMarketingVO>>(ListResult.build(resultList, response.getCount()));
    }
}
