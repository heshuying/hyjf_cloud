package com.hyjf.am.trade.controller.front.protocol;

import com.hyjf.am.response.trade.AssetManageResponse;
import com.hyjf.am.response.trade.TenderAgreementResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.TenderAgreement;
import com.hyjf.am.trade.service.front.protocol.TenderAgreementService;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldObligatoryRightListCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class TenderAgreementController extends BaseController {

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


    /**
     * 根据borrowId获取法大大协议信息列表
     * @author Zha Daojian
     * @date 2019/5/9 10:42
     * @param
     * @return
     **/
    @RequestMapping("/getTenderAgreementByBorrowNid/{borrowId}")
    public TenderAgreementResponse getTenderAgreementByBorrowNid(@PathVariable(value = "borrowId") String borrowId){
        logger.info("borrowId:" +borrowId);
        TenderAgreementResponse response = new TenderAgreementResponse();
        List<TenderAgreement> tenderAgreements=tenderAgreementService.getTenderAgreementByBorrowNid(borrowId);
        if (CollectionUtils.isNotEmpty(tenderAgreements)){
            response.setResultList(CommonUtils.convertBeanList(tenderAgreements,TenderAgreementVO.class));
        }
        return response;
    }


    @GetMapping("/getTenderAgreementInfo/{tenderAgreementID}")
    public TenderAgreementResponse getTenderAgreementInfo(@PathVariable String tenderAgreementID){
        TenderAgreementResponse response = new TenderAgreementResponse();
        TenderAgreement tenderAgreement=tenderAgreementService.getTenderAgreementInfo(tenderAgreementID);
        if (Validator.isNotNull(tenderAgreement)){
            response.setResult(CommonUtils.convertBean(tenderAgreement,TenderAgreementVO.class));
        }
        return response;
    }


    @GetMapping("/selectTenderAgreementByTenderNid/{tenderNid}")
    public TenderAgreementResponse selectTenderAgreementByTenderNid(@PathVariable String tenderNid){
        TenderAgreementResponse response = new TenderAgreementResponse();
        List<TenderAgreement> tenderAgreements=tenderAgreementService.selectTenderAgreementByNid(tenderNid);
        if (CollectionUtils.isNotEmpty(tenderAgreements)){
            response.setResultList(CommonUtils.convertBeanList(tenderAgreements,TenderAgreementVO.class));
        }
        return response;
    }

    @GetMapping("/getTenderAgreementListByTenderNidAndStatusNot2/{tenderNid}")
    public TenderAgreementResponse getTenderAgreementListByTenderNidAndStatusNot2(@PathVariable String tenderNid){
        TenderAgreementResponse response = new TenderAgreementResponse();
        List<TenderAgreement> agreements = this.tenderAgreementService.getTenderAgreementListByTenderNidAndStatusNot2(tenderNid);
        if (CollectionUtils.isNotEmpty(agreements)){
            response.setResultList(CommonUtils.convertBeanList(agreements,TenderAgreementVO.class));
        }
        return response;
    }
}
