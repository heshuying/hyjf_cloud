package com.hyjf.cs.trade.service.home;

import javax.servlet.http.HttpServletResponse;

import com.hyjf.cs.common.bean.result.WebResult;

/**
 * Web首页service
 * @author zhangyk
 * @date 2018/7/4 13:49
 */
public interface WebHomeService {



    /**
     * 获取首页数据
     * @author zhangyk
     * @date 2018/7/4 13:52
     */
    WebResult getHomeData(String userId);

    /**
     * 安卓下载
     * @author zhangyk
     * @date 2018/9/5 11:37
     */
    void androidDownload(HttpServletResponse response);
}
