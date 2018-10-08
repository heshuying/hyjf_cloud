/*
 * Copyright (c) 2005-2018 , FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.hyjf.common.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiasq
 * @version ExcelUtil, v0.1 2018/7/4 14:35 excel导出工具类
 */
public class ExcelUtil {
	/** 每次往Excel写几条数据，也就是内存中的数据 */
	public static final int ROW_ACCESS_WINDOW_SIZE = 1000;
	/** excel写入开始行号,0是标题行，1开始是数据行 */
	private static final int EXCEL_BEGIN_ROW_INDEX = 1;
	/** 表头数组 */
	private String[] headers;
	/** 字段名数组 */
	private String[] properties;
	/** 表头数组+字段名数组 */
	private List<ExcelField> headList;
	/** 当前sheet */
	private SXSSFSheet currentSheet;
	/** sheet序号 */
	private AtomicInteger sheetCount = new AtomicInteger(1);
	/** 每个sheet页显示多少条数据 */
	private static final int sheetShowCount = 10000;
	/** 分页查询次数 */
	private int runningQueryCount = 1;
	/** 允许最大导出数据量 */
	private static final int ALLOW_EXPORT_MAX_COUNT = 100000;
	/** 日期格式化 */
	private static final String pattern = "yyyy-MM-dd HH:mm:ss";
	/** 表头样式 */
	private CellStyle headCellStyle;
	/** 表内容样式 */
	private CellStyle contentCellStyle;
	/** sheet name */
	private String sheetName;

