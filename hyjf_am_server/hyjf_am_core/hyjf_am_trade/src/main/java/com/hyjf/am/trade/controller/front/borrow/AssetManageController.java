package com.hyjf.am.trade.controller.front.borrow;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.app.AppAlreadyRepayListCustomizeResponse;
import com.hyjf.am.response.app.AppTenderToCreditListCustomizeResponse;
import com.hyjf.am.response.trade.AssetManageResponse;
import com.hyjf.am.response.trade.MyCreditDetailResponse;
import com.hyjf.am.response.trade.QueryMyProjectVOResponse;
import com.hyjf.am.response.trade.RepayPlanResponse;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.resquest.trade.WechatMyProjectRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowRecover;
import com.hyjf.am.trade.dao.model.auto.TenderAgreement;
import com.hyjf.am.trade.dao.model.customize.*;
import com.hyjf.am.trade.service.front.asset.AssetManageService;
import com.hyjf.am.trade.service.front.config.TenderAgreementService;
import com.hyjf.am.vo.app.AppTenderToCreditListCustomizeVO;
import com.hyjf.am.vo.trade.TenderCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.*;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version AssetManageController, v0.1 2018/6/24 14:47
 */
@RestController
@RequestMapping("/am-trade/assetmanage")
public class AssetManageController extends BaseController {
    @Autowired
    private AssetManageService assetManageService;

    @Autowired
    private TenderAgreementService tenderAgreementService;

    //初始化放款/承接时间(大于2018年3月28号法大大上线时间)
    private static final int ADD_TIME = 1922195200;

    //放款/承接时间(2018-3-28法大大上线时间）
    private static final int ADD_TIME328 = 1522195200;

