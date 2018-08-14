package com.hyjf.admin.controller.manager;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.FinmanChargeNewService;
import com.hyjf.admin.utils.ValidatorFieldCheckUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.FinmanChargeNewResponse;
import com.hyjf.am.resquest.admin.FinmanChargeNewRequest;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CustomConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author xiehuili on 2018/8/13.
 */
@Api(value = "配置中心流程配置--费率配置",tags ="配置中心流程配置--费率配置")
@RestController
@RequestMapping("/hyjf-admin/config/finmanchargenew")
public class FinmanChargeNewController extends BaseController {

    //权限名称
    private static final String PERMISSIONS = "finmanchargenew";

    @Autowired
    private FinmanChargeNewService finmanChargeNewService;
    @ApiOperation(value = "查询费率配置", notes = "查询费率配置")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult selectFinmanChargeList( @RequestBody FinmanChargeNewRequest adminRequest) {
        FinmanChargeNewResponse response =new FinmanChargeNewResponse();
        // 汇直投项目列表
        List<BorrowProjectTypeVO> borrowProjectTypeList = this.finmanChargeNewService.borrowProjectTypeList("HZT");
        response.setBorrowProjectTypeList(borrowProjectTypeList);
        List<ParamNameVO> paramNameVOS = this.finmanChargeNewService.getParamNameList(CustomConstants.ENDDAY_MONTH);
        response.setParamNames(paramNameVOS);
        FinmanChargeNewResponse resList=finmanChargeNewService.selectFinmanChargeList(adminRequest);
        if (resList!=null &&Response.isSuccess(resList)) {
            response.setResultList(resList.getResultList());
        }
        return new AdminResult<FinmanChargeNewResponse>(response) ;
    }

    @ApiOperation(value = "检索费率配置", notes = "检索费率配置")
    @PostMapping("/searchAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult getFinmanChargeList(@RequestBody FinmanChargeNewRequest adminRequest) {
        FinmanChargeNewResponse response =new FinmanChargeNewResponse();
        // 汇直投项目列表
        List<BorrowProjectTypeVO> borrowProjectTypeList = this.finmanChargeNewService.borrowProjectTypeList("HZT");
        response.setBorrowProjectTypeList(borrowProjectTypeList);
        List<ParamNameVO> paramNameVOS = this.finmanChargeNewService.getParamNameList(CustomConstants.ENDDAY_MONTH);
        response.setParamNames(paramNameVOS);
        FinmanChargeNewResponse resList=finmanChargeNewService.selectFinmanChargeList(adminRequest);
        if (resList!=null &&Response.isSuccess(resList)) {
            response.setResultList(resList.getResultList());
        }
        return new AdminResult<FinmanChargeNewResponse>(response) ;
    }

    @ApiOperation(value = "查询费率配置详情", notes = "查询费率配置详情 ")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminResult finmanChargeInfo(@RequestBody FinmanChargeNewRequest adminRequest) {
        FinmanChargeNewResponse response = new FinmanChargeNewResponse();

//        BorrowFinmanNewChargeVO record = new BorrowFinmanNewChargeVO();
//        record.setManChargeTimeType("month");
        if (StringUtils.isNotEmpty(adminRequest.getManChargeCd())) {
            response = this.finmanChargeNewService.getRecordInfo(adminRequest.getManChargeCd());
        }
        // 汇直投项目列表
        List<BorrowProjectTypeVO> borrowProjectTypeList = this.finmanChargeNewService.borrowProjectTypeList("HZT");
        response.setBorrowProjectTypeList(borrowProjectTypeList);
        List<ParamNameVO> paramNameVOS = this.finmanChargeNewService.getParamNameList(CustomConstants.ENDDAY_MONTH);
        response.setParamNames(paramNameVOS);
        // 资金来源
        List<HjhInstConfigVO> hjhInstConfigList = this.finmanChargeNewService.hjhInstConfigList("");
        response.setHjhInstConfigList(hjhInstConfigList);
        // 产品类型
        List<HjhAssetTypeVO> assetTypeList = this.finmanChargeNewService.hjhAssetTypeList(response.getResult().getInstCode());
        response.setAssetTypeList(assetTypeList);
        return new AdminResult<FinmanChargeNewResponse>(response) ;
    }

