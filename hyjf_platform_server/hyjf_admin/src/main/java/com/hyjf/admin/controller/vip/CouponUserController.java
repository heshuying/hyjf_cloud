/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.vip;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.CouponCheckService;
import com.hyjf.admin.service.coupon.CouponUserService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CouponUserCustomizeResponse;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.resquest.admin.AdminCouponUserRequestBean;
import com.hyjf.am.resquest.admin.CouponConfigRequest;
import com.hyjf.am.resquest.admin.CouponUserBeanRequest;
import com.hyjf.am.resquest.admin.CouponUserRequest;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.admin.coupon.CouponTenderDetailVo;
import com.hyjf.am.vo.admin.coupon.CouponTenderVo;
import com.hyjf.am.vo.admin.coupon.CouponUserCustomizeVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author yaoyong
 * @version CouponUserController, v0.1 2018/7/23 15:23
 * 优惠券用户列表
 */
@Api(tags = "VIP中心-优惠券用户")
@RestController
@RequestMapping("/hyjf-admin/couponuser")
public class CouponUserController extends BaseController {

    /**
     * 权限名称
     */
    private static final String PERMISSIONS = "couponuser";
    /**
     * 排他check
     */
    private static final String SYN_OPERATION = "syn.operation";

    @Value("${coupon.audit.pwd}")
    private String Coupon_AUDIT_PWD;

    @Autowired
    private CouponUserService couponUserService;
    @Autowired
    private CouponCheckService couponCheckService;

