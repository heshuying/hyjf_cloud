package com.hyjf.admin.controller.user;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ChangeLogService;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.user.ChangeLogResponse;
import com.hyjf.am.resquest.user.ChangeLogRequest;
import com.hyjf.am.vo.user.ChangeLogVO;
import com.hyjf.common.util.AsteriskProcessUtil;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * @author 董泽杉
 * @version V1.0  
 * @package
 * @date 2018/6/27
 */
@Api(value = "会员中心-操作日志",tags ="会员中心-操作日志")
@RestController
@RequestMapping("/hyjf-admin/changelog")
public class ChangeLogController extends BaseController {
//	private Logger logger = LoggerFactory.getLogger(ChangeLogController.class);
	@Autowired
	private ChangeLogService changeLogService;
	/**
	 * 权限维护画面初始化
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "查询操作日志", notes = "查询操作日志")
	@PostMapping(value = "/userauthlist")
	@ResponseBody
	public AdminResult<ListResult<ChangeLogVO>> userManagerInit(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, String> map) {
		ChangeLogRequest clr = new ChangeLogRequest();
		clr.setUsername(map.get("username"));
		clr.setRealName(map.get("realName"));
		clr.setMobile(map.get("mobile"));
		clr.setRecommendUser(map.get("recommendUser"));
		clr.setStartTime(map.get("startTime"));
		clr.setEndTime(map.get("endTime"));
		clr.setCurrPage(Integer.valueOf(map.get("currPage")));
		clr.setPageSize(Integer.valueOf(map.get("pageSize")));
		clr.setAttribute(map.get("attribute"));
		ChangeLogResponse prs=changeLogService.getChangeLogList(clr);
		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());
		}
		return new AdminResult<ListResult<ChangeLogVO>>(ListResult.build(prs.getResultList(), prs.getRecordTotal()));

	}

	 /**
     * 导出用户信息修改列表EXCEL
     * @param request
     * @param response
     * @throws Exception
     */
	@ApiOperation(value = "下载操作日志", notes = "下载操作日志")
	@PostMapping(value = "/exportAccountsExcel")
	@ResponseBody
    public void exportAccountsExcel(HttpServletRequest request,HttpServletResponse response,@RequestBody Map<String, String> map)
            throws Exception {
		ChangeLogRequest clr = new ChangeLogRequest();
		clr.setUsername(map.get("username"));
		clr.setRealName(map.get("realName"));
		clr.setMobile(map.get("mobile"));
		clr.setRecommendUser(map.get("recommendUser"));
		clr.setStartTime(map.get("startTime"));
		clr.setEndTime(map.get("endTime"));
		clr.setAttribute(map.get("attribute"));
		clr.setCurrPage(-1);
		clr.setPageSize(-1);
		ChangeLogResponse prs=changeLogService.getChangeLogList(clr);
		if (prs == null) {
			return ;
		}
		if (!AdminResponse.isSuccess(prs)) {
			return ;
		}
        // 表格sheet名称
        String sheetName = "操作日志";
        List<ChangeLogVO> recordList = prs.getResultList();

        String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";

        String[] titles = new String[] { "序号", "用户名", "姓名", "手机号", "用户角色", "用户属性", "推荐人", "51老用户", "用户状态", "修改人", "修改时间", "说明"};
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
                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles,
                            (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }

                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    ChangeLogVO changeLog = recordList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    
                    // 用户名
                    else if (celLength == 1) {
                        cell.setCellValue(changeLog.getUsername());
                    }
                    // 真实姓名
                    else if (celLength == 2) {
                        cell.setCellValue(changeLog.getRealName());
                    }
                    // 手机号
                    else if (celLength == 3) {
                        cell.setCellValue(AsteriskProcessUtil.getAsteriskedValue(changeLog.getMobile(), 3));
                    }
                    // 用户角色
                    else if (celLength == 4) {
                        cell.setCellValue(changeLog.getRole()==null?"":changeLog.getRole()==1?"投资人":"借款人");
                    }
                    // 用户属性
                    else if (celLength == 5) {
                        cell.setCellValue(changeLog.getAttribute()==null?"":changeLog.getAttribute().equals("0")?"无主单":changeLog.getAttribute().equals("1")?"有主单":changeLog.getAttribute().equals("2")?"线下员工":"线上员工");
                    }
                    // 推荐人
                    else if (celLength == 6) {
                        cell.setCellValue(changeLog.getRecommendUser());
                    }
                    // 是否51老用户
                    else if (celLength == 7) {
                        cell.setCellValue(changeLog.getIs51()==null?"":changeLog.getIs51()==1?"是" : "否");
                    }
                    // 用户状态
                    else if (celLength == 8) {
                        cell.setCellValue(changeLog.getStatus()==null?"":changeLog.getStatus()==1?"启用" : "禁用");
                    }
                    // 修改人
                    else if (celLength == 9) {
                        cell.setCellValue(changeLog.getChangeUser());
                    }
                    // 修改时间
                    else if (celLength == 10) {
                        cell.setCellValue(changeLog.getChangeTime());
                    }
                    // 修改说明
                    else if (celLength == 11) { 
                        cell.setCellValue(changeLog.getRemark());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);

    }


}
