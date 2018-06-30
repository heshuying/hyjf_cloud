/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.resquest.user.RegistRcordRequest;
import com.hyjf.am.vo.user.RegistRecordVO;

import java.util.List;

/**
 * @author nxl
 * @version UserCenterService, v0.1 2018/6/20 15:34
 */
public interface AccountDetailService {
    /**
     * 查找注册记录列表
     *
     * @param request
     * @return
     */
    List<RegistRecordVO> findRegistRecordList(RegistRcordRequest request);

}
