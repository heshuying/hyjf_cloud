package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.cs.trade.client.RepayManageClient;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.RepayManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 还款管理列表
 * @author hesy
 * @version RepayManageServiceImpl, v0.1 2018/6/23 17:16
 */
@Service
public class RepayManageServiceImpl extends BaseTradeServiceImpl implements RepayManageService {
    @Autowired
    RepayManageClient repayManageClient;

    /**
     * 请求参数校验
     * @param requestBean
     */
    @Override
    public void checkForRepayList(RepayListRequest requestBean){
        if(requestBean.getCurrPage() <= 0){
            requestBean.setCurrPage(1);
        }

        if(requestBean.getPageSize() <= 0){
            requestBean.setPageSize(10);
        }
    }

    /**
     * 用户已还款/待还款列表
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> selectRepayList(RepayListRequest requestBean){
        return repayManageClient.repayList(requestBean);
    }

    /**
     * 垫付机构待还款列表
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> selectOrgRepayList(RepayListRequest requestBean){
        return repayManageClient.orgRepayList(requestBean);
    }

    /**
     * 垫付机构已还款列表
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> selectOrgRepayedList(RepayListRequest requestBean){
        return repayManageClient.orgRepayedList(requestBean);
    }

    /**
     * 用户待还款/已还款总记录数
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectRepayCount(RepayListRequest requestBean){
        return repayManageClient.repayCount(requestBean);
    }

    /**
     * 垫付机构待还款总记录数
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectOrgRepayCount(RepayListRequest requestBean){
        return repayManageClient.orgRepayCount(requestBean);
    }

    /**
     * 垫付机构已还款总记录数
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectOrgRepayedCount(RepayListRequest requestBean){
        return repayManageClient.orgRepayedCount(requestBean);
    }
}
