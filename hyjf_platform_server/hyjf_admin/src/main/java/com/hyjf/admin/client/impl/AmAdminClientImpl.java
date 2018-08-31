package com.hyjf.admin.client.impl;

import com.hyjf.admin.beans.request.STZHWhiteListRequestBean;
import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhDebtCreditReponse;
import com.hyjf.am.response.trade.BorrowStyleResponse;
import com.hyjf.am.response.trade.STZHWhiteListResponse;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.response.user.UtmPlatResponse;
import com.hyjf.am.resquest.admin.HjhDebtCreditListRequest;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author tanyy
 * @version AmAdminClientImpl, v0.1 2018/8/23 20:01
 */
@Service
public class AmAdminClientImpl implements AmAdminClient {
    private static Logger logger = LoggerFactory.getLogger(AmAdminClientImpl.class);

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public IntegerResponse countList(ChannelStatisticsDetailRequest request){
        return restTemplate.
                postForEntity("http://AM-ADMIN/am-admin/extensioncenter/channelstatisticsdetail/count", request, IntegerResponse.class).getBody();
    }
    @Override
    public ChannelStatisticsDetailResponse searchAction(ChannelStatisticsDetailRequest request){
        return restTemplate.
                postForEntity("http://AM-ADMIN/am-admin/extensioncenter/channelstatisticsdetail/searchaction", request, ChannelStatisticsDetailResponse.class).getBody();
    }
    @Override
    public List<UtmPlatVO> getPCUtm(){
        UtmPlatResponse response =  restTemplate.
                postForEntity("http://AM-ADMIN/am-admin/extensioncenter/channelstatisticsdetail/pcutm_list", null, UtmPlatResponse.class).getBody();
        if (UtmPlatResponse.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public STZHWhiteListResponse getUserByUserName(STZHWhiteListRequestBean requestBean) {
        String url = "http://AM-ADMIN/am-trade/stzfwhiteconfig/getUserByUserName";
        STZHWhiteListResponse response = restTemplate.postForEntity(url,requestBean,STZHWhiteListResponse.class).getBody();
        if (STZHWhiteListResponse.isSuccess(response)) {
            return response;
        }
        return null;
    }

    /**
     * 还款方式下拉列表
     *
     * @param
     * @return
     * @author wangjun
     * yangchangwei 迁移至amadminClient
     */
    @Override
    public List<BorrowStyleVO> selectCommonBorrowStyleList() {
        String url = "http://AM-ADMIN/am-trade/admin_common/select_borrow_style";
        BorrowStyleResponse response = restTemplate.getForEntity(url, BorrowStyleResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询汇计划转让列表
     *
     * @param request
     * @return
     * yangchangwei
     */
    @Override
    public HjhDebtCreditReponse queryHjhDebtCreditList(HjhDebtCreditListRequest request) {

        HjhDebtCreditReponse response = restTemplate.
                postForEntity("http://AM-ADMIN/am-trade/adminHjhDebtCredit/getList", request, HjhDebtCreditReponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }
}
