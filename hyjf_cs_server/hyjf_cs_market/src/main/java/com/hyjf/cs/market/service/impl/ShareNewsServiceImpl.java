package com.hyjf.cs.market.service.impl;

import com.hyjf.am.vo.market.ShareNewsBeanVO;
import com.hyjf.cs.market.client.AmConfigClient;
import com.hyjf.cs.market.service.ShareNewsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/26 17:04
 * @Description: ShareNewsServiceImpl
 */
@Service
public class ShareNewsServiceImpl implements ShareNewsService {
    @Resource
    private AmConfigClient amConfigClient;
    @Override
    public ShareNewsBeanVO queryShareNews() {
        return amConfigClient.queryShareNews();
    }
}
