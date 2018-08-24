package com.hyjf.am.trade.controller.admin.borrow;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.admin.AdminBorrowRecoverResponse;
import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.AdminBorrowRecoverCustomize;
import com.hyjf.am.trade.service.admin.borrow.AdminBorrowRecoverService;
import com.hyjf.am.vo.admin.BorrowRecoverCustomizeVO;
import com.hyjf.common.util.CommonUtils;

import io.swagger.annotations.Api;

/**
 * @author pangchengchao
 * @version AdminBorrowRecoverController, v0.1 2018/7/2 16:23
 */
@Api(tags = "产品中心-汇直投-放款明细查询")
@RestController
@RequestMapping("/am-trade/adminBorrowRecover")
public class AdminBorrowRecoverController extends BaseController {
    @Autowired
    AdminBorrowRecoverService adminBorrowRecoverService;

    @RequestMapping(value = "/countBorrowRecover")
    public AdminBorrowRecoverResponse countBorrowRecover(@RequestBody @Valid BorrowRecoverRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AdminBorrowRecoverResponse response = new AdminBorrowRecoverResponse();
        int count = this.adminBorrowRecoverService.countBorrowRecover(request);

        response.setTotal(count);
        return response;
    }

    @RequestMapping(value = "/selectBorrowRecoverList")
    public AdminBorrowRecoverResponse selectBorrowRecoverList(@RequestBody @Valid BorrowRecoverRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AdminBorrowRecoverResponse response = new AdminBorrowRecoverResponse();

        List<AdminBorrowRecoverCustomize> list = adminBorrowRecoverService.selectBorrowRecoverList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<BorrowRecoverCustomizeVO> voList = CommonUtils.convertBeanList(list, BorrowRecoverCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    @RequestMapping(value = "/sumBorrowRecoverList")
    public AdminBorrowRecoverResponse sumBorrowRecoverList(@RequestBody @Valid BorrowRecoverRequest request){
        logger.info("请求参数:" +JSONObject.toJSON(request));
        AdminBorrowRecoverResponse response = new AdminBorrowRecoverResponse();

        AdminBorrowRecoverCustomize customize = adminBorrowRecoverService.sumBorrowRecoverList(request);
        if(customize!=null){
            BorrowRecoverCustomizeVO vo = CommonUtils.convertBean(customize,BorrowRecoverCustomizeVO.class);
            response.setResult(vo);
        }
        return response;
    }

}
