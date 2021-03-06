package com.hyjf.cs.market.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.market.SellDailyVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.market.client.AmConfigClient;
import com.hyjf.cs.market.client.AmMarketClient;
import com.hyjf.cs.market.client.AmUserClient;
import com.hyjf.cs.market.mq.base.CommonProducer;
import com.hyjf.cs.market.mq.base.MessageContent;
import com.hyjf.cs.market.service.BaseMarketServiceImpl;
import com.hyjf.cs.market.service.DailyAutoSendService;
import com.hyjf.cs.market.service.DailyGeneratorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author fuqiang
 * @version DailyGeneratorDataServiceImpl, v0.1 2018/11/19 15:48
 */
@Service
public class DailyGeneratorDataServiceImpl extends BaseMarketServiceImpl implements DailyGeneratorDataService {
    @Autowired
    private AmConfigClient amConfigClient;
    @Autowired
    private AmMarketClient amMarketClient;
    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    private CommonProducer commonProducer;
    @Autowired
    private DailyAutoSendService dailyAutoSendService;

    private StoreNumInner storeNumInner = new StoreNumInner();

    private static final String NMZX_DIVISION_NAME = "纳觅咨询";
    private static final String QGR_DIVISION_NAME = "裕峰瑞";
    private static final String DTHJ_DIVISION_NAME = "大唐汇金";
    private static final String SHRJ_DIVISION_NAME = "上海嵘具";
    private static final String YYZX_DIVISION_NAME = "运营中心";
    private static final String HZSW_DIVISION_NAME = "惠众";
    private static final String QIANLE_DIVISION_NAME = "渠道";

    /** 第一部分： 纳觅咨询  */
    private static final int DRAW_ORDER_LEVEL1 = 1;
    /** 第二部分： 运营中心， 渠道（千乐）， 惠众 */
    private static final int DRAW_ORDER_LEVEL2 = 2;
    /** 第三部分： 裕峰瑞 */
    private static final int DRAW_ORDER_LEVEL3 = 3;
    /** 第四部分： 大唐汇金 */
    private static final int DRAW_ORDER_LEVEL4 = 4;

    /** 不需要显示的二级部门 */
    private static final List NMZX_IGNORE_TWO_DIVISION_LIST = Arrays.asList("胶州分部");
    private static final List DTHJ_IGNORE_TWO_DIVISION_LIST = Arrays.asList("樟树分部", "东莞分部", "西安分部");


    @Override
    public void generatorSellDaily() {
        Date currentDate = new Date();
        String currentDateStr = GetDate.formatDate(currentDate);
        // 休息日、节假日不执行
        if (!amConfigClient.isWorkdateOnSomeDay()) {
            logger.info("节假日不发送邮件...currentDate is :{}", currentDateStr);
            return;
        }
        // 判断当天已有数据，不执行
        if (amMarketClient.hasGeneratorDataToday()) {
            logger.info("数据已经生成，如想重复生成请删除数据...currentDate is :{}", currentDateStr);
            return;
        }

        logger.info("销售日报生成开始.......");
        long startTime = System.currentTimeMillis();
        // 初始化部门
        initDepartment();
        long startTime2 = System.currentTimeMillis();
        logger.info("初始化部门结束，耗时: {}ms", startTime2 - startTime);
        generatorSellDaily(currentDate);
        logger.info("销售日报生成结束，耗时: {}ms", System.currentTimeMillis() - startTime2);

    }

