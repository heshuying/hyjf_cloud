package com.hyjf.cs.market.client;

import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.admin.SellDailyDistributionVO;
import com.hyjf.am.vo.market.ActivityListBeanVO;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.market.SellDailyVO;

import java.util.List;
import java.util.Map;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/26 11:18
 * @Description: AmMarketClient
 */
public interface AmMarketClient {
    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  根据条件查询活动列表总数
     * @Date 11:04 2018/7/26
     * @Param activityListRequest
     * @return
     */
    Integer queryActivityCount(ActivityListRequest activityListRequest);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  根据条件分页查询数据
     * @Date 11:05 2018/7/26
     * @Param activityListRequest
     * @return List
     */
    List<ActivityListBeanVO> queryActivityList(ActivityListRequest activityListRequest);

    /**
     * 获取后台发送邮件配置
     * @return
     */
    List<SellDailyDistributionVO> selectSellDailyDistribution();

    /**
     * 根据统计时间查询销售日报数据
     * @param dateStr
     * @return
     */
    List<SellDailyVO> selectSellDailyByDateStr(String dateStr);

    /**
     * 根据类型查询合计 type：1
     * @param formatDateStr
     * @return
     */
    SellDailyVO selectOCSum(String formatDateStr);

    /**
     * 根据类型查询合计 type:2
     * @param formatDateStr
     * @param drawOrder
     * @return
     */
    SellDailyVO selectPrimaryDivisionSum(String formatDateStr, int drawOrder);

    /**
     * 根据类型查询合计 type:3
     * @param formatDateStr
     * @return
     */
    SellDailyVO selectAllSum(String formatDateStr);

    /**
     * 当前日期是否已经生成销售日报
     * @return
     */
    boolean hasGeneratorDataToday();

    /**
     * 批量插入
     * @param list
     */
    void batchInsertSellDaily(List<SellDailyVO> list);

    /**
     * 计算第四、六、十列速率,第十六列净资金流
     */
    void calculateRate();

    /**
     * 进行中的活动列表状态变更定时
     */
    void callActivityEnd();

    /**
     * 查询首页banner
     * @param ads
     * @return
     */
    List<AppAdsCustomizeVO> searchBannerList(Map<String, Object> ads);

    /**
     * 保存用户竞猜信息
     * @param userId
     * @param grade
     * @return
     */
    boolean insertActivityUserGuess(int userId, int grade);

    /**
     * 判断是否重复竞猜
     * @param userId
     * @return
     */
    boolean existsActivityUserGuess(int userId);

    /**
     * 保存用户领取记录
     * @param userId
     * @param rewardName 奖励名称
     * @param rewardType 奖励类型
     * @return
     */
    boolean insertActivityUserReward(int userId, String rewardName, String rewardType);
}
