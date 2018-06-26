/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.resquest.user.BorrowFinmanNewChargeRequest;
import com.hyjf.am.trade.dao.model.auto.*;

import java.util.List;

/**
 * @author fuqiang
 * @version BorrowService, v0.1 2018/6/13 18:52
 */
public interface BorrowService {
    Borrow getBorrow(String borrowNid);

    BorrowStyle getborrowStyleByNid(String borrowStyle);


    /**
     * 根据项目类型，期限，获取借款利率
     * @param request
     * @return
     */
    BorrowFinmanNewCharge selectBorrowApr(BorrowFinmanNewChargeRequest request);

    /**
     * 获取系统配置
     * @param configCd
     * @return
     */
    BorrowConfig getBorrowConfigByConfigCd(String configCd);

    /**
     * 借款表插入
     * @param borrow
     */
    int insertBorrow(Borrow borrow);

    int insertBorrowManinfo(BorrowManinfo borrowManinfo);

    /**
     * 更新相应的标的状态为备案中
     * @param request
     * @return
     */
    int updateBorrowRegist(BorrowRegistRequest request);

    /**
     * 检索正在还款中的标的
     * @return
     */
    List<Borrow> selectBorrowList();

    /**
     * 获取BorrowInfo
     * @param borrowNid
     * @return
     */
    BorrowInfo getBorrowInfoByNid(String borrowNid);

    /**
     * 投资之前插入tmp表
     * @param tenderRequest
     * @return
     */
    int insertBeforeTender(TenderRequest tenderRequest);
}
