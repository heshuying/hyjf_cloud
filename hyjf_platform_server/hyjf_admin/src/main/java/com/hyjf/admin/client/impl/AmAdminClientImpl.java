package com.hyjf.admin.client.impl;

import com.hyjf.admin.beans.request.STZHWhiteListRequestBean;
import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminSubConfigResponse;
import com.hyjf.am.response.admin.HjhDebtCreditReponse;
import com.hyjf.am.response.admin.PoundageCustomizeResponse;
import com.hyjf.am.response.trade.BorrowStyleResponse;
import com.hyjf.am.response.trade.STZHWhiteListResponse;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.response.user.UtmPlatResponse;
import com.hyjf.am.resquest.admin.AdminSubConfigRequest;
import com.hyjf.am.resquest.admin.HjhDebtCreditListRequest;
import com.hyjf.am.resquest.admin.PoundageListRequest;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.vo.admin.PoundageCustomizeVO;
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

    /**
     * 查询手续费分账count
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getPoundageCount(PoundageListRequest request) {
        String url = "http://AM-ADMIN/am-admin/poundage/getPoundageCount";
        PoundageCustomizeResponse response = restTemplate.postForEntity(url, request, PoundageCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCount();
        }
        return 0;
    }
    /**
     * 查询手续费分账list
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<PoundageCustomizeVO> searchPoundageList(PoundageListRequest request) {
        String url = "http://AM-ADMIN/am-admin/poundage/searchPoundageList";
        PoundageCustomizeResponse response = restTemplate.postForEntity(url, request, PoundageCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取手续费分账数额总计
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public PoundageCustomizeVO getPoundageSum(PoundageListRequest request) {
        String url = "http://AM-ADMIN/am-admin/poundage/getPoundageSum";
        PoundageCustomizeResponse response = restTemplate.postForEntity(url, request, PoundageCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据id查询手续费分账信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public PoundageCustomizeVO getPoundageById(Integer id) {
        String url = "http://AM-ADMIN/am-admin/poundage/getPoundageById/" + id;
        PoundageCustomizeResponse response = restTemplate.getForEntity(url,PoundageCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 审核-更新poundage表
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public Integer updatePoundage(PoundageCustomizeVO poundageCustomizeVO) {
        String url = "http://AM-ADMIN/am-admin/poundage/updatePoundage";
        PoundageCustomizeResponse response = restTemplate.postForEntity(url, poundageCustomizeVO, PoundageCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCount();
        }
        return 0;
    }

    /**
     * 根据用户名查询分账名单是否存在
     * @author xiehuili
     * @param username
     * @return
     */
    @Override
    public AdminSubConfigResponse subconfig(AdminSubConfigRequest adminRequest){
        String url = "http://AM-ADMIN/am-admin/config/subconfig";
        AdminSubConfigResponse response = restTemplate.postForEntity(url,adminRequest, AdminSubConfigResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response;
        }
        return null;
    }
}
