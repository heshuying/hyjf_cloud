/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.datacollect.AppAccesStatisticsVO;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsDetailVO;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsVO;
import com.hyjf.am.vo.trade.wrb.WrbTenderNotifyCustomizeVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.market.client.AmAdminClient;
import com.hyjf.cs.market.client.AmUserClient;
import com.hyjf.cs.market.client.CsMessageClient;
import com.hyjf.cs.market.mq.base.MessageContent;
import com.hyjf.cs.market.mq.producer.AppChannelStatisticsProducer;
import com.hyjf.cs.market.service.AppChannelStatisticsService;
import com.hyjf.cs.market.service.BaseMarketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author fuqiang
 * @version AppChannelStatisticsServiceImpl, v0.1 2018/7/16 14:18
 */
@Service
public class AppChannelStatisticsServiceImpl extends BaseMarketServiceImpl implements AppChannelStatisticsService {
	@Autowired
	private AmUserClient amUserClient;

	@Autowired
	private AmAdminClient amAdminClient;

	@Autowired
	private CsMessageClient csMessageClient;

	@Autowired
	private AppChannelStatisticsProducer producer;

	@Override
	public void insertStatistics() {
		logger.info("----------------APP渠道统计定时任务Start-------------");

		AppChannelStatisticsRequest request = new AppChannelStatisticsRequest();
//		String nowDate = "2018-08-21";
		String nowDate = GetDate.date2Str(GetDate.date_sdf);
		request.setTimeStartSrch(GetDate.getDayStart(nowDate));
		request.setTimeEndSrch(GetDate.getDayEnd(nowDate));

		// 查询所有app渠道
		List<UtmVO> voList = amUserClient.selectUtmPlatList("app");
		if (!CollectionUtils.isEmpty(voList)) {
			for (UtmVO vo : voList) {
				Integer sourceId = vo.getSourceId();
				request.setSourceId(String.valueOf(sourceId));
				// 访问数
				Integer accessNumber = getAccessNumber(request);
				// 注册数
				Integer registNumber = getRegistNumber(request);
				// 开户数
				Integer openaccountnumber = getOpenAccountNumber(request);
				// 查询相应的app渠道无主单开户数
				Integer openAccountAttrCount = getOpenAccountAttrCount(request);
				// 查询相应的app渠道投资无主单用户数
				Integer investAttrNumber = getTenderNumber(request,"all");
				// 累计充值
				BigDecimal cumulativeRecharge = getCumulativeRecharge(request);
				// 汇直投投资金额
				BigDecimal hztTenderPrice = getHztTenderPrice(request);
				// 汇消费投资金额
				BigDecimal hxfTenderPrice = getHxfTenderPrice(request);
				// 汇天利投资金额
				BigDecimal htlTenderPrice = BigDecimal.ZERO;
				// 汇添金投资金额
				BigDecimal htjTenderPrice = BigDecimal.ZERO;
				// 汇金理财投资金额
				BigDecimal rtbTenderPrice = BigDecimal.ZERO;
				// 汇转让投资金额
				BigDecimal hzrTenderPrice = getHzrTenderPrice(request);
				// app渠道主单注册数
				BigDecimal registerAttrCount = getRegisterAttrCount(request);
				// 查询相应的app渠道Ios开户数
				Integer accountNumberIos = getAccountNumber(request,"ios");
				// 查询相应的app渠道PC开户数
				Integer accountNumberPc =getAccountNumber(request,"pc");
				// 查询相应的app渠道安卓开户数
				Integer accountNumberAndroid = getAccountNumber(request,"android");
				// 查询相应的app渠道微信开户数
				Integer accountNumberWechat =  getAccountNumber(request,"wechat");
				// 查询相应的app渠道用户Android投资数
				Integer tenderNumberAndroid = getTenderNumber(request,"android");
				// 查询相应的app渠道用户IOS投资数
				Integer tenderNumberIos = getTenderNumber(request,"ios");
				// 查询相应的app渠道用户PC投资数
				Integer tenderNumberPc = getTenderNumber(request,"pc");
				// 查询相应的app渠道用户微信投资数
				Integer tenderNumberWechat = getTenderNumber(request,"wechat");
				// 投资用户数
				Integer investNumber = getTenderNumber(request,"all");
				// 查询相应的app渠道无主单用户充值数
				BigDecimal cumulativeAttrCharge = getCumulativeAttrCharge(request);
				// 查询相应的app渠道无主单用户投资总额
				BigDecimal cumulativeAttrInvest = hztTenderPrice.add(hxfTenderPrice).add(hzrTenderPrice);
				// 查询相应的app渠道累计投资
				BigDecimal cumulativeInvest = hztTenderPrice.add(hxfTenderPrice).add(hzrTenderPrice);
				AppChannelStatisticsVO statisticsVO = new AppChannelStatisticsVO(sourceId, vo.getSourceName(),
						accessNumber, registNumber, openaccountnumber, investNumber, cumulativeRecharge, hztTenderPrice,
						hxfTenderPrice, htlTenderPrice, htjTenderPrice, rtbTenderPrice, hzrTenderPrice, new Date(),
						registerAttrCount, accountNumberIos, accountNumberPc, accountNumberAndroid, accountNumberWechat,
						tenderNumberAndroid, tenderNumberIos, tenderNumberPc, tenderNumberWechat, cumulativeAttrCharge,
						openAccountAttrCount, investAttrNumber, cumulativeAttrInvest,cumulativeInvest);
				try {
					producer.messageSend(new MessageContent(MQConstant.APP_CHANNEL_STATISTICS_TOPIC,
							System.currentTimeMillis() + "", JSONObject.toJSONBytes(statisticsVO)));
				} catch (MQException e) {
					logger.error("APP渠道统计数据定时任务出错......", e);
				}
			}
		}
	}

