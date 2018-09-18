package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.vo.trade.DataSearchCustomizeVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version QianleDataSearchCustomizeMapper, v0.1 2018/8/22 14:13
 */

public interface QianleDataSearchCustomizeMapper {

    /**
     * 查询所有数据
     * @param hashMap
     * @return
     */
    List<DataSearchCustomizeVO> queryList(HashMap<String, Object> hashMap);

    /**
     *查询所有数据条数
     * @param hashMap
     * @return
     */
    Integer queryCount(HashMap<String, Object> hashMap);

    /**
     * 查询散标
      * @return
     */
    List<DataSearchCustomizeVO> querySanList(HashMap<String, Object> hashMap);

    /**
     * 查詢散标的数量
     * @param hashMap
     * @return
     */
    Integer querySanCount(HashMap<String, Object> hashMap);

    /**
     * 查询计划
     * @param hashMap
     * @return
     */
    List<DataSearchCustomizeVO> queryPlanList(HashMap<String, Object> hashMap);

    /**
     * 查询计划的数据量
     * @param hashMap
     * @return
     */
    Integer queryPlanCount(HashMap<String, Object> hashMap);

    /**
     *查询散标金额
     * @param hashMap
     * @return
     */
    Map<String, Object> querySanMoney(HashMap<String, Object> hashMap);

    /**
     *查询计划金额
     * @param hashMap
     * @return
     */
    Map<String, Object> queryPlanMoney(HashMap<String, Object> hashMap);

    /**
     *查询总金额
     * @param hashMap
     * @return
     */
    Map<String, Object> queryFirstTender(HashMap<String, Object> hashMap);
}
