package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.vo.config.ContentArticleVO;

import java.util.List;

/**
 * 公司动态client
 * @author zhangyk
 * @date 2018/7/5 9:31
 */
public interface ContentArticleClient {

    List<ContentArticleVO> searchContentArticleList(ContentArticleRequest request);
}
