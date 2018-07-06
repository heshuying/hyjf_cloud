package com.hyjf.cs.user.service.myasset;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.cs.common.service.BaseService;

/**
 * app账户总览service
 * @author jun
 *
 */
public interface MyAssetService extends BaseService{

	UserInfoVO getUsersInfoByUserId(Integer userId);

	AccountVO getAccount(Integer userId);

}
