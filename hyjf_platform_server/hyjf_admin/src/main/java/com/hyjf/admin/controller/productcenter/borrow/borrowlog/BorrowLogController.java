package com.hyjf.admin.controller.productcenter.borrow.borrowlog;

import com.hyjf.admin.beans.BorrowLogBean;
import com.hyjf.admin.beans.BorrowRepaymentInfoBean;
import com.hyjf.admin.beans.request.BorrowLogRequsetBean;
import com.hyjf.admin.beans.request.BorrowRecoverRequestBean;
import com.hyjf.admin.beans.request.BorrowRepaymentInfoRequsetBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowLogService;
import com.hyjf.admin.service.BorrowRecoverService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.am.resquest.admin.BorrowLogRequset;
import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.resquest.admin.BorrowRepaymentInfoRequset;
import com.hyjf.am.vo.admin.BorrowLogCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRecoverCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version BorrowLogController, v0.1 2018/7/11 9:50
 */

@Api(value = "借款操作日志",tags ="借款操作日志")
@RestController
@RequestMapping("/hyjf-admin/borrow/borrowlog")
public class BorrowLogController extends BaseController {

    /** 查看权限 */
    public static final String PERMISSIONS = "borrowlog";
    @Autowired
    private BorrowLogService borrowLogService;
    /**
     * 画面初始化
     *
     * @param request
     */
    @ApiOperation(value = "借款操作日志", notes = "借款操作日志页面查询初始化")
    @PostMapping(value = "/searchAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BorrowLogBean> searchAction(HttpServletRequest request, @RequestBody @Valid BorrowLogRequsetBean form) {
        BorrowLogRequset copyForm=new BorrowLogRequset();
        BeanUtils.copyProperties(form, copyForm);
        BorrowLogBean bean = borrowLogService.selectBorrowLogList(copyForm);
        bean.setBorrowStatusList(ConvertUtils.convertParamMapToDropDown(CacheUtil.getParamNameMap("BORROW_STATUS")));
        AdminResult<BorrowLogBean> result=new AdminResult<BorrowLogBean> ();
        result.setData(bean);
        return result;
    }
    /**
     * 带条件导出
     * 1.无法指定相应的列的顺序，
     * 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet
     * 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     * @param request
     * @return 带条件导出
     */
    @ApiOperation(value = "借款操作日志列表导出", notes = "带条件导出EXCEL")
    @PostMapping(value = "/exportAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid BorrowLogRequsetBean form) throws Exception {
        BorrowLogRequset copyForm=new BorrowLogRequset();
        BeanUtils.copyProperties(form, copyForm);
        // 表格sheet名称
        String sheetName = "借款操作日志列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 查询
        List<BorrowLogCustomizeVO> resultList = this.borrowLogService.exportBorrowLogList(copyForm);
        // 列头
        String[] titles = new String[] { "序号", "借款编号", "项目状态", "修改类型","操作人", "操作时间", "备注" };
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
                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }
                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    BorrowLogCustomizeVO record = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);


                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 借款编号
                    else if (celLength == 1) {
                        cell.setCellValue(record.getBorrowNid());
                    }
                    // 项目状态
                    else if (celLength == 2) {
                        cell.setCellValue(record.getBorrowStatus());
                    }
                    // 修改类型
                    else if (celLength == 3) {
                        cell.setCellValue(record.getType());
                    }
                    // 操作人
                    else if (celLength == 4) {
                        cell.setCellValue(record.getCreateUserName());
                    }
                    // 操作时间
                    else if (celLength == 5) {
                        cell.setCellValue(record.getCreateTime());
                    }
                    // 备注
                    else if (celLength == 6) {
                        cell.setCellValue(record.getRemark());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
}
