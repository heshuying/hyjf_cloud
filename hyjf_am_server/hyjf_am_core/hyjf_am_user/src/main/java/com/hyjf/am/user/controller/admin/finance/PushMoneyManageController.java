/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.finance;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.PushMoneyResponse;
import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.customize.admin.finance.PushMoneyCustomize;
import com.hyjf.am.user.service.admin.finance.PushMoneyManageService;
import com.hyjf.am.vo.trade.PushMoneyVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zdj
 * @version PushMoneyManageController, v0.1 2018/67/7 9:13
 *          后台资金中心-提成管理
 */

@RestController
@RequestMapping("/am-user/pushMoneyRecord")
public class PushMoneyManageController extends BaseController {
    @Autowired
    private PushMoneyManageService pushMoneyManagerService;

    /**
     * 根据筛选条件查找(查找提成管理)
     *
     * @param request
     * @return
     */
    @RequestMapping("/am-user/findPushMoneyRecordList")
    public PushMoneyResponse findPushMoneyRecordList(@RequestBody @Valid PushMoneyRequest request) {
        logger.info("---findPushMoneyRecordList by param---  " + JSONObject.toJSON(request));
        PushMoneyResponse response = new PushMoneyResponse();
        int pushMoneyTotal = pushMoneyManagerService.countPushMoney(request);
        if (pushMoneyTotal > 0) {
            List<PushMoneyCustomize> pushMoneyCustomizeList = pushMoneyManagerService.selectPushMoneyList(request);
            if (!CollectionUtils.isEmpty(pushMoneyCustomizeList)) {
                List<PushMoneyVO> userBankRecord = CommonUtils.convertBeanList(pushMoneyCustomizeList, PushMoneyVO.class);
                response.setResultList(userBankRecord);
                response.setCount(pushMoneyTotal);
                response.setRtn(Response.SUCCESS);//代表成功
            }
        }
        return response;
    }

}
