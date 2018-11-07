/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.borrow;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.admin.AdminCreditTenderResponse;
import com.hyjf.am.response.trade.BorrowCreditRepayResponse;
import com.hyjf.am.response.trade.BorrowCreditTenderResponse;
import com.hyjf.am.response.trade.CountResponse;
import com.hyjf.am.response.trade.CreditTenderResponse;
import com.hyjf.am.resquest.admin.BorrowCreditRepayAmRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.auto.CreditTender;
import com.hyjf.am.trade.dao.model.customize.AdminBorrowCreditTenderCustomize;
import com.hyjf.am.trade.service.front.borrow.BorrowCreditTenderService;
import com.hyjf.am.vo.admin.BorrowCreditRepaySumVO;
import com.hyjf.am.vo.admin.BorrowCreditTenderVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhangyk
 * @date 2018/7/11 15:28
 */
@RestController
@RequestMapping("/am-trade/creditTender")
public class BorrowCreditTenderController extends BaseController {

    @Autowired
    private BorrowCreditTenderService borrowCreditTenderService;

    /**
     *  还款列表list
     * @author zhangyk
     * @date 2018/7/12 19:20
     */
    @PostMapping("/getRepayList")
    public BorrowCreditTenderResponse getBorrowCreditTenderList(@RequestBody BorrowCreditRepayAmRequest request){
        BorrowCreditTenderResponse response = new BorrowCreditTenderResponse();
        List<AdminBorrowCreditTenderCustomize> list = borrowCreditTenderService.searchBorrowCreditRepayList(request);
        if (CollectionUtils.isNotEmpty(list)){
            response.setResultList(CommonUtils.convertBeanList(list,BorrowCreditRepayVO.class));
        }
        return response;
    }


    /**
     * 还款列表count
     * @author zhangyk
     * @date 2018/7/12 19:27
     */
    @PostMapping("/getRepayCount")
    public BorrowCreditTenderResponse countBorrowCreditTender(@RequestBody BorrowCreditRepayAmRequest request){
        BorrowCreditTenderResponse response = new BorrowCreditTenderResponse();
        Integer count = borrowCreditTenderService.countBorrowCreditRepay(request);
        response.setCount(count);
        return response;
    }

    /**
     * 还款列表合计
     * @author zhangyk
     * @date 2018/7/12 19:34
     */
    @PostMapping("/getRepaySum")
    public BorrowCreditTenderResponse sumBorrowCreditTender(@RequestBody BorrowCreditRepayAmRequest request){
        BorrowCreditTenderResponse response = new BorrowCreditTenderResponse();
        BorrowCreditRepaySumVO sumVO = borrowCreditTenderService.sumBorrowCreditRepay(request);
        response.setSumData(sumVO);
        return response;
    }

    /**
     * 汇转让还款详情count
     * @author zhangyk
     * @date 2018/7/12 15:22
     */
    @PostMapping("/getRepayInfoCount")
    public BorrowCreditRepayResponse getCreditTenderInfoCount(@RequestBody BorrowCreditRepayAmRequest request){
        BorrowCreditRepayResponse response = new BorrowCreditRepayResponse();
        Integer count = borrowCreditTenderService.getCreditRepayInfoListCount(request);
        response.setCount(count);
        return response;

    }


    /**
     * 汇转让还款详情list
     * @author zhangyk
     * @date 2018/7/12 15:23
     */
    @PostMapping("/getRepayInfoList")
    public BorrowCreditRepayResponse getCreditTenderInfoList(@RequestBody BorrowCreditRepayAmRequest request){
        BorrowCreditRepayResponse response = new BorrowCreditRepayResponse();
        List<BorrowCreditRepayInfoVO> list = borrowCreditTenderService.getCreditRepayInfoList(request);
        response.setResultList(list);
        return response;
    }

