package com.hyjf.am.config.controller.callcenter;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.customize.CallcenterBankConfigCustomize;
import com.hyjf.am.config.service.CallCenterService;
import com.hyjf.am.response.callcenter.CallcenterBankConfigResponse;
import com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest;
import com.hyjf.am.vo.callcenter.CallcenterBankConfigVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author libin
 * @version CallCenterController, v0.1 2018/6/6 14:14
 */
@RestController
@RequestMapping("/am-config/callcenter")
public class CallCenterController extends BaseConfigController {
	
    @Autowired
    CallCenterService callCenterService;
    
    @RequestMapping("/getBankConfigList")
    public CallcenterBankConfigResponse getBankConfigList(@RequestBody @Valid CallcenterAccountHuifuRequest callcenterAccountHuifuRequest){
    	CallcenterBankConfigResponse CallcenterBankConfigResponse = new CallcenterBankConfigResponse();
    	List<CallcenterBankConfigCustomize> list = callCenterService.getBankConfigList(callcenterAccountHuifuRequest);
        if(!CollectionUtils.isEmpty(list)){
            List<CallcenterBankConfigVO> CallcenterBankConfigVOS = CommonUtils.convertBeanList(list,CallcenterBankConfigVO.class);
            CallcenterBankConfigResponse.setResultList(CallcenterBankConfigVOS);
        }
        return CallcenterBankConfigResponse;
    }
}
