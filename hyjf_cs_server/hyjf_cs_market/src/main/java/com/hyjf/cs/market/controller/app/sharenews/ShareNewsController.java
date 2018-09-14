package com.hyjf.cs.market.controller.app.sharenews;

import com.hyjf.am.response.market.ShareNewsResponse;
import com.hyjf.am.vo.market.ShareNewsBeanVO;
import com.hyjf.cs.market.config.SystemConfig;
import com.hyjf.cs.market.controller.BaseMarketController;
import com.hyjf.cs.market.service.ShareNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/26 16:29
 * @Description: ShareNewsController
 */
@Api(tags = "app端-app分享信息")
@RestController
@RequestMapping("/hyjf-app/shareNews")
public class ShareNewsController extends BaseMarketController {
    @Resource
    private ShareNewsService shareNewsService;
    @Resource
    private SystemConfig systemConfig;

    @ApiOperation(value = "分享信息", notes = "app-分享信息")
    @PostMapping("/getNews")
    public ShareNewsResponse init(@RequestHeader(value = "userId") Integer userId){
        ShareNewsResponse response = new ShareNewsResponse();

        ShareNewsBeanVO shareNewsBean = this.shareNewsService.queryShareNews();
        if (shareNewsBean.getTitle() != null) {
            try {
                response.setLinkUrl(systemConfig.getWechatQrcodeUrl().replace("{userId}", userId+""));
            } catch (Exception e) {
                response.setStatus("708");
                response.setStatusDesc("获取分享信息失败");
                return response;
            }
            response.setStatus("0");
            response.setStatusDesc("成功");
            response.setTitle(shareNewsBean.getTitle());
            response.setContent(shareNewsBean.getContent());
            response.setImg("https://www.hyjf.com" + shareNewsBean.getImg());// 提供绝对路径
        } else {
            response.setStatus("2");
            response.setStatusDesc("获取分享信息失败");
        }
        return response;
    }
}
