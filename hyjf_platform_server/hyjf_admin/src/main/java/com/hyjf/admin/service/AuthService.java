package com.hyjf.admin.service;

import com.hyjf.am.vo.user.HjhUserAuthConfigVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

/**
 * @author hesy
 * @version AuthService, v0.1 2018/10/15 11:54
 */
public interface AuthService {
    /**
     * 从redis里面获取授权配置
     * @param key
     * @return
     */
    static HjhUserAuthConfigVO getAuthConfigFromCache(String key){
        HjhUserAuthConfigVO hjhUserAuthConfig=RedisUtils.getObj(key,HjhUserAuthConfigVO.class);
        return hjhUserAuthConfig;
    }

    BankCallBean getTermsAuthQuery(int userId, String channel);

    boolean checkDefaultConfig(BankCallBean bean, String authType);

    boolean checkIsAuth(Integer userId, String txcode);

    void updateUserAuth(Integer userId, BankCallBean retBean, String authType);

    HjhUserAuthVO getHjhUserAuthByUserId(Integer userId);
}
