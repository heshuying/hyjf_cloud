package com.hyjf.admin.controller.manager;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowFlowService;
import com.hyjf.admin.service.BorrowProjectTypeService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBorrowFlowResponse;
import com.hyjf.am.resquest.admin.AdminBorrowFlowRequest;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/30.
 */
@Api(tags = "配置中心-借款项目配置---流程配置")
@RestController
@RequestMapping("/hyjf-admin/config/borrowflow")
public class BorrowFlowController extends BaseController {

    //权限名称
    private static final String PERMISSIONS = "borrowflow";

    @Autowired
    private BorrowFlowService borrowFlowService;
//    @Autowired
//    private BorrowCommonService BorrowCommonService;
    @Autowired
    private BorrowProjectTypeService borrowProjectTypeService;
    @ApiOperation(value = "查询流程配置", notes = "查询流程配置")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult selectBorrowFlowList(HttpServletRequest request, @RequestBody AdminBorrowFlowRequest adminRequest) {
        AdminBorrowFlowResponse resList=borrowFlowService.selectBorrowFlowList(adminRequest);
        if(resList==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(resList)) {
            return new AdminResult<>(FAIL, resList.getMessage());
        }
        // 项目列表
        List<BorrowProjectTypeVO> borrowProjectTypeList = this.borrowFlowService.borrowProjectTypeList("HZT");
        resList.setBorrowProjectTypeList(borrowProjectTypeList);
        // 资金来源
        List<HjhInstConfigVO> hjhInstConfigList = this.borrowFlowService.hjhInstConfigList("");
        resList.setHjhInstConfigList(hjhInstConfigList);
        // 产品类型
        List<HjhAssetTypeVO> assetTypeList = this.borrowFlowService.hjhAssetTypeList(adminRequest.getInstCodeSrch());
        resList.setAssetTypeList(assetTypeList);
        // 状态
        List<ParamNameVO> statusList = this.borrowProjectTypeService.getParamNameList("FLOW_STATUS");
        resList.setStatusList(statusList);
        return new AdminResult<AdminBorrowFlowResponse>(resList) ;
    }

    @ApiOperation(value = "查询流程配置详情页面", notes = "查询流程配置详情页面")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminResult selectBorrowFlowInfo(HttpServletRequest request, @RequestBody AdminBorrowFlowRequest adminRequest) {
        AdminBorrowFlowResponse resList= null;
        if(adminRequest.getId() != null){
            resList=borrowFlowService.selectBorrowFlowInfo(adminRequest);
        }
        if (!Response.isSuccess(resList)) {
            // 项目列表
            List<BorrowProjectTypeVO> borrowProjectTypeList = this.borrowFlowService.borrowProjectTypeList("HZT");
            resList.setBorrowProjectTypeList(borrowProjectTypeList);
            // 资金来源
            List<HjhInstConfigVO> hjhInstConfigList = this.borrowFlowService.hjhInstConfigList("");
            resList.setHjhInstConfigList(hjhInstConfigList);
            // 产品类型
            List<HjhAssetTypeVO> assetTypeList = this.borrowFlowService.hjhAssetTypeList(adminRequest.getInstCodeSrch());
            resList.setAssetTypeList(assetTypeList);
            return new AdminResult<AdminBorrowFlowResponse>(resList) ;
        }
        return new AdminResult<AdminBorrowFlowResponse>(resList) ;
    }
    @ApiOperation(value = "添加流程配置", notes = "添加流程配置")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertBorrowFlowRecord(HttpServletRequest request, @RequestBody AdminBorrowFlowRequest adminRequest) {
        AdminBorrowFlowResponse resList=new AdminBorrowFlowResponse();
        ModelAndView modelAndView = new ModelAndView();
        // 表单校验
        String message=this.validatorFieldCheck(modelAndView, adminRequest);
        if (StringUtils.isNotBlank(message)) {
            // 项目列表
            List<BorrowProjectTypeVO> borrowProjectTypeList = this.borrowFlowService.borrowProjectTypeList("HZT");
            resList.setBorrowProjectTypeList(borrowProjectTypeList);
            // 资金来源
            List<HjhInstConfigVO> hjhInstConfigList = this.borrowFlowService.hjhInstConfigList("");
            resList.setHjhInstConfigList(hjhInstConfigList);
            // 产品类型
            List<HjhAssetTypeVO> assetTypeList = this.borrowFlowService.hjhAssetTypeList(adminRequest.getInstCode());
            resList.setAssetTypeList(assetTypeList);
            resList.setRtn(Response.FAIL);
            resList.setMessage(message);
            return new AdminResult<AdminBorrowFlowResponse>(resList) ;
        }
        AdminSystemVO user = getUser(request);
        if(org.apache.commons.lang.StringUtils.isNotBlank(user.getId())){
            adminRequest.setCreateUser(Integer.parseInt(user.getId()));
            adminRequest.setUpdateUser(Integer.valueOf(user.getId()));
        }else{
            adminRequest.setCreateUser(3);//为了接口测试用
            adminRequest.setUpdateUser(3);
        }
        // 插入
        this.borrowFlowService.insertRecord(adminRequest);
        resList.setRtn(Response.SUCCESS);
        return new AdminResult<AdminBorrowFlowResponse>(resList) ;
    }

