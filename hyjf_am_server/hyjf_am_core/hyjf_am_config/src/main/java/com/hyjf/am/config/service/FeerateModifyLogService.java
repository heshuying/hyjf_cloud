package com.hyjf.am.config.service;

import com.hyjf.am.resquest.admin.FinmanChargeNewRequest;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xiehuili on 2018/8/14.
 */
public interface FeerateModifyLogService {

    /**
     * 添加费率配置日志
     * @author xiehuili
     * @return
     */
    public int insertFeerateModifyLog(@RequestBody FinmanChargeNewRequest adminRequest);
    /**
     * 修改费率配置日志
     * @author xiehuili
     * @return
     */
    public int updateFeerateModifyLog(@RequestBody FinmanChargeNewRequest adminRequest);
    /**
     * 删除费率配置日志
     * @author xiehuili
     * @return
     */
    public int deleteFeerateModifyLog(@RequestBody FinmanChargeNewRequest adminRequest);
}
