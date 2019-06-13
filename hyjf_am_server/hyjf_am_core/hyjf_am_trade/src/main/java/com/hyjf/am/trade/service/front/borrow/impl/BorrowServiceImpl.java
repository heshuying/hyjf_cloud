/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.borrow.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.crmtender.CrmInvestMsgBean;
import com.hyjf.am.resquest.trade.BatchCenterCustomizeRequest;
import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.resquest.user.BorrowFinmanNewChargeRequest;
import com.hyjf.am.trade.bean.repay.*;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.BankAccountManageCustomize;
import com.hyjf.am.trade.dao.model.customize.BatchCenterCustomize;
import com.hyjf.am.trade.dao.model.customize.WebProjectRepayListCustomize;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.front.account.AccountService;
import com.hyjf.am.trade.service.front.borrow.BorrowService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.BorrowCustomizeVO;
import com.hyjf.am.vo.admin.WebProjectRepayListCustomizeVO;
import com.hyjf.am.vo.admin.WebUserInvestListCustomizeVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.DebtBorrowCustomizeVO;
import com.hyjf.am.vo.trade.borrow.TenderBgVO;
import com.hyjf.am.vo.trade.borrow.TenderRetMsg;
import com.hyjf.am.vo.trade.repay.WebUserRepayProjectListCustomizeVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.calculate.AccountManagementFeeUtils;
import com.hyjf.common.util.calculate.DateUtils;
import com.hyjf.common.util.calculate.UnnormalRepayUtils;
import com.hyjf.common.validator.Validator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * @author fuqiang
 * @version BorrowServiceImpl, v0.1 2018/6/13 18:53
 */
@Service
public class BorrowServiceImpl extends BaseServiceImpl implements BorrowService {
    Logger _log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommonProducer commonProducer;

    @Autowired
    private AccountService accountService;

