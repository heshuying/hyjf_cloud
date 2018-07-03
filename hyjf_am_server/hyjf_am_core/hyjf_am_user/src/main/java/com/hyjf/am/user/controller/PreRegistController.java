package com.hyjf.am.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.AdminPreRegistListResponse;
import com.hyjf.am.resquest.user.AdminPreRegistListRequest;
import com.hyjf.am.user.dao.model.customize.AdminPreRegistListCustomize;
import com.hyjf.am.user.service.PreRegistService;
import com.hyjf.am.vo.user.AdminPreRegistListVO;
import com.hyjf.am.vo.user.AdminUserAuthListVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
/**
 * admin预注册记录
 * @author dongzeeshan
 */
@RestController
@RequestMapping("/am-user/preregist")
public class PreRegistController extends BaseController{

	@Autowired
	private PreRegistService preRegistService;

	/**
	 * 权限维护画面初始化
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	 @RequestMapping("/preregistlist")
	public AdminPreRegistListResponse init(@RequestBody  AdminPreRegistListRequest adminPreRegistListRequest) {
		return this.createPage(adminPreRegistListRequest);
	}

	/**
	 * 创建权限维护分页机能
	 * 
	 * @param request
	 * @param modelAndView
	 * @param form
	 */
	private AdminPreRegistListResponse createPage(AdminPreRegistListRequest adminPreRegistListRequest) {

		Map<String, Object> registUser = new HashMap<String, Object>();
		registUser.put("mobile", adminPreRegistListRequest.getMobile());
		registUser.put("referrer", adminPreRegistListRequest.getReferrer());
		registUser.put("source", adminPreRegistListRequest.getSource());
		registUser.put("preRegistTime", adminPreRegistListRequest.getPreRegistTime());
		registUser.put("registFlag", adminPreRegistListRequest.getRegistFlag());
		registUser.put("registTime", adminPreRegistListRequest.getRegistTime());
		registUser.put("platformId", adminPreRegistListRequest.getPlatformId());
		registUser.put("platformName", adminPreRegistListRequest.getPlatformName());
		registUser.put("remark", adminPreRegistListRequest.getRemark());
		registUser.put("createTime", adminPreRegistListRequest.getCreateTime());
		registUser.put("updateTime", adminPreRegistListRequest.getUpdateTime());
		registUser.put("updateBy", adminPreRegistListRequest.getUpdateBy());
		registUser.put("preRegTimeStart", StringUtils.isNotBlank(adminPreRegistListRequest.getPreRegTimeStart())?adminPreRegistListRequest.getPreRegTimeStart():null);
		registUser.put("preRegTimeEnd", StringUtils.isNotBlank(adminPreRegistListRequest.getPreRegTimeEnd())?adminPreRegistListRequest.getPreRegTimeEnd():null);
		AdminPreRegistListResponse aprlr=new AdminPreRegistListResponse();
		int recordTotal = this.preRegistService.countRecordTotal(registUser);

		if (recordTotal > 0) {
			Paginator paginator = new Paginator(adminPreRegistListRequest.getPaginatorPage(), recordTotal);
			List<AdminPreRegistListCustomize> recordList = this.preRegistService.getRecordList(registUser, paginator.getOffset(), paginator.getLimit());
			if (recordList != null) {
				List<AdminPreRegistListVO> avo = CommonUtils.convertBeanList(recordList,AdminPreRegistListVO.class);
				aprlr.setResultList(avo);
				aprlr.setRecordTotal(recordTotal);
				aprlr.setRtn(Response.SUCCESS);
			}
			return aprlr;
		}
		return new AdminPreRegistListResponse();
	}

