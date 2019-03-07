/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.resquest.api.AutoTenderComboRequest;
import com.hyjf.am.trade.dao.mapper.auto.BorrowTenderTmpinfoMapper;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowTenderTmp;
import com.hyjf.am.trade.dao.model.auto.BorrowTenderTmpinfo;
import com.hyjf.am.trade.service.api.autotender.ApiAutoTenderService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author libin
 * @version ApiAutoTenderServiceImpl.java, v0.1 2018年8月27日 上午10:11:11
 */
@Service
public class ApiAutoTenderServiceImpl extends BaseServiceImpl implements ApiAutoTenderService{
	
	@Autowired
	private BorrowTenderTmpinfoMapper borrowTenderTmpinfoMapper;

	@SuppressWarnings("unused")
	@Override
	public Integer updateTenderLog(AutoTenderComboRequest autoTenderComboRequest) {
		Integer flg;
		Integer userId = autoTenderComboRequest.getUserId();
		Borrow borrow = getBorrowByNid(autoTenderComboRequest.getBorrowNid());
		// 1，更新 BorrowTenderTmp 表
		BorrowTenderTmp temp = new BorrowTenderTmp();
		// 注意注掉的部分微服务已不再用
		temp.setUserId(userId);
		temp.setBorrowNid(autoTenderComboRequest.getBorrowNid());
		temp.setNid(autoTenderComboRequest.getGenerateOrderId());
		temp.setAccount(new BigDecimal(autoTenderComboRequest.getAccount()));
		temp.setAddIp(autoTenderComboRequest.getIpAddress());
		/*temp.setChangeStatus(0);*/
		/*temp.setChangeUserid(0);*/
		/*temp.setChangePeriod(0);*/
		/*temp.setTenderStatus(0);*/
		/*temp.setTenderNid(borrowNid);*/
		/*temp.setTenderAwardAccount(new BigDecimal(0));*/
		temp.setRecoverFullStatus(0);
		temp.setRecoverFee(new BigDecimal(0));
		/*temp.setRecoverType("");*/
		temp.setRecoverAdvanceFee(new BigDecimal(0));
		temp.setRecoverLateFee(new BigDecimal(0));
		/*temp.setTenderAwardFee(new BigDecimal(0));*/
/*		temp.setContents("");
		temp.setAutoStatus(0);
		temp.setWebStatus(0);
		temp.setPeriodStatus(0);
		temp.setWeb(0);*/
		temp.setIsBankTender(1);
		
		temp.setStatus(0);
		temp.setBorrowUserId(borrow.getUserId());
		temp.setBorrowUserName(borrow.getBorrowUserName());
		temp.setInviteUserId(0);
		
		temp.setCreateTime(new Date());
		/*temp.setTenderUserName(userName);*/
        String couponGrantId = autoTenderComboRequest.getCouponGrantId();
        if(StringUtils.isNotEmpty(couponGrantId)){
        	temp.setCouponGrantId(Integer.valueOf(couponGrantId));// 为出借完全掉单优惠券出借时修复做记录
        }
        boolean tenderTmpFlag = borrowTenderTmpMapper.insertSelective(temp) > 0 ? true : false;
		if (!tenderTmpFlag) {
			try {
				throw new Exception("插入borrowTenderTmp表失败，出借订单号：" + autoTenderComboRequest.getGenerateOrderId());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		// 2.更新 BorrowTenderTmpinfo 表
		BorrowTenderTmpinfo info = new BorrowTenderTmpinfo();
		info.setOrdid(autoTenderComboRequest.getGenerateOrderId());
		Map<String, String> map = new HashMap<String, String>();
		map.put("borrow_nid", autoTenderComboRequest.getBorrowNid());
		map.put("user_id", autoTenderComboRequest.getUserId() + "");
		map.put("account", new BigDecimal(autoTenderComboRequest.getAccount()) + "");
		map.put("status", "0");
		map.put("nid", autoTenderComboRequest.getGenerateOrderId());
		map.put("addtime", (new Date().getTime() / 1000) + "");
		map.put("addip", autoTenderComboRequest.getIpAddress());
		String array = JSON.toJSONString(map);
		info.setTmpArray(array);
		info.setCreateTime(new Date());
		flg = borrowTenderTmpinfoMapper.insertSelective(info);
		if(flg == null){
			try {
				throw new Exception("插入borrowTenderTmpInfo表失败，出借订单号：" + autoTenderComboRequest.getGenerateOrderId());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			return 0;
		}
		return flg;
	}
}
