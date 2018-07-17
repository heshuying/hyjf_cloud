/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.auto.UtmReg;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.admin.promotion.channel.UtmChannelVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author sunss
 * @Date 2018/6/23 11:08
 */
public interface UtmRegCustomizeMapper {

    /**
     * 更新huiyingdai_utm_reg相应的用户信息
     *
     * @param params
     */
    void updateFirstUtmReg(Map<String, Object> params);

    /**
     * 获取数据
     * @param map 查询参数
     * @return List<UtmPlat>
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
    List<UtmPlatVO> getUtmPlat(Map<String,Object> map);

    /**
     * @Author walter.limeng
     * @Description  获取Utm对象
     * @Date 15:58 2018/7/14
     * @Param utmId
     * @return
     */
    UtmChannelVO getUtmByUtmId(String utmId);

	/**
	 * 查询访问数
	 * 
	 * @param sourceId 账户推广平台
	 * @return
	 */
	Integer getAccessNumber(@Param("sourceId") Integer sourceId, @Param("dayStart") String dayStart,
			@Param("dayEnd") String dayEnd);

	/**
	 * 查询注册数量
	 * 
	 * @param sourceId 账户推广平台
	 * @return
	 */
	Integer getRegistNumber(@Param("sourceId") Integer sourceId, @Param("dayStart") String dayStart,
			@Param("dayEnd") String dayEnd);

	/**
	 * 查询开户数量
	 * 
	 * @param sourceId 账户推广平台
	 * @return
	 */
	Integer getOpenAccountNumber(@Param("sourceId") Integer sourceId, @Param("dayStart") String dayStart,
			@Param("dayEnd") String dayEnd);

	List<UtmReg> getUtmRegList(Integer sourceId);
}
