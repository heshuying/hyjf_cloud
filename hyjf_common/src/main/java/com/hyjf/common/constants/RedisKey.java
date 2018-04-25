package com.hyjf.common.constants;

/**
 * @author xiasq
 * @version RedisKey, v0.1 2018/4/24 14:59
 */
public interface RedisKey {
    /**
     * 用户token令牌前缀
     */
    String USER_TOKEN_REDIS = "user_token_";

    /**
     * 放款批次号key
     */
    String BATCH_NO = "batchNo";
    /**
     *
     */
    String DATA_BATCH_NO = "dataBatchNo";
    /**
     * 短信配置key
     */
    String SMS_CONFIG = "smsConfig";

    /**
     * 记录密码错误次数Redis前缀
     */
    String PASSWORD_ERR_COUNT = "password_err_count_";
}
