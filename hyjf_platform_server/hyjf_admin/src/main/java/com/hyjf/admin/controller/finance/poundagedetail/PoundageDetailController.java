/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.poundagedetail;

import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.PoundageDetailService;
import com.hyjf.admin.service.PoundageService;
import com.hyjf.am.resquest.admin.AdminPoundageDetailRequest;
import com.hyjf.am.vo.admin.PoundageCustomizeVO;
import com.hyjf.am.vo.admin.PoundageDetailVO;
import com.hyjf.am.vo.admin.PoundageLedgerVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.ExportExcel;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: PoundageDetailController, v0.1 2018/9/7 9:53
 */
@Api(value = "资金中心-手续费分账详细信息(二级页面)",tags = "资金中心-手续费分账详细信息(二级页面)")
@RestController
@RequestMapping(value = "/hyjf-admin/finance/poundagedetail")
public class PoundageDetailController extends BaseController {
    @Autowired
    private PoundageDetailService poundageDetailService;
    @Autowired
    private PoundageService poundageService;
    @Autowired
    private AdminCommonService adminCommonService;

    @ApiOperation(value = "手续费分账详细信息列表",notes = "手续费分账详细信息列表")
    @PostMapping(value = "/poundagedetaillist")
    public AdminResult poundageDetailList(HttpServletRequest request, @RequestBody AdminPoundageDetailRequest poundageDetailRequest){
        Map<String,Object> result = new HashMap<>();
        Integer loginUserId = Integer.valueOf(getUser(request).getId());
        PoundageCustomizeVO poundageCustomizeVO = poundageService.getPoundageById(loginUserId,poundageDetailRequest.getPoundageId());
        result.put("poundage",poundageCustomizeVO);
        // 查询明细对应的手续费配置项
        PoundageLedgerVO poundageLedgerVO = new PoundageLedgerVO();
        if(poundageCustomizeVO.getLedgerId()!=null) {
            poundageLedgerVO = poundageDetailService.getPoundageLedgerById(poundageCustomizeVO.getLedgerId());
        }
        result.put("poundageLedger", poundageLedgerVO);
        // 设置明细查询条件
        poundageDetailRequest.setLedgerIdSer(poundageCustomizeVO.getLedgerId());
        if(poundageDetailRequest.getLedgerTimeSer()==null) {
            poundageDetailRequest.setLedgerTimeSer(Integer.parseInt(poundageCustomizeVO.getPoundageTime()));
        }
        Integer count = poundageDetailService.getPoundageDetailCount(poundageDetailRequest);
        count = (count == null)?0:count;
        List<PoundageDetailVO> poundageDetailVOList = poundageDetailService.searchPoundageDetailList(poundageDetailRequest);
        result.put("count",count);
        result.put("poundageDetail",poundageDetailVOList);
        return new AdminResult<>(result);
    }

