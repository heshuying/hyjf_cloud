package com.hyjf.admin.utils.exportutils;

import com.google.common.collect.Lists;
import com.hyjf.admin.utils.ExportExcel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 导出到excel工具类
 * Created by cuigq on 2018/1/23.
 */
public class DataSet2ExcelHelper<T> {
    private static Logger logger = LoggerFactory.getLogger(DataSet2ExcelHelper.class);

    /**
     * 查询结果导出到Excel
     *
     * @param workbook
     * @param sheetName
     * @param beanPropertyColumnMap：Bean属性和Excel表头对应Map
     * @param mapValueAdapter:
     * @param dataset:查询结果
     */
    public void export(HSSFWorkbook workbook, String sheetName, Map<String, String> beanPropertyColumnMap, Map<String, IValueFormatter> mapValueAdapter, Collection<T> dataset) {

        //属性
        List<String> properties = Lists.newArrayList();
        //表头
        List<String> columnTitles = Lists.newArrayList();

        properties.addAll(beanPropertyColumnMap.keySet());
        columnTitles.addAll(beanPropertyColumnMap.values());

        String[] arrayColumn = new String[columnTitles.size()];
        columnTitles.toArray(arrayColumn);

        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, arrayColumn, sheetName);

        Iterator<T> it = dataset.iterator();
        int index = 1;
        while (it.hasNext()) {
            // 创建一行
            HSSFRow columnValueRow = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            for (int i = 0; i < properties.size(); i++) {
                // 创建相应的单元格
                HSSFCell cell = columnValueRow.createCell(i);
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
                    logger.debug("导出到Excel失败！", e);
                }
            }
            index++;
        }
    }
    public static void write2Response(HttpServletRequest request, HttpServletResponse response, String fileName, HSSFWorkbook workbook) {
        write2Response(request, response, fileName, workbook,null);
    }
    public static void write2Response(HttpServletRequest request, HttpServletResponse response, String fileName, HSSFWorkbook workbook,Cookie cookie) {
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
            logger.error(e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }
}