    @Override
    public BorrowFinmanNewCharge selectBorrowApr(BorrowFinmanNewChargeRequest request) {
        BorrowFinmanNewChargeExample example = new BorrowFinmanNewChargeExample();
        BorrowFinmanNewChargeExample.Criteria cra = example.createCriteria();
        cra.andProjectTypeEqualTo(request.getBorrowClass());
        cra.andInstCodeEqualTo(request.getInstCode());
        cra.andAssetTypeEqualTo(request.getAssetType());
        cra.andManChargeTimeTypeEqualTo(request.getQueryBorrowStyle());
        cra.andManChargeTimeEqualTo(request.getBorrowPeriod());
        cra.andStatusEqualTo(0);
        List<BorrowFinmanNewCharge> list = this.borrowFinmanNewChargeMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public BorrowStyle getborrowStyleByNid(String borrowStyle) {
        BorrowStyleExample example = new BorrowStyleExample();
        BorrowStyleExample.Criteria cri = example.createCriteria();
        cri.andNidEqualTo(borrowStyle);
        List<BorrowStyle> style = borrowStyleMapper.selectByExample(example);
        return style.get(0);
    }

    @Override
    public BorrowConfig getBorrowConfigByConfigCd(String configCd) {
        BorrowConfig borrowConfig = this.borrowConfigMapper.selectByPrimaryKey(configCd);
        return borrowConfig;
    }

    @Override
    public int insertBorrow(Borrow borrow) {
        return borrowMapper.insertSelective(borrow);
    }

    @Override
    public int insertBorrowManinfo(BorrowManinfo borrowManinfo) {
         return borrowManinfoMapper.insertSelective(borrowManinfo);
    }

    @Override
    public int updateBorrowRegist(BorrowRegistRequest request) {
        BorrowAndInfoVO borrowVO = request.getBorrowVO();
        int status = request.getStatus();
        int registStatus = request.getRegistStatus();
        Date nowDate = new Date();
//		AdminSystem adminSystem = (AdminSystem) SessionUtils.getSession(CustomConstants.LOGIN_USER_INFO);
        BorrowExample example = new BorrowExample();
        example.createCriteria().andIdEqualTo(borrowVO.getId()).andStatusEqualTo(borrowVO.getStatus()).andRegistStatusEqualTo(borrowVO.getRegistStatus());
        borrowVO.setRegistStatus(registStatus);
        borrowVO.setStatus(status);
        borrowVO.setRegistUserId(1);
        borrowVO.setRegistUserName("AutoRecord");
        borrowVO.setRegistTime(nowDate);
        Borrow borrow = new Borrow();
        BeanUtils.copyProperties(borrowVO, borrow);
        return borrowMapper.updateByExampleSelective(borrow, example);
    }

    /**
     * 检索正在还款中的标的
     * @return
     */
    @Override
    public List<BorrowAndInfoVO> selectBorrowList() {
        List<BorrowAndInfoVO> list = this.borrowCustomizeMapper.selectBorrowRepayList();
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    /**
     * 获取borrowInfo
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowInfo getBorrowInfoByNid(String borrowNid) {
        BorrowInfoExample example = new BorrowInfoExample();
        BorrowInfoExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        List<BorrowInfo> list=this.borrowInfoMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 检索逾期的还款标的
     */
	@Override
	public List<Borrow> selectOverdueBorrowList() {
		BorrowExample example = new BorrowExample();
    	example.createCriteria().andRepayLastTimeLessThanOrEqualTo(GetDate.getDayEnd10(GetDate.getTodayBeforeOrAfter(-1))).andStatusEqualTo(4).andPlanNidIsNull();
    	List<Borrow> borrows = borrowMapper.selectByExample(example);
    	if(CollectionUtils.isNotEmpty(borrows)){
    		return borrows;
    	}
    	return null;
	}

    /**
     * 出借之前插入tmp表
     *
     * @param tenderRequest
     * @return
     */
    @Override
    public int insertBeforeTender(TenderRequest tenderRequest) {
        Integer userId = tenderRequest.getUserId();

        Borrow borrow = getBorrowByNid(tenderRequest.getBorrowNid());
        BorrowTenderTmp temp = new BorrowTenderTmp();
        temp.setUserId(userId);
        temp.setUserName(tenderRequest.getUserName());
        temp.setBorrowNid(tenderRequest.getBorrowNid());
        temp.setNid(tenderRequest.getOrderId());
        temp.setAccount(new BigDecimal(tenderRequest.getAccount()));
        temp.setAddIp(tenderRequest.getIp());
       /* temp.setChangeStatus(0);
        temp.setChangeUserid(0);
        temp.setChangePeriod(0);
        temp.setTenderStatus(0);
        temp.setTenderNid(borrowNid);
        temp.setTenderAwardAccount(new BigDecimal(0));*/
        temp.setRecoverFullStatus(0);
        temp.setRecoverFee(new BigDecimal(0));
        /*temp.setRecoverType("");*/
        temp.setRecoverAdvanceFee(new BigDecimal(0));
        temp.setRecoverLateFee(new BigDecimal(0));
        /*temp.setTenderAwardFee(new BigDecimal(0));
        temp.setContents("");
        temp.setAutoStatus(0);
        temp.setWebStatus(0);
        temp.setPeriodStatus(0);
        temp.setWeb(0);*/
        temp.setIsBankTender(1);
        temp.setStatus(0);
        temp.setBorrowUserId(borrow.getUserId());
        temp.setBorrowUserName(borrow.getBorrowUserName());
        temp.setInviteUserId(0);
        Integer couponGrantId = tenderRequest.getCouponGrantId();
        if (couponGrantId==null) {
            couponGrantId = 0;
        }
        // 为出借完全掉单优惠券出借时修复做记录
        temp.setCouponGrantId(couponGrantId);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(tenderRequest.getTenderFrom())) {
            temp.setTenderFrom(tenderRequest.getTenderFrom());
        }
        logger.info("开始插入temp表   {}",JSONObject.toJSONString(temp));
        boolean tenderTmpFlag = borrowTenderTmpMapper.insertSelective(temp) > 0 ? true : false;
        if (!tenderTmpFlag) {
            logger.error("插入borrowTenderTmp表失败，出借订单号：" + tenderRequest.getOrderId());
            throw new RuntimeException("插入borrowTenderTmp表失败，出借订单号：" + tenderRequest.getOrderId());
        }
        logger.info("完成插入temp表");
        BorrowTenderTmpinfo info = new BorrowTenderTmpinfo();
        info.setOrdid(tenderRequest.getOrderId());
        Map<String, String> map = new HashMap<String, String>();
        map.put("borrow_nid", tenderRequest.getBorrowNid());
        map.put("user_id", userId + "");
        map.put("account", tenderRequest.getAccount() + "");
        map.put("status", "0");
        map.put("nid", tenderRequest.getOrderId());
        map.put("addtime", (System.currentTimeMillis() / 1000) + "");
        map.put("addip", tenderRequest.getIp());
        String array = JSON.toJSONString(map);
        info.setTmpArray(array);
        Boolean tenderTmpInfoFlag = borrowTenderTmpinfoMapper.insertSelective(info) > 0 ? true : false;
        if (!tenderTmpInfoFlag) {
            logger.error("插入borrowTenderTmpInfo表失败，出借订单号：" + tenderRequest.getOrderId());
            throw new RuntimeException("插入borrowTenderTmpInfo表失败，出借订单号：" + tenderRequest.getOrderId());
        }
        logger.info("完成出借前操作");
        return 1;
    }

    @Override
    public ProjectCustomeDetailVO getProjectDetail(String borrowNid) {
        return borrowCustomizeMapper.getProjectDetail(borrowNid);
    }

    @Override
    public ProjectCompanyDetailVO getProjectCompany(String borrowNid) {
        return borrowCustomizeMapper.getProjectCompanyDetail(borrowNid);
    }

    @Override
    public WebProjectPersonDetailVO getProjectPerson(String borrowNid) {
        return borrowCustomizeMapper.getProjectPsersonDetail(borrowNid);
    }

    /**
     * 出借异步修改表
     *
     * @param tenderBg
     */
    @Override
    public void updateTenderAfter(TenderBgVO tenderBg) {
        Borrow borrow = getBorrowByNid(tenderBg.getBorrowNid());
        BorrowInfo borrowInfo = getBorrowInfoByNid(tenderBg.getBorrowNid());
        Integer userId = tenderBg.getUserId();
        // 删除临时表
        BorrowTenderTmpExample borrowTenderTmpExample = new BorrowTenderTmpExample();
        BorrowTenderTmpExample.Criteria criteria1 = borrowTenderTmpExample.createCriteria();
        criteria1.andNidEqualTo(tenderBg.getOrderId());
        criteria1.andUserIdEqualTo(tenderBg.getUserId());
        criteria1.andBorrowNidEqualTo(tenderBg.getBorrowNid());
        boolean tenderTempFlag = borrowTenderTmpMapper.deleteByExample(borrowTenderTmpExample) > 0 ? true : false;
        if (!tenderTempFlag) {
            logger.error("删除borrowTenderTmp表失败 borrowNid:{}  logOrdId:{} userId:{} ",tenderBg.getBorrowNid(),tenderBg.getOrderId(),tenderBg.getUserId());
            throw new RuntimeException("删除borrowTenderTmp表失败");
        }
        // 插入冻结表
        FreezeList record = new FreezeList();
        record.setAmount(tenderBg.getAccountDecimal());
        record.setBorrowNid(tenderBg.getBorrowNid());
        record.setOrdid(tenderBg.getOrderId());
        record.setUserId(tenderBg.getUserId());
        record.setRespcode(tenderBg.getRetCode());
        record.setTrxid("");
        record.setUsrcustid(tenderBg.getAccountId());
        record.setXfrom(1);
        record.setStatus(0);
        record.setUnfreezeManual(0);
        boolean freezeFlag = freezeListMapper.insertSelective(record) > 0 ? true : false;
        if (!freezeFlag) {
            logger.error("散标出借  插入freezeFlag表失败 ");
            throw new RuntimeException("插入freezeFlag表失败");
        }
        // 插入BorrowTender表
        BorrowTender borrowTender = new BorrowTender();
        borrowTender.setAccount(tenderBg.getAccountDecimal());
        borrowTender.setAddIp(tenderBg.getIp());
        borrowTender.setBorrowNid(tenderBg.getBorrowNid());
        borrowTender.setClient(tenderBg.getClient());
        borrowTender.setLoanAmount(tenderBg.getAccountDecimal().subtract(tenderBg.getPerService()));
        borrowTender.setNid(tenderBg.getOrderId());
        borrowTender.setOrderDate(tenderBg.getOrderDate());
        borrowTender.setRecoverAccountAll(new BigDecimal(0));//
        borrowTender.setRecoverAccountCapitalWait(new BigDecimal(0));//
        borrowTender.setRecoverAccountCapitalYes(new BigDecimal(0));
        borrowTender.setRecoverAccountInterest(new BigDecimal(0));
        borrowTender.setRecoverAccountInterestWait(new BigDecimal(0));
        borrowTender.setRecoverAccountInterestYes(new BigDecimal(0));
        borrowTender.setRecoverAccountWait(new BigDecimal(0));
        borrowTender.setRecoverAccountYes(new BigDecimal(0));
        borrowTender.setRecoverAdvanceFee(new BigDecimal(0));
        borrowTender.setRecoverFee(new BigDecimal(0));
        borrowTender.setRecoverFullStatus(0);
        borrowTender.setRecoverLateFee(new BigDecimal(0));
        borrowTender.setRecoverTimes(0);
        borrowTender.setStatus(0);
        borrowTender.setUserId(tenderBg.getUserId());
        borrowTender.setInviteRegionId(tenderBg.getInviteRegionId());
        borrowTender.setInviteRegionName(tenderBg.getInviteRegionName());
        borrowTender.setInviteBranchId(tenderBg.getInviteBranchId());
        borrowTender.setInviteBranchName(tenderBg.getInviteBranchName());
        borrowTender.setInviteDepartmentId(tenderBg.getInviteDepartmentId());
        borrowTender.setInviteDepartmentName(tenderBg.getInviteDepartmentName());
        borrowTender.setInviteUserId(tenderBg.getInviteUserId());
        // 投资时候渠道
        borrowTender.setTenderUserUtmId(tenderBg.getTenderUserUtmId());

        logger.info("看看推荐人存在不存在？：" + tenderBg.getInviteUserName());
        borrowTender.setInviteUserName(tenderBg.getInviteUserName());


        borrowTender.setInviteUserAttribute(tenderBg.getInviteUserAttribute());
        borrowTender.setTenderUserAttribute(tenderBg.getTenderUserAttribute());
        borrowTender.setInvestType(0);
        // 单笔出借的融资服务费
        borrowTender.setLoanFee(tenderBg.getPerService());
        //出借授权码
        borrowTender.setAuthCode(tenderBg.getAuthCode());
        borrowTender.setRemark("现金出借");
        borrowTender.setBorrowUserId(borrow.getUserId());
        borrowTender.setBorrowUserName(borrow.getBorrowUserName());
        
        // 主干是 tender_user_name 迁移到微服务后是 username
        borrowTender.setUserName(tenderBg.getUserName());
        
        
        logger.info("开始插入borrowTender表...borrowTender:{}",JSONObject.toJSONString(borrowTender));
        borrowTenderMapper.insertSelective(borrowTender);
        logger.info("插入borrowTender表结束...");


        // 插入产品加息
        if (Validator.isIncrease(borrowInfo.getIncreaseInterestFlag(), borrowInfo.getBorrowExtraYield())) {
            boolean increaseFlag = this.insertIncreaseInterest(borrow, borrowInfo, tenderBg, borrowTender);
            if (!increaseFlag) {
                throw new RuntimeException("插入产品加息表失败！");
            }
            logger.info("用户:" + userId + "******插入产品加息，出借订单号：" + borrowTender.getNid());
        }

        // 更新用户账户余额表
        Account accountBean = new Account();
        accountBean.setUserId(userId);
        // 出借人冻结金额增加
        accountBean.setBankFrost(tenderBg.getAccountDecimal());
        // 出借人可用余额扣减
        accountBean.setBankBalance(tenderBg.getAccountDecimal());
        // 江西银行账户余额
        // 此账户余额出借后应该扣减掉相应出借金额,sql已改
        accountBean.setBankBalanceCash(tenderBg.getAccountDecimal());
        // 江西银行账户冻结金额
        accountBean.setBankFrostCash(tenderBg.getAccountDecimal());
        logger.info("开始更新用户账户信息表");
        // mod by liuyang 20190221 更新账户时,可用大于投资金额时,才更新 start
        Boolean accountFlag = this.accountCustomizeMapper.updateAccountOfTender(accountBean) > 0 ? true : false;
        // mod by liuyang 20190221 更新账户时,可用大于投资金额时,才更新 end
        if (!accountFlag) {
            logger.error("用户账户信息表更新失败");
            throw new RuntimeException("用户账户信息表更新失败");
        }
        // 插入account_list表
        Account account = this.accountService.getAccount(userId);
        AccountList accountList = new AccountList();
        accountList.setAmount(tenderBg.getAccountDecimal());
        /** 银行存管相关字段设置 */
        accountList.setAccountId(account.getAccountId());
        accountList.setBankAwait(account.getBankAwait());
        accountList.setBankAwaitCapital(account.getBankAwaitCapital());
        accountList.setBankAwaitInterest(account.getBankAwaitInterest());
        accountList.setBankBalance(account.getBankBalance());
        accountList.setBankFrost(account.getBankFrost());
        accountList.setBankInterestSum(account.getBankInterestSum());
        accountList.setBankTotal(account.getBankTotal());
        accountList.setBankWaitCapital(account.getBankWaitCapital());
        accountList.setBankWaitInterest(account.getBankWaitInterest());
        accountList.setBankWaitRepay(account.getBankWaitRepay());
        accountList.setPlanBalance(account.getPlanBalance());//汇计划账户可用余额
        accountList.setPlanFrost(account.getPlanFrost());
        accountList.setCheckStatus(0);
        accountList.setTradeStatus(1);// 交易状态
        // 0:失败 1:成功
        accountList.setIsBank(1);
        accountList.setTxDate(Integer.parseInt(tenderBg.getTxDate()));
        accountList.setTxTime(Integer.parseInt(tenderBg.getTxTime()));
        accountList.setSeqNo(tenderBg.getSeqNo());
        accountList.setBankSeqNo(tenderBg.getTxDate() + tenderBg.getTxTime() + tenderBg.getSeqNo());
        /** 非银行存管相关字段 */
        accountList.setAwait(new BigDecimal(0));
        accountList.setBalance(account.getBalance());
        accountList.setFrost(account.getFrost());
        accountList.setIp(tenderBg.getIp());
        accountList.setNid(tenderBg.getOrderId());
        accountList.setOperator(userId + "");
        accountList.setRemark(tenderBg.getBorrowNid());
        accountList.setRepay(new BigDecimal(0));
        accountList.setTotal(account.getTotal());
        accountList.setTrade("tender");// 出借
        accountList.setTradeCode("frost");// 投标冻结后为frost
        accountList.setType(3);// 收支类型1收入2支出3冻结
        accountList.setUserId(userId);
        accountList.setWeb(0);
        accountList.setIsBank(1);// 是否是银行的交易记录(0:否,1:是)
        logger.info("开始更新用户账户交易明细表更新");
        boolean accountListFlag = accountListMapper.insertSelective(accountList) > 0 ? true : false;
        if (!accountListFlag) {
            logger.error("用户账户交易明细表更新失败");
            throw new RuntimeException("用户账户交易明细表更新失败");
        }

        // 更新borrow表
        Map<String, Object> borrowParam = new HashMap<String, Object>();
        borrowParam.put("borrowAccountYes", tenderBg.getAccountDecimal());
        borrowParam.put("borrowService", tenderBg.getPerService());
        borrowParam.put("borrowId", borrow.getId());


        logger.info("看看智投编号存在不存在？：" + borrow.getPlanNid());


        logger.info("开始更新borrow表 标的号{} borrowAccountYes {}  ",borrow.getBorrowNid(),tenderBg.getAccountDecimal());
        boolean updateBorrowAccountFlag = borrowCustomizeMapper.updateOfBorrow(borrowParam) > 0 ? true : false;
        // 更新borrow表
        if (!updateBorrowAccountFlag) {
            logger.error("更新borrow表失败");
            throw new RuntimeException("borrow表更新失败");
        }

        // 投标成功后,发送CRM绩效统计
        CrmInvestMsgBean crmInvestMsgBean = new CrmInvestMsgBean();
        crmInvestMsgBean.setInvestType(0);
        crmInvestMsgBean.setOrderId(borrowTender.getNid());
        //加入明细表插表成功的前提下，继续
        //crm出借推送
        try {
            logger.info("投资成功后,发送CRM投资统计MQ:投资订单号:[" + borrowTender.getNid() + "].");
            commonProducer.messageSendDelay(new MessageContent(MQConstant.CRM_TENDER_INFO_TOPIC, UUID.randomUUID().toString(), crmInvestMsgBean), 2);
        } catch (Exception e) {
            logger.error("发送CRM消息失败:" + e.getMessage());
        }

        // 计算此时的剩余可出借金额
        BigDecimal accountWait = this.getBorrowByNid(tenderBg.getBorrowNid()).getBorrowAccountWait();
        String borrowNid = tenderBg.getBorrowNid();
        logger.info("标的{}  剩余可投为：{} ",borrowNid,accountWait);
        // 满标处理
        if (accountWait.compareTo(new BigDecimal(0)) == 0) {
            logger.info("用户:" + userId + "***********************************项目满标，订单号：" + tenderBg.getOrderId());
            Map<String, Object> borrowFull = new HashMap<String, Object>();
            borrowFull.put("borrowId", borrow.getId());
            boolean fullFlag = borrowCustomizeMapper.updateOfFullBorrow(borrowFull) > 0 ? true : false;
            if (!fullFlag) {
                logger.error("满标更新borrow表失败");
                throw new RuntimeException("满标更新borrow表失败");
            }
            // 清除标总额的缓存
            RedisUtils.del(RedisConstants.BORROW_NID+borrowNid);
            // 纯发短信接口
            Map<String, String> replaceMap = new HashMap<String, String>();
            replaceMap.put("val_title", borrowNid);
            replaceMap.put("val_date", DateUtils.getNowDate());
            BorrowSendTypeExample sendTypeExample = new BorrowSendTypeExample();
            BorrowSendTypeExample.Criteria sendTypeCriteria = sendTypeExample.createCriteria();
            sendTypeCriteria.andSendCdEqualTo("AUTO_FULL");
            List<BorrowSendType> sendTypeList = borrowSendTypeMapper.selectByExample(sendTypeExample);
            if (sendTypeList == null || sendTypeList.size() == 0) {
                logger.error("用户:" + userId + "***********************************冻结成功后处理afterChinaPnR：" + "数据库查不到 sendTypeList == null");
                throw new RuntimeException("用户:" + userId + "***********************************冻结成功后处理afterChinaPnR：" + "数据库查不到 sendTypeList == null");
            }
            BorrowSendType sendType = sendTypeList.get(0);
            if (sendType.getAfterTime() == null) {
                logger.error("用户:" + userId + "***********************************冻结成功后处理afterChinaPnR：" + "sendType.getAfterTime()==null");
                throw new RuntimeException("用户:" + userId + "***********************************冻结成功后处理afterChinaPnR：" + "sendType.getAfterTime()==null");
            }
            replaceMap.put("val_times", sendType.getAfterTime() + "");
            // 发送短信验证码
            SmsMessage smsMessage = new SmsMessage(null, replaceMap, null, null, MessageConstant.SMS_SEND_FOR_MANAGER, null, CustomConstants.PARAM_TPL_XMMB, CustomConstants.CHANNEL_TYPE_NORMAL);
            try{
                commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC,
                        UUID.randomUUID().toString(), smsMessage));
            }catch (Exception e){
                logger.error(e.getMessage());
                logger.error("发送短信失败");
            }

            // add by liushouyi nifa2 20181204 start
            // 发送满标状态埋点
            // 发送发标成功的消息队列到互金上报数据
            Map<String, String> params = new HashMap<String, String>();
            params.put("borrowNid", borrowNid);
            commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.ISSUE_INVESTED_TAG, UUID.randomUUID().toString(), params),
                    MQConstant.HG_REPORT_DELAY_LEVEL);
            // add by liushouyi nifa2 20181204 end

            // add by liuyang wbs系统推送标的信息 20190517 start
            try{
                // 满标后,发送标的募集结束MQ
                if("10000000".equals(borrowInfo.getPublishInstCode())) {
                    JSONObject borrowParams = new JSONObject();
                    // 产品编号
                    borrowParams.put("productNo", borrowNid);
                    // 产品状态 :3:募集结束
                    borrowParams.put("productStatus", "3");
                    // 产品类型 0 散标类, 1 计划类
                    borrowParams.put("productType", 0);
                    commonProducer.messageSend(new MessageContent(MQConstant.WBS_BORROW_INFO_TOPIC, MQConstant.WBS_BORROW_INFO_TAG, UUID.randomUUID().toString(), borrowParams));
                }
            }catch (Exception e){
                logger.error(e.getMessage());
                logger.error("wbs系统推送标的信息发送MQ失败");
            }
            // add by liuyang wbs系统推送标的信息 20190517 end
        } else if (accountWait.compareTo(BigDecimal.ZERO) < 0) {
            logger.error("用户:" + userId + "项目编号:" + borrowNid + "***********************************项目暴标");
            throw new RuntimeException("用户:" + userId + "项目编号:" + borrowNid + "***********************************项目暴标");
        }


    }

    // 插入产品加息表
    private boolean insertIncreaseInterest(Borrow borrow, BorrowInfo borrowInfo, TenderBgVO tenderBg, BorrowTender borrowTender) {
        // 操作ip
        String ip = tenderBg.getIp();
        // 操作平台
        int client = borrowTender.getClient();
        // 出借人id
        Integer userId = borrowTender.getUserId();
        // 借款金额
        String account = tenderBg.getAccountDecimal().toString();
        // 订单id
        String tenderOrderId = tenderBg.getOrderId();
        // 项目编号
        String borrowNid = borrow.getBorrowNid();
        // 项目的还款方式
        String borrowStyle = borrow.getBorrowStyle();
        BorrowStyle borrowStyleMain = this.getborrowStyleByNid(borrowStyle);
        String borrowStyleName = borrowStyleMain.getName();
        // 借款期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
        // 生成额外利息订单
        String orderId = GetOrderIdUtils.getOrderId2(Integer.valueOf(userId));
        if (borrowTender != null) {
            IncreaseInterestInvest increaseInterestInvest = new IncreaseInterestInvest();
            increaseInterestInvest.setUserId(userId);
            increaseInterestInvest.setInvestUserName(borrowTender.getUserName());
            increaseInterestInvest.setTenderId(borrowTender.getId());
            increaseInterestInvest.setTenderNid(tenderOrderId);
            increaseInterestInvest.setBorrowNid(borrowNid);
            increaseInterestInvest.setBorrowApr(borrow.getBorrowApr());
            increaseInterestInvest.setBorrowExtraYield(borrowInfo.getBorrowExtraYield());
            increaseInterestInvest.setBorrowPeriod(borrowPeriod);
            increaseInterestInvest.setBorrowStyle(borrowStyle);
            increaseInterestInvest.setBorrowStyleName(borrowStyleName);
            increaseInterestInvest.setOrderId(orderId);
            increaseInterestInvest.setOrderDate(GetDate.getServerDateTime(10, new Date()));
            increaseInterestInvest.setAccount(new BigDecimal(account));
            increaseInterestInvest.setStatus(0);
            increaseInterestInvest.setWeb(0);
            increaseInterestInvest.setClient(client);
            increaseInterestInvest.setAddIp(ip);
            increaseInterestInvest.setRemark("加息收益");
            increaseInterestInvest.setInvestType(0);
            increaseInterestInvest.setCreateTime(GetDate.getNowTime());
            increaseInterestInvest.setCreateUserId(userId);
            increaseInterestInvest.setCreateUserName(borrowTender.getUserName());

            // 设置推荐人之类的
            increaseInterestInvest.setTenderUserAttribute(borrowTender.getTenderUserAttribute());
            increaseInterestInvest.setInviteRegionId(borrowTender.getInviteRegionId());
            increaseInterestInvest.setInviteRegionName(borrowTender.getInviteRegionName());
            increaseInterestInvest.setInviteBranchId(borrowTender.getInviteBranchId());
            increaseInterestInvest.setInviteBranchName(borrowTender.getInviteBranchName());
            increaseInterestInvest.setInviteDepartmentId(borrowTender.getInviteDepartmentId());
            increaseInterestInvest.setInviteDepartmentName(borrowTender.getInviteDepartmentName());
            increaseInterestInvest.setInviteUserId(borrowTender.getInviteUserId());
            increaseInterestInvest.setInviteUserName(borrowTender.getInviteUserName());

            boolean incinvflag = increaseInterestInvestMapper.insertSelective(increaseInterestInvest) > 0 ? true : false;
            if (!incinvflag) {
                logger.error("产品加息出借额外利息出借失败，插入额外出借信息失败,出借订单号:" + tenderOrderId);
                throw new RuntimeException("产品加息出借额外利息出借失败，插入额外出借信息失败,出借订单号:" + tenderOrderId);
            }
            return incinvflag;
        }
        return false;
    }

    /**
     * 散标出借异步返回结果
     *
     * @param tenderRetMsg
     */
    @Override
    public void updateTenderResult(TenderRetMsg tenderRetMsg) {
        BorrowTenderTmpExample borrowTenderTmpExample = new BorrowTenderTmpExample();
        BorrowTenderTmpExample.Criteria criteria1 = borrowTenderTmpExample.createCriteria();
        criteria1.andNidEqualTo(tenderRetMsg.getLogOrderId());
        criteria1.andUserIdEqualTo(Integer.parseInt(tenderRetMsg.getLogUserId()));
        criteria1.andBorrowNidEqualTo(tenderRetMsg.getProductId());
        List<BorrowTenderTmp> list = borrowTenderTmpMapper.selectByExample(borrowTenderTmpExample);
        if (list != null && list.size() == 1) {
            BorrowTenderTmp borrowTenderTmp = list.get(0);
            borrowTenderTmp.setRetCode(tenderRetMsg.getRespCode());
            borrowTenderTmp.setRetMsg(tenderRetMsg.getRetMsg());
            borrowTenderTmpMapper.updateByPrimaryKeySelective(borrowTenderTmp);
        }
    }

    /**
     * 获取散标出借异步返回结果
     *
     * @param userId
     * @param logOrdId
     * @param borrowNid
     * @return
     */
    @Override
    public String getBorrowTenderResult(Integer userId, String logOrdId, String borrowNid) {
        String result = "";
        BorrowTenderTmpExample borrowTenderTmpExample = new BorrowTenderTmpExample();
        BorrowTenderTmpExample.Criteria criteria1 = borrowTenderTmpExample.createCriteria();
        criteria1.andNidEqualTo(logOrdId);
        criteria1.andUserIdEqualTo(userId);
        criteria1.andBorrowNidEqualTo(borrowNid);
        List<BorrowTenderTmp> list = borrowTenderTmpMapper.selectByExample(borrowTenderTmpExample);
        if (list != null && list.size() == 1) {
            BorrowTenderTmp borrowTenderTmp = list.get(0);
            result = borrowTenderTmp.getRetMsg();
        }
        logger.info("散标出借获取失败结果：result：{} list.size():{}", result, (list == null ? 0 : list.size()));
        return result;
    }

    @Override
    public Integer getTotalInverestCount(Integer userId) {
       return borrowCustomizeMapper.getTotalInverestCount(userId);
    }

	/**
	 * COUNT
	 * 
	 * @param
	 * @return
	 */
	@Override
    public Long countBorrow(BorrowCommonCustomizeVO borrowCommonCustomizeVO) {
		return this.borrowCustomizeMapper.countBorrow(borrowCommonCustomizeVO);
	}

	/**
	 * 借款列表
	 * 
	 * @return
	 */
	@Override
    public List<BorrowCustomizeVO> selectBorrowList(BorrowCommonCustomizeVO borrowCommonCustomizeVO) {
		return this.borrowCustomizeMapper.selectBorrowList(borrowCommonCustomizeVO);
	}


    @Override
    public List<AccountBorrow> getAccountBorrowList(String borrowNid) {
        AccountBorrowExample example = new AccountBorrowExample();
        AccountBorrowExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(borrowNid);
        List<AccountBorrow> accountBorrows = accountBorrowMapper.selectByExample(example);
        return  accountBorrows;
    }


    /**
     * 查询订单风控信息
     * @author zhangyk
     * @date 2018/8/10 15:40
     */
    @Override
    public BorrowInfoWithBLOBs getBorrowInfoWithBLOBs(String borrowNid) {
        BorrowInfoExample example = new BorrowInfoExample();
        BorrowInfoExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(borrowNid);
        List<BorrowInfoWithBLOBs> list = borrowInfoMapper.selectByExampleWithBLOBs(example);
        if (CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }


    /**
     * 查询计划还款日前一天，处于出借中和复审中的原始标的，发送邮件预警
     * @author zhangyk
     * @date 2018/8/20 16:26
     */
    @Override
    public List<BorrowCustomizeVO> selectUnDealBorrowBeforeLiquidate() {
        List<BorrowCustomizeVO> list = borrowCustomizeMapper.selectUnDealBorrowBeforeLiquidate();
        return list;
    }

	@Override
	public List<BorrowCommonCustomizeVO> exportBorrowList(BorrowCommonCustomizeVO BorrowCommonCustomizeVO) {
		return this.borrowCustomizeMapper.exportBorrowList(BorrowCommonCustomizeVO);
	}

	/**
	 * 根据Nid和当前时间获取borrow
	 * @author zhangyk
	 * @date 2018/9/3 17:25
	 */
    @Override
    public Borrow selectBorrowByNidAndNowTime(String borrowNid, Integer nowtime) {
        BorrowExample example = new BorrowExample();
        BorrowExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(borrowNid);
        criteria.andStatusEqualTo(1);
        criteria.andVerifyStatusEqualTo(3);
        criteria.andOntimeGreaterThan(0);
        criteria.andOntimeLessThan(nowtime);
        List<Borrow> list = borrowMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 获取还款计算公式 add by liushouyi
     *
     * @param borrowStyle
     * @return
     */
    @Override
    public List<BorrowStyleWithBLOBs> selectBorrowStyleWithBLOBs(String borrowStyle) {
        BorrowStyleExample example = new BorrowStyleExample();
        BorrowStyleExample.Criteria cra = example.createCriteria();
        cra.andStatusEqualTo(CustomConstants.FLAG_STATUS_ENABLE);
        cra.andNidEqualTo(borrowStyle);
        List<BorrowStyleWithBLOBs> borrowStyleWithBLOBsList = borrowStyleMapper.selectByExampleWithBLOBs(example);
        if (null != borrowStyleWithBLOBsList && borrowStyleWithBLOBsList.size() > 0) {
            return borrowStyleWithBLOBsList;
        }
        return null;
    }

    @Override
    public Long countBatchCenter(BatchCenterCustomizeRequest batchCenterCustomize){
        return batchCenterCustomizeMapper.countBatchCenter(batchCenterCustomize);
    }

    @Override
    public List<BatchCenterCustomize> selectBatchCenterList(BatchCenterCustomizeRequest batchCenterCustomize){
        Map<String, String> REPAY_STATUS =  CacheUtil.getParamNameMap("REPAY_STATUS");
        List<BatchCenterCustomize> mppBatch = batchCenterCustomizeMapper.selectBatchCenterList(batchCenterCustomize);
        for (BatchCenterCustomize o : mppBatch){
            o.setStatusStr(REPAY_STATUS.get(o.getStatus()));
        }
        return mppBatch;
        //return batchCenterCustomizeMapper.selectBatchCenterList(batchCenterCustomize);
    }

    @Override
    public String getborrowIdByProductId(Map<String, Object> params){
        String borrowNid = null;
        HjhPlanAssetExample example = new HjhPlanAssetExample();
        Object productId = params.get("productId");
        Object instCode = params.get("instCode");
        example.createCriteria().andAssetIdEqualTo(productId==null? "":productId.toString()).andInstCodeEqualTo(instCode==null?"":instCode.toString());
        List<HjhPlanAsset> planList = this.hjhPlanAssetMapper.selectByExample(example);
        if (planList != null && planList.size() > 0) {
            borrowNid = planList.get(0).getBorrowNid();
        }
        return borrowNid;
    }

    @Override
    public List<BorrowAndInfoVO> getborrowByProductId(Map<String, Object> params){
        List<BorrowAndInfoVO> borrows = this.hjhPlanCustomizeMapper.getPlanBorrowList(params);
        if (borrows != null && borrows.size() > 0) {
            return borrows;
        }
        return null;
    }

    @Override
    public List<WebUserRepayProjectListCustomizeVO> selectOrgRepayProjectList(Map<String, Object> params){
        return webUserRepayListCustomizeMapper.selectOrgRepayProjectList(params);
    }

    @Override
    public List<WebUserRepayProjectListCustomizeVO> selectUserRepayProjectList(Map<String, Object> params){
        return webUserRepayListCustomizeMapper.selectUserRepayProjectList(params);
    }

    @Override
    @Deprecated
    public ProjectBeanVO searchRepayProjectDetail(ProjectBeanVO form) throws Exception {
        boolean isAllRepay = form.isBlAllRepay();
        String userId = StringUtils.isNotEmpty(form.getUserId()) ? form.getUserId() : null;
        String borrowNid = StringUtils.isNotEmpty(form.getBorrowNid()) ? form.getBorrowNid() : null;
        //BorrowExample example = new BorrowExample();
        //BorrowExample.Criteria crt = example.createCriteria();
        BorrowCustomizeVO borrowStr = new BorrowCustomizeVO();
        //crt.andBorrowNidEqualTo(borrowNid);
        borrowStr.setBorrowNid(borrowNid);
        if (StringUtils.isNotEmpty(form.getRoleId()) && "3".equals(form.getRoleId())) {
            // 垫付机构
            //crt.andRepayOrgUserIdEqualTo(Integer.parseInt(userId));
            borrowStr.setRepayOrgUserId(Integer.valueOf(userId));
        } else {
            // 普通借款人
            //crt.andUserIdEqualTo(Integer.parseInt(userId));
            borrowStr.setUserId(Integer.parseInt(userId));
        }

        //List<Borrow> projects = borrowMapper.selectByExample(example);// 查询相应的用户还款项目
        List<BorrowCustomizeVO> projects = borrowCustomizeMapper.selectBorrowByNidList(borrowStr);// 查询相应的用户还款项目
        if (projects != null && projects.size() > 0) {
            BorrowCustomizeVO borrow = projects.get(0);
            // userId 改成借款人的userid！！！
            userId = borrow.getUserId().toString();
            form.settType("0");// 设置为非汇添金专属项目
            // 设置相应的项目名称
            // 之前取borrow表的Name，现在取borrow表的projectName
            // form.setBorrowName(borrow.getName());
            form.setBorrowName(borrow.getProjectName());

            // 获取相应的项目还款方式
            String borrowStyle = StringUtils.isNotEmpty(borrow.getBorrowStyle()) ? borrow.getBorrowStyle() : null;
            form.setBorrowStyle(borrowStyle);

            // 用户是否全部结清，是否正在还款，是否只能全部结清 默认都否
            form.setAllRepay("0");
            form.setRepayStatus("0");
            form.setOnlyAllRepay("0");

            BorrowApicronExample exampleBorrowApicron = new BorrowApicronExample();
            BorrowApicronExample.Criteria crtBorrowApicron = exampleBorrowApicron.createCriteria();
            crtBorrowApicron.andBorrowNidEqualTo(borrowNid);
            crtBorrowApicron.andApiTypeEqualTo(1);
            crtBorrowApicron.andStatusNotEqualTo(6);// 不是已经还款的，正在还款的
            List<BorrowApicron> borrowApicrons = borrowApicronMapper.selectByExample(exampleBorrowApicron);
            // 有正在还款，查看是否是一次结清，适用分期和一次性还款方式，   下面逻辑中的分期最后一期继续适用
            if (borrowApicrons != null && borrowApicrons.size() > 0) {
                BorrowApicron borrowApicron = borrowApicrons.get(0);
                Integer allrepay = borrowApicron.getIsAllrepay();
                if(allrepay != null && allrepay.intValue() ==1) {
                    form.setAllRepay("1");
                    isAllRepay = true;
                }
                // 能查到，无论如何都是有正在还款
                form.setRepayStatus("1");
            }

            // 一次性还款
            if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle) || CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                RepayBean repay = calculateRepay(Integer.valueOf(userId), borrow);
                this.setRecoverDetail(form, userId, borrow,repay);
            } else {
                RepayBean repayByTerm = new RepayBean();
                BorrowRepay borrowRepay = this.searchRepay(Integer.parseInt(userId), borrow.getBorrowNid());
                // 获取相应的还款信息
                BeanUtils.copyProperties(borrowRepay, repayByTerm);
                repayByTerm.setBorrowPeriod(String.valueOf(borrow.getBorrowPeriod()));
                // 计算当前还款期数
                int period = Integer.valueOf(borrow.getBorrowPeriod()) - borrowRepay.getRepayPeriod() + 1;
                BorrowRepayPlan borrowRepayPlan = null;
                String repayTimeStart =null;
                if(period ==1) {
                    borrowRepayPlan = this.searchRepayPlan(Integer.valueOf(userId), borrowNid, period);
                    repayTimeStart = borrowRepayPlan.getCreateTime().toString();
                }else {
                    borrowRepayPlan = this.searchRepayPlan(Integer.valueOf(userId), borrowNid, period-1);
                    repayTimeStart = borrowRepayPlan.getRepayTime().toString();

                    int curPlanStart = GetDate.getIntYYMMDD(Integer.valueOf(repayTimeStart));
                    int nowDate = GetDate.getIntYYMMDD(new Date());
                    // 超前还款的情况，只能一次性还款
                    if(nowDate <= curPlanStart) {
                        form.setOnlyAllRepay("1");
                        isAllRepay = true;
                    }
                }

                // 计算分期的项目还款信息
                if(isAllRepay) {
                    // 全部结清的
//        			RepayBean repayByTerm = this.searchRepayPlanTotal(Integer.parseInt(userId), borrow);
                    // 分期 当前期 计算，如果当前期没有还款，则先算当前期，后算所有剩下的期数
                    this.calculateRepayPlanAll(repayByTerm, borrow, period);
                    this.setRecoverPlanAllDetail(form, isAllRepay, userId,borrow,repayByTerm);

                }else {
                    // 当期还款
                    this.calculateRepayPlan(repayByTerm, borrow, period);
                    this.setRecoverPlanDetail(form, isAllRepay, userId,borrow,repayByTerm);
                }
            }
            return form;

        } else {
            return null;
        }
    }

    /**
     * 分期还款 计算各期数据
     * @param form
     * @param isAllRepay
     * @param userId
     * @param borrow
     * @param repayByTerm
     * @throws ParseException
     */
    @Deprecated
    private void setRecoverPlanDetail(ProjectBeanVO form, boolean isAllRepay, String userId,BorrowCustomizeVO borrow,RepayBean repayByTerm) throws ParseException {

        String borrowNid = borrow.getBorrowNid();
        // 还款总期数
        int periodTotal = Integer.valueOf(borrow.getBorrowPeriod());

        // 计算当前还款期数
        int repayPeriod = periodTotal - repayByTerm.getRepayPeriod() + 1;
        // 如果用户不是还款最后一期
        if (repayPeriod <= periodTotal) {
//		    BorrowApicronExample exampleBorrowApicron = new BorrowApicronExample();
//		    BorrowApicronExample.Criteria crtBorrowApicron = exampleBorrowApicron.createCriteria();
//		    crtBorrowApicron.andBorrowNidEqualTo(borrowNid);
//		    crtBorrowApicron.andPeriodNowEqualTo(repayPeriod);
//		    crtBorrowApicron.andApiTypeEqualTo(1);
//		    List<BorrowApicron> borrowApicrons = borrowApicronMapper.selectByExample(exampleBorrowApicron);
//		    // 正在还款当前期
//		    if (borrowApicrons != null && borrowApicrons.size() > 0) {
//		        BorrowApicron borrowApicron = borrowApicrons.get(0);
//		        if (borrowApicron.getStatus() != 6) {
//		            // 用户还款当前期
//		            form.setRepayStatus("1");
//		        } else {// 用户当前期正在还款
//		            form.setRepayStatus("0");
//		        }
//		    } else {// 用户未还款当前期
//		        form.setRepayStatus("0");
//		    }
        } else {// 用户正在还款最后一期
            form.setRepayStatus("1");
        }
        // 设置当前的还款期数
        form.setRepayPeriod(String.valueOf(repayPeriod));
        // 获取统计的用户还款计划列表
        List<RepayDetailBean> userRepayPlans = repayByTerm.getRepayPlanList();
        if (userRepayPlans != null && userRepayPlans.size() > 0) {
            List<ProjectRepayVO> recoverList = new ArrayList<ProjectRepayVO>();
            // 遍历计划还款信息，拼接数据
            for (int i = 0; i < userRepayPlans.size(); i++) {
                // 获取用户的还款信息
                RepayDetailBean userRepayPlan = userRepayPlans.get(i);
                // 声明需拼接数据的实体
                ProjectRepayVO userRepayBean = new ProjectRepayVO();
                // 如果本期已经还款完成
                if (userRepayPlan.getRepayStatus() == 1) {
                    // 获取本期的用户已还款总额
                    userRepayBean.setRepayTotal(userRepayPlan.getRepayAccountYes().add(userRepayPlan.getRepayFee()).toString());
                }
                // 用户未还款本息
                else {
                    // 此处分期计算的是本息+管理费
                    userRepayBean.setRepayTotal(userRepayPlan.getRepayAccountAll().toString());
                }
                userRepayBean.setRepayAccount(userRepayPlan.getRepayAccount().add(userRepayPlan.getChargeInterest()).add(userRepayPlan.getDelayInterest()).add(userRepayPlan.getLateInterest())
                        .toString());// 设置本期的用户本息和
                userRepayBean.setRepayCapital(userRepayPlan.getRepayCapital().toString());// 用户未还款本息
                userRepayBean.setRepayInterest(userRepayPlan.getRepayInterest().toString()); // 设置本期的用户利息
                userRepayBean.setUserId(userRepayPlan.getUserId().toString());
                userRepayBean.setRepayPeriod(userRepayPlan.getRepayPeriod().toString());
                userRepayBean.setAdvanceStatus(userRepayPlan.getAdvanceStatus().toString());
                userRepayBean.setStatus(userRepayPlan.getRepayStatus().toString());
                userRepayBean.setRepayTime(GetDate.getDateMyTimeInMillis(Integer.valueOf(userRepayPlan.getRepayTime())));
                userRepayBean.setChargeDays(userRepayPlan.getChargeDays().toString());
                userRepayBean.setChargeInterest(userRepayPlan.getChargeInterest().multiply(new BigDecimal("-1")).toString());
                userRepayBean.setDelayDays(userRepayPlan.getDelayDays().toString());
                userRepayBean.setDelayInterest(userRepayPlan.getDelayInterest().toString());
                userRepayBean.setManageFee(userRepayPlan.getRepayFee().toString());
                userRepayBean.setLateDays(userRepayPlan.getLateDays().toString());
                userRepayBean.setLateInterest(userRepayPlan.getLateInterest().toString());
                if (repayPeriod == userRepayPlan.getRepayPeriod()) {
                    form.setManageFee(userRepayPlan.getRepayFee().toString());
                    form.setRepayTotal(userRepayPlan.getRepayAccountAll().toString());// 此处计算的是还款总额包含管理费
                    form.setRepayAccount(userRepayPlan.getRepayAccount().add(userRepayPlan.getChargeInterest()).add(userRepayPlan.getDelayInterest()).add(userRepayPlan.getLateInterest())
                            .toString());
                    form.setRepayCapital(userRepayPlan.getRepayCapital().toString());
                    form.setRepayInterest(userRepayPlan.getRepayInterest().add(userRepayPlan.getChargeInterest()).add(userRepayPlan.getDelayInterest()).add(userRepayPlan.getLateInterest()).toString());
                    form.setShouldInterest(userRepayPlan.getRepayInterest().toString());
                    form.setAdvanceStatus(userRepayPlan.getAdvanceStatus().toString());
                    form.setChargeDays(userRepayPlan.getChargeDays().toString());
                    form.setChargeInterest(userRepayPlan.getChargeInterest().multiply(new BigDecimal("-1")).toString());
                    form.setDelayDays(userRepayPlan.getDelayDays().toString());
                    form.setDelayInterest(userRepayPlan.getDelayInterest().toString());
                    form.setLateDays(userRepayPlan.getLateDays().toString());
                    form.setLateInterest(userRepayPlan.getLateInterest().toString());
                }
                List<RepayRecoverPlanBean> userRecoversDetails = userRepayPlan.getRecoverPlanList();
                List<ProjectRepayDetailVO> userRepayDetails = new ArrayList<ProjectRepayDetailVO>();
                for (int j = 0; j < userRecoversDetails.size(); j++) {
                    RepayRecoverPlanBean userRecoverPlan = userRecoversDetails.get(j);
                    BigDecimal recoverAccount = userRecoverPlan.getRecoverAccountOld();
                    // 如果发生债转
                    int hjhFlag = 0;//是否计划债转
                    List<RepayCreditRepayBean> creditRepays = userRecoverPlan.getCreditRepayList();
                    if (creditRepays != null && creditRepays.size() > 0) {
                        // 循环遍历添加记录
                        for (int k = 0; k < creditRepays.size(); k++) {
                            RepayCreditRepayBean creditRepay = creditRepays.get(k);
                            ProjectRepayDetailVO userRepayDetail = new ProjectRepayDetailVO();
                            userRepayDetail.setRepayAccount(creditRepay.getAssignAccount().toString());
                            userRepayDetail.setRepayCapital(creditRepay.getAssignCapital().toString());
                            userRepayDetail.setRepayInterest(creditRepay.getAssignInterest().toString());
                            userRepayDetail.setChargeDays(userRecoverPlan.getChargeDays().toString());

                            userRepayDetail.setChargeInterest(creditRepay.getChargeInterest().multiply(new BigDecimal("-1")).toString());
                            if(creditRepay.getChargeInterest().equals(BigDecimal.ZERO)){
                                userRepayDetail.setChargeInterest("0.00");
                            }
                            userRepayDetail.setDelayDays(userRecoverPlan.getDelayDays().toString());
                            userRepayDetail.setDelayInterest(creditRepay.getDelayInterest().toString());
                            userRepayDetail.setManageFee(creditRepay.getManageFee().toString());
                            userRepayDetail.setLateDays(userRecoverPlan.getLateDays().toString());
                            userRepayDetail.setLateInterest(creditRepay.getLateInterest().toString());
                            userRepayDetail.setAdvanceStatus(userRecoverPlan.getAdvanceStatus().toString());
                            userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(Integer.valueOf(userRecoverPlan.getRecoverTime())));
                            BigDecimal total = BigDecimal.ZERO;
                            if (creditRepay.getStatus() == 1) {
                                total = creditRepay.getAssignRepayAccount().add(creditRepay.getManageFee());
                            } else {
                                total = creditRepay.getAssignTotal();
                            }
                            userRepayDetail.setRepayTotal(total.toString());
                            userRepayDetail.setStatus(creditRepay.getStatus().toString());
                            userRepayDetail.setUserId(creditRepay.getUserId().toString());
                            String userName = this.searchUserNameById(creditRepay.getUserId());
                            String userNameStr = userName.substring(0, 1).concat("**");
                            userRepayDetail.setUserName(userNameStr);
                            userRepayDetails.add(userRepayDetail);
                        }
                    }
                    //计划债转列表
                    List<HjhDebtCreditRepayBean> hjhCreditRepayList = userRecoverPlan.getHjhCreditRepayList();
                    BigDecimal sumAccount = BigDecimal.ZERO;
                    if (hjhCreditRepayList != null && hjhCreditRepayList.size() > 0) {
                        hjhFlag = 1;
                        for (int k = 0; k < hjhCreditRepayList.size(); k++) {
                            HjhDebtCreditRepayBean creditRepay = hjhCreditRepayList.get(k);
                            ProjectRepayDetailVO userRepayDetail = new ProjectRepayDetailVO();
                            sumAccount = sumAccount.add(creditRepay.getRepayAccount());
                            userRepayDetail.setRepayAccount(creditRepay.getRepayAccount().toString());
                            userRepayDetail.setRepayCapital(creditRepay.getRepayCapital().toString());
                            userRepayDetail.setRepayInterest(creditRepay.getRepayInterest().toString());
                            userRepayDetail.setChargeDays(userRecoverPlan.getChargeDays().toString());
                            userRepayDetail.setChargeInterest(String.valueOf(creditRepay.getRepayAdvanceInterest().multiply(new BigDecimal("-1"))));
                            if(creditRepay.getRepayAdvanceInterest().equals(BigDecimal.ZERO)){
                                userRepayDetail.setChargeInterest("0.00");
                            }
                            userRepayDetail.setDelayDays(userRecoverPlan.getDelayDays().toString());
                            userRepayDetail.setDelayInterest(creditRepay.getRepayDelayInterest().toString());
                            userRepayDetail.setManageFee(creditRepay.getManageFee().toString());
                            userRepayDetail.setLateDays(userRecoverPlan.getLateDays().toString());
                            userRepayDetail.setLateInterest(creditRepay.getRepayLateInterest().toString());
                            userRepayDetail.setAdvanceStatus(userRecoverPlan.getAdvanceStatus().toString());
                            userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(Integer.valueOf(userRecoverPlan.getRecoverTime())));
                            BigDecimal total = new BigDecimal("0");
                            if (creditRepay.getRepayStatus() == 1) {
                                total = creditRepay.getReceiveAccountYes().add(creditRepay.getManageFee());
                            } else {
                                total = creditRepay.getAssignTotal();
                            }
                            userRepayDetail.setRepayTotal(total.toString());
                            userRepayDetail.setStatus(creditRepay.getRepayStatus().toString());
                            userRepayDetail.setUserId(creditRepay.getUserId().toString());
                            String userName = this.searchUserNameById(creditRepay.getUserId());
                            String userNameStr = userName.substring(0, 1).concat("**");
                            userRepayDetail.setUserName(userNameStr);
                            userRepayDetails.add(userRepayDetail);
                        }
                    }
                    boolean overFlag = isOverUndertake(userRecoverPlan,recoverAccount,sumAccount,true,hjhFlag);
                    Integer recoverStatus = userRecoverPlan.getRecoverStatus();
                    if(hjhFlag == 0){
                        if(userRecoverPlan.getCreditStatus() == 2){
                            overFlag =false;
                        }
                    }
                    if (overFlag) {
                        ProjectRepayDetailVO userRepayDetail = new ProjectRepayDetailVO();
                        userRepayDetail.setRepayAccount(userRecoverPlan.getRecoverAccount().toString());
                        userRepayDetail.setRepayCapital(userRecoverPlan.getRecoverCapital().toString());
                        userRepayDetail.setRepayInterest(userRecoverPlan.getRecoverInterest().toString());
                        userRepayDetail.setChargeDays(userRecoverPlan.getChargeDays().toString());
                        if (recoverStatus == 1) {//已还款
                            userRepayDetail.setChargeInterest(userRecoverPlan.getChargeInterestOld().multiply(new BigDecimal("-1")).toString());
                        }else{
                            userRepayDetail.setChargeInterest(userRecoverPlan.getChargeInterest().multiply(new BigDecimal("-1")).toString());
                        }
                        userRepayDetail.setDelayDays(userRecoverPlan.getDelayDays().toString());
                        if (recoverStatus == 1) {
                            userRepayDetail.setDelayInterest(userRecoverPlan.getDelayInterestOld().toString());
                        }else{
                            userRepayDetail.setDelayInterest(userRecoverPlan.getDelayInterest().toString());
                        }
                        userRepayDetail.setManageFee(userRecoverPlan.getRecoverFee().toString());
                        userRepayDetail.setLateDays(userRecoverPlan.getLateDays().toString());
                        if (recoverStatus == 1) {
                            userRepayDetail.setLateInterest(userRecoverPlan.getLateInterestOld().toString());
                        }else{
                            userRepayDetail.setLateInterest(userRecoverPlan.getLateInterest().toString());
                        }
                        userRepayDetail.setAdvanceStatus(userRecoverPlan.getAdvanceStatus().toString());
                        userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(Integer.valueOf(userRecoverPlan.getRecoverTime())));
                        BigDecimal total = new BigDecimal("0");
                        if (recoverStatus == 1) {
                            total = userRecoverPlan.getRecoverAccountYesOld().subtract(sumAccount).add(userRecoverPlan.getRecoverFee());
                        } else {
                            // recover中account未更新
                            total = userRecoverPlan.getRecoverTotal();
                        }
                        userRepayDetail.setRepayTotal(total.toString());
                        userRepayDetail.setStatus(recoverStatus.toString());
                        userRepayDetail.setUserId(userRecoverPlan.getUserId().toString());
                        String userName = this.searchUserNameById(userRecoverPlan.getUserId());
                        String userNameStr = userName.substring(0, 1).concat("**");
                        userRepayDetail.setUserName(userNameStr);
                        userRepayDetails.add(userRepayDetail);
                    }
                }
                userRepayBean.setUserRepayDetailList(userRepayDetails);
                recoverList.add(userRepayBean);
            }
            form.setUserRepayList(recoverList);
        }
    }

    /***
     * 计算用户分期还款本期应还金额
     * @param repay
     * @param borrow
     * @param period
     * @return
     * @throws Exception
     */
    @Deprecated
    private BigDecimal calculateRepayPlan(RepayBean repay, BorrowCustomizeVO borrow, int period) throws Exception {

        List<RepayDetailBean> borrowRepayPlanDeails = new ArrayList<RepayDetailBean>();
        List<BorrowRepayPlan> borrowRepayPlans = searchRepayPlan(repay.getUserId(), borrow.getBorrowNid());
        BigDecimal repayAccountAll = new BigDecimal("0");
        if (borrowRepayPlans != null && borrowRepayPlans.size() > 0) {
            // 用户实际还款额
            for (int i = 0; i < borrowRepayPlans.size(); i++) {
                RepayDetailBean repayPlanDetail = new RepayDetailBean();
                BorrowRepayPlan borrowRepayPlan = borrowRepayPlans.get(i);
                if (period == borrowRepayPlan.getRepayPeriod()) {
                    String repayTimeStart = null;
                    if (i == 0) {
                        repayTimeStart = borrowRepayPlan.getCreateTime().toString();
                    } else {
                        repayTimeStart = borrowRepayPlans.get(i - 1).getRepayTime().toString();
                    }


                    // 当期是下一期的话，不能超前还的检查
                    String repayTimeEnd = borrowRepayPlan.getRepayTime().toString();
                    // 用户计划还款时间
                    Date repayEndDate = GetDate.getDate(Integer.parseInt(repayTimeEnd));
                    Date repayStartDate = org.apache.commons.lang.time.DateUtils.addMonths(repayEndDate, -1);

                    int curPlanStart = GetDate.getIntYYMMDD(repayStartDate);
                    int nowDate = GetDate.getIntYYMMDD(new Date());

                    if (nowDate < curPlanStart) {
                        throw new Exception("不能超前还，只能全部结清");
                    }

                    // 计算还款期的数据
                    BeanUtils.copyProperties(borrowRepayPlan, repayPlanDetail);
                    this.calculateRecoverPlan(repayPlanDetail, borrow, period, repayTimeStart);
                    borrowRepayPlanDeails.add(repayPlanDetail);
                    repay.setRepayAccountAll(repayPlanDetail.getRepayAccountAll());
                    repay.setRepayAccount(repayPlanDetail.getRepayAccount());
                    repay.setRepayCapital(repayPlanDetail.getRepayCapital());
                    repay.setRepayInterest(repayPlanDetail.getRepayInterest());
                    repay.setRepayFee(repayPlanDetail.getRepayFee());
                    repay.setChargeDays(repayPlanDetail.getChargeDays());
                    repay.setChargeInterest(repayPlanDetail.getChargeInterest());
                    repay.setDelayDays(repayPlanDetail.getDelayDays());
                    repay.setDelayInterest(repayPlanDetail.getDelayInterest());
                    repay.setLateDays(repayPlanDetail.getLateDays());
                    repay.setLateInterest(repayPlanDetail.getLateInterest());
                    repayAccountAll = repayPlanDetail.getRepayAccountAll();
                } else {
                    BeanUtils.copyProperties(borrowRepayPlan, repayPlanDetail);
                    this.calculateRecoverPlan(repayPlanDetail, borrow);
                    borrowRepayPlanDeails.add(repayPlanDetail);
                }
            }
            repay.setRepayPlanList(borrowRepayPlanDeails);
        }
        return repayAccountAll;
    }

    /**
     * 分期还款 全部结清 计算各期数据
     * @param form
     * @param isAllRepay
     * @param userId
     * @param borrow
     * @param repayByTerm
     * @throws ParseException
     */
    @Deprecated
    private void setRecoverPlanAllDetail(ProjectBeanVO form, boolean isAllRepay, String userId,BorrowCustomizeVO borrow,RepayBean repayByTerm) throws ParseException {

        String borrowNid = borrow.getBorrowNid();
        // 还款总期数
        int periodTotal = Integer.valueOf(borrow.getBorrowPeriod());

        // 计算当前还款期数
        int repayPeriod = periodTotal - repayByTerm.getRepayPeriod() + 1;
        // 如果用户不是还款最后一期
        if (repayPeriod > periodTotal) {
            form.setRepayStatus("1");
        }
        // 设置当前的还款期数
        form.setRepayPeriod(String.valueOf(repayPeriod));
        // 获取统计的用户还款计划列表
        List<RepayDetailBean> userRepayPlans = repayByTerm.getRepayPlanList();
        if (userRepayPlans != null && userRepayPlans.size() > 0) {
            List<ProjectRepayVO> recoverList = new ArrayList<ProjectRepayVO>();
            // 遍历计划还款信息，拼接数据
            for (int i = 0; i < userRepayPlans.size(); i++) {
                // 获取用户的还款信息
                RepayDetailBean userRepayPlan = userRepayPlans.get(i);
                // 声明需拼接数据的实体
                ProjectRepayVO userRepayBean = new ProjectRepayVO();
                // 如果本期已经还款完成
                if (userRepayPlan.getRepayStatus() == 1) {
                    // 获取本期的用户已还款总额
                    userRepayBean.setRepayTotal(userRepayPlan.getRepayAccountYes().add(userRepayPlan.getRepayFee()).toString());
                }
                // 用户未还款本息
                else {
                    // 此处分期计算的是本息+管理费
                    userRepayBean.setRepayTotal(userRepayPlan.getRepayAccountAll().toString());
                }
                userRepayBean.setRepayAccount(userRepayPlan.getRepayAccount().add(userRepayPlan.getChargeInterest()).add(userRepayPlan.getDelayInterest()).add(userRepayPlan.getLateInterest())
                        .toString());// 设置本期的用户本息和
                userRepayBean.setRepayCapital(userRepayPlan.getRepayCapital().toString());// 用户未还款本息
                userRepayBean.setRepayInterest(userRepayPlan.getRepayInterest().toString()); // 设置本期的用户利息
                userRepayBean.setUserId(userRepayPlan.getUserId().toString());
                userRepayBean.setRepayPeriod(userRepayPlan.getRepayPeriod().toString());
                userRepayBean.setAdvanceStatus(userRepayPlan.getAdvanceStatus().toString());
                userRepayBean.setStatus(userRepayPlan.getRepayStatus().toString());
                userRepayBean.setRepayTime(GetDate.getDateMyTimeInMillis(Integer.valueOf(userRepayPlan.getRepayTime())));
                userRepayBean.setChargeDays(userRepayPlan.getChargeDays().toString());
                userRepayBean.setChargeInterest(userRepayPlan.getChargeInterest().multiply(new BigDecimal("-1")).toString());
                if(userRepayPlan.getChargeInterest().equals(BigDecimal.ZERO)){
                    userRepayBean.setChargeInterest("0.00");
                }
                userRepayBean.setDelayDays(userRepayPlan.getDelayDays().toString());
                userRepayBean.setDelayInterest(userRepayPlan.getDelayInterest().toString());
                userRepayBean.setManageFee(userRepayPlan.getRepayFee().toString());
                userRepayBean.setLateDays(userRepayPlan.getLateDays().toString());
                userRepayBean.setLateInterest(userRepayPlan.getLateInterest().toString());
                List<RepayRecoverPlanBean> userRecoversDetails = userRepayPlan.getRecoverPlanList();
                List<ProjectRepayDetailVO> userRepayDetails = new ArrayList<ProjectRepayDetailVO>();
                for (int j = 0; j < userRecoversDetails.size(); j++) {
                    RepayRecoverPlanBean userRecoverPlan = userRecoversDetails.get(j);
                    Integer id = userRecoverPlan.getId();
//		            BorrowRecoverPlan planInfo = this.borrowRecoverPlanMapper.selectByPrimaryKey(id);

                    BigDecimal recoverAccount = userRecoverPlan.getRecoverAccountOld();
                    // 如果发生债转
                    int hjhFlag = 0;//是否计划债转
                    List<RepayCreditRepayBean> creditRepays = userRecoverPlan.getCreditRepayList();
                    if (creditRepays != null && creditRepays.size() > 0) {
                        // 循环遍历添加记录
                        for (int k = 0; k < creditRepays.size(); k++) {
                            RepayCreditRepayBean creditRepay = creditRepays.get(k);
                            ProjectRepayDetailVO userRepayDetail = new ProjectRepayDetailVO();
                            userRepayDetail.setRepayAccount(creditRepay.getAssignAccount().toString());
                            userRepayDetail.setRepayCapital(creditRepay.getAssignCapital().toString());
                            userRepayDetail.setRepayInterest(creditRepay.getAssignInterest().toString());
                            userRepayDetail.setChargeDays(userRecoverPlan.getChargeDays().toString());
                            userRepayDetail.setChargeInterest(creditRepay.getChargeInterest().multiply(new BigDecimal("-1")).toString());
                            if(creditRepay.getChargeInterest().equals(BigDecimal.ZERO)){
                                userRepayDetail.setChargeInterest("0.00");
                            }
                            userRepayDetail.setDelayDays(userRecoverPlan.getDelayDays().toString());
                            userRepayDetail.setDelayInterest(creditRepay.getDelayInterest().toString());
                            userRepayDetail.setManageFee(creditRepay.getManageFee().toString());
                            userRepayDetail.setLateDays(userRecoverPlan.getLateDays().toString());
                            userRepayDetail.setLateInterest(creditRepay.getLateInterest().toString());
                            userRepayDetail.setAdvanceStatus(userRecoverPlan.getAdvanceStatus().toString());
                            userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(Integer.valueOf(userRecoverPlan.getRecoverTime())));
                            BigDecimal total = new BigDecimal("0");
                            if (creditRepay.getStatus() == 1) {
                                total = creditRepay.getAssignRepayAccount().add(creditRepay.getManageFee());
                            } else {
                                total = creditRepay.getAssignTotal();
                            }
                            userRepayDetail.setRepayTotal(total.toString());
                            userRepayDetail.setStatus(creditRepay.getStatus().toString());
                            userRepayDetail.setUserId(creditRepay.getUserId().toString());
                            String userName = this.searchUserNameById(creditRepay.getUserId());
                            String userNameStr = userName.substring(0, 1).concat("**");
                            userRepayDetail.setUserName(userNameStr);
                            userRepayDetails.add(userRepayDetail);
                        }
                    }
                    //计划债转列表
                    List<HjhDebtCreditRepayBean> hjhCreditRepayList = userRecoverPlan.getHjhCreditRepayList();
                    BigDecimal sumAccount = BigDecimal.ZERO;
                    if (hjhCreditRepayList != null && hjhCreditRepayList.size() > 0) {
                        hjhFlag = 1;
                        for (int k = 0; k < hjhCreditRepayList.size(); k++) {
                            HjhDebtCreditRepayBean creditRepay = hjhCreditRepayList.get(k);
                            ProjectRepayDetailVO userRepayDetail = new ProjectRepayDetailVO();
                            sumAccount = sumAccount.add(creditRepay.getRepayAccount());
                            userRepayDetail.setRepayAccount(creditRepay.getRepayAccount().toString());
                            userRepayDetail.setRepayCapital(creditRepay.getRepayCapital().toString());
                            userRepayDetail.setRepayInterest(creditRepay.getRepayInterest().toString());
                            userRepayDetail.setChargeDays(userRecoverPlan.getChargeDays().toString());
                            userRepayDetail.setChargeInterest(creditRepay.getRepayAdvanceInterest().multiply(new BigDecimal("-1")).toString());
                            if(creditRepay.getRepayAdvanceInterest().equals(BigDecimal.ZERO)){
                                userRepayDetail.setChargeInterest("0.00");
                            }
                            userRepayDetail.setDelayDays(userRecoverPlan.getDelayDays().toString());
                            userRepayDetail.setDelayInterest(creditRepay.getRepayDelayInterest().toString());
                            userRepayDetail.setManageFee(creditRepay.getManageFee().toString());
                            userRepayDetail.setLateDays(userRecoverPlan.getLateDays().toString());
                            userRepayDetail.setLateInterest(creditRepay.getRepayLateInterest().toString());
                            userRepayDetail.setAdvanceStatus(userRecoverPlan.getAdvanceStatus().toString());
                            userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(Integer.valueOf(userRecoverPlan.getRecoverTime())));
                            BigDecimal total = new BigDecimal("0");
                            if (creditRepay.getRepayStatus() == 1) {
                                total = creditRepay.getReceiveAccountYes().add(creditRepay.getManageFee());
                            } else {
                                total = creditRepay.getAssignTotal();
                            }
                            userRepayDetail.setRepayTotal(total.toString());
                            userRepayDetail.setStatus(creditRepay.getRepayStatus().toString());
                            userRepayDetail.setUserId(creditRepay.getUserId().toString());
                            String userName = this.searchUserNameById(creditRepay.getUserId());
                            String userNameStr = userName.substring(0, 1).concat("**");
                            userRepayDetail.setUserName(userNameStr);
                            userRepayDetails.add(userRepayDetail);
                        }
                    }
//		            BorrowRecover borrowRecover = getBorrowRecoverByPlanInfo(userRecoverPlan);
                    boolean overFlag = isOverUndertake(userRecoverPlan,recoverAccount,sumAccount,true,hjhFlag);
                    Integer recoverStatus = userRecoverPlan.getRecoverStatus();
                    if(hjhFlag == 0){
                        if(userRecoverPlan.getCreditStatus() == 2){
                            overFlag = false;
                        }
                    }
                    if (overFlag) {
                        ProjectRepayDetailVO userRepayDetail = new ProjectRepayDetailVO();
                        userRepayDetail.setRepayAccount(userRecoverPlan.getRecoverAccount().toString());
                        userRepayDetail.setRepayCapital(userRecoverPlan.getRecoverCapital().toString());
                        userRepayDetail.setRepayInterest(userRecoverPlan.getRecoverInterest().toString());
                        userRepayDetail.setChargeDays(userRecoverPlan.getChargeDays().toString());
                        if (recoverStatus == 1) {//已还款
                            userRepayDetail.setChargeInterest(userRecoverPlan.getChargeInterestOld().multiply(new BigDecimal("-1")).toString());
                        }else{
                            userRepayDetail.setChargeInterest(userRecoverPlan.getChargeInterest().multiply(new BigDecimal("-1")).toString());
                        }
                        if("0".equals(userRepayDetail.getChargeInterest())){
                            userRepayDetail.setChargeInterest("0.00");
                        }
                        userRepayDetail.setDelayDays(userRecoverPlan.getDelayDays().toString());
                        if (recoverStatus == 1) {
                            userRepayDetail.setDelayInterest(userRecoverPlan.getDelayInterestOld().toString());
                        }else{
                            userRepayDetail.setDelayInterest(userRecoverPlan.getDelayInterest().toString());
                        }
                        userRepayDetail.setManageFee(userRecoverPlan.getRecoverFee().toString());
                        userRepayDetail.setLateDays(userRecoverPlan.getLateDays().toString());
                        if (recoverStatus == 1) {
                            userRepayDetail.setLateInterest(userRecoverPlan.getLateInterestOld().toString());
                        }else{
                            userRepayDetail.setLateInterest(userRecoverPlan.getLateInterest().toString());
                        }
                        userRepayDetail.setAdvanceStatus(userRecoverPlan.getAdvanceStatus().toString());
                        userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(Integer.valueOf(userRecoverPlan.getRecoverTime())));
                        BigDecimal total = new BigDecimal("0");
                        if (recoverStatus == 1) {
                            total = userRecoverPlan.getRecoverAccountYesOld().subtract(sumAccount).add(userRecoverPlan.getRecoverFee());
                        } else {
                            // recover中account未更新
                            total = userRecoverPlan.getRecoverTotal();
                        }
                        userRepayDetail.setRepayTotal(total.toString());
                        userRepayDetail.setStatus(recoverStatus.toString());
                        userRepayDetail.setUserId(userRecoverPlan.getUserId().toString());
                        String userName = this.searchUserNameById(userRecoverPlan.getUserId());
                        String userNameStr = userName.substring(0, 1).concat("**");
                        userRepayDetail.setUserName(userNameStr);
                        userRepayDetails.add(userRepayDetail);
                    }
                }
                userRepayBean.setUserRepayDetailList(userRepayDetails);
                recoverList.add(userRepayBean);
            }
            form.setUserRepayList(recoverList);

            form.setManageFee(repayByTerm.getRepayFee().toString());
            form.setRepayTotal(repayByTerm.getRepayAccountAll().toString());// 此处计算的是还款总额包含管理费
            form.setRepayAccount(repayByTerm.getRepayAccount().add(repayByTerm.getChargeInterest()).add(repayByTerm.getDelayInterest()).add(repayByTerm.getLateInterest())
                    .toString());
            form.setRepayCapital(repayByTerm.getRepayCapital().toString());
            form.setRepayInterest(repayByTerm.getRepayInterest().add(repayByTerm.getChargeInterest()).add(repayByTerm.getDelayInterest()).add(repayByTerm.getLateInterest()).toString());
            form.setShouldInterest(repayByTerm.getRepayInterest().toString());
            form.setAdvanceStatus(repayByTerm.getAdvanceStatus().toString());
            form.setChargeDays(repayByTerm.getChargeDays().toString());
            form.setChargeInterest(repayByTerm.getChargeInterest().toString());
