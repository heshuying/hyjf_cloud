package com.hyjf.cs.market.client;

import com.hyjf.am.response.config.SmsConfigResponse;
import com.hyjf.am.response.trade.DataSearchCustomizeResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.resquest.admin.SmsConfigRequest;
import com.hyjf.am.resquest.trade.DataSearchRequest;
import com.hyjf.am.vo.trade.TenderCityCountVO;
import com.hyjf.am.vo.trade.TenderSexCountVO;
import com.hyjf.am.vo.trade.wrb.WrbTenderNotifyCustomizeVO;
import com.hyjf.am.vo.user.UtmPlatVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/9/1  13:51
 */
public interface AmAdminClient {

    /**
     * 查询千乐渠道散标数据
     * @return
     */
    DataSearchCustomizeResponse querySanList(DataSearchRequest dataSearchRequest);

    /**
     * 查询千乐渠道计划数据
     * @return
     */
    DataSearchCustomizeResponse queryPlanList(DataSearchRequest dataSearchRequest);
    /**
     * 查询千乐渠道全部数据
     * @return
     */
    DataSearchCustomizeResponse queryQianleList(DataSearchRequest dataSearchRequest);


    /**
     * 查询短信加固数据
     *
     * @param request
     * @return
     * @author xiehuili
     */
     SmsConfigResponse initSmsConfig(SmsConfigRequest request);

    List<Integer> getUsersInfoList();

    List<Integer> getUsersList(String source);

    List<WrbTenderNotifyCustomizeVO> getBorrowTenderByAddtime(AppChannelStatisticsRequest request);

    List<WrbTenderNotifyCustomizeVO> getCreditTenderByAddtime(AppChannelStatisticsRequest request);

    List<WrbTenderNotifyCustomizeVO> getAccountRechargeByAddtime(AppChannelStatisticsRequest request);

    List<WrbTenderNotifyCustomizeVO> getBorrowTenderByClient(AppChannelStatisticsRequest request);

    List<WrbTenderNotifyCustomizeVO> getProductListByClient(AppChannelStatisticsRequest request);

    List<WrbTenderNotifyCustomizeVO> getDebtPlanAccedeByClient(AppChannelStatisticsRequest request);

    List<WrbTenderNotifyCustomizeVO> getCreditTenderByClient(AppChannelStatisticsRequest request);

}
