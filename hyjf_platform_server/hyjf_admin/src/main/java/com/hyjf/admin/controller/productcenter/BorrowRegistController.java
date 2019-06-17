/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter;

import com.hyjf.admin.beans.request.BorrowRegistCancelRequestBean;
import com.hyjf.admin.beans.request.BorrowRegistRequestBean;
import com.hyjf.admin.beans.response.BorrowRegistResponseBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.BorrowRegistService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.vo.admin.BorrowRegistCancelConfirmCustomizeVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wangjun
 * @version BorrowRegistController, v0.1 2018/6/29 11:18
 */

@Api(value = "产品中心-汇直投-标的备案", tags = "产品中心-汇直投-标的备案")
@RestController
@RequestMapping("/hyjf-admin/borrow_regist")
public class BorrowRegistController extends BaseController {
    @Autowired
    BorrowRegistService borrowRegistService;

    @Autowired
    AdminCommonService adminCommonService;

    /** 权限 */
    public static final String PERMISSIONS = "borrowRegist";

    /**
     * 标的备案初始化
     *
     * @return
     */
    @ApiOperation(value = "标的备案初始化", notes = "标的备案初始化")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
    public AdminResult<BorrowRegistResponseBean> init() {
        BorrowRegistResponseBean responseBean = new BorrowRegistResponseBean();
        //项目类型
        List<BorrowProjectTypeVO> borrowProjectTypeList = borrowRegistService.selectBorrowProjectList();
        responseBean.setBorrowProjectTypeList(ConvertUtils.convertListToDropDown(borrowProjectTypeList,"borrowCd","borrowName"));
        //还款方式
        List<DropDownVO> borrowStyleList = adminCommonService.selectBorrowStyleList();
        responseBean.setBorrowStyleList(borrowStyleList);
        //备案状态
        List<DropDownVO> borrowRegistStatusList = adminCommonService.getParamNameList(CustomConstants.REGIST_STATUS);
        responseBean.setBorrowRegistStatusList(borrowRegistStatusList);

        return new AdminResult(responseBean);
    }

    /**
     * 获取标的备案列表
     *
     * @param borrowRegistRequestBean
     * @return
     */
    @ApiOperation(value = "获取标的备案列表", notes = "获取标的备案列表")
    @PostMapping("/search")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BorrowRegistResponseBean> search(@RequestBody BorrowRegistRequestBean borrowRegistRequestBean) {
        BorrowRegistListRequest borrowRegistListRequest = new BorrowRegistListRequest();
        BeanUtils.copyProperties(borrowRegistRequestBean, borrowRegistListRequest);
        BorrowRegistResponseBean responseBean = borrowRegistService.getRegistList(borrowRegistListRequest);
        return new AdminResult(responseBean);
    }

    /**
     * 标的备案
     *
     * @param request
     * @param borrowNid
     * @return
     */
    @ApiOperation(value = "标的备案", notes = "标的备案")
    @ApiImplicitParam(name = "borrowNid", value = "标的编号", required = true, dataType = "String", paramType = "path")
    @GetMapping("/debt_regist/{borrowNid}")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSIONS_DEBT_REGIST)
    public AdminResult debtRegist(HttpServletRequest request, @PathVariable String borrowNid) {
        AdminSystemVO currUser = getUser(request);
        if(currUser == null){
            return new AdminResult(BaseResult.FAIL, "未获取到当前登录用户信息");
        }
        return borrowRegistService.updateBorrowRegist(borrowNid, currUser.getId(), currUser.getUsername());
    }

    /**
     * 备案撤销
     * @param borrowNid
     * @return
     */
    @ApiOperation(value = "备案撤销", notes = "备案撤销")
    @ApiImplicitParam(name = "borrowNid", value = "标的编号", required = true, dataType = "String", paramType = "path")
    @GetMapping("/debtregist_cancel/{borrowNid}")
    @AuthorityAnnotation(key = "borrow", value = ShiroConstants.PERMISSIONS_DEBT_REGIST)
    public AdminResult registCancel(HttpServletRequest request, @PathVariable String borrowNid){
        AdminSystemVO currUser = getUser(request);
        if(currUser == null){
            return new AdminResult(BaseResult.FAIL, "未获取到当前登录用户信息");
        }
        return borrowRegistService.registCancel(borrowNid, null, null, currUser.getId(), currUser.getUsername());
    }

    /**
     * 异常标的备案撤销
     */
    @ApiOperation(value = "异常标的备案撤销", notes = "异常标的备案撤销")
    @PostMapping("/debtregist_cancel_exception")
    @AuthorityAnnotation(key = "cancelRecord", value = ShiroConstants.PERMISSIONS_DEBT_REGIST)
    public AdminResult registCancelForException(HttpServletRequest request, @RequestBody BorrowRegistCancelRequestBean requestBean){
        AdminSystemVO currUser = getUser(request);
        if(currUser == null){
            return new AdminResult(BaseResult.FAIL, "未获取到当前登录用户信息");
        }
        if(StringUtils.isBlank(requestBean.getBorrowNid())){
            return new AdminResult(BaseResult.FAIL, "请求参数错误");
        }
        return borrowRegistService.registCancel(requestBean.getBorrowNid(),requestBean.getBorrowAccountId(), requestBean.getRaiseDate(), currUser.getId(), currUser.getUsername());
    }

    /**
     * 备案撤销确认页面
     */
    @ApiOperation(value = "备案撤销确认页面", notes = "备案撤销确认页面")
    @GetMapping("/registcancel_confirm/{borrowNid}")
    @AuthorityAnnotation(key = "cancelRecord", value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BorrowRegistCancelConfirmCustomizeVO> registCancelConfirm(HttpServletRequest request, @PathVariable String borrowNid) {
        BorrowRegistCancelConfirmCustomizeVO responseBean = borrowRegistService.selectRegistCancelConfirm(borrowNid);
        if(responseBean == null){
            return new AdminResult(BaseResult.FAIL, "未查询到标的信息");
        }
        return new AdminResult(responseBean);
    }
}
