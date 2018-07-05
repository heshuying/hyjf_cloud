/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.associatedrecords;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: sunpeikai
 * @version: AssociatedRecodesController, v0.1 2018/7/5 11:25
 */
@Api(value = "资金中心-定向转账-关联记录")
@RestController
@RequestMapping(value = "/hyjf-admin/associatedrecords")
public class AssociatedRecodesController {

    /**
     * 定向转账列表
     * @param request
     * @return
     */
    @ApiOperation(value = "查询关联记录列表",notes = "查询关联记录列表")
    @PostMapping(value = "/getassociatedrecordlist")
    public JSONObject getAssociatedRecordList(){

        return null;
    }
}
