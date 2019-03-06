package com.hyjf.cs.user.controller.web.bankopenloan;

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
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * 
 * @author sunss
 *
 */
@Api(value = "web端-借款人开户",tags = "web端-借款人开户")
@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/hyjf-web/user/secure/loanbankopen")
public class LoanBankOpenController extends BaseUserController {
	private static final Logger logger = LoggerFactory.getLogger(LoanBankOpenController.class);

	@Autowired
	private BankOpenService bankOpenService;

	@Autowired
	SystemConfig systemConfig;

    @ApiOperation(value = "获取开户信息", notes = "获取开户信息")
	@GetMapping(value = "/init")
    @ResponseBody
	public WebResult<Object> init(@RequestHeader(value = "userId") int userId) {
        UserVO user = this.bankOpenService.getUsersById(userId);
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
    @ApiOperation(value = "借款人开户", notes = "用户开户")
	@PostMapping(value = "/openBankAccount")
    @ResponseBody
	public WebResult<Object> openBankAccount(@RequestHeader(value = "userId") int userId, @RequestBody @Valid BankOpenVO bankOpenVO, HttpServletRequest request) {
        logger.info("web  借款人开户 start, bankOpenVO is :{}", JSONObject.toJSONString(bankOpenVO));
        WebResult<Object> result = new WebResult<Object>();

        UserVO user = this.bankOpenService.getUsersById(userId);
        // 检查请求参数
        bankOpenService.checkRequestParam(user, bankOpenVO);

        OpenAccountPageBean openBean = new OpenAccountPageBean();
        try {
			PropertyUtils.copyProperties(openBean, bankOpenVO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
        openBean.setChannel(BankCallConstant.CHANNEL_PC);
        openBean.setUserId(user.getUserId());
        openBean.setIp(CustomUtil.getIpAddr(request));
        openBean.setPlatform(ClientConstants.WEB_CLIENT+"");
        openBean.setClientHeader(ClientConstants.CLIENT_HEADER_PC);
        openBean.setIdentity(BankCallConstant.ACCOUNT_USER_IDENTITY_2);

        // 组装参数
        Map<String,Object> data = bankOpenService.getLoanOpenAccountMV(openBean,null);
        result.setData(data);
        //保存开户日志
        int uflag = this.bankOpenService.updateUserAccountLog(user.getUserId(), user.getUsername(), openBean.getMobile(), openBean.getOrderId(),CustomConstants.CLIENT_PC ,openBean.getTrueName(),openBean.getIdNo(),"", "");
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
    @ApiOperation(value = "页面开户异步处理", notes = "页面开户异步处理")
    @PostMapping("/bgReturn")
    @ResponseBody
    public BankCallResult openAccountBgReturn( @RequestBody BankCallBean bean, @RequestParam("phone") String mobile) {
        logger.info("web端开户异步处理start,userId:{}", bean.getLogUserId());
        bean.setMobile(mobile);
        BankCallResult result = bankOpenService.openAccountBgReturn(bean);
        return result;
    }

    /**
     * @Description 查询开户失败原因
     * @Author sunss
     */
    @ApiOperation(value = "借款人开户查询开户失败原因", notes = "查询开户失败原因")
    @PostMapping("/seachFiledMess")
    @ResponseBody
    public WebResult<Object> seachFiledMess(@RequestHeader(value = "userId") int userId,@RequestParam("logOrdId") String logOrdId) {
        logger.info("查询开户失败原因start,logOrdId:{},userid:{}", logOrdId,userId);
        WebResult<Object> result = bankOpenService.getFiledMess(logOrdId,userId);
        return result;
    }
}