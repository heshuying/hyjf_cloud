package com.hyjf.admin.controller.productcenter.plancenter.daycreditdetail;

import com.hyjf.admin.beans.request.DayCreditDetailRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.DayCreditDetailService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DayCreditDetailResponse;
import com.hyjf.am.resquest.admin.DayCreditDetailRequest;
import com.hyjf.am.vo.trade.hjh.DayCreditDetailVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 转让详情
 * @Author : huanghui
 */
@Api(value = "产品中心-汇计划-资金计划",tags ="产品中心-汇计划-资金计划")
@RestController
@RequestMapping(value = "/dayCreditDetail")
public class DayCreditDetailController extends BaseController {

    @Autowired
    private DayCreditDetailService dayCreditDetailService;

    /**
     * 资金计划 - 汇计划按天转让记录列表
     * @param request
     * @return
     */
    @ApiOperation(value = "资金计划", notes = "汇计划按天转让记录列表")
    @PostMapping(value = "hjhDayCreditDetailList")
    @ResponseBody
    public AdminResult<ListResult<DayCreditDetailVO>> init(@RequestBody @Valid DayCreditDetailRequestBean request){

        DayCreditDetailRequest copyRequest = new DayCreditDetailRequest();
        BeanUtils.copyProperties(request, copyRequest);

        //默认应传入清算日期和转让人planNid
        if (StringUtils.isEmpty(request.getDate())){
            return new AdminResult<>(FAIL, "清算日期不能为空!");
        }
        if (StringUtils.isEmpty(request.getPlanNid())){
            return new AdminResult<>(FAIL, "转让人PlanNid不能为空!");
        }

        //初始化返回List
        List<DayCreditDetailVO> returnList = null;

        //类表查询
        DayCreditDetailResponse response = this.dayCreditDetailService.hjhDayCreditDetailList(copyRequest);

        if (response == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        if (!Response.isSuccess(response)){
            return new AdminResult<>(FAIL, response.getMessage());
        }

        if (CollectionUtils.isNotEmpty(response.getResultList())){
            returnList = CommonUtils.convertBeanList(response.getResultList(), DayCreditDetailVO.class);
            return new AdminResult<ListResult<DayCreditDetailVO>>(ListResult.build(returnList, response.getCount()));
        }else {
            return new AdminResult<ListResult<DayCreditDetailVO>>(ListResult.build(returnList, 0));
        }
    }
}
