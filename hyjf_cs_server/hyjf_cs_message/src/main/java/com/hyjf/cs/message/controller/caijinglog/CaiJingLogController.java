/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.caijinglog;

import com.hyjf.am.response.admin.CaiJingLogResponse;
import com.hyjf.am.resquest.admin.CaiJingLogRequest;
import com.hyjf.am.vo.admin.CaiJingPresentationLogVO;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.service.hgreportdata.caijing.CaiJingPresentationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author yaoyong
 * @version CaiJingLogController, v0.1 2019/6/10 9:57
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/dataCenter")
public class CaiJingLogController extends BaseController {

    @Autowired
    private CaiJingPresentationLogService presentationLogService;
    /**
     * 查询01财经日志记录
     * @param request
     * @return
     */
    @RequestMapping("/caiJingLog")
    public CaiJingLogResponse queryCaiJingLog(@RequestBody CaiJingLogRequest request) {
        CaiJingLogResponse response = new CaiJingLogResponse();
        List<CaiJingPresentationLogVO> list = presentationLogService.queryCaiJingLog(request);
        if (!CollectionUtils.isEmpty(list)) {
            response.setResultList(list);
        }
        int logCount = presentationLogService.selectLogCount(request);
        response.setLogCount(logCount);
        return response;
    }
}
