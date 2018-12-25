/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.EvaluationCheckResponse;
import com.hyjf.am.response.trade.EvaluationMoneyResponse;
import com.hyjf.am.resquest.admin.EvaluationCheckRequest;
import com.hyjf.am.resquest.admin.EvaluationMoneyRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.EvaluationConfig;
import com.hyjf.am.trade.service.admin.config.EvaluationConfigService;
import com.hyjf.am.vo.admin.EvaluationCheckConfigVO;
import com.hyjf.am.vo.admin.EvaluationMoneyConfigVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class EvaluationConfigController extends BaseController {

    @Autowired
    EvaluationConfigService configService;

    /**
     * 风险测评开关配置查询列表
     * @author Zha Daojian
     * @date 2018/12/24 10:21
     * @param evaluationCheckRequest
     * @return EvaluationCheckResponse
     **/
    @ApiOperation(value = "风险测评开关配置查询列表")
    @PostMapping("/getEvaluationCheckList")
    public EvaluationCheckResponse getEvaluationCheckList(@RequestBody EvaluationCheckRequest evaluationCheckRequest) {
        EvaluationCheckResponse response = new EvaluationCheckResponse();
        Integer recordTotal = configService.selectEvaluationCheckCount(evaluationCheckRequest);
        Paginator paginator = new Paginator(evaluationCheckRequest.getCurrPage(), recordTotal, evaluationCheckRequest.getPageSize());
        evaluationCheckRequest.setLimitStart(paginator.getOffset());
        evaluationCheckRequest.setLimitEnd(paginator.getLimit());

        List<EvaluationConfig> evaluationCheckConfigVOList = configService.selectEvaluationCheckList(evaluationCheckRequest);
        if (null != evaluationCheckConfigVOList && evaluationCheckConfigVOList.size() > 0) {
            List<EvaluationCheckConfigVO> evaluationCheckConfigVOs = CommonUtils.convertBeanList(evaluationCheckConfigVOList, EvaluationCheckConfigVO.class);
            response.setResultList(evaluationCheckConfigVOs);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    @ApiOperation(value = "风险测评限额配置查询列表")
    @PostMapping("/getEvaluationMoneyList")
    public EvaluationMoneyResponse getEvaluationMoneyList(@RequestBody EvaluationMoneyRequest request) {
        EvaluationMoneyResponse response = new EvaluationMoneyResponse();
        Integer recordTotal = configService.selectEvaluationMoneyCount(request);
        Paginator paginator = new Paginator(request.getCurrPage(), recordTotal, request.getPageSize());
        request.setLimitStart(paginator.getOffset());
        request.setLimitEnd(paginator.getLimit());

        List<EvaluationConfig> evaluationConfigList = configService.selectEvaluationMoneyList(request);
        if (null != evaluationConfigList && evaluationConfigList.size() > 0) {
            List<EvaluationMoneyConfigVO> evaluationCheckConfigVOs = CommonUtils.convertBeanList(evaluationConfigList, EvaluationMoneyConfigVO.class);
            response.setResultList(evaluationCheckConfigVOs);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 根据id查询风险测评开关配置
     * @author Zha Daojian
     * @date 2018/12/24 11:37
     * @param id
     **/
    @ApiOperation(value = "根据id查询风险测评开关配置")
    @GetMapping("/getEvaluationCheckById/{id}")
    public EvaluationCheckResponse getEvaluationCheckById(@PathVariable Integer id) {
        EvaluationCheckResponse response = new EvaluationCheckResponse();
        EvaluationConfig evaluationConfig = configService.selectEvaluationCheckById(id);
        if (null != evaluationConfig) {
            EvaluationCheckConfigVO evaluationCheckConfigVO = CommonUtils.convertBean(evaluationConfig, EvaluationCheckConfigVO.class);
            response.setResult(evaluationCheckConfigVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 根据id查询风险测评限额配置
     * @author Zha Daojian
     * @date 2018/12/24 11:37
     * @param id
     **/
    @ApiOperation(value = "根据id查询风险测评限额配置")
    @GetMapping("/getEvaluationMoneyById/{id}")
    public EvaluationMoneyResponse getEvaluationMoneyById(@PathVariable Integer id) {
        EvaluationMoneyResponse response = new EvaluationMoneyResponse();
        EvaluationConfig evaluationConfig = configService.selectEvaluationMoneyById(id);
        if (null != evaluationConfig) {
            EvaluationMoneyConfigVO evaluationMoneyConfigVO = CommonUtils.convertBean(evaluationConfig, EvaluationMoneyConfigVO.class);
            response.setResult(evaluationMoneyConfigVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 修改风险测评限额配置
     * @author Zha Daojian
     * @date 2018/12/24 13:41
     * @param request
     * @return com.hyjf.am.response.trade.EvaluationMoneyResponse
     **/
    @ApiOperation(value = "根据该机构可用还款方式更新可用授信方式")
    @PostMapping("/updateEvaluationMoney")
    public EvaluationMoneyResponse updateEvaluationMoney(@RequestBody EvaluationMoneyRequest request) {
        EvaluationMoneyResponse response = new EvaluationMoneyResponse();
        response.setResultBoolean(configService.updateEvaluationMoney(request));
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 修改风险测评限额配置
     * @author Zha Daojian
     * @date 2018/12/24 13:41
     * @param request
     * @return com.hyjf.am.response.trade.EvaluationMoneyResponse
     **/
    @ApiOperation(value = "根据该机构可用还款方式更新可用授信方式")
    @PostMapping("/updateEvaluationCheck")
    public EvaluationCheckResponse updateEvaluationCheck(@RequestBody EvaluationCheckRequest request) {
        EvaluationCheckResponse response = new EvaluationCheckResponse();
        response.setResultBoolean(configService.updateEvaluationCheck(request));
        response.setRtn(Response.SUCCESS);
        return response;
    }
}
