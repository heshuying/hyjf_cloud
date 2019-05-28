/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.hjh;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.resquest.trade.DebtCreditRequest;
import com.hyjf.am.resquest.trade.HjhDebtCreditRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditTender;
import com.hyjf.am.trade.dao.model.customize.UserHjhInvistListCustomize;
import com.hyjf.am.trade.service.front.hjh.HjhDebtCreditService;
import com.hyjf.am.vo.trade.borrow.ProjectUndertakeListVO;
import com.hyjf.am.vo.trade.hjh.AppCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.trade.hjh.UserHjhInvistListCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author PC-LIUSHOUYI
 * @version HjhDebtCreditController, v0.1 2018/6/27 14:43
 */

@Api(value = "汇计划加入明细表")
@RestController
@RequestMapping("/am-trade/hjhDebtCredit")
public class HjhDebtCreditController extends BaseController {

    @Autowired
    HjhDebtCreditService hjhDebtCreditService;

    @GetMapping("/selectHjhDebtCreditListByAccedeOrderId/{accedeOrderId}")
    public HjhDebtCreditResponse selectHjhDebtCreditListByAccedeOrderId(@PathVariable String accedeOrderId) {
        HjhDebtCreditResponse response = new HjhDebtCreditResponse();
        List<HjhDebtCredit> hjhDebtCredits = hjhDebtCreditService.selectHjhDebtCreditListByAccedeOrderId(accedeOrderId);
        if(hjhDebtCredits != null){
            List<HjhDebtCreditVO> hjhDebtCreditVOS = CommonUtils.convertBeanList(hjhDebtCredits,HjhDebtCreditVO.class);
            response.setResultList(hjhDebtCreditVOS);
        }
        return response;
    }

    /**
     * @Author liushouyi
     * @Version v0.1
     * @Date
     */
    @GetMapping("/selectHjhDebtCreditListByOrderIdNid/{accedeOrderId}/{borrowNid}")
    public HjhDebtCreditResponse selectHjhDebtCreditListByOrderIdNid(@PathVariable String accedeOrderId, @PathVariable String borrowNid){
        HjhDebtCreditResponse response = new HjhDebtCreditResponse();
        List<HjhDebtCredit> hjhDebtCredits = hjhDebtCreditService.selectHjhDebtCreditListByOrderIdNid(accedeOrderId,borrowNid);
        if(hjhDebtCredits != null){
            List<HjhDebtCreditVO> hjhDebtCreditVOS = CommonUtils.convertBeanList(hjhDebtCredits,HjhDebtCreditVO.class);
            response.setResultList(hjhDebtCreditVOS);
        }
        return response;
    }


    @GetMapping("/selectHjhCreditTenderListByAssignOrderId/{assignOrderId}")
    public HjhDebtCreditTenderResponse selectHjhCreditTenderListByAssignOrderId(@PathVariable String assignOrderId){
        HjhDebtCreditTenderResponse response = new HjhDebtCreditTenderResponse();
        List<HjhDebtCreditTender> hjhDebtCreditTenders =hjhDebtCreditService.selectHjhCreditTenderListByAssignOrderId(assignOrderId);
        if (CollectionUtils.isNotEmpty(hjhDebtCreditTenders)){
            response.setResultList(CommonUtils.convertBeanList(hjhDebtCreditTenders,HjhDebtCreditTenderVO.class));
        }
        return response;
    }


    @PostMapping("/getHjhDebtCreditList")
    public HjhDebtCreditResponse getHjhDebtCreditList(@RequestBody HjhDebtCreditRequest request){
        HjhDebtCreditResponse response=new HjhDebtCreditResponse();
        List<HjhDebtCredit> borrowCredits=hjhDebtCreditService.getHjhDebtCreditList(request);
        if (CollectionUtils.isNotEmpty(borrowCredits)){
            response.setResultList(CommonUtils.convertBeanList(borrowCredits,HjhDebtCreditVO.class));
        }
        return response;
    }

    /**
     * @Author pcc
     * @Version v0.1
     * @Date
     */
    @GetMapping("/getHjhDebtCreditListByCreditNid/{creditNid}")
    public HjhDebtCreditResponse getHjhDebtCreditListByCreditNid(@PathVariable String creditNid){
        HjhDebtCreditResponse response = new HjhDebtCreditResponse();
        List<HjhDebtCredit> hjhDebtCredits = hjhDebtCreditService.getHjhDebtCreditListByCreditNid(creditNid);
        if(hjhDebtCredits != null){
            List<HjhDebtCreditVO> hjhDebtCreditVOS = CommonUtils.convertBeanList(hjhDebtCredits,HjhDebtCreditVO.class);
            response.setResultList(hjhDebtCreditVOS);
        }
        return response;
    }

