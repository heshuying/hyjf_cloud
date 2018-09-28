package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;
import java.util.Map;

import com.hyjf.am.trade.dao.model.customize.CouponConfigCustomize;
import com.hyjf.am.trade.dao.model.customize.CouponConfigExportCustomize;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.coupon.CouponTenderCustomizeVO;

/**
 * @author yaoyong
 */
public interface CouponConfigCustomizeMapper {

	/**
	 * 获取记录数
	 * @param mapParam
	 * @return
	 */
	int countCouponConfig(Map<String, Object> mapParam);

	/**
	 * 查询列表
	 * @param mapParam
	 * @return
	 */
	List<CouponConfigCustomize> selectCouponConfigList(Map<String, Object> mapParam);

	/**
	 * 根据优惠券编号查询已发行数量
	 * @param couponCode
	 * @return
	 */
    int checkCouponSendExcess(String couponCode);

    /**
     * @Author walter.limeng
     * @Description  根据优惠券投资订单编号，取得优惠券信息
     * @Date 12:00 2018/7/17
     * @Param ordId
     * @return 
     */
    CouponConfigVO getCouponConfigByOrderId(String ordId);

	/**
	 * @Author walter.limeng
	 * @Description  更新还款期
	 * @Date 14:15 2018/7/17
	 * @Param tenderNid 投资订单编号
	 * @Param currentRecoverFlg 0:非还款期，1;还款期
	 * @Param period 期数
	 * @return
	 */
    void crRecoverPeriod(Map<String,Object> paramMap);

	/**
	 * @Author walter.limeng
	 * @Description  根据nid 取得体验金收益期限
	 * @Date 14:33 2018/7/17
	 * @Param tenderNid
	 * @return
	 */
	Integer getCouponProfitTime(Map<String,Object> paramMap);

	/**
	 * @Author walter.limeng
	 * @Description  
	 * @Date 14:58 2018/7/17
	 * @Param 
	 * @return 
	 */
    Integer updateOfLoansTenderHjh(AccountVO account);

    /**
     * @Author walter.limeng
     * @Description  根据orderId查询所有待还款优惠券
     * @Date 17:03 2018/7/17
     * @Param orderId
     * @return
     */
    List<CouponTenderCustomizeVO> getCouponTenderListHjh(Map<String,Object> paramMap);

	/**
	 * 查询导出列表
	 * @param configCustomize
	 * @return
	 */
	List<CouponConfigExportCustomize> exportCouponConfigList(CouponConfigCustomize configCustomize);
}