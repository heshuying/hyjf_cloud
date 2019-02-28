/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.hyjf.admin.beans.response.EvaluationBorrowLevelConfigResponseBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.EvaluationBorrowLevelConfigService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.EvaluationBorrowLevelConfigResponse;
import com.hyjf.am.resquest.admin.EvaluationBorrowLevelConfigRequest;
import com.hyjf.am.vo.admin.EvaluationBorrowLevelConfigVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.common.cache.CacheUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 风险测评配置- 信用等级配置Controller
 *
 * @author liuyang
 * @version BorrowLevelConfigController, v0.1 2018/12/24 16:43
 */
@Api(tags = "配置中心-风险测评配置信用等级配置")
@RestController
@RequestMapping("/hyjf-admin/manager/config/borrowlevelconfig")
public class EvaluationBorrowLevelConfigController extends BaseController {

    public static final String PERMISSIONS = "borrowLevelConfig";

    @Autowired
    private EvaluationBorrowLevelConfigService service;

    @ApiOperation(value = "风险测评配置信用等级配置", notes = "风险测评配置信用等级配置")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<EvaluationBorrowLevelConfigVO>> getRecordList(@RequestBody EvaluationBorrowLevelConfigRequest requestBean) {
        EvaluationBorrowLevelConfigResponse response = service.getEvaluationBorrowLevelConfigList(requestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }


    @SuppressWarnings("unused")
	@ApiOperation(value = "修改画面迁移", notes = "修改画面迁移")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<EvaluationBorrowLevelConfigResponseBean> info(@RequestBody EvaluationBorrowLevelConfigRequest requestBean) {
        logger.info(EvaluationBorrowLevelConfigController.class.toString(), "infoAction");
        EvaluationBorrowLevelConfigResponseBean response = new EvaluationBorrowLevelConfigResponseBean();
        // modify by libin sonar start 非空拿到前面
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        // modify by libin sonar end
        // 风险测评投资等级
        Map<String, String> investLevelList = CacheUtil.getParamNameMap("INVEST_LEVEL");
        List<DropDownVO> investLevelDropDownVO = ConvertUtils.convertParamMapToDropDown(investLevelList);
        if (requestBean.getId() != null) {
            EvaluationBorrowLevelConfigVO evaluationBorrowLevelConfigVO = this.service.getEvaluationBorrowLevelConfigById(requestBean.getId());
            response.setForm(evaluationBorrowLevelConfigVO);
            response.setInvestLevel(investLevelDropDownVO);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        logger.info(EvaluationBorrowLevelConfigController.class.toString(), "infoAction");
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "修改风险测评配置信用等级配置", notes = "修改风险测评配置信用等级配置")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateAction(HttpServletRequest request, @RequestBody EvaluationBorrowLevelConfigRequest requestBean) {
        AdminSystemVO loginUser = getUser(request);
        requestBean.setUpdateUser(loginUser.getUsername());
        EvaluationBorrowLevelConfigResponse response = service.updateBorrowLevelConfig(requestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }


}
