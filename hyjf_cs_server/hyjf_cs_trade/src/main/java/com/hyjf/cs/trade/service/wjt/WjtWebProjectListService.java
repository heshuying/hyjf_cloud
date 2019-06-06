package com.hyjf.cs.trade.service.wjt;

import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.cs.common.bean.result.WebResult;

/**
 * @author pangchengchao
 * @version WjtWebProjectListService,  v0.1  2019/5/14  13:44
 * @description 类说明
 */
public interface WjtWebProjectListService {
    /**
     * 获取温金投Web端项目列表
     * @param request
     * @author liuyang
     * @return
     */
    WebResult searchWjtWebProjectListNew(ProjectListRequest request);
}
