package com.hyjf.admin.excel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ReadExcel extends XxlsAbstract {
    public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
    public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";

    public static final String POINT = ".";
    public static final String LIB_PATH = "E:\\date\\";
    public static final String STUDENT_INFO_XLS_PATH = LIB_PATH + "excel-41万简历记录" + POINT + OFFICE_EXCEL_2010_POSTFIX;
    public static final String STUDENT_INFO_XLSX_PATH = LIB_PATH + "60000" + POINT + OFFICE_EXCEL_2010_POSTFIX;
    public static final String NOT_EXCEL_FILE = " : Not the Excel file!";
    public static final String PROCESSING = "Processing...";

    private static Map<String, String> titleMap = null;
    private static Map<String, String> nameMap = null;
    private List<JSONObject> resumeList = new ArrayList<>();
    private Map<String, Integer> rowIndexMap;
    public static final String EMPTY = "";

    public static void main(String[] args) {
        try {
            System.out.println("startTime:" + new Date());
            Map<String, String> nameMaps = new HashMap<>();
            nameMaps.put("couponCode", "couponCode");
            nameMaps.put("activityId", "activityId");
            nameMaps.put("userName", "userName");
            ReadExcel readExcel = new ReadExcel();
//			List<JSONObject> list = readExcel.readExcel("E:/excel/汇计划加息8.xlsx",nameMaps);
            List<JSONObject> list = readExcel.readExcel("E:/excel/汇计划加息9.xls", nameMaps);

            System.out.println("size:" + list.size());
            System.out.println("endTime:" + new Date());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Excel解析开始
     *
     * @param filePath
     * @param nameMaps
     * @return
     * @throws IOException
     */
    public List<JSONObject> readExcel(String filePath, Map<String, String> nameMaps) throws IOException {
        resumeList.clear();
        titleMap = nameMaps;
        nameMap = nameMaps;
        try {
            if (filePath == null || EMPTY.equals(filePath)) {
                System.out.println("文件地址为空！");
            } else {
                String postfix = getPostfix(filePath);
                if (!EMPTY.equals(postfix)) {
                    if (OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
                        new HxlsPrint(filePath).parseXls(filePath, titleMap, resumeList);
                    } else if (OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                        readXlsx1(filePath, resumeList);
                    }
                } else {
                    System.out.println(NOT_EXCEL_FILE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resumeList;

    }

    /**
     * get postfix of the path
     *
     * @param path
     * @return
     */
    public static String getPostfix(String path) {
        if (path == null || EMPTY.equals(path.trim())) {
            return EMPTY;
        }
        if (path.contains(POINT)) {
            return path.substring(path.lastIndexOf(POINT) + 1, path.length());
        }
        return EMPTY;
    }


    /**
     * Read the Excel 2010
     *
     * @param path       the path of the excel file
     * @param path
     * @param resumeList
     * @return
     * @throws IOException
     */
    public void readXlsx1(String path, List<JSONObject> resumeList) throws IOException {
        System.out.println(PROCESSING + path);
        try {
            new ReadExcel().processOneSheet(path, 1, resumeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 输出解析的数据
     */
    @Override
    public void optRows(int sheetIndex, int curRow, List<String> rowlist, List<JSONObject> resumeList) throws SQLException {
        JSONObject jsonObject = null;
        if (rowIndexMap != null && rowIndexMap.size() > 0) {
            jsonObject = new JSONObject();
            for (Map.Entry<String, Integer> entry : rowIndexMap.entrySet()) {
                Integer index = entry.getValue();
                String cellValue = rowlist.get(index);
                String key = entry.getKey();
                //保存数据
                jsonObject = setFieldValue(key, cellValue, jsonObject);

            }
        } else {
            for (int i = 0; i < rowlist.size(); i++) {
                String value = rowlist.get(i);
                if (rowIndexMap == null || rowIndexMap.size() <= 0) {
                    rowIndexMap = new HashMap<String, Integer>();
                }
                if (titleMap.containsKey(value)) {
                    rowIndexMap.put(titleMap.get(value), i);
                }
            }
        }
        if (jsonObject != null) {

            resumeList.add(jsonObject);
        }
    }


    /**
     * 保存对象数据值
     *
     * @param key
     * @param cellValue
     * @param jsonObject
     * @return
     */
    public JSONObject setFieldValue(String key, String cellValue, JSONObject jsonObject) {
        if (cellValue != null && !"".equals(cellValue.trim())) {
            if (cellValue.contains("E") && key.equals("base.mobile")) {
                BigDecimal bd = new BigDecimal(cellValue);
                cellValue = bd.toPlainString();
            }
            try {
                if (key.contains("year")) {
                    if (cellValue.contains("岁")) {
                        cellValue = cellValue.substring(0, cellValue.indexOf("岁"));
                    }
                    key = "base.born";
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar rightNow = Calendar.getInstance();
                    rightNow.setTime(new Date());
                    rightNow.add(Calendar.YEAR, -Integer.parseInt(cellValue));//日期减year年
                    Date dt1 = rightNow.getTime();
                    cellValue = sdf.format(dt1);
                }
            } catch (Exception e) {
                key = null;
            }
            if (key != null) {
                try {
                    if (key.indexOf("[") > 0 && key.indexOf("[") < key.indexOf(".")) {
                        String entityName = key.substring(0, key.indexOf("["));
                        String otherName = key.substring(key.indexOf("]") + 1, key.indexOf("."));
                        JSONArray finalName = (JSONArray) jsonObject.get(entityName);
                        if (finalName == null) {
                            finalName = new JSONArray();
                            JSONObject js = new JSONObject();
                            finalName.add(js);
                            jsonObject.put(entityName, finalName);
                        }
                        JSONArray json = setJSONValue(key.substring(key.indexOf(".") + 1), cellValue, finalName, otherName);
                        jsonObject.remove(entityName);
                        jsonObject.put(entityName, json);
                    } else if (key.contains(".")) {
                        String entityName = key.substring(0, key.indexOf("."));
                        JSONObject finalName = (JSONObject) jsonObject.get(entityName);
                        if (finalName == null) {
                            finalName = new JSONObject();
                            jsonObject.put(entityName, finalName);
                        }
                        //递归调用保存数据
                        JSONObject json = setFieldValue(key.substring(key.indexOf(".") + 1), cellValue, finalName);
                        jsonObject.remove(entityName);
                        jsonObject.put(entityName, json);
                    } else {
                        Object object = jsonObject.get(key);
                        if (object != null) {
                            jsonObject.remove(key);
                        }
                        jsonObject.put(nameMap.get(key), cellValue);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject;
    }

    /**
     * 对数组进行处理数据
     *
     * @param key
     * @param cellValue
     * @param jsonArray
     * @param otherName
     * @return
     */
    public JSONArray setJSONValue(String key, String cellValue, JSONArray jsonArray, String otherName) {
        try {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                jsonObject.put(nameMap.get(key), cellValue);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

}
