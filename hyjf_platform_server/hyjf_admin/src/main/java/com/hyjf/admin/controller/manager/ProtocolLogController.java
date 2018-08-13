package com.hyjf.admin.controller.manager;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ProtocolLogService;
import com.hyjf.am.response.admin.ProtocolLogResponse;
import com.hyjf.am.resquest.admin.ProtocolLogRequest;
import com.hyjf.am.vo.admin.ProtocolLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 协议模板管理日志
 *
 * @author：yinhui
 * @Date: 2018/8/10  11:42
 */
@Api(value = "配置中心-协议模板日志管理", tags = "配置中心-协议模板日志管理")
@RestController
@RequestMapping("/hyjf-admin/manager/protocolog")
public class ProtocolLogController extends BaseController {

    @Autowired
    private ProtocolLogService protocolLogService;

    /**
     * 分页显示
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "配置中心-协议模板日志管理", notes = "配置中心-协议模板日志管理 分页显示")
    @RequestMapping("/search")
    public AdminResult<ListResult<ProtocolLogVO>> search(ProtocolLogRequest request) {
        ProtocolLogResponse response = new ProtocolLogResponse();
        response = protocolLogService.searchPage(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        return new AdminResult<ListResult<ProtocolLogVO>>(ListResult.build(response.getResultList(), response.getCount()));
    }
}
