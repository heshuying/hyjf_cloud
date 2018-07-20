package com.hyjf.cs.user.service.recharge;

import com.hyjf.am.vo.config.BankRechargeLimitConfigVO;
import com.hyjf.am.vo.config.FeeConfigVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.user.AccountChinapnrVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.cs.user.vo.QpCardInfoVO;
import com.hyjf.pay.lib.chinapnr.ChinapnrBean;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface RdfRechargeService extends BaseUserService {
	
	UserVO findUserByMobile(String mobile);
	


}
