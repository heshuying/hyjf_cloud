package com.hyjf.am.trade.service.impl.task.issuerecover;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.mapper.customize.trade.HjhPlanCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.task.issuerecover.AutoIssueMessageService;
import com.hyjf.am.vo.task.issuerecover.BorrowWithBLOBs;
import com.hyjf.common.bean.RedisBorrow;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/12 11:10
 * 关联计划消息
 * @Description: AutoIssueMessageServiceImpl
 */
@Service
public class AutoIssueMessageServiceImpl implements AutoIssueMessageService {
    private static final Logger logger = LoggerFactory.getLogger(AutoIssueMessageServiceImpl.class);
    public static JedisPool pool = RedisUtils.getPool();
    @Resource
    private HjhPlanAssetMapper hjhPlanAssetMapper;
    @Resource
    private HjhLabelMapper hjhLabelMapper;
    @Resource
    private HjhAllocationEngineMapper hjhAllocationEngineMapper;
    @Resource
    private BorrowMapper borrowMapper;
    @Resource
    private HjhPlanCustomizeMapper hjhPlanCustomizeMapper;
    @Resource
    private HjhDebtCreditMapper hjhDebtCreditMapper;
    @Override
    public HjhPlanAsset selectPlanAssetByBorrowNid(String borrowNid, String instCode) {
        HjhPlanAsset resultAsset = null;
        HjhPlanAssetExample example = new HjhPlanAssetExample();
        HjhPlanAssetExample.Criteria crt = example.createCriteria();
        crt.andBorrowNidEqualTo(borrowNid);
        crt.andInstCodeEqualTo(instCode);

        List<HjhPlanAsset> list = this.hjhPlanAssetMapper.selectByExample(example);

        if(list != null && list.size() > 0){
            resultAsset = list.get(0);
        }

        return resultAsset;
    }

