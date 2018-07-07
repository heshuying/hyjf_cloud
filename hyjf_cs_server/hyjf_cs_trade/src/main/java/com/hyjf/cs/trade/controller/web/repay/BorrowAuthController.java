package com.hyjf.cs.trade.controller.web.repay;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.resquest.trade.BorrowAuthRequest;
import com.hyjf.am.vo.trade.repay.BorrowAuthCustomizeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.BorrowAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 借款人受托支付
 * @author hesy
 * @version BorrowAuthController, v0.1 2018/7/6 14:15
 */
@Api(value = "借款人受托支付相关")
@RestController
@RequestMapping("/web/borrowauth")
public class BorrowAuthController extends BaseTradeController {
    @Autowired
    BorrowAuthService borrowAuthService;

    /**
     * 借款人待授权列表
     * @auther: hesy
     * @date: 2018/7/6
     */
    @ApiOperation(value = "待授权列表", notes = "待授权列表")
    @PostMapping(value = "/list_auth", produces = "application/json; charset=utf-8")
    public WebResult<List<BorrowAuthCustomizeVO>> authList(@RequestHeader(value = "token", required = true) String token, @RequestBody BorrowAuthRequest requestBean, HttpServletRequest request){
        WebResult<List<BorrowAuthCustomizeVO>> result = new WebResult<List<BorrowAuthCustomizeVO>>();
        WebViewUserVO userVO = borrowAuthService.getUsersByToken(token);

        logger.info("获取待授权列表开始，requestBean：{}", JSON.toJSONString(requestBean));

        // 请求参数校验
        borrowAuthService.checkForAuthList(requestBean);

        Page page = Page.initPage(requestBean.getCurrPage(), requestBean.getPageSize());
        Integer count = borrowAuthService.selectAuthCount(requestBean);
        page.setTotal(count);

        try {
            List<BorrowAuthCustomizeVO> resultList = borrowAuthService.selectAuthList(requestBean);
            result.setData(resultList);
        } catch (Exception e) {
            logger.error("获取待授权列表异常", e);
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc(WebResult.ERROR_DESC);
        }

        result.setPage(page);
        return result;
    }

    /**
     * 借款人已授权列表
     * @auther: hesy
     * @date: 2018/7/6
     */
    @ApiOperation(value = "已授权列表", notes = "已授权列表")
    @PostMapping(value = "/list_authed", produces = "application/json; charset=utf-8")
    public WebResult<List<BorrowAuthCustomizeVO>> authedList(@RequestHeader(value = "token", required = true) String token, @RequestBody BorrowAuthRequest requestBean, HttpServletRequest request){
        WebResult<List<BorrowAuthCustomizeVO>> result = new WebResult<List<BorrowAuthCustomizeVO>>();
        WebViewUserVO userVO = borrowAuthService.getUsersByToken(token);

        logger.info("获取已授权列表开始，requestBean：{}", JSON.toJSONString(requestBean));

        // 请求参数校验
        borrowAuthService.checkForAuthList(requestBean);

        Page page = Page.initPage(requestBean.getCurrPage(), requestBean.getPageSize());
        Integer count = borrowAuthService.selectAuthedCount(requestBean);
        page.setTotal(count);

        try {
            List<BorrowAuthCustomizeVO> resultList = borrowAuthService.selectAuthedList(requestBean);
            result.setData(resultList);
        } catch (Exception e) {
            logger.error("获取已授权列表异常", e);
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc(WebResult.ERROR_DESC);
        }

        result.setPage(page);
        return result;
    }
}
