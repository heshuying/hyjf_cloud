package com.hyjf.am.trade.controller.front.trade;

import com.hyjf.am.response.admin.UnderLineRechargeResponse;
import com.hyjf.am.resquest.admin.UnderLineRechargeRequest;
import com.hyjf.am.resquest.trade.SynBalanceBeanRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.UnderLineRecharge;
import com.hyjf.am.trade.service.front.trade.SynBalanceService;
import com.hyjf.am.vo.admin.UnderLineRechargeVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author pangchengchao
 * @version SynBalanceController, v0.1 2018/6/20 9:53
 */
@Api(value = "江西银行同步线下充值信息")
@RestController
@RequestMapping("/am-trade/synBalance")
public class SynBalanceController extends BaseController {
    @Autowired
    private SynBalanceService synBalanceService;

    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping(value = "/insertAccountDetails")
    public boolean insertAccountDetails(@RequestBody SynBalanceBeanRequest synBalanceBeanRequest){
        return synBalanceService.insertAccountDetails(synBalanceBeanRequest);
    }

    /**
     * 获取列表,同步数据时使用
     * @param request
     * @return
     */
    @RequestMapping(value = "/selectUnderLineListBySyn", method = RequestMethod.POST)
    public UnderLineRechargeResponse selectUnderLineListBySyn(@RequestBody UnderLineRechargeRequest request){
        UnderLineRechargeResponse response = new UnderLineRechargeResponse();

        List<UnderLineRecharge> underLineRechargeList = this.synBalanceService.getUnderLineRechargeListByCode(request);

        if (!CollectionUtils.isEmpty(underLineRechargeList)){
            List<UnderLineRechargeVO> underLineRechargeVOList = CommonUtils.convertBeanList(underLineRechargeList, UnderLineRechargeVO.class);
            response.setResultList(underLineRechargeVOList);
        }
        return response;
    }
}
