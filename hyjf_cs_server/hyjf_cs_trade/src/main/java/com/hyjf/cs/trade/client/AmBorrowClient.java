package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.trade.UserHjhInvistDetailCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.borrow.TenderBgVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;

import java.util.List;
import java.util.Map;

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

    /**
     * 投资之前插入tmp表
     * @param request
     */
    boolean updateBeforeChinaPnR(TenderRequest request);

    /**
     * 用户投资散标操作表
     * @param tenderBg
     * @return
     */
    boolean borrowTender(TenderBgVO tenderBg);

    /**
     * 修改状态临时表结果
     * @param logUserId
     * @param logOrderId
     * @param respCode
     * @param retMsg
     * @param productId
     */
    boolean updateTenderResult(String logUserId, String logOrderId, String respCode, String retMsg, String productId);

    /**
     * 获取投资异步结果
     * @param userId
     * @param logOrdId
     * @param borrowNid
     * @return
     */
    String getBorrowTenderResult(Integer userId, String logOrdId, String borrowNid);



    /**
     * 会计划投资详情
     * @param params
     * @return
     */
    public UserHjhInvistDetailCustomizeVO selectUserHjhInvistDetail(Map<String, Object> params);
}
