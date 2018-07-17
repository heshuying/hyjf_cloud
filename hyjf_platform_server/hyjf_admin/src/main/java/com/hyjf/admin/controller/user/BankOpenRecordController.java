/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.service.BankOpenRecordService;
import com.hyjf.am.resquest.user.AccountRecordRequest;
import com.hyjf.am.resquest.user.BankAccountRecordRequest;
import com.hyjf.am.vo.user.BankOpenAccountRecordVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.AsteriskProcessUtil;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version RegistRecordController, v0.1 2018/6/23 15:16
 */

@Api(value = "会员中心-开户记录")
@RestController
@RequestMapping("/hyjf-admin/bankOpenRecord")
public class BankOpenRecordController {
    @Autowired
    private BankOpenRecordService bankOpenRecordService;


    @ApiOperation(value = "开户记录", notes = "开户记录页面初始化")
    @PostMapping(value = "/bankOpenRecordInit")
    @ResponseBody
    public JSONObject userManagerInit() {
        JSONObject jsonObject = new JSONObject();
        // 用户属性
        Map<String, String> userPropertys = CacheUtil.getParamNameMap("USER_PROPERTY");
        // 注册平台
        Map<String, String> registPlat = CacheUtil.getParamNameMap("CLIENT");
        jsonObject.put("userPropertys", userPropertys);
        jsonObject.put("registPlat", registPlat);
        return jsonObject;

    }

    //会员管理列表查询
    @ApiOperation(value = "开户记录", notes = "汇付银行开户记录查询")
    @PostMapping(value = "/bankOpenRecordAccount")
    @ResponseBody
    public JSONObject bankOpenRecordAccount(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map){
        JSONObject jsonObject = new JSONObject();
        AccountRecordRequest accountRecordRequest =setRequese(map);
        List<BankOpenAccountRecordVO> bankOpenRecordServiceAccountRecordList =bankOpenRecordService.findAccountRecordList(accountRecordRequest);
        String status="error";
        if(null!=bankOpenRecordServiceAccountRecordList&&bankOpenRecordServiceAccountRecordList.size()>0){
            jsonObject.put("record",bankOpenRecordServiceAccountRecordList);
            status="success";
        }
        jsonObject.put("status",status);
        return jsonObject;
    }
    @ApiOperation(value = "开户记录", notes = "江西银行开户记录查询")
    @PostMapping(value = "/bankOpenRecordBankAccount")
    @ResponseBody
    public JSONObject bankOpenRecordBankAccount(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map){
        JSONObject jsonObject = new JSONObject();
        BankAccountRecordRequest registerRcordeRequest =setBankRequese(map);
        List<BankOpenAccountRecordVO> bankOpenRecordServiceAccountRecordList=bankOpenRecordService.findBankAccountRecordList(registerRcordeRequest);
        String status="error";
        if(null!=bankOpenRecordServiceAccountRecordList&&bankOpenRecordServiceAccountRecordList.size()>0){
            jsonObject.put("record",bankOpenRecordServiceAccountRecordList);
            status="success";
        }
        jsonObject.put("status",status);
        return jsonObject;
    }
    private AccountRecordRequest  setRequese(Map<String,Object> mapParam){
        AccountRecordRequest accountRecordRequest = new AccountRecordRequest();
        if(null!=mapParam&&mapParam.size()>0){
            if(mapParam.containsKey("openAccountPlat")){
                accountRecordRequest.setOpenAccountPlat(mapParam.get("openAccountPlat").toString());
            }
            if(mapParam.containsKey("userName")){
                accountRecordRequest.setUserName(mapParam.get("userName").toString());
            }
            if(mapParam.containsKey("userProperty")){
                accountRecordRequest.setUserProperty(mapParam.get("userProperty").toString());
            }
            if(mapParam.containsKey("idCard")){
                accountRecordRequest.setIdCard(mapParam.get("idCard").toString());
            }
            if(mapParam.containsKey("realName")){
                accountRecordRequest.setRealName(mapParam.get("realName").toString());
            }
            if(mapParam.containsKey("openTimeStart")){
                accountRecordRequest.setOpenTimeStart(mapParam.get("openTimeStart").toString());
            }
            if(mapParam.containsKey("openTimeEnd")){
                accountRecordRequest.setOpenTimeEnd(mapParam.get("openTimeEnd").toString());
            }
            if (mapParam.containsKey("limit")&& StringUtils.isNotBlank(mapParam.get("limit").toString())) {
                accountRecordRequest.setLimit(Integer.parseInt(mapParam.get("limit").toString()));
            }
        }
        return accountRecordRequest;
    }

    private BankAccountRecordRequest setBankRequese(Map<String,Object> mapParam){
        BankAccountRecordRequest bankAccountRecordRequest = new BankAccountRecordRequest();
        if(null!=mapParam&&mapParam.size()>0){
            if(mapParam.containsKey("customerAccount")){
                bankAccountRecordRequest.setCustomerAccount(mapParam.get("customerAccount").toString());
            }
            if(mapParam.containsKey("mobile")){
                bankAccountRecordRequest.setMobile(mapParam.get("mobile").toString());
            }
            if(mapParam.containsKey("openAccountPlat")){
                bankAccountRecordRequest.setOpenAccountPlat(mapParam.get("openAccountPlat").toString());
            }
            if(mapParam.containsKey("userName")){
                bankAccountRecordRequest.setUserName(mapParam.get("userName").toString());
            }
            if(mapParam.containsKey("idCard")){
                bankAccountRecordRequest.setIdCard(mapParam.get("idCard").toString());
            }
            if(mapParam.containsKey("realName")){
                bankAccountRecordRequest.setRealName(mapParam.get("realName").toString());
            }
            if(mapParam.containsKey("openTimeStart")){
                bankAccountRecordRequest.setOpenTimeStart(mapParam.get("openTimeStart").toString());
            }
            if(mapParam.containsKey("openTimeEnd")){
                bankAccountRecordRequest.setOpenTimeEnd(mapParam.get("openTimeEnd").toString());
            }
            if (mapParam.containsKey("limit")&& StringUtils.isNotBlank(mapParam.get("limit").toString())) {
                bankAccountRecordRequest.setLimit(Integer.parseInt(mapParam.get("limit").toString()));
            }
        }
        return bankAccountRecordRequest;
    }

