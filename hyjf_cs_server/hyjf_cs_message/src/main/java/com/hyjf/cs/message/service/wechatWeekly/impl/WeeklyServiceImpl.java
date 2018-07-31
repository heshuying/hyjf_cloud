package com.hyjf.cs.message.service.wechatWeekly.impl;

import com.hyjf.am.response.config.EventResponse;
import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.response.trade.*;
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
import com.hyjf.cs.message.client.AmConfigClient;
import com.hyjf.cs.message.client.AmMarketClient;
import com.hyjf.cs.message.client.AmTradeClient;
import com.hyjf.cs.message.client.AmUserClient;
import com.hyjf.cs.message.mongo.ic.WeeklyreportMongoDao;
import com.hyjf.cs.message.service.wechatWeekly.WeeklyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lisheng
 * @version WeeklyServiceImpl, v0.1 2018/7/27 15:05
 */
@Service
public class WeeklyServiceImpl implements WeeklyService {
    @Autowired
    AmUserClient amUserClient;

    @Autowired
    WeeklyreportMongoDao weeklyreportMongoDao;

    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    AmMarketClient amMarketClient;

    @Autowired
    AmConfigClient amConfigClient;
    @Override
    public UserVO getUserById(Integer userId) {
        return amUserClient.findUserById(userId);
    }

    @Override
    public UserInfoVO findUsersInfoById(Integer userId) {
        return amUserClient.findUsersInfoById(userId);
    }

    /**
     * 周报数据
     * @param userId
     * @param beginDate
     * @return
     */
    @Override
    public List<WeeklyreportEntity> getWeeklyReport(int userId, String beginDate) {
        Query query = new Query();
        Criteria criteria = Criteria.where("userId").is(userId);
        criteria.and("beginDate").is(beginDate);
        query.addCriteria(criteria);
        List<WeeklyreportEntity> list = weeklyreportMongoDao.find(query);
        return list;
    }

    /**
     * 插入数据
     * @param weeklyReport
     */
    @Override
    public void inWeeklyReport(WeeklyreportEntity weeklyReport) {
        weeklyreportMongoDao.insert(weeklyReport);
    }

    /**
     * 根据id获取用户的账号信息
     * @param userId
     * @return
     */
    @Override
    public AccountVO getAccount(Integer userId) {
        return amTradeClient.getAccountByUserId(userId);
    }

    /**
     * 查询投资百分比
     * @param percentage
     * @param begin
     * @param end
     * @param userId
     * @return
     */
    @Override
    public EventsVO selectPercentage(int percentage, int begin, int end, int userId) {
        return amConfigClient.selectPercentage(percentage,  begin,  end,  userId);
    }

    /**
     *获取投资金额和预期金额
     * @param userid
     * @param begin
     * @param end
     * @return
     */
    @Override
    public List<BorrowTenderVO> getBorrowTender(int userid, int begin, int end) {
        if(amTradeClient.getBorrowTender(userid,  begin,  end)!=null){
            return amTradeClient.getBorrowTender(userid,  begin,  end).getResultList();
        }
        return null;
    }

    @Override
    public List<CreditTenderVO> getCreditTender(int userId, String begin, String end) {
        CreditTenderResponse creditTender = amTradeClient.getCreditTender(userId, Integer.valueOf(begin), Integer.valueOf(end));
        if(creditTender!=null){
            return creditTender.getResultList();
        }
        return null;
    }

    @Override
    public List<HjhAccedeVO> getAccede(int userId, int begin, int end) {
        HjhAccedeResponse accede = amTradeClient.getAccede(userId, begin, end);
        if (accede != null) {
            return accede.getResultList();
        }
        return null;
    }

    @Override
    public List<BorrowTenderCpnVO> getBorrowTenderCPN(int userId, int begin, int end) {
        BorrowTenderCpnResponse borrowTenderCPN = amTradeClient.getBorrowTenderCPN(userId, begin, end);
        if (borrowTenderCPN != null) {
            return borrowTenderCPN.getResultList();
        }
        return null;
    }

    @Override
    public List<BorrowRecoverVO> getBorrowRecover(int userId, String begin,String end) {
        BorrowRecoverResponse borrowRecover = amTradeClient.getBorrowRecover(userId, Integer.valueOf(begin), Integer.valueOf(end));
        if (borrowRecover != null) {
            return borrowRecover.getResultList();
        }
        return null;
    }

    @Override
    public List<CreditRepayVO> getCreditRepay(int userId, int begin,int end) {
        CreditRepayResponse creditRepay = amTradeClient.getCreditRepay(userId, begin, end);
        if (creditRepay != null) {
            return creditRepay.getResultList();
        }
        return null;
    }

    @Override
    public List<CreditRepayVO> getCreditRepayToCredit(int userId, int begin, int end) {
        CreditRepayResponse creditRepayToCredit = amTradeClient.getCreditRepayToCredit(userId, begin, end);
        if (creditRepayToCredit != null) {
            return creditRepayToCredit.getResultList();
        }
        return null;
    }

    @Override
    public List<EventVO>  getEvents(int userId, int begin, int end) {
        EventResponse events = amConfigClient.getEvents(userId, begin, end);
        if (events != null) {
            return events.getResultList();
        }
        return null;
    }

    @Override
    public List<ActivityListVO> getActivity(int day) {
        ActivityListResponse activity = amMarketClient.getActivity(day);
        if (activity != null) {
            return activity.getResultList();
        }
        return null;
    }
    @Override
    public boolean getCoupon(Integer userId) {
        return amTradeClient.getCoupon(userId);
    }

    /**
     * 获取公司纪事
     * @param begin
     * @param end
     * @return
     */
    @Override
    public EventsVO getEventsAll(int begin, int end) {
        return  amConfigClient.getEventsAll(begin,end);
    }




}
