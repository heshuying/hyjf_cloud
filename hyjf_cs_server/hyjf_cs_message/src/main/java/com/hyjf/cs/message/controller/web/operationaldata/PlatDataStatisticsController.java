package com.hyjf.cs.message.controller.web.operationaldata;


import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.message.PlatDataAgeDataBean;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.message.bean.ic.report.OperationGroupReport;
import com.hyjf.cs.message.bean.ic.report.OperationReport;
import com.hyjf.cs.message.bean.ic.SubEntity;
import com.hyjf.cs.message.service.report.PlatDataStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author tanyy
 * @version PlatDataStatisticsController, v0.1 2018/9/16 17:39
 */
@Api(tags = "运营数据")
@RestController
@RequestMapping("/hyjf-web/platdatastatistics")
public class PlatDataStatisticsController {

    private Logger _log = LoggerFactory.getLogger(PlatDataStatisticsController.class);

    @Autowired
    private PlatDataStatisticsService platDataStatisticsService;

    /**
     * 获取平台实时数据
     *
     * @return
     */
    @ApiOperation(value = "运营数据", notes = "运营数据")
    @GetMapping("/initPlatData")
    @ResponseBody
    public WebResult<Object> getPlatformRealTimeData() {
        WebResult result = new WebResult();
        JSONObject jsonObject = new JSONObject();
        DecimalFormat df = new DecimalFormat("#,##0");
        OperationReport oe = platDataStatisticsService.findOneOperationReportEntity();
        _log.info("获取到的OperationReport=" + JSONObject.toJSONString(oe));
        // 累计出借
        jsonObject.put("investTotal", df.format(platDataStatisticsService.selectTotalInvest().setScale(0, BigDecimal.ROUND_DOWN)));
        // 累计收益
        jsonObject.put("interestTotal",
                df.format(platDataStatisticsService.selectTotalInterest().setScale(0, BigDecimal.ROUND_DOWN)));
        // 累计交易笔数
        jsonObject.put("tenderCounts", platDataStatisticsService.selectTotalTradeSum());
        // 累计人均出借金额
        jsonObject.put("perCapitaInvestment", df.format(oe.getPerInvest()));
        // 借贷余额（原借款人待还）
        if (oe.getWillPayMoney() != null) {
            jsonObject.put("totalRepayAwait", df.format(oe.getWillPayMoney()));
        } else {
            jsonObject.put("totalRepayAwait", df.format(0));
        }
        // 借贷笔数
        jsonObject.put("totalRepayCounts", oe.getLoanNum());
        // 出借人总数
        jsonObject.put("registerCounts", oe.getTenderCount());

        OperationGroupReport oegroup = platDataStatisticsService.findOneOperationMongoGroupEntity();
        Map<Integer, Integer> ageMap = oegroup.getInvestorAgeMap();
        int r1 = ageMap.get(OperationGroupReport.ageRange1);
        int r2 = ageMap.get(OperationGroupReport.ageRange2);
        int r3 = ageMap.get(OperationGroupReport.ageRange3);
        int r4 = ageMap.get(OperationGroupReport.ageRange4);
        int total = r1 + r2 + r3 + r4;

        // 年龄
        String ageData = "[ {value:" + r1 + ", name:'25岁以下:　　　" + oegroup.formatDate((float) r1 * 100 / total)
                + "%　',icon:'circle',}," + "{value:" + r2 + ", name:'25至35岁:　　　"
                + oegroup.formatDate((float) r2 * 100 / total) + "%　',icon:'circle',}," + "{value:" + r3
                + ", name:'36至50岁:　　　" + oegroup.formatDate((float) r3 * 100 / total) + "%　' ,icon:'circle',},"
                + "{value:" + r4 + ", name:'50岁以上:　　　" + oegroup.formatDate((float) r4 * 100 / total)
                + "%　',icon:'circle',}]";

        List<PlatDataAgeDataBean> ageDataBeanList = new ArrayList<PlatDataAgeDataBean>();
        PlatDataAgeDataBean bean = new PlatDataAgeDataBean();
        bean.setName("25岁以下:");
        bean.setRateValue(oegroup.formatDate((float) r1 * 100 / total) + "%");
        bean.setStyleClass("background:rgb(254,216,125)");

        PlatDataAgeDataBean bean1 = new PlatDataAgeDataBean();
        bean1.setName("25至35岁:");
        bean1.setRateValue(oegroup.formatDate((float) r2 * 100 / total) + "%");
        bean1.setStyleClass("background:rgb(211,73,86)");

        PlatDataAgeDataBean bean2 = new PlatDataAgeDataBean();
        bean2.setName("36至50岁:");
        bean2.setRateValue(oegroup.formatDate((float) r3 * 100 / total) + "%");
        bean2.setStyleClass("background:rgb(22,81,122)");

        PlatDataAgeDataBean bean3 = new PlatDataAgeDataBean();
        bean3.setName("50岁以上:");
        bean3.setRateValue(oegroup.formatDate((float) r4 * 100 / total) + "%");
        bean3.setStyleClass("background:rgb(103,195,203)");
        ageDataBeanList.add(bean);
        ageDataBeanList.add(bean1);
        ageDataBeanList.add(bean2);
        ageDataBeanList.add(bean3);

        // 性别

        Map<Integer, Integer> sexMap = oegroup.getInvestorSexMap();
        int maleCount = sexMap.get(OperationGroupReport.MALE);
        int femaleCount = sexMap.get(OperationGroupReport.FEMALE);
        float malePer = (float) maleCount * 100 / (maleCount + femaleCount);
        float femalePer = (float) femaleCount * 100 / (maleCount + femaleCount);

        List<PlatDataAgeDataBean> sexDataBeanList = new ArrayList<PlatDataAgeDataBean>();
        PlatDataAgeDataBean bean4 = new PlatDataAgeDataBean();
        bean4.setName("男:");
        bean4.setRateValue(oegroup.formatDate(malePer) + "%");
        bean4.setStyleClass("background:rgb(28,90,132)");

        PlatDataAgeDataBean bean5 = new PlatDataAgeDataBean();
        bean5.setName("女:");
        bean5.setRateValue(oegroup.formatDate(femalePer) + "%");
        bean5.setStyleClass("background:rgb(211,73,86)");
        sexDataBeanList.add(bean4);
        sexDataBeanList.add(bean5);
        jsonObject.put("sexDataBeanList", sexDataBeanList);

        String sexData = "[ {value:" + maleCount + ", name:'男:　　　" + oegroup.formatDate(malePer)
                + "%　',icon:'circle',}," + "{value:" + femaleCount + ", name:'女:　　　" + oegroup.formatDate(femalePer)
                + "%　',icon:'circle',}]";
        jsonObject.put("sexData", JSONObject.parseArray(sexData, Object.class));
        jsonObject.put("ageData", JSONObject.parseArray(ageData, Object.class));

        // 区域
        OperationGroupReport region = platDataStatisticsService.findOneOperationMongoGroupEntity();

        Map<Integer, String> cityMap = region.getInvestorRegionMap();
        List<SubEntity> list = region.orgnizeData(cityMap);
        List<SubEntity> sublist = region.formatList(list);

        StringBuffer regionData = new StringBuffer();
        regionData.append("[");
        regionData.append("{name: '北京',value: " + getRegionValue(sublist, "北京") + " },");
        regionData.append("{name: '天津',value: " + getRegionValue(sublist, "天津") + " },");
        regionData.append("{name: '上海',value: " + getRegionValue(sublist, "上海") + " },");
        regionData.append("{name: '重庆',value: " + getRegionValue(sublist, "重庆") + " },");
        regionData.append("{name: '河北',value: " + getRegionValue(sublist, "河北") + " },");
        regionData.append("{name: '河南',value: " + getRegionValue(sublist, "河南") + " },");
        regionData.append("{name: '云南',value: " + getRegionValue(sublist, "云南") + " },");
        regionData.append("{name: '辽宁',value: " + getRegionValue(sublist, "辽宁") + " },");
        regionData.append("{name: '黑龙江',value: " + getRegionValue(sublist, "黑龙江") + " },");
        regionData.append("{name: '湖南',value: " + getRegionValue(sublist, "湖南") + " },");
        regionData.append("{name: '安徽',value: " + getRegionValue(sublist, "安徽") + " },");
        regionData.append("{name: '山东',value: " + getRegionValue(sublist, "山东") + " },");
        regionData.append("{name: '新疆',value: " + getRegionValue(sublist, "新疆") + " },");
        regionData.append("{name: '江苏',value: " + getRegionValue(sublist, "江苏") + " },");
        regionData.append("{name: '浙江',value: " + getRegionValue(sublist, "浙江") + " },");
        regionData.append("{name: '江西',value: " + getRegionValue(sublist, "江西") + " },");
        regionData.append("{name: '湖北',value: " + getRegionValue(sublist, "湖北") + " },");
        regionData.append("{name: '广西',value: " + getRegionValue(sublist, "广西") + " },");
        regionData.append("{name: '甘肃',value: " + getRegionValue(sublist, "甘肃") + " },");
        regionData.append("{name: '山西',value: " + getRegionValue(sublist, "山西") + " },");
        regionData.append("{name: '内蒙古',value: " + getRegionValue(sublist, "内蒙古") + " },");
        regionData.append("{name: '陕西',value: " + getRegionValue(sublist, "陕西") + " },");
        regionData.append("{name: '吉林',value: " + getRegionValue(sublist, "吉林") + " },");
        regionData.append("{name: '福建',value: " + getRegionValue(sublist, "福建") + " },");
        regionData.append("{name: '贵州',value: " + getRegionValue(sublist, "贵州") + " },");
        regionData.append("{name: '广东',value: " + getRegionValue(sublist, "广东") + " },");
        regionData.append("{name: '青海',value: " + getRegionValue(sublist, "青海") + " },");
        regionData.append("{name: '西藏', value: " + getRegionValue(sublist, "西藏") + " },");
        regionData.append("{name: '四川',value: " + getRegionValue(sublist, "四川") + " },");
        regionData.append("{name: '宁夏',value: " + getRegionValue(sublist, "宁夏") + " },");
        regionData.append("{name: '海南',value: " + getRegionValue(sublist, "海南") + " },");
        regionData.append("{name: '台湾',value: " + getRegionValue(sublist, "台湾") + " },");
        regionData.append("{name: '香港',value: " + getRegionValue(sublist, "香港") + " },");
        regionData.append("{name: '澳门',value: " + getRegionValue(sublist, "澳门") + " },");
        regionData.append("{name: '南海诸岛',value: " + getRegionValue(sublist, "南海诸岛") + " },");
        regionData.append(" ]");

        // 城市排名
        List<PlatDataAgeDataBean> regionDataList = getTop(sublist);

        // 获取12个月的数据
        List<OperationReport> slist = platDataStatisticsService.findOperationReportEntityList();
        // 每月交易总额
        List<String> monthlyTenderTitle = new ArrayList<String>();
        List<String> monthlyTenderData = new ArrayList<String>();
        // 每月交易笔数
        List<String> monthlyTenderCountData = new ArrayList<String>();
        for (int i = slist.size() - 1; i >= 0; i--) {
            monthlyTenderTitle.add(oe.formatnew(String.valueOf(slist.get(i).getStatisticsMonth())));
            if (slist.get(i).getAccountMonth() != null) {
                monthlyTenderData.add(trim(slist.get(i).getAccountMonth().intValue(), 100000000));
            }
            monthlyTenderCountData.add(trim(slist.get(i).getTradeCountMonth(), 10000));
        }


        jsonObject.put("monthlyTenderTitle", monthlyTenderTitle);
        jsonObject.put("monthlyTenderData", monthlyTenderData);
        jsonObject.put("monthlyTenderCountData", monthlyTenderCountData);
        jsonObject.put("regionData", JSONObject.parseArray(regionData.toString(), Object.class));
        jsonObject.put("top10RegionData", regionDataList);
        jsonObject.put("ageDataBeanList", ageDataBeanList);
        // 满标时间
        float time = oe.getFullBillTimeCurrentMonth();
        jsonObject.put("hour", oe.getHour(time));
        jsonObject.put("min", oe.getMinutes(time));
        jsonObject.put("sec", oe.getSeconds(time));

        jsonObject.put("deadline", transferIntToDate(oe.getStatisticsMonth()));

        //借款人相关数据统计：

        jsonObject.put("borrowuserCountTotal", oe.getBorrowUserCountTotal());
        jsonObject.put("borrowuserCountCurrent", oe.getBorrowUserCountCurrent());
        jsonObject.put("tenderuserCountCurrent", oe.getTenderUserCountCurrent());
        jsonObject.put("borrowuserMoneyTopone", oe.getBorrowUserMoneyTopOne() == null ? 0.00 : oe.getBorrowUserMoneyTopOne().setScale(2, BigDecimal.ROUND_DOWN));
        jsonObject.put("borrowuserMoneyTopten", oe.getBorrowUserMoneyTopTen() == null ? 0.00 : oe.getBorrowUserMoneyTopTen().setScale(2, BigDecimal.ROUND_DOWN));
        result.setData(jsonObject);
        return result;
    }

