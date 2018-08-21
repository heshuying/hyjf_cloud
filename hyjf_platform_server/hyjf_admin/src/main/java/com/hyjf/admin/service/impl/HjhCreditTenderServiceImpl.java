/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.mq.FddProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.HjhCreditTenderService;
import com.hyjf.am.response.admin.HjhCreditTenderResponse;
import com.hyjf.am.resquest.admin.HjhCreditTenderRequest;
import com.hyjf.am.vo.fdd.FddDessenesitizationBeanVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.hjh.HjhCreditTenderCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhCreditTenderSumVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.common.constants.FddGenerateContractConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;

/**
 * @author libin
 * @version HjhCreditTenderServiceImpl.java, v0.1 2018年7月11日 下午3:02:16
 */
@Service
public class HjhCreditTenderServiceImpl implements HjhCreditTenderService{
	
    @Autowired
    private AmTradeClient amTradeClient;
    
	@Autowired
	private FddProducer fddProducer;
	private static final Logger _log = LoggerFactory.getLogger(HjhCreditTenderServiceImpl.class);
	/**
	 * 获取详细列表
	 * 
	 * @param DebtCreditCustomize
	 * @return
	 */
	@Override
	public HjhCreditTenderResponse getHjhCreditTenderListByParam(HjhCreditTenderRequest form) {
		HjhCreditTenderResponse response = amTradeClient.getHjhCreditTenderListByParam(form);
		return response;
	}

	@Override
	public List<HjhCreditTenderCustomizeVO> getHjhCreditTenderListByParamWithOutPage(HjhCreditTenderRequest form) {
		List<HjhCreditTenderCustomizeVO> list = amTradeClient.getHjhCreditTenderListByParamWithOutPage(form);
		return list;
	}

	@Override
	public HjhDebtCreditTenderVO selectHjhCreditTenderRecord(HjhCreditTenderRequest form) {
		HjhDebtCreditTenderVO vo = amTradeClient.selectHjhCreditTenderRecord(form);
		return vo;
	}

	@Override
	public void updateSaveSignInfo(TenderAgreementVO tenderAgreement, String borrowNid, Integer transType,
			String instCode) {
		String download_url = tenderAgreement.getDownloadUrl();
		String savePath = null;
		String path = "/pdf_tem/";
		String ftpPath = null;
		if (FddGenerateContractConstant.PROTOCOL_TYPE_TENDER == transType || FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT == transType
				|| FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET == transType) {
			savePath = path + "pdf/" + borrowNid;
			ftpPath = "PDF/" + instCode + "/" + borrowNid + "/" + tenderAgreement.getTenderNid() + "/";
		} else if (FddGenerateContractConstant.PROTOCOL_TYPE_PLAN == transType) {
			savePath = path + "pdf/" + instCode;
			ftpPath = "PDF/" + instCode + "/" + tenderAgreement.getTenderNid() + "/";
		}
		//下载协议并脱敏
		FddDessenesitizationBeanVO bean = new FddDessenesitizationBeanVO();
		bean.setAgrementID(tenderAgreement.getId().toString());
		bean.setSavePath(savePath);
		bean.setTransType(transType.toString());
		bean.setFtpPath(ftpPath);
		bean.setDownloadUrl(download_url);
		bean.setOrdid(tenderAgreement.getTenderNid());
		/*rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_DOWNDESSENESITIZATION_CONTRACT, JSONObject.toJSONString(bean));*/
        try {
			fddProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC,MQConstant.FDD_DOWNPDF_AND_DESSENSITIZATION_TAG,JSON.toJSONBytes(bean)));
		} catch (MQException e) {
			e.printStackTrace();
			_log.error("法大大发送下载脱敏消息失败...", e);	
		}
	}

	/**
	 * 传参查询承接债转表列总计
	 * 
	 * @param DebtCreditCustomize
	 * @return
	 */
	@Override
	public HjhCreditTenderSumVO getCalcSumByParam(HjhCreditTenderRequest form) {
		HjhCreditTenderSumVO vo = amTradeClient.getHjhCreditTenderCalcSumByParam(form);
		return vo;
	}

}
