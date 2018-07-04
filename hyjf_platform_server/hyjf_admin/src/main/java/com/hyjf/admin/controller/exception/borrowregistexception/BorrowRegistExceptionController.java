/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.exception.borrowregistexception;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.BorrowRegistExceptionService;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BorrowRegistExceptionController, v0.1 2018/7/3 10:55
 */
@RestController
@RequestMapping("/hyjf-admin/borrow_regist_exception")
@Api(value = "异常中心-标的备案掉单")
public class BorrowRegistExceptionController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BorrowRegistExceptionService borrowRegistExceptionService;
    /**
     * 查询银行标的备案异常list
     * @auth 孙沛凯
     * @param request 异常列表筛选检索条件
     * @return
     */
    @ApiOperation(value = "银行标的备案异常", notes = "银行标的备案异常list查询")
    @PostMapping(value = "/searchlist")
    public JSONObject searchList(@RequestBody BorrowRegistListRequest request){
        JSONObject jsonObject = new JSONObject();
        // 数据总数
        Integer count = borrowRegistExceptionService.getRegistCount(request);
        jsonObject.put("count",count);
        // 异常列表list
        List<BorrowRegistCustomizeVO> borrowRegistCustomizeVOList = borrowRegistExceptionService.selectBorrowRegistList(request);
        jsonObject.put("recordList",borrowRegistCustomizeVOList);
        // 筛选条件 项目类型list
        List<BorrowProjectTypeVO> borrowProjectTypeVOList = borrowRegistExceptionService.selectBorrowProjectList();
        jsonObject.put("borrowProjectTypeList",borrowProjectTypeVOList);
        // 筛选条件 还款方式 list
        List<BorrowStyleVO> borrowStyleVOList = borrowRegistExceptionService.selectBorrowStyleList();
        jsonObject.put("borrowStyleList",borrowStyleVOList);
        return jsonObject;
    }
    /**
     * 标的备案异常处理
     * @auth 孙沛凯
     * @param request 异常列表筛选检索条件
     * @return
     */
    @ApiOperation(value = "银行标的备案异常", notes = "银行标的备案异常处理")
    @PostMapping(value = "/borrowregisthandleexception")
    public JSONObject borrowRegistHandleException(@RequestHeader(value = "userId")Integer userId, @RequestBody BorrowRegistListRequest request){
        JSONObject jsonObject = new JSONObject();
        String borrowNid = request.getBorrowNidSrch();
        logger.info("borrowRegistHandleException::::::::::userId=[{}],borrowNid=[{}]",userId,borrowNid);
        if(StringUtils.isBlank(borrowNid)){
            jsonObject.put("success","1");
            jsonObject.put("msg","项目编号为空");
        }else{
            jsonObject = borrowRegistExceptionService.handleBorrowRegistException(borrowNid,userId);
        }
        return jsonObject;
    }
}
