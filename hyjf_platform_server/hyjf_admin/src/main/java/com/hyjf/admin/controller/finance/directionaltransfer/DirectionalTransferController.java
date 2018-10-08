package com.hyjf.admin.controller.finance.directionaltransfer;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.DirectionalTransferService;
import com.hyjf.am.resquest.admin.DirectionalTransferListRequest;
import com.hyjf.am.vo.admin.AccountDirectionalTransferVO;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(value = "资金中心-定向转账-定向转账",tags = "资金中心-定向转账-定向转账")
@RestController
@RequestMapping(value = "/hyjf-admin/finance/directionaltransfer")
public class DirectionalTransferController extends BaseController {

    @Autowired
    private DirectionalTransferService directionaltransferService;

    /**
     * 定向转账列表
     * @param request
     * @return
     */
    @ApiOperation(value = "查询定向转账列表",notes = "查询定向转账列表")
    @PostMapping(value = "/getdirectionaltransferlist")
    public AdminResult<ListResult<AccountDirectionalTransferVO>> getDirectionalTransferList(@RequestBody DirectionalTransferListRequest request) {
        Integer count = directionaltransferService.getDirectionalTransferCount(request);
        count = (count == null)?0:count;
        List<AccountDirectionalTransferVO> accountDirectionalTransferVOList = directionaltransferService.searchDirectionalTransferList(request);
        return new AdminResult<>(ListResult.build(accountDirectionalTransferVOList,count));
    }


    /**
     * 定向转账列表导出
     * @param request 筛选条件
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "导出定向转账列表",notes = "导出定向转账列表")
    @PostMapping(value = "/directionaltransferlistexport")
    public void exportDirectionalTransferListExcel(@RequestBody DirectionalTransferListRequest request,HttpServletResponse response) throws Exception {
        // currPage<0 为全部,currPage>0 为具体某一页
        request.setCurrPage(-1);

        // 表格sheet名称
        String sheetName = "定向转账列表";
        // 文件名称
        String fileName =
                URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        // 检索列表
        List<AccountDirectionalTransferVO> resultList = directionaltransferService.searchDirectionalTransferList(request);
        String[] titles = new String[] { "序号", "转出账户", "转入账户", "转账订单号", "转账金额", "转账状态", "转账时间", "说明" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (resultList != null && resultList.size() > 0) {
            int sheetCount = 1;
            int rowNum = 0;
            for (int i = 0; i < resultList.size(); i++) {
                rowNum++;
                if (i != 0 && i % 60000 == 0) {
                    sheetCount++;
                    sheet =
                            ExportExcel
                                    .createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }
                // 新建一行
                Row row = sheet.createRow(rowNum);

                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    AccountDirectionalTransferVO accountDirectionalTransfer = resultList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) { // 转出账户
                        cell.setCellValue(accountDirectionalTransfer.getTurnOutUsername());
                    } else if (celLength == 2) { // 转入账户
                        cell.setCellValue(accountDirectionalTransfer.getShiftToUsername());
                    } else if (celLength == 3) { // 转账订单号
                        cell.setCellValue(accountDirectionalTransfer.getOrderId());
                    } else if (celLength == 4) {// 转账金额
                        cell.setCellValue(String.valueOf(accountDirectionalTransfer.getTransferAccountsMoney()));
                    } else if (celLength == 5) {// 转账状态
                        if (accountDirectionalTransfer.getTransferAccountsState() == 0) {
                            cell.setCellValue("转账中");
                        } else if (accountDirectionalTransfer.getTransferAccountsState() == 1) {
                            cell.setCellValue("成功");
                        } else if (accountDirectionalTransfer.getTransferAccountsState() == 2) {
                            cell.setCellValue("失败");
                        }
                    } else if (celLength == 6) {// 转账时间
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        cell.setCellValue(df.format(accountDirectionalTransfer.getTransferAccountsTime()));
                    } else if (celLength == 7) {// 说明
                        cell.setCellValue(accountDirectionalTransfer.getRemark());
                    }
                }
            }
        }

        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);

    }
}
