package com.hyjf.am.trade.controller.admin.borrow;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.admin.AdminBorrowLogResponse;
import com.hyjf.am.resquest.admin.BorrowLogRequset;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.BorrowLogCustomize;
import com.hyjf.am.trade.service.admin.borrow.AdminBorrowLogService;
import com.hyjf.am.vo.admin.BorrowLogCustomizeVO;
import com.hyjf.common.util.CommonUtils;

import io.swagger.annotations.Api;

/**
 * @author pangchengchao
 * @version AdminBorrowLogController, v0.1 2018/7/11 10:35
 */
@Api(value = "产品中心-汇直投-放款明细查询")
@RestController
@RequestMapping("/am-trade/adminBorrowLog")
public class AdminBorrowLogController extends BaseController {
    @Autowired
    AdminBorrowLogService adminBorrowLogService;

    @RequestMapping(value = "/countBorrowLog")
    public AdminBorrowLogResponse countBorrowLog(@RequestBody @Valid BorrowLogRequset request){
        logger.info("request:" +JSONObject.toJSON(request));
        AdminBorrowLogResponse response = new AdminBorrowLogResponse();
        int count = this.adminBorrowLogService.countBorrowLog(request);

        response.setTotal(count);
        return response;
    }

    @RequestMapping(value = "/selectBorrowLogList")
    public AdminBorrowLogResponse selectBorrowLogList(@RequestBody @Valid BorrowLogRequset request){
        logger.info("request:" +JSONObject.toJSON(request));
        AdminBorrowLogResponse response = new AdminBorrowLogResponse();

        List<BorrowLogCustomize> list = adminBorrowLogService.selectBorrowLogList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<BorrowLogCustomizeVO> voList = CommonUtils.convertBeanList(list, BorrowLogCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    @RequestMapping(value = "/exportBorrowRecoverList")
    public AdminBorrowLogResponse exportBorrowRecoverList(@RequestBody @Valid BorrowLogRequset request){
        logger.info("request:" +JSONObject.toJSON(request));
        AdminBorrowLogResponse response = new AdminBorrowLogResponse();

        List<BorrowLogCustomize> list = adminBorrowLogService.exportBorrowLogList(request);
        if(!CollectionUtils.isEmpty(list)){
            List<BorrowLogCustomizeVO> voList = CommonUtils.convertBeanList(list, BorrowLogCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }
}
