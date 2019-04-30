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
        WbsRecoverVO wbsCommonVO =new WbsRecoverVO();
        String jsonRequest = JSONObject.toJSONString(wbsCommonExQO);
        WbsCommonQO wbsCommonQO = wbsCommonExQO;
        //验签
        Boolean booleanSian = WbsSignUtil.verify(wbsCommonQO,wbsCommonExQO.getSign(), wbsConfig.getAppSecret());
        logger.info("---searchInfoRecover.searchAction by param---wbs回款信息接口  " + JSONObject.toJSON(wbsCommonExQO)+"验签结果："+booleanSian);
        if(!booleanSian){
            wbsCommonVO.setCode(Response.ERROR);
            wbsCommonVO.setMsg("验签失败");
            wbsCommonVO.setData("");
            return wbsCommonVO;
        }

        RecoverQO recoverQO = JSONObject.parseObject(wbsCommonQO.getData(),RecoverQO.class);
        if (recoverQO!=null){
            if (recoverQO.getEntId()==null|| recoverQO.getCurrentPage().isEmpty()){
                wbsCommonVO.setCode(Response.ERROR);
                wbsCommonVO.setMsg("请求参数不允许为空");
                wbsCommonVO.setData("");
            }
            int countRecover=recoverService.getRecoverCount(recoverQO);
            Paginator paginator = new Paginator(Integer.parseInt(recoverQO.getCurrentPage()),countRecover,10000);
            recoverQO.setLimitStart(paginator.getOffset());
            recoverQO.setLimitEnd(paginator.getLimit());
            List<RecoverVO> recoverVOS = recoverService.getRecoverInfo(recoverQO);
            if (recoverVOS!=null&&recoverVOS.size()>0){
                wbsCommonVO.setCode(Response.SUCCESS);
                wbsCommonVO.setMsg(Response.SUCCESS_MSG);
                wbsCommonVO.setData(recoverVOS);
                wbsCommonVO.setCurrentPage(recoverQO.getCurrentPage());
                wbsCommonVO.setTotalPages(paginator.getTotalPages()+"");
            }else {
                wbsCommonVO.setCode(Response.ERROR);
                wbsCommonVO.setMsg(Response.FAIL_MSG);
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
