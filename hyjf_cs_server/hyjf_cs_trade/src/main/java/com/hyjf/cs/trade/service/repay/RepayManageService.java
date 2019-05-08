package com.hyjf.cs.trade.service.repay;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.resquest.trade.RepayRequestDetailRequest;
import com.hyjf.am.resquest.user.WebUserRepayTransferRequest;
import com.hyjf.am.vo.admin.WebUserInvestListCustomizeVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.repay.BankRepayOrgFreezeLogVO;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.am.vo.trade.repay.RepayPlanListVO;
import com.hyjf.am.vo.trade.repay.RepayWaitOrgVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.WebUserRepayTransferCustomizeVO;
import com.hyjf.am.vo.user.WebUserTransferBorrowInfoCustomizeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.repay.ProjectBean;
import com.hyjf.cs.trade.bean.repay.RepayBean;
import com.hyjf.cs.trade.service.BaseTradeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

    BigDecimal getUserLateInterestWaitTotal(Integer userId);

    BigDecimal getUserBorrowAccountTotal(Integer userId);

    /**
     * 担保机构管理费总待还
     * @param userId
     * @return
     */
    BigDecimal getOrgRepayFeeWaitTotal(Integer userId);

    BigDecimal getOrgLateInterestWaitTotal(Integer userId);

    /**
     * 担保机构待还
     * @param userId
     * @return
     */
    RepayWaitOrgVO getOrgRepayWaitTotal(Integer userId);
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

    List<RepayPlanListVO> selectRepayPlanList(String borrowNid);

    /**
     * 垫付机构待还款列表
     *
     * @param requestBean
     * @return
     */
    List<RepayListCustomizeVO> selectOrgRepayList(RepayListRequest requestBean);

    BigDecimal selectOrgRepayWaitTotalCurrent(RepayListRequest requestBean);

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

    void checkForSingleRepayRequest(String borrowNid, String password,  WebViewUserVO user);

    void checkForBankBalance(WebViewUserVO user, RepayBean repayBean);

    RepayBean getRepayBean(Integer userId, String roleId, String borrowNid, boolean isAllRepay, int latePeriod);
    /**
     * 还款申请回调数据更新
     * @auther: hesy
     * @date: 2018/7/10
     */
    Boolean updateForRepayRequest(RepayBean repayBean, BankCallBean bankCallBean, boolean isAllRepay, int latePeriod);
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

    /**
     * 获取失败信息
     * @param retCode
     * @return
     */
    String getBankRetMsg(String retCode);

    /**
     * 插入垫付机构冻结日志信息
     * @author wgx
     * @date 2018/10/11
     */
    Integer insertRepayOrgFreezeLog(Integer userId, String orderId, String account, String borrowNid, RepayBean repay,
                                 String userName, boolean isAllRepay, int latePeriod);
    /**
     * 根据条件查询垫付机构冻结日志
     * @author wgx
     * @date 2018/10/11
     */
    List<BankRepayOrgFreezeLogVO> getBankRepayOrgFreezeLogList(String borrowNid, String orderId);

    /**
     * 删除垫付机构还款冻结日志
     * @author wgx
     * @date 2018/10/11
     */
    Integer deleteOrgFreezeTempLogs(String orderId, String borrowNid);

    /**
     * 单笔还款申请冻结查询
     * @auther: wgx
     * @date: 2018/10/11
     */
    boolean queryBalanceFreeze(Integer userId, String userName, String orderId, String account);

    /**
     * 还款申请冻结资金
     * @auther: wgx
     * @date: 2018/10/16
     */
    WebResult getBalanceFreeze(WebViewUserVO userVO, String borrowNid, RepayBean repayBean, String orderId, String account, WebResult webResult,  boolean isAllRepay, int latePeriod);
    /**
     * 代偿冻结（合规要求）
     * @auther: wgx
     * @date: 2018/10/11
     */
    Map<String, Object> getBankRefinanceFreezePage(Integer userId, String userName, String ip, String orderId, String borrowNid, BigDecimal repayTotal, String account);

    /**
     * 担保机构批量还款
     * @auther: wgx
     * @date: 2018/10/16
     */
    Map<String, Object> startOrgRepay(String startDate, String endDate, Integer userId, String password, String ip, BankOpenAccountVO userBankOpenAccount);

    File createAgreementPDFFileRepay(HttpServletRequest request, HttpServletResponse response, String borrowNid, String nid, String flag, Integer userId);

    List<WebUserInvestListCustomizeVO> selectUserInvestList(String borrowNid);

    /**
     * 获取标的信息
     * @param borrowNid
     * @return
     */
    WebUserTransferBorrowInfoCustomizeVO getUserTransferBorrowInfo(String borrowNid);

    List<TenderAgreementVO> selectTenderAgreementByNid(String borrowNid);

    /**
     * 用户待还债转详情列表
     * @return
     * @Author : huanghui
     */
   WebResult selectUserRepayTransferDetailList(WebUserRepayTransferRequest repayTransferRequest);

   /***
    * 根据订单号获取用户放款信息
   * @author Zha Daojian
   * @date 2019/2/28 14:08
   * @param nid
   * @return com.hyjf.am.vo.trade.borrow.BorrowRecoverVO
   **/
   BorrowRecoverVO selectBorrowRecoverByNid(String nid);

    /**
     * 根据借款编号查询当前标的是否有承接失败的债权
     * @author liubin & wgx
     * @date 2019/03/04
     */
   boolean getFailCredit(String borrowNid);
}
