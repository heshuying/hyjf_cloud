/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.datacenter;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.CaiJingLogService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CaiJingLogResponse;
import com.hyjf.am.resquest.admin.CaiJingLogRequest;
import com.hyjf.am.vo.admin.CaiJingPresentationLogVO;
import com.hyjf.common.util.GetDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yaoyong
 * @version CaiJingController, v0.1 2019/6/10 9:11
 */
@Api(tags = "数据中心-零一财经数据报送日志")
@RestController
@RequestMapping("/hyjf-admin/datacenter/caijinglog")
public class CaiJingController extends BaseController {

    @Autowired
    private CaiJingLogService caiJingLogService;

    /**
     * 查询日志列表
     * @param request
     * @return
     */
    @ApiOperation(value = "查询日志列表", notes = "查询日志列表")
    @RequestMapping("/queryLogList")
    public AdminResult queryCaiJingLog(@RequestBody CaiJingLogRequest request) {
        CaiJingLogResponse response = caiJingLogService.queryCaiJingLog(request);
        if (!CollectionUtils.isEmpty(response.getResultList())) {
            for (CaiJingPresentationLogVO logVO : response.getResultList()) {
                logVO.setPushTime(GetDate.getDateTimeMyTime(logVO.getPresentationTime()));
            }
        }
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult(ListResult.build(response.getResultList(),response.getLogCount()));
    }

    /**
     * 重新报送接口
     * @param logType
     * @return
     */
    @ApiOperation(value = "重新报送接口", notes = "重新报送接口")
    @RequestMapping("/reQueryLogList")
    public AdminResult reQueryLogList(@PathVariable String logType) {
        BooleanResponse response = caiJingLogService.reQueryCaiJingLog(logType);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult();
    }
}
