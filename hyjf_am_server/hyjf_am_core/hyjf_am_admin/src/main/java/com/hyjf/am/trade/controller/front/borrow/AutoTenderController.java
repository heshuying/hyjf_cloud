/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.borrow;

import com.hyjf.am.response.BigDecimalResponse;
import com.hyjf.am.response.MapResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.trade.HjhAccedeResponse;
import com.hyjf.am.resquest.trade.SaveCreditTenderLogRequest;
import com.hyjf.am.resquest.trade.UpdateBorrowForAutoTenderRequest;
import com.hyjf.am.resquest.trade.UpdateCreditForAutoTenderRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;
import com.hyjf.am.trade.dao.model.customize.HjhAccedeCustomize;
import com.hyjf.am.trade.service.front.borrow.AutoTenderService;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 自动投资（原子层）
 *
 * @author liubin
 * @version AutoTenderController, v0.1 2018/6/28 19:08
 */
@RestController
@RequestMapping("/am-trade/autoTenderController")
public class AutoTenderController extends BaseController {

    @Autowired
    private AutoTenderService autoTenderService;

    /**
     * 取得自动投资用加入计划列表
     * @return
     */
    @RequestMapping("/selectPlanJoinList")
    public HjhAccedeResponse selectPlanJoinList() {
        HjhAccedeResponse response = new HjhAccedeResponse();
        List<HjhAccedeCustomize> list = autoTenderService.selectPlanJoinList();
        if (!CollectionUtils.isEmpty(list)) {
            response.setResultList(CommonUtils.convertBeanList(list, HjhAccedeVO.class));
        }
        return response;
    }

    /**
     * 计算计划债转实际金额 保存creditTenderLog表
     * @param request
     * @return
     */
    @RequestMapping("/saveCreditTenderLog")
    public MapResponse saveCreditTenderLog(@RequestBody SaveCreditTenderLogRequest request) {
        MapResponse response = new MapResponse();
        response.setResultMap(this.autoTenderService.saveCreditTenderLog(
                                                        request.getCredit(),
                                                        request.getHjhAccede(),
                                                        request.getOrderId(),
                                                        request.getOrderDate(),
                                                        request.getYujiAmoust(),
                                                        request.isLast()));
        return response;
    }

    /**
     * 取得当前债权在清算前已经发生债转的本金
     * @param hjhDebtCreditVO
     * @return
     */
    @RequestMapping("/getPreCreditCapital")
    public BigDecimalResponse getPreCreditCapital(@RequestBody HjhDebtCreditVO hjhDebtCreditVO) {
        HjhDebtCredit hjhDebtCredit = new HjhDebtCredit();
        BeanUtils.copyProperties(hjhDebtCreditVO, hjhDebtCredit);
        return new BigDecimalResponse(this.autoTenderService.getPreCreditCapital(hjhDebtCredit));
    }

    /**
     * 银行自动投资成功后，更新投资数据
     *
     * @param request
     * @return
     */
    @RequestMapping("/updateBorrowForAutoTender")
    public Response updateBorrowForAutoTender(@RequestBody UpdateBorrowForAutoTenderRequest request) {
        Borrow borrow = new Borrow();
        HjhAccede hjhAccede = new HjhAccede();
        BankCallBean bean = new BankCallBean();
        String borrowNid = request.getBorrowNid();
        String accedeOrderId = request.getAccedeOrderId();
        BeanUtils.copyProperties(request.getBankCallBeanVO(), bean);
        boolean result = this.autoTenderService.updateBorrowForAutoTender(borrowNid, accedeOrderId, bean);
        if (!result) {
            return new Response(Response.FAIL, Response.FAIL_MSG);
        }
        return new Response();
    }

    /**
     * 银行自动债转成功后，更新债转数据
     *
     * @param request
     * @return
     */
    @RequestMapping("/updateCreditForAutoTender")
    public Response updateCreditForAutoTender(@RequestBody UpdateCreditForAutoTenderRequest request) {
        //参数转换
        BankCallBean bean = new BankCallBean();
        String creditNid = request.getCreditNid();
        String accedeOrderId = request.getAccedeOrderId();
        String planNid = request.getPlanNid();
        String tenderUsrcustid = request.getTenderUsrcustid();
        String sellerUsrcustid = request.getSellerUsrcustid();
        Map<String, Object> resultMap = request.getResultMap();
        BeanUtils.copyProperties(request.getBankCallBeanVO(), bean);
        //银行自动投资成功后，更新投资数据
        boolean result = this.autoTenderService.updateCreditForAutoTender(
                creditNid, accedeOrderId, planNid, bean,
                tenderUsrcustid, sellerUsrcustid, resultMap
        );
        if (!result) {
            return new Response(Response.FAIL, Response.FAIL_MSG);
        }
        return new Response();
    }

    /**
     * 根据是否原始债权获出让人投标成功的授权号
     *
     * @param tenderOrderId
     * @param sourceType
     * @return
     */
    @RequestMapping("/getSellerAuthCode/{tenderOrderId}/{sourceType}")
    public StringResponse getSellerAuthCode(@PathVariable String tenderOrderId, @PathVariable Integer sourceType) {
        String authCode = this.autoTenderService.getSellerAuthCode(tenderOrderId, sourceType);
        return new StringResponse(authCode);
    }

    /**
     * 保存用户的债转承接log数据
     * @param request
     * @return
     * @author nxl
     */
    @RequestMapping("/saveCreditTenderLogNoSave")
    public MapResponse saveCreditTenderLogNoSave(@RequestBody SaveCreditTenderLogRequest request) {
        MapResponse response = new MapResponse();
        response.setResultMap(this.autoTenderService.saveCreditTenderLogNoSave(
                request.getCredit(),
                request.getHjhAccede(),
                request.getOrderId(),
                request.getOrderDate(),
                request.getYujiAmoust(),
                request.isLast()));
        return response;
    }
}
