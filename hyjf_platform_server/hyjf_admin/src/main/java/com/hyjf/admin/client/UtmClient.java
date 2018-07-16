package com.hyjf.admin.client;

import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.vo.admin.promotion.channel.ChannelCustomizeVO;
import com.hyjf.am.vo.admin.promotion.channel.UtmChannelVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmPlatVO;

import java.util.List;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
public interface UtmClient {
    /**
     * 获取数据
     * @param map 查询参数
     * @return UtmResultResponse
     */
    UtmResponse getByPageList(Map<String,Object> map);
    /**
     * 获取数据总条数
     * @param map 查询参数
     * @return UtmResultResponse
     */
    UtmResponse getCountByParam(Map<String,Object> map);

    /**
     * @Author walter.limeng
     * @Description  根据条件查询推广总数
     * @Date 11:16 2018/7/14
     * @Param channelCustomizeVO
     * @return Integer
     */
    Integer getChannelCount(ChannelCustomizeVO channelCustomizeVO);

    /**
     * @Author walter.limeng
     * @Description  分页查询推广数据
     * @Date 11:16 2018/7/14
     * @Param channelCustomizeVO
     * @return List<ChannelCustomizeVO>
     */
    List<ChannelCustomizeVO> getChannelList(ChannelCustomizeVO channelCustomizeVO);

    /**
     * @Author walter.limeng
     * @Description  取pc渠道
     * @Date 15:14 2018/7/14
     * @Param
     * @return
     */
    List<UtmPlatVO> getUtmPlat(String sourceId);

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
     * @Description  新增或者更新对象
     * @Date 10:04 2018/7/16
     * @Param channelCustomizeVO
     * @return
     */
    boolean insertOrUpdateUtm(ChannelCustomizeVO channelCustomizeVO);

    /**
     * @Author walter.limeng
     * @Description  删除对象
     * @Date 10:28 2018/7/16
     * @Param channelCustomizeVO
     * @return
     */
    boolean deleteAction(ChannelCustomizeVO channelCustomizeVO);

    /**
     * @Author walter.limeng
     * @Description  根据主键ID获取Utm对象
     * @Date 10:48 2018/7/16
     * @Param id
     * @return 
     */
    UtmPlatVO getDataById(Integer id);

    /**
     * @Author walter.limeng
     * @Description  根据sourceName和sourceId验证是否重复
     * @Date 11:15 2018/7/16
     * @Param sourceName
     * @Param sourceId
     * @return
     */
    int sourceNameIsExists(String sourceName, Integer sourceId);

    /**
     * @Author walter.limeng
     * @Description  新增或者修改对象
     * @Date 11:24 2018/7/16
     * @Param utmPlatVO
     * @return
     */
    boolean insertOrUpdateUtmPlat(UtmPlatVO utmPlatVO);

    /**
     * @Author walter.limeng
     * @Description  删除utmplat对象
     * @Date 11:46 2018/7/16
     * @Param utmPlatVO
     * @return 
     */
    boolean utmClientdeleteUtmPlatAction(UtmPlatVO utmPlatVO);
}
