package com.hyjf.cs.user.controller.web.bankopen;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.bean.OpenAccountPageBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.bankopen.BankOpenService;
import com.hyjf.cs.user.vo.BankOpenVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author sunss
 *
 */
@Api(value = "web端用户开户接口")
@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/web/user/secure/open")
public class WebBankOpenController extends BaseUserController {
	private static final Logger logger = LoggerFactory.getLogger(WebBankOpenController.class);

	@Autowired
	private BankOpenService bankOpenService;

	@Autowired
	SystemConfig systemConfig;

	@GetMapping(value = "/init")
    @ResponseBody
	public WebResult<Object> init(@RequestHeader(value = "token", required = true) String token) {
        UserVO user = this.bankOpenService.getUsers(token);
        WebResult<Object> result = new WebResult<Object>();
        if(user==null){
            throw new CheckException(MsgEnum.ERR_USER_NOT_LOGIN);
        }
        if(user.getBankOpenAccount()==1){
            throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_ALREADY_OPEN);
        }
        result.setStatus(ApiResult.SUCCESS);
        Map<String,String> map = new HashedMap();
        map.put("mobile",user.getMobile());
        result.setData(map);
		return result;
	}

	/**
	 * @Description 开户
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/12 10:17
	 */
    @ApiOperation(value = "web端用户开户", notes = "用户开户")
	@PostMapping(value = "/openBankAccount")
    @ResponseBody
	public WebResult<Object> openBankAccount(@RequestHeader(value = "token", required = true) String token, @RequestBody @Valid BankOpenVO bankOpenVO, HttpServletRequest request) {
        logger.info("web  openBankAccount start, bankOpenVO is :{}", JSONObject.toJSONString(bankOpenVO));
        WebResult<Object> result = new WebResult<Object>();
        // 验证请求参数
        if (token == null) {
            throw new CheckException(MsgEnum.ERR_USER_NOT_LOGIN);
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
        // 开户角色
        openBean.setIdentity(BankCallConstant.ACCOUNT_USER_IDENTITY_3);
        // 组装参数
        Map<String,Object> data = bankOpenService.getOpenAccountMV(openBean);
        result.setData(data);
        //保存开户日志
        int uflag = this.bankOpenService.updateUserAccountLog(user.getUserId(), user.getUsername(), openBean.getMobile(), openBean.getOrderId(),CustomConstants.CLIENT_PC ,openBean.getTrueName(),openBean.getIdNo(),"");
        if (uflag == 0) {
            logger.info("保存开户日志失败,手机号:[" + openBean.getMobile() + "],用户ID:[" + user.getUserId() + "]");
            throw new CheckException(MsgEnum.STATUS_CE999999);
        }
        logger.info("开户end");
		return result;
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

    /**
     * @Description 查询开户失败原因
     * @Author sunss
     */
    @ApiOperation(value = "查询开户失败原因", notes = "查询开户失败原因")
    @RequestMapping("/seachFiledMess")
    @ResponseBody
    public WebResult<Object> seachFiledMess(@RequestParam("logOrdId") String logOrdId) {
        logger.info("查询开户失败原因start,logOrdId:{}", logOrdId);
        WebResult<Object> result = bankOpenService.getFiledMess(logOrdId);
        return result;
    }
}