//            form.setDelayDays(repayByTerm.getDelayDays().toString());
//            form.setDelayInterest(repayByTerm.getDelayInterest().toString());
//            form.setLateDays(repayByTerm.getLateDays().toString());
//            form.setLateInterest(repayByTerm.getLateInterest().toString());

        }
    }

    public List<BorrowRepayPlan> searchRepayPlan(int userId, String borrowNid) {
        BorrowRepayPlanExample borrowRepayPlanExample = new BorrowRepayPlanExample();
        BorrowRepayPlanExample.Criteria borrowRepayPlanCrt = borrowRepayPlanExample.createCriteria();
        borrowRepayPlanCrt.andUserIdEqualTo(userId);
        borrowRepayPlanCrt.andBorrowNidEqualTo(borrowNid);
        List<BorrowRepayPlan> borrowRepayPlans = borrowRepayPlanMapper.selectByExample(borrowRepayPlanExample);
        return borrowRepayPlans;
    }

    public List<BorrowRepayPlan> searchRepayPlanAll(int userId, String borrowNid) {
        BorrowRepayPlanExample borrowRepayPlanExample = new BorrowRepayPlanExample();
        BorrowRepayPlanExample.Criteria borrowRepayPlanCrt = borrowRepayPlanExample.createCriteria();
        borrowRepayPlanCrt.andUserIdEqualTo(userId);
        borrowRepayPlanCrt.andBorrowNidEqualTo(borrowNid);
        borrowRepayPlanExample.setOrderByClause("repay_period");
        List<BorrowRepayPlan> borrowRepayPlans = borrowRepayPlanMapper.selectByExample(borrowRepayPlanExample);
        return borrowRepayPlans;
    }

    public BorrowRepayPlan searchRepayPlan(int userId, String borrowNid, int period) {
        BorrowRepayPlanExample borrowRepayPlanExample = new BorrowRepayPlanExample();
        BorrowRepayPlanExample.Criteria borrowRepayPlanCrt = borrowRepayPlanExample.createCriteria();
        borrowRepayPlanCrt.andUserIdEqualTo(userId);
        borrowRepayPlanCrt.andBorrowNidEqualTo(borrowNid);
        borrowRepayPlanCrt.andRepayPeriodEqualTo(period);
        List<BorrowRepayPlan> borrowRepayPlans = borrowRepayPlanMapper.selectByExample(borrowRepayPlanExample);
        if (borrowRepayPlans != null && borrowRepayPlans.size() == 1) {
            return borrowRepayPlans.get(0);
        } else {
            return null;
        }
    }
    /***
     * 计算用户分期还款本期应还金额
     * @param repay
     * @param borrow
     * @param period
     * @return
     * @throws Exception
     */
    @Deprecated
    private BigDecimal calculateRepayPlanAll(RepayBean repay, BorrowCustomizeVO borrow, int period) throws Exception {

        List<RepayDetailBean> borrowRepayPlanDeails = new ArrayList<RepayDetailBean>();
        List<BorrowRepayPlan> borrowRepayPlans = searchRepayPlanAll(repay.getUserId(), borrow.getBorrowNid());
        BigDecimal repayAccountAll = BigDecimal.ZERO;

        // 一下值先清0，因为是从数据库repay 表复制过来的
        repay.setBorrowPeriod(borrow.getBorrowPeriod().toString());
        repay.setRepayAccountAll(BigDecimal.ZERO);
        repay.setRepayAccount(BigDecimal.ZERO);
        repay.setRepayCapital(BigDecimal.ZERO);
        repay.setRepayInterest(BigDecimal.ZERO);
        repay.setRepayFee(BigDecimal.ZERO);
        repay.setChargeInterest(BigDecimal.ZERO);
        repay.setAdvanceStatus(1);// 属于提前还款

        int totalPeriod = Integer.valueOf(borrow.getBorrowPeriod()) - period;
        if (borrowRepayPlans != null && borrowRepayPlans.size() > 0) {
            // 用户实际还款额
            for (int i = 0; i < borrowRepayPlans.size(); i++) {
                RepayDetailBean repayPlanDetail = new RepayDetailBean();
                BorrowRepayPlan borrowRepayPlan = borrowRepayPlans.get(i);
                // 当前期
                if (period == borrowRepayPlan.getRepayPeriod()) {

                    // 当前期已经还款
                    if(borrowRepayPlan.getRepayStatus() == 1) {

                        BeanUtils.copyProperties(borrowRepayPlan, repayPlanDetail);
                        calculateRecoverPlan(repayPlanDetail, borrow);
                        borrowRepayPlanDeails.add(repayPlanDetail);

                    }else {
                        // 查看当前还款时间 是否 在当前期里头,如果超前则不算当期还款

                        String repayTimeStart = null;
                        if (i == 0) {
                            repayTimeStart = borrowRepayPlan.getCreateTime().toString();
                        } else {
                            repayTimeStart = borrowRepayPlans.get(i - 1).getRepayTime().toString();
                        }

                        int curPlanStart = GetDate.getIntYYMMDD(Integer.parseInt(repayTimeStart));
                        int nowDate = GetDate.getIntYYMMDD(new Date());

                        // 超期还
                        if(i != 0 && nowDate <= curPlanStart) {
                            // 当前期也算的话，需要加上当前期
                            totalPeriod = Integer.valueOf(borrow.getBorrowPeriod()) - period + 1;
                            // 计算还款期的数据
                            BeanUtils.copyProperties(borrowRepayPlan, repayPlanDetail);
                            calculateRecoverPlanAll(repayPlanDetail, borrow, totalPeriod);
                            borrowRepayPlanDeails.add(repayPlanDetail);

                            repay.setRepayAccountAll(repay.getRepayAccountAll().add(repayPlanDetail.getRepayAccountAll()));
                            repay.setRepayAccount(repay.getRepayAccount().add(repayPlanDetail.getRepayAccount()));
                            repay.setRepayCapital(repay.getRepayCapital().add(repayPlanDetail.getRepayCapital()));
                            repay.setRepayInterest(repay.getRepayInterest().add(repayPlanDetail.getRepayInterest()));
                            repay.setRepayFee(repay.getRepayFee().add(repayPlanDetail.getRepayFee()));
                            repay.setChargeDays(repayPlanDetail.getChargeDays());
                            repay.setChargeInterest(repay.getChargeInterest().add(repayPlanDetail.getChargeInterest()));

                            repayAccountAll = repayPlanDetail.getRepayAccountAll();

                        }else {

                            // 计算还款期的数据
                            BeanUtils.copyProperties(borrowRepayPlan, repayPlanDetail);
                            this.calculateRecoverPlan(repayPlanDetail, borrow, period, null);
                            borrowRepayPlanDeails.add(repayPlanDetail);

                            if(repayPlanDetail.getAdvanceStatus() == 2 || repayPlanDetail.getAdvanceStatus() == 3) {
                                throw new Exception("当期延期或者逾期，不能全部结清");
                            }

                            repay.setRepayAccountAll(repay.getRepayAccountAll().add(repayPlanDetail.getRepayAccountAll()));
                            repay.setRepayAccount(repay.getRepayAccount().add(repayPlanDetail.getRepayAccount()));
                            repay.setRepayCapital(repay.getRepayCapital().add(repayPlanDetail.getRepayCapital()));
                            repay.setRepayInterest(repay.getRepayInterest().add(repayPlanDetail.getRepayInterest()));
                            repay.setRepayFee(repay.getRepayFee().add(repayPlanDetail.getRepayFee()));
                            repay.setChargeDays(repayPlanDetail.getChargeDays());
                            repay.setChargeInterest(repay.getChargeInterest().add(repayPlanDetail.getChargeInterest()));

                            repay.setDelayDays(repayPlanDetail.getDelayDays());
                            repay.setDelayInterest(repayPlanDetail.getDelayInterest());
                            repay.setLateDays(repayPlanDetail.getLateDays());
                            repay.setLateInterest(repayPlanDetail.getLateInterest());

                            repayAccountAll = repayPlanDetail.getRepayAccountAll();
                        }
                    }
                } else if(borrowRepayPlan.getRepayPeriod() > period) {

                    // 计算还款期的数据
                    BeanUtils.copyProperties(borrowRepayPlan, repayPlanDetail);
                    calculateRecoverPlanAll(repayPlanDetail, borrow, totalPeriod);
                    borrowRepayPlanDeails.add(repayPlanDetail);

                    repay.setRepayAccountAll(repay.getRepayAccountAll().add(repayPlanDetail.getRepayAccountAll()));
                    repay.setRepayAccount(repay.getRepayAccount().add(repayPlanDetail.getRepayAccount()));
                    repay.setRepayCapital(repay.getRepayCapital().add(repayPlanDetail.getRepayCapital()));
                    repay.setRepayInterest(repay.getRepayInterest().add(repayPlanDetail.getRepayInterest()));
                    repay.setRepayFee(repay.getRepayFee().add(repayPlanDetail.getRepayFee()));
                    repay.setChargeDays(repayPlanDetail.getChargeDays());
                    repay.setChargeInterest(repay.getChargeInterest().add(repayPlanDetail.getChargeInterest()));
//                    repay.setDelayDays(repayPlanDetail.getDelayDays());
//                    repay.setDelayInterest(repayPlanDetail.getDelayInterest());
//                    repay.setLateDays(repayPlanDetail.getLateDays());
//                    repay.setLateInterest(repayPlanDetail.getLateInterest());

                    repayAccountAll = repayPlanDetail.getRepayAccountAll();

                } else {
                    BeanUtils.copyProperties(borrowRepayPlan, repayPlanDetail);
                    this.calculateRecoverPlan(repayPlanDetail, borrow);
                    borrowRepayPlanDeails.add(repayPlanDetail);
                }
            }
            repay.setRepayPlanList(borrowRepayPlanDeails);
        }
        return repayAccountAll;
    }

    /**
     * 统计分期还款用户正常还款的总标
     * @param borrowRepayPlan
     * @param borrow
     * @param totalPeriod
     */
    @Deprecated
    private void calculateRecoverPlanAll(RepayDetailBean borrowRepayPlan, BorrowCustomizeVO borrow,int totalPeriod) {

        // 项目编号
        String borrowNid = borrow.getBorrowNid();
        // 还款方式
        String borrowStyle = borrow.getBorrowStyle();
        // 管理费率
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : Integer.valueOf(borrow.getVerifyTime());
        // 项目总期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : Integer.valueOf(borrow.getBorrowPeriod());
        // 还款期数
        int repayPeriod = borrowRepayPlan.getRepayPeriod();
        // ====> 是否分期最后一期
        boolean isLastPeriod = (borrowPeriod==repayPeriod?true:false);

        List<BorrowRecover> borrowRecoverList = this.selectBorrowRecoverList(borrow.getBorrowNid());
        List<BorrowRecoverPlan> borrowRecoverPlans = searchBorrowRecoverPlan(borrow.getBorrowNid(), borrowRepayPlan.getRepayPeriod());

        List<RepayRecoverPlanBean> repayRecoverPlanList = new ArrayList<RepayRecoverPlanBean>();

        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        BigDecimal repayChargeInterest = BigDecimal.ZERO;// 提前还款利息
        BigDecimal repayChargePenaltyInterest = BigDecimal.ZERO;// 提前还款罚息

        if (borrowRecoverList == null || borrowRecoverList.size() <= 0) {
            _log.error(borrow.getBorrowNid()+" 没有recover 数据");
            return;
        }
        if (borrowRecoverPlans == null || borrowRecoverPlans.size() <= 0) {
            _log.error(borrow.getBorrowNid()+"  还款期："+borrowRepayPlan.getRepayPeriod()+" 没有recoverPlan 数据");
            return;
        }

        for (int i = 0; i < borrowRecoverList.size(); i++) {

            BorrowRecover borrowRecover = borrowRecoverList.get(i);
            String recoverNid = borrowRecover.getNid();// 出借订单号
            int recoverUserId = borrowRecover.getUserId();// 出借用户userId

            BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
            BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户还款管理费
            BigDecimal userChargeInterest = BigDecimal.ZERO;// 计算用户提前还款利息
            BigDecimal userChargePenaltyInterest = BigDecimal.ZERO;// 计算用户提前还款罚息
            //计算债权总的违约金

            for (int j = 0; j < borrowRecoverPlans.size(); j++) {
                BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlans.get(j);
                String recoverPlanNid = borrowRecoverPlan.getNid();// 出借订单号
                int recoverPlanUserId = borrowRecoverPlan.getUserId();// 出借用户userId

                if (recoverPlanNid.equals(recoverNid) && recoverUserId == recoverPlanUserId) {

                    RepayRecoverPlanBean repayRecoverPlanBean = new RepayRecoverPlanBean();
                    userAccount = borrowRecoverPlan.getRecoverAccount();// 获取应还款本息
                    userCapital = borrowRecoverPlan.getRecoverCapital();
                    userInterest = borrowRecoverPlan.getRecoverInterest();
                    userManageFee = borrowRecoverPlan.getRecoverFee();// 获取应还款管理费
                    BigDecimal recoverUserCapital = borrowRecover.getRecoverCapital().subtract(borrowRecover.getRecoverCapitalYes()); // 原始出借本金

                    // 给页面展示，就不计算了
                    repayRecoverPlanBean.setRecoverAccountOld(userAccount);
                    repayRecoverPlanBean.setChargeInterestOld(borrowRecoverPlan.getChargeInterest());
                    repayRecoverPlanBean.setChargePenaltyInterestOld(borrowRecoverPlan.getChargePenaltyInterest());
                    repayRecoverPlanBean.setDelayInterestOld(borrowRecoverPlan.getDelayInterest());
                    repayRecoverPlanBean.setLateInterestOld(borrowRecoverPlan.getLateInterest());
                    repayRecoverPlanBean.setRecoverAccountYesOld(borrowRecoverPlan.getRecoverAccountYes());

                    repayRecoverPlanBean.setRecoverCapitalOld(borrowRecover.getRecoverCapital());
                    repayRecoverPlanBean.setCreditAmountOld(borrowRecover.getCreditAmount());


                    // ** 计算三天罚息
                    BigDecimal acctualInterest = UnnormalRepayUtils.aheadEndRepayInterest(recoverUserCapital, new BigDecimal(borrow.getBorrowApr()), 0);
                    if(isLastPeriod) {
                        acctualInterest = UnnormalRepayUtils.aheadLastRepayInterest(recoverUserCapital, new BigDecimal(borrow.getBorrowApr()), totalPeriod);
                    }

                    if(acctualInterest.compareTo(userInterest) >=0) {
                        userChargeInterest = BigDecimal.ZERO;
                    }else {
                        userChargeInterest = userInterest.subtract(acctualInterest);
                    }
                    // 项目提前还款时，提前还款利息不得大于应还款利息
                    if (userChargeInterest.compareTo(userInterest) > 0) {
                        userChargeInterest = userInterest;
                    }

                    // 如果发生债转
                    if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                        if (Validator.isNull(borrowRecover.getAccedeOrderId())){

                            List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, recoverNid, repayPeriod, 0);

                            if (creditRepayList != null && creditRepayList.size() > 0) {
                                List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                                BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                                BigDecimal assignChargePenaltyInterest = BigDecimal.ZERO;// 计算用户提前还款罚息
                                for (int k = 0; k < creditRepayList.size(); k++) {
                                    CreditRepay creditRepay = creditRepayList.get(k);
                                    RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                    String assignNid = creditRepay.getAssignNid();// 承接订单号
                                    CreditTender creditTender = this.getCreditTender(assignNid);// 查询相应的承接记录
                                    assignAccount = creditRepay.getAssignAccount();// 承接本息
                                    assignCapital = creditRepay.getAssignCapital();// 用户实际还款本本金
                                    assignInterest = creditRepay.getAssignInterest();
                                    BigDecimal assignUserCapital = BigDecimal.ZERO;//剩余承接本金
                                    if (borrowRecoverPlan.getCreditStatus() == 2 && k == creditRepayList.size() - 1) {
                                        assignManageFee = userManageFee;

                                    } else {
                                        // 等额本息month、等额本金principal
                                        if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                                            if (repayPeriod == borrowPeriod.intValue()) {
                                                assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                                                        new BigDecimal(borrow.getAccount()), borrowPeriod, borrowVerifyTime);
                                            } else {
                                                assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                                                        new BigDecimal(borrow.getAccount()), borrowPeriod, borrowVerifyTime);
                                            }
                                        }
                                        // 先息后本endmonth
                                        else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
                                            if (repayPeriod == borrowPeriod.intValue()) {
                                                assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                        borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
                                            } else {
                                                assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                        borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
                                            }
                                        }

                                    }
                                    assignUserCapital =  creditTender.getAssignCapital().subtract(creditTender.getAssignRepayCapital());
                                    BigDecimal acctualAsignInterest = UnnormalRepayUtils.aheadEndRepayInterest(assignUserCapital, new BigDecimal(borrow.getBorrowApr()), 0);
                                    if(isLastPeriod) {
                                        acctualAsignInterest = UnnormalRepayUtils.aheadLastRepayInterest(assignUserCapital, new BigDecimal(borrow.getBorrowApr()), totalPeriod);
                                    }
                                    if(acctualAsignInterest.compareTo(assignInterest) >=0) {
                                        assignChargeInterest = BigDecimal.ZERO;
                                    }else {
                                        assignChargeInterest = assignInterest.subtract(acctualAsignInterest);
                                    }
                                    // 项目提前还款时，提前还款利息不得大于应还款利息
                                    if (assignChargeInterest.compareTo(assignInterest) > 0) {
                                        assignChargeInterest = assignInterest;
                                    }

                                    BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                    creditRepayBean.setAssignTotal(assignAccount.subtract(assignChargeInterest).add(assignManageFee));
                                    creditRepayBean.setManageFee(assignManageFee);
                                    creditRepayBean.setAdvanceStatus(1);
                                    creditRepayBean.setChargeInterest(assignChargeInterest.multiply(new BigDecimal(-1)));
                                    creditRepayBean.setChargePenaltyInterest(acctualAsignInterest);
                                    creditRepayBean.setChargeDays(3);// 默认是3天
                                    creditRepayBeanList.add(creditRepayBean);
                                    // 统计出让人还款金额
                                    userAccount = userAccount.subtract(assignAccount);
                                    userCapital = userCapital.subtract(assignCapital);
                                    recoverUserCapital = recoverUserCapital.subtract(assignUserCapital);
                                    userInterest = userInterest.subtract(assignInterest);
                                    userManageFee = userManageFee.subtract(assignManageFee);
                                    userChargeInterest = userChargeInterest.subtract(assignChargeInterest);// 提前还款利息
                                    userChargePenaltyInterest = userChargePenaltyInterest.subtract(assignChargePenaltyInterest);// 提前还款罚息
                                    // 统计总额
                                    repayTotal = repayTotal.add(assignAccount).add(assignManageFee).subtract(assignChargeInterest);// 统计总和本息+管理费
                                    repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                    repayCapital = repayCapital.add(assignCapital);
                                    repayInterest = repayInterest.add(assignInterest);
                                    repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                    repayChargeInterest = repayChargeInterest.add(assignChargeInterest);// 统计提前还款减少的利息
                                    repayChargePenaltyInterest = repayChargePenaltyInterest.add(assignChargePenaltyInterest);// 统计提前还款罚息
                                }
                                repayRecoverPlanBean.setCreditRepayList(creditRepayBeanList);
                            }
                            if (borrowRecoverPlan.getCreditStatus() != 2) {
                                //出让人剩余部分不再通过兜底进行计算，通过剩余本金进行计算
                                BigDecimal acctualUserInterest = UnnormalRepayUtils.aheadEndRepayInterest(recoverUserCapital, new BigDecimal(borrow.getBorrowApr()), 0);
                                if(isLastPeriod) {
                                    acctualUserInterest = UnnormalRepayUtils.aheadLastRepayInterest(recoverUserCapital, new BigDecimal(borrow.getBorrowApr()), totalPeriod);
                                }
                                if(acctualUserInterest.compareTo(userInterest) >=0) {
                                    userChargeInterest = BigDecimal.ZERO;
                                }else {
                                    userChargeInterest = userInterest.subtract(acctualUserInterest);
                                }
                                // 统计总额
                                repayTotal = repayTotal.add(userAccount).add(userManageFee).subtract(userChargeInterest);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                repayCapital = repayCapital.add(userCapital);
                                repayInterest = repayInterest.add(userInterest);
                                repayManageFee = repayManageFee.add(userManageFee);// 统计管理费
                                repayChargeInterest = repayChargeInterest.add(userChargeInterest);
                                repayChargePenaltyInterest = repayChargePenaltyInterest.add(acctualUserInterest);
                            }
                        }else{//计划还款
                            boolean overFlag = false;
                            List<HjhDebtCreditRepay> creditRepayList =this.selectHjhDebtCreditRepay(borrowNid, recoverNid, repayPeriod, borrowRecoverPlan.getRecoverStatus());
                            if (creditRepayList != null && creditRepayList.size() > 0) {
                                List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                                BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                BigDecimal sumCreditAccount = BigDecimal.ZERO;//总承接金额
                                BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                                BigDecimal assignChargePenaltyInterest = BigDecimal.ZERO;// 计算用户提前还款罚息
                                int hjhFlag = 0;
                                for (HjhDebtCreditRepay hjhDebtCreditRepayBean : creditRepayList) {
                                    hjhFlag++;
                                    sumCreditAccount = sumCreditAccount.add(hjhDebtCreditRepayBean.getRepayAccount());
                                }
                                //判断当前期是否全部承接
                                overFlag = isOverUndertake(borrowRecover,borrowRecoverPlan.getRecoverAccount(),sumCreditAccount,true,hjhFlag);
                                for (int k = 0; k < creditRepayList.size(); k++) {
                                    HjhDebtCreditRepay creditRepay = creditRepayList.get(k);
                                    HjhDebtCreditRepayBean creditRepayBean = new HjhDebtCreditRepayBean();
                                    String assignNid = creditRepay.getAssignOrderId();// 承接订单号
                                    HjhDebtCreditTender creditTender = this.getHjhDebtCreditTender(assignNid);// 查询相应的承接记录
                                    assignAccount = creditRepay.getRepayAccount();// 承接本息
                                    assignCapital = creditRepay.getRepayCapital();// 用户实际还款本本金
                                    assignInterest = creditRepay.getRepayInterest();
                                    if (!overFlag && k == creditRepayList.size() - 1) {
                                        assignManageFee = userManageFee;
                                    } else {
                                        // 等额本息month、等额本金principal
                                        if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                                            if (repayPeriod == borrowPeriod.intValue()) {
                                                assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                                                        borrowRecover.getRecoverCapital(), borrowPeriod, borrowVerifyTime);
                                            } else {
                                                assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                                                        borrowRecover.getRecoverCapital(), borrowPeriod, borrowVerifyTime);
                                            }
                                        }
                                        // 先息后本endmonth
                                        else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
                                            if (repayPeriod == borrowPeriod.intValue()) {
                                                assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                        borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
                                            } else {
                                                assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                        borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
                                            }
                                        }
                                    }

                                    // modify by cwyang 2018-5-23 计算金额取自剩余承接本金
                                    BigDecimal assignUserCapital =  getAssignSurplusCapital(assignNid,recoverNid);
                                    BigDecimal acctualAsignInterest = UnnormalRepayUtils.aheadEndRepayInterest(assignUserCapital, new BigDecimal(borrow.getBorrowApr()), 0);
                                    if(isLastPeriod) {
                                        acctualAsignInterest = UnnormalRepayUtils.aheadLastRepayInterest(assignUserCapital, new BigDecimal(borrow.getBorrowApr()), totalPeriod);
                                    }
                                    if(acctualAsignInterest.compareTo(assignInterest) >=0) {
                                        assignChargeInterest = BigDecimal.ZERO;
                                    }else {
                                        assignChargeInterest = assignInterest.subtract(acctualAsignInterest);
                                    }
                                    // 项目提前还款时，提前还款利息不得大于应还款利息，需求变更
                                    if (assignChargeInterest.compareTo(assignInterest) > 0) {
                                        assignChargeInterest = assignInterest;
                                    }
                                    BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                    creditRepayBean.setAssignTotal(assignAccount.subtract(assignChargeInterest).add(assignManageFee));
                                    creditRepayBean.setManageFee(assignManageFee);
                                    creditRepayBean.setAdvanceStatus(1);
                                    creditRepayBean.setRepayAdvanceInterest(assignChargeInterest.multiply(new BigDecimal(-1)));
                                    creditRepayBean.setRepayAdvancePenaltyInterest(acctualAsignInterest);
                                    creditRepayBean.setAdvanceDays(3);
                                    creditRepayBeanList.add(creditRepayBean);
                                    // 统计出让人还款金额
                                    userAccount = userAccount.subtract(assignAccount);
                                    userCapital = userCapital.subtract(assignCapital);
                                    recoverUserCapital = recoverUserCapital.subtract(assignUserCapital);
                                    userInterest = userInterest.subtract(assignInterest);
                                    userManageFee = userManageFee.subtract(assignManageFee);
                                    userChargeInterest = userChargeInterest.subtract(assignChargeInterest);// 提前还款利息
                                    userChargePenaltyInterest = userChargePenaltyInterest.subtract(assignChargePenaltyInterest);// 提前还款罚息
                                    // 统计总额
                                    repayTotal = repayTotal.add(assignAccount).subtract(assignChargeInterest).add(assignManageFee);// 统计总和本息+管理费
                                    repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                    repayCapital = repayCapital.add(assignCapital);
                                    repayInterest = repayInterest.add(assignInterest);
                                    repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                    repayChargeInterest = repayChargeInterest.add(assignChargeInterest);// 统计提前还款减少的利息
                                    repayChargePenaltyInterest = repayChargePenaltyInterest.add(assignChargePenaltyInterest);// 统计提前还款罚息
                                }
                                repayRecoverPlanBean.setHjhCreditRepayList(creditRepayBeanList);
                            }
                            if (overFlag) {
                                //出让人剩余部分不再通过兜底进行计算，通过剩余本金进行计算
                                BigDecimal acctualUserInterest = UnnormalRepayUtils.aheadEndRepayInterest(recoverUserCapital, new BigDecimal(borrow.getBorrowApr()), 0);
                                if(isLastPeriod) {
                                    acctualUserInterest = UnnormalRepayUtils.aheadLastRepayInterest(recoverUserCapital, new BigDecimal(borrow.getBorrowApr()), totalPeriod);
                                }
                                if(acctualUserInterest.compareTo(userInterest) >=0) {
                                    userChargeInterest = BigDecimal.ZERO;
                                }else {
                                    userChargeInterest = userInterest.subtract(acctualUserInterest);
                                }
                                // 统计总额
                                repayTotal = repayTotal.add(userAccount).add(userManageFee).subtract(userChargeInterest);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                repayCapital = repayCapital.add(userCapital);
                                repayInterest = repayInterest.add(userInterest);
                                repayManageFee = repayManageFee.add(userManageFee);// 统计管理费
                                repayChargeInterest = repayChargeInterest.add(userChargeInterest);
                                repayChargePenaltyInterest = repayChargePenaltyInterest.add(acctualUserInterest);
                            }
                        }

                    } else {
                        // 统计总和
                        repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userManageFee);// 统计总和本息+管理费
                        repayAccount = repayAccount.add(userAccount); // 统计本息总和
                        repayCapital = repayCapital.add(userCapital);
                        repayInterest = repayInterest.add(userInterest);
                        repayManageFee = repayManageFee.add(userManageFee);// 管理费
                        repayChargeInterest = repayChargeInterest.add(userChargeInterest);
                        repayChargePenaltyInterest = repayChargePenaltyInterest.add(userChargePenaltyInterest);
                    }
                    BeanUtils.copyProperties(borrowRecoverPlan, repayRecoverPlanBean);
                    repayRecoverPlanBean.setRecoverTotal(userAccount.subtract(userChargeInterest).add(userManageFee));
                    repayRecoverPlanBean.setRecoverAccount(userAccount);
                    repayRecoverPlanBean.setRecoverCapital(userCapital);
                    repayRecoverPlanBean.setRecoverInterest(userInterest);
                    repayRecoverPlanBean.setRecoverFee(userManageFee);
                    repayRecoverPlanBean.setChargeDays(3);
                    repayRecoverPlanBean.setChargeInterest(userChargeInterest.multiply(new BigDecimal(-1)));
                    repayRecoverPlanBean.setChargePenaltyInterest(userChargePenaltyInterest);
                    repayRecoverPlanBean.setAdvanceStatus(1);
                    repayRecoverPlanList.add(repayRecoverPlanBean);
                }
            }

        }
        borrowRepayPlan.setRecoverPlanList(repayRecoverPlanList);
        borrowRepayPlan.setRepayAccount(repayAccount);
        borrowRepayPlan.setRepayAccountAll(repayTotal);
        borrowRepayPlan.setRepayCapital(repayCapital);
        borrowRepayPlan.setRepayInterest(repayInterest);
        borrowRepayPlan.setRepayFee(repayManageFee);
        borrowRepayPlan.setAdvanceStatus(1);
        borrowRepayPlan.setChargeDays(3);
        borrowRepayPlan.setChargeInterest(repayChargeInterest.multiply(new BigDecimal(-1)));
        borrowRepayPlan.setChargePenaltyInterest(repayChargePenaltyInterest);
    }

    /**
     * 获得剩余本金
     * @param assignNid
     * @param recoverNid
     * @return
     */
    private BigDecimal getAssignSurplusCapital(String assignNid, String recoverNid) {
        HjhDebtCreditRepayExample example = new HjhDebtCreditRepayExample();
        HjhDebtCreditRepayExample.Criteria criteria = example.createCriteria();
        criteria.andAssignOrderIdEqualTo(assignNid);
        criteria.andInvestOrderIdEqualTo(recoverNid);
        criteria.andRepayStatusEqualTo(0);
        criteria.andDelFlagEqualTo(0);
        List<HjhDebtCreditRepay> repayList = this.hjhDebtCreditRepayMapper.selectByExample(example);
        if(repayList != null && repayList.size() > 0){
            BigDecimal sumCapital = BigDecimal.ZERO;
            for (HjhDebtCreditRepay info: repayList) {
                sumCapital = sumCapital.add(info.getRepayCapital());
            }
            return sumCapital;
        }
        return BigDecimal.ZERO;
    }

    /***
     * 计算用户分期还款本期应还金额
     * @param borrowRepayPlan
     * @param borrow
     * @param period
     * @param repayTimeStart
     * @throws ParseException
     */
    @Deprecated
    private void calculateRecoverPlan(RepayDetailBean borrowRepayPlan, BorrowCustomizeVO borrow, int period, String repayTimeStart) throws ParseException {

        int delayDays = borrowRepayPlan.getDelayDays().intValue();
        String repayTimeStr = borrowRepayPlan.getRepayTime().toString();
        int time = GetDate.getNowTime10();
        // 用户计划还款时间
        String repayTime = GetDate.getDateTimeMyTimeInMillis(Integer.parseInt(repayTimeStr));
        // 用户实际还款时间
        String RepayTime = GetDate.getDateTimeMyTimeInMillis(time);
        // 获取实际还款同计划还款时间的时间差
        int distanceDays = GetDate.daysBetween(RepayTime, repayTime);
        if (distanceDays < 0) {// 用户延期或者逾期了
            int lateDays = delayDays + distanceDays;
            if (lateDays >= 0) {// 用户延期还款
                delayDays = -distanceDays;
                this.calculateRecoverPlanDelay(borrowRepayPlan, borrow, delayDays);
            } else {// 用户逾期还款
                lateDays = -lateDays;
                Integer lateFreeDays = borrow.getLateFreeDays();
                if (lateFreeDays >= lateDays) {//在免息期以内,正常还款
                    this.calculateRecoverPlan(borrowRepayPlan, borrow, delayDays);
                } else {//过了免息期,罚免息期外的利息
                    lateDays = lateDays - lateFreeDays;
                    this.calculateRecoverPlanLate(borrowRepayPlan, borrow, delayDays, lateDays);
                }
            }
        } else {// 用户正常或者提前还款
            // 获取提前还款的阀值
            String repayAdvanceDay = this.getBorrowConfig("REPAY_ADVANCE_TIME");
            int advanceDays = distanceDays;
            //如果是融通宝项目,不判断提前还款的阙值 add by cwyang 2017-6-14
            int projectType = Integer.valueOf(borrow.getProjectType());
            if (13 == projectType) {
                repayAdvanceDay = "0";
            }
            if (Integer.parseInt(repayAdvanceDay) < advanceDays) {// 用户提前还款
                // 提前还款方便页面判断，实际数据不更新
                borrowRepayPlan.setAdvanceStatus(1);
            } else {// 用户正常还款
                borrowRepayPlan.setAdvanceStatus(0);
            }

            // 计算用户实际还款总额 提前还款当期不减息
            this.calculateRecoverPlan(borrowRepayPlan, borrow, advanceDays);

            borrowRepayPlan.setChargeDays(advanceDays);
        }
    }

    /**
     * 统计分期还款用户逾期还款的总标
     * @param borrowRepayPlan
     * @param borrow
     * @param delayDays
     * @param lateDays
     * @throws ParseException
     */
    @Deprecated
    private void calculateRecoverPlanLate(RepayDetailBean borrowRepayPlan, BorrowCustomizeVO borrow, int delayDays, int lateDays) throws ParseException {

        // 项目编号
        String borrowNid = borrow.getBorrowNid();
        // 还款方式
        String borrowStyle = borrow.getBorrowStyle();
        // 管理费率
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : Integer.valueOf(borrow.getVerifyTime());
        // 项目总期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : Integer.valueOf(borrow.getBorrowPeriod());
        // 还款期数
        int repayPeriod = borrowRepayPlan.getRepayPeriod();
        List<BorrowRecover> borrowRecoverList = this.selectBorrowRecoverList(borrow.getBorrowNid());
        List<BorrowRecoverPlan> borrowRecoverPlans = searchBorrowRecoverPlan(borrow.getBorrowNid(), borrowRepayPlan.getRepayPeriod());
        List<RepayRecoverPlanBean> repayRecoverPlanList = new ArrayList<RepayRecoverPlanBean>();
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        BigDecimal repayDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
        BigDecimal repayOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
        if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
            for (int i = 0; i < borrowRecoverList.size(); i++) {
                BorrowRecover borrowRecover = borrowRecoverList.get(i);
                String recoverNid = borrowRecover.getNid();// 出借订单号
                int recoverUserId = borrowRecover.getUserId();// 出借用户userId
                if (borrowRecoverPlans != null && borrowRecoverPlans.size() > 0) {
                    BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                    BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                    BigDecimal userDelayInterest = BigDecimal.ZERO;// 计算用户延期还款利息
                    BigDecimal userOverdueInterest = BigDecimal.ZERO;// 计算用户逾期还款利息
                    for (int j = 0; j < borrowRecoverPlans.size(); j++) {
                        BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlans.get(j);
                        String recoverPlanNid = borrowRecoverPlan.getNid();// 出借订单号
                        int recoverPlanUserId = borrowRecoverPlan.getUserId();// 出借用户userId
                        userAccount = borrowRecoverPlan.getRecoverAccount();
                        userCapital = borrowRecoverPlan.getRecoverCapital();
                        userInterest = borrowRecoverPlan.getRecoverInterest();
                        // 计算用户逾期利息
                        userOverdueInterest = UnnormalRepayUtils.overdueRepayOverdueInterest(userAccount, lateDays);
                        if (StringUtils.isNotBlank(borrow.getPlanNid())) {//计划相关
                            //BigDecimal planRate = new BigDecimal(borrow.getLateInterestRate());
                            userOverdueInterest = UnnormalRepayUtils.overduePlanRepayOverdueInterest(userAccount, lateDays, borrow.getLateInterestRate());
                        }
                        // 计算用户延期利息
                        userDelayInterest = UnnormalRepayUtils.overdueRepayDelayInterest(userCapital, new BigDecimal(borrow.getBorrowApr()), delayDays);
                        // 获取应还款管理费
                        userManageFee = borrowRecoverPlan.getRecoverFee();
                        if (recoverPlanNid.equals(recoverNid) && recoverUserId == recoverPlanUserId) {
                            RepayRecoverPlanBean repayRecoverPlanBean = new RepayRecoverPlanBean();

                            // 给页面展示，就不计算了
                            repayRecoverPlanBean.setRecoverAccountOld(userAccount);
                            repayRecoverPlanBean.setChargeInterestOld(borrowRecoverPlan.getChargeInterest());
                            repayRecoverPlanBean.setDelayInterestOld(borrowRecoverPlan.getDelayInterest());
                            repayRecoverPlanBean.setLateInterestOld(borrowRecoverPlan.getLateInterest());
                            repayRecoverPlanBean.setRecoverAccountYesOld(borrowRecoverPlan.getRecoverAccountYes());

                            repayRecoverPlanBean.setRecoverCapitalOld(borrowRecover.getRecoverCapital());
                            repayRecoverPlanBean.setCreditAmountOld(borrowRecover.getCreditAmount());

                            if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                                if (Validator.isNull(borrowRecover.getAccedeOrderId())){
                                    // 直投类项目债转还款
                                    List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, recoverNid, repayPeriod, 0);
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                                        CreditRepay creditRepay = null;
                                        RepayCreditRepayBean creditRepayBean = null;
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                                        BigDecimal assignOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            creditRepay = creditRepayList.get(k);
                                            creditRepayBean = new RepayCreditRepayBean();
                                            String assignNid = creditRepay.getAssignNid();// 承接订单号
                                            CreditTender creditTender = this.getCreditTender(assignNid);// 查询相应的承接记录
                                            assignAccount = creditRepay.getAssignAccount();// 承接本息
                                            assignCapital = creditRepay.getAssignCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getAssignInterest();
                                            // 计算用户实际获得的本息和
                                            assignAccount = UnnormalRepayUtils.overdueRepayPrincipalInterest(assignAccount, assignCapital, new BigDecimal(borrow.getBorrowApr()), delayDays, lateDays);
                                            //最后一笔兜底
                                            if (borrowRecoverPlan.getCreditStatus() == 2 && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                                // 计算用户逾期利息
                                                assignOverdueInterest = userOverdueInterest;
                                                // 计算用户延期利息
                                                assignDelayInterest = userDelayInterest;
                                            } else {
                                                // 等额本息month、等额本金principal
                                                if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                                                                new BigDecimal(borrow.getAccount()), borrowPeriod, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                                                                new BigDecimal(borrow.getAccount()), borrowPeriod, borrowVerifyTime);
                                                    }
                                                }
                                                // 先息后本endmonth
                                                else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
                                                    }
                                                }
                                                // 计算用户逾期利息
                                                assignOverdueInterest = UnnormalRepayUtils.overdueRepayOverdueInterest(assignAccount, lateDays);
                                                if (StringUtils.isNotBlank(borrow.getPlanNid())) {//计划相关
                                                    //BigDecimal planRate = new BigDecimal(borrow.getLateInterestRate());
                                                    userOverdueInterest = UnnormalRepayUtils.overduePlanRepayOverdueInterest(assignAccount, lateDays, borrow.getLateInterestRate());
                                                }
                                                // 计算用户延期利息
                                                assignDelayInterest = UnnormalRepayUtils.overdueRepayDelayInterest(assignCapital, new BigDecimal(borrow.getBorrowApr()), delayDays);
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBean.setAdvanceStatus(3);
                                            creditRepayBean.setDelayDays(delayDays);
                                            creditRepayBean.setDelayInterest(assignDelayInterest);
                                            creditRepayBean.setLateDays(lateDays);
                                            creditRepayBean.setLateInterest(assignOverdueInterest);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
                                            userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                            userDelayInterest = userDelayInterest.subtract(assignDelayInterest);// 提前还款利息
                                            userOverdueInterest = userOverdueInterest.subtract(assignOverdueInterest);// 逾期利息
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                            repayDelayInterest = repayDelayInterest.add(assignDelayInterest);// 统计借款用户总延期利息
                                            repayOverdueInterest = repayOverdueInterest.add(assignOverdueInterest);// 统计借款用户总逾期利息
                                        }
                                        repayRecoverPlanBean.setCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 统计总和
                                        repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                        repayDelayInterest = repayDelayInterest.add(userDelayInterest);
                                        repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);
                                    }
                                }else{
                                    // 计划类项目债转还款
                                    List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, recoverNid, repayPeriod, borrowRecoverPlan.getRecoverStatus());
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                                        HjhDebtCreditRepay creditRepay = null;
                                        HjhDebtCreditRepayBean creditRepayBean = null;
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                                        BigDecimal assignOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
                                        BigDecimal oldAssignAccount = BigDecimal.ZERO;// 原始承接本金
                                        BigDecimal sumCreditAccount = BigDecimal.ZERO;//总承接金额
                                        int hjhFlag = 0;
                                        for (HjhDebtCreditRepay hjhDebtCreditRepayBean : creditRepayList) {
                                            hjhFlag++;
                                            sumCreditAccount = sumCreditAccount.add(hjhDebtCreditRepayBean.getRepayAccount());
                                        }
                                        //判断当前期是否全部承接
                                        boolean overFlag = isOverUndertake(borrowRecover,borrowRecoverPlan.getRecoverAccount(),sumCreditAccount,true,hjhFlag);
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            creditRepay = creditRepayList.get(k);
                                            creditRepayBean = new HjhDebtCreditRepayBean();
                                            String assignNid = creditRepay.getAssignOrderId();// 承接订单号
                                            HjhDebtCreditTender creditTender = this.getHjhDebtCreditTender(assignNid);// 查询相应的承接记录
                                            assignAccount = creditRepay.getRepayAccount();// 承接本息
                                            oldAssignAccount = assignAccount;
                                            assignCapital = creditRepay.getRepayCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getRepayInterest();
                                            // 计算用户实际获得的本息和
