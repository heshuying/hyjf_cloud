package com.hyjf.am.user.service.front.ca;


import com.hyjf.am.resquest.user.CertificateAuthorityExceptionRequest;
import com.hyjf.am.user.dao.model.auto.CertificateAuthority;
import com.hyjf.am.user.service.BaseService;
import com.hyjf.common.exception.MQException;

import java.text.ParseException;
import java.util.List;

/**
 * CA认证异常处理Service
 * @author dongzeshan
 */
public interface CertificateAuthorityExceptionService extends BaseService {
    /**
     * 检索CA异常列表数量
     * @param form
     * @return
     */
    Integer countCAExceptionList(CertificateAuthorityExceptionRequest form);

    /**
     * 检索CA异常列表
     * @param form
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<CertificateAuthority> getCAExceptionList(CertificateAuthorityExceptionRequest form,int limitStart,int limitEnd);

    /**
     * 发送CA认证MQ
     * @param userId
     */
    void updateUserCAMQ(int userId) throws ParseException, MQException;;
}
