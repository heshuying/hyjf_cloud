package com.hyjf.am.config.controller;

import com.hyjf.am.config.service.FeerateModifyLogService;
import com.hyjf.am.resquest.admin.FinmanChargeNewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiehuili on 2018/8/14.
 */
@RestController
@RequestMapping("/am-config/feerateModifyLog")
public class FeerateModifyLogController {

    @Autowired
    private FeerateModifyLogService feerateModifyLogService;
    /**
     * 添加费率配置日志
     * @author xiehuili
     * @return
     */
    @RequestMapping("/insert")
    public int insertFeerateModifyLog(@RequestBody FinmanChargeNewRequest adminRequest){
        return feerateModifyLogService.insertFeerateModifyLog(adminRequest);
    }
    /**
     * 修改费率配置日志
     * @author xiehuili
     * @return
     */
    @RequestMapping("/update")
    public int updateFeerateModifyLog(@RequestBody FinmanChargeNewRequest adminRequest){
        return feerateModifyLogService.updateFeerateModifyLog(adminRequest);
    }

    /**
     * 删除费率配置日志
     * @author xiehuili
     * @return
     */
    @RequestMapping("/delete")
    public int deleteFeerateModifyLog(@RequestBody FinmanChargeNewRequest adminRequest){
        return feerateModifyLogService.deleteFeerateModifyLog(adminRequest);
    }


}
