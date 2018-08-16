/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.accountdetail;

import com.hyjf.admin.beans.request.AccountDetailRequestBean;
import com.hyjf.admin.beans.vo.AccountDetailCustomizeVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AccountDetailService;
import com.hyjf.admin.service.UserCenterService;
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
import com.hyjf.am.vo.user.SpreadsUserVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
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
import java.net.URLEncoder;
import java.util.*;

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
    public AdminResult<List<Map<String,Object>>> userManagerInit() {
        List<AccountTradeVO> accountTradeVOList = accountDetailService.selectTradeTypes();
        List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
        if(null!=accountTradeVOList&&accountTradeVOList.size()>0){
            for(AccountTradeVO accountTradeVO:accountTradeVOList){
                Map<String,Object> mapParam = new HashMap<>();
                mapParam.put("key",accountTradeVO.getId());
                mapParam.put("value",accountTradeVO.getName());
                listMap.add(mapParam);
            }

        }
        return new AdminResult<List<Map<String,Object>>>(listMap);
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
        List<AccountDetailVO> listAccountDtaileShow = new ArrayList<AccountDetailVO>();
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
                //根据筛选条件判断
                AccountDetailVO accountDetailVoDefine = SearchParan(accountDetailRequestBean,accountDetailVO);
                if(null!=accountDetailVoDefine){
                    listAccountDtaileShow.add(accountDetailVoDefine);
                }
            }
        }
        List<AccountDetailCustomizeVO> accountDetailCustomizeVOList = new ArrayList<AccountDetailCustomizeVO>();
        if(null!=listAccountDtaileShow&&listAccountDtaileShow.size()>0){
            accountDetailCustomizeVOList = CommonUtils.convertBeanList(listAccountDtaileShow, AccountDetailCustomizeVO.class);
        }
        return new AdminResult<ListResult<AccountDetailCustomizeVO>>(ListResult.build(accountDetailCustomizeVOList, accountDetailResponse.getRecordTotal())) ;
    }

    private AccountDetailVO SearchParan(AccountDetailRequestBean accountDetailRequestBean,AccountDetailVO accountDetailVO) {
        AccountDetailVO accountDetailVoDefine = new AccountDetailVO();
        //根据推荐人姓名筛选
        if (null != accountDetailRequestBean.getReferrerName()) {
            String referrerNameSearch = accountDetailRequestBean.getReferrerName().toString();
            UserVO userReferrerSearch = userCenterService.selectUserByRecommendName(referrerNameSearch);
            if (null != userReferrerSearch &&accountDetailVO.getReferrerId().equals(userReferrerSearch.getUserId().toString())) {
                BeanUtils.copyProperties(accountDetailVO,accountDetailVoDefine);
            }else{
                return null;
            }
        } else {
            BeanUtils.copyProperties(accountDetailVO,accountDetailVoDefine);
        }
        //根据用户名查找
        if (null != accountDetailRequestBean.getUsername()) {
            String userNameSearch = accountDetailRequestBean.getUsername().toString();
            UserVO userReferrerSearch = userCenterService.selectUserByRecommendName(userNameSearch);
            if (null != userReferrerSearch && accountDetailVO.getUserId().equals(userReferrerSearch.getUserId())) {
                BeanUtils.copyProperties(accountDetailVO,accountDetailVoDefine);
            }else{
                return null;
            }
        } else {
            BeanUtils.copyProperties(accountDetailVO,accountDetailVoDefine);
        }
        return accountDetailVoDefine;
    }


    @ApiOperation(value = "交易明细修复", notes = "交易明细修复")
    @PostMapping(value = "/accountdetailDataRepair")
    @ResponseBody
    public AdminResult accountdetailDataRepair() {
        String strRepayDate = "";
        // 查询出还款后,交易明细有问题的用户ID
        AdminAccountDetailDataRepairResponse adminAccountDetailDataRepairResponse = accountDetailService.queryAccountDetailErrorUserList();
        if (null != adminAccountDetailDataRepairResponse) {
            List<AdminAccountDetailDataRepairVO> adminAccountDetailDataRepairVOList = adminAccountDetailDataRepairResponse.getResultList();
            //
            if (null!=adminAccountDetailDataRepairVOList&&adminAccountDetailDataRepairVOList.size()>0){
                for(AdminAccountDetailDataRepairVO adminAccountDetailDataRepairVO:adminAccountDetailDataRepairVOList){
                    Integer userId = adminAccountDetailDataRepairVO.getUserId();
                    // 查询交易明细最小的id
                    AdminAccountDetailDataRepairResponse accountdetailDataRepair = accountDetailService.accountdetailDataRepair(userId);
                    if(null!=accountdetailDataRepair&& null!=accountdetailDataRepair.getResult()){
                        Integer accountListId = Integer.parseInt(accountdetailDataRepair.getResult().getId());
                        this.repayDataRepair(userId, accountListId);
                    }
                }
            }
        }else {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>();
    }

    private String repayDataRepair(Integer userId, Integer accountListId) {
        // 根据Id查询此条交易明细
        AccountListResponse accountListResponse = accountDetailService.selectAccountById(accountListId);
        if (null != accountListResponse && null != accountListResponse.getResult()) {
            AccountListVO accountListVO = accountListResponse.getResult();
            // 获取账户可用余额
            BigDecimal balance = accountListVO.getBalance();
            // 查询此用户的下一条交易明细
            AccountListResponse accountListResponseNext = accountDetailService.selectNextAccountList(accountListId, userId);
            // 如果下一条交易明细不为空
            if (null != accountListResponseNext && null != accountListResponseNext.getResult()) {
                AccountListVO accountListVOnext = accountListResponseNext.getResult();
                // 根据查询用交易类型查询用户操作金额
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
                    return "查询ht_account_trade交易类型失败,交易明细Value:" + accountListVOnext.getTrade();
                }

            } else {
                return "未查询到下一条交易明细,上一条交易明细ID:" + accountListId;
            }
        } else {
            return "获取交易明细失败" + accountListId;
        }
        return null;
    }

    /**
     * 导出资金明细列表
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "导出资金明细列表", notes = "导出资金明细列表")
    @PostMapping(value = "/exportqueryaccountdetail")
    public void exportAccountsExcel(HttpServletRequest request, HttpServletResponse response,@RequestBody AccountDetailRequestBean accountDetailRequestBean) throws Exception {
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