    /**
     * @Description 获取用户当前持有债权列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectCurrentHoldObligatoryRightList")
    public AssetManageResponse selectCurrentHoldObligatoryRightList(@RequestBody AssetManageBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        List<CurrentHoldObligatoryRightListCustomize> list = assetManageService.selectCurrentHoldObligatoryRightList(request);
        if(!CollectionUtils.isEmpty(list)){
            for (CurrentHoldObligatoryRightListCustomize currentHoldObligatoryRightListCustomize : list) {
                String nid = currentHoldObligatoryRightListCustomize.getNid();
                //法大大居间服务协议（type=2时候，为债转协议）
                List<TenderAgreement> tenderAgreementsNid= tenderAgreementService.selectTenderAgreementByNid(nid);//居间协议
                if(tenderAgreementsNid!=null && tenderAgreementsNid.size()>0){
                    TenderAgreement tenderAgreement = tenderAgreementsNid.get(0);
                    Integer fddStatus = tenderAgreement.getStatus();
                    //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                    //System.out.println("******************1法大大协议状态："+tenderAgreement.getStatus());
                    if(fddStatus.equals(3)){
                        currentHoldObligatoryRightListCustomize.setFddStatus(1);
                    }else {
                        //隐藏下载按钮
                        //System.out.println("******************2法大大协议状态：0");
                        currentHoldObligatoryRightListCustomize.setFddStatus(2);
                    }
                }else {
                    /**
                     * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
                     * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
                     * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
                     */
                    //根据订单号获取用户放款信息
                    BorrowRecover borrowRecover =  tenderAgreementService.selectBorrowRecoverByNid(nid);
                    int addTime = ADD_TIME;
                    if(borrowRecover != null){
                        //放款记录创建时间（放款时间）
                        addTime =  (borrowRecover.getCreateTime() == null? 0 : GetDate.getTime10(borrowRecover.getCreateTime()));
                    }
                    if (addTime<ADD_TIME328) {
                        //下载老版本协议
                        currentHoldObligatoryRightListCustomize.setFddStatus(1);
                    } else {
                        //隐藏下载按钮
                        currentHoldObligatoryRightListCustomize.setFddStatus(0);
                    }
                }
            }

            List<CurrentHoldObligatoryRightListCustomizeVO> voList = CommonUtils.convertBeanList(list, CurrentHoldObligatoryRightListCustomizeVO.class);
            response.setCurrentHoldObligatoryRightList(voList);
        }
        return response;
    }

    /**
     * @Description 获取用户当前持有债权列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectCurrentHoldObligatoryRightListTotal")
    public AssetManageResponse selectCurrentHoldObligatoryRightListTotal(@RequestBody AssetManageBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        int currentHoldObligatoryRightCount = this.assetManageService.selectCurrentHoldObligatoryRightListTotal(request);

        response.setCurrentHoldObligatoryRightCount(currentHoldObligatoryRightCount);
        return response;
    }

    /**
     * @Description 获取用户已回款债权列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectRepaymentList")
    public AssetManageResponse selectRepaymentList(@RequestBody AssetManageBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        List<RepayMentListCustomize> list = assetManageService.selectRepaymentList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<RepayMentListCustomizeVO> voList = CommonUtils.convertBeanList(list, RepayMentListCustomizeVO.class);
            response.setRepayMentList(voList);
        }

        return response;
    }



    /**
     * @Description 获取用户已回款债权列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectRepaymentListTotal")
    public AssetManageResponse selectRepaymentListTotal(@RequestBody AssetManageBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        //int repayMentCount = this.assetManageService.selectRepaymentListTotal(request);
        int repayMentCount = this.assetManageService.countAlreadyRepayListRecordTotal(request);
        response.setRepayMentCount(repayMentCount);
        return response;
    }
    /**
     * @Description 获取用户已回款债权列表数量(产品加息需求迁移时添加)
     * @Author sunpeikai
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectRepaymentListTotalWeb")
    public AssetManageResponse selectRepaymentListTotalWeb(@RequestBody AssetManageBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        int repayMentCount = this.assetManageService.selectRepaymentListTotalWeb(request);

        response.setRepayMentCount(repayMentCount);
        return response;
    }

    /**
     * @Description 获取用户转让列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectCreditRecordList")
    public AssetManageResponse selectCreditRecordList(@RequestBody AssetManageBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        List<TenderCreditDetailCustomize> list = assetManageService.selectCreditRecordList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<TenderCreditDetailCustomizeVO> voList = CommonUtils.convertBeanList(list, TenderCreditDetailCustomizeVO.class);
            response.setCreditRecordList(voList);
        }

        return response;
    }

    /**
     * @Description 获取用户转让列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/countCreditRecordTotal")
    public AssetManageResponse countCreditRecordTotal(@RequestBody AssetManageBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        int tenderCreditDetailCount = this.assetManageService.countCreditRecordTotal(request);
        response.setTenderCreditDetailCount(tenderCreditDetailCount);
        return response;
    }

    /**
     * @Description 获取用户当前持有计划列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectCurrentHoldPlanList")
    public AssetManageResponse selectCurrentHoldPlanList(@RequestBody AssetManageBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        List<CurrentHoldPlanListCustomize> list = assetManageService.selectCurrentHoldPlanList(request);
        logger.info("list:{}", JSONObject.toJSONString(list));
        if(!CollectionUtils.isEmpty(list)){
            List<CurrentHoldPlanListCustomizeVO> voList = CommonUtils.convertBeanList(list, CurrentHoldPlanListCustomizeVO.class);
            response.setCurrentHoldPlanList(voList);
        }

        return response;
    }

    /**
     * @Description 获取用户当前持有计划列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/countCurrentHoldPlanTotal")
    public AssetManageResponse countCurrentHoldPlanTotal(@RequestBody AssetManageBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        int  currentHoldPlanCount= this.assetManageService.countCurrentHoldPlanTotal(request);

        response.setCurrentHoldPlanCount(currentHoldPlanCount);
        return response;
    }

    /**
     * @Description 获取用户已回款计划列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectRepayMentPlanList")
    public AssetManageResponse selectRepayMentPlanList(@RequestBody AssetManageBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        List<RepayMentPlanListCustomize> list = assetManageService.selectRepayMentPlanList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<RepayMentPlanListCustomizeVO> voList = CommonUtils.convertBeanList(list, RepayMentPlanListCustomizeVO.class);
            response.setRepayMentPlanList(voList);
        }

        return response;
    }

    /**
     * @Description 获取用户已回款计划列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/countRepayMentPlanTotal")
    public AssetManageResponse countRepayMentPlanTotal(@RequestBody AssetManageBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        int  repayMentPlanCount= this.assetManageService.countRepayMentPlanTotal(request);
        response.setRepayMentPlanCount(repayMentPlanCount);
        return response;
    }

    /**
     * @Description 微信端查询用户当前持有项目列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectWechatCurrentHoldObligatoryRightList")
    public QueryMyProjectVOResponse selectWechatCurrentHoldObligatoryRightList(@RequestBody WechatMyProjectRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        QueryMyProjectVOResponse response = new QueryMyProjectVOResponse();
        QueryMyProjectVO  vo= this.assetManageService.selectWechatCurrentHoldObligatoryRightList(request);
        response.setResult(vo);
        return response;
    }
    /**
     * @Description 微信端查询用户已回款项目列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectWechatRepaymentList")
    public QueryMyProjectVOResponse selectWechatRepaymentList(@RequestBody WechatMyProjectRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        QueryMyProjectVOResponse response = new QueryMyProjectVOResponse();
        QueryMyProjectVO vo= this.assetManageService.selectWechatRepaymentList(request);

        response.setResult(vo);
        return response;
    }
    /**
     * @Description 微信端查询用户债转项目列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectWechatCreditRecordList")
    public QueryMyProjectVOResponse selectWechatCreditRecordList(@RequestBody WechatMyProjectRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        QueryMyProjectVOResponse response = new QueryMyProjectVOResponse();
        QueryMyProjectVO  vo= this.assetManageService.selectWechatCreditRecordList(request);

        response.setResult(vo);
        return response;
    }
    /**
     * @Description 微信端查询用户当前持有计划列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectWechatCurrentHoldPlanList")
    public QueryMyProjectVOResponse selectWechatCurrentHoldPlanList(@RequestBody WechatMyProjectRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        QueryMyProjectVOResponse response = new QueryMyProjectVOResponse();
        QueryMyProjectVO  vo= this.assetManageService.selectWechatCurrentHoldPlanList(request);

        response.setResult(vo);
        return response;
    }
    /**
     * @Description 微信端查询用户已回款计划列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectWechatRepayMentPlanList")
    public QueryMyProjectVOResponse selectWechatRepayMentPlanList(@RequestBody WechatMyProjectRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        QueryMyProjectVOResponse response = new QueryMyProjectVOResponse();
        QueryMyProjectVO  vo= this.assetManageService.selectWechatRepayMentPlanList(request);
        response.setResult(vo);
        return response;
    }



    /**
     * @Description App获取用户当前持有债权列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectAppAlreadyRepayList")
    public AssetManageResponse selectAppAlreadyRepayList(@RequestBody AssetManageBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        List<AppAlreadyRepayListCustomize> list = assetManageService.selectAppAlreadyRepayList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<AppAlreadyRepayListCustomizeVO> voList = CommonUtils.convertBeanList(list, AppAlreadyRepayListCustomizeVO.class);
            response.setAppAlreadyRepayList(voList);
        }
        return response;
    }
    /**
     * @Description App 获取用户当前持有债权列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/searchAppCreditRecordList")
    public AssetManageResponse searchAppCreditRecordList(@RequestBody AssetManageBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        List<AppTenderCreditRecordListCustomize> list = assetManageService.searchAppCreditRecordList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<AppTenderCreditRecordListCustomizeVO> voList = CommonUtils.convertBeanList(list, AppTenderCreditRecordListCustomizeVO.class);
            response.setAppTenderCreditRecordList(voList);
        }
        return response;
    }

    /**
     * @Description 获取用户已回款计划列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectTenderToCreditListCount")
    public AssetManageResponse selectTenderToCreditListCount(@RequestBody AssetManageBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        int  tenderCreditDetailCount= this.assetManageService.selectTenderToCreditListCount(request);
        response.setTenderCreditDetailCount(tenderCreditDetailCount);
        return response;
    }


    /**
     * @Description App 获取用户计划列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/selectAppMyPlanList")
    public AssetManageResponse selectAppMyPlanList(@RequestBody AssetManageBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        List<AppMyPlanCustomize> list = assetManageService.selectAppMyPlanList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<AppMyPlanCustomizeVO> voList = CommonUtils.convertBeanList(list, AppMyPlanCustomizeVO.class);
            response.setAppMyPlanCustomizeList(voList);
        }
        return response;
    }

    /**
     * @Description App 获取用户计划列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping("/countAppMyPlan")
    public AssetManageResponse countAppMyPlan(@RequestBody AssetManageBeanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AssetManageResponse response = new AssetManageResponse();
        int  appMyPlanCount= this.assetManageService.countAppMyPlan(request);
        response.setAppMyPlanCount(appMyPlanCount);
        return response;
    }


    @PostMapping("/selectAlreadyRepayList")
    public AppAlreadyRepayListCustomizeResponse selectAlreadyRepayList(@RequestBody AssetManageBeanRequest request){
        AppAlreadyRepayListCustomizeResponse response = new AppAlreadyRepayListCustomizeResponse();
        List<AppAlreadyRepayListCustomize> lst = assetManageService.selectAlreadyRepayList(request);
        if (CollectionUtils.isNotEmpty(lst)){
            response.setResultList(CommonUtils.convertBeanList(lst,AppAlreadyRepayListCustomizeVO.class));
        }
        return response;
    }


    @PostMapping("/selectTenderToCreditList")
    public AppTenderToCreditListCustomizeResponse selectTenderToCreditList(@RequestBody Map<String, Object> params){
        AppTenderToCreditListCustomizeResponse response = new AppTenderToCreditListCustomizeResponse();
        List<AppTenderToCreditListCustomize> list=assetManageService.selectTenderToCreditList(params);
        if (CollectionUtils.isNotEmpty(list)){
            response.setResultList(CommonUtils.convertBeanList(list,AppTenderToCreditListCustomizeVO.class));
        }
        return response;
    }

    /**
     * 获取用户还款计划
     * @param borrowNid
     * @param nid
     * @param type
     * @return
     */
    @GetMapping("/getRepayPlanInfo/{borrowNid}/{nid}/{type}")
    public RepayPlanResponse getRepayPlanInfo(@PathVariable String borrowNid, @PathVariable String nid, @PathVariable String type){
        return assetManageService.getRepayPlanInfo(borrowNid, nid, type);
    }

    /**
     * 获取用户散标转让记录查看详情
     * @param creditNid
     * @return
     */
    @GetMapping("/getMyCreditAssignDetail/{creditNid}")
    public MyCreditDetailResponse getMyCreditAssignDetail(@PathVariable String creditNid){
        return assetManageService.getMyCreditAssignDetail(creditNid);
    }
}
