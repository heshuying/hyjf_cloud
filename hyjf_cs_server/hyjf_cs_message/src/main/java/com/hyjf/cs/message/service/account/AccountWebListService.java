package com.hyjf.cs.message.service.account;

import com.hyjf.am.vo.datacollect.AccountWebListVO;

/**
 * @Auther: walter.limeng
 * @Date: 2018/8/1 17:08
 * @Description: AccountWebListService
 */
public interface AccountWebListService {
    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  根据nid和trade查询
     * @Date 17:10 2018/8/1
     * @Param nid
     * @Param trade
     * @return
     */
    Integer countAccountWebList(String nid, String trade);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  新增accountWebList
     * @Date 17:10 2018/8/1
     * @Param accountWebList
     * @return
     */
    Integer insertAccountWebList(AccountWebListVO accountWebList);
}
