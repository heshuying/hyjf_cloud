package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.ProtocolClient;
import com.hyjf.am.response.MapResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminProtocolResponse;
import com.hyjf.am.resquest.admin.AdminProtocolRequest;
import com.hyjf.am.vo.admin.ProtocolTemplateCommonVO;
import com.hyjf.am.vo.admin.ProtocolVersionVO;
import com.hyjf.am.vo.trade.ProtocolTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

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

    @Override
    public List<ProtocolTemplateVO> getNewInfo() {
        ResponseEntity<Response<ProtocolTemplateVO>> response =
                restTemplate.exchange("http://AM-TRADE/am-trade/protocol/getnewinfo", HttpMethod.GET,
                        null, new ParameterizedTypeReference<Response<ProtocolTemplateVO>>() {});

        List<ProtocolTemplateVO> vo = null;
        if(response.getBody().getResultList().size() > 0){

           vo =  response.getBody().getResultList();
        }
        return vo;
    }

    @Override
    public ProtocolVersionVO byIdProtocolVersion(Integer id) {
        ResponseEntity<Response<ProtocolVersionVO>> response =
                restTemplate.exchange("http://AM-TRADE/am-trade/protocol/byIdProtocolVersion/"+id, HttpMethod.GET
                        ,null, new ParameterizedTypeReference<Response<ProtocolVersionVO>>() {});

        return response.getBody().getResult();
    }

    @Override
    public ProtocolTemplateVO byIdTemplateBy(String protocolId) {
        ResponseEntity<Response<ProtocolTemplateVO>> response =
                restTemplate.exchange("http://AM-TRADE/am-trade/protocol/byIdTemplateBy/"+protocolId,HttpMethod.GET
                        ,null, new ParameterizedTypeReference<Response<ProtocolTemplateVO>>() {});

        return response.getBody().getResult();
    }

    @Override
    public int getProtocolVersionSize(AdminProtocolRequest adminProtocolRequest) {
        AdminProtocolResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/protocol/getProtocolVersionSize",
                adminProtocolRequest, AdminProtocolResponse.class);
        return response.getCount();
    }

    @Override
    public boolean startUseExistProtocol(AdminProtocolRequest adminProtocolRequest) {

        AdminProtocolResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/protocol/startuseexistprotocol",
                adminProtocolRequest, AdminProtocolResponse.class);
        if(response.getRtn() == Response.SUCCESS){
            return true;
        }

        return false;
    }

    @Override
    public Map<String, Object> validatorFieldCheckClient(AdminProtocolRequest adminProtocolRequest) {

        MapResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/protocol/validatorfieldcheck",
                adminProtocolRequest, MapResponse.class);

        return response.getResultMap();
    }
}
