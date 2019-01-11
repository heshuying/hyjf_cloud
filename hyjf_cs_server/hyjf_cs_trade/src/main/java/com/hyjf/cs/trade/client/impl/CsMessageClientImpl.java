/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.ChinapnrExclusiveLogWithBLOBsResponse;
import com.hyjf.am.response.trade.ChinapnrLogResponse;
import com.hyjf.am.vo.trade.ChinapnrExclusiveLogWithBLOBsVO;
import com.hyjf.am.vo.trade.ChinapnrLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.admin.AccountWebListResponse;
import com.hyjf.am.response.app.AppUtmRegResponse;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.cs.trade.client.CsMessageClient;

import java.util.List;

/**
 * @author yaoyong
 * @version CsMessageClientImpl, v0.1 2018/8/14 19:46
 */
@Service
public class CsMessageClientImpl implements CsMessageClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Integer insertAccountWebList(AccountWebListVO accountWebList) {
        String url = "http://CS-MESSAGE/cs-message/accountweblist/insertaccountweblist";
        AccountWebListResponse response = restTemplate.postForEntity(url,accountWebList,AccountWebListResponse.class).getBody();
        if (response != null) {
            return response.getRecordTotal();
        }
        return null;
    }
    /**
     * 根据userId查询用户渠道信息
     *
     * @param userId
     * @return
     */
    @Override
    public AppUtmRegVO getAppChannelStatisticsDetailByUserId(Integer userId) {
        AppUtmRegResponse response = restTemplate.getForEntity(
                "http://AM-USER/am-user/app_utm_reg/findByUserId/" + userId,
                AppUtmRegResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public ChinapnrExclusiveLogWithBLOBsVO selectChinapnrExclusiveLog(long id) {
        String url = "http://CS-MESSAGE/cs-message/chinapnr/selectChinapnrExclusiveLog/"+id ;
        ChinapnrExclusiveLogWithBLOBsResponse response = restTemplate.getForEntity(url,ChinapnrExclusiveLogWithBLOBsResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public void updateChinapnrExclusiveLogStatus(long uuid, String status) {
        String url = "http://CS-MESSAGE/cs-message/chinapnr/updateChinapnrExclusiveLogStatus/"+uuid+"/"+ status;
        restTemplate.getForEntity(url,IntegerResponse.class).getBody();
    }

    @Override
    public int updateChinapnrExclusiveLog(ChinapnrExclusiveLogWithBLOBsVO record) {
        IntegerResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/chinapnr/updateChinapnrExclusiveLog", record, IntegerResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultInt();
        }
        return 0;
    }



    @Override
    public List<ChinapnrLogVO> getChinapnrLog(String ordId) {
        String url = "http://CS-MESSAGE/cs-message/chinapnr/getChinapnrLog/"+ordId ;
        ChinapnrLogResponse response = restTemplate.getForEntity(url,ChinapnrLogResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }
}
