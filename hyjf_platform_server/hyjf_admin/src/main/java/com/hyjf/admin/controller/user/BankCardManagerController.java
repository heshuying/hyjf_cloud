/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.service.BankCardManagerService;
import com.hyjf.am.resquest.user.BankCardManagerRequest;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.user.BankcardManagerVO;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version RegistRecordController, v0.1 2018/6/23 15:16
 */

@Api(value = "銀行卡管理")
@RestController
@RequestMapping("/hyjf-admin/bankcardManager")
public class BankCardManagerController {
    @Autowired
    private BankCardManagerService bankCardManagerService;

    @ApiOperation(value = "銀行卡管理", notes = "銀行卡管理页面初始化")
    @PostMapping(value = "/bankCardInit")
    @ResponseBody
    public JSONObject userManagerInit() {
        JSONObject jsonObject = new JSONObject();
        // 银行卡属性
        Map<String, String> bankcardProperty = CacheUtil.getParamNameMap("BANKCARD_PROPERTY");
        // 是否默认
        Map<String, String> bankcardType  = CacheUtil.getParamNameMap("BANKCARD_TYPE");
        List<BanksConfigVO> listBanksConfigVO = bankCardManagerService.selectBankConfigList();
        jsonObject.put("bankcardType", bankcardType);
        jsonObject.put("bankcardProperty", bankcardProperty);
        jsonObject.put("banks", listBanksConfigVO);
        return jsonObject;

    }
    //汇付银行开户銀行卡記錄查询
    @ApiOperation(value = "銀行卡管理", notes = "汇付银行开户銀行卡記錄查询")
    @PostMapping(value = "/bankOpenRecordAccount")
    @ResponseBody
    public JSONObject bankOpenRecordAccount(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map){
        JSONObject jsonObject = new JSONObject();
        BankCardManagerRequest requestBank =setRequese(map);
        List<BankcardManagerVO> bankcardManagerVOList =bankCardManagerService.selectBankCardList(requestBank);
        String status="error";
        if(null!=bankcardManagerVOList&&bankcardManagerVOList.size()>0){
            jsonObject.put("record",bankcardManagerVOList);
            status="success";
        }
        jsonObject.put("status",status);
        return jsonObject;
    }
    @ApiOperation(value = "銀行卡管理", notes = "江西银行开户銀行卡記錄查询")
    @PostMapping(value = "/bankOpenRecordBankAccount")
    @ResponseBody
    public JSONObject bankOpenRecordBankAccount(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map){
        JSONObject jsonObject = new JSONObject();
        BankCardManagerRequest requestBank =setRequese(map);
        List<BankcardManagerVO> bankcardManagerVOList =bankCardManagerService.selectNewBankCardList(requestBank);
        String status="error";
        if(null!=bankcardManagerVOList&&bankcardManagerVOList.size()>0){
            jsonObject.put("record",bankcardManagerVOList);
            status="success";
        }
        jsonObject.put("status",status);
        return jsonObject;
    }
    private BankCardManagerRequest  setRequese(Map<String,Object> mapParam){
        BankCardManagerRequest requestBank = new BankCardManagerRequest();
        if(null!=mapParam&&mapParam.size()>0){
            if(mapParam.containsKey("userName")){
                requestBank.setUserName(mapParam.get("userName").toString());
            }
            if(mapParam.containsKey("userName")){
                requestBank.setUserName(mapParam.get("userName").toString());
            }
            if(mapParam.containsKey("bank")){
                requestBank.setBank(mapParam.get("bank").toString());
            }
            if(mapParam.containsKey("account")){
                requestBank.setAccount(mapParam.get("account").toString());
            }
            if(mapParam.containsKey("realName")){
                requestBank.setRealName(mapParam.get("realName").toString());
            }
            if(mapParam.containsKey("cardProperty")){
                requestBank.setCardProperty(mapParam.get("cardProperty").toString());
            }
            if(mapParam.containsKey("cardType")){
                requestBank.setCardType(mapParam.get("cardType").toString());
            }
            if(mapParam.containsKey("addTimeStart")){
                requestBank.setAddTimeStart(mapParam.get("addTimeStart").toString());
            }
            if(mapParam.containsKey("addTimeEnd")){
                requestBank.setAddTimeEnd(mapParam.get("addTimeEnd").toString());
            }
            if(mapParam.containsKey("mobile")){
                requestBank.setMobile(mapParam.get("mobile").toString());
            }
            if (mapParam.containsKey("limit")&& StringUtils.isNotBlank(mapParam.get("limit").toString())) {
                requestBank.setLimit(Integer.parseInt(mapParam.get("limit").toString()));
            }
        }
        return requestBank;
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
    @ApiOperation(value = "銀行卡管理", notes = "汇付银行开户銀行卡記錄导出")
    @PostMapping(value = "/exportbankcard")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) throws Exception {
        // 封装查询条件
        BankCardManagerRequest requestBank =setRequese(map);
        // 表格sheet名称
        String sheetName = "银行卡管理";
        // 文件名称
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 需要输出的结果列表
        List<BankcardManagerVO> bankcardManagerVOList =bankCardManagerService.selectBankCardList(requestBank);
        String[] titles = new String[] { "序号", "用户名", "银行账号", "所属银行", "是否默认", "银行卡属性", "添加时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (bankcardManagerVOList != null && bankcardManagerVOList.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < bankcardManagerVOList.size(); i++) {
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
                    BankcardManagerVO user = bankcardManagerVOList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {// 用户名
                        cell.setCellValue(user.getUserName());
                    } else if (celLength == 2) {// 银行账号
                        cell.setCellValue(AsteriskProcessUtil.getAsteriskedValue(user.getAccount(),3,7));
                    } else if (celLength == 3) {// 所属银行
                        cell.setCellValue(user.getBank());
                    } else if (celLength == 4) {// 是否默认
                        cell.setCellValue(user.getCardType());
                    } else if (celLength == 5) {// 银行卡属性
                        cell.setCellValue(user.getCardProperty());
                    } else if (celLength == 6) {// 添加时间
                        cell.setCellValue(user.getAddTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }


    /**
     *
     * 导出方法
     * @author pcc
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "銀行卡管理", notes = "江西银行开户銀行卡記錄导出")
    @PostMapping(value = "/exportnewbankcard")
    public void exportExcelNew(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) throws Exception {

        // 表格sheet名称
        String sheetName = "银行卡管理";
        // 文件名称
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 封装查询条件
        BankCardManagerRequest requestBank =setRequese(map);
        // 需要输出的结果列表
        List<BankcardManagerVO> bankcardManagerVOList =bankCardManagerService.selectNewBankCardList(requestBank);
        //序号、用户名、当前手机号、姓名、身份证号、银行卡号、绑卡时间
        String[] titles = new String[] { "序号", "用户名", "当前手机号", "姓名", "身份证号", "银行卡号", "绑卡时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (bankcardManagerVOList != null && bankcardManagerVOList.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < bankcardManagerVOList.size(); i++) {
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
                    BankcardManagerVO user = bankcardManagerVOList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {// 用户名
                        cell.setCellValue(user.getUserName());
                    } else if (celLength == 2) {// 当前手机号
                        cell.setCellValue(user.getMobile());
                    } else if (celLength == 3) {// 姓名
                        cell.setCellValue(user.getRealName());
                    } else if (celLength == 4) {// 身份证号
                        cell.setCellValue(user.getIdcard());
                    } else if (celLength == 5) {// 银行卡号
                        cell.setCellValue(user.getAccount());
                    } else if (celLength == 6) {// 绑卡时间
                        cell.setCellValue(user.getAddTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
}
