package com.hyjf.common.excel;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xiasq
 * @version AbstractExcelExportHandler, v0.1 2018/7/4 14:35 excel导出处理器
 */
public abstract class AbstractExcelExportHandler<T> {
	private Logger logger = LoggerFactory.getLogger(AbstractExcelExportHandler.class);
	/** 日期格式化 */
	private static final String pattern = "yyyyMMdd";
	/**
	 * excel列标题和属性对应关系
	 *
	 * @return
	 */
	public abstract List<ExcelField> buildExcelFields();

	/**
	 * 查询导出总数
	 * 
	 * @return
	 */
	public abstract int countExportData();

	/**
	 * 查询明细数据
	 * 
	 * @param limitStart
	 * @param limit
	 * @return
	 */
	public abstract List<T> selectExportDataList(int limitStart, int limit);

	/**
	 * 执行导出
	 * 
	 * @param response
	 * @param fileName
	 *            文件名
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public void exportExcel(HttpServletResponse response, String fileName) {

		// 1.获取总条数
		int count = countExportData();

		// 2.构造工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(ExcelUtil.ROW_ACCESS_WINDOW_SIZE);
		ExcelUtil excelUtil = new ExcelUtil(workbook, buildExcelFields(), count, fileName);

		// 3.写入excel
		int list_count = excelUtil.getRunningQueryCount();
		for (int i = 0; i < excelUtil.getRunningQueryCount(); i++) {
			int startNum = i * ExcelUtil.ROW_ACCESS_WINDOW_SIZE;
			List<T> result = selectExportDataList(startNum, ExcelUtil.ROW_ACCESS_WINDOW_SIZE);
			logger.info("result:" + result.size() + ", i:" + i + ", 任务数还剩:" + --list_count);
			try {
				excelUtil.exportXLSX(workbook, result, startNum);
			} catch (InvocationTargetException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		// 4.输出
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			workbook.write(buildOutputStream(response, fileName + "_" + sdf.format(new Date())+".xlsx" ));
			workbook.close();
			workbook.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 构造excel输出流
	 * 
	 * @param response
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private ServletOutputStream buildOutputStream(HttpServletResponse response, String fileName) throws IOException {
		ServletOutputStream out = response.getOutputStream();
		response.reset();
		response.setContentType("application/msexcel;charset=utf-8");
		response.setHeader("content-disposition",
				"attachment;filename=" + new String((fileName).getBytes("UTF-8"), "ISO8859-1"));
		return out;
	}
}
