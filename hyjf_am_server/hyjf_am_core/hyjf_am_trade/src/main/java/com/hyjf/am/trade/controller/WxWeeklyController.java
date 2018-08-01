package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.*;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.WeeklyService;
import com.hyjf.am.vo.trade.CreditRepayVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lisheng
 * @version WxWeeklyController, v0.1 2018/7/27 17:42
 */
@RestController
@RequestMapping("am-trade/wxWeekly")
public class WxWeeklyController {
    @Autowired
    WeeklyService weeklyService;


    /**
     * 获取投资金额和预期金额
     * @param begin
     * @param end
     * @param userId
     * @return
     */
    @GetMapping("/getBorrowTender/{userid}/{begin}/{end}")
    public BorrowTenderResponse getBorrowTender(@PathVariable int userId, @PathVariable int begin, @PathVariable int end){
        BorrowTenderResponse borrowTenderResponse = new BorrowTenderResponse();
        List<BorrowTender> borrowTender = weeklyService.getBorrowTender(userId, begin, end);
        List<BorrowTenderVO> borrowTenderVOS = CommonUtils.convertBeanList(borrowTender, BorrowTenderVO.class);
        borrowTenderResponse.setResultList(borrowTenderVOS);
        return borrowTenderResponse;
    }


    /**
     * 获取投资金额和预期金额
     * @param userid
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/getCreditTender/{userid}/{begin}/{end}")
    public CreditTenderResponse getCreditTender(@PathVariable int userid,@PathVariable int begin,@PathVariable int end){
        CreditTenderResponse creditTenderResponse = new CreditTenderResponse();
        List<CreditTender> creditTender = weeklyService.getCreditTender(userid, begin + "", end + "");
        List<CreditTenderVO> creditTenderVOS = CommonUtils.convertBeanList(creditTender, CreditTenderVO.class);
        creditTenderResponse.setResultList(creditTenderVOS);
        return creditTenderResponse;
    }
    /**
     * 获取汇计划预计
     * @param userid
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/getAccede/{userid}/{begin}/{end}")
    public HjhAccedeResponse getAccede(@PathVariable int userid, @PathVariable int begin, @PathVariable int end){
        HjhAccedeResponse hjhAccedeResponse = new HjhAccedeResponse();
        List<HjhAccede> accede = weeklyService.getAccede(userid, begin, end);
        List<HjhAccedeVO> hjhAccedeVOS = CommonUtils.convertBeanList(accede, HjhAccedeVO.class);
        hjhAccedeResponse.setResultList(hjhAccedeVOS);
        return hjhAccedeResponse;
    }
    /**
     * 获取优惠券 预期金额
     * @param userid
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/getBorrowTenderCPN/{userid}/{begin}/{end}")
    public BorrowTenderCpnResponse getBorrowTenderCPN(@PathVariable int userid, @PathVariable int begin, @PathVariable int end){
        BorrowTenderCpnResponse borrowTenderCpnResponse = new BorrowTenderCpnResponse();
        List<BorrowTenderCpn> borrowTenderCPN = weeklyService.getBorrowTenderCPN(userid, begin, end);
        List<BorrowTenderCpnVO> borrowTenderCpnVOS = CommonUtils.convertBeanList(borrowTenderCPN, BorrowTenderCpnVO.class);
        borrowTenderCpnResponse.setResultList(borrowTenderCpnVOS);
        return borrowTenderCpnResponse;
    }
    /**
     * 获取还款总额
     * @param userid
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/getBorrowRecover/{userid}/{begin}/{end}")
    public BorrowRecoverResponse getBorrowRecover(@PathVariable int userid,@PathVariable int begin,@PathVariable int end){
        BorrowRecoverResponse borrowRecoverResponse = new BorrowRecoverResponse();
        List<BorrowRecover> borrowRecover = weeklyService.getBorrowRecover(userid, begin + "", end + "");
        List<BorrowRecoverVO> borrowRecoverVOS = CommonUtils.convertBeanList(borrowRecover, BorrowRecoverVO.class);
        borrowRecoverResponse.setResultList(borrowRecoverVOS);
        return borrowRecoverResponse;
    }
    /**
     * 获取还款总额
     * @param userid
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/getCreditRepay/{userid}/{begin}/{end}")
    public CreditRepayResponse getCreditRepay(@PathVariable int userid,@PathVariable int begin,@PathVariable int end){
        CreditRepayResponse creditRepayResponse = new CreditRepayResponse();
        List<CreditRepay> creditRepay = weeklyService.getCreditRepay(userid, begin, end);
        List<CreditRepayVO> creditRepayVOS = CommonUtils.convertBeanList(creditRepay, CreditRepayVO.class);
        creditRepayResponse.setResultList(creditRepayVOS);
        return creditRepayResponse;
    }
    /**
     * 获取债转总额
     * @param userid
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/getCreditRepayToCredit/{userid}/{begin}/{end}")
    public CreditRepayResponse getCreditRepayToCredit(@PathVariable int userid,@PathVariable int begin,@PathVariable int end){
        CreditRepayResponse creditRepayResponse = new CreditRepayResponse();
        List<CreditRepay> creditRepayToCredit = weeklyService.getCreditRepayToCredit(userid, begin, end);
        List<CreditRepayVO> creditRepayVO = CommonUtils.convertBeanList(creditRepayToCredit, CreditRepayVO.class);
        creditRepayResponse.setResultList(creditRepayVO);
        return creditRepayResponse;
    }
    /**
     * 获取用户可用优惠券
     * @param userid
     * @return
     */
    @GetMapping("/getCoupon/{userid}")
    public boolean getCoupon(@PathVariable int userid){
        return weeklyService.coupon(userid);
    }


}
