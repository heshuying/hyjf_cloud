/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.assetpush.InfoBean;
import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.AssetPushService;
import com.hyjf.common.util.GetDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fuqiang
 * @version AssetPushServiceImpl, v0.1 2018/6/12 10:07
 */
@Service
public class AssetPushServiceImpl implements AssetPushService {

    private Logger _log = LoggerFactory.getLogger(AssetPushServiceImpl.class);

    @Autowired
    private HjhAssetBorrowTypeMapper hjhAssetBorrowTypeMapper;

    @Autowired
    private BorrowProjectTypeMapper borrowProjectTypeMapper;

    @Autowired
    private BorrowProjectRepayMapper borrowProjectRepayMapper;

    @Autowired
    private STZHWhiteListMapper sTZHWhiteListMapper;

    @Autowired
    private HjhPlanAssetMapper hjhPlanAssetMapper;

    @Autowired
    private HjhAssetRiskInfoMapper hjhAssetRiskInfoMapper;

    @Override
    public HjhAssetBorrowType selectAssetBorrowType(String instCode, int assetType) {
        HjhAssetBorrowTypeExample example = new HjhAssetBorrowTypeExample();
        HjhAssetBorrowTypeExample.Criteria crt = example.createCriteria();
        crt.andInstCodeEqualTo(instCode);
        crt.andAssetTypeEqualTo(assetType);
        List<HjhAssetBorrowType> list = hjhAssetBorrowTypeMapper.selectByExample(example);
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<BorrowProjectRepay> selectProjectRepay(String borrowcCd) {
        BorrowProjectTypeExample example = new BorrowProjectTypeExample();
        BorrowProjectTypeExample.Criteria crt = example.createCriteria();
        crt.andBorrowCdEqualTo(borrowcCd);
        crt.andStatusEqualTo("0");
        List<BorrowProjectType> list = this.borrowProjectTypeMapper.selectByExample(example);
        String brrowClass = null;
        if(list.size() > 0){
            brrowClass = list.get(0).getBorrowClass();

            BorrowProjectRepayExample example2 = new BorrowProjectRepayExample();
            BorrowProjectRepayExample.Criteria crt2 = example2.createCriteria();
            crt2.andBorrowClassEqualTo(brrowClass);
            crt2.andDelFlagEqualTo("0");
            List<BorrowProjectRepay> list2 = this.borrowProjectRepayMapper.selectByExample(example2);

            return list2;

        }else{
            return null;
        }
    }

    @Override
    public STZHWhiteList selectStzfWhiteList(String instCode, String entrustedAccountId) {
        STZHWhiteListExample example = new STZHWhiteListExample();
        STZHWhiteListExample.Criteria crt = example.createCriteria();
        crt.andStAccountidEqualTo(entrustedAccountId);
        crt.andInstcodeEqualTo(instCode);
        crt.andDelFlgEqualTo(0);
        crt.andStateEqualTo(1);
        List<STZHWhiteList> list = this.sTZHWhiteListMapper.selectByExample(example);
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public int insertAssert(HjhPlanAsset hjhPlanAsset) {
        return this.hjhPlanAssetMapper.insertSelective(hjhPlanAsset);
    }

    @Override
    public void insertRiskInfo(List<InfoBean> infobeans) {
        for (InfoBean infobean : infobeans) {
            HjhAssetRiskInfoWithBLOBs riskInfo = new HjhAssetRiskInfoWithBLOBs();

            int nowTime = GetDate.getNowTime10(); // 当前时间
            riskInfo.setCreateTime(nowTime);
            riskInfo.setUpdateTime(nowTime);
            riskInfo.setCreateUser(1);// 默认系统用户
            riskInfo.setUpdateUserId(1);

            riskInfo.setAssetId(infobean.getAssetId());
            riskInfo.setAmazonInfo(infobean.getAmazonInfo());
            riskInfo.setEbayInfo(infobean.getEbayInfo());
            riskInfo.setJingdongInfo(infobean.getJingdongInfo());
            riskInfo.setTaobaoInfo(infobean.getTaobaoInfo());
            riskInfo.setTianmaoInfo(infobean.getTianmaoInfo());

            HjhAssetRiskInfoExample example = new HjhAssetRiskInfoExample();
            HjhAssetRiskInfoExample.Criteria crt =example.createCriteria();
            crt.andAssetIdEqualTo(riskInfo.getAssetId());
            //已存在的assetid做更新处理
            if (this.hjhAssetRiskInfoMapper.countByExample(example) > 0) {
                if (this.hjhAssetRiskInfoMapper.updateByExampleSelective(riskInfo,example) > 0 ? false : true) {
                    _log.error("----------------------商家信息更新失败,资产编号：" + infobean.getAssetId() +"-----------------------------");
                }
            } else {
                if (this.hjhAssetRiskInfoMapper.insertSelective(riskInfo) > 0 ? false : true) {
                    _log.error("----------------------商家信息插入失败,资产编号：" + infobean.getAssetId() +"-----------------------------");
                }
            }
        }
    }
}
