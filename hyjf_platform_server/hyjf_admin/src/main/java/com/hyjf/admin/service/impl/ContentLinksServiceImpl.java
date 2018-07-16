/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.request.ContentLinksRequestBean;
import com.hyjf.admin.client.ContentLinsClinet;
import com.hyjf.admin.service.ContentLinksService;
import com.hyjf.am.response.config.LinkResponse;
import com.hyjf.am.vo.config.LinkVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fuqiang
 * @version ContentLinksServiceImpl, v0.1 2018/7/13 10:51
 */
@Service
public class ContentLinksServiceImpl implements ContentLinksService {
    @Autowired
    private ContentLinsClinet contentLinsClinet;

    @Override
    public LinkResponse searchAction(ContentLinksRequestBean requestBean) {
        return contentLinsClinet.searchAction(requestBean);
    }

    @Override
    public LinkResponse insertAction(ContentLinksRequestBean requestBean) {
        return contentLinsClinet.insertAction(requestBean);
    }

    @Override
    public LinkResponse updateAction(ContentLinksRequestBean requestBean) {
        return contentLinsClinet.updateAction(requestBean);
    }

    @Override
    public LinkResponse updateStatus(ContentLinksRequestBean requestBean) {
        Integer id = requestBean.getId();
        LinkVO record = contentLinsClinet.getRecord(id);
        if (record.getStatus() == 1) {
            record.setStatus(0);
        } else if (record.getStatus() == 0) {
            record.setStatus(1);
        }
        BeanUtils.copyProperties(record, requestBean);
        return contentLinsClinet.updateAction(requestBean);
    }

    @Override
    public LinkResponse deleteById(Integer id) {
        return contentLinsClinet.deleteById(id);
    }
}
