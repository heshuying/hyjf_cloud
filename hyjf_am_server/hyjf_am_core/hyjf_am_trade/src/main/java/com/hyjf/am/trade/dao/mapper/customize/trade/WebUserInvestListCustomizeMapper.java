/**
 * Description:会员用户开户记录初始化列表查询
 * Copyright: (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 上午11:01:57
 * Modification History:
 * Modified by : 
 * */

package com.hyjf.am.trade.dao.mapper.customize.trade;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.customize.app.AppUserInvestListCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.WebProjectCompanyDetailCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.WebProjectConsumeListCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.WebProjectInvestListCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.WebProjectPersonDetailCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.WebProjectRepayListCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.WebUserInvestListCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.WebUserProjectListCustomize;

public interface WebUserInvestListCustomizeMapper {

	List<WebUserProjectListCustomize> selectUserProjectList(Map<String, Object> params);
	
	int countUserProjectRecordTotal(Map<String, Object> params);

	WebProjectPersonDetailCustomize selectProjectPersonDetail(@Param("borrowNid")String borrowNid);

	WebProjectCompanyDetailCustomize selectProjectCompanyDetail(@Param("borrowNid")String borrowNid);

	List<WebProjectInvestListCustomize> selectProjectInvestList(Map<String, Object> params);
	
	int countProjectInvestRecordTotal(Map<String, Object> params);
	
	List<WebProjectConsumeListCustomize> selectProjectConsumeList(Map<String, Object> params);

	int countProjectConsumeRecordTotal(Map<String, Object> params);
	
	List<WebUserInvestListCustomize> selectUserInvestList(Map<String, Object> params);
	
	int countUserInvestRecordTotal(Map<String, Object> params);

	int countProjectRepayRecordTotal(Map<String, Object> params);

	List<WebProjectRepayListCustomize> selectProjectRepayList(Map<String, Object> params);
	
	int countProjectRepayPlanRecordTotal(Map<String, Object> params);
	
	List<WebProjectRepayListCustomize> selectProjectRepayPlanList(Map<String, Object> params);
	
	List<WebProjectRepayListCustomize> selectCouponProjectRepayPlanList(Map<String, Object> params);
	
	int countCouponProjectRepayPlanRecordTotal(Map<String, Object> params);

    int countNewUserTotal(String userId);

    Integer selectUserInvestFlag(Integer userId);

    Integer updateUserInvestFlag(Integer userId);

    void updateUserInvestFlagZero(Integer userId);

	int countUserInvestNumBynid(Map<String, Object> params);
//查询用户预约相关信息
	/**
	 * 
	 * @method: selectUserAppointmentInfo
	 * @description: 			
	 *  @param userId
	 *  @return
	 * authType 授权方式：0完全授权，1部分授权
	 * authStatus 预约授权状态：0：未授权1：已授权
	 * authTime 授权操作时间
	 * recodTotal 违约累计积分
	 * recodTime  违约更新时间
	 * recodTruncateTime 积分清空时间
	 * @return: Map<String,Object>
	* @mender: zhouxiaoshuai
	 * @date:   2016年7月27日 上午11:36:35
	 */
	Map<String, Object> selectUserAppointmentInfo(String userId);

	int countProjectLoanDetailRecordTotal(Map<String, Object> params);

	List<WebProjectRepayListCustomize> selectProjectLoanDetailList(
			Map<String, Object> params);

	int countUserDebtInvestRecordTotal(Map<String, Object> params);
	/**
	 * 
	 * @method: selectUserDebtInvestList
	 * @description: 	查询专属标投资信息		
	 *  @param params
	 *  @return 
	 * @return: List<WebUserInvestListCustomize>
	* @mender: zhouxiaoshuai
	 * @date:   2016年9月29日 上午10:20:05
	 */
	List<WebUserInvestListCustomize> selectUserDebtInvestList(
			Map<String, Object> params);
	/**
	 * 
	 * @method: countDebtRepayDetailRecordTotal
	 * @description: 查询专属标计划分期			
	 *  @param params
	 *  @return 
	 * @return: int
	* @mender: zhouxiaoshuai
	 * @date:   2016年9月29日 上午10:19:49
	 */
	int countDebtLoanDetailRecordTotal(Map<String, Object> params);

	List<WebProjectRepayListCustomize> selectDebtLoanDetailList(
			Map<String, Object> params);

	List<AppUserInvestListCustomize> selectAppUserInvestList(Map<String, Object> params);
}
