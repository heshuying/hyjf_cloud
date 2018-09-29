package com.hyjf.cs.trade.service.repay;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.resquest.trade.RepayRequestDetailRequest;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.trade.bean.repay.ProjectBean;
import com.hyjf.cs.trade.bean.repay.RepayBean;
import com.hyjf.cs.trade.service.BaseTradeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hesy
 * @version RepayManageService, v0.1 2018/6/23 18:02
 */
public interface RepayManageService extends BaseTradeService {
    /**
     * 普通用户管理费总待还
     * @param userId
     * @return
     */
    BigDecimal getUserRepayFeeWaitTotal(Integer userId);
    /**
     * 担保机构管理费总待还
     * @param userId
     * @return
     */
    BigDecimal getOrgRepayFeeWaitTotal(Integer userId);
    /**
     * 担保机构待还
     * @param userId
     * @return
     */
    BigDecimal getOrgRepayWaitTotal(Integer userId);
    /**
     * 请求参数校验
     *
     * @param requestBean
     */
    void checkForRepayList(RepayListRequest requestBean);
    /**
     * 用户已还款/待还款列表
     *
     * @param requestBean
     * @return
     */
    List<RepayListCustomizeVO> selectRepayList(RepayListRequest requestBean);
    /**
     * 垫付机构待还款列表
     *
     * @param requestBean
     * @return
     */
    List<RepayListCustomizeVO> selectOrgRepayList(RepayListRequest requestBean);
    /**
     * 垫付机构已还款列表
     *
     * @param requestBean
     * @return
     */
    List<RepayListCustomizeVO> selectOrgRepayedList(RepayListRequest requestBean);
    /**
     * 用户待还款/已还款总记录数
     *
     * @param requestBean
     * @return
     */
    Integer selectRepayCount(RepayListRequest requestBean);
    /**
     * 垫付机构待还款总记录数
     *
     * @param requestBean
     * @return
     */
    Integer selectOrgRepayCount(RepayListRequest requestBean);
    /**
     * 垫付机构已还款总记录数
     *
     * @param requestBean
     * @return
     */
    Integer selectOrgRepayedCount(RepayListRequest requestBean);

    boolean checkPassword(Integer userId, String password);

    JSONObject getRepayDetailData(RepayRequestDetailRequest requestBean);

    void checkForRepayRequest(String borrowNid, String password,  WebViewUserVO user, RepayBean repayBean);

    void checkForRepayRequestOrg(String borrowNid, String password,  WebViewUserVO user, RepayBean repayBean, int flag);

    RepayBean getRepayBean(Integer userId, String roleId, String borrowNid, boolean isAllRepay);
    /**
     * 还款申请回调数据更新
     * @auther: hesy
     * @date: 2018/7/10
     */
    Boolean updateForRepayRequest(RepayBean repayBean, BankCallBean bankCallBean);
    /**
     * 如果有正在出让的债权,先去把出让状态停止
     * @param borrowNid
     * @return
     */
    Boolean updateBorrowCreditStautus(String borrowNid);
    /**
     * 校验重复还款
     * @param userId
     * @param borrowNid
     * @return
     */
    boolean checkRepayInfo(Integer userId, String borrowNid);
    /**
     * 添加冻结日志
     * @auther: hesy
     * @date: 2018/7/10
     */
    Integer addFreezeLog(Integer userId, String orderId, String account, String borrowNid, BigDecimal repayTotal,
                         String userName);
    /**
     * 删除冻结日志
     * @auther: hesy
     * @date: 2018/7/10
     */
    Integer deleteFreezeLogByOrderId(String orderId);
    /**
     * 更新借款API任务表
     * @return
     */
    Boolean updateBorrowApicron(BorrowApicronVO apicronVO, Integer status);
    /**
     * 根据bankSeqNo检索
     * @param bankSeqNO
     * @return
     */
    BorrowApicronVO selectBorrowApicron(String bankSeqNO);
    /**
     * 批次状态查询
     * @param apicron
     * @return
     */
    BankCallBean batchQuery(BorrowApicronVO apicron);
    /**
     * 获取批量还款页面数据
     */
    ProjectBean getOrgBatchRepayData(String userId, String startDate, String endDate);
}
