package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.trade.TenderCityCountResponse;
import com.hyjf.am.resquest.trade.TenderCityCountRequest;
import com.hyjf.am.vo.trade.TenderCityCountVO;
import com.hyjf.am.vo.trade.TenderSexCountVO;
import com.hyjf.am.response.config.SmsConfigResponse;
import com.hyjf.am.response.trade.DataSearchCustomizeResponse;
import com.hyjf.am.resquest.admin.SmsConfigRequest;
import com.hyjf.am.resquest.trade.DataSearchRequest;
import com.hyjf.cs.market.client.AmAdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/9/1  13:51
 */
@Service
public class AmAdminClientImpl implements AmAdminClient{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<TenderCityCountVO> getTenderCityGroupBy(Date lastDay) {
        TenderCityCountRequest request = new TenderCityCountRequest();
        request.setCreateTime(lastDay);
        TenderCityCountResponse response = restTemplate.postForObject("http://AM-ADMIN/am-admin/tendercitycount/gettendercitygroupby",
                request,TenderCityCountResponse.class);
        if (response != null) {
            return response.getListTenderCityCountVO();
        }
        return null;
    }

    @Override
    public List<TenderSexCountVO> getTenderSexGroupBy(Date date) {
        TenderCityCountRequest request = new TenderCityCountRequest();
        request.setCreateTime(date);
        TenderCityCountResponse response = restTemplate.postForObject("http://AM-ADMIN/am-admin/tendercitycount/gettendersexgroupby",
                request,TenderCityCountResponse.class);
        if (response != null) {
            return response.getListTenderSexCountVO();
        }
        return null;
    }

    @Override
    public int getTenderAgeByRange(Date date, int firstAge, int endAge) {
        TenderCityCountRequest request = new TenderCityCountRequest();
        request.setCreateTime(date);
        request.setFirstAge(firstAge);
        request.setEndAge(endAge);
        TenderCityCountResponse response = restTemplate.postForObject("http://AM-ADMIN/am-admin/tendercitycount/gettenderagebyrange",
                request,TenderCityCountResponse.class);
        if (response != null) {
            return response.getAge();
        }
        return 0;
    /**
     * 查询千乐散标数据
     * @param dataSearchRequest
     * @return
     */
    @Override
    public DataSearchCustomizeResponse querySanList(DataSearchRequest dataSearchRequest) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/qianle/querysanlist", dataSearchRequest, DataSearchCustomizeResponse.class).getBody();
    }
    /**
     * 查询千乐计划数据
     * @param dataSearchRequest
     * @return
     */
    @Override
    public DataSearchCustomizeResponse queryPlanList(DataSearchRequest dataSearchRequest) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/qianle/queryPlanList", dataSearchRequest, DataSearchCustomizeResponse.class).getBody();

    }
    /**
     * 查询千乐全部数据
     * @param dataSearchRequest
     * @return
     */
    @Override
    public DataSearchCustomizeResponse queryQianleList(DataSearchRequest dataSearchRequest) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/qianle/queryList", dataSearchRequest, DataSearchCustomizeResponse.class).getBody();

    }

    /**
     * 查询短信加固数据
     *
     * @param request
     * @return
     * @author xiehuili
     */
    @Override
    public SmsConfigResponse initSmsConfig(SmsConfigRequest request) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-config/smsConfig/initSmsConfig", request, SmsConfigResponse.class).getBody();
    }

}
