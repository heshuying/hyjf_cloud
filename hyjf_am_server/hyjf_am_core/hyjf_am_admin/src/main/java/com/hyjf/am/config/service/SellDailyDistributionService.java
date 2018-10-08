package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.SellDailyDistribution;
import com.hyjf.am.resquest.admin.EmailRecipientRequest;

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
    List<SellDailyDistribution> queryRecordList(EmailRecipientRequest form, Integer limitStart, Integer limitEnd);

    /**
     * 根据id查询单条记录
     * @param id
     * @return
     */
    SellDailyDistribution queryRecordById(Integer id) ;

    /**
     * 修改邮件配置表
     * @param form
     * @return
     */
    boolean updateRecord(EmailRecipientRequest form);

    /**
     * 禁用
     * @param form
     * @return
     */
    boolean updateForbidden(EmailRecipientRequest form);

    /**
     * 添加邮件配置
     * @param form
     * @return
     */
    boolean insertRecord(EmailRecipientRequest form);
}
