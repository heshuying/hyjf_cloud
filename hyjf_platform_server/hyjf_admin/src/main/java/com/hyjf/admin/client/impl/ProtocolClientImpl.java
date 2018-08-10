package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.ProtocolClient;
import com.hyjf.am.response.admin.AdminProtocolResponse;
import com.hyjf.am.resquest.admin.AdminProtocolRequest;
import com.hyjf.am.vo.admin.ProtocolTemplateCommonVO;
import com.hyjf.am.vo.trade.ProtocolTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/8  16:50
 */
@Service
public class ProtocolClientImpl implements ProtocolClient {

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Integer countRecord(AdminProtocolRequest request) {
        AdminProtocolResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/protocol/countRecord",
                request, AdminProtocolResponse.class);

        return response.getCount();
    }

    @Override
    public List<ProtocolTemplateCommonVO> getRecordList(AdminProtocolRequest request) {
        AdminProtocolResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/protocol/getRecordList",
                request, AdminProtocolResponse.class);

        return response.getResultList();
    }

    @Override
    public ProtocolTemplateCommonVO getProtocolTemplateById(AdminProtocolRequest request) {
        AdminProtocolResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/protocol/getProtocolTemplateById",
                request, AdminProtocolResponse.class);
        return response.getResult();
    }

    @Override
    public Integer getProtocolTemplateNum(AdminProtocolRequest request) {
        AdminProtocolResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/protocol/getProtocolTemplateNum",
                request, AdminProtocolResponse.class);

        return response.getCount();
    }

    @Override
    public ProtocolTemplateVO getProtocolTemplateByProtocolName(AdminProtocolRequest request) {
        AdminProtocolResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/protocol/getProtocolTemplateByProtocolName",
                request, AdminProtocolResponse.class);
        ProtocolTemplateCommonVO vo = response.getResult();
        return vo.getProtocolTemplateVO();
    }

    @Override
    public Integer insert(AdminProtocolRequest request) {
        AdminProtocolResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/protocol/insert",
                request, AdminProtocolResponse.class);

        return response.getCount();
    }

    @Override
    public Integer updateProtocolTemplate(AdminProtocolRequest request) {
        AdminProtocolResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/protocol/updateProtocolTemplate",
                request, AdminProtocolResponse.class);

        return response.getCount();
    }

    @Override
    public Integer updateDisplayFlag(AdminProtocolRequest request) {
        AdminProtocolResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/protocol/updateDisplayFlag",
                request, AdminProtocolResponse.class);

        return response.getCount();
    }

    @Override
    public AdminProtocolResponse deleteProtocolTemplate(AdminProtocolRequest request) {
        AdminProtocolResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/protocol/deleteProtocolTemplate",
                request, AdminProtocolResponse.class);

        return response;
    }
}
