/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.beans.request.ContentLinksRequestBean;
import com.hyjf.admin.client.ContentLinsClinet;
import com.hyjf.am.response.config.LinkResponse;
import com.hyjf.am.vo.config.LinkVO;
import org.springframework.stereotype.Service;

/**
 * @author fuqiang
 * @version ContentLinsClinetImpl, v0.1 2018/7/14 14:06
 */
@Service
public class ContentLinsClinetImpl implements ContentLinsClinet {
    @Override
    public LinkResponse searchAction(ContentLinksRequestBean requestBean) {
        return null;
    }

    @Override
    public LinkResponse insertAction(ContentLinksRequestBean requestBean) {
        return null;
    }

    @Override
    public LinkResponse updateAction(ContentLinksRequestBean requestBean) {
        return null;
    }

    @Override
    public LinkVO getRecord(Integer id) {
        return null;
    }

    @Override
    public LinkResponse deleteById(Integer id) {
        return null;
    }
}
