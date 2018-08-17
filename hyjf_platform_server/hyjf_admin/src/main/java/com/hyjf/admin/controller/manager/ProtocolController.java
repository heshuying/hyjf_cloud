package com.hyjf.admin.controller.manager;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ProtocolService;
import com.hyjf.am.response.admin.AdminProtocolResponse;
import com.hyjf.am.resquest.admin.AdminProtocolRequest;
import com.hyjf.am.vo.admin.ProtocolTemplateCommonVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 协议模板管理
 * @author：yinhui
 * @Date: 2018/8/8  15:10
 */
@Api(tags ="配置中心-协议模板管理")
@RestController
@RequestMapping("/hyjf-admin/manager/protocol")
public class ProtocolController extends BaseController{

    @Autowired
    private ProtocolService protocolService;

    /**
     * 分页显示
     * @param request
     * @return
     */
    @ApiOperation(value = "配置中心-协议模板管理", notes = "配置中心-协议模板管理 分页显示")
    @PostMapping("/search")
    public AdminResult<ListResult<ProtocolTemplateCommonVO>> search(@RequestBody AdminProtocolRequest request){
        AdminProtocolResponse response = new AdminProtocolResponse();
        response = protocolService.searchPage(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        return new AdminResult<ListResult<ProtocolTemplateCommonVO>>(ListResult.build(response.getResultList(), response.getCount())) ;
    }

    /**
     * 迁移到查看详细画面
     * @param request
     * @return
     */
    @ApiOperation(value = "配置中心-协议模板管理", notes = "配置中心-协议模板管理 迁移到查看详细画面")
    @PostMapping("/infoInfoAction")
    public AdminResult<ProtocolTemplateCommonVO> infoInfoAction(@RequestBody  AdminProtocolRequest request){
        AdminProtocolResponse response = new AdminProtocolResponse();
        response = protocolService.getProtocolTemplateById(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        return new AdminResult<ProtocolTemplateCommonVO>(response.getResult()) ;
    }

    /**
     * 添加协议模板
     * @param request
     * @return
     */
    @ApiOperation(value = "配置中心-协议模板管理", notes = "配置中心-协议模板管理 添加协议模板")
    @PostMapping("/insertAction")
    public AdminProtocolResponse insertAction(@RequestBody AdminProtocolRequest request, HttpServletRequest httpServletRequest){
        AdminProtocolResponse response = new AdminProtocolResponse();
        AdminSystemVO user = getUser(httpServletRequest);
        String userId = user.getId();
        protocolService.insertProtocolTemplate(request,userId);


        return response;
    }

    /**
     * 修改协议模板
     * @param request
     * @return
     */
    @ApiOperation(value = "配置中心-协议模板管理", notes = "配置中心-协议模板管理 修改协议模板")
    @PostMapping("/updateAction")
    public AdminProtocolResponse updateProtocolTemplate(@RequestBody  AdminProtocolRequest request, HttpServletRequest httpServletRequest){
        AdminProtocolResponse response = new AdminProtocolResponse();
        AdminSystemVO user = getUser(httpServletRequest);
        String userId = user.getId();
        protocolService.updateProtocolTemplate(request,userId);
        return response;
    }

    /**
     * 删除协议模板
     * @param request
     * @return
     */
    @ApiOperation(value = "配置中心-协议模板管理", notes = "配置中心-协议模板管理 删除协议模板")
    @RequestMapping(value="/deleteAction",method = RequestMethod.DELETE)
    public AdminProtocolResponse deleteAction(@RequestBody  AdminProtocolRequest request, HttpServletRequest httpServletRequest){
        AdminProtocolResponse response = new AdminProtocolResponse();
        AdminSystemVO user = getUser(httpServletRequest);
        String userId = user.getId();
        protocolService.deleteProtocolTemplate(request,userId);
        return response;
    }
}
