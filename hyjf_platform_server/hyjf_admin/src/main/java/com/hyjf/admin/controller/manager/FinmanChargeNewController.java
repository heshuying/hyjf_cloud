package com.hyjf.admin.controller.manager;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.FinmanChargeNewService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.FinmanChargeNewResponse;
import com.hyjf.am.resquest.admin.FinmanChargeNewRequest;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowFinmanNewChargeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CustomConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiehuili on 2018/8/13.
 */
@Api(tags ="配置中心-借款项目配置--费率配置")
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
        adminRequest = setManChargeTimeRequest(adminRequest);
        if(null == adminRequest){
            response.setRtn(Response.FAIL);
            response.setMessage("期限不存在，请填写：几天或几个月");
            return new AdminResult<FinmanChargeNewResponse>(response) ;
        }
        // 汇直投项目列表
        List<BorrowProjectTypeVO> borrowProjectTypeList = this.finmanChargeNewService.borrowProjectTypeList("HZT");
        response.setBorrowProjectTypeList(borrowProjectTypeList);
        List<ParamNameVO> paramNameVOS = this.finmanChargeNewService.getParamNameList(CustomConstants.ENDDAY_MONTH);
        response.setParamNames(paramNameVOS);
        FinmanChargeNewResponse resList=finmanChargeNewService.selectFinmanChargeList(adminRequest);
        if (resList!=null &&Response.isSuccess(resList)) {
            response.setResultList(resList.getResultList());
            response.setRecordTotal(resList.getRecordTotal());
        }
        // 资金来源
        List<HjhInstConfigVO> hjhInstConfigList = this.finmanChargeNewService.hjhInstConfigList("");
        response.setHjhInstConfigList(hjhInstConfigList);
        // 产品类型
        List<HjhAssetTypeVO> assetTypeList = this.finmanChargeNewService.hjhAssetTypeList(adminRequest.getInstCodeSrch());
        response.setAssetTypeList(assetTypeList);
        return new AdminResult<FinmanChargeNewResponse>(response) ;
    }

    @ApiOperation(value = "检索费率配置", notes = "检索费率配置")
    @PostMapping("/searchAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
    public AdminResult getFinmanChargeList(@RequestBody FinmanChargeNewRequest adminRequest) {
        FinmanChargeNewResponse response =new FinmanChargeNewResponse();
        adminRequest = setManChargeTimeRequest(adminRequest);
        if(null == adminRequest){
            response.setRtn(Response.FAIL);
            response.setMessage("期限不存在，请填写：几天或几个月");
            return new AdminResult<FinmanChargeNewResponse>(response) ;
        }
        // 汇直投项目列表
        List<BorrowProjectTypeVO> borrowProjectTypeList = this.finmanChargeNewService.borrowProjectTypeList("HZT");
        response.setBorrowProjectTypeList(borrowProjectTypeList);
        List<ParamNameVO> paramNameVOS = this.finmanChargeNewService.getParamNameList(CustomConstants.ENDDAY_MONTH);
        response.setParamNames(paramNameVOS);
        FinmanChargeNewResponse resList=finmanChargeNewService.selectFinmanChargeList(adminRequest);
        if (resList!=null &&Response.isSuccess(resList)) {
            response.setResultList(resList.getResultList());
            response.setRecordTotal(resList.getRecordTotal());
        }
        // 资金来源
        List<HjhInstConfigVO> hjhInstConfigList = this.finmanChargeNewService.hjhInstConfigList("");
        response.setHjhInstConfigList(hjhInstConfigList);
        // 产品类型
        List<HjhAssetTypeVO> assetTypeList = this.finmanChargeNewService.hjhAssetTypeList(adminRequest.getInstCodeSrch());
        response.setAssetTypeList(assetTypeList);
        return new AdminResult<FinmanChargeNewResponse>(response) ;
    }
    @ApiOperation(value = "查询费率配置详情", notes = "查询费率配置详情 ")
    @PostMapping("/initAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult initAction(@RequestBody FinmanChargeNewRequest adminRequest) {
        FinmanChargeNewResponse response = new FinmanChargeNewResponse();
        BorrowFinmanNewChargeVO vo= new BorrowFinmanNewChargeVO();
//        if (StringUtils.isNotEmpty(adminRequest.getManChargeCd())) {
//            response = this.finmanChargeNewService.getRecordInfo(adminRequest.getManChargeCd());
//            if(null != response.getResult()){
//                vo =response.getResult();
//            }
//        }
        // 汇直投项目列表
        List<BorrowProjectTypeVO> borrowProjectTypeList = this.finmanChargeNewService.borrowProjectTypeList("HZT");
        response.setBorrowProjectTypeList(borrowProjectTypeList);
        List<ParamNameVO> paramNameVOS = this.finmanChargeNewService.getParamNameList(CustomConstants.ENDDAY_MONTH);
        response.setParamNames(paramNameVOS);
        // 资金来源
        List<HjhInstConfigVO> hjhInstConfigList = this.finmanChargeNewService.hjhInstConfigList("");
        response.setHjhInstConfigList(hjhInstConfigList);
        // 产品类型
        List<HjhAssetTypeVO> assetTypeList = this.finmanChargeNewService.hjhAssetTypeList(vo.getInstCode());
        response.setAssetTypeList(assetTypeList);
        return new AdminResult<FinmanChargeNewResponse>(response) ;
    }

    @ApiOperation(value = "查询费率配置详情", notes = "查询费率配置详情 ")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult finmanChargeInfo(@RequestBody FinmanChargeNewRequest adminRequest) {
        FinmanChargeNewResponse response = new FinmanChargeNewResponse();
        BorrowFinmanNewChargeVO vo= new BorrowFinmanNewChargeVO();
        if (StringUtils.isNotEmpty(adminRequest.getManChargeCd())) {
            response = this.finmanChargeNewService.getRecordInfo(adminRequest.getManChargeCd());
            if(null != response.getResult()){
                vo =response.getResult();
            }
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
        List<HjhAssetTypeVO> assetTypeList = this.finmanChargeNewService.hjhAssetTypeList(vo.getInstCode());
        response.setAssetTypeList(assetTypeList);
        return new AdminResult<FinmanChargeNewResponse>(response) ;
    }

    @ApiOperation(value = "添加费率配置", notes = "添加费率配置")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertFinmanCharge(@RequestBody FinmanChargeNewRequest adminRequest,HttpServletRequest request){
        AdminResult ress= new AdminResult();
        adminRequest.setCreateUserId(Integer.valueOf(this.getUser(request).getId()));
//        adminRequest.setCreateUserId(3);
        FinmanChargeNewResponse response = new FinmanChargeNewResponse();
        //表单字段校验
        String message = this.validatorFieldCheck( adminRequest);
        if (StringUtils.isNotBlank(message)) {
            // 汇直投项目列表
            List<BorrowProjectTypeVO> borrowProjectTypeList = this.finmanChargeNewService.borrowProjectTypeList("HZT");
            response.setBorrowProjectTypeList(borrowProjectTypeList);
            List<ParamNameVO> paramNameVOS = this.finmanChargeNewService.getParamNameList(CustomConstants.ENDDAY_MONTH);
            response.setParamNames(paramNameVOS);
            // 资金来源
            List<HjhInstConfigVO> hjhInstConfigList = this.finmanChargeNewService.hjhInstConfigList("");
            response.setHjhInstConfigList(hjhInstConfigList);
            // 产品类型
            List<HjhAssetTypeVO> assetTypeList = this.finmanChargeNewService.hjhAssetTypeList(adminRequest.getInstCode());
            response.setAssetTypeList(assetTypeList);
            response.setRtn(Response.FAIL);
            response.setMessage(message);
            ress.setStatus(BaseResult.FAIL);
            ress.setStatusDesc(BaseResult.FAIL_DESC);
            ress.setData(response);
            return ress;
        }
        //插入
        response= this.finmanChargeNewService.insertRecord(adminRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return ress;
    }
    @ApiOperation(value = "修改费率配置", notes = "修改费率配置")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateFinmanCharge(@RequestBody FinmanChargeNewRequest adminRequest,HttpServletRequest request){
        adminRequest.setCreateUserId(Integer.valueOf(this.getUser(request).getId()));
//        adminRequest.setCreateUserId(3);
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
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteFinmanCharge(@RequestBody FinmanChargeNewRequest adminRequest,HttpServletRequest request){
        adminRequest.setCreateUserId(Integer.valueOf(this.getUser(request).getId()));
//        adminRequest.setCreateUserId(3);
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
     * 下拉联动
     * @param instCode
     * @return 进入资产列表页面
     */
    @ApiOperation(value = "费率配置--下拉联动", notes = "费率配置--下拉联动")
    @PostMapping("/assetTypeAction/{instCode}")
    public List<Map<String, Object>> assetTypeAction(@PathVariable String instCode) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        // 根据资金来源取得产品类型
        // 产品类型
        List<HjhAssetTypeVO> assetTypeList = this.finmanChargeNewService.hjhAssetTypeList(instCode);
        if (!CollectionUtils.isEmpty(assetTypeList)) {
            for (HjhAssetTypeVO hjhAssetType : assetTypeList) {
                Map<String, Object> mapTemp = new HashMap<String, Object>();
                mapTemp.put("id", hjhAssetType.getAssetType());
                mapTemp.put("text", hjhAssetType.getAssetTypeName());
                resultList.add(mapTemp);
            }
        }
        return resultList;
    }
    /**
     * 画面校验
     *
     * @param form
     */
    private String validatorFieldCheck( FinmanChargeNewRequest form) {
        String message ="";
        // 类型
        if(StringUtils.isBlank(form.getManChargeTimeType())){
            message="类型不能为空！";
            return message;
        }
        // 期限
        if(null == form.getManChargeTime()){
            message="期限不能为空！";
            return message;
        }
        //项目类型
        if(StringUtils.isBlank(form.getProjectType())){
            message="项目类型不能为空！";
            return message;
        }
        //资产来源
        if(StringUtils.isBlank(form.getInstCode())){
            message="资产来源不能为空！";
            return message;
        }
        //产品类型
        if(null == form.getAssetType()){
            message="产品类型不能为空！";
            return message;
        }
        //服务费率
        if(StringUtils.isBlank(form.getChargeRate())){
            message="服务费率不能为空！";
            return message;
        }
        // 管理费率
        if(StringUtils.isBlank(form.getManChargeRate())){
            message="管理费率不能为空！";
            return message;
        }
        // 收益差率
        if(StringUtils.isBlank(form.getReturnRate())){
            message="收益差率不能为空！";
            return message;
        }
        // 逾期利率
        if(StringUtils.isBlank(form.getLateInterest())){
            message="逾期利率不能为空！";
            return message;
        }
        // 逾期免息天数
        if(null == form.getLateFreeDays()){
            message="逾期免息天数不能为空！";
            return message;
        }
        // 状态
        if(null == form.getStatus()){
            message="状态不能为空！";
            return message;
        }
        // 检查唯一性
        int cnt = this.finmanChargeNewService.countRecordByProjectType(form.getManChargeTimeType(), form.getManChargeTime(), form.getInstCode(),form.getAssetType());
        if (cnt > 0) {
            message =  "相同资产来源，产品类型，期限的费率配置已经存在,请重新操作!";
        }
        return message;
    }

    public  FinmanChargeNewRequest setManChargeTimeRequest(FinmanChargeNewRequest adminRequest){
        String manChargeTimeSear = adminRequest.getManChargeTimeSear();
        if(org.apache.commons.lang3.StringUtils.isNotBlank(manChargeTimeSear)){
            manChargeTimeSear = manChargeTimeSear.trim().replaceAll(" ","");
            String manChargeTime = manChargeTimeSear.substring(0,1).matches("^[1-9]\\d*$")?manChargeTimeSear.substring(0,1):null;
            String manChargeTimeType = manChargeTimeSear.substring(manChargeTimeSear.length()-1);
            if(org.apache.commons.lang3.StringUtils.isNotBlank(manChargeTime)&& org.apache.commons.lang3.StringUtils.isNotBlank(manChargeTimeType)&&("月".equals(manChargeTimeType)||"天".equals(manChargeTimeType))){
                if("月".equals(manChargeTimeType)){
                    if(!(manChargeTime+"个月").equals(manChargeTimeSear)){
                        return null;
                    }
                    adminRequest.setManChargeTypeSear("month");
                }
                if("天".equals(manChargeTimeType)){
                    if(!(manChargeTime+"天").equals(manChargeTimeSear)){
                        return null;
                    }
                    adminRequest.setManChargeTypeSear("endday");
                }
                adminRequest.setManChargeTimeSear(manChargeTime);
                return adminRequest;
            }
            return null;
        }
        return adminRequest;
    }

}
