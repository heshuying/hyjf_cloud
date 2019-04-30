/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.response.BorrowRegistResponseBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.vo.admin.BorrowRegistCancelConfirmCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowRegistService, v0.1 2018/6/29 15:34
 */
public interface BorrowRegistService {
    /**
     * 获取项目类型
     *
     * @return
     */
    List<BorrowProjectTypeVO> selectBorrowProjectList();


    /**
     * 根据param获取对应数据
     *
     * @param param
     * @return
     */
    Map<String, String> getParamNameMap(String param);

    /**
     * 获取标的备案列表
     *
     * @param borrowRegistListRequest
     * @return
     */
    BorrowRegistResponseBean getRegistList(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 标的备案
     *
     * @param borrowNid
     * @return
     */
    AdminResult updateBorrowRegist(String borrowNid, String currUserId, String currUserName);

    AdminResult registCancel(String borrowNid, String currUserId, String currUserName);

    BorrowRegistCancelConfirmCustomizeVO selectRegistCancelConfirm(String borrowNid);
}
