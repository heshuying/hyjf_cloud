package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.resquest.trade.BorrowAuthRequest;
import com.hyjf.am.vo.trade.repay.BorrowAuthCustomizeVO;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.BorrowAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 借款人受托支付授权
 * @author hesy
 * @version BorrowAuthServiceImpl, v0.1 2018/7/6 14:09
 */
@Service
public class BorrowAuthServiceImpl extends BaseTradeServiceImpl implements BorrowAuthService {
    /**
     * 请求参数校验
     * @auther: hesy
     * @date: 2018/7/6
     */
    @Override
    public void checkForAuthList(BorrowAuthRequest requestBean){
        if(requestBean.getCurrPage()<=0){
            requestBean.setCurrPage(1);
        }

        if(requestBean.getPageSize()<=0){
            requestBean.setPageSize(10);
        }
    }

    /**
     * 待授权数
     * @auther: hesy
     * @date: 2018/7/6
     */
    @Override
    public Integer selectAuthCount(BorrowAuthRequest requestBean){
        return amTradeClient.selectBorrowAuthCount(requestBean);
    }

    /**
     * 待授权列表
     * @auther: hesy
     * @date: 2018/7/6
     */
    @Override
    public List<BorrowAuthCustomizeVO> selectAuthList(BorrowAuthRequest requestBean){
        return amTradeClient.selectBorrowAuthList(requestBean);
    }

    /**
     * 已授权数
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectAuthedCount(BorrowAuthRequest requestBean){
        return amTradeClient.selectBorrowAuthedCount(requestBean);
    }

    /**
     * 已授权列表
     * @auther: hesy
     * @date: 2018/7/6
     */
    @Override
    public List<BorrowAuthCustomizeVO> selectAuthedList(BorrowAuthRequest requestBean){
        return amTradeClient.selectBorrowAuthedList(requestBean);
    }
}
