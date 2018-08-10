package com.hyjf.admin.controller.productcenter.plancenter.plancapital;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.HjhPlanCapitalRequestBean;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.PlanCapitalService;
import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 产品中心 --> 汇计划 --> 资金计划
 * @Author : huanghui
 */
@Api(value = "产品中心-汇计划-资金计划",tags ="产品中心-汇计划-资金计划")
@RestController
@RequestMapping(value = "/plancapital")
public class PlanCapitalController extends BaseController {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private PlanCapitalService planCapitalService;

    /**
     * 计划资金 列表
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "资金计划", notes = "资金计划列表")
    @PostMapping(value = "/init")
    @ResponseBody
    public JSONObject init(@RequestBody @Valid HjhPlanCapitalRequestBean requestBean){

        HjhPlanCapitalRequest hjhPlanCapitalRequest = new HjhPlanCapitalRequest();
        BeanUtils.copyProperties(requestBean, hjhPlanCapitalRequest);

        //初始化时时间不能为空
        if (StringUtils.isEmpty(hjhPlanCapitalRequest.getDateFromSrch()) && StringUtils.isEmpty(hjhPlanCapitalRequest.getDateToSrch())){
            return this.fail("日期不能为空!");
        }

        try{
            Date timeStart = dateFormat.parse(hjhPlanCapitalRequest.getDateFromSrch());
            Date timeEnd = dateFormat.parse(hjhPlanCapitalRequest.getDateToSrch());

            if (timeStart.getTime() > timeEnd.getTime()){
                return this.fail("结束时间应大于等于开始时间!");
            }
        }catch (ParseException e){
            return this.fail(e.getMessage());
        }

        //获取总条数
//        Integer count = this.planCapitalService.getPlanCapitaCount(request);
//
//        //设置分页
//        Page page = Page.initPage(hjhPlanCapitalRequest.getCurrPage(), hjhPlanCapitalRequest.getPageSize());
//        page.setTotal(count);
//        hjhPlanCapitalRequest.setLimitStart(page.getOffset());
//        hjhPlanCapitalRequest.setLimitEnd(page.getLimit());

        //查询列表
        List<HjhPlanCapitalVO> capitalVOList = this.planCapitalService.getPlanCapitalList(hjhPlanCapitalRequest);
        JSONObject jsonObject = this.success(String.valueOf(1), capitalVOList);
        return  jsonObject;
    }


}
