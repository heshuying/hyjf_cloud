package com.hyjf.admin.client.impl;

import com.hyjf.admin.beans.request.BorrowCreditRequest;
import com.hyjf.admin.client.AmBorrowCreditClient;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBorrowCreditInfoResponse;
import com.hyjf.am.response.admin.AdminBorrowCreditResponse;
import com.hyjf.am.resquest.admin.BorrowCreditAmRequest;
import com.hyjf.am.vo.admin.BorrowCreditInfoSumVO;
import com.hyjf.am.vo.admin.BorrowCreditInfoVO;
import com.hyjf.am.vo.admin.BorrowCreditSumVO;
import com.hyjf.am.vo.admin.BorrowCreditVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AmBorrowCreditClientImpl implements AmBorrowCreditClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询债转列表
     * @author zhangyk
     * @date 2018/7/10 16:26
     */
    @Override
    public List<BorrowCreditVO> getBorrowCreditList(BorrowCreditAmRequest request) {
        AdminBorrowCreditResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/borrowCredit/getBorrowCreditList4admin",request,AdminBorrowCreditResponse.class).getBody();
        if (AdminResponse.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询债转count
     * @author zhangyk
     * @date 2018/7/10 16:26
     */
    @Override
    public Integer getBorrowCreditCount(BorrowCreditAmRequest request) {
        AdminBorrowCreditResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/borrowCredit/countBorrowCreditList4admin",request,AdminBorrowCreditResponse.class).getBody();
        if (AdminResponse.isSuccess(response)){
            return response.getRecordTotal();
        }
        return null;
    }

    /**
     * 查询合计数据
     * @author zhangyk
     * @date 2018/7/10 16:27
     */
    @Override
    public BorrowCreditSumVO getBorrwoCreditTotalSum(BorrowCreditAmRequest request) {
        AdminBorrowCreditResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/borrowCredit/getBorrowCreditTotalCount",request,AdminBorrowCreditResponse.class).getBody();
        if (AdminResponse.isSuccess(response)){
            return response.getSumVO();
        }
        return null;
    }

    /**
     * 查询债转详情count
     * @author zhangyk
     * @date 2018/7/10 16:27
     */
    @Override
    public Integer countBorrowCreditInfo(BorrowCreditAmRequest request) {
        AdminBorrowCreditInfoResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/borrowCredit/countBorrowCreditInfo4admin",request,AdminBorrowCreditInfoResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getCount();
        }
        return null;
    }


    /**
     * 查询债转详情count
     * @author zhangyk
     * @date 2018/7/10 17:29
     */
    @Override
    public List<BorrowCreditInfoVO> searchBorrowCreditInfoList(BorrowCreditAmRequest request) {
        AdminBorrowCreditInfoResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/borrowCredit/searchBorrowCreditInfo4admin",request,AdminBorrowCreditInfoResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public BorrowCreditInfoSumVO sumBorrowCreditInfoData(BorrowCreditAmRequest request) {
        AdminBorrowCreditInfoResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/borrowCredit/sumBorrowCreditInfo4admin",request,AdminBorrowCreditInfoResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getSumData();
        }
        return null;
    }


}
