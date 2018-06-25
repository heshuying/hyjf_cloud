package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
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
     * @Description 插入计划交易明细表  加入计划成功后 操作计划表和明细表
     * @Author sunss
     * @Date 2018/6/22 10:34
     */
    boolean insertHJHPlanAccede(HjhAccedeVO planAccede);

    /**
     * 检索正在还款中的标的
     * @return
     */
    List<BorrowVO> selectBorrowList();

    /**
     * 获取borrow对象
     * @param borrowId
     * @return
     */
    BorrowVO getBorrowByNid(String borrowId);

    /**
     * 获取userInfo对象
     * @param borrowNid
     * @return
     */
	public BorrowInfoVO getBorrowInfoByNid(String borrowNid);
}
