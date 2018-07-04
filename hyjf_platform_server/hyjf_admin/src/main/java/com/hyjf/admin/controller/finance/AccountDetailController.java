/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AccountDetailService;
import com.hyjf.admin.service.UserCenterService;
import com.hyjf.am.response.admin.AccountDetailResponse;
import com.hyjf.am.response.admin.AdminAccountDetailDataRepairResponse;
import com.hyjf.am.response.trade.AccountListResponse;
import com.hyjf.am.response.trade.AccountTradeResponse;
import com.hyjf.am.resquest.admin.AccountDetailRequest;
import com.hyjf.am.resquest.admin.AccountListRequest;
import com.hyjf.am.vo.admin.AccountDetailVO;
import com.hyjf.am.vo.admin.AdminAccountDetailDataRepairVO;
import com.hyjf.am.vo.trade.AccountTradeVO;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.user.SpreadsUserVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author nxl
 * @version AccountdetailController, v0.1 2018/6/29 13:38
 *          后台管理系统，资金中心->资金明细
 */
@Api(value = "资金明细")
@RestController
@RequestMapping("/hyjf-admin/accountDetail")
public class AccountDetailController extends BaseController {

    @Autowired
    private AccountDetailService accountDetailService;
    @Autowired
    private UserCenterService userCenterService;

    @ApiOperation(value = "资金明细", notes = "资金明细页面初始化")
    @PostMapping(value = "/accountDetailInit")
    @ResponseBody
    public JSONObject userManagerInit() {
        JSONObject jsonObject = null;
        // List<AccountDetailVO> listAccountDetail =  accountDetailService.findAccountDetailList();

        return jsonObject;
    }

    @ApiOperation(value = "资金明细", notes = "资金明细页面列表显示")
    @PostMapping(value = "/queryAccountDetail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = false, dataType = "String"),
            @ApiImplicitParam(name = "referrerName", value = "推荐人", required = false, dataType = "String"),
            @ApiImplicitParam(name = "nid", value = "订单号", required = false, dataType = "String"),
            @ApiImplicitParam(name = "accountId", value = "电子账号", required = false, dataType = "String"),
            @ApiImplicitParam(name = "seqNo", value = "交易流水号", required = false, dataType = "String"),
            @ApiImplicitParam(name = "isBank", value = "账户类型是否银行存管,汇付天下=0,江西银行 =1", required = false, dataType = "String"),
            @ApiImplicitParam(name = "checkStatus", value = "对账状态", required = false, dataType = "String"),
            @ApiImplicitParam(name = "tradeStatus", value = "交易状态", required = false, dataType = "String"),
            @ApiImplicitParam(name = "typeSearch", value = "收支类型", required = false, dataType = "String"),
            @ApiImplicitParam(name = "tradeTypeSearch", value = "交易类型", required = false, dataType = "String"),
            @ApiImplicitParam(name = "startDate", value = "开始时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", required = false, dataType = "String"),
            @ApiImplicitParam(name = "remarkSrch", value = "备注查询", required = false, dataType = "String"),
            @ApiImplicitParam(name = "currPage", value = "当前页数", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "一页显示多少", required = false, dataType = "Integer"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功")
    })
    @ResponseBody
    public JSONObject queryAccountDetail(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = null;
        AccountDetailRequest requestAccountDetail = setParam(map);
        AccountDetailResponse accountDetailResponse = accountDetailService.findAccountDetailList(requestAccountDetail);
        List<AccountDetailVO> listAccountDtaileShow = new ArrayList<AccountDetailVO>();
        if (null != accountDetailResponse) {
            List<AccountDetailVO> listAccountDetail = accountDetailResponse.getResultList();
            String recordCount = String.valueOf(accountDetailResponse.getRecordTotal());
            if (null != listAccountDetail && listAccountDetail.size() > 0) {
                for (AccountDetailVO accountDetailVO : listAccountDetail) {
                    //根据用户id获取用户信息
                    UserVO userVO = userCenterService.selectUserByUserId(accountDetailVO.getUserId().toString());
                    //根据用户id获取推荐人信息
                    SpreadsUserVO spreadsUserVO = userCenterService.selectSpreadsUsersByUserId(accountDetailVO.getUserId().toString());
                    if (null != userVO) {
                        accountDetailVO.setUsername(userVO.getUsername());
                    }
                    if (null != spreadsUserVO) {
                        //设置推荐人id
                        accountDetailVO.setReferrerId(spreadsUserVO.getSpreadsUserId().toString());
                        //根据推荐人id查找姓名
                        UserVO userSpreads = userCenterService.selectUserByUserId(spreadsUserVO.getSpreadsUserId().toString());
                        accountDetailVO.setReferrerName(userSpreads.getUsername());
                        accountDetailVO.setReferrerId(userSpreads.getUserId().toString());
                    }
                    //根据推荐人姓名筛选
                    if (null != requestAccountDetail.getReferrerName()) {
                        String referrerNameSearch = requestAccountDetail.getReferrerName().toString();
                        UserVO userReferrerSearch = userCenterService.selectUserByRecommendName(referrerNameSearch);
                        String userRefer = userReferrerSearch.getUserId().toString();
                        if (userRefer.equals(accountDetailVO.getReferrerId())) {
                            listAccountDtaileShow.add(accountDetailVO);
                        }
                    } else {
                        listAccountDtaileShow.add(accountDetailVO);
                    }
                }
            }
            if (null != listAccountDtaileShow) {
                jsonObject = this.success(recordCount, listAccountDtaileShow);
            } else {
                jsonObject = this.fail("暂无符合条件数据");
            }
        }
        return jsonObject;
    }


