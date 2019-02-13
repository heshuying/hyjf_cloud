package com.hyjf.am.trade.controller.hgreportdata.bifa;


import com.hyjf.am.response.Response;
import com.hyjf.am.response.bifa.BifaBorrowUserInfoResponse;
import com.hyjf.am.response.bifa.BifaHjhPlanResponse;
import com.hyjf.am.response.bifa.UserIdAccountSumBeanResponse;
import com.hyjf.am.response.trade.BorrowResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.HjhPlan;
import com.hyjf.am.trade.dao.model.bifa.UserIdAccountSumBean;
import com.hyjf.am.trade.service.front.borrow.BorrowUserService;
import com.hyjf.am.vo.trade.bifa.BifaBorrowUserInfoVO;
import com.hyjf.am.vo.trade.bifa.UserIdAccountSumBeanVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author jijun
 * @version BifaBorrowUserInfoController
 */
@Api(value = "北互金")
@RestController
@RequestMapping("/am-trade/bifaDataReport")
public class BifaDataReportController extends BaseController {

    @Autowired
    private BorrowUserService borrowUserService;

    /**
     * 获取借款人信息
     */
    @GetMapping("/getBorrowUserInfo/{borrowNid}/{companyOrPersonal}")
    public BifaBorrowUserInfoResponse getBorrowUserInfo(@PathVariable String borrowNid,@PathVariable String companyOrPersonal) {
        BifaBorrowUserInfoResponse response = new BifaBorrowUserInfoResponse();
        BifaBorrowUserInfoVO bifaBorrowUserInfoVO = borrowUserService.getBifaBorrowUserInfo(borrowNid,companyOrPersonal);
        if (null != bifaBorrowUserInfoVO) {
            response.setResult(bifaBorrowUserInfoVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 散标转让服务费
     * @param creditNid
     * @return
     */
    @GetMapping("/getBorrowCreditFeeSumByCreditNid/{creditNid}")
    public BigDecimal getBorrowCreditFeeSumByCreditNid(@PathVariable String creditNid){
        return borrowUserService.getBorrowCreditFeeSumByCreditNid(creditNid);
    }

    /**
     * 智投转让服务费
     * @param creditNid
     * @return
     */
    @GetMapping("/getHjhCreditFeeSumByCreditNid/{creditNid}")
    public BigDecimal getHjhCreditFeeSumByCreditNid(@PathVariable String creditNid){
        return borrowUserService.getHjhCreditFeeSumByCreditNid(creditNid);
    }

    /**
     * 获取智投数
     * @param planNid
     * @return
     */
    @GetMapping("/countHjhPlan/{planNid}")
    public int countHjhPlan(@PathVariable String planNid){
        return borrowUserService.countHjhPlan(planNid);
    }

    /**
     * 获取智投列表
     */
    @GetMapping("/selectHjhPlanInfoList")
    public BifaHjhPlanResponse selectHjhPlanInfoList(){
        BifaHjhPlanResponse response = new BifaHjhPlanResponse();
        List<HjhPlan> recordList = borrowUserService.selectHjhPlanInfoList();
        if (CollectionUtils.isNotEmpty(recordList)){
            response.setResultList(CommonUtils.convertBeanList(recordList,HjhPlanVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     *已开户且出借>0的用户
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/getBorrowTenderAccountSum/{startDate}/{endDate}")
    public UserIdAccountSumBeanResponse getBorrowTenderAccountSum(@PathVariable Integer startDate,@PathVariable Integer endDate){
        UserIdAccountSumBeanResponse response = new UserIdAccountSumBeanResponse();
        List<UserIdAccountSumBean> recordList = borrowUserService.getBorrowTenderAccountSum(startDate,endDate);
        if (CollectionUtils.isNotEmpty(recordList)){
            response.setResultList(CommonUtils.convertBeanList(recordList,UserIdAccountSumBeanVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 借款人信息
     */
    @GetMapping("/selectBorrowUserInfo/{startDate}/{endDate}")
    public BorrowResponse selectBorrowUserInfo(@PathVariable Integer startDate,@PathVariable Integer endDate){
        BorrowResponse response = new BorrowResponse();
        List<BorrowAndInfoVO> recordList = borrowUserService.selectBorrowUserInfo(startDate,endDate);
        if (CollectionUtils.isNotEmpty(recordList)){
            response.setResultList(recordList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 借贷笔数
     */
    @PostMapping("/getLoanNum")
    public int getLoanNum(@RequestBody Date time){
        return borrowUserService.getLoanNum(time);
    }

    /**
     * 累计借贷余额
     * @param time
     * @return
     */
    @PostMapping("/getWillPayMoney")
    public BigDecimal getWillPayMoney(@RequestBody Date time){
        return borrowUserService.getWillPayMoney(time);
    }

    /**
     * 累计借贷余额笔数
     * @return
     */
    @GetMapping("/getTotalLoanBalanceNum")
    public int getTotalLoanBalanceNum(){
        return borrowUserService.getTotalLoanBalanceNum();
    }

    /**
     * 累计借款人
     * @return
     */
    @GetMapping("/countBorrowUser")
    public int countBorrowUser(){
        return borrowUserService.countBorrowUser();
    }

    /**
     * 累计投资人数
     * @return
     */
    @PostMapping("/getTenderCount")
    public int getTenderCount (@RequestBody Date time){
        return borrowUserService.getTenderCount(time);
    }
    /**
     * 当前借款人
     */
    @GetMapping("/countCurrentBorrowUser")
    public int countCurrentBorrowUser(){
        return borrowUserService.countCurrentBorrowUser();
    }

    /**
     * 当前出借人
     * @return
     */
    @GetMapping("/countCurrentTenderUser")
    public int countCurrentTenderUser(){
        return borrowUserService.countCurrentTenderUser();
    }

    /**
     *平台前十大融资人融资待还余额占比
     * @return
     */
    @GetMapping("/sumBorrowUserMoneyTopTen")
    public BigDecimal sumBorrowUserMoneyTopTen() {
        return borrowUserService.sumBorrowUserMoneyTopTen();
    }

    /**
     * 代还总金额
     * @return
     */
    @GetMapping("/sumBorrowUserMoney")
    public BigDecimal sumBorrowUserMoney() {
        return borrowUserService.sumBorrowUserMoney();
    }

    /**
     * 平台单一融资人最大融资待还余额占比
     * @return
     */
    @GetMapping("/sumBorrowUserMoneyTopOne")
    public BigDecimal sumBorrowUserMoneyTopOne() {
        return borrowUserService.sumBorrowUserMoneyTopOne();
    }

}
