package com.hyjf.am.trade.service.task.issuerecover.impl;

import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.task.issuerecover.AutoPreAuditMessageService;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/12 09:24
 * 自动初审消息
 * @Description: AutoPreAuditMessageServiceImpl
 */
@Service
public class AutoPreAuditMessageServiceImpl extends BaseServiceImpl implements AutoPreAuditMessageService {

    @Override
    public boolean updateRecordBorrow(Borrow borrow, BorrowInfo borrowInfo) {
        //检查是否交过保证金
        String borrowNid = borrow.getBorrowNid();

        BorrowBailExample exampleBail = new BorrowBailExample();
        BorrowBailExample.Criteria craBail = exampleBail.createCriteria();
        craBail.andBorrowNidEqualTo(borrowNid);
        List<BorrowBail> borrowBailList = this.borrowBailMapper.selectByExample(exampleBail);
        // 该借款编号没有交过保证金
        if (borrowBailList == null || borrowBailList.size() == 0) {
            logger.info("该借款编号没有交过保证金 "+borrowNid);
            return false;
        }

        // 插入时间
        int systemNowDateLong = GetDate.getNowTime10();
        Date systemNowDate = GetDate.getDate(systemNowDateLong);
        if (borrow != null && borrow.getId() != null) {
            // 剩余的金额
            borrow.setBorrowAccountWait(borrow.getAccount());
            int time = systemNowDateLong;

            // 是否可以进行借款
            borrow.setBorrowStatus(1);
            // 初审时间
            borrow.setVerifyTime(GetDate.getNowTime10());
            // 发标的状态
            borrow.setVerifyStatus(Integer.valueOf(4));
            // 状态
            borrow.setStatus(2);
            // 借款到期时间
            borrow.setBorrowEndTime(String.valueOf(time + borrowInfo.getBorrowValidTime() * 86400));
            // 更新时间
            borrow.setUpdatetime(systemNowDate);
            this.borrowMapper.updateByPrimaryKey(borrow);

            return true;

        }

        return false;
    }

    @Override
    public HjhPlanAsset selectPlanAsset(String assetId, String instCode) {
        HjhPlanAsset resultAsset = null;
        HjhPlanAssetExample example = new HjhPlanAssetExample();
        HjhPlanAssetExample.Criteria crt = example.createCriteria();
        crt.andAssetIdEqualTo(assetId);
        crt.andInstCodeEqualTo(instCode);

        List<HjhPlanAsset> list = hjhPlanAssetMapper.selectByExample(example);

        if(list != null && list.size() > 0){
            resultAsset = list.get(0);
        }

        return resultAsset;
    }

