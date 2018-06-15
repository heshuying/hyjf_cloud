/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl.callcenter;

import com.hyjf.am.resquest.callcenter.CallCenterServiceUsersRequest;
import com.hyjf.am.resquest.callcenter.CallCenterUserInfoRequest;
import com.hyjf.am.user.dao.mapper.auto.BankCardMapper;
import com.hyjf.am.user.dao.mapper.auto.CallcenterServiceUsersMapper;
import com.hyjf.am.user.dao.mapper.customize.callcenter.CallCenterCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.dao.model.auto.BankCardExample;
import com.hyjf.am.user.dao.model.auto.CallcenterServiceUsers;
import com.hyjf.am.user.dao.model.auto.CallcenterServiceUsersExample;
import com.hyjf.am.user.dao.model.customize.callcenter.CallcenterUserBaseCustomize;
import com.hyjf.am.user.service.callcenter.CallCenterBankService;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author wangjun
 * @version CallCenterBankServiceImpl, v0.1 2018/6/6 14:22
 */
@Service
public class CallCenterBankServiceImpl implements CallCenterBankService {
	@Autowired
	private BankCardMapper bankCardMapper;
	@Autowired
	private CallCenterCustomizeMapper callCenterCustomizeMapper;

	@Autowired
	private CallcenterServiceUsersMapper callcenterServiceUsersMapper;

	public List<BankCard> getTiedCardOfAccountBank(Integer userId){
		BankCardExample example = new BankCardExample();
		BankCardExample.Criteria cra = example.createCriteria();
		cra.andUserIdEqualTo(userId);
		List<BankCard> bankCardList = bankCardMapper.selectByExample(example);
		if(bankCardList!= null && bankCardList.size()>0){
			return bankCardList;
		}
		return null;
	}

	@Override
	public List<CallcenterUserBaseCustomize> getNoServiceFuTouUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest) {
		List<CallcenterUserBaseCustomize> CallcenterUserBaseCustomizeList = callCenterCustomizeMapper.findNoServiceFuTouUsersList(callCenterUserInfoRequest);
		return CallcenterUserBaseCustomizeList;
	}

	@Override
	public List<CallcenterUserBaseCustomize> getNoServiceLiuShiUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest) {
		List<CallcenterUserBaseCustomize> CallcenterUserBaseCustomizeList = callCenterCustomizeMapper.findNoServiceLiuShiUsersList(callCenterUserInfoRequest);
		return CallcenterUserBaseCustomizeList;
	}

	@Override
	public List<CallcenterUserBaseCustomize> getNoServiceUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest) {
		List<CallcenterUserBaseCustomize> CallcenterUserBaseCustomizeList = callCenterCustomizeMapper.findNoServiceUsersList(callCenterUserInfoRequest);
		return CallcenterUserBaseCustomizeList;
	}

	@Override
	public Integer updateRecord(CallCenterServiceUsersRequest callCenterServiceUsersRequest){
		//当前时间
		Date nowDate = new Date();
		//操作记录数
		int rowCound = 0;
		List<CallcenterServiceUsers> centerServiceUsersList =
				CommonUtils.convertBeanList(callCenterServiceUsersRequest.getCallCenterServiceUsersVOList(),CallcenterServiceUsers.class);
		for (CallcenterServiceUsers bean : centerServiceUsersList) {
			//检索条件
			CallcenterServiceUsersExample example = new CallcenterServiceUsersExample();
			example.createCriteria().andUsernameEqualTo(bean.getUsername());
			//检索
			List<CallcenterServiceUsers> list = callcenterServiceUsersMapper.selectByExample(example);
			if (list.size() > 0) {
				//更新
				bean.setUpddate(nowDate);//更新时间
				rowCound += this.callcenterServiceUsersMapper.updateByExampleSelective(bean, example);
			}else{
				//登陆
				bean.setInsdate(nowDate);//登陆时间
				rowCound += callcenterServiceUsersMapper.insertSelective(bean);
			}
		}
		return rowCound;
	}

	@Override
	public List<CallcenterUserBaseCustomize> getBasicUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest) {
		List<CallcenterUserBaseCustomize> CallcenterUserBaseCustomizeList = callCenterCustomizeMapper.findBasicUsersList(callCenterUserInfoRequest);

		/*在此拼装*/


		return CallcenterUserBaseCustomizeList;
	}

	@Override
	public List<CallcenterUserBaseCustomize> getUserDetailById(CallCenterUserInfoRequest callCenterUserInfoRequest) {
		List<CallcenterUserBaseCustomize> CallcenterUserBaseCustomizeList = callCenterCustomizeMapper.findUserDetailById(callCenterUserInfoRequest);

		/*在此拼装*/

		return CallcenterUserBaseCustomizeList;
	}
}
