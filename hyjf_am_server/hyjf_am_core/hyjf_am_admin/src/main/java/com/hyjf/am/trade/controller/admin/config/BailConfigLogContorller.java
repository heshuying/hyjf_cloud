/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.config;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BailConfigLogCustomizeResponse;
import com.hyjf.am.resquest.admin.BailConfigLogRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.config.BailConfigLogService;
import com.hyjf.am.vo.admin.BailConfigLogCustomizeVO;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigLogContorller, v0.1 2018/9/28 11:25
 */
@Api(value = "保证金配置日志")
@RestController
@RequestMapping("/am-admin/bail_config_log")
public class BailConfigLogContorller extends BaseController {

    @Autowired
    BailConfigLogService bailConfigLogService;

    /**
     * @Author: liushouyi
     * @Desc :查询总件数
     */
    @ApiOperation(value = "查询保证金日志总件数")
    @PostMapping("/select_bail_config_log_count")
    public IntegerResponse selectBailConfigLogCount(@RequestBody BailConfigLogRequest bailConfigLogRequest) {
        Integer re = bailConfigLogService.selectBailConfigLogCount(bailConfigLogRequest);
        return new IntegerResponse(re);
    }

    /**
     * @Author: liushouyi
     * @Desc :查询列表数据
     */
    @ApiOperation(value = "保证金日志查询列表")
    @PostMapping("/select_bail_config_log_list")
    public BailConfigLogCustomizeResponse selectBailConfigRecordList(@RequestBody BailConfigLogRequest bailConfigLogRequest) {
        BailConfigLogCustomizeResponse response = new BailConfigLogCustomizeResponse();
        Integer recordTotal = bailConfigLogService.selectBailConfigLogCount(bailConfigLogRequest);
        Paginator paginator = new Paginator(bailConfigLogRequest.getCurrPage(), recordTotal, bailConfigLogRequest.getPageSize());
        bailConfigLogRequest.setLimitStart(paginator.getOffset());
        bailConfigLogRequest.setLimitEnd(paginator.getLimit());

        List<BailConfigLogCustomizeVO> bailConfigLogList = bailConfigLogService.selectBailConfigLogList(bailConfigLogRequest);
        if (null != bailConfigLogList && bailConfigLogList.size() > 0) {
            response.setResultList(bailConfigLogList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

}