	/**
	 * 访问数
	 * @param request
	 * @return
	 */
	public Integer getAccessNumber(AppChannelStatisticsRequest request){
		List<AppAccesStatisticsVO> list = csMessageClient.getAppAccesStatisticsVO(request);
		if(list == null || list.size() == 0){
			return 0;
		}

		return list.size();
	}

	/**
	 * 注册数
	 * @param request
	 * @return
	 */
	public Integer getRegistNumber(AppChannelStatisticsRequest request){
		request.setSourceIdSrch("registerTime");
		List<AppChannelStatisticsDetailVO> list = csMessageClient.getAppChannelStatisticsDetailVO(request);
		if(list == null || list.size() == 0){
			return 0;
		}

		return list.size();
	}

	/**
	 * 开户数
	 * @param request
	 * @return
	 */
	public Integer getOpenAccountNumber(AppChannelStatisticsRequest request){
		request.setSourceIdSrch("openAccountTime");
		List<AppChannelStatisticsDetailVO> list = csMessageClient.getAppChannelStatisticsDetailVO(request);
		if(list == null || list.size() == 0){
			return 0;
		}

		return list.size();
	}

	/**
	 * 查询相应的app渠道无主单开户数
	 * @param request
	 * @return
	 */
	public Integer getOpenAccountAttrCount(AppChannelStatisticsRequest request){
		request.setSourceIdSrch("openAccountTime");
		List<AppChannelStatisticsDetailVO> list = csMessageClient.getAppChannelStatisticsDetailVO(request);
		if(list == null || list.size() == 0){
			return 0;
		}

		List<Integer> listUserId = amAdminClient.getUsersInfoList();
		if(listUserId == null || listUserId.size() == 0){
			return 0;
		}

		int i = 0;
		for(AppChannelStatisticsDetailVO vo : list){
			boolean flag = listUserId.contains(vo.getUserId());
			if(flag){
				i++;
			}
		}

		return i;
	}

	/**
	 * 累计充值
	 * @param request
	 * @return
	 */
	public BigDecimal getCumulativeRecharge(AppChannelStatisticsRequest request){
		List<AppChannelStatisticsDetailVO> list = csMessageClient.getAppChannelStatisticsDetailVO(request);
		if(list == null || list.size() == 0){
			return BigDecimal.ZERO;
		}

		Set<Integer> setUserId = new HashSet<>();
		for(AppChannelStatisticsDetailVO appVo : list){

			setUserId.add(appVo.getUserId());
		}

		List<WrbTenderNotifyCustomizeVO> tenderNotifyCustomizeVOList = amAdminClient.getAccountRechargeByAddtime(request);

		BigDecimal money = BigDecimal.ZERO;
		for(WrbTenderNotifyCustomizeVO vo : tenderNotifyCustomizeVOList){

			if(setUserId.contains(vo.getUserId())){

				money = money.add(vo.getAccount());
			}
		}

		return money;
	}

