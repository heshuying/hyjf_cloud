package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.BankInterfaceMapper;
import com.hyjf.am.config.dao.mapper.customize.CallcenterConfigCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.BankInterface;
import com.hyjf.am.config.dao.model.auto.BankInterfaceExample;
import com.hyjf.am.config.service.BankInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pangchengchao
 * @version BankInterfaceServiceImpl, v0.1 2018/6/22 14:34
 */
@Service
public class BankInterfaceServiceImpl implements BankInterfaceService {
    @Autowired
    protected BankInterfaceMapper bankInterfaceMapper;
    @Override
    public Integer getBanksConfigByBankId(String type) {
        BankInterfaceExample bankInterfaceExample = new BankInterfaceExample();
        BankInterfaceExample.Criteria criteria= bankInterfaceExample.createCriteria();
        criteria.andInterfaceTypeEqualTo(type);
        criteria.andIsDeleteEqualTo(0);
        criteria.andIsUsableEqualTo(1);
        bankInterfaceExample.setLimitStart(0);
        bankInterfaceExample.setLimitEnd(1);
        bankInterfaceExample.setOrderByClause("`update_time` DESC");
        List<BankInterface> bankInterfaces = bankInterfaceMapper.selectByExample(bankInterfaceExample);
        if(bankInterfaces.size() > 0){
            //返回接口类型
            return bankInterfaces.get(0).getInterfaceStatus();
        }else {
            //默认绑卡旧接口
            return 0;
        }
    }
}
