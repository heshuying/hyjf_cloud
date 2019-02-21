package com.hyjf.admin.service.promotion.channel;

import com.hyjf.am.vo.admin.promotion.channel.ChannelCustomizeVO;
import com.hyjf.am.vo.admin.promotion.channel.UtmChannelVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmPlatVO;

import java.util.List;
import java.util.Map;

/**
 * @Author walter.limeng
 * @Description  推广管理
 * @Date 10:45 2018/7/14
 */
public interface ChannelService {

    /**
     * @Author walter.limeng
     * @Description  根据条件查询总数
     * @Date 11:10 2018/7/14
     * @Param channelCustomizeVO
     * @return Integer
     */
    Integer countList(ChannelCustomizeVO channelCustomizeVO);

    /**
     * @Author walter.limeng
     * @Description  分页查询数据
     * @Date 11:11 2018/7/14
     * @Param channelCustomizeVO
     * @return List<ChannelCustomizeVO>
     */
    List<ChannelCustomizeVO> getByPageList(ChannelCustomizeVO channelCustomizeVO);

    /**
     * @Author walter.limeng
     * @Description  取pc渠道
     * @Date 15:14 2018/7/14
     * @Param 
     * @return 
     */
    List<UtmPlatVO> getUtmPlat();

    /**
     * @Author walter.limeng
     * @Description  获取Utm对象
     * @Date 15:14 2018/7/14
     * @Param utmId
     * @return 
     */
    UtmChannelVO getRecord(String utmId);

    /**
     * @Author walter.limeng
     * @Description  根据userId获取用户对象
     * @Date 15:15 2018/7/14
     * @Param empty
     * @Param utmReferrer
     * @return
     */
    UserVO getUser(String utmReferrer, String userId);

    /**
     * @Author walter.limeng
     * @Description  新增或者修改对象
     * @Date 10:02 2018/7/16
     * @Param channelCustomizeVO
     * @return
     */
    boolean insertOrUpdateUtm(ChannelCustomizeVO channelCustomizeVO);

    /**
     * @Author walter.limeng
     * @Description  删除信息
     * @Date 10:27 2018/7/16
     * @Param channelCustomizeVO
     * @return
     */
    boolean deleteAction(ChannelCustomizeVO channelCustomizeVO);

    /**
     * @Author walter.limeng
     * @Description  获取所有得utmPlat
     * @Date 11:50 2018/10/11
     * @Param null
     * @return
     */
    List<UtmPlatVO> getAllUtmPlat(Map<String, Object> map);

    /**
     * 新增信息
     * @param voList
     */
    void insertUtmList(List<ChannelCustomizeVO> voList);

    /**
     * @Author walter.limeng
     * @Description  根据渠道和关键字判断是否已经存在
     * @Date 15:17 2018/11/14
     * @Param sourceId
     * @Param utmTerm
     * @return
     */
    boolean getBySourceIdAndTerm(String utmId, String sourceId, String utmTerm);

    /**
     * 根据用户名查询用户
     * @param utmReferrer
     * @return
     */
    UserVO getUserByUserName(String utmReferrer);
}
