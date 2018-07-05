package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.ContentArticleResponse;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.cs.trade.client.ContentArticleClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ContentArticleClientImpl implements ContentArticleClient {


    @Autowired
    RestTemplate restTemplate;


    @Override
    public List<ContentArticleVO> searchContentArticleList(ContentArticleRequest request) {
        ContentArticleResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/article/contentArticleList",request,ContentArticleResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }
}
