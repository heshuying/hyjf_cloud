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
    /**
     * 手动冲正列表
     * @param requestBean
     * @return
     */
    List<ManualReverseCustomizeVO> getManualReverseList(ManualReverseCustomizeRequest requestBean);
    /**
     * 手动冲正列表总记录数
     * @param requestBean
     * @return
     */
    int getManualReverseCount(ManualReverseCustomizeRequest requestBean);
    /**
     * 手动冲正更新
     * @param requestBean
     * @return
     */
    Boolean updateManualReverse(ManualReverseAddRequest requestBean);
    /**
     * 校验请求参数
     * @param requestBean
     * @return
     */
    boolean checkForManualReverse(ManualReverseAddRequest requestBean);
    /**
     * 根据userName获取电子账号
     * @param userName
     * @return
     */
    String getAccountIdByUserName(String userName);
}
