/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.user.HjhPlanResponse;
import com.hyjf.am.vo.trade.CalculateInvestInterestVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;
import com.hyjf.cs.trade.client.BatchHjhBorrowRepayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BatchHjhBorrowRepayClientImpl, v0.1 2018/6/25 17:41
 */
@Service
public class BatchHjhBorrowRepayClientImpl implements BatchHjhBorrowRepayClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 标的投资详情表 拉取出事状态的数据
     * 查询条件：AccedeOrderIdEqualTo(accedeOrderId)                     ht_borrow_tender
     *           TenderTypeEqualTo(0) 状态(0:初始,1:已放款,2:放款失败)
     * @param accedeOrderId
     * @return
     */
    @Override
    public List<BorrowTenderVO> selectBorrowTenderListByAccedeOrderId(String accedeOrderId) {
        BorrowTenderResponse response = restTemplate
                .getForEntity("http://AM-USER/am-trade/batchHjhBorrowRepay/selectBorrowTenderListByAccedeOrderId/" + accedeOrderId , BorrowTenderResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据订单号获取汇计划加入明细
     *
     * @param accedeOrderId
     * @return
     */
    @Override
    public List<HjhAccedeVO> selectHjhAccedeListByOrderId(String accedeOrderId) {
        HjhAccedeResponse response = restTemplate
                .getForEntity("http://AM-USER/am-trade/batchHjhBorrowRepay/selectHjhAccedeListByOrderId/" + accedeOrderId , HjhAccedeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<HjhAccedeVO> selectHjhAccedeListById(Integer id) {
        HjhAccedeResponse response = restTemplate
                .getForEntity("http://AM-USER/am-trade/batchHjhBorrowRepay/selectHjhAccedeListById/" + id , HjhAccedeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<HjhPlanVO> selectHjhPlanListByPlanNid(String planNid) {
        HjhPlanResponse response = restTemplate
                .getForEntity("http://AM-USER/am-trade/batchHjhBorrowRepay/selectHjhPlanListByPlanNid/" + planNid , HjhPlanResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public Integer updateHjhBorrowRepayInterest(HjhAccedeVO hjhAccedeVO) {
        Integer result =  restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/batchHjhBorrowRepay/updateHjhBorrowRepayInterest/", hjhAccedeVO,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }

    @Override
    public Integer updateHjhAccedeByPrimaryKey(HjhAccedeVO hjhAccedeVO) {
        Integer result =  restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/batchHjhBorrowRepay/updateHjhAccedeByPrimaryKey/", hjhAccedeVO,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }

    @Override
    public List<BorrowRecoverVO> selectBorrowRecoverListByAccedeOrderId(String accedeOrderId) {
        BorrowRecoverResponse response = restTemplate
                .getForEntity("http://AM-USER/am-trade/batchHjhBorrowRepay/selectBorrowRecoverListByAccedeOrderId/" + accedeOrderId , BorrowRecoverResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<HjhRepayVO> selectHjhRepayListByAccedeOrderId(String accedeOrderId) {
        HjhRepayResponse response = restTemplate
                .getForEntity("http://AM-USER/am-trade/batchHjhBorrowRepay/selectHjhRepayListByAccedeOrderId/" + accedeOrderId , HjhRepayResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public HjhRepayVO selectHjhRepayListById(Integer Id) {
        HjhRepayResponse response = restTemplate
                .getForEntity("http://AM-USER/am-trade/batchHjhBorrowRepay/selectHjhRepayListById/" + Id , HjhRepayResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public Integer insertHjhBorrowRepay(HjhRepayVO hjhRepayVO) {
        Integer result =  restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/batchHjhBorrowRepay/insertHjhBorrowRepay/", hjhRepayVO,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }

    @Override
    public Integer updateBankTotalForLockPlan(AccountVO accountVO) {
        Integer result =  restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/batchHjhBorrowRepay/updateBankTotalForLockPlan/", accountVO,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }

    @Override
    public Integer updateHjhRepayByPrimaryKey(HjhRepayVO hjhRepayVO) {
        Integer result =  restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/batchHjhBorrowRepay/updateHjhRepayByPrimaryKey/", hjhRepayVO,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }

    @Override
    public Integer updateHjhPlanByPrimaryKey(HjhPlanVO hjhPlanVO) {
        Integer result =  restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/batchHjhBorrowRepay/updateHjhPlanByPrimaryKey/", hjhPlanVO,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }

    @Override
    public List<CalculateInvestInterestVO> selectCalculateInvestInterest() {
        CalculateInvestInterestResponse response = restTemplate
                .getForEntity("http://AM-USER/am-trade/batchHjhBorrowRepay/selectCalculateInvestInterest/" , CalculateInvestInterestResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public Integer updateCalculateInvestByPrimaryKey(CalculateInvestInterestVO calculateInvestInterestVO) {
        Integer result =  restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/batchHjhBorrowRepay/updateCalculateInvestByPrimaryKey/", calculateInvestInterestVO,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }
}
