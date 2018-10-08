package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.WebPandectBorrowRecoverCustomize;
import com.hyjf.am.trade.dao.model.customize.WebPandectCreditTenderCustomize;
import com.hyjf.am.trade.dao.model.customize.WebPandectRecoverMoneyCustomize;
import com.hyjf.am.trade.dao.model.customize.WebPandectWaitMoneyCustomize;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 账户总览
 * @author Albert
 *
 */
public interface WebPandectCustomizeMapper {

	/**
	 * 债转统计
	 * @param userId
	 * @return
	 */
	public WebPandectCreditTenderCustomize queryCreditInfo(@Param("userId") Integer userId);
	
	/**债转信息
	 * 去掉已债转（r.recover_status=1）; 去掉待收已债转（r.recover_status=0）
	 * @param userId
	 * @param recoverStatus
	 * @return
	 */
	public WebPandectBorrowRecoverCustomize queryRecoverInfo(@Param("userId") Integer userId, @Param("recoverStatus") Integer recoverStatus);
	
	/**
	 * 投标冻结金额
	 * @param userId
	 * @return
	 */
	public BigDecimal queryFrostCapital(@Param("userId") Integer userId);

	/**
	 * 累计投资金额
	 * @param userId
	 * @return
	 */
	public BigDecimal queryInvestTotal(@Param("userId") Integer userId);
	/**
	 * 待收本金和 待收利息
	 * @param userId
	 * @return
	 */
	public WebPandectWaitMoneyCustomize queryWaitMoney(@Param("userId") Integer userId);
	/**
	 * 已回收本金和 已回收利息
	 * @param userId
	 * @return
	 */
	public WebPandectRecoverMoneyCustomize queryRecoverMoney(@Param("userId") Integer userId);
	
	/**
	 * 应还总额（利息+本金）
	 * @param userId
	 * @param borrowNid
	 * @return
	 */
	public BigDecimal queryRecoverAccount(@Param("userId") Integer userId, @Param("borrowNid") String borrowNid);
	/**
	 * 获取汇天利 购买明细表可赎回金额总额
	 * @param userId
	 * @return
	 */
	public BigDecimal queryHtlSumRestAmount(@Param("userId") Integer userId);
	
	
	/**
	 * 获取汇天利 总收益
	 * @param userId
	 * @return
	 */
	public BigDecimal queryHtlSumInterest(@Param("userId") Integer userId);
/**
 * 
 * @method: updateUserAuthStatus
 * @description: 	更新预约授权状态		
 *  @param params
 *  @return 
 * @return: int
* @mender: zhouxiaoshuai
 * @date:   2016年7月26日 下午3:40:12
 */
	public int updateUserAuthStatus(Map<String, Object> params);
/**
 * 
 * @method: insertAppointmentAuthLog
 * @description: 	插入预约授权记录表			
 *  @param params
 *  @return 
 * @return: int
* @mender: zhouxiaoshuai
 * @date:   2016年7月26日 下午3:55:45
 */
public int insertAppointmentAuthLog(Map<String, Object> params);

public WebPandectWaitMoneyCustomize queryWaitMoneyForRtb(Integer userId);

/**
 * 融通宝累计收益计算
 * @Title queryRecoverMoneyForRtb
 * @param userId
 * @return
 */
public WebPandectRecoverMoneyCustomize queryRecoverMoneyForRtb(Integer userId);
	
	
}