    private void initDepartment() {
        List<SellDailyVO> list = new ArrayList<>();
        List<String> twoDivisionList1 = amUserClient.selectTwoDivisionByPrimaryDivision(NMZX_DIVISION_NAME);
        for (String twoDivision : twoDivisionList1) {
            if (NMZX_IGNORE_TWO_DIVISION_LIST.contains(twoDivision)) {
                continue;
            }
            list.add(new SellDailyVO(NMZX_DIVISION_NAME, twoDivision, DRAW_ORDER_LEVEL1,
                    storeNumInner.getStoreNum(NMZX_DIVISION_NAME, twoDivision)));
        }

        list.add(new SellDailyVO(YYZX_DIVISION_NAME, "网络运营部", DRAW_ORDER_LEVEL2, 0));
        list.add(new SellDailyVO(YYZX_DIVISION_NAME, "无主单", DRAW_ORDER_LEVEL2, 0));
        list.add(new SellDailyVO(QIANLE_DIVISION_NAME, "千乐", DRAW_ORDER_LEVEL2, 0));
        //list.add(new SellDailyVO("其中：", "APP推广", DRAW_ORDER_LEVEL2, 0));
        list.add(new SellDailyVO(HZSW_DIVISION_NAME, "其它", DRAW_ORDER_LEVEL2, 0));


        List<String> twoDivisionList3 = amUserClient.selectTwoDivisionByPrimaryDivision(QGR_DIVISION_NAME);
        for (String twoDivision : twoDivisionList3) {
            list.add(new SellDailyVO(QGR_DIVISION_NAME, twoDivision, DRAW_ORDER_LEVEL3,
                    storeNumInner.getStoreNum(QGR_DIVISION_NAME, twoDivision)));
        }

        List<String> twoDivisionList4 = amUserClient.selectTwoDivisionByPrimaryDivision(DTHJ_DIVISION_NAME);
        List<String> twoDivisionList5 = amUserClient.selectTwoDivisionByPrimaryDivision(SHRJ_DIVISION_NAME);
        for (String twoDivision : twoDivisionList4) {
            if (DTHJ_IGNORE_TWO_DIVISION_LIST.contains(twoDivision)) {
                continue;
            }
            list.add(new SellDailyVO(DTHJ_DIVISION_NAME, twoDivision, DRAW_ORDER_LEVEL4,
                    storeNumInner.getStoreNum(DTHJ_DIVISION_NAME, twoDivision)));
        }
        for (String twoDivision : twoDivisionList5) {
            list.add(new SellDailyVO(SHRJ_DIVISION_NAME, twoDivision, DRAW_ORDER_LEVEL4,
                    storeNumInner.getStoreNum(SHRJ_DIVISION_NAME, twoDivision)));
        }

        amMarketClient.batchInsertSellDaily(list);

    }

