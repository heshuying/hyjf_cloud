package com.hyjf.admin.service.exception;

import com.hyjf.am.resquest.admin.RepayFreezeOrgRequest;
import com.hyjf.am.vo.admin.BankRepayFreezeOrgCustomizeVO;

import java.util.List;

/**
 * @author hesy
 * @version BankRepayFreezeOrgService, v0.1 2018/10/19 12:07
 */
public interface BankRepayFreezeOrgService {
    List<BankRepayFreezeOrgCustomizeVO> selectList(RepayFreezeOrgRequest requestBean);

    Integer selectCount(RepayFreezeOrgRequest requestBean);
}
