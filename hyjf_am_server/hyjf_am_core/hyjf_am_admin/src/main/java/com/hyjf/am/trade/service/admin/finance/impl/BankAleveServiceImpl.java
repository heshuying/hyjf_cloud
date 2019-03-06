package com.hyjf.am.trade.service.admin.finance.impl;

import com.hyjf.am.trade.dao.mapper.customize.AleveCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.AleveLogExample;
import com.hyjf.am.trade.dao.model.auto.EveLogExample;
import com.hyjf.am.trade.dao.model.customize.AleveLogCustomize;
import com.hyjf.am.trade.service.admin.finance.BankAleveService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zdj on 2018/1/22.
 */
@Service
public class BankAleveServiceImpl extends BaseServiceImpl implements BankAleveService {

    @Autowired
    private AleveCustomizeMapper aleveCustomizeMapper;

    /**
     * 根据条件查询列表总数
     *
     * @param mapParam
     * @return
     */
    @Override
    public int countRecord(Map<String, Object> mapParam) {
        Integer count = aleveCustomizeMapper.queryAleveLogCount(mapParam);
        int vipcount = count.intValue();
        return vipcount;
    }

    /**
     * 根据查询条件查询列表
     *
     * @param mapParam
     * @return
     */
    @Override
    public List<AleveLogCustomize> selectBankAleveInfoList(Map<String, Object> mapParam) {
        List<AleveLogCustomize> manageList = aleveCustomizeMapper.queryAleveLogList(mapParam);
        return manageList;
    }

    /**
     * 根据创建日期查询该天导入aleve的条数
     *
     * @param dualDate
     * @return
     */
    @Override
    public Integer countAleveByDualDate(String dualDate) {
        AleveLogExample example = new AleveLogExample();
        AleveLogExample.Criteria criteria = example.createCriteria();
        criteria.andCreateDayEqualTo(dualDate);
        return aleveLogMapper.countByExample(example);
    }

    /**
     * 根据创建日期查询该天导入eve的条数
     *
     * @param dualDate
     * @return
     */
    @Override
    public Integer countEveByDualDate(String dualDate) {
        EveLogExample example = new EveLogExample();
        EveLogExample.Criteria criteria = example.createCriteria();
        criteria.andCreateDayEqualTo(dualDate);
        return eveLogMapper.countByExample(example);
    }

}
