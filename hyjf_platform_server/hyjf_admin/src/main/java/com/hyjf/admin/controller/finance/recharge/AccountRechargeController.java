package com.hyjf.admin.controller.finance.recharge;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.AccountRechargeRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.finance.recharge.AccountRechargeService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.account.AccountRechargeResponse;
import com.hyjf.am.resquest.admin.AccountRechargeRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 资金中心 - 充值管理
 * @Author : huanghui
 */
@Api(value = "资金中心->充值管理",tags = "资金中心->充值管理")
@RestController
@RequestMapping("/hyjf-admin/recharge")
public class AccountRechargeController extends BaseController {

    @Autowired
    private AccountRechargeService rechargeService;

    /**
     * 资金中心 - 充值管理 检索下拉框
     * @return
     */
    @ApiOperation(value = "充值管理检索下拉框", notes = "充值管理检索下拉框")
    @RequestMapping(value = "/dropDownBox", method = RequestMethod.GET)
    public JSONObject dropDownBox(){
        JSONObject jsonObject = new JSONObject();

        //用户账户充值状态
        List<ParamNameVO> paramList = this.rechargeService.getParamNameList("RECHARGE_STATUS");

        List<Object> rechargeStatusList = new ArrayList<>();
        for(int i = 0; i<paramList.size(); i++){
            Map<String, Object> rechargeStatusMap = new HashedMap();
            rechargeStatusMap.put("key", paramList.get(i).getNameCd());
            rechargeStatusMap.put("value", paramList.get(i).getName());
            rechargeStatusList.add(rechargeStatusMap);
        }

        // 充值银行列表
        List<BanksConfigVO> banks = this.rechargeService.getBankcardList();

        List<Object> banksList = new ArrayList<>();
        for(int i = 0; i<banks.size(); i++){
            Map<String, Object> banksMap = new HashedMap();
            banksMap.put("key", banks.get(i).getName());
            banksMap.put("value", banks.get(i).getName());
            banksList.add(banksMap);
        }

        // 资金托管平台
        List<ParamNameVO> bankType = this.rechargeService.getParamNameList("BANK_TYPE");

        List<Object> bankTypeList = new ArrayList<>();
        for(int i = 0; i<bankType.size(); i++){
            Map<String, Object> bankTypeMap = new HashedMap();
            bankTypeMap.put("key", bankType.get(i).getNameCd());
            bankTypeMap.put("value", bankType.get(i).getName());
            bankTypeList.add(bankTypeMap);
        }

        //充值类型
        //初始充值类型List
        List<Object> rechargeList = new ArrayList<>();

        //初始充值类型Map
        Map<String, Object> rechargeType = new HashedMap();
        Map<String, Object> rechargeType2 = new HashedMap();
        Map<String, Object> rechargeType3 = new HashedMap();
        Map<String, Object> rechargeType4 = new HashedMap();

        //设置对应的键值对
        rechargeType.put("key", "B2C");
        rechargeType.put("value", "个人网银充值");
        rechargeType2.put("key", "B2B");
        rechargeType2.put("value", "企业网银充值");
        rechargeType3.put("key", "B2B");
        rechargeType3.put("value", "企业网银充值");
        rechargeType4.put("key", "B2B");
        rechargeType4.put("value", "企业网银充值");

        //map加入List
        rechargeList.add(rechargeType);
        rechargeList.add(rechargeType2);
        rechargeList.add(rechargeType3);
        rechargeList.add(rechargeType4);

        //充值平台
        //初始化充值平台的list 和 Map
        List<Object> rechargePlatformList = new ArrayList<>();
        Map<String, Object> rechargePlatform = new HashedMap();
        Map<String, Object> rechargePlatform2 = new HashedMap();
        Map<String, Object> rechargePlatform3 = new HashedMap();
        Map<String, Object> rechargePlatform4 = new HashedMap();

        rechargePlatform.put("key", 0);
        rechargePlatform.put("value", "PC");
        rechargePlatform2.put("key", 1);
        rechargePlatform2.put("value", "微信");
        rechargePlatform3.put("key", 2);
        rechargePlatform3.put("value", "Android");
        rechargePlatform4.put("key", 3);
        rechargePlatform4.put("value", "iOS");

        //Map 加入 List
        rechargePlatformList.add(rechargePlatform);
        rechargePlatformList.add(rechargePlatform2);
        rechargePlatformList.add(rechargePlatform3);
        rechargePlatformList.add(rechargePlatform4);

        //获取充值手续费方式
        //初始化获取充值手续费List 和 Map
        List<Object> accessFeeMethodList = new ArrayList<>();
        Map<String, Object> accessFeeMethodMap = new HashedMap();
        Map<String, Object> accessFeeMethodMap2 = new HashedMap();

        accessFeeMethodMap.put("key", 0);
        accessFeeMethodMap.put("value", "向用户收取");
        accessFeeMethodMap2.put("key", 1);
        accessFeeMethodMap2.put("value", "向商户收取");

        //Map 加入 List
        accessFeeMethodList.add(accessFeeMethodMap);
        accessFeeMethodList.add(accessFeeMethodMap2);

        //用户角色
        List<Object> userRoleList = new ArrayList<>();
        Map<String, Object> userRoleMap = new HashedMap();
        Map<String, Object> userRoleMap2 = new HashedMap();
        Map<String, Object> userRoleMap3 = new HashedMap();
        Map<String, Object> userRoleMap4 = new HashedMap();

        //选项加入Map
        userRoleMap.put("key", " ");
        userRoleMap.put("value", "全部");
        userRoleMap2.put("key", 1);
        userRoleMap2.put("value", "投资人");
        userRoleMap3.put("key", 2);
        userRoleMap3.put("value", "借款人");
        userRoleMap4.put("key", 3);
        userRoleMap4.put("value", "垫付机构");

        userRoleList.add(userRoleMap);
        userRoleList.add(userRoleMap2);
        userRoleList.add(userRoleMap3);
        userRoleList.add(userRoleMap4);

        // 初始化总的Map
        Map<String, Object> allMap = new HashedMap();
        allMap.put("rechargeStatusList", rechargeStatusList);
        allMap.put("banksList", banksList);
        allMap.put("bankTypeList", bankTypeList);
        allMap.put("rechargeTypeList", rechargeList);
        allMap.put("rechargePlatformList", rechargePlatformList);
        allMap.put("accessFeeMethodList", accessFeeMethodList);
        allMap.put("userRoleList", userRoleList);

        //初始化List 将所有Map放入此List 以达到返回指定格式数据的方式
//        List<Object> allList = new ArrayList<>();
//        allList.add(allMap);

        if (paramList != null && banks != null && bankType != null){
            jsonObject.put("status", BaseResult.SUCCESS);
            jsonObject.put("statusDesc", BaseResult.SUCCESS_DESC);
            jsonObject.put("data", allMap);
        }else {
            jsonObject.put("status", Response.FAIL);
            jsonObject.put("statusDesc", Response.FAIL_MSG);
            jsonObject.put("data", "");
        }
        return jsonObject;
    }

