package com.hyjf.wbs.trade.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.http.UnicodeFormatter;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
        WbsCommonQO wbsCommonQO =new WbsCommonQO();
        BeanUtils.copyProperties(wbsCommonExQO, wbsCommonQO);
        //验签
        Boolean booleanSian = WbsSignUtil.verify(wbsCommonQO,wbsCommonExQO.getSign(), wbsConfig.getAppSecret());
        logger.info("---searchInfoRecover.searchAction by param---wbs订单信息接口  " + JSONObject.toJSON(wbsCommonExQO)+"验签结果："+booleanSian);
        if(!booleanSian){
            wbsCommonVO.setCode(Response.ERROR);
            wbsCommonVO.setMsg("验签失败");
            wbsCommonVO.setData("");
            return wbsCommonVO;
        }
        String data="";
        try {
            data=URLDecoder.decode(wbsCommonQO.getData(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        TenderAccedeQO tenderAccedeQO = JSONObject.parseObject(data,TenderAccedeQO.class);
        // 订单明细
        if (tenderAccedeQO!=null){
            if (tenderAccedeQO.getStartTime().isEmpty()||tenderAccedeQO.getEndTime().isEmpty()){
                wbsCommonVO.setCode(Response.ERROR);
                wbsCommonVO.setMsg("请求参数不允许为空");
                wbsCommonVO.setData("");
            }
            tenderAccedeQO.setEntIds(getUtmId(tenderAccedeQO.getEntId()));
            logger.info("---searchInfoRecover.searchAction by param---wbs订单信息接口 请求参数 " + JSONObject.toJSON(tenderAccedeQO));
            List<TenderAccedeVO> tenderAccedeVOS = this.orderService.getOrderInfo(tenderAccedeQO);
            logger.info("---searchInfoRecover.searchAction by param---wbs订单信息接口 总条数 " + tenderAccedeVOS.size());
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
    public String getUtmId(Integer entId) {
        String thirdIds = wbsConfig.getThridPropertyIds();
        String[] thirdIdsArr = thirdIds.split(",");
        if (entId.equals(thirdIdsArr[0])){
            return wbsConfig.getUtmNami()+","+wbsConfig.getUtmYufengrui();
        }else if (entId.equals(thirdIdsArr[1])){
            return wbsConfig.getUtmDatang()+"";
        }else if (entId.equals(thirdIdsArr[2])){
            return wbsConfig.getUtmQianle()+"";
        }else {
            return null;
        }
    }
}
