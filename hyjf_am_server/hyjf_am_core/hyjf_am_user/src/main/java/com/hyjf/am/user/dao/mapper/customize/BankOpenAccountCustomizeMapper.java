package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.auto.BankOpenAccount;

import java.util.List;

public interface BankOpenAccountCustomizeMapper {

    List<BankOpenAccount> selectByUserList (List<Integer> user);
}