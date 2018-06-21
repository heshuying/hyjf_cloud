package com.hyjf.cs.trade.controller.web.assetmanage;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.EvalationVO;
import com.hyjf.am.vo.user.QuestionCustomizeVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.WebViewUser;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.controller.web.wirhdraw.WebBankWithdrawController;
import com.hyjf.cs.trade.service.AssetManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version WebAssetManageController, v0.1 2018/6/20 17:19
 */
@Api(value = "Web资产管理页面")
@RestController
@RequestMapping("/web/assetmanage")
public class WebAssetManageController extends BaseTradeController {
    private static final Logger logger = LoggerFactory.getLogger(WebAssetManageController.class);

    @Autowired
    private AssetManageService assetManageService;
    /**
     * @Description 资产管理页面初始化
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "获取用户资产信息", notes = "获取用户资产信息")
    @PostMapping(value = "/init")
    public Map<String,Object> init(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
        logger.info("web获取用户资产信息, token is :{}", JSONObject.toJSONString(token));
        Map<String,Object> result = new HashMap<>();
        WebViewUser user = RedisUtils.getObj(token, WebViewUser.class);

        String currentTab = request.getParameter("currentTab");
        AccountVO account = assetManageService.getAccount(user.getUserId());
        result.put("account", account);
        result.put("currentTab", currentTab);

        return result;
    }
}