    /**
     * 汇转让还款详情合计行
     * @author zhangyk
     * @date 2018/7/12 15:23
     */
    @PostMapping("/getRepayInfoSum")
    public BorrowCreditRepayResponse getCreditTenderInfoSum(@RequestBody BorrowCreditRepayAmRequest request){
        BorrowCreditRepayResponse response = new BorrowCreditRepayResponse();
        Map<String,Object> map = borrowCreditTenderService.getCreditRepayInfoListSum(request);
        Map<String,String> result = new HashMap<>();
        map.forEach((k,v) -> result.put(k,String.valueOf(v)));
        response.setSumData(result);
        return response;
    }


    /**
     * 汇转让承接信息count
     * @author zhangyk
     * @date 2018/7/12 20:59
     */
    @PostMapping("/getTenderCount")
    public AdminCreditTenderResponse getTenderCount(@RequestBody BorrowCreditRepayAmRequest request){
        AdminCreditTenderResponse response = new AdminCreditTenderResponse();
        Integer count = borrowCreditTenderService.getCreditTenderCount(request);
        response.setCount(count);
        return response;
    }


    /**
     * 汇转让承接信息列表list
     * @author zhangyk
     * @date 2018/7/12 20:59
     */
    @PostMapping("/getTenderList")
    public AdminCreditTenderResponse getTenderList(@RequestBody BorrowCreditRepayAmRequest request){
        AdminCreditTenderResponse response = new AdminCreditTenderResponse();
        List<BorrowCreditTenderVO> list = borrowCreditTenderService.getCreditTenderList(request);
        response.setResultList(list);
        return response;
    }


    /**
     * 汇转让承接信息列表list
     * @author zhangyk
     * @date 2018/7/12 20:59
     */
    @PostMapping("/getTenderSum")
    public AdminCreditTenderResponse getTenderSum(@RequestBody BorrowCreditRepayAmRequest request){
        AdminCreditTenderResponse response = new AdminCreditTenderResponse();
        Map<String,Object> sum = borrowCreditTenderService.getCreditTenderSum(request);
        response.setSumData(sum);
        return response;
    }


    @PostMapping("/getBorrowCreditTender4Admin")
    public CountResponse getBorrowCreditTenderCount(@RequestBody Map<String,Object> param){
        CountResponse response = new CountResponse();
        int count = borrowCreditTenderService.getBorrowCreditTenderCount4Admin(param);
        response.setCount(count);
        return response;
    }



    @GetMapping("getCreditTenderServiceFee/{creditNid}")
    public StringResponse getCreditTenderServiceFee(@PathVariable String creditNid){
        StringResponse response = new StringResponse();
        String fee = borrowCreditTenderService.getCreditTenderServiceFee(creditNid);
        response.setResultStr(fee);
        return response;
    }


    /**
     * 根据用户ID查询用户承接记录
     *
     * @param userId
     * @return
     */
    @RequestMapping("/selectCreditTenderByUserId/{userId}")
    public CreditTenderResponse selectCreditTenderByUserId(@PathVariable Integer userId){
        CreditTenderResponse response = new CreditTenderResponse();
        List<CreditTender> list = this.borrowCreditTenderService.selectCreditTenderByUserId(userId);
        if (!CollectionUtils.isEmpty(list)) {
            List<CreditTenderVO> voList = CommonUtils.convertBeanList(list, CreditTenderVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 根据承接订单号查询承接记录
     *
     * @param assignOrderId
     * @return
     */
    @RequestMapping("/selectCreditTenderByAssignOrderId/{assignOrderId}")
    public CreditTenderResponse selectCreditTenderByAssignOrderId(@PathVariable String assignOrderId) {
        CreditTenderResponse response = new CreditTenderResponse();
        CreditTender creditTender = this.borrowCreditTenderService.selectCreditTenderByAssignOrderId(assignOrderId);
        if (creditTender != null) {
            CreditTenderVO creditTenderVO = CommonUtils.convertBean(creditTender, CreditTenderVO.class);
            response.setResult(creditTenderVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }








}
