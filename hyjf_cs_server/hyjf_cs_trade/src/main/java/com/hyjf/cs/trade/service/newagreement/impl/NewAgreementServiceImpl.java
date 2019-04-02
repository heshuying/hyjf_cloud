/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.newagreement.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.ProtocolEnum;
import com.hyjf.cs.trade.bean.newagreement.NewAgreementResultBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.newagreement.NewAgreementService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * APP端协议ServiceImpl
 * @author libin
 * @version NewAgreementServiceImpl.java, v0.1 2018年7月25日 下午2:23:06
 */
@Service
public class NewAgreementServiceImpl extends BaseTradeServiceImpl implements NewAgreementService{

    @Autowired
    private AmTradeClient amTradeClient;
    

    
    @Autowired
    private AmUserClient couponUserClient;	
	/**
	 * 获取债转承接信息
	 * @param nid
	 * @return
	 */
	@Override
	public HjhDebtCreditTenderVO getHjhDebtCreditTenderByPrimaryKey(Integer nid) {
		HjhDebtCreditTenderVO vo = amTradeClient.getHjhDebtCreditTenderByPrimaryKey(nid);
		return vo;
	}
	
	/**
	 * 查询协议表by assignNid(实际上是tenderNID)
	 * @return
	 */
	@Override
	public List<TenderAgreementVO> getTenderAgreementByTenderNid(String accedeOrderId) {
		List<TenderAgreementVO> list = this.amTradeClient.selectTenderAgreementByTenderNid(accedeOrderId);
		return list;
	}

	/**
	 * 查询用户详情
	 * @param userId
	 * @return
	 */
	@Override
	public UserInfoVO getUsersInfoByUserId(Integer userId) {
		UserInfoVO userInfoVO = couponUserClient.getUserInfo(userId);
        if (userInfoVO != null) {
            return userInfoVO;
        }
		return null;
	}

	/**
	 * 根据borrowNid获取标的
	 * @param borrowNid
	 * @return
	 */
	@Override
	public BorrowAndInfoVO getBorrowByNid(String borrowNid) {
		// 借款详情
		BorrowAndInfoVO borrow = this.amTradeClient.getBorrowByNid(borrowNid);
        if (borrow != null) {
            return borrow;
        }
		return null;
	}

	/**
	 * 根据creditNid获取债转信息
	 * @param creditNid
	 * @return
	 */
	@Override
	public HjhDebtCreditVO getHjhDebtCreditByCreditNid(String creditNid) {
		HjhDebtCreditVO credit = this.amTradeClient.selectHjhDebtCreditByCreditNid(creditNid);
        if (credit != null) {
            return credit;
        }
		return null;
	}

	/**
	 * 根据参数查询债转列表
	 * @param request
	 * @return
	 */
	@Override
	public List<CreditTenderVO> getCreditTenderList(CreditTenderRequest request) {
		List<CreditTenderVO> creditTenderList = this.amTradeClient.getCreditTenderList(request);
		return creditTenderList;
	}

	/**
	 * 根据参数查询 TenderToCreditDetailCustomizeVO
	 * @param params
	 * @return
	 */
	@Override
	public List<TenderToCreditDetailCustomizeVO> selectWebCreditTenderDetailForContract(Map<String, Object> params) {
		List<TenderToCreditDetailCustomizeVO> tenderToCreditDetailList = this.amTradeClient.selectWebCreditTenderDetailForContract(params);
		return tenderToCreditDetailList;
	}

	/**
	 * 根据参数查询 BorrowTenderVO
	 * @param tenderNid
	 * @return
	 */
	@Override
	public List<BorrowTenderVO> getBorrowTenderListByNid(String tenderNid) {
		List<BorrowTenderVO> tenderList = this.amTradeClient.getBorrowTenderListByNid(tenderNid);
		return tenderList;
	}

	/**
	* 会计划出借详情
	* @param params
	* @return
	*/
	@Override
	public UserHjhInvistDetailCustomizeVO selectUserHjhInvistDetail(Map<String, Object> params) {
		/*原return this.amBorrowClient.selectUserHjhInvistDetail(params);*/
		return this.amTradeClient.selectUserHjhInvistDetail(params);
	}

	/**
	 * 获取债转承接信息by AssignOrderId
	 * @param assignOrderId
	 * @return
	 */
	@Override
	public HjhDebtCreditTenderVO getHjhDebtCreditTenderByAssignOrderId(String assignOrderId) {
		HjhDebtCreditTenderVO vo = amTradeClient.getHjhDebtCreditTenderByAssignOrderId(assignOrderId);
		return vo;
	}

