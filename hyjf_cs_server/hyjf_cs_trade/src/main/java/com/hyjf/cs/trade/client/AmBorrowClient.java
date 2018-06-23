package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;

import java.util.List;

/**
 * @Description 标的操作
 * @Author sss
 * @Version v0.1
 * @Date 2018/6/19 13:50
 */
public interface AmBorrowClient {


    /**
     * @Description 根据计划编号查询计划
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 13:53
     */
    public HjhPlanVO getPlanByNid(String borrowNid) ;

    /**
     * 检索正在还款中的标的
     * @return
     */
    List<BorrowVO> selectBorrowList();
}
