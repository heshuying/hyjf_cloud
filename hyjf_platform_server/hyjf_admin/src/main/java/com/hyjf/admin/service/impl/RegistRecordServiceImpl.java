/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.RegistRecordClient;
import com.hyjf.admin.service.RegistRecordService;
import com.hyjf.am.resquest.user.RegistRcordRequest;
import com.hyjf.am.vo.user.RegistRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nixiaoling
 * @version RegistRecordServiceImpl, v0.1 2018/6/20 15:36
 */
@Service
public class RegistRecordServiceImpl implements RegistRecordService {
    @Autowired
    private RegistRecordClient registRecordClient;
    /**
     * 查找注册记录列表
     *
     * @param request
     * @return
     */
    @Override
    public List<RegistRecordVO> findRegistRecordList(RegistRcordRequest request){
        List<RegistRecordVO> listRgistRecord = registRecordClient.findRegistRecordList(request);
        return listRgistRecord;
    }

}