    @ApiOperation(value = "添加费率配置", notes = "添加费率配置")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertFinmanCharge(@RequestBody FinmanChargeNewRequest adminRequest){
        FinmanChargeNewResponse response = new FinmanChargeNewResponse();
        BorrowProjectTypeVO borrowProjectTypeVO = new BorrowProjectTypeVO();
        // 表单校验(双表校验)
        ModelAndView model = new ModelAndView();
        //表单字段校验
        this.validatorFieldCheck(model, adminRequest);
        if (ValidatorFieldCheckUtil.hasValidateError(model)) {
            // 汇直投项目列表
            List<BorrowProjectTypeVO> borrowProjectTypeList = this.finmanChargeNewService.borrowProjectTypeList("HZT");
            response.setBorrowProjectTypeList(borrowProjectTypeList);
            List<ParamNameVO> paramNameVOS = this.finmanChargeNewService.getParamNameList(CustomConstants.ENDDAY_MONTH);
            response.setParamNames(paramNameVOS);
            // 资金来源
            List<HjhInstConfigVO> hjhInstConfigList = this.finmanChargeNewService.hjhInstConfigList("");
            response.setHjhInstConfigList(hjhInstConfigList);
            // 产品类型
            List<HjhAssetTypeVO> assetTypeList = this.finmanChargeNewService.hjhAssetTypeList(response.getResult().getInstCode());
            response.setAssetTypeList(assetTypeList);
            response.setRtn(Response.FAIL);
            response.setMessage("输入内容验证失败");
            return new AdminResult<FinmanChargeNewResponse>(response);
        }
//        // 插入(双表插入)
//        int cot = this.finmanChargeNewService.insertRecord(adminRequest);
//        // added by libin 当变更成功后(插入记录数>0)，操作日志表
//        if( cot > 0){
//            response= this.finmanChargeNewService.insertLogRecord(adminRequest);
//        }
        //插入
        response= this.finmanChargeNewService.insertRecord(adminRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>();
    }
    @ApiOperation(value = "修改费率配置", notes = "修改费率配置")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult updateFinmanCharge(@RequestBody FinmanChargeNewRequest adminRequest){
        //修改费率 配置（双表）
        FinmanChargeNewResponse response= this.finmanChargeNewService.updateRecord(adminRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>();
    }
    @ApiOperation(value = "删除费率配置", notes = "删除费率配置")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult deleteFinmanCharge(@RequestBody FinmanChargeNewRequest adminRequest){
        //删除费率 配置（双表）
        FinmanChargeNewResponse response= this.finmanChargeNewService.deleteRecord(adminRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>();
    }

    /**
     * 画面校验
     *
     * @param modelAndView
     * @param form
     */
    private void validatorFieldCheck(ModelAndView modelAndView, FinmanChargeNewRequest form) {
        // 类型
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "chargeTimeType", form.getManChargeTimeType());
        // 期限
        ValidatorFieldCheckUtil
                .validateRequired(modelAndView, "manChargeTime", String.valueOf(form.getManChargeTime()));
        //项目类型
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "projectType", form.getProjectType());
        //资产来源
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "instCode", form.getInstCode());
        //产品类型
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "assetType", String.valueOf(form.getAssetType()));
        //服务费率
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "chargeRate", form.getChargeRate());
        // 管理费率
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "manChargeRate", form.getManChargeRate());
        // 收益差率
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "returnRate", form.getReturnRate());
        // 逾期利率
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "lateInterest", form.getLateInterest());
        // 逾期免息天数
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "lateFreeDays", String.valueOf(form.getLateFreeDays()));
        // 状态
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "status", String.valueOf(form.getStatus()));
        // 检查唯一性
        int cnt =
                this.finmanChargeNewService.countRecordByProjectType(form.getManChargeTimeType(),
                        form.getManChargeTime(), form.getInstCode(),form.getAssetType());
        if (cnt > 0) {
            ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "instCode", "mancharge.time.type.repeat");
        }
        // added by libin
        // 检查费率配置Log表的唯一性(by: inst_code资产来源, asset_type产品类型,borrow_period借款期限 定位一条记录)
/*        int count = this.finmanChargeNewService.countRecordByItems(form.getManChargeTime(), form.getInstCode(),form.getAssetType());
        if (count > 0) {
            ValidatorFieldCheckUtil.validateSpecialError(modelAndView, "instCode", "mancharge.time.type.repeat");
        }*/
    }

}
