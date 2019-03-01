/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.AuthBean;
import com.hyjf.admin.beans.request.UserPayAuthRequestBean;
import com.hyjf.admin.beans.vo.UserPayAuthCustomizeVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AuthService;
import com.hyjf.admin.service.UserPayAuthService;
import com.hyjf.admin.service.UserauthService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.HjhUserAuthResponse;
import com.hyjf.am.response.user.UserPayAuthResponse;
import com.hyjf.am.resquest.user.UserPayAuthRequest;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserPayListAuthCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author nxl
 * @version UserPayAuthController, v0.1 2018/10/15 9:23
 */
@Api(value = "会员中心-缴费授权接口", tags = "会员中心-缴费授权接口")
@RestController
@RequestMapping("/hyjf-admin/userPayAuth")
public class UserPayAuthController extends BaseController {
    @Autowired
    private UserPayAuthService userPayAuthService;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserauthService userauthService;
    @Autowired
    public SystemConfig systemConfig;


    /**
     * 缴费授权列表查询
     *
     * @param userPayAuthRequestBean
     * @return
     */
    @ApiOperation(value = "缴费授权列表查询", notes = "缴费授权列表查询")
    @PostMapping(value = "/userPayAuthList")
    @ResponseBody
    public AdminResult<ListResult<UserPayAuthCustomizeVO>> getUserslist(@RequestBody UserPayAuthRequestBean userPayAuthRequestBean) {
        UserPayAuthRequest userPayAuthRequest = new UserPayAuthRequest();
        BeanUtils.copyProperties(userPayAuthRequestBean, userPayAuthRequest);
        UserPayAuthResponse userManagerResponse = userPayAuthService.selectUserMemberList(userPayAuthRequest);
        if (userManagerResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        List<UserPayListAuthCustomizeVO> userPayAuthCustomizeVOList = userManagerResponse.getResultList();
        List<UserPayAuthCustomizeVO> userManagerCustomizeList = new ArrayList<UserPayAuthCustomizeVO>();
        if (null != userPayAuthCustomizeVOList && userPayAuthCustomizeVOList.size() > 0) {
            userManagerCustomizeList = CommonUtils.convertBeanList(userPayAuthCustomizeVOList, UserPayAuthCustomizeVO.class);
        }
        if (!Response.isSuccess(userManagerResponse)) {
            return new AdminResult<>(FAIL, userManagerResponse.getMessage());
        }
        return new AdminResult<ListResult<UserPayAuthCustomizeVO>>(ListResult.build(userManagerCustomizeList, userManagerResponse.getCount()));
    }

    @ApiOperation(value = "缴费授权查询", notes = "缴费授权查询")
    @PostMapping(value = "/userPayAuthQuery")
    @ResponseBody
    public AdminResult userPayAuthQuery(@RequestBody String userId) {
        logger.info("查询开始，查询用户：{}", userId);
        if (StringUtils.isBlank(userId)) {
            return new AdminResult<>(FAIL, "用户id不能为空!");
        }
        int intUserId = Integer.parseInt(userId);
        HjhUserAuthResponse hjhUserAuthResponse = userPayAuthService.selectUserPayAuthByUserId(intUserId);
        if (hjhUserAuthResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        HjhUserAuthVO hjhUserAuthVO = hjhUserAuthResponse.getResult();
        if (hjhUserAuthVO == null) {
            return new AdminResult<>(FAIL, "用户信息不存在");
        }
        BankCallBean retBean = authService.getTermsAuthQuery(intUserId, BankCallConstant.CHANNEL_PC);
        try {
            if (authService.checkDefaultConfig(retBean, AuthBean.AUTH_TYPE_PAYMENT_AUTH)) {
                logger.info("checkDefaultConfig return");
                return new AdminResult<>();
            }
            if (retBean != null
                    && BankCallConstant.RESPCODE_SUCCESS.equals(retBean.get(BankCallConstant.PARAM_RETCODE))) {
                this.authService.updateUserAuth(intUserId, retBean, AuthBean.AUTH_TYPE_PAYMENT_AUTH);
                return new AdminResult<>();
            } else {
                String retCode = retBean != null ? retBean.getRetCode() : "";
                String retMessage = this.userPayAuthService.getBankRetMsg(retCode);
                String retMsg = retBean.getRetMsg();
                if("请联系客服！".equals(retMessage) && org.apache.commons.lang3.StringUtils.isNotBlank(retMsg)){
                    retMessage = retMsg;
                }
                return new AdminResult<>(FAIL, StringUtils.isNotEmpty(retMessage) ? retMessage : "未知错误");
            }
        } catch (Exception e) {
            logger.error("查询出错", e);
            return new AdminResult<>(FAIL, e.getMessage());
        }
    }
    @ApiOperation(value = "缴费授权解约", notes = "缴费授权解约")
    @PostMapping(value = "/userPayAuthDis")
    @ResponseBody
    public AdminResult userPayAuthDis(@RequestBody String userId) {
        if (StringUtils.isBlank(userId)) {
            return new AdminResult<>(FAIL, "用户id不能为空!");
        }
        int intUserId = Integer.parseInt(userId);
        if (userPayAuthService.isDismissPay(intUserId) > 0) {
            return new AdminResult<>(FAIL, "当前用户有未回款的债权,暂不能解约");
        }
        if ("00".equals(userauthService.canCancelAuth(intUserId).getRtn())) {
            return new AdminResult<>(FAIL,  "当前用户存在持有中计划，不能解约！");
        }
        String authType = "9";

        BankCallBean retBean = this.userPayAuthService.cancelPayAuth(intUserId, BankCallConstant.CHANNEL_PC);
        try {
            if (retBean != null) {
                if (BankCallConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode())) {
                    // 关闭授权操作
                    userPayAuthService.updateCancelPayAuth(intUserId);
                    //在auth_log表中插入解约记录
                    userPayAuthService.insertUserAuthLog2(intUserId,retBean,authType);
                    return new AdminResult<>();
                } else {
                    String retCode = retBean.getRetCode() != null ? retBean.getRetCode() : "";
                    String retMessage = this.userPayAuthService.getBankRetMsg(retCode);
                    String retMsg = retBean.getRetMsg();
                    if("请联系客服！".equals(retMessage) && org.apache.commons.lang3.StringUtils.isNotBlank(retMsg)){
                        retMessage = retMsg;
                    }
                    return new AdminResult<>(FAIL, StringUtils.isNotEmpty(retMessage) ? retMessage : "未知错误");
                }
            } else {
                return new AdminResult<>(FAIL,"调用银行接口失败");
            }
        } catch (Exception e) {
            logger.error("缴费授权解约出错", e);
            return new AdminResult<>(FAIL,e.getMessage());
        }
    }
    @ApiOperation(value = "缴费授权列表导出", notes = "缴费授权列表导出")
    @PostMapping(value = "/exportpayauth")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody UserPayAuthRequestBean userPayAuthRequestBean) throws Exception {

        //取数据
        UserPayAuthRequest userPayAuthRequest = new UserPayAuthRequest();
        BeanUtils.copyProperties(userPayAuthRequestBean, userPayAuthRequest);
        userPayAuthRequest.setLimitFlg(true);
        int totalCount = userPayAuthService.selectUserMemberCount(userPayAuthRequest);
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "缴费授权";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
//        String[] titles = new String[]{"序号", "用户名", "当前手机号", "授权金额", "签约到期日", "授权状态", "银行电子账户", "授权时间"};
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i <= sheetCount; i++) {
            //请求第一页5000条
            userPayAuthRequest.setPageSize(defaultRowMaxCount);
            userPayAuthRequest.setCurrPage(i);
            userPayAuthRequest.setLimitFlg(false);
            UserPayAuthResponse resultResponse2 = userPayAuthService.selectUserMemberList(userPayAuthRequest);
            if (resultResponse2 != null && resultResponse2.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  resultResponse2.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter stringFormatAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return Objects.equals(object, null)? "":object.toString();
            }
        };

        IValueFormatter authTypeFormatAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                if (Integer.valueOf(object.toString()) == 1) {
                    return "已授权";
                } else if (Integer.valueOf(object.toString()) == 0) {
                    return "未授权";
                } else if (Integer.valueOf(object.toString()) == 2) {
                    return "已解约";
                }
                return "";
            }
        };

        IValueFormatter paymentFormatAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return "250000";
            }
        };

        mapAdapter.put("userName", stringFormatAdapter);
        mapAdapter.put("mobile", stringFormatAdapter);
        mapAdapter.put("paymentMaxAmt", paymentFormatAdapter);
        mapAdapter.put("signEndDate", stringFormatAdapter);
        mapAdapter.put("authType", authTypeFormatAdapter);
        mapAdapter.put("bankid", stringFormatAdapter);
        mapAdapter.put("signDate", stringFormatAdapter);
        return mapAdapter;
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("userName","用户名");
        map.put("mobile","当前手机号");
        map.put("paymentMaxAmt","授权金额");
        map.put("signEndDate","签约到期日");
        map.put("authType","授权状态");
        map.put("bankid","银行电子账户");
        map.put("signDate","授权时间");
        return map;
    }
}
