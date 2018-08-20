package com.hyjf.cs.trade.service.hjh;

import com.hyjf.am.vo.trade.HjhLockVo;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;

import java.util.List;

/**
 * @author hesy
 * @version HjhCommisionComputeService, v0.1 2018/8/17 14:21
 */
public interface HjhCommisionComputeService {
    List<HjhAccedeVO> getAccedesWaitCompute();

    Boolean commisionCompute(HjhLockVo hjhLockVo);

    HjhLockVo getBeanForCompute(String accedeOrderId);
}
