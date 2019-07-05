package com.hyjf.wbs.trade.service.recover;

import com.hyjf.wbs.qvo.RecoverQO;
import com.hyjf.wbs.qvo.RecoverVO;
import com.hyjf.wbs.trade.dao.model.auto.Account;
import com.hyjf.wbs.trade.service.BaseService;

import java.util.List;


/**
 * @Auther: kou
 * @Date: 2019-04-18 09:20
 * @Description:
 */
public interface RecoverService extends BaseService {

    List<RecoverVO> getRecoverInfo(RecoverQO recoverQO);
    int getRecoverCount(RecoverQO recoverQO);
}
