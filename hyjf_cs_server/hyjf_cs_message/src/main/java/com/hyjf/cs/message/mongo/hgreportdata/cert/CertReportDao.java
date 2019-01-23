/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mongo.hgreportdata.cert;

import com.hyjf.cs.message.bean.hgreportdata.cert.CertReportEntity;
import com.hyjf.cs.message.mongo.ic.BaseMongoDao;
import org.springframework.stereotype.Repository;

/**
 * 国家互联网应急中心    CERT 用户信息上报
 * @author sss
 * @version BaseHgDataReportEntity, v0.1 2018/6/27 10:06
 */
@Repository
public class CertReportDao extends BaseMongoDao<CertReportEntity> {
    @Override
    protected Class<CertReportEntity> getEntityClass() {
        return CertReportEntity.class;
    }
}
