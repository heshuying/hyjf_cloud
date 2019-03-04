package com.hyjf.am.trade.controller.admin.borrow;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.admin.AdminBorrowRepaymentResponse;
import com.hyjf.am.response.admin.AdminRepayDelayResponse;
import com.hyjf.am.response.trade.BorrowRepayPlanResponse;
import com.hyjf.am.response.trade.BorrowRepayResponse;
import com.hyjf.am.resquest.admin.BorrowRepaymentPlanRequest;
import com.hyjf.am.resquest.admin.BorrowRepaymentRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowRepay;
import com.hyjf.am.trade.dao.model.auto.BorrowRepayPlan;
import com.hyjf.am.trade.dao.model.customize.*;
import com.hyjf.am.trade.service.admin.borrow.AdminBorrowRepaymentService;
import com.hyjf.am.vo.admin.AdminRepayDelayCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepaymentCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepaymentPlanCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayBeanVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanBeanVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRepaymentController, v0.1 2018/7/4 14:26
 */
@Api(value = "产品中心-汇直投-还款信息查询",tags ="产品中心-汇直投-还款信息查询")
@RestController
@RequestMapping("/am-trade/adminBorrowRepayment")
public class AdminBorrowRepaymentController extends BaseController {

    @Autowired
    AdminBorrowRepaymentService adminBorrowRepaymentService;

    @RequestMapping(value = "/countBorrowRepayment")
    public AdminBorrowRepaymentResponse countBorrowRepayment(@RequestBody @Valid BorrowRepaymentRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AdminBorrowRepaymentResponse response = new AdminBorrowRepaymentResponse();
        int count = this.adminBorrowRepaymentService.countBorrowRecover(request);

        response.setTotal(count);
        return response;
    }

    @RequestMapping(value = "/selectBorrowRepaymentList")
    public AdminBorrowRepaymentResponse selectBorrowRepaymentList(@RequestBody @Valid BorrowRepaymentRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AdminBorrowRepaymentResponse response = new AdminBorrowRepaymentResponse();

        List<AdminBorrowRepaymentCustomize> list = adminBorrowRepaymentService.selectBorrowRepaymentList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<BorrowRepaymentCustomizeVO> voList = CommonUtils.convertBeanList(list, BorrowRepaymentCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    @RequestMapping(value = "/sumBorrowRepaymentInfo")
    public AdminBorrowRepaymentResponse sumBorrowRepaymentInfo(@RequestBody @Valid BorrowRepaymentRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AdminBorrowRepaymentResponse response = new AdminBorrowRepaymentResponse();

        AdminBorrowRepaymentCustomize customize = adminBorrowRepaymentService.sumBorrowRecoverList(request);
        if(customize!=null){
            BorrowRepaymentCustomizeVO vo = CommonUtils.convertBean(customize,BorrowRepaymentCustomizeVO.class);
            response.setResult(vo);
        }
        return response;
    }

    @RequestMapping(value = "/countBorrowRepaymentPlan")
    public AdminBorrowRepaymentResponse countBorrowRepaymentPlan(@RequestBody @Valid BorrowRepaymentRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AdminBorrowRepaymentResponse response = new AdminBorrowRepaymentResponse();
        int count = this.adminBorrowRepaymentService.countBorrowRepaymentPlan(request);

        response.setTotal(count);
        return response;
    }

    @RequestMapping(value = "/selectBorrowRepaymentPlanList")
    public AdminBorrowRepaymentResponse selectBorrowRepaymentPlanList(@RequestBody @Valid BorrowRepaymentRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AdminBorrowRepaymentResponse response = new AdminBorrowRepaymentResponse();

        List<AdminBorrowRepaymentPlanCustomize> list = adminBorrowRepaymentService.selectBorrowRepaymentPlanList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<BorrowRepaymentPlanCustomizeVO> voList = CommonUtils.convertBeanList(list, BorrowRepaymentPlanCustomizeVO.class);
            response.setBorrowRepaymentPlanList(voList);
        }
        return response;
    }

    @RequestMapping(value = "/sumBorrowRepaymentPlanInfo")
    public AdminBorrowRepaymentResponse sumBorrowRepaymentPlanInfo(@RequestBody @Valid BorrowRepaymentRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AdminBorrowRepaymentResponse response = new AdminBorrowRepaymentResponse();

        AdminBorrowRepaymentPlanCustomize customize = adminBorrowRepaymentService.sumBorrowRepaymentPlanInfo(request);
        if(customize!=null){
            BorrowRepaymentPlanCustomizeVO vo = CommonUtils.convertBean(customize,BorrowRepaymentPlanCustomizeVO.class);
            response.setBorrowRepaymentPlanCustomizeVO(vo);
        }
        return response;
    }


    @RequestMapping(value = "/exportRepayClkActBorrowRepaymentInfoList")
    public AdminBorrowRepaymentResponse exportRepayClkActBorrowRepaymentInfoList(@RequestBody @Valid BorrowRepaymentPlanRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AdminBorrowRepaymentResponse response = new AdminBorrowRepaymentResponse();
        response.setTotal( adminBorrowRepaymentService.exportRepayClkActBorrowRepaymentInfoListCount(request));
        List<AdminBorrowRepaymentPlanCustomize> list = adminBorrowRepaymentService.exportRepayClkActBorrowRepaymentInfoList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<BorrowRepaymentPlanCustomizeVO> voList = CommonUtils.convertBeanList(list, BorrowRepaymentPlanCustomizeVO.class);
            response.setBorrowRepaymentPlanList(voList);
        }
        return response;
    }


