package com.hyjf.am.trade.service.api.wrb.impl;

import com.hyjf.am.trade.dao.mapper.auto.HjhInstConfigMapper;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfigExample;
import com.hyjf.am.trade.service.api.wrb.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lisheng
 * @version UserRegisterServiceImpl, v0.1 2018/9/19 15:26
 */
@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    @Autowired
    protected HjhInstConfigMapper hjhInstConfigMapper;

    @Override
    public HjhInstConfig selectInstConfigByInstCode(String instCode) {
        HjhInstConfigExample example = new HjhInstConfigExample();
        HjhInstConfigExample.Criteria cra = example.createCriteria();
        cra.andInstCodeEqualTo(instCode);
        List<HjhInstConfig> list = this.hjhInstConfigMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

}
