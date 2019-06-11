/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.ca;

import com.hyjf.am.vo.user.CertificateAuthorityVO;

import java.util.List;
import java.util.Set;

/**
 * @author: sunpeikai
 * @version: CertificateAuthorityService, v0.1 2018/7/17 10:11
 */
public interface CertificateAuthorityService {

    /**
     * CertificateAuthority更新-法大大CA认证MQ用
     * @auth sunpeikai
     * @param certificateAuthorityVO 更新参数
     * @return
     */
    Integer updateCertificateAuthority(CertificateAuthorityVO certificateAuthorityVO);

    /**
     * CertificateAuthority新增-法大大CA认证MQ用
     * @auth sunpeikai
     * @param certificateAuthorityVO 插入参数
     * @return
     */
    Integer insertCertificateAuthority(CertificateAuthorityVO certificateAuthorityVO);

    List<CertificateAuthorityVO> queryCustomerId(Set<Integer> userId);

    List<CertificateAuthorityVO> queryCustomerIds(List<Integer> userIds);
}
