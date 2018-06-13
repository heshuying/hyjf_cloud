/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.assetpush.InfoBean;
import com.hyjf.am.response.trade.BorrowProjectRepayReponse;
import com.hyjf.am.response.trade.HjhAssetBorrowTypeResponse;
import com.hyjf.am.response.trade.STZHWhiteListResponse;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectRepay;
import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowType;
import com.hyjf.am.trade.dao.model.auto.HjhPlanAsset;
import com.hyjf.am.trade.dao.model.auto.STZHWhiteList;
import com.hyjf.am.trade.service.AssetPushService;
import com.hyjf.am.vo.assetpush.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.assetpush.STZHWhiteListVO;
import com.hyjf.am.vo.trade.BorrowProjectRepayVO;
import com.hyjf.am.vo.trade.HjhPlanAssetVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fuqiang
 * @version AssetPushController, v0.1 2018/6/12 10:04
 */
@RestController
@RequestMapping("/am-borrow/assetPush")
public class AssetPushController {

    @Autowired
    private AssetPushService assetPushService;

    /**
     * 获取机构信息
     * @param instCode
     * @param assetType
     * @return
     */
    @GetMapping("/selectAssetBorrowType/{instCode}/{assetType}")
    public HjhAssetBorrowTypeResponse selectAssetBorrowType(String instCode, int assetType) {
        HjhAssetBorrowTypeResponse response = new HjhAssetBorrowTypeResponse();
        HjhAssetBorrowType hjhAssetBorrowType = assetPushService.selectAssetBorrowType(instCode, assetType);
        if (hjhAssetBorrowType != null) {
            HjhAssetBorrowTypeVO hjhAssetBorrowTypeVO = new HjhAssetBorrowTypeVO();
            BeanUtils.copyProperties(hjhAssetBorrowType, hjhAssetBorrowTypeVO);
            response.setResult(hjhAssetBorrowTypeVO);
        }
        return response;
    }

    /**
     * 根据项目类型去还款方式
     * @param borrowcCd
     * @return
     */
    @GetMapping("/selectProjectRepay/{borrowcCd}")
    public BorrowProjectRepayReponse selectProjectRepay(String borrowcCd) {
        BorrowProjectRepayReponse response = new BorrowProjectRepayReponse();
        List<BorrowProjectRepay> repayList = assetPushService.selectProjectRepay(borrowcCd);
        if (!CollectionUtils.isEmpty(repayList)) {
            List<BorrowProjectRepayVO> repayVOList = CommonUtils.convertBeanList(repayList, BorrowProjectRepayVO.class);
            response.setResultList(repayVOList);
        }
        return response;
    }

    /**
     * 获取受托支付电子账户列表
     * @param instCode
     * @param entrustedAccountId
     * @return
     */
    @GetMapping("/selectStzfWhiteList/{instCode}/{entrustedAccountId}")
    public STZHWhiteListResponse selectStzfWhiteList(String instCode, String entrustedAccountId) {
        STZHWhiteListResponse response = new STZHWhiteListResponse();
        STZHWhiteList stzhWhiteList =  assetPushService.selectStzfWhiteList(instCode, entrustedAccountId);
        if (stzhWhiteList != null) {
            STZHWhiteListVO stzhWhiteListVO = new STZHWhiteListVO();
            BeanUtils.copyProperties(stzhWhiteList, stzhWhiteListVO);
            response.setResult(stzhWhiteListVO);
        }
        return response;
    }

    /**
     * 插入资产表
     * @param hjhPlanAssetVO
     * @return
     */
    @PostMapping("/insertAssert")
    public int insertAssert(@RequestBody HjhPlanAssetVO hjhPlanAssetVO) {
        int result = 0;
        if (hjhPlanAssetVO != null) {
            HjhPlanAsset hjhPlanAsset = new HjhPlanAsset();
            BeanUtils.copyProperties(hjhPlanAssetVO, hjhPlanAsset);
            result =  assetPushService.insertAssert(hjhPlanAsset);
        }
        return result;
    }

    /**
     * 插入资产表
     * @param infobeans
     */
    @PostMapping("/insertRiskInfo")
    public void insertRiskInfo(@RequestBody List<InfoBean> infobeans) {
        assetPushService.insertRiskInfo(infobeans);
    }

}
