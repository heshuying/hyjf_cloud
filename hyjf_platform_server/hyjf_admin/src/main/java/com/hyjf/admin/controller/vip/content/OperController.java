package com.hyjf.admin.controller.vip.content;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.service.vip.content.OperService;
import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.resquest.admin.CustomerTaskConfigRequest;
import com.hyjf.am.resquest.admin.ScreenConfigRequest;
import com.hyjf.am.vo.user.CustomerTaskConfigVO;
import com.hyjf.am.vo.user.ScreenConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(value = "会员中心-内容中心", tags = "会员中心-内容中心")
@RequestMapping("/hyjf-admin/content")
@RestController
public class OperController {

    @Autowired
    private OperService operService;

    // 大屏运营部数据配置

    @ApiOperation(value = "大屏运营部数据配置-列表查询",notes = "大屏运营部数据配置-列表查询")
    @PostMapping("/oper/list")
    @ResponseBody
    private AdminResult operist(@RequestBody ScreenConfigRequest request){
        AdminResult result = new AdminResult();
        result.setTotalCount(0);
        List<ScreenConfigVO> list = operService.operList(request);
        if (list != null){
            result.setTotalCount(list.size());
        }
        result.setData(list);
        return result;
    }

    @ApiOperation(value = "大屏运营部数据配置-数据新增",notes = "大屏运营部数据配置-数据新增")
    @PostMapping("/oper/add")
    @ResponseBody
    private AdminResult operAdd(@RequestBody ScreenConfigVO screenConfigVO){
        if(blankCheck(1, screenConfigVO, null, null)){
            return new AdminResult("3", "必传字段未传");
        }
        screenConfigVO.setStatus(1);
        int insertFlag = operService.operAdd(screenConfigVO);
        if (insertFlag < 1){
            return new AdminResult(BaseResult.FAIL, BaseResult.FAIL_DESC);
        }
        return new AdminResult();
    }


    @ApiOperation(value = "大屏运营部数据配置-数据编辑",notes = "大屏运营部数据配置-数据编辑")
    @PostMapping("/oper/update")
    @ResponseBody
    private AdminResult operUpdate(@RequestBody ScreenConfigVO screenConfigVO){
        if(blankCheck(2, screenConfigVO, null, null)){
            return new AdminResult("3", "必传字段未传");
        }
        int updateFlag = operService.operUpdate(screenConfigVO);
        if (updateFlag < 1){
            return new AdminResult(BaseResult.FAIL, BaseResult.FAIL_DESC);
        }
        return new AdminResult();
    }

    @ApiOperation(value = "大屏运营部数据配置-数据启用/禁用",notes = "大屏运营部数据配置-数据启用/禁用")
    @PostMapping("/oper/able")
    @ResponseBody
    private AdminResult operAble(@RequestBody ScreenConfigVO screenConfigVO){
        if(blankCheck(3, screenConfigVO, null, null)){
            return new AdminResult("3", "必传字段未传");
        }
        int updateFlag = operService.operUpdate(screenConfigVO);
        if (updateFlag < 1){
            return new AdminResult(BaseResult.FAIL, BaseResult.FAIL_DESC);
        }
        return new AdminResult();
    }

    // 坐席月任务配置

    @ApiOperation(value = "坐席月任务配置-列表查询",notes = "坐席月任务配置-列表查询")
    @PostMapping("/task/list")
    @ResponseBody
    private AdminResult taskList(@RequestBody CustomerTaskConfigRequest request){
        AdminResult result = new AdminResult();
        result.setTotalCount(0);
        List<CustomerTaskConfigVO> list = operService.taskList(request);
        if (list != null){
            result.setTotalCount(list.size());
        }
        result.setData(list);
        return result;
    }

    @ApiOperation(value = "坐席月任务配置-数据新增",notes = "坐席月任务配置-数据新增")
    @PostMapping("/task/add")
    @ResponseBody
    private AdminResult taskAdd(@RequestBody CustomerTaskConfigVO customerTaskConfigVO){
        if(blankCheck(4, null, customerTaskConfigVO, null)){
            return new AdminResult("3", "必传字段未传");
        }
        customerTaskConfigVO.setStatus(1);
        int insertFlag = operService.taskAdd(customerTaskConfigVO);
        if (insertFlag < 1){
            return new AdminResult(BaseResult.FAIL, BaseResult.FAIL_DESC);
        }
        return new AdminResult();
    }


    @ApiOperation(value = "坐席月任务配置-数据编辑",notes = "坐席月任务配置-数据编辑")
    @PostMapping("/task/update")
    @ResponseBody
    private AdminResult taskUpdate(@RequestBody CustomerTaskConfigVO customerTaskConfigVO){
        if(blankCheck(5, null, customerTaskConfigVO, null)){
            return new AdminResult("3", "必传字段未传");
        }
        int updateFlag = operService.taskUpdate(customerTaskConfigVO);
        if (updateFlag < 1){
            return new AdminResult(BaseResult.FAIL, BaseResult.FAIL_DESC);
        }
        return new AdminResult();
    }

    @ApiOperation(value = "坐席月任务配置-数据启用/禁用",notes = "坐席月任务配置-数据启用/禁用")
    @PostMapping("/task/able")
    @ResponseBody
    private AdminResult taskAble(@RequestBody CustomerTaskConfigVO customerTaskConfigVO){
        if(blankCheck(6, null, customerTaskConfigVO, null)){
            return new AdminResult("3", "必传字段未传");
        }
        int updateFlag = operService.taskUpdate(customerTaskConfigVO);
        if (updateFlag < 1){
            return new AdminResult(BaseResult.FAIL, BaseResult.FAIL_DESC);
        }
        return new AdminResult();
    }

    @ApiOperation(value = "添加/编辑重复检验",notes = "添加/编辑重复检验")
    @PostMapping("/common/save_check")
    @ResponseBody
    private Map saveCheck(@RequestBody CustomerTaskConfigRequest request){
        return operService.saveCheck(request);
    }

    /**
     * 接口字段非空校验
     * @param flag
     * @param screenConfigVO
     * @param customerTaskConfigVO
     * @param request
     * @return
     */
    private boolean blankCheck(int flag, ScreenConfigVO screenConfigVO, CustomerTaskConfigVO customerTaskConfigVO, CustomerTaskConfigRequest request){
        return operService.blankCheck(flag, screenConfigVO, customerTaskConfigVO, request);
    }
}
