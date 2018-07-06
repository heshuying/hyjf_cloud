package com.hyjf.am.trade.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.admin.AdminBorrowRepaymentResponse;
import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.resquest.admin.BorrowRepaymentPlanRequest;
import com.hyjf.am.trade.controller.AssetManageController;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowRepaymentCustomize;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowRepaymentPlanCustomize;
import com.hyjf.am.trade.service.admin.AdminBorrowRepaymentService;
import com.hyjf.am.vo.admin.BorrowRepaymentCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepaymentPlanCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRepaymentController, v0.1 2018/7/4 14:26
 */
@Api(value = "产品中心-汇直投-还款信息查询")
@RestController
@RequestMapping("/am-trade/adminBorrowRepayment")
public class AdminBorrowRepaymentController {

    private static Logger logger = LoggerFactory.getLogger(AssetManageController.class);
    @Autowired
    AdminBorrowRepaymentService adminBorrowRepaymentService;

    @RequestMapping(value = "/countBorrowRepayment")
    public AdminBorrowRepaymentResponse countBorrowRepayment(@RequestBody @Valid BorrowRecoverRequest request){
        logger.info("request:" +JSONObject.toJSON(request));
        AdminBorrowRepaymentResponse response = new AdminBorrowRepaymentResponse();
        int count = this.adminBorrowRepaymentService.countBorrowRecover(request);

        response.setTotal(count);
        return response;
    }

    @RequestMapping(value = "/selectBorrowRepaymentList")
    public AdminBorrowRepaymentResponse selectBorrowRepaymentList(@RequestBody @Valid BorrowRecoverRequest request){
        logger.info("request:" +JSONObject.toJSON(request));
        AdminBorrowRepaymentResponse response = new AdminBorrowRepaymentResponse();

        List<AdminBorrowRepaymentCustomize> list = adminBorrowRepaymentService.selectBorrowRecoverList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<BorrowRepaymentCustomizeVO> voList = CommonUtils.convertBeanList(list, BorrowRepaymentCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    @RequestMapping(value = "/sumBorrowRepaymentInfo")
    public AdminBorrowRepaymentResponse sumBorrowRepaymentInfo(@RequestBody @Valid BorrowRecoverRequest request){
        logger.info("request:" +JSONObject.toJSON(request));
        AdminBorrowRepaymentResponse response = new AdminBorrowRepaymentResponse();

        AdminBorrowRepaymentCustomize customize = adminBorrowRepaymentService.sumBorrowRecoverList(request);
        if(customize!=null){
            BorrowRepaymentCustomizeVO vo = CommonUtils.convertBean(customize,BorrowRepaymentCustomizeVO.class);
            response.setResult(vo);
        }
        return response;
    }


    @RequestMapping(value = "/exportRepayClkActBorrowRepaymentInfoList")
    public AdminBorrowRepaymentResponse exportRepayClkActBorrowRepaymentInfoList(@RequestBody @Valid BorrowRepaymentPlanRequest request){
        logger.info("request:" +JSONObject.toJSON(request));
        AdminBorrowRepaymentResponse response = new AdminBorrowRepaymentResponse();

        List<AdminBorrowRepaymentPlanCustomize> list = adminBorrowRepaymentService.exportRepayClkActBorrowRepaymentInfoList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<BorrowRepaymentPlanCustomizeVO> voList = CommonUtils.convertBeanList(list, BorrowRepaymentPlanCustomizeVO.class);
            response.setBorrowRepaymentPlanList(voList);
        }
        return response;
    }

}
