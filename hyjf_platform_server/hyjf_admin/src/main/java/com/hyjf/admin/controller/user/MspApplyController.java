package com.hyjf.admin.controller.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.admin.beans.request.MspApplytRequestBean;
import com.hyjf.admin.beans.request.MspRequestBean;
import com.hyjf.admin.beans.response.MspApplytResponseBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.MspApplyService;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.user.MspApplytResponse;
import com.hyjf.am.response.user.MspResponse;
import com.hyjf.am.resquest.user.MspApplytRequest;
import com.hyjf.am.resquest.user.MspRequest;
import com.hyjf.am.vo.user.MspApplyVO;
import com.hyjf.am.vo.user.MspConfigureVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * 
 * @author
 *
 */
@Api(value = "安融反欺诈")
@RestController
@RequestMapping("/hyjf-admin/msp")
public class MspApplyController extends BaseController {

	private static final String PERMISSIONS = "mspapply";
	private static final String PERMISSIONS2 = "msp";
	@Autowired
	private MspApplyService mspApplyService;

	/**
	 * 列表维护画面初始化
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "预注册用户页面载入", notes = "预注册用户页面载入")
	@PostMapping(value = "/init")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<ListResult<MspApplyVO>> init(HttpServletRequest request,
			@RequestBody MspApplytRequestBean mspApplytRequestBean) {
		MspApplytRequest aprlr = new MspApplytRequest();
		// 可以直接使用
		BeanUtils.copyProperties(mspApplytRequestBean, aprlr);

		MspApplytResponse prs = mspApplyService.getRecordList(aprlr);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult<ListResult<MspApplyVO>>(ListResult.build(prs.getResultList(), prs.getRecordTotal()));
	}

	/**
	 * 画面迁移(含有id更新，不含有id添加)
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "新增页面初始化", notes = "新增页面初始化")
	@RequestMapping("/infoAction")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<MspApplytResponseBean> info(HttpServletRequest request,
			@RequestBody MspApplytRequestBean mspApplytRequestBean) {
		MspApplytResponse prs = mspApplyService.infoAction();
		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		MspApplytResponseBean marb = new MspApplytResponseBean();
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		marb.setApplyDate(formatter.format(currentTime));

		marb.setRegionList(prs.getRegionList());
		marb.setConfigureList(prs.getConfigureList());
		return new AdminResult<MspApplytResponseBean>(marb);
	}

	/**
	 * 添加活动信息
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "新增", notes = "新增")
	@RequestMapping("/insertAction")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
	public AdminResult insertAction(HttpServletRequest request,
			@RequestBody MspApplytRequestBean mspApplytRequestBean) {
		MspApplytRequest aprlr = new MspApplytRequest();
		// 可以直接使用
		BeanUtils.copyProperties(mspApplytRequestBean, aprlr);
		aprlr.setAdminId(this.getUser(request).getId());
		MspApplytResponse prs = mspApplyService.insertAction(aprlr);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult<>();
	}

	

	/**
	 * 修改活动维护信息
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "修改", notes = "修改")
	@RequestMapping("/updateAction")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
	public AdminResult updateAction(HttpServletRequest request,
			@RequestBody MspApplytRequestBean mspApplytRequestBean) {
		MspApplytRequest aprlr = new MspApplytRequest();
		// 可以直接使用
		BeanUtils.copyProperties(mspApplytRequestBean, aprlr);
		// form.setLogo("1");
		// 更新
		MspApplytResponse prs=mspApplyService.updateAction(aprlr);
		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult<>();
	}

	/**
	 * 删除胡配置信息
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "删除", notes = "删除")
	@RequestMapping("/deleteAction")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
	public AdminResult deleteRecordAction(HttpServletRequest request,
			@RequestBody MspApplytRequestBean mspApplytRequestBean) {

		MspApplytRequest aprlr = new MspApplytRequest();
		// 可以直接使用
		BeanUtils.copyProperties(mspApplytRequestBean, aprlr);
		// form.setLogo("1");
		// 更新
		MspApplytResponse prs=mspApplyService.deleteRecordAction(aprlr);
		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult<>();
	}

	

	@ResponseBody
	@RequestMapping("/validateBeforeAction")
	@ApiOperation(value = "验证", notes = "验证")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult validateBeforeAction(HttpServletRequest request,
			@RequestBody MspApplytRequestBean mspApplytRequestBean) {
		MspApplytRequest aprlr = new MspApplytRequest();
		// 可以直接使用
		BeanUtils.copyProperties(mspApplytRequestBean, aprlr);
		aprlr.setAdminId(this.getUser(request).getId());
		MspApplytResponse prs = mspApplyService.validateBeforeAction(aprlr);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult<>();
	}

	/**
	 * 导出功能
	 * 
	 * @param request
	 * @param modelAndView
	 * @param form
	 */
	@RequestMapping("/validateBeforeAction")
	@ApiOperation(value = "导出", notes = "导出")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
	public void exportAction(HttpServletRequest request,
			@RequestBody MspApplytRequestBean mspApplytRequestBean,HttpServletResponse response) throws Exception {
	
		MspApplytRequest aprlr = new MspApplytRequest();
		// 可以直接使用
		aprlr.setPageSize(-1);
		aprlr.setCurrPage(-1);
		BeanUtils.copyProperties(mspApplytRequestBean, aprlr);

		MspApplytResponse prs = mspApplyService.getRecordList(aprlr);

		List<MspApplyVO> recordList = this.mspApplyService.getRecordList(aprlr).getResultList();

		// 表格sheet名称
		String sheetName = "安融反欺诈查询";

		String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date())
				+ CustomConstants.EXCEL_EXT;
		String[] titles = new String[] { "序号", "姓名", "身份证号", "操作人", "查询时间" };
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 生成一个表格
		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (recordList != null && recordList.size() > 0) {

			int sheetCount = 1;
			int rowNum = 0;

			for (int i = 0; i < recordList.size(); i++) {
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
					MspApplyVO pInfo = recordList.get(i);

					// 创建相应的单元格
					Cell cell = row.createCell(celLength);

					// 序号
					if (celLength == 0) {
						cell.setCellValue(i + 1);
					} else if (celLength == 1) {
						cell.setCellValue(pInfo.getName());
					} else if (celLength == 2) {
						cell.setCellValue(pInfo.getIdentityCard());
					} else if (celLength == 3) {
						cell.setCellValue(pInfo.getCreateUser());
					} else if (celLength == 4) {
						Long time1 = new Long(pInfo.getCreateTime());
						String d = format.format(time1 * 1000);
						cell.setCellValue(d);
					}

				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
	}

	/**
	 * 画面迁移(含有id更新，不含有id添加)
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/applyAction")
	@ApiOperation(value = "共享", notes = "共享")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
	public AdminResult<MspApplytResponseBean> applyInfo(HttpServletRequest request,
			@RequestBody MspApplytRequestBean mspApplytRequestBean) {
		MspApplytRequest aprlr = new MspApplytRequest();
		// 可以直接使用
		BeanUtils.copyProperties(mspApplytRequestBean, aprlr);
		aprlr.setAdminId(this.getUser(request).getId());
		MspApplytResponse prs = mspApplyService.applyInfo(aprlr);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		MspApplytResponseBean mspapply=new MspApplytResponseBean();
		BeanUtils.copyProperties(prs.getResult(),mspapply);
		mspapply.setRegionList(prs.getRegionList());
		return new AdminResult<MspApplytResponseBean>(mspapply);
	}

	// 安融共享
	@ResponseBody
	@RequestMapping("/shareUser")
	@ApiOperation(value = "安融共享", notes = "安融共享")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
	public AdminResult shareUser(HttpServletRequest request,
			@RequestBody MspApplytRequestBean mspApplytRequestBean) {
		MspApplytRequest aprlr = new MspApplytRequest();
		// 可以直接使用
		BeanUtils.copyProperties(mspApplytRequestBean, aprlr);
		aprlr.setAdminId(this.getUser(request).getId());
		MspApplytResponse prs = mspApplyService.shareUser(aprlr);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult<>();

	}

	/**
	 * 列表维护画面初始化
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/downloadFile")
	@ApiOperation(value = "安融共享", notes = "安融共享")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<MspApplytResponseBean> download(HttpServletRequest request,
			@RequestBody MspApplytRequestBean mspApplytRequestBean) {
		MspApplytRequest aprlr = new MspApplytRequest();
		// 可以直接使用
		BeanUtils.copyProperties(mspApplytRequestBean, aprlr);
		aprlr.setAdminId(this.getUser(request).getId());
		MspApplytResponse prs = mspApplyService.download(aprlr);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		MspApplytResponseBean marb=new MspApplytResponseBean();
		BeanUtils.copyProperties(prs, marb);
		return new AdminResult<>(marb);
	
	}
	
	@ResponseBody
	@RequestMapping("/mspSearchAction")
	@ApiOperation(value = "安融配置列表", notes = "安融配置列表")
	@AuthorityAnnotation(key = PERMISSIONS2, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<ListResult<MspConfigureVO>> searchAction(HttpServletRequest request,
			@RequestBody MspRequestBean mspRequestBean) {
		MspRequest aprlr = new MspRequest();
		// 可以直接使用
		BeanUtils.copyProperties(request, aprlr);

		 MspResponse prs = mspApplyService.searchAction(aprlr);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult<ListResult<MspConfigureVO>>(ListResult.build(prs.getResultList(), prs.getRecordTotal()));
		
	}
	@ResponseBody
	@RequestMapping("/mspInfoAction")
	@ApiOperation(value = "配置新增页面初始化", notes = "配置新增页面初始化")
	@AuthorityAnnotation(key = PERMISSIONS2, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<Map<String, Object>> infoAction(HttpServletRequest request,
			@RequestBody MspRequestBean mspRequestBean) {
		Map<String, Object> map = null;
		MspRequest aprlr = new MspRequest();
		// 可以直接使用
		BeanUtils.copyProperties(request, aprlr);

		 MspResponse prs = mspApplyService.searchAction(aprlr);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		map.put("record", prs.getResult());
		map.put("regionList", prs.getRegionList());
		return new AdminResult<Map<String, Object>>(map);
	}
	@ResponseBody
	@RequestMapping("/mspInsertAction")
	@ApiOperation(value = "新增配置", notes = "新增配置")
	@AuthorityAnnotation(key = PERMISSIONS2, value = ShiroConstants.PERMISSION_ADD)
	public AdminResult insertAction(HttpServletRequest request,
			@RequestBody MspRequestBean mspRequestBean) {
		MspRequest aprlr = new MspRequest();
		// 可以直接使用
		BeanUtils.copyProperties(request, aprlr);

		 MspResponse prs = mspApplyService.insertAction(aprlr);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult();
	}
	@ResponseBody
	@RequestMapping("/mspUpdateAction")
	@ApiOperation(value = "配置修改", notes = "配置修改")
	@AuthorityAnnotation(key = PERMISSIONS2, value = ShiroConstants.PERMISSION_UPDATE)
	public AdminResult updateAction(HttpServletRequest request,
			@RequestBody MspRequestBean mspRequestBean) {
		MspRequest aprlr = new MspRequest();
		// 可以直接使用
		BeanUtils.copyProperties(request, aprlr);

		 MspResponse prs = mspApplyService.updateAction(aprlr);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult();
	}
	@ResponseBody
	@RequestMapping("/configureNameError")
	@ApiOperation(value = "检测标名", notes = "检测标名")
	@AuthorityAnnotation(key = PERMISSIONS2, value = ShiroConstants.PERMISSION_UPDATE)
	public AdminResult configureNameError(HttpServletRequest request,
			@RequestBody MspRequestBean mspRequestBean) {
		MspRequest aprlr = new MspRequest();
		// 可以直接使用
		BeanUtils.copyProperties(request, aprlr);

		 MspResponse prs = mspApplyService.configureNameError(aprlr);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult();
	}
	@ResponseBody
	@RequestMapping("/mspDeleteAction")
	@ApiOperation(value = "删除配置", notes = "删除配置")
	@AuthorityAnnotation(key = PERMISSIONS2, value = ShiroConstants.PERMISSION_DELETE)
	public AdminResult deleteAction(HttpServletRequest request,
			@RequestBody MspRequestBean mspRequestBean) {
		MspRequest aprlr = new MspRequest();
		// 可以直接使用
		BeanUtils.copyProperties(request, aprlr);

		 MspResponse prs = mspApplyService.deleteAction(aprlr);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult();
	}
	@ResponseBody
	@RequestMapping("/mspCheckAction")
	@ApiOperation(value = "检查编号唯一性", notes = "检查编号唯一性")
	@AuthorityAnnotation(key = PERMISSIONS2, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult checkAction(HttpServletRequest request,
			@RequestBody MspRequestBean mspRequestBean) {
		MspRequest aprlr = new MspRequest();
		// 可以直接使用
		BeanUtils.copyProperties(request, aprlr);

		 MspResponse prs = mspApplyService.checkAction(aprlr);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult();
	}
	/**
	 * 数据导出
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/mspexportAction")
	@ApiOperation(value = "检查编号唯一性", notes = "检查编号唯一性")
	@AuthorityAnnotation(key = PERMISSIONS2, value = ShiroConstants.PERMISSION_EXPORT)
	public void exportAction(HttpServletRequest request,
			@RequestBody MspRequestBean mspRequestBean,HttpServletResponse response) throws Exception {
		
		
		MspRequest aprlr = new MspRequest();
		// 可以直接使用
		BeanUtils.copyProperties(request, aprlr);
		aprlr.setPageSize(-1);
		aprlr.setCurrPage(-1);
		 MspResponse prs = mspApplyService.searchAction(aprlr);
		// 表格sheet名称
		String sheetName = "安融反欺诈查询配置表";


		List<MspConfigureVO> resultList = prs.getResultList();


		String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

		String[] titles = new String[] { "序号", "标的名称", "业务类型", "借款类型(借款用途)", "审批结果 ", "借款金额（合同金额）（元）", "借款/还款期数（月）", "借款城市(借款地点)", "担保类型", "未偿还本金", "当前还款状态"};
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
					MspConfigureVO record = resultList.get(i);

					// 创建相应的单元格
					Cell cell = row.createCell(celLength);

					// 序号
					if (celLength == 0) {
						cell.setCellValue(i + 1);
					}
					// 标的名称
					else if (celLength == 1) {
						cell.setCellValue(record.getConfigureName());
					}
					// 业务类型
					else if (celLength == 2) {
						cell.setCellValue(record.getServiceTypeName());
					}
					// 借款类型
					else if (celLength == 3) {
						cell.setCellValue(record.getLoanTypeName());
					}
					// 审批结果
					else if (celLength == 4) {
						cell.setCellValue(record.getApprovalResultName());
					}
					// 借款金额
					else if (celLength == 5) {
						cell.setCellValue(record.getLoanMoney().toString());
					}
					// 期数
					else if (celLength == 6) {
						cell.setCellValue(record.getLoanTimeLimit());
					}
					// 借款地点
					else if (celLength == 7) {
						cell.setCellValue(record.getCreditAddress());
					}
					// 担保类型
					else if (celLength == 8) {
						cell.setCellValue(record.getGuaranteeTypeName());
					}

					// 未偿还本金
					else if (celLength == 9) {
						cell.setCellValue(record.getUnredeemedMoney().toString());
					}
					// 当前还款状态
					else if (celLength == 10) {
						cell.setCellValue(record.getRepaymentStatusName());
					}
					
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
	}

}