    @ApiOperation(value = "资金明细", notes = "交易明细修复")
    @PostMapping(value = "/accountdetailDataRepair")
    @ResponseBody
    public JSONObject accountdetailDataRepair(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = null;
        String strRepayDate = "";
        // 查询出20170120还款后,交易明细有问题的用户ID
        AdminAccountDetailDataRepairResponse adminAccountDetailDataRepairResponse = accountDetailService.queryAccountDetailErrorUserList();
        if (null != adminAccountDetailDataRepairResponse) {
            List<AdminAccountDetailDataRepairVO> adminAccountDetailDataRepairVOList = adminAccountDetailDataRepairResponse.getResultList();
            List<Map<String, Object>> mapReturnList = getAccountId(adminAccountDetailDataRepairVOList);
            if (null != mapReturnList&&mapReturnList.size()>0) {
                // mapReturn && mapReturn.size() > 0 && mapReturn.containsKey("userId") && mapReturn.containsKey("accountId")
                for(Map<String, Object>mapReturn:mapReturnList){
                    Integer userId = Integer.parseInt(mapReturn.get("userId").toString());
                    Integer accountId = Integer.parseInt(mapReturn.get("accountId").toString());
                    strRepayDate = this.repayDataRepair(userId, accountId);
                    if (!StringUtils.isNotBlank(strRepayDate)) {
                        jsonObject = this.success();
                        return jsonObject;
                    }
                }

            }
        }
        //代表失敗
        jsonObject = this.fail(strRepayDate);
        return jsonObject;
    }

    /**
     * 设置参数
     *
     * @param mapParam
     * @return
     */
    private AccountDetailRequest setParam(Map<String, Object> mapParam) {
        AccountDetailRequest request = new AccountDetailRequest();
        if (null != mapParam && mapParam.size() > 0) {
            if (mapParam.containsKey("userName")) {
                request.setUsername(mapParam.get("userName").toString());
            }
            if (mapParam.containsKey("referrerName")) {
                request.setReferrerName(mapParam.get("referrerName").toString());
            }
            if (mapParam.containsKey("nid")) {
                request.setNid(mapParam.get("nid").toString());
            }
            if (mapParam.containsKey("accountId")) {
                request.setAccountId(mapParam.get("accountId").toString());
            }
            if (mapParam.containsKey("seqNo")) {
                request.setSeqNo(mapParam.get("seqNo").toString());
            }
            if (mapParam.containsKey("isBank")) {
                request.setIsBank(mapParam.get("isBank").toString());
            }
            if (mapParam.containsKey("checkStatus")) {
                request.setCheckStatus(mapParam.get("checkStatus").toString());
            }
            if (mapParam.containsKey("tradeStatus")) {
                request.setTradeStatus(mapParam.get("tradeStatus").toString());
            }
            if (mapParam.containsKey("typeSearch")) {
                request.setTypeSearch(mapParam.get("typeSearch").toString());
            }
            if (mapParam.containsKey("tradeTypeSearch")) {
                request.setTradeTypeSearch(mapParam.get("tradeTypeSearch").toString());
            }
            if (mapParam.containsKey("startDate")) {
                request.setStartDate(mapParam.get("startDate").toString());
            }
            if (mapParam.containsKey("endDate")) {
                request.setEndDate(mapParam.get("endDate").toString());
            }
            if (mapParam.containsKey("remarkSrch")) {
                request.setRemarkSrch(mapParam.get("remarkSrch").toString());
            }
            if (mapParam.containsKey("currPage") && StringUtils.isNotBlank(mapParam.get("currPage").toString())) {
                request.setCurrPage(Integer.parseInt(mapParam.get("currPage").toString()));
            }
            if (mapParam.containsKey("pageSize") && StringUtils.isNotBlank(mapParam.get("pageSize").toString())) {
                request.setPageSize(Integer.parseInt(mapParam.get("pageSize").toString()));
            }
            if (mapParam.containsKey("limitFlg") && StringUtils.isNotBlank(mapParam.get("limitFlg").toString())) {
                request.setLimitFlg(Boolean.parseBoolean(mapParam.get("limitFlg").toString()));
            }
        }
        return request;
    }

