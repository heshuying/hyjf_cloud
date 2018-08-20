/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.bankaccountmanage;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BankAccountManageBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.controller.finance.bank.merchant.redpacket.BankRedPacketAccounttListBean;
import com.hyjf.admin.service.BankAccountManageService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BankAccountManageCustomizeResponse;
import com.hyjf.am.response.admin.BankMerchantAccountListCustomizeResponse;
import com.hyjf.am.resquest.admin.BankAccountManageRequest;
import com.hyjf.am.vo.admin.BankAccountManageCustomizeVO;
import com.hyjf.am.vo.admin.BankMerchantAccountListCustomizeVO;
import com.hyjf.am.vo.admin.OADepartmentCustomizeVO;
import com.hyjf.am.vo.trade.AccountTradeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.GetterUtil;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
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
import java.math.BigDecimal;
import java.util.*;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageController, v0.1 2018/6/28 15:57
 */
@Api(value = "账户管理(银行)", tags = "资金中心-账户管理(银行)")
@RestController
@RequestMapping("/hyjf-admin/bankaccountmanage")
public class BankAccountManageController extends BaseController {

    @Autowired
    BankAccountManageService bankAccountManageService;
//
//    @ApiOperation(value = "账户管理页面初始化", notes = "账户管理页面初始化")
//    @PostMapping(value = "/bankaccountmanageinit")
//    @ResponseBody
//    public AdminResult<List<DropDownVO>> bankAccountManageInit() {
//        List<OADepartmentCustomizeVO> departmentList = bankAccountManageService.queryDepartmentInfo();
//        AdminResult<List<DropDownVO>> listMap = new AdminResult<>();
//        listMap.setData(ConvertUtils.convertListToDropDown(departmentList,"id","name"));
//
//
//        return new AdminResult<>();
//    }

    /**
     * 银行账户管理页面
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "账户管理明细", notes = "银行账户管理")
    @PostMapping(value = "/init")
    @ResponseBody
    public AdminResult<BankAccountManageBean> init(@RequestBody BankAccountManageRequest request) {
        Map<String, Object> result = new HashMap<>();

        BankAccountManageBean bean = new BankAccountManageBean();

        // 部门下拉框
        List<OADepartmentCustomizeVO> departmentList = bankAccountManageService.queryDepartmentInfo();
        bean.setDepartmentList(ConvertUtils.convertListToDropDown(departmentList,"id","name"));
        // 会员等级下拉框
        bean.setVipList(ConvertUtils.convertParamMapToDropDown(CacheUtil.getParamNameMap("VIP_GRADE")));

        Integer count = bankAccountManageService.selectAccountInfoCount(request);
        count = (count == null)?0:count;
        result.put("count",count);
        List<BankAccountManageCustomizeVO> bankAccountManageCustomizeVO = bankAccountManageService.queryAccountInfos(request);
        if (bankAccountManageCustomizeVO ==null || bankAccountManageCustomizeVO.size() == 0) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        List<BankAccountManageCustomizeVO> recordList = bankAccountManageCustomizeVO;
        result.put("recordList", recordList);
        return new AdminResult(result);
    }

//    /**
//     * 拼装查询参数
//     *
//     * @param form
//     * @return
//     */
//    private BankAccountManageRequest setRequese(BankAccountManageBean form) {
//        BankAccountManageRequest bankAccountManageRequest = new BankAccountManageRequest();
//        if (null != form) {
//            // 用户名
//            if (StringUtils.isNotBlank(form.getUserNameSrch())) {
//                bankAccountManageRequest.setUserNameSrch(form.getUserNameSrch());
//            }
//            // 部门
//            if (StringUtils.isNotBlank(form.getCombotreeSrch())) {
//                bankAccountManageRequest.setCombotreeSrch(form.getCombotreeSrch());
//            }
//            // 部门
////            if (mapParam.containsKey("combotreeListSrch")) {
////                bankAccountManageRequest.setCombotreeListSrch(mapParam.get("combotreeListSrch").toString());
////            }
//            // 产品类型
//            if (StringUtils.isNotBlank(form.getAccountSrch())) {
//                bankAccountManageRequest.setAccountSrch(form.getAccountSrch());
//            }
//            // 产品类型
//            if (StringUtils.isNotBlank(form.getVipSrch())) {
//                bankAccountManageRequest.setVipSrch(form.getVipSrch());
//            }
//            if (form.getLimitStart() && StringUtils.isNotBlank(mapParam.get("limitStart").toString())) {
//                bankAccountManageRequest.setLimitStart(Integer.parseInt(mapParam.get("limitStart").toString()));
//            }
//            if (form.getLimitEnd() && StringUtils.isNotBlank(mapParam.get("limitEnd").toString())) {
//                bankAccountManageRequest.setLimitEnd(Integer.parseInt(mapParam.get("limitEnd").toString()));
//            }
//        }
//        return bankAccountManageRequest;
//    }

    /**
     * 导出资金明细列表
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "导出资金明细列表", notes = "银行账户管理")
    @PostMapping("/exportaccountdetailexcel")
    public void exportAccountDetailExcel(@RequestBody BankAccountManageRequest request, HttpServletResponse response) {
        // 表格sheet名称
        String sheetName = "资金明细";
        JSONObject jsonObject = new JSONObject();
        // 取得数据
        request.setLimitStart(-1);
        request.setLimitEnd(-1);
        List<BankAccountManageCustomizeVO> recordList = this.bankAccountManageService.queryAccountDetails(request);

        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";

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
     * @param request
     * @param form
     * @return
     */
    @ApiOperation(value = "更新账户余额", notes = "银行账户管理")
    @ResponseBody
    @PostMapping("/updateBalanceAction")
    public String updateBalanceAction(HttpServletRequest request, @RequestBody BankAccountManageCustomizeVO form) {

        JSONObject ret = new JSONObject();

        // 用户ID
        Integer userId = GetterUtil.getInteger(form.getUserId());
        if (Validator.isNull(userId)) {
            ret.put("status", "error");
            ret.put("result", "更新发生错误,请重新操作!");
            logger.error("参数不正确[userId=" + userId + "]", BankAccountManageController.class);
            return ret.toString();
        }

        // 取得用户在银行的账户信息
        BankOpenAccountVO bankOpenAccountVO = bankAccountManageService.getBankOpenAccount(userId);
        // 用户未开户时,返回错误信息
        if (bankOpenAccountVO == null) {
            ret.put("status", "error");
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
        bean.setAccountId(bankOpenAccountVO.getAccount());
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
                ret.put("status", "error");
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
                ret.put("status", "error");
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
            ret.put("status", "success");
            ret.put("result", "更新操作成功!");
        } else {
            ret.put("status", "error");
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
    @PostMapping("/startAccountCheckAction")
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
}