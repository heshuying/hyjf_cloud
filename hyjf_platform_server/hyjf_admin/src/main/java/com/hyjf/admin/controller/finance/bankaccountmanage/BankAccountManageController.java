/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.bankaccountmanage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.response.BankAccountManageResponseBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.BankAccountManageService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.resquest.admin.BankAccountManageRequest;
import com.hyjf.am.vo.admin.BankAccountManageCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.common.util.*;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageController, v0.1 2018/6/28 15:57
 */
@Api(value = "账户管理(银行)", tags = "资金中心-账户管理(银行)")
@RestController
@RequestMapping("/hyjf-admin/bank_account_manage")
public class BankAccountManageController extends BaseController {

    @Autowired
    BankAccountManageService bankAccountManageService;

    /**
     * 银行账户管理页面
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "账户管理明细", notes = "银行账户管理")
    @PostMapping(value = "/search")
    @ResponseBody
    public AdminResult<BankAccountManageResponseBean> search(@RequestBody BankAccountManageRequest request) {

        BankAccountManageResponseBean bean = new BankAccountManageResponseBean();

        Integer count = bankAccountManageService.selectAccountInfoCount(request);
        count = (count == null) ? 0 : count;
        bean.setTotal(count);
        //分页参数
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        page.setTotal(count);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        if (count > 0) {
            List<BankAccountManageCustomizeVO> bankAccountManageCustomizeVO = bankAccountManageService.queryAccountInfos(request);
            if (bankAccountManageCustomizeVO == null || bankAccountManageCustomizeVO.size() == 0) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            List<BankAccountManageCustomizeVO> recordList = bankAccountManageCustomizeVO;
            bean.setRecordList(recordList);
        }
        return new AdminResult(bean);
    }

    /**
     * 导出资金明细列表
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "导出资金明细列表", notes = "银行账户管理")
    @PostMapping("/export_account_detail_excel")
    public void exportAccountDetailExcel(@RequestBody BankAccountManageRequest request, HttpServletResponse response) {
        // 表格sheet名称
        String sheetName = "资金明细";
        // 取得数据
        List<BankAccountManageCustomizeVO> recordList = this.bankAccountManageService.queryAccountDetails(request);

        String fileName = null;
        try {
            fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("转码错误....", e);
        }

        String[] titles = new String[]{"序号", "明细ID", "用户名", "推荐人", "推荐组", "订单号", "操作类型", "交易类型", "操作金额", "可用余额", "冻结金额", "备注说明", "时间"};
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (recordList != null && recordList.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < recordList.size(); i++) {
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
                    BankAccountManageCustomizeVO accountDetailCustomize = recordList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }

                    // 明细ID
                    else if (celLength == 1) {
                        cell.setCellValue(accountDetailCustomize.getId());
                    }
                    // 用户名
                    else if (celLength == 2) {
                        cell.setCellValue(accountDetailCustomize.getUsername());
                    }
                    // 推荐人
                    else if (celLength == 3) {
                        cell.setCellValue(accountDetailCustomize.getReferrerName());
                    }
                    // 推荐组
                    else if (celLength == 4) {
                        cell.setCellValue(accountDetailCustomize.getReferrerGroup());
                    }
                    // 订单号
                    else if (celLength == 5) {
                        cell.setCellValue(accountDetailCustomize.getNid());
                    }
                    // 操作类型
                    else if (celLength == 6) {
                        cell.setCellValue(accountDetailCustomize.getType());
                    }
                    // 交易类型
                    else if (celLength == 7) {
                        cell.setCellValue(accountDetailCustomize.getTradeType());
                    }
                    // 操作金额
                    else if (celLength == 8) {
                        cell.setCellValue(accountDetailCustomize.getAmount() + "");
                    }
                    // 可用余额
                    else if (celLength == 9) {
                        cell.setCellValue(accountDetailCustomize.getBalance() + "");
                    }
                    // 冻结金额
                    else if (celLength == 10) {
                        cell.setCellValue(accountDetailCustomize.getFrost() + "");
                    }
                    // 备注说明
                    else if (celLength == 11) {
                        cell.setCellValue(accountDetailCustomize.getRemark());
                    }
                    // 时间
                    else if (celLength == 12) {
                        cell.setCellValue(accountDetailCustomize.getCreateTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);

    }

    /**
     * 更新
     *
     * @param userIdStr
     * @return
     */
    @ApiOperation(value = "更新账户余额", notes = "银行账户管理")
    @ResponseBody
    @PostMapping("/update_balance_action/{userIdStr}")
    public String updateBalanceAction(@PathVariable String userIdStr) {

        JSONObject ret = new JSONObject();

        // 用户ID
        Integer userId = GetterUtil.getInteger(userIdStr);
        if (null == userId || 0 ==userId) {
            ret.put("status", "1");
            ret.put("result", "更新发生错误,请重新操作!");
            logger.error("参数不正确[userId=" + userId + "]", BankAccountManageController.class);
            return ret.toString();
        }

        // 取得用户在银行的账户信息
        AccountVO accountVO = bankAccountManageService.getAccountByUserId(userId);
        // 用户未开户时,返回错误信息
        if (accountVO == null || StringUtils.isBlank(accountVO.getAccountId())) {
            ret.put("status", "1");
            ret.put("result", "用户未开户!");
            logger.error("参数不正确[userId=" + userId + "]", BankAccountManageController.class);
            return ret.toString();
        }

        // 账户可用余额
        BigDecimal balance = BigDecimal.ZERO;
        // 账户冻结金额
        BigDecimal frost = BigDecimal.ZERO;
        // 账面余额
        BigDecimal currBalance = BigDecimal.ZERO;

        BankCallBean bean = new BankCallBean();
        // 版本号
        bean.setVersion(BankCallConstant.VERSION_10);
        // 交易代码
        bean.setTxCode(BankCallMethodConstant.TXCODE_BALANCE_QUERY);
        // 交易日期
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        // 交易时间
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        // 交易流水号
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        // 交易渠道
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        // 电子账号
        bean.setAccountId(accountVO.getAccountId());
        // 订单号
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(Integer.valueOf(userId)));
        // 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        bean.setLogUserId(String.valueOf(userId));
        // 平台
        bean.setLogClient(0);
        try {
            BankCallBean resultBean = BankCallUtils.callApiBg(bean);
            if (resultBean == null) {
                logger.error("调用银行接口发生错误", BankAccountManageController.class);
                ret.put("status", "1");
                ret.put("result", "调用银行接口发生错误");
                return ret.toString();
            }
            if (resultBean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
                // 账户余额
                balance = new BigDecimal(resultBean.getAvailBal().replace(",", ""));
                // 账面余额
                currBalance = new BigDecimal(resultBean.getCurrBal().replace(",", ""));
                // 账户冻结金额
                frost = currBalance.subtract(balance);
            } else {
                logger.error("调用银行接口发生错误", BankAccountManageController.class);
                ret.put("status", "1");
                ret.put("result", "调用银行接口发生错误");
                return ret.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int cnt = 0;

        try {
            // 更新处理
            cnt = this.bankAccountManageService.updateAccount(userId, balance, frost);
        } catch (Exception e) {
            logger.error("余额更新异常！", BankAccountManageController.class, e);
        }

        // 返现成功
        if (cnt > 0) {
            ret.put("status", "0");
            ret.put("result", "更新操作成功!");
        } else {
            ret.put("status", "1");
            ret.put("result", "更新发生错误,请重新操作!");
        }

        return ret.toString();
    }

    /**
     * 开始线下充值对账 add by liushouyi
     *
     * @param request
     * @param form
     * @return
     */
    @ApiOperation(value = "开始线下充值对账", notes = "银行账户管理")
    @ResponseBody
    @PostMapping("/start_account_check_action")
    public String bankAccountCheckAction(HttpServletRequest request, @RequestBody BankAccountManageCustomizeVO form) {
        Integer userId = form.getUserId();
        JSONObject ret = new JSONObject();
        String startTime = form.getStartTime();
        String endTime = form.getEndTime();
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            ret.put("msg", "开始时间或结束时间不得为空!");
            ret.put("status", "error");
            return ret.toString();
        }
        String result = this.bankAccountManageService.updateAccountCheck(userId, startTime, endTime);

        if ("success".equals(result)) {
            ret.put("msg", "success");
        } else {
            ret.put("msg", result);
            ret.put("status", "error");
        }

        return ret.toString();
    }

     /**
     * 取得部门信息
     *
     * @return
     */
    @ApiOperation(value = "取得部门信息", notes = "银行账户管理")
    @GetMapping(value = "/get_crm_department_list")
    public JSONObject getCrmDepartmentListAction() {
        // 部门
        String[] list = new String[]{};

        JSONArray ja = this.bankAccountManageService.getCrmDepartmentList(list);
        if (ja != null) {

            //在部门树中加入 0=部门（其他）,因为前端不能显示id=0,就在后台将0=其他转换为-10086=其他
            JSONObject jo = new JSONObject();

            jo.put("value", "-10086");
            jo.put("title", "其他");
            JSONArray array = new JSONArray();
            jo.put("key", UUID.randomUUID());
            jo.put("children", array);

            ja.add(jo);
            JSONObject ret= new JSONObject();
            ret.put("data", ja);
            ret.put("status", "000");
            ret.put("statusDesc", "成功");
            return ret;
        }
        return new JSONObject();
    }
}
