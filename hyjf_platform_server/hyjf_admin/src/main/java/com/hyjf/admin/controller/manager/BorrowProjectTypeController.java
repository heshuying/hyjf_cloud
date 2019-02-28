package com.hyjf.admin.controller.manager;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowProjectTypeService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BorrowProjectTypeResponse;
import com.hyjf.am.resquest.trade.BorrowProjectTypeRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectRepayVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.common.util.CustomConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/27.
 */
@Api(tags = "配置中心-借款项目配置---项目流程的项目类型")
@RestController
@RequestMapping("/hyjf-admin/config/projecttype")
public class BorrowProjectTypeController extends BaseController {

    //权限名称
    private static final String PERMISSIONS = "projecttype";

    @Autowired
    private BorrowProjectTypeService borrowProjectTypeService;

    @ApiOperation(value = "查询项目类型", notes = "查询项目类型")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult selectProjectTypeList(HttpServletRequest request, @RequestBody BorrowProjectTypeRequest adminRequest) {
        BorrowProjectTypeResponse resList=borrowProjectTypeService.selectProjectTypeList(adminRequest);
        if(resList==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(resList)) {
            return new AdminResult<>(FAIL, resList.getMessage());

        }
        return new AdminResult<ListResult<BorrowProjectTypeVO>>(ListResult.build(resList.getResultList(), resList.getRecordTotal())) ;
    }

    @ApiOperation(value = "查询项目类型详情", notes = "查询项目类型详情 ")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult subConfigSearch(HttpServletRequest request, @RequestBody BorrowProjectTypeRequest adminRequest) {
        BorrowProjectTypeResponse response = new BorrowProjectTypeResponse();
        BorrowProjectTypeVO record = new BorrowProjectTypeVO();
        List<BorrowProjectRepayVO> selectRepay = null;
        record.setBorrowCd(adminRequest.getBorrowCd());
        boolean isExists = false;
        if (record.getBorrowCd() != null) {
            // 根据主键判断该条数据在数据库中是否存在
            isExists = this.borrowProjectTypeService.isExistsRecord(record);
        }
        if (isExists) {
            // 根据主键检索数据
            record = this.borrowProjectTypeService.getRecord(record);
            //优惠券类型转换
           if(record.getInterestCoupon() != null){

               if(record.getInterestCoupon() == 1&&record.getTasteMoney() != 1){
                   record.setCoupon("0");
               }
               if(record.getInterestCoupon() == 0&&record.getTasteMoney() == 1){
                   record.setCoupon("1");
               }
               if(record.getInterestCoupon() == 1&&record.getTasteMoney() == 1){
                   record.setCoupon("0,1");
               }
           }
            record.setModifyFlag("E");
            // 根据项目编号查询
            selectRepay = this.borrowProjectTypeService.selectRepay(record.getBorrowClass());
        } else {
            record.setModifyFlag("N");
        }
//        record.setMethodName(adminRequest.getMethodName());
        record.setRepayNames(selectRepay);
        // 回显checkbox标签
        List<BorrowStyleVO> selectStyles = this.borrowProjectTypeService.selectStyles();
        record.setRepayStyles(selectStyles);
        // 用户角色
        List<ParamNameVO> investUsers = this.borrowProjectTypeService.getParamNameList("INVEST_USER");
        record.setInvestUsers(investUsers);
        List<ParamNameVO> projectTypeList =  this.borrowProjectTypeService.getParamNameList(CustomConstants.BORROW_PROJTCT);
        record.setProjectTypeList(projectTypeList);
        response.setResult(record);
        return new AdminResult<BorrowProjectTypeResponse>(response) ;
    }

