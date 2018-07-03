/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.exception.borrowregistexception;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.service.BorrowRegistService;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BorrowRegistExceptionController, v0.1 2018/7/3 10:55
 */
@RestController
@RequestMapping("/hyjf-admin/user_auth_exception")
@Api(value = "银行标的备案异常")
public class BorrowRegistExceptionController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BorrowRegistService borrowRegistService;
    /**
     * 查询银行标的备案异常list
     * @auth 孙沛凯
     * @param request 异常列表筛选检索条件
     * @return
     */
    @ApiOperation(value = "银行标的备案异常", notes = "银行标的备案异常list查询")
    @RequestMapping("/searchlist")
    public JSONObject searchList(BorrowRegistListRequest request){
        JSONObject jsonObject = new JSONObject();
        Integer count = borrowRegistService.getRegistCount(request);
        jsonObject.put("count",count);
        List<BorrowRegistCustomizeVO> borrowRegistCustomizeVOList = borrowRegistService.selectBorrowRegistList(request);
        if(null != borrowRegistCustomizeVOList && borrowRegistCustomizeVOList.size() > 0){
            jsonObject.put("list",borrowRegistCustomizeVOList);
        }
        return jsonObject;
    }
}
