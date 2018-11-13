package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.WrbTenderNotifyResponse;
import com.hyjf.am.response.config.SmsConfigResponse;
import com.hyjf.am.response.trade.DataSearchCustomizeResponse;
import com.hyjf.am.response.trade.TenderCityCountResponse;
import com.hyjf.am.response.user.UtmPlatResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.resquest.admin.SmsConfigRequest;
import com.hyjf.am.resquest.datacollect.TzjDayReportRequest;
import com.hyjf.am.resquest.trade.DataSearchRequest;
import com.hyjf.am.resquest.trade.TenderCityCountRequest;
import com.hyjf.am.vo.trade.TenderCityCountVO;
import com.hyjf.am.vo.trade.TenderSexCountVO;
import com.hyjf.am.vo.trade.wrb.WrbTenderNotifyCustomizeVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.cs.market.client.AmAdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
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

    @Override
    public List<Integer> getUsersInfoList() {
        IntegerResponse response = restTemplate
                .getForObject("http://AM-ADMIN/am-user/utmplat/getUsersInfoList", IntegerResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<Integer> getUsersList(String source) {
        IntegerResponse response = restTemplate
                .getForObject("http://AM-ADMIN/am-user/utmplat/getUsersList/"+source, IntegerResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     *  汇直投投资总额
     */
    @Override
    public List<WrbTenderNotifyCustomizeVO> getBorrowTenderByAddtime(AppChannelStatisticsRequest request) {
        WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-ADMIN/am-trade/borrowTender/getBorrowTenderByAddtime",
                request,WrbTenderNotifyResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     *  汇转让投资总额
     */
    @Override
    public List<WrbTenderNotifyCustomizeVO> getCreditTenderByAddtime(AppChannelStatisticsRequest request) {
        WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-ADMIN/am-trade/borrowTender/getCreditTenderByAddtime",
                request,WrbTenderNotifyResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     *  app渠道用户充值金额
     */
    @Override
    public List<WrbTenderNotifyCustomizeVO> getAccountRechargeByAddtime(AppChannelStatisticsRequest request) {
        WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-ADMIN/am-trade/borrowTender/getAccountRechargeByAddtime",
                request,WrbTenderNotifyResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     *  1
     */
    @Override
    public List<WrbTenderNotifyCustomizeVO> getBorrowTenderByClient(AppChannelStatisticsRequest request) {
        WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-ADMIN/am-trade/borrowTender/getBorrowTenderByClient",
                request,WrbTenderNotifyResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     *  2
     */
    @Override
    public List<WrbTenderNotifyCustomizeVO> getProductListByClient(AppChannelStatisticsRequest request) {
        WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-ADMIN/am-trade/borrowTender/getProductListByClient",
                request,WrbTenderNotifyResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     *  3
     */
    @Override
    public List<WrbTenderNotifyCustomizeVO> getDebtPlanAccedeByClient(AppChannelStatisticsRequest request) {
        WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-ADMIN/am-trade/borrowTender/getDebtPlanAccedeByClient",
                request,WrbTenderNotifyResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     *  4
     */
    @Override
    public List<WrbTenderNotifyCustomizeVO> getCreditTenderByClient(AppChannelStatisticsRequest request) {
        WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-ADMIN/am-trade/borrowTender/getCreditTenderByClient",
                request,WrbTenderNotifyResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
}
