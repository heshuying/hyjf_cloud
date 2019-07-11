/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user;

import com.hyjf.am.user.dao.model.auto.BankMobileModify;
import com.hyjf.am.user.service.BaseService;
import com.hyjf.am.vo.user.BankMobileModifyVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BankMobileModifyService, v0.1 2019/5/9 15:30
 */
public interface BankMobileModifyService extends BaseService {
    /**
     * 插入修改银行预留手机号日志表
     *
     * @param bankMobileModify
     * @return
     */
    Integer insertBankMobileModify(BankMobileModify bankMobileModify);

    /**
     * 查询修改银预留手机号日志表
     *
     * @param vo
     * @return
     */
    List<BankMobileModify> selectBankMobileModify(BankMobileModifyVO vo);

    /**
     * 更新银预留手机号日志表
     *
     * @param bankMobileModify
     * @return
     */
    Integer updateBankMoblieModify(BankMobileModify bankMobileModify);

    /**
     * 更新用户银行预留手机号
     *
     * @param userId
     * @param bankMobile
     * @return
     */
    Integer updateBankMobileByUserId(Integer userId, String bankMobile);
}
