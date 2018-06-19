package com.hyjf.cs.user.controller.web.bankopen;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.common.validator.ValidatorCheckUtil;
import com.hyjf.cs.user.bean.OpenAccountPageBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.AuthorizedError;
import com.hyjf.cs.user.constants.OpenAccountError;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.bankopen.BankOpenService;
import com.hyjf.cs.user.vo.BankOpenVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 
 * @author sunss
 *
 */
@Api(value = "web端用户开户接口")
@Controller
@RequestMapping("/web/secure/open")
public class BankOpenController extends BaseUserController {
	private static final Logger logger = LoggerFactory.getLogger(BankOpenController.class);

	@Autowired
	private BankOpenService bankOpenService;

	@Autowired
	SystemConfig systemConfig;

	@GetMapping(value = "/init")
	public String init(Model model) {
		return "bankopen/init";
	}

	/**
	 * @Description 开户
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/12 10:17
	 */
    @ApiOperation(value = "web端用户开户", notes = "用户开户")
	@PostMapping(value = "/openBankAccount")
	public ModelAndView openBankAccount(@RequestHeader(value = "token", required = true) String token, @RequestBody @Valid BankOpenVO bankOpenVO, HttpServletRequest request) {
        logger.info("web  openBankAccount start, bankOpenVO is :{}", JSONObject.toJSONString(bankOpenVO));
		
		ModelAndView reuslt = new ModelAndView();
        // 验证请求参数
        if (token == null) {
            throw new ReturnMessageException(OpenAccountError.USER_NOT_LOGIN_ERROR);
        }
        UserVO user = this.bankOpenService.getUsers(token);
        // 检查请求参数
        bankOpenService.checkRequestParam(user, bankOpenVO);

        OpenAccountPageBean openBean = new OpenAccountPageBean();
        try {
			PropertyUtils.copyProperties(openBean, bankOpenVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
        openBean.setChannel(BankCallConstant.CHANNEL_PC);
        openBean.setUserId(user.getUserId());
        openBean.setIp(CustomUtil.getIpAddr(request));
        openBean.setPlatform(ClientConstants.WEB_CLIENT+"");
        openBean.setClientHeader(ClientConstants.CLIENT_HEADER_PC);
        // 组装参数
        reuslt = bankOpenService.getOpenAccountMV(openBean);
        //保存开户日志
        int uflag = this.bankOpenService.updateUserAccountLog(user.getUserId(), user.getUsername(), openBean.getMobile(), openBean.getOrderId(),CustomConstants.CLIENT_PC ,openBean.getTrueName(),openBean.getIdNo(),"");
        if (uflag == 0) {
            logger.info("保存开户日志失败,手机号:[" + openBean.getMobile() + "],用户ID:[" + user.getUserId() + "]");
            throw new ReturnMessageException(OpenAccountError.SYSTEM_ERROR);
        }
        logger.info("开户end");
		return reuslt;
	}

    /**
     * web页面开户异步处理
     *
     * @param bean
     * @return
     */
    @ApiOperation(value = "web端页面开户异步处理", notes = "web端页面开户异步处理")
    @RequestMapping("/bgReturn")
    @ResponseBody
    public BankCallResult openAccountBgReturn(BankCallBean bean, @RequestParam("phone") String mobile) {
        logger.info("web端开户异步处理start,userId:{}", bean.getLogUserId());
        bean.setMobile(mobile);
        BankCallResult result = bankOpenService.openAccountBgReturn(bean);
        return result;
    }
}