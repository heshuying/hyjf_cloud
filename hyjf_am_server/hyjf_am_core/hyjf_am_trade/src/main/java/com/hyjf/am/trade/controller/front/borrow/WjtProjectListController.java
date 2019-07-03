/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.borrow;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.AppPushManageResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.app.AppProjectInvestListCustomizeResponse;
import com.hyjf.am.response.app.AppProjectListResponse;
import com.hyjf.am.response.app.AppTenderCreditInvestListCustomizeResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.resquest.trade.AppHomePageRequest;
import com.hyjf.am.resquest.trade.AppProjectListRequest;
import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowCredit;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestInvest;
import com.hyjf.am.trade.dao.model.customize.*;
import com.hyjf.am.trade.service.front.borrow.ProjectListService;
import com.hyjf.am.trade.service.front.borrow.WjtProjectListService;
import com.hyjf.am.vo.admin.AppPushManageVO;
import com.hyjf.am.vo.api.ApiProjectListCustomize;
import com.hyjf.am.vo.app.AppProjectInvestListCustomizeVO;
import com.hyjf.am.vo.app.AppTenderCreditInvestListCustomizeVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.hjh.HjhPlanCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.trade.hjh.PlanDetailCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.ConvertUtils;
import com.hyjf.common.util.FormatRateUtil;
import com.hyjf.common.validator.Validator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 项目列表
 * @author pcc
 * @version WjtProjectListController, v0.1 2018/6/13 11:15
 */
@RestController
@RequestMapping("/am-trade/projectlist")
public class WjtProjectListController extends BaseController {

    @Autowired
   private WjtProjectListService wjtProjectListService;

    /**
     * Web网站首页获取散标推荐
     * @param request
     * @return
     */
    @PostMapping("/web/wjt/searchWjtWebProjectList")
    public ProjectListResponse searchWjtWebProjectList(@RequestBody @Valid ProjectListRequest request){
        logger.info("web端散标列表 requestBean:{}", JSON.toJSONString(request));
        ProjectListResponse projectListResponse = new ProjectListResponse();
        List<WebProjectListCustomize> list = wjtProjectListService.searchWjtWebProjectList(request);
        // add by nxl 判断是否为产品加息 start
        if(null!=list&&list.size()>0){
            for(WebProjectListCustomize webProjectListCustomize:list){
                int intFlg = Integer.parseInt(StringUtils.isNotBlank(webProjectListCustomize.getIncreaseInterestFlag())?webProjectListCustomize.getIncreaseInterestFlag():"0");
                BigDecimal dbYield=new BigDecimal(StringUtils.isNotBlank(webProjectListCustomize.getBorrowExtraYield())?webProjectListCustomize.getBorrowExtraYield():"0");
                boolean booleanVal = Validator.isIncrease(intFlg,dbYield);
                webProjectListCustomize.setIncrease(String.valueOf(booleanVal));
                //平台所有利率（参考年回报率，历史年回报率，折让率，加息利率）
                // 全部统一为：小数点后一位（除非后台配置为小数点后两位且不为0时，则展示小数点后两位）
                // mod by nxl 20190409 start
                //历史年回报率
                String fromatBorr= FormatRateUtil.formatBorrowApr(webProjectListCustomize.getBorrowApr());
                webProjectListCustomize.setBorrowApr(fromatBorr);
                //加息利率
                String fromatExtraYield= FormatRateUtil.formatBorrowApr(webProjectListCustomize.getBorrowExtraYield());
                webProjectListCustomize.setBorrowExtraYield(fromatExtraYield);
                // mod by nxl 20190409 end
            }
        }
        // add by nxl 判断是否为产品加息 end
        if(!CollectionUtils.isEmpty(list)){
            List<WebProjectListCustomizeVO> webProjectListCustomizeVO = CommonUtils.convertBeanList(list,WebProjectListCustomizeVO.class);
            projectListResponse.setResultList(webProjectListCustomizeVO);
        }
        return projectListResponse;
    }

    /**
     * 网站首页获取散标推荐数目
     * @param request
     * @return
     */
    @RequestMapping("/web/wjt/countWjtWebProjectList")
    public ProjectListResponse countWjtWebProjectList(@RequestBody @Valid ProjectListRequest request){
        ProjectListResponse projectListResponse = new ProjectListResponse();
        int count = wjtProjectListService.countWjtWebProjectList(request);
        projectListResponse.setCount(count);
        return projectListResponse;
    }
    /**
     * 微信端获取首页项目列表
     * @author zhangyk
     * @date 2018/6/22 10:22
     */
    @RequestMapping("/wechat/wjt/searchHomeProejctList")
    public WechatProjectListResponse searchHomeProejctList(@RequestBody @Valid Map<String,Object> map){
        WechatProjectListResponse response = new WechatProjectListResponse();
        List<WechatHomeProjectListVO> list = wjtProjectListService.searchWjtWechatProjectList(map);
        response.setResultList(list);
        return response;
    }
}
