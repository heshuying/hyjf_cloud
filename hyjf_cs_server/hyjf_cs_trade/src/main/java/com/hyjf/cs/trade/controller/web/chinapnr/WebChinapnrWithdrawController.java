/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.web.chinapnr;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.chinapnr.ChinapnrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version WebChinapnrWithdrawController, v0.1 2018/9/5 15:23
 */
@Api(tags = "web端-用户汇付提现接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-web/chinapnr/withdraw")
public class WebChinapnrWithdrawController extends BaseTradeController {

    @Autowired
    ChinapnrService chinapnrService;

    /**
     * 跳转到提现页面
     * @param userId
     * @return
     */
    @ApiOperation(value = "跳转到提现页面")
    @PostMapping("/toWithdraw")
    public WebResult toWithdraw(@RequestHeader(value = "userId") Integer userId) {
        WebResult result = new WebResult();
        Map<String,Object> map = chinapnrService.toWithdraw(userId);
        result.setData(map);
        return result;
    }

    /**
     * 用户提现
     * @param userId
     * @param map
     * @return
     */
    @ApiOperation(value = "用户提现")
    @ApiImplicitParam(name = "param",value = "{money:String,cardId:String,cashchl:String}",dataType = "Map")
    @RequestMapping("/cash")
    public WebResult cash(@RequestHeader(value = "userId") Integer userId,@RequestBody Map<String,String> map,HttpServletRequest request){
        WebResult result = new WebResult();
        UserVO user = chinapnrService.getUserByUserId(userId);
        // 用户名
        String userName = user.getUsername();
        // 交易金额
        String transAmt = map.get("money");
        // 提现银行卡号
        String bankId = map.get("cardId");
        // 取现渠道(暂时无用)
        String cashchl = map.get("cashchl");
        // 检查参数
        chinapnrService.checkParam(userId, transAmt, bankId);
        //汇付提现
        Map<String, Object> resultMap = chinapnrService.cash(userId, transAmt, bankId,cashchl,userName,CustomUtil.getIpAddr(request));
        result.setData(resultMap);
        return result;
    }

}
