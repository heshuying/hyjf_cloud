/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import com.hyjf.admin.beans.AuthBean;
import com.hyjf.admin.beans.request.UserPayAuthRequestBean;
import com.hyjf.admin.beans.vo.UserPayAuthCustomizeVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AuthService;
import com.hyjf.admin.service.UserPayAuthService;
import com.hyjf.admin.service.UserauthService;
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
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author nxl
 * @version UserPayAuthController, v0.1 2018/10/15 9:23
 */
@Api(value = "会员中心-还款授权接口", tags = "会员中心-还款授权接口")
@RestController
@RequestMapping("/hyjf-admin/userRepayAuth")
public class UserRePayAuthController extends BaseController {
    @Autowired
    private UserPayAuthService userPayAuthService;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserauthService userauthService;


    /**
     * 还款授权列表查询
     *
     * @param userPayAuthRequestBean
     * @return
     */
    @ApiOperation(value = "还款授权列表查询", notes = "还款授权列表查询")
    @PostMapping(value = "/userRepayAuthList")
    @ResponseBody
    public AdminResult<ListResult<UserPayAuthCustomizeVO>> getUserslist(@RequestBody UserPayAuthRequestBean userPayAuthRequestBean) {
        UserPayAuthRequest userPayAuthRequest = new UserPayAuthRequest();
        BeanUtils.copyProperties(userPayAuthRequestBean, userPayAuthRequest);
        UserPayAuthResponse userManagerResponse = userPayAuthService.selectRecordListRePay(userPayAuthRequest);
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

    @ApiOperation(value = "还款授权查询", notes = "还款授权查询")
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
            if (authService.checkDefaultConfig(retBean, AuthBean.AUTH_TYPE_REPAY_AUTH)) {
                logger.info("checkDefaultConfig return");
                return new AdminResult<>();
            }
            if (retBean != null
                    && BankCallConstant.RESPCODE_SUCCESS.equals(retBean.get(BankCallConstant.PARAM_RETCODE))) {
                this.authService.updateUserAuth(intUserId, retBean, AuthBean.AUTH_TYPE_REPAY_AUTH);
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

    @ApiOperation(value = "还款授权解约", notes = "还款授权解约")
    @PostMapping(value = "/userPayAuthDis")
    @ResponseBody
    public AdminResult userPayAuthDis(@RequestBody String userId) {
        logger.info("还款授权解约开始，用户：{}", userId);
        if (StringUtils.isBlank(userId)) {
            return new AdminResult<>(FAIL, "用户id不能为空!");
        }
        int intUserId = Integer.parseInt(userId);
        if (userPayAuthService.isDismissRePay(intUserId) > 0) {
            return new AdminResult<>(FAIL, "当前用户存在未还款标的，不能解约！");
        }
        String authType = "10";

        BankCallBean retBean = this.userPayAuthService.cancelRePayAuth(intUserId, BankCallConstant.CHANNEL_PC);
        try {
            if (retBean != null) {
                if (BankCallConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode())) {
                    // 关闭授权操作
                    userPayAuthService.updateCancelRePayAuth(intUserId);
                    //在auth_log表中插入解约记录
                    userPayAuthService.insertUserAuthLog2(intUserId, retBean, authType);
                    return new AdminResult<>();
                } else {
                    String retCode = retBean != null ? retBean.getRetCode() : "";
                    String retMessage = this.userPayAuthService.getBankRetMsg(retCode);
                    String retMsg = retBean.getRetMsg();
                    if ("请联系客服！".equals(retMessage) && org.apache.commons.lang3.StringUtils.isNotBlank(retMsg)) {
                        retMessage = retMsg;
                    }
                    return new AdminResult<>(FAIL, StringUtils.isNotEmpty(retMessage) ? retMessage : "未知错误");
                }
            } else {
                return new AdminResult<>(FAIL, "调用银行接口失败");
            }
        } catch (Exception e) {
            logger.error("还款授权解约出错", e);
            return new AdminResult<>(FAIL, e.getMessage());
        }
    }

    @ApiOperation(value = "还款授权列表导出", notes = "还款授权列表导出")
    @PostMapping(value = "/exportpayauth")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody UserPayAuthRequestBean userPayAuthRequestBean) throws Exception {
        // 表格sheet名称
        String sheetName = "还款授权";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";
        String[] titles = new String[]{"序号", "用户名", "授权金额", "签约到期日", "授权状态", "银行电子账户", "授权时间"};
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        //取数据
        UserPayAuthRequest userPayAuthRequest = new UserPayAuthRequest();
        BeanUtils.copyProperties(userPayAuthRequestBean, userPayAuthRequest);
        userPayAuthRequest.setLimitFlg(true);

        UserPayAuthResponse userManagerResponse = userPayAuthService.selectRecordListRePay(userPayAuthRequest);
        if (null != userManagerResponse) {
            // 需要输出的结果列表
            List<UserPayListAuthCustomizeVO> recordList = userManagerResponse.getResultList();
            // 生成一个表格
            HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
            if (recordList != null && recordList.size() > 0) {

                int sheetCount = 1;
                int rowNum = 0;

                for (int i = 0; i < recordList.size(); i++) {
                    rowNum++;
                    if (i != 0 && i % 60000 == 0) {
                        sheetCount++;
                        sheet = com.hyjf.common.util.ExportExcel.createHSSFWorkbookTitle(workbook, titles,
                                (sheetName + "_第" + sheetCount + "页"));
                        rowNum = 1;
                    }

                    // 新建一行
                    Row row = sheet.createRow(rowNum);
                    // 循环数据
                    for (int celLength = 0; celLength < titles.length; celLength++) {
                        UserPayListAuthCustomizeVO user = recordList.get(i);
                        // 创建相应的单元格
                        Cell cell = row.createCell(celLength);
                        if (celLength == 0) {// 序号
                            cell.setCellValue(i + 1);
                        } else if (celLength == 1) {// 用户名
                            cell.setCellValue(user.getUserName());
                        } else if (celLength == 2) {//授权金额
                            cell.setCellValue("1350000");
                        } else if (celLength == 3) {// 签约到期日
                            cell.setCellValue(user.getSignEndDate());
                        } else if (celLength == 4) {// 授权状态
                            if (Integer.valueOf(user.getAuthType()) == 1) {
                                cell.setCellValue("已授权");
                            } else if (Integer.valueOf(user.getAuthType()) == 0) {
                                cell.setCellValue("未授权");
                            } else if (Integer.valueOf(user.getAuthType()) == 2) {
                                cell.setCellValue("已解约");
                            }
                        } else if (celLength == 5) {// 银行电子账户
                            cell.setCellValue(user.getBankid());
                        } else if (celLength == 6) {// 授权时间
                            cell.setCellValue(user.getSignDate());
                        }
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
}
