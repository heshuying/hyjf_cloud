package com.hyjf.am.market.controller.app.find;

import com.hyjf.am.market.dao.model.customize.app.AppFindAdCustomize;
import com.hyjf.am.market.service.AdsService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.app.AppFindAdResponse;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.vo.app.find.AppFindAdCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wgx
 * @date 2019/4/16
 */
@RestController
@RequestMapping("/am-market/find")
public class AppFindController {

    @Autowired
    private AdsService adsService;

    /**
     * 获取app发现页顶部广告位
     * @return
     */
    @PostMapping("/getFindModules")
    public AppFindAdResponse getFindModules(@RequestBody AdsRequest adsRequest){
        AppFindAdResponse response = new AppFindAdResponse();
        String host = adsRequest.getHost();
        if(StringUtils.isBlank(host)){
            response.setRtn(Response.FAIL);
            response.setMessage("app前端地址不能为空");
            return response;
        }
        String platformType = adsRequest.getPlatformType();
        String baseCode;
        if("2".equals(platformType)){
            baseCode = "android_find_module";
            adsRequest.setPlatformType("1");
        }else if("3".equals(platformType)){
            baseCode = "ios_find_module";
            adsRequest.setPlatformType("2");
        }else{
            response.setRtn(Response.FAIL);
            response.setMessage("平台类型不正确");
            return response;
        }
        List resultList = new ArrayList();
        StringBuilder code = new StringBuilder(baseCode);
        adsRequest.setCode(code.append("1").toString());
        AppFindAdCustomize appAdsCustomize = adsService.searchAppFindAdCustomize(adsRequest);
        code = new StringBuilder(baseCode);
        adsRequest.setCode(code.append("2").toString());
        resultList.add(appAdsCustomize);
        appAdsCustomize = adsService.searchAppFindAdCustomize(adsRequest);
        code = new StringBuilder(baseCode);
        adsRequest.setCode(code.append("3").toString());
        resultList.add(appAdsCustomize);
        appAdsCustomize = adsService.searchAppFindAdCustomize(adsRequest);
        code = new StringBuilder(baseCode);
        adsRequest.setCode(code.append("4").toString());
        resultList.add(appAdsCustomize);
        appAdsCustomize = adsService.searchAppFindAdCustomize(adsRequest);
        resultList.add(appAdsCustomize);
        response.setResultList(CommonUtils.convertBeanList(resultList, AppFindAdCustomizeVO.class));
        return response;
    }

    /**
     * 获取app发现页广告banner
     * @return
     */
    @PostMapping("/getFindBanner")
    public AppFindAdResponse getFindBanner(@RequestBody AdsRequest adsRequest){
        AppFindAdResponse response = new AppFindAdResponse();
        String host = adsRequest.getHost();
        if(StringUtils.isBlank(host)){
            response.setRtn(Response.FAIL);
            response.setMessage("app前端地址不能为空");
            return response;
        }
        String platformType = adsRequest.getPlatformType();
        String code;
        if("2".equals(platformType)){
            code = "android_find_banner";
            adsRequest.setPlatformType("1");
        }else if("3".equals(platformType)){
            code = "ios_find_banner";
            adsRequest.setPlatformType("2");
        }else{
            response.setRtn(Response.FAIL);
            response.setMessage("平台类型不正确");
            return response;
        }
        adsRequest.setCode(code);
        AppFindAdCustomize appAdsCustomize = adsService.searchAppFindAdCustomize(adsRequest);
        response.setResult(CommonUtils.convertBean(appAdsCustomize, AppFindAdCustomizeVO.class));
        return response;
    }
}
