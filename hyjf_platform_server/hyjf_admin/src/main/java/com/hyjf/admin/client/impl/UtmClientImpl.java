package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.UtmClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.vo.admin.promotion.channel.ChannelCustomizeVO;
import com.hyjf.am.vo.admin.promotion.channel.UtmChannelVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
@Service
public class UtmClientImpl implements UtmClient {
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public UtmResponse getByPageList(Map<String, Object> map) {
        UtmResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/promotion/utm/getbypagelist", map, UtmResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public UtmResponse getCountByParam(Map<String, Object> map) {
        UtmResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/promotion/utm/getcount", map, UtmResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    @Override
    public Integer getChannelCount(ChannelCustomizeVO channelCustomizeVO) {
        UtmResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/channel/getchannelcount", channelCustomizeVO, UtmResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getRecordTotal();
        }
        return null;
    }

    @Override
    public List<ChannelCustomizeVO> getChannelList(ChannelCustomizeVO channelCustomizeVO) {
        UtmResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/channel/getchannellist", channelCustomizeVO, UtmResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<UtmPlatVO> getUtmPlat(String sourceId) {
        UtmResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/promotion/utm/getutmplat/"+sourceId, UtmResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public UtmChannelVO getRecord(String utmId) {
        UtmResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/promotion/utm/getutmbyutmid/"+utmId, UtmResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return (UtmChannelVO)response.getResult();
        }
        return null;
    }

    @Override
    public UserVO getUser(String utmReferrer, String userId) {
        UtmResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/user/getuser/"+utmReferrer+"/"+userId, UtmResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return (UserVO)response.getResult();
        }
        return null;
    }

    @Override
    public boolean insertOrUpdateUtm(ChannelCustomizeVO channelCustomizeVO) {
        UtmResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/promotion/utm/insertorupdateutm/",channelCustomizeVO, UtmResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return true;
        }else{
            return  false;
        }
    }

    @Override
    public boolean deleteAction(ChannelCustomizeVO channelCustomizeVO) {
        UtmResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/promotion/utm/deleteutm/",channelCustomizeVO, UtmResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return true;
        }else{
            return  false;
        }
    }

    @Override
    public UtmPlatVO getDataById(Integer id) {
        UtmResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/promotion/utm/getutmbyid/"+id, UtmResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return (UtmPlatVO)response.getResult();
        }else{
            return null;
        }
    }

    @Override
    public int sourceNameIsExists(String sourceName, Integer sourceId) {
        UtmResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/promotion/utm/sourcenameisexists/"+sourceName+"/"+sourceId, UtmResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getRecordTotal();
        }else{
            return 0;
        }
    }

    @Override
    public boolean insertOrUpdateUtmPlat(UtmPlatVO utmPlatVO) {
        UtmResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/promotion/utm/insertorupdateutmplat/",utmPlatVO, UtmResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return true;
        }else{
            return  false;
        }
    }

    @Override
    public boolean utmClientdeleteUtmPlatAction(UtmPlatVO utmPlatVO) {
        UtmResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/promotion/utm/deleteutmplat/",utmPlatVO, UtmResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return true;
        }else{
            return  false;
        }
    }
}
