package com.hyjf.cs.trade.controller.api.aems.overdue;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.AemsErrorCodeConstant;
import com.hyjf.cs.trade.bean.AemsOverdueRequestBean;
import com.hyjf.cs.trade.bean.AemsOverdueResultBean;
import com.hyjf.cs.trade.service.aems.overdue.AemsOverdueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiehuili on 2019/3/12.
 */
@RestController
@Api(value = "aems逾期查询接口", tags ="aems逾期查询接口")
@RequestMapping("/hyjf-api/aems/overdue")
public class AemsOverdueController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AemsOverdueService aemsOverdueService;

    @ApiOperation(value = "aems逾期查询接口", notes = "aems逾期查询接口")
    @PostMapping("/query")
    public AemsOverdueResultBean userRepay(@RequestBody AemsOverdueRequestBean requestBean, HttpServletRequest request) {
        logger.info("aems逾期查询接口开始,请求参数:" + JSONObject.toJSONString(requestBean));
        AemsOverdueResultBean resultBean = new AemsOverdueResultBean();
        if (null == requestBean.getBorrowNids() || requestBean.getBorrowNids().size()==0) {
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000011);
            resultBean.setStatusDesc("aems逾期查询接口,项目编号不能为空");
            logger.info("-------------------aems逾期查询接口,项目编号和还款期数不能为空！--------------------");
            return resultBean;
        }

//        //验签
//        if(!SignUtil.AEMSVerifyRequestSign(requestBean, "/aems/overdue/query")){
//            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000012);
//            resultBean.setStatusDesc("验签失败！");
//            logger.info("-------------------验签失败！--------------------");
//            return resultBean;
//        }
        //逾期，返回逾期数据
        resultBean = aemsOverdueService.selectRepayOverdue(requestBean);
        if(null == resultBean){
            resultBean = new AemsOverdueResultBean();
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000002);
            resultBean.setStatusDesc("没有查询到对应逾期数据");
            logger.info("aems逾期查询接口未查询到相应的逾期数据！！");
            return resultBean;
        }
        resultBean.setStatusForResponse(AemsErrorCodeConstant.SUCCESS);
        resultBean.setStatusDesc("逾期查询成功");
        logger.info("aems逾期查询接口查询结束--------------" );
        return resultBean;
    }

}