	/**
	 * app渠道无主单用户充值数
	 * @param request
	 * @return
	 */
	public BigDecimal getCumulativeAttrCharge(AppChannelStatisticsRequest request){
		request.setSourceIdSrch("hxfTenderPrice");
		List<AppChannelStatisticsDetailVO> list = csMessageClient.getAppChannelStatisticsDetailVO(request);
		if(list == null || list.size() == 0){
			return BigDecimal.ZERO;
		}

		List<Integer> integerList = amAdminClient.getUsersInfoList();
		Set<Integer> setUnUserId = new HashSet<>();
		Set<Integer> setUserId = new HashSet<>();
		for(AppChannelStatisticsDetailVO appVo : list){
			setUnUserId.add(appVo.getUserId());
		}

		for(Integer ids : integerList){

			if(setUnUserId.contains(ids)){

				setUserId.add(ids);
			}
		}

		List<WrbTenderNotifyCustomizeVO> tenderNotifyCustomizeVOList = amAdminClient.getAccountRechargeByAddtime(request);

		BigDecimal money = BigDecimal.ZERO;
		for(WrbTenderNotifyCustomizeVO vo : tenderNotifyCustomizeVOList){

			if(setUserId.contains(vo.getUserId())){

				money = money.add(vo.getAccount());
			}
		}

		return money;
	}

	/**
	 * 汇直投投资金额
	 * @param request
	 * @return
	 */
	public BigDecimal getHztTenderPrice(AppChannelStatisticsRequest request){
		request.setSourceIdSrch("hztTenderPrice");
		List<AppChannelStatisticsDetailVO> list = csMessageClient.getAppChannelStatisticsDetailVO(request);
		if(list == null || list.size() == 0){
			return BigDecimal.ZERO;
		}

		Set<Integer> setUserId = new HashSet<>();
		for(AppChannelStatisticsDetailVO appVo : list){

			setUserId.add(appVo.getUserId());
		}

		List<WrbTenderNotifyCustomizeVO> tenderNotifyCustomizeVOList = amAdminClient.getBorrowTenderByAddtime(request);

		BigDecimal money = BigDecimal.ZERO;
		for(WrbTenderNotifyCustomizeVO vo : tenderNotifyCustomizeVOList){

			if(setUserId.contains(vo.getUserId())){

				money = money.add(vo.getAccount());
			}
		}

		return money;
	}

	/**
	 * 汇消费投资金额
	 * @param request
	 * @return
	 */
	public BigDecimal getHxfTenderPrice(AppChannelStatisticsRequest request){
		request.setSourceIdSrch("hxfTenderPrice");
		List<AppChannelStatisticsDetailVO> list = csMessageClient.getAppChannelStatisticsDetailVO(request);
		if(list == null || list.size() == 0){
			return BigDecimal.ZERO;
		}

		Set<Integer> setUserId = new HashSet<>();
		for(AppChannelStatisticsDetailVO appVo : list){

			setUserId.add(appVo.getUserId());
		}

		List<WrbTenderNotifyCustomizeVO> tenderNotifyCustomizeVOList = amAdminClient.getBorrowTenderByAddtime(request);

		BigDecimal money = BigDecimal.ZERO;
		for(WrbTenderNotifyCustomizeVO vo : tenderNotifyCustomizeVOList){

			if(setUserId.contains(vo.getUserId())){

				money = money.add(vo.getAccount());
			}
		}

		return money;
	}

