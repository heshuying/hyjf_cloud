package com.hyjf.am.trade.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.admin.AdminBorrowRepaymentInfoListResponse;
import com.hyjf.am.response.admin.AdminBorrowRepaymentInfoResponse;
import com.hyjf.am.resquest.admin.BorrowRepaymentInfoListRequset;
import com.hyjf.am.resquest.admin.BorrowRepaymentInfoRequset;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowRepaymentInfoCustomize;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowRepaymentInfoListCustomize;
import com.hyjf.am.trade.service.admin.AdminBorrowRepaymentInfoListService;
import com.hyjf.am.trade.service.admin.AdminBorrowRepaymentInfoService;
import com.hyjf.am.vo.admin.BorrowRepaymentInfoCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepaymentInfoListCustomizeVO;
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
 * @version AdminBorrowRepaymentInfoListController, v0.1 2018/7/10 11:03
 */

@Api(value = "产品中心-汇直投-还款明细列表查询")
@RestController
@RequestMapping("/am-trade/adminBorrowRepaymentInfoList")
public class AdminBorrowRepaymentInfoListController extends BaseController {

    @Autowired
    AdminBorrowRepaymentInfoListService adminBorrowRepaymentInfoListService;

    @RequestMapping(value = "/countBorrowRepaymentInfoList")
    public AdminBorrowRepaymentInfoListResponse countBorrowRepaymentInfoList(@RequestBody @Valid BorrowRepaymentInfoListRequset request){
        logger.info("request:" +JSONObject.toJSON(request));
        AdminBorrowRepaymentInfoListResponse response = new AdminBorrowRepaymentInfoListResponse();
        int count = this.adminBorrowRepaymentInfoListService.countBorrowRepaymentInfoList(request);
        response.setTotal(count);
        return response;
    }

    @RequestMapping(value = "/selectBorrowRepaymentInfoListList")
    public AdminBorrowRepaymentInfoListResponse selectBorrowRepaymentInfoListList(@RequestBody @Valid BorrowRepaymentInfoListRequset request){
        logger.info("request:" +JSONObject.toJSON(request));
        AdminBorrowRepaymentInfoListResponse response = new AdminBorrowRepaymentInfoListResponse();
        List<AdminBorrowRepaymentInfoListCustomize> list = adminBorrowRepaymentInfoListService.selectBorrowRepaymentInfoListList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<BorrowRepaymentInfoListCustomizeVO> voList = CommonUtils.convertBeanList(list, BorrowRepaymentInfoListCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    @RequestMapping(value = "/sumBorrowRepaymentInfoList")
    public AdminBorrowRepaymentInfoListResponse sumBorrowRepaymentInfoList(@RequestBody @Valid BorrowRepaymentInfoListRequset request){
        logger.info("request:" +JSONObject.toJSON(request));
        AdminBorrowRepaymentInfoListResponse response = new AdminBorrowRepaymentInfoListResponse();
        AdminBorrowRepaymentInfoListCustomize customize = adminBorrowRepaymentInfoListService.sumBorrowRepaymentInfoList(request);
        if(customize!=null){
            BorrowRepaymentInfoListCustomizeVO vo = CommonUtils.convertBean(customize,BorrowRepaymentInfoListCustomizeVO.class);
            response.setResult(vo);
        }
        return response;
    }
}