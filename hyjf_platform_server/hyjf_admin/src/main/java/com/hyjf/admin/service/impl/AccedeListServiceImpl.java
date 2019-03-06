/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.controller.productcenter.plancenter.AccedeListController;
import com.hyjf.admin.mq.base.CommonProducer;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author libin
 * @version AccedeListServiceImpl.java, v0.1 2018年7月7日 下午3:06:15
 */
@Service
public class AccedeListServiceImpl implements AccedeListService{
	
    @Autowired
    private AmTradeClient amTradeClient;
    
    @Autowired
    private AmUserClient amUserClient;
    
	@Autowired
	private CommonProducer commonProducer;
	
	private static final Logger _log = LoggerFactory.getLogger(AccedeListController.class);

	/**
	 * 获取加入计划列表的总数
	 * @return
	 */
	@Override
	public int getAccedeListByParamCount(AccedeListRequest form) {
		AccedeListResponse response = amTradeClient.getAccedeListByParamCount(form);
		return response.getCount();
	}

	/**
	 * 分页获取加入计划列表
	 * @return
	 */
	@Override
	public List<AccedeListCustomizeVO> getAccedeListByParamList(AccedeListRequest form) {
		AccedeListResponse response = amTradeClient.getAccedeListByParamList(form);
		return response.getResultList();
	}

	/**
     * 获取加入计划列表
     * @return
     */
	@Override
	public AccedeListResponse getAccedeListByParam(AccedeListRequest form) {
		AccedeListResponse response = amTradeClient.getAccedeListByParam(form);
		return response;
	}
	
	/**
     * 获取加入计划列表不分页
     * @return
     */
	@Override
	public List<AccedeListCustomizeVO> getAccedeListByParamWithoutPage(AccedeListRequest form) {
		List<AccedeListCustomizeVO> list = amTradeClient.getAccedeListByParamWithoutPage(form);
		return list;
	}
	
	/**
     * 获取加入计划列表列总计
     * @return
     */
	@Override
	public HjhAccedeSumVO getCalcSumByParam(AccedeListRequest form) {
		HjhAccedeSumVO vo = amTradeClient.getCalcSumByParam(form);
		return vo;
	}
	
	/**
	 * EMAIL入力后发送协议
	 * @author pcc
	 * @param userid
	 * @param planOrderId
	 * @param debtPlanNid
	 */
	@Override
	public String resendMessageAction(String userid, String planOrderId, String debtPlanNid, String sendEmail) {
		return null;
	}

	/**
	 * 通过用户id获取用户信息   抽成共通
	 * @author pcc
	 * @param userid
	 * @param planOrderId
	 * @param debtPlanNid
	 */
	@Override
	public UserVO getUserByUserId(int userId) {
		UserVO vo = amUserClient.getUserByUserId(userId);
		return vo;
	}

	/**
	 * 通过计划订单号查询法大大协议列表
	 * @param userid
	 * @param planOrderId
	 * @param debtPlanNid
	 */
	@Override
	public List<TenderAgreementVO> selectTenderAgreementByNid(String planOrderId) {
		List<TenderAgreementVO> tenderAgreementList = amTradeClient.selectTenderAgreementByNid(planOrderId);
		return tenderAgreementList;
	}

	/**
	 * 通过userid获取用户详情
	 * @author pcc
	 * @param userid
	 * @param planOrderId
	 * @param debtPlanNid
	 */
	@Override
	public UserInfoVO getUsersInfoByUserId(int userid) {
		UserInfoVO vo = amUserClient.selectUsersInfoByUserId(userid);
		return vo;
	}

	/**
	 * 更新协议发送状态
	 * @author 
	 * @param userid
	 * @param planOrderId
	 * @param debtPlanNid
	 */
	@Override
	public int updateSendStatusByParam(AccedeListRequest request) {
		int flg = amTradeClient.updateSendStatusByParam(request);
		return flg;
	}

	/**
	 * PDF下载加脱敏
	 * @param tenderAgreement
	 * @param borrowNid
	 * @param transType
	 * @param instCode
	 */
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
        	_log.info("-----------开始下载脱敏：" + bean.getOrdid() + ",开始推送脱敏队列");
			commonProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC,MQConstant.FDD_DOWNPDF_AND_DESSENSITIZATION_TAG, UUID.randomUUID().toString(),bean));
			_log.info("-----------开始下载脱敏：" + bean.getOrdid() + ",推送脱敏队列完成1");

		} catch (MQException e) {
			_log.error("法大大发送下载脱敏消息失败...", e);
		}
	}

	/**
	 * 查询用户出借详情
	 * @param request
	 */
	@Override
	public UserHjhInvistDetailVO selectUserHjhInvistDetail(AccedeListRequest request) {
		UserHjhInvistDetailVO vo = amTradeClient.selectUserHjhInvistDetail(request);
		return vo;
	}

	@Override
	public List<AccedeListCustomizeVO> paging(AccedeListRequest request, List<AccedeListCustomizeVO> result){
		int current=request.getCurrPage(); //页码
		int pageSize=request.getPageSize(); //每页显示的数量
		int totalCount=result.size();
		int pageCount = (totalCount / pageSize) + ((totalCount % pageSize > 0) ? 1 : 0);

		if(current < 1){
			current = 1;
		}
		int start=(current-1) * pageSize;
		int end = Math.min(totalCount, current * pageSize);

		if(pageCount >= current){
			result=result.subList(start,end);
		}else{
			result = new ArrayList<>();
		}

		return result;
	}
}
