/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task;

import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.trade.hjh.HjhCalculateFairValueVO;

import java.util.List;

/**
 * 汇计划自动清算Service
 *
 * @author liuyang
 * @version HjhAutoCreditService, v0.1 2018/6/26 16:20
 */
public interface HjhAutoCreditService extends BaseService {
    /**
     * 检索退出中的加入订单,用于计算计划订单的公允价值
     *
     * @return
     */
    List<HjhAccede> selectHjhQuitAccedeList();

    /**
     * 清算时,发送计算计划加入订单的公允价值MQ
     *
     * @param hjhCalculateFairValueBean
     */
    void sendHjhCalculateFairValueMQ(HjhCalculateFairValueVO hjhCalculateFairValueBean);

    /**
     * 检索到期的计划加入订单,用于清算
     *
     * @return
     */
    List<HjhAccede> hjhDeadLineAccedeList();

    /**
     * 汇计划自动清算
     *
     * @param hjhAccede
     * @param creditCompleteFlag
     * @return
     */
    List<String> updateAutoCredit(HjhAccede hjhAccede, Integer creditCompleteFlag) throws Exception;

    /**
     * 清算完成后,发送绑定计划MQ
     *
     * @param creditNid
     */
    void sendBorrowIssueMQ(String creditNid);
}
