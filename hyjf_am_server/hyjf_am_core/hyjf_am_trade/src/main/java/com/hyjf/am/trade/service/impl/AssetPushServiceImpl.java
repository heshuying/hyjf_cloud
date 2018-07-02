/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.resquest.assetpush.InfoBean;
import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.AssetPushService;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;

/**
 * @author fuqiang
 * @version AssetPushServiceImpl, v0.1 2018/6/12 10:07
 */
@Service
public class AssetPushServiceImpl extends BaseServiceImpl implements AssetPushService {

    private Logger _log = LoggerFactory.getLogger(AssetPushServiceImpl.class);

    @Override
    public HjhAssetBorrowtype selectAssetBorrowType(String instCode, int assetType) {
        HjhAssetBorrowtypeExample example = new HjhAssetBorrowtypeExample();
        HjhAssetBorrowtypeExample.Criteria crt = example.createCriteria();
        crt.andInstCodeEqualTo(instCode);
        crt.andAssetTypeEqualTo(assetType);
        /*List<HjhAssetBorrowtype> list = hjhAssetBorrowTypeMapper.selectByExample(example);
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }*/
        return null;
    }

    @Override
    public List<BorrowProjectRepay> selectProjectRepay(String borrowcCd) {
        BorrowProjectTypeExample example = new BorrowProjectTypeExample();
        BorrowProjectTypeExample.Criteria crt = example.createCriteria();
        crt.andBorrowCdEqualTo(borrowcCd);
        crt.andStatusEqualTo(0);
        List<BorrowProjectType> list = this.borrowProjectTypeMapper.selectByExample(example);
        String brrowClass = null;
        if(list.size() > 0){
            brrowClass = list.get(0).getBorrowClass();

            BorrowProjectRepayExample example2 = new BorrowProjectRepayExample();
            BorrowProjectRepayExample.Criteria crt2 = example2.createCriteria();
            crt2.andBorrowClassEqualTo(brrowClass);
            crt2.andDelFlagEqualTo(0);
            List<BorrowProjectRepay> list2 = this.borrowProjectRepayMapper.selectByExample(example2);

            return list2;

        }else{
            return null;
        }
    }

    @Override
    public StzhWhiteList selectStzfWhiteList(String instCode, String entrustedAccountId) {
        StzhWhiteListExample example = new StzhWhiteListExample();
        StzhWhiteListExample.Criteria crt = example.createCriteria();
        crt.andStAccountidEqualTo(entrustedAccountId);
        crt.andInstcodeEqualTo(instCode);
        crt.andDelFlagEqualTo(0);
        crt.andStateEqualTo(1);
       /* List<StzhWhiteList> list = this.sTZHWhiteListMapper.selectByExample(example);
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }*/
       return null;
    }

    @Override
    public int insertAssert(HjhPlanAsset hjhPlanAsset) {
        return this.hjhPlanAssetMapper.insertSelective(hjhPlanAsset);
    }

    @Override
    public void insertRiskInfo(List<InfoBean> infobeans) {
        for (InfoBean infobean : infobeans) {
            // todo riskinfo 迁移到mongo  data-center
//            HjhAssetRiskInfoWithBLOBs riskInfo = new HjhAssetRiskInfoWithBLOBs();
//
//            riskInfo.setCreateUser(1);// 默认系统用户
//            riskInfo.setUpdateUserId(1);
//
//            riskInfo.setAssetId(infobean.getAssetId());
//            riskInfo.setAmazonInfo(infobean.getAmazonInfo());
//            riskInfo.setEbayInfo(infobean.getEbayInfo());
//            riskInfo.setJingdongInfo(infobean.getJingdongInfo());
//            riskInfo.setTaobaoInfo(infobean.getTaobaoInfo());
//            riskInfo.setTianmaoInfo(infobean.getTianmaoInfo());
//
//            HjhAssetRiskInfoExample example = new HjhAssetRiskInfoExample();
//            HjhAssetRiskInfoExample.Criteria crt =example.createCriteria();
//            crt.andAssetIdEqualTo(riskInfo.getAssetId());
//            //已存在的assetid做更新处理
//            if (this.hjhAssetRiskInfoMapper.countByExample(example) > 0) {
//                if (this.hjhAssetRiskInfoMapper.updateByExampleSelective(riskInfo,example) > 0 ? false : true) {
//                    _log.error("----------------------商家信息更新失败,资产编号：" + infobean.getAssetId() +"-----------------------------");
//                }
//            } else {
//                if (this.hjhAssetRiskInfoMapper.insertSelective(riskInfo) > 0 ? false : true) {
//                    _log.error("----------------------商家信息插入失败,资产编号：" + infobean.getAssetId() +"-----------------------------");
//                }
//            }
        }
    }

    @Override
    public HjhPlanAsset selectPlanAsset(String assetId, String instCode) {
        HjhPlanAsset resultAsset = null;
        HjhPlanAssetExample example = new HjhPlanAssetExample();
        HjhPlanAssetExample.Criteria crt = example.createCriteria();
        crt.andAssetIdEqualTo(assetId);
        crt.andInstCodeEqualTo(instCode);

        List<HjhPlanAsset> list = this.hjhPlanAssetMapper.selectByExample(example);

        if(list != null && list.size() > 0){
            resultAsset = list.get(0);
        }

        return resultAsset;
    }

    @Override
    public void updatePlanAsset(HjhPlanAssetVO planAssetVO) {
        HjhPlanAsset planAsset = new HjhPlanAsset();
        planAsset.setId(planAssetVO.getId());
        planAsset.setStatus(1);//待补缴保证金
        this.hjhPlanAssetMapper.updateByPrimaryKeySelective(planAsset);
    }

    @Override
    public List<BorrowProjectType> selectBorrowProjectByBorrowCd(String borrowCd) {
        BorrowProjectTypeExample example = new BorrowProjectTypeExample();
        BorrowProjectTypeExample.Criteria cra = example.createCriteria();
        cra.andStatusEqualTo(0);
        cra.andBorrowCdEqualTo(borrowCd);

        List<BorrowProjectType> list = this.borrowProjectTypeMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list;
        }
        return null;
    }

    @Override
    public int updateHjhPlanAssetnew(HjhPlanAsset hjhPlanAsset) {
        return hjhPlanAssetMapper.updateByPrimaryKeySelective(hjhPlanAsset);
    }
}