    /**
     * 获取项目类型下拉框数据
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "项目类型下拉框数据",notes = "项目类型下拉框数据")
    @PostMapping(value = "/getprojecttype")
    public AdminResult<ListResult<DropDownVO>> getSelectorData(){
        List<DropDownVO> projectType = adminCommonService.selectProjectType();
        return new AdminResult<>(ListResult.build(projectType,0));
    }
    /**
     * 导出手续费明细列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "导出手续费明细列表",notes = "导出手续费明细列表")
    @PostMapping(value = "/exportpoundagedetaillist")
    public void exportPoundageDetailList(HttpServletRequest request, HttpServletResponse response, @RequestBody AdminPoundageDetailRequest poundageDetailRequest) throws UnsupportedEncodingException {
        Integer loginUserId = Integer.valueOf(getUser(request).getId());
        PoundageCustomizeVO poundageCustomizeVO = poundageService.getPoundageById(loginUserId,poundageDetailRequest.getPoundageId());
        // 表格sheet名称
        String sheetName = "手续费分账明细";
        // 查询明细对应的手续费配置项
        PoundageLedgerVO poundageLedgerCustomize = this.poundageDetailService.getPoundageLedgerById(poundageCustomizeVO.getLedgerId());
        // 根据手续费配置id和分账时间段查询对应的手续费明细
        poundageDetailRequest.setLedgerIdSer(poundageCustomizeVO.getLedgerId());
        poundageDetailRequest.setLedgerTimeSer(Integer.parseInt(poundageCustomizeVO.getPoundageTime()));
        List<PoundageDetailVO> recordList = this.poundageDetailService.searchPoundageDetailList(poundageDetailRequest);
        // excel详细信息
        String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        String[] titles = new String[]{"序号", "项目编号", "项目类型", "放款/还款时间", "投资人", "投资人分公司",
                "分账类型", "分账来源", "服务费分账比例", "债转服务费分账比例", "管理费分账比例", "分账金额",
                "收款方用户名", "收款方姓名", "收款方电子帐号", "分账状态", "实际分账时间"};
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        if (recordList != null && recordList.size() > 0) {
            int sheetCount = 1;
            int rowNum = 0;
            for (int i = 0; i < recordList.size(); i++) {
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
                    PoundageDetailVO bean = recordList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    //序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    //项目编号
                    if (celLength == 1) {
                        cell.setCellValue(bean.getBorrowNid());
                    }
                    //项目类型
                    if (celLength == 2) {
                        cell.setCellValue(bean.getBorrowType());
                    }
                    //放款/还款时间
                    if (celLength == 3) {
                        Date addTime = bean.getCreateTime();
                        cell.setCellValue(addTime == null ? "" : GetDate.dateToString(addTime));
                    }
                    //投资人
                    if (celLength == 4) {
                        cell.setCellValue(bean.getUsernname());
                    }
                    //分账金额
                    if (celLength == 11) {
                        BigDecimal amount = bean.getAmount();
                        cell.setCellValue(amount != null ? amount.toString() : "");
                    }
                    /*根据详情的ledger_id关联相应的手续费配置项 start*/
                    //分账类型
                    if (celLength == 6) {
                        cell.setCellValue(getTypeStr(poundageLedgerCustomize.getType()));
                    }
                    //投资人分公司
                    if (celLength == 5) {
                        cell.setCellValue(poundageLedgerCustomize.getInvestorCompany());
                    }
                    //分账来源
                    if (celLength == 7) {
                        cell.setCellValue(getSourceStr(poundageLedgerCustomize.getSource()));
                    }
                    //服务费分账比例
                    if (celLength == 8) {
                        cell.setCellValue(getRatio(poundageLedgerCustomize.getServiceRatio()));
                    }
                    //债转服务费分账比例
                    if (celLength == 9) {
                        cell.setCellValue(getRatio(poundageLedgerCustomize.getCreditRatio()));
                    }
                    //管理费分账比例
                    if (celLength == 10) {
                        cell.setCellValue(getRatio(poundageLedgerCustomize.getManageRatio()));
                    }
                    //收款人用户名
                    if (celLength == 12) {
                        cell.setCellValue(poundageLedgerCustomize.getUsername());
                    }
                    //收款人姓名
                    if (celLength == 13) {
                        cell.setCellValue(poundageLedgerCustomize.getTruename());
                    }
                    //收款人电子帐号
                    if (celLength == 14) {
                        cell.setCellValue(poundageLedgerCustomize.getAccount());
                    }
                    /*根据详情的ledger_id关联相应的手续费配置项 end*/
                    /*手续费分账项 start*/
                    //分账状态
                    if (celLength == 15) {
                        cell.setCellValue(PoundageCustomizeVO.getStatusStr(poundageCustomizeVO.getStatus()));
                    }
                    //实际分账时间
                    if (celLength == 16) {
                        Integer ledgerTime = poundageCustomizeVO.getAddTime();
                        cell.setCellValue(ledgerTime == null ? "" : GetDate.timestamptoStrYYYYMMDDHHMMSS(ledgerTime));
                    }
                    /*手续费分账项 end*/
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }

    /**
     * 获取分账比例
     *
     * @param ratio
     * @return
     */
    private String getRatio(BigDecimal ratio) {
        if (ratio == null || ratio.compareTo(new BigDecimal(0.00)) == 0) {
            return "--";
        }
        return ratio.toString();
    }

    private static final int TYPE_INVEST = 1;//"分账类型 1:按投资人分账";
    private static final int TYPE_LOAN = 2;//"分账类型 2:按借款人分账";
    private static final String TYPE_INVEST_STR = "按投资人分账";
    private static final String TYPE_LOAN_STR = "按借款人分账";

    private static final String SOURCE_ALL = "0";//"分账来源  0:全部";
    private static final String SOURCE_SERVICE = "1";//"分账来源 1:服务费";
    private static final String SOURCE_CREDIT = "2"; //"分账来源 2:债转服务费";
    private static final String SOURCE_MANAGE = "3";//"分账来源 3:管理费";
    private static final String SOURCE_ALL_STR = "全部";
    private static final String SOURCE_SERVICE_STR = "服务费";
    private static final String SOURCE_CREDIT_STR = "债转服务费";
    private static final String SOURCE_MANAGE_STR = "管理费";

    /**
     * 获取分账类型信息
     *
     * @param type
     * @return
     * @author wgx
     */
    private static String getTypeStr(int type) {
        switch (type) {
            case TYPE_INVEST:
                return TYPE_INVEST_STR;
            case TYPE_LOAN:
                return TYPE_LOAN_STR;
        }
        return "";
    }

    /**
     * 获取分账来源信息
     *
     * @param source
     * @return
     * @author wgx
     */
    private static String getSourceStr(String source) {
        switch (source) {
            case SOURCE_ALL:
                return SOURCE_ALL_STR;
            case SOURCE_SERVICE:
                return SOURCE_SERVICE_STR;
            case SOURCE_CREDIT:
                return SOURCE_CREDIT_STR;
            case SOURCE_MANAGE:
                return SOURCE_MANAGE_STR;
        }
        return "";
    }

}