	/**
	 * 获取债转承接信息by AssignNid
	 * @param assignNid
	 * @return
	 */
	@Override
	public CreditTenderVO getCreditTenderByAssignNid(String assignNid) {
		CreditTenderVO vo = amTradeClient.getCreditTenderByAssignNid(assignNid);
		return vo;
	}

	/**
	 * 获取协议模板 by  DisplayName
	 * @param displayName
	 * @return
	 */
	@Override
	public List<ProtocolTemplateVO> getProtocolTemplateVOByDisplayName(String displayName) {
		List<ProtocolTemplateVO> volist = amTradeClient.getProtocolTemplateVOByDisplayName(displayName);
		return volist;
	}

	/**
	 * 协议名称 动态获得
	 * @return
	 */
	@Override
	public List<ProtocolTemplateVO> getdisplayNameDynamic() {
		List<ProtocolTemplateVO> volist = amTradeClient.getNewInfo();
		return volist;
	}

	/**
	 * 获得协议模板图片
	 * @param aliasName 别名
	 * @return
	 */
	@Override
	public NewAgreementResultBean setProtocolImg(String aliasName){
		List<String> url = null;
		String protocolId = null;
		NewAgreementResultBean newAgreementResultBean = new NewAgreementResultBean();

		if (StringUtils.isEmpty(aliasName)) {
			newAgreementResultBean.setStatus("99");
			newAgreementResultBean.setStatusDesc("请求参数非法");
			return newAgreementResultBean;
		}

		//是否在枚举中有定义
		String displayName = ProtocolEnum.getDisplayName(aliasName);
		if (StringUtils.isEmpty(displayName)) {
			newAgreementResultBean.setStatus("99");
			newAgreementResultBean.setStatusDesc("请求参数非法");
			return newAgreementResultBean;
		}

		protocolId = RedisUtils.get(RedisConstants.PROTOCOL_TEMPLATE_ALIAS + aliasName);
		if (StringUtils.isEmpty(protocolId)) {

			boolean flag = this.setRedisProtocolTemplate(displayName);
			if (!flag) {
				newAgreementResultBean.setStatus("000");
				newAgreementResultBean.setStatusDesc("成功");
				return newAgreementResultBean;
			}
			protocolId = RedisUtils.get(RedisConstants.PROTOCOL_TEMPLATE_ALIAS + aliasName);
		}

		try {
			url = this.getImgUrlList(protocolId);
			newAgreementResultBean.setStatus("000");
			newAgreementResultBean.setStatusDesc("成功");
			newAgreementResultBean.setRequest(url);
		} catch (Exception e) {
			newAgreementResultBean.setStatus("99");
			newAgreementResultBean.setStatusDesc("数据非法");
		}

		return newAgreementResultBean;
	}

	/**
	 * 往Redis中放入协议模板内容
	 *
	 * @param displayName
	 * @return
	 */
	public boolean setRedisProtocolTemplate(String displayName) {
		List<ProtocolTemplateVO> list = this.getProtocolTemplateVOByDisplayName(displayName);
		if (CollectionUtils.isEmpty(list)) {
			return false;
		}
		ProtocolTemplateVO protocolTemplate = list.get(0);
		//将协议模板放入redis中
		RedisUtils.set(RedisConstants.PROTOCOL_TEMPLATE_URL + protocolTemplate.getProtocolId(), protocolTemplate.getProtocolUrl() + "&" + protocolTemplate.getImgUrl());
		//获取协议模板前端显示名称对应的别名
		String alias = ProtocolEnum.getAlias(protocolTemplate.getProtocolType());
		if (StringUtils.isNotBlank(alias)) {
			RedisUtils.set(RedisConstants.PROTOCOL_TEMPLATE_ALIAS + alias, protocolTemplate.getProtocolId());//协议 ID放入redis
		}
		return true;
	}

	/**
	 * 查询协议图片
	 *
	 * @param protocolId 协议模版的ID
	 * @return
	 */
	public List<String> getImgUrlList(String protocolId) throws Exception {

		// 拿出来的信息 /hyjfdata/data/pdf/template/1528268728879.pdf&/hyjfdata/data/pdf/template/1528268728879-0, 1, 2, 3, 4
		String templateUrl = RedisUtils.get(RedisConstants.PROTOCOL_TEMPLATE_URL + protocolId);

		if (StringUtils.isEmpty(templateUrl)) {
			throw new Exception("templateUrl is null");
		}

		if (!templateUrl.contains("&")) {
			throw new Exception("templateUrl is null");
		}

		String[] strUrl = templateUrl.split("&");// &之前的是 pdf路径，&之后的是 图片路径

		//图片地址存储的路径是： /hyjfdata/data/pdf/template/1528087341328-0,1,2
		String imgUrl = strUrl[1];
		if (!imgUrl.contains("-")) {
			throw new Exception("templateUrl is null");
		}

		return getJpgJson(imgUrl);
	}

