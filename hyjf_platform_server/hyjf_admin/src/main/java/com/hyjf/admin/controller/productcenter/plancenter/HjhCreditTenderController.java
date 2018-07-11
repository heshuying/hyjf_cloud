/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter.plancenter;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.HjhCreditTenderService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhCreditTenderResponse;
import com.hyjf.am.resquest.admin.HjhCreditTenderRequest;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.hjh.HjhCreditTenderCustomizeVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author libin
 * @version HjhCreditTenderController.java, v0.1 2018年7月11日 下午2:18:37
 */
@Api(value = "汇计划承接记录列表")
@RestController
@RequestMapping("/hyjf-admin/hjhcredittender")
public class HjhCreditTenderController extends BaseController{
	
	@Autowired
	private HjhCreditTenderService hjhCreditTenderService;
	@Autowired
	private SystemConfig systemConfig;
    @Autowired
    private AdminCommonService adminCommonService;
	
    /** 权限 */
	public static final String PERMISSIONS = "hjhcredittender";
	private static final String PARAM_NAME = "hyjf_param_name:";
	
	/**
	 * 画面初始化
	 *
	 * @param request
	 * @return 汇计划承接记录列表   已测试
	 */
	@ApiOperation(value = "汇计划承接记录列表", notes = "汇计划承接记录列表初始化")
	@PostMapping(value = "/init")
	@ResponseBody
	public JSONObject init(HttpServletRequest request, @RequestBody @Valid HjhCreditTenderRequest form) {
		JSONObject jsonObject = new JSONObject();
		// 初始化下拉菜单
        // 还款方式
        List<BorrowStyleVO> borrowStyleList = adminCommonService.selectBorrowStyleList();
        jsonObject.put("borrowStyleList", borrowStyleList);
        // 承接方式
        Map<String, String> clientList = adminCommonService.getParamNameMap(PARAM_NAME + "PLAN_ASSIGN_TYPE");
        jsonObject.put("clientList", clientList);
		return jsonObject;
	}
	
    /**
     * 汇计划承接记录列表查询      已测试
     *
     * @param request
     * @return 汇计划承接记录列表查询       
     */
    @ApiOperation(value = "汇计划承接记录列表", notes = "汇计划承接记录列表查询")
    @PostMapping(value = "/searchAction")
    @ResponseBody
    /*@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW) */  
    public AdminResult<ListResult<HjhCreditTenderCustomizeVO>> search(HttpServletRequest request, @RequestBody @Valid HjhCreditTenderRequest form) {
    	//是否从加入明细列表跳转 1:是 0:否
		if(form.getIsAccedelist()!=1){
		    form.setIsAccedelist(0);
		}
    	// 根据删选条件获取计划列表
    	HjhCreditTenderResponse response = this.hjhCreditTenderService.getHjhCreditTenderListByParam(form);
		if(response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<ListResult<HjhCreditTenderCustomizeVO>>(ListResult.build(response.getResultList(), response.getCount())) ;
    }
    
    /**
     * 导出功能
     *
     * @param request
     * @return 汇计划承接记录列表查询       
     */
    @ApiOperation(value = "汇计划承接记录列表", notes = "汇计划承接记录列表导出")
    @PostMapping(value = "/exportAction")
    @ResponseBody
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid HjhCreditTenderRequest form) throws Exception {
		// 表格sheet名称
		String sheetName = "汇添金计划承接记录";
		String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		String[] titles = new String[] { "序号","承接人","承接计划编号","承接计划订单号","出让人","债转编号","原项目编号","还款方式","承接本金","垫付利息","实际支付金额"," 承接时间","承接方式","项目总期数","承接时所在期数"};
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
		List<HjhCreditTenderCustomizeVO> resultList = this.hjhCreditTenderService.getHjhCreditTenderListByParamWithOutPage(form);
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
					HjhCreditTenderCustomizeVO debtCreditTender = resultList.get(i);

					// 创建相应的单元格
					Cell cell = row.createCell(celLength);
					// 序号
					if (celLength == 0) {
						cell.setCellValue(i + 1);
					}
					// 承接人
					else if (celLength == 1) {
						cell.setCellValue(debtCreditTender.getAssignOrderId());
					}

					// 承接计划编号
					else if (celLength == 2) {
						cell.setCellValue(debtCreditTender.getAssignPlanNid());
					}
					// 承接计划订单号
					else if (celLength == 3) {
						cell.setCellValue(debtCreditTender.getAssignOrderId());
					}
					// 出让人
					else if (celLength == 4) {
						cell.setCellValue(debtCreditTender.getCreditUserName());
					}
					// 债转编号
					else if (celLength == 5) {
						cell.setCellValue(debtCreditTender.getCreditNid());
					}
					// 原项目编号
					else if (celLength == 6) {
						cell.setCellValue(debtCreditTender.getBorrowNid());
					}
					// 还款方式
					else if (celLength == 7) {
						cell.setCellValue(debtCreditTender.getRepayStyleName());
					}
					// 承接本金
					else if (celLength == 8) {
						cell.setCellValue(debtCreditTender.getAssignCapital());
					}
					// 垫付利息
					else if (celLength == 9) {
						cell.setCellValue(debtCreditTender.getAssignInterestAdvance());
					}
					// 实际支付金额
					else if (celLength == 10) {
						cell.setCellValue(debtCreditTender.getAssignPay());
					}
					// 承接时间
					else if (celLength == 11) {
						cell.setCellValue(debtCreditTender.getAssignTime());
					}
					// 承接方式
					else if (celLength == 12) {
						cell.setCellValue(debtCreditTender.getAssignTypeName());
					}
					// 项目总期数
					else if (celLength == 13) {
						cell.setCellValue(debtCreditTender.getBorrowPeriod());
					}
					// 承接时所在期数
					else if (celLength == 14) {
						cell.setCellValue(debtCreditTender.getAssignPeriod());
					}
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    	
    }
	
	
	
	
	
	
	
	
	
	
	
	

}