    private String repayDataRepair(Integer userId, Integer accountListId) {
        AccountListResponse accountListResponse = accountDetailService.selectAccountById(accountListId);
        if (null != accountListResponse && null != accountListResponse.getResult()) {
            AccountListVO accountListVO = accountListResponse.getResult();
            // 获取账户可用余额
            BigDecimal balance = accountListVO.getBalance();
            // 查询此用户的下一条交易明细
            AccountListResponse accountListResponseNext = accountDetailService.selectNextAccountList(accountListId, userId);
            if (null != accountListResponseNext && null != accountListResponseNext.getResult()) {
                AccountListVO accountListVOnext = accountListResponseNext.getResult();
                AccountTradeResponse accountTradeResponse = accountDetailService.selectAccountTradeByValue(accountListVOnext.getTrade());
                if (null != accountTradeResponse && null != accountTradeResponse.getResult()) {
                    AccountTradeVO accountTradeVO = accountTradeResponse.getResult();
                    // 更新交易明细的账户余额
                    if ("ADD".equals(accountTradeVO.getOperation())) {
                        accountListVOnext.setBalance(balance.add(accountListVOnext.getAmount()));
                    } else if ("SUB".equals(accountTradeVO.getOperation()) && !"cash_success".equals(accountListVOnext.getTrade())) {
                        accountListVOnext.setBalance(balance.subtract(accountListVOnext.getAmount()));
                    } /*else if ("SUB".equals(accountTradeVO.getOperation())&&"cash_success".equals(accountListVOnext.getTrade())){
                        // 提现不处理
                        //return ;
                    }*/ else if ("UNCHANGED".equals(accountTradeVO.getOperation())) {
                        accountListVOnext.setBalance(balance);
                    }
                    // 更新用户的交易明细
                    AccountListRequest accountListRequest = new AccountListRequest();
                    //
                    BeanUtils.copyProperties(accountListVOnext, accountListRequest);
                    boolean isAccountListUpdateFlag = accountDetailService.updateAccountList(accountListRequest) > 0 ? true : false;
                    if (isAccountListUpdateFlag) {
                        // 递归更新下一条交易明细
                        this.repayDataRepair(userId, accountListVOnext.getId());

                    } else {
                        return "交易明细更新失败,交易明细ID:" + accountListVOnext.getId();
                    }
                } else {
                    return "查询huiyingdai_account_trade交易类型失败,交易明细Value:" + accountListVOnext.getTrade();
                }

            } else {
                return "未查询到下一条交易明细,上一条交易明细ID:" + accountListId;
            }
        } else {
            return "获取交易明细失败" + accountListId;
        }
        return null;
    }

    private List<Map<String, Object>> getAccountId(List<AdminAccountDetailDataRepairVO> adminAccountDetailDataRepairVOList) {
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();

        if (adminAccountDetailDataRepairVOList != null && adminAccountDetailDataRepairVOList.size() > 0) {
            for (AdminAccountDetailDataRepairVO adminAccountDetailDataRepairCustomize : adminAccountDetailDataRepairVOList) {
                Map<String, Object> mapReturn = new HashMap<String, Object>();
                Integer userId = adminAccountDetailDataRepairCustomize.getUserId();
                mapReturn.put("userId", userId);
                // 查询交易明细最小的id
                AdminAccountDetailDataRepairResponse accountdetailDataRepair = accountDetailService.accountdetailDataRepair(userId);
                if (null != accountdetailDataRepair) {
                    AdminAccountDetailDataRepairVO accountList = accountdetailDataRepair.getResult();
                    if (accountList != null) {
                        Integer accountListId = Integer.parseInt(accountList.getId());
                        mapReturn.put("accountId", accountListId);
                    }
                }
                listMap.add(mapReturn);
            }
        }
        return listMap;
    }

    /**
     * 导出资金明细列表
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "资金明细", notes = "导出资金明细列表")
    @PostMapping(value = "/exportqueryaccountdetail")
    public void exportAccountsExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) throws Exception {
        // 表格sheet名称
        String sheetName = "资金明细";
        //分页区分，代表不进行分页
        map.put("limitFlg", false);

        // 设置默认查询时间
        if (StringUtils.isEmpty(map.get("startDate").toString())) {
            map.put("startDate", (GetDate.getDate("yyyy-MM-dd")));
        }
        if (StringUtils.isEmpty(map.get("endDate").toString())) {
            map.put("endDate", GetDate.getDate("yyyy-MM-dd"));
        }
        AccountDetailRequest requestAccountDetail = setParam(map);
        AccountDetailResponse accountDetailResponse = accountDetailService.findAccountDetailList(requestAccountDetail);

        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";

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
                            cell.setCellValue(StringUtils.isEmpty(accountDetailCustomize.getIsBank()) ? "" : accountDetailCustomize.getIsBank().equals("1") ? "江西银行" : "汇付天下");
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
                            cell.setCellValue(StringUtils.isEmpty(accountDetailCustomize.getTradeStatus()) ? "" : accountDetailCustomize.getTradeStatus().equals("0") ? "失败" : "成功" + "");
                        }
                        // 对账状态
                        else if (celLength == 20) {
                            cell.setCellValue(StringUtils.isEmpty(accountDetailCustomize.getCheckStatus()) ? "" : accountDetailCustomize.getCheckStatus().equals("0") ? "未对账" : "已对账" + "");
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
