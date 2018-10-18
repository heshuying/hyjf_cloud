package com.hyjf.admin.utils.exportutils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.hyjf.common.util.ExportExcel;

/**
 * 导出到excel工具类
 * 采用SXSSF方式，固定ROW数量写入硬盘
 *
 * Created by cuigq on 2018/9/7
 */
public class DataSet2ExcelSXSSFHelper<T> {
    private static Logger logger = LoggerFactory.getLogger(DataSet2ExcelSXSSFHelper.class);

    /**
     * 查询结果导出到Excel
     *
     * @param workbook
     * @param sheetName
     * @param beanPropertyColumnMap：Bean属性和Excel表头对应Map
     * @param mapValueAdapter:
     * @param dataset:查询结果
     */
    public void export(SXSSFWorkbook workbook, String sheetName, Map<String, String> beanPropertyColumnMap, Map<String, IValueFormatter> mapValueAdapter, Collection<T> dataset) {

        //属性
        List<String> properties = Lists.newArrayList();
        //表头
        List<String> columnTitles = Lists.newArrayList();

        properties.addAll(beanPropertyColumnMap.keySet());
        columnTitles.addAll(beanPropertyColumnMap.values());

        String[] arrayColumn = new String[columnTitles.size()];
        columnTitles.toArray(arrayColumn);

        SXSSFSheet sheet = createWorkbookTitle(workbook, arrayColumn, sheetName);

        Iterator<T> it = dataset.iterator();
        int index = 1;
        while (it.hasNext()) {
            // 创建一行
            Row columnValueRow = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            for (int i = 0; i < properties.size(); i++) {
                // 创建相应的单元格
                Cell  cell = columnValueRow.createCell(i);
                String property = properties.get(i);
                String getMethodName = "get" + property.substring(0, 1).toUpperCase() + property.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    // 判断值的类型后进行强制类型转换
                    String textValue = "";
                    if (value != null) {
                        if (mapValueAdapter.get(property) != null) {
                            textValue = mapValueAdapter.get(property).format(value);
                        } else {
                            textValue = String.valueOf(value);
                        }
                    }
                    cell.setCellValue(textValue);
                } catch (Exception e) {
                    logger.error("导出到Excel失败！" + e.getMessage());
                    throw new IllegalArgumentException(e);
                }
            }
            index++;
        }
    }

    private SXSSFSheet createWorkbookTitle(SXSSFWorkbook workbook, String[] titles, String sheetName) {
        // 生成一个表格
        SXSSFSheet sheet = workbook.createSheet(sheetName);
        Row row = sheet.createRow(0);
        for (int celLength = 0; celLength < titles.length; celLength++) {
            // 创建相应的单元格
            Cell cell = row.createCell(celLength);
            cell.setCellValue(titles[celLength]);
        }

        return sheet;
    }

    public static void write2Response(HttpServletRequest request, HttpServletResponse response, String fileName, SXSSFWorkbook workbook) {
        write2Response(request, response, fileName, workbook,null);
    }
    public static void write2Response(HttpServletRequest request, HttpServletResponse response, String fileName, SXSSFWorkbook workbook,Cookie cookie) {
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
            if(cookie != null) {
                response.addCookie(cookie);
            }
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