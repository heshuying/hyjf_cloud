package com.hyjf.am.market.dao.mapper.customize.market;

import com.hyjf.am.market.dao.model.auto.ActivityList;
import com.hyjf.am.market.dao.model.auto.PerformanceReturnDetail;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.vo.admin.NaMiMarketingVO;
import com.hyjf.am.vo.admin.PerformanceReturnDetailVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version NaMiMarketingCustomizeMapper, v0.1 2018/12/26 15:58
 */

public interface NaMiMarketingCustomizeMapper {

    /**
     *查询合伙人
     * @return
     */
    List<User> selectId0List(@Param("activityList") ActivityList activityList);

    /**
     * //根据邀请人账户名，查询邀请人id
     * @param refferName
     * @return
     */
    Integer selectRefferIdByName(String refferName);

    /**
     *查询邀请明细 好友id
     * @param paraMap
     * @return
     */
    List<Integer> selectIdList(Map<String, Object> paraMap);

    /**
     * 查询邀请明细列表
     * @param paraMap
     * @return
     */
    List<NaMiMarketingVO> selectNaMiMarketingList(Map<String, Object> paraMap);

    /**
     * 查询业绩返现详情条数
     * @param paraMap
     * @return
     */
    Integer selectNaMiMarketingPerfanceCount(Map<String, Object> paraMap);

    int selectNaMiMarketingRefferCount(Map<String, Object> paraMap);

    /**
     * 查询业绩返现详情列表
     * @param paraMap
     * @return
     */
    List<NaMiMarketingVO> selectNaMiMarketingPerfanceList(Map<String, Object> paraMap);

    List<NaMiMarketingVO> selectNaMiMarketingRefferList(Map<String, Object> paraMap);
    /**
     * 业绩返现详情 根据推荐人用户名查询
     * @param id
     * @return
     */
    PerformanceReturnDetail selectReturnDetail(@Param("referName") String referName );

    /**
     * 查询邀请人返现统计 条数
     * @param paraMap
     * @return
     */
    int selectNaMiMarketingRefferTotalCount(Map<String, Object> paraMap);

    public List<String> selectMonthList();
    /**
     * 查询邀请人返现统计 列表
     * @param paraMap
     * @return
     */
    List<NaMiMarketingVO> selectNaMiMarketingRefferTotalList(Map<String, Object> paraMap);

    /**
     * 查询邀请人返现统计 合计
     * @param paraMap
     * @return
     */
    BigDecimal selectNaMiMarketingRefferTotalAmount(Map<String, Object> paraMap);
}
