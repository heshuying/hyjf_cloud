package com.hyjf.cs.market.controller.app.sharenews;

import com.hyjf.am.response.market.ShareNewsResponse;
import com.hyjf.am.resquest.market.ShareNewsRequest;
import com.hyjf.am.vo.market.ShareNewsBeanVO;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.market.config.SystemConfig;
import com.hyjf.cs.market.controller.BaseMarketController;
import com.hyjf.cs.market.service.ShareNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/26 16:29
 * @Description: ShareNewsController
 */
@Api(value = "app分享信息", tags = "ap分享信息")
@RestController
@RequestMapping("/hyjf-app/shareNews")
public class ShareNewsController extends BaseMarketController {
    @Resource
    private ShareNewsService shareNewsService;
    @Resource
    private SystemConfig systemConfig;

    @ApiOperation(value = "分享信息", notes = "app-分享信息")
    @PostMapping("/getNews")
    public ShareNewsResponse init(HttpServletRequest request, @RequestBody ShareNewsRequest shareNewsRequest){
        ShareNewsResponse response = new ShareNewsResponse();

// 检查参数正确性
        if (Validator.isNull(shareNewsRequest.getVersion()) || Validator.isNull(shareNewsRequest.getNetStatus()) || Validator.isNull(shareNewsRequest.getPlatform())
                || Validator.isNull(shareNewsRequest.getSign())) {
            response.setStatus("1");
            response.setStatusDesc("请求参数非法");
            return response;
        }

        // // 判断sign是否存在
        // boolean isSignExists = SecretUtil.isExists(sign);
        // if (!isSignExists) {
        // ret.put("status", "1");
        // ret.put("statusDesc", "请求参数非法");
        // return ret;
        // }

        // 取得加密用的Key
        String key = SecretUtil.getKey(shareNewsRequest.getSign());
        if (Validator.isNull(key)) {
            response.setStatus("1");
            response.setStatusDesc("请求参数非法");
            return response;
        }

        ShareNewsBeanVO shareNewsBean = this.shareNewsService.queryShareNews();
        if (shareNewsBean.getTitle() != null) {
            if (null != SecretUtil.getToken(shareNewsRequest.getSign())) {// 如果用户是登陆状态 拼接用户编号
                Integer userId = SecretUtil.getUserId(shareNewsRequest.getSign());
                try {
                    response.setLinkUrl(systemConfig.getWechatQrcodeUrl().replace("{userId}", userId+""));
                } catch (Exception e) {
                    response.setLinkUrl(systemConfig.getWechatQrcodeUrl());
                }

            } else {
                response.setLinkUrl(systemConfig.getWechatQrcodeUrl());
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