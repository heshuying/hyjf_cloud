package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.trade.DadaCenterCouponCustomizeRequest;
import com.hyjf.am.trade.dao.model.customize.CouponConfigCustomizeV2;
import com.hyjf.am.trade.dao.model.customize.CouponUserCustomize;
import com.hyjf.am.trade.dao.model.customize.CouponUserForAppCustomize;
import com.hyjf.am.trade.dao.model.customize.DataCenterCouponCustomize;

import java.util.List;
import java.util.Map;

public interface CouponUserCustomizeMapper {

	/**
	 * 获取优惠券列表
	 * @param CouponConfigCustomize
	 * @return
	 */
	List<CouponUserCustomize> selectCouponUserList(Map<String,Object> paraMap);
	
	/**
	 * 
	 * 获取优惠券列表(App)
	 * @author hsy
	 * @param paraMap
	 * @return
	 */
	List<CouponUserForAppCustomize> selectCouponUserListForApp(Map<String,Object> paraMap);
	
	/**
	 * 获取优惠券列表记录数
	 * 
	 * @param CouponConfigCustomize
	 * @return
	 */
	Integer countCouponUser(Map<String,Object> paraMap);
	
    /**
     * 获取优惠券列表记录数(App)
     * 
     * @param CouponConfigCustomize
     * @return
     */
	
	Integer countCouponUserForApp(Map<String,Object> paraMap);
	
	int getCouponUsedCount(int couponId);

	
	/**
	 * 根据用户优惠券表（coupon_user）编号，取得对应的优惠券信息
	 * @param paramMap
	 * @return
	 */
	CouponConfigCustomizeV2 selectCouponConfigByGrantId(Map<String,Object> paramMap);
	/**
     * 根据优惠券投资nid（hyjf_borrow_tender_cpn）
     * @param paramMap
     * @return
     */
    CouponConfigCustomizeV2 selectCouponConfigByNid(Map<String,Object> paramMap);
	
	/**
	 * 
	 * 统计用户可用的优惠券个数
	 */
	Integer countCouponValid(Integer userId);
	
	/**
	 * 查询用户可用的优惠券列表
	 */
	List<CouponUserCustomize> selectLatestCouponValidList(Integer userId);
	
	/**
	 * 
	 * 查询有效未读的优惠券列表
	 * @author hsy
	 * @param userId
	 * @return
	 */
	List<CouponUserCustomize> selectLatestCouponValidUNReadList(Integer userId);

	/**
	 * 获取加息券列表
	 * @param request
	 * @return
	 */
    List<DataCenterCouponCustomize> selectDataCenterCouponJXList(DadaCenterCouponCustomizeRequest request);

	/**
	 * 获取代金券列表
	 * @param request
	 * @return
	 */
	List<DataCenterCouponCustomize> selectDataCenterCouponDJList(DadaCenterCouponCustomizeRequest request);
}