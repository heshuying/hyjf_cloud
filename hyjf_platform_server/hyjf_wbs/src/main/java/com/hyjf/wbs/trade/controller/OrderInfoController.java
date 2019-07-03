package com.hyjf.wbs.trade.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.http.UnicodeFormatter;
import com.hyjf.wbs.common.EntUtmIds;
import com.hyjf.wbs.configs.WbsConfig;
import com.hyjf.wbs.exceptions.WbsFundDetailsException;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author koudeli
 * @version RecoverInfoController, v0.1 2019/4/23 14:14
 */
@Api(value = "订单信息",tags ="订单信息" )
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
                return wbsCommonVO;
            }
            if(tenderAccedeQO.getAssetId()!=null){
                if(tenderAccedeQO.getAssetId()==2 ||tenderAccedeQO.getAssetId()==3){
                    wbsCommonVO.setCode(Response.ERROR);
                    wbsCommonVO.setMsg("请输入正确的assetId");
                    wbsCommonVO.setData("");
                    return wbsCommonVO;
                }
            }
            if(tenderAccedeQO.getEntId()!=null&&getUtmIdList(tenderAccedeQO.getEntId())!=null){
                tenderAccedeQO.setUtmIds(getUtmIdList(tenderAccedeQO.getEntId()));
            }else {
                wbsCommonVO.setCode(Response.ERROR);
                wbsCommonVO.setMsg("请输入有效的entId");
                wbsCommonVO.setData("");
                return wbsCommonVO;
            }
            logger.info("---searchInfoRecover.searchAction by param---wbs订单信息接口 请求参数 " + JSONObject.toJSON(tenderAccedeQO));
            List<TenderAccedeVO> tenderAccedeVOS = this.orderService.getOrderInfo(tenderAccedeQO);
            if (tenderAccedeVOS!=null&&tenderAccedeVOS.size()>0){
                logger.info("---searchInfoRecover.searchAction by param---wbs订单信息接口 总条数 " + tenderAccedeVOS.size());
                wbsCommonVO.setCode(Response.SUCCESS);
                wbsCommonVO.setMsg(Response.SUCCESS_MSG);
                wbsCommonVO.setData(tenderAccedeVOS);
            }else {
                logger.info("---searchInfoRecover.searchAction by param---wbs订单信息接口 总条数 0");
                wbsCommonVO.setCode(Response.SUCCESS);
                wbsCommonVO.setMsg("订单信息为空");
                wbsCommonVO.setData("");
            }
        }else {
            wbsCommonVO.setCode(Response.ERROR);
            wbsCommonVO.setMsg("请求参数不允许为空");
            wbsCommonVO.setData("");
        }

        return wbsCommonVO;
    }
    public static List<Integer> getUtmIdList(Integer entIds) {
        List<Integer> utmIds = new ArrayList<Integer>();
        //转换成平台的渠道号8001=>11200009
        String utmId= EntUtmIds.getUtmId(String.valueOf(entIds));
        if(Strings.isNullOrEmpty(utmId)){
            throw new WbsFundDetailsException("未找到entId【"+entIds+"】对应的UTMID");
        }
        List<String> lstUtm= Splitter.on(",").trimResults().splitToList(utmId);
        for (int i = 0; i < lstUtm.size(); i++) {
            utmIds.add(new Integer(lstUtm.get(i)));
        }
        return utmIds;
    }
}
