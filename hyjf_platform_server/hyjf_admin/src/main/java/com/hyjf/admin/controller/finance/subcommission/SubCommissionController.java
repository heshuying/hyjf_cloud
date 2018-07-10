/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.subcommission;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.service.SubCommissionService;
import com.hyjf.am.resquest.admin.SubCommissionRequest;
import com.hyjf.am.vo.admin.SubCommissionVO;
import com.hyjf.am.vo.config.ParamNameVO;
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

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: SubCommissionController, v0.1 2018/7/10 9:28
 */
@Api(value = "资金中心-平台账户分佣")
@RestController
@RequestMapping(value = "/hyjf-admin/subcommission")
public class SubCommissionController {

    @Autowired
    private SubCommissionService subCommissionService;

    /**
     * 平台账户分佣
     * @auth sunpeikai
     * @param request 筛选参数
     * @return
     */
    @ApiOperation(value = "平台账户分佣",notes = "平台账户分佣列表查询")
    @PostMapping(value = "/subcommissionlist")
    public JSONObject subCommissionList(@RequestBody SubCommissionRequest request){
        JSONObject result = new JSONObject();
        Integer count = subCommissionService.getSubCommissionCount(request);
        count = (count == null)?0:count;
        result.put("count",count);
        List<SubCommissionVO> subCommissionVOList = subCommissionService.searchSubCommissionList(request);
        result.put("subCommissionVOList",subCommissionVOList);
        return result;
    }

    /**
     * 平台账户分佣导出
     * @auth sunpeikai
     * @param request 筛选参数
     * @return
     */
    @ApiOperation(value = "平台账户分佣导出",notes = "平台账户分佣导出")
    @PostMapping(value = "/subcommissionlistexport")
    public void exportSubCommissionList(HttpServletResponse response, @RequestBody SubCommissionRequest request){
        // currPage<0 为全部,currPage>0 为具体某一页
        request.setCurrPage(-1);
        // 表格sheet名称
        String sheetName = "账户分佣明细";
        List<SubCommissionVO> subCommissionVOList = subCommissionService.searchSubCommissionList(request);
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        String[] titles = new String[] { "序号", "转账订单号", "转出电子账户号", "转账金额", "转入用户名","转入姓名", "转入电子账户号", "转账状态", "转账时间", "操作人", "备注" ,"发送日期" ,"发送时间" ,"系统跟踪号" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        if (subCommissionVOList != null && subCommissionVOList.size() > 0) {
            int sheetCount = 1;
            int rowNum = 0;
            for (int i = 0; i < subCommissionVOList.size(); i++) {
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
                    SubCommissionVO record = subCommissionVOList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {
                        cell.setCellValue(StringUtils.isEmpty(record.getOrderId()) ? StringUtils.EMPTY : record.getOrderId());
                    } else if (celLength == 2) {
                        cell.setCellValue(StringUtils.isEmpty(record.getAccountId()) ? StringUtils.EMPTY : record.getAccountId());
                    } else if (celLength == 3) {
                        cell.setCellValue(record.getAccount() == null ? "0" : record.getAccount().toString());
                    } else if (celLength == 4) {
                        cell.setCellValue(StringUtils.isEmpty(record.getReceiveUserName()) ? StringUtils.EMPTY : record.getReceiveUserName());
                    } else if (celLength == 5) {
                        cell.setCellValue(StringUtils.isEmpty(record.getTruename()) ? StringUtils.EMPTY : record.getTruename());
                    } else if (celLength == 6) {
                        cell.setCellValue(StringUtils.isEmpty(record.getReceiveAccountId()) ? StringUtils.EMPTY : record.getReceiveAccountId());
                    } else if (celLength == 7) {
                        // 转账状态
                        List<ParamNameVO> transferStatus = this.subCommissionService.searchParamNameList("FS_TRANSFER_STATUS");
                        for (int j = 0; j < transferStatus.size(); j++) {
                            if (transferStatus.get(j).getNameCd().equals(String.valueOf(record.getTradeStatus()))) {
                                cell.setCellValue(transferStatus.get(j).getName());
                            }
                        }
                    } else if (celLength == 8) {
                        if (record.getCreateTime() == null) {
                            cell.setCellValue("");
                        } else {
                            cell.setCellValue(GetDate.dateToString2(record.getCreateTime()));
                        }
                    } else if (celLength == 9) {
                        cell.setCellValue(StringUtils.isEmpty(record.getCreateUserName()) ? StringUtils.EMPTY : record.getCreateUserName());
                    } else if (celLength == 10) {
                        cell.setCellValue(StringUtils.isEmpty(record.getRemark()) ? StringUtils.EMPTY : record.getRemark());
                    } else if (celLength == 11) {
                        String tmpTxDate = record.getTxDate().toString();
                        cell.setCellValue(StringUtils.isEmpty(record.getTxDate().toString()) ? StringUtils.EMPTY : tmpTxDate.substring(0, 4) + "-" + tmpTxDate.substring(4, 6) + "-" + tmpTxDate.substring(6, 8));
                    } else if (celLength == 12) {
                        String tmpTxTime = String.format("%06d", record.getTxTime());
                        cell.setCellValue(StringUtils.isEmpty(record.getTxTime().toString()) ? StringUtils.EMPTY : tmpTxTime.substring(0, 2) + ":" + tmpTxTime.substring(2, 4) + ":" + tmpTxTime.substring(4, 6));
                    } else if (celLength == 13) {
                        cell.setCellValue(StringUtils.isEmpty(record.getSeqNo()) ? StringUtils.EMPTY : record.getSeqNo());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }

    /**
     * 查询发起账户分佣所需的detail信息
     * @auth sunpeikai
     * @param userId
     * @return
     */
    @ApiOperation(value = "发起账户分佣",notes = "发起账户分佣所需的detail信息")
    @PostMapping(value = "/searchdetails")
    public JSONObject searchDetails(@RequestHeader(value = "userId")Integer userId){
        return subCommissionService.searchDetails(userId);
    }

    /**
     * 发起账户分佣
     * @auth sunpeikai
     * @param request 插入数据参数
     * @return
     */
    @ApiOperation(value = "发起账户分佣",notes = "发起账户分佣")
    @PostMapping(value = "/subcommission")
    public JSONObject subCommission(@RequestHeader(value = "userId")Integer loginUserId,@RequestBody SubCommissionRequest request){
        JSONObject jsonObject = subCommissionService.subCommission(loginUserId,request);
        return jsonObject;
    }
}
