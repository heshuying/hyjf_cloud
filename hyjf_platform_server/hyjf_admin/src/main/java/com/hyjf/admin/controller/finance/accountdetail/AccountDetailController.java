/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.accountdetail;

import com.hyjf.admin.beans.request.AccountDetailRequestBean;
import com.hyjf.admin.beans.vo.AccountDetailCustomizeVO;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AccountDetailService;
import com.hyjf.admin.service.UserCenterService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AccountDetailResponse;
import com.hyjf.am.response.admin.AdminAccountDetailDataRepairResponse;
import com.hyjf.am.response.trade.account.AccountListResponse;
import com.hyjf.am.response.trade.account.AccountTradeResponse;
import com.hyjf.am.resquest.admin.AccountDetailRequest;
import com.hyjf.am.resquest.admin.AccountListRequest;
import com.hyjf.am.vo.admin.AccountDetailVO;
import com.hyjf.am.vo.admin.AdminAccountDetailDataRepairVO;
import com.hyjf.am.vo.trade.AccountTradeVO;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author nxl
 * @version AccountdetailController, v0.1 2018/6/29 13:38
 *          后台管理系统，资金中心->资金明细
 */
@Api(value = "资金中心->资金明细",tags = "资金中心->资金明细")
@RestController
@RequestMapping("/hyjf-admin/accountDetail")
public class AccountDetailController extends BaseController {

    @Autowired
    private AccountDetailService accountDetailService;
    @Autowired
    private UserCenterService userCenterService;


    @ApiOperation(value = "资金明细页面初始化", notes = "资金明细页面初始化")
    @PostMapping(value = "/accountDetailInit")
    @ResponseBody
    public AdminResult<List<DropDownVO>> userManagerInit() {
        List<AccountTradeVO> accountTradeVOList = accountDetailService.selectTradeTypes();
        List<DropDownVO> dropDownVOList = ConvertUtils.convertListToDropDown(accountTradeVOList,"id","name");
        return new AdminResult<List<DropDownVO>>(dropDownVOList);
    }

    @ApiOperation(value = "资金明细", notes = "资金明细页面列表显示")
    @PostMapping(value = "/queryAccountDetail")
    @ResponseBody
    public AdminResult<ListResult<AccountDetailCustomizeVO>> queryAccountDetail(@RequestBody AccountDetailRequestBean accountDetailRequestBean) {
        AccountDetailRequest requestAccountDetail = new AccountDetailRequest();
        BeanUtils.copyProperties(accountDetailRequestBean,requestAccountDetail);
        AccountDetailResponse accountDetailResponse = accountDetailService.findAccountDetailList(requestAccountDetail);
        if(accountDetailResponse==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(accountDetailResponse)) {
            return new AdminResult<>(FAIL, accountDetailResponse.getMessage());
        }
        List<AccountDetailVO> listAccountDetail = accountDetailResponse.getResultList();
        List<AccountDetailCustomizeVO> accountDetailCustomizeVOList = new ArrayList<AccountDetailCustomizeVO>();
        if(null!=listAccountDetail&&listAccountDetail.size()>0){
            accountDetailCustomizeVOList = CommonUtils.convertBeanList(listAccountDetail, AccountDetailCustomizeVO.class);
        }
        return new AdminResult<ListResult<AccountDetailCustomizeVO>>(ListResult.build(accountDetailCustomizeVOList, accountDetailResponse.getRecordTotal())) ;
    }

