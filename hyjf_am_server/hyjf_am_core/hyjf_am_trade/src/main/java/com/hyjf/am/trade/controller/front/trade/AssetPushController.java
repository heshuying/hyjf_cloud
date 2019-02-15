/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.trade;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.resquest.assetpush.InfoBean;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.asset.AssetPushService;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.hjh.BorrowBailVO;
import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
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
        List<BorrowProjectType> borrowProjectTypeList = assetPushService.selectBorrowProjectByBorrowCd(Integer.valueOf(borrowCd));
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
    @RequestMapping("/updateHjhPlanAssetNew")
    public IntegerResponse updateHjhPlanAssetNew(@PathVariable HjhPlanAssetVO hjhPlanAssetVO) {
        Integer num = -1;
        if (hjhPlanAssetVO != null) {
            HjhPlanAsset hjhPlanAsset = new HjhPlanAsset();
            BeanUtils.copyProperties(hjhPlanAssetVO, hjhPlanAsset);
            num = assetPushService.updateHjhPlanAssetnew(hjhPlanAsset);
        }
        return new IntegerResponse(num);
    }

    /**
     * 检查是否存在重复资产
     * @param assetId
     * @return
     */
    @RequestMapping("/checkDuplicateAssetId/{assetId}")
    public HjhPlanAssetResponse checkDuplicateAssetId(@PathVariable String assetId) {
        HjhPlanAssetResponse response = new HjhPlanAssetResponse();
        List<HjhPlanAsset> hjhPlanAssetList = assetPushService.checkDuplicateAssetId(assetId);
        if (!CollectionUtils.isEmpty(hjhPlanAssetList)) {
            List<HjhPlanAssetVO> voList = CommonUtils.convertBeanList(hjhPlanAssetList, HjhPlanAssetVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 录标时添加企业资产
     *
     * @param borrowUserVO
     * @return
     */
    @PostMapping("/insertCompanyInfoToBorrowUsers")
    public int insertCompanyInfoToBorrowUsers(@RequestBody BorrowUserVO borrowUserVO) {
        int result = 0;
        if (borrowUserVO != null) {
            BorrowUser borrowUser = new BorrowUser();
            BeanUtils.copyProperties(borrowUserVO, borrowUser);
            result =  assetPushService.insertCompanyInfoToBorrowUsers(borrowUser);
        }
        return result;
    }

    /**
     * 检查是否交过保证金 add by liushouyi
     *
     * @param borrowNid
     * @return
     */
    @RequestMapping("/select_borrow_bail/{borrowNid}")
    public BorrowBailResponse selectBorrowBail(@PathVariable String borrowNid){
        BorrowBailResponse response = new BorrowBailResponse();
        List<BorrowBail> borrowBailList = this.assetPushService.selectBorrowBail(borrowNid);
        if (!CollectionUtils.isEmpty(borrowBailList)) {
            List<BorrowBailVO> voList = CommonUtils.convertBeanList(borrowBailList, BorrowBailVO.class);
            response.setResult(voList.get(0));
        }
        return response;
    }

    /**
     * 更新借款表 add by liushouyi
     *
     * @param borrowVO
     * @return
     */
    @RequestMapping("/update_borrow_by_borrow_nid")
    public IntegerResponse selectBorrowByBorrowNid(@RequestBody BorrowAndInfoVO borrowVO){
        Integer result = -1;
        if (borrowVO != null){
            Borrow borrow = new Borrow();
            BeanUtils.copyProperties(borrowVO, borrow);
            result = this.assetPushService.updateBorrowByBorrowNid(borrow);
        }
        return new IntegerResponse(result);
    }

    /**
     * 更新borrow对象
     * @author zhangyk
     * @date 2018/9/13 17:36
     */
    @RequestMapping("/updateRightBorrowByBorrowNid")
    public IntegerResponse updateBorrowByBorrowNid(@RequestBody RightBorrowVO rightBorrowVO){
        Integer result = -1;
        if (rightBorrowVO != null){
            Borrow borrow = new Borrow();
            BeanUtils.copyProperties(rightBorrowVO, borrow);
            result = this.assetPushService.updateBorrowByBorrowNid(borrow);
        }
        return new IntegerResponse(result);
    }

    /**
     * 配置表获取保证金比率 add by liushouyi
     *
     * @param configCd
     * @return
     */
    @RequestMapping("/select_borrow_config/{configCd}")
    public StringResponse selectBorrowConfig(@PathVariable String configCd){
        String result =  this.assetPushService.selectBorrowConfig(configCd);
        return new StringResponse(result);
    }

    /**
     * 插入保证金 add by liushouyi
     *
     * @param borrowBailVO
     * @return
     */
    @RequestMapping("/insert_borrow_bail")
    public IntegerResponse insertBorrowBail(@RequestBody BorrowBailVO borrowBailVO){
        Integer result = -1;
        if (borrowBailVO != null){
            BorrowBail borrowBail = new BorrowBail();
            BeanUtils.copyProperties(borrowBailVO, borrowBail);
            result = this.assetPushService.insertBorrowBail(borrowBail);
        }
        return new IntegerResponse(result);
    }

    /**
     *  根据标的编号查询资产推送表
     *
     * @param borrowNid
     * @return
     */
    @RequestMapping("/selectHjhPlanAssetByBorrowNid/{borrowNid}")
    public HjhPlanAssetResponse selectHjhPlanAssetByBorrowNid(@PathVariable String borrowNid) {
        HjhPlanAssetResponse response = new HjhPlanAssetResponse();
        HjhPlanAsset hjhPlanAsset = assetPushService.selectHjhPlanAssetByBorrowNid(borrowNid);
        if (hjhPlanAsset != null) {
            HjhPlanAssetVO hjhPlanAssetVO = new HjhPlanAssetVO();
            BeanUtils.copyProperties(hjhPlanAsset, hjhPlanAssetVO);
            response.setResult(hjhPlanAssetVO);
        }
        return response;
    }

}
