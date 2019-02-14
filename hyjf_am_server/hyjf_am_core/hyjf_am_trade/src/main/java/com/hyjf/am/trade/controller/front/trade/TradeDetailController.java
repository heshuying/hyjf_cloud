package com.hyjf.am.trade.controller.front.trade;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.trade.EvaluationConfigResponse;
import com.hyjf.am.response.trade.TenderDetailResponse;
import com.hyjf.am.resquest.app.AppTradeDetailBeanRequest;
import com.hyjf.am.resquest.trade.TradeDetailBeanRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.AppTradeListCustomize;
import com.hyjf.am.trade.dao.model.customize.WebUserRechargeListCustomize;
import com.hyjf.am.trade.dao.model.customize.WebUserTradeListCustomize;
import com.hyjf.am.trade.dao.model.customize.WebUserWithdrawListCustomize;
import com.hyjf.am.trade.service.front.trade.TradeDetailService;
import com.hyjf.am.vo.app.AppTradeListCustomizeVO;
import com.hyjf.am.vo.trade.EvaluationConfigVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserRechargeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserTradeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserWithdrawListCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author pangchengchao
 * @version TradeDetailController , v0.1 2018/6/27 11:06
 */
@RestController
@RequestMapping("am-trade/tradedetail")
public class TradeDetailController extends BaseController {
    @Autowired
    private TradeDetailService tradeDetailService;

    /**
     * @Description "获取用户收支明细列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/searchUserTradeList")
    public TenderDetailResponse searchUserTradeList(@RequestBody TradeDetailBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        TenderDetailResponse response = new TenderDetailResponse();
        List<WebUserTradeListCustomize> list = tradeDetailService.searchUserTradeList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<WebUserTradeListCustomizeVO> voList = CommonUtils.convertBeanList(list, WebUserTradeListCustomizeVO.class);
            response.setUserTrades(voList);
        }
        return response;
    }

    /**
     * @Description 获取用户收支明细列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/countUserTradeRecordTotal")
    public TenderDetailResponse countUserTradeRecordTotal(@RequestBody TradeDetailBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        TenderDetailResponse response = new TenderDetailResponse();
        int count = this.tradeDetailService.countUserTradeRecordTotal(request);

        response.setUserTradesCount(count);
        return response;
    }


    /**
     * @Description "获取用户充值记录列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/searchUserRechargeList")
    public TenderDetailResponse searchUserRechargeList(@RequestBody TradeDetailBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        TenderDetailResponse response = new TenderDetailResponse();
        List<WebUserRechargeListCustomize> list = tradeDetailService.searchUserRechargeList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<WebUserRechargeListCustomizeVO> voList = CommonUtils.convertBeanList(list, WebUserRechargeListCustomizeVO.class);
            response.setRechargeList(voList);
        }
        return response;
    }
    /**
     * @Description 获取用户充值记录列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/countUserRechargeRecordTotal")
    public TenderDetailResponse countUserRechargeRecordTotal(@RequestBody TradeDetailBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        TenderDetailResponse response = new TenderDetailResponse();
        int count = this.tradeDetailService.countUserRechargeRecordTotal(request);

        response.setRechargeListCount(count);
        return response;
    }


    /**
     * @Description "获取用户提现记录列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/searchUserWithdrawList")
    public TenderDetailResponse searchUserWithdrawList(@RequestBody TradeDetailBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        TenderDetailResponse response = new TenderDetailResponse();
        List<WebUserWithdrawListCustomize> list = tradeDetailService.searchUserWithdrawList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<WebUserWithdrawListCustomizeVO> voList = CommonUtils.convertBeanList(list, WebUserWithdrawListCustomizeVO.class);
            response.setWithdrawList(voList);
        }
        return response;
    }
    /**
     * @Description 获取用户提现记录列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/countUserWithdrawRecordTotal")
    public TenderDetailResponse countUserWithdrawRecordTotal(@RequestBody TradeDetailBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        TenderDetailResponse response = new TenderDetailResponse();
        int count = this.tradeDetailService.countUserWithdrawRecordTotal(request);

        response.setWithdrawListCount(count);
        return response;
    }


    /**
     * @Description "获取app用户交易记录列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/searchAppTradeDetailList")
    public TenderDetailResponse searchAppTradeDetailList(@RequestBody AppTradeDetailBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        TenderDetailResponse response = new TenderDetailResponse();
        List<AppTradeListCustomize> list = tradeDetailService.searchAppTradeDetailList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<AppTradeListCustomizeVO> voList = CommonUtils.convertBeanList(list, AppTradeListCustomizeVO.class);
            response.setAppTradeList(voList);
        }
        return response;
    }
    /**
     * @Description 获取用户提现记录列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/countAppTradeDetailListRecordTotal")
    public TenderDetailResponse countAppTradeDetailListRecordTotal(@RequestBody AppTradeDetailBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        TenderDetailResponse response = new TenderDetailResponse();
        int count = this.tradeDetailService.countAppTradeDetailListRecordTotal(request);
        response.setAppTradeDetailListCount(count);
        return response;
    }

    /**
     * @Description 获取测评配置
     * @Author wenxin
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectEvaluationConfig")
    public EvaluationConfigResponse selectEvaluationConfig(@RequestBody EvaluationConfigVO request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        EvaluationConfigResponse response = new EvaluationConfigResponse();
        List<EvaluationConfigVO> list = tradeDetailService.selectEvaluationConfigList(request);
        if(!CollectionUtils.isEmpty(list)){
            response.setResultList(list);
        }
        return response;
    }
}
