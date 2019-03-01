package com.hyjf.admin.controller.manager;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
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
import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult selectBorrowFlowInfo(HttpServletRequest request, @RequestBody AdminBorrowFlowRequest adminRequest) {
        HjhAssetBorrowTypeVO record = new HjhAssetBorrowTypeVO();
        // modify by libin sonar start
        AdminBorrowFlowResponse resList=  new AdminBorrowFlowResponse();
        // modify by libin sonar end
        if(adminRequest.getId() != null){
            resList=borrowFlowService.selectBorrowFlowInfo(adminRequest);
            if(Response.isSuccess(resList)){
                record=resList.getResult();
                record.setBorrowCdCd(String.valueOf(record.getBorrowCd()));
            }else{
                resList=new AdminBorrowFlowResponse();
                resList.setRtn(Response.FAIL);
                resList.setMessage(Response.FAIL_MSG);
            }
        }
        // 项目列表
        List<BorrowProjectTypeVO> borrowProjectTypeList = this.borrowFlowService.borrowProjectTypeList("HZT");
        resList.setBorrowProjectTypeList(borrowProjectTypeList);
        // 资金来源

         List<HjhInstConfigVO> hjhInstConfigList = this.borrowFlowService.hjhInstConfigList("");
        resList.setHjhInstConfigList(hjhInstConfigList);
        // 产品类型
        List<HjhAssetTypeVO> assetTypeList = this.borrowFlowService.hjhAssetTypeList(record.getInstCode());
        List<Map<String, Object>> assetTypeListMap = new ArrayList<Map<String, Object>>();
        if (assetTypeList != null && assetTypeList.size() > 0) {
            for (HjhAssetTypeVO hjhAssetBorrowType : assetTypeList) {
                Map<String, Object> mapTemp = new HashMap<String, Object>();
                mapTemp.put("id", hjhAssetBorrowType.getAssetType());
                mapTemp.put("text", hjhAssetBorrowType.getAssetTypeName());
                assetTypeListMap.add(mapTemp);
            }
        }
        resList.setAssetTypeListMap(assetTypeListMap);
        return new AdminResult<AdminBorrowFlowResponse>(resList) ;
    }
    @ApiOperation(value = "添加流程配置", notes = "添加流程配置")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertBorrowFlowRecord(HttpServletRequest request, @RequestBody AdminBorrowFlowRequest adminRequest) {
        AdminResult result= new AdminResult();
        if(StringUtils.isNotBlank(adminRequest.getBorrowCdCd())){
            adminRequest.setBorrowCd(Integer.valueOf(adminRequest.getBorrowCdCd()));
        }
        AdminBorrowFlowResponse resList=null;
        ModelAndView modelAndView = new ModelAndView();
        // 表单校验
        String message=this.validatorFieldCheck(modelAndView, adminRequest);
        if (StringUtils.isNotBlank(message)) {
            resList=new AdminBorrowFlowResponse();
            // 项目列表
            List<BorrowProjectTypeVO> borrowProjectTypeList = this.borrowFlowService.borrowProjectTypeList("HZT");
            resList.setBorrowProjectTypeList(borrowProjectTypeList);
            // 资金来源
            List<HjhInstConfigVO> hjhInstConfigList = this.borrowFlowService.hjhInstConfigList("");
            resList.setHjhInstConfigList(hjhInstConfigList);
            // 产品类型
            List<HjhAssetTypeVO> assetTypeList = this.borrowFlowService.hjhAssetTypeList(adminRequest.getInstCode());
            resList.setAssetTypeList(assetTypeList);
            result.setStatus(BaseResult.FAIL);
            result.setStatusDesc(message);
            result.setData(resList);
            return result ;
        }
        AdminSystemVO user = getUser(request);
        adminRequest.setCreateUser(Integer.parseInt(user.getId()));
        adminRequest.setUpdateUser(Integer.valueOf(user.getId()));
//        adminRequest.setCreateUser(3);
//        adminRequest.setUpdateUser(3);
        // 插入
        resList = this.borrowFlowService.insertRecord(adminRequest);
        if (resList == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(resList)) {
            return new AdminResult<>(FAIL, resList.getMessage());
        }
        return new AdminResult<AdminBorrowFlowResponse>(resList) ;
    }

    @ApiOperation(value = "修改流程配置", notes = "修改流程配置")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateBorrowFlowRecord(HttpServletRequest request, @RequestBody AdminBorrowFlowRequest adminRequest) {
        if(adminRequest.getId() == null){
            return new AdminResult<>(FAIL, "id不能为空");
        }
        if(StringUtils.isNotBlank(adminRequest.getBorrowCdCd())){
            adminRequest.setBorrowCd(Integer.valueOf(adminRequest.getBorrowCdCd()));
        }
        AdminSystemVO user = getUser(request);
        adminRequest.setUpdateUser(Integer.valueOf(user.getId()));
//        adminRequest.setUpdateUser(3);
        // 数据更新
        AdminBorrowFlowResponse resList=this.borrowFlowService.updateRecord(adminRequest);
        if (resList == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(resList)) {
            return new AdminResult<>(FAIL, resList.getMessage());
        }
        return new AdminResult<AdminBorrowFlowResponse>(resList) ;
    }
    @ApiOperation(value = "配置中心借款项目配置---项目流程 流程配置", notes = "删除流程配置")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteBorrowFlowRecord(HttpServletRequest request, @RequestBody AdminBorrowFlowRequest adminRequest) {
        // 数据更新
        AdminBorrowFlowResponse resList=this.borrowFlowService.deleteRecord(adminRequest);
        if (resList == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(resList)) {
            return new AdminResult<>(FAIL, resList.getMessage());
        }
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
            return "项目类型不能为空，请重新操作！";
        }
        //机构编号
        if(StringUtils.isBlank(form.getInstCode())){
            return "资产来源不能为空，请重新操作！";
        }
        //资产类型
        if(null == form.getAssetType()){
            return "产品类型不能为空，请重新操作！";
        }
//    	是否关联计划
//    	ValidatorFieldCheckUtil.validateRequired(modelAndView, "isAssociatePlan", form.getIsAssociatePlan().toString());
        //自动录标
        if(null == form.getAutoAdd()){
            return "自动录标不能为空，请重新操作！";
        }
        //自动备案
        if(null == form.getAutoRecord()){
            return "自动备案不能为空，请重新操作！";
        }
        //自动保证金
//        if(null == form.getAutoBail()){
//            return "自动保证金不能为空，请重新操作！";
//        }
        //自动初审
        if(null == form.getAutoAudit()){
            return "自动初审不能为空，请重新操作！";
        }
        //自动复审
        if(null == form.getAutoReview()){
            return "自动复审不能为空，请重新操作！";
        }
        //是否开启
        if(null == form.getIsOpen()){
            return "是否开启不能为空，请重新操作！";
        }
        // 检查唯一性
        int cnt = this.borrowFlowService.countRecordByPK(form.getInstCode(), form.getAssetType());
        if (cnt > 0) {
            return "相同资产来源，产品类型的流程配置已经存在，请重新操作！";
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
    public AdminResult assetTypeAction(@RequestBody AdminBorrowFlowRequest request) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        if(StringUtils.isBlank(request.getInstCode())){
            return new AdminResult<>(Response.FAIL,"资产来源不能为空，请重新操作！") ;
        }
        // 根据资金来源取得产品类型
        List<HjhAssetTypeVO> hjhAssetTypeList = this.borrowFlowService.hjhAssetTypeList(request.getInstCode());
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
