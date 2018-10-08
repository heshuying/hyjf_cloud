package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.BorrowCustomize;
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
    List<BorrowCustomize> queryOntimeTenderList(@Param("ontime") Integer ontime);

    /**
     * 查询符合条件的定时汇计划投标 列表
     *
     * @param ontime
     * @return
     */
    List<BorrowCustomize> queryHjhOntimeTenderList(@Param("ontime") Integer ontime);

}
