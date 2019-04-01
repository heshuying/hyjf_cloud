package com.hyjf.am.user.controller.admin.vip.content;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.vip.content.CustomerTaskConfigVOResponse;
import com.hyjf.am.response.admin.vip.content.ScreenConfigVOResponse;
import com.hyjf.am.resquest.admin.CustomerTaskConfigRequest;
import com.hyjf.am.resquest.admin.ScreenConfigRequest;
import com.hyjf.am.user.dao.model.auto.CustomerTaskConfig;
import com.hyjf.am.user.dao.model.auto.ScreenConfig;
import com.hyjf.am.user.service.admin.vip.content.OperService;
import com.hyjf.am.vo.user.CustomerTaskConfigVO;
import com.hyjf.am.vo.user.ScreenConfigVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/am-user/vip/content")
@RestController
public class OperController {

    @Autowired
    private OperService operService;

    // 大屏运营部数据配置

    /**
     * 大屏运营部数据配置列表查询
     * @param request
     * @return
     */
    @PostMapping("/oper/list")
    @ResponseBody
    private ScreenConfigVOResponse operList(@RequestBody ScreenConfigRequest request){
        ScreenConfigVOResponse response = new ScreenConfigVOResponse();
        // 大屏运营部数据配置列表数据总条数查询
        int totalCount = operService.countOperList(request);
        // 大屏运营部数据配置列表查询
        List<ScreenConfigVO> list = operService.operList(request);
        if (!CollectionUtils.isEmpty(list)){
            response.setTotalCount(totalCount);
            response.setResultList(list);
        }
        return response;
    }

    /**
     * 大屏运营部数据配置数据新增
     * @param screenConfigVO
     * @return
     */
    @PostMapping("/oper/add")
    @ResponseBody
    private IntegerResponse operAdd(@RequestBody ScreenConfigVO screenConfigVO){
        IntegerResponse response = new IntegerResponse();

        int insertFlag = operService.operAdd(screenConfigVO);
        if (insertFlag != 1){
            response.setRtn(Response.FAIL);
            response.setMessage(Response.FAIL_MSG);
            return response;
        }
        response.setResultInt(insertFlag);
        return response;
    }

    /**
     * 大屏运营部数据配置数据详情
     * @param screenConfigVO
     * @return
     */
    @PostMapping("/oper/info")
    @ResponseBody
    private ScreenConfigVOResponse operInfo(@RequestBody ScreenConfigVO screenConfigVO){
        ScreenConfigVOResponse response = new ScreenConfigVOResponse();

        ScreenConfig screenConfig = operService.operInfo(screenConfigVO.getId());
        if (null != screenConfig){
            ScreenConfigVO poundageCustomizeVO = CommonUtils.convertBean(screenConfig, ScreenConfigVO.class);
            response.setResult(poundageCustomizeVO);
        }
        return response;
    }

    /**
     * 大屏运营部数据配置数据编辑/启用/禁用
     * @param screenConfigVO
     * @return
     */
    @PostMapping("/oper/update")
    @ResponseBody
    private IntegerResponse operUpdate(@RequestBody ScreenConfigVO screenConfigVO){
        IntegerResponse response = new IntegerResponse();

        int updatetFlag = operService.operUpdate(screenConfigVO);
        if (updatetFlag != 1){
            response.setRtn(Response.FAIL);
            response.setMessage(Response.FAIL_MSG);
            return response;
        }
        response.setResultInt(updatetFlag);
        return response;
    }

    // 坐席月任务配置

    /**
     * 坐席月任务配置列表查询
     * @param request
     * @return
     */
    @PostMapping("/task/list")
    @ResponseBody
    private CustomerTaskConfigVOResponse taskList(@RequestBody CustomerTaskConfigRequest request){
        CustomerTaskConfigVOResponse response = new CustomerTaskConfigVOResponse();
        // 坐席月任务配置列表数据总条数查询
        int totalCount = operService.countTaskList(request);
        // 坐席月任务配置列表查询
        List<CustomerTaskConfigVO> list = operService.taskList(request);
        if (!CollectionUtils.isEmpty(list)){
            response.setTotalCount(totalCount);
            response.setResultList(list);
        }
        return response;
    }

    /**
     * 坐席月任务配置数据新增
     * @param customerTaskConfigVO
     * @return
     */
    @PostMapping("/task/add")
    @ResponseBody
    private IntegerResponse taskAdd(@RequestBody CustomerTaskConfigVO customerTaskConfigVO){
        IntegerResponse response = new IntegerResponse();

        int insertFlag = operService.taskAdd(customerTaskConfigVO);
        if (insertFlag != 1){
            response.setRtn(Response.FAIL);
            response.setMessage(Response.FAIL_MSG);
            return response;
        }
        response.setResultInt(insertFlag);
        return response;
    }

    /**
     * 坐席月任务配置数据详情
     * @param customerTaskConfigVO
     * @return
     */
    @PostMapping("/task/info")
    @ResponseBody
    private Response<CustomerTaskConfigVO> taskInfo(@RequestBody CustomerTaskConfigVO customerTaskConfigVO){
        Response<CustomerTaskConfigVO> response = new Response<>();

        CustomerTaskConfig customerTaskConfig = operService.taskInfo(customerTaskConfigVO.getId());
        if (null != customerTaskConfig){
            CustomerTaskConfigVO poundageCustomizeVO = CommonUtils.convertBean(customerTaskConfig, CustomerTaskConfigVO.class);
            response.setResult(poundageCustomizeVO);
        }
        return response;
    }

    /**
     * 坐席月任务配置数据编辑/启用/禁用
     * @param customerTaskConfigVO
     * @return
     */
    @PostMapping("/task/update")
    @ResponseBody
    private IntegerResponse taskUpdate(@RequestBody CustomerTaskConfigVO customerTaskConfigVO){
        IntegerResponse response = new IntegerResponse();

        int updatetFlag = operService.taskUpdate(customerTaskConfigVO);
        if (updatetFlag != 1){
            response.setRtn(Response.FAIL);
            response.setMessage(Response.FAIL_MSG);
            return response;
        }
        response.setResultInt(updatetFlag);
        return response;
    }
}
