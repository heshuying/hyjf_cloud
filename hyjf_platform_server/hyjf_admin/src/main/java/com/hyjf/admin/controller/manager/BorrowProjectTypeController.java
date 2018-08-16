package com.hyjf.admin.controller.manager;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowProjectTypeService;
import com.hyjf.admin.utils.ValidatorFieldCheckUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BorrowProjectTypeResponse;
import com.hyjf.am.resquest.trade.BorrowProjectTypeRequest;
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
@Api(tags = "配置中心借款项目配置---项目流程的项目类型")
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
        return new AdminResult<ListResult<BorrowProjectTypeVO>>(ListResult.build(resList.getResultList(), resList.getResultList().size())) ;
    }
    @ApiOperation(value = "查询项目类型详情", notes = "查询项目类型详情 ")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public BorrowProjectTypeResponse subConfigSearch(HttpServletRequest request, @RequestBody BorrowProjectTypeRequest adminRequest) {
        BorrowProjectTypeResponse response = new BorrowProjectTypeResponse();
        BorrowProjectTypeVO record = new BorrowProjectTypeVO();
        List<BorrowProjectRepayVO> selectRepay = null;
        record.setBorrowCd(adminRequest.getBorrowCd());
        boolean isExists = false;
        if (StringUtils.isNotEmpty(record.getBorrowCd())) {
            // 根据主键判断该条数据在数据库中是否存在
            isExists = this.borrowProjectTypeService.isExistsRecord(record);
        }
        if (isExists) {
            record.setModifyFlag("E");
            // 根据主键检索数据
            record = this.borrowProjectTypeService.getRecord(record);
            // 根据项目编号查询
            selectRepay = this.borrowProjectTypeService.selectRepay(record.getBorrowClass());
        } else {
            record.setModifyFlag("N");
        }
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
        return response ;
    }

    @ApiOperation(value = "项目类型添加", notes = "项目类型添加")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public BorrowProjectTypeResponse insertSubConfig(HttpServletRequest request,  @RequestBody BorrowProjectTypeRequest adminRequest) {
        BorrowProjectTypeResponse response = new BorrowProjectTypeResponse();
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
            response.setMessage("输入内容验证失败");
            return response;
        }
        if (StringUtils.equals("N", adminRequest.getModifyFlag())) {
            response = this.borrowProjectTypeService.insertRecord(adminRequest);
        }
        return response;
    }
    @ApiOperation(value = "项目类型修改", notes = "项目类型修改")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_UPDATE)
    public BorrowProjectTypeResponse updateAction(HttpServletRequest request,  @RequestBody BorrowProjectTypeRequest adminRequest) {
        BorrowProjectTypeResponse response =  new BorrowProjectTypeResponse();
        BorrowProjectTypeVO borrowProjectTypeVO = new BorrowProjectTypeVO();
        // 表单校验(双表校验)
        ModelAndView model = new ModelAndView();
        //表单字段校验
        String message = this.validatorFieldCheck(model, adminRequest);
        if (StringUtils.isNotBlank(message)) {
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
            response.setMessage("输入内容验证失败");
            response.setResult(borrowProjectTypeVO);
            return response;
        }
        if (StringUtils.isNotEmpty(adminRequest.getBorrowCd())) {
            this.borrowProjectTypeService.updateRecord(adminRequest);
        }
         response=borrowProjectTypeService.selectProjectTypeList(adminRequest);
        return response;
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
     * @param request
     * @return
     */
    @ApiOperation(value = "项目类型校验", notes = "项目类型校验")
    @PostMapping("/checkAction")
//    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public String checkAction(HttpServletRequest request) {
        String borrowCd = request.getParameter("param");
        JSONObject ret = new JSONObject();
        // 检查项目名称唯一性
        BorrowProjectTypeRequest form =new BorrowProjectTypeRequest();
        form.setBorrowCd(borrowCd);
        int cnt = this.borrowProjectTypeService.borrowCdIsExists(form);
        if (cnt > 0) {
//            String message = ValidatorFieldCheckUtil.getErrorMessage("repeat", "");
            String message = "{label}"+"项目编号重复了";
            ret.put("info", message);
        }
        // 没有错误时,返回y
        if (!ret.containsKey("info")) {
            ret.put("status", "y");
        }
        return ret.toString();
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
        boolean borrowCdFlag = ValidatorFieldCheckUtil.validateRequired(modelAndView, "borrowCd", form.getBorrowCd());
        // 项目编号
        ValidatorFieldCheckUtil.validateAlphaAndMaxLength(modelAndView, "borrowClass", form.getBorrowClass(), 20, true);
        // 权限名字
        ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "borrowName", form.getBorrowName(), 20, true);
        // 用户类型
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "investUserType", form.getInvestUserType());
        // 状态
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "status", form.getStatus());
        // 还款方式
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "methodName", form.getMethodName());
        // 投资最小范围
        ValidatorFieldCheckUtil.validateSignlessNum(modelAndView, "investStart", form.getInvestStart(), 10, true);
        // 投资最大范围
        ValidatorFieldCheckUtil.validateSignlessNum(modelAndView, "investEnd", form.getInvestEnd(), 10, true);

        if ("N".equals(form.getModifyFlag()) && borrowCdFlag) {
            // 检查唯一性
            int cnt = this.borrowProjectTypeService.borrowCdIsExists(form);
            if (cnt > 0) {
                message="borrowCd重复了！";
            }
        }
        return message;
    }

}
