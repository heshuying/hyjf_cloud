/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.hyjf.admin.beans.request.PushMoneyRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.PushMoneyService;
import com.hyjf.admin.utils.ValidatorFieldCheckUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.PushMoneyResponse;
import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.vo.trade.PushMoneyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 提成设置
 * 
 * @author fuqiang
 * @version PushMoneyController, v0.1 2018/7/10 10:56
 */
@Api(tags = "配置中心-提成配置")
@RestController
@RequestMapping("/hyjf-admin/pushmoney")
public class PushMoneyController extends BaseController {

	@Autowired
	private PushMoneyService pushMoneyService;

	public static final String PERMISSIONS = "pushmoney";

	@ApiOperation(value = "获取提成配置列表", notes = "获取提成配置列表")
	@PostMapping("/init")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<ListResult<PushMoneyVO>> getRecordList(@RequestBody PushMoneyRequest requestBean) {
		PushMoneyResponse response = pushMoneyService.getRecordList(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	@ApiOperation(value = "画面迁移(含有id更新，不含有id添加)", notes = "画面迁移(含有id更新，不含有id添加)")
	@PostMapping("/info")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<PushMoneyResponse> getInfoAction(@RequestBody PushMoneyRequest requestBean) {
        PushMoneyResponse response = new PushMoneyResponse();
		if (requestBean.getId() != null) {
			response = pushMoneyService.getInfoAction(requestBean.getId());
		}
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(response);
	}

	@ApiOperation(value = "添加提成配置", notes = "添加提成配置")
	@PostMapping("/insert")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
	public AdminResult add(@RequestBody PushMoneyRequestBean requestBean) {
		PushMoneyResponse response = pushMoneyService.insertPushMoney(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	@ApiOperation(value = "修改提成配置", notes = "修改提成配置")
	@PostMapping("/update")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_UPDATE)
	public AdminResult update(@RequestBody PushMoneyRequestBean requestBean) {
        ModelAndView modelAndView = new ModelAndView();
	    // 调用校验
        if (validatorFieldCheck(modelAndView, requestBean) != null) {
            // 失败返回
            return new AdminResult<>(FAIL, "表单校验未通过");
        }
		PushMoneyResponse response = pushMoneyService.updatePushMoney(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(response);
	}

   /* @ApiOperation(value = "删除配置信息", notes = "删除配置信息")
    @PostMapping("/delete")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult<ListResult<PushMoneyVO>> deleteRecord(@RequestBody PushMoneyRequest requestBean) {
        PushMoneyResponse response = new PushMoneyResponse();
        if (StringUtils.isNotBlank(requestBean.getIds())) {
            // 解析json字符串
            List<Integer> ids = JSONArray.parseArray(requestBean.getIds(), Integer.class);
            response = pushMoneyService.deleteRecord(ids);
        }
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }*/

    /**
     * 调用校验表单方法
     *
     * @param modelAndView
     * @param form
     * @return
     */
    private ModelAndView validatorFieldCheck(ModelAndView modelAndView, PushMoneyRequestBean form) {
        // 字段校验
        if (form.getType() != null
                && !ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "name", form.getType(), 20, true)) {
            return modelAndView;
        }
        if (form.getMonthTender() != null
                && !ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "value", form.getMonthTender(), 20, true)) {
            return modelAndView;
        }
        return null;
    }
}
