package com.hyjf.wbs.user.controller.bank;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.resquest.user.BankOpenRequest;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.wbs.user.controller.BaseController;
import com.hyjf.wbs.user.dao.model.auto.UserInfo;
import com.hyjf.wbs.user.service.bank.BankOpenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(value = "demo", tags = "demo")
@RequestMapping("/am-user/bankopen")
public class BankOpenController extends BaseController {

	@Autowired
	private BankOpenService bankOpenService;

	/**
	 * 插入开户日志表
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "插入开户日志表")
	@PostMapping("/updateUserAccountLog")
	public IntegerResponse updateUserAccountLog(@RequestBody @Valid BankOpenRequest request) {
		logger.info("updateUserAccountLog...param is :{}", JSONObject.toJSONString(request));
		
		Integer userId = request.getUserId();
		String username = request.getUsername();
		String mobile = request.getMobile();
		String orderId = request.getOrderId();
		String channel = request.getChannel();
		String trueName = request.getTrueName();
		String idNo = request.getIdNo();
		String cardNO = request.getCardNo();
		
		boolean result = this.bankOpenService.updateUserAccountLog(userId, username, mobile, orderId, channel, trueName,idNo,cardNO);
        
		return new IntegerResponse(result?1:0);
	}

	/**
	 *
	 * @param cardId 450109197702086442
	 * @return
	 */
	@ApiOperation(value = "查询userInfo")
	@GetMapping("/findByCardId/{cardId}")
	public UserInfoResponse findByCardId(@PathVariable String cardId) {
		UserInfoResponse response = new UserInfoResponse();
		UserInfo usersInfo = bankOpenService.findUserInfoByCradId(cardId);
		if (usersInfo != null) {
			UserInfoVO userInfoVO = new UserInfoVO();
			BeanUtils.copyProperties(usersInfo, userInfoVO);
			response.setResult(userInfoVO);
		}
		return response;
	}

}
