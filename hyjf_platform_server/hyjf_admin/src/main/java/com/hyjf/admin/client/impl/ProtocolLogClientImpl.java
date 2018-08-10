package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.ProtocolLogClient;
import com.hyjf.am.response.admin.ProtocolLogResponse;
import com.hyjf.am.resquest.admin.ProtocolLogRequest;
import com.hyjf.am.vo.admin.ProtocolLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/10  14:33
 */
@Service
public class ProtocolLogClientImpl implements ProtocolLogClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Integer countRecordLog(ProtocolLogRequest request) {
        ProtocolLogResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/protocol/countRecordLog",
                request, ProtocolLogResponse.class);

        return response.getCount();
    }

    @Override
    public List<ProtocolLogVO> getProtocolLogVOAll(ProtocolLogRequest request) {
        ProtocolLogResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/protocol/getProtocolLogVOAll",
                request, ProtocolLogResponse.class);

        return response.getResultList();
    }
}
