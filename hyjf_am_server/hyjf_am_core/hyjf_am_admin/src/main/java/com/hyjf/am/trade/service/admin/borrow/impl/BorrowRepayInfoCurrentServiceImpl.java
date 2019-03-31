package com.hyjf.am.trade.service.admin.borrow.impl;

import com.hyjf.am.resquest.admin.BorrowRepayInfoCurrentRequest;
import com.hyjf.am.trade.dao.mapper.customize.AdminBorrowRepayInfoCurrentCustomizeMapper;
import com.hyjf.am.trade.service.admin.borrow.BorrowRepayInfoCurrentService;
import com.hyjf.am.vo.admin.BorrowRepayInfoCurrentCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 当前债权还款明细
 * @author hesy
 */
@Service
public class BorrowRepayInfoCurrentServiceImpl implements BorrowRepayInfoCurrentService {
    @Autowired
    private AdminBorrowRepayInfoCurrentCustomizeMapper adminBorrowRepayInfoCurrentCustomizeMapper;

    /**
     * 列表总记录数
     * @param requestBean
     * @return
     */
    @Override
    public Integer getRepayInfoCurrentCount(BorrowRepayInfoCurrentRequest requestBean){
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("borrowNid", requestBean.getBorrowNid());

        return adminBorrowRepayInfoCurrentCustomizeMapper.getRepayInfoCurrentCount(paraMap);
    }

    /**
     * 获取列表数据
     * @param requestBean
     * @return
     */
    @Override
    public List<BorrowRepayInfoCurrentCustomizeVO> getRepayInfoCurrentList(BorrowRepayInfoCurrentRequest requestBean, Integer offset, Integer limit){
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("borrowNid", requestBean.getBorrowNid());
        paraMap.put("limitStart", offset);
        paraMap.put("limitEnd", limit);

        List<BorrowRepayInfoCurrentCustomizeVO> resultList = adminBorrowRepayInfoCurrentCustomizeMapper.getRepayInfoCurrentList(paraMap);
        if(resultList == null){
            resultList = new ArrayList<>();
        }
        return resultList;
    }

    /**
     * 统计列表总计数据
     * @param requestBean
     * @return
     */
    @Override
    public Map<String,Object> getRepayInfoCurrentSum(BorrowRepayInfoCurrentRequest requestBean){
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("borrowNid", requestBean.getBorrowNid());

        Map<String,Object> resultMap = adminBorrowRepayInfoCurrentCustomizeMapper.getRepayInfoCurrentSum(paraMap);
        if(resultMap == null){
            resultMap = new HashMap<>();
        }
        return resultMap;
    }
}
