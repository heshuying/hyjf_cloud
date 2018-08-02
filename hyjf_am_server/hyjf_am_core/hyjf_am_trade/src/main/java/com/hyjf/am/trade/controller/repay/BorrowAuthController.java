package com.hyjf.am.trade.controller.repay;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BorrowAuthResponse;
import com.hyjf.am.response.trade.STZHWhiteListResponse;
import com.hyjf.am.resquest.trade.BorrowAuthRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.StzhWhiteList;
import com.hyjf.am.trade.service.repay.BorrowAuthService;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.repay.BorrowAuthCustomizeVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 借款人受托支付申请
 * @author hesy
 * @version BorrowAuthController, v0.1 2018/7/6 11:31
 */
@RestController
@RequestMapping("/am-trade/borrowauth")
public class BorrowAuthController extends BaseController {
    @Autowired
    BorrowAuthService borrowAuthService;

    /**
     * 获取借款人待授权总记录数
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/count_auth")
    public Integer selectAuthCount(@RequestBody @Valid BorrowAuthRequest requestBean) {
        return borrowAuthService.countBorrowNeedAuthRecordTotal(requestBean);
    }

    /**
     * 获取接口人待授权列表
     * @param: [requestBean]
     * @return: com.hyjf.am.response.trade.BorrowAuthResponse
     * @auther: hesy
     * @date: 2018/7/6 14:01
     */
    @RequestMapping(value = "/list_auth")
    public BorrowAuthResponse selectListAuth(@RequestBody @Valid BorrowAuthRequest requestBean) {
        BorrowAuthResponse responseBean = new BorrowAuthResponse();
        List<BorrowAuthCustomizeVO> resultList = borrowAuthService.searchBorrowNeedAuthList(requestBean);
        responseBean.setResultList(resultList);

        return responseBean;
    }

    /**
     * 获取借款人已授权总记录数
     * @auther: hesy
     * @date: 2018/7/6
     */
    @RequestMapping(value = "/count_authed")
    public Integer selectAuthedCount(@RequestBody @Valid BorrowAuthRequest requestBean) {
        return borrowAuthService.countBorrowAuthedRecordTotal(requestBean);
    }

    /**
     * 获取借款人已授权列表
     * @auther: hesy
     * @date: 2018/7/6
     */
    @RequestMapping(value = "/list_authed")
    public BorrowAuthResponse selectListAuthed(@RequestBody @Valid BorrowAuthRequest requestBean) {
        BorrowAuthResponse responseBean = new BorrowAuthResponse();
        List<BorrowAuthCustomizeVO> resultList = borrowAuthService.searchBorrowAuthedList(requestBean);
        responseBean.setResultList(resultList);

        return responseBean;
    }

    /**
     * 受托支付申请回调更新
     * @auther: hesy
     * @date: 2018/7/6
     */
    @GetMapping("/auth_update/{borrowNid}")
    public Integer updateTrusteePaySuccess(@PathVariable String borrowNid){
        if(StringUtils.isBlank(borrowNid)){
            logger.error("受托支付申请回调更新失败，请求参数不全");
            return 0;
        }

        try {
            borrowAuthService.updateTrusteePaySuccess(borrowNid);
            return 1;
        } catch (Exception e) {
            logger.error("受托支付申请更新异常", e);
            return 0;
        }
    }

    /**
     * 获取指定用户受托支付白名单
     * @auther: hesy
     * @date: 2018/7/6
     */
    @GetMapping("/get_whitelist/{userId}/{stzUserId}")
    public STZHWhiteListResponse getStzhWhiteList(@PathVariable Integer userId, @PathVariable Integer stzUserId){
        STZHWhiteListResponse response = new STZHWhiteListResponse();
        if(userId == null || stzUserId == null){
            logger.error("获取指定用户受托支付白名单，请求参数不全");
            response.setRtn(Response.FAIL);
            response.setMessage("请求参数不全");
        }

        StzhWhiteList record = borrowAuthService.getSTZHWhiteListByUserID(userId, stzUserId);
        if(record != null){
            STZHWhiteListVO vo = new STZHWhiteListVO();
            BeanUtils.copyProperties(record,vo);
            response.setResult(vo);
        }

        return response;
    }
}