    @Override
    public HjhAssetBorrowtype selectAssetBorrowType(HjhPlanAsset hjhPlanAsset) {
        HjhAssetBorrowtypeExample example = new HjhAssetBorrowtypeExample();
        HjhAssetBorrowtypeExample.Criteria cra = example.createCriteria();
        cra.andInstCodeEqualTo(hjhPlanAsset.getInstCode());
        cra.andAssetTypeEqualTo(hjhPlanAsset.getAssetType());
        cra.andIsOpenEqualTo(1);

        List<HjhAssetBorrowtype> list = this.hjhAssetBorrowTypeMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public boolean updateRecordBorrow(HjhPlanAsset hjhPlanAsset, HjhAssetBorrowtype hjhAssetBorrowType) {
//        // 验证资产风险保证金是否足够（redis）,关联汇计划才验证
//        if (!checkAssetCanSend(hjhPlanAsset)) {
//            logger.info("资产编号："+hjhPlanAsset.getAssetId()+" 保证金不足");
//            //add by pcc 20180531 增加待补缴状态
//            HjhPlanAsset planAsset = new HjhPlanAsset();
//            planAsset.setId(hjhPlanAsset.getId());
//            planAsset.setStatus(1);//待补缴保证金
//            this.hjhPlanAssetMapper.updateByPrimaryKeySelective(planAsset);
//            //end
//            return false;
//        }

        // 风险保证金，初审
        if(hjhAssetBorrowType.getAutoBail() != null && hjhAssetBorrowType.getAutoBail() == 1){
            saveBailRecord(hjhPlanAsset.getBorrowNid());
        }

        //修改发标状态 更新资产表，更新borrow
        if(hjhAssetBorrowType.getAutoAudit() != null && hjhAssetBorrowType.getAutoAudit() == 1){

            updateOntimeRecord(hjhPlanAsset,hjhAssetBorrowType);

            HjhPlanAsset hjhPlanAssetnew = new HjhPlanAsset();
            hjhPlanAssetnew.setId(hjhPlanAsset.getId());
            hjhPlanAssetnew.setStatus(7);//投资中
            //获取当前时间
            hjhPlanAssetnew.setUpdateTime(new Date());
            hjhPlanAssetnew.setUpdateUserId(1);
            boolean borrowFlag = this.hjhPlanAssetMapper.updateByPrimaryKeySelective(hjhPlanAssetnew)>0?true:false;
            if(borrowFlag){
                return true;
            }
        }
        return false;
    }

    /**
     * 发标，更新状态
     *
     * @param hjhPlanAsset
     */
    private boolean updateOntimeRecord(HjhPlanAsset hjhPlanAsset,HjhAssetBorrowtype hjhAssetBorrowType) {

        //检查是否交过保证金
        String borrowNid = hjhPlanAsset.getBorrowNid();


        BorrowBailExample exampleBail = new BorrowBailExample();
        BorrowBailExample.Criteria craBail = exampleBail.createCriteria();
        craBail.andBorrowNidEqualTo(borrowNid);
        List<BorrowBail> borrowBailList = this.borrowBailMapper.selectByExample(exampleBail);
        // 该借款编号没有交过保证金
        if (borrowBailList == null || borrowBailList.size() == 0) {
            logger.info("该借款编号没有交过保证金 "+borrowNid);
            return false;
        }

        // 插入时间
        int systemNowDateLong = GetDate.getNowTime10();
        Date systemNowDate = GetDate.getDate(systemNowDateLong);
        BorrowExample borrowExample = new BorrowExample();
        BorrowExample.Criteria borrowCra = borrowExample.createCriteria();
        borrowCra.andBorrowNidEqualTo(borrowNid);
        List<Borrow> borrowList = this.borrowMapper.selectByExample(borrowExample);
        if (borrowList != null && borrowList.size() == 1) {
            Borrow borrow = borrowList.get(0);
            BorrowInfoExample example = new BorrowInfoExample();
            example.createCriteria().andBorrowNidEqualTo(borrow.getBorrowNid());
            BorrowInfo borrowInfo = this.borrowInfoMapper.selectByExample(example).get(0);
            // 剩余的金额
            borrow.setBorrowAccountWait(borrow.getAccount());
            int time = systemNowDateLong;

            // 是否可以进行借款
            borrow.setBorrowStatus(1);
            // 初审时间
            borrow.setVerifyTime(GetDate.getNowTime10());
            // 发标的状态
            borrow.setVerifyStatus(Integer.valueOf(4));
            // 状态
            borrow.setStatus(2);
            // 借款到期时间
            borrow.setBorrowEndTime(String.valueOf(time + (borrowInfo==null?0:borrowInfo.getBorrowValidTime() * 86400)));
            // 更新时间
            borrow.setUpdatetime(systemNowDate);
            this.borrowMapper.updateByExampleSelective(borrow, borrowExample);

            return true;

        }

        return false;
    }

    /**
     * 交保证金（默认已交风险保证金）
     *
     * @param borrowPreNid
     */
    private boolean saveBailRecord(String borrowPreNid) {
        // 借款编号存在
        if (StringUtils.isNotEmpty(borrowPreNid)) {
            BorrowExample example = new BorrowExample();
            BorrowExample.Criteria cra = example.createCriteria();
            cra.andBorrowNidEqualTo(borrowPreNid);
            List<Borrow> borrowList = this.borrowMapper.selectByExample(example);
            if (borrowList != null && borrowList.size() == 1) {
                Borrow borrow = borrowList.get(0);
                // 该借款编号没有交过保证金
                BorrowBailExample exampleBail = new BorrowBailExample();
                BorrowBailExample.Criteria craBail = exampleBail.createCriteria();
                craBail.andBorrowNidEqualTo(borrow.getBorrowNid());
                List<BorrowBail> borrowBailList = this.borrowBailMapper.selectByExample(exampleBail);
                if (borrowBailList == null || borrowBailList.size() == 0) {
//					AdminSystem adminSystem = (AdminSystem) SessionUtils.getSession(CustomConstants.LOGIN_USER_INFO);
                    BorrowBail borrowBail = new BorrowBail();
                    // 借款人的ID
                    borrowBail.setBorrowUid(borrow.getUserId());
                    // 操作人的ID
                    borrowBail.setOperaterUid(1);
                    // 借款编号
                    borrowBail.setBorrowNid(borrow.getBorrowNid());
                    // 保证金数值
                    BigDecimal bailPercent = new BigDecimal(this.getBorrowConfig(CustomConstants.BORROW_BAIL_RATE));// 计算公式：保证金金额=借款金额×3％
                    BigDecimal accountBail = (borrow.getAccount()).multiply(bailPercent).setScale(2, BigDecimal.ROUND_DOWN);
                    borrowBail.setBailNum(accountBail);
                    // 10位系统时间（到秒）
                    borrowBail.setUpdateTime(new Date());
                    boolean bailFlag = this.borrowBailMapper.insertSelective(borrowBail) > 0 ? true : false;
                    if (bailFlag) {
                        borrow.setVerifyStatus(1);
                        boolean borrowFlag = this.borrowMapper.updateByPrimaryKey(borrow) > 0 ? true : false;
                        if (borrowFlag) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取系统配置
     *
     * @return
     */
    @Override
    public String getBorrowConfig(String configCd) {
        BorrowConfig borrowConfig = this.borrowConfigMapper.selectByPrimaryKey(configCd);
        return borrowConfig.getConfigValue();
    }

    /**
     * 验证资产风险保证金是否足够（redis）
     * @param hjhPlanAsset
     * @return
     */
//    private boolean checkAssetCanSend(HjhPlanAsset hjhPlanAsset) {
//        String instCode = hjhPlanAsset.getInstCode();
//
//        String capitalToplimit = RedisUtils.get(RedisConstants.CAPITAL_TOPLIMIT_+instCode);
//        BigDecimal lcapitalToplimit = new BigDecimal(capitalToplimit);
//        BigDecimal assetAcount = new BigDecimal(hjhPlanAsset.getAccount());
//
//        if (BigDecimal.ZERO.compareTo(lcapitalToplimit) >= 0) {
//            logger.info("资产编号："+hjhPlanAsset.getAssetId()+" 风险保证金小于等于零 "+capitalToplimit);
//            // 风险保证金小于等于0不能发标
//            return false;
//        }
//
//        if(assetAcount.compareTo(lcapitalToplimit) > 0){
//            logger.info("资产编号："+hjhPlanAsset.getAssetId()+" 金额： "+assetAcount+" 风险保证金小于等于零 "+capitalToplimit);
//            // 风险保证金不够不能发标
//            return false;
//        }
//
//        return true;
//    }
}
