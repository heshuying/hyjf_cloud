/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.app.home;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.market.controller.BaseMarketController;
import com.hyjf.cs.market.service.HomePageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version HomePageController, v0.1 2018/7/26 10:15
 */
@Api(description = "app起始页信息获取", tags = "app起始页信息获取")
@RestController
@RequestMapping("/hyjf-app/homepage")
public class HomePageController extends BaseMarketController {
    private static final Logger logger = LoggerFactory.getLogger(HomePageController.class);
    @Autowired
    private HomePageService homePageService;

    //TODO @Value("${file.domain.url}")
    private String HOST_URL;

    /** @RequestMapping值 */
    public static final String REQUEST_HOME = "/hyjf-app";
    /** 首页接口  @RequestMapping值 */
    public static final String REQUEST_MAPPING = "/homepage";
    /** 首页项目列表  @RequestMapping值 */
    public static final String START_PAGE_ACTION = "/getStartPage";

    /**
     * 获取起始页banner
     * @param request
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "获取起始页广告信息", httpMethod = "POST", notes = "获取起始页广告信息")
    @ApiParam(required = true, name = "request", value = "查询条件")
    @PostMapping(value = "/getStartPage")
    public JSONObject getStartPage(HttpServletRequest request) {
        logger.info(HomePageController.class.toString(), "startLog -- /hyjf-app/homepage/getStartPage");
        JSONObject result = new JSONObject();
        String platform = request.getParameter("realPlatform");
        if (StringUtils.isBlank(platform)) {
            platform = request.getParameter("platform");
        }
        result.put(CustomConstants.APP_REQUEST, REQUEST_HOME + REQUEST_MAPPING + START_PAGE_ACTION);
        try {
            Map<String, Object> ads = new HashMap<String, Object>();
            ads.put("limitStart",0 );
            ads.put("limitEnd", 1);
            //TODO ads.put("host", HOST_URL);
            ads.put("host", "http://cdn.huiyingdai.com/");
            ads.put("code", "startpage");
            if ("2".equals(platform)) {
                ads.put("platformType","1");
            } else if ("3".equals(platform)) {
                ads.put("platformType","2");
            }
            List<AppAdsCustomizeVO> picList = homePageService.searchBannerList(ads);
            if(CollectionUtils.isEmpty(picList)){
                result.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_FAIL);
                result.put(CustomConstants.APP_STATUS_DESC, "获取banner失败,暂无数据");
                return result;
            }
            logger.info("platform======"+platform);
            logger.info("picUrl======"+picList.get(0).getImage());
            logger.info("actionUrl======"+picList.get(0).getUrl());
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
        logger.info(HomePageController.class.toString(), "endLog -- /hyjf-app/homepage/getStartPage");
        return result;
    }

    /**
     * 获取JumpCommend
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "获取JumpCommend", httpMethod = "GET", notes = "获取JumpCommend")
    @RequestMapping(value = "/getJumpCommend")
    public BaseResultBeanFrontEnd getJumpCommend() {
        logger.info(HomePageController.class.toString(), "startLog -- /hyjf-app/homepage/getJumpCommend");
        BaseResultBeanFrontEnd baseResultBeanFrontEnd=new BaseResultBeanFrontEnd();
        baseResultBeanFrontEnd.setStatus(BaseResultBeanFrontEnd.SUCCESS);
        baseResultBeanFrontEnd.setStatusDesc(BaseResultBeanFrontEnd.SUCCESS_MSG);
        logger.info(HomePageController.class.toString(), "endLog -- /hyjf-app/homepage/getJumpCommend");
        return baseResultBeanFrontEnd;
    }
}
