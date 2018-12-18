/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.bean.ic;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author tanyy
 * @version OperationGroupReport, v0.1 2018/8/1 9:45
 */
@Document(collection = "ht_operation_group_report")
@SuppressWarnings("unchecked")
public class OperationGroupReport implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static int ageRange1 = 25;
    public static int ageRange2 = 35;
    public static int ageRange3 = 50;
    public static int ageRange4 = 1000;

    public static String ageRange1Desc = "25岁以下";
    public static String ageRange2Desc = "25至35岁";
    public static String ageRange3Desc = "36至50岁";
    public static String ageRange4Desc = "50岁以上";

//	public static String ageRange1Color = "red";
//	public static String ageRange2Color = "green";
//	public static String ageRange3Color = "black";
//	public static String ageRange4Color = "blue";

    public static String ageRange1Color = "#7893f4";
    public static String ageRange2Color = "#fd6767";
    public static String ageRange3Color = "#fecad9";
    public static String ageRange4Color = "#6bc3ca";

    public static int MALE = 1;
    public static int FEMALE = 2;

    private String id;
    // 数据统计的月份，用于检索

    @Indexed(unique = true,name="statisticsMonth")
    private int statisticsMonth;
    // 插入数据的时间
    private int insertDate;
    public Map<Integer, String> investorRegionMap;
    public Map<Integer, Integer> investorSexMap;
    public Map<Integer, Integer> investorAgeMap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatisticsMonth() {
        return statisticsMonth;
    }

    public void setStatisticsMonth(int statisticsMonth) {
        this.statisticsMonth = statisticsMonth;
    }

    public int getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(int insertDate) {
        this.insertDate = insertDate;
    }

    // public Map<Integer, String> getInvestorRegionMap() {
    // return investorRegionMap;
    // }
    // public void setInvestorRegionMap(Map<Integer, String> investorRegionMap)
    // {
    // this.investorRegionMap = investorRegionMap;
    // }
    public Map<Integer, Integer> getInvestorSexMap() {
        return investorSexMap;
    }

    public void setInvestorSexMap(Map<Integer, Integer> investorSexMap) {
        this.investorSexMap = investorSexMap;
    }

    public Map<Integer, Integer> getInvestorAgeMap() {
        return investorAgeMap;
    }

    public void setInvestorAgeMap(Map<Integer, Integer> investorAgeMap) {
        this.investorAgeMap = investorAgeMap;
    }

    public Map<Integer, String> getInvestorRegionMap() {
        return investorRegionMap;
    }

    public void setInvestorRegionMap(Map<Integer, String> investorRegionMap) {
        this.investorRegionMap = investorRegionMap;
    }

    /**
     * 格式化返回的数据，保留两位小数
     *
     * @return
     */
    public String formatDate(float data) {
        DecimalFormat df = new DecimalFormat("##.##");
        return df.format(data);
    }

    public String getAge(String desc, String color, String value) {
        JSONObject ageJson = new JSONObject();
        ageJson.put("name", desc);
        ageJson.put("color", color);
        ageJson.put("value", value);
        return ageJson.toString();
    }

    /**
     * 整理投资人区域信息，按照由小到大排列
     *
     * @param cityMap
     */
    public List<SubEntity> orgnizeData(Map<Integer, String> cityMap) {
        Iterator<Integer> iter = cityMap.keySet().iterator();
        List<SubEntity> list = new ArrayList<SubEntity>();
        while (iter.hasNext()) {
            String[] arr = cityMap.get(iter.next()).split(":");
            SubEntity sub = new SubEntity();
            sub.setName(arr[1]);
            sub.setValue(Integer.valueOf(arr[0]));
            list.add(sub);
        }

        Comparator mycmp = ComparableComparator.getInstance();
        mycmp = ComparatorUtils.nullLowComparator(mycmp);// 允许null
        mycmp = ComparatorUtils.reversedComparator(mycmp);// 逆序
        ArrayList<Object> sortFields=new ArrayList<Object>();
        sortFields.add(new BeanComparator("value",mycmp));
        ComparatorChain multiSort=new ComparatorChain(sortFields);
        Collections.sort(list, multiSort);
        return list;
    }

    public List<SubEntity> formatList(List<SubEntity> list) {
        int total=0;
        for(int i=0;i<list.size();i++){
            total=total+list.get(i).getValue();
        }
        for(int i=0;i<list.size();i++){
            SubEntity sub=list.get(i);
            int value=sub.getValue();
            double perValue = 0;
            if(0!=total){
                perValue = (double)value * 100/total;
            }
            double per= BigDecimal.valueOf(perValue).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            sub.setPercent(per+"%");
        }
        return list;
    }
}