    @ApiOperation(value = "项目类型添加", notes = "项目类型添加")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertSubConfig(HttpServletRequest request,  @RequestBody BorrowProjectTypeRequest adminRequest) {
        AdminResult result =new AdminResult();
        BorrowProjectTypeResponse response = new BorrowProjectTypeResponse();
        if(adminRequest.getBorrowCd() == null ){
            response.setRtn(Response.FAIL);
            response.setMessage("项目名称不能为空，请重试。");
            result.setData(response);
            result.setStatus(BaseResult.FAIL);
            result.setStatusDesc(BaseResult.FAIL_DESC);
            return result;
        }
        if(adminRequest.getBorrowCd() != null ){
            int bo=Integer.valueOf(adminRequest.getBorrowCd());
            if(bo>127||bo<0){
                response.setRtn(Response.FAIL);
                response.setMessage("项目名称要小于128，请重试。");
                result.setData(response);
                result.setStatus(BaseResult.FAIL);
                result.setStatusDesc(BaseResult.FAIL_DESC);
                return result;
            }
        }
        AdminSystemVO user = getUser(request);
        adminRequest.setCreateUserId(user.getId());
//        adminRequest.setCreateUserId("3");
        //优惠券类型转换
        if(StringUtils.isNotBlank(adminRequest.getCoupon())&&adminRequest.getCoupon().equals("0")){
            adminRequest.setInterestCoupon(1);
            adminRequest.setTasteMoney(0);
        }
        if(StringUtils.isNotBlank(adminRequest.getCoupon())&&adminRequest.getCoupon().equals("1")){
            adminRequest.setInterestCoupon(0);
            adminRequest.setTasteMoney(1);
        }
        if(StringUtils.isNotBlank(adminRequest.getCoupon())&&adminRequest.getCoupon().equals("0,1")){
            adminRequest.setInterestCoupon(1);
            adminRequest.setTasteMoney(1);
        }
        BorrowProjectTypeVO borrowProjectTypeVO = new BorrowProjectTypeVO();
        // 表单校验(双表校验)
        ModelAndView model = new ModelAndView();
        //表单字段校验
        String message =  this.validatorFieldCheck(model, adminRequest);
        if (StringUtils.isNotBlank(message)) {
            List<BorrowProjectRepayVO> selectRepay = new ArrayList<BorrowProjectRepayVO>();
            if (StringUtils.isNotEmpty(adminRequest.getMethodName())) {
                String name[] = adminRequest.getMethodName().split(",");
                if (name != null && name.length > 0) {
                    for (int i = 0; i < name.length; i++) {
                        BorrowProjectRepayVO borrowProjectRepay = new BorrowProjectRepayVO();
                        borrowProjectRepay.setRepayMethod(name[i]);
                        selectRepay.add(borrowProjectRepay);
                    }
                }
            }
            borrowProjectTypeVO.setRepayNames(selectRepay);
            // 用户角色
            List<ParamNameVO> investUsers = this.borrowProjectTypeService.getParamNameList("INVEST_USER");
            borrowProjectTypeVO.setInvestUsers(investUsers);
            // 回显checkbox标签
            List<BorrowStyleVO> selectStyles = this.borrowProjectTypeService.selectStyles();
            borrowProjectTypeVO.setRepayStyles(selectStyles);
            response.setResult(borrowProjectTypeVO);
            BeanUtils.copyProperties(adminRequest,borrowProjectTypeVO);
            response.setRtn(Response.FAIL);
            response.setMessage(message);
            result.setData(response);
            result.setStatus(BaseResult.FAIL);
            result.setStatusDesc(BaseResult.FAIL_DESC);
            return result;
        }
        if (StringUtils.equals("N", adminRequest.getModifyFlag())) {
            response = this.borrowProjectTypeService.insertRecord(adminRequest);
            return new AdminResult<BorrowProjectTypeResponse>(response);
        }
        response.setRtn(Response.FAIL);
        response.setMessage("modifyFlag 等于N，才能添加！");
        return new AdminResult<BorrowProjectTypeResponse>(response);
    }

    @ApiOperation(value = "项目类型修改", notes = "项目类型修改")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateAction(HttpServletRequest request,  @RequestBody BorrowProjectTypeRequest adminRequest) {
        AdminSystemVO user = getUser(request);
        adminRequest.setUpdateUserId(user.getId());
//        adminRequest.setUpdateUserId(String.valueOf(3));
        //优惠券类型转换
        if(StringUtils.isNotBlank(adminRequest.getCoupon())&&adminRequest.getCoupon().equals("0")){
            adminRequest.setInterestCoupon(1);
            adminRequest.setTasteMoney(0);
        }
        if(StringUtils.isNotBlank(adminRequest.getCoupon())&&adminRequest.getCoupon().equals("1")){
            adminRequest.setInterestCoupon(0);
            adminRequest.setTasteMoney(1);
        }
        if(StringUtils.isNotBlank(adminRequest.getCoupon())&&adminRequest.getCoupon().equals("0,1")){
            adminRequest.setInterestCoupon(1);
            adminRequest.setTasteMoney(1);
        }
        BorrowProjectTypeResponse response = null;
        BorrowProjectTypeVO borrowProjectTypeVO = new BorrowProjectTypeVO();
        // 表单校验(双表校验)
        ModelAndView model = new ModelAndView();
        //表单字段校验
        String message = this.validatorFieldCheck(model, adminRequest);
        if (StringUtils.isNotBlank(message)) {
            response =  new BorrowProjectTypeResponse();
            List<BorrowProjectRepayVO> selectRepay = new ArrayList<>();
            if (StringUtils.isNotEmpty(adminRequest.getMethodName())) {
                String name[] = adminRequest.getMethodName().split(",");
                if (name != null && name.length > 0) {
                    for (int i = 0; i < name.length; i++) {
                        BorrowProjectRepayVO borrowProjectRepay = new BorrowProjectRepayVO();
                        borrowProjectRepay.setRepayMethod(name[i]);
                        selectRepay.add(borrowProjectRepay);
                    }
                }
            }
            borrowProjectTypeVO.setRepayNames(selectRepay);
            // 用户角色
            List<ParamNameVO> investUsers = this.borrowProjectTypeService.getParamNameList("INVEST_USER");
            borrowProjectTypeVO.setInvestUsers(investUsers);
            // 回显checkbox标签
            List<BorrowStyleVO> selectStyles = this.borrowProjectTypeService.selectStyles();
            borrowProjectTypeVO.setRepayStyles(selectStyles);
            BeanUtils.copyProperties(adminRequest,borrowProjectTypeVO);
            response.setRtn(Response.FAIL);
            response.setMessage(message);
            response.setResult(borrowProjectTypeVO);
            return new AdminResult<BorrowProjectTypeResponse>(response);
        }
        if (adminRequest.getBorrowCd() != null ) {
            response = this.borrowProjectTypeService.updateRecord(adminRequest);
        }
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        response=borrowProjectTypeService.selectProjectTypeList(adminRequest);
        return new AdminResult<BorrowProjectTypeResponse>(response);
    }


