/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.asset.impl;

import com.hyjf.am.resquest.assetpush.InfoBean;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.asset.AssetPushService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author fuqiang
 * @version AssetPushServiceImpl, v0.1 2018/6/12 10:07
 */
@Service
public class AssetPushServiceImpl extends BaseServiceImpl implements AssetPushService {

    @Override
    public HjhAssetBorrowtype selectAssetBorrowType(String instCode, int assetType) {
        HjhAssetBorrowtypeExample example = new HjhAssetBorrowtypeExample();
        HjhAssetBorrowtypeExample.Criteria crt = example.createCriteria();
        crt.andInstCodeEqualTo(instCode);
        crt.andAssetTypeEqualTo(assetType);
        List<HjhAssetBorrowtype> list = this.hjhAssetBorrowtypeMapper.selectByExample(example);
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
        crt.andBorrowCdEqualTo(Integer.valueOf(borrowcCd));
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
        crt.andStAccountIdEqualTo(entrustedAccountId);
        crt.andInstCodeEqualTo(instCode);
        crt.andDelFlagEqualTo(0);
        crt.andStateEqualTo(1);
        List<StzhWhiteList> list = this.stzhWhiteListMapper.selectByExample(example);
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
            HjhAssetRiskInfo riskInfo = new HjhAssetRiskInfo();

            riskInfo.setCreateUserId(1);// 默认系统用户
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
                    logger.error("----------------------商家信息更新失败,资产编号：" + infobean.getAssetId() +"-----------------------------");
                }
            } else {
                if (this.hjhAssetRiskInfoMapper.insertSelective(riskInfo) > 0 ? false : true) {
                    logger.error("----------------------商家信息插入失败,资产编号：" + infobean.getAssetId() +"-----------------------------");
                }
            }
        }
    }

    @Override
    public void updatePlanAsset(HjhPlanAssetVO planAssetVO) {
        HjhPlanAsset planAsset = new HjhPlanAsset();
        planAsset.setId(planAssetVO.getId());
        planAsset.setStatus(1);//待补缴保证金
        this.hjhPlanAssetMapper.updateByPrimaryKeySelective(planAsset);
    }

    @Override
    public List<BorrowProjectType> selectBorrowProjectByBorrowCd(Integer borrowCd) {
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

    /**
     * 检查是否存在重复资产
     * @param assetId
     * @return
     */
    @Override
    public List<HjhPlanAsset> checkDuplicateAssetId(String assetId) {
        HjhPlanAssetExample example = new HjhPlanAssetExample();
        HjhPlanAssetExample.Criteria crt = example.createCriteria();
        crt.andAssetIdEqualTo(assetId);
        List<HjhPlanAsset> list = hjhPlanAssetMapper.selectByExample(example);
        if(list.size() > 0){
            return list;
        }else{
            return null;
        }
    }

    /**
     * 录标时添加企业资产
     *
     * @param borrowUser
     * @return
     */
    @Override
    public int insertCompanyInfoToBorrowUsers(BorrowUser borrowUser) {
        return borrowUserMapper.insertSelective(borrowUser);
    }

    /**
     * 检查是否交过保证金 add by liushouyi
     *
     * @param borrowNid
     * @return
     */
    @Override
    public List<BorrowBail> selectBorrowBail(String borrowNid) {
        BorrowBailExample example = new BorrowBailExample();
        example.createCriteria().andBorrowNidEqualTo(borrowNid);
        List<BorrowBail> borrowBailList = this.borrowBailMapper.selectByExample(example);
        if(null != borrowBailList && borrowBailList.size() > 0 ) {
            return borrowBailList;
        }
        return null;
    }

    /**
     * 更新借款表 add by liushouyi
     *
     * @param borrow
     * @return
     */
    @Override
    public Integer updateBorrowByBorrowNid(Borrow borrow) {
        BorrowExample example = new BorrowExample();
        example.createCriteria().andBorrowNidEqualTo(borrow.getBorrowNid());
        return this.borrowMapper.updateByExampleSelective(borrow,example);
    }

    /**
     * 插入保证金 add by liushouyi
     *
     * @param borrowBail
     * @return
     */
    @Override
    public Integer insertBorrowBail(BorrowBail borrowBail) {
        return this.borrowBailMapper.insertSelective(borrowBail);
    }

    /**
     * 根据标的编号查询资产推送表
     *
     * @param borrowNid
     * @return
     */
    @Override
    public HjhPlanAsset selectHjhPlanAssetByBorrowNid(String borrowNid) {
        HjhPlanAssetExample example = new HjhPlanAssetExample();
        HjhPlanAssetExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        List<HjhPlanAsset> list = this.hjhPlanAssetMapper.selectByExample(example);
        if (list != null && list.size() != 0) {
            return list.get(0);
        }
        return null;
    }
}
