package com.hyjf.admin.controller.manager;

import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowFlowService;
import com.hyjf.admin.service.BorrowProjectTypeService;
import com.hyjf.admin.utils.ValidatorFieldCheckUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBorrowFlowResponse;
import com.hyjf.am.resquest.admin.AdminBorrowFlowRequest;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "配置中心借款项目配置---流程配置",tags = "配置中心借款项目配置---流程配置")
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
    public AdminBorrowFlowResponse selectBorrowFlowList(HttpServletRequest request, @RequestBody AdminBorrowFlowRequest adminRequest) {
        AdminBorrowFlowResponse resList=borrowFlowService.selectBorrowFlowList(adminRequest);
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
        return resList ;
    }

    @ApiOperation(value = "查询流程配置详情页面", notes = "查询流程配置详情页面")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminBorrowFlowResponse selectBorrowFlowInfo(HttpServletRequest request, @RequestBody AdminBorrowFlowRequest adminRequest) {
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
            return resList;
        }
        return resList;
    }
    @ApiOperation(value = "添加流程配置", notes = "添加流程配置")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminBorrowFlowResponse insertBorrowFlowRecord(HttpServletRequest request, @RequestBody AdminBorrowFlowRequest adminRequest) {
        AdminBorrowFlowResponse resList=new AdminBorrowFlowResponse();
        ModelAndView modelAndView = new ModelAndView();
        // 表单校验
        this.validatorFieldCheck(modelAndView, adminRequest);
        if (ValidatorFieldCheckUtil.hasValidateError(modelAndView)) {
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
            resList.setMessage("输入内容验证失败");
            return resList;
        }
        // todo 联调时需要放开 todo-------------------------------------------
//        AdminSystemVO user = getUser(request);
//        String userId = user.getId();
//        adminRequest.setCreateUser(Integer.valueOf(userId));
//        adminRequest.setUpdateUser(Integer.valueOf(userId));
        adminRequest.setCreateUser(3);
        adminRequest.setUpdateUser(3);
        // 插入
        this.borrowFlowService.insertRecord(adminRequest);
        resList.setRtn(Response.SUCCESS);
        return resList ;
    }

    @ApiOperation(value = "修改流程配置", notes = "修改流程配置")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_UPDATE)
    public AdminBorrowFlowResponse updateBorrowFlowRecord(HttpServletRequest request, @RequestBody AdminBorrowFlowRequest adminRequest) {
        AdminBorrowFlowResponse resList=new AdminBorrowFlowResponse();
        // todo 联调时需要放开 todo-------------------------------------------
//        AdminSystemVO user = getUser(request);
//        String userId = user.getId();
//        adminRequest.setUpdateUser(Integer.valueOf(userId));
        adminRequest.setUpdateUser(3);
        // 数据更新
        this.borrowFlowService.updateRecord(adminRequest);
        resList.setRtn(Response.SUCCESS);
        return resList ;
    }
    @ApiOperation(value = "配置中心借款项目配置---项目流程 流程配置", notes = "删除流程配置")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public String deleteBorrowFlowRecord(HttpServletRequest request, @RequestBody AdminBorrowFlowRequest adminRequest) {
        AdminBorrowFlowResponse resList=new AdminBorrowFlowResponse();
        // 数据更新
        this.borrowFlowService.deleteRecord(adminRequest);
        resList.setRtn(Response.SUCCESS);
        return "redirect:" + "/manager/config/borrowflow/init";
    }

    /**
     * 画面校验
     *
     * @param modelAndView
     * @param form
     */
    private void validatorFieldCheck(ModelAndView modelAndView, AdminBorrowFlowRequest form) {
        //标的类型
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "borrowCd", form.getBorrowCd().toString());
        //机构编号
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "instCode", form.getInstCode());
        //资产类型
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "assetType", form.getAssetType().toString());
//    	是否关联计划
//    	ValidatorFieldCheckUtil.validateRequired(modelAndView, "isAssociatePlan", form.getIsAssociatePlan().toString());
        //自动录标
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "autoAdd", form.getAutoAdd().toString());
        //自动备案
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "autoRecord", form.getAutoRecord().toString());
        //自动保证金
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "autoBail", form.getAutoBail().toString());
        //自动初审
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "autoAudit", form.getAutoAudit().toString());
        //自动复审
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "autoReview", form.getAutoReview().toString());
        //是否开启
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "isOpen", form.getIsOpen().toString());

        // 检查唯一性
        int cnt = this.borrowFlowService.countRecordByPK(form.getInstCode(), form.getAssetType());
        if (cnt > 0) {
            ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "instCode", "borrowflow.pk.repeat");
        }

    }

    /**
     * 下拉联动
     *
     * @param request
     * @return 进入资产列表页面
     */
    @ApiOperation(value = "配置中心借款项目配置---项目流程 流程配置", notes = "下拉联动")
    @PostMapping("/assetTypeAction/{instCode}")
    public List<Map<String, Object>> assetTypeAction(@PathVariable String instCode) {
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
        return resultList;
    }

}
