/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.exception;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.customize.trade.BorrowRegistCustomize;
import com.hyjf.am.vo.trade.borrow.BorrowRegistExceptionVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BorrowRegistExceptionService, v0.1 2018/7/3 15:06
 */
public interface BorrowRegistExceptionService {
    /**
     * 获取项目类型
     * @return
     */
    List<BorrowProjectType> selectBorrowProjectList();

    /**
     * 获取还款方式
     * @return
     */
    List<BorrowStyle> selectBorrowStyleList();

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
    List<BorrowRegistCustomize> selectBorrowRegistList(BorrowRegistListRequest borrowRegistListRequest);
    /**
     * 标的异常处理
     * @auth sunpeikai
     * @param
     * @return
     */
    BorrowVO searchBorrowByBorrowNid(String borrowNid);

    /**
     *
     * @auth sunpeikai
     * @param
     * @return
     */
    String getStAccountIdByEntrustedUserId(Integer entrustedUserId);
    /**
     *
     * @auth sunpeikai
     * @param
     * @return
     */
    Boolean updateBorrowRegistByType(BorrowVO borrowVO,Integer type);
    /**
     *
     * @auth sunpeikai
     * @param
     * @return
     */
    Boolean updateBorrowAsset(BorrowVO borrowVO,Integer status);
}
