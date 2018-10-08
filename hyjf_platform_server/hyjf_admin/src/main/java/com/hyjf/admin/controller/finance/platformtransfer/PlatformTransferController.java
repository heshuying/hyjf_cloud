/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.platformtransfer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.PlatformTransferService;
import com.hyjf.am.resquest.admin.PlatformTransferListRequest;
import com.hyjf.am.resquest.admin.PlatformTransferRequest;
import com.hyjf.am.vo.admin.AccountRechargeVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: PlatformTransferController, v0.1 2018/7/9 10:13
 */
@Api(value = "资金中心-转账管理-平台转账",tags = "资金中心-转账管理-平台转账")
@RestController
@RequestMapping(value = "/hyjf-admin/finance/platformtransfer")
public class PlatformTransferController extends BaseController {

    @Autowired
    private PlatformTransferService platformTransferService;

    /**
     * 平台转账-查询转账列表
     * @auth sunpeikai
     * @param request 查询条件
     * @return
     */
    @ApiOperation(value = "平台转账-查询转账列表",notes = "平台转账-查询转账列表")
    @PostMapping(value = "/transferlist")
    public AdminResult<ListResult<AccountRechargeVO>> transferList(@RequestBody PlatformTransferListRequest request){
        Integer count = platformTransferService.getPlatformTransferCount(request);
        count = (count == null)?0:count;
        List<AccountRechargeVO> accountRechargeVOList = platformTransferService.searchPlatformTransferList(request);
        return new AdminResult<>(ListResult.build(accountRechargeVOList,count));
    }

    /**
     * 根据userName检查是否可以平台转账
     * @auth sunpeikai
     * @param requestMap 请求参数
     * @return
     */
    @ApiOperation(value = "平台转账-根据username查询用户信息",notes = "平台转账-根据username查询用户信息")
    @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String")
    @PostMapping(value = "/getuserinfobyusername")
    public AdminResult getUserInfoByUserName(@RequestBody Map<String,String> requestMap){
        String userName = requestMap.get("userName");
        logger.info("getuserinfobyusername======userName=[{}]",userName);
        JSONObject result = new JSONObject();
        if(StringUtils.isNotEmpty(userName)){
            result = platformTransferService.checkTransfer(userName);
        }else{
            logger.error("getUserInfoByUserName,,,,userName=null");
            result.put("status","99");
            result.put("info","用户账号不能为空");
        }
        if("0".equals(result.get("status"))){
            return new AdminResult(SUCCESS,SUCCESS_DESC);
        }else{
            return new AdminResult(FAIL,result.getString("info"));
        }
    }

    @ApiOperation(value = "平台转账-查询商户账户余额",notes = "平台转账-查询商户账户余额")
    @PostMapping(value = "/tohandrecharge")
    public AdminResult toHandRecharge(HttpServletRequest request){
        Integer userId = Integer.valueOf(getUser(request).getId());
        logger.info("toHandRecharge=========userId:[{}]",userId);
        BigDecimal balance = platformTransferService.getAccountBalance(userId);
        Map<String,BigDecimal> map = new HashMap<>();
        map.put("balance",balance);
        return new AdminResult(map);
    }

    /**
     * 平台转账
     * @auth sunpeikai
     * @param request 传参
     * @return
     */
    @ApiOperation(value = "平台转账",notes = "平台转账")
    @PostMapping(value = "/handrecharge")
    public AdminResult handRecharge(HttpServletRequest request, @RequestBody PlatformTransferRequest platformTransferRequest){
        Integer userId = Integer.valueOf(getUser(request).getId());
        JSONObject result = platformTransferService.handRecharge(userId,request,platformTransferRequest);
        if("0".equals(result.get("status"))){
            return new AdminResult(SUCCESS,result.getString("result"));
        }else{
            return new AdminResult(FAIL,result.getString("result"));
        }
    }

    @ApiOperation(value = "平台转账-导出excel",notes = "平台转账-导出excel")
    @PostMapping(value = "/platformtransferlist")
    public void exportPlatformTransferList(HttpServletResponse response, @RequestBody PlatformTransferListRequest platformTransferListRequest) throws UnsupportedEncodingException {
        // currPage<0 为全部,currPage>0 为具体某一页
        platformTransferListRequest.setCurrPage(-1);
        // 表格sheet名称
        String sheetName = "平台转账详情数据";
        // 设置默认查询时间
        if (StringUtils.isEmpty(platformTransferListRequest.getStartDate())) {
            platformTransferListRequest.setStartDate(GetDate.getDate("yyyy-MM-dd"));
        }
        if (StringUtils.isEmpty(platformTransferListRequest.getEndDate())) {
            platformTransferListRequest.setEndDate(GetDate.getDate("yyyy-MM-dd"));
        }
        List<AccountRechargeVO> accountRechargeVOList = platformTransferService.searchPlatformTransferList(platformTransferListRequest);
        //RechargeCustomize rechargeCustomize = new RechargeCustomize();
        //BeanUtils.copyProperties(platformTransferListRequest, rechargeCustomize);
        //rechargeCustomize.setTypeSearch("ADMIN");
        //List<RechargeCustomize> rechargeCustomizes = this.rechargeService.queryRechargeList(rechargeCustomize);
        String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        // String[] titles = new String[] { "用户名", "订单号", "充值渠道", "充值银行",
        // "银行卡号", "充值金额", "手续费", "垫付手续费" , "到账金额", "充值状态", "充值平台", "充值时间" };
        String[] titles = new String[] { "序号", "订单号", "用户名", "手机号", "转账金额", "可用余额", "转账状态", "转账时间", "备注" ,"发送日期" ,"发送时间" ,"系统跟踪号" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (accountRechargeVOList != null && accountRechargeVOList.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < accountRechargeVOList.size(); i++) {
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
                    AccountRechargeVO record = accountRechargeVOList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {
                        cell.setCellValue(record.getNid());
                    } else if (celLength == 2) {
                        cell.setCellValue(record.getUsername());
                    }
                    // 手机号
                    else if (celLength == 3) {
                        cell.setCellValue(record.getMobile());
                    } // 转账金额
                    else if (celLength == 4) {
                        cell.setCellValue(record.getMoney() + "");
                    }
                    // 可用余额
                    else if (celLength == 5) {
                        cell.setCellValue(record.getBalance() + "");
                    }
                    // 转账状态
                    else if (celLength == 6) {
                        String status = "";
                        if(record.getStatus() == 1){
                            status = "转账中";
                        }else if(record.getStatus() == 2){
                            status = "成功";
                        }else{
                            status = "失败";
                        }
                        cell.setCellValue(status);
                    }
                    // 转账时间
                    else if (celLength == 7) {
                        cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(record.getCreateTime()));
                    }
                    // 备注
                    else if (celLength == 8) {
                        cell.setCellValue(record.getRemark());
                    }
                    else if (celLength == 9) {
                        String dateStr = String.valueOf(record.getTxDate());
                        String year = dateStr.substring(0,4);
                        String month = dateStr.substring(4,6);
                        String day = dateStr.substring(6,8);
                        cell.setCellValue(year + "-" + month + "-" + day);
                    }
                    else if (celLength == 10) {
                        cell.setCellValue(record.getTxTimeStr());
                    }
                    else if (celLength == 11) {
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
