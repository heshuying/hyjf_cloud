/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.resquest.admin.ChannelReconciliationRequest;
import com.hyjf.am.user.dao.model.auto.UtmReg;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.admin.promotion.channel.ChannelReconciliationVO;
import com.hyjf.am.vo.admin.promotion.channel.UtmChannelVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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
    UtmChannelVO getUtmByUtmId(@Param("utmId") String utmId);

	/**
	 * @Author walter.limeng
	 * @Description  根据source_id获取Utm对象
	 * @Date 15:58 2018/7/14
	 * @Param utmId
	 * @return
	 */
	UtmChannelVO getUtmBySourceId(@Param("sourceId") String sourceId);

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
	 * @param type 0无主单1有主单
	 * @return
	 */
	Integer getOpenAccountNumber(@Param("sourceId") Integer sourceId, @Param("dayStart") String dayStart,
			@Param("dayEnd") String dayEnd, @Param("type") String type);

	List<UtmReg> getUtmRegList(@Param("sourceId") Integer sourceId, @Param("type") String type);

	/**
	 * 查询相应的app渠道主单注册数
	 * @param list
	 * @param dayStart
	 * @param dayEnd
	 * @return
	 */
	BigDecimal getRegisterAttrCount(@Param("list") List<Integer> list, @Param("dayStart") String dayStart,
			@Param("dayEnd") String dayEnd);

	/**
	 * 查询相应的渠道开户数
	 * @param list
	 * @param dayStart
	 * @param dayEnd
	 * @param type 0:pc 1:微信 2:安卓 3:ios
	 * @return
	 */
	Integer getAccountNumber(@Param("list") List<Integer> list, @Param("dayStart") String dayStart,
			@Param("dayEnd") String dayEnd, @Param("type") String type);

	/**
	 * 查询pc统计明细散标
	 * @param request
	 * @return
	 */
    List<ChannelReconciliationVO> selectPcChannelReconciliationRecord(ChannelReconciliationRequest request);

	/**
	 * 查询pc统计明细计划
	 * @param request
	 * @return
	 */
	List<ChannelReconciliationVO> selectPcChannelReconciliationRecordHjh(ChannelReconciliationRequest request);

	/**
	 * 查询app统计明细散标
	 * @param request
	 * @return
	 */
    List<ChannelReconciliationVO> selectAppChannelReconciliationRecord(ChannelReconciliationRequest request);

	/**
	 * 查询app统计明细计划
	 * @param request
	 * @return
	 */
	List<ChannelReconciliationVO> selectAppChannelReconciliationRecordHjh(ChannelReconciliationRequest request);

	/**
	 * 查询app统计明细计划数量
	 * @param request
	 * @return
	 */
	Integer selectAppChannelReconciliationRecordHjhCount(ChannelReconciliationRequest request);

    /**
     * 查询pc统计明细散标数量
     * @param request
     * @return
     */
	Integer selectPcChannelReconciliationRecordCount(ChannelReconciliationRequest request);

    /**
     * 查询pc统计明细计划数量
     * @param request
     * @return
     */
    Integer selectPcChannelReconciliationRecordHjhCount(ChannelReconciliationRequest request);

    /**
     * 查询app统计明细数量
     * @param request
     * @return
     */
    Integer selectAppChannelReconciliationRecordCount(ChannelReconciliationRequest request);

	/**
	 * 根据条件查询符合条件的sourceId集合
	 * @param sourceType
	 * @return
	 */
	List<Integer> searchUserIdList(@Param("sourceType")int sourceType);

	/**
	 * 插入pc渠道信息
	 * @param utmReg
	 * @return
	 */
	void insertPcUtmReg(UtmReg utmReg);

	/**
	 * 修改pc渠道信息
	 * @param utmReg
	 * @return
	 */
	void updatePcUtmReg(UtmReg utmReg);
}