//                                            assignAccount = UnnormalRepayUtils.overdueRepayPrincipalInterest(assignAccount, assignCapital, borrow.getBorrowApr(), delayDays, lateDays);
                                            //最后一笔兜底
                                            if (!overFlag && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                                // 计算用户逾期利息
                                                assignOverdueInterest = userOverdueInterest;
                                                // 计算用户延期利息
                                                assignDelayInterest = userDelayInterest;
                                            } else {
                                                // 等额本息month、等额本金principal
                                                if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                                                                borrowRecover.getRecoverCapital(), borrowPeriod, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                                                                borrowRecover.getRecoverCapital(), borrowPeriod, borrowVerifyTime);
                                                    }
                                                }
                                                // 先息后本endmonth
                                                else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
                                                    }
                                                }
                                                // 计算用户逾期利息
                                                assignOverdueInterest = UnnormalRepayUtils.overdueRepayOverdueInterest(oldAssignAccount, lateDays);
                                                if (StringUtils.isNotBlank(borrow.getPlanNid())) {//计划相关
                                                    //BigDecimal planRate = new BigDecimal(borrow.getLateInterestRate());
                                                    assignOverdueInterest = UnnormalRepayUtils.overduePlanRepayOverdueInterest(oldAssignAccount, lateDays, borrow.getLateInterestRate());
                                                }
                                                // 计算用户延期利息
                                                assignDelayInterest = UnnormalRepayUtils.overdueRepayDelayInterest(assignCapital, new BigDecimal(borrow.getBorrowApr()), delayDays);
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBean.setAdvanceStatus(3);
                                            creditRepayBean.setDelayDays(delayDays);
                                            creditRepayBean.setRepayDelayInterest(assignDelayInterest);
                                            creditRepayBean.setLateDays(lateDays);
                                            creditRepayBean.setRepayLateInterest(assignOverdueInterest);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
                                            userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                            userDelayInterest = userDelayInterest.subtract(assignDelayInterest);// 提前还款利息
                                            userOverdueInterest = userOverdueInterest.subtract(assignOverdueInterest);// 逾期利息
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                            repayDelayInterest = repayDelayInterest.add(assignDelayInterest);// 统计借款用户总延期利息
                                            repayOverdueInterest = repayOverdueInterest.add(assignOverdueInterest);// 统计借款用户总逾期利息
                                        }
                                        repayRecoverPlanBean.setHjhCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 统计总和
                                        repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                        repayDelayInterest = repayDelayInterest.add(userDelayInterest);
                                        repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);
                                    }
                                }
                            } else {
                                // 统计总和
                                repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                repayCapital = repayCapital.add(userCapital);
                                repayInterest = repayInterest.add(userInterest);
                                repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                repayDelayInterest = repayDelayInterest.add(userDelayInterest);
                                repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);
                            }
                            // 计算还款期的数据
                            BeanUtils.copyProperties(borrowRecoverPlan, repayRecoverPlanBean);
                            repayRecoverPlanBean.setRecoverTotal(userAccount.add(userDelayInterest).add(userOverdueInterest).add(userManageFee));
                            repayRecoverPlanBean.setRecoverAccount(userAccount);
                            repayRecoverPlanBean.setRecoverCapital(userCapital);
                            repayRecoverPlanBean.setRecoverInterest(userInterest);
                            repayRecoverPlanBean.setRecoverFee(userManageFee);
                            repayRecoverPlanBean.setDelayInterest(userDelayInterest);
                            repayRecoverPlanBean.setLateInterest(userOverdueInterest);
                            repayRecoverPlanBean.setDelayDays(delayDays);
                            repayRecoverPlanBean.setLateDays(lateDays);
                            repayRecoverPlanBean.setAdvanceStatus(3);
                            repayRecoverPlanList.add(repayRecoverPlanBean);
                        }
                    }
                }
            }
            borrowRepayPlan.setRecoverPlanList(repayRecoverPlanList);
        }
        borrowRepayPlan.setRepayAccountAll(repayTotal);
        borrowRepayPlan.setRepayAccount(repayAccount);
        borrowRepayPlan.setRepayCapital(repayCapital);
        borrowRepayPlan.setRepayInterest(repayInterest);
        borrowRepayPlan.setRepayFee(repayManageFee);
        borrowRepayPlan.setDelayDays(delayDays);
        borrowRepayPlan.setDelayInterest(repayDelayInterest);
        borrowRepayPlan.setLateDays(lateDays);
        borrowRepayPlan.setLateInterest(repayOverdueInterest);
        borrowRepayPlan.setAdvanceStatus(3);
    }

    /**
     * 统计分期还款用户正常还款的总标
     * @param borrowRepayPlan
     * @param borrow
     * @param interestDay
     * @throws ParseException
     */
    @Deprecated
    private void calculateRecoverPlan(RepayDetailBean borrowRepayPlan, BorrowCustomizeVO borrow, int interestDay) throws ParseException {

        // 项目编号
        String borrowNid = borrow.getBorrowNid();
        // 还款方式
        String borrowStyle = borrow.getBorrowStyle();
        // 管理费率
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : Integer.valueOf(borrow.getVerifyTime());
        // 项目总期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : Integer.valueOf(borrow.getBorrowPeriod());
        // 还款期数
        int repayPeriod = borrowRepayPlan.getRepayPeriod();
        List<BorrowRecover> borrowRecoverList = this.selectBorrowRecoverList(borrow.getBorrowNid());
        List<BorrowRecoverPlan> borrowRecoverPlans = searchBorrowRecoverPlan(borrow.getBorrowNid(), borrowRepayPlan.getRepayPeriod());
        List<RepayRecoverPlanBean> repayRecoverPlanList = new ArrayList<RepayRecoverPlanBean>();
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
            for (int i = 0; i < borrowRecoverList.size(); i++) {
                BorrowRecover borrowRecover = borrowRecoverList.get(i);
                String recoverNid = borrowRecover.getNid();// 出借订单号
                int recoverUserId = borrowRecover.getUserId();// 出借用户userId
                if (borrowRecoverPlans != null && borrowRecoverPlans.size() > 0) {
                    BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                    BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                    for (int j = 0; j < borrowRecoverPlans.size(); j++) {
                        BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlans.get(j);
                        String recoverPlanNid = borrowRecoverPlan.getNid();// 出借订单号
                        int recoverPlanUserId = borrowRecoverPlan.getUserId();// 出借用户userId
                        if (recoverPlanNid.equals(recoverNid) && recoverUserId == recoverPlanUserId) {
                            RepayRecoverPlanBean repayRecoverPlanBean = new RepayRecoverPlanBean();
                            userAccount = borrowRecoverPlan.getRecoverAccount();// 获取应还款本息
                            userCapital = borrowRecoverPlan.getRecoverCapital();
                            userInterest = borrowRecoverPlan.getRecoverInterest();
                            userManageFee = borrowRecoverPlan.getRecoverFee();// 获取应还款管理费

                            // 给页面展示，就不计算了
                            repayRecoverPlanBean.setRecoverAccountOld(userAccount);
                            repayRecoverPlanBean.setChargeInterestOld(borrowRecoverPlan.getChargeInterest());
                            repayRecoverPlanBean.setDelayInterestOld(borrowRecoverPlan.getDelayInterest());
                            repayRecoverPlanBean.setLateInterestOld(borrowRecoverPlan.getLateInterest());
                            repayRecoverPlanBean.setRecoverAccountYesOld(borrowRecoverPlan.getRecoverAccountYes());

                            repayRecoverPlanBean.setRecoverCapitalOld(borrowRecover.getRecoverCapital());
                            repayRecoverPlanBean.setCreditAmountOld(borrowRecover.getCreditAmount());
                            // 如果发生债转
                            if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                                if (Validator.isNull(borrowRecover.getAccedeOrderId())){
                                    List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, recoverNid, repayPeriod, 0);

                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            CreditRepay creditRepay = creditRepayList.get(k);
                                            RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                            String assignNid = creditRepay.getAssignNid();// 承接订单号
                                            CreditTender creditTender = this.getCreditTender(assignNid);// 查询相应的承接记录
                                            assignAccount = creditRepay.getAssignAccount();// 承接本息
                                            assignCapital = creditRepay.getAssignCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getAssignInterest();
                                            if (borrowRecoverPlan.getCreditStatus() == 2 && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                            } else {
                                                // 等额本息month、等额本金principal
                                                if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                                                                new BigDecimal(borrow.getAccount()), borrowPeriod, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                                                                new BigDecimal(borrow.getAccount()), borrowPeriod, borrowVerifyTime);
                                                    }
                                                }
                                                // 先息后本endmonth
                                                else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
                                                    }
                                                }
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBean.setAdvanceStatus(0);
                                            creditRepayBean.setChargeInterest(BigDecimal.ZERO);
                                            creditRepayBean.setChargeDays(interestDay);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
                                            userManageFee = userManageFee.subtract(assignManageFee);
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                        }
                                        repayRecoverPlanBean.setCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 统计总额
                                        repayTotal = repayTotal.add(userAccount).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                    }
                                }else{//计划还款
                                    boolean overFlag = false;
                                    List<HjhDebtCreditRepay> creditRepayList =this.selectHjhDebtCreditRepay(borrowNid, recoverNid, repayPeriod, borrowRecoverPlan.getRecoverStatus());
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        BigDecimal sumCreditAccount = BigDecimal.ZERO;//总承接金额
                                        int hjhFlag = 0;
                                        for (HjhDebtCreditRepay hjhDebtCreditRepayBean : creditRepayList) {
                                            hjhFlag++;
                                            sumCreditAccount = sumCreditAccount.add(hjhDebtCreditRepayBean.getRepayAccount());
                                        }
                                        //判断当前期是否全部承接
                                        overFlag = isOverUndertake(borrowRecover,borrowRecoverPlan.getRecoverAccount(),sumCreditAccount,true,hjhFlag);
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            HjhDebtCreditRepay creditRepay = creditRepayList.get(k);
                                            HjhDebtCreditRepayBean creditRepayBean = new HjhDebtCreditRepayBean();
                                            String assignNid = creditRepay.getAssignOrderId();// 承接订单号
                                            HjhDebtCreditTender creditTender = this.getHjhDebtCreditTender(assignNid);// 查询相应的承接记录
                                            assignAccount = creditRepay.getRepayAccount();// 承接本息
                                            assignCapital = creditRepay.getRepayCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getRepayInterest();
                                            if (!overFlag && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                            } else {
                                                // 等额本息month、等额本金principal
                                                if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                                                                borrowRecover.getRecoverCapital(), borrowPeriod, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                                                                borrowRecover.getRecoverCapital(), borrowPeriod, borrowVerifyTime);
                                                    }
                                                }
                                                // 先息后本endmonth
                                                else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
                                                    }
                                                }
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBean.setAdvanceStatus(0);
                                            creditRepayBean.setRepayAdvanceInterest(BigDecimal.ZERO);
                                            creditRepayBean.setAdvanceDays(interestDay);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
                                            userManageFee = userManageFee.subtract(assignManageFee);
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                        }
                                        repayRecoverPlanBean.setHjhCreditRepayList(creditRepayBeanList);
                                    }
                                    if (overFlag) {
                                        // 统计总额
                                        repayTotal = repayTotal.add(userAccount).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                    }
                                }
                            } else {
                                // 统计总和
                                repayTotal = repayTotal.add(userAccount).add(userManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(userAccount); // 统计本息总和
                                repayCapital = repayCapital.add(userCapital);
                                repayInterest = repayInterest.add(userInterest);
                                repayManageFee = repayManageFee.add(userManageFee);// 管理费
                            }
                            BeanUtils.copyProperties(borrowRecoverPlan, repayRecoverPlanBean);
                            repayRecoverPlanBean.setRecoverTotal(userAccount.add(userManageFee));
                            repayRecoverPlanBean.setRecoverAccount(userAccount);
                            repayRecoverPlanBean.setRecoverCapital(userCapital);
                            repayRecoverPlanBean.setRecoverInterest(userInterest);
                            repayRecoverPlanBean.setRecoverFee(userManageFee);
                            repayRecoverPlanBean.setChargeDays(interestDay);
                            repayRecoverPlanBean.setAdvanceStatus(0);
                            repayRecoverPlanList.add(repayRecoverPlanBean);
                        }
                    }
                }
            }
            borrowRepayPlan.setRecoverPlanList(repayRecoverPlanList);
        }
        borrowRepayPlan.setRepayAccountAll(repayTotal);
        borrowRepayPlan.setRepayAccount(repayAccount);
        borrowRepayPlan.setRepayCapital(repayCapital);
        borrowRepayPlan.setRepayInterest(repayInterest);
        borrowRepayPlan.setRepayFee(repayManageFee);
        borrowRepayPlan.setChargeDays(interestDay);
        borrowRepayPlan.setAdvanceStatus(0);
    }

    /**
     * 统计分期还款用户延期还款的总标
     * @param borrowRepayPlan
     * @param borrow
     * @param delayDays
     * @throws ParseException
     */
    @Deprecated
    private void calculateRecoverPlanDelay(RepayDetailBean borrowRepayPlan, BorrowCustomizeVO borrow, int delayDays) throws ParseException {

        // 项目编号
        String borrowNid = borrow.getBorrowNid();
        // 还款方式
        String borrowStyle = borrow.getBorrowStyle();
        // 管理费率
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : Integer.valueOf(borrow.getVerifyTime());
        // 项目总期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : Integer.valueOf(borrow.getBorrowPeriod());
        // 还款期数
        int repayPeriod = borrowRepayPlan.getRepayPeriod();
        List<BorrowRecover> borrowRecoverList = this.selectBorrowRecoverList(borrow.getBorrowNid());
        List<BorrowRecoverPlan> borrowRecoverPlans = searchBorrowRecoverPlan(borrow.getBorrowNid(), borrowRepayPlan.getRepayPeriod());
        List<RepayRecoverPlanBean> repayRecoverPlanList = new ArrayList<RepayRecoverPlanBean>();
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        BigDecimal repayDelayInterest = new BigDecimal(0); // 统计借款用户总延期利息
        if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
            for (int i = 0; i < borrowRecoverList.size(); i++) {
                BorrowRecover borrowRecover = borrowRecoverList.get(i);
                String recoverNid = borrowRecover.getNid();// 出借订单号
                int recoverUserId = borrowRecover.getUserId();// 出借用户userId
                if (borrowRecoverPlans != null && borrowRecoverPlans.size() > 0) {
                    BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                    BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                    BigDecimal userDelayInterest = BigDecimal.ZERO;// 计算用户提前还款利息
                    for (int j = 0; j < borrowRecoverPlans.size(); j++) {
                        BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlans.get(j);
                        String recoverPlanNid = borrowRecoverPlan.getNid();// 出借订单号
                        int recoverPlanUserId = borrowRecoverPlan.getUserId();// 出借用户userId
                        if (recoverPlanNid.equals(recoverNid) && recoverUserId == recoverPlanUserId) {
                            RepayRecoverPlanBean repayRecoverPlanBean = new RepayRecoverPlanBean();
                            userAccount = borrowRecoverPlan.getRecoverAccount();
                            userCapital = borrowRecoverPlan.getRecoverCapital();
                            userInterest = borrowRecoverPlan.getRecoverInterest();
                            // 计算用户延期利息
                            userDelayInterest = UnnormalRepayUtils.delayRepayInterest(userCapital, new BigDecimal(borrow.getBorrowApr()), delayDays);
                            if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle) && delayDays > 0) {// 先息后本
                                BigDecimal userDelayInterest1 = UnnormalRepayUtils.delayRepayInterest(borrow.getRepayAccountCapital(), new BigDecimal(borrow.getBorrowApr()), delayDays);
                                logger.info("第三方查询先息后本原计算延期利息本金：{},新计算延期利息本金：{}", userCapital, borrow.getRepayAccountCapital());
                                logger.info("第三方查询先息后本原延期利息：{},新延期利息：{}", userDelayInterest, userDelayInterest1);
                            }
                            // 获取应还款管理费
                            userManageFee = borrowRecoverPlan.getRecoverFee();

                            // 给页面展示，就不计算了
                            repayRecoverPlanBean.setRecoverAccountOld(userAccount);
                            repayRecoverPlanBean.setChargeInterestOld(borrowRecoverPlan.getChargeInterest());
                            repayRecoverPlanBean.setDelayInterestOld(borrowRecoverPlan.getDelayInterest());
                            repayRecoverPlanBean.setLateInterestOld(borrowRecoverPlan.getLateInterest());
                            repayRecoverPlanBean.setRecoverAccountYesOld(borrowRecoverPlan.getRecoverAccountYes());

                            repayRecoverPlanBean.setRecoverCapitalOld(borrowRecover.getRecoverCapital());
                            repayRecoverPlanBean.setCreditAmountOld(borrowRecover.getCreditAmount());

                            if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                                if (Validator.isNull(borrowRecover.getAccedeOrderId())){
                                    // 直投项目还款
                                    List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, recoverNid, repayPeriod, 0);
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                                        CreditRepay creditRepay = null;
                                        RepayCreditRepayBean creditRepayBean = null;
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            creditRepay = creditRepayList.get(k);
                                            creditRepayBean = new RepayCreditRepayBean();
                                            String assignNid = creditRepay.getAssignNid();// 承接订单号
                                            CreditTender creditTender = this.getCreditTender(assignNid);// 查询相应的承接记录
                                            assignAccount = creditRepay.getAssignAccount();// 承接本息
                                            assignCapital = creditRepay.getAssignCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getAssignInterest();
                                            //用户延期利息
                                            if (borrowRecoverPlan.getCreditStatus() == 2 && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                                // 计算用户延期利息
                                                assignDelayInterest = userDelayInterest;
                                            } else {
                                                // 等额本息month、等额本金principal
                                                if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                                                                new BigDecimal(borrow.getAccount()), borrowPeriod, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                                                                new BigDecimal(borrow.getAccount()), borrowPeriod, borrowVerifyTime);
                                                    }
                                                }
                                                // 先息后本endmonth
                                                else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
                                                    }
                                                }
                                                // 计算用户延期利息
                                                assignDelayInterest = UnnormalRepayUtils.delayRepayInterest(assignCapital, new BigDecimal(borrow.getBorrowApr()), delayDays);
                                                if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle) && delayDays > 0) {// 先息后本
                                                    BigDecimal userDelayInterest1 = UnnormalRepayUtils.delayRepayInterest(borrow.getRepayAccountCapital(), new BigDecimal(borrow.getBorrowApr()), delayDays);
                                                    logger.info("第三方查询直投债转先息后本原计算延期利息本金：{},新计算延期利息本金：{}", userCapital, borrow.getRepayAccountCapital());
                                                    logger.info("第三方查询直投债转先息后本原延期利息：{},新延期利息：{}", userDelayInterest, userDelayInterest1);
                                                }
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignDelayInterest).add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBean.setAdvanceStatus(2);
                                            creditRepayBean.setDelayDays(delayDays);
                                            creditRepayBean.setDelayInterest(assignDelayInterest);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
                                            userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                            userDelayInterest = userDelayInterest.subtract(assignDelayInterest);// 提前还款利息
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).add(assignDelayInterest).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayManageFee = repayManageFee.add(assignManageFee);// 統計管理費
                                            repayDelayInterest = repayDelayInterest.add(assignDelayInterest);
                                        }
                                        repayRecoverPlanBean.setCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 统计总额
                                        repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                        repayDelayInterest = repayDelayInterest.add(userDelayInterest);
                                    }
                                }else{
                                    // 计划类债转还款
                                    List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, recoverNid, repayPeriod, borrowRecoverPlan.getRecoverStatus());
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                                        HjhDebtCreditRepay creditRepay = null;
                                        HjhDebtCreditRepayBean creditRepayBean = null;
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                                        BigDecimal sumCreditAccount = BigDecimal.ZERO;//总承接金额
                                        int hjhFlag = 0;
                                        for (HjhDebtCreditRepay hjhDebtCreditRepayBean : creditRepayList) {
                                            hjhFlag++;
                                            sumCreditAccount = sumCreditAccount.add(hjhDebtCreditRepayBean.getRepayAccount());
                                        }
                                        //判断当前期是否全部承接
                                        boolean overFlag = isOverUndertake(borrowRecover,borrowRecoverPlan.getRecoverAccount(),sumCreditAccount,true,hjhFlag);
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            creditRepay = creditRepayList.get(k);
                                            creditRepayBean = new HjhDebtCreditRepayBean();
                                            String assignNid = creditRepay.getAssignOrderId();// 承接订单号
                                            HjhDebtCreditTender creditTender = this.getHjhDebtCreditTender(assignNid);// 查询相应的承接记录
                                            assignAccount = creditRepay.getRepayAccount();// 承接本息
                                            assignCapital = creditRepay.getRepayCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getRepayInterest();
                                            //用户延期利息
                                            if (!overFlag && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                                // 计算用户延期利息
                                                assignDelayInterest = userDelayInterest;
                                            } else {
                                                // 等额本息month、等额本金principal
                                                if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                                                                borrowRecover.getRecoverCapital(), borrowPeriod, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                                                                borrowRecover.getRecoverCapital(), borrowPeriod, borrowVerifyTime);
                                                    }
                                                }
                                                // 先息后本endmonth
                                                else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
                                                    }
                                                }
                                                // 计算用户延期利息
                                                assignDelayInterest = UnnormalRepayUtils.delayRepayInterest(assignCapital, new BigDecimal(borrow.getBorrowApr()), delayDays);
                                                if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle) && delayDays > 0) {// 先息后本
                                                    BigDecimal userDelayInterest1 = UnnormalRepayUtils.delayRepayInterest(borrow.getRepayAccountCapital(), new BigDecimal(borrow.getBorrowApr()), delayDays);
                                                    logger.info("第三方查询计划债转先息后本原计算延期利息本金：{},新计算延期利息本金：{}", userCapital, borrow.getRepayAccountCapital());
                                                    logger.info("第三方查询计划债转先息后本原延期利息：{},新延期利息：{}", userDelayInterest, userDelayInterest1);
                                                }
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignDelayInterest).add(assignManageFee));
                                            creditRepayBean.setManageFee(userManageFee);
                                            creditRepayBean.setAdvanceStatus(2);
                                            creditRepayBean.setDelayDays(delayDays);
                                            creditRepayBean.setRepayDelayInterest(assignDelayInterest);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
                                            userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
