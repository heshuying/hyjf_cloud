package com.hyjf.am.trade.controller.admin.productcenter.applyagreement;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.ApplyAgreementResponse;
import com.hyjf.am.response.trade.BorrowRepayAgreementResponse;
import com.hyjf.am.resquest.admin.ApplyAgreementRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.ApplyAgreement;
import com.hyjf.am.trade.dao.model.customize.BorrowRepayAgreementCustomize;
import com.hyjf.am.trade.service.admin.productcenter.applyagreement.ApplyAgreementService;
import com.hyjf.am.vo.trade.BorrowRepayAgreementVO;
import com.hyjf.am.vo.trade.borrow.ApplyAgreementVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.ConvertUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 垫付协议管理（原子层）
 * @version ApplyAgreementController, v0.1 2018/8/14 14:19
 * @Author: Zha Daojian
 */

@RestController
@RequestMapping("/am-trade/applyAgreement")
public class ApplyAgreementController extends BaseController {

    @Autowired
    private ApplyAgreementService applyAgreementService;
    /**
     * 垫付协议申请明细列表
     * @auther: Zha Daojian
     * @date: 2018/8/14 14:19
     */
    @RequestMapping("/getApplyAgreementList")
    public ApplyAgreementResponse getApplyAgreementList(@RequestBody ApplyAgreementRequest request) {
        ApplyAgreementResponse response = new ApplyAgreementResponse();
        Integer count = applyAgreementService.getListTotal(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count);
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("getApplyAgreementList::::::::::currPage=[{}],limitStart=[{}],limitEnd=[{}]",request.getCurrPage(),request.getLimitStart(),request.getLimitEnd());
        List<ApplyAgreement> applyAgreementList = applyAgreementService.getList(request);
        if(!CollectionUtils.isEmpty(applyAgreementList)){
            List<ApplyAgreementVO> applyAgreementVOList= CommonUtils.convertBeanList(applyAgreementList,ApplyAgreementVO.class);
            response.setCount(count);
            response.setResultList(applyAgreementVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
    /**
     * 垫付协议申请明细列表页count
     * @auther: Zha Daojian
     * @date: 2018/8/14 14:19
     */
    @RequestMapping("/getApplyAgreementCount")
    public Integer getApplyAgreementCount(@RequestBody ApplyAgreementRequest request){
        return  applyAgreementService.getListTotal(request);
    }

    /**
     * 垫付协议申请明细列表页--分期
     * @auther: Zha Daojian
     * @date: 2018/8/14 14:19
     */
    @RequestMapping("/getAddApplyAgreementPlanList")
    public BorrowRepayAgreementResponse getAddApplyAgreementPlanList(@RequestBody BorrowRepayAgreementRequest request){
        BorrowRepayAgreementResponse response = new BorrowRepayAgreementResponse();
        Integer total = getAddApplyAgreementPlanCount(request);
        Paginator paginator = new Paginator(request.getCurrPage(), total,request.getPageSize());
        if(request.getPageSize() ==0){
            paginator = new Paginator(request.getCurrPage(), total);
        }
        int limitStart = paginator.getOffset();
        int limitEnd = paginator.getLimit();

        if(request.getCurrPage() == -1){
            limitStart = -1;
            limitEnd = -1;
        }
        Map map = ConvertUtils.convertObjectToMap(request);
        List<BorrowRepayAgreementCustomize> list =  applyAgreementService.getListPlay(map,limitStart,limitEnd);
        List<BorrowRepayAgreementVO> voList = null;
        if(!CollectionUtils.isEmpty(list)){
            voList = CommonUtils.convertBeanList(list, BorrowRepayAgreementVO.class);
        }
        response.setCount(total);
        response.setResultList(voList);
        return response;
    }

    /**
     *垫付协议申请明细列表页--分期
     * @param request
     * @return
     */
    @ApiOperation(value = "垫付协议申请明细列表页--分总条数")
    @PostMapping("/getAddApplyAgreementPlanCount")
    public Integer getAddApplyAgreementPlanCount(@RequestBody BorrowRepayAgreementRequest request) {
        Map map = ConvertUtils.convertObjectToMap(request);
        Integer count = applyAgreementService.getListTotalPlan(map);
        return count;
    }


    /**
     * 垫付协议申请明细列表页--分期
     * @auther: Zha Daojian
     * @date: 2018/8/14 14:19
     */
    @RequestMapping("/getAddApplyAgreementList")
    public BorrowRepayAgreementResponse getAddApplyAgreementList(@RequestBody BorrowRepayAgreementRequest request){
        BorrowRepayAgreementResponse response = new BorrowRepayAgreementResponse();
        Integer total = getAddApplyAgreementCount(request);
        Paginator paginator = new Paginator(request.getCurrPage(), total,request.getPageSize());
        if(request.getPageSize() ==0){
            paginator = new Paginator(request.getCurrPage(), total);
        }
        int limitStart = paginator.getOffset();
        int limitEnd = paginator.getLimit();

        if(request.getCurrPage() == -1){
            limitStart = -1;
            limitEnd = -1;
        }
        Map map = ConvertUtils.convertObjectToMap(request);
        List<BorrowRepayAgreementCustomize> list =  applyAgreementService.getList(map,limitStart,limitEnd);
        List<BorrowRepayAgreementVO> voList = null;
        if(!CollectionUtils.isEmpty(list)){
            voList = CommonUtils.convertBeanList(list, BorrowRepayAgreementVO.class);
        }
        response.setCount(total);
        response.setResultList(voList);
        return response;
    }

    /**
     *垫付协议申请明细列表页--分期
     * @param request
     * @return
     */
    @ApiOperation(value = "垫付协议申请明细列表页--分总条数")
    @PostMapping("/getListTotal")
    public Integer getAddApplyAgreementCount(@RequestBody BorrowRepayAgreementRequest request) {
        Map map = ConvertUtils.convertObjectToMap(request);
        Integer count = applyAgreementService.getListTotal(map);
        return count;
    }
}
