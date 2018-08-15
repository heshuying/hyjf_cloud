package com.hyjf.cs.market.controller.wechat.find;

import com.hyjf.am.response.config.WechatContentArticleResponse;
import com.hyjf.am.resquest.config.WechatContentArticleRequest;
import com.hyjf.cs.market.service.AppFindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lisheng
 * @version WechatContentArticleController, v0.1 2018/7/27 9:57
 */
@Api(tags = "wechat端-根据文章类型获取文章列表")
@RestController
@RequestMapping("/hyjf-wechat")
public class WechatContentArticleController {

    @Autowired
    private AppFindService appFindService;

    /**
     * 根据文章类型获取文章列表
     * @return
     */
    @ApiOperation(value = "根据文章类型获取文章列表", notes = "根据文章类型获取文章列表")
    @RequestMapping("/find/contentArticle/getWechatContentArticleListByType")
    public WechatContentArticleResponse getContentArticleListByType(@RequestBody  WechatContentArticleRequest form)
    {
        WechatContentArticleResponse wechatContentArticleResponse = appFindService.searchContentArticleList(form);
        wechatContentArticleResponse.setCurrentPage(form.getCurrentPage());
        return wechatContentArticleResponse;
    }


}