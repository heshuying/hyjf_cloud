/**
 * Description:利用poi导出excel
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月26日 下午6:47:27
 * Modification History:
 * Modified by : 
 */

package com.hyjf.common.util;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * @param <T>
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 */
public class ExportExcel<T> implements Serializable {

	/**
	 * serialVersionUID:序列化id
	 */

	private static final long serialVersionUID = -5142024015171582474L;

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 
	 * @param sheetName
	 *            表格sheet名称
	 * @param author
	 *            表格作者
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 * @throws IOException
	 */
	public void exportExcel(String sheetName, String author, Map<String, String> columnNames, Collection<T> dataset, HttpServletResponse response, String pattern) throws IOException {
		exportExcel(sheetName, author, columnNames, dataset, response.getOutputStream(), pattern);
	}

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 
	 * @param sheetName
	 *            表格sheet名称
	 * @param author
	 *            表格作者
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */
	public void exportExcel(String sheetName, String author, Map<String, String> columnNames, Collection<T> dataset, ServletOutputStream out, String pattern) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(sheetName);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.WHITE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor(author);
		// 产生表格标题行
		HSSFRow columnTitleRow = sheet.createRow(0);
		// 根据标题列名字，导出时显示相应的导出列名字
		Iterator<Map.Entry<String, String>> columIterator = columnNames.entrySet().iterator();
		int columnIndex = 0;
		String[] columns = new String[columnNames.size()];
		while (columIterator.hasNext()) {
			Map.Entry<String, String> entry = columIterator.next();
			String column = (String) entry.getKey();
			String columnName = (String) entry.getValue();
			HSSFCell cellTitle = columnTitleRow.createCell(columnIndex);
			cellTitle.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(columnName);
			cellTitle.setCellValue(text);
			columns[columnIndex] = column;
			columnIndex++;
		}
		// 遍历集合数据，产生数据行
		int index = 1;// 行号
		Iterator<T> it = dataset.iterator();
		while (it.hasNext()) {
			// 创建一行
			HSSFRow columnValueRow = sheet.createRow(index);
			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			for (int i = 0; i < columns.length; i++) {
				// 创建相应的单元格
				HSSFCell cellValue = columnValueRow.createCell(i);
				cellValue.setCellStyle(style2);
				String getMethodName = "get" + columns[i].substring(0, 1).toUpperCase() + columns[i].substring(1);
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = null;
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
						} else if (value instanceof byte[]) {
							// 有图片时，设置行高为60px;
							columnValueRow.setHeightInPoints(60);
							// 设置图片所在列宽度为80px,注意这里单位的一个换算
							sheet.setColumnWidth(i, (short) (35.7 * 80));
							// sheet.autoSizeColumn(i);
							byte[] bsValue = (byte[]) value;
							HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6, index);
							anchor.setAnchorType(2);
							patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
						} else {
							// 其它数据类型都当作字符串简单处理
							textValue = value.toString();
						}
					} else {
						continue;
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cellValue.setCellValue(Double.parseDouble(textValue));
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(textValue);
							HSSFFont font3 = workbook.createFont();
							font3.setColor(HSSFColor.BLACK.index);
							richString.applyFont(font3);
							cellValue.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
				// 自动调整列的宽度
				sheet.autoSizeColumn(i);
			}
			index++;
		}
		// 根据列名填充相应的数据
		try {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 创建导出标题
	 * 
	 * @param titles
	 * @param sheetName
	 * @return
	 */
	public static HSSFSheet createHSSFWorkbookTitle(HSSFWorkbook workbook, String[] titles, String sheetName) {

		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(sheetName);
		Row row = sheet.createRow(0);
		for (int celLength = 0; celLength < titles.length; celLength++) {
			// 创建相应的单元格
			Cell cell = row.createCell(celLength);
			cell.setCellValue(titles[celLength]);
		}

		return sheet;
	}

	/**
	 * 输出附件
	 * 
	 * @param titles
	 * @param sheetName
	 * @return
	 */
	public static void writeExcelFile(HttpServletResponse response, HSSFWorkbook workbook, String[] titles, String fileName) {
		// 根据列名填充相应的数据
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			response.reset();
			response.setContentType("application/msexcel;charset=utf-8");
			response.setHeader("content-disposition", "attachment;filename=" + new String((fileName).getBytes("UTF-8"), "ISO8859-1"));

			workbook.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 输出附件
	 * 
	 * @param titles
	 * @param sheetName
	 * @return
	 */
	public static void writeExcelFile(HttpServletRequest request,HttpServletResponse response, HSSFWorkbook workbook, String[] titles, String fileName) {
		// 根据列名填充相应的数据
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			response.reset();
			response.setContentType("application/msexcel;charset=utf-8");

            String filenameWrapper;
            String header = request.getHeader("User-Agent").toUpperCase();
            if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
                filenameWrapper = URLEncoder.encode(fileName, "utf-8");
                filenameWrapper = filenameWrapper.replace("+", "%20");    //IE下载文件名空格变+号问题
            } else {
                filenameWrapper = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }
			response.setHeader("content-disposition", "attachment;filename=" + filenameWrapper);

			workbook.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}