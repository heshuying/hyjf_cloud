package com.hyjf.am.user.service.front.user.cert;

import com.hyjf.am.user.dao.model.auto.CertUser;
import com.hyjf.am.user.dao.model.customize.CertSendUserCustomize;

import java.util.List;

public interface CertUserService {


    /**
     * 插入国家互联网应急中心已上送用户表
     * @param request
     */
    void insertCertUser(CertUser request);

    /**
     * 根据userId查询需要上报的用户信息
     * @param userId
     */
    CertSendUserCustomize getCertSendUserByUserId(Integer userId);

    /**
     * 修改国家互联网应急中心已上送用户表
     * @param request
     */
    void updateCertUser(CertUser request);

    /**
     * 根据borrowNid userId查询
     * @param userId
     * @param borrowNid
     */
    List<CertUser> getCertUserByUserIdBorrowNid(Integer userId, String borrowNid);

    /**
     * userId查询
     * @param userId
     * @return
     */
    List<CertUser> getCertUserByUserId(Integer userId);

    /**
     * 根据用户哈希值查询是否已经上报过了
     * @param userIdcardHash
     * @return
     */
    List<CertUser> getCertUserByUserIdcardHash(String userIdcardHash);
}
