package com.hyjf.cs.user.service.account;

import java.util.List;
import java.util.Map;

import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.cs.user.service.BaseUserService;
/**
 * surong账户相关查询
 * @author jun
 *
 */
public interface RdfAccountService extends BaseUserService{
	
      public String getBalance(String mobile);
      
      public BankCardVO getBankCard(String mobile);
      
      public List<Map<String, String>> balanceSync(List<Integer> ids);
}