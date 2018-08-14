package com.hyjf.admin.controller.productcenter.plancenter.planrepay;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.HjhRepayRequestBean;
import com.hyjf.admin.beans.response.HjhRepayResponseBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.PlanRepayService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.resquest.admin.HjhRepayRequest;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 产品中心 --> 汇计划 --> 订单退出  Warning : 汇计划三期 订单退出改为计划还款
 * @Author : huanghui
 */
@Api(value = "产品中心-汇计划-订单退出",tags ="产品中心-汇计划-订单退出")
@RestController
@RequestMapping(value = "/planrepay")
public class PlanRepayController extends BaseController {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private PlanRepayService planRepayService;

    /**
     * 订单退出初始化列表
     * @param repayRequestBean
     * @return
     */
    @ApiOperation(value = "订单退出", notes = "订单退出初始化列表")
    @PostMapping(value = "/init")
    @ResponseBody
    public JSONObject init(@RequestBody @Valid HjhRepayRequestBean repayRequestBean){
        HjhRepayRequest repayRequest = new HjhRepayRequest();
        BeanUtils.copyProperties(repayRequestBean, repayRequest);

        //初始化时应默认清算时间不能为空
        if (StringUtils.isEmpty(repayRequest.getRepayTimeStart()) && StringUtils.isEmpty(repayRequest.getRepayTimeEnd())){
            return this.fail("请输入清算开始和结束时间!");
        }

        //实际退出时间不能为空
        if (StringUtils.isNotEmpty(repayRequest.getActulRepayTimeStart()) && StringUtils.isNotEmpty(repayRequest.getActulRepayTimeEnd())){
            return this.fail("请输入实际退出开始和结束时间!");
        }

        //防止前端将起始时间传错误.
        try {
            Date timeStart = dateFormat.parse(repayRequest.getRepayTimeStart());
            Date timeEnd = dateFormat.parse(repayRequest.getRepayTimeEnd());

            if (timeStart.getTime() > timeEnd.getTime()){
                return this.fail("清算结束时间应大于等于开始时间!");
            }

            Date actuTimeStart = dateFormat.parse(repayRequest.getActulRepayTimeStart());
            Date actuTimeEnd = dateFormat.parse(repayRequest.getActulRepayTimeEnd());
            if (actuTimeStart.getTime() > actuTimeEnd.getTime()){
                return this.fail("实际退出结束时间应大于等于开始时间!");
            }
        }catch (ParseException e){
            return this.fail(e.getMessage());
        }

        Integer count = this.planRepayService.selectRepayCount(repayRequest);

        Page page = Page.initPage(repayRequestBean.getCurrPage(), repayRequestBean.getPageSize());
        page.setTotal(count);
        repayRequestBean.setLimitStart(page.getOffset());
        repayRequestBean.setLimitEnd(page.getLimit());

        //查询列表
        List<HjhRepayVO> repayResponseBean = this.planRepayService.selectByExample(repayRequest);
        JSONObject jsonObject = this.success(String.valueOf(count), repayResponseBean);
        return jsonObject;
    }

    /**
     * 订单退出检索列表
     * @param repayRequestBean
     * @return
     */
    @ApiOperation(value = "订单退出", notes = "订单退出查询列表")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject search(@RequestBody @Valid HjhRepayRequestBean repayRequestBean){

        HjhRepayRequest repayRequest = new HjhRepayRequest();
        BeanUtils.copyProperties(repayRequestBean, repayRequest);

        //初始化时应默认带入初始时间
        if (StringUtils.isEmpty(repayRequest.getRepayTimeStart()) && StringUtils.isEmpty(repayRequest.getRepayTimeEnd())){
            return this.fail("请输入开始和结束时间!");
        }

        //防止前端将起始时间传错误.
        try {
            Date timeStart = dateFormat.parse(repayRequest.getRepayTimeStart());
            Date timeEnd = dateFormat.parse(repayRequest.getRepayTimeEnd());

            if (timeStart.getTime() > timeEnd.getTime()){
                return this.fail("结束时间应大于等于开始时间!");
            }

            Date actuTimeStart = dateFormat.parse(repayRequest.getActulRepayTimeStart());
            Date actuTimeEnd = dateFormat.parse(repayRequest.getActulRepayTimeEnd());
            if (actuTimeStart.getTime() > actuTimeEnd.getTime()){
                return this.fail("实际退出结束时间应大于等于开始时间!");
            }
        }catch (ParseException e){
            return this.fail(e.getMessage());
        }

        Integer count = this.planRepayService.selectRepayCount(repayRequest);

        Page page = Page.initPage(repayRequestBean.getCurrPage(), repayRequestBean.getPageSize());
        page.setTotal(count);
        repayRequestBean.setLimitStart(page.getOffset());
        repayRequestBean.setLimitEnd(page.getLimit());

        //查询列表
        List<HjhRepayVO> repayResponseBean = this.planRepayService.selectByExample(repayRequest);
        JSONObject jsonObject = this.success(String.valueOf(count), repayResponseBean);

        return jsonObject;
    }

    @ApiOperation(value = "还款明细", notes = "订单退出->还款明细")
    @PostMapping(value = "/repaymentDetails")
    public AdminResult repaymentDetails(@RequestBody HjhRepayRequestBean repayRequestBean){

        HjhRepayRequest repayRequest = new HjhRepayRequest();
        BeanUtils.copyProperties(repayRequestBean, repayRequest);

        HjhRepayResponseBean repayResponseBean = planRepayService.selectByAccedeOrderId(repayRequestBean.getAccedeOrderIdSrch());

        return new AdminResult(repayResponseBean);
    }

}
