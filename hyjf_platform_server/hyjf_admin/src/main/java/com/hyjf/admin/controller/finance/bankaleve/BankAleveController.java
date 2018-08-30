/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.bankaleve;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BankAleveService;
import com.hyjf.am.resquest.admin.BankAleveRequest;
import com.hyjf.am.vo.admin.BankAleveVO;
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

@Api(value = "资金中心-银行账务明细",tags = "资金中心-银行账务明细")
@RestController
@RequestMapping("/hyjf-admin/bankaleve")
public class BankAleveController {
    @Autowired
    private BankAleveService bankAleveService;
    /** 权限 */
    public static final String PERMISSIONS = "bankalevelist";

    /**
     * 银行账务明细列表查询
    * @author Zha Daojian
    * @date 2018/8/21 9:56
    * @param bankAleveRequest
    * @return com.alibaba.fastjson.JSONObject
    **/
    @ApiOperation(value = "银行账务明细", notes = "银行账务明细列表查询")
    @PostMapping(value = "/bankalevelist")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public JSONObject getBankaleveList(@RequestBody BankAleveRequest bankAleveRequest){
        JSONObject jsonObject = new JSONObject();
        List<BankAleveVO> bankAleveList =bankAleveService.queryBankAleveList(bankAleveRequest);
        String status="99";
        String statusDesc = "未检索到相应的列表数据";
        if(null!=bankAleveList&&bankAleveList.size()>0){
            Integer count = bankAleveList.size();
            jsonObject.put("count",count);
            jsonObject.put("record",bankAleveList);
            status =  "000";
            statusDesc = "查询银行账务明细成功";
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
     * @param bankAleveRequest
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "银行账务明细", notes = "银行账务明细导出")
    @PostMapping(value = "/exportbankaleve")
    public void exportBankaleveList( HttpServletResponse response, @RequestBody BankAleveRequest bankAleveRequest) throws UnsupportedEncodingException {
        // 表格sheet名称
        String sheetName = "银行账务明细";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 需要输出的结果列表
        List<BankAleveVO> bankAleveList =bankAleveService.queryBankAleveList(bankAleveRequest);
        String[] titles = new String[] { "序号", "银行号", "电子账号", "交易金额", "货币代码", "交易金额符号", "入帐日期","交易日期",
                "自然日期", "交易时间", "交易流水号", "关联交易流水号", "交易类型", "交易描述", "交易后余额","对手交易账号",
                "冲正撤销标志", "交易标识", "系统根踪号", "原交易流水号", "保留域"};
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        if (bankAleveList != null && bankAleveList.size() > 0) {
            int sheetCount = 1;
            int rowNum = 0;
            for (int i = 0; i < bankAleveList.size(); i++) {
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
                    BankAleveVO bankAleve = bankAleveList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {// 银行号
                        cell.setCellValue(bankAleve.getBank());
                    } else if (celLength == 2) {// 电子账号
                        cell.setCellValue(bankAleve.getCardnbr());
                    } else if (celLength == 3) {// 交易金额
                        cell.setCellValue(bankAleve.getAmount()+"");
                    }else if (celLength == 4) {// 货币代码
                        cell.setCellValue(bankAleve.getCurNum());
                    } else if (celLength == 5) {// 交易金额符号
                        cell.setCellValue(bankAleve.getCrflag());
                    }else if (celLength == 6) {// 入帐日期
                        cell.setCellValue(bankAleve.getValdate());
                    } else if (celLength == 7) {// 交易日期
                        cell.setCellValue(bankAleve.getInpdate());
                    }else if (celLength == 8) {// 自然日期
                        cell.setCellValue(bankAleve.getReldate());
                    }else if (celLength == 9) {// 交易时间
                        cell.setCellValue(bankAleve.getInptime());
                    }else if (celLength == 10) {// 交易流水号
                        cell.setCellValue(bankAleve.getTranno());
                    }else if (celLength == 11) {// 关联交易流水号
                        cell.setCellValue(bankAleve.getOriTranno());
                    }else if (celLength == 12) {// 交易类型
                        cell.setCellValue(bankAleve.getTranstype());
                    }else if (celLength == 13) {// 交易描述
                        cell.setCellValue(bankAleve.getDesline());
                    }else if (celLength == 14) {// 交易后余额
                        cell.setCellValue(bankAleve.getCurrBal()+"");
                    }else if (celLength == 15) {// 对手交易账号
                        cell.setCellValue(bankAleve.getForcardnbr());
                    }else if (celLength == 16) {// 冲正撤销标志
                        cell.setCellValue(bankAleve.getRevind());
                    }else if (celLength == 17) {// 交易标识
                        cell.setCellValue(bankAleve.getAccchg());
                    }else if (celLength == 18) {// 系统根踪号
                        cell.setCellValue(bankAleve.getSeqno());
                    }else if (celLength == 19) {// 原交易流水号
                        cell.setCellValue(bankAleve.getOriNum());
                    }else if (celLength == 20) {// 保留域
                        cell.setCellValue(bankAleve.getResv());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
}
