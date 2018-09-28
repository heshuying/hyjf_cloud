package com.hyjf.cs.user.service.account;

import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.cs.user.service.BaseUserService;

import java.util.List;
import java.util.Map;
/**
 * 融东风用户账户接口
 * @author jun
 *
 */
public interface RdfAccountService extends BaseUserService{

      /**
       * 通过手机号获取账户余额
       * @param mobile
       * @return
       */
      public String getBalance(String mobile);

      /**
       * 通过手机号获取银行卡信息
       * @param mobile
       * @return
       */
      public BankCardVO getBankCard(String mobile);

      /**
       * 同步余额
       * @param ids
       * @return
       */
      public List<Map<String, String>> balanceSync(List<Integer> ids);
}