package com.hyjf.am.trade.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.trade.AssetManageResponse;
import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.dao.model.auto.TenderAgreement;
import com.hyjf.am.trade.dao.model.customize.trade.CurrentHoldObligatoryRightListCustomize;
import com.hyjf.am.trade.service.AssetManageService;
import com.hyjf.am.trade.service.TenderAgreementService;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldObligatoryRightListCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author pangchengchao
 * @version TenderAgreementController, v0.1 2018/6/27 9:28
 */
@Api(value = "法大大协议信息")
@RestController
@RequestMapping("/am-trade/tenderagreement")
public class TenderAgreementController {
    private static Logger logger = LoggerFactory.getLogger(TenderAgreementController.class);

    @Autowired
    private TenderAgreementService tenderAgreementService;
    /**
     * @Description 根据Nid获取法大大协议信息列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectTenderAgreementByNid/{nid}")
    public AssetManageResponse selectTenderAgreementByNid(@PathVariable(value = "nid") String nid){
        logger.info("nid:" +nid);
        AssetManageResponse response = new AssetManageResponse();
        List<TenderAgreement> list = tenderAgreementService.selectTenderAgreementByNid(nid);
        if(!CollectionUtils.isEmpty(list)){
            List<CurrentHoldObligatoryRightListCustomizeVO> voList = CommonUtils.convertBeanList(list, CurrentHoldObligatoryRightListCustomizeVO.class);
            response.setCurrentHoldObligatoryRightList(voList);
        }
        return response;
    }
}
