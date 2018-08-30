/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.bankjournal;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.service.BankJournalService;
import com.hyjf.am.resquest.admin.BankEveRequest;
import com.hyjf.am.vo.admin.BankEveVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * @author zdj
 * @version BankAleveController, v0.1 2018/7/20 15:16
 * 资金中心->银行账务明细
 */

@Api(value = "资金中心-银行交易明细",tags = "资金中心-银行交易明细")
@RestController
@RequestMapping("/hyjf-admin/bankeve")
public class BankJournalController {
    @Autowired
    private BankJournalService bankJournalService;

    @ApiOperation(value = "银行交易明细", notes = "银行交易明细列表查询")
    @PostMapping(value = "/bankalevelist")
    @ResponseBody
    public JSONObject getBankaleveList(@RequestBody BankEveRequest bankEveRequest){
        JSONObject jsonObject = new JSONObject();

        List<BankEveVO> bankEveList =bankJournalService.queryBankEveList(bankEveRequest);
        String status="error";
        String statusDesc = "未检索到相应的列表数据";
        if(null!=bankEveList&&bankEveList.size()>0){
            Integer count = bankEveList.size();
            jsonObject.put("count",count);
            jsonObject.put("record",bankEveList);
            jsonObject.put("status", "000");
            jsonObject.put("statusDesc", "查询银行交易明细成功");

        }
        jsonObject.put("status",status);
        jsonObject.put("statusDesc",statusDesc);
        return jsonObject;
    }
    /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     *
     * @param bankEveRequest
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "银行交易明细", notes = "银行交易明细导出")
    @PostMapping(value = "/exportbankeeve")
    public void exportBankeeveList( HttpServletResponse response, @RequestBody BankEveRequest bankEveRequest) throws UnsupportedEncodingException {
        // 表格sheet名称
        String sheetName = "银行账务明细";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 需要输出的结果列表
        List<BankEveVO> bankEveList =bankJournalService.queryBankEveList(bankEveRequest);
        String[] titles = new String[] { "序号", "发送方标识码", "系统跟踪号", "交易传输时间", "主账号", "交易金额", "交易金额符号","消息类型",
                "交易类型码", "订单号", "内部交易流水号", "内部保留域", "冲正撤销标志", "主机交易类型"};
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        if (bankEveList != null && bankEveList.size() > 0) {
            int sheetCount = 1;
            int rowNum = 0;
            for (int i = 0; i < bankEveList.size(); i++) {
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
                    BankEveVO bankeve = bankEveList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {// 发送方标识码
                        cell.setCellValue(bankeve.getForcode());
                    } else if (celLength == 2) {// 系统跟踪号
                        cell.setCellValue(bankeve.getSeqno());
                    } else if (celLength == 3) {// 交易传输时间
                        cell.setCellValue(bankeve.getCendt()+"");
                    }else if (celLength == 4) {// 主账号
                        cell.setCellValue(bankeve.getCardnbr());
                    } else if (celLength == 5) {// 交易金额
                        cell.setCellValue(bankeve.getAmount()+"");
                    }else if (celLength == 6) {// 交易金额符号
                        cell.setCellValue(bankeve.getCrflag());
                    } else if (celLength == 7) {// 消息类型
                        cell.setCellValue(bankeve.getMsgtype());
                    }else if (celLength == 8) {// 交易类型码
                        cell.setCellValue(bankeve.getProccode());
                    }else if (celLength == 9) {// 订单号
                        cell.setCellValue(bankeve.getOrderno());
                    }else if (celLength == 10) {// 内部交易流水号
                        cell.setCellValue(bankeve.getTranno());
                    }else if (celLength == 11) {// 关联交易流水号
                        cell.setCellValue(bankeve.getReserved());
                    }else if (celLength == 12) {// 内部保留域
                        cell.setCellValue(bankeve.getReserved());
                    }else if (celLength == 13) {// 冲正撤销标志
                        cell.setCellValue(bankeve.getRevind());
                    }else if (celLength == 14) {// 主机交易类型
                        cell.setCellValue(bankeve.getTranstype()+"");
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
}
