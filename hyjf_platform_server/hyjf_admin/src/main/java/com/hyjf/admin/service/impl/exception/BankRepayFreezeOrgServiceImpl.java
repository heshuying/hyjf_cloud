package com.hyjf.admin.service.impl.exception;

import com.hyjf.admin.service.exception.BankRepayFreezeOrgService;
import com.hyjf.admin.service.impl.BaseAdminServiceImpl;
import com.hyjf.am.resquest.admin.RepayFreezeOrgRequest;
import com.hyjf.am.vo.admin.BankRepayFreezeOrgCustomizeVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 还款冻结异常处理
 * @author hesy
 * @version BankRepayFreezeOrgServiceImpl, v0.1 2018/10/19 10:09
 */
@Service
public class BankRepayFreezeOrgServiceImpl extends BaseAdminServiceImpl implements BankRepayFreezeOrgService {

    /**
     * 异常中心-还款冻结异常列表数据
     * @param requestBean
     * @return
     */
    @Override
    public List<BankRepayFreezeOrgCustomizeVO> selectList(RepayFreezeOrgRequest requestBean){
        return amAdminClient.getBankReapyFreezeOrgList(requestBean);
    }

    /**
     * 异常中心-还款冻结异常列表记录数
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectCount(RepayFreezeOrgRequest requestBean){
        return amAdminClient.getBankReapyFreezeOrgCount(requestBean);
    }

}
