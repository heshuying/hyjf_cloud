/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl.cert;

import com.hyjf.am.config.dao.mapper.auto.CertLogMapper;
import com.hyjf.am.config.dao.model.auto.CertLog;
import com.hyjf.am.config.dao.model.auto.CertLogExample;
import com.hyjf.am.config.service.cert.CertService;
import com.hyjf.am.resquest.admin.CertReportLogRequestBean;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 合规数据上报 CERT 应急中心上报记录
 */
@Service
public class CertServiceImpl implements CertService {
    @Autowired
    protected CertLogMapper certLogMapper;

    /**
     * 上报日志数量
     *
     * @param form
     * @return
     */
    @Override
    public int selectCertReportLogListCount(CertReportLogRequestBean form) {
        CertLogExample example = new CertLogExample();
        CertLogExample.Criteria creteria = example.createCriteria();
        if (null != form) {
            if (StringUtils.isNotEmpty(form.getSendStartTimeStr())) {
                Integer startTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(form.getSendStartTimeStr()+" 00:00:00");
                creteria.andSendTimeGreaterThanOrEqualTo(startTime);
            }
            if (StringUtils.isNotEmpty(form.getSendEndtTimeStr())) {
                Integer endTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(form.getSendStartTimeStr()+" 23:59:59");
                creteria.andSendTimeLessThanOrEqualTo(endTime);
            }
            if (form.getInfType() != null && form.getInfType().intValue() > 0) {
                creteria.andInfTypeEqualTo(form.getInfType());
            }
            if (form.getSendStatus() != null && form.getSendStatus().intValue() > 0) {
                creteria.andSendStatusEqualTo(form.getSendStatus());
            }
            if (StringUtils.isNotEmpty(form.getLogOrdId())) {
                creteria.andLogOrdIdEqualTo(form.getLogOrdId());
            }
            // 对账状态
            if (form.getQueryStatus()!=null && form.getQueryStatus().intValue()>=0) {
                creteria.andQueryResultEqualTo(form.getQueryStatus());
            }
        }
        return certLogMapper.countByExample(example);
    }

    /**
     * 上报日志
     *
     * @param form
     * @return
     */
    @Override
    public List<CertLog> selectCertReportLogList(CertReportLogRequestBean form) {
        CertLogExample example = new CertLogExample();
        CertLogExample.Criteria creteria = example.createCriteria();
        if (null != form) {
            if (StringUtils.isNotEmpty(form.getSendStartTimeStr())) {
                Integer startTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(form.getSendStartTimeStr()+" 00:00:00");
                creteria.andSendTimeGreaterThanOrEqualTo(startTime);
            }
            if (StringUtils.isNotEmpty(form.getSendEndtTimeStr())) {
                Integer endTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(form.getSendStartTimeStr()+" 23:59:59");
                creteria.andSendTimeLessThanOrEqualTo(endTime);
            }
            if (form.getInfType() != null && form.getInfType().intValue() > 0) {
                creteria.andInfTypeEqualTo(form.getInfType());
            }
            if (form.getSendStatus() != null && form.getSendStatus().intValue() >= 0) {
                creteria.andSendStatusEqualTo(form.getSendStatus());
            }
            if (StringUtils.isNotEmpty(form.getLogOrdId())) {
                creteria.andLogOrdIdEqualTo(form.getLogOrdId());
            }
            // 对账状态
            if (form.getQueryStatus()!=null && form.getQueryStatus().intValue()>=0) {
                creteria.andQueryResultEqualTo(form.getQueryStatus());
            }
        }
        example.setLimitStart(form.getPaginator().getOffset());
        example.setLimitEnd(form.getPaginator().getLimit());
        example.setOrderByClause(" send_time DESC");
        return certLogMapper.selectByExample(example);
    }
}
