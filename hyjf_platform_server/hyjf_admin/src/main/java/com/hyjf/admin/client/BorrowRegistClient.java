/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowRegistClient, v0.1 2018/6/29 15:34
 */
public interface BorrowRegistClient {
    /**
     * 获取项目类型
     * @return
     */
    List<BorrowProjectTypeVO> selectBorrowProjectList();

    /**
     * 获取还款方式
     * @return
     */
    List<BorrowStyleVO> selectBorrowStyleList();

    /**
     * 获取标的备案列表count
     * @param borrowRegistListRequest
     * @return
     */
    Integer getRegistCount(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 获取标的备案列表
     * @param borrowRegistListRequest
     * @return
     */
    List <BorrowRegistCustomizeVO> selectBorrowRegistList(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 标的备案
     * @param borrowNid
     * @return
     */
    JSONObject updateBorrowRegist(String borrowNid);

    /**
     * 统计总额
     * @param borrowRegistListRequest
     * @return
     */
    String sumBorrowRegistAccount(BorrowRegistListRequest borrowRegistListRequest);
}
