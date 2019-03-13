/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.crm.impl;

import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.auto.BorrowTenderExample;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.dao.model.auto.HjhAccedeExample;
import com.hyjf.am.trade.service.api.crm.CrmTenderRepairService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * CRM投资同步修复Service实现类
 *
 * @author liuyang
 * @version CrmTenderRepairServiceImpl, v0.1 2019/3/12 9:55
 */
@Service
public class CrmTenderRepairServiceImpl extends BaseServiceImpl implements CrmTenderRepairService {
    @Override
    public List<BorrowTender> selectCrmBorrowTenderList() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        //这里会有一个异常，所以要用try catch捕获异常
        try {
            d = sdf.parse("2019-03-09");
        } catch (Exception e) {
            e.printStackTrace();
        }
        BorrowTenderExample example = new BorrowTenderExample();
        BorrowTenderExample.Criteria cra = example.createCriteria();
        cra.andAccedeOrderIdIsNull();
        cra.andCreateTimeGreaterThan(d);
        cra.andCreateTimeLessThanOrEqualTo(new Date());
        List<BorrowTender> list = this.borrowTenderMapper.selectByExample(example);
        return list;
    }


    @Override
    public List<HjhAccede> selectCrmHjhAccedeList() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        //这里会有一个异常，所以要用try catch捕获异常
        try {
            d = sdf.parse("2019-03-09");
        } catch (Exception e) {
            e.printStackTrace();
        }
        HjhAccedeExample example = new HjhAccedeExample();
        HjhAccedeExample.Criteria cra = example.createCriteria();
        cra.andCreateTimeGreaterThan(d);
        cra.andCreateTimeLessThanOrEqualTo(new Date());
        List<HjhAccede> list = this.hjhAccedeMapper.selectByExample(example);
        return list;
    }

}
