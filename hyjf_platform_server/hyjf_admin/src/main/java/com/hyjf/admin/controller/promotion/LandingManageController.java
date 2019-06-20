package com.hyjf.admin.controller.promotion;

import com.hyjf.admin.beans.request.LandingManagerRequestBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.beans.vo.TemplateConfigCustomizeVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.LandingManagerService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.TemplateConfigResponse;
import com.hyjf.am.resquest.user.LandingManagerRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.user.TemplateConfigVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author walter.nxl
 * @version LandingManageController, v0.1 2019/6/18 11:17
 */
@Api(tags = "推广中心-着陆页管理")
@RestController
@RequestMapping("/hyjf-admin/promotion/landing")
public class LandingManageController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(LandingManageController.class);
    /**
     * 查看权限
     */
    public static final String PERMISSIONS = "landing";

    @Autowired
    private LandingManagerService landingManagerService;

    @ApiOperation(value = "页面初始化", notes = "初始化下拉列表")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult utmListInit(HttpServletRequest request, HttpServletResponse response) {
        // 模板类型 todo redis 名修改
        Map<String, String> userRoles = CacheUtil.getParamNameMap("TEMP_TYPE");
        List<DropDownVO> listUserRoles = com.hyjf.admin.utils.ConvertUtils.convertParamMapToDropDown(userRoles);
        AdminResult adminResult = new AdminResult();
        adminResult.setData(listUserRoles);
        return adminResult;
    }

    //会员管理列表查询
    @ApiOperation(value = "着陆页管理列表查询", notes = "着陆页管理列表查询")
    @PostMapping(value = "/landinglist")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<TemplateConfigCustomizeVO>> getUserslist(@RequestBody LandingManagerRequestBean landingManagerRequestBean, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LandingManagerRequest landingManagerRequest = new LandingManagerRequest();
        BeanUtils.copyProperties(landingManagerRequestBean, landingManagerRequest);
        TemplateConfigResponse templateConfigResponse = landingManagerService.selectTempConfigList(landingManagerRequest);
        if (!Response.isSuccess(templateConfigResponse)) {
            return new AdminResult<>(FAIL, templateConfigResponse.getMessage());
        }
        List<TemplateConfigVO> templateConfigVOList = templateConfigResponse.getResultList();
        if (null != templateConfigVOList && templateConfigVOList.size() > 0) {
            for (TemplateConfigVO templateConfigVO : templateConfigVOList) {
                if (templateConfigVO.getStatus() == 1) {
                    templateConfigVO.setStatusStr("启用");
                } else {
                    templateConfigVO.setStatusStr("关闭");
                }
                String strCreate = sdf.format(templateConfigVO.getCreateTime());
                templateConfigVO.setCreateTimeStr(strCreate);
            }
        }
        List<TemplateConfigCustomizeVO> templateConfigCustomizeVOS = CommonUtils.convertBeanList(templateConfigVOList, TemplateConfigCustomizeVO.class);
        return new AdminResult<ListResult<TemplateConfigCustomizeVO>>(ListResult.build(templateConfigCustomizeVOS, templateConfigResponse.getCount()));
    }

    @ApiOperation(value = "画面迁移(含有id更新，不含有id添加)", notes = "画面迁移(含有id更新，不含有id添加)")
    @PostMapping("/selectTemplateById")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD, ShiroConstants.PERMISSION_MODIFY})
    public AdminResult<TemplateConfigCustomizeVO> selectTemplateById(HttpServletRequest request, HttpServletResponse response, @RequestBody LandingManagerRequestBean landingManagerRequestBean) {
        TemplateConfigCustomizeVO templateConfigCustomizeVO = new TemplateConfigCustomizeVO();
        if (null != landingManagerRequestBean.getId()) {
            TemplateConfigResponse templateConfigResponse = landingManagerService.selectTemplateById(landingManagerRequestBean.getId());
            if (!Response.isSuccess(templateConfigResponse)) {
                return new AdminResult<>(FAIL, templateConfigResponse.getMessage());
            }
            BeanUtils.copyProperties(templateConfigResponse.getResult(), templateConfigCustomizeVO);

        }
        return new AdminResult<TemplateConfigCustomizeVO>(templateConfigCustomizeVO);
    }

    @ApiOperation(value = "添加或修改信息", notes = "添加或修改信息")
    @PostMapping("/insertTemplate")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD, ShiroConstants.PERMISSION_MODIFY})
    public AdminResult insertTemplate(HttpServletRequest request, HttpServletResponse response, @RequestBody LandingManagerRequestBean landingManagerRequestBean) {
        //根据id判断，如存在，则为修改，如不存在，则为新增
        LandingManagerRequest landingManagerRequest = new LandingManagerRequest();
        AdminSystemVO adminSystemVO = this.getUser(request);
        int intFlg = 0;
        if (null != landingManagerRequestBean.getId()) {
            //修改
            TemplateConfigResponse templateConfigResponse = landingManagerService.selectTemplateById(landingManagerRequestBean.getId());
            if (!Response.isSuccess(templateConfigResponse)) {
                return new AdminResult<>(FAIL, templateConfigResponse.getMessage());
            }
        }
        BeanUtils.copyProperties(landingManagerRequestBean, landingManagerRequest);
        landingManagerRequest.setLoginUserId(adminSystemVO.getId());
        intFlg = landingManagerService.updateOrInsertTemplate(landingManagerRequest);
        if (intFlg <= 0) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "删除信息", notes = "删除信息")
    @PostMapping("/deleteaction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteAction(HttpServletRequest request, HttpServletResponse response, @RequestBody LandingManagerRequestBean landingManagerRequestBean) {
        AdminResult adminResult = new AdminResult();
        //根据utmId判断，如存在，则为修改，如不存在，则为新增
        if (null != landingManagerRequestBean.getId()) {
            //int tempId = Integer.parseInt();
            boolean flag = landingManagerService.deleteTemplate(landingManagerRequestBean.getId());
            if (!flag) {
                adminResult.setStatus(AdminResult.FAIL);
                adminResult.setStatusDesc("系统异常，请联系管理员!");
            }
        } else {
            adminResult.setStatus(AdminResult.FAIL);
            adminResult.setStatusDesc("删除异常！");
        }
        return adminResult;
    }

}
