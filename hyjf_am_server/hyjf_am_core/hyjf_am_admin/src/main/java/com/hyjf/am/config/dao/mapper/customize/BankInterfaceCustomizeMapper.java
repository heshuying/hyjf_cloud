package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.config.dao.model.auto.BankInterface;

import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/20.
 */
public interface BankInterfaceCustomizeMapper {

    /**
     * 获取列表
     * @param paraMap
     * @return
     */
    List<BankInterface> selectBankInterfaceListByPage(Map<String, Object> paraMap);

    /**
     * 获得列表数
     *
     * @param paraMap
     * @return
     */
    Integer selectBankInterfaceListCount(Map<String, Object> paraMap);
}
