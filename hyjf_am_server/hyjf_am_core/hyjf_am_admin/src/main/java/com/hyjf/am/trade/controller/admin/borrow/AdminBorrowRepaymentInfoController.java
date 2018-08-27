package com.hyjf.am.trade.controller.admin.borrow;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.admin.AdminBorrowRepaymentInfoResponse;
import com.hyjf.am.resquest.admin.BorrowRepaymentInfoRequset;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.AdminBorrowRepaymentInfoCustomize;
import com.hyjf.am.trade.service.admin.borrow.AdminBorrowRepaymentInfoService;
import com.hyjf.am.vo.admin.BorrowRepaymentInfoCustomizeVO;
import com.hyjf.common.util.CommonUtils;

import io.swagger.annotations.Api;

/**
 * @author pangchengchao
 * @version AdminBorrowRepaymentInfoController, v0.1 2018/7/9 9:48
 */
@Api(value = "产品中心-汇直投-还款明细查询")
@RestController
@RequestMapping("/am-trade/adminBorrowRepaymentInfo")
public class AdminBorrowRepaymentInfoController extends BaseController {
    @Autowired
    AdminBorrowRepaymentInfoService adminBorrowRepaymentInfoService;

    @RequestMapping(value = "/countBorrowRepaymentInfo")
    public AdminBorrowRepaymentInfoResponse countBorrowRepaymentInfo(@RequestBody @Valid BorrowRepaymentInfoRequset request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AdminBorrowRepaymentInfoResponse response = new AdminBorrowRepaymentInfoResponse();
        int count = this.adminBorrowRepaymentInfoService.countBorrowRecover(request);

        response.setTotal(count);
        return response;
    }

    @RequestMapping(value = "/selectBorrowRepaymentInfoListForView")
    public AdminBorrowRepaymentInfoResponse selectBorrowRepaymentInfoListForView(@RequestBody @Valid BorrowRepaymentInfoRequset request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AdminBorrowRepaymentInfoResponse response = new AdminBorrowRepaymentInfoResponse();

        List<AdminBorrowRepaymentInfoCustomize> list = adminBorrowRepaymentInfoService.selectBorrowRepaymentInfoListForView(request);
        if(!CollectionUtils.isEmpty(list)){
            List<BorrowRepaymentInfoCustomizeVO> voList = CommonUtils.convertBeanList(list, BorrowRepaymentInfoCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    @RequestMapping(value = "/sumBorrowRepaymentInfo")
    public AdminBorrowRepaymentInfoResponse sumBorrowRepaymentInfo(@RequestBody @Valid BorrowRepaymentInfoRequset request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AdminBorrowRepaymentInfoResponse response = new AdminBorrowRepaymentInfoResponse();

        AdminBorrowRepaymentInfoCustomize customize = adminBorrowRepaymentInfoService.sumBorrowRecoverList(request);
        if(customize!=null){
            BorrowRepaymentInfoCustomizeVO vo = CommonUtils.convertBean(customize,BorrowRepaymentInfoCustomizeVO.class);
            response.setResult(vo);
        }
        return response;
    }

    @RequestMapping(value = "/selectBorrowRepaymentInfoList")
    public AdminBorrowRepaymentInfoResponse selectBorrowRepaymentInfoList(@RequestBody @Valid BorrowRepaymentInfoRequset request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AdminBorrowRepaymentInfoResponse response = new AdminBorrowRepaymentInfoResponse();

        List<AdminBorrowRepaymentInfoCustomize> list = adminBorrowRepaymentInfoService.selectBorrowRepaymentInfoList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<BorrowRepaymentInfoCustomizeVO> voList = CommonUtils.convertBeanList(list, BorrowRepaymentInfoCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

}
