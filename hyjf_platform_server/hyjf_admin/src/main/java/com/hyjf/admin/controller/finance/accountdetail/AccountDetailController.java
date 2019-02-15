/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.accountdetail;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.AccountDetailRequestBean;
import com.hyjf.admin.beans.vo.AccountDetailCustomizeVO;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AccountDetailService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AccountDetailResponse;
import com.hyjf.am.response.admin.AdminAccountDetailDataRepairResponse;
import com.hyjf.am.resquest.admin.AccountDetailRequest;
import com.hyjf.am.vo.admin.AccountDetailVO;
import com.hyjf.am.vo.admin.AdminAccountDetailDataRepairVO;
import com.hyjf.am.vo.trade.AccountTradeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    public static final String PERMISSIONS = "accountdetail";

    @ApiOperation(value = "资金明细页面初始化", notes = "资金明细页面初始化")
    @PostMapping(value = "/accountDetailInit")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<List<DropDownVO>> userManagerInit() {
        List<AccountTradeVO> accountTradeVOList = accountDetailService.selectTradeTypes();
        List<DropDownVO> dropDownVOList = ConvertUtils.convertListToDropDown(accountTradeVOList,"id","name");
        return new AdminResult<List<DropDownVO>>(dropDownVOList);
    }

    @ApiOperation(value = "资金明细", notes = "资金明细页面列表显示")
    @PostMapping(value = "/queryAccountDetail")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
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
            return new AdminResult<>(FAIL, "暂无可修复的数据");
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
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAccountsExcel(HttpServletRequest httpRequest, HttpServletResponse response, @RequestBody AccountDetailRequestBean accountDetailRequestBean) throws Exception {

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "资金明细";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        int sheetCount = 0;
        String sheetNameTmp = sheetName + "_第1页";
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        accountDetailRequestBean.setCurrPage(1);
        accountDetailRequestBean.setPageSize(defaultRowMaxCount);

        AccountDetailRequest requestAccountDetail = new AccountDetailRequest();
        BeanUtils.copyProperties(accountDetailRequestBean,requestAccountDetail);
        //查找全部数据
        //requestAccountDetail.setLimitFlg(true);
        AccountDetailResponse accountDetailResponse = accountDetailService.findAccountDetailList(requestAccountDetail);
        if (accountDetailResponse == null || accountDetailResponse.getRecordTotal() <= 0){
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else{
            int totalCount = accountDetailResponse.getRecordTotal();
            sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, accountDetailResponse.getResultList());
        }

        for (int i = 1; i < sheetCount; i++) {
            requestAccountDetail.setCurrPage(i+1);
            AccountDetailResponse AccountDetailResponse2 = accountDetailService.findAccountDetailList(requestAccountDetail);
            if (AccountDetailResponse2 != null && AccountDetailResponse2.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  AccountDetailResponse2.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(httpRequest, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("id", "明细ID");
        map.put("username", "用户名");
        map.put("accountId", "电子账号");
        map.put("referrerName", "推荐人");
        map.put("referrerGroup", "推荐组");
        map.put("isBank", "资金托管平台");
        map.put("seqNo", "流水号");
        map.put("nid", "订单号");
        map.put("type", "操作类型");
        map.put("tradeType", "交易类型");
        map.put("amount", "操作金额");
        map.put("bankTotal", "银行总资产");
        map.put("bankBalance", "银行可用余额");
        map.put("bankFrost", "银行冻结金额");
        map.put("balance", "汇付可用余额");
        map.put("frost", "汇付冻结金额");
        map.put("planBalance","智投服务可用余额");
        map.put("planFrost","智投服务冻结金额");
        map.put("tradeStatus", "交易状态");
        map.put("checkStatus", "对账状态");
        map.put("remark", "备注说明");
        map.put("createTime", "时间");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter isBankAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String isBank = (String) object;
               return StringUtils.isEmpty(isBank) ? "" : "1".equals(isBank) ? "江西银行" : "汇付天下";
            }
        };
        IValueFormatter amountAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal amount = (BigDecimal) object;
                return amount + "";
            }
        };
        IValueFormatter bankBalanceAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal bankBalance = (BigDecimal) object;
                return bankBalance + "";
            }
        };
        IValueFormatter bankFrostAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal bankFrost = (BigDecimal) object;
                return bankFrost + "";
            }
        };
        IValueFormatter balanceAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal balance = (BigDecimal) object;
                return balance + "";
            }
        };
        IValueFormatter frostAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal frost = (BigDecimal) object;
                return frost + "";
            }
        };
        IValueFormatter planBalanceAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal planBalance = (BigDecimal) object;
                return planBalance + "";
            }
        };

        IValueFormatter planFrostAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal planFrost = (BigDecimal) object;
                return planFrost + "";
            }
        };

        IValueFormatter tradeStatusAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String tradeStatus = (String) object;
                return StringUtils.isEmpty(tradeStatus) ? "" : "0".equals(tradeStatus) ? "失败" : "成功" + "";
            }
        };

        IValueFormatter checkStatusAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String checkStatus = (String) object;
                return StringUtils.isEmpty(checkStatus) ? "" : "0".equals(checkStatus) ? "未对账" : "已对账" + "";
            }
        };
        mapAdapter.put("isBank",isBankAdapter);
        mapAdapter.put("amount",amountAdapter);
        mapAdapter.put("bankBalance",bankBalanceAdapter);
        mapAdapter.put("bankFrost",bankFrostAdapter);
        mapAdapter.put("balance",balanceAdapter);
        mapAdapter.put("frost",frostAdapter);
        mapAdapter.put("planBalance",planBalanceAdapter);
        mapAdapter.put("planFrost",planFrostAdapter);
        mapAdapter.put("tradeStatus",tradeStatusAdapter);
        mapAdapter.put("checkStatus",checkStatusAdapter);
        return mapAdapter;
    }


}
