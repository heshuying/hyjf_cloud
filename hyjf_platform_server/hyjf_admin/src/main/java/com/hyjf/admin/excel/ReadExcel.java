package com.hyjf.admin.excel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReadExcel extends XxlsAbstract {
    private Logger logger = LoggerFactory.getLogger(ReadExcel.class);
    public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
    public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";

    public static final String POINT = ".";
    public static final String NOT_EXCEL_FILE = " : Not the Excel file!";
    public static final String PROCESSING = "Processing...";

    private static Map<String, String> titleMap = null;
    private static Map<String, String> nameMap = null;
    private List<JSONObject> resumeList = new ArrayList<>();
    private Map<String, Integer> rowIndexMap;
    public static final String EMPTY = "";

    /**
     * Excel解析开始
     *
     * @param filePath 文件路径
     * @param nameMaps Excel表头
     * @return List<JSONObject>
     * @throws IOException
     */
    public List<JSONObject> readExcel(String filePath, Map<String, String> nameMaps,Map<String, String> titleMaps) throws IOException {
        resumeList.clear();
        if (titleMap != null){
            titleMap = titleMaps;
        }else{
            titleMap = nameMaps;
        }

        nameMap = nameMaps;
        try {
            if (filePath == null || EMPTY.equals(filePath)) {
                logger.info("文件地址为空！");
            } else {
                String postfix = getPostfix(filePath);
                if (!EMPTY.equals(postfix)) {
                    if (OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
                        new HxlsPrint(filePath).parseXls(filePath, titleMap, resumeList);
                    } else if (OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                        logger.info("文件路径 ：" + filePath);
                        readXlsx1(filePath, resumeList);
                    }
                } else {
                    logger.info(NOT_EXCEL_FILE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resumeList;

    }


    /**
     * Excel解析开始
     *
     * @param filePath 文件流
     * @param nameMaps Excel表头
     * @return List<JSONObject>
     * @throws IOException
     */
    public List<JSONObject> readExcel(InputStream filePath, Map<String, String> nameMaps) throws IOException {
        resumeList.clear();
        titleMap = nameMaps;
        nameMap = nameMaps;
        try {
        	// modify by libin sonar start filePath 不是string 不能用 !EMPTY.equals(filePath)这种判断
            if (filePath != null) {
            // modify by libin sonar end
                logger.info("文件路径 ：" + filePath);
                readXlsx1(filePath, resumeList);
            } else {
                logger.info(NOT_EXCEL_FILE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resumeList;

    }

    /**
     * get postfix of the path
     *
     * @param path 文件路径
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
     * @param path       文件路径
     * @param resumeList 返回结果list
     * @return
     * @throws IOException
     */
    public void readXlsx1(String path, List<JSONObject> resumeList) throws IOException {
        logger.info(PROCESSING + path);
        try {
            new ReadExcel().processOneSheet(path, 1, resumeList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("读取excel文件失败,失败原因 ：", e);
        }
    }


    /**
     * Read the Excel 2010
     *
     * @param in         文件流
     * @param resumeList 返回结果list
     * @return
     * @throws IOException
     */
    public void readXlsx1(InputStream in, List<JSONObject> resumeList) throws IOException {
        try {
            new ReadExcel().processOneSheet(in, 1, resumeList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("读取excel文件失败,失败原因 ：", e);
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
                String cellValue;
                if (rowlist.size() != 0) {
                    if (rowlist.size() < (index + 1)) {
                        cellValue = "";
                    } else {
                        cellValue = rowlist.get(index);
                    }
                    String key = entry.getKey();
                    //保存数据
                    jsonObject = setFieldValue(key, cellValue, jsonObject);
                }
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
