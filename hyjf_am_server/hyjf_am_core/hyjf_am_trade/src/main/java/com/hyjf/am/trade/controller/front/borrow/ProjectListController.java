/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.borrow;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 项目列表
 * @author liuyang
 * @version ProjectListController, v0.1 2018/6/13 11:15
 */
@RestController
@RequestMapping("/am-trade/projectlist")
public class ProjectListController extends BaseController {

    @Autowired
   private ProjectListService projectListService;

    /**
     * Web网站首页获取散标推荐
     * @param request
     * @return
     */
    @PostMapping("/web/searchProjectList")
    public ProjectListResponse searchProjectList(@RequestBody @Valid ProjectListRequest request){
        ProjectListResponse projectListResponse = new ProjectListResponse();
        List<WebProjectListCustomize> list = projectListService.searchProjectList(request);
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
    @RequestMapping("/web/countProjectList")
    public ProjectListResponse countProjectList(@RequestBody @Valid ProjectListRequest request){
        ProjectListResponse projectListResponse = new ProjectListResponse();
        int count = projectListService.countProjectList(request);
        projectListResponse.setCount(count);
        return projectListResponse;
    }

    /**
     * Web端散标和新手标的详情
     * @author zhangyk
     * @date 2018/6/23 14:23
     */
    @RequestMapping("/web/searchProjectDetail")
    public ProjectDetailResponse getProjectDetail(@RequestBody @Valid Map map){
        ProjectDetailResponse response = new ProjectDetailResponse();
        ProjectCustomeDetailVO vo = projectListService.getProjectDetail(map);
        // add by nxl 判断是否为产品加息 start
        if (null != vo) {
            int intFlg = Integer.parseInt(StringUtils.isNotBlank(vo.getIncreaseInterestFlag())?vo.getIncreaseInterestFlag():"0");
            BigDecimal dbYield = new BigDecimal(StringUtils.isNotBlank(vo.getBorrowExtraYield())?vo.getBorrowExtraYield():"0");
            boolean booleanVal = Validator.isIncrease(intFlg, dbYield);
            vo.setIsIncrease(String.valueOf(booleanVal));
            //平台所有利率（参考年回报率，历史年回报率，折让率，加息利率）
            // 全部统一为：小数点后一位（除非后台配置为小数点后两位且不为0时，则展示小数点后两位）
            // mod by nxl 20190409 start
            //历史年回报率
            String fromatBorr= FormatRateUtil.formatBorrowApr(vo.getBorrowApr());
            vo.setBorrowApr(fromatBorr);
            //加息利率
            String fromatExtraYield= FormatRateUtil.formatBorrowApr(vo.getBorrowExtraYield());
            vo.setBorrowExtraYield(fromatExtraYield);
            // mod by nxl 20190409 end

            // upd by liushouyi nifa2 20190214 start
            // 处理借款用途
            if(StringUtils.isNotBlank(vo.getFinancePurpose())){
                switch (vo.getFinancePurpose()) {
                    case "01":
                        vo.setFinancePurpose("个人消费");
                        break;
                    case "02":
                        vo.setFinancePurpose("个人经营");
                        break;
                    case "03":
                        vo.setFinancePurpose("个人资金周转");
                        break;
                    case "04":
                        vo.setFinancePurpose("房贷");
                        break;
                    case "05":
                        vo.setFinancePurpose("企业经营");
                        break;
                    case "06":
                        vo.setFinancePurpose("企业周转");
                        break;
                    default:
                        vo.setFinancePurpose("其他");
                        break;
                }
            }
            // upd by liushouyi nifa2 20190214 end
        }
        // add by nxl 判断是否为产品加息 end
        response.setResult(vo);
        return response;
    }

    /**
     * @desc  查询web端债转列表count
     * @author zhangyk
     * 原接口: com.hyjf.web.bank.web.user.credit.CreditController.searchWebCreditList()
     * @date 2018/6/19 15:12
     */
    @RequestMapping("/web/countCreditList")
    public CreditListResponse searchCreditListCount(@RequestBody @Valid CreditListRequest request){
        CreditListResponse CreditListResponse = new CreditListResponse();
        int count = projectListService.countCreditList(request);
        CreditListResponse.setCount(count);
        return CreditListResponse;
    }


    /**
     * @desc 查询web端债转列表数据
     * @author zhangyk
     * @date 2018/6/19 15:09
     */
    @RequestMapping("/web/searchWebCreditList")
    public CreditListResponse searchCreditList(@RequestBody @Valid CreditListRequest request){
        CreditListResponse res = new CreditListResponse();
        List<CreditListVO> list = projectListService.searchCreditList(request);
        if(null!=list&&list.size()>0){
            for(CreditListVO creditListVO:list){
                //平台所有利率（参考年回报率，历史年回报率，折让率，加息利率）
                // 全部统一为：小数点后一位（除非后台配置为小数点后两位且不为0时，则展示小数点后两位）
                // mod by nxl 20190409 start
                //历史年回报率
                String fromatBorr= FormatRateUtil.formatBorrowApr(creditListVO.getBidApr());
                creditListVO.setBidApr(fromatBorr);
                // mod by nxl 20190409 end
            }
        }
        res.setResultList(list);
        return res;
    }


    /**
     * 查询web端计划专区上部统计数据
     * @author zhangyk
     * @date 2018/6/21 15:40
     */
    @RequestMapping("/web/planData")
    public ProjectListResponse searchPlanData(@RequestBody ProjectListRequest request){
        ProjectListResponse res = new ProjectListResponse();
        Map<String,Object> map = projectListService.searchPlanData();
        res.setTotalData(map);
        return res;
    }

    /**
     * 查询web端计划专区计划列表count
     * @author zhangyk
     * @date 2018/6/21 15:49
     */
    @RequestMapping("/web/countPlanList")
    public ProjectListResponse countPlanList(@RequestBody @Valid  ProjectListRequest request){
        ProjectListResponse res = new ProjectListResponse();
        int count = projectListService.countWebPlanList(request);
        res.setCount(count);
        return res;
    }


    /**
     * 查询web端计划专区计划列表List
     * @author zhangyk
     * @date 2018/6/21 15:49
     */
    @RequestMapping("/web/searchPlanList")
    public HjhPlanResponse searchPlanList(@RequestBody @Valid  ProjectListRequest request){
        HjhPlanResponse res = new HjhPlanResponse();
        List<HjhPlanCustomize> list= projectListService.searchWebPlanList(request);
        if (CollectionUtils.isNotEmpty(list)){
            for(HjhPlanCustomize hjhPlanCustomize:list){
                //平台所有利率（参考年回报率，历史年回报率，折让率，加息利率）
                // 全部统一为：小数点后一位（除非后台配置为小数点后两位且不为0时，则展示小数点后两位）
                // mod by nxl 20190409
                String fromatBorr= FormatRateUtil.formatBorrowApr(hjhPlanCustomize.getPlanApr());
                hjhPlanCustomize.setPlanApr(fromatBorr);
            }
            res.setResultList(CommonUtils.convertBeanList(list,HjhPlanCustomizeVO.class));
        }
        return res;
    }


    /**
     * 查询web端计划基本详情()
     * @author zhangyk
     * @date 2018/7/14 17:59
     */
    @GetMapping("/web/searchPlanDetail/{planNid}")
    public HjhPlanDetailResponse searchPlanList(@PathVariable String planNid){
        HjhPlanDetailResponse res = new HjhPlanDetailResponse();
        PlanDetailCustomize detail= projectListService.getPlanDetail(planNid);
        if (detail != null){
            PlanDetailCustomizeVO detailCustomizeVO = CommonUtils.convertBean(detail,PlanDetailCustomizeVO.class);
            //平台所有利率（参考年回报率，历史年回报率，折让率，加息利率）
            // 全部统一为：小数点后一位（除非后台配置为小数点后两位且不为0时，则展示小数点后两位）
            // mod by nxl 20190409
            String fromatBorr= FormatRateUtil.formatBorrowApr(detailCustomizeVO.getPlanApr());
            detailCustomizeVO.setPlanApr(fromatBorr);
            res.setResult(detailCustomizeVO);
        }
        return res;
    }






    // --------------------------------------web end------------------------------------------
    //---------------------------------------app start------------------------------------------
    /**
     * App端获取散标出借
     * @param request
     * @return
     */
    @RequestMapping("/app/searchAppProjectList")
    public AppProjectListResponse searchAppProjectList(@RequestBody @Valid AppProjectListRequest request){
        AppProjectListResponse projectListResponse = new AppProjectListResponse();
        ProjectListRequest req = CommonUtils.convertBean(request,ProjectListRequest.class);
        List<AppProjectListCustomize> list = projectListService.searchAppProjectList(req);
        if(!CollectionUtils.isEmpty(list)){
            List<AppProjectListCustomizeVO> appProjectListCustomizeVOList = CommonUtils.convertBeanList(list,AppProjectListCustomizeVO.class);
            projectListResponse.setResultList(appProjectListCustomizeVOList);
        }
        return projectListResponse;
    }

    /**
     * app端散标出借count
     * @param request
     * @return
     */
    @RequestMapping("/app/countProjectList")
    public ProjectListResponse countAppProjectList(@RequestBody @Valid ProjectListRequest request){
        ProjectListResponse projectListResponse = new ProjectListResponse();
        int count = projectListService.countAppProjectList(request);
        projectListResponse.setCount(count);
        return projectListResponse;
    }

    /**
     * App端获取债转出借list
     * @param request
     * @return
     */
    @RequestMapping("/app/searchCreditList")
    public ProjectListResponse searchAppCreditList(@RequestBody @Valid ProjectListRequest request){
        ProjectListResponse projectListResponse = new ProjectListResponse();
        List<WebProjectListCustomize> list = projectListService.searchAppCreditList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<WebProjectListCustomizeVO> webProjectListCustomizeVO = CommonUtils.convertBeanList(list,WebProjectListCustomizeVO.class);
            projectListResponse.setResultList(webProjectListCustomizeVO);
        }
        return projectListResponse;
    }

