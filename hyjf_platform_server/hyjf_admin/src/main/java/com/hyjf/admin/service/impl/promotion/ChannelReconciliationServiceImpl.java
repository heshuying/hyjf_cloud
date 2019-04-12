/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl.promotion;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.promotion.ChannelReconciliationService;
import com.hyjf.am.response.Response;
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
 * @author yinhui
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

        if(request.getInvestStartTime() == null || request.getInvestEndTime() == null ){
            ChannelReconciliationResponse response = new ChannelReconciliationResponse();
            response.setRtn(Response.FAIL);
            response.setMessage("出借时间不能为空");
            return response;
        }

        // 出借信息
        if (request.getUtmPlat() != null && request.getUtmPlat().length == 1) {
            request.setSourceId(request.getUtmPlat()[0]);
        }
        ChannelReconciliationResponse response = amAdminClient.selectAppChannelReconciliationRecord(request);
        return response;
    }

    @Override
    public Integer searchAppActionCount(ChannelReconciliationRequest request) {
        // 出借信息
        if (request.getUtmPlat() != null && request.getUtmPlat().length == 1) {
            request.setSourceId(request.getUtmPlat()[0]);
        }
        ChannelReconciliationResponse response = amAdminClient.selectAppChannelReconciliationCount(request);
        return response.getCount();
    }
    @Override
    public ChannelReconciliationResponse searchAppHJHAction(ChannelReconciliationRequest request) {

        if(request.getInvestStartTime() == null || request.getInvestEndTime() == null ){
            ChannelReconciliationResponse response = new ChannelReconciliationResponse();
            response.setRtn(Response.FAIL);
            response.setMessage("出借时间不能为空");
            return response;
        }

        // 出借信息
        if (request.getUtmPlat() != null && request.getUtmPlat().length == 1) {
            request.setSourceId(request.getUtmPlat()[0]);
        }
        ChannelReconciliationResponse response = amAdminClient.selectAppChannelReconciliationRecordHjh(request);
        return response;
    }

    @Override
    public Integer searchAppHJHCount(ChannelReconciliationRequest request) {
        // 出借信息
        if (request.getUtmPlat() != null && request.getUtmPlat().length == 1) {
            request.setSourceId(request.getUtmPlat()[0]);
        }
        // 投资信息
        ChannelReconciliationResponse response = amAdminClient.selectAppChannelReconciliationRecordHjhCount(request);
        if (response != null) {
            return response.getCount();
        }
        return 0;
    }

    @Override
    public List<Integer> searchUserIdList(int sourceType) {
        return amAdminClient.searchUserIdList(sourceType);
    }
}