	/**
	 * 通用EXCEL导出 调用前请先调用 initExport(workbook, headList);
	 *
	 * @param workbook
	 * @param centnts
	 *            内容
	 * @param startRow
	 */
	public <T> void exportXLSX(SXSSFWorkbook workbook, List<T> centnts, int startRow)
			throws InvocationTargetException, IllegalAccessException {

		// 遍历集合数据，产生数据行
		int rowIndex = startRow;

		for (T obj : centnts) {
			// 首or尾行
			synchronized (currentSheet) {
				if (rowIndex == 0) {
					// 数据内容从 rowIndex=2开始
					rowIndex += EXCEL_BEGIN_ROW_INDEX;
				} else if ((rowIndex %= sheetShowCount) == 0) {
					// 如果数据超过了，则在第二页显示
					sheetCount.addAndGet(1);
					rowIndex += EXCEL_BEGIN_ROW_INDEX;
					initHeaderAndSheet(workbook, headList);
				}
			}

			SXSSFRow dataRow;
			dataRow = currentSheet.createRow(rowIndex);
			for (int i = 0; i < properties.length; i++) {
				SXSSFCell cell = dataRow.createCell(i);

				// 处理结果集是List<Map<String,Object>>
				if (obj instanceof Map) {
					Map map = (Map) obj;
					Object cellValue = map.get(properties[i]);
					cell.setCellValue(convertValueToString(cellValue));
					cell.setCellStyle(contentCellStyle);
				} else {
					Class tClass = obj.getClass();
					Field fieldlist[] = tClass.getDeclaredFields();
					for (Field f : fieldlist) {
						String fName = f.getName();
						if (fName.equals(properties[i])) {
							String paramName = fName.substring(0, 1).toUpperCase() + fName.substring(1, fName.length());
							try {
								Object cellValue = tClass.getMethod("get" + paramName).invoke(obj);
								cell.setCellValue(convertValueToString(cellValue));
								cell.setCellStyle(contentCellStyle);
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			rowIndex++;
		}

	}

	/**
	 * 数据转换
	 * 
	 * @param value
	 * @return
	 */
	private String convertValueToString(Object value) {
		String textValue = "";
		if (value != null) {
			if (value instanceof Integer) {
				int intValue = (Integer) value;
				textValue = String.valueOf(intValue);
			} else if (value instanceof Float) {
				float fValue = (Float) value;
				textValue = String.valueOf(fValue);
			} else if (value instanceof Double) {
				double dValue = (Double) value;
				textValue = String.valueOf(dValue);
			} else if (value instanceof Long) {
				long longValue = (Long) value;
				textValue = String.valueOf(longValue);
			} else if (value instanceof Boolean) {
				boolean bValue = (Boolean) value;
				textValue = "true";
				if (!bValue) {
					textValue = "false";
				}
			} else if (value instanceof Date) {
				Date date = (Date) value;
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				textValue = sdf.format(date);
			} else {
				// 其它数据类型都当作字符串简单处理
				textValue = value.toString();
			}
		}

		return textValue;
	}

	/**
	 * 设置excel样式，生成第一行Header数据
	 *
	 * @param workbook
	 *            头
	 * @param headList
	 *            列头 用map无法保证列的顺序，所以改用list
	 * @param count
	 *            导出数据总量
	 * @param sheetName
	 */
	public ExcelUtil(SXSSFWorkbook workbook, List<ExcelField> headList, int count, String sheetName) {
		this.headList = headList;
		workbook.setCompressTempFiles(true);

		if (count > ALLOW_EXPORT_MAX_COUNT) {
			throw new RuntimeException(
					"一次行导出数据过多，请减少导出数据量，当前导出：" + count + "条，最大允许导出: " + ALLOW_EXPORT_MAX_COUNT + "条");
		}
		runningQueryCount = count / ROW_ACCESS_WINDOW_SIZE + 1;

		this.sheetName = sheetName;
		initHeaderAndSheet(workbook, headList);
	}

	private void initHeaderAndSheet(SXSSFWorkbook workbook, List<ExcelField> headList) {

		// 生成一个(带标题)表格
		currentSheet = workbook.createSheet(sheetName + "_第" + sheetCount + "页");
		int headerSize = headList.size();
		// 产生表格标题行,以及设置列宽
		properties = new String[headerSize];
		headers = new String[headerSize];
		int index = 0;
		for (ExcelField field : headList) {
			String fieldName = field.getName();
			properties[index] = field.getValue();
			headers[index] = fieldName;
			index++;
		}

		initStyle(workbook);

		// 列头 rowIndex =1
		SXSSFRow headerRow = currentSheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			SXSSFCell sxssfCell = headerRow.createCell(i);
			sxssfCell.setCellValue(headers[i]);
			sxssfCell.setCellStyle(headCellStyle);
		}

	}

	/**
	 * 初始化样式
	 * 
	 * @param workbook
	 */
	private void initStyle(SXSSFWorkbook workbook) {
		// 生成一个样式
		CellStyle headStyle = workbook.createCellStyle();
		// 设置这些样式
		headStyle.setFillForegroundColor(IndexedColors.WHITE.index);
		headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headStyle.setBorderBottom(BorderStyle.THIN);
		headStyle.setBorderLeft(BorderStyle.THIN);
		headStyle.setBorderRight(BorderStyle.THIN);
		headStyle.setBorderTop(BorderStyle.THIN);
		headStyle.setAlignment(HorizontalAlignment.CENTER);
		// 生成一个字体
		Font font = workbook.createFont();
		font.setColor(IndexedColors.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		// 字体加粗
		font.setBold(true);
		// 把字体应用到当前的样式
		headStyle.setFont(font);
		this.headCellStyle = headStyle;

		// 生成并设置另一个样式
		CellStyle contentStyle = workbook.createCellStyle();
		contentStyle.setFillForegroundColor(IndexedColors.WHITE.index);
		contentStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		contentStyle.setBorderBottom(BorderStyle.THIN);
		contentStyle.setBorderLeft(BorderStyle.THIN);
		contentStyle.setBorderRight(BorderStyle.THIN);
		contentStyle.setBorderTop(BorderStyle.THIN);
		contentStyle.setAlignment(HorizontalAlignment.CENTER);
		contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// 生成另一个字体
		Font font2 = workbook.createFont();
		font2.setBold(false);
		// 把字体应用到当前的样式
		contentStyle.setFont(font2);
		this.contentCellStyle = contentStyle;
	}

	public int getRunningQueryCount() {
		return runningQueryCount;
	}

}
