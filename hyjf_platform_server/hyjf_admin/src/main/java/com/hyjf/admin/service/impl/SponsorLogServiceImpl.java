package com.hyjf.admin.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.SponsorLogService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.SponsorLogResponse;
import com.hyjf.am.resquest.trade.SponsorLogRequest;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;

@Service
public class SponsorLogServiceImpl implements SponsorLogService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${hyjf.bank.instcode}")
    private String BANK_INSTCODE;

    @Value("${hyjf.bank.bankcode}")
    private String BANK_BANKCODE;
    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmUserClient amUserClient;
 
	@Override
	public SponsorLogResponse sponsorLogList(SponsorLogRequest sponsorLogRequest) {
		return amTradeClient.sponsorLogList(sponsorLogRequest);
	}

	@Override
	public SponsorLogResponse deleteSponsorLog(SponsorLogRequest sponsorLogRequest) {
		return amTradeClient.deleteSponsorLog(sponsorLogRequest);
	}

	@Override
	public SponsorLogResponse insertSponsorLog(SponsorLogRequest sponsorLogRequest) {
		return amTradeClient.insertSponsorLog(sponsorLogRequest);
	}

	@Override
	public SponsorLogResponse selectSponsorLog(SponsorLogRequest sponsorLogRequest) {
		if(this.handleBorrowRegistException(sponsorLogRequest)) {
			sponsorLogRequest.setStatus(1);
			SponsorLogResponse slr = amTradeClient.updateSponsorLog(sponsorLogRequest);
			slr.setMessage("修改成功");
			return slr;
		}else {
			SponsorLogResponse slr = new SponsorLogResponse();
			slr.setRtn(Response.ERROR);
			slr.setMessage("担保账户未修改");
			return  slr;
		}
		
	}

	   /**
     * 备案异常处理
     * @param borrowNid 项目编号
     * @param loginUserId 当前登录用户id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean handleBorrowRegistException(SponsorLogRequest sponsorLogRequest) {
        BorrowAndInfoVO borrowVO = amTradeClient.searchBorrowByBorrowNid(sponsorLogRequest.getBorrowNid());
        if(null != borrowVO){
            Integer borrowUserId = borrowVO.getUserId();
            logger.info("handleBorrowRegistException::::::::borrowUserId=======[{}]",borrowUserId);
            BankOpenAccountVO bankOpenAccountVO = amUserClient.searchBankOpenAccount(borrowUserId);
            if(null != bankOpenAccountVO){
                logger.info("查询出来借款人银行账户::::::::[{}]",bankOpenAccountVO.getAccount());
                // 借款人银行账户
                String accountId = bankOpenAccountVO.getAccount();
                // 查询相应的标的备案状态
                logger.info("标的备案异常，borrowNid:[{}],accountId:[{}],loginUserId:[{}]",sponsorLogRequest.getBorrowNid(),accountId,sponsorLogRequest.getAdminUserId());
                BankCallBean searchResult = this.borrowRegistSearch(sponsorLogRequest.getBorrowNid(), accountId, sponsorLogRequest.getAdminUserId());
                logger.info("银行返回报文:【{}】",searchResult);
                if (Validator.isNotNull(searchResult)) {
                    String searchRetCode = StringUtils.isNotBlank(searchResult.getRetCode()) ? searchResult.getRetCode() : "";
                    logger.info("银行返回报文searchRetCode:[{}]",searchRetCode);
                    // 如果返回成功
                    if (BankCallConstant.RESPCODE_SUCCESS.equals(searchRetCode)) {
                        String subPacks = searchResult.getSubPacks();
                        logger.info("subPacks:[{}]",subPacks);
                        if (StringUtils.isNotBlank(subPacks)) {
                            logger.debug("subPacks不为空");
                            JSONArray debtDetails = JSONObject.parseArray(subPacks);
                            if (debtDetails != null) {
                                logger.debug("debtDetails不为空");
                                if (debtDetails.size() == 1) {
                                    logger.debug("debtDetails.size == 1");
                                    JSONObject debtDetail = debtDetails.getJSONObject(0);
                                    String bailAccountId = debtDetail.getString(BankCallConstant.PARAM_BAILACCOUNTID);
                                    UserVO user = amUserClient.getUserByUserName(sponsorLogRequest.getUpdateUserName());
                                    BankOpenAccountVO account = amUserClient.getBankOpenAccountByUserId(user.getUserId());
                                    if(bailAccountId.equals(account.getAccount())) {
                                    	//更新成功
                                    	return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    /**
     * 查询相应的标的备案状态
     * @param borrowNid 标的id
     * @param accountId 银行账户
     * @param loginUserId 登录用户id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public BankCallBean borrowRegistSearch(String borrowNid, String accountId, int loginUserId) {
        // 获取共同参数
        String channel = BankCallConstant.CHANNEL_PC;
        String orderId = GetOrderIdUtils.getOrderId2(loginUserId);
        String orderDate = GetOrderIdUtils.getOrderDate();
        String txDate = GetOrderIdUtils.getTxDate();
        String txTime = GetOrderIdUtils.getTxTime();
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        // 调用开户接口
        BankCallBean debtRegistBean = new BankCallBean();
        debtRegistBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        debtRegistBean.setTxCode(BankCallConstant.TXCODE_DEBT_DETAILS_QUERY);// 消息类型(借款人标的信息查询)
        debtRegistBean.setInstCode(this.BANK_INSTCODE);// 机构代码
        debtRegistBean.setBankCode(this.BANK_BANKCODE);
        debtRegistBean.setTxDate(txDate);
        debtRegistBean.setTxTime(txTime);
        debtRegistBean.setSeqNo(seqNo);
        debtRegistBean.setChannel(channel);
        debtRegistBean.setAccountId(accountId);// 借款人电子账号
        debtRegistBean.setProductId(borrowNid);// 标的表id
        debtRegistBean.setPageNum("1");
        debtRegistBean.setPageSize("10");
        debtRegistBean.setLogOrderId(orderId);
        debtRegistBean.setLogOrderDate(orderDate);
        debtRegistBean.setLogUserId(String.valueOf(loginUserId));
        debtRegistBean.setLogRemark("借款人标的登记");
        debtRegistBean.setLogClient(0);
        try {
            BankCallBean registResult = BankCallUtils.callApiBg(debtRegistBean);
            return registResult;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
