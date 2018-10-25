/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl.promotion;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.promotion.ChannelReconciliationService;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.admin.promotion.ChannelReconciliationResponse;
import com.hyjf.am.resquest.admin.ChannelReconciliationRequest;
import com.hyjf.am.vo.admin.UtmVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fq
 * @version ChannelReconciliationServiceImpl, v0.1 2018/9/21 9:57
 */
@Service
public class ChannelReconciliationServiceImpl implements ChannelReconciliationService {
    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    private AmAdminClient amAdminClient;

    @Override
    public List<UtmVO> searchUtmList(int sourceType) {
        Map<String, Object> map = new HashMap<>();
        map.put("sourceType", sourceType);
        UtmResponse response = amUserClient.getByPageList(map);
        if (response != null) {
            return response.getResultListS();
        }
        return null;
    }

    @Override
    public ChannelReconciliationResponse searchAction(ChannelReconciliationRequest request) {
        return amAdminClient.selectPcChannelReconciliationRecord(request);
    }

    @Override
    public ChannelReconciliationResponse searchHJHAction(ChannelReconciliationRequest request) {
        return amAdminClient.selectPcChannelReconciliationRecordHjh(request);
    }

    @Override
    public ChannelReconciliationResponse searchAppAction(ChannelReconciliationRequest request) {
        return amAdminClient.selectAppChannelReconciliationRecord(request);
    }

    @Override
    public ChannelReconciliationResponse searchAppHJHAction(ChannelReconciliationRequest request) {
        return amAdminClient.selectAppChannelReconciliationRecordHjh(request);
    }
}