    @Override
    public HjhLabel getLabelId(BorrowInfo borrowInfo,Borrow borrow, HjhPlanAsset hjhPlanAsset) {
        HjhLabel resultLabel = null;

        HjhLabelExample example = new HjhLabelExample();
        HjhLabelExample.Criteria cra = example.createCriteria();

        cra.andDelFlagEqualTo(0);
        cra.andLabelStateEqualTo(1);
        cra.andBorrowStyleEqualTo(borrow.getBorrowStyle());
        cra.andIsCreditEqualTo(0); // 原始标
        cra.andIsLateEqualTo(0); // 是否逾期
        example.setOrderByClause(" update_time desc ");

        List<HjhLabel> list = this.hjhLabelMapper.selectByExample(example);
        if (list != null && list.size() <= 0) {
            logger.info(borrow.getBorrowStyle()+" 该原始标还款方式 没有一个标签");
            return resultLabel;
        }
        // continue过滤输入了但是不匹配的标签，如果找到就是第一个
        for (HjhLabel hjhLabel : list) {
            // 标的期限
//			int score = 0;
            if(hjhLabel.getLabelTermEnd() != null && hjhLabel.getLabelTermEnd().intValue()>0 && hjhLabel.getLabelTermStart()!=null
                    && hjhLabel.getLabelTermStart().intValue()>0){
                if(borrow.getBorrowPeriod() >= hjhLabel.getLabelTermStart() && borrow.getBorrowPeriod() <= hjhLabel.getLabelTermEnd()){
//					score = score+1;
                }else{
                    continue;
                }
            }else if ((hjhLabel.getLabelTermEnd() != null && hjhLabel.getLabelTermEnd().intValue()>0) ||
                    (hjhLabel.getLabelTermStart()!=null && hjhLabel.getLabelTermStart().intValue()>0)) {
                if(borrow.getBorrowPeriod().equals(hjhLabel.getLabelTermStart()) || borrow.getBorrowPeriod().equals(hjhLabel.getLabelTermEnd())){
//					score = score+1;
                }else{
                    continue;
                }
            }else{
                continue;
            }
            // 标的实际利率
            if(hjhLabel.getLabelAprStart() != null && hjhLabel.getLabelAprStart().compareTo(BigDecimal.ZERO)>0 &&
                    hjhLabel.getLabelAprEnd()!=null && hjhLabel.getLabelAprEnd().compareTo(BigDecimal.ZERO)>0){
                if(borrow.getBorrowApr().compareTo(hjhLabel.getLabelAprStart())>=0 && borrow.getBorrowApr().compareTo(hjhLabel.getLabelAprEnd())<=0){
//					score = score+1;
                }else{
                    continue;
                }
            }else if (hjhLabel.getLabelAprStart() != null && hjhLabel.getLabelAprStart().compareTo(BigDecimal.ZERO)>0) {
                if(borrow.getBorrowApr().compareTo(hjhLabel.getLabelAprStart())==0 ){
//					score = score+1;
                }else{
                    continue;
                }

            }else if (hjhLabel.getLabelAprEnd()!=null && hjhLabel.getLabelAprEnd().compareTo(BigDecimal.ZERO)>0 ) {
                if(borrow.getBorrowApr().compareTo(hjhLabel.getLabelAprEnd())==0){
//					score = score+1;
                }else {
                    continue;
                }
            }
            // 标的实际支付金额
            if(hjhLabel.getLabelPaymentAccountStart() != null && hjhLabel.getLabelPaymentAccountStart().compareTo(BigDecimal.ZERO)>0 &&
                    hjhLabel.getLabelPaymentAccountEnd()!=null && hjhLabel.getLabelPaymentAccountEnd().compareTo(BigDecimal.ZERO)>0){
                if(borrow.getAccount().compareTo(hjhLabel.getLabelPaymentAccountStart())>=0 && borrow.getAccount().compareTo(hjhLabel.getLabelPaymentAccountEnd())<=0){
//					score = score+1;
                }else{
                    continue;
                }
            }else if (hjhLabel.getLabelPaymentAccountStart() != null && hjhLabel.getLabelPaymentAccountStart().compareTo(BigDecimal.ZERO)>0) {
                if(borrow.getAccount().compareTo(hjhLabel.getLabelPaymentAccountStart())==0 ){
//					score = score+1;
                }else{
                    continue;
                }

            }else if (hjhLabel.getLabelPaymentAccountEnd()!=null && hjhLabel.getLabelPaymentAccountEnd().compareTo(BigDecimal.ZERO)>0 ) {
                if(borrow.getAccount().compareTo(hjhLabel.getLabelPaymentAccountEnd())==0){
//					score = score+1;
                }else{
                    continue;
                }
            }
            // 资产来源
            if(StringUtils.isNotBlank(hjhLabel.getInstCode())){
                if(hjhLabel.getInstCode().equals(borrowInfo.getInstCode())){
//					score = score+1;
                }else{
                    continue;
                }
            }
            // 产品类型
            if(hjhLabel.getAssetType() != null && hjhLabel.getAssetType().intValue() >= 0){
                if(hjhLabel.getAssetType().equals(borrowInfo.getAssetType())){
                    ;
                }else{
                    continue;
                }
            }
            // 项目类型
            if(hjhLabel.getProjectType() != null && hjhLabel.getProjectType().intValue() >= 0){
                if(hjhLabel.getProjectType().equals(borrowInfo.getProjectType())){
                    ;
                }else{
                    continue;
                }
            }

            // 推送时间节点
            if(hjhPlanAsset != null && hjhPlanAsset.getRecieveTime() != null && hjhPlanAsset.getRecieveTime().intValue() > 0){
                Date reciveDate = GetDate.getDate(hjhPlanAsset.getRecieveTime());

                if(hjhLabel.getPushTimeStart() != null && hjhLabel.getPushTimeEnd()!=null){
                    if(reciveDate.getTime() >= hjhLabel.getPushTimeStart().getTime() &&
                            reciveDate.getTime() <= hjhLabel.getPushTimeEnd().getTime()){
//						score = score+1;
                    }else{
                        continue;
                    }
                }else if (hjhLabel.getPushTimeStart() != null) {
                    if(reciveDate.getTime() == hjhLabel.getPushTimeStart().getTime() ){
//						score = score+1;
                    }else{
                        continue;
                    }

                }else if (hjhLabel.getPushTimeEnd()!=null) {
                    if(reciveDate.getTime() == hjhLabel.getPushTimeEnd().getTime() ){
//						score = score+1;
                    }else{
                        continue;
                    }
                }

            }

            // 如果找到返回最近的一个
            return hjhLabel;

        }

        return resultLabel;
    }

