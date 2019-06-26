package com.hyjf.wbs.trade.dao.mapper.auto;


import com.hyjf.wbs.qvo.RecoverQO;
import com.hyjf.wbs.qvo.RecoverVO;
import com.hyjf.wbs.trade.dao.model.auto.Account;
import com.hyjf.wbs.trade.dao.model.auto.AccountExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecoverMapper {

    List<RecoverVO> getRecoverInfo(RecoverQO recoverQO);

    int getRecoverCount(RecoverQO recoverQO);
}