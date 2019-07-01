package com.hyjf.am.trade.service.admin.borrow.impl;

import com.hyjf.am.resquest.admin.BorrowRepayInfoCurrentRequest;
import com.hyjf.am.trade.dao.mapper.customize.AdminBorrowRepayInfoCurrentCustomizeMapper;
import com.hyjf.am.trade.service.admin.borrow.BorrowRepayInfoCurrentService;
import com.hyjf.am.vo.admin.BorrowRepayInfoCurrentCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepayInfoCurrentExportCustomizeVO;
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
     */
    @Override
    public Integer getRepayInfoCurrentCount(Map<String,Object> paraMap){
        return adminBorrowRepayInfoCurrentCustomizeMapper.getRepayInfoCurrentCount(paraMap);
    }

    /**
     * 获取列表数据
     */
    @Override
    public List<BorrowRepayInfoCurrentCustomizeVO> getRepayInfoCurrentList(Map<String,Object> paraMap){
        List<BorrowRepayInfoCurrentCustomizeVO> resultList = adminBorrowRepayInfoCurrentCustomizeMapper.getRepayInfoCurrentList(paraMap);
        if(resultList == null){
            resultList = new ArrayList<>();
        }
        return resultList;
    }

    /**
     * 统计列表总计数据
     */
    @Override
    public Map<String,Object> getRepayInfoCurrentSum(Map<String,Object> paraMap){
        Map<String,Object> resultMap = adminBorrowRepayInfoCurrentCustomizeMapper.getRepayInfoCurrentSum(paraMap);
        if(resultMap == null){
            resultMap = new HashMap<>();
        }
        return resultMap;
    }

    /**
     * 获取列表导出数据
     */
    @Override
    public List<BorrowRepayInfoCurrentExportCustomizeVO> getRepayInfoCurrentListExport(Map<String,Object> paraMap){
        List<BorrowRepayInfoCurrentExportCustomizeVO> resultList = adminBorrowRepayInfoCurrentCustomizeMapper.getRepayInfoCurrentListExport(paraMap);
        if(resultList == null){
            resultList = new ArrayList<>();
        }
        return resultList;
    }

    /**
     * 获取列表导出总记录数
     */
    @Override
    public Integer getRepayInfoCurrentCountExport(Map<String,Object> paraMap){
        Integer count = adminBorrowRepayInfoCurrentCustomizeMapper.getRepayInfoCurrentCountExport(paraMap);
        if(count == null){
            count = 0;
        }
        return count;
    }
}