    @Override
    public String getPlanNid(Integer labelId) {
        HjhAllocationEngineExample example = new HjhAllocationEngineExample();
        HjhAllocationEngineExample.Criteria cra = example.createCriteria();

        cra.andDelFlagEqualTo(0);
        cra.andLabelIdEqualTo(labelId);
        cra.andConfigStatusEqualTo(1);
        cra.andLabelStatusEqualTo(1);
        List<HjhAllocationEngine> list = this.hjhAllocationEngineMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0).getPlanNid();
        }

        return null;
    }

    @Override
    public boolean updateIssueBorrow(BorrowInfo borrowInfo, Borrow borrow, String planNid, HjhPlanAsset hjhPlanAsset) {
        // 关联计划
        Borrow updateBloBs = new Borrow();
        updateBloBs.setId(borrow.getId());

        // 是否可以进行借款
        updateBloBs.setPlanNid(planNid);
        updateBloBs.setLabelId(borrow.getLabelId());

        // 更新时间
        int systemNowDateLong = GetDate.getNowTime10();
        Date systemNowDate = GetDate.getDate(systemNowDateLong);
        updateBloBs.setUpdatetime(systemNowDate);

        this.borrowMapper.updateByPrimaryKeySelective(updateBloBs);


        //增加redis相应计划可投金额
        //更新汇计划表
        HjhPlan hjhPlan = new HjhPlan();
        hjhPlan.setPlanNid(planNid);
        hjhPlan.setAvailableInvestAccount(borrow.getAccount());
        this.hjhPlanCustomizeMapper.updatePlanAccount(hjhPlan);
        logger.info(borrow.getBorrowNid()+" 成功更新计划池"+planNid+"总额 + "+borrow.getAccount());

        // 更新资产表
        if(hjhPlanAsset != null){
            HjhPlanAsset hjhPlanAssetnew = new HjhPlanAsset();
            hjhPlanAssetnew.setId(hjhPlanAsset.getId());
            hjhPlanAssetnew.setPlanNid(planNid);
            //获取当前时间
            hjhPlanAssetnew.setUpdateTime(new Date());
            hjhPlanAssetnew.setUpdateUserId(1);
            hjhPlanAssetMapper.updateByPrimaryKey(hjhPlanAssetnew);
        }


        redisAdd(RedisConstants.HJH_PLAN+planNid,borrow.getAccount().toString());//增加redis相应计划可投金额
        if (!CustomConstants.INST_CODE_HYJF.equals(borrowInfo.getInstCode())) {
            redisSubstrack(RedisConstants.CAPITAL_TOPLIMIT_+borrowInfo.getInstCode(),borrow.getAccount().toString());//减少风险保证金可投金额
        }
        RedisBorrow redisBorrow = new RedisBorrow();
        redisBorrow.setBorrowNid(borrow.getBorrowNid());
        redisBorrow.setBorrowAccountWait(borrow.getAccount());

        RedisUtils.leftpush(RedisConstants.HJH_PLAN_LIST+RedisConstants.HJH_BORROW_INVEST+RedisConstants.HJH_SLASH+planNid, JSON.toJSONString(redisBorrow));//redis相应计划


        logger.info(borrow.getBorrowNid()+" 计划编号："+planNid+" 关联计划成功");

        return true;
    }

    @Override
    public HjhDebtCredit getCreditByNid(String creditNid) {
        HjhDebtCreditExample example = new HjhDebtCreditExample();
        HjhDebtCreditExample.Criteria criteria = example.createCriteria();
        criteria.andCreditNidEqualTo(creditNid);
        List<HjhDebtCredit> list = this.hjhDebtCreditMapper.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public HjhLabel getLabelId(HjhDebtCredit credit) {
        HjhLabel resultLabel = null;

        HjhLabelExample example = new HjhLabelExample();
        HjhLabelExample.Criteria cra = example.createCriteria();

        cra.andDelFlagEqualTo(0);
        cra.andLabelStateEqualTo(1);
        cra.andBorrowStyleEqualTo(credit.getBorrowStyle());
        cra.andIsCreditEqualTo(1); // 债转标
        example.setOrderByClause(" update_time desc ");
        List<HjhLabel> list = this.hjhLabelMapper.selectByExample(example);

        if (list != null && list.size() <= 0) {
            logger.info(credit.getBorrowStyle()+" 该债转还款方式 没有一个标签");
            return resultLabel;
        }

        // continue过滤输入了但是不匹配的标签，如果找到就是第一个
        for (HjhLabel hjhLabel : list) {

            // 标的是否逾期 ,此为必须字段
            if(hjhLabel.getIsLate()!= null && hjhLabel.getIsLate().intValue()==1){
                if(credit.getIsLateCredit()!=null && credit.getIsLateCredit() == 1 ){
                    ;
                }else{
                    continue;
                }
            }else if(hjhLabel.getIsLate()!= null && hjhLabel.getIsLate().intValue()==0){
                if(credit.getIsLateCredit()!=null && credit.getIsLateCredit() == 0 ){
                    ;
                }else{
                    continue;
                }
            }
            // 标的期限
//			int score = 0;
            if(hjhLabel.getLabelTermEnd() != null && hjhLabel.getLabelTermEnd().intValue()>0 && hjhLabel.getLabelTermStart()!=null
                    && hjhLabel.getLabelTermStart().intValue()>0){
                if(credit.getBorrowPeriod() >= hjhLabel.getLabelTermStart() && credit.getBorrowPeriod() <= hjhLabel.getLabelTermEnd()){
//					score = score+1;
                }else{
                    continue;
                }
            }else if ((hjhLabel.getLabelTermEnd() != null && hjhLabel.getLabelTermEnd().intValue()>0) ||
                    (hjhLabel.getLabelTermStart()!=null && hjhLabel.getLabelTermStart().intValue()>0)) {
                if(credit.getBorrowPeriod().equals(hjhLabel.getLabelTermStart()) || credit.getBorrowPeriod().equals(hjhLabel.getLabelTermEnd())){
//					score = score+1;
                }else{
                    continue;
                }
            }
            // 标的实际利率
            if(hjhLabel.getLabelAprStart() != null && hjhLabel.getLabelAprStart().compareTo(BigDecimal.ZERO)>0 &&
                    hjhLabel.getLabelAprEnd()!=null && hjhLabel.getLabelAprEnd().compareTo(BigDecimal.ZERO)>0){
                if(credit.getActualApr().compareTo(hjhLabel.getLabelAprStart())>=0 && credit.getActualApr().compareTo(hjhLabel.getLabelAprEnd())<=0){
                    ;
                }else{
                    continue;
                }
            }else if (hjhLabel.getLabelAprStart() != null && hjhLabel.getLabelAprStart().compareTo(BigDecimal.ZERO)>0) {
                if(credit.getActualApr().compareTo(hjhLabel.getLabelAprStart())==0 ){
//					score = score+1;
                }else{
                    continue;
                }

            }else if (hjhLabel.getLabelAprEnd()!=null && hjhLabel.getLabelAprEnd().compareTo(BigDecimal.ZERO)>0 ) {
                if(credit.getActualApr().compareTo(hjhLabel.getLabelAprEnd())==0){
                    ;
                }else{
                    continue;
                }
            }
            // 标的实际支付金额
            if(hjhLabel.getLabelPaymentAccountStart() != null && hjhLabel.getLabelPaymentAccountStart().compareTo(BigDecimal.ZERO)>0 &&
                    hjhLabel.getLabelPaymentAccountEnd()!=null && hjhLabel.getLabelPaymentAccountEnd().compareTo(BigDecimal.ZERO)>0){
                if(credit.getLiquidationFairValue().compareTo(hjhLabel.getLabelPaymentAccountStart())>=0 && credit.getLiquidationFairValue().compareTo(hjhLabel.getLabelPaymentAccountEnd())<=0){
                    ;
                }else{
                    continue;
                }
            }else if (hjhLabel.getLabelPaymentAccountStart() != null && hjhLabel.getLabelPaymentAccountStart().compareTo(BigDecimal.ZERO)>0) {
                if(credit.getLiquidationFairValue().compareTo(hjhLabel.getLabelPaymentAccountStart())==0 ){
                    ;
                }else{
                    continue;
                }

            }else if (hjhLabel.getLabelPaymentAccountEnd()!=null && hjhLabel.getLabelPaymentAccountEnd().compareTo(BigDecimal.ZERO)>0 ) {
                if(credit.getLiquidationFairValue().compareTo(hjhLabel.getLabelPaymentAccountEnd())==0){
                    ;
                }else{
                    continue;
                }
            }
            // 资产来源
            if(StringUtils.isNotBlank(hjhLabel.getInstCode())){
                if(hjhLabel.getInstCode().equals(credit.getInstCode())){
//					score = score+1;
                }else{
                    continue;
                }
            }
            // 产品类型
            if(hjhLabel.getAssetType() != null && hjhLabel.getAssetType().intValue() >= 0){
                if(hjhLabel.getAssetType().equals(credit.getAssetType())){
                    ;
                }else{
                    continue;
                }
            }
            // 项目类型
            if(hjhLabel.getProjectType() != null && hjhLabel.getProjectType().intValue() >= 0){
                if(hjhLabel.getProjectType().equals(credit.getProjectType())){
                    ;
                }else{
                    continue;
                }
            }

            // 剩余天数
            if(hjhLabel.getRemainingDaysEnd() != null && hjhLabel.getRemainingDaysEnd().intValue()>=0 && hjhLabel.getRemainingDaysStart()!=null
                    && hjhLabel.getRemainingDaysStart().intValue()>=0){
                if(credit.getRemainDays() != null && credit.getRemainDays() >= hjhLabel.getRemainingDaysStart() && credit.getRemainDays() <= hjhLabel.getRemainingDaysEnd()){
                    ;
                }else{
                    continue;
                }
            }else if ((hjhLabel.getRemainingDaysEnd() != null && hjhLabel.getRemainingDaysEnd().intValue()>=0) ||
                    (hjhLabel.getRemainingDaysStart()!=null && hjhLabel.getRemainingDaysStart().intValue()>=0)) {
                if(credit.getRemainDays() != null && credit.getRemainDays().equals(hjhLabel.getRemainingDaysStart()) || credit.getRemainDays().equals(hjhLabel.getRemainingDaysEnd())){
                    ;
                }else{
                    continue;
                }
            }

            // 找出即为最新的标签
            return hjhLabel;

        }

        return resultLabel;
    }

    @Override
    public boolean updateIssueCredit(HjhDebtCredit credit, String planNid) {
        // 关联计划
        HjhDebtCredit updateBloBs = new HjhDebtCredit();
        updateBloBs.setId(credit.getId());
        updateBloBs.setPlanNidNew(planNid);
        updateBloBs.setLabelId(credit.getLabelId());
        updateBloBs.setLabelName(credit.getLabelName());
        // 更新时间
        updateBloBs.setUpdateTime(new Date());
        this.hjhDebtCreditMapper.updateByPrimaryKeySelective(updateBloBs);


        //增加redis相应计划可投金额
        //更新汇计划表
        HjhPlan hjhPlan = new HjhPlan();
        hjhPlan.setPlanNid(planNid);
        hjhPlan.setAvailableInvestAccount(credit.getLiquidationFairValue());
//		hjhPlan.setJoinTotal(credit.getLiquidationFairValue());
        this.hjhPlanCustomizeMapper.updatePlanAccount(hjhPlan);
        logger.info(credit.getCreditNid()+" 成功更新计划池"+planNid+"总额 + "+credit.getLiquidationFairValue());

        redisAdd(RedisConstants.HJH_PLAN+planNid,credit.getLiquidationFairValue().toString());//增加redis相应计划可投金额
//		redisSubstrack(RedisConstants.CAPITAL_TOPLIMIT_+credit.getInstCode(),credit.getLiquidationFairValue().toString());//减少风险保证金可投金额//TODO:待确认

        RedisBorrow redisBorrow = new RedisBorrow();
        redisBorrow.setBorrowNid(credit.getCreditNid());
        redisBorrow.setBorrowAccountWait(credit.getLiquidationFairValue());

        RedisUtils.leftpush(RedisConstants.HJH_PLAN_LIST+RedisConstants.HJH_BORROW_CREDIT+RedisConstants.HJH_SLASH+planNid, JSON.toJSONString(redisBorrow));//redis相应计划


        logger.info(credit.getCreditNid()+" 计划编号："+planNid+" 关联计划成功");

        return true;
    }

    /**
     * 并发情况下保证设置一个值
     * @param key
     * @param value
     */
    private boolean redisSubstrack(String key,String value){

        Jedis jedis = pool.getResource();
        boolean result = false;

        while ("OK".equals(jedis.watch(key))) {
            List<Object> results = null;

            String balance = jedis.get(key);
            BigDecimal bal = new BigDecimal(balance);
            BigDecimal val = new BigDecimal(value);

            if(val.compareTo(bal)>0){
                return false;
            }

            Transaction tx = jedis.multi();
            String valbeset = bal.subtract(val).toString();
            tx.set(key, valbeset);
            results = tx.exec();
            if (results == null || results.isEmpty()) {
                jedis.unwatch();
            } else {
                String ret = (String) results.get(0);
                if (ret != null && "OK".equals(ret)) {
                    // 成功后
                    result = true;
                    break;
                } else {
                    jedis.unwatch();
                }
            }
        }

        return result;
    }

    /**
     * 并发情况下保证设置一个值
     * @param key
     * @param value
     */
    private void redisAdd(String key,String value){

        Jedis jedis = pool.getResource();

        while ("OK".equals(jedis.watch(key))) {
            List<Object> results = null;

            String balance = jedis.get(key);
            BigDecimal bal = new BigDecimal(0);
            if (balance != null) {
                bal =  new BigDecimal(balance);
            }
            BigDecimal val =  new BigDecimal(value);

            Transaction tx = jedis.multi();
            String valbeset = bal.add(val).toString();
            tx.set(key, valbeset);
            results = tx.exec();
            if (results == null || results.isEmpty()) {
                jedis.unwatch();
            } else {
                String ret = (String) results.get(0);
                if (ret != null && "OK".equals(ret)) {
                    // 成功后
                    break;
                } else {
                    jedis.unwatch();
                }
            }
        }
    }
}