//                                            userDelayInterest = userDelayInterest.subtract(assignDelayInterest);// 提前还款利息
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).add(assignDelayInterest).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayManageFee = repayManageFee.add(assignManageFee);// 統計管理費
                                            repayDelayInterest = repayDelayInterest.add(assignDelayInterest);
                                        }
                                        repayRecoverPlanBean.setHjhCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 统计总额
                                        repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                        repayDelayInterest = repayDelayInterest.add(userDelayInterest);
                                    }
                                }
                            } else {
                                // 统计总额
                                repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                repayCapital = repayCapital.add(userCapital);
                                repayInterest = repayInterest.add(userInterest);
                                repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                repayDelayInterest = repayDelayInterest.add(userDelayInterest);
                            }
                            // 计算还款期的数据
                            BeanUtils.copyProperties(borrowRecoverPlan, repayRecoverPlanBean);
                            repayRecoverPlanBean.setRecoverTotal(userAccount.add(userDelayInterest).add(userManageFee));
                            repayRecoverPlanBean.setRecoverAccount(userAccount);
                            repayRecoverPlanBean.setRecoverCapital(userCapital);
                            repayRecoverPlanBean.setRecoverInterest(userInterest);
                            repayRecoverPlanBean.setRecoverFee(userManageFee);
                            repayRecoverPlanBean.setDelayInterest(userDelayInterest);
                            repayRecoverPlanBean.setDelayDays(delayDays);
                            repayRecoverPlanBean.setAdvanceStatus(2);
                            repayRecoverPlanList.add(repayRecoverPlanBean);
                        }
                    }
                }
            }
            borrowRepayPlan.setRecoverPlanList(repayRecoverPlanList);
        }
        borrowRepayPlan.setRepayAccountAll(repayTotal);
        borrowRepayPlan.setRepayAccount(repayAccount);
        borrowRepayPlan.setRepayCapital(repayCapital);
        borrowRepayPlan.setRepayInterest(repayInterest);
        borrowRepayPlan.setRepayFee(repayManageFee);
        borrowRepayPlan.setDelayInterest(repayDelayInterest);
        borrowRepayPlan.setAdvanceStatus(2);
        borrowRepayPlan.setDelayDays(delayDays);
    }

    /**
     * 统计分期还款用户还款信息
     * @param borrowRepayPlan
     * @param borrow
     * @throws ParseException
     */
    @Deprecated
    private void calculateRecoverPlan(RepayDetailBean borrowRepayPlan, BorrowCustomizeVO borrow) throws ParseException {

        // 项目编号
        String borrowNid = borrow.getBorrowNid();
        // 还款方式
        String borrowStyle = borrow.getBorrowStyle();
        // 管理费率
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : Integer.valueOf(borrow.getVerifyTime());
        // 项目总期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : Integer.valueOf(borrow.getBorrowPeriod());
        // 还款期数
        int repayPeriod = borrowRepayPlan.getRepayPeriod();
        List<BorrowRecover> borrowRecoverList = selectBorrowRecoverList(borrow.getBorrowNid());
        List<BorrowRecoverPlan> borrowRecoverPlans = searchBorrowRecoverPlan(borrow.getBorrowNid(), borrowRepayPlan.getRepayPeriod());
        List<RepayRecoverPlanBean> repayRecoverPlanList = new ArrayList<RepayRecoverPlanBean>();
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayChargeInterest = BigDecimal.ZERO;// 提前还款利息
        BigDecimal repayDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
        BigDecimal repayOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
            for (int i = 0; i < borrowRecoverList.size(); i++) {
                BorrowRecover borrowRecover = borrowRecoverList.get(i);
                String recoverNid = borrowRecover.getNid();// 出借订单号
                int recoverUserId = borrowRecover.getUserId();// 出借用户userId
                if (borrowRecoverPlans != null && borrowRecoverPlans.size() > 0) {
                    BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                    BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userChargeInterest = BigDecimal.ZERO;// 计算用户提前还款利息
                    BigDecimal userDelayInterest = BigDecimal.ZERO;// 计算用户延期还款利息
                    BigDecimal userOverdueInterest = BigDecimal.ZERO;// 计算用户逾期还款利息
                    BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                    for (int j = 0; j < borrowRecoverPlans.size(); j++) {
                        BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlans.get(j);
                        String recoverPlanNid = borrowRecoverPlan.getNid();// 出借订单号
                        int recoverPlanUserId = borrowRecoverPlan.getUserId();// 出借用户userId
                        if (recoverPlanNid.equals(recoverNid) && recoverUserId == recoverPlanUserId) {
                            RepayRecoverPlanBean repayRecoverPlanBean = new RepayRecoverPlanBean();
                            userAccount = borrowRecoverPlan.getRecoverAccount();// 获取应还款本息
                            userCapital = borrowRecoverPlan.getRecoverCapital();
                            userInterest = borrowRecoverPlan.getRecoverInterest();
                            userChargeInterest = borrowRecoverPlan.getChargeInterest();
                            userManageFee = borrowRecoverPlan.getRecoverFee();// 获取应还款管理费
                            userOverdueInterest = borrowRecoverPlan.getLateInterest();
                            userDelayInterest = borrowRecoverPlan.getDelayInterest();

                            // 给页面展示，就不计算了
                            repayRecoverPlanBean.setRecoverAccountOld(userAccount);
                            repayRecoverPlanBean.setChargeInterestOld(borrowRecoverPlan.getChargeInterest());
                            repayRecoverPlanBean.setDelayInterestOld(borrowRecoverPlan.getDelayInterest());
                            repayRecoverPlanBean.setLateInterestOld(borrowRecoverPlan.getLateInterest());
                            repayRecoverPlanBean.setRecoverAccountYesOld(borrowRecoverPlan.getRecoverAccountYes());

                            repayRecoverPlanBean.setRecoverCapitalOld(borrowRecover.getRecoverCapital());
                            repayRecoverPlanBean.setCreditAmountOld(borrowRecover.getCreditAmount());
                            // 如果发生债转
                            if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                                if(Validator.isNull(borrowRecover.getAccedeOrderId())){
                                    // 直投类项目债转还款
                                    List<CreditRepay> creditRepayList = this.selectCreditRepayList(borrowNid, recoverNid, repayPeriod);
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                                        BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                                        BigDecimal assignOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            CreditRepay creditRepay = creditRepayList.get(k);
                                            RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                            String assignNid = creditRepay.getAssignNid();// 承接订单号
                                            CreditTender creditTender = this.getCreditTender(assignNid);// 查询相应的承接记录
                                            assignAccount = creditRepay.getAssignAccount();// 承接本息
                                            assignCapital = creditRepay.getAssignCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getAssignInterest();
                                            assignChargeInterest = creditRepay.getChargeInterest();
                                            assignOverdueInterest = creditRepay.getLateInterest(); // 计算用户逾期利息
                                            assignDelayInterest = creditRepay.getDelayInterest();// 计算用户延期利息
                                            if (borrowRecoverPlan.getCreditStatus() == 2 && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                            } else {
                                                if (creditRepay.getStatus() == 1) {
                                                    assignManageFee = creditRepay.getManageFee();
                                                } else {
                                                    // 等额本息month、等额本金principal
                                                    if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                                                        if (repayPeriod == borrowPeriod.intValue()) {
                                                            assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                                                                    new BigDecimal(borrow.getAccount()), borrowPeriod, borrowVerifyTime);
                                                        } else {
                                                            assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                                                                    new BigDecimal(borrow.getAccount()), borrowPeriod, borrowVerifyTime);
                                                        }
                                                    }
                                                    // 先息后本endmonth
                                                    else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
                                                        if (repayPeriod == borrowPeriod.intValue()) {
                                                            assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                    borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
                                                        } else {
                                                            assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                    borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
                                                        }
                                                    }
                                                }
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignChargeInterest).add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
                                            userChargeInterest = userChargeInterest.subtract(assignChargeInterest);// 提前还款利息
                                            userDelayInterest = userDelayInterest.subtract(assignDelayInterest);// 提前还款利息
                                            userOverdueInterest = userOverdueInterest.subtract(assignOverdueInterest);// 逾期利息
                                            userManageFee = userManageFee.subtract(assignManageFee);
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).add(assignChargeInterest).add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayChargeInterest = repayChargeInterest.add(assignChargeInterest);// 统计提前还款减少的利息
                                            repayDelayInterest = repayDelayInterest.add(assignDelayInterest);// 统计借款用户总延期利息
                                            repayOverdueInterest = repayOverdueInterest.add(assignOverdueInterest);// 统计借款用户总逾期利息
                                            repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                        }
                                        repayRecoverPlanBean.setCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 统计总额
                                        repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userDelayInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayChargeInterest = repayChargeInterest.add(userChargeInterest);// 统计提前还款减少的利息
                                        repayDelayInterest = repayDelayInterest.add(userDelayInterest);// 统计借款用户总延期利息
                                        repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 统计借款用户总逾期利息
                                        repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                    }
                                }else{
                                    // 计划类项目债转还款
                                    List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, recoverNid, repayPeriod, borrowRecoverPlan.getRecoverStatus());
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                                        BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                                        BigDecimal assignOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        BigDecimal sumCreditAccount = BigDecimal.ZERO;//总承接金额
                                        int hjhFlag = 0;
                                        for (HjhDebtCreditRepay hjhDebtCreditRepayBean : creditRepayList) {
                                            hjhFlag++;
                                            sumCreditAccount = sumCreditAccount.add(hjhDebtCreditRepayBean.getRepayAccount());
                                        }
                                        //判断当前期是否全部承接
                                        boolean overFlag = isOverUndertake(borrowRecover,borrowRecoverPlan.getRecoverAccount(),sumCreditAccount,true,hjhFlag);
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            HjhDebtCreditRepay creditRepay = creditRepayList.get(k);
                                            HjhDebtCreditRepayBean creditRepayBean = new HjhDebtCreditRepayBean();
                                            String assignNid = creditRepay.getAssignOrderId();// 承接订单号
                                            HjhDebtCreditTender creditTender = this.getHjhDebtCreditTender(assignNid);// 查询相应的承接记录
                                            assignAccount = creditRepay.getRepayAccount();// 承接本息
                                            assignCapital = creditRepay.getRepayCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getRepayInterest();
                                            assignChargeInterest = creditRepay.getRepayAdvanceInterest();
                                            assignOverdueInterest = creditRepay.getRepayLateInterest(); // 计算用户逾期利息
                                            assignDelayInterest = creditRepay.getRepayDelayInterest();// 计算用户延期利息
                                            if (!overFlag && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                            } else {
                                                if (creditRepay.getRepayStatus() == 1) {
                                                    assignManageFee = creditRepay.getManageFee();
                                                } else {
                                                    // 等额本息month、等额本金principal
                                                    if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                                                        if (repayPeriod == borrowPeriod.intValue()) {
                                                            assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                                                                    borrowRecover.getRecoverCapital(), borrowPeriod, borrowVerifyTime);
                                                        } else {
                                                            assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                                                                    borrowRecover.getRecoverCapital(), borrowPeriod, borrowVerifyTime);
                                                        }
                                                    }
                                                    // 先息后本endmonth
                                                    else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
                                                        if (repayPeriod == borrowPeriod.intValue()) {
                                                            assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                    borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
                                                        } else {
                                                            assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                    borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
                                                        }
                                                    }
                                                }
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignChargeInterest).add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
//                                            userChargeInterest = userChargeInterest.subtract(assignChargeInterest);// 提前还款利息
//                                            userDelayInterest = userDelayInterest.subtract(assignDelayInterest);// 提前还款利息
//                                            userOverdueInterest = userOverdueInterest.subtract(assignOverdueInterest);// 逾期利息
                                            userManageFee = userManageFee.subtract(assignManageFee);
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).add(assignChargeInterest).add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayChargeInterest = repayChargeInterest.add(assignChargeInterest);// 统计提前还款减少的利息
                                            repayDelayInterest = repayDelayInterest.add(assignDelayInterest);// 统计借款用户总延期利息
                                            repayOverdueInterest = repayOverdueInterest.add(assignOverdueInterest);// 统计借款用户总逾期利息
                                            repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                        }
                                        repayRecoverPlanBean.setHjhCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 统计总额
                                        repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userDelayInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayChargeInterest = repayChargeInterest.add(userChargeInterest);// 统计提前还款减少的利息
                                        repayDelayInterest = repayDelayInterest.add(userDelayInterest);// 统计借款用户总延期利息
                                        repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 统计借款用户总逾期利息
                                        repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                    }
                                }
                            } else {
                                // 统计总和
                                repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userDelayInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(userAccount); // 统计本息总和
                                repayCapital = repayCapital.add(userCapital);
                                repayInterest = repayInterest.add(userInterest);
                                repayChargeInterest = repayChargeInterest.add(userChargeInterest);// 统计提前还款减少的利息
                                repayDelayInterest = repayDelayInterest.add(userDelayInterest);// 统计借款用户总延期利息
                                repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 统计借款用户总逾期利息
                                repayManageFee = repayManageFee.add(userManageFee);// 管理费
                            }
                            BeanUtils.copyProperties(borrowRecoverPlan, repayRecoverPlanBean);
                            repayRecoverPlanBean.setRecoverTotal(userAccount.add(userManageFee));
                            repayRecoverPlanBean.setRecoverAccount(userAccount);
                            repayRecoverPlanBean.setRecoverCapital(userCapital);
                            repayRecoverPlanBean.setRecoverInterest(userInterest);
                            repayRecoverPlanBean.setChargeInterest(userChargeInterest);
                            repayRecoverPlanBean.setDelayInterest(userDelayInterest);
                            repayRecoverPlanBean.setLateInterest(userOverdueInterest);
                            repayRecoverPlanBean.setRecoverFee(userManageFee);
                            repayRecoverPlanList.add(repayRecoverPlanBean);
                        }
                    }
                }
            }
            borrowRepayPlan.setRecoverPlanList(repayRecoverPlanList);
        }
        borrowRepayPlan.setRepayAccountAll(repayTotal);
        borrowRepayPlan.setRepayAccount(repayAccount);
        borrowRepayPlan.setRepayCapital(repayCapital);
        borrowRepayPlan.setRepayInterest(repayInterest);
        borrowRepayPlan.setChargeInterest(repayChargeInterest);
        borrowRepayPlan.setDelayInterest(repayDelayInterest);
        borrowRepayPlan.setLateInterest(repayOverdueInterest);
        borrowRepayPlan.setRepayFee(repayManageFee);
    }

    /**
     * 根据项目编号，出借用户，订单号获取用户的放款总记录
     *
     * @param borrowNid
     * @return
     */
    private List<BorrowRecover> selectBorrowRecoverList(String borrowNid) {
        BorrowRecoverExample example = new BorrowRecoverExample();
        BorrowRecoverExample.Criteria crt = example.createCriteria();
        crt.andBorrowNidEqualTo(borrowNid);
        List<BorrowRecover> borrowRecoverList = this.borrowRecoverMapper.selectByExample(example);
        return borrowRecoverList;
    }
    /**
     *  设置一次性还款方式数据
     * @param form
     * @param userId
     * @param borrow
     * @throws ParseException
     */
    @Deprecated
    private void setRecoverDetail(ProjectBeanVO form, String userId, BorrowCustomizeVO borrow,RepayBean repay)
            throws ParseException {

        String borrowNid = borrow.getBorrowNid();

        form.setRepayPeriod("0");
        form.setManageFee(repay.getRepayFee().toString());
        form.setRepayTotal(repay.getRepayAccountAll().toString()); // 计算的是还款总额
        form.setRepayAccount(repay.getRepayAccount().add(repay.getChargeInterest()).add(repay.getDelayInterest()).add(repay.getLateInterest()).toString());
        form.setRepayCapital(repay.getRepayCapital().toString());
        form.setRepayInterest(repay.getRepayInterest().add(repay.getChargeInterest()).add(repay.getDelayInterest()).add(repay.getLateInterest()).toString());
        form.setShouldInterest(repay.getRepayInterest().toString());

        form.setAdvanceStatus(String.valueOf(repay.getAdvanceStatus()));
        form.setChargeDays(repay.getChargeDays().toString());
        form.setChargeInterest(repay.getChargeInterest().toString());
        if("0".equals(repay.getChargeInterest().toString())){
            form.setChargeInterest("0.00");
        }
        form.setDelayDays(repay.getDelayDays().toString());
        form.setDelayInterest(repay.getDelayInterest().toString());
        form.setLateDays(repay.getLateDays().toString());
        form.setLateInterest(repay.getLateInterest().toString());
        List<ProjectRepayVO> userRepayList = new ArrayList<ProjectRepayVO>();
        ProjectRepayVO userRepayBean = new ProjectRepayVO();
        // 此处是本息和
        userRepayBean.setRepayTotal(repay.getRepayAccountAll().toString());
        userRepayBean.setRepayAccount(repay.getRepayAccount().add(repay.getChargeInterest()).add(repay.getDelayInterest()).add(repay.getLateInterest()).toString());
        userRepayBean.setRepayCapital(repay.getRepayCapital().toString());
        userRepayBean.setRepayInterest(repay.getRepayInterest().toString());
        userRepayBean.setChargeDays(repay.getChargeDays().toString());
        userRepayBean.setChargeInterest(repay.getChargeInterest().multiply(new BigDecimal("-1")).toString());
        if("0".equals(userRepayBean.getChargeInterest())){
            userRepayBean.setChargeInterest("0.00");
        }
        userRepayBean.setDelayDays(repay.getDelayDays().toString());
        userRepayBean.setDelayInterest(repay.getDelayInterest().toString());
        userRepayBean.setManageFee(repay.getRepayFee().toString());
        userRepayBean.setLateDays(repay.getLateDays().toString());
        userRepayBean.setLateInterest(repay.getLateInterest().toString());
        userRepayBean.setRepayTime(GetDate.getDateMyTimeInMillis(Integer.parseInt(repay.getRepayTime().toString())));
        userRepayBean.setStatus(repay.getRepayStatus().toString());
        userRepayBean.setUserId(repay.getUserId().toString());
        userRepayBean.setRepayPeriod("1");
        userRepayBean.setAdvanceStatus(repay.getAdvanceStatus().toString());
        List<RepayRecoverBean> userRecovers = repay.getRecoverList();
        if (userRecovers != null && userRecovers.size() > 0) {
            List<ProjectRepayDetailVO> userRepayDetails = new ArrayList<ProjectRepayDetailVO>();
            for (int i = 0; i < userRecovers.size(); i++) {
                RepayRecoverBean userRecover = userRecovers.get(i);
                // 如果发生债转
                List<RepayCreditRepayBean> creditRepays = userRecover.getCreditRepayList();
                if (creditRepays != null && creditRepays.size() > 0) {
                    // 循环遍历添加记录
                    for (int j = 0; j < creditRepays.size(); j++) {
                        RepayCreditRepayBean creditRepay = creditRepays.get(j);
                        ProjectRepayDetailVO userRepayDetail = new ProjectRepayDetailVO();
                        userRepayDetail.setRepayAccount(creditRepay.getAssignAccount().toString());
                        userRepayDetail.setRepayCapital(creditRepay.getAssignCapital().toString());
                        userRepayDetail.setRepayInterest(creditRepay.getAssignInterest().toString());
                        userRepayDetail.setChargeDays(userRecover.getChargeDays().toString());
                        userRepayDetail.setChargeInterest(creditRepay.getChargeInterest().multiply(new BigDecimal("-1")).toString());
                        if("0".equals(userRepayDetail.getChargeInterest())){
                            userRepayDetail.setChargeInterest("0.00");
                        }
                        userRepayDetail.setDelayDays(userRecover.getDelayDays().toString());
                        userRepayDetail.setDelayInterest(creditRepay.getDelayInterest().toString());
                        userRepayDetail.setManageFee(creditRepay.getManageFee().toString());
                        userRepayDetail.setLateDays(userRecover.getLateDays().toString());
                        userRepayDetail.setLateInterest(creditRepay.getLateInterest().toString());
                        userRepayDetail.setAdvanceStatus(userRecover.getAdvanceStatus().toString());
                        userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(Integer.parseInt(userRecover.getRecoverTime().toString())));
                        BigDecimal total = new BigDecimal("0");
                        if (creditRepay.getStatus() == 1) {
                            total = creditRepay.getAssignRepayAccount().add(creditRepay.getManageFee());
                        } else {
                            total = creditRepay.getAssignTotal();
                        }
                        userRepayDetail.setRepayTotal(total.toString());
                        userRepayDetail.setStatus(creditRepay.getStatus().toString());
                        userRepayDetail.setUserId(creditRepay.getUserId().toString());
                        String userName = searchUserNameById(creditRepay.getUserId());
                        String userNameStr = userName.substring(0, 1).concat("**");
                        userRepayDetail.setUserName(userNameStr);
                        userRepayDetails.add(userRepayDetail);
                    }
                }
                //计划债转列表
                List<HjhDebtCreditRepayBean> hjhCreditRepayList = userRecover.getHjhCreditRepayList();

                if (hjhCreditRepayList != null && hjhCreditRepayList.size() > 0) {
                    for (int k = 0; k < hjhCreditRepayList.size(); k++) {
                        HjhDebtCreditRepayBean creditRepay = hjhCreditRepayList.get(k);
                        ProjectRepayDetailVO userRepayDetail = new ProjectRepayDetailVO();
                        userRepayDetail.setRepayAccount(creditRepay.getRepayAccount().toString());
                        userRepayDetail.setRepayCapital(creditRepay.getRepayCapital().toString());
                        userRepayDetail.setRepayInterest(creditRepay.getRepayInterest().toString());
                        userRepayDetail.setChargeDays(userRecover.getChargeDays().toString());
                        userRepayDetail.setChargeInterest(creditRepay.getRepayAdvanceInterest().multiply(new BigDecimal("-1")).toString());
                        if("0".equals(userRepayDetail.getChargeInterest())){
                            userRepayDetail.setChargeInterest("0.00");
                        }
                        userRepayDetail.setDelayDays(userRecover.getDelayDays().toString());
                        userRepayDetail.setDelayInterest(creditRepay.getRepayDelayInterest().toString());
                        userRepayDetail.setManageFee(creditRepay.getManageFee().toString());
                        userRepayDetail.setLateDays(userRecover.getLateDays().toString());
                        userRepayDetail.setLateInterest(creditRepay.getRepayLateInterest().toString());
                        userRepayDetail.setAdvanceStatus(userRecover.getAdvanceStatus().toString());
                        userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(Integer.parseInt(userRecover.getRecoverTime().toString())));
                        BigDecimal total = new BigDecimal("0");
                        if (creditRepay.getRepayStatus() == 1) {
                            total = creditRepay.getRepayAccount().add(creditRepay.getManageFee());
                        } else {
                            total = creditRepay.getAssignTotal();
                        }
                        userRepayDetail.setRepayTotal(total.toString());
                        userRepayDetail.setStatus(creditRepay.getRepayStatus().toString());
                        userRepayDetail.setUserId(creditRepay.getUserId().toString());
                        String userName = searchUserNameById(creditRepay.getUserId());
                        String userNameStr = userName.substring(0, 1).concat("**");
                        userRepayDetail.setUserName(userNameStr);
                        userRepayDetails.add(userRepayDetail);
                    }
                }

                boolean overFlag = isOverUndertake(userRecover,null,null,false,0);
                if (overFlag) {
                    ProjectRepayDetailVO userRepayDetail = new ProjectRepayDetailVO();
                    userRepayDetail.setRepayAccount(userRecover.getRecoverAccount().toString());
                    userRepayDetail.setRepayCapital(userRecover.getRecoverCapital().toString());
                    userRepayDetail.setRepayInterest(userRecover.getRecoverInterest().toString());
                    userRepayDetail.setChargeDays(userRecover.getChargeDays().toString());
                    userRepayDetail.setChargeInterest(userRecover.getChargeInterest().multiply(new BigDecimal("-1")).toString());
                    if("0".equals(userRepayDetail.getChargeInterest())){
                        userRepayDetail.setChargeInterest("0.00");
                    }
                    userRepayDetail.setDelayDays(userRecover.getDelayDays().toString());
                    userRepayDetail.setDelayInterest(userRecover.getDelayInterest().toString());
                    userRepayDetail.setManageFee(userRecover.getRecoverFee().toString());
                    userRepayDetail.setLateDays(userRecover.getLateDays().toString());
                    userRepayDetail.setLateInterest(userRecover.getLateInterest().toString());
                    userRepayDetail.setAdvanceStatus(userRecover.getAdvanceStatus().toString());
                    userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(Integer.parseInt(userRecover.getRecoverTime().toString())));
                    BigDecimal total = new BigDecimal("0");
                    if (userRecover.getRecoverStatus() == 1) {
                        total = userRecover.getRecoverAccountYes().add(userRecover.getRecoverFee());
                    } else {
                        // recover中account未更新
                        total = userRecover.getRecoverTotal();
                    }
                    userRepayDetail.setRepayTotal(total.toString());
                    userRepayDetail.setStatus(userRecover.getRecoverStatus().toString());
                    userRepayDetail.setUserId(userRecover.getUserId().toString());
                    String userName = searchUserNameById(userRecover.getUserId());
                    String userNameStr = userName.substring(0, 1).concat("**");
                    userRepayDetail.setUserName(userNameStr);
                    userRepayDetails.add(userRepayDetail);
                }
            }
            userRepayBean.setUserRepayDetailList(userRepayDetails);
            userRepayList.add(userRepayBean);
        }
        form.setUserRepayList(userRepayList);
    }

    private String searchUserNameById(Integer userId) {
        //HT_USER 和 HT_R_USER 数据应该同步
        RUser user = rUserMapper.selectByPrimaryKey(userId);
        if(user != null){
            return user.getUsername();
        }
        return "***";
    }

    /**
     * 计算单期的总的还款信息
     *
     * @param userId
     * @param borrow
     * @return
     * @throws ParseException
     */
    @Deprecated
    public RepayBean calculateRepay(int userId, BorrowCustomizeVO borrow) throws ParseException {
       RepayBean repay = new RepayBean();
        // 获取还款总表数据
        BorrowRepay borrowRepay = this.searchRepay(userId, borrow.getBorrowNid());
        // 判断是否存在还款数据
        if (borrowRepay != null) {
            // 获取相应的还款信息
            BeanUtils.copyProperties(borrowRepay, repay);
            // 计划还款时间
            String repayTimeStr = borrowRepay.getRepayTime().toString();
            // 获取用户申请的延期天数
            int delayDays = borrowRepay.getDelayDays().intValue();
            repay.setBorrowPeriod(String.valueOf(borrow.getBorrowPeriod()));
            // 未分期默认传分期为0
            calculateRecover(repay, borrow, repayTimeStr, delayDays);
        }
        return repay;
    }

    public BorrowRepay searchRepay(int userId, String borrowNid) {
        // 获取还款总表数据
        BorrowRepayExample borrowRepayExample = new BorrowRepayExample();
        BorrowRepayExample.Criteria borrowRepayCrt = borrowRepayExample.createCriteria();
        borrowRepayCrt.andUserIdEqualTo(userId);
        borrowRepayCrt.andBorrowNidEqualTo(borrowNid);
        List<BorrowRepay> borrowRepays = borrowRepayMapper.selectByExample(borrowRepayExample);
        if (borrowRepays != null && borrowRepays.size() == 1) {
            return borrowRepays.get(0);
        } else {
            return null;
        }
    }

    /**
     *  计算单期的用户的还款信息
     *
     * @param repay
     * @param borrow
     * @param repayTimeStr
     * @param delayDays
     * @throws ParseException
     */
    @Deprecated
    private void calculateRecover(RepayBean repay, BorrowCustomizeVO borrow, String repayTimeStr, int delayDays) throws ParseException {
        int time = GetDate.getNowTime10();
        // 用户计划还款时间
        String repayTime = GetDate.getDateTimeMyTimeInMillis(Integer.parseInt(repayTimeStr));
        // 用户实际还款时间
        String factRepayTime = GetDate.getDateTimeMyTimeInMillis(time);
        int distanceDays = GetDate.daysBetween(factRepayTime, repayTime);
        String borrowStyle = borrow.getBorrowStyle();
        if (distanceDays < 0) {// 用户延期或者逾期了
            int lateDays = delayDays + distanceDays;
            if (lateDays >= 0) {// 用户延期还款
                delayDays = -distanceDays;
                this.calculateRecoverTotalDelay(repay, borrow, delayDays);
            } else {// 用户逾期还款
                lateDays = -lateDays;
                if (StringUtils.isNotBlank(borrow.getPlanNid())) {//汇计划计算逾期免息金额
                    Integer lateFreeDays = borrow.getLateFreeDays();
                    if (lateFreeDays >= lateDays) {//在免息期以内,正常还款
                        calculateRecoverTotal(repay, borrow, distanceDays);
                    } else {//过了免息期,罚免息期外的利息
                        lateDays = lateDays - lateFreeDays;
                        calculateRecoverTotalLate(repay, borrow, delayDays, lateDays);
                    }
                } else {
                    calculateRecoverTotalLate(repay, borrow, delayDays, lateDays);
                }
            }
        } else {// 用户正常或者提前还款
            // 获取提前还款的阀值
            String repayAdvanceDay = this.getBorrowConfig("REPAY_ADVANCE_TIME");
            int advanceDays = distanceDays;
            // 用户提前还款
            //如果是融通宝项目,不判断提前还款的阀值 add by cwyang 2017-6-14
            int projectType = Integer.valueOf(borrow.getProjectType());
            if (13 == projectType || CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                repayAdvanceDay = "0";

                if (Integer.parseInt(repayAdvanceDay) < advanceDays) {
                    // 计算用户提前还款总额
                    calculateRecoverTotalAdvance(repay, borrow, advanceDays);
                } else {// 用户正常还款
                    // 计算用户实际还款总额
                    calculateRecoverTotal(repay, borrow, advanceDays);
                }
            }else if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                if (Integer.parseInt(repayAdvanceDay) <= advanceDays) {
                    // 计算用户提前还款总额
                    calculateRecoverTotalAdvance(repay, borrow, advanceDays);
                } else {// 用户正常还款
                    // 计算用户实际还款总额
                    calculateRecoverTotal(repay, borrow, advanceDays);
                }

            }
        }
    }

    /**
     * 统计单期还款用户延期还款的总标
     *
     * @param repay
     * @param borrow
     * @param delayDays
     * @throws ParseException
     */
    @Deprecated
    private void calculateRecoverTotalDelay(RepayBean repay, BorrowCustomizeVO borrow, int delayDays) throws ParseException {
        // 用户延期
        String borrowNid = borrow.getBorrowNid();// 项目编号
        String borrowStyle = borrow.getBorrowStyle(); // 还款方式
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate()); // 管理费率
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : Integer.valueOf(borrow.getVerifyTime()); // 初审时间
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : Integer.valueOf(borrow.getBorrowPeriod());// 项目总期数
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        BigDecimal repayDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
        // 查询相应的不分期的还款信息
        List<BorrowRecover> borrowRecovers = searchBorrowRecover(borrowNid);
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            List<RepayRecoverBean> repayRecoverList = new ArrayList<RepayRecoverBean>();
            BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
            BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
            BigDecimal userManageFee = BigDecimal.ZERO;// 获取应还款管理费
            for (int i = 0; i < borrowRecovers.size(); i++) {
                BorrowRecover borrowRecover = borrowRecovers.get(i);
                RepayRecoverBean repayRecoverBean = new RepayRecoverBean();
                String tenderOrderId = borrowRecover.getNid();// 出借订单号
                userAccount = borrowRecover.getRecoverAccount();
                userCapital = borrowRecover.getRecoverCapital();
                userInterest = borrowRecover.getRecoverInterest();
                // 计算用户延期利息
                userDelayInterest = UnnormalRepayUtils.delayRepayInterest(userCapital, new BigDecimal(borrow.getBorrowApr()), delayDays);
                if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle) && delayDays > 0) {// 先息后本
                    BigDecimal userDelayInterest1 = UnnormalRepayUtils.delayRepayInterest(borrow.getRepayAccountCapital(), new BigDecimal(borrow.getBorrowApr()), delayDays);
                    logger.info("第三方查询一次性还款先息后本原计算延期利息本金：{},新计算延期利息本金：{}", userCapital, borrow.getRepayAccountCapital());
                    logger.info("第三方查询一次性还款先息后本原延期利息：{},新延期利息：{}", userDelayInterest, userDelayInterest1);
                }
                // 用户管理费
                userManageFee = borrowRecover.getRecoverFee();
                repayRecoverBean.setRecoverCapitalOld(userCapital);
                repayRecoverBean.setCreditAmountOld(borrowRecover.getCreditAmount());

                // 如果已经发生债转此笔不考虑提前，延期，逾期还款
                if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                    // 如果是直投还款
                    if (Validator.isNull(borrowRecover.getAccedeOrderId())) {
                        List<CreditRepay> creditRepayList = selectCreditRepay(borrowNid, tenderOrderId, 1, 0);
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                CreditRepay creditRepay = creditRepayList.get(j);
                                RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                assignCapital = creditRepay.getAssignCapital();// 承接本金
                                assignInterest = creditRepay.getAssignInterest();
                                // 计算用户实际获得的本息和
                                assignAccount = creditRepay.getAssignAccount();
                                //最后一笔兜底
                                if (borrowRecover.getCreditStatus() == 2 && j == creditRepayList.size() - 1) {
                                    assignManageFee = userManageFee;
                                    assignDelayInterest = userDelayInterest;
                                } else {
                                    // 按月计息，到期还本还息end
                                    if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                       assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 按天计息到期还本还息
                                    else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByDay(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 计算用户延期利息
                                    assignDelayInterest = UnnormalRepayUtils.delayRepayInterest(assignCapital, new BigDecimal(borrow.getBorrowApr()), delayDays);
                                    if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle) && delayDays > 0) {// 先息后本
                                        BigDecimal userDelayInterest1 = UnnormalRepayUtils.delayRepayInterest(borrow.getRepayAccountCapital(), new BigDecimal(borrow.getBorrowApr()), delayDays);
                                        logger.info("第三方查询一次性还款直投债转先息后本原计算延期利息本金：{},新计算延期利息本金：{}", userCapital, borrow.getRepayAccountCapital());
                                        logger.info("第三方查询一次性还款直投债转先息后本原延期利息：{},新延期利息：{}", userDelayInterest, userDelayInterest1);
                                    }
                                }
                                BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                creditRepayBean.setAssignTotal(assignAccount.add(assignDelayInterest).add(assignManageFee));
                                creditRepayBean.setManageFee(assignManageFee);
                                creditRepayBean.setAdvanceStatus(2);
                                creditRepayBean.setDelayDays(delayDays);
                                creditRepayBean.setDelayInterest(assignDelayInterest);
                                creditRepayBeanList.add(creditRepayBean);
                                // 统计出让人还款金额
                                userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                userCapital = userCapital.subtract(assignCapital);
                                userInterest = userInterest.subtract(assignInterest);
                                userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                userDelayInterest = userDelayInterest.subtract(assignDelayInterest);// 提前还款利息
                                // 统计总额
                                repayTotal = repayTotal.add(assignAccount).add(assignDelayInterest).add(assignManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                repayCapital = repayCapital.add(assignCapital);
                                repayInterest = repayInterest.add(assignInterest);
                                repayManageFee = repayManageFee.add(assignManageFee);// 統計管理費
                                repayDelayInterest = repayDelayInterest.add(assignDelayInterest);
                            }
                            repayRecoverBean.setCreditRepayList(creditRepayBeanList);
                        }
                        if (borrowRecover.getCreditStatus() != 2) {
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                            repayDelayInterest = repayDelayInterest.add(userDelayInterest);
                        }
                    } else {
                        // 计划还款
                        List<HjhDebtCreditRepay> creditRepayList = selectHjhDebtCreditRepay(borrowNid, tenderOrderId, 1, borrowRecover.getRecoverStatus());
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                            //判断当前期是否全部承接
                            boolean overFlag = isOverUndertake(borrowRecover,null,null,false,0);
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                HjhDebtCreditRepay creditRepay = creditRepayList.get(j);
                                HjhDebtCreditRepayBean creditRepayBean = new HjhDebtCreditRepayBean();
                                assignCapital = creditRepay.getRepayCapital();// 承接本金
                                assignInterest = creditRepay.getRepayInterest();
                                // 计算用户实际获得的本息和
                                assignAccount = creditRepay.getRepayAccount();
                                //最后一笔兜底
                                if (!overFlag && j == creditRepayList.size() - 1) {
                                    assignManageFee = userManageFee;
                                    assignDelayInterest = userDelayInterest;
                                } else {
                                    // 按月计息，到期还本还息end
                                    if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 按天计息到期还本还息
                                    else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByDay(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 计算用户延期利息
                                    assignDelayInterest = UnnormalRepayUtils.delayRepayInterest(assignCapital, new BigDecimal(borrow.getBorrowApr()), delayDays);
                                    if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle) && delayDays > 0) {// 先息后本
                                        BigDecimal userDelayInterest1 = UnnormalRepayUtils.delayRepayInterest(borrow.getRepayAccountCapital(), new BigDecimal(borrow.getBorrowApr()), delayDays);
                                        logger.info("第三方查询一次性还款计划债转先息后本原计算延期利息本金：{},新计算延期利息本金：{}", userCapital, borrow.getRepayAccountCapital());
                                        logger.info("第三方查询一次性还款计划债转先息后本原延期利息：{},新延期利息：{}", userDelayInterest, userDelayInterest1);
                                    }
                                }
                                BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                creditRepayBean.setAssignTotal(assignAccount.add(assignDelayInterest).add(assignManageFee));
                                creditRepayBean.setManageFee(assignManageFee);
                                creditRepayBean.setAdvanceStatus(2);
                                creditRepayBean.setDelayDays(delayDays);
                                creditRepayBean.setRepayDelayInterest(assignDelayInterest);
                                creditRepayBeanList.add(creditRepayBean);
                                // 统计出让人还款金额
                                userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                userCapital = userCapital.subtract(assignCapital);
                                userInterest = userInterest.subtract(assignInterest);
                                userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
