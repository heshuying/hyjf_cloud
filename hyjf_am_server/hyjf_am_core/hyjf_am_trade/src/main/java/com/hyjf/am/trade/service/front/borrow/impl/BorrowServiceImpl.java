/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.borrow.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.resquest.user.BorrowFinmanNewChargeRequest;
import com.hyjf.am.trade.dao.mapper.customize.AccountCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.WebCalculateInvestInterestCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.producer.SmsProducer;
import com.hyjf.am.trade.service.front.account.AccountService;
import com.hyjf.am.trade.service.front.borrow.BorrowService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.BorrowCustomizeVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.trade.ProjectCompanyDetailVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.WebProjectPersonDetailVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.borrow.TenderBgVO;
import com.hyjf.am.vo.trade.borrow.TenderRetMsg;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author fuqiang
 * @version BorrowServiceImpl, v0.1 2018/6/13 18:53
 */
@Service
public class BorrowServiceImpl extends BaseServiceImpl implements BorrowService {


    @Autowired
    private SmsProducer smsProducer;
    @Autowired
    private AccountCustomizeMapper accountCustomizeMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private WebCalculateInvestInterestCustomizeMapper webCalculateInvestInterestCustomizeMapper;


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
        BorrowVO borrowVO = request.getBorrowVO();
        int status = request.getStatus();
        int registStatus = request.getRegistStatus();
        Date nowDate = new Date();
//		AdminSystem adminSystem = (AdminSystem) SessionUtils.getSession(CustomConstants.LOGIN_USER_INFO);
        BorrowExample example = new BorrowExample();
        example.createCriteria().andIdEqualTo(borrowVO.getId()).andStatusEqualTo(borrowVO.getStatus()).andRegistStatusEqualTo(borrowVO.getRegistStatus());
        borrowVO.setRegistStatus(registStatus);
        borrowVO.setStatus(status);
        borrowVO.setRegistUserId(1);//TODO:id写死1
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
    public List<Borrow> selectBorrowList() {
        BorrowExample example = new BorrowExample();
        BorrowExample.Criteria cra = example.createCriteria();
        cra.andStatusEqualTo(3);
        cra.andRepayFullStatusEqualTo(0);
        List<Borrow> list = this.borrowMapper.selectByExample(example);
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
     * 投资之前插入tmp表
     *
     * @param tenderRequest
     * @return
     */
    @Override
    public int insertBeforeTender(TenderRequest tenderRequest) {
        Integer userId = tenderRequest.getUserId();

        Borrow borrow = getBorrow(tenderRequest.getBorrowNid());
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
        // 为投资完全掉单优惠券投资时修复做记录
        temp.setCouponGrantId(couponGrantId);
        logger.info("开始插入temp表");
        boolean tenderTmpFlag = borrowTenderTmpMapper.insertSelective(temp) > 0 ? true : false;
        if (!tenderTmpFlag) {
            logger.error("插入borrowTenderTmp表失败，投资订单号：" + tenderRequest.getOrderId());
            throw new RuntimeException("插入borrowTenderTmp表失败，投资订单号：" + tenderRequest.getOrderId());
        }
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
            logger.error("插入borrowTenderTmpInfo表失败，投资订单号：" + tenderRequest.getOrderId());
            throw new RuntimeException("插入borrowTenderTmpInfo表失败，投资订单号：" + tenderRequest.getOrderId());
        }
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
     * 投资异步修改表
     *
     * @param tenderBg
     */
    @Override
    public void updateTenderAfter(TenderBgVO tenderBg) {
        Integer userId = tenderBg.getUserId();
        // 删除临时表
        BorrowTenderTmpExample borrowTenderTmpExample = new BorrowTenderTmpExample();
        BorrowTenderTmpExample.Criteria criteria1 = borrowTenderTmpExample.createCriteria();
        criteria1.andNidEqualTo(tenderBg.getOrderId());
        criteria1.andUserIdEqualTo(tenderBg.getUserId());
        criteria1.andBorrowNidEqualTo(tenderBg.getBorrowNid());
        boolean tenderTempFlag = borrowTenderTmpMapper.deleteByExample(borrowTenderTmpExample) > 0 ? true : false;
        if (!tenderTempFlag) {
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
        borrowTender.setInviteUserName(tenderBg.getInviteUserName());
        borrowTender.setInviteUserAttribute(tenderBg.getAttribute());
        borrowTender.setInvestType(0);
        // 单笔投资的融资服务费
        borrowTender.setLoanFee(tenderBg.getPerService());
        //投资授权码
        borrowTender.setAuthCode(tenderBg.getAuthCode());
        borrowTender.setRemark("现金投资");
        borrowTenderMapper.insertSelective(borrowTender);

        // 更新用户账户余额表
        Account accountBean = new Account();
        accountBean.setUserId(userId);
        // 投资人冻结金额增加
        accountBean.setBankFrost(tenderBg.getAccountDecimal());
        // 投资人可用余额扣减
        accountBean.setBankBalance(tenderBg.getAccountDecimal());
        // 江西银行账户余额
        // 此账户余额投资后应该扣减掉相应投资金额,sql已改
        accountBean.setBankBalanceCash(tenderBg.getAccountDecimal());
        // 江西银行账户冻结金额
        accountBean.setBankFrostCash(tenderBg.getAccountDecimal());
        Boolean accountFlag = this.accountCustomizeMapper.updateOfTender(accountBean) > 0 ? true : false;
        if (!accountFlag) {
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
        accountList.setTrade("tender");// 投资
        accountList.setTradeCode("frost");// 投标冻结后为frost
        accountList.setType(3);// 收支类型1收入2支出3冻结
        accountList.setUserId(userId);
        accountList.setWeb(0);
        accountList.setIsBank(1);// 是否是银行的交易记录(0:否,1:是)
        boolean accountListFlag = accountListMapper.insertSelective(accountList) > 0 ? true : false;
        if (!accountListFlag) {
            throw new RuntimeException("用户账户交易明细表更新失败");
        }

        // 更新borrow表
        Map<String, Object> borrowParam = new HashMap<String, Object>();
        borrowParam.put("borrowAccountYes", tenderBg.getAccountDecimal());
        borrowParam.put("borrowService", tenderBg.getPerService());
        borrowParam.put("borrowId", tenderBg.getBorrowNid());
        boolean updateBorrowAccountFlag = borrowCustomizeMapper.updateOfBorrow(borrowParam) > 0 ? true : false;
        // 更新borrow表
        if (!updateBorrowAccountFlag) {
            throw new RuntimeException("borrow表更新失败");
        }

        // 投资、收益统计表  改为组合层调用
       /* List<CalculateInvestInterest> calculates = this.calculateInvestInterestMapper.selectByExample(new CalculateInvestInterestExample());
        if (calculates != null && calculates.size() > 0) {
            CalculateInvestInterest calculateNew = new CalculateInvestInterest();
            calculateNew.setTenderSum(tenderBg.getAccountDecimal());
            calculateNew.setId(calculates.get(0).getId());
            calculateNew.setCreateTime(GetDate.getDate(GetDate.getNowTime10()));
            this.webCalculateInvestInterestCustomizeMapper.updateCalculateInvestByPrimaryKey(calculateNew);
        }*/

        // 计算此时的剩余可投资金额
        BigDecimal accountWait = this.getBorrow(tenderBg.getBorrowNid()).getBorrowAccountWait();
        String borrowNid = tenderBg.getBorrowNid();
        // 满标处理
        if (accountWait.compareTo(new BigDecimal(0)) == 0) {
            System.out.println("用户:" + userId + "***********************************项目满标，订单号：" + tenderBg.getOrderId());
            Map<String, Object> borrowFull = new HashMap<String, Object>();
            borrowFull.put("borrowId", borrowNid);
            boolean fullFlag = borrowCustomizeMapper.updateOfFullBorrow(borrowFull) > 0 ? true : false;
            if (!fullFlag) {
                throw new RuntimeException("满标更新borrow表失败");
            }
            // 清除标总额的缓存
            RedisUtils.del(borrowNid);
            // 纯发短信接口
            Map<String, String> replaceMap = new HashMap<String, String>();
            replaceMap.put("val_title", borrowNid);
            replaceMap.put("val_date", DateUtils.getNowDate());
            BorrowSendTypeExample sendTypeExample = new BorrowSendTypeExample();
            BorrowSendTypeExample.Criteria sendTypeCriteria = sendTypeExample.createCriteria();
            sendTypeCriteria.andSendCdEqualTo("AUTO_FULL");
            List<BorrowSendType> sendTypeList = borrowSendTypeMapper.selectByExample(sendTypeExample);
            if (sendTypeList == null || sendTypeList.size() == 0) {
                throw new RuntimeException("用户:" + userId + "***********************************冻结成功后处理afterChinaPnR：" + "数据库查不到 sendTypeList == null");
            }
            BorrowSendType sendType = sendTypeList.get(0);
            if (sendType.getAfterTime() == null) {
                throw new RuntimeException("用户:" + userId + "***********************************冻结成功后处理afterChinaPnR：" + "sendType.getAfterTime()==null");
            }
            replaceMap.put("val_times", sendType.getAfterTime() + "");
            // 发送短信验证码
            SmsMessage smsMessage = new SmsMessage(null, replaceMap, null, null, MessageConstant.SMS_SEND_FOR_MANAGER, null, CustomConstants.PARAM_TPL_XMMB, CustomConstants.CHANNEL_TYPE_NORMAL);
            try{
                smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC,
                        UUID.randomUUID().toString(), JSON.toJSONBytes(smsMessage)));
            }catch (Exception e){

            }
        } else if (accountWait.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("用户:" + userId + "项目编号:" + borrowNid + "***********************************项目暴标");
        }


    }

    /**
     * 散标投资异步返回结果
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
     * 获取散标投资异步返回结果
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
        logger.info("散标投资获取失败结果：result：{} list.size():{}", result, (list == null ? 0 : list.size()));
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
     * 查询计划还款日前一天，处于投资中和复审中的原始标的，发送邮件预警
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
}
