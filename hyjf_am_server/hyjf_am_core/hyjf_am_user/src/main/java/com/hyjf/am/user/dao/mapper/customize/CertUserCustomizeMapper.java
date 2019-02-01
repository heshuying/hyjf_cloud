package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.customize.CertSendUserCustomize;

/**
 * 合规数据上报 CERT
 */
public interface CertUserCustomizeMapper {
    /**
     * 查询需要上报的用户信息
     * @param userId
     * @return
     */
    CertSendUserCustomize getCertSendUserByUserId(int userId);
}

	