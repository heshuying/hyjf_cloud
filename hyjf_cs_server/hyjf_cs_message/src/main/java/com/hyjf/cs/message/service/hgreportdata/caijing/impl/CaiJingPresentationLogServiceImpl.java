/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.hgreportdata.caijing.impl;

import com.hyjf.am.resquest.admin.CaiJingLogRequest;
import com.hyjf.am.vo.admin.CaiJingPresentationLogVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.hgreportdata.caijing.CaiJingPresentationLog;
import com.hyjf.cs.message.mongo.hgreportdata.caijing.CaiJingPresentationLogDao;
import com.hyjf.cs.message.service.hgreportdata.caijing.CaiJingPresentationLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author yaoyong
 * @version CaiJingPresentationLogServiceImpl, v0.1 2019/6/6 17:17
 */
@Service
public class CaiJingPresentationLogServiceImpl implements CaiJingPresentationLogService {

    @Autowired
    private CaiJingPresentationLogDao presentationLogDao;

    @Override
    public boolean insertLog(CaiJingPresentationLog presentationLog) {
        presentationLog.setPresentationTime(GetDate.getNowTime10());
        presentationLog.setCreateTime(GetDate.getNowTime10());
        this.presentationLogDao.insert(presentationLog);
        return true;
    }

    @Override
    public List<CaiJingPresentationLogVO> queryCaiJingLog(CaiJingLogRequest request) {
        List<CaiJingPresentationLog> logs = presentationLogDao.queryCaiJingLog(request);
        if (!CollectionUtils.isEmpty(logs)) {
            List<CaiJingPresentationLogVO> logVOS = CommonUtils.convertBeanList(logs, CaiJingPresentationLogVO.class);
            return logVOS;
        }
        return null;
    }

    @Override
    public int selectLogCount(CaiJingLogRequest request) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(request.getPresentationTimeStart()) && StringUtils.isNotBlank(request.getPresentationTimeEnd())) {
            Date startDate = GetDate.stringToDate2(request.getPresentationTimeStart());
            Date endDate = GetDate.stringToDate2(request.getPresentationTimeEnd());
            criteria.and("presentationTime").gte(GetDate.getSearchStartTime(startDate))
                    .lte(GetDate.getSearchEndTime(endDate));
        }
        if (StringUtils.isNotBlank(request.getLogType())) {
            criteria.and("logType").is(request.getLogType());
        }
        if (request.getStatus() != null) {
            criteria.and("status").is(request.getStatus());
        }
        query.addCriteria(criteria);
        return presentationLogDao.count(query).intValue();
    }

    @Override
    public void deleteLog(CaiJingLogRequest request) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(request.getPresentationTimeStart()) && StringUtils.isNotBlank(request.getPresentationTimeEnd())) {
            criteria.and("startDate").is(request.getPresentationTimeStart());
            criteria.and("endDate").is(request.getPresentationTimeEnd());
        }
        if (StringUtils.isNotBlank(request.getLogType())) {
            criteria.and("logType").is(request.getLogType());
        }
        query.addCriteria(criteria);
        presentationLogDao.del(query);
    }
}