    /**
     * 生成销售日报数据
     * @param currentDate
     */
    private void generatorSellDaily(Date currentDate) {
        // 昨日结束日期（昨日统计数据的结束日期）
        Date yesterdayEndTime;
        // 本月累计列计算的开始时间
        Date totalMonthStartTime;
        // 本月累计列计算的结束时间
        Date totalMonthEndTime;
        // 上月累计结束日期
        Date totalPreviousMonthEndTime;
        // 上月累计开始时间
        Date totalPreviousMonthStartTime;
        // 昨日开始日期 节假日向前退推移
        Date yesterdayStartTime;
        // 第十七列: 查询当日待还（工作日计算当天， 如果工作日次日是节假日，那么计算当天到节假日过后第一个工作日）
        Date todayEndTime;

        // 当月第一个工作日，查询上个月数据
        if (dailyAutoSendService.isTodayFirstWorkdayOnMonth()) {
            logger.info("当月第一个工作日，查询上个月数据...");
            yesterdayEndTime = GetDate.getYesterdayEndTime(currentDate);
            totalMonthStartTime = GetDate.getFirstDayOnMonth(GetDate.getYesterdayEndTime(getFirstDayOnMonth(currentDate)));
            totalMonthEndTime = GetDate.getLastDayOnMonth(totalMonthStartTime);
            totalPreviousMonthEndTime = getPreviousMonth(totalMonthEndTime);
            totalPreviousMonthStartTime = GetDate.getFirstDayOnMonth(totalPreviousMonthEndTime);
            yesterdayStartTime = this.getYesterdayStartTime(currentDate);
            todayEndTime = getNextWorkDate(currentDate);

        } else {
            yesterdayEndTime = GetDate.getYesterdayEndTime(currentDate);
            totalMonthStartTime = GetDate.getFirstDayOnMonth(yesterdayEndTime);
            totalMonthEndTime = yesterdayEndTime;
            totalPreviousMonthEndTime = getPreviousMonth(yesterdayEndTime);
            totalPreviousMonthStartTime = GetDate.getFirstDayOnMonth(totalPreviousMonthEndTime);
            yesterdayStartTime = this.getYesterdayStartTime(currentDate);
            todayEndTime = getNextWorkDate(currentDate);
        }


        // 第一列: 查询本月累计规模业绩
        this.insertSomeColumn(totalMonthStartTime, totalMonthEndTime, 1);

        // 第三列: 查询上月累计规模业绩

        this.insertSomeColumn(totalPreviousMonthStartTime, totalPreviousMonthEndTime, 3);

        // 第二列: 查询本月累计已还款
        this.insertSomeColumn(totalMonthStartTime, totalMonthEndTime, 2);

        // 第五列: 查询本月累计提现
        this.insertSomeColumn(totalMonthStartTime, totalMonthEndTime, 5);

        // 第七列: 查询本月累计充值
        this.insertSomeColumn(totalMonthStartTime, totalMonthEndTime, 7);

        // 第八列: 计算本月累计年化业绩
        this.insertSomeColumn(totalMonthStartTime, totalMonthEndTime, 8);
        // 第九列: 计算上月累计年化业绩
        this.insertSomeColumn(totalPreviousMonthStartTime, totalPreviousMonthEndTime, 9);

        // 第十一列: 查询昨日规模业绩
        this.insertSomeColumn(yesterdayStartTime, yesterdayEndTime, 11);
        // 第十二列: 查询昨日还款
        this.insertSomeColumn(yesterdayStartTime, yesterdayEndTime, 12);
        // 第十三列: 查询昨日年化业绩
        this.insertSomeColumn(yesterdayStartTime, yesterdayEndTime, 13);
        // 第十四列: 查询昨日提现
        this.insertSomeColumn(yesterdayStartTime, yesterdayEndTime, 14);
        // 第十五列: 查询昨日充值
        this.insertSomeColumn(yesterdayStartTime, yesterdayEndTime, 15);

        // 统一计算第四、六、十列速率,第十六列净资金流
        // 第四列: 计算环比增速
        // 第六列: 计算提现占比
        // 第十列: 计算环比增速
        // 第十六列: 计算昨日净资金流（充值-提现） 不能保证更新时间， 调整到在发送邮件前更新
        //amMarketClient.calculateRate();

        // 第十七列: 查询当日待还（工作日计算当天， 如果工作日次日是节假日，那么计算当天到节假日过后第一个工作日）
        this.insertSomeColumn(GetDate.getSomeDayStart(currentDate), GetDate.getSomeDayEnd(todayEndTime), 17);

        // 第十八列: 查询昨日注册数
        this.insertSomeColumn(yesterdayStartTime, yesterdayEndTime, 18);

        // 第十九列: 查询其中充值≥3000人数
        this.insertSomeColumn(yesterdayStartTime, yesterdayEndTime, 19);
        // 第二十列: 查询出借≥3000人数
        this.insertSomeColumn(yesterdayStartTime, yesterdayEndTime, 20);
        // 第二十一列: 查询本月累计出借3000以上新客户数
        this.insertSomeColumn(totalMonthStartTime, yesterdayEndTime, 21);
    }


    /**
     * 插入本月累计规模、上月累计规模
     *
     * @param startTime
     * @param endTime
     * @param column
     *            1-本月累计规模 2-本月累计已还款 3-上月累计规模 5-本月累计提现 7-本月累计充值 8-本月累计年化业绩
     *            9-上月累计年化业绩 11-昨日规模业绩 12-昨日还款 13-昨日年化业绩 14-昨日提现 15-昨日充值 17-当日待还
     */
    private void insertSomeColumn(Date startTime, Date endTime, int column) {

        logger.info("startTime :" + GetDate.formatTime(startTime) + ", endTime is :" + GetDate.formatTime(endTime)
                + ", column:" + column);
        JSONObject params = new JSONObject();
        params.put("column", column);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
		try {
			commonProducer.messageSend(new MessageContent(MQConstant.SELL_DAILY_TOPIC,
					MQConstant.SELL_DAILY_SELECT_TAG, UUID.randomUUID().toString(), params));
		} catch (MQException e) {
			logger.error("销售日报发送消息失败......", e);
		}
    }

