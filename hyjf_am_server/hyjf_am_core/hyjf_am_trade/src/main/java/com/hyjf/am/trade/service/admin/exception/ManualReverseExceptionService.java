package com.hyjf.am.trade.service.admin.exception;

import com.hyjf.am.resquest.admin.ManualReverseAddRequest;
import com.hyjf.am.resquest.admin.ManualReverseCustomizeRequest;
import com.hyjf.am.trade.dao.model.customize.ManualReverseCustomize;

import java.util.List;

/**
 * @author hesy
 * @version ManualReverseExceptionService, v0.1 2018/7/14 12:34
 */
public interface ManualReverseExceptionService {
    List<ManualReverseCustomize> selectManualReverseList(ManualReverseCustomizeRequest requestBean);

    int countManualReverse(ManualReverseCustomizeRequest requestBean);

    boolean updateManualReverse(ManualReverseAddRequest requestBean);
}