//                                userDelayInterest = userDelayInterest.subtract(assignDelayInterest);// 提前还款利息
                                // 统计总额
                                repayTotal = repayTotal.add(assignAccount).add(assignDelayInterest).add(assignManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                repayCapital = repayCapital.add(assignCapital);
                                repayInterest = repayInterest.add(assignInterest);
                                repayManageFee = repayManageFee.add(assignManageFee);// 統計管理費
                                repayDelayInterest = repayDelayInterest.add(assignDelayInterest);
                            }
                            repayRecoverBean.setHjhCreditRepayList(creditRepayBeanList);
                        }
                        if (borrowRecover.getCreditStatus() != 2) {
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                            repayDelayInterest = repayDelayInterest.add(userDelayInterest);
                        }
                    }

                } else {
                    // 统计总额
                    repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userManageFee);// 统计总和本息+管理费
                    repayAccount = repayAccount.add(userAccount); // 统计本息总和
                    repayCapital = repayCapital.add(userCapital);
                    repayInterest = repayInterest.add(userInterest);
                    repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                    repayDelayInterest = repayDelayInterest.add(userDelayInterest);
                }
                // 用户延期还款
                BeanUtils.copyProperties(borrowRecover, repayRecoverBean);
                repayRecoverBean.setRecoverTotal(userAccount.add(userDelayInterest).add(userManageFee));
                repayRecoverBean.setRecoverAccount(userAccount);
                repayRecoverBean.setRecoverCapital(userCapital);
                repayRecoverBean.setRecoverInterest(userInterest);
                repayRecoverBean.setRecoverFee(userManageFee);
                repayRecoverBean.setAdvanceStatus(2);
                repayRecoverBean.setDelayInterest(userDelayInterest); // 延期利息
                repayRecoverBean.setDelayDays(delayDays);
                repayRecoverList.add(repayRecoverBean);
            }
            repay.setRecoverList(repayRecoverList);
        }
        repay.setRepayAccountAll(repayTotal);
        repay.setRepayAccount(repayAccount);
        repay.setRepayCapital(repayCapital);
        repay.setRepayInterest(repayInterest);
        repay.setRepayFee(repayManageFee);
        repay.setAdvanceStatus(2);
        repay.setDelayDays(delayDays);
        repay.setDelayInterest(repayDelayInterest);
    }

    /**
     * 根据项目id查询相应的用户的待还款信息
     *
     * @param borrowNid
     * @return
     */
    public List<BorrowRecover> searchBorrowRecover(String borrowNid) {
        BorrowRecoverExample example = new BorrowRecoverExample();
        BorrowRecoverExample.Criteria crt = example.createCriteria();
        crt.andBorrowNidEqualTo(borrowNid);
        List<BorrowRecover> borrowRecovers = borrowRecoverMapper.selectByExample(example);
        return borrowRecovers;
    }

    /**
     * 查询出借用户分期的详情
     *
     * @param borrowNid
     * @param period
     * @return
     */
    private List<BorrowRecoverPlan> searchBorrowRecoverPlan(String borrowNid, int period) {
        BorrowRecoverPlanExample example = new BorrowRecoverPlanExample();
        BorrowRecoverPlanExample.Criteria crt = example.createCriteria();
        crt.andBorrowNidEqualTo(borrowNid);
        crt.andRecoverPeriodEqualTo(period);
        List<BorrowRecoverPlan> borrowRecovers = borrowRecoverPlanMapper.selectByExample(example);
        return borrowRecovers;
    }

    /***
     * 查询相应的债转还款记录
     *
     * @param borrowNid
     * @param tenderOrderId
     * @param periodNow
     * @param status
     * @return
     */
    private List<CreditRepay> selectCreditRepay(String borrowNid, String tenderOrderId, Integer periodNow, int status) {
        CreditRepayExample example = new CreditRepayExample();
        CreditRepayExample.Criteria crt = example.createCriteria();
        crt.andBidNidEqualTo(borrowNid);
        crt.andCreditTenderNidEqualTo(tenderOrderId);
        crt.andRecoverPeriodEqualTo(periodNow);
        crt.andStatusEqualTo(status);
        example.setOrderByClause("id ASC");
        List<CreditRepay> creditRepayList = this.creditRepayMapper.selectByExample(example);
        return creditRepayList;
    }

    /**
     * 查询相应的债转还款记录
     *
     * @param borrowNid
     * @param tenderOrderId
     * @param periodNow
     * @return
     */
    private List<CreditRepay> selectCreditRepayList(String borrowNid, String tenderOrderId, Integer periodNow) {
        CreditRepayExample example = new CreditRepayExample();
        CreditRepayExample.Criteria crt = example.createCriteria();
        crt.andBidNidEqualTo(borrowNid);
        crt.andCreditTenderNidEqualTo(tenderOrderId);
        crt.andRecoverPeriodEqualTo(periodNow);
        example.setOrderByClause("id ASC");
        List<CreditRepay> creditRepayList = this.creditRepayMapper.selectByExample(example);
        return creditRepayList;
    }
    /**
     *  查询相应的债转还款记录
     * @param borrowNid
     * @param tenderOrderId
     * @param periodNow
     * @param status
     * @return
     */
    private List<HjhDebtCreditRepay> selectHjhDebtCreditRepay(String borrowNid, String tenderOrderId, int periodNow, int status) {
        HjhDebtCreditRepayExample example = new HjhDebtCreditRepayExample();
        HjhDebtCreditRepayExample.Criteria crt = example.createCriteria();
        crt.andBorrowNidEqualTo(borrowNid);
        crt.andInvestOrderIdEqualTo(tenderOrderId);
        crt.andRepayPeriodEqualTo(periodNow);
        crt.andRepayStatusEqualTo(status);
        crt.andDelFlagEqualTo(0);
        crt.andRepayAccountGreaterThan(BigDecimal.ZERO);
        example.setOrderByClause("id ASC");
        List<HjhDebtCreditRepay> creditRepayList = this.hjhDebtCreditRepayMapper.selectByExample(example);
        return creditRepayList;
    }

    /**
     * 判断是否完全承接  true:未完全承接
     * @param borrowRecover
     * @param recoverPlanCapital
     * @param creditSumCapital
     * @param isMonth
     * @param hjhFlag
     * @return
     */
    private boolean isOverUndertake(RepayRecoverBean borrowRecover, BigDecimal recoverPlanCapital, BigDecimal creditSumCapital, boolean isMonth, int hjhFlag) {
        if (isMonth) {//分期标的并且是计划债转
            if (hjhFlag > 0) {
                //分期待还本金 大于 承接本金
                if (recoverPlanCapital.compareTo(creditSumCapital) > 0) {
                    return true;
                }
            }else{
                return true;
            }
        }else{
            if (borrowRecover.getRecoverCapitalOld().compareTo(borrowRecover.getCreditAmountOld()) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否完全承接  true:未完全承接
     * @param borrowRecover
     * @param recoverPlanCapital
     * @param creditSumCapital
     * @param isMonth
     * @param hjhFlag
     * @return
     */
    private boolean isOverUndertake(BorrowRecover borrowRecover, BigDecimal recoverPlanCapital, BigDecimal creditSumCapital, boolean isMonth, int hjhFlag) {
        if (isMonth) {//分期标的并且是计划债转
            if (hjhFlag > 0) {
                //分期待还本金 大于 承接本金
                if (recoverPlanCapital.compareTo(creditSumCapital) > 0) {
                    return true;
                }
            }else{
                return true;
            }
        }else{
            if (borrowRecover.getRecoverCapital().compareTo(borrowRecover.getCreditAmount()) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否完全承接  true:未完全承接
     * @param userRecoverPlan
     * @param creditSumCapital
     * @param creditSumCapital
     * @param isMonth
     * @return
     */
    private boolean isOverUndertake(RepayRecoverPlanBean userRecoverPlan, BigDecimal recoverPlanCapital, BigDecimal creditSumCapital, boolean isMonth, int hjhFlag) {
        if (isMonth) {//分期标的并且是计划债转
            if (hjhFlag > 0) {
                //分期待还本金 大于 承接本金
                if (recoverPlanCapital.compareTo(creditSumCapital) > 0) {
                    return true;
                }
            }else{
                return true;
            }
        }else{
            if (userRecoverPlan.getRecoverCapitalOld().compareTo(userRecoverPlan.getCreditAmountOld()) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 统计单期还款用户正常还款的总标
     *
     * @param repay
     * @param borrow
     * @param interestDay
     * @throws ParseException
     */
    @Deprecated
    private void calculateRecoverTotal(RepayBean repay, BorrowCustomizeVO borrow, int interestDay) throws ParseException {

        // 正常还款
        String borrowNid = borrow.getBorrowNid();// 项目编号
        String borrowStyle = borrow.getBorrowStyle(); // 还款方式
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());// 管理费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());// 差异费率
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : Integer.valueOf(borrow.getVerifyTime());// 初审时间
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : Integer.valueOf(borrow.getBorrowPeriod());// 项目总期数
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        List<BorrowRecover> borrowRecovers = searchBorrowRecover(borrowNid);
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            List<RepayRecoverBean> repayRecoverList = new ArrayList<RepayRecoverBean>();
            BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
            BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户還款管理費
            for (int i = 0; i < borrowRecovers.size(); i++) {
                BorrowRecover borrowRecover = borrowRecovers.get(i);
                RepayRecoverBean repayRecoverBean = new RepayRecoverBean();
                String tenderOrderId = borrowRecover.getNid();// 出借订单号
                userAccount = borrowRecover.getRecoverAccount();// 计算用户实际获得的本息和
                userCapital = borrowRecover.getRecoverCapital();
                userInterest = borrowRecover.getRecoverInterest();
                userManageFee = borrowRecover.getRecoverFee();// 计算用户還款管理費

                repayRecoverBean.setRecoverCapitalOld(userCapital);
                repayRecoverBean.setCreditAmountOld(borrowRecover.getCreditAmount());

                if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                    if(Validator.isNull(borrowRecover.getAccedeOrderId())){
                        // 出借项目还款
                        List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, tenderOrderId, 1, 0);
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                CreditRepay creditRepay = creditRepayList.get(j);
                                RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                assignAccount = creditRepay.getAssignAccount();// 计算用户实际获得的本息和
                                assignCapital = creditRepay.getAssignCapital();// 用户实际还款本本金
                                assignInterest = creditRepay.getAssignInterest();
                                if (borrowRecover.getCreditStatus() == 2 && j == creditRepayList.size() - 1) {
                                    assignManageFee = userManageFee;
                                } else {
                                    // 按月计息，到期还本还息end
                                    if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 按天计息到期还本还息
                                    else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByDay(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                }
                                BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                creditRepayBean.setAssignTotal(assignAccount.add(assignManageFee));
                                creditRepayBean.setAssignInterest(assignInterest);
                                creditRepayBean.setManageFee(assignManageFee);
                                creditRepayBean.setAdvanceStatus(0);
                                creditRepayBean.setChargeInterest(BigDecimal.ZERO);
                                creditRepayBean.setChargeDays(interestDay);
                                creditRepayBeanList.add(creditRepayBean);
                                // 统计出让人还款金额
                                userAccount = userAccount.subtract(assignAccount);
                                userCapital = userCapital.subtract(assignCapital);
                                userInterest = userInterest.subtract(assignInterest);
                                userManageFee = userManageFee.subtract(assignManageFee);
                                // 统计总额
                                repayTotal = repayTotal.add(assignAccount).add(assignManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                repayCapital = repayCapital.add(assignCapital);
                                repayInterest = repayInterest.add(assignInterest);
                                repayManageFee = repayManageFee.add(assignManageFee);// 統計管理費
                            }
                            repayRecoverBean.setCreditRepayList(creditRepayBeanList);
                        }
                        if (borrowRecover.getCreditStatus() != 2) {
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                        }
                    }else{
                        // 计划类还款
                        List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, tenderOrderId, 1, borrowRecover.getRecoverStatus());
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            //判断当前期是否全部承接
                            boolean overFlag = isOverUndertake(borrowRecover,null,null,false,0);
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                HjhDebtCreditRepay creditRepay = creditRepayList.get(j);
                                HjhDebtCreditRepayBean creditRepayBean = new HjhDebtCreditRepayBean();
                                assignAccount = creditRepay.getRepayAccount();// 计算用户实际获得的本息和
                                assignCapital = creditRepay.getRepayCapital();// 用户实际还款本本金
                                assignInterest = creditRepay.getRepayInterest();
                                if (!overFlag && j == creditRepayList.size() - 1) {
                                    assignManageFee = userManageFee;
                                } else {
                                    // 按月计息，到期还本还息end
                                    if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 按天计息到期还本还息
                                    else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByDay(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                }
                                BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                creditRepayBean.setAssignTotal(assignAccount.add(assignManageFee));
                                creditRepayBean.setRepayInterest(assignInterest);
                                creditRepayBean.setManageFee(assignManageFee);
                                creditRepayBean.setAdvanceStatus(0);
                                creditRepayBean.setRepayAdvanceInterest(BigDecimal.ZERO);
                                creditRepayBean.setAdvanceDays(interestDay);
                                creditRepayBeanList.add(creditRepayBean);
                                // 统计出让人还款金额
                                userAccount = userAccount.subtract(assignAccount);
                                userCapital = userCapital.subtract(assignCapital);
                                userInterest = userInterest.subtract(assignInterest);
                                userManageFee = userManageFee.subtract(assignManageFee);
                                // 统计总额
                                repayTotal = repayTotal.add(assignAccount).add(assignManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                repayCapital = repayCapital.add(assignCapital);
                                repayInterest = repayInterest.add(assignInterest);
                                repayManageFee = repayManageFee.add(assignManageFee);// 統計管理費
                            }
                            repayRecoverBean.setHjhCreditRepayList(creditRepayBeanList);
                        }
                        if (borrowRecover.getCreditStatus() != 2) {
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                        }
                    }
                } else {
                    repayTotal = repayTotal.add(userAccount).add(userManageFee);// 统计总和本息+管理费
                    repayAccount = repayAccount.add(userAccount); // 统计本息总和
                    repayCapital = repayCapital.add(userCapital);
                    repayInterest = repayInterest.add(userInterest);
                    repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                }
                BeanUtils.copyProperties(borrowRecover, repayRecoverBean);
                repayRecoverBean.setRecoverTotal(userAccount.add(userManageFee));
                repayRecoverBean.setRecoverAccount(userAccount);
                repayRecoverBean.setRecoverCapital(userCapital);
                repayRecoverBean.setRecoverInterest(userInterest);
                repayRecoverBean.setRecoverFee(userManageFee);
                repayRecoverBean.setChargeDays(interestDay);
                repayRecoverBean.setAdvanceStatus(0);
                repayRecoverList.add(repayRecoverBean);
            }
            repay.setRecoverList(repayRecoverList);
        }
        repay.setRepayAccountAll(repayTotal);
        repay.setRepayAccount(repayAccount);
        repay.setRepayCapital(repayCapital);
        repay.setRepayInterest(repayInterest);
        repay.setRepayFee(repayManageFee);
        repay.setChargeDays(interestDay);
        repay.setAdvanceStatus(0);
    }

    /**
     * 统计单期还款用户逾期还款的总标
     *
     * @param repay
     * @param borrow
     * @param delayDays
     * @param lateDays
     * @throws ParseException
     */
    @Deprecated
    private void calculateRecoverTotalLate(RepayBean repay, BorrowCustomizeVO borrow, int delayDays, int lateDays) throws ParseException {

        // 项目编号
        String borrowNid = borrow.getBorrowNid();
        // 还款方式
        String borrowStyle = borrow.getBorrowStyle();
        // 管理费率
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : Integer.valueOf(borrow.getVerifyTime());
        // 项目总期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : Integer.valueOf(borrow.getBorrowPeriod());
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        BigDecimal repayDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
        BigDecimal repayOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
        List<BorrowRecover> borrowRecovers = searchBorrowRecover(borrowNid);
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            List<RepayRecoverBean> repayRecoverList = new ArrayList<RepayRecoverBean>();
            BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
            BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户還款管理費
            BigDecimal userDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
            BigDecimal userOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
            for (int i = 0; i < borrowRecovers.size(); i++) {
                BorrowRecover borrowRecover = borrowRecovers.get(i);
                RepayRecoverBean repayRecoverBean = new RepayRecoverBean();
                String tenderOrderId = borrowRecover.getNid();// 出借订单号
                userAccount = borrowRecover.getRecoverAccount();// 获取未还款前用户能够获取的本息和
                userCapital = borrowRecover.getRecoverCapital();
                userInterest = borrowRecover.getRecoverInterest();
                // 计算用户逾期利息
                userOverdueInterest = UnnormalRepayUtils.overdueRepayOverdueInterest(userAccount, lateDays);
                if (StringUtils.isNotBlank(borrow.getPlanNid())) {//计划相关
                    //BigDecimal planRate = new BigDecimal(borrow.getLateInterestRate());
                    userOverdueInterest = UnnormalRepayUtils.overduePlanRepayOverdueInterest(userAccount, lateDays, borrow.getLateInterestRate());
                }
                // 计算用户延期利息
                userDelayInterest = UnnormalRepayUtils.overdueRepayDelayInterest(userCapital, new BigDecimal(borrow.getBorrowApr()), delayDays);
                // 获取应还款管理费
                userManageFee = borrowRecover.getRecoverFee();

                repayRecoverBean.setRecoverCapitalOld(userCapital);
                repayRecoverBean.setCreditAmountOld(borrowRecover.getCreditAmount());

                if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                    if(Validator.isNull(borrowRecover.getAccedeOrderId())){
                        // 直投类项目还款
                        List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, tenderOrderId, 1, 0);
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                            BigDecimal assignOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                CreditRepay creditRepay = creditRepayList.get(j);
                                RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                assignAccount = creditRepay.getAssignAccount();// 承接本息
                                assignCapital = creditRepay.getAssignCapital();// 承接本金
                                assignInterest = creditRepay.getAssignInterest();
                                //最后一笔兜底
                                if (borrowRecover.getCreditStatus() == 2 && j == creditRepayList.size() - 1) {
                                    assignManageFee = userManageFee;
                                    // 计算用户逾期利息
                                    assignOverdueInterest = userOverdueInterest;
                                    // 计算用户延期利息
                                    assignDelayInterest = userDelayInterest;
                                } else {
                                    // 按月计息，到期还本还息end
                                    if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 按天计息到期还本还息
                                    else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByDay(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 计算用户逾期利息
                                    assignOverdueInterest = UnnormalRepayUtils.overdueRepayOverdueInterest(assignAccount, lateDays);
                                    if (StringUtils.isNotBlank(borrow.getPlanNid())) {//计划相关
                                        //BigDecimal planRate = new BigDecimal(borrow.getLateInterestRate());
                                        userOverdueInterest = UnnormalRepayUtils.overduePlanRepayOverdueInterest(assignAccount, lateDays, borrow.getLateInterestRate());
                                    }
                                    // 计算用户延期利息
                                    assignDelayInterest = UnnormalRepayUtils.overdueRepayDelayInterest(assignCapital, new BigDecimal(borrow.getBorrowApr()), delayDays);
                                }
                                BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                creditRepayBean.setAssignTotal(assignAccount.add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee));
                                creditRepayBean.setManageFee(assignManageFee);
                                creditRepayBean.setAdvanceStatus(3);
                                creditRepayBean.setDelayDays(delayDays);
                                creditRepayBean.setDelayInterest(assignDelayInterest);
                                creditRepayBean.setLateDays(lateDays);
                                creditRepayBean.setLateInterest(assignOverdueInterest);
                                creditRepayBeanList.add(creditRepayBean);
                                // 统计出让人还款金额
                                userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                userCapital = userCapital.subtract(assignCapital);
                                userInterest = userInterest.subtract(assignInterest);
                                userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                userDelayInterest = userDelayInterest.subtract(assignDelayInterest);// 提前还款利息
                                userOverdueInterest = userOverdueInterest.subtract(assignOverdueInterest);// 逾期利息
                                // 统计总额
                                repayTotal = repayTotal.add(assignAccount).add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                repayCapital = repayCapital.add(assignCapital);
                                repayInterest = repayInterest.add(assignInterest);
                                repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                repayDelayInterest = repayDelayInterest.add(assignDelayInterest);// 统计借款用户总延期利息
                                repayOverdueInterest = repayOverdueInterest.add(assignOverdueInterest);// 统计借款用户总逾期利息
                            }
                            repayRecoverBean.setCreditRepayList(creditRepayBeanList);
                        }
                        if (borrowRecover.getCreditStatus() != 2) {
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                            repayDelayInterest = repayDelayInterest.add(userDelayInterest);// 统计借款用户总延期利息
                            repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 统计借款用户总逾期利息
                        }
                    }else{
                        // 计划类债转还款
                        List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, tenderOrderId, 1, borrowRecover.getRecoverStatus());
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                            BigDecimal assignOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
                            //判断当前期是否全部承接
                            boolean overFlag = isOverUndertake(borrowRecover,null,null,false,0);
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                HjhDebtCreditRepay creditRepay = creditRepayList.get(j);
                                HjhDebtCreditRepayBean creditRepayBean = new HjhDebtCreditRepayBean();
                                assignAccount = creditRepay.getRepayAccount();// 承接本息
                                assignCapital = creditRepay.getRepayCapital();// 承接本金
                                assignInterest = creditRepay.getRepayInterest();
                                //最后一笔兜底
                                if (!overFlag && j == creditRepayList.size() - 1) {
                                    assignManageFee = userManageFee;
                                    // 计算用户逾期利息
                                    assignOverdueInterest = userOverdueInterest;
                                    // 计算用户延期利息
                                    assignDelayInterest = userDelayInterest;
                                } else {
                                    // 按月计息，到期还本还息end
                                    if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 按天计息到期还本还息
                                    else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByDay(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 计算用户逾期利息
                                    assignOverdueInterest = UnnormalRepayUtils.overdueRepayOverdueInterest(assignAccount, lateDays);
                                    if (StringUtils.isNotBlank(borrow.getPlanNid())) {//计划相关
                                        //BigDecimal planRate = new BigDecimal(borrow.getLateInterestRate());
                                        userOverdueInterest = UnnormalRepayUtils.overduePlanRepayOverdueInterest(assignAccount, lateDays, borrow.getLateInterestRate());
                                    }
                                    // 计算用户延期利息
                                    assignDelayInterest = UnnormalRepayUtils.overdueRepayDelayInterest(assignCapital, new BigDecimal(borrow.getBorrowApr()), delayDays);
                                }
                                BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                creditRepayBean.setAssignTotal(assignAccount.add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee));
                                creditRepayBean.setManageFee(assignManageFee);
                                creditRepayBean.setAdvanceStatus(3);
                                creditRepayBean.setDelayDays(delayDays);
                                creditRepayBean.setRepayDelayInterest(assignDelayInterest);
                                creditRepayBean.setLateDays(lateDays);
                                creditRepayBean.setRepayLateInterest(assignOverdueInterest);
                                creditRepayBeanList.add(creditRepayBean);
                                // 统计出让人还款金额
                                userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                userCapital = userCapital.subtract(assignCapital);
                                userInterest = userInterest.subtract(assignInterest);
                                userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
//                                userDelayInterest = userDelayInterest.subtract(assignDelayInterest);// 提前还款利息
//                                userOverdueInterest = userOverdueInterest.subtract(assignOverdueInterest);// 逾期利息
                                // 统计总额
                                repayTotal = repayTotal.add(assignAccount).add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                repayCapital = repayCapital.add(assignCapital);
                                repayInterest = repayInterest.add(assignInterest);
                                repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                repayDelayInterest = repayDelayInterest.add(assignDelayInterest);// 统计借款用户总延期利息
                                repayOverdueInterest = repayOverdueInterest.add(assignOverdueInterest);// 统计借款用户总逾期利息
                            }
                            repayRecoverBean.setHjhCreditRepayList(creditRepayBeanList);
                        }
                        if (borrowRecover.getCreditStatus() != 2) {
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                            repayDelayInterest = repayDelayInterest.add(userDelayInterest);// 统计借款用户总延期利息
                            repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 统计借款用户总逾期利息
                        }
                    }
                } else {
                    // 统计总额
                    repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                    repayAccount = repayAccount.add(userAccount);// 统计总和本息
                    repayCapital = repayCapital.add(userCapital);
                    repayInterest = repayInterest.add(userInterest);
                    repayManageFee = repayManageFee.add(userManageFee);// 管理费
                    repayDelayInterest = repayDelayInterest.add(userDelayInterest);// 统计借款用户总延期利息
                    repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 统计借款用户总逾期利息
                }
                // 延期还款利息
                BeanUtils.copyProperties(borrowRecover, repayRecoverBean);
                // 用户延期还款
                repayRecoverBean.setRecoverTotal(userAccount.add(userDelayInterest).add(userOverdueInterest).add(userManageFee));
                repayRecoverBean.setRecoverAccount(userAccount);
                repayRecoverBean.setRecoverCapital(userCapital);
                repayRecoverBean.setRecoverInterest(userInterest);
                repayRecoverBean.setRecoverFee(userManageFee);
                repayRecoverBean.setDelayInterest(userDelayInterest);
                repayRecoverBean.setLateInterest(userOverdueInterest);
                repayRecoverBean.setDelayDays(delayDays);
                repayRecoverBean.setLateDays(lateDays);
                repayRecoverBean.setAdvanceStatus(3);
                repayRecoverList.add(repayRecoverBean);
            }
            repay.setRecoverList(repayRecoverList);
        }
        repay.setRepayAccountAll(repayTotal);
        repay.setRepayAccount(repayAccount);
        repay.setRepayCapital(repayCapital);
        repay.setRepayInterest(repayInterest);
        repay.setRepayFee(repayManageFee);
        repay.setDelayDays(delayDays);
        repay.setDelayInterest(repayDelayInterest);
        repay.setLateDays(lateDays);
        repay.setLateInterest(repayOverdueInterest);
        repay.setAdvanceStatus(3);
    }

    /**
     * 统计单期还款用户提前还款的总标
     *
     * @param repay
     * @param borrow
     * @param interestDay
     * @throws ParseException
     */
    @Deprecated
    private void calculateRecoverTotalAdvance(RepayBean repay, BorrowCustomizeVO borrow, int interestDay) throws ParseException {

        // 用户提前还款
        String borrowNid = borrow.getBorrowNid();// 项目编号
        String borrowStyle = borrow.getBorrowStyle();// 还款方式
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());// 管理费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());// 差异费率
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : Integer.valueOf(borrow.getVerifyTime());// 初审时间
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : Integer.valueOf(borrow.getBorrowPeriod());// 项目总期数
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayChargeInterest = BigDecimal.ZERO;// 提前还款利息
        BigDecimal repayChargePenaltyInterest = BigDecimal.ZERO;// 提前还款罚息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