    /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "开户记录", notes = "汇付银行开户记录导出")
    @PostMapping(value = "/exportaccount")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) throws Exception {
        // 表格sheet名称
        String sheetName = "开户记录";
        // 文件名称
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        AccountRecordRequest accountRecordRequest =setRequese(map);
        // 需要输出的结果列表
        List<BankOpenAccountRecordVO> bankOpenRecordServiceAccountRecordList =bankOpenRecordService.findAccountRecordList(accountRecordRequest);
        String[] titles = new String[] { "序号", "用户名", "姓名", "性别", "年龄", "生日", "户籍所在地", "身份证号码", "开户状态", "汇付账号", "开户平台", "开户时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        if (bankOpenRecordServiceAccountRecordList != null && bankOpenRecordServiceAccountRecordList.size() > 0) {
            int sheetCount = 1;
            int rowNum = 0;
            for (int i = 0; i < bankOpenRecordServiceAccountRecordList.size(); i++) {
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
                    BankOpenAccountRecordVO user = bankOpenRecordServiceAccountRecordList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {// 用户名
                        cell.setCellValue(user.getUserName());
                    } else if (celLength == 2) {// 真实姓名
                        cell.setCellValue(user.getRealName());
                    } else if (celLength == 3) {// 性别
                        if ("1".equals(user.getSex())) {
                            cell.setCellValue("男");
                        } else if ("2".equals(user.getSex())) {
                            cell.setCellValue("女");
                        } else {
                            cell.setCellValue("未知");
                        }
                    } else if (celLength == 4) {// 年龄
                        cell.setCellValue(this.getAge(user.getBirthday()));
                    } else if (celLength == 5) {// 生日
                        cell.setCellValue(user.getBirthday());
                    } /*else if (celLength == 6) {// 户籍所在地
                        cell.setCellValue(accountService.getAreaByIdCard(user.getIdcard()));
                    }*/ else if (celLength == 7) {// 身份证号码
                        cell.setCellValue(AsteriskProcessUtil.getAsteriskedValue(user.getIdCard(), 3,7));
                    }else if (celLength == 8) {// 开户状态
                        cell.setCellValue(user.getAccountStatusName());
                    } else if (celLength == 9) {// 汇付账号
                        cell.setCellValue(user.getAccount());
                    } else if (celLength == 10) {// 开户平台
                        cell.setCellValue(user.getOpenAccountPlat());
                    } else if (celLength == 11) {// 开户时间
                        cell.setCellValue(user.getOpenTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }

    /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "开户记录", notes = "江西银行开户记录导出")
    @PostMapping(value = "/exportbankaccount")
    public void exportBankExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) throws Exception {

        BankAccountRecordRequest registerRcordeRequest =setBankRequese(map);
        // 表格sheet名称
        String sheetName = "江西银行开户记录";
        // 文件名称
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 需要输出的结果列表
        List<BankOpenAccountRecordVO> bankOpenRecordServiceAccountRecordList=bankOpenRecordService.findBankAccountRecordList(registerRcordeRequest);

        String[] titles = new String[] { "序号", "用户名","当前手机号", "姓名", "身份证号码","银行卡号", "银行电子账户", "开户平台", "开户时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        if (bankOpenRecordServiceAccountRecordList != null && bankOpenRecordServiceAccountRecordList.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < bankOpenRecordServiceAccountRecordList.size(); i++) {
                rowNum++;
                if (i != 0 && i % 60000 == 0) {
                    sheetCount++;
                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles,
                            (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }
                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    BankOpenAccountRecordVO user = bankOpenRecordServiceAccountRecordList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    //序号、用户名、当前手机号、姓名、身份证号、银行卡号、银行电子账户、开户平台、开户时间
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {// 用户名
                        cell.setCellValue(user.getUserName());
                    } else if (celLength == 2) {// 用户名
                        cell.setCellValue(user.getMobile());
                    } else if (celLength == 3) {// 真实姓名
                        cell.setCellValue(user.getRealName());
                    } else if (celLength == 4) {// 身份证号码
                        cell.setCellValue(user.getIdCard());
                    } else if (celLength == 5) {// 开户状态
                        cell.setCellValue(user.getAccount());
                    } else if (celLength == 6) {// 汇付账号
                        cell.setCellValue(user.getCustomerAccount());
                    } else if (celLength == 7) {// 开户平台
                        cell.setCellValue(user.getOpenAccountPlat());
                    } else if (celLength == 8) {// 开户时间
                        cell.setCellValue(user.getOpenTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }



    /**
     *
     * @Description:获取年龄
     * @param birthday
     * @return String
     * @exception:
     * @author: xulijie
     * @time:2017年5月3日 下午4:00:48
     */
    public String getAge(String birthday) {
        if (org.apache.commons.lang.StringUtils.isBlank(birthday)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String formatDate = sdf.format(date);
        int age = Integer.parseInt(formatDate) - Integer.parseInt(birthday.substring(0, 4));
        return String.valueOf(age);
    }


}
