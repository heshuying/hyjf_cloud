package com.hyjf.am.config.controller.admin.workflow;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.WorkFlow;
import com.hyjf.am.config.service.WorkFlowConfigService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.WorkFlowConfigResponse;
import com.hyjf.am.resquest.admin.WorkFlowConfigRequest;
import com.hyjf.am.vo.admin.WorkFlowVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xiehuili on 2019/4/12.
 */
@RestController
@RequestMapping("/am-admin/workflow/bussinessflow")
public class WorkFlowConfigController extends BaseConfigController {

    @Autowired
    private WorkFlowConfigService workFlowConfigService;

    /**
     * 工作流查询查询业务流程配置
     * @param adminRequest
     * @return
     */
    @PostMapping("/init")
    public WorkFlowConfigResponse selectWorkFlowConfigList(@RequestBody WorkFlowConfigRequest adminRequest) {
        logger.info("工作流查询查询业务流程配置..." + JSONObject.toJSON(adminRequest));
        WorkFlowConfigResponse response = new WorkFlowConfigResponse();
        int count = workFlowConfigService.countWorkFlowConfigList(adminRequest);
        if(count>0){
            Paginator paginator = new Paginator(adminRequest.getCurrPage(), count, adminRequest.getPageSize());
            List<WorkFlowVO> workFlowVOS =workFlowConfigService.selectWorkFlowConfigList(adminRequest, paginator.getOffset(), paginator.getLimit());
            if(!CollectionUtils.isEmpty(workFlowVOS)){
                response.setResultList(workFlowVOS);
            }
            response.setCount(count);
        }
        logger.debug("工作流查询查询业务流程配置..." + JSONObject.toJSON(response));
        return response;
    }

    /**
     * 添加业务流程
     * @param workFlowVO
     * @return
     */
    @PostMapping("/insert")
    public BooleanResponse insertWorkFlowConfig( @RequestBody WorkFlowVO workFlowVO) {
        logger.info("添加业务流程..." + JSONObject.toJSON(workFlowVO));
        BooleanResponse response = new BooleanResponse();

//        //校验业务id对应的业务流程是否存在
//        WorkFlowConfigRequest adminRequest = new WorkFlowConfigRequest();
//        adminRequest.setBusinessId(workFlowVO.getBusinessId());
//        int count = workFlowConfigService.countWorkFlowConfigList(adminRequest);
//        if(count > 0){
//            response.setRtn(Response.FAIL);
//            response.setMessage("该业务id对应的业务流程已经存在，请修改");
//            logger.debug("该业务id对应的业务流程已经存在，请修改" );
//            return response;
//        }

        //添加业务流程
        int flag = workFlowConfigService.insertWorkFlowConfig(workFlowVO);
        if(flag ==0){
            response.setRtn(Response.FAIL);
            response.setMessage("添加业务流程失败");
            logger.debug("添加业务流程失败" );
            return response;
        }
        response.setRtn(Response.SUCCESS);
        response.setMessage(Response.SUCCESS_MSG);
        return response;
    }

    /**
     * 查询业务流程详情页面
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    public WorkFlowConfigResponse selectWorkFlowConfigInfo( @PathVariable Integer id) {
        logger.info("查询业务流程详情页面,id:" + id);
        WorkFlowConfigResponse response = new WorkFlowConfigResponse();
        WorkFlowVO workFlowVOS =workFlowConfigService.selectWorkFlowConfigInfo(id);
        if(null != workFlowVOS){
            response.setResult(workFlowVOS);
            logger.debug("查询业务流程详情页面,数据为："+JSONObject.toJSONString(workFlowVOS));
            return response;
        }
        return response;
    }

    /**
     * 修改业务流程
     * @param workFlowVO
     * @return
     */
    @PostMapping("/update")
    public BooleanResponse updateWorkFlowConfig( @RequestBody WorkFlowVO workFlowVO) {
        logger.info("修改业务流程,请求参数workFlowVO：" + JSONObject.toJSON(workFlowVO));
        BooleanResponse response = new BooleanResponse();

        //修改业务流程
        int flag = workFlowConfigService.updateWorkFlowConfig(workFlowVO);
        if(flag ==0){
            response.setRtn(Response.FAIL);
            response.setMessage("修改业务流程失败");
            logger.debug("修改业务流程失败" );
            return response;
        }
        response.setRtn(Response.SUCCESS);
        response.setMessage(Response.SUCCESS_MSG);
        return response;
    }

    /**
     * 删除工作流配置业务流程
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public BooleanResponse deleteWorkFlowConfigById(@PathVariable int id){
        logger.info("删除业务流程,请求参数id：" + id);
        BooleanResponse response = new BooleanResponse();

        //删除业务流程
        Integer flag = workFlowConfigService.deleteWorkFlowConfigById(id);
        if(null == flag){
            response.setRtn(Response.FAIL);
            response.setMessage("业务流程的id等于："+id+"的流程不存在");
            logger.debug("业务流程的id等于："+id+"的流程不存在");
            return response;
        }
        if(flag ==2){
            response.setRtn(Response.FAIL);
            response.setMessage("不需要审核的时候，才能删除业务流程");
            logger.debug("不需要审核的时候，才能删除业务流程" );
            return response;
        }
        if(flag ==0){
            response.setRtn(Response.FAIL);
            response.setMessage("修改业务流程失败");
            logger.debug("修改业务流程失败" );
            return response;
        }
        response.setRtn(Response.SUCCESS);
        response.setMessage(Response.SUCCESS_MSG);
        return response;
    }

    /**
     * 校验业务id是否存在
     * @param businessId
     * @return
     */
    @GetMapping("/exist/{businessId}")
    public WorkFlowConfigResponse selectWorkFlowConfigByBussinessId( @PathVariable Integer businessId) {
        logger.info("校验业务id是否存在,businessId:" + businessId);
        WorkFlowConfigRequest adminRequest = new WorkFlowConfigRequest();
        adminRequest.setBusinessId(businessId);
        WorkFlowConfigResponse response = new WorkFlowConfigResponse();
        List<WorkFlowVO> workFlowVOS =workFlowConfigService.selectWorkFlowConfigList(adminRequest, -1, -1);
        if(!CollectionUtils.isEmpty(workFlowVOS)){
            response.setResultList(workFlowVOS);
            logger.debug("校验业务id存在,数据为："+JSONObject.toJSONString(workFlowVOS));
            return response;
        }
        return response;
    }

    /**
     * 获取所有工作业务流
     * @return
     */
    @GetMapping("/findWorkFlowAll")
    public WorkFlowConfigResponse findWorkFlowAll() {
        WorkFlowConfigResponse response = new WorkFlowConfigResponse();
        List<WorkFlowVO> workFlow =workFlowConfigService.findWorkFlowAll();
        if(!CollectionUtils.isEmpty(workFlow)){
            response.setResultList(workFlow);
            return response;
        }
        return response;
    }

    /**
     * 修改工作流程表的flowStatus
     * @return
     */
    @GetMapping("/updateFlowStatus/{businessId}")
    public BooleanResponse updateFlowStatus(@PathVariable Integer businessId) {
        BooleanResponse response = new BooleanResponse();
        WorkFlow workFlow = new WorkFlow();
        workFlow.setId(businessId);
        workFlow.setFlowStatus(2);
        boolean flag = workFlowConfigService.updateFlowStatus(workFlow) > 0 ?true:false;
        response.setResultBoolean(flag);
        response.setRtn(Response.SUCCESS);
        response.setMessage(Response.SUCCESS_MSG);
        return response;
    }

}
