/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.app.find.contentArticle;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.config.service.app.AppContentArticleService;
import com.hyjf.am.response.app.AppContentArticleResponse;
import com.hyjf.am.vo.app.AppContentArticleVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dangzw
 * @version AppContentArticleController, v0.1 2018/7/31 0:53
 */
@Api(description = "APP端协议接口")
@RestController
@RequestMapping(value = "/am-config/find/contentArticle/")
public class AppContentArticleController extends BaseConfigController {

    private AppContentArticleService appContentArticleService;

    @RequestMapping("/getContentArticleById/{contentArticleId}")
    public AppContentArticleResponse getContentArticleById(@PathVariable(value = "contentArticleId") Integer contentArticleId){
        logger.info("contentArticleId:" + contentArticleId);
        AppContentArticleResponse response = new AppContentArticleResponse();
        ContentArticle list = appContentArticleService.getContentArticleById(contentArticleId);
        if(list != null){
            AppContentArticleVO voList = CommonUtils.convertBean(list, AppContentArticleVO.class);
            response.setResult(voList);
        }
        return response;
    }

}
