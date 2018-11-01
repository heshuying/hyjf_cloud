/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.web.landingpage;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.LandingPageBean;
import com.hyjf.am.vo.market.AdsVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.ic.CalculateInvestInterest;
import com.hyjf.cs.message.service.landingpage.LandingPageService;
import com.hyjf.cs.message.util.RSAJSPUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author wangjun
 * @version LandingPageController, v0.1 2018/7/30 11:58
 */
@Api(value = "web端-着陆页用户信息接口",tags = "web端-着陆页用户信息接口")
@RestController
@RequestMapping("/hyjf-web/landingPage")
public class WebLandingPageController extends BaseController {

    @Autowired
    private LandingPageService landingPageService;
    // 着陆页 banner1 type
    private static final String LAND_ADS_TYPE1 = "10";

    // 着陆页banner2 type
    private static final String LAND_ADS_TYPE2 = "11";

    @ApiOperation(value = "获取着陆页用户信息",notes = "获取着陆页用户信息")
    @PostMapping(value = "/init")
    public WebResult init(HttpServletRequest request, HttpServletResponse response, @RequestHeader(value = "userId" ,required = false) Integer userId,
                          @RequestBody LandingPageBean form){
        WebResult webResult = new WebResult();
        JSONObject jsonObject = new JSONObject();
        long t= GetDate.getMillis();
        //毫秒数 13位  记录初始化时间
        jsonObject.put("inittime",t);
        // 着陆页banner1 List
        List<AdsVO> landingPageBanner1 = this.landingPageService.getAdsList(LAND_ADS_TYPE1);
        if(userId==null){
            jsonObject.put("isLogin",0);
        }else {
            jsonObject.put("isLogin",1);
        }
/*        String utmId = form.getUtm_id();
        CookieUtils.addCookie(request, response, "utm_id", utmId, null,
                InterceptorDefine.HYJF_WEB_DOMAIN_NAMES_LIST.get(0));*/
        CalculateInvestInterest calculateInvestInterest = this.landingPageService.getTenderSum();
        if(calculateInvestInterest != null){
            //投资总额(亿元)
            jsonObject.put("tenderSum", calculateInvestInterest.getTenderSum()==null?new BigDecimal(0):calculateInvestInterest.getTenderSum());
            //收益总额(亿元)
            jsonObject.put("interestSum", calculateInvestInterest.getInterestSum()==null?new BigDecimal(0):calculateInvestInterest.getInterestSum());
        }else{
            //投资总额(亿元)
            jsonObject.put("tenderSum", "0.00");
            //收益总额(亿元)
            jsonObject.put("interestSum", "0.00");
        }
        // 着陆页banner2 List
        List<AdsVO> landingPageBanner2 = this.landingPageService.getAdsList(LAND_ADS_TYPE2);
        form.setLandingPageBanner1(landingPageBanner1);
        form.setLandingPageBanner2(landingPageBanner2);
        jsonObject.put("landingpageForm", form);
        request.getSession().setAttribute("from_id", form.getFrom_id());
        //加密返回的两个参数
        jsonObject.put("pubexponent", "10001");
        jsonObject.put("pubmodules", RSAJSPUtil.getPunlicKeys());
        webResult.setData(jsonObject);
        return webResult;
    }
}
