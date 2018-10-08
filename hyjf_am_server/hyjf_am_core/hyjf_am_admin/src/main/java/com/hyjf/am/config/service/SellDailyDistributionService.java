package com.hyjf.am.config.service;

import com.hyjf.am.resquest.admin.EmailRecipientRequest;
import com.hyjf.am.vo.admin.SellDailyDistributionVO;

import java.util.List;

/**
 * @author lisheng
 * @version SellDailyDistributionService, v0.1 2018/10/8 11:37
 */

public interface SellDailyDistributionService {
    /**
     *查询数据条数
     * @param form
     * @return
     */
    Integer queryTotal(EmailRecipientRequest form);

    /**
     * 根据查询条件 取得数据
     * @return
     */
    List<SellDailyDistributionVO> queryRecordList(EmailRecipientRequest form, int limitStart, int limitEnd);

    /**
     * 根据id查询单条记录
     * @param id
     * @return
     */
    SellDailyDistributionVO queryRecordById(Integer id) ;

}
