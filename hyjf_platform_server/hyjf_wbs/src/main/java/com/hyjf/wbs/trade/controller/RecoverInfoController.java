package com.hyjf.wbs.trade.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.BorrowRepaymentInfoRequset;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.StringUtil;
import com.hyjf.wbs.configs.WbsConfig;
import com.hyjf.wbs.qvo.*;
import com.hyjf.wbs.sign.WbsSignUtil;
import com.hyjf.wbs.trade.service.recover.RecoverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * @author koudeli
 * @version RecoverInfoController, v0.1 2019/4/18 14:14
 */
@Api(value = "回款信息",tags ="回款信息" )
@RestController
@RequestMapping("/hyjf-wbs/trade/borrowrecoverinfo")
public class RecoverInfoController extends BaseController {

    @Autowired
    private RecoverService recoverService;
    @Autowired
    private WbsConfig wbsConfig;
    /**
     * @param
     */
    @ApiOperation(value = "还款明细", notes = "还款明细")
    @ResponseBody
//    @PostMapping(value = "/searchInfoRecover")
    @RequestMapping(value = "/searchInfoRecover", method = RequestMethod.POST)
    public WbsCommonVO searchAction(
                                    @RequestBody WbsCommonExQO wbsCommonExQO
//                                    @RequestBody RecoverQO recoverQO
    ) {
        WbsCommonVO wbsCommonVO =new WbsCommonVO();
        WbsRecoverVO wbsRecoverVO=new WbsRecoverVO();
        WbsCommonQO wbsCommonQO =new WbsCommonQO();
        BeanUtils.copyProperties(wbsCommonExQO, wbsCommonQO);
        //验签
        Boolean booleanSian = WbsSignUtil.verify(wbsCommonQO,wbsCommonExQO.getSign(), wbsConfig.getAppSecret());
        logger.info("---searchInfoRecover.searchAction by param---wbs回款信息接口  " + JSONObject.toJSON(wbsCommonExQO)+"验签结果："+booleanSian);
        if(!booleanSian){
            wbsRecoverVO.setCode(Response.ERROR);
            wbsRecoverVO.setMsg("验签失败");
            wbsRecoverVO.setData("");
            return wbsRecoverVO;
        }
        String data="";
        try {
            data=URLDecoder.decode(wbsCommonQO.getData(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        RecoverQO recoverQO = JSONObject.parseObject(data,RecoverQO.class);
        if (recoverQO!=null){
            if (recoverQO.getEntId()==null|| recoverQO.getCurrentPage().isEmpty()){
                wbsRecoverVO.setCode(Response.ERROR);
                wbsRecoverVO.setMsg("请求参数不允许为空");
                wbsRecoverVO.setData("");
                return wbsRecoverVO;
            }
            if(getUtmId(recoverQO.getEntId())!=null){
                recoverQO.setEntIds(getUtmId(recoverQO.getEntId()));
            }else {
                wbsRecoverVO.setCode(Response.ERROR);
                wbsRecoverVO.setMsg("请输入有效的entId");
                wbsRecoverVO.setData("");
                return wbsRecoverVO;
            }
            recoverQO.setEntId(recoverQO.getEntId());
            logger.info("---searchInfoRecover.searchAction by param---wbs回款信息接口 请求实体参数 " + JSONObject.toJSON(recoverQO));
            int countRecover=recoverService.getRecoverCount(recoverQO);
            Paginator paginator = new Paginator(Integer.parseInt(recoverQO.getCurrentPage()),countRecover,10000);
            recoverQO.setLimitStart(paginator.getOffset());
            recoverQO.setLimitEnd(paginator.getLimit());
            List<RecoverVO> recoverVOS = recoverService.getRecoverInfo(recoverQO);
            logger.info("---searchInfoRecover.searchAction by param---wbs回款信息接口 响应结果集 " + "总条数："+countRecover);
            if (recoverVOS!=null&&recoverVOS.size()>0){
                wbsRecoverVO.setCode(Response.SUCCESS);
                wbsRecoverVO.setMsg(Response.SUCCESS_MSG);
                wbsRecoverVO.setData(recoverVOS);
                wbsRecoverVO.setCurrentPage(recoverQO.getCurrentPage());
                wbsRecoverVO.setTotalPages(paginator.getTotalPages()+"");
            }else {
                wbsRecoverVO.setCode(Response.SUCCESS);
                wbsRecoverVO.setMsg("回款信息为空");
                wbsRecoverVO.setData("");
            }
        }else {
            wbsRecoverVO.setCode(Response.ERROR);
            wbsRecoverVO.setMsg("请求参数不允许为空");
            wbsRecoverVO.setData("");
        }
        return wbsRecoverVO;
    }
    public String getUtmId(Integer entIds) {
        String entId=entIds.toString();
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
