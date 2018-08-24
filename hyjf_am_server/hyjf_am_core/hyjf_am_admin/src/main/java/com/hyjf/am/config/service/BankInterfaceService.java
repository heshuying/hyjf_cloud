package com.hyjf.am.config.service;

import java.util.List;
import java.util.Map;

import com.hyjf.am.config.dao.model.auto.BankInterface;
import com.hyjf.am.vo.trade.account.BankInterfaceVO;

/**
 * @author pangchengchao
 * @version BankInterfaceService, v0.1 2018/6/22 14:34
 */
public interface BankInterfaceService {
    Integer getBanksConfigByBankId(String type);

    /**
     * 查询接口切换列表条数
     * @param paraMap
     * @return
     */
    Integer selectBankInterfaceListCount(Map<String, Object> paraMap);

    /**
     * 查询接口切换列表条数
     * @param paraMap
     * @return
     */
    List<BankInterface> selectBankInterfaceListByPage(Map<String, Object> paraMap);

    /**
     * 查询接口切换列表条数
     * @param id
     * @return
     */
   BankInterface selectBankInterfaceInfo(Integer id);

    /**
     * 修改接口切换
     * @param bankInterfaceVO
     */
    void   updateBankInterfaceInfo(BankInterfaceVO bankInterfaceVO);
}
