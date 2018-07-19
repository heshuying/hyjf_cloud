package com.hyjf.admin.service.impl.promotion.channel;

import com.hyjf.admin.client.UtmClient;
import com.hyjf.admin.service.promotion.channel.ChannelService;
import com.hyjf.am.vo.admin.promotion.channel.ChannelCustomizeVO;
import com.hyjf.am.vo.admin.promotion.channel.UtmChannelVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/14 10:47
 * @Description: ChannelServiceImpl
 */
@Service
public class ChannelServiceImpl implements ChannelService {
    private Logger logger = LoggerFactory.getLogger(ChannelServiceImpl.class);
    @Autowired
    private UtmClient utmClient;

    @Override
    public Integer countList(ChannelCustomizeVO channelCustomizeVO) {
        return utmClient.getChannelCount(channelCustomizeVO);
    }

    @Override
    public List<ChannelCustomizeVO> getByPageList(ChannelCustomizeVO channelCustomizeVO) {
        return utmClient.getChannelList(channelCustomizeVO);
    }

    @Override
    public List<UtmPlatVO> getUtmPlat(String sourceId) {
        return utmClient.getUtmPlat(sourceId);
    }

    @Override
    public UtmChannelVO getRecord(String utmId) {
        return utmClient.getRecord(utmId);
    }

    @Override
    public UserVO getUser(String utmReferrer, String userId) {
        return utmClient.getUser(utmReferrer, userId);
    }

    @Override
    public boolean insertOrUpdateUtm(ChannelCustomizeVO channelCustomizeVO) {
        return utmClient.insertOrUpdateUtm(channelCustomizeVO);
    }

    @Override
    public boolean deleteAction(ChannelCustomizeVO channelCustomizeVO) {
        return utmClient.deleteAction(channelCustomizeVO);
    }
}