package com.hyjf.admin.controller.manager;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowCommonService;
import com.hyjf.admin.service.InstConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminInstConfigDetailResponse;
import com.hyjf.am.response.admin.AdminInstConfigListResponse;
import com.hyjf.am.response.config.LinkResponse;
import com.hyjf.am.resquest.admin.AdminInstConfigListRequest;
import com.hyjf.am.vo.admin.HjhInstConfigWrapVo;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.LinkVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @author by xiehuili on 2018/7/5.
 * @version InstConfigController, v0.1 2018/7/5.
 */
@Api(tags ="配置中心-机构配置")
@RestController
@RequestMapping("/hyjf-admin/config/instconfig")
public class InstConfigController extends BaseController {
    //权限名称
    private static final String PERMISSIONS = "instconfig";
    @Autowired
    private InstConfigService instConfigService;

    @Autowired
    private BorrowCommonService borrowCommonService;

    @ApiOperation(value = "查询配置中心机构配置", notes = "查询配置中心机构配置")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<HjhInstConfigWrapVo>> instConfigInit( @RequestBody AdminInstConfigListRequest adminRequest) {
        AdminInstConfigDetailResponse response=instConfigService.instConfigInit(adminRequest);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<ListResult<HjhInstConfigWrapVo>>(ListResult.build(response.getResultList(), response.getRecordTotal())) ;
    }

    @ApiOperation(value = "机构配置详情页面", notes = "机构配置详情页面")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult<HjhInstConfigWrapVo>  instConfigInfo(@RequestBody AdminInstConfigListRequest adminRequest) {
        AdminInstConfigDetailResponse adminResponse= instConfigService.searchInstConfigInfo(adminRequest);
        if (adminResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(adminResponse)) {
            return new AdminResult<>(FAIL, adminResponse.getMessage());
        }
        return new AdminResult<HjhInstConfigWrapVo>(adminResponse.getResult()) ;
    }

    @ApiOperation(value = "机构配置添加", notes = "机构配置添加")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertInstConfig(HttpServletRequest request, @RequestBody AdminInstConfigListRequest req) {
        AdminSystemVO user = getUser(request);
        req.setUserId(Integer.parseInt(user.getId()));
//        req.setUserId(3);//为了接口测试用

        AdminInstConfigListResponse prs = instConfigService.saveInstConfig(req);
        if(prs==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(prs)) {
            return new AdminResult<>(FAIL, prs.getMessage());

        }
        return new AdminResult<>();
    }
    @ApiOperation(value = "机构配置修改", notes = "机构配置修改")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateInstConfig(HttpServletRequest request, @RequestBody AdminInstConfigListRequest req) {
        AdminSystemVO user = getUser(request);
        req.setUserId(Integer.parseInt(user.getId()));
//        req.setUserId(3);//为了接口测试用
        AdminInstConfigListResponse prs = instConfigService.updateInstConfig(req);
        if(prs==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(prs)) {
            return new AdminResult<>(FAIL, prs.getMessage());

        }
        return new AdminResult<>();
    }
    @ApiOperation(value = "机构配置删除", notes = "机构配置删除")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteInstConfig( @RequestBody AdminInstConfigListRequest req) {
        AdminInstConfigListResponse prs =new AdminInstConfigListResponse();
        if(StringUtils.isNotBlank(req.getIds())){
            prs = instConfigService.deleteInstConfig(req);
        }
        if(prs==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(prs)) {
            return new AdminResult<>(FAIL, prs.getMessage());

        }
        return new AdminResult<>();
    }


    /**
     * 发标额度上限校验
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "机构配置发标额度上限校验", notes = "机构配置发标额度上限校验")
    @PostMapping("/topLimitCheckAction")
    public AdminResult topLimitCheckAction(HttpServletRequest request,@RequestParam(value="capitalToplimitStr",required = true)String capitalToplimitStr,
                                      @RequestParam(value="capitalUsedStr",required = false)String capitalUsedStr) {
        JSONObject ret = new JSONObject();
        //新增配置instCode
        if (StringUtils.isBlank(capitalUsedStr) || "undefined".equals(capitalUsedStr)) {
            ret.put("error", "发标额度余额不可为空，请重试.");
            return new AdminResult<String>(ret.toJSONString());
        }
        if (StringUtils.isNotBlank(capitalToplimitStr)) {
            BigDecimal capitalToplimit = new BigDecimal(capitalToplimitStr);
            BigDecimal capitalUsed = new BigDecimal(capitalUsedStr);
            if (capitalToplimit.compareTo(capitalUsed) < 0) {
                ret.put("error", "发标额度余额不可为负数，请重试.");
                return new AdminResult<String>(ret.toJSONString());
            }
        }
        //校验通过正常返回
        ret.put("status", "y");
        return new AdminResult<String>(ret.toJSONString());
    }

    @ApiOperation(value = "获取担保公司合作列表", notes = "获取担保公司合作列表")
    @GetMapping("/getBondingCompany")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult getBondingCompany() {
        LinkResponse response = borrowCommonService.getLinks();
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult(response.getResultList()) ;
//        return new AdminResult<ListResult<LinkVO>>(ListResult.build(response.getResultList(), response.getCount())) ;
    }


}
