package com.hyjf.admin.controller.user;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.UserauthService;
import com.hyjf.am.response.user.AdminUserAuthListResponse;
import com.hyjf.am.response.user.AdminUserAuthLogListResponse;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
import com.hyjf.am.resquest.user.AdminUserAuthLogListRequest;
import com.hyjf.am.vo.user.AdminUserAuthListVO;
import com.hyjf.am.vo.user.AdminUserAuthLogListVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author 董泽杉
 * @version V1.0  
 * @package
 * @date 2018/6/27
 */
@Api(value = "授权状态&授权记录",tags ="授权状态&授权记录")
@RestController
@RequestMapping("/hyjf-admin/userauth")
public class UserauthController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(UserauthController.class);
	@Autowired
	private UserauthService userauthService;

	/**
	 * 权限维护画面初始化
	 *
	 * @param request
	 * @param
	 * @return
	 */
	@ApiOperation(value = "授权状态", notes = "授权状态集合")
	@PostMapping(value = "/userauthlist")
	@ResponseBody
	public AdminResult<ListResult<AdminUserAuthListVO>> userManagerInit(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AdminUserAuthListRequest adminUserAuthListRequest) {
		AdminUserAuthListResponse rqes = userauthService.userauthlist(adminUserAuthListRequest);
		return new AdminResult<>(ListResult.build(rqes.getResultList(), rqes.getRecordTotal()));

	}


	/**
	 * 自动投资解约
	 *
	 * @param
	 */
	@ApiOperation(value = "授权状态", notes = "自动投资解约")
	@PostMapping(value = "/userinvescancel")
	@ResponseBody
	public AdminResult cancelInvestAuth(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Integer userId) {
		logger.info("自动投资解约开始，用户：{}", userId);
		if ("00".equals(userauthService.canCancelAuth(userId).getRtn())) {

			return new AdminResult<>("99",  "当前用户存在持有中计划，不能解约！");
		}

		//插入数据库
		AdminUserAuthListResponse uu =userauthService.cancelInvestAuth(userId);
		if(AdminUserAuthListResponse.isSuccess(uu)) {
			return new AdminResult<>();
		}else {
			return new AdminResult<>(AdminResult.FAIL, uu.getMessage());
		}
		
	}

	/**
	 * 自动债转解约
	 *
	 * @param
	 */
	@ApiOperation(value = "授权状态", notes = "自动债转解约")
	@PostMapping(value = "/usercreditcancel")
	@ResponseBody
	public AdminResult cancelCreditAuth(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Integer userId) {
		

		// 返回结果
		logger.info("自动债转授权开始，用户：{}", userId);
		if ("00".equals(userauthService.canCancelAuth(userId).getRtn())) {
			return new AdminResult<>("99",  "当前用户存在持有中计划，不能解约！");
		}
		//插入数据库
		AdminUserAuthListResponse uu = userauthService.cancelCreditAuth(userId);
		if(AdminUserAuthListResponse.isSuccess(uu)) {
			return new AdminResult<>();
		}else {
			return new AdminResult<>(AdminResult.FAIL, uu.getMessage());
		}
		
		
	}
	/**
	 * 自动债转解约
	 *
	 * @param
	 */
	@ApiOperation(value = "授权状态", notes = "授权日志流水")
	@ResponseBody
	@PostMapping("/userauthloglist")
	public AdminResult<ListResult<AdminUserAuthLogListVO>> cancelCreditAuth(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AdminUserAuthLogListRequest form) {
		AdminUserAuthLogListResponse rqes = userauthService.userauthLoglist(form);
		List<AdminUserAuthLogListVO> list = rqes.getResultList();
		 Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
		Map<String, String> invert = CacheUtil.getParamNameMap("AUTO_INVER_TYPE");
		 List<AdminUserAuthLogListVO> list2=new  ArrayList<AdminUserAuthLogListVO>();
		 for (AdminUserAuthLogListVO adminUserAuthLogListVO : list) {
			 AdminUserAuthLogListVO avo=new AdminUserAuthLogListVO();
			 avo=adminUserAuthLogListVO;
			 avo.setAuthType(invert.get(adminUserAuthLogListVO.getAuthType()));
			 avo.setOperateEsb(client.get(adminUserAuthLogListVO.getOperateEsb()));
			 list2.add(avo);
		}
		return new AdminResult<ListResult<AdminUserAuthLogListVO>>(ListResult.build(list2, rqes.getRecordTotal()));
		
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
	@ApiOperation(value = "授权状态", notes = "授权导出")
	@ResponseBody
	@PostMapping("/exportExcel")
    public void exportExcel(@RequestBody AdminUserAuthListRequest from, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        // 表格sheet名称
        String sheetName = "授权状态";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";

        // 需要输出的结果列表

        // 封装查询条件
        from.setCurrPage(-1);
        from.setPageSize(-1);
		 List<AdminUserAuthListVO> recordList = userauthService.userauthlist(from).getResultList();
        String[] titles = new String[]{"序号", "用户名", "手机号", "自动投标交易金额", "自动投标总金额", "自动投标到期日", "自动投标授权状态", "自动债转授权状态", "授权时间"};
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
                	AdminUserAuthListVO user = recordList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {// 用户名
                        cell.setCellValue(user.getUserName());
                    } else if (celLength == 2) {// 手机号
                        cell.setCellValue(user.getMobile());
                    } else if (celLength == 3) {// 自动投标交易金额
                        cell.setCellValue("2000000");
                    } else if (celLength == 4) {// 自动投标总金额
                        cell.setCellValue("1000000000");
                    } else if (celLength == 5) {// 自动投标到期日
                        cell.setCellValue(user.getAutoInvesEndTime());
                    } else if (celLength == 6) {// 自动投标授权状态
                        cell.setCellValue(user.getAutoInvesStatus());
                    } else if (celLength == 7) {// 自动债转授权状态
                        cell.setCellValue(user.getAutoCreditStatus());
                    } else if (celLength == 8) {// 债转授权时间
                        cell.setCellValue(user.getAutoCreateTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
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
	@ApiOperation(value = "授权状态", notes = "授权导出")
	@ResponseBody
	@PostMapping("/exportLogExcel")
    public void exportExcel(@RequestBody AdminUserAuthLogListRequest form, HttpServletRequest request, HttpServletResponse response) throws Exception {


        // 表格sheet名称
        String sheetName = "授权记录";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";
		Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
		Map<String, String> invert = CacheUtil.getParamNameMap("AUTO_INVER_TYPE");
        form.setCurrPage(-1);
        form.setPageSize(-1);
		 List<AdminUserAuthLogListVO> recordList = userauthService.userauthLoglist(form).getResultList();
        String[] titles = new String[]{"序号", "订单号", "类型", "用户名", "操作平台", "订单状态", "授权时间"};
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
                	AdminUserAuthLogListVO user = recordList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {// 订单号
                        cell.setCellValue(user.getOrderId());
                    } else if (celLength == 2) {// 类型
                        cell.setCellValue(invert.get(user.getAuthType()));
                    } else if (celLength == 3) {// 用户名
                        cell.setCellValue(user.getUserName());
                    } else if (celLength == 4) {// 操作平台
                        cell.setCellValue(client.get(user.getOperateEsb()));
                    } else if (celLength == 5) {// 订单状态
                        cell.setCellValue(user.getOrderStatus());
                    } else if (celLength == 6) {// 授权时间
                        cell.setCellValue(user.getCreditTime());
                    }

                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
}
