/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BorrowRegistExceptionService, v0.1 2018/7/3 14:52
 */
public interface BorrowRegistExceptionService {
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
     * 获取标的列表count
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
     * 备案异常处理
     * @auth 孙沛凯
     * @param borrowNid 借款编号
     * @return
     */
    JSONObject handleBorrowRegistException(String borrowNid,Integer userId);

}
