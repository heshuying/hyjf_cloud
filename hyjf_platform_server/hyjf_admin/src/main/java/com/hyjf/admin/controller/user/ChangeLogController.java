package com.hyjf.admin.controller.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ChangeLogService;
import com.hyjf.am.response.user.AdminUserAuthListResponse;
import com.hyjf.am.response.user.ChangeLogResponse;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
import com.hyjf.am.resquest.user.ChangeLogRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * @author 董泽杉
 * @version V1.0  
 * @package
 * @date 2018/6/27
 */
@Api(value = "操作日志")
@RestController
@RequestMapping("/hyjf-admin/changelog")
public class ChangeLogController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(ChangeLogController.class);
	@Autowired
	private ChangeLogService changeLogService;
	/**
	 * 权限维护画面初始化
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "查询操作日志", notes = "查询操作日志")
	@RequestMapping(value = "/userauthlist")
	@ResponseBody
	public JSONObject userManagerInit(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, String> map) {
		JSONObject info = new JSONObject();
		ChangeLogRequest clr = new ChangeLogRequest();
		clr.setUsername(map.get("username"));
		clr.setRealName(map.get("realName"));
		clr.setMobile(map.get("mobile"));
		clr.setRecommendUser(map.get("recommendUser"));
		//clr.setPageSize(Integer.valueOf(map.get("pageSize")) );
		clr.setStartTime(map.get("startTime"));
		clr.setEndTime(map.get("endTime"));
		ChangeLogResponse rqes=changeLogService.getChangeLogList(clr);
		info.put("list", rqes.getResultList());
		info.put("recordTotal", rqes.getRecordTotal());
		info.put("status", "00");
		info.put("msg", "成功");
		return info;

	}

//	 /**
//     * 导出用户信息修改列表EXCEL
//     * @param request
//     * @param response
//     * @throws Exception
//     */
//    @RequestMapping(ChangeLogDefine.EXPORT_CHANGELOG_ACTION)
//    @RequiresPermissions(ChangeLogDefine.PERMISSIONS_CHANGELOG_EXPORT)
//    public void exportAccountsExcel(HttpServletRequest request, HttpServletResponse response, ChangeLogBean form)
//            throws Exception {
//        // 表格sheet名称
//        String sheetName = "操作日志";
//        List<ChangeLogCustomize> recordList = this.changeLogService.getChangeLogList(form);
//
//        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";
//
//        String[] titles = new String[] { "序号", "用户名", "姓名", "手机号", "用户角色", "用户属性", "推荐人", "51老用户", "用户状态", "修改人", "修改时间", "说明"};
//        // 声明一个工作薄
//        HSSFWorkbook workbook = new HSSFWorkbook();
//
//        // 生成一个表格
//        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
//
//        if (recordList != null && recordList.size() > 0) {
//
//            int sheetCount = 1;
//            int rowNum = 0;
//
//            for (int i = 0; i < recordList.size(); i++) {
//                rowNum++;
//                if (i != 0 && i % 60000 == 0) {
//                    sheetCount++;
//                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles,
//                            (sheetName + "_第" + sheetCount + "页"));
//                    rowNum = 1;
//                }
//
//                // 新建一行
//                Row row = sheet.createRow(rowNum);
//                // 循环数据
//                for (int celLength = 0; celLength < titles.length; celLength++) {
//                    ChangeLogCustomize changeLog = recordList.get(i);
//
//                    // 创建相应的单元格
//                    Cell cell = row.createCell(celLength);
//
//                    // 序号
//                    if (celLength == 0) {
//                        cell.setCellValue(i + 1);
//                    }
//                    
//                    // 用户名
//                    else if (celLength == 1) {
//                        cell.setCellValue(changeLog.getUsername());
//                    }
//                    // 真实姓名
//                    else if (celLength == 2) {
//                        cell.setCellValue(changeLog.getRealName());
//                    }
//                    // 手机号
//                    else if (celLength == 3) {
//                        cell.setCellValue(AsteriskProcessUtil.getAsteriskedValue(changeLog.getMobile(), ChangeLogDefine.PERMISSION_HIDE_SHOW));
//                    }
//                    // 用户角色
//                    else if (celLength == 4) {
//                        cell.setCellValue(changeLog.getRole()==null?"":changeLog.getRole()==1?"投资人":"借款人");
//                    }
//                    // 用户属性
//                    else if (celLength == 5) {
//                        cell.setCellValue(changeLog.getAttribute()==null?"":changeLog.getAttribute().equals("0")?"无主单":changeLog.getAttribute().equals("1")?"有主单":changeLog.getAttribute().equals("2")?"线下员工":"线上员工");
//                    }
//                    // 推荐人
//                    else if (celLength == 6) {
//                        cell.setCellValue(changeLog.getRecommendUser());
//                    }
//                    // 是否51老用户
//                    else if (celLength == 7) {
//                        cell.setCellValue(changeLog.getIs51()==null?"":changeLog.getIs51()==1?"是" : "否");
//                    }
//                    // 用户状态
//                    else if (celLength == 8) {
//                        cell.setCellValue(changeLog.getStatus()==null?"":changeLog.getStatus()==1?"启用" : "禁用");
//                    }
//                    // 修改人
//                    else if (celLength == 9) {
//                        cell.setCellValue(changeLog.getChangeUser());
//                    }
//                    // 修改时间
//                    else if (celLength == 10) {
//                        cell.setCellValue(changeLog.getChangeTime());
//                    }
//                    // 修改说明
//                    else if (celLength == 11) { 
//                        cell.setCellValue(changeLog.getRemark());
//                    }
//                }
//            }
//        }
//        // 导出
//        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
//
//    }


}