    /**
     * 部门数量写死，使用内部类，第一，类的职责划分，第二，未来可以使用其他方法替换，比如配置文件
     */
    private static final class StoreNumInner{
        /**
         * 初始化部门 日期 一级部门 二级部门 绘制顺序 门店数量
         */
        private static final List<String> NM_NUM_0 = Arrays.asList("威海中信分部", "东营一分部", "青岛开发区分部", "济南分部", "青岛市南分部", "天津分部",
                "乳山分部", "郑州一分部", "荣成分部", "上海一分部", "青岛清江路社区", "上海运营中心", "邹平分部", "吉林分部", "漯河分部", "上海二分部", "成都分公司", "深圳分部",
                "郑州二分部", "青岛红岛社区", "阜阳分部", "成都分部", "青岛崂山社区");
        private static final List<String> NM_NUM_1 = Arrays.asList("莱州分部", "即墨二分部", "常州分部", "威海经区分部", "北京分部", "烟台开发区分部",
                "黄岛分部", "招远分部", "青岛市北分部", "即墨一分部");
        private static final List<String> NM_NUM_2 = Arrays.asList("文登分部", "烟台一分部", "淄博分部", "青岛城阳分部", "上海三分部");
        private static final List<String> NM_NUM_3 = Arrays.asList("牡丹江分部", "潍坊分部");
        private static final List<String> QRF_NUM_0 = Arrays.asList("苏州分部", "合肥分部", "威海文登分部", "威海中信分部", "青岛红岛社区", "石家庄分部",
                "通辽分部");
        private static final List<String> QRF_NUM_1 = Arrays.asList("青岛开发区分部", "即墨二分部");
        private static final List<String> QRF_NUM_2 = Arrays.asList("青岛市南分部");

        private Integer getStoreNum(String primaryDivision, String twoDivision) {
            Integer num = 0;
            if (NMZX_DIVISION_NAME.equals(primaryDivision)) {
                if (NM_NUM_0.contains(twoDivision)) {
                    num = 0;
                } else if (NM_NUM_1.contains(twoDivision)) {
                    num = 1;
                } else if (NM_NUM_2.contains(twoDivision)) {
                    num = 2;
                } else if (NM_NUM_3.contains(twoDivision)) {
                    num = 3;
                }
            }
            if (QGR_DIVISION_NAME.equals(primaryDivision)) {
                if (QRF_NUM_0.contains(twoDivision)) {
                    num = 0;
                } else if (QRF_NUM_1.contains(twoDivision)) {
                    num = 1;
                } else if (QRF_NUM_2.contains(twoDivision)) {
                    num = 2;
                }
            }
            if (DTHJ_DIVISION_NAME.equals(primaryDivision) || SHRJ_DIVISION_NAME.equals(primaryDivision)) {
                num = 0;
            }
            return num;
        }
    }


    /**
     * 获取指定时间上月
     *
     * @param date
     * @return
     */
    private Date getPreviousMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 昨日开始时间 昨日节假日往前推直到第一个工作日
     *
     * @param currentDate
     * @return
     */
    private Date getYesterdayStartTime(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = calendar.getTime();

        // 昨天非工作日， 前推到第一个工作日
        if (!amConfigClient.isWorkdateOnSomeDay(yesterday)) {
            yesterday = amConfigClient.getFirstWorkdateBeforeSomeDate(yesterday);
        }

        return GetDate.getSomeDayStart(yesterday);
    }

    /**
     * 获取下一个工作日前节假日结束时间
     * @param currentDate
     * @return
     */
    private Date getNextWorkDate(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date torrowDate = calendar.getTime();

        // 明天非工作日， 后推到第一个工作日
        if (!amConfigClient.isWorkdateOnSomeDay(torrowDate)) {
            torrowDate = amConfigClient.getFirstWorkdateAfterSomeDate(torrowDate);
            // 取昨日
            calendar.setTime(torrowDate);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            currentDate = calendar.getTime();
        }
        logger.info("统计昨日待还金额结束时间：" + currentDate);
        return currentDate;
    }

    /**
     * 获取某日期的月第一天
     *
     * @param currentDate
     * @return
     */
    private Date getFirstDayOnMonth(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }
}
