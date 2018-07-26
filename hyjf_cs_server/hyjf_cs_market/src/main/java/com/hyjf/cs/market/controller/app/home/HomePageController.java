/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.app.home;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.market.service.HomePageService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version HomePageController, v0.1 2018/7/26 10:15
 */
@Api(value = "app获取起始页广告信息", description = "app获取起始页广告信息")
@RestController
@RequestMapping("/hyjf-app/homepage")
public class HomePageController {

    @Autowired
    private HomePageService homePageService;

    /**
     * 获取起始页banner
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getStartPage", method = RequestMethod.POST ,produces = "application/json; charset=utf-8")
    public JSONObject getStartPage(HttpServletRequest request, HttpServletResponse response) {
        //LogUtil.startLog(HomePageDefine.THIS_CLASS, HomePageDefine.START_PAGE_ACTION);
        JSONObject result = new JSONObject();
        String platform = request.getParameter("realPlatform");
        if (StringUtils.isBlank(platform)) {
            platform = request.getParameter("platform");
        }
        result.put(CustomConstants.APP_REQUEST, "/hyjf-app/homepage/getStartPage");
        try {
            Map<String, Object> ads = new HashMap<String, Object>();
            ads.put("limitStart",0 );
            ads.put("limitEnd", 1);
            ads.put("host", "file.domain.url");
            ads.put("code", "startpage");
            if (platform.equals("2")) {
                ads.put("platformType","1");
            } else if (platform.equals("3")) {
                ads.put("platformType","2");
            }
            List<AppAdsCustomizeVO> picList = homePageService.searchBannerList(ads);
            if(picList == null || picList.size() == 0){
                result.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_FAIL);
                result.put(CustomConstants.APP_STATUS_DESC, "获取banner失败,暂无数据");
                return result;
            }
            //_log.info("platform======"+platform);
            //_log.info("picUrl======"+picList.get(0).getImage());
            //_log.info("actionUrl======"+picList.get(0).getUrl());
            result.put("picUrl",picList.get(0).getImage());
            if(StringUtils.isNotEmpty(picList.get(0).getUrl())){
                result.put("actionUrl", picList.get(0).getUrl());
            }else{
                result.put("actionUrl","");
            }
            result.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_SUCCESS);
            result.put(CustomConstants.APP_STATUS_DESC, CustomConstants.APP_STATUS_DESC_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            result.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_FAIL);
            result.put(CustomConstants.APP_STATUS_DESC, "获取banner出现异常");
            return result;
        }
        //LogUtil.endLog(HomePageDefine.THIS_CLASS, HomePageDefine.START_PAGE_ACTION);
        return result;
    }

}
