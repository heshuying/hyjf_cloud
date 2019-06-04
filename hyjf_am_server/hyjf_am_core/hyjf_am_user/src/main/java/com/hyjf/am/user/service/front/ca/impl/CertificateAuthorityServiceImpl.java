/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.ca.impl;

import com.hyjf.am.user.dao.mapper.customize.CertificateCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.CertificateAuthority;
import com.hyjf.am.user.service.front.ca.CertificateAuthorityService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.user.CertificateAuthorityVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author: sunpeikai
 * @version: CertificateAuthorityServiceImpl, v0.1 2018/7/17 10:12
 */
@Service
public class CertificateAuthorityServiceImpl extends BaseServiceImpl implements CertificateAuthorityService {

    @Autowired
    private CertificateCustomizeMapper certificateCustomizeMapper;

    /**
     * CertificateAuthority更新-法大大CA认证MQ用
     * @auth sunpeikai
     * @param certificateAuthorityVO 更新参数
     * @return
     */
    @Override
    public Integer updateCertificateAuthority(CertificateAuthorityVO certificateAuthorityVO) {
        CertificateAuthority certificateAuthority = CommonUtils.convertBean(certificateAuthorityVO,CertificateAuthority.class);
        return certificateAuthorityMapper.updateByPrimaryKeySelective(certificateAuthority);
    }

    /**
     * CertificateAuthority新增-法大大CA认证MQ用
     * @auth sunpeikai
     * @param certificateAuthorityVO 插入参数
     * @return
     */
    @Override
    public Integer insertCertificateAuthority(CertificateAuthorityVO certificateAuthorityVO) {
        CertificateAuthority certificateAuthority = CommonUtils.convertBean(certificateAuthorityVO,CertificateAuthority.class);
        return certificateAuthorityMapper.insertSelective(certificateAuthority);
    }

    /**
     * 通过用户id查询 ca客户编号
     */
    @Override
    public List<CertificateAuthorityVO> queryCustomerId(Set<Integer> userId) {
         return certificateCustomizeMapper.queryCustomerId(userId);
    }
}
