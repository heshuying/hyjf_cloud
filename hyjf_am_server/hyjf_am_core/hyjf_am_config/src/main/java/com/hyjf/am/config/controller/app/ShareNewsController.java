package com.hyjf.am.config.controller.app;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.controller.admin.content.CategoryController;
import com.hyjf.am.config.dao.model.auto.Category;
import com.hyjf.am.config.service.app.ShareNewsService;
import com.hyjf.am.response.admin.CategoryResponse;
import com.hyjf.am.response.config.ShareNewsResponse;
import com.hyjf.am.vo.market.ShareNewsBeanVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/27 09:18
 * @Description: ShareNewsController
 */
@RestController
@RequestMapping("/am-config/article")
public class ShareNewsController extends BaseConfigController {
    private static final Logger logger = LoggerFactory.getLogger(ShareNewsController.class);

    @Resource
    private ShareNewsService shareNewsService;

    /**
     * @Author walter.limeng
     * @Description  获取分享信息
     * @Date 9:26 2018/7/27
     * @Param
     * @return ShareNewsResponse
     */
    @RequestMapping("/querysharenews")
    public ShareNewsResponse queryShareNews() {
        ShareNewsResponse response = new ShareNewsResponse();
        ShareNewsBeanVO shareNewsBeanVO = shareNewsService.queryShareNews();
        response.setResult(shareNewsBeanVO);
        return response;
    }
}