    /**
     * 资金中心 - 充值管理列表
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "充值管理列表", notes = "资金中心->充值管理")
    @PostMapping(value = "/hjhDayCreditDetailList")
    public AdminResult<ListResult<AccountRechargeVO>> queryRechargeList(@RequestBody AccountRechargeRequestBean requestBean){

        AccountRechargeRequest copyRequest = new AccountRechargeRequest();
        BeanUtils.copyProperties(requestBean, copyRequest);


        //初始化返回List
        List<AccountRechargeVO> returnList = null;
        AccountRechargeResponse rechargeResponse = rechargeService.queryRechargeList(copyRequest);

        if (rechargeResponse == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        if (!Response.isSuccess(rechargeResponse)){
            return new AdminResult<>(FAIL, rechargeResponse.getMessage());
        }

        if (CollectionUtils.isNotEmpty(rechargeResponse.getResultList())){
            returnList = CommonUtils.convertBeanList(rechargeResponse.getResultList(), AccountRechargeVO.class);
            return new AdminResult<ListResult<AccountRechargeVO>>(ListResult.build(returnList, rechargeResponse.getCount()));
        }else {
            return new AdminResult<ListResult<AccountRechargeVO>>(ListResult.build(returnList, 0));
        }
    }

    /**
     * 资金中心 - 充值管理列表
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "更新充值状态", notes = "资金中心->更新充值状态")
    @PostMapping(value = "/modifyRechargeStatus")
    public JSONObject modifyRechargeStatus(@RequestBody AccountRechargeRequestBean requestBean){

        JSONObject jsonObject = new JSONObject();

        //用户ID
        Integer userId = requestBean.getUserId();
        //订单ID
        String nid = requestBean.getNidSearch();

        if (Validator.isNull(userId) || Validator.isNull(nid)){
            jsonObject.put("status", Response.FAIL);
            jsonObject.put("statusDesc", Response.FAIL_MSG + ":订单编号或用户ID为空!");
            return jsonObject;
        }

        //更新充值状态
        boolean isAccountUpdate = this.rechargeService.updateRechargeStatus(userId, nid);
        String status= BaseResult.SUCCESS;
        if (isAccountUpdate){
            status = BaseResult.SUCCESS;
            jsonObject.put("statusDesc", BaseResult.SUCCESS_DESC);
        }else {
            status = Response.FAIL;
            jsonObject.put("statusDesc", Response.FAIL_MSG);
        }
        jsonObject.put("status", status);
        return jsonObject;
    }

    /**
     * 资金中心 - 充值管理列表
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "确认充值(FIX) 操作", notes = "资金中心->确认充值(FIX) 操作")
    @PostMapping(value = "/rechargeFix")
    public JSONObject rechargeFix(@RequestBody AccountRechargeRequestBean requestBean){

        AccountRechargeRequest copyRequest = new AccountRechargeRequest();
        BeanUtils.copyProperties(requestBean, copyRequest);

        JSONObject jsonObject = new JSONObject();

        //用户ID
        Integer userId = requestBean.getUserId();
        //订单编号
        String nid = requestBean.getNidSearch();
        //订单状态
        String status = requestBean.getStatusSearch();

        if (Validator.isNull(userId)){
            jsonObject.put("status", Response.FAIL);
            jsonObject.put("statusDesc", Response.FAIL_MSG + ":用户ID不能为空!");
            return jsonObject;
        }else if (Validator.isNull(nid)){
            jsonObject.put("status", Response.FAIL);
            jsonObject.put("statusDesc", Response.FAIL_MSG + ":订单编号不能为空!");
            return jsonObject;
        }else if (Validator.isNull(status)){
            jsonObject.put("status", Response.FAIL);
            jsonObject.put("statusDesc", Response.FAIL_MSG + ":订单状态为空!");
            return jsonObject;
        }

        boolean isAccountUpdate = this.rechargeService.updateAccountAfterRecharge(copyRequest);

        if (isAccountUpdate){
            jsonObject.put("status", BaseResult.SUCCESS);
            jsonObject.put("statusDesc", BaseResult.SUCCESS_DESC);
        }else {
            jsonObject.put("status", Response.FAIL);
            jsonObject.put("statusDesc", Response.FAIL_MSG);
        }
        return jsonObject;
    }

    @ApiOperation(value = "充值管理数据导出",notes = "资金中心->充值管理数据导出")
    @PostMapping(value = "/exportAction")
    public void exportAction(HttpServletResponse response, @RequestBody AccountRechargeRequestBean request) throws UnsupportedEncodingException {

        AccountRechargeRequest accountRechargeRequest = new AccountRechargeRequest();
        BeanUtils.copyProperties(request, accountRechargeRequest);

        // currPage<0 为全部,currPage>0 为具体某一页
        accountRechargeRequest.setCurrPage(-1);

        // 表格sheet名称
        String sheetName = "充值管理详情数据";
        // 设置默认查询时间
        if (StringUtils.isEmpty(accountRechargeRequest.getStartDate())) {
            accountRechargeRequest.setStartDate(GetDate.getDate("yyyy-MM-dd"));
        }
        if (StringUtils.isEmpty(accountRechargeRequest.getEndDate())) {
            accountRechargeRequest.setEndDate(GetDate.getDate("yyyy-MM-dd"));
        }

        List<AccountRechargeVO> returnList = null;
        AccountRechargeResponse rechargeResponse = this.rechargeService.queryRechargeList(accountRechargeRequest);

        if (rechargeResponse.getCount() > 0) {
            returnList = CommonUtils.convertBeanList(rechargeResponse.getResultList(), AccountRechargeVO.class);
        }
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        // String[] titles = new String[] { "用户名", "订单号", "充值渠道", "充值银行",
        // "银行卡号", "充值金额", "手续费", "垫付手续费" , "到账金额", "充值状态", "充值平台", "充值时间" };
        String[] titles = new String[] { "序号", "订单号", "用户名", "电子账号", "手机号", "流水号", "资金托管平台", "用户角色", "用户属性（当前）", "用户所属一级分部（当前）", "用户所属二级分部（当前）", "用户所属团队（当前）", "推荐人用户名（当前）", "推荐人姓名（当前）",
                "推荐人所属一级分部（当前）", "推荐人所属二级分部（当前）", "推荐人所属团队（当前）", "充值渠道", "充值类型", "充值银行", "银行卡号", "充值金额", "手续费", "垫付手续费", "到账金额", "充值状态", "充值平台", "充值时间" ,"发送日期" ,"发送时间" ,"系统跟踪号" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (returnList != null && returnList.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < returnList.size(); i++) {
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
                    AccountRechargeVO record = returnList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {
                        cell.setCellValue(record.getNid());
                    } else if (celLength == 2) {
                        cell.setCellValue(record.getUsername());
                    } else if (celLength == 3) {
                        cell.setCellValue(record.getAccountId());
                    }
                    // 手机号
                    else if (celLength == 4) {
                        cell.setCellValue(record.getMobile());
                    }
                    // 流水号
                    else if (celLength == 5) {
                        cell.setCellValue(record.getSeqNo());
                    }
                    // 资金托管平台
                    else if (celLength == 6) {
                        cell.setCellValue(record.getIsBank());
                    }
                    // 用户角色
                    else if (celLength == 7) {
                        cell.setCellValue(record.getRoleId());
                    }
                    // 用户属性（当前）
                    else if (celLength == 8) {
                        if ("0".equals(record.getUserAttribute())) {
                            cell.setCellValue("无主单");
                        } else if ("1".equals(record.getUserAttribute())) {
                            cell.setCellValue("有主单");
                        } else if ("2".equals(record.getUserAttribute())) {
                            cell.setCellValue("线下员工");
                        } else if ("3".equals(record.getUserAttribute())) {
                            cell.setCellValue("线上员工");
                        }
                    }
                    // 用户所属一级分部（当前）
                    else if (celLength == 9) {
                        cell.setCellValue(record.getUserRegionName());
                    }
                    // 用户所属二级分部（当前）
                    else if (celLength == 10) {
                        cell.setCellValue(record.getUserBranchName());
                    }
                    // 用户所属团队（当前）
                    else if (celLength == 11) {
                        cell.setCellValue(record.getUserDepartmentName());
                    }
                    // 推荐人用户名（当前）
                    else if (celLength == 12) {
                        cell.setCellValue(record.getReferrerName());
                    }
                    // 推荐人姓名（当前）
                    else if (celLength == 13) {
                        cell.setCellValue(record.getReferrerTrueName());
                    }
                    // 推荐人所属一级分部（当前）
                    else if (celLength == 14) {
                        cell.setCellValue(record.getReferrerRegionName());
                    }
                    // 推荐人所属二级分部（当前）
                    else if (celLength == 15) {
                        cell.setCellValue(record.getReferrerBranchName());
                    }
                    // 推荐人所属团队（当前）
                    else if (celLength == 16) {
                        cell.setCellValue(record.getReferrerDepartmentName());
                    } else if (celLength == 17) {
                        cell.setCellValue(record.getType());// 充值渠道
                    } else if (celLength == 18) {
                        if (record.getGateType() != null && "B2C".equals(record.getGateType())) {
                            cell.setCellValue("个人网银充值"); // 充值类型
                        } else if (record.getGateType() != null && "B2B".equals(record.getGateType())) {
                            cell.setCellValue("企业网银充值"); // 充值类型
                        } else if (record.getGateType() != null && "QP".equals(record.getGateType())) {
                            cell.setCellValue("快捷充值"); // 充值类型
                        } else {
                            cell.setCellValue(record.getGateType()); // 充值类型
                        }
                    } else if (celLength == 19) {
                        cell.setCellValue(record.getBankName());
                    } else if (celLength == 20) {
                        cell.setCellValue(record.getCardid());
                    } else if (celLength == 21) {
                        cell.setCellValue(record.getMoney() + "");
                    } else if (celLength == 22) {
                        cell.setCellValue(record.getFee() != null ? (record.getFee() + "") : (0 + ""));
                    } else if (celLength == 23) {
                        cell.setCellValue(record.getDianfuFee() != null ? (record.getDianfuFee() + "") : (0 + ""));
                    } else if (celLength == 24) {
                        cell.setCellValue(record.getBalance() + "");
                    } else if (celLength == 25) {
                        cell.setCellValue(record.getStatus());
                    } else if (celLength == 26) {
                        cell.setCellValue(record.getClient());
                    } else if (celLength == 27) {
                        cell.setCellValue(record.getCreateTime());
                    } else if (celLength == 28) {
                        cell.setCellValue(record.getTxDate());
                    } else if (celLength == 29) {
                        cell.setCellValue(record.getTxTime());
                    } else if (celLength == 30) {
                        cell.setCellValue(record.getBankSeqNo());
                    }
                    // 以下都是空
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);

    }

}
