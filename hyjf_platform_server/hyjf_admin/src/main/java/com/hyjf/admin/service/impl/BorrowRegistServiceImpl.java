/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.BorrowRegistClient;
import com.hyjf.admin.service.BorrowRegistService;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.common.cache.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowRegistServiceImpl, v0.1 2018/6/29 15:34
 */
@Service
public class BorrowRegistServiceImpl implements BorrowRegistService {
    @Autowired
    BorrowRegistClient borrowRegistClient;

    @Override
    public List<BorrowProjectTypeVO> selectBorrowProjectList(){
        return borrowRegistClient.selectBorrowProjectList();
    }

    @Override
    public List<BorrowStyleVO> selectBorrowStyleList(){
        return borrowRegistClient.selectBorrowStyleList();
    }

    @Override
    public Map<String, String> getParamNameMap(String param){
        Map<String,String> resultMap = CacheUtil.getParamNameMap(param);
        return resultMap;
    }

    @Override
    public Integer getRegistCount(BorrowRegistListRequest borrowRegistListRequest){
        return borrowRegistClient.getRegistCount(borrowRegistListRequest);
    }

    @Override
    public List <BorrowRegistCustomizeVO> selectBorrowRegistList(BorrowRegistListRequest borrowRegistListRequest){
        return borrowRegistClient.selectBorrowRegistList(borrowRegistListRequest);
    }

    @Override
    public JSONObject updateBorrowRegist(String borrowNid){
        return borrowRegistClient.updateBorrowRegist(borrowNid);
    }

    @Override
    public String sumBorrowRegistAccount(BorrowRegistListRequest borrowRegistListRequest){
        return borrowRegistClient.sumBorrowRegistAccount(borrowRegistListRequest);
    }
}