    /**
     * @Author pcc
     * @Version v0.1
     * @Date
     */
    @GetMapping("/getHjhDebtCreditListByBorrowNid/{borrowNid}")
    public HjhDebtCreditResponse getHjhDebtCreditListByBorrowNid(@PathVariable String borrowNid){
        HjhDebtCreditResponse response = new HjhDebtCreditResponse();
        List<HjhDebtCredit> hjhDebtCredits = hjhDebtCreditService.getHjhDebtCreditListByBorrowNid(borrowNid);
        if(hjhDebtCredits != null){
            List<HjhDebtCreditVO> hjhDebtCreditVOS = CommonUtils.convertBeanList(hjhDebtCredits,HjhDebtCreditVO.class);
            response.setResultList(hjhDebtCreditVOS);
        }
        return response;
    }

    /**
     * 根据债转编号查询债转信息
     * @author zhangyk
     * @date 2018/6/30 11:19
     */
    @GetMapping("/selectCreditDetailBycreditNid/{creditNid}")
    public HjhAppCreditResponse selectCreditDetailBycreditNid(@PathVariable String creditNid){
        HjhAppCreditResponse response =  new HjhAppCreditResponse();
        AppCreditDetailCustomizeVO appCreditDetailCustomizeVO = hjhDebtCreditService.selectCreditDetailByCreditNid(creditNid);
        response.setResult(appCreditDetailCustomizeVO);
        return response;
    }

    /**
     * 根据债转编号查询债转信息
     * @author liubin
     * @date 2018/6/30 11:19
     */
    @GetMapping("/selectHjhDebtCreditByCreditNid/{creditNid}")
    public HjhDebtCreditResponse selectHjhDebtCreditByCreditNid(@PathVariable String creditNid){
        HjhDebtCreditResponse response =  new HjhDebtCreditResponse();
        HjhDebtCredit hjhDebtCredit = hjhDebtCreditService.selectHjhDebtCreditByCreditNid(creditNid);
        response.setResult(CommonUtils.convertBean(hjhDebtCredit, HjhDebtCreditVO.class));
        return response;
    }

    /**
     * 根据债转编号查询债转信息(从写库)
     * @author liubin
     * @date 2018/6/30 11:19
     */
    @GetMapping("/doSelectHjhDebtCreditByCreditNid/{creditNid}")
    public HjhDebtCreditResponse doSelectHjhDebtCreditByCreditNid(@PathVariable String creditNid){
        HjhDebtCreditResponse response =  new HjhDebtCreditResponse();
        HjhDebtCredit hjhDebtCredit = hjhDebtCreditService.doSelectHjhDebtCreditByCreditNid(creditNid);
        response.setResult(CommonUtils.convertBean(hjhDebtCredit, HjhDebtCreditVO.class));
        return response;
    }

    @PostMapping("/updateHjhDebtCreditByPK")
    public IntegerResponse updateHjhDebtCreditByPK(@RequestBody HjhDebtCreditVO hjhDebtCreditVO){
        HjhDebtCredit hjhDebtCredit =  new HjhDebtCredit();
        BeanUtils.copyProperties(hjhDebtCreditVO, hjhDebtCredit);
        return new IntegerResponse(this.hjhDebtCreditService.updateHjhDebtCreditByPK(hjhDebtCredit));
    }
	/**
	 * 获取债转承接信息 PrimaryKey
	 * by libin
	 * @param nid
	 * @return
	 */
    @GetMapping("/getHjhDebtCreditTenderByPrimaryKey/{nid}")
    public HjhDebtCreditTenderResponse getHjhDebtCreditTenderByPrimaryKey(@PathVariable Integer nid){
        HjhDebtCreditTenderResponse response = new HjhDebtCreditTenderResponse();
        HjhDebtCreditTenderVO hjhDebtCreditTenderVO = hjhDebtCreditService.getHjhDebtCreditTenderByPrimaryKey(nid);
        if (hjhDebtCreditTenderVO != null){
            response.setResult(hjhDebtCreditTenderVO);
        }
        return response;
    }
    
	/**
	 * 获取债转承接信息 by AssignOrderId
	 * by libin
	 * @param assignOrderId
	 * @return
	 */
    @GetMapping("/getHjhDebtCreditTenderByAssignOrderId/{assignOrderId}")
    public HjhDebtCreditTenderResponse getHjhDebtCreditTenderByAssignOrderId(@PathVariable String assignOrderId){
        HjhDebtCreditTenderResponse response = new HjhDebtCreditTenderResponse();
        HjhDebtCreditTenderVO hjhDebtCreditTenderVO = hjhDebtCreditService.getHjhDebtCreditTenderByAssignOrderId(assignOrderId);
        if (hjhDebtCreditTenderVO != null){
            response.setResult(hjhDebtCreditTenderVO);
        }
        return response;
    }


