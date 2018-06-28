/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.LoanCoverClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.LoanCoverUserResponse;
import com.hyjf.am.response.user.UserManagerResponse;
import com.hyjf.am.resquest.user.LoanCoverUserRequest;
import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.vo.user.LoanCoverUserVO;
import com.hyjf.am.vo.user.UserManagerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author nxl
 * @version LoanCoverClientImpl, v0.1 2018/6/26 17:13
 */
@Service
public class LoanCoverClientImpl implements LoanCoverClient {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查找借款盖章用户信息
     *
     * @param request
     * @return
     */
    @Override
    public List<LoanCoverUserVO> selectUserMemberList(LoanCoverUserRequest request) {
        LoanCoverUserResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/loanCoverUser/loanCoverUserRecord", request, LoanCoverUserResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }
    /**
     * 保存记录
     */
    @Override
    public int insertLoanCoverUser(LoanCoverUserRequest request){
        int updFlg = restTemplate.
                postForEntity("http://AM-USER/am-user/loanCoverUser/insertLoanCoverUserRecord", request, Integer.class).
                getBody();
        return updFlg;
    }

    /**
     * 查找记录
     */
    @Override
    public LoanCoverUserVO selectIsExistsRecordByIdNo(String strIdNo){
        LoanCoverUserResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/loanCoverUser/selectIsExistsRecordByIdNo/"+strIdNo, LoanCoverUserResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }
    /**
     * 根据id查找记录
     */
    @Override
    public LoanCoverUserVO selectIsExistsRecordById(String strId){
        LoanCoverUserResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/loanCoverUser/selectIsExistsRecordById/"+strId, LoanCoverUserResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }
    /**
     * 更新记录
     */
    @Override
    public int updateLoanCoverUserRecord(LoanCoverUserRequest request){
        int updFlg = restTemplate.
                postForEntity("http://AM-USER/am-user/loanCoverUser/updateLoanCoverUserRecord", request, Integer.class).
                getBody();
        return updFlg;
    }

}
