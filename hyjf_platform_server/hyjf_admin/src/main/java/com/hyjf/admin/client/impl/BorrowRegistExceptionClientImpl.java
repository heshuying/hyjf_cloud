/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BorrowRegistExceptionClient;
import com.hyjf.am.response.admin.BorrowRegistCustomizeResponse;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BorrowRegistExceptionClientImpl, v0.1 2018/7/3 15:00
 */
@Service
public class BorrowRegistExceptionClientImpl implements BorrowRegistExceptionClient {
    @Autowired
    private RestTemplate restTemplate;


    @Override
    public List<BorrowProjectTypeVO> selectBorrowProjectList(){
        String url = "http://AM-TRADE/am-trade/borrow_regist_exception/select_borrow_project";
        BorrowProjectTypeResponse response = restTemplate.getForEntity(url,BorrowProjectTypeResponse.class).getBody();
        if(response != null){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<BorrowStyleVO> selectBorrowStyleList(){
        String url = "http://AM-TRADE/am-trade/borrow_regist_exception/select_borrow_style";
        BorrowStyleResponse response = restTemplate.getForEntity(url,BorrowStyleResponse.class).getBody();
        if(response != null){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public Integer getRegistCount(BorrowRegistListRequest borrowRegistListRequest){
        String url = "http://AM-TRADE/am-trade/borrow_regist_exception/get_regist_count";
        return restTemplate.postForEntity(url,borrowRegistListRequest,Integer.class).getBody();
    }

    @Override
    public List <BorrowRegistCustomizeVO> selectBorrowRegistList(BorrowRegistListRequest borrowRegistListRequest){
        String url = "http://AM-TRADE/am-trade/borrow_regist_exception/select_borrow_regist_list";
        BorrowRegistCustomizeResponse response = restTemplate.postForEntity(url,borrowRegistListRequest,BorrowRegistCustomizeResponse.class).getBody();
        if(response != null){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public BorrowVO searchBorrowByBorrowNid(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrow_regist_exception/search_borrow_by_borrownid/" + borrowNid;
        BorrowResponse response = restTemplate.getForEntity(url,BorrowResponse.class).getBody();
        if(response != null){
            return response.getResult();
        }
        return null;
    }

    @Override
    public BankOpenAccountVO searchBankOpenAccount(Integer userId) {
        String url = "http://AM-USER/am-user/borrow_regist_exception/searchbankopenaccount/" + userId;
        BankOpenAccountResponse response = restTemplate.getForEntity(url,BankOpenAccountResponse.class).getBody();
        if(response != null){
            return response.getResult();
        }
        return null;
    }

    @Override
    public AdminSystemVO getUserInfoById(Integer loginUserId) {
        String url = "http://AM-CONFIG/am-config/adminSystem/get_admin_system_by_userid/" + loginUserId;
        AdminSystemResponse response = restTemplate.getForEntity(url,AdminSystemResponse.class).getBody();
        if(response != null){
            return response.getResult();
        }
        return null;
    }

    @Override
    public String getStAccountIdByEntrustedUserId(Integer entrustedUserId) {
        String url = "http://AM-TRADE/am-trade/borrow_regist_exception/get_staccountid_by_entrusteduserid/" + entrustedUserId;
        String response = restTemplate.getForEntity(url,String.class).getBody();
        return response;
    }

    @Override
    public boolean updateBorrowRegist(BorrowVO borrowVO,Integer type) {
        String url = "http://AM-TRADE/am-trade/borrow_regist_exception/update_borrowregist_by_type/" + type;
        Boolean response = restTemplate.postForEntity(url,borrowVO,Boolean.class).getBody();
        return response;
    }
    /**
     * 备案成功看标的是否关联计划，如果关联则更新对应资产表
     * @param borrowVO
     * @return
     */
    @Override
    public boolean updateBorrowAsset(BorrowVO borrowVO, Integer status) {
        String url = "http://AM-TRADE/am-trade/borrow_regist_exception/update_borrowasset/" + status;
        Boolean response = restTemplate.postForEntity(url,borrowVO,Boolean.class).getBody();
        return response;
    }
}