    /**
     * 获取hjh出借列表信息
     * @return
     */
    @PostMapping("/getUserHjhInvestList")
    public HjhUserInvestListResponse getUserHjhInvestList(@RequestBody Map<String,Object> params){
        HjhUserInvestListResponse response = new HjhUserInvestListResponse();
        List<UserHjhInvistListCustomize> list = hjhDebtCreditService.getUserHjhInvestList(params);
        if (CollectionUtils.isNotEmpty(list)){
            response.setResultList(CommonUtils.convertBeanList(list,UserHjhInvistListCustomizeVO.class));
        }
        return response;
    }

    /**
     * 获取hjh出借列表信息count
     * @return
     */
    @PostMapping("/getUserHjhInvestCount")
    public IntegerResponse getUserHjhInvestCount(@RequestBody Map<String,Object> params){
        IntegerResponse response = new IntegerResponse();
        int count = hjhDebtCreditService.getUserHjhInvestCount(params);
        response.setResultInt(count);
        return response;
    }

    @PostMapping("/selectHjhDebtCreditListByBorrowNidAndStatus")
    public HjhDebtCreditResponse selectHjhDebtCreditListByBorrowNidAndStatus(@RequestBody DebtCreditRequest request){
        HjhDebtCreditResponse response = new HjhDebtCreditResponse();
        List<HjhDebtCredit> list = hjhDebtCreditService.selectHjhDebtCreditListByBorrowNidAndStatus(request);
        if (CollectionUtils.isNotEmpty(list)){
            List<HjhDebtCreditVO> resultList = CommonUtils.convertBeanList(list,HjhDebtCreditVO.class);
            response.setResultList(resultList);
        }
        return response;
    }

    /**
     * 查询承接记录数
     * @author zhangyk
     * @date 2018/8/9 11:06
     */
    @PostMapping("/countCreditTenderByBorrowNidAndUserId")
    public HjhDebtCreditResponse countCreditTenderByBorrowNidAndUserId(@RequestBody Map<String,Object> params){
        HjhDebtCreditResponse response = new HjhDebtCreditResponse();
        int count = hjhDebtCreditService.countCreditTenderByBorrowNidAndUserId(params);
        response.setTenderCount(count);
        return response;
    }

    /**
     * 查询当前承接下的总金额
     * @author zhangyk
     * @date 2018/8/9 13:48
     */
    @GetMapping("/sumUndertakeAmount/{borrowNid}")
    public HjhDebtCreditResponse sumUnderTakeAmountByBorrowNid(@PathVariable String borrowNid){
        HjhDebtCreditResponse response = new HjhDebtCreditResponse();
        String sum = hjhDebtCreditService.sumUnderTakeAmountByBorrowNid(borrowNid);
        response.setSum(sum);
        return response;
    }


    /**
     * 在承接中的列表
     * @author zhangyk
     * @date 2018/8/9 14:17
     */
    @PostMapping("/selectProjectUndertakeList")
    public HjhCreditUnderTakeResponse selectProjectUndertakeList(@RequestBody Map<String,Object> params){
        HjhCreditUnderTakeResponse response = new HjhCreditUnderTakeResponse();
        List<ProjectUndertakeListVO> list = hjhDebtCreditService.selectProjectUndertakeList(params);
        if (CollectionUtils.isNotEmpty(list)){
            response.setResultList(list);
        }
        return response;
    }

    /**
     * 根据原投资订单号查找转让信息
     * @param sellOrderId
     * @return
     * add by nxl
     */
    @GetMapping("/selectCreditBySellOrderId/{sellOrderId}")
    public HjhDebtCreditResponse selectCreditBySellOrderId(@PathVariable String sellOrderId){
        HjhDebtCreditResponse response = new HjhDebtCreditResponse();
        response.setRtn(Response.FAIL);
        List<HjhDebtCredit> hjhDebtCreditList = hjhDebtCreditService.selectCreditBySellOrderId(sellOrderId);
        if(CollectionUtils.isNotEmpty(hjhDebtCreditList)){
            List<HjhDebtCreditVO> hjhDebtCreditVOList = CommonUtils.convertBeanList(hjhDebtCreditList,HjhDebtCreditVO.class);
            response.setRtn(Response.SUCCESS);
            response.setResultList(hjhDebtCreditVOList);
        }
        return response;
    }
}