    @ApiOperation(value = "交易明细修复", notes = "交易明细修复")
    @PostMapping(value = "/accountdetailDataRepair")
    @ResponseBody
    public AdminResult accountdetailDataRepair() {
        // 查询出还款后,交易明细有问题的用户ID
        AdminAccountDetailDataRepairResponse adminAccountDetailDataRepairResponse = accountDetailService.queryAccountDetailErrorUserList();
        if (null != adminAccountDetailDataRepairResponse) {
            List<AdminAccountDetailDataRepairVO> adminAccountDetailDataRepairVOList = adminAccountDetailDataRepairResponse.getResultList();
            //
            if (null!=adminAccountDetailDataRepairVOList&&adminAccountDetailDataRepairVOList.size()>0){
                for(AdminAccountDetailDataRepairVO adminAccountDetailDataRepairVO:adminAccountDetailDataRepairVOList){
                    Integer userId = adminAccountDetailDataRepairVO.getUserId();
                    // 查询交易明细最小的id
                    AdminAccountDetailDataRepairResponse accountdetailDataRepair = accountDetailService.getdetailDataRepair(userId);
                    if(null!=accountdetailDataRepair&& null!=accountdetailDataRepair.getResult()){
                        Integer accountListId = Integer.parseInt(accountdetailDataRepair.getResult().getId());
                        accountDetailService.repayDataRepair(userId, accountListId);
                    }
                }
            }
        }else {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>();
    }

    /**
     * 导出资金明细列表
     *
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "导出资金明细列表", notes = "导出资金明细列表")
    @PostMapping(value = "/exportqueryaccountdetail")
    public void exportAccountsExcel(HttpServletResponse response,@RequestBody AccountDetailRequestBean accountDetailRequestBean) throws Exception {
        // 表格sheet名称
        String sheetName = "资金明细";

        AccountDetailRequest requestAccountDetail = new AccountDetailRequest();
        BeanUtils.copyProperties(accountDetailRequestBean,requestAccountDetail);
        //查找全部数据
        requestAccountDetail.setLimitFlg(true);
        AccountDetailResponse accountDetailResponse = accountDetailService.findAccountDetailList(requestAccountDetail);

        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";

        String[] titles = new String[]{"序号", "明细ID", "用户名", "电子账号", "推荐人", "推荐组", "资金托管平台", "流水号", "订单号", "操作类型", "交易类型", "操作金额", "银行总资产", "银行可用余额", "银行冻结金额", "汇付可用金额", "汇付冻结金额", "汇添金可用余额",
                "汇添金冻结金额", "交易状态", "对账状态", "备注说明", "时间"};
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        if (null != accountDetailResponse && null != accountDetailResponse.getResultList()) {
            List<AccountDetailVO> accountDetailVOList = accountDetailResponse.getResultList();
            if (accountDetailVOList != null && accountDetailVOList.size() > 0) {
                int sheetCount = 1;
                int rowNum = 0;
                for (int i = 0; i < accountDetailVOList.size(); i++) {
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
                        AccountDetailVO accountDetailCustomize = accountDetailVOList.get(i);
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
                        // 电子账号
                        else if (celLength == 3) {
                            cell.setCellValue(accountDetailCustomize.getAccountId());
                        }
                        // 推荐人
                        else if (celLength == 4) {
                            cell.setCellValue(accountDetailCustomize.getReferrerName());
                        }
                        // 推荐组
                        else if (celLength == 5) {
                            cell.setCellValue(accountDetailCustomize.getReferrerGroup());
                        }
                        // 资金托管平台
                        else if (celLength == 6) {
                            cell.setCellValue(StringUtils.isEmpty(accountDetailCustomize.getIsBank()) ? "" : "1".equals(accountDetailCustomize.getIsBank()) ? "江西银行" : "汇付天下");
                        }
                        // 流水号
                        else if (celLength == 7) {
                            cell.setCellValue(accountDetailCustomize.getSeqNo());
                        }
                        // 订单号
                        else if (celLength == 8) {
                            cell.setCellValue(accountDetailCustomize.getNid());
                        }
                        // 操作类型
                        else if (celLength == 9) {
                            cell.setCellValue(accountDetailCustomize.getType());
                        }
                        // 交易类型
                        else if (celLength == 10) {
                            cell.setCellValue(accountDetailCustomize.getTradeType());
                        }
                        // 操作金额
                        else if (celLength == 11) {
                            cell.setCellValue(accountDetailCustomize.getAmount() + "");
                        }
                        // 银行总资产
                        else if (celLength == 12) {
                            cell.setCellValue(accountDetailCustomize.getBankTotal() + "");
                        }
                        // 银行可用余额
                        else if (celLength == 13) {
                            cell.setCellValue(accountDetailCustomize.getBankBalance() + "");
                        }
                        // 银行冻结金额
                        else if (celLength == 14) {
                            cell.setCellValue(accountDetailCustomize.getBankFrost() + "");
                        }
                        // 汇付可用金额
                        else if (celLength == 15) {
                            cell.setCellValue(accountDetailCustomize.getBalance() + "");
                        }
                        // 汇付冻结金额
                        else if (celLength == 16) {
                            cell.setCellValue(accountDetailCustomize.getFrost() + "");
                        }
                        // 汇添金可用金额
                        else if (celLength == 17) {
                            cell.setCellValue(accountDetailCustomize.getPlanBalance() + "");
                        }
                        // 汇添金冻结金额
                        else if (celLength == 18) {
                            cell.setCellValue(accountDetailCustomize.getPlanFrost() + "");
                        }
                        // 交易状态
                        else if (celLength == 19) {
                            cell.setCellValue(StringUtils.isEmpty(accountDetailCustomize.getTradeStatus()) ? "" : "0".equals(accountDetailCustomize.getTradeStatus()) ? "失败" : "成功" + "");
                        }
                        // 对账状态
                        else if (celLength == 20) {
                            cell.setCellValue(StringUtils.isEmpty(accountDetailCustomize.getCheckStatus()) ? "" : "0".equals(accountDetailCustomize.getCheckStatus()) ? "未对账" : "已对账" + "");
                        }
                        // 备注说明
                        else if (celLength == 21) {
                            cell.setCellValue(accountDetailCustomize.getRemark());
                        }
                        // 时间
                        else if (celLength == 22) {
                            cell.setCellValue(accountDetailCustomize.getCreateTime());
                        }
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);

    }


}