    @ApiOperation(value = "页面初始化", notes = "页面初始化")
    @PostMapping("/couponUserList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<CouponUserCustomizeVO>> searchCouponUser(@RequestBody CouponUserBeanRequest couponUserBeanRequest) {
        CouponUserCustomizeResponse response = couponUserService.searchList(couponUserBeanRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<ListResult<CouponUserCustomizeVO>>(ListResult.build(response.getResultList(), response.getCount()));
    }

    @ApiOperation(value = "删除优惠券", notes = "删除优惠券")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteCouponUser(HttpServletRequest request, @RequestBody CouponUserBeanRequest couponUserBeanRequest) {
        AdminSystemVO user = getUser(request);
        String userId = user.getId();
        couponUserBeanRequest.setUpdateUser(userId);
        CouponUserCustomizeResponse response = couponUserService.deleteById(couponUserBeanRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "手动发放页面信息", notes = "手动发放页面信息")
    @PostMapping("/distributeViewAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult distributeViewAction() {
        CouponConfigRequest request = new CouponConfigRequest();
        request.setStatus(CustomConstants.COUPON_STATUS_PUBLISHED);
        CouponUserCustomizeResponse response = couponUserService.getRecordList(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }


    @ApiOperation(value = "发放优惠券", notes = "发放优惠券")
    @PostMapping("/distributeAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult distributeAction(@RequestBody CouponUserBeanRequest couponUserBeanRequest, HttpServletRequest request) {
        CouponUserResponse couponUserResponse = new CouponUserResponse();
        AdminSystemVO user = getUser(request);
        String loginUserId = user.getId();
        //校验请求参数
        String message = this.validatorFieldCheck(couponUserBeanRequest);
        if (message != null) {
            return new AdminResult<>(FAIL, message);
        }
        //校验数量
        if (couponUserBeanRequest.getAmount() == null || couponUserBeanRequest.getAmount() < 0) {
            couponUserBeanRequest.setAmount(1);
        }

        for (int i = 0; i < couponUserBeanRequest.getAmount(); i++) {
            CouponUserCustomizeResponse response = new CouponUserCustomizeResponse();
            //根据用户名获取用户id
            String userName = couponUserBeanRequest.getUserName();
            UserVO userVO = couponUserService.getUser(userName);
            Integer userId = userVO.getUserId();

            //根据用户id获取用户详情信息
            UserInfoVO userInfoVO = couponUserService.getUserInfo(userId);

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
            couponUserRequest.setCouponCode(configVO.getCouponCode());
            couponUserRequest.setContent(couponUserBeanRequest.getContent());
            couponUserRequest.setCouponUserCode(GetCode.getCouponUserCode(configVO.getCouponType()));
            couponUserRequest.setCreateUserId(Integer.parseInt(loginUserId));
            couponUserRequest.setCreateTime(GetDate.getDate());
            couponUserRequest.setUpdateUserId(Integer.parseInt(loginUserId));
            couponUserRequest.setUpdateTime(GetDate.getDate());
            couponUserRequest.setDelFlag(CustomConstants.FALG_NOR);
            couponUserRequest.setUsedFlag(CustomConstants.USER_COUPON_STATUS_WAITING_PUBLISH);
            couponUserRequest.setReadFlag(CustomConstants.USER_COUPON_READ_FLAG_NO);
            if (couponUserBeanRequest.getActivityId() == null) {
                couponUserRequest.setCouponSource(CustomConstants.USER_COUPON_SOURCE_MANUAL);
            } else {
                couponUserRequest.setCouponSource(CustomConstants.USER_COUPON_SOURCE_ACTIVE);
                couponUserRequest.setActivityId(couponUserBeanRequest.getActivityId());
            }
            couponUserRequest.setAttribute(userInfoVO.getAttribute());
            couponUserRequest.setChannel(channelName);
            boolean result = couponCheckService.checkSendNum(couponUserBeanRequest.getCouponCode());
            if (!result) {
                return new AdminResult(AdminResult.FAIL, "优惠券发行数量超出上限，不再发放！");
            }
            couponUserResponse = couponUserService.insertCouponUser(couponUserRequest);
        }
        return new AdminResult<>(couponUserResponse);
    }


    @ApiOperation(value = "用户有效性校验", notes = "用户有效性校验")
    @PostMapping("/checkUserAction")
    public AdminResult checkUserAction(@RequestBody CouponUserBeanRequest request) {
        CouponUserCustomizeResponse response = new CouponUserCustomizeResponse();
        String userName = request.getUserName();
        if (StringUtils.isEmpty(userName)) {
            String message = "用户名不能为空";
            response.setMessage(message);
            return new AdminResult<>(FAIL, message);
        }
        UserVO user = couponUserService.getUser(userName);
        if (user == null) {
            String message = "用户名不存在";
            response.setMessage(message);
            return new AdminResult<>(FAIL, message);
        } else if (user.getStatus() != null && user.getStatus() == 1) {
            String message = "用户已锁定";
            response.setMessage(message);
            return new AdminResult<>(FAIL, message);
        }
        return new AdminResult<>(SUCCESS, SUCCESS_DESC);
    }

    @ApiOperation(value = "校验优惠券是否可用", notes = "校验优惠券是否可用")
    @RequestMapping(value = "/checkCouponValidAction", method = RequestMethod.POST)
    public AdminResult checkCouponValidAction(@RequestBody CouponUserBeanRequest request) {
        CouponUserCustomizeResponse response = new CouponUserCustomizeResponse();
        String couponCode = request.getCouponCode();
        int amount = 0;
        if (request.getAmount() != null) {
            amount = request.getAmount();
        }
        if (StringUtils.isEmpty(couponCode)) {
            response.setMessage("请求参数不正确");
            return new AdminResult<>(FAIL, response.getMessage());
        }
        CouponUserResponse couponUserResponse = couponUserService.getCouponUserByCouponCode(couponCode);
        List<CouponUserVO> couponUserVOS = couponUserResponse.getResultList();
        //已发送优惠券的数量
        if (!CollectionUtils.isEmpty(couponUserVOS) && couponUserVOS.size() > 0) {
            int sendedCouponSum = couponUserVOS.size();
            CouponConfigResponse configResponse = couponUserService.getCouponConfig(couponCode);
            CouponConfigVO configVO = configResponse.getResult();
            if (configVO.getCouponQuantity() > sendedCouponSum + amount) {
                response.setMessage(Response.SUCCESS_MSG);
            } else {
                response.setMessage("优惠券发行数量已用完");
            }
        }
        return new AdminResult<>(response);
    }


    @ApiOperation(value = "加载优惠券配置信息", notes = "加载优惠券配置信息")
    @RequestMapping(value = "/loadCouponConfigAction", method = RequestMethod.POST)
    public AdminResult loadCouponConfigAction(@RequestBody CouponUserBeanRequest request) {
        CouponConfigResponse response = new CouponConfigResponse();
        String couponCode = request.getCouponCode();
        if (StringUtils.isEmpty(couponCode)) {
            response.setMessage("优惠券编码不能为空");
            return new AdminResult<>(FAIL, response.getMessage());
        }
        CouponConfigResponse configResponse = couponUserService.getCouponConfig(couponCode);
        CouponConfigVO configVO = configResponse.getResult();
        //截止日
        if (configVO.getExpirationType() == 1) {
            configVO.setContent(GetDate.formatDate(Long.parseLong((configVO.getExpirationDate() + "000"))));
        } else if (configVO.getExpirationType() == 2) {
            Date date = GetDate.countDate(GetDate.getDate(), 2, configVO.getExpirationLength());
            configVO.setContent(GetDate.formatDate(date));
        } else if (configVO.getExpirationType() == 3) {
            Date date = GetDate.countDate(GetDate.getDate(), 5, configVO.getExpirationLengthDay());
            configVO.setContent(GetDate.formatDate(date));
        }
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
            projectString = "所有散标/新手/智投项目";
        } else {
            projectString = projectString + "所有";
            for (String project : projectSed) {
                if ("1".equals(project)) {
                    projectString = projectString + "散标/";
                }
                if ("2".equals(project)) {
                    projectString = projectString + "";
                }
                if ("3".equals(project)) {
                    projectString = projectString + "新手/";
                }
                if ("4".equals(project)) {
                    projectString = projectString + "";
                }
                if ("5".equals(project)) {
                    projectString = projectString + "";
                }
                if ("6".equals(project)) {
                    projectString = projectString + "智投/";
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


    @ApiOperation(value = "用户优惠券详情页", notes = "用户优惠券详情页")
    @RequestMapping(value = "/infoAction", method = RequestMethod.POST)
    public AdminResult infoAction(@RequestBody CouponUserBeanRequest request) {
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
            projectString = "所有散标/新手/智投项目";
        } else {
            projectString = projectString + "所有";
            for (String project : projectSed) {
                if ("1".equals(project)) {
                    projectString = projectString + "散标/";
                }
                if ("2".equals(project)) {
                    projectString = projectString + "";
                }
                if ("3".equals(project)) {
                    projectString = projectString + "新手/";
                }
                if ("4".equals(project)) {
                    projectString = projectString + "";
                }
                if ("5".equals(project)) {
                    projectString = projectString + "";
                }
                if ("6".equals(project)) {
                    projectString = projectString + "智投/";
                }
            }
            projectString = StringUtils.removeEnd(
                    projectString, "/");
            projectString = projectString + "项目";
        }
        detail.setProjectType("适用" + projectString);

        couponTenderHztVo.setDetail(detail);
        couponTenderHztVo.setCouponRecoverlist(list);
        return new AdminResult<>(couponTenderHztVo);
    }

    @ApiOperation(value = "编辑初始页", notes = "编辑初始页")
    @RequestMapping(value = "/auditInitAction", method = RequestMethod.POST)
    public AdminResult auditInitAction(@RequestBody CouponUserBeanRequest request) {
        CouponUserCustomizeVO customizeVO = new CouponUserCustomizeVO();
        CouponUserCustomizeResponse response = new CouponUserCustomizeResponse();
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
            projectString = "所有散标/新手/智投项目";
        } else {
            projectString = projectString + "所有";
            for (String project : projectSed) {
                if ("1".equals(project)) {
                    projectString = projectString + "散标/";
                }
                if ("2".equals(project)) {
                    projectString = projectString + "";
                }
                if ("3".equals(project)) {
                    projectString = projectString + "新手/";
                }
                if ("4".equals(project)) {
                    projectString = projectString + "";
                }
                if ("5".equals(project)) {
                    projectString = projectString + "";
                }
                if ("6".equals(project)) {
                    projectString = projectString + "智投/";
                }
            }
            projectString = StringUtils.removeEnd(
                    projectString, "/");
            projectString = projectString + "项目";
        }
        detail.setProjectType("适用" + projectString);
        CouponUserVO couponUserVO = couponUserService.selectCouponUserById(couponUserId);
        response.setDetail(detail);
        response.setCouponUser(couponUserVO);
        return new AdminResult<>(response);
    }


    @ApiOperation(value = "编辑保存", notes = "编辑保存")
    @RequestMapping(value = "/auditAction", method = RequestMethod.POST)
    public AdminResult auditAction(HttpServletRequest request, @RequestBody CouponUserBeanRequest couponUserBeanRequest) {
        CouponUserCustomizeVO customizeVO = new CouponUserCustomizeVO();
        CouponUserCustomizeResponse response = new CouponUserCustomizeResponse();
        CouponUserVO record = couponUserService.selectCouponUserById(couponUserBeanRequest.getId());
        if (record == null || StringUtils.isEmpty(record.getCouponCode())) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        CouponConfigResponse configResponse = couponUserService.getCouponConfig(record.getCouponCode());
        if (configResponse == null || configResponse.getResult() == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        String message = this.validatorFieldCheckAudit(couponUserBeanRequest, record);
        if (message != null) {
            Integer couponUserId = couponUserBeanRequest.getId();
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
                projectString = "所有散标/新手/智投项目";
            } else {
                projectString = projectString + "所有";
                for (String project : projectSed) {
                    if ("1".equals(project)) {
                        projectString = projectString + "散标/";
                    }
                    if ("2".equals(project)) {
                        projectString = projectString + "";
                    }
                    if ("3".equals(project)) {
                        projectString = projectString + "新手/";
                    }
                    if ("4".equals(project)) {
                        projectString = projectString + "";
                    }
                    if ("5".equals(project)) {
                        projectString = projectString + "";
                    }
                    if ("6".equals(project)) {
                        projectString = projectString + "智投/";
                    }
                }
                projectString = StringUtils.removeEnd(
                        projectString, "/");
                projectString = projectString + "项目";
            }
            detail.setProjectType("适用" + projectString);
            CouponUserVO couponUserVO = couponUserService.selectCouponUserById(couponUserId);
            response.setDetail(detail);
            response.setCouponUser(couponUserVO);
            response.setRtn(Response.FAIL);
            response.setMessage(message);
            return new AdminResult(FAIL,message);
        } else {
            AdminSystemVO user = getUser(request);
            String loginUserId = user.getId();
            AdminCouponUserRequestBean adminCouponUserRequestBean = new AdminCouponUserRequestBean();
            adminCouponUserRequestBean.setCouponUserBeanRequest(couponUserBeanRequest);
            adminCouponUserRequestBean.setCouponConfigVO(configResponse.getResult());
            adminCouponUserRequestBean.setUserId(record.getUserId());
            adminCouponUserRequestBean.setLoginUserId(loginUserId);
            response = couponUserService.auditRecord(adminCouponUserRequestBean);
        }
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "手动批量发券上传", notes = "手动批量发券上传")
    @RequestMapping(value = "/uploadAction", method = RequestMethod.POST)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_IMPORT)
    public AdminResult uploadAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AdminSystemVO user = getUser(request);
        String loginUserId = user.getId();
        String s = couponUserService.uploadFile(request, response, loginUserId);
        if (StringUtils.isNotBlank(s)) {
            return new AdminResult<>(SUCCESS, SUCCESS_DESC);
        } else {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }


    //@ApiOperation(value = "导出", notes = "导出")
    //@RequestMapping(value = "/exportAction", method = RequestMethod.POST)
    /*public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody CouponUserBeanRequest beanRequest) throws Exception {
        logger.info("优惠券列表导出开始！");
        // 表格sheet名称
        String sheetName = "优惠券用户列表";
        CouponUserCustomizeResponse customizeResponse = couponUserService.searchList(beanRequest);
        List<CouponUserCustomizeVO> resultList = customizeResponse.getResultList();
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        String[] titles = new String[]{"序号", "优惠券类别编号", "优惠券用户编号", "用户名", "发券时属性", "注册渠道", "优惠券类型", "面值", "出借限额", "有效期", "来源", "操作平台", "项目类型", "项目期限", "使用状态", "获得时间"};
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
                    } else if (celLength == 1) {
                        cell.setCellValue(couponUser.getCouponCode());
                    } else if (celLength == 2) {
                        cell.setCellValue(couponUser.getCouponUserCode());
                    } else if (celLength == 3) {
                        cell.setCellValue(couponUser.getUsername());
                    } else if (celLength == 4) {
                        cell.setCellValue(couponUser.getAttribute() == null ? "" : "0".equals(couponUser.getAttribute()) ? "无主单" : "1".equals(couponUser.getAttribute()) ? "有主单" : "2".equals(couponUser.getAttribute()) ? "线下员工" : "线上员工");
                    } else if (celLength == 5) {
                        cell.setCellValue(couponUser.getChannel());
                    } else if (celLength == 6) {
                        cell.setCellValue(couponUser.getCouponType());
                    } else if (celLength == 7) {
                        cell.setCellValue(couponUser.getCouponQuota());
                    } else if (celLength == 8) {
                        cell.setCellValue(couponUser.getTenderQuota());
                    } else if (celLength == 9) {
                        cell.setCellValue(couponUser.getEndTime());
                    } else if (celLength == 10) {
                        cell.setCellValue(couponUser.getCouponSource());
                    } else if (celLength == 11) {
                        //被选中操作平台
                        String clientString = "";
                        String clientSed[] = StringUtils.split(couponUser.getCouponSystem(), ",");
                        for (int k = 0; k < clientSed.length; k++) {
                            if ("-1".equals(clientSed[k])) {
                                clientString = clientString + "不限";
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
                        cell.setCellValue(clientString);
                    } else if (celLength == 12) {
                        //被选中项目类型    新逻辑 pcc20160715
                        String projectString = "";
                        //被选中项目类型
                        String projectSed[] = StringUtils.split(couponUser.getProjectType(), ",");
                        if (couponUser.getProjectType().indexOf("-1") != -1) {
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
                        cell.setCellValue("适用" + projectString);
                    } else if (celLength == 13) {
                        cell.setCellValue(couponUser.getProjectExpirationType());
                    } else if (celLength == 14) {
                        cell.setCellValue(couponUser.getUsedFlagView());
                    } else if (celLength == 15) {
                        cell.setCellValue(couponUser.getAddTime());
                    }
                }
            }
        }
        logger.info("优惠券列表导出结束！");
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }*/

    /**
     * 导出excel
     *
     * @param beanRequest
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "导出", notes = "导出")
    @RequestMapping(value = "/exportAction", method = RequestMethod.POST)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportActionCu(HttpServletRequest request, HttpServletResponse response, @RequestBody CouponUserBeanRequest beanRequest) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "优惠券用户列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        beanRequest.setLimitFlg(true);
        //请求第一页5000条
        beanRequest.setPageSize(defaultRowMaxCount);
        beanRequest.setCurrPage(1);

        // 如果未加时间条件则只返回空excel
        if(!(StringUtils.isNotBlank(beanRequest.getTimeStartAddSrch()) && StringUtils.isNotBlank(beanRequest.getTimeEndAddSrch())) || (StringUtils.isNotBlank(beanRequest.getTimeStartSrch()) && StringUtils.isNotBlank(beanRequest.getTimeEndSrch()))){
            // 导出
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
            return;
        }

        // 需要输出的结果列表
        CouponUserCustomizeResponse customizeResponse = couponUserService.searchList(beanRequest);
        Integer totalCount = customizeResponse.getCount();
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;

        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        } else {
            //操作平台
            Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
            for (CouponUserCustomizeVO couponUserCustomizeVO : customizeResponse.getResultList()) {
                //循环计数（每循环一次是一行）根据行数下标位置获取数组，无法确定当前行下标
                String clientString = "";
                if (StringUtils.isNotEmpty(couponUserCustomizeVO.getCouponSystem())) {
                    String clientSed[] = StringUtils.split(couponUserCustomizeVO.getCouponSystem(), ",");
                    for (int k = 0; k < clientSed.length; k++) {
                        if ("-1".equals(clientSed[k])) {
                            clientString = clientString + "不限";
                            break;
                        } else {
                            for (String key : client.keySet()) {
                                if (clientSed[k].equals(key)) {
                                    if (clientString.length() != 0) {
                                        clientString = clientString + "/";
                                    }
                                    clientString = clientString + client.get(key);
                                }
                            }
                        }
                        //couponUserCustomizeVO.setCouponSystem(clientString);
                    }
                }
                couponUserCustomizeVO.setCouponSystem(clientString);
            }
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, customizeResponse.getResultList());
        }
        for (int i = 1; i < sheetCount; i++) {
            beanRequest.setPageSize(defaultRowMaxCount);
            beanRequest.setCurrPage(i + 1);
            CouponUserCustomizeResponse customizeResponse2 = couponUserService.searchList(beanRequest);
            //操作平台
            Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
            //插入参数
            if (customizeResponse2 != null && customizeResponse2.getResultList().size() > 0) {
                for (CouponUserCustomizeVO couponUserCustomizeVO : customizeResponse2.getResultList()) {
                    //循环计数（每循环一次是一行）根据行数下标位置获取数组，无法确定当前行下标
                    String clientString = "";
                    String clientSed[] = StringUtils.split(couponUserCustomizeVO.getCouponSystem(), ",");
                    for (int k = 0; k < clientSed.length; k++) {
                        if ("-1".equals(clientSed[k])) {
                            clientString = clientString + "不限";
                            break;
                        } else {
                            for (String key : client.keySet()) {
                                if (clientSed[k].equals(key)) {
                                    if (clientString.length() != 0) {
                                        clientString = clientString + "/";
                                    }
                                    clientString = clientString + client.get(key);
                                }
                            }
                        }
                        //couponUserCustomizeVO.setCouponSystem(clientString);
                    }
                    couponUserCustomizeVO.setCouponSystem(clientString);
                }
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, customizeResponse2.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("couponUserCode", "优惠券ID");
        map.put("couponCode", "优惠券类别编号");
        map.put("username", "用户名");
        map.put("attribute", "发券时属性");
        map.put("channel", "注册渠道");
        map.put("couponType", "优惠券类型");
        map.put("couponQuota", "面值");
        map.put("tenderQuota", "出借限额");
        map.put("endTime", "有效期");
        map.put("couponSource", "来源");
        map.put("couponSystem", "操作平台");
        map.put("projectType", "项目类型");
        map.put("projectExpirationType", "项目期限");
        map.put("usedFlagView", "使用状态");
        map.put("addTime", "获得时间");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter attributeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String attribute = (String) object;
                return attribute == null ? "" : "0".equals(attribute) ? "无主单" : "1".equals(attribute) ? "有主单" : "2".equals(attribute) ? "线下员工" : "线上员工";
            }
        };

        IValueFormatter projectTypeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String projectType = (String) object;
                //被选中项目类型    新逻辑 pcc20160715
                String projectString = "";
                //被选中项目类型
                String projectSed[] = StringUtils.split(projectType, ",");
                if (projectType.indexOf("-1") != -1) {
                    projectString = "所有散标/新手/智投项目";
                } else {
                    projectString = projectString + "所有";
                    for (String project : projectSed) {
                        if ("1".equals(project)) {
                            projectString = projectString + "散标/";
                        }
                        if ("2".equals(project)) {
                            projectString = projectString + "";
                        }
                        if ("3".equals(project)) {
                            projectString = projectString + "新手/";
                        }
                        if ("4".equals(project)) {
                            projectString = projectString + "";
                        }
                        if ("5".equals(project)) {
                            projectString = projectString + "";
                        }
                        if ("6".equals(project)) {
                            projectString = projectString + "智投/";
                        }

                    }
                    projectString = StringUtils.removeEnd(
                            projectString, "/");
                    projectString = projectString + "项目";
                }
                return projectString;
            }
        };

        mapAdapter.put("attribute", attributeAdapter);
        mapAdapter.put("projectType", projectTypeAdapter);
        return mapAdapter;
    }

    /**
     * 画面校验
     *
     * @param request
     * @param record
     * @return
     */
    private String validatorFieldCheckAudit(CouponUserBeanRequest request, CouponUserVO record) {
        String message = null;
        if (Integer.parseInt(request.getUpdateTime()) != record.getUpdateTime()) {
            return message = "排他check" + SYN_OPERATION;
        }
        if (!Coupon_AUDIT_PWD.equals(MD5.toMD5Code(request.getCouponAuditPwd()))) {
            message = "审核口令错误";
        }
        return message;
    }

    /**
     * 画面校验
     *
     * @param request
     * @return
     */
    private String validatorFieldCheck(CouponUserBeanRequest request) {
        String message = null;
        //优惠券编码
        if (request.getCouponCode() == null) {
            message = "优惠券编码不能为空";
        }
        //用户名
        if (request.getUserName() == null) {
            message = "用户名不能为空";
        }
        return message;
    }
}
