package com.hyjf.wbs.trade.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.wbs.configs.WbsConfig;
import com.hyjf.wbs.qvo.*;
import com.hyjf.wbs.sign.WbsSignUtil;
import com.hyjf.wbs.trade.service.recover.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author koudeli
 * @version RecoverInfoController, v0.1 2019/4/23 14:14
 */
@Api(value = "回款信息",tags ="回款信息" )
@RestController
@RequestMapping("/hyjf-wbs/trade/order")
public class OrderInfoController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private WbsConfig wbsConfig;
    /**
     * @param
     */
    @ApiOperation(value = "订单明细", notes = "订单明细")
    @ResponseBody
    @PostMapping(value = "/batchsync")
    public WbsCommonVO searchAction(
                                    @RequestBody WbsCommonExQO wbsCommonExQO
//@RequestBody TenderAccedeQO tenderAccedeQO
    ) {
        WbsCommonVO wbsCommonVO =new WbsCommonVO();
        WbsCommonQO wbsCommonQO = wbsCommonExQO;
        //验签
        Boolean booleanSian = WbsSignUtil.verify(wbsCommonQO,wbsCommonExQO.getSign(), wbsConfig.getAppSecret());
        logger.info("---searchInfoRecover.searchAction by param---wbs订单信息接口  " + JSONObject.toJSON(wbsCommonExQO)+"验签结果："+booleanSian);
        if(!booleanSian){
            wbsCommonVO.setCode(Response.ERROR);
            wbsCommonVO.setMsg("验签失败");
            wbsCommonVO.setData("");
            return wbsCommonVO;
        }
        TenderAccedeQO tenderAccedeQO = JSONObject.parseObject(wbsCommonQO.getData(),TenderAccedeQO.class);
        // 订单明细
        if (tenderAccedeQO!=null){
            if (tenderAccedeQO.getStartTime().isEmpty()||tenderAccedeQO.getEndTime().isEmpty()){
                wbsCommonVO.setCode(Response.ERROR);
                wbsCommonVO.setMsg("请求参数不允许为空");
                wbsCommonVO.setData("");
            }
            List<TenderAccedeVO> tenderAccedeVOS = this.orderService.getOrderInfo(tenderAccedeQO);
            if (tenderAccedeVOS!=null&&tenderAccedeVOS.size()>0){
                wbsCommonVO.setCode(Response.SUCCESS);
                wbsCommonVO.setMsg(Response.SUCCESS_MSG);
                wbsCommonVO.setData(tenderAccedeVOS);
            }else {
                wbsCommonVO.setCode(Response.ERROR);
                wbsCommonVO.setMsg("返回结果集为空");
                wbsCommonVO.setData("");
            }
        }else {
            wbsCommonVO.setCode(Response.ERROR);
            wbsCommonVO.setMsg("请求参数不允许为空");
            wbsCommonVO.setData("");
        }

        return wbsCommonVO;
    }
}
