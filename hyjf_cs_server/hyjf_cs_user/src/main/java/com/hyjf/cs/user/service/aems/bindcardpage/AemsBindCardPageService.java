package com.hyjf.cs.user.service.aems.bindcardpage;

import com.hyjf.cs.user.bean.AemsBindCardPageRequestBean;
import com.hyjf.cs.user.bean.BindCardPageBean;
import com.hyjf.cs.user.bean.BindCardPageRequestBean;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @version AemsBindCardPageService, v0.1 2018/12/6 14:18
 * @Author: Zha Daojian
 */
public interface AemsBindCardPageService  extends BaseUserService {

    /**
     * 用户绑卡后处理
     *
     * @param bean
     * @return
     */
     void updateAfterBindCard(BankCallBean bean) throws Exception;
    /**
     * 新版开户绑卡页面共同
     * @param bean
     * @return
     */
     ModelAndView getCallbankMV(BindCardPageBean bean);

    /**
     * 绑卡校验API端
     * @param bankCardRequestBean
     * @return
     */
    Map<String,String> checkParamBindCardPageApi(AemsBindCardPageRequestBean bankCardRequestBean);
}
