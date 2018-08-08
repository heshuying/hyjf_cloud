package com.hyjf.am.user.service.admin.promotion;

import com.hyjf.am.user.dao.model.auto.Utm;
import com.hyjf.am.user.dao.model.auto.UtmPlat;
import com.hyjf.am.user.service.BaseService;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.admin.promotion.channel.ChannelCustomizeVO;
import com.hyjf.am.vo.admin.promotion.channel.UtmChannelVO;
import com.hyjf.am.vo.user.UtmPlatVO;

import java.util.List;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
public interface UtmService extends BaseService {
    /**
     * 分页获取数据
     * @param map 查询参数
     * @return List<Utm>
     */
    List<UtmVO> getByPageList(Map<String,Object> map);

    /**
     * 根据条件获取总条数
     * @param map 查询参数
     * @return Integer
     */
    Integer getCountByParam(Map<String,Object> map);

    /**
     * @Author walter.limeng
     * @Description  取pc渠道
     * @Date 15:57 2018/7/14
     * @Param sourceId
     * @return
     */
    List<UtmPlatVO> getUtmPlat(String sourceId);

    /**
     * @Author walter.limeng
     * @Description  获取Utm对象
     * @Date 15:58 2018/7/14
     * @Param utmId
     * @return
     */
    UtmChannelVO getUtmByUtmId(String utmId);

    /**
     * @Author walter.limeng
     * @Description  新增或者修改对象
     * @Date 10:11 2018/7/16
     * @Param channelCustomizeVO
     * @return
     */
    Utm insertOrUpdateUtm(ChannelCustomizeVO channelCustomizeVO);

    /**
     * @Author walter.limeng
     * @Description  删除对象
     * @Date 10:29 2018/7/16
     * @Param channelCustomizeVO
     * @return
     */
    Utm deleteUtm(ChannelCustomizeVO channelCustomizeVO);

    /**
     * @Author walter.limeng
     * @Description  根据主键ID查询对象
     * @Date 10:55 2018/7/16
     * @Param id
     * @return
     */
    UtmPlatVO getUtmPlatById(Integer id);

    /**
     * @Author walter.limeng
     * @Description  根据sourceName和sourceId查询总数
     * @Date 11:21 2018/7/16
     * @Param sourceName
     * @Param sourceId
     * @return
     */
    Integer sourceNameIsExists(String sourceName, Integer sourceId);

    /**
     * @Author walter.limeng
     * @Description  新增或修改UtmPlat对象
     * @Date 11:31 2018/7/16
     * @Param utmPlatVO
     * @return
     */
    UtmPlat insertOrUpdateUtmPlat(UtmPlatVO utmPlatVO);

    /**
     * @Author walter.limeng
     * @Description  删除utmPlat对象
     * @Date 11:49 2018/7/16
     * @Param utmPlatVO
     * @return
     */
    UtmPlat deleteUtmPlat(UtmPlatVO utmPlatVO);

    /**
     * 查询访问数
     * @param sourceId 账户推广平台
     * @return
     */
    Integer getAccessNumber(Integer sourceId);

    /**
     * 查询注册数量
     * @param sourceId 账户推广平台
     * @return
     */
    Integer getRegistNumber(Integer sourceId);

    /**
     * 查询开户数量
     * @param sourceId 账户推广平台
     * @return
     */
    Integer getOpenAccountNumber(Integer sourceId, String type);
}
