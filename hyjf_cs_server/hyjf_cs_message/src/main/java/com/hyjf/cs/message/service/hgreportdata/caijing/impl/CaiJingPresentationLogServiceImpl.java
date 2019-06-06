/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.hgreportdata.caijing.impl;

import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.hgreportdata.caijing.CaiJingPresentationLog;
import com.hyjf.cs.message.mongo.hgreportdata.caijing.CaiJingPresentationLogDao;
import com.hyjf.cs.message.service.hgreportdata.caijing.CaiJingPresentationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
