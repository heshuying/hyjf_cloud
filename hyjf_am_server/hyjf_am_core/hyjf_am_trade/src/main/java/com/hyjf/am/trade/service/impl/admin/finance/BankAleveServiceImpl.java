package com.hyjf.am.trade.service.impl.admin.finance;

import com.hyjf.am.trade.dao.customize.finance.AleveLogCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.trade.AleveLogCustomize;
import com.hyjf.am.trade.service.admin.finance.BankAleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by cuigq on 2018/1/22.
 */
@Service
public class BankAleveServiceImpl implements BankAleveService {

    @Autowired
    AleveLogCustomizeMapper aleveLogMapper;

    /**
     * 根据条件查询列表总数
     *
     * @param mapParam
     * @return
     */
    @Override
    public int countRecord(Map<String, Object> mapParam) {
        Integer count = aleveLogMapper.countRecord(mapParam);
        int vipcount = count.intValue();
        return vipcount;
    }

    /**
     * 根据查询条件查询列表
     *
     * @param mapParam
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<AleveLogCustomize> selectBankAleveInfoList(Map<String, Object> mapParam, int limitStart, int limitEnd) {
        // 封装查询条件
        if (limitStart == 0 || limitStart > 0) {
            mapParam.put("limitStart", limitStart);
        }
        if (limitEnd > 0) {
            mapParam.put("limitEnd", limitEnd);
        }
        List<AleveLogCustomize> manageList = aleveLogMapper.selectBankAleveInfoList(mapParam);
        return manageList;
    }

}
