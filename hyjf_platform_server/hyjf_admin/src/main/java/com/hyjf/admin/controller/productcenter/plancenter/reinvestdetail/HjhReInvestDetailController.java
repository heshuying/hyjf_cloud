package com.hyjf.admin.controller.productcenter.plancenter.reinvestdetail;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.HjhReInvestDetailRequestBean;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.HjhReInvestDetailService;
import com.hyjf.am.resquest.admin.HjhReInvestDetailRequest;
import com.hyjf.am.vo.trade.hjh.HjhReInvestDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 复投原始标的
 * @Author : huanghui
 */
@Api(value = "产品中心-汇计划-资金计划",tags ="产品中心-汇计划-资金计划-复投详情")
@RestController
@RequestMapping(value = "/hjhReInvestDetail")
public class HjhReInvestDetailController extends BaseController {

    @Autowired
    private HjhReInvestDetailService hjhReInvestDetailService;

    /**
     * 获取复投原始标的列表,初始化只需当前日期和planNid
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "资金计划", notes = "复投详情列表")
    @PostMapping(value = "/hjhReInvest")
    @ResponseBody
    public JSONObject hjhReInvestList(@RequestBody @Valid HjhReInvestDetailRequestBean requestBean){

        HjhReInvestDetailRequest reInvestDetailRequest = new HjhReInvestDetailRequest();
        BeanUtils.copyProperties(requestBean, reInvestDetailRequest);

        if (StringUtils.isEmpty(requestBean.getDate())){
            return this.fail("Date不能为空!");
        }

        if (StringUtils.isEmpty(requestBean.getPlanNid())){
            return this.fail("PlanNid不能为空!");
        }

        //总条数
        Integer count = this.hjhReInvestDetailService.countHjhReInvestDetailTotal(requestBean.getDate(), requestBean.getPlanNid());

//        if (count > 0 ){
            //获取列表
            List<HjhReInvestDetailVO> investDetailVOList = this.hjhReInvestDetailService.hjhReInvestDetailList(requestBean.getDate(), requestBean.getPlanNid());

            JSONObject jsonObject = this.success(String.valueOf(count), investDetailVOList);
            return jsonObject;
//        }
    }

}
