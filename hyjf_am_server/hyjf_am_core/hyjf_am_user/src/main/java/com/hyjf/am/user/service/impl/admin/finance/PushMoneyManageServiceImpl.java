/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl.admin.finance;

import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.user.dao.mapper.customize.admin.finance.PushMoneyCustomizeMapper;
import com.hyjf.am.user.dao.model.customize.admin.finance.PushMoneyCustomize;
import com.hyjf.am.user.service.admin.finance.PushMoneyManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zdj
 * @version PushMoneyManageServiceImpl, v0.1 2018/6/20 15:36
 */
@Service
public class PushMoneyManageServiceImpl implements PushMoneyManageService {

    @Autowired
    private PushMoneyCustomizeMapper pushMoneyMapper;
    /**
     * 提成管理 （列表）
     *
     * @return
     */
    @Override
    public List<PushMoneyCustomize> selectPushMoneyList(PushMoneyRequest request){
        List<PushMoneyCustomize> listRecord = pushMoneyMapper.queryPushMoneyList(request);
        return listRecord;
    }

    /**
     * 提成管理 （列表条数）
     * @return
     */
    @Override
    public int countPushMoney(PushMoneyRequest request){
        Integer integerCount = pushMoneyMapper.queryPushMoneyCount(request);
        return integerCount.intValue();
    }

}