	/**
	 * 将图片拆分，配上路径
	 *
	 * @param imgUrl
	 * @return
	 */
	public List<String> getJpgJson(String imgUrl) {
		List<String> listImg = new ArrayList<>();
		String[] url = imgUrl.split("-");
		String imgPath = url[0];// /hyjfdata/data/pdf/template/1528087341328
		String[] imgSize = url[1].split(",");// 0,1,2
		for (String str : imgSize) {

			listImg.add(new StringBuilder().append(imgPath).append("/").append(str).append(".jpg").toString());
		}
		return listImg;
	}

	/**
	 * 获得对应的协议模板pdf路径
	 *
	 * @return
	 */
	@Override
	public NewAgreementResultBean getAgreementPdf(String aliasName) {
		NewAgreementResultBean newAgreementResultBean = new NewAgreementResultBean();
		if (StringUtils.isEmpty(aliasName)) {
			newAgreementResultBean.setStatus("99");
			newAgreementResultBean.setStatusDesc("请求参数非法");
			return newAgreementResultBean;
		}

		//是否在枚举中有定义
		String displayName = ProtocolEnum.getDisplayName(aliasName);
		if (StringUtils.isEmpty(displayName)) {
			newAgreementResultBean.setStatus("99");
			newAgreementResultBean.setStatusDesc("请求参数非法");
			return newAgreementResultBean;
		}

		String protocolId = RedisUtils.get(RedisConstants.PROTOCOL_TEMPLATE_ALIAS + aliasName);
		if (StringUtils.isEmpty(protocolId)) {

			boolean flag = this.setRedisProtocolTemplate(displayName);
			if (!flag) {
				newAgreementResultBean.setStatus("000");
				newAgreementResultBean.setStatusDesc("成功");
				return newAgreementResultBean;
			}
			protocolId = RedisUtils.get(RedisConstants.PROTOCOL_TEMPLATE_ALIAS + aliasName);
		}

		// 拿出来的信息 /hyjfdata/data/pdf/template/1528268728879.pdf&/hyjfdata/data/pdf/template/1528268728879-0, 1, 2, 3, 4
		String templateUrl = RedisUtils.get(RedisConstants.PROTOCOL_TEMPLATE_URL + protocolId);

		if (StringUtils.isEmpty(templateUrl)) {
			newAgreementResultBean.setStatus("99");
			newAgreementResultBean.setStatusDesc("templateUrl is null");
			return newAgreementResultBean;
		}

		if (!templateUrl.contains("&")) {
			newAgreementResultBean.setStatus("99");
			newAgreementResultBean.setStatusDesc("templateUrl is null");
			return newAgreementResultBean;
		}

		String[] strUrl = templateUrl.split("&");// &之前的是 pdf路径，&之后的是 图片路径

		String fileDomainUrl = strUrl[0];

		newAgreementResultBean.setStatus("000");
		newAgreementResultBean.setStatusDesc("成功");
		newAgreementResultBean.setInfo(fileDomainUrl);

		return newAgreementResultBean;
	}

	/**
	 * 协议名称-动态获得
	 * @return
	 */
	@Override
	public JSONObject getdisplayNameDynamicMethod() {
		JSONObject jsonObject = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		logger.info("*******************************协议名称-动态获得************************************");
		jsonObject = JSONObject.parseObject(RedisUtils.get(RedisConstants.PROTOCOL_PARAMS));
		if (jsonObject == null) {
			jsonObject = new JSONObject();
			try {
				List<ProtocolTemplateVO> list = getdisplayNameDynamic();
				//是否在枚举中有定义
                if (CollectionUtils.isNotEmpty(list)){
                    for (ProtocolTemplateVO p : list) {
                        String protocolType = p.getProtocolType();
                        String alia = ProtocolEnum.getAlias(protocolType);
                        if (alia != null){
                            map.put(alia, p.getDisplayName());
                        }
                    }
                }
				jsonObject.put("status","000");
				jsonObject.put("statusDesc","成功");
				jsonObject.put("displayName",map);
			} catch (Exception e) {
				logger.error("协议查询异常：" + e);
				jsonObject.put("status","99");
				jsonObject.put("statusDesc","失败");
			}
			RedisUtils.set(RedisConstants.PROTOCOL_PARAMS,jsonObject.toString());
		}
		return jsonObject;
	}

	/**
	 * 获取所有在帮助中心显示的模板列表
	 * add by nxl 20190313
	 * PC 1.1.2
	 * @return
	 */
	@Override
	public List<ProtocolTemplateVO> selectAllShowProtocolTemplate() {
		List<ProtocolTemplateVO> volist = amTradeClient.getAllShowProtocolTemp();
		return volist;
	}
}