//        int time = GetDate.getNowTime10(); // 现在时间
        String factRepayTime = GetDate.getDayStart(new Date());
        //获取标的总的提前减息利息

//        BigDecimal totalChargeInterest = new BigDecimal(0);
//        // 实际持有天数
//        int totalAcctualDays = GetDate.daysBetween(GetDate.getDayStart(GetDate.getDate(repay.getCreateTime())),factRepayTime);
//        // 用户提前还款减少的利息
//        BigDecimal tatalAcctualInterest = UnnormalRepayUtils.aheadEndRepayInterest(repay.getRepayCapital(), borrow.getBorrowApr(), totalAcctualDays);
//        totalChargeInterest = repay.getRepayInterest().subtract(tatalAcctualInterest);
//        if(tatalAcctualInterest.compareTo(repay.getRepayInterest()) >= 0){
//            totalChargeInterest = BigDecimal.ZERO;
//        }

        List<BorrowRecover> borrowRecovers = searchBorrowRecover(borrow.getBorrowNid());
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            List<RepayRecoverBean> repayRecoverList = new ArrayList<RepayRecoverBean>();
            BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
            BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
            BigDecimal userChargePenaltyInterest = BigDecimal.ZERO;// 计算用户提前还款罚息
            BigDecimal userManageFee = BigDecimal.ZERO;// 获取应还款管理费
            for (int i = 0; i < borrowRecovers.size(); i++) {
                BorrowRecover borrowRecover = borrowRecovers.get(i);
                RepayRecoverBean repayRecoverBean = new RepayRecoverBean();
                String tenderOrderId = borrowRecover.getNid();// 出借订单号
                String recoverTime = GetDate.getDateTimeMyTimeInMillis(Integer.valueOf(borrowRecover.getRecoverTime()));
                String createTime = GetDate.getDateTimeMyTimeInMillis(borrowRecover.getCreateTime());
                int totalDays = GetDate.daysBetween(createTime, recoverTime);// 获取这两个时间之间有多少天
                // 计算出借用户实际获得的本息和
                userAccount = borrowRecover.getRecoverAccount();
                userCapital = borrowRecover.getRecoverCapital();
                userInterest = borrowRecover.getRecoverInterest();

                repayRecoverBean.setRecoverCapitalOld(userCapital);
                repayRecoverBean.setCreditAmountOld(borrowRecover.getCreditAmount());
                // 如果项目类型为融通宝，调用新的提前还款利息计算公司
                if (Integer.valueOf(borrow.getProjectType()) == 13 || CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                    // 提前还款不应该大于本次计息时间
                    if (totalDays < interestDay) {
                        // 用户提前还款减少的利息
                        userChargeInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(userCapital, new BigDecimal(borrow.getBorrowApr()), totalDays);
                    } else {
                        // 用户提前还款减少的利息
                        userChargeInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(userCapital, new BigDecimal(borrow.getBorrowApr()), interestDay);
                    }
                } else {

                    // 实际持有天数
                    int acctualDays = GetDate.daysBetween(createTime,factRepayTime);

                    // 用户提前还款减少的利息
                    BigDecimal acctualInterest = UnnormalRepayUtils.aheadEndRepayInterest(userCapital, new BigDecimal(borrow.getBorrowApr()), acctualDays);
                    if(acctualInterest.compareTo(userInterest) >=0) {
                        userChargeInterest = BigDecimal.ZERO;
                    }else {
                        userChargeInterest = userInterest.subtract(acctualInterest);
                    }
                    userChargePenaltyInterest = acctualInterest;
                }

//                if(i == borrowRecovers.size() - 1){
//                    userChargeInterest = totalChargeInterest;
//                }else{
//                    totalChargeInterest = totalChargeInterest.subtract(userChargeInterest);
//                }
                // 项目提前还款时，提前还款利息不得大于应还款利息，需求变更
                if (userChargeInterest.compareTo(userInterest) > 0) {
                    userChargeInterest = userInterest;
                }
                userManageFee = borrowRecover.getRecoverFee();// 获取应还款管理费
                if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                    if(Validator.isNull(borrowRecover.getAccedeOrderId())){
                        // 直投项目债转还款
                        // 债转还款数据
                        List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, tenderOrderId, 1, 0);
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                            BigDecimal assignChargePenaltyInterest = BigDecimal.ZERO;// 计算用户提前还款罚息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户还款管理费
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                CreditRepay creditRepay = creditRepayList.get(j);
                                RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                // 计算出借用户实际获得的本息和
                                assignAccount = creditRepay.getAssignAccount();
                                assignCapital = creditRepay.getAssignCapital();// 承接本金
                                assignInterest = creditRepay.getAssignInterest();
                                //最后一笔兜底
                                if (borrowRecover.getCreditStatus() == 2 && j == creditRepayList.size() - 1) {
                                    assignManageFee = userManageFee;
                                } else {
                                    // 按月计息，到期还本还息end
                                    if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 按天计息到期还本还息
                                    else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByDay(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }

                                }
                                // 如果项目类型为融通宝，调用新的提前还款利息计算公司
                                if (Integer.valueOf(borrow.getProjectType()) == 13 || CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                    // 提前还款不应该大于本次计息时间
                                    if (totalDays < interestDay) {
                                        // 用户提前还款减少的利息
                                        assignChargeInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(assignCapital, new BigDecimal(borrow.getBorrowApr()), totalDays);
                                    } else {
                                        // 用户提前还款减少的利息
                                        assignChargeInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(assignCapital, new BigDecimal(borrow.getBorrowApr()), interestDay);
                                    }
                                } else {
                                    // 实际持有天数
                                    int acctualDays = GetDate.daysBetween(createTime,factRepayTime);

                                    // 用户提前还款减少的利息
                                    BigDecimal acctualInterest = UnnormalRepayUtils.aheadEndRepayInterest(assignCapital, new BigDecimal(borrow.getBorrowApr()), acctualDays);
                                    if(acctualInterest.compareTo(assignInterest) >=0) {
                                        assignChargeInterest = BigDecimal.ZERO;
                                    }else {
                                        assignChargeInterest = assignInterest.subtract(acctualInterest);
                                    }
                                    assignChargePenaltyInterest = acctualInterest;
                                }

                                BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                creditRepayBean.setAssignTotal(assignAccount.subtract(assignChargeInterest).add(assignManageFee));
                                creditRepayBean.setManageFee(assignManageFee);
                                creditRepayBean.setAdvanceStatus(1);
                                creditRepayBean.setChargeInterest(assignChargeInterest.multiply(new BigDecimal(-1)));
                                creditRepayBean.setChargePenaltyInterest(assignChargePenaltyInterest);
                                creditRepayBean.setChargeDays(interestDay);
                                creditRepayBeanList.add(creditRepayBean);
                                // 统计出让人还款金额
                                userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                userCapital = userCapital.subtract(assignCapital);
                                userInterest = userInterest.subtract(assignInterest);
                                userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                userChargeInterest = userChargeInterest.subtract(assignChargeInterest);// 提前还款利息
                                userChargePenaltyInterest = userChargePenaltyInterest.subtract(assignChargePenaltyInterest);// 提前还款罚息
                                // 统计总额
                                repayTotal = repayTotal.add(assignAccount).subtract(assignChargeInterest).add(assignManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                repayCapital = repayCapital.add(assignCapital);
                                repayInterest = repayInterest.add(assignInterest);
                                repayManageFee = repayManageFee.add(assignManageFee);// 統計管理費
                                repayChargeInterest = repayChargeInterest.add(assignChargeInterest);// 统计提前还款减少的利息
                                repayChargePenaltyInterest = repayChargePenaltyInterest.add(assignChargePenaltyInterest);// 统计提前还款罚息
                            }
                            repayRecoverBean.setCreditRepayList(creditRepayBeanList);
                        }
                        if (borrowRecover.getCreditStatus() != 2) {
                            // 实际持有天数
                            int acctualDays = GetDate.daysBetween(createTime,factRepayTime);

                            if(CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                // 用户提前还款减少的利息
                                BigDecimal acctualInterest = UnnormalRepayUtils.aheadEndRepayInterest(userCapital, new BigDecimal(borrow.getBorrowApr()), acctualDays);
                                if (acctualInterest.compareTo(userInterest) >= 0) {
                                    userChargeInterest = BigDecimal.ZERO;
                                } else {
                                    userChargeInterest = userInterest.subtract(acctualInterest);
                                }
                                userChargePenaltyInterest = acctualInterest;
                            }
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                            repayChargeInterest = repayChargeInterest.add(userChargeInterest);// 统计提前还款减少的利息
                            repayChargePenaltyInterest = repayChargePenaltyInterest.add(userChargePenaltyInterest);// 统计提前还款罚息
                        }
                    }else{
                        // 计划类还款
                        boolean overFlag = false;
                        // 债转还款数据
                        List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, tenderOrderId, 1, borrowRecover.getRecoverStatus());
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                            BigDecimal assignChargePenaltyInterest = BigDecimal.ZERO;// 计算用户提前还款罚息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户还款管理费
                            //判断当前期是否全部承接
                            overFlag = isOverUndertake(borrowRecover,null,null,false,0);
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                HjhDebtCreditRepay creditRepay = creditRepayList.get(j);
                                HjhDebtCreditRepayBean creditRepayBean = new HjhDebtCreditRepayBean();
                                // 计算出借用户实际获得的本息和
                                assignAccount = creditRepay.getRepayAccount();
                                assignCapital = creditRepay.getRepayCapital();// 承接本金
                                assignInterest = creditRepay.getRepayInterest();
                                // 按月计息，到期还本还息end
                                if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                    assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                }
                                // 按天计息到期还本还息
                                else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                    assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByDay(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                }
                                // 如果项目类型为融通宝，调用新的提前还款利息计算公司
                                if (Integer.valueOf(borrow.getProjectType()) == 13 || CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                    // 提前还款不应该大于本次计息时间
                                    if (totalDays < interestDay) {
                                        // 用户提前还款减少的利息
                                        assignChargeInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(assignCapital, new BigDecimal(borrow.getBorrowApr()), totalDays);
                                    } else {
                                        // 用户提前还款减少的利息
                                        assignChargeInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(assignCapital, new BigDecimal(borrow.getBorrowApr()), interestDay);
                                    }
                                } else {
                                    // 实际持有天数
                                    int acctualDays = GetDate.daysBetween(createTime,factRepayTime);

                                    // 用户提前还款减少的利息
                                    BigDecimal acctualInterest = UnnormalRepayUtils.aheadEndRepayInterest(assignCapital, new BigDecimal(borrow.getBorrowApr()), acctualDays);
                                    if(acctualInterest.compareTo(assignInterest) >=0) {
                                        assignChargeInterest = BigDecimal.ZERO;
                                    }else {
                                        assignChargeInterest = assignInterest.subtract(acctualInterest);
                                    }
                                    assignChargePenaltyInterest = acctualInterest;
                                }

                                BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                creditRepayBean.setAssignTotal(assignAccount.subtract(assignChargeInterest).add(assignManageFee));
                                creditRepayBean.setManageFee(assignManageFee);
                                creditRepayBean.setAdvanceStatus(1);
                                creditRepayBean.setRepayAdvanceInterest(assignChargeInterest.multiply(new BigDecimal(-1)));
                                creditRepayBean.setRepayAdvancePenaltyInterest(assignChargePenaltyInterest);
                                creditRepayBean.setAdvanceDays(interestDay);
                                creditRepayBeanList.add(creditRepayBean);
                                // 统计出让人还款金额
                                userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                userCapital = userCapital.subtract(assignCapital);
                                userInterest = userInterest.subtract(assignInterest);
                                userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                userChargeInterest = userChargeInterest.subtract(assignChargeInterest);// 提前还款利息
                                userChargePenaltyInterest = userChargePenaltyInterest.subtract(assignChargePenaltyInterest);// 提前还款罚息
                                // 统计总额
                                repayTotal = repayTotal.add(assignAccount).subtract(assignChargeInterest).add(assignManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                repayCapital = repayCapital.add(assignCapital);
                                repayInterest = repayInterest.add(assignInterest);
                                repayManageFee = repayManageFee.add(assignManageFee);// 統計管理費
                                repayChargeInterest = repayChargeInterest.add(assignChargeInterest);// 统计提前还款减少的利息
                                repayChargePenaltyInterest = repayChargePenaltyInterest.add(assignChargePenaltyInterest);// 统计提前还款罚息
                            }
                            repayRecoverBean.setHjhCreditRepayList(creditRepayBeanList);
                        }
                        if (overFlag) {
                            // 重新计算债转后出让人剩余金额的提前减息金额
                            // 实际持有天数
                            int acctualDays = GetDate.daysBetween(createTime,factRepayTime);
                            if(CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                // 用户提前还款减少的利息
                                BigDecimal acctualInterest = UnnormalRepayUtils.aheadEndRepayInterest(userCapital, new BigDecimal(borrow.getBorrowApr()), acctualDays);
                                if (acctualInterest.compareTo(userInterest) >= 0) {
                                    userChargeInterest = BigDecimal.ZERO;
                                } else {
                                    userChargeInterest = userInterest.subtract(acctualInterest);
                                }
                            }
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                            repayChargeInterest = repayChargeInterest.add(userChargeInterest);// 统计提前还款减少的利息
                            repayChargePenaltyInterest = repayChargePenaltyInterest.add(userChargePenaltyInterest);// 统计提前还款罚息
                        }
                    }
                } else {
                    // 统计总额
                    repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userManageFee);// 统计总和本息+管理费
                    repayAccount = repayAccount.add(userAccount); // 统计本息总和
                    repayCapital = repayCapital.add(userCapital);
                    repayInterest = repayInterest.add(userInterest);
                    repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                    repayChargeInterest = repayChargeInterest.add(userChargeInterest);// 统计提前还款减少的利息
                    repayChargePenaltyInterest = repayChargePenaltyInterest.add(userChargePenaltyInterest);// 统计提前还款罚息
                }
                BeanUtils.copyProperties(borrowRecover, repayRecoverBean);
                repayRecoverBean.setRecoverTotal(userAccount.subtract(userChargeInterest).add(userManageFee));
                repayRecoverBean.setRecoverAccount(userAccount);
                repayRecoverBean.setRecoverCapital(userCapital);
                repayRecoverBean.setRecoverInterest(userInterest);
                repayRecoverBean.setRecoverFee(userManageFee);
                repayRecoverBean.setChargeInterest(userChargeInterest.multiply(new BigDecimal(-1)));
                repayRecoverBean.setChargePenaltyInterest(userChargePenaltyInterest);
                repayRecoverBean.setAdvanceStatus(1);
                repayRecoverBean.setChargeDays(interestDay);
                repayRecoverList.add(repayRecoverBean);
            }
            repay.setRecoverList(repayRecoverList);
        }
        repay.setRepayAccountAll(repayTotal);
        repay.setRepayAccount(repayAccount);
        repay.setRepayCapital(repayCapital);
        repay.setRepayInterest(repayInterest);
        repay.setRepayFee(repayManageFee);
        repay.setChargeDays(interestDay);
        repay.setChargeInterest(repayChargeInterest.multiply(new BigDecimal(-1)));
        repay.setChargePenaltyInterest(repayChargePenaltyInterest);
        repay.setAdvanceStatus(1);

    }


    @Override
    public List<BorrowListVO> getBorrowList(Map<String, Object> param) {
        Map<String,String> map = new HashMap<>();
        map.put("borrowNidSrch",String.valueOf(param.get("borrowNidSrch")));
       return  borrowCustomizeMapper.getBorrowList(param);
    }

    @Override
    public List<DebtBorrowCustomizeVO> getDebtBorrowList(Map<String, Object> param) {
        return borrowCustomizeMapper.getDebtBorrowList(param);
    }

    @Override
    public List<WebProjectRepayListCustomizeVO> selectProjectLoanDetailList(Map<String, Object> param) {
        return borrowCustomizeMapper.selectProjectLoanDetailList(param);
    }

    @Override
    public List<WebUserInvestListCustomizeVO> selectUserDebtInvestList(Map<String, Object> param) {
        return borrowCustomizeMapper.selectUserDebtInvestList(param);
    }

    @Override
    public int planInfoCountProjectRepayPlanRecordTotal(Map<String, Object> param) {
        return borrowCustomizeMapper.planInfoCountProjectRepayPlanRecordTotal(param);
    }

    @Override
    public int myTenderCountProjectRepayPlanRecordTotal(Map<String, Object> param) {
        return borrowCustomizeMapper.myTenderCountProjectRepayPlanRecordTotal(param);
    }

    @Override
    public List<WebUserInvestListCustomizeVO> selectUserInvestList(Map<String, Object> param) {
        return borrowCustomizeMapper.selectUserInvestList(param);
    }

    @Override
    public List<BorrowCustomizeVO> searchBorrowList(BorrowCommonCustomizeVO borrowCommonCustomizeVO) {
        return  borrowCustomizeMapper.searchBorrowList(borrowCommonCustomizeVO);
    }

    @Override
    public int countProjectRepayPlanRecordTotal(String borrowNid, String userId, String nid) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("borrowNid", borrowNid);
        params.put("nid", nid);
        int total = webUserInvestListCustomizeMapper.countProjectRepayPlanRecordTotal(params);
        return total;
    }

    @Override
    public List<WebProjectRepayListCustomize> selectProjectRepayPlanList(String borrowNid, String userId, String nid) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("borrowNid", borrowNid);
        params.put("nid", nid);
        List<WebProjectRepayListCustomize> projectRepayList =
                webUserInvestListCustomizeMapper.selectProjectRepayPlanList(params);
        return projectRepayList;
    }

    /**
     * 查询用户账户信息金额信息
     *
     * @param userId
     * @return
     */
    @Override
    public BankAccountManageCustomize queryAccountUserMoney(Integer userId) {
        BankAccountManageCustomize accountInfos = accountCustomizeMapper.queryAccountUserMoney(userId);
        return accountInfos;
    }
}

