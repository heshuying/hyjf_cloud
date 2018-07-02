/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.resquest.assetpush.InfoBean;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.AssetPushService;
import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectRepayVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
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
@RequestMapping("/am-trade/assetPush")
public class AssetPushController extends BaseController {

    @Autowired
    private AssetPushService assetPushService;

    /**
     * 获取机构信息
     * @param instCode
     * @param assetType
     * @return
     */
    @GetMapping("/selectAssetBorrowType/{instCode}/{assetType}")
    public HjhAssetBorrowTypeResponse selectAssetBorrowType(@PathVariable String instCode, @PathVariable int assetType) {
        HjhAssetBorrowTypeResponse response = new HjhAssetBorrowTypeResponse();
        HjhAssetBorrowtype hjhAssetBorrowType = assetPushService.selectAssetBorrowType(instCode, assetType);
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
    public BorrowProjectRepayReponse selectProjectRepay(@PathVariable String borrowcCd) {
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
    public STZHWhiteListResponse selectStzfWhiteList(@PathVariable String instCode, @PathVariable String entrustedAccountId) {
        STZHWhiteListResponse response = new STZHWhiteListResponse();
        StzhWhiteList stzhWhiteList =  assetPushService.selectStzfWhiteList(instCode, entrustedAccountId);
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

    /**
     * 查询资产表
     * @param assetId
     * @param instCode
     * @return
     */
    @RequestMapping("/selectPlanAsset/{assetId}/{isntCode}")
    public HjhPlanAssetResponse selectPlanAsset(@PathVariable String assetId, @PathVariable String instCode) {
        HjhPlanAssetResponse response = new HjhPlanAssetResponse();
        HjhPlanAsset hjhPlanAsset = assetPushService.selectPlanAsset(assetId, instCode);
        if (hjhPlanAsset != null) {
            HjhPlanAssetVO hjhPlanAssetVO = new HjhPlanAssetVO();
            BeanUtils.copyProperties(hjhPlanAsset, hjhPlanAssetVO);
            response.setResult(hjhPlanAssetVO);
        }
        return response;
    }

    /**
     * 更新资产表
     * @param planAssetVO
     */
    @RequestMapping("/updatePlanAsset")
    public void updatePlanAsset(@RequestBody HjhPlanAssetVO planAssetVO) {
        assetPushService.updatePlanAsset(planAssetVO);
    }

    /**
     * 获取项目类型
     * @param borrowCd
     * @return
     */
    @RequestMapping("/selectBorrowProjectByBorrowCd/{borrowCd}")
    public BorrowProjectTypeResponse selectBorrowProjectByBorrowCd(@PathVariable String borrowCd) {
        BorrowProjectTypeResponse response = new BorrowProjectTypeResponse();
        List<BorrowProjectType> borrowProjectTypeList = assetPushService.selectBorrowProjectByBorrowCd(borrowCd);
        if (!CollectionUtils.isEmpty(borrowProjectTypeList)) {
            List<BorrowProjectTypeVO> voList = CommonUtils.convertBeanList(borrowProjectTypeList, BorrowProjectTypeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 更新资产表
     * @param hjhPlanAssetVO
     * @return
     */
    public int updateHjhPlanAssetnew(HjhPlanAssetVO hjhPlanAssetVO) {
        if (hjhPlanAssetVO != null) {
            HjhPlanAsset hjhPlanAsset = new HjhPlanAsset();
            BeanUtils.copyProperties(hjhPlanAssetVO, hjhPlanAsset);
           return assetPushService.updateHjhPlanAssetnew(hjhPlanAsset);
        }
        return 0;
    }
}
