package com.hyjf.cs.trade.client;


import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;

/**
 * 项目业务client
 *
 * @author zhangyk
 * @date 2018/6/26 11:18
 */
public interface AmProjectClient {

    ProjectCustomeDetailVO selectProjectDetail(String borrowNid);
}
