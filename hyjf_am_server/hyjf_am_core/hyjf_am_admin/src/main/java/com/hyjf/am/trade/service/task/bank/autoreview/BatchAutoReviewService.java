package com.hyjf.am.trade.service.task.bank.autoreview;

import com.hyjf.am.trade.utils.constant.BorrowSendTypeEnum;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.task.issuerecover.BorrowWithBLOBs;

import java.util.List;

/**
 * @Author walter.limeng
 * @Description  自动复审任务
 * @Date 9:29 2018/7/13
 */
public interface BatchAutoReviewService {

    /**
     * @Author walter.limeng
     * @Description  给到期未满标项目发短信
     * @Date 9:36 2018/7/13
     */
    void sendMsgToNotFullBorrow();

    /**
     * @Author walter.limeng
     * @Description  获取过期时间
     * @Date 11:05 2018/7/13
     * @Param fushensendCd
     * @return Integer
     */
    Integer getAfterTime(BorrowSendTypeEnum fushensendCd) throws Exception;

    /**
     * @Author walter.limeng
     * @Description  查询相应的待复审的标的
     * @Date 11:11 2018/7/13
     * @Param 
     * @return List<BorrowWithBLOBs>
     */
    List<BorrowWithBLOBs> selectAutoReview();

    /**
     * @Author walter.limeng
     * @Description  复审
     * @Date 11:19 2018/7/13
     * @Param 
     * @return 
     */
    void updateBorrow(BorrowWithBLOBs borrow, Integer afterTime) throws Exception;
}