    public int getRegionValue(List<SubEntity> list, String cityName) {
        for (int i = 0; i < list.size(); i++) {
            SubEntity sub = list.get(i);
            if (sub.getName().contains(cityName)) {
                return sub.getValue();
            }
        }
        return 0;
    }

    public List<PlatDataAgeDataBean> getTop(List<SubEntity> list) {
        List<PlatDataAgeDataBean> regionDataList = new ArrayList<PlatDataAgeDataBean>();
        for (int i = 0; i <= 9; i++) {
            PlatDataAgeDataBean regionData = new PlatDataAgeDataBean();
            int j = i + 1;
            if (j < 10) {
                regionData.setAreaNumber("0" + j);
            } else {
                regionData.setAreaNumber(j + "");
            }
            if (!CollectionUtils.isEmpty(list)) {
                if (list.get(i).getName().contains("新疆")) {
                    regionData.setName("新疆");
                } else {
                    regionData.setName(list.get(i).getName().replace("省", ""));
                }
                regionData.setRateValue(list.get(i).getPercent());
            }
            regionDataList.add(regionData);
        }
        return regionDataList;

    }

    public String trim(float input, int fenzi) {
        return new BigDecimal((float) input / fenzi).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public String transferIntToDate(int date) {
        Calendar cl = Calendar.getInstance();
        String str = String.valueOf(date);
        int year = Integer.valueOf(str.substring(0, 4));
        int month = Integer.valueOf(str.substring(4, 6)) - 1;

        cl.set(Calendar.YEAR, year);
        cl.set(Calendar.MONTH, month);
        int lastDay = cl.getActualMaximum(Calendar.DAY_OF_MONTH);
        cl.set(Calendar.DAY_OF_MONTH, lastDay);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(cl.getTime());
    }
}
