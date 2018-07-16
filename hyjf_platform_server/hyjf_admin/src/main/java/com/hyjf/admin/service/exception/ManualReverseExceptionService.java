package com.hyjf.admin.service.exception;

import com.hyjf.am.resquest.admin.ManualReverseAddRequest;
import com.hyjf.am.resquest.admin.ManualReverseCustomizeRequest;
import com.hyjf.am.vo.admin.ManualReverseCustomizeVO;

import java.util.List;

/**
 * @author hesy
 * @version ManualReverseExceptionService, v0.1 2018/7/14 15:23
 */
public interface ManualReverseExceptionService {
    List<ManualReverseCustomizeVO> getManualReverseList(ManualReverseCustomizeRequest requestBean);

    int getManualReverseCount(ManualReverseCustomizeRequest requestBean);

    Boolean updateManualReverse(ManualReverseAddRequest requestBean);

    boolean checkForManualReverse(ManualReverseAddRequest requestBean);

    String getAccountIdByUserName(String userName);
}
