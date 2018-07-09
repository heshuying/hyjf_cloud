/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.platformtransfer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.PlatformTransferService;
import com.hyjf.am.resquest.admin.PlatformTransferListRequest;
import com.hyjf.am.vo.admin.AccountRechargeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: PlatformTransferController, v0.1 2018/7/9 10:13
 */
@Api(value = "资金中心-转账管理-平台转账")
@RestController
@RequestMapping(value = "/hyjf-admin/platformtransfer")
public class PlatformTransferController extends BaseController {

    @Autowired
    private PlatformTransferService platformTransferService;

    /**
     * 平台转账-查询转账列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "平台转账-查询转账列表",notes = "平台转账-查询转账列表")
    @PostMapping(value = "/transferlist")
    public JSONObject transferList(@RequestBody PlatformTransferListRequest request){
        JSONObject result = new JSONObject();
        Integer count = platformTransferService.getPlatformTransferCount(request);
        count = (count == null)?0:count;
        result.put("count",count);
        List<AccountRechargeVO> accountRechargeVOList = platformTransferService.searchPlatformTransferList(request);
        result.put("accountRechargeVOList",accountRechargeVOList);
        return result;
    }

}
