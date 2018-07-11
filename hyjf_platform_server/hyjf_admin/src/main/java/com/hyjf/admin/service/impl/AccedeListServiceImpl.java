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
import com.hyjf.admin.client.AccedeListClient;
import com.hyjf.admin.controller.productcenter.plancenter.AccedeListController;
import com.hyjf.admin.mq.FddProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.AccedeListService;
import com.hyjf.am.response.admin.AccedeListResponse;
import com.hyjf.am.resquest.admin.AccedeListRequest;
import com.hyjf.am.vo.fdd.FddDessenesitizationBeanVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.hjh.AccedeListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeSumVO;
import com.hyjf.am.vo.trade.hjh.UserHjhInvistDetailVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.FddGenerateContractConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
/**
 * @author libin
 * @version AccedeListServiceImpl.java, v0.1 2018年7月7日 下午3:06:15
 */
@Service
public class AccedeListServiceImpl implements AccedeListService{
	
    @Autowired
    private AccedeListClient accedeListClient;
    
	@Autowired
	private FddProducer fddProducer;
	
	private static final Logger _log = LoggerFactory.getLogger(AccedeListController.class);

	@Override
	public AccedeListResponse getAccedeListByParam(AccedeListRequest form) {
		AccedeListResponse response = accedeListClient.getAccedeListByParam(form);
		return response;
	}

	@Override
	public List<AccedeListCustomizeVO> getAccedeListByParamWithoutPage(AccedeListRequest form) {
		List<AccedeListCustomizeVO> list = accedeListClient.getAccedeListByParamWithoutPage(form);
		return list;
	}

	@Override
	public HjhAccedeSumVO getCalcSumByParam(AccedeListRequest form) {
		HjhAccedeSumVO vo = accedeListClient.getCalcSumByParam(form);
		return vo;
	}

	@Override
	public String resendMessageAction(String userid, String planOrderId, String debtPlanNid, String sendEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserVO getUserByUserId(int userId) {
		UserVO vo = accedeListClient.getUserByUserId(userId);
		return vo;
	}

	@Override
	public List<TenderAgreementVO> selectTenderAgreementByNid(String planOrderId) {
		List<TenderAgreementVO> tenderAgreementList = accedeListClient.selectTenderAgreementByNid(planOrderId);
		return tenderAgreementList;
	}

	@Override
	public UserInfoVO getUsersInfoByUserId(int userid) {
		UserInfoVO vo = accedeListClient.selectUsersInfoByUserId(userid);
		return vo;
	}

	@Override
	public int updateSendStatusByParam(AccedeListRequest request) {
		int flg = accedeListClient.updateSendStatusByParam(request);
		return flg;
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

	@Override
	public UserHjhInvistDetailVO selectUserHjhInvistDetail(AccedeListRequest request) {
		UserHjhInvistDetailVO vo = accedeListClient.selectUserHjhInvistDetail(request);
		return vo;
	}
}
