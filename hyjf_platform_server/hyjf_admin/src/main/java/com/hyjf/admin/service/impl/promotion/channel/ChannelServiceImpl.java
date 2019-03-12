package com.hyjf.admin.service.impl.promotion.channel;

import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.promotion.channel.ChannelService;
import com.hyjf.am.response.user.UtmPlatResponse;
import com.hyjf.am.vo.admin.promotion.channel.ChannelCustomizeVO;
import com.hyjf.am.vo.admin.promotion.channel.UtmChannelVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/14 10:47
 * @Description: ChannelServiceImpl
 */
@Service
public class ChannelServiceImpl implements ChannelService {
    private Logger logger = LoggerFactory.getLogger(ChannelServiceImpl.class);

    @Value("${file.domain.url}")
    private String FILEDOMAILURL;
    @Value("${file.physical.path}")
    private String FILEPHYSICALPATH;
    @Value("${file.upload.activity.img.path}")
    private String FILEUPLOADTEMPPATH;
    @Autowired
    private AmUserClient amUserClient;

    @Override
    public Integer countList(ChannelCustomizeVO channelCustomizeVO) {
        return amUserClient.getChannelCount(channelCustomizeVO);
    }

    @Override
    public List<ChannelCustomizeVO> getByPageList(ChannelCustomizeVO channelCustomizeVO) {
        return amUserClient.getChannelList(channelCustomizeVO);
    }

    @Override
    public List<UtmPlatVO> getUtmPlat() {
        return amUserClient.getUtmPlat();
    }

    @Override
    public UtmChannelVO getRecord(String utmId) {
        return amUserClient.getRecord(utmId);
    }

    @Override
    public UserVO getUser(String utmReferrer, String userId) {
        return amUserClient.getUser(utmReferrer, userId);
    }
    @Override
    public  UserVO getUserByUserId(int userId){
        return amUserClient.getUserByUserId(userId);

    }


    @Override
    public boolean insertOrUpdateUtm(ChannelCustomizeVO channelCustomizeVO) {
        return amUserClient.insertOrUpdateUtm(channelCustomizeVO);
    }

    @Override
    public boolean deleteAction(ChannelCustomizeVO channelCustomizeVO) {
        return amUserClient.deleteAction(channelCustomizeVO);
    }

    @Override
    public List<UtmPlatVO> getAllUtmPlat(Map<String, Object> map) {
        UtmPlatResponse utmPlatResponse = amUserClient.getAllUtmPlat(map);
        List<UtmPlatVO> utmPlatVOList = new ArrayList<UtmPlatVO>();
        if(null != utmPlatResponse){
            utmPlatVOList = utmPlatResponse.getResultList();
        }
        return utmPlatVOList;
    }

    @Override
    public void insertUtmList(List<ChannelCustomizeVO> voList) {
        amUserClient.insertUtmList(voList);
    }

    @Override
    public boolean getBySourceIdAndTerm(String utmId,String sourceId, String utmTerm) {
        boolean flag = false;
        Integer total = amUserClient.getBySourceIdAndTerm(utmId,sourceId,utmTerm);
        if(total != null && total > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public UserVO getUserByUserName(String utmReferrer) {
        UserVO user = amUserClient.getUserByUserName(utmReferrer);
        if(user != null){
            return user;
        }
        return null;
    }
}
