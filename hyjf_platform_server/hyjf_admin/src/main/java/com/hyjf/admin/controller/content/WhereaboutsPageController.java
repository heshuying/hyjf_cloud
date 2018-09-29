/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.content;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.beans.request.WhereaboutsPageRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.WhereaboutsPageService;
import com.hyjf.admin.utils.FileUpLoadUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.config.WhereaboutsPageResponse;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.config.WhereaboutsPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * @author tanyy
 * @version WhereaboutsPageController, v0.1 2018/7/20 10:35
 */
@Api(value = "移动端着陆页管理",tags ="内容中心-移动端着陆页管理")
@RestController
@RequestMapping("/hyjf-admin/content/whereaboutspage")
public class WhereaboutsPageController extends BaseController {
	@Autowired
	private WhereaboutsPageService whereaboutsPageService;

	@ApiOperation(value = "移动端着陆页管理", notes = "移动端着陆页管理列表查询")
	@PostMapping("/searchaction")
	public AdminResult<ListResult<WhereaboutsPageVo>> searchAction(@RequestBody WhereaboutsPageRequestBean requestBean) {
		WhereaboutsPageResponse response = whereaboutsPageService.searchAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	@ApiOperation(value = "移动端着陆页管理", notes = "添加移动端着陆页管理")
	@PostMapping("/insert")
	public AdminResult add(@RequestBody WhereaboutsPageRequestBean requestBean) {
		WhereaboutsPageResponse response = whereaboutsPageService.insertAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "移动端着陆页管理", notes = "修改移动端着陆页管理")
	@PostMapping("/update")
	public AdminResult update(@RequestBody WhereaboutsPageRequestBean requestBean) {
		WhereaboutsPageResponse response = whereaboutsPageService.updateAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}
	@ApiOperation(value = "移动端着陆页管理", notes = "修改移动端着陆页管理状态")
	@PostMapping("/updatestatus")
	public AdminResult updateStatus(@RequestBody WhereaboutsPageRequestBean requestBean) {
		WhereaboutsPageResponse response = whereaboutsPageService.updateStatus(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}
	@ApiOperation(value = "移动端着陆页管理", notes = "删除着路页管理")
	@GetMapping("/delete/{id}")
	public AdminResult delete(@PathVariable Integer id) {
		WhereaboutsPageResponse response = whereaboutsPageService.deleteById(id);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}


    @ApiOperation(value = "检查渠道", notes = "检查渠道")
    @GetMapping("/checkutmid/{utmId}")
    public AdminResult checkUtmId(@PathVariable Integer utmId) {
        AdminResult adminResult = new AdminResult();
        StringResponse msg = this.whereaboutsPageService.checkUtmId(utmId);
        adminResult.setData(msg.getResult());
        return adminResult;
    }

    @ApiOperation(value = "检查推广人", notes = "检查推广人")
    @GetMapping("/checkreferrer/{referrer}")
    public AdminResult checkReferrer(@PathVariable String referrer) {
        AdminResult adminResult = new AdminResult();
        StringResponse msg = this.whereaboutsPageService.checkReferrer(referrer);
        adminResult.setData(msg.getResult());
        return adminResult;
    }

	/**
	 * 迁移到更新画面
	 *
	 * @param
	 * @param
	 * @return
	 */
	@ApiOperation(value = "移动端着陆页修改获取信息", notes = "移动端着陆页修改获取信息")
	@PostMapping("/updateinfoaction")
	public AdminResult updateInfoAction(@RequestBody WhereaboutsPageRequestBean requestBean) {
		//样式
		AdminResult adminResult = new  AdminResult();
		JSONObject jsonObject = new JSONObject();
		List<ParamNameVO> pageStyles = this.whereaboutsPageService.getParamNameList("WHEREABOUTS_STYLE");
		jsonObject.put("pageStyles",pageStyles);
		if(requestBean.getId()!=null&&requestBean.getId()!=0){
			WhereaboutsPageResponse response = 	whereaboutsPageService.getWhereaboutsPageConfigById(requestBean);
			adminResult.setData(response.getForm());
		}
		return adminResult;
	}

	@Autowired
	private FileUpLoadUtil fileUpLoadUtil;
	/**
	 * 资料上传
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "移动端着陆页管理", notes = "资料上传")
	@PostMapping("/uploadfile")
	public AdminResult uploadFile(HttpServletRequest request) throws Exception {
        AdminResult adminResult = new AdminResult();
        LinkedList<BorrowCommonImage> borrowCommonImages = fileUpLoadUtil.upLoad(request);
        adminResult.setData(borrowCommonImages);
        return adminResult;
	}
}
