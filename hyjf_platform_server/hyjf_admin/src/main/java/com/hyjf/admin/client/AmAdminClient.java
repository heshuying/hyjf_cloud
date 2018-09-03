package com.hyjf.admin.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.STZHWhiteListRequestBean;
import com.hyjf.admin.beans.request.SmsCodeRequestBean;
import com.hyjf.admin.beans.request.WhereaboutsPageRequestBean;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.config.WhereaboutsPageResponse;
import com.hyjf.am.response.trade.STZHWhiteListResponse;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.resquest.trade.CorpOpenAccountRecordRequest;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.vo.admin.*;
import com.hyjf.am.vo.admin.promotion.channel.ChannelCustomizeVO;
import com.hyjf.am.vo.admin.promotion.channel.UtmChannelVO;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsDetailVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.user.*;

import java.util.List;
import java.util.Map;

/**
 * @author tanyy
 * @version AmAdminClient, v0.1 2018/8/19 12:44
 */
public interface AmAdminClient {

    /**
     * count PC统计明细列表
     *
     * @return
     */
     IntegerResponse countList(ChannelStatisticsDetailRequest request);


    /**
     * 根据条件查询PC统计明细
     *
     * @param request
     * @return
     */
    ChannelStatisticsDetailResponse searchAction(ChannelStatisticsDetailRequest request);

    /**
     * 获取app渠道列表
     */
    public List<UtmPlatVO> getPCUtm();

    /**
     * 获取用户/机构信息
     * @param requestBean
     * @return
     */
    STZHWhiteListResponse getUserByUserName(STZHWhiteListRequestBean requestBean);

    /**
     * 还款方式下拉列表
     *
     * @param
     * @return
     * @author wangjun
     * yangchangwei 迁移至amadminClient
     */
    List<BorrowStyleVO> selectCommonBorrowStyleList();


    /**
     * 查询汇计划转让列表
     *
     * @param request
     * @return
     * yangchangwei
     */
    HjhDebtCreditReponse queryHjhDebtCreditList(HjhDebtCreditListRequest request);

    /**
     * 查询手续费分账count
     * @auth sunpeikai
     * @param
     * @return
     */
    int getPoundageCount(PoundageListRequest request);
    /**
     * 查询手续费分账list
     * @auth sunpeikai
     * @param
     * @return
     */
    List<PoundageCustomizeVO> searchPoundageList(PoundageListRequest request);
}
