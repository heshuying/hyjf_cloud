package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.HjhAccountBalanceRequest;
import com.hyjf.am.vo.admin.HjhAccountBalanceVO;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/7  11:51
 */
public interface HjhInfoAccountBalanceCustomizeMapper {

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
     * 按月统计查询数据
     *
     * @return
     */
    List<HjhAccountBalanceVO> getHjhAccountBalanceMonthList(HjhAccountBalanceRequest request);

    int getHjhAccountBalanceMonthCountNew(HjhAccountBalanceRequest request);

}
