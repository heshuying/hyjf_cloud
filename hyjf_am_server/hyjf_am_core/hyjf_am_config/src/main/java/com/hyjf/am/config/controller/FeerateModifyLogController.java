package com.hyjf.am.config.controller;

import com.hyjf.am.config.service.FeerateModifyLogService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.FinmanChargeNewResponse;
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
    public FinmanChargeNewResponse insertFeerateModifyLog(@RequestBody FinmanChargeNewRequest adminRequest){
        FinmanChargeNewResponse response = new FinmanChargeNewResponse();
        int count = feerateModifyLogService.insertFeerateModifyLog(adminRequest);
        if(count > 0){
            response.setRtn(Response.SUCCESS);
            response.setMessage(Response.SUCCESS_MSG);
        }
        return null;
    }
    /**
     * 修改费率配置日志
     * @author xiehuili
     * @return
     */
    @RequestMapping("/update")
    public FinmanChargeNewResponse updateFeerateModifyLog(@RequestBody FinmanChargeNewRequest adminRequest){
        FinmanChargeNewResponse response = new FinmanChargeNewResponse();
        int count = feerateModifyLogService.updateFeerateModifyLog(adminRequest);
        if(count > 0){
            response.setRtn(Response.SUCCESS);
            response.setMessage(Response.SUCCESS_MSG);
        }
        return null;
    }

    /**
     * 删除费率配置日志
     * @author xiehuili
     * @return
     */
    @RequestMapping("/delete")
    public FinmanChargeNewResponse deleteFeerateModifyLog(@RequestBody FinmanChargeNewRequest adminRequest){
        FinmanChargeNewResponse response = new FinmanChargeNewResponse();
        int count =feerateModifyLogService.deleteFeerateModifyLog(adminRequest);
        if(count > 0){
            response.setRtn(Response.SUCCESS);
            response.setMessage(Response.SUCCESS_MSG);
        }
        return null;
    }


}
