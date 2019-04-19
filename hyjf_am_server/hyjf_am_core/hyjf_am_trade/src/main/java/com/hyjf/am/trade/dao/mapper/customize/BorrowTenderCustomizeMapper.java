/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.vo.trade.borrow.BorrowTenderCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditCustomizeVO;
import com.hyjf.am.vo.trade.wrb.WrbTenderNotifyCustomizeVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author fuqiang
 * @version BorrowTenderCustomizeMapper, v0.1 2018/7/17 9:45
 */
public interface BorrowTenderCustomizeMapper {
	/**
	 * 获取utm注册用户出借次数
	 * @param list utm注册用户userid集合
	 * @param dayStart
	 * @param dayEnd
	 * @param type
	 * @return
	 */
	Integer getUtmTenderNum(@Param("list") List<Integer> list, @Param("dayStart") String dayStart, @Param("dayEnd") String dayEnd, @Param("type") String type);

	/**
	 * 获取utm注册用户HZT出借额
	 * @param list utm注册用户userid集合
	 * @param dayStart
	 * @param dayEnd
	 * @return
	 */
	BigDecimal getHztTenderPrice(@Param("list") List<Integer> list, @Param("dayStart") String dayStart,
			@Param("dayEnd") String dayEnd);

	/**
	 * 获取utm注册用户HXF出借额
	 * @param list utm注册用户userid集合
	 * @param dayStart
	 * @param dayEnd
	 * @return
	 */
	BigDecimal getHxfTenderPrice(@Param("list") List<Integer> list, @Param("dayStart") String dayStart,
			@Param("dayEnd") String dayEnd);

	/**
	 * 获取utm注册用户HXF出借额
	 * @param list utm注册用户userid集合
	 * @param dayStart
	 * @param dayEnd
	 * @return
	 */
	BigDecimal getRtbTenderPrice(@Param("list") List<Integer> list, @Param("dayStart") String dayStart,
			@Param("dayEnd") String dayEnd);

	/**
	 * @Author walter.limeng
	 * @Description  根据订单编号取得该订单的还款列表
	 * @Date 17:38 2018/7/17
	 * @Param paramMap
	 * @return
	 */
    CouponRecoverCustomizeVO getCurrentCouponRecover(Map<String,Object> paramMap);

    /**
     * @Author walter.limeng
     * @Description  风车理财根据出借订单号查询出借信息
     * @Date 14:25 2018/7/20
     * @Param paramMap
     * @return
     */
	WrbTenderNotifyCustomizeVO searchBorrowTenderByNid(Map<String,Object> paramMap);

	List<WrbTenderNotifyCustomizeVO> getBorrowTenderByAddtime(@Param("sourceIdSrch")String sourceIdSrch,@Param("timeStartSrch") String dayStart,
															  @Param("timeEndSrch") String dayEnd);

	List<WrbTenderNotifyCustomizeVO> getCreditTenderByAddtime(@Param("timeStartSrch") String dayStart,
															  @Param("timeEndSrch") String dayEnd);

	List<WrbTenderNotifyCustomizeVO> getAccountRechargeByAddtime(@Param("timeStartSrch") String dayStart,
																 @Param("timeEndSrch") String dayEnd);

	List<WrbTenderNotifyCustomizeVO> getBorrowTenderByClient(@Param("sourceIdSrch") String sourceIdSrch,@Param("timeStartSrch") String dayStart,
															 @Param("timeEndSrch") String dayEnd);

	List<WrbTenderNotifyCustomizeVO> getProductListByClient(@Param("sourceIdSrch") String sourceIdSrch,@Param("timeStartSrch") String dayStart,
															@Param("timeEndSrch") String dayEnd);

	List<WrbTenderNotifyCustomizeVO> getDebtPlanAccedeByClient(@Param("sourceIdSrch") String sourceIdSrch,@Param("timeStartSrch") String dayStart,
															   @Param("timeEndSrch") String dayEnd);

	List<WrbTenderNotifyCustomizeVO> getCreditTenderByClient(@Param("sourceIdSrch") String sourceIdSrch,@Param("timeStartSrch") String dayStart,
															 @Param("timeEndSrch") String dayEnd);

	// 应急中心二期，产品配置历史数据上报 add by nxl start
	/**
	 * @description 查找 未还款，转让被部分被承接的债权信息
	 * @auth nxl
	 * @param
	 * @return
	 */
	List<HjhDebtCreditCustomizeVO> getHjhDebtCreditInfoCustomize();
	/**
	 * @description 查找未还款的债权信息
	 * @auth nxl
	 * @param
	 * @return
	 */
	List<BorrowTenderCustomizeVO> getBorrowTenderInfoCustomize();
	// 应急中心二期，产品配置历史数据上报 add by nxl end

}
