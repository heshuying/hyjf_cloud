package com.hyjf.cs.user.service.aems.auth;

import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.cs.user.bean.AemsMergeAuthPagePlusRequestBean;
import com.hyjf.cs.user.bean.AuthBean;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

public interface AemsAuthService extends BaseUserService {

    /**
     * AEMS系统多合一授权参数校验
     * @param requestBean
     * @return
     */
    Map<String,String> checkAemsParam(@Valid AemsMergeAuthPagePlusRequestBean requestBean);

    /**
     * 检查是否授权过了
     * @author pcc
     * @param userId
     * @return
     */
    boolean checkIsAuth(Integer userId, String authType);

    /**
     * 校验用户角色和授权类型是否一致
     * @param authBean
     * @param authTypeString
     * @param paramMap
     * @return
     */
    Map<String,String> checkUserRoleAndAuthType(AuthBean authBean, String authTypeString, Map<String, String> paramMap);

    /**
     * 补充数据，请求银行
     * @param authBean
     * @return
     */
    ModelAndView getApiCallbankMV(AuthBean authBean);

    /**
     * 判断授权类型，保存至授权日志表
     * @param authBean
     * @param orderId
     */
    void saveUserAuthLog(AuthBean authBean, String orderId);

    /**
     * 校验是否默认配置
     * @param bean
     * @param authType
     * @return
     */
    boolean checkDefaultConfig(BankCallBean bean, String authType);

    /**
     * 更新授权日志信息
     * @param logOrderId
     * @param message
     */
    void updateUserAuthLog(String logOrderId, String message);

    /**
     * 根据用户id更新用户签约授权信息
     * @param userId
     * @param bean
     * @param authType
     */
    void updateUserAuth(Integer userId, BankCallBean bean, String authType);

    /**
	 * 根据用户id查询授权
	 */
	HjhUserAuthVO getHjhUserAuthByUserId(Integer userId);

    /**
     * 查询授权错误信息
     * @param orderId
     * @return
     */
    String seachUserAuthErrorMessgae(String orderId);
}
