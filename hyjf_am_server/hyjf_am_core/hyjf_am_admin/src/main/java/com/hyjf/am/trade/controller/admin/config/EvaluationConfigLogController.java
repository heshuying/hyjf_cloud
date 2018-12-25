/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.EvaluationCheckLogResponse;
import com.hyjf.am.response.trade.EvaluationMoneyLogResponse;
import com.hyjf.am.resquest.admin.EvaluationCheckLogRequest;
import com.hyjf.am.resquest.admin.EvaluationMoneyLogRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.EvaluationConfigLog;
import com.hyjf.am.trade.service.admin.config.EvaluationConfigService;
import com.hyjf.am.vo.trade.EvaluationCheckLogConfigVO;
import com.hyjf.am.vo.trade.EvaluationMoneyLogConfigVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 风险测评配置
 * @author Zha Daojian
 * @date 2018/12/24 9:48
 * @param
 * @return
 **/
@Api(value = "风险测评配置")
@RestController
@RequestMapping("/am-trade/evaluation")
public class EvaluationConfigLogController extends BaseController {

    @Autowired
    EvaluationConfigService configService;

    /**
     * 风险测评开关配置查询列表(操作日志)
     * @author Zha Daojian
     * @date 2018/12/24 10:21
     * @param evaluationCheckRequest
     * @return EvaluationCheckResponse
     **/
    @ApiOperation(value = "风险测评开关配置查询列表，操作日志")
    @PostMapping("/getEvaluationCheckLogList")
    public EvaluationCheckLogResponse getEvaluationCheckLogList(@RequestBody EvaluationCheckLogRequest evaluationCheckRequest) {
        EvaluationCheckLogResponse response = new EvaluationCheckLogResponse();
        Integer recordTotal = configService.selectEvaluationCheckLogCount(evaluationCheckRequest);
        Paginator paginator = new Paginator(evaluationCheckRequest.getCurrPage(), recordTotal, evaluationCheckRequest.getPageSize());
        evaluationCheckRequest.setLimitStart(paginator.getOffset());
        evaluationCheckRequest.setLimitEnd(paginator.getLimit());

        List<EvaluationConfigLog> evaluationCheckConfigVOList = configService.selectEvaluationCheckLogList(evaluationCheckRequest);
        if (null != evaluationCheckConfigVOList && evaluationCheckConfigVOList.size() > 0) {
            List<EvaluationCheckLogConfigVO> evaluationCheckConfigVOs = CommonUtils.convertBeanList(evaluationCheckConfigVOList, EvaluationCheckLogConfigVO.class);
            response.setResultList(evaluationCheckConfigVOs);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    @ApiOperation(value = "风险测评限额配置查询列表，操作日志")
    @PostMapping("/getEvaluationMoneyLogList")
    public EvaluationMoneyLogResponse getEvaluationMoneyLogList(@RequestBody EvaluationMoneyLogRequest request) {
        EvaluationMoneyLogResponse response = new EvaluationMoneyLogResponse();
        Integer recordTotal = configService.selectEvaluationMoneyLogCount(request);
        Paginator paginator = new Paginator(request.getCurrPage(), recordTotal, request.getPageSize());
        request.setLimitStart(paginator.getOffset());
        request.setLimitEnd(paginator.getLimit());

        List<EvaluationConfigLog> evaluationConfigList = configService.selectEvaluationMoneyLogList(request);
        if (null != evaluationConfigList && evaluationConfigList.size() > 0) {
            List<EvaluationMoneyLogConfigVO> evaluationCheckConfigVOs = CommonUtils.convertBeanList(evaluationConfigList, EvaluationMoneyLogConfigVO.class);
            response.setResultList(evaluationCheckConfigVOs);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
}
