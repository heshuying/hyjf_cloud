package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.market.InvitePrizeConfResponse;
import com.hyjf.am.resquest.admin.ReturnCashRequest;
import com.hyjf.am.resquest.trade.InvitePrizeConfVO;
import com.hyjf.cs.trade.client.AmMarketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/16 19:21
 * @Description: InvitePrizeConfigClientImpl
 */
@Service
public class AmMarketClientImpl implements AmMarketClient {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<InvitePrizeConfVO> getListByGroupCode(String groupCode) {
        String url = "http://AM-MARKET/am-market/inviteprizeconfig/getlistbygroupcode/"+groupCode;
        InvitePrizeConfResponse response = restTemplate.getForEntity(url,InvitePrizeConfResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public void  updateJoinTime(String borrowNid,Integer nowTime){
        String url = "http://AM-ADMIN/am-market/returncash/updatejointime/"+borrowNid+"/"+nowTime;
         restTemplate.getForEntity(url,String.class);

    }
    @Override
    public StringResponse checkActivityIfAvailable(Integer activityId){
        String url = "http://AM-ADMIN/am-market/activity/checkActivityIfAvailable/"+activityId;
        StringResponse response = restTemplate.getForEntity(url,StringResponse.class).getBody();
        return response;
    }
    @Override
    public void saveReturnCash(ReturnCashRequest returnCashRequest){
        String url = "http://AM-ADMIN/am-market/returncash/saveReturnCash";
        restTemplate.postForEntity(url,returnCashRequest,String.class);
    }

}
