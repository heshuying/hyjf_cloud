package com.hyjf.am.trade.dao.mapper.customize.admin;

import com.hyjf.am.resquest.admin.HjhAccountBalanceRequest;
import com.hyjf.am.vo.admin.HjhAccountBalanceVO;

import java.util.Date;
import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/7  11:51
 */
public interface HjhAccountBalanceCustomizeMapper {

    /**
     * 按日查询总计
     *
     * @return
     */
    HjhAccountBalanceVO getHjhAccountBalanceSum(HjhAccountBalanceRequest request);

    /**
     * 按月查询总计
     *
     * @return
     */
    HjhAccountBalanceVO getHjhAccountBalanceMonthSum(HjhAccountBalanceRequest request);

    /**
     * 按日统计查询数量
     *
     * @param entity
     * @return
     */
    Integer getHjhAccountBalancecount(HjhAccountBalanceRequest entity);

    /**
     * 按月统计查询数量
     *
     * @param entity
     * @return
     */
    Integer getHjhAccountBalanceMonthCount(HjhAccountBalanceRequest entity);


    List<HjhAccountBalanceVO> getHjhAccountBalanceList(HjhAccountBalanceRequest entity);

    /**
     * 获取该日期的实际债转和复投金额
     *
     * @param date
     * @return
     */
    List<HjhAccountBalanceVO> selectHjhAccountBalanceForActList(Date date);

    /**
     * 按月统计查询数据
     *
     * @return
     */
    List<HjhAccountBalanceVO> getHjhAccountBalanceMonthList(HjhAccountBalanceRequest request);

    int getHjhAccountBalanceMonthCountNew(HjhAccountBalanceRequest request);

    List<HjhAccountBalanceVO> getHjhAccountBalanceMonthListNew(
            HjhAccountBalanceRequest request);
}
