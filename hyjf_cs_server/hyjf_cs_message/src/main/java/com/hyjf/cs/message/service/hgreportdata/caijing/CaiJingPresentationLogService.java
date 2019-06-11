/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.hgreportdata.caijing;

import com.hyjf.am.resquest.admin.CaiJingLogRequest;
import com.hyjf.am.vo.admin.CaiJingPresentationLogVO;
import com.hyjf.cs.message.bean.hgreportdata.caijing.CaiJingPresentationLog;

import java.util.List;

/**
 * @author yaoyong
 * @version CaiJingPresentationLogService, v0.1 2019/6/6 17:16
 */
public interface CaiJingPresentationLogService {

    boolean insertLog(CaiJingPresentationLog presentationLog);

    List<CaiJingPresentationLogVO> queryCaiJingLog(CaiJingLogRequest request);

    int selectLogCount(CaiJingLogRequest request);
}