	/**
	 * 汇转让投资金额
	 * @param request
	 * @return
	 */
	public BigDecimal getHzrTenderPrice(AppChannelStatisticsRequest request){
		List<AppChannelStatisticsDetailVO> list = csMessageClient.getAppChannelStatisticsDetailVO(request);
		if(list == null || list.size() == 0){
			return BigDecimal.ZERO;
		}

		Set<Integer> setUserId = new HashSet<>();
		for(AppChannelStatisticsDetailVO appVo : list){

			setUserId.add(appVo.getUserId());
		}

		List<WrbTenderNotifyCustomizeVO> tenderNotifyCustomizeVOList = amAdminClient.getCreditTenderByAddtime(request);

		BigDecimal money = BigDecimal.ZERO;
		for(WrbTenderNotifyCustomizeVO vo : tenderNotifyCustomizeVOList){

			if(setUserId.contains(vo.getUserId())){

				money = money.add(vo.getAccount());
			}
		}

		return money;
	}

	/**
	 * app渠道主单注册数
	 * @param request
	 * @return
	 */
	public BigDecimal getRegisterAttrCount(AppChannelStatisticsRequest request){
		request.setSourceIdSrch("registerTime");
		List<AppChannelStatisticsDetailVO> list = csMessageClient.getAppChannelStatisticsDetailVO(request);
		if(list == null || list.size() == 0){
			return BigDecimal.ZERO;
		}

		return new BigDecimal(list.size());
	}

	/**
	 * app渠道Ios、pc、wechat、android开户数
	 * @param request
	 * @return
	 */
	public Integer getAccountNumber(AppChannelStatisticsRequest request,String source){
		request.setSourceIdSrch("openAccountTime");
		List<AppChannelStatisticsDetailVO> list = csMessageClient.getAppChannelStatisticsDetailVO(request);
		if(list == null || list.size() == 0){
			return 0;
		}

		List<Integer> listUserId = amAdminClient.getUsersList(source);
		if(listUserId == null || listUserId.size() == 0){
			return 0;
		}

		int i = 0;
		for(AppChannelStatisticsDetailVO vo : list){
			boolean flag = listUserId.contains(vo.getUserId());
			if(flag){
				i++;
			}
		}

		return i;
	}

	/**
	 * app渠道Ios、pc、wechat、android开户数
	 * @param request
	 * @return
	 */
	public Integer getTenderNumber(AppChannelStatisticsRequest request,String source){
		request.setSourceIdSrch("hxfTenderPrice");
		List<AppChannelStatisticsDetailVO> list = csMessageClient.getAppChannelStatisticsDetailVO(request);
		if(list == null || list.size() == 0){
			return 0;
		}

		int i = 0;
		//1、
		List<WrbTenderNotifyCustomizeVO> list1 = amAdminClient.getBorrowTenderByClient(request);
		if(list1.size() > 0){

			for(AppChannelStatisticsDetailVO vo : list){

				for(WrbTenderNotifyCustomizeVO tender : list1 ){
					if(vo.getUserId().equals(tender.getUserId())){
						i++;
						break;
					}
				}

			}
		}

		//2、
		List<WrbTenderNotifyCustomizeVO> list2 = amAdminClient.getProductListByClient(request);
		if(list2.size() > 0){

			for(AppChannelStatisticsDetailVO vo : list){

				for(WrbTenderNotifyCustomizeVO tender : list2 ){
					if(vo.getUserId().equals(tender.getUserId())){
						i++;
						break;
					}
				}

			}
		}

		//3、
		List<WrbTenderNotifyCustomizeVO> list3 = amAdminClient.getDebtPlanAccedeByClient(request);
		if(list3.size() > 0){

			for(AppChannelStatisticsDetailVO vo : list){

				for(WrbTenderNotifyCustomizeVO tender : list3 ){
					if(vo.getUserId().equals(tender.getUserId())){
						i++;
						break;
					}
				}

			}
		}

		//4、
		List<WrbTenderNotifyCustomizeVO> list4 = amAdminClient.getCreditTenderByClient(request);
		if(list4.size() > 0){

			for(AppChannelStatisticsDetailVO vo : list){

				for(WrbTenderNotifyCustomizeVO tender : list4 ){
					if(vo.getUserId().equals(tender.getUserId())){
						i++;
						break;
					}
				}

			}
		}

		return i;
	}

}
