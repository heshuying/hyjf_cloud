package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.account.AccountListVO;

/**
 * @author pangchengchao
 * @version AccountListClient, v0.1 2018/6/11 17:26
 */
public interface AccountListClient {

    int countAccountListByOrdId(String ordId, String type);


    Integer insertAccountListSelective(AccountListVO accountListVO);

    /**
     * @Author walter.limeng
     * @Description  根据nid和trade查询收支明细
     * @Date 10:03 2018/7/18
     * @Param nid
     * @Param trade
     * @return
     */
    int countByNidAndTrade(String nid,String trade);
}