    /**
     * app端获取债转出借count
     * @param request
     * @return
     */
    @RequestMapping("/app/countCreditList")
    public ProjectListResponse countAppCreditList(@RequestBody @Valid ProjectListRequest request){
        ProjectListResponse projectListResponse = new ProjectListResponse();
        int count = projectListService.countAppCreditList(request);
        projectListResponse.setCount(count);
        return projectListResponse;
    }

    /**
     * app端获取计划count
     * @author zhangyk
     * @date 2018/6/22 10:22
     */
    @RequestMapping("/app/countPlanList")
    public ProjectListResponse countAppPlanList(@RequestBody @Valid ProjectListRequest request){
        ProjectListResponse projectListResponse = new ProjectListResponse();
        int count = projectListService.countAppPlanList(request);
        projectListResponse.setCount(count);
        return projectListResponse;
    }

    /**
     * app端获取计划list
     * @author zhangyk
     * @date 2018/6/22 10:22
     */
    @RequestMapping("/app/searchPlanList")
    public HjhPlanResponse searchAppPlanList(@RequestBody @Valid ProjectListRequest request){
        HjhPlanResponse response = new HjhPlanResponse();
        List<HjhPlanVO> list = projectListService.searchAppPlanList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<HjhPlanCustomizeVO> hjhPlanCustomizeVOList = CommonUtils.convertBeanList(list,HjhPlanCustomizeVO.class);
            response.setResultList(hjhPlanCustomizeVOList);
        }
        return response;
    }


    @RequestMapping("/app/countProjectInvestRecordTotal")
    public int countProjectInvestRecordTotal(@RequestBody Map<String, Object> params){
        return projectListService.countProjectInvestRecordTotal(params);
    }


    /**
     * 散标出借记录
     * @return
     */
    @RequestMapping("/app/selectProjectInvestList")
    public AppProjectInvestListCustomizeResponse selectProjectInvestList(@RequestBody Map<String, Object> params){
        AppProjectInvestListCustomizeResponse response = new AppProjectInvestListCustomizeResponse();
        List<AppProjectInvestListCustomize> list=projectListService.selectProjectInvestList(params);
        if (CollectionUtils.isNotEmpty(list)){
            response.setResultList(CommonUtils.convertBeanList(list,AppProjectInvestListCustomizeVO.class));
        }
        return response;
    }


    /**
     * app端债转承接记录数
     * @param params
     * @return
     */
    @PostMapping("/app/countTenderCreditInvestRecordTotal")
    public int countTenderCreditInvestRecordTotal(@RequestBody Map<String, Object> params){
        return projectListService.countTenderCreditInvestRecordTotal(params);
    }


    /**
     * 债转承接记录列表
     * @param params
     * @return
     */
    @PostMapping("/app/searchTenderCreditInvestList")
    public AppTenderCreditInvestListCustomizeResponse searchTenderCreditInvestList(@RequestBody Map<String, Object> params){
        AppTenderCreditInvestListCustomizeResponse response = new AppTenderCreditInvestListCustomizeResponse();
        List<AppTenderCreditInvestListCustomize> list=projectListService.searchTenderCreditInvestList(params);
        if (CollectionUtils.isNotEmpty(list)){
            response.setResultList(CommonUtils.convertBeanList(list,AppTenderCreditInvestListCustomizeVO.class));
        }
        return response;

    }

    @GetMapping("/app/selectBorrowCreditByNid/{transferId}")
    public BorrowCreditResponse selectBorrowCreditByNid(@PathVariable String transferId){
        BorrowCreditResponse response = new BorrowCreditResponse();
        List<BorrowCredit> list = projectListService.selectBorrowCreditByNid(transferId);
        if (CollectionUtils.isNotEmpty(list)){
            response.setResultList(CommonUtils.convertBeanList(list,BorrowCreditVO.class));
        }
        return response;
    }
    /**
     * 根据订单号查询产品加息信息
     * @auth sunpeikai
     * @param orderId 订单id
     * @return
     */
    @GetMapping(value = "/app/getIncreaseInterestInvestByOrdId/{orderId}")
    public IncreaseInterestInvestResponse getIncreaseInterestInvestByOrdId(@PathVariable String orderId){
        IncreaseInterestInvestResponse response = new IncreaseInterestInvestResponse();
        IncreaseInterestInvest increaseInterestInvest = projectListService.getIncreaseInterestInvestByOrdId(orderId);
        if(increaseInterestInvest != null){
            IncreaseInterestInvestVO increaseInterestInvestVO = CommonUtils.convertBean(increaseInterestInvest,IncreaseInterestInvestVO.class);
            response.setResult(increaseInterestInvestVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 查询产品加息信息
     * @auth sunpeikai
     * @param tenderNid 对应tender表里的nid
     * @return
     */
    @GetMapping(value = "/app/getIncreaseInterestInvestByTenderNid/{tenderNid}")
    public IncreaseInterestInvestResponse getIncreaseInterestInvestByTenderNid(@PathVariable String tenderNid){
        IncreaseInterestInvestResponse response = new IncreaseInterestInvestResponse();
        IncreaseInterestInvest increaseInterestInvest = projectListService.getIncreaseInterestInvestByTenderNid(tenderNid);
        if(increaseInterestInvest != null){
            IncreaseInterestInvestVO increaseInterestInvestVO = CommonUtils.convertBean(increaseInterestInvest,IncreaseInterestInvestVO.class);
            response.setResult(increaseInterestInvestVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }


    // --------------------------------------app end-------------------------------------------------

    // --------------------------------------wechat start--------------------------------------------------

    /**
     * 微信端获取首页项目列表
     * @author zhangyk
     * @date 2018/6/22 10:22
     */
    @RequestMapping("/wechat/searchHomeProejctList")
    public WechatProjectListResponse searchHomeProejctList(@RequestBody @Valid Map<String,Object> map){
        WechatProjectListResponse response = new WechatProjectListResponse();
        List<WechatHomeProjectListVO> list = projectListService.searchWechatProjectList(map);
//        List<WechatHomeProjectListVO> list2=new ArrayList<WechatHomeProjectListVO>();
//        for (WechatHomeProjectListVO wechatHomeProjectListVO : list) {
//        	wechatHomeProjectListVO.setBorrowApr( FormatRateUtil.formatBorrowApr(wechatHomeProjectListVO.getBorrowApr()));
//        	list2.add(wechatHomeProjectListVO);
//		}
        response.setResultList(list);
        return response;
    }

    /**
     * 微信端首页汇计划加载两条稍后开启
     * @author zhangyk
     * @date 2018/6/22 10:22
     */
    @RequestMapping("/wechat/selectHomeHjhOpenLaterList")
    public WechatProjectListResponse selectHomeHjhOpenLaterList(){
        WechatProjectListResponse response = new WechatProjectListResponse();
        List<WechatHomeProjectListVO> list = projectListService.selectHomeHjhOpenLaterList();
        response.setResultList(list);
        return response;
    }


    /**
     * 微信端首页汇计划加载两条稍后开启
     * @author zhangyk
     * @date 2018/6/22 10:22
     */
    @GetMapping("/wechat/selectHomeRepaymentsProjectList")
    public WechatProjectListResponse selectHomeRepaymentsProjectList(){
        WechatProjectListResponse response = new WechatProjectListResponse();
        List<WechatHomeProjectListVO> list = projectListService.selectHomeRepaymentsProjectList();
        response.setResultList(list);
        return response;
    }

    // --------------------------------------wechat end--------------------------------------------------


    /*---------------------------------------api start ------------------------------------------------*/
    /**
     * api： 查询标的列表
     * @author zhangyk
     * @date 2018/8/27 14:47
     */
    @PostMapping("/api/getBorrowList")
    public ApiProjectListResponse getBorrowList(@RequestBody Map<String,Object> params){
        ApiProjectListResponse response = new ApiProjectListResponse();
        List<ApiProjectListCustomize> list = projectListService.getApiBorrowList(params);
        response.setResultList(list);
        return response;
    }

    /*---------------------------------------api end ------------------------------------------------*/

    /**
     * 首页汇计划推广计划列表 - 首页显示 ②	若没有可投计划，则显示锁定期限短的
     * @Author yangchangwei 2018/10/16
     * @param request
     * @return
     */
    @PostMapping("/apphomepage/selectIndexHjhExtensionPlanListByLockTime")
    public HjhPlanResponse selectIndexHjhExtensionPlanListByLockTime(@RequestBody @Valid AppHomePageRequest request){
        HjhPlanResponse response = new HjhPlanResponse();
        Map map = ConvertUtils.convertObjectToMap(request);
        List<HjhPlanCustomizeVO> resultList = projectListService.getIndexHjhExtensionPlanListByLockTime(map);
        response.setResultList(resultList);
        return response;
    }

    /**
     * 首页汇计划推广计划列表 - 首页显示
     * @Author yangchangwei 2018/10/16
     * @param request
     * @return
     */
    @PostMapping("/apphomepage/selectIndexHjhExtensionPlanList")
    public HjhPlanResponse selectIndexHjhExtensionPlanList(@RequestBody @Valid AppHomePageRequest request){
        HjhPlanResponse response = new HjhPlanResponse();
        Map map = ConvertUtils.convertObjectToMap(request);
        List<HjhPlanCustomizeVO> resultList = projectListService.getIndexHjhExtensionPlanList(map);
        response.setResultList(resultList);
        return response;
    }

    /**
     * 首页汇计划推广计划列表 - 首页显示
     * @Author yangchangwei 2018/10/16
     * @param request
     * @return
     */
    @PostMapping("/apphomepage/selectHomeProjectList")
    public AppProjectListResponse selectHomeProjectList(@RequestBody @Valid AppHomePageRequest request){
        AppProjectListResponse response = new AppProjectListResponse();
        Map map = ConvertUtils.convertObjectToMap(request);
        List<AppProjectListCustomizeVO> resultList = projectListService.getHomeProjectList(map);
        response.setResultList(resultList);
        return response;
    }

    /**
     * 首页还款中项目 - 首页显示
     * @Author yangchangwei 2019/3/3
     * @param request
     * @return
     */
    @PostMapping("/apphomepage/selectHomeRepayProjectList")
    public AppProjectListResponse selectRepayHomeProjectList(@RequestBody @Valid AppHomePageRequest request){
        AppProjectListResponse response = new AppProjectListResponse();
        Map map = ConvertUtils.convertObjectToMap(request);
        List<AppProjectListCustomizeVO> resultList = projectListService.getHomeRepayProjecList(map);
        response.setResultList(resultList);
        return response;
    }

    /**
     * app首页获取有效公告
     * @Author yangchangwei 2018/10/18
     * @return
     */
    @GetMapping("/apphomepage/getAnnouncements")
    public AppPushManageResponse selectAnnouncements(){

        AppPushManageResponse reponse = new AppPushManageResponse();
        List<AppPushManageVO> resultList = projectListService.getAnnouncements();
        reponse.setResultList(resultList);
        return reponse;
    }

    /**
     * app首页获取有效公告
     * @Author yangchangwei 2018/10/18
     * @return
     */
    @GetMapping("/apphomepage/getAnnouncementsByID/{id}")
    public AppPushManageResponse selectAnnouncementsByID(@PathVariable String id){

        AppPushManageResponse reponse = new AppPushManageResponse();
        AppPushManageVO result = projectListService.getAnnouncementsByID(id);
        reponse.setResult(result);
        return reponse;
    }
}
