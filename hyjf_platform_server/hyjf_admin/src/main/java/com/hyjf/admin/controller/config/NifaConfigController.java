/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.hyjf.admin.beans.request.NifaContractTemplateAddRequestBean;
import com.hyjf.admin.beans.request.NifaContractTemplateRequestBean;
import com.hyjf.admin.beans.request.NifaFieldDefinitionAddRequestBean;
import com.hyjf.admin.beans.request.NifaFieldDefinitionRequestBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.beans.vo.NifaContractTemplateCustomizeVO;
import com.hyjf.admin.beans.vo.NifaFieldDefinitionCustomizeVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.CustomerTransferService;
import com.hyjf.admin.service.NifaConfigService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.NifaContractTemplateResponse;
import com.hyjf.am.response.admin.NifaFieldDefinitionResponse;
import com.hyjf.am.response.trade.FddTempletResponse;
import com.hyjf.am.resquest.admin.NifaContractTemplateAddRequest;
import com.hyjf.am.resquest.admin.NifaFieldDefinitionAddRequest;
import com.hyjf.am.resquest.config.NifaContractTemplateRequest;
import com.hyjf.am.resquest.config.NifaFieldDefinitionRequest;
import com.hyjf.am.vo.admin.NifaContractTemplateVO;
import com.hyjf.am.vo.admin.NifaFieldDefinitionVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.FddTempletVO;
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
                        nifaFieldDefinitionCustomizeVO.setUpdateUserName(adminSystemVO.getUsername());
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

    /**
     * 查找合同模板编号
     * @return
     */
    @ApiOperation(value = "查找合同模板编号", notes = "互金字段定义列表管理列表")
    @PostMapping("/selectFddTempletId")
    public AdminResult<List<DropDownVO>> selectFddTempletId(){
        FddTempletResponse fddTempletResponse = nifaConfigService.selectFddTempletId();
        List<DropDownVO> dropDownVOList = new ArrayList<DropDownVO>();
        if(fddTempletResponse==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(fddTempletResponse)) {
            return new AdminResult<>(FAIL, fddTempletResponse.getMessage());
        }
        List<FddTempletVO> fddTempletVOList = fddTempletResponse.getResultList();
        dropDownVOList = ConvertUtils.convertListToDropDown(fddTempletResponse.getResultList(),"id","templetId");
        AdminResult<List<DropDownVO>> result=new AdminResult<List<DropDownVO>> ();
        result.setData(dropDownVOList);
        return result ;
    }
    /**
     * 添加互金字段定义
     * @param request
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "添加合同模板条款", notes = "互金字段定义列表管理列表")
    @PostMapping("/insertNifaContractTemplate")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertNifaContractTemplate(HttpServletRequest request, @RequestBody NifaContractTemplateAddRequestBean requestBean){
        NifaContractTemplateAddRequest nifaFieldDefinitionAddRequest = new NifaContractTemplateAddRequest();
        BeanUtils.copyProperties(requestBean, nifaFieldDefinitionAddRequest);
        AdminSystemVO adminSystemVO = this.getUser(request);
        String loginUserId = adminSystemVO.getId();
        if(StringUtils.isNotBlank(loginUserId)){
            nifaFieldDefinitionAddRequest.setCreateUserId(Integer.parseInt(loginUserId));
        }
        nifaFieldDefinitionAddRequest.setCreateTime(new Date());
        boolean isInset = nifaConfigService.insertNifaContractTemplate(nifaFieldDefinitionAddRequest);
        if (!isInset) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>();
    }
    /**
     * 添加互金字段定义
     * @param request
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "修改合同模板条款", notes = "互金字段定义列表管理列表")
    @PostMapping("/updateNifaContractTemplate")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateNifaContractTemplate(HttpServletRequest request, @RequestBody NifaContractTemplateAddRequestBean requestBean){
        NifaContractTemplateAddRequest nifaFieldDefinitionAddRequest = new NifaContractTemplateAddRequest();
        BeanUtils.copyProperties(requestBean, nifaFieldDefinitionAddRequest);
        AdminSystemVO adminSystemVO = this.getUser(request);
        String loginUserId = adminSystemVO.getId();
        if(StringUtils.isNotBlank(loginUserId)){
            nifaFieldDefinitionAddRequest.setCreateUserId(Integer.parseInt(loginUserId));
        }
        nifaFieldDefinitionAddRequest.setCreateTime(new Date());
        boolean isInset = nifaConfigService.updateNifaContractTemplate(nifaFieldDefinitionAddRequest);
        if (!isInset) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>();
    }
    /**
     * 根据id查找合同模版约定条款表
     * @param contractId
     * @return
     */
    @ApiOperation(value = "根据id查找合同模版约定条款表", notes = "互金字段定义列表管理列表")
    @PostMapping("/selectNifaContractTemplateById")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<NifaContractTemplateCustomizeVO> selectNifaContractTemplateById(@RequestBody String contractId){
        NifaContractTemplateResponse response = nifaConfigService.selectNifaContractTemplateById(contractId);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        NifaContractTemplateCustomizeVO nifaFieldDefinitionCustomizeVO = new NifaContractTemplateCustomizeVO();
        NifaContractTemplateVO nifaFieldDefinitionVO =  response.getResult();
        if(null!=nifaFieldDefinitionVO){
            BeanUtils.copyProperties(nifaFieldDefinitionVO, nifaFieldDefinitionCustomizeVO);
        }
        return new AdminResult<NifaContractTemplateCustomizeVO>(nifaFieldDefinitionCustomizeVO) ;
    }

    @ApiOperation(value = "合同模版约定条款表列表", notes = "互金字段定义列表管理列表")
    @PostMapping("/selectNifaContractTemplateList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<NifaContractTemplateCustomizeVO>> selectNifaContractTemplateList(@RequestBody NifaContractTemplateRequestBean requestBean){
        NifaContractTemplateRequest request = new NifaContractTemplateRequest();
        BeanUtils.copyProperties(requestBean, request);
        NifaContractTemplateResponse response = nifaConfigService.selectNifaContractTemplateList(request);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<NifaContractTemplateCustomizeVO> nifaFieldDefinitionCustomizeVOList = new ArrayList<NifaContractTemplateCustomizeVO>();
        if(null!=response.getResultList()&&response.getResultList().size()>0){
            nifaFieldDefinitionCustomizeVOList = CommonUtils.convertBeanList(response.getResultList(),NifaContractTemplateCustomizeVO.class);
        }
        SimpleDateFormat smp = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");
        if(null!=nifaFieldDefinitionCustomizeVOList&&nifaFieldDefinitionCustomizeVOList.size()>0){
            for(NifaContractTemplateCustomizeVO nifaFieldDefinitionCustomizeVO:nifaFieldDefinitionCustomizeVOList){
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
                        nifaFieldDefinitionCustomizeVO.setUpdateUserName(adminSystemVO.getUsername());
                    }
                }
                //日期处理
                String strCreate = smp.format(nifaFieldDefinitionCustomizeVO.getCreateTime());
                String strUpd = smp.format(nifaFieldDefinitionCustomizeVO.getUpdateTime());
                nifaFieldDefinitionCustomizeVO.setCreateDate(strCreate);
                nifaFieldDefinitionCustomizeVO.setUpdateDate(strUpd);
            }
        }
        return new AdminResult<ListResult<NifaContractTemplateCustomizeVO>>(ListResult.build(nifaFieldDefinitionCustomizeVOList, response.getRecordTotal())) ;
    }

    @ApiOperation(value = "删除合同模板条款", notes = "互金字段定义列表管理列表")
    @PostMapping("/deleteNifaContractTemplateById")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteNifaContractTemplateById(@RequestBody String contractId){
       if(StringUtils.isNotBlank(contractId)){
           int intId = Integer.parseInt(contractId);
           boolean deleteFlg = nifaConfigService.deleteNifaContractTemplateById(intId);
           if(!deleteFlg){
               return new AdminResult<>(FAIL, FAIL_DESC);
           }
       }else{
           return new AdminResult<>(FAIL, "id不能为空");
       }
        return new AdminResult<>();
    }
}
