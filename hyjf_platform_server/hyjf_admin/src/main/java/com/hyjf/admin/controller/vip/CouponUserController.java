/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.vip;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.coupon.CouponUserService;
import com.hyjf.admin.utils.AdminValidatorFieldCheckUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminCouponUserCustomizeResponse;
import com.hyjf.am.response.admin.CouponUserCustomizeResponse;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.admin.AdminCouponUserRequestBean;
import com.hyjf.am.resquest.admin.CouponConfigRequest;
import com.hyjf.am.resquest.admin.CouponUserBeanRequest;
import com.hyjf.am.resquest.admin.CouponUserRequest;
import com.hyjf.am.vo.admin.coupon.*;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CustomErrors;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version CouponUserController, v0.1 2018/7/23 15:23
 * 优惠券用户列表
 */
@Api(value = "优惠券用户列表", tags = "优惠券用户列表")
@RestController
@RequestMapping("/hyjf-admin/couponUser")
public class CouponUserController extends BaseController {

    /**
     * 权限名称
     */
    private static final String PERMISSIONS = "couponuser";

    @Value("${coupon.audit.pwd}")
     private String Coupon_AUDIT_PWD;

    @Autowired
    private CouponUserService couponUserService;

    @ApiOperation(value = "优惠券用户列表", notes = "优惠券用户列表页面初始化")
    @PostMapping("/couponUserList")
    @AuthorityAnnotation(key = PERMISSIONS,value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<CouponUserCustomizeVO>> searchCouponUser(CouponUserBeanRequest couponUserBeanRequest) {
        CouponUserCustomizeResponse response = couponUserService.searchList(couponUserBeanRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<ListResult<CouponUserCustomizeVO>>(ListResult.build(response.getResultList(), response.getCount()));
    }

    @ApiOperation(value = "优惠券用户列表", notes = "删除一条优惠券")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS,value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult<CouponUserCustomizeVO> deleteCouponUser(HttpServletRequest request, CouponUserBeanRequest couponUserBeanRequest) {
        AdminSystemVO user = getUser(request);
        String userId = user.getId();
        int id = couponUserBeanRequest.getId();
        String remark = couponUserBeanRequest.getContent();
        CouponUserCustomizeResponse response = couponUserService.deleteById(id, remark, userId);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<CouponUserCustomizeVO>(response.getResult());
    }

    @ApiOperation(value = "优惠券用户列表", notes = "手动发放页面信息")
    @PostMapping("/distributeViewAction")
    @AuthorityAnnotation(key = PERMISSIONS,value = ShiroConstants.PERMISSION_ADD)
    public AdminResult<AdminCouponUserCustomizeVO> distributeViewAction(CouponConfigRequest request) {
        request.setStatus(CustomConstants.COUPON_STATUS_PUBLISHED);
        AdminCouponUserCustomizeResponse response = couponUserService.getRecordList(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<AdminCouponUserCustomizeVO>(response.getResult());
    }


    @ApiOperation(value = "优惠券用户列表", notes = "发放一条优惠券")
    @PostMapping("/distributeAction")
    @AuthorityAnnotation(key = PERMISSIONS,value = ShiroConstants.PERMISSION_ADD)
    public AdminResult distributeAction(CouponUserBeanRequest couponUserBeanRequest, HttpServletRequest request) {
        AdminSystemVO user = getUser(request);
        String loginUserId = null;
        if (user != null) {
            loginUserId = user.getId();
        }
        //校验请求参数
        JSONObject json = new JSONObject();
        json = this.validatorFieldCheck(json, couponUserBeanRequest);
        if (AdminValidatorFieldCheckUtil.hasValidateError(json)) {
            return new AdminResult<>(FAIL, "校验失败");
        }
        //校验数量
        if (couponUserBeanRequest.getAmount() == null || couponUserBeanRequest.getAmount() < 0) {
            couponUserBeanRequest.setAmount(1);
        }
        CouponUserCustomizeResponse response = new CouponUserCustomizeResponse();
        //根据用户名获取用户id
        String userName = couponUserBeanRequest.getUserName();
        UserResponse userResponse = couponUserService.getUser(userName);
        UserVO userVO = userResponse.getResult();
        Integer userId = userVO.getUserId();

        //根据用户id获取用户详情信息
        UserInfoResponse userInfoResponse = couponUserService.getUserInfo(userId);
        UserInfoVO userInfoVO = userInfoResponse.getResult();

        //根据用户id获取注册时渠道名
        UtmResponse utmResponse = couponUserService.getChannelName(userId);
        String channelName = utmResponse.getChannelName();

        //根据优惠券编码查询优惠券
        CouponConfigResponse configResponse = couponUserService.getCouponConfig(couponUserBeanRequest.getCouponCode());
        CouponConfigVO configVO = configResponse.getResult();

        CouponUserRequest couponUserRequest = new CouponUserRequest();
        //截止日
        if (configVO.getExpirationType() == 1) {
            couponUserRequest.setEndTime(configVO.getExpirationDate());
        } else if (configVO.getExpirationType() == 2) {
            Date endDate = GetDate.countDate(GetDate.getDate(), 2, configVO.getExpirationLength());
            couponUserRequest.setEndTime((int) (endDate.getTime() / 1000));
        } else if (configVO.getExpirationType() == 3) {
            Date endDate = GetDate.countDate(GetDate.getDate(), 5, configVO.getExpirationLengthDay());
            couponUserRequest.setEndTime((int) (endDate.getTime() / 1000));
        }
        couponUserRequest.setUserId(userId);
        couponUserRequest.setCouponUserCode(GetCode.getCouponUserCode(configVO.getCouponType()));
        couponUserRequest.setCreateUserId(Integer.parseInt(loginUserId));
        couponUserRequest.setCreateTime(GetDate.getDate());
        couponUserRequest.setUpdateUserId(Integer.parseInt(loginUserId));
        couponUserRequest.setUpdateTime(GetDate.getDate());
        couponUserRequest.setDelFlag(CustomConstants.FALG_NOR);
        couponUserRequest.setUsedFlag(CustomConstants.USER_COUPON_STATUS_WAITING_PUBLISH);
        couponUserRequest.setReadFlag(CustomConstants.USER_COUPON_READ_FLAG_NO);
        if (couponUserRequest.getActivityId() == null) {
            couponUserRequest.setCouponSource(CustomConstants.USER_COUPON_SOURCE_MANUAL);
        } else {
            couponUserRequest.setCouponSource(CustomConstants.USER_COUPON_SOURCE_ACTIVE);
        }
        couponUserRequest.setAttribute(userInfoVO.getAttribute());
        couponUserRequest.setChannel(channelName);
        for (int i = 0; i < couponUserBeanRequest.getAmount(); i++) {
            CouponUserResponse couponUserResponse = couponUserService.insertCouponUser(couponUserRequest);
        }
        return new AdminResult<>();
    }


    @ApiOperation(value = "优惠券用户列表", notes = "用户有效性校验")
    @PostMapping("/checkUserAction")
    public AdminResult checkUserAction(CouponUserBeanRequest request) {
        CouponUserCustomizeResponse response = new CouponUserCustomizeResponse();
        String userName = request.getUserName();
        if (StringUtils.isEmpty(userName)) {
            String message = "用户名不能为空";
            response.setMessage(message);
            return new AdminResult<>(FAIL,FAIL_DESC);
        }
        UserResponse userResponse = couponUserService.getUser(userName);
        if (userResponse.getResult() == null) {
            String message = "用户名不存在";
            response.setMessage(message);
            return new AdminResult<>(FAIL,FAIL_DESC);
        } else if (userResponse.getResult().getStatus() != null && userResponse.getResult().getStatus() == 1) {
            String message = "用户已锁定";
            response.setMessage(message);
            return new AdminResult<>(FAIL,FAIL_DESC);
        }
        response.setRtn(Response.SUCCESS);
        response.setMessage(Response.SUCCESS_MSG);
        return new AdminResult<>(response.getResult());
    }

    @ApiOperation(value = "优惠券用户列表", notes = "校验优惠券是否可用")
    @RequestMapping("/checkCouponValidAction")
    public AdminResult checkCouponValidAction(HttpServletRequest request) {
        CouponUserCustomizeResponse response = new CouponUserCustomizeResponse();
        String couponCode = request.getParameter("couponCode");
        String amount = request.getParameter("amount");
        if (StringUtils.isEmpty(couponCode)) {
            response.setMessage("请求参数不正确");
            return new AdminResult<>(response.getMessage());
        }
        if (amount == null || "".equals(amount)) {
            amount = "0";
        }
        CouponUserResponse couponUserResponse = couponUserService.getCouponUserByCouponCode(couponCode);
        List<CouponUserVO> couponUserVOS = couponUserResponse.getResultList();
        //已发送优惠券的数量
        if (!CollectionUtils.isEmpty(couponUserVOS) && couponUserVOS.size() > 0) {
            int sendedCouponSum = couponUserVOS.size();
            CouponConfigResponse configResponse = couponUserService.getCouponConfig(couponCode);
            CouponConfigVO configVO = configResponse.getResult();
            if (configVO.getCouponQuantity() > sendedCouponSum + Integer.parseInt(amount)) {
                response.setMessage(Response.SUCCESS_MSG);
            } else {
                response.setMessage("优惠券发行数量已用完");
            }
        }
        return new AdminResult<>(response.getMessage());
    }


    @ApiOperation(value = "优惠券用户列表", notes = "加载优惠券配置信息")
    @RequestMapping("/loadCouponConfigAction")
    public AdminResult loadCouponConfigAction(CouponUserBeanRequest request) {
        CouponConfigResponse response = new CouponConfigResponse();
        String couponCode = request.getCouponCode();
        if (StringUtils.isEmpty(couponCode)) {
            response.setRtn(Response.FAIL);
            return new AdminResult<>(response.getRtn());
        }
        CouponConfigResponse configResponse = couponUserService.getCouponConfig(couponCode);
        CouponConfigVO configVO = configResponse.getResult();
        //操作平台
        Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
        String clientString = "";

        //被选中操作平台
        String clientSed[] = StringUtils.split(configVO.getCouponSystem(), ",");
        for (int i = 0; i < clientSed.length; i++) {
            if ("-1".equals(clientSed[i])) {
                clientString = clientString + "所有平台";
                break;
            } else {
                for (String paramName : client.keySet()) {
                    if (clientSed[i].equals(paramName)) {
                        if (i != 0 && clientString.length() != 0) {
                            clientString = clientString + "/";
                        }
                        clientString = clientString + client.get(paramName);
                    }
                }
            }
        }
        configVO.setCouponSystem(clientString);
        String projectString = "";
        //被选中项目类型
        String projectSed[] = StringUtils.split(configVO.getProjectType(), ",");
        if (configVO.getProjectType().indexOf("-1") != -1) {
            projectString = "所有汇直投/汇消费/新手汇/尊享汇/汇添金/汇计划项目";
        } else {
            projectString = projectString + "所有";
            for (String project : projectSed) {
                if ("1".equals(project)) {
                    projectString = projectString + "汇直投/";
                }
                if ("2".equals(project)) {
                    projectString = projectString + "汇消费/";
                }
                if ("3".equals(project)) {
                    projectString = projectString + "新手汇/";
                }
                if ("4".equals(project)) {
                    projectString = projectString + "尊享汇/";
                }
                if ("5".equals(project)) {
                    projectString = projectString + "汇添金/";
                }
                if ("6".equals(project)) {
                    projectString = projectString + "汇计划/";
                }
            }
            projectString = StringUtils.removeEnd(
                    projectString, "/");
            projectString = projectString + "项目";
        }
        configVO.setProjectType(projectString);
        response.setResult(configVO);
        return new AdminResult<>(response.getResult());
    }


    @ApiOperation(value = "优惠券用户列表", notes = "用户优惠券详情页")
    @RequestMapping("/infoAction")
    public AdminResult<ListResult<CouponTenderVo>> infoAction(CouponUserBeanRequest request) {
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        ListResult<CouponTenderVo> result = new ListResult<CouponTenderVo>();
        Integer couponUserId = request.getId();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("couponUserId", couponUserId);
        CouponTenderDetailVo detail = couponUserService.getCouponTenderDetailCustomize(paramMap);
        List<CouponRecoverVO> list = couponUserService.getCouponRecoverCustomize(paramMap);
        //操作平台
        Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
        //被选中操作平台
        String clientString = "";
        String clientSed[] = StringUtils.split(detail.getCouponSystem(), ",");
        for (int i = 0; i < clientSed.length; i++) {
            if ("-1".equals(clientSed[i])) {
                clientString = clientString + "所有平台";
                break;
            } else {
                for (String key : client.keySet()) {
                    if (clientSed[i].equals(key)) {
                        if (i != 0 && clientString.length() != 0) {
                            clientString = clientString + "/";
                        }
                        clientString = clientString + client.get(key);
                    }
                }
            }
        }
        detail.setCouponSystem(clientString);

        //被选中项目类型
        String projectString = "";
        //被选中项目类型
        String projectSed[] = StringUtils.split(detail.getProjectType(), ",");
        if (detail.getProjectType().indexOf("-1") != -1) {
            projectString = "所有汇直投/汇消费/新手汇/尊享汇/汇添金/汇计划项目";
        } else {
            projectString = projectString + "所有";
            for (String project : projectSed) {
                if ("1".equals(project)) {
                    projectString = projectString + "汇直投/";
                }
                if ("2".equals(project)) {
                    projectString = projectString + "汇消费/";
                }
                if ("3".equals(project)) {
                    projectString = projectString + "新手汇/";
                }
                if ("4".equals(project)) {
                    projectString = projectString + "尊享汇/";
                }
                if ("5".equals(project)) {
                    projectString = projectString + "汇添金/";
                }
                if ("6".equals(project)) {
                    projectString = projectString + "汇计划/";
                }
            }
            projectString = StringUtils.removeEnd(
                    projectString, "/");
            projectString = projectString + "项目";
        }
        detail.setProjectType("适用" + projectString);

        couponTenderHztVo.setDetail(detail);
        couponTenderHztVo.setCouponRecoverlist(list);
        return new AdminResult<>(result);
    }

    @ApiOperation(value = "用户优惠券列表",notes = "编辑初始页")
    @RequestMapping("/auditInitAction")
    public AdminResult<ListResult<CouponUserCustomizeVO>> auditInitAction(CouponUserBeanRequest request) {
        CouponUserCustomizeVO customizeVO = new CouponUserCustomizeVO();
        ListResult<CouponUserCustomizeVO> result =  new ListResult<>();
        Integer couponUserId = request.getId();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("couponUserId", couponUserId);
        CouponTenderDetailVo detail = couponUserService.getCouponTenderDetailCustomize(paramMap);
        //操作平台
        Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
        //被选中操作平台
        String clientString = "";
        String clientSed[] = StringUtils.split(detail.getCouponSystem(), ",");
        for (int i = 0; i < clientSed.length; i++) {
            if ("-1".equals(clientSed[i])) {
                clientString = clientString + "所有平台";
                break;
            } else {
                for (String key : client.keySet()) {
                    if (clientSed[i].equals(key)) {
                        if (i != 0 && clientString.length() != 0) {
                            clientString = clientString + "/";
                        }
                        clientString = clientString + client.get(key);
                    }
                }
            }
        }
        detail.setCouponSystem(clientString);

        //被选中项目类型
        String projectString = "";
        //被选中项目类型
        String projectSed[] = StringUtils.split(detail.getProjectType(), ",");
        if (detail.getProjectType().indexOf("-1") != -1) {
            projectString = "所有汇直投/汇消费/新手汇/尊享汇/汇添金/汇计划项目";
        } else {
            projectString = projectString + "所有";
            for (String project : projectSed) {
                if ("1".equals(project)) {
                    projectString = projectString + "汇直投/";
                }
                if ("2".equals(project)) {
                    projectString = projectString + "汇消费/";
                }
                if ("3".equals(project)) {
                    projectString = projectString + "新手汇/";
                }
                if ("4".equals(project)) {
                    projectString = projectString + "尊享汇/";
                }
                if ("5".equals(project)) {
                    projectString = projectString + "汇添金/";
                }
                if ("6".equals(project)) {
                    projectString = projectString + "汇计划/";
                }
            }
            projectString = StringUtils.removeEnd(
                    projectString, "/");
            projectString = projectString + "项目";
        }
        detail.setProjectType("适用" + projectString);
        CouponUserVO couponUserVO = couponUserService.selectCouponUserById(couponUserId);
        customizeVO.setDetail(detail);
        customizeVO.setCouponUser(couponUserVO);
        customizeVO.setId(couponUserId);
        return new AdminResult<>(result);
    }


    @ApiOperation(value = "优惠券用户列表",notes = "编辑保存")
    @RequestMapping("/auditAction")
    public AdminResult<ListResult<CouponUserCustomizeVO>> auditAction(HttpServletRequest request,CouponUserBeanRequest couponUserBeanRequest) {
        JSONObject json = new JSONObject();
        CouponUserCustomizeVO customizeVO = new CouponUserCustomizeVO();
        ListResult<CouponUserCustomizeVO> result =  new ListResult<>();
        CouponUserVO record = couponUserService.selectCouponUserById(couponUserBeanRequest.getId());
        if(record == null || StringUtils.isEmpty(record.getCouponCode())){
            return new AdminResult<>(result);
        }
        CouponConfigResponse configResponse = couponUserService.getCouponConfig(couponUserBeanRequest.getCouponCode());
        if (configResponse == null && configResponse.getResult() == null) {
            return new AdminResult<>(result);
        }
        json = this.validatorFieldCheckAudit(json, couponUserBeanRequest, record);

        if (AdminValidatorFieldCheckUtil.hasValidateError(json)) {
            Integer couponUserId = couponUserBeanRequest.getId();
            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("couponUserId",couponUserId);
            CouponTenderDetailVo detail = couponUserService.getCouponTenderDetailCustomize(paramMap);
            //操作平台
            Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
            //被选中操作平台
            String clientString = "";
            String clientSed[] = StringUtils.split(detail.getCouponSystem(), ",");
            for (int i = 0; i < clientSed.length; i++) {
                if ("-1".equals(clientSed[i])) {
                    clientString = clientString + "所有平台";
                    break;
                } else {
                    for (String key : client.keySet()) {
                        if (clientSed[i].equals(key)) {
                            if (i != 0 && clientString.length() != 0) {
                                clientString = clientString + "/";
                            }
                            clientString = clientString + client.get(key);
                        }
                    }
                }
            }
            detail.setCouponSystem(clientString);

            //被选中项目类型
            String projectString = "";
            //被选中项目类型
            String projectSed[] = StringUtils.split(detail.getProjectType(), ",");
            if (detail.getProjectType().indexOf("-1") != -1) {
                projectString = "所有汇直投/汇消费/新手汇/尊享汇/汇添金/汇计划项目";
            } else {
                projectString = projectString + "所有";
                for (String project : projectSed) {
                    if ("1".equals(project)) {
                        projectString = projectString + "汇直投/";
                    }
                    if ("2".equals(project)) {
                        projectString = projectString + "汇消费/";
                    }
                    if ("3".equals(project)) {
                        projectString = projectString + "新手汇/";
                    }
                    if ("4".equals(project)) {
                        projectString = projectString + "尊享汇/";
                    }
                    if ("5".equals(project)) {
                        projectString = projectString + "汇添金/";
                    }
                    if ("6".equals(project)) {
                        projectString = projectString + "汇计划/";
                    }
                }
                projectString = StringUtils.removeEnd(
                        projectString, "/");
                projectString = projectString + "项目";
            }
            detail.setProjectType("适用" + projectString);
            CouponUserVO couponUserVO = couponUserService.selectCouponUserById(couponUserId);
            customizeVO.setDetail(detail);
            customizeVO.setCouponUser(couponUserVO);
            customizeVO.setId(couponUserId);
            return new AdminResult<>(result);
        }else {
            AdminSystemVO user = getUser(request);
            String loginUserId = user.getId();
            AdminCouponUserRequestBean adminCouponUserRequestBean = new AdminCouponUserRequestBean();
            adminCouponUserRequestBean.setCouponUserBeanRequest(couponUserBeanRequest);
            adminCouponUserRequestBean.setCouponConfigVO(configResponse.getResult());
            adminCouponUserRequestBean.setUserId(record.getUserId());
            adminCouponUserRequestBean.setLoginUserId(loginUserId);
            CouponUserCustomizeResponse couponUserCustomizeResponse = couponUserService.auditRecord(adminCouponUserRequestBean);
        }
        return new AdminResult<>(result);
    }

    @ApiOperation(value = "优惠券用户列表",notes = "手动批量发券上传")
    @RequestMapping("/uploadAction")
    public AdminResult uploadAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AdminSystemVO user = getUser(request);
        String loginUserId = user.getId();
        String s = couponUserService.uploadFile(request,response,loginUserId);
        if (StringUtils.isNotBlank(s)) {
            return new AdminResult<>(SUCCESS, SUCCESS_DESC);
        } else {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }


    @ApiOperation(value = "优惠券用户列表",notes = "导出")
    @RequestMapping("/exportAction")
    @AuthorityAnnotation(key = PERMISSIONS,value = ShiroConstants.PERMISSION_EXPORT)
    public AdminResult exportAction(HttpServletRequest request, HttpServletResponse response, CouponUserBeanRequest beanRequest) throws Exception {
        // 表格sheet名称
        String sheetName = "优惠券用户列表";

//        Map<String, Object> paraMap = new HashMap<String, Object>();
//        paraMap.put("userId", beanRequest.getUserId());
//        paraMap.put("couponCode", beanRequest.getCouponCode());
//        paraMap.put("couponUserCode", beanRequest.getCouponUserCode());
//        paraMap.put("username", beanRequest.getUserName());
//        paraMap.put("couponType", beanRequest.getCouponType());
//        paraMap.put("usedFlag", beanRequest.getUsedFlag());
//        paraMap.put("couponSource", beanRequest.getCouponSource());
//        if(StringUtils.isNotEmpty(beanRequest.getTimeStartAddSrch())){
//            paraMap.put("timeStartAddSrch", GetDate.getDayStart10(beanRequest.getTimeStartAddSrch()));
//        }
//        if(StringUtils.isNotEmpty(beanRequest.getTimeEndAddSrch())){
//            paraMap.put("timeEndAddSrch", GetDate.getDayEnd10(beanRequest.getTimeEndAddSrch()));
//        }
//        if(StringUtils.isNotEmpty(beanRequest.getTimeStartSrch())){
//            paraMap.put("timeStartSrch", GetDate.getDayStart10(beanRequest.getTimeStartSrch()));
//        }
//        if(StringUtils.isNotEmpty(beanRequest.getTimeEndSrch())){
//            paraMap.put("timeEndSrch", GetDate.getDayEnd10(beanRequest.getTimeEndSrch()));
//        }

        CouponUserCustomizeResponse customizeResponse  = couponUserService.searchList(beanRequest);
        List<CouponUserCustomizeVO> resultList = customizeResponse.getResultList();
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        String[] titles = new String[] {"序号", "优惠券类别编号","优惠券用户编号","用户名","发券时属性","注册渠道", "优惠券类型","面值","投资限额","有效期","来源", "操作平台", "项目类型", "项目期限", "使用状态","获得时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (resultList != null && resultList.size() > 0) {

            //操作平台
            Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < resultList.size(); i++) {
                rowNum++;
                if (i != 0 && i % 60000 == 0) {
                    sheetCount++;
                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }
                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    CouponUserCustomizeVO couponUser = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    else if (celLength == 1) {
                        cell.setCellValue(couponUser.getCouponCode());
                    }
                    else if (celLength == 2) {
                        cell.setCellValue(couponUser.getCouponUserCode());
                    }
                    else if (celLength == 3) {
                        cell.setCellValue(couponUser.getUsername());
                    }
                    else if (celLength == 4) {
                        cell.setCellValue(couponUser.getAttribute() == null ? "" : couponUser.getAttribute().equals("0") ? "无主单" : couponUser.getAttribute().equals("1") ? "有主单" : couponUser.getAttribute().equals("2") ? "线下员工" : "线上员工");
                    }
                    else if(celLength == 5) {
                        cell.setCellValue(couponUser.getChannel());
                    }
                    else if (celLength == 6) {
                        cell.setCellValue(couponUser.getCouponType());
                    }
                    else if (celLength == 7) {
                        cell.setCellValue(couponUser.getCouponQuota());
                    }
                    else if (celLength == 8) {
                        cell.setCellValue(couponUser.getTenderQuota());
                    }
                    else if (celLength == 9) {
                        cell.setCellValue(couponUser.getEndTime());
                    }
                    else if (celLength == 10) {
                        cell.setCellValue(couponUser.getCouponSource());
                    }
                    else if (celLength == 11) {
                        //被选中操作平台
                        String clientString = "";
                        String clientSed[] = StringUtils.split(couponUser.getCouponSystem(), ",");
                        for(int k=0 ; k< clientSed.length;k++){
                            if("-1".equals(clientSed[k])){
                                clientString=clientString+"不限";
                                break;
                            }else{
                                for (String key : client.keySet()) {
                                    if (clientSed[i].equals(key)) {
                                        if (i != 0 && clientString.length() != 0) {
                                            clientString = clientString + "/";
                                        }
                                        clientString = clientString + client.get(key);
                                    }
                                }
                            }
                        }
                        cell.setCellValue(clientString);
                    }
                    else if (celLength == 12) {
                        //被选中项目类型    新逻辑 pcc20160715
                        String projectString = "";
                        //被选中项目类型
                        String projectSed[] = StringUtils.split(couponUser.getProjectType(), ",");
                        if(couponUser.getProjectType().indexOf("-1")!=-1){
                            projectString="所有汇直投/汇消费/新手汇/尊享汇/汇添金/汇计划项目";
                        }else{
                            projectString=projectString+"所有";
                            for (String project : projectSed) {
                                if("1".equals(project)){
                                    projectString=projectString+"汇直投/";
                                }
                                if("2".equals(project)){
                                    projectString=projectString+"汇消费/";
                                }
                                if("3".equals(project)){
                                    projectString=projectString+"新手汇/";
                                }
                                if("4".equals(project)){
                                    projectString=projectString+"尊享汇/";
                                }
                                if("5".equals(project)){
                                    projectString=projectString+"汇添金/";
                                }
                                if("6".equals(project)){
                                    projectString=projectString+"汇计划/";
                                }

                            }
                            projectString = StringUtils.removeEnd(
                                    projectString, "/");
                            projectString=projectString+"项目";
                        }
                        cell.setCellValue("适用"+projectString);
                    }
                    else if (celLength == 13) {
                        cell.setCellValue(couponUser.getProjectExpirationType());
                    }
                    else if (celLength == 14) {
                        cell.setCellValue(couponUser.getUsedFlagView());
                    }
                    else if (celLength == 15) {
                        cell.setCellValue(couponUser.getAddTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
        return new AdminResult<>(SUCCESS,SUCCESS_DESC);
    }

    /**
     * 画面校验
     * @param jsonObject
     * @param request
     * @param record
     * @return
     */
    private JSONObject validatorFieldCheckAudit(JSONObject jsonObject, CouponUserBeanRequest request, CouponUserVO record) {
        AdminValidatorFieldCheckUtil.validateSynOperation(jsonObject,"synOperation",request.getUpdateTime(), record.getUpdateTime());
        if (!Coupon_AUDIT_PWD.equals(MD5.toMD5Code(request.getCouponAuditPwd()))) {
            CustomErrors.add(jsonObject, "couponAuditPwd", "same", "审核口令错误");
        }
        return jsonObject;
    }


    /**
     * 画面校验
     * @param jsonObject
     * @param request
     * @return
     */
    private JSONObject validatorFieldCheck(JSONObject jsonObject, CouponUserBeanRequest request) {
        //优惠券编码
        AdminValidatorFieldCheckUtil.validateRequired(jsonObject, "couponCode", request.getCouponCode());
        //用户名
        AdminValidatorFieldCheckUtil.validateRequired(jsonObject, "userName", request.getUserName());
        return jsonObject;
    }
}