    @RequestMapping(value = "/selectBorrowInfo/{borrowNid}")
    public AdminRepayDelayResponse selectBorrowInfo(@PathVariable String borrowNid){
        logger.info("项目订单编号:" +borrowNid);
        AdminRepayDelayResponse response = new AdminRepayDelayResponse();
        try{
        AdminRepayDelayCustomize customize = adminBorrowRepaymentService.selectBorrowInfo(borrowNid);
        if(customize!=null){
            AdminRepayDelayCustomizeVO vo = CommonUtils.convertBean(customize,AdminRepayDelayCustomizeVO.class);
            response.setResult(vo);
        }
        return response;
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value = "/getBorrowRepayDelay/{borrowNid}/{borrowApr}/{borrowStyle}")
    public BorrowRepayResponse getBorrowRepayDelay(@PathVariable String borrowNid,@PathVariable String borrowApr,@PathVariable String borrowStyle) throws ParseException {
        logger.info("项目订单编号:" +borrowNid);
        logger.info("项目年化利率:" +borrowApr);
        logger.info("项目类型:" +borrowStyle);

        BorrowRepayResponse response = new BorrowRepayResponse();
        try{
            BorrowRepay customize = adminBorrowRepaymentService.getBorrowRepayDelay(borrowNid,borrowApr,borrowStyle);
            if(customize!=null){
                BorrowRepayVO vo = CommonUtils.convertBean(customize,BorrowRepayVO.class);
                response.setResult(vo);
            }
            return response;
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value = "/getBorrowRepayPlanDelay/{borrowNid}/{borrowApr}/{borrowStyle}")
    public BorrowRepayPlanResponse getBorrowRepayPlanDelay(@PathVariable String borrowNid,@PathVariable String borrowApr,@PathVariable String borrowStyle) throws ParseException {
        logger.info("项目订单编号:" +borrowNid);
        logger.info("项目年化利率:" +borrowApr);
        logger.info("项目类型:" +borrowStyle);

        BorrowRepayPlanResponse response = new BorrowRepayPlanResponse();
        try{
            BorrowRepayPlan customize = adminBorrowRepaymentService.getBorrowRepayPlanDelay(borrowNid,borrowApr,borrowStyle);
            if(customize!=null){
                BorrowRepayPlanVO vo = CommonUtils.convertBean(customize,BorrowRepayPlanVO.class);
                response.setResult(vo);
            }
            return response;
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value = "/getBorrowRepayInfo/{borrowNid}/{borrowApr}/{borrowStyle}")
    public BorrowRepayResponse getBorrowRepayInfo(@PathVariable String borrowNid,@PathVariable String borrowApr,@PathVariable String borrowStyle) throws ParseException {

        BorrowRepayResponse response = new BorrowRepayResponse();
        try{
            BorrowRepayBean customize = adminBorrowRepaymentService.getBorrowRepayInfo(borrowNid,borrowApr,borrowStyle);
            if(customize!=null){
                BorrowRepayBeanVO vo = CommonUtils.convertBean(customize,BorrowRepayBeanVO.class);
                response.setBorrowRepayBeanVO(vo);
            }
            return response;
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value = "/getBorrowRepayPlanInfo/{borrowNid}/{borrowApr}/{borrowStyle}")
    public BorrowRepayPlanResponse getBorrowRepayPlanInfo(@PathVariable String borrowNid,@PathVariable String borrowApr,@PathVariable String borrowStyle) throws ParseException {

        BorrowRepayPlanResponse response = new BorrowRepayPlanResponse();
        try{
            BorrowRepayPlanBean customize = adminBorrowRepaymentService.getBorrowRepayPlanInfo(borrowNid,borrowApr,borrowStyle);
            if(customize!=null){
                BorrowRepayPlanBeanVO vo = CommonUtils.convertBean(customize,BorrowRepayPlanBeanVO.class);
                response.setBorrowRepayPlanBeanVO(vo);
            }
            return response;
        }catch (Exception e){
            logger.error(this.getClass().getName(), "getBorrowRepayPlanInfo", e);
        }
        return response;
    }


    // 更新用户的交易明细
    @RequestMapping(value = "/updateBorrowRepayDelayDays/{borrowNid}/{delayDays}", method = RequestMethod.GET)
    public IntegerResponse updateBorrowRepayDelayDays(@PathVariable String borrowNid, @PathVariable String delayDays) {
        IntegerResponse response=new IntegerResponse();
        try {
            response.setResultInt(adminBorrowRepaymentService.updateBorrowRepayDelayDays(borrowNid,delayDays));
            return response;
        } catch (ParseException e) {
            logger.error(this.getClass().getName(), "updateBorrowRepayDelayDays", e);
        }
        return response;
    }
}
