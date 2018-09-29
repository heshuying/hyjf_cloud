package com.hyjf.am.trade.controller.admin.exception;

import com.hyjf.am.response.admin.ManualReverseCustomizeResponse;
import com.hyjf.am.resquest.admin.ManualReverseAddRequest;
import com.hyjf.am.resquest.admin.ManualReverseCustomizeRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.ManualReverseCustomize;
import com.hyjf.am.trade.service.admin.exception.ManualReverseExceptionService;
import com.hyjf.am.vo.admin.ManualReverseCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 手动冲正
 * @author hesy
 * @version ManualReverseExceptionController, v0.1 2018/7/14 15:05
 */
@Api(value = "异常中心-手动冲正")
@RestController
@RequestMapping("/am-trade/manualreverse")
public class ManualReverseExceptionController extends BaseController {
    @Autowired
    ManualReverseExceptionService manualReverseExceptionService;

    /**
     * 手动冲正列表
     * @param requestBean
     * @return
     */
    @RequestMapping("/getlist")
    public ManualReverseCustomizeResponse getManualReverseList(@RequestBody ManualReverseCustomizeRequest requestBean){
        ManualReverseCustomizeResponse response = new ManualReverseCustomizeResponse();
        List<ManualReverseCustomize> recordList = manualReverseExceptionService.selectManualReverseList(requestBean);
        if (Validator.isNotNull(recordList)){
            response.setResultList(CommonUtils.convertBeanList(recordList,ManualReverseCustomizeVO.class));
        }
        return response;
    }

    /**
     * 手动冲正总记录数
     * @param requestBean
     * @return
     */
    @RequestMapping("/getcount")
    public Integer getManualReverseCount(@RequestBody ManualReverseCustomizeRequest requestBean){
        return manualReverseExceptionService.countManualReverse(requestBean);
    }

    /**
     * 手动冲正更新
     * @param requestBean
     * @return
     */
    @RequestMapping("/update_manualreverse")
    public boolean updateManualReverse(@RequestBody ManualReverseAddRequest requestBean){
        return manualReverseExceptionService.updateManualReverse(requestBean);
    }
}
