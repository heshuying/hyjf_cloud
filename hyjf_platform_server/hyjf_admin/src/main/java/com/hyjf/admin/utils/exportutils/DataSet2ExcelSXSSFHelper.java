package com.hyjf.admin.utils.exportutils;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.streaming.SheetDataWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
            //properties.size() + 1 是因为添加了"序号"一栏
            for (int i = 0; i < properties.size() + 1; i++) {
                // 创建相应的单元格
                Cell cell = columnValueRow.createCell(i);
                //为"序号"一栏赋值
                if(i == 0){
                    cell.setCellValue(index);
                    continue;
                }
                //为"序号"栏空出一栏，导致【i】从1开始才是自定义栏位,而数组下标是从0开始，所以这里 i-1
                String property = properties.get(i-1);
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
                    if (mapValueAdapter.get(property) != null) {
                        cell.setCellValue(mapValueAdapter.get(property).format(null));
                    }
                    logger.error("导出到Excel失败！" + e.getMessage());
                    //throw new IllegalArgumentException(e);
                }
            }
            index++;
        }
    }
    /**
     * 查询结果导出到Excel
     *
     * @param workbook
     * @param sheetName
     * @param beanPropertyColumnMap：Bean属性和Excel表头对应Map
     * @param mapValueAdapter:
     * @param dataset:查询结果
     */
    public void export(SXSSFWorkbook workbook, String sheetName, Map<String, String> beanPropertyColumnMap, Map<String, IValueFormatter> mapValueAdapter, Collection<T> dataset,String[] lastRow) {

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
            //properties.size() + 1 是因为添加了"序号"一栏
            for (int i = 0; i < properties.size() + 1; i++) {
                // 创建相应的单元格
                Cell cell = columnValueRow.createCell(i);
                //为"序号"一栏赋值
                if(i == 0){
                    cell.setCellValue(index);
                    continue;
                }
                //为"序号"栏空出一栏，导致【i】从1开始才是自定义栏位,而数组下标是从0开始，所以这里 i-1
                String property = properties.get(i-1);
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
                    if (mapValueAdapter.get(property) != null) {
                        cell.setCellValue(mapValueAdapter.get(property).format(null));
                    }
                    logger.error("导出到Excel失败！" + e.getMessage());
                    //throw new IllegalArgumentException(e);
                }
            }
            index++;
        }
        //excel最后一行内容
        if(lastRow.length>0){
            //总条数
            Row rowTow = sheet.createRow(index);
            for (int celLength = 0; celLength < lastRow.length; celLength++) {
                // 创建相应的单元格
                Cell cell = rowTow.createCell(celLength);
                cell.setCellValue(lastRow[celLength]);
            }
        }
    }
    private SXSSFSheet createWorkbookTitle(SXSSFWorkbook workbook, String[] titles, String sheetName) {
        // 生成一个表格
        SXSSFSheet sheet = workbook.createSheet(sheetName);
        Row row = sheet.createRow(0);
        //titles.length + 1  是因为添加了"序号"一栏
        for (int celLength = 0; celLength < titles.length + 1; celLength++) {
            // 创建相应的单元格
            if(celLength != 0){
                Cell cell = row.createCell(celLength);
                //为"序号"栏空出一栏，导致celLength从1开始才是自定义栏位,而数组下标是从0开始，所以这里 celLength-1
                cell.setCellValue(titles[celLength-1]);
                continue;
            }
            //添加"序号"一栏
            Cell cell = row.createCell(0);
            cell.setCellValue("序号");
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
            workbook.dispose();
           // deleteSXSSFTempFiles(workbook);
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
    public static void deleteSXSSFTempFiles(SXSSFWorkbook workbook)
            throws NoSuchFieldException, IllegalAccessException {
        int numberOfSheets = workbook.getNumberOfSheets();
        // iterate through all sheets (each sheet as a temp file)
        for (int i = 0; i <= numberOfSheets; i++) {
            Sheet sheetAt = workbook.getSheetAt(i);
            // delete only if the sheet is written by stream
            if (sheetAt instanceof SXSSFSheet) {
                SheetDataWriter sdw = (SheetDataWriter) getPrivateAttribute(sheetAt, "_writer");
                File f = (File) getPrivateAttribute(sdw, "_fd");
                try {
                    f.delete();
                } catch (Exception ex) {
                    // could not delete the file
                }
            }
        }
    }
    public static Object getPrivateAttribute(
            Object containingClass, String fieldToGet)
            throws NoSuchFieldException, IllegalAccessException 
    {
        // get the field of the containingClass instance
        Field declaredField = containingClass.getClass().getDeclaredField(fieldToGet);
        declaredField.setAccessible(true); // access it
        Object get = declaredField.get(containingClass); // return it!
        return get;
    }
}