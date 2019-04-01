package com.hyjf.admin.controller.vip.content;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.vip.content.OperService;
import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.response.admin.vip.content.CustomerTaskConfigVOResponse;
import com.hyjf.am.response.admin.vip.content.ScreenConfigVOResponse;
import com.hyjf.am.resquest.admin.CustomerTaskConfigRequest;
import com.hyjf.am.resquest.admin.ScreenConfigRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.user.CustomerTaskConfigVO;
import com.hyjf.am.vo.user.ScreenConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(value = "会员中心-内容中心", tags = "会员中心-内容中心")
@RequestMapping("/hyjf-admin/content")
@RestController
public class OperController extends BaseController {

    /**
     * 大屏运营部数据配置-权限关键字
     */
    public static final String PERMISSIONS = "vipcontentoper";
    /**
     * 坐席月任务配置-权限关键字
     */
    public static final String PERMISSIONST = "vipcontenttask";
    @Autowired
    private OperService operService;

    // 大屏运营部数据配置

    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    @ApiOperation(value = "大屏运营部数据配置-列表查询",notes = "大屏运营部数据配置-列表查询")
    @PostMapping("/oper/list")
    @ResponseBody
    private AdminResult operList(@RequestBody ScreenConfigRequest request){
        AdminResult result = new AdminResult();
        ScreenConfigVOResponse response = operService.operList(request);
        result.setTotalCount(response.getTotalCount());
        result.setData(response.getResultList());
        return result;
    }

    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    @ApiOperation(value = "大屏运营部数据配置-数据新增",notes = "大屏运营部数据配置-数据新增")
    @PostMapping("/oper/add")
    @ResponseBody
    private AdminResult operAdd(HttpServletRequest httpServletRequest, @RequestBody ScreenConfigVO screenConfigVO){
        if(blankCheck(1, screenConfigVO, null, null)){
            return new AdminResult("3", "必传字段未传");
        }
        AdminSystemVO user = getUser(httpServletRequest);
        screenConfigVO.setCreateUserId(Integer.valueOf(user.getId()));
        screenConfigVO.setUpdateUserId(Integer.valueOf(user.getId()));
        screenConfigVO.setStatus(1);
        int insertFlag = operService.operAdd(screenConfigVO);
        if (insertFlag < 1){
            return new AdminResult(BaseResult.FAIL, BaseResult.FAIL_DESC);
        }

        return new AdminResult();
    }

    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    @ApiOperation(value = "大屏运营部数据配置-数据详情",notes = "大屏运营部数据配置-数据详情")
    @PostMapping("/oper/info")
    @ResponseBody
    private AdminResult operInfo(@RequestBody ScreenConfigVO screenConfigVO){
        if(blankCheck(10, screenConfigVO, null, null)){
            return new AdminResult("3", "必传字段未传");
        }
        AdminResult result = new AdminResult();
        result.setData(operService.operInfo(screenConfigVO));
        return result;
    }

    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    @ApiOperation(value = "大屏运营部数据配置-数据编辑",notes = "大屏运营部数据配置-数据编辑")
    @PostMapping("/oper/update")
    @ResponseBody
    private AdminResult operUpdate(HttpServletRequest httpServletRequest, @RequestBody ScreenConfigVO screenConfigVO){
        if(blankCheck(2, screenConfigVO, null, null)){
            return new AdminResult("3", "必传字段未传");
        }
        AdminSystemVO user = getUser(httpServletRequest);
        screenConfigVO.setUpdateUserId(Integer.valueOf(user.getId()));
        int updateFlag = operService.operUpdate(screenConfigVO);
        if (updateFlag < 1){
            return new AdminResult(BaseResult.FAIL, BaseResult.FAIL_DESC);
        }
        return new AdminResult();
    }

    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    @ApiOperation(value = "大屏运营部数据配置-数据启用/禁用",notes = "大屏运营部数据配置-数据启用/禁用")
    @PostMapping("/oper/able")
    @ResponseBody
    private AdminResult operAble(HttpServletRequest httpServletRequest, @RequestBody ScreenConfigVO screenConfigVO){
        if(blankCheck(3, screenConfigVO, null, null)){
            return new AdminResult("3", "必传字段未传");
        }
        AdminSystemVO user = getUser(httpServletRequest);
        screenConfigVO.setUpdateUserId(Integer.valueOf(user.getId()));
        int updateFlag = operService.operUpdate(screenConfigVO);
        if (updateFlag < 1){
            return new AdminResult(BaseResult.FAIL, BaseResult.FAIL_DESC);
        }
        return new AdminResult();
    }

