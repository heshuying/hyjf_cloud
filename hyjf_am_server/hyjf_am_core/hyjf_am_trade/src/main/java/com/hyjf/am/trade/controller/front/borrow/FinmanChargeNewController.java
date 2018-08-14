package com.hyjf.am.trade.controller.front.borrow;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.admin.FinmanChargeNewResponse;
import com.hyjf.am.response.trade.BorrowProjectTypeResponse;
import com.hyjf.am.resquest.admin.FinmanChargeNewRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.front.borrow.FinmanChargeNewService;
import com.hyjf.am.vo.trade.borrow.BorrowFinmanNewChargeVO;
import com.hyjf.common.paginator.Paginator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiehuili on 2018/8/14.
 */
@RestController
@RequestMapping("/am-trade/config/finmanchargenew")
public class FinmanChargeNewController extends BaseController {
    @Autowired
    private FinmanChargeNewService finmanChargeNewService;
    /**
     * 分页查询配置中心项目类型
     * @return
     */
    @RequestMapping("/list")
    public FinmanChargeNewResponse selectFinmanChargeNewList(@RequestBody FinmanChargeNewRequest adminRequest) {
        logger.info("费率配置 列表..." + JSONObject.toJSON(adminRequest));
        FinmanChargeNewResponse response =null;
        int total = this.finmanChargeNewService.countRecordTotal(adminRequest);
        if(total > 0){
            Paginator paginator = new Paginator(adminRequest.getCurrPage(), total);
            List<BorrowFinmanNewChargeVO> recordList =
                    this.finmanChargeNewService.getRecordList(adminRequest, paginator.getOffset(), paginator.getLimit());
            if(!CollectionUtils.isEmpty(recordList)){
                response=new FinmanChargeNewResponse();
                response.setResultList(recordList);
                return response;
            }
            return response;
        }
        return response;
    }

}
