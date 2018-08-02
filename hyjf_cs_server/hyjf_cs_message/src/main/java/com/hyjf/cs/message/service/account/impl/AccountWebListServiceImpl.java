package com.hyjf.cs.message.service.account.impl;

import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.message.bean.ic.AccountWebList;
import com.hyjf.cs.message.mongo.ic.AccountWebListDao;
import com.hyjf.cs.message.service.account.AccountWebListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: walter.limeng
 * @Date: 2018/8/1 17:08
 * @Description: AccountWebListServiceImpl
 */
@Service
public class AccountWebListServiceImpl implements AccountWebListService {
    @Resource
    private AccountWebListDao accountWebListDao;
    @Override
    public Integer countAccountWebList(String nid, String trade) {
        return this.accountWebListDao.countAccountWebList(nid,trade);
    }

    @Override
    public Integer insertAccountWebList(AccountWebListVO accountWebList) {
        accountWebListDao.insert(CommonUtils.convertBean(accountWebList,AccountWebList.class));
        return 1;
    }
}
