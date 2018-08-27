/**
 * Description:汇转让WEB接口DAO
 * Copyright: Copyright (HYJF Corporation) 2016
 * Company: HYJF Corporation
 * @author: 朱晓东
 * @version: 1.0
 * Created at: 2015年03月24日 下午18:35:00
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;
import java.util.Map;

import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.trade.dao.model.customize.AppAlreadyRepayListCustomize;
import com.hyjf.am.trade.dao.model.customize.AppMyPlanCustomize;
import com.hyjf.am.trade.dao.model.customize.AppRepayCalendarCustomize;
import com.hyjf.am.trade.dao.model.customize.AppTenderCreditRecordListCustomize;
import com.hyjf.am.trade.dao.model.customize.CurrentHoldObligatoryRightListCustomize;
import com.hyjf.am.trade.dao.model.customize.CurrentHoldPlanListCustomize;
import com.hyjf.am.trade.dao.model.customize.RecentPaymentListCustomize;
import com.hyjf.am.trade.dao.model.customize.RepayMentListCustomize;
import com.hyjf.am.trade.dao.model.customize.RepayMentPlanListCustomize;
import com.hyjf.am.trade.dao.model.customize.TenderCreditDetailCustomize;
import com.hyjf.am.trade.dao.model.customize.WechatRepayMentListCustomize;


public interface AssetManageCustomizeMapper {

    /**
     *
     * 获取近期回款列表
     * @author pcc
     * @param paraMap
     * @return
     */
    List<RecentPaymentListCustomize> selectRecentPaymentList(Map<String, Object> paraMap);
    /**
     * @Description 获取用户当前持有债权列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    List<CurrentHoldObligatoryRightListCustomize> selectCurrentHoldObligatoryRightList(Map<String,Object> params);
    /**
     * @Description 获取用户当前持有债权列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    int selectCurrentHoldObligatoryRightListTotal(Map<String, Object> params);
    /**
     * @Description 获取用户已回款债权列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    int selectRepaymentListTotal(Map<String, Object> params);
    /**
     * @Description 获取用户转让列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    int countCreditRecordTotal(Map<String, Object> params);
    /**
     * @Description 获取用户已回款债权列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    List<RepayMentListCustomize> selectRepaymentList(Map<String, Object> params);

    /**
     * 查询用户转让记录
     * @param params
     * @return
     */
    List<TenderCreditDetailCustomize> selectCreditRecordList(Map<String,Object> params);
    /**
     * @Description 获取用户当前持有计划列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date  
     */
    List<CurrentHoldPlanListCustomize> selectCurrentHoldPlanList(Map<String,Object> params);
    /**
     * @Description 获取用户当前持有计划列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    int countCurrentHoldPlanTotal(Map<String, Object> params);
    /**
     * @Description 获取用户已回款计划列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    List<RepayMentPlanListCustomize> selectRepayMentPlanList(Map<String,Object> params);
    /**
     * @Description 获取用户已回款计划列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    int countRepayMentPlanTotal(Map<String, Object> params);
    /**
     * @Description 微信端用户已回款列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    List<WechatRepayMentListCustomize> selectWechatRepaymentList(Map<String,Object> mapParameter);

    List<AppAlreadyRepayListCustomize> selectAppAlreadyRepayList(AssetManageBeanRequest request);

    List<AppTenderCreditRecordListCustomize> searchAppCreditRecordList(AssetManageBeanRequest request);

    int selectTenderToCreditListCount(AssetManageBeanRequest request);

    List<AppMyPlanCustomize> selectAppMyPlanList(AssetManageBeanRequest request);

    int countAppMyPlan(AssetManageBeanRequest request);

    /**
     * 查询回款日历总数
     * @param params
     * @return
     */
    Integer countRepaymentCalendar(Map<String, Object> params);

    /**
     * 查询回款日历明细
     * @param params
     * @return
     */
    List<AppRepayCalendarCustomize> selectRepaymentCalendar(Map<String, Object> params);

    /**
     * 返回用户最近回款时间戳-秒
     * @param params
     * @return
     */
    Integer selectNearlyRepaymentTime(Map<String, Object> params);
}