    @ApiOperation(value = "项目类型删除", notes = "项目类型删除")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteAction(HttpServletRequest request,  @RequestBody BorrowProjectTypeRequest adminRequest) {
        BorrowProjectTypeResponse response = null;
        if(adminRequest.getBorrowCd() != null){
            response= borrowProjectTypeService.deleteProjectType(adminRequest);
        }
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>();
    }

    /**
     * 检查手机号码或用户名唯一性
     *
     * @param adminRequest
     * @return
     */
    @ApiOperation(value = "项目类型校验", notes = "项目类型校验")
    @PostMapping("/checkAction")
//    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult checkAction(@RequestBody BorrowProjectTypeRequest adminRequest) {
        Integer borrowCd =adminRequest.getBorrowCd();
        if(borrowCd == null ){
            return new AdminResult<String>("项目名称不能为空，请重试。");
        }
        // 检查项目名称唯一性
        BorrowProjectTypeRequest form =new BorrowProjectTypeRequest();
        form.setBorrowCd(borrowCd);
        int cnt = this.borrowProjectTypeService.borrowCdIsExists(form);
        if (cnt > 0) {
            return new AdminResult<String>("项目编号重复。");
        }
        // 没有错误时,返回y
        return new AdminResult<String>("y");
    }
    @ApiOperation(value = "查询项目类型下拉框", notes = "查询项目类型下拉框 ")
    @PostMapping("/infoInit")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult subConfigSelect(HttpServletRequest request, @RequestBody BorrowProjectTypeRequest adminRequest) {
        BorrowProjectTypeResponse response = new BorrowProjectTypeResponse();
        BorrowProjectTypeVO record = new BorrowProjectTypeVO();
        // 回显checkbox标签
        List<BorrowStyleVO> selectStyles = this.borrowProjectTypeService.selectStyles();
        record.setRepayStyles(selectStyles);
        // 用户角色
        List<ParamNameVO> investUsers = this.borrowProjectTypeService.getParamNameList("INVEST_USER");
        record.setInvestUsers(investUsers);
        List<ParamNameVO> projectTypeList =  this.borrowProjectTypeService.getParamNameList(CustomConstants.BORROW_PROJTCT);
        record.setProjectTypeList(projectTypeList);
        response.setResult(record);
        return new AdminResult<BorrowProjectTypeResponse>(response) ;
    }
    /**
     * 画面校验
     *
     * @param modelAndView
     * @param form
     */
    private String validatorFieldCheck(ModelAndView modelAndView, BorrowProjectTypeRequest form) {
        String message="";
        // 项目类型
        if(form.getBorrowCd() == null ){
            message="项目类型不能为空,请重试。";
            return message;
        }
        // 项目编号
        if(StringUtils.isBlank(form.getBorrowClass())|| (StringUtils.isNotBlank(form.getBorrowClass())&&form.getBorrowClass().length()>20)){
            message="项目编号不为空且最大长度小于20,请重试。";
            return message;
        }
        // 权限名字
        if(StringUtils.isBlank(form.getBorrowName())|| (StringUtils.isNotBlank(form.getBorrowName())&&form.getBorrowName().length()>20)){
            message="权限名字不为空且最大长度小于20,请重试。";
            return message;
        }
        // 用户类型
        if(StringUtils.isBlank(form.getInvestUserType())){
            message="用户类型不能为空,请重试。";
            return message;
        }
        // 状态
        if(StringUtils.isBlank(form.getStatus())){
            message="状态不能为空,请重试。";
            return message;
        }
        // 还款方式
        if(StringUtils.isBlank(form.getMethodName())){
            message="还款方式不能为空,请重试。";
            return message;
        }
        // 出借最小范围
        if(StringUtils.isBlank(form.getInvestStart())|| (StringUtils.isNotBlank(form.getInvestStart())&&form.getInvestStart().length()>10)){
            message="出借最小范围不能为空且最大长度为10位,请重试。";
            return message;
        }
        // 出借最大范围
        if(StringUtils.isBlank(form.getInvestEnd())|| (StringUtils.isNotBlank(form.getInvestEnd())&&form.getInvestEnd().length()>10)){
            message="出借最大范围不能为空且最大长度为10位,请重试。";
            return message;
        }
        if ("N".equals(form.getModifyFlag())) {
            // 检查唯一性
            int cnt = this.borrowProjectTypeService.borrowCdIsExists(form);
            if (cnt > 0) {
                message="项目类型重复了,请重试。";
            }
        }
        return message;
    }

}
