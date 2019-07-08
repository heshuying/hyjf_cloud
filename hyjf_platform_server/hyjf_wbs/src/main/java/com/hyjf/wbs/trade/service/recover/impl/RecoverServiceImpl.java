package com.hyjf.wbs.trade.service.recover.impl;

import com.hyjf.wbs.qvo.RecoverQO;
import com.hyjf.wbs.qvo.RecoverVO;
import com.hyjf.wbs.trade.dao.mapper.auto.RecoverMapper;
import com.hyjf.wbs.trade.dao.model.auto.Account;
import com.hyjf.wbs.trade.dao.model.auto.AccountExample;
import com.hyjf.wbs.trade.service.impl.BaseServiceImpl;
import com.hyjf.wbs.trade.service.recover.RecoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: kou
 * @Date: 2019-04-18 09:24
 * @Description:
 */
@Service
public class RecoverServiceImpl extends BaseServiceImpl implements RecoverService {

    @Override
    public List<RecoverVO> getRecoverInfo(RecoverQO recoverQO) {
        List<RecoverVO> recoverInfo = recoverMapper.getRecoverInfo(recoverQO);
        if (recoverInfo != null && recoverInfo.size() > 0) {
            return recoverInfo;
        }
        return null;
    }
    @Override
    public int getRecoverCount(RecoverQO recoverQO){
        Integer recoverCount = recoverMapper.getRecoverCount(recoverQO);
         return recoverCount;
    }
}