    @ApiOperation(value = "修改流程配置", notes = "修改流程配置")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_UPDATE)
    public AdminResult updateBorrowFlowRecord(HttpServletRequest request, @RequestBody AdminBorrowFlowRequest adminRequest) {
        AdminBorrowFlowResponse resList=new AdminBorrowFlowResponse();
        AdminSystemVO user = getUser(request);
        if(org.apache.commons.lang.StringUtils.isNotBlank(user.getId())){
            adminRequest.setUpdateUser(Integer.valueOf(user.getId()));
        }else{
            adminRequest.setUpdateUser(3);
        }
        // 数据更新
        this.borrowFlowService.updateRecord(adminRequest);
        resList.setRtn(Response.SUCCESS);
        return new AdminResult<AdminBorrowFlowResponse>(resList) ;
    }
    @ApiOperation(value = "配置中心借款项目配置---项目流程 流程配置", notes = "删除流程配置")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteBorrowFlowRecord(HttpServletRequest request, @RequestBody AdminBorrowFlowRequest adminRequest) {
        AdminBorrowFlowResponse resList=new AdminBorrowFlowResponse();
        // 数据更新
        this.borrowFlowService.deleteRecord(adminRequest);
        resList.setRtn(Response.SUCCESS);
        return new AdminResult<AdminBorrowFlowResponse>(resList) ;
    }

    /**
     * 画面校验
     *
     * @param modelAndView
     * @param form
     */
    private String validatorFieldCheck(ModelAndView modelAndView, AdminBorrowFlowRequest form) {
        //标的类型
        if(null == form.getBorrowCd()){
            return "borrowCd 不能为空！";
        }
        //机构编号
        if(StringUtils.isBlank(form.getInstCode())){
            return "instCode 不能为空！";
        }
        //资产类型
        if(null == form.getAssetType()){
            return "assetType 不能为空！";
        }
//    	是否关联计划
//    	ValidatorFieldCheckUtil.validateRequired(modelAndView, "isAssociatePlan", form.getIsAssociatePlan().toString());
        //自动录标
        if(null == form.getAutoAdd()){
            return "autoAdd 不能为空！";
        }
        //自动备案
        if(null == form.getAutoRecord()){
            return "autoRecord 不能为空！";
        }
        //自动保证金
        if(null == form.getAutoBail()){
            return "autoBail 不能为空！";
        }
        //自动初审
        if(null == form.getAutoAudit()){
            return "iautoAudit 不能为空！";
        }
        //自动复审
        if(null == form.getAutoReview()){
            return "autoReview 不能为空！";
        }
        //是否开启
        if(null == form.getIsOpen()){
            return "isOpen 不能为空！";
        }
        // 检查唯一性
        int cnt = this.borrowFlowService.countRecordByPK(form.getInstCode(), form.getAssetType());
        if (cnt > 0) {
            return "instCode 重复了！";
        }
        return "";
    }

    /**
     * 下拉联动
     *
     * @return 进入资产列表页面
     */
    @ApiOperation(value = "配置中心借款项目配置---项目流程 流程配置", notes = "下拉联动")
    @PostMapping("/assetTypeAction")
    public AdminResult assetTypeAction(@RequestParam(value="instCode") String instCode) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        // 根据资金来源取得产品类型
        List<HjhAssetTypeVO> hjhAssetTypeList = this.borrowFlowService.hjhAssetTypeList(instCode);
        if (hjhAssetTypeList != null && hjhAssetTypeList.size() > 0) {
            for (HjhAssetTypeVO hjhAssetBorrowType : hjhAssetTypeList) {
                Map<String, Object> mapTemp = new HashMap<String, Object>();
                mapTemp.put("id", hjhAssetBorrowType.getAssetType());
                mapTemp.put("text", hjhAssetBorrowType.getAssetTypeName());
                resultList.add(mapTemp);
            }
        }
        return new AdminResult<List<Map<String, Object>>>(resultList) ;
    }

}
