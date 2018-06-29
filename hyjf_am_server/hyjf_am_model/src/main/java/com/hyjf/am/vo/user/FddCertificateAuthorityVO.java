package com.hyjf.am.vo.user;

import java.io.Serializable;

/**
 * 法大大CA认证Bean
 *
 * @author liuyang
 */
public class FddCertificateAuthorityVO implements Serializable {

    private static final long serialVersionUID = -2739950621085835647L;
    // 用户ID
    private Integer userId;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
