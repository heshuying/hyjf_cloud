package com.hyjf.cs.message.service.wechatWeekly;

import com.hyjf.am.vo.config.EventVO;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.am.vo.market.EventsVO;
import com.hyjf.am.vo.trade.CreditRepayVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.message.bean.ic.WeeklyreportEntity;

import java.util.List;

/**
 * @author lisheng
 * @version WeeklyService, v0.1 2018/7/27 15:04
 */

public interface WeeklyService {
    UserVO getUserById(Integer userId);

    UserInfoVO findUsersInfoById(Integer userId);

    /**
     * 查询是否存在数据
     * @param userId
     * @param beginDate
     * @return
     */
    List<WeeklyreportEntity> getWeeklyReport(int userId, String beginDate);

    /**
     *获取公司纪事
     * @param begin
     * @param end
     * @return
     */
    EventVO getEventsAll(int begin, int end);


    /**
     * 插入数据
     * @param weeklyReport
     */
    void inWeeklyReport(WeeklyreportEntity weeklyReport);

    /**
     * 获取用户的账户信息
     *
     * @param userId
     * @return 用户的身份证号
     */
     AccountVO getAccount(Integer userId);


    /**
     * 查询投资百分比
     * @param percentage
     * @param begin
     * @param end
     * @param userId
     * @return
     */
    EventVO selectPercentage(int percentage,int begin,int end,int userId);


    /**
     * 获取投资金额和预期金额
     * @param userid
     * @param begin
     * @param end
     * @return
     */
    List<BorrowTenderVO> getBorrowTender(int userid, int begin, int end);


    /**
     * 获取投资金额和预期金额
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    List<CreditTenderVO> getCreditTender(int userId, String begin,String end);
    /**
     * 获取汇计划预计
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    List<HjhAccedeVO> getAccede(int userId, int begin, int end);
    /**
     * 获取优惠券 预期金额
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    List<BorrowTenderCpnVO> getBorrowTenderCPN(int userId, int begin, int end);
    /**
     * 获取还款总额
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    List<BorrowRecoverVO> getBorrowRecover(int userId, String begin,String end);
    /**
     * 获取还款总额
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    List<CreditRepayVO> getCreditRepay(int userId, int begin,int end);
    /**
     * 获取债转总额
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    List<CreditRepayVO> getCreditRepayToCredit(int userId, int begin, int end);

    /**
     * 获取公司纪事
     * @param begin
     * @param end
     * @param userId
     * @return
     */
    List<EventVO>  getEvents(int userId, int begin, int end);


    /**
     * 获取活动列表
     * @param day
     * @return
     */
    List<ActivityListVO> getActivity(int day);


    boolean getCoupon(Integer userId);
}
