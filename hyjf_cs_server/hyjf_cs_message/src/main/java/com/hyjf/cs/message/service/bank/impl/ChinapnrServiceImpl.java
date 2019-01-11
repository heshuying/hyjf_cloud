/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.bank.impl;

import com.hyjf.am.vo.trade.ChinapnrExclusiveLogWithBLOBsVO;
import com.hyjf.cs.message.bean.ic.ChinapnrExclusiveLog;
import com.hyjf.cs.message.bean.ic.ChinapnrLog;
import com.hyjf.cs.message.mongo.ic.ChinapnrExclusiveLogDao;
import com.hyjf.cs.message.mongo.ic.ChinapnrLogDao;
import com.hyjf.cs.message.service.bank.ChinapnrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangqingqing
 * @version ChinapnrServiceImpl, v0.1 2019/1/10 16:06
 */
@Service
public class ChinapnrServiceImpl  implements ChinapnrService{

    @Autowired
    ChinapnrExclusiveLogDao chinapnrExclusiveLogDao;

    @Autowired
    ChinapnrLogDao chinapnrLogDao;

    @Override
    public ChinapnrExclusiveLog queryById(String id){
        return chinapnrExclusiveLogDao.queryById(id);
    }

    @Override
    public void updateChinapnrExclusiveLogStatus(String uuid, String status) {
         chinapnrExclusiveLogDao.updateByPrimaryKeySelective(uuid,status);
    }

    @Override
    public void updateChinapnrExclusiveLog(ChinapnrExclusiveLogWithBLOBsVO record) {
         chinapnrExclusiveLogDao.updateByExampleSelective(record);
    }

    @Override
    public List<ChinapnrLog> getChinapnrLog(String ordId) {

        List<ChinapnrLog> list = chinapnrLogDao.selectByExampleWithBLOBs(ordId);
        return list;
    }
}
