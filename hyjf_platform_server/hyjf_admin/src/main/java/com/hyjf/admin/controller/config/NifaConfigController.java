/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.hyjf.admin.beans.request.NifaFieldDefinitionAddRequestBean;
import com.hyjf.admin.beans.request.NifaFieldDefinitionRequestBean;
import com.hyjf.admin.beans.vo.NifaFieldDefinitionCustomizeVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.CustomerTransferService;
import com.hyjf.admin.service.NifaConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.NifaFieldDefinitionResponse;
import com.hyjf.am.resquest.admin.NifaFieldDefinitionAddRequest;
import com.hyjf.am.resquest.user.NifaFieldDefinitionRequest;
import com.hyjf.am.vo.admin.NifaFieldDefinitionVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author nxl
 * @version NifaConfigController, v0.1 2018/8/15 17:38
 */
@Api(value = "配置中心_互金系统配置", tags = "配置中心_互金系统配置")
@RestController
@RequestMapping("/hyjf-admin/nifaconfig")
public class NifaConfigController  extends BaseController
{
    @Autowired
    private NifaConfigService nifaConfigService;
    @Autowired
    private CustomerTransferService customerTransferService;

    private static final String PERMISSIONS = "nifaconfig";

    /**
     * 互金字段定义列表
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "互金字段定义列表", notes = "互金字段定义列表管理列表")
    @PostMapping("/selectFieldDefinitionList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<NifaFieldDefinitionCustomizeVO>> selectFieldDefinitionList(@RequestBody NifaFieldDefinitionRequestBean requestBean){
        NifaFieldDefinitionRequest request = new NifaFieldDefinitionRequest();
        BeanUtils.copyProperties(requestBean, request);
        NifaFieldDefinitionResponse response = nifaConfigService.selectFieldDefinitionList(request);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<NifaFieldDefinitionCustomizeVO> nifaFieldDefinitionCustomizeVOList = new ArrayList<NifaFieldDefinitionCustomizeVO>();
        if(null!=response.getResultList()&&response.getResultList().size()>0){
            nifaFieldDefinitionCustomizeVOList = CommonUtils.convertBeanList(response.getResultList(),NifaFieldDefinitionCustomizeVO.class);
        }
        SimpleDateFormat smp = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");
        if(null!=nifaFieldDefinitionCustomizeVOList&&nifaFieldDefinitionCustomizeVOList.size()>0){
            for(NifaFieldDefinitionCustomizeVO nifaFieldDefinitionCustomizeVO:nifaFieldDefinitionCustomizeVOList){
                //根据创建者id查找admin,获取创建者用户名
                if(null!=nifaFieldDefinitionCustomizeVO.getCreateUserId()){
                    AdminSystemVO adminSystemVO = customerTransferService.getAdminSystemByUserId(nifaFieldDefinitionCustomizeVO.getCreateUserId());
                    if(null!=adminSystemVO){
                        nifaFieldDefinitionCustomizeVO.setCreateUserName(adminSystemVO.getUsername());
                    }
                }
                //根据修改者id查找admin,获取修改者用户名
                if(null!=nifaFieldDefinitionCustomizeVO.getUpdateUserId()){
                    AdminSystemVO adminSystemVO = customerTransferService.getAdminSystemByUserId(nifaFieldDefinitionCustomizeVO.getUpdateUserId());
                    if(null!=adminSystemVO){
                        nifaFieldDefinitionCustomizeVO.setCreateUserName(adminSystemVO.getUsername());
                    }
                }
                //日期处理
                String strCreate = smp.format(nifaFieldDefinitionCustomizeVO.getCreateTime());
                String strUpd = smp.format(nifaFieldDefinitionCustomizeVO.getUpdateTime());
                nifaFieldDefinitionCustomizeVO.setCreateDate(strCreate);
                nifaFieldDefinitionCustomizeVO.setUpdateDate(strUpd);
            }
        }
        return new AdminResult<ListResult<NifaFieldDefinitionCustomizeVO>>(ListResult.build(nifaFieldDefinitionCustomizeVOList, response.getRecordTotal())) ;
    }

    /**
     * 添加互金字段定义
     * @param request
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "添加互金字段定义", notes = "互金字段定义列表管理列表")
    @PostMapping("/insertNifaFieldDefinition")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertNifaFieldDefinition(HttpServletRequest request, @RequestBody NifaFieldDefinitionAddRequestBean requestBean){
        NifaFieldDefinitionAddRequest nifaFieldDefinitionAddRequest = new NifaFieldDefinitionAddRequest();
        BeanUtils.copyProperties(requestBean, nifaFieldDefinitionAddRequest);
        AdminSystemVO adminSystemVO = this.getUser(request);
        String loginUserId = adminSystemVO.getId();
        if(StringUtils.isNotBlank(loginUserId)){
            nifaFieldDefinitionAddRequest.setCreateUserId(Integer.parseInt(loginUserId));
        }
        nifaFieldDefinitionAddRequest.setCreateTime(new Date());
        boolean isInset = nifaConfigService.insertNifaFieldDefinition(nifaFieldDefinitionAddRequest);
        if (!isInset) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>();
    }

    /**
     * 根据id查找互金字段定义
     * @param nifaId
     * @return
     */
    @ApiOperation(value = "根据id查找互金字段定义", notes = "互金字段定义列表管理列表")
    @PostMapping("/initNifaFieldDefinitionInfo")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<NifaFieldDefinitionCustomizeVO> initNifaFieldDefinitionInfo(@RequestBody String nifaId){
        NifaFieldDefinitionResponse response = nifaConfigService.selectFieldDefinitionById(nifaId);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        NifaFieldDefinitionCustomizeVO nifaFieldDefinitionCustomizeVO = new NifaFieldDefinitionCustomizeVO();
        NifaFieldDefinitionVO nifaFieldDefinitionVO =  response.getResult();
        if(null!=nifaFieldDefinitionVO){
            BeanUtils.copyProperties(nifaFieldDefinitionVO, nifaFieldDefinitionCustomizeVO);
        }
        return new AdminResult<NifaFieldDefinitionCustomizeVO>(nifaFieldDefinitionCustomizeVO) ;
    }

    /**
     * 修改互金字段定义
     * @param request
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "修改互金字段定义", notes = "互金字段定义列表管理列表")
    @PostMapping("/updateNifaFieldDefinition")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateNifaFieldDefinition(HttpServletRequest request, @RequestBody NifaFieldDefinitionAddRequestBean requestBean){
        NifaFieldDefinitionAddRequest nifaFieldDefinitionAddRequest = new NifaFieldDefinitionAddRequest();
        BeanUtils.copyProperties(requestBean, nifaFieldDefinitionAddRequest);
        // 当前登陆用户
        AdminSystemVO adminSystemVO = this.getUser(request);
        String loginUserId = adminSystemVO.getId();
        if(StringUtils.isNotBlank(loginUserId)){
            nifaFieldDefinitionAddRequest.setUpdateUserId(Integer.parseInt(loginUserId));
        }
        //修改时间
        nifaFieldDefinitionAddRequest.setUpdateTime(new Date());
        //修改
        boolean updFlg =nifaConfigService.updateNifaFieldDefinition(nifaFieldDefinitionAddRequest);
        if(!updFlg){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>();
    }
}
