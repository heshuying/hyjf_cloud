package com.hyjf.am.trade.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.trade.AccountWithdrawResponse;
import com.hyjf.am.response.trade.AssetManageResponse;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.trade.dao.model.auto.AccountWithdraw;
import com.hyjf.am.trade.dao.model.customize.trade.CurrentHoldObligatoryRightListCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.RepayMentListCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.TenderCreditDetailCustomize;
import com.hyjf.am.trade.service.AssetManageService;
import com.hyjf.am.vo.trade.TenderCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldObligatoryRightListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.RepayMentListCustomizeVO;
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
    private static Logger logger = LoggerFactory.getLogger(AccountWithdrawController.class);
    @Autowired
    private AssetManageService assetManageService;
    /**
     * @Description 获取用户当前持有列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectCurrentHoldObligatoryRightList")
    public AssetManageResponse selectCurrentHoldObligatoryRightList(@RequestBody AssetManageBeanRequest request){
        logger.info("request:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        List<CurrentHoldObligatoryRightListCustomize> list = assetManageService.selectCurrentHoldObligatoryRightList(request);
        List<CurrentHoldObligatoryRightListCustomizeVO> voList=null;
        if(!CollectionUtils.isEmpty(list)){
            voList=new ArrayList<>(list.size());
            for (CurrentHoldObligatoryRightListCustomize customize:list) {
                CurrentHoldObligatoryRightListCustomizeVO vo=new CurrentHoldObligatoryRightListCustomizeVO();
                BeanUtils.copyProperties(customize,vo);;
                voList.add(vo);
            }
        }
        response.setCurrentHoldObligatoryRightList(voList);
        return response;
    }

    /**
     * @Description 获取用户当前持有列表数量
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
     * @Description 获取用户当前持有列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectRepaymentList")
    public AssetManageResponse selectRepaymentList(@RequestBody AssetManageBeanRequest request){
        logger.info("request:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        List<RepayMentListCustomize> list = assetManageService.selectRepaymentList(request);
        List<RepayMentListCustomizeVO> voList=null;
        if(!CollectionUtils.isEmpty(list)){
            voList=new ArrayList<>(list.size());
            for (RepayMentListCustomize customize:list) {
                RepayMentListCustomizeVO vo=new RepayMentListCustomizeVO();
                BeanUtils.copyProperties(customize,vo);;
                voList.add(vo);
            }
        }
        response.setRepayMentList(voList);
        return response;
    }



    /**
     * @Description 获取用户已回款列表数量
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
     * @Description 获取用户当前持有列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectCreditRecordList")
    public AssetManageResponse selectCreditRecordList(@RequestBody AssetManageBeanRequest request){
        logger.info("request:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        List<TenderCreditDetailCustomize> list = assetManageService.selectCreditRecordList(request);
        List<TenderCreditDetailCustomizeVO> voList=null;
        if(!CollectionUtils.isEmpty(list)){
            voList=new ArrayList<>(list.size());
            for (TenderCreditDetailCustomize customize:list) {
                TenderCreditDetailCustomizeVO vo=new TenderCreditDetailCustomizeVO();
                BeanUtils.copyProperties(customize,vo);;
                voList.add(vo);
            }
        }
        response.setCreditRecordList(voList);
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

}
