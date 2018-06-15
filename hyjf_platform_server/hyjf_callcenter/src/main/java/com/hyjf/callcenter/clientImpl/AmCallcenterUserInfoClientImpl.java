package com.hyjf.callcenter.clientImpl;

import java.util.List;
import java.util.Map;

import com.hyjf.am.resquest.callcenter.CallCenterServiceUsersRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.callcenter.CallCenterUserBaseResponse;
import com.hyjf.am.resquest.callcenter.CallCenterUserInfoRequest;
import com.hyjf.am.vo.callcenter.CallCenterUserBaseVO;
import com.hyjf.callcenter.beans.customizebean.CallcenterUserBaseCustomize;
import com.hyjf.callcenter.client.AmCallcenterUserInfoClient;
import com.hyjf.ribbon.EurekaInvokeClient;

/**
 * @author libin
 * @version AmCallcenterUserInfoClientImpl, v0.1 2018/6/6 10:03
 */
@Service
public class AmCallcenterUserInfoClientImpl implements AmCallcenterUserInfoClient {
    private RestTemplate restTemplate = EurekaInvokeClient.getInstance().buildRestTemplate();

    @Override
    public List<CallCenterUserBaseVO> selectNoServiceFuTouUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest) {
        CallCenterUserBaseResponse callCenterUserBaseResponse = restTemplate
                .postForEntity("http://AM-USER//am-user/callcenter/getNoServiceFuTouUsersList/",callCenterUserInfoRequest, CallCenterUserBaseResponse.class)
                .getBody();
        if (callCenterUserBaseResponse != null) {
            return callCenterUserBaseResponse.getResultList();
        }
        return null;
    }

    @Override
    public List<CallCenterUserBaseVO> selectNoServiceLiuShiUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest) {
        CallCenterUserBaseResponse callCenterUserBaseResponse = restTemplate
                .postForEntity("http://AM-USER//am-user/callcenter/getNoServiceLiuShiUsersList/",callCenterUserInfoRequest, CallCenterUserBaseResponse.class)
                .getBody();
        if (callCenterUserBaseResponse != null) {
            return callCenterUserBaseResponse.getResultList();
        }
        return null;
    }

    @Override
    public List<CallCenterUserBaseVO> selectNoServiceUsersList(CallCenterUserInfoRequest callCenterUserInfoRequest) {
        CallCenterUserBaseResponse callCenterUserBaseResponse = restTemplate
                .postForEntity("http://AM-USER//am-user/callcenter/getNoServiceUsersList/",callCenterUserInfoRequest, CallCenterUserBaseResponse.class)
                .getBody();
        if (callCenterUserBaseResponse != null) {
            return callCenterUserBaseResponse.getResultList();
        }
        return null;
    }

    @Override
    public Integer executeRecord(CallCenterServiceUsersRequest callCenterServiceUsersRequest){
        Integer response = restTemplate
                .postForEntity("http://AM-USER//am-user/callcenter/executeRecord/",callCenterServiceUsersRequest, Integer.class)
                .getBody();
        return response;
    }

    @Override
    public List<CallCenterUserBaseVO> selectUserList(CallCenterUserInfoRequest callCenterUserInfoRequest) {
        CallCenterUserBaseResponse callCenterUserBaseResponse = restTemplate
                .postForEntity("http://AM-USER//am-user/callcenter/getBasicUsersList/",callCenterUserInfoRequest, CallCenterUserBaseResponse.class)
                .getBody();
        if (callCenterUserBaseResponse != null) {
            return callCenterUserBaseResponse.getResultList();
        }
        return null;
    }

    @Override
    public List<CallCenterUserBaseVO> selectUserDetailById(CallCenterUserInfoRequest callCenterUserInfoRequest) {
        CallCenterUserBaseResponse callCenterUserBaseResponse = restTemplate
                .postForEntity("http://AM-USER//am-user/callcenter/getUserDetailById/",callCenterUserInfoRequest, CallCenterUserBaseResponse.class)
                .getBody();
        if (callCenterUserBaseResponse != null) {
            return callCenterUserBaseResponse.getResultList();
        }
        return null;
    }
}
