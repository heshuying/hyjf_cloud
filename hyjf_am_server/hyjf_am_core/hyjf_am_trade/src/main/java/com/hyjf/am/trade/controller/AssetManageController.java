package com.hyjf.am.trade.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.trade.AccountWithdrawResponse;
import com.hyjf.am.response.trade.AssetManageResponse;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.trade.dao.model.auto.AccountWithdraw;
import com.hyjf.am.trade.dao.model.customize.trade.*;
import com.hyjf.am.trade.service.AssetManageService;
import com.hyjf.am.vo.trade.TenderCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldObligatoryRightListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldPlanListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.RepayMentListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.RepayMentPlanListCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CommonUtils;
import netscape.javascript.JSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pangchengchao
 * @version AssetManageController, v0.1 2018/6/24 14:47
 */
@RestController
@RequestMapping("/am-trade/assetmanage")
public class AssetManageController {
    private static Logger logger = LoggerFactory.getLogger(AssetManageController.class);
    @Autowired
    private AssetManageService assetManageService;
    /**
     * @Description 获取用户当前持有债权列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectCurrentHoldObligatoryRightList")
    public AssetManageResponse selectCurrentHoldObligatoryRightList(@RequestBody AssetManageBeanRequest request){
        logger.info("request:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        List<CurrentHoldObligatoryRightListCustomize> list = assetManageService.selectCurrentHoldObligatoryRightList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<CurrentHoldObligatoryRightListCustomizeVO> voList = CommonUtils.convertBeanList(list, CurrentHoldObligatoryRightListCustomizeVO.class);
            response.setCurrentHoldObligatoryRightList(voList);
        }
        return response;
    }

    /**
     * @Description 获取用户当前持有债权列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectCurrentHoldObligatoryRightListTotal")
    public AssetManageResponse selectCurrentHoldObligatoryRightListTotal(@RequestBody AssetManageBeanRequest request){
        logger.info("request:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        int currentHoldObligatoryRightCount = this.assetManageService.selectCurrentHoldObligatoryRightListTotal(request);

        response.setCurrentHoldObligatoryRightCount(currentHoldObligatoryRightCount);
        return response;
    }

    /**
     * @Description 获取用户已回款债权列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectRepaymentList")
    public AssetManageResponse selectRepaymentList(@RequestBody AssetManageBeanRequest request){
        logger.info("request:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        List<RepayMentListCustomize> list = assetManageService.selectRepaymentList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<RepayMentListCustomizeVO> voList = CommonUtils.convertBeanList(list, RepayMentListCustomizeVO.class);
            response.setRepayMentList(voList);
        }

        return response;
    }



    /**
     * @Description 获取用户已回款债权列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectRepaymentListTotal")
    public AssetManageResponse selectRepaymentListTotal(@RequestBody AssetManageBeanRequest request){
        logger.info("request:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        int repayMentCount = this.assetManageService.selectRepaymentListTotal(request);

        response.setRepayMentCount(repayMentCount);
        return response;
    }

    /**
     * @Description 获取用户转让列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectCreditRecordList")
    public AssetManageResponse selectCreditRecordList(@RequestBody AssetManageBeanRequest request){
        logger.info("request:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        List<TenderCreditDetailCustomize> list = assetManageService.selectCreditRecordList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<TenderCreditDetailCustomizeVO> voList = CommonUtils.convertBeanList(list, TenderCreditDetailCustomizeVO.class);
            response.setCreditRecordList(voList);
        }

        return response;
    }

    /**
     * @Description 获取用户转让列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/countCreditRecordTotal")
    public AssetManageResponse countCreditRecordTotal(@RequestBody AssetManageBeanRequest request){
        logger.info("request:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        int tenderCreditDetailCount = this.assetManageService.countCreditRecordTotal(request);
        response.setTenderCreditDetailCount(tenderCreditDetailCount);
        return response;
    }

    /**
     * @Description 获取用户当前持有计划列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectCurrentHoldPlanList")
    public AssetManageResponse selectCurrentHoldPlanList(@RequestBody AssetManageBeanRequest request){
        logger.info("request:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        List<CurrentHoldPlanListCustomize> list = assetManageService.selectCurrentHoldPlanList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<CurrentHoldPlanListCustomizeVO> voList = CommonUtils.convertBeanList(list, CurrentHoldPlanListCustomizeVO.class);
            response.setCurrentHoldPlanList(voList);
        }

        return response;
    }

    /**
     * @Description 获取用户当前持有计划列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/countCurrentHoldPlanTotal")
    public AssetManageResponse countCurrentHoldPlanTotal(@RequestBody AssetManageBeanRequest request){
        logger.info("request:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        int  currentHoldPlanCount= this.assetManageService.countCurrentHoldPlanTotal(request);

        response.setCurrentHoldPlanCount(currentHoldPlanCount);
        return response;
    }

    /**
     * @Description 获取用户已回款计划列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectRepayMentPlanList")
    public AssetManageResponse selectRepayMentPlanList(@RequestBody AssetManageBeanRequest request){
        logger.info("request:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        List<RepayMentPlanListCustomize> list = assetManageService.selectRepayMentPlanList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<RepayMentPlanListCustomizeVO> voList = CommonUtils.convertBeanList(list, RepayMentPlanListCustomizeVO.class);
            response.setRepayMentPlanList(voList);
        }

        return response;
    }

    /**
     * @Description 获取用户已回款计划列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/countRepayMentPlanTotal")
    public AssetManageResponse countRepayMentPlanTotal(@RequestBody AssetManageBeanRequest request){
        logger.info("request:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        int  repayMentPlanCount= this.assetManageService.countRepayMentPlanTotal(request);

        response.setRepayMentPlanCount(repayMentPlanCount);
        return response;
    }
}
