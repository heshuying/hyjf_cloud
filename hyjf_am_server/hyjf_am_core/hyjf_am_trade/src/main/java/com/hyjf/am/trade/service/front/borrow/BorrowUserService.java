package com.hyjf.am.trade.service.front.borrow;

import com.hyjf.am.trade.dao.model.auto.BorrowUser;
import com.hyjf.am.trade.dao.model.auto.HjhPlan;
import com.hyjf.am.trade.dao.model.bifa.UserIdAccountSumBean;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.trade.bifa.BifaBorrowUserInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 借款人公司信息
 * @Author zhangyk upd by liushouyi
 */
public interface BorrowUserService extends BaseService {

    /**
     * 根据借款编号获取借款人公司信息
     *
     * @param borrowNid
     * @return
     */
    BorrowUser getBorrowUserByNid(String borrowNid);

    /**
     * 北互金获取借款人信息
     * @param borrowNid
     * @param companyOrPersonal
     * @return
     */
    BifaBorrowUserInfoVO getBifaBorrowUserInfo(String borrowNid, String companyOrPersonal);

    /**
     * 散标转让服务费
     * @param creditNid
     * @return
     */
    BigDecimal getBorrowCreditFeeSumByCreditNid(String creditNid);

    /**
     * 智投转让服务费
     * @param creditNid
     * @return
     */
    BigDecimal getHjhCreditFeeSumByCreditNid(String creditNid);

    /**
     * 获取智投数
     * @param planNid
     * @return
     */
    int countHjhPlan(String planNid);

    /**
     * 获取智投列表
     * @return
     */
    List<HjhPlan> selectHjhPlanInfoList();

    /**
     * 已开户且出借>0的用户
     * @param startDate
     * @param endDate
     * @return
     */
    List<UserIdAccountSumBean> getBorrowTenderAccountSum(Integer startDate, Integer endDate);

    /**
     * 借款人信息
     * @param startDate
     * @param endDate
     * @return
     */
    List<BorrowAndInfoVO> selectBorrowUserInfo(Integer startDate, Integer endDate);

    /**
     * 累计借款笔数
     * @param time
     * @return
     */
    int getLoanNum(Date time);

    /**
     * 累计借贷余额
     * @return
     */
    BigDecimal getWillPayMoney();

    /**
     * 累计借贷余额笔数
     * @return
     */
    int getTotalLoanBalanceNum();

    /**
     * 累计借款人
     * @return
     */
    int countBorrowUser();

    /**
     * 累计投资人数
     * @param time
     * @return
     */
    int getTenderCount(Date time);

    /**
     * 当前借款人
     * @return
     */
    int countCurrentBorrowUser();

    /**
     * 当前出借人
     * @return
     */
    int countCurrentTenderUser();

    /**
     * 平台前十大融资人融资待还余额占比
     * @return
     */
    BigDecimal sumBorrowUserMoneyTopTen();

    /**
     * 代还总金额
     * @return
     */
    BigDecimal sumBorrowUserMoney();

    /**
     * 平台单一融资人最大融资待还余额占比
     * @return
     */
    BigDecimal sumBorrowUserMoneyTopOne();
}
