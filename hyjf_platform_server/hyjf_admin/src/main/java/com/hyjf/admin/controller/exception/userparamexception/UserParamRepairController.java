/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.exception.userparamexception;

import com.hyjf.admin.beans.request.UserManagerRequestBean;
import com.hyjf.admin.beans.response.UserParamExceptInitResponseBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.beans.vo.UserManagerCustomizeVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.UserCenterService;
import com.hyjf.admin.service.UserParamExceptionService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.UserManagerResponse;
import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.user.UserManagerVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: nxl
 * @version: UserParamExceptionController, v0.1 2018/7/2 10:04
 * 后台管理系统，异常中心->会员属性异常
 */
@RestController
@RequestMapping("/hyjf-admin/exception/userparamrepair")
@Api(value = "异常中心-用户属性异常", tags = "异常中心-用户属性异常")
public class UserParamRepairController extends BaseController {

    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private UserParamExceptionService userParamExceptionService;

    public static final String PERMISSIONS = "userparamexception";

    //userParamExceptionInit
    @ApiOperation(value = "用户属性异常页面初始化(下拉列表)", notes = "用户属性异常初始化")
    @PostMapping(value = "/userParamInit")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<UserParamExceptInitResponseBean> userParamExceptionInit() {
        UserParamExceptInitResponseBean responseBean = new UserParamExceptInitResponseBean();
        // 用户角色
        Map<String, String> userRoles = CacheUtil.getParamNameMap("USER_ROLE");
        // 用户属性
        Map<String, String> userPropertys = CacheUtil.getParamNameMap("USER_PROPERTY");
        // 开户状态
        Map<String, String> accountStatus = CacheUtil.getParamNameMap("ACCOUNT_STATUS");
        // 用户状态
        Map<String, String> userStatus = CacheUtil.getParamNameMap("USER_STATUS");
        List<DropDownVO> lsitUserRoles = ConvertUtils.convertParamMapToDropDown(userRoles);
        List<DropDownVO> lsitUserPropertys = ConvertUtils.convertParamMapToDropDown(userPropertys);
        List<DropDownVO> lsitAccountStatus = ConvertUtils.convertParamMapToDropDown(accountStatus);
        List<DropDownVO> lsitUserStatus = ConvertUtils.convertParamMapToDropDown(userStatus);
        responseBean.setUserRoles(lsitUserRoles);
        responseBean.setUserPropertys(lsitUserPropertys);
        responseBean.setAccountStatus(lsitAccountStatus);
        responseBean.setUserStatus(lsitUserStatus);
        return new AdminResult<UserParamExceptInitResponseBean>(responseBean);
    }

    //会员管理列表查询
    @ApiOperation(value = "用户属性异常列表查询", notes = "用户属性异常列表查询")
    @PostMapping(value = "/userparamlist")
    @ResponseBody
    public AdminResult<ListResult<UserManagerCustomizeVO>> getUserslist(@RequestBody UserManagerRequestBean userManagerRequestBean) {
        UserManagerRequest managerRequest = new UserManagerRequest();
        BeanUtils.copyProperties(userManagerRequestBean, managerRequest);
        UserManagerResponse userManagerResponse = userCenterService.selectUserMemberList(managerRequest);
        if (userManagerResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        List<UserManagerVO> listUserManagetVO = userManagerResponse.getResultList();
        List<UserManagerCustomizeVO> userManagerCustomizeList = new ArrayList<UserManagerCustomizeVO>();
        if (null != listUserManagetVO && listUserManagetVO.size() > 0) {
            userManagerCustomizeList = CommonUtils.convertBeanList(listUserManagetVO, UserManagerCustomizeVO.class);
        }
        if (!Response.isSuccess(userManagerResponse)) {
            return new AdminResult<>(FAIL, userManagerResponse.getMessage());
        }
        return new AdminResult<ListResult<UserManagerCustomizeVO>>(ListResult.build(userManagerCustomizeList, userManagerResponse.getCount()));
    }

    @ApiOperation(value = "更新用户属性", notes = "更新用户属性")
    @PostMapping(value = "/updateUserParam")
    @ResponseBody
    public AdminResult updateUserParam(@RequestBody String strUserId) {
        if (Validator.isNull(strUserId) || !Validator.isNumber(strUserId)) {
            return new AdminResult<>(FAIL, "用户ID不正确");
        }
        int userId = Integer.parseInt(strUserId);
        userParamExceptionService.updateUserParam(userId);
        return new AdminResult<>();
    }


    // 判断一轮定时是否跑完,如果没跑完,不允许再跑一个
    private static Boolean isOver = true;

    /**
     * bug号15597  对应,注释掉更新全部和出借数据修复
     */
   /* @ApiOperation(value = "更新全部用户属性", notes = "更新全部用户属性")
    @PostMapping(value = "/updateAllUserParam")
    @ResponseBody
    public AdminResult updateAllUserParam() {
        if (isOver) {
            isOver = false;
            {
                List<UserVO> userVOList = userParamExceptionService.getAllUser();
                if (null != userVOList && userVOList.size() > 0) {
                    for (UserVO userVO : userVOList) {
                        userParamExceptionService.updateUserParam(userVO.getUserId());
                    }
                }
            }
            isOver = true;
        } else {
            return new AdminResult<>(FAIL, "已经在进行处理,请勿重复操作");
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "出借数据修复", notes = "出借数据修复")
    @PostMapping(value = "/tenderRepairAction")
    @ResponseBody
    public AdminResult tenderRepairAction(@RequestParam (value = "repairStartDate")  String repairStartDate, @RequestParam (value = "repairStartDate")  String repairEndDate) {
        List<BorrowTenderVO> borrowTenderVOList = userParamExceptionService.selectBorrowTenderListByDate(repairStartDate, repairEndDate);
        if (null != borrowTenderVOList && borrowTenderVOList.size() > 0) {
            for (BorrowTenderVO borrowTenderVO : borrowTenderVOList) {
                userParamExceptionService.updateUserTender(borrowTenderVO, repairStartDate, repairEndDate);
            }
        }
        return new AdminResult<>();
    }
*/
}