	/**
	 * 预注册用户编辑页面跳转
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/updatepreregistlist")
	public AdminPreRegistListResponse updatePreRegistList(@RequestBody  AdminPreRegistListRequest adminPreRegistListRequest) {
		AdminPreRegistListResponse arpr=new AdminPreRegistListResponse();
		if (!StringUtils.isEmpty(adminPreRegistListRequest.getId())) {
			AdminPreRegistListCustomize preRegist = this.preRegistService.getPreRegist(Integer.parseInt(adminPreRegistListRequest.getId()));
			if (preRegist != null) {
				AdminPreRegistListVO avo=new AdminPreRegistListVO();
				BeanUtils.copyProperties(preRegist, avo);
				arpr.setResult(avo);
			}
			return arpr;
		} else {
			return arpr;
		}

	}

	/**
	 * 预注册用户编辑保存
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/savepreregistlist")
	public AdminPreRegistListResponse savePreRegistList(@RequestBody  AdminPreRegistListRequest adminPreRegistListRequest) {
		AdminPreRegistListResponse apr =new AdminPreRegistListResponse();
		try {
			if (!StringUtils.isEmpty(adminPreRegistListRequest.getId())) {
				 AdminPreRegistListCustomize aprlr=new AdminPreRegistListCustomize();
				BeanUtils.copyProperties(adminPreRegistListRequest,aprlr);
				Map<String, Object> resultMap = this.preRegistService.savePreRegist(aprlr);
				if ((Boolean) resultMap.get("success")) {
					return apr;
				} else {
					apr.setRtn("failed");
					apr.setMessage((String)resultMap.get("msg"));
					return apr;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		apr.setMessage("校验失败");
		return apr;
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
//	@RequestMapping("/exportpreregist")
//	public void exportExcel(@RequestBody  AdminPreRegistListRequest adminPreRegistListRequest) throws Exception {
//
//
//		// 表格sheet名称
//		String sheetName = "预注册用户";
//		// 文件名称
//		String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
//
//		// 需要输出的结果列表
//		Map<String, Object> registUser = new HashMap<String, Object>();
//		registUser.put("mobile", adminPreRegistListRequest.getMobile());
//		registUser.put("referrer", adminPreRegistListRequest.getReferrer());
//		registUser.put("source", adminPreRegistListRequest.getSource());
//		registUser.put("preRegistTime", adminPreRegistListRequest.getPreRegistTime());
//		registUser.put("registFlag", adminPreRegistListRequest.getRegistFlag());
//		registUser.put("registTime", adminPreRegistListRequest.getRegistTime());
//		registUser.put("platformId", adminPreRegistListRequest.getPlatformId());
//		registUser.put("platformName", adminPreRegistListRequest.getPlatformName());
//		registUser.put("remark", adminPreRegistListRequest.getRemark());
//		registUser.put("createTime", adminPreRegistListRequest.getCreateTime());
//		registUser.put("updateTime", adminPreRegistListRequest.getUpdateTime());
//		registUser.put("updateBy", adminPreRegistListRequest.getUpdateBy());
//		registUser.put("preRegTimeStart", adminPreRegistListRequest.getPreRegTimeStart());
//		registUser.put("preRegTimeEnd", adminPreRegistListRequest.getPreRegTimeEnd());
//		List<AdminPreRegistListCustomize> recordList = this.preRegistService.getRecordList(registUser, -1, -1);
//		String[] titles = new String[] { "序号", "手机号", "推荐人", "渠道", "预注册时间", "是否已经注册", "操作终端", "备注" };
//		// 声明一个工作薄
//		HSSFWorkbook workbook = new HSSFWorkbook();
//		// 生成一个表格
//		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
//
//		if (recordList != null && recordList.size() > 0) {
//
//			int sheetCount = 1;
//			int rowNum = 0;
//
//			for (int i = 0; i < recordList.size(); i++) {
//				rowNum++;
//				if (i != 0 && i % 60000 == 0) {
//					sheetCount++;
//					sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
//					rowNum = 1;
//				}
//
//				// 新建一行
//				Row row = sheet.createRow(rowNum);
//				// 循环数据
//				for (int celLength = 0; celLength < titles.length; celLength++) {
//					AdminPreRegistListCustomize preRegist = recordList.get(i);
//					// 创建相应的单元格
//					Cell cell = row.createCell(celLength);
//					if (celLength == 0) {// 序号
//						cell.setCellValue(i + 1);
//					} else if (celLength == 1) {// 手机号
//						cell.setCellValue(preRegist.getMobile());
//					} else if (celLength == 2) {// 推荐人
//						cell.setCellValue(preRegist.getReferrer());
//					} else if (celLength == 3) {// 渠道
//						cell.setCellValue(preRegist.getSource());
//					} else if (celLength == 4) {// 预注册时间
//						cell.setCellValue(preRegist.getPreRegistTime());
//					} else if (celLength == 5) {// 是否已经注册
//						if (preRegist.getRegistFlag().equals("1")) {
//							cell.setCellValue("是");
//						} else {
//							cell.setCellValue("否");
//						}
//					} else if (celLength == 6) {// 操作终端
//						cell.setCellValue(preRegist.getPlatformName());
//					} else if (celLength == 7) {// 备注
//						cell.setCellValue(preRegist.getRemark());
//					}
//				}
//			}
//		}
//		// 导出
//		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
//
//	}
}
