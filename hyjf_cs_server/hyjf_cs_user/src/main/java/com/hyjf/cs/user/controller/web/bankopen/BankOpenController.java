package com.hyjf.cs.user.controller.web.bankopen;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.common.validator.ValidatorCheckUtil;
import com.hyjf.cs.user.bean.OpenAccountPageBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.OpenAccountError;
import com.hyjf.cs.user.service.bankopen.BankOpenService;
import com.hyjf.cs.user.vo.BankOpenVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author sunss
 *
 */
@Api(value = "web端用户开户接口")
@Controller
@RequestMapping("/web/secure/open")
public class BankOpenController {
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
	public ModelAndView openBankAccount(@RequestHeader(value = "token", required = true) String token, @RequestBody @Valid BankOpenVO bankOpenVO, HttpServletRequest request, Model model) {
        logger.info("openBankAccount start, bankOpenVO is :{}", JSONObject.toJSONString(bankOpenVO));
		
		ModelAndView reuslt = new ModelAndView("bankopen/error");

        // 验证请求参数
        if (token == null) {
            throw new ReturnMessageException(OpenAccountError.USER_NOT_LOGIN_ERROR);
        }
        
        UserVO user = this.bankOpenService.getUsers(token);
        
        if (user == null) {
            throw new ReturnMessageException(OpenAccountError.GET_USER_INFO_ERROR);
        }
        
        // 手机号
        if (StringUtils.isEmpty(bankOpenVO.getMobile())) {
            throw new ReturnMessageException(OpenAccountError.MOBILE_NULL_ERROR);
        }
        // 姓名
        if (StringUtils.isEmpty(bankOpenVO.getTrueName())) {
            throw new ReturnMessageException(OpenAccountError.TRUENAME_NULL_ERROR);
        }else{
            //判断真实姓名是否包含空格
            if (!ValidatorCheckUtil.verfiyChinaFormat(bankOpenVO.getTrueName())) {
                throw new ReturnMessageException(OpenAccountError.TRUENAME_BLANKL_ERROR);
            }
            //判断真实姓名的长度,不能超过10位
            if (bankOpenVO.getTrueName().length() > 10) {
                throw new ReturnMessageException(OpenAccountError.TRUENAME_LENGTH_ERROR);
            }
        }
        // 身份证号
        if (StringUtils.isEmpty(bankOpenVO.getIdNo())) {
            throw new ReturnMessageException(OpenAccountError.IDNO_NULL_ERROR);
        }

        if (bankOpenVO.getIdNo().length() != 18) {
            throw new ReturnMessageException(OpenAccountError.IDNO_FORMAT_ERROR);
        }
        String idNo = bankOpenVO.getIdNo().toUpperCase().trim();
        bankOpenVO.setIdNo(idNo);
        //增加身份证唯一性校验
        boolean isOnly = bankOpenService.checkIdNo(idNo);
        if (isOnly) {
            throw new ReturnMessageException(OpenAccountError.IDNO_USED_ERROR);
        }
        if(!Validator.isMobile(bankOpenVO.getMobile())){
            throw new ReturnMessageException(OpenAccountError.MOBILE_FORMAT_ERROR);
        }
        String mobile = user.getMobile();
        if (StringUtils.isBlank(mobile)) {
            if (StringUtils.isNotBlank(bankOpenVO.getMobile())) {
                if(!bankOpenService.existUser(bankOpenVO.getMobile())){
                    mobile = bankOpenVO.getMobile();
                }else{
                    throw new ReturnMessageException(OpenAccountError.MOBILE_USED_ERROR);
                }
            } else {
                throw new ReturnMessageException(OpenAccountError.MOBILE_ERROR);
            }
        } else {
            if (StringUtils.isNotBlank(bankOpenVO.getMobile()) && !mobile.equals(bankOpenVO.getMobile())) {
                throw new ReturnMessageException(OpenAccountError.MOBILE_ERROR);
            }
        }
        // 拼装参数 调用江西银行
        // 同步调用路径
        String retUrl = systemConfig.getWebHost() + "/web/secure/open/return"+ "?phone="+bankOpenVO.getMobile();;
        // 异步调用路
        String bgRetUrl = systemConfig.getWebHost() + "/web/secure/open/bgReturn" + "?phone="+bankOpenVO.getMobile();

        OpenAccountPageBean openBean = new OpenAccountPageBean();
        
        try {
			PropertyUtils.copyProperties(openBean, bankOpenVO);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

        openBean.setChannel(BankCallConstant.CHANNEL_PC);
        openBean.setUserId(user.getUserId());
        openBean.setIp(CustomUtil.getIpAddr(request));
        // 同步 异步
        openBean.setRetUrl(retUrl);
        openBean.setNotifyUrl(bgRetUrl);
        openBean.setCoinstName("汇盈金服");
        openBean.setPlatform("0");
        // 账户用途 写死
        /*00000-普通账户
        10000-红包账户（只能有一个）
        01000-手续费账户（只能有一个）
        00100-担保账户*/
        openBean.setAcctUse("00000");
        /**
         *  1：出借角色
            2：借款角色
            3：代偿角色
         */
        openBean.setIdentity("1");
        reuslt = getCallbankMV(openBean);
        //保存开户日志
        int uflag = this.bankOpenService.updateUserAccountLog(user.getUserId(), user.getUsername(), openBean.getMobile(), openBean.getOrderId(),CustomConstants.CLIENT_PC ,openBean.getTrueName(),openBean.getIdNo(),openBean.getCardNo());
        if (uflag == 0) {
            logger.info("保存开户日志失败,手机号:[" + openBean.getMobile() + "],用户ID:[" + user.getUserId() + "]");
            throw new ReturnMessageException(OpenAccountError.SYSTEM_ERROR);
        }
        logger.info("开户end");
    
		return reuslt;
	}
	

    public ModelAndView getCallbankMV(OpenAccountPageBean openBean) {
        ModelAndView mv = new ModelAndView();
        // 根据身份证号码获取性别
        String gender = "";
        int sexInt = Integer.parseInt(openBean.getIdNo().substring(16, 17));
        if (sexInt % 2 == 0) {
            gender = "F";
        } else {
            gender = "M";
        }
        // 获取共同参数
        String bankCode =systemConfig.getBankCode();
        String bankInstCode = systemConfig.getBankInstcode();
        String orderDate = GetOrderIdUtils.getOrderDate();
        String txDate = GetOrderIdUtils.getTxDate();
        String txTime = GetOrderIdUtils.getTxTime();
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        String idType = BankCallConstant.ID_TYPE_IDCARD;
        // 调用开户接口
        BankCallBean openAccoutBean = new BankCallBean();
        openAccoutBean.setVersion(BankCallConstant.VERSION_10);
        openAccoutBean.setTxCode(BankCallConstant.TXCODE_ACCOUNT_OPEN_PAGE);
        openAccoutBean.setInstCode(bankInstCode);
        openAccoutBean.setBankCode(bankCode);
        openAccoutBean.setTxDate(txDate);
        openAccoutBean.setTxTime(txTime);
        openAccoutBean.setSeqNo(seqNo);
        openAccoutBean.setChannel(openBean.getChannel());
        openAccoutBean.setIdType(idType);
        openAccoutBean.setIdNo(openBean.getIdNo());
        openAccoutBean.setName(openBean.getTrueName());
        openAccoutBean.setGender(gender);
        openAccoutBean.setMobile(openBean.getMobile());
        openAccoutBean.setAcctUse(openBean.getAcctUse());
        openAccoutBean.setIdentity(openBean.getIdentity());
        openAccoutBean.setRetUrl(openBean.getRetUrl());
        openAccoutBean.setNotifyUrl(openBean.getNotifyUrl());
        openAccoutBean.setCoinstName(openBean.getCoinstName());
        // 银行卡号
        openAccoutBean.setCardNo(openBean.getCardNo());
        
        // 页面调用必须传的
        String orderId = GetOrderIdUtils.getOrderId2(openBean.getUserId());
        openAccoutBean.setLogBankDetailUrl(BankCallConstant.BANK_URL_ACCOUNT_OPEN_PAGE);
        openAccoutBean.setLogOrderId(orderId);
        openAccoutBean.setLogOrderDate(orderDate);
        openAccoutBean.setLogUserId(String.valueOf(openBean.getUserId()));
        openAccoutBean.setLogRemark("外部服务接口:开户页面");
        openAccoutBean.setLogIp(openBean.getIp());
        openAccoutBean.setLogClient(Integer.parseInt(openBean.getPlatform()));
        openBean.setOrderId(orderId);
        try {
            mv = BankCallUtils.callApi(openAccoutBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    /**
     * web开户同步跳转地址
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "web端用户同步回调", notes = "web端用户开户")
    @PostMapping(value = "/return")
    public Map<String, String> returnPage(HttpServletRequest request, @RequestHeader(value = "token", required = true) String token) {
        String isSuccess = request.getParameter("isSuccess");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }
        logger.info("web端开户同步请求,token:{},isSuccess:{}", token, isSuccess);
        Map<String, String> result = bankOpenService.openAccountReturn(token, isSuccess);
        logger.info("web端开户同步请求返回值：{}", JSONObject.toJSONString(result));
        return result;
    }

    /**
     * web页面开户异步处理
     *
     * @param bean
     * @return
     */
    @ApiOperation(value = "web端页面开户异步处理", notes = "web端页面开户异步处理")
    @PostMapping("/bgReturn")
    public BankCallResult openAccountBgReturn(@RequestBody @Valid BankCallBean bean, @RequestParam("phone") String mobile) {
        logger.info("web端开户异步处理start,userId:{}", bean.getLogUserId());
        bean.setMobile(mobile);
        BankCallResult result = bankOpenService.openAccountBgReturn(bean);
        return result;
    }
}