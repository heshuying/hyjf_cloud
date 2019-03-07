package com.hyjf.admin.excel;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HxlsPrint extends HxlsAbstract {

	private static final Logger logger = LoggerFactory.getLogger(HxlsPrint.class);

	private Map<String, Integer> rowIndexMap;
	private static Map<String, String> titleMap = null;
	private static List<JSONObject> resumeList = null;
	public HxlsPrint(String feth) throws IOException,
			FileNotFoundException, SQLException {
		super(feth);
	}
	
	
	/**
	 * 输出解析的数据
	 */
	@Override
	public void optRows(int sheetIndex,int curRow, Map<Integer, String> rowMap) throws SQLException {
		
		if (sheetIndex == 0) {
			JSONObject jsonObject = null;
			ReadExcel readExcel = new ReadExcel();
		  	  
			if(rowIndexMap != null && rowIndexMap.size() > 0){
				jsonObject = new JSONObject();
				for(Map.Entry<String, Integer> entry : rowIndexMap.entrySet()){
        		 Integer index = entry.getValue();
        		 String cellValue = rowMap.get(index);
        		 String key = entry.getKey();
        		 readExcel.setFieldValue(key,cellValue,jsonObject);
        	 }
			}else {
				for (int i = 0; i < rowMap.size(); i++) {
					String value = rowMap.get(i);
					if (rowIndexMap == null || rowIndexMap.size() <= 0) {
						rowIndexMap = new HashMap<String, Integer>();
				}
  				if(titleMap.containsKey(value)){
  					rowIndexMap.put(titleMap.get(value), i);
  					}
				}
			}
			if(jsonObject != null){
  				resumeList.add(jsonObject);
  			}
		}	
	}
	
	/**读取到的Excel文档,返回文档的content*/  
    public List<JSONObject> getList() {
		return resumeList;
	}
	
	/**
	 * 调用方法，解析xls
	 * @param feth
	 * @param titleMaps
	 * @param resumeLists
	 */
	public void parseXls(String feth, Map<String, String> titleMaps,List<JSONObject> resumeLists){
		HxlsPrint xls2csv;
		try {
			titleMap = titleMaps;
			resumeList = resumeLists;
			xls2csv = new HxlsPrint(feth);
			xls2csv.process();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
}
