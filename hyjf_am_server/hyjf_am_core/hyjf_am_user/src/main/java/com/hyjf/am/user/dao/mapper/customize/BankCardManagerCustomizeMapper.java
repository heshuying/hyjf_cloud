/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.customize.BankcardManagerCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version UserManagerCustomizeMapper, v0.1 2018/6/20 10:01
 */
public interface BankCardManagerCustomizeMapper {

    /**
     *  根据筛选条件查找汇付银行卡信息列表
     * @param mapParam 筛选条件
     * @return
     */
    List<BankcardManagerCustomize> selectBankCardList(Map<String, Object> mapParam);
    /**
     * 根据条件统计汇付银行卡信息
     * @param mapParam
     * @return
     */
    Integer countRecordTotal(Map<String, Object> mapParam);

    /**
     * 根据筛选条件查找江西银行卡信息列表
     * @return
     */
    List<BankcardManagerCustomize> selectNewBankCardList(Map<String, Object> mapParam);

    /**
     *根据条件统计江西银行卡信息
     * @return
     */
    int countRecordTotalNew(Map<String, Object> mapParam);
}
