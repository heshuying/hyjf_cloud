/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.ContentLinksService;
import com.hyjf.am.response.config.LinkResponse;
import com.hyjf.am.resquest.admin.ContentLinksRequest;
import com.hyjf.am.vo.config.LinkVO;
import com.hyjf.common.paginator.Paginator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fuqiang
 * @version ContentLinksServiceImpl, v0.1 2018/7/13 10:51
 */
@Service
public class ContentLinksServiceImpl implements ContentLinksService {
    @Autowired
    private AmConfigClient amConfigClient;

    @Override
    public LinkResponse searchAction(ContentLinksRequest requestBean) {
        LinkResponse response = new LinkResponse();
        // 友情连接为1
        requestBean.setType(1);
        requestBean.setLimitStart(-1);
        requestBean.setLimitEnd(-1);

        List<LinkVO> recordList = amConfigClient.searchActions(requestBean);
        response.setCount(recordList.size());
        if (recordList != null) {
            Paginator paginator = new Paginator(requestBean.getCurrPage(), recordList.size(),requestBean.getPageSize()==0?10:requestBean.getPageSize());
            requestBean.setLimitStart(paginator.getOffset());
            requestBean.setLimitEnd(paginator.getLimit());
            recordList = amConfigClient.searchActions(requestBean);
            response.setResultList(recordList);
        }

        return response;
    }

    @Override
    public LinkResponse insertAction(ContentLinksRequest requestBean) {
        return amConfigClient.insertActions(requestBean);
    }

    @Override
    public LinkResponse infoInfoAction(ContentLinksRequest requestBean) {
        return amConfigClient.infoInfoAction(requestBean.getId());
    }

    @Override
    public LinkResponse updateAction(ContentLinksRequest requestBean) {
        return amConfigClient.updateActions(requestBean);
    }

    @Override
    public LinkResponse updateStatus(ContentLinksRequest requestBean) {
        Integer id = requestBean.getId();
        LinkVO record = amConfigClient.getLinkRecord(id);
        if (record.getStatus() == 1) {
            record.setStatus(0);
        } else if (record.getStatus() == 0) {
            record.setStatus(1);
        }
        BeanUtils.copyProperties(record, requestBean);
        return amConfigClient.updateActions(requestBean);
    }

    @Override
    public LinkResponse deleteById(Integer id) {
        return amConfigClient.deleteLinkById(id);
    }
}
