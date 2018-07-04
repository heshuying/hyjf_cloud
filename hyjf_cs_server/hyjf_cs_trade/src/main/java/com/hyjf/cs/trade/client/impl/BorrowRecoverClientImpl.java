package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.BorrowRecoverResponse;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.BorrowRecoverClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author pangchengchao
 * @version WithdrawClientImpl, v0.1 2018/6/13 11:18
 */
@Service
public class BorrowRecoverClientImpl implements BorrowRecoverClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BorrowRecoverVO selectBorrowRecoverByTenderNid(String tenderAgreementID) {
        String url = "http://AM-TRADE/am-trade/borrowRecover/selectBorrowRecoverByTenderNid/"+tenderAgreementID;
        BorrowRecoverResponse response = restTemplate.getForEntity(url,BorrowRecoverResponse.class).getBody();
        if (Validator.isNotNull(response)){
           return response.getResult();
        }
        return null;
    }

    @Override
    public BorrowRecoverVO selectBorrowRecoverById(Integer id) {
        String url = "http://AM-TRADE/am-trade/borrowRecover/select_by_id/"+id;
        BorrowRecoverResponse response = restTemplate.getForEntity(url,BorrowRecoverResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public BorrowRecoverVO selectBorrowRecoverByNid(String nid) {
        String url = "http://AM-TRADE/am-trade/borrowRecover/select_by_nid/"+nid;
        BorrowRecoverResponse response = restTemplate.getForEntity(url,BorrowRecoverResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<BorrowRecoverVO> selectBorrowRecoverByBorrowNid(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrowRecover/select_by_borrownid/"+borrowNid;
        BorrowRecoverResponse response = restTemplate.getForEntity(url,BorrowRecoverResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public void updateBorrowRecover(BorrowRecoverVO borrowRecover) {
        String url = "http://AM-TRADE/am-trade/borrowRecover/updateBorrowRecover";
        restTemplate.postForEntity(url,borrowRecover,Boolean.class).getBody();
    }
}
