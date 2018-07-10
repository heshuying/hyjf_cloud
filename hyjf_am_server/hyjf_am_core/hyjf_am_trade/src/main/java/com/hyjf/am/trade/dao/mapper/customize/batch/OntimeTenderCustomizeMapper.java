package com.hyjf.am.trade.dao.mapper.customize.batch;

import com.hyjf.am.trade.dao.model.auto.Borrow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiasq
 * @version OntimeTenderCustomizeMapper, v0.1 2018/7/10 14:08
 * 定时发标
 */
public interface OntimeTenderCustomizeMapper {

    /**
     * 查询符合条件的定时投标 数量
     *
     * @param ontime
     * @return
     */
    Integer queryOntimeTenderCount(@Param("ontime") Integer ontime);

    /**
     * 查询符合条件的定时投标 列表
     *
     * @param ontime
     * @return
     */
    List<Borrow> queryOntimeTenderList(@Param("ontime") Integer ontime);

    /**
     * 查询符合条件的定时汇计划投标 列表
     *
     * @param ontime
     * @return
     */
    List<Borrow> queryHjhOntimeTenderList(@Param("ontime") Integer ontime);

    /**
     * 查询所有未复审的标
     *
     * @return
     */
    List<Borrow> queryAllunrecheckTenders();

    /**
     * 查询分期发标的标的拆分总期数
     *
     * @param borrowPreNid
     * @return
     */
    Integer querySplitTenderCount(@Param("borrowPreNid") Integer borrowPreNid);

    /**
     * 查询相应的定时预约开始标的
     * @param onTime
     * @return
     */

    List<Borrow> selectBorrowAppointStart(@Param("ontime") Integer ontime);

    /**
     * 查询相应的定时预约结束标的
     * @param onTime
     * @return
     */

    List<Borrow> selectBorrowAppointEnd(@Param("ontime") Integer ontime);

    /**
     * 查询标的是否达定时发标时间
     * @param onTime
     * @return
     */
    Integer queryOntimeIdByNid(@Param("borrowNid") String borrowNid, @Param("ontime") Integer ontime);
}