    // 坐席月任务配置

    @AuthorityAnnotation(key = PERMISSIONST, value = ShiroConstants.PERMISSION_VIEW)
    @ApiOperation(value = "坐席月任务配置-列表查询",notes = "坐席月任务配置-列表查询")
    @PostMapping("/task/list")
    @ResponseBody
    private AdminResult taskList(@RequestBody CustomerTaskConfigRequest request){
        AdminResult result = new AdminResult();
        CustomerTaskConfigVOResponse response = operService.taskList(request);
        result.setTotalCount(response.getTotalCount());
        result.setData(response.getResultList());
        return result;
    }

    @AuthorityAnnotation(key = PERMISSIONST, value = ShiroConstants.PERMISSION_ADD)
    @ApiOperation(value = "坐席月任务配置-数据新增",notes = "坐席月任务配置-数据新增")
    @PostMapping("/task/add")
    @ResponseBody
    private AdminResult taskAdd(HttpServletRequest httpServletRequest, @RequestBody CustomerTaskConfigVO customerTaskConfigVO){
        if(blankCheck(4, null, customerTaskConfigVO, null)){
            return new AdminResult("3", "必传字段未传");
        }
        AdminSystemVO user = getUser(httpServletRequest);
        customerTaskConfigVO.setCreateUserId(Integer.valueOf(user.getId()));
        customerTaskConfigVO.setUpdateUserId(Integer.valueOf(user.getId()));
        customerTaskConfigVO.setStatus(1);
        int insertFlag = operService.taskAdd(customerTaskConfigVO);
        if (insertFlag < 1){
            return new AdminResult(BaseResult.FAIL, BaseResult.FAIL_DESC);
        }
        return new AdminResult();
    }

    @AuthorityAnnotation(key = PERMISSIONST, value = ShiroConstants.PERMISSION_MODIFY)
    @ApiOperation(value = "坐席月任务配置-数据详情",notes = "坐席月任务配置-数据详情")
    @PostMapping("/task/info")
    @ResponseBody
    private AdminResult taskInfo(@RequestBody CustomerTaskConfigVO customerTaskConfigVO){
        if(blankCheck(11, null, customerTaskConfigVO, null)){
            return new AdminResult("3", "必传字段未传");
        }
        AdminResult result = new AdminResult();
        result.setData(operService.taskInfo(customerTaskConfigVO));
        return result;
    }

    @AuthorityAnnotation(key = PERMISSIONST, value = ShiroConstants.PERMISSION_MODIFY)
    @ApiOperation(value = "坐席月任务配置-数据编辑",notes = "坐席月任务配置-数据编辑")
    @PostMapping("/task/update")
    @ResponseBody
    private AdminResult taskUpdate(HttpServletRequest httpServletRequest, @RequestBody CustomerTaskConfigVO customerTaskConfigVO){
        if(blankCheck(5, null, customerTaskConfigVO, null)){
            return new AdminResult("3", "必传字段未传");
        }
        AdminSystemVO user = getUser(httpServletRequest);
        customerTaskConfigVO.setUpdateUserId(Integer.valueOf(user.getId()));
        int updateFlag = operService.taskUpdate(customerTaskConfigVO);
        if (updateFlag < 1){
            return new AdminResult(BaseResult.FAIL, BaseResult.FAIL_DESC);
        }
        return new AdminResult();
    }

    @AuthorityAnnotation(key = PERMISSIONST, value = ShiroConstants.PERMISSION_MODIFY)
    @ApiOperation(value = "坐席月任务配置-数据启用/禁用",notes = "坐席月任务配置-数据启用/禁用")
    @PostMapping("/task/able")
    @ResponseBody
    private AdminResult taskAble(HttpServletRequest httpServletRequest, @RequestBody CustomerTaskConfigVO customerTaskConfigVO){
        if(blankCheck(6, null, customerTaskConfigVO, null)){
            return new AdminResult("3", "必传字段未传");
        }
        AdminSystemVO user = getUser(httpServletRequest);
        customerTaskConfigVO.setUpdateUserId(Integer.valueOf(user.getId()));
        int updateFlag = operService.taskUpdate(customerTaskConfigVO);
        if (updateFlag < 1){
            return new AdminResult(BaseResult.FAIL, BaseResult.FAIL_DESC);
        }
        return new AdminResult();
    }

    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD, ShiroConstants.PERMISSION_MODIFY})
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
