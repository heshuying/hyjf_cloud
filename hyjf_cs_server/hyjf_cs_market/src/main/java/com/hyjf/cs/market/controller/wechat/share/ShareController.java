package com.hyjf.cs.market.controller.wechat.share;

import com.hyjf.cs.market.vo.ShareVO;
import com.hyjf.cs.market.util.WxConfigUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiasq
 * @version ShareController, v0.1 2018/9/26 11:21
 */
@Api(tags = "wechat端-微信分享")
@RestController
@RequestMapping("/hyjf-wechat/wx/share")
public class ShareController {
    private static final Logger logger = LoggerFactory.getLogger(ShareController.class);

    /**
     * 微信公众号配置
     */
    @Value("${hyjf.wechat.app.appId}")
    private String WECHAT_APP_APPID;
    @Value("${hyjf.wechat.app.secret}")
    private String WECHAT_APP_SECRET;

    @ApiOperation(value = "微信分享", notes = "微信分享")
    @RequestMapping(value = "/doShare", method = RequestMethod.POST)
    public ShareVO doShare(@RequestHeader(required = false) Integer userId,
                           @RequestBody String url) {
        ShareVO resultBean = new ShareVO();
        logger.info("当前登录用户: {}", userId);
        WxConfigUtil.TicketBean ticketBean = WxConfigUtil.getSignature(url, WECHAT_APP_APPID,
                WECHAT_APP_SECRET, userId);

        if(resultBean!=null){
            BeanUtils.copyProperties(ticketBean, resultBean);

            resultBean.setUrl(url);
            resultBean.setAppId(WECHAT_APP_APPID);
            if (userId != null) {
                resultBean.setReffer(userId);
            }
        }

        return resultBean;
    }
}
