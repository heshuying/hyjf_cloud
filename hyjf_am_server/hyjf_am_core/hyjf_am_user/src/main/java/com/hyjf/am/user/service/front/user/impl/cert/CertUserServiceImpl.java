package com.hyjf.am.user.service.front.user.impl.cert;

import com.hyjf.am.user.dao.mapper.auto.CertUserMapper;
import com.hyjf.am.user.dao.mapper.customize.CertUserCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.CertUser;
import com.hyjf.am.user.dao.model.auto.CertUserExample;
import com.hyjf.am.user.dao.model.customize.CertSendUserCustomize;
import com.hyjf.am.user.service.front.user.cert.CertUserService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 合规数据上报 CERT
 * @Author sunss
 * @Date 2019/1/21 17:57
 */
@Service
public class CertUserServiceImpl extends BaseServiceImpl implements CertUserService {

    @Resource
    private CertUserMapper certUserMapper;

    @Resource
    private CertUserCustomizeMapper certUserCustomizeMapper;

    /**
     * 插入国家互联网应急中心已上送用户表
     *
     * @param request
     */
    @Override
    public void insertCertUser(CertUser request) {
        certUserMapper.insertSelective(request);
    }

    /**
     * 根据userId查询需要上报的用户信息
     *
     * @param userId
     */
    @Override
    public CertSendUserCustomize getCertSendUserByUserId(Integer userId) {
        return certUserCustomizeMapper.getCertSendUserByUserId(userId);
    }

    /**
     * 修改国家互联网应急中心已上送用户表
     *
     * @param request
     */
    @Override
    public void updateCertUser(CertUser request) {
        certUserMapper.updateByPrimaryKeySelective(request);
    }

    /**
     * 根据borrowNid userId查询
     *
     * @param userId
     * @param borrowNid
     */
    @Override
    public List<CertUser> getCertUserByUserIdBorrowNid(Integer userId, String borrowNid) {
        CertUserExample example = new CertUserExample();
        CertUserExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        cra.andBorrowNidEqualTo(borrowNid);
        List<CertUser> list = certUserMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list;
        }

        return null;
    }
}
