package com.hyjf.admin.controller.productcenter.borrow.borrowrepaycurrent;

import com.alibaba.fastjson.JSON;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowRepayInfoCurrentService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BorrowRepayInfoCurrentResponse;
import com.hyjf.am.resquest.admin.BorrowRepayInfoCurrentRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 当前债权还款明细
 * @author hesy
 */
@Api(value = "产品中心-汇直投-当前债权还款明细",tags ="产品中心-汇直投-当前债权还款明细")
@RestController
@RequestMapping("/hyjf-admin/borrow/repayinfocurrent")
public class BorrowRepayInfoCurrentController extends BaseController {
    /** 查看权限 */
    public static final String PERMISSIONS = "repayinfocurrent";

    @Autowired
    private BorrowRepayInfoCurrentService borrowRepayInfoCurrentService;

    /**
     * 画面初始化
     *
     * @param request
     */
    @ApiOperation(value = "当前债权还款明细页面数据", notes = "当前债权还款明细页面数据")
    @PostMapping(value = "/searchAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<Map<String,Object>> searchAction(HttpServletRequest request, @RequestBody @Valid BorrowRepayInfoCurrentRequest requestBean) {
        logger.info("当前债权还款明细-start, requestBean:{}", JSON.toJSONString(requestBean));
        AdminResult<Map<String,Object>> response = new AdminResult<>();
        Map<String,Object> dataMap = new HashMap<>();

        //请求参数校验
        if(requestBean.getCurrPage() <= 0){
            requestBean.setCurrPage(1);
        }
        if(requestBean.getPageSize() <= 0){
            requestBean.setPageSize(10);
        }
        //borrowNid为必须传的参数
        if(StringUtils.isBlank(requestBean.getBorrowNid())){
            response.setStatus(AdminResult.FAIL);
            response.setStatusDesc("请求参数错误，borrowNid为空");
            return response;
        }

        //请求原子层获取当前债权还款信息数据
        BorrowRepayInfoCurrentResponse amResponse = borrowRepayInfoCurrentService.getRepayInfoCurrentData(requestBean.getBorrowNid());
        if(amResponse == null || !Response.SUCCESS.equals(amResponse.getRtn())){
            response.setStatus(AdminResult.FAIL);
            response.setStatusDesc(amResponse.getMessage());
            return response;
        }

        dataMap.put("recordList", amResponse.getResultList());
        dataMap.put("sumInfo", amResponse.getSumInfo());
        response.setData(dataMap);
        response.setTotalCount(amResponse.getCount());
        logger.info("当前债权还款明细-end, response:{}", JSON.toJSONString(response));
        return response;
    }
}
