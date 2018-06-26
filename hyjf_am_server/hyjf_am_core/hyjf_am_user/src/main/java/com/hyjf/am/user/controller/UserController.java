package com.hyjf.am.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.service.UserService;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.MD5Utils;
import com.hyjf.common.validator.Validator;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiasq
 * @version UserController, v0.1 2018/1/21 22:37
 */

@RestController
@RequestMapping("/am-user/user")
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public UserResponse register(@RequestBody @Valid RegisterUserRequest userRequest) {
		logger.info("user register:" + JSONObject.toJSONString(userRequest));
		UserResponse userResponse = new UserResponse();
		User user = null;
		try {
			user = userService.register(userRequest);

			if (user == null) {
				userResponse.setRtn(Response.FAIL);
				userResponse.setMessage(Response.FAIL_MSG);
				logger.error("user register error,user " + userRequest.getMobile());
			} else {
				UserVO userVO = new UserVO();
				BeanUtils.copyProperties(user, userVO);
				userResponse.setResult(userVO);
			}
		} catch (MQException e) {
			logger.error("user register error...", e);
			userResponse.setRtn(Response.FAIL);
			userResponse.setMessage(Response.FAIL_MSG);
		}

		return userResponse;
	}

	/**
	 *  根据渠道号检索渠道是否存在
	 * @param utmId
	 * @return
	 */
	@RequestMapping("/selectUtmPlatByUtmId/{utmId}")
	public UtmPlatResponse selectUtmPlatByUtmId(String utmId){
		UtmPlat utmPlat = userService.selectUtmPlatByUtmId(utmId);
		UtmPlatResponse response = new UtmPlatResponse();
		if(null!= utmPlat){
			UtmPlatVO utmPlatVO = new UtmPlatVO();
			BeanUtils.copyProperties(utmPlat,utmPlatVO);
			response.setResult(utmPlatVO);
		}
		return response;
	}

	/**
	 * 根据userId查询
	 *
	 * @param userId
	 * @return
	 */
	@RequestMapping("/findById/{userId}")
	public UserResponse findUserByUserId(@PathVariable int userId) {
		logger.info("findUserByUserId run...userId is :{}", userId);
		UserResponse response = new UserResponse();
		User user = userService.findUserByUserId(userId);
		if (user != null) {
			UserVO userVO = new UserVO();
			BeanUtils.copyProperties(user, userVO);
			response.setResult(userVO);
		}
		return response;
	}

	/**
	 * 根据mobile查找用户
	 *
	 * @param mobile
	 * @return
	 */
	@RequestMapping("/findByMobile/{mobile}")
	public UserResponse findUserByMobile(@PathVariable String mobile) {
		logger.info("findUserByMobile...mobile is :{}", mobile);
		UserResponse response = new UserResponse();
		User user = userService.findUserByMobile(mobile);
		if (user != null) {
			UserVO userVO = new UserVO();
			BeanUtils.copyProperties(user, userVO);
			response.setResult(userVO);
		}
		return response;
	}

	/**
	 * 根据username 或者 mobile查询用户
	 *
	 * @param condition
	 * @return
	 */
	@RequestMapping("/findByCondition/{condition}")
	public UserResponse findUserByCondition(@PathVariable String condition) {
		logger.info("findUserByCondition run...condition is :{}", condition);
		UserResponse response = new UserResponse();
		User user = userService.findUserByUsernameOrMobile(condition);
		if (user != null) {
			UserVO userVO = new UserVO();
			BeanUtils.copyProperties(user, userVO);
			response.setResult(userVO);
		}
		return response;
	}

	/**
	 * 根据推荐人手机号或userId 查询推荐人
	 *
	 * @param reffer
	 * @return
	 */
	@RequestMapping("/findReffer/{reffer}")
	public UserResponse findUserByRecommendName(@PathVariable String reffer) {
		logger.info("findUserByRecommendName run...reffer is :{}", reffer);
		UserResponse response = new UserResponse();
		User user = userService.findUserByRecommendName(reffer);
		if (user != null) {
			UserVO userVO = new UserVO();
			BeanUtils.copyProperties(user, userVO);
			response.setResult(userVO);
		}
		return response;
	}

	@RequestMapping("/updateLoginUser/{userId}/{ip}")
	public void updateLoginUser(@PathVariable int userId, @PathVariable String ip) {
		/**
		 * ip version等作为请求一部分的时候，用base64解码
		 */
		String args = "";
		try {
			args = new String(Base64.decodeBase64(ip.getBytes()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new ReturnMessageException(Response.FAIL_MSG);
		}

		logger.info("updateLoginUser run...userId is :{}, ip is :{}", userId, args);
		userService.updateLoginUser(userId, args);
	}

	@RequestMapping("/getHjhUserAuthByUserId/{userId}")
	public HjhUserAuthResponse getHjhUserAuthByUserId(@PathVariable Integer userId) {
		logger.info("getHjhUserAuthByUserId run...userId is :{}", userId);
		HjhUserAuthResponse response = new HjhUserAuthResponse();
		HjhUserAuth hjhUserAuth = userService.getHjhUserAuthByUserId(userId);
		if (hjhUserAuth != null) {
			HjhUserAuthVO hjhUserAuthVO = new HjhUserAuthVO();
			BeanUtils.copyProperties(hjhUserAuth, hjhUserAuthVO);
			response.setResult(hjhUserAuthVO);
		}
		return response;
	}

	@RequestMapping("/insertLogSelective")
	public void insertLogSelective(@RequestBody HjhUserAuthLog hjhUserAuthLog) {
		logger.info("insertSelective:" + JSONObject.toJSONString(hjhUserAuthLog));
		userService.insertSelective(hjhUserAuthLog);
	}

	@RequestMapping("/selectByExample/{orderId}")
	public HjhUserAuthLogResponse selectByExample(@RequestBody String orderId) {
		HjhUserAuthLogResponse response = new HjhUserAuthLogResponse();
		HjhUserAuthLog hjhUserAuthLog = userService.selectByExample(orderId);
		if (null != hjhUserAuthLog) {
			HjhUserAuthLogVO hjhUserAuthLogVO = new HjhUserAuthLogVO();
			BeanUtils.copyProperties(hjhUserAuthLog, hjhUserAuthLogVO);
			response.setResult(hjhUserAuthLogVO);
		}
		return response;
	}

	@RequestMapping("/updateUserAuthInves")
	public void updateUserAuthInves(@RequestBody BankRequest bean) {
		userService.updateUserAuthInves(bean);
	}

	/**
	 * 根据userId修改用户信息
	 * return
	 */
	@RequestMapping("/updateByUserId")
	public int updateByUserId(@RequestBody User user) {
		if (user == null || user.getUserId() == null) {
			return 0;
		}
		logger.info("updatePassWord run...user is :{}", user.toString());
		return userService.updateUserById(user);
	}

	/**
	 * 根据userId修改用户信息
	 * return
	 */
	@RequestMapping("/updatePassWd/{userId}/{oldPW}/{newPW}")
	public JSONObject updatePassWd(@PathVariable Integer userId, @PathVariable String oldPW, @PathVariable String newPW) {
		logger.info("UserController.updatePassWd run...userId is :{}, oldPW is :{}, newPW is :{}", userId, oldPW, newPW);
		JSONObject ret = new JSONObject();
		if (userId == null || StringUtils.isBlank(oldPW) || StringUtils.isBlank(newPW)) {
			ret.put("status", "1");
			ret.put("statusDesc", "请求参数非法");
			return ret;
		}
		User user = userService.findUserByUserId(userId);

		// 验证用的password
		oldPW = MD5Utils.MD5(MD5Utils.MD5(oldPW) + user.getSalt());
		if (!oldPW.equals(user.getPassword())) {
			ret.put("status", "1");
			ret.put("statusDesc", "旧密码不正确");
			return ret;
		}

		if (newPW.length() < 6 || newPW.length() > 16) {
			ret.put("status", "1");
			ret.put("statusDesc", "密码长度6-16位");
			return ret;
		}


		boolean hasNumber = false;
		for (int i = 0; i < newPW.length(); i++) {
			if (Validator.isNumber(newPW.substring(i, i + 1))) {
				hasNumber = true;
				break;
			}
		}
		if (!hasNumber) {
			ret.put("status", "1");
			ret.put("statusDesc", "密码必须包含数字");
			return ret;
		}
		String regEx = "^[a-zA-Z0-9]+$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(newPW);
		if (!m.matches()) {
			ret.put("status", "1");
			ret.put("statusDesc", "密码必须由数字和字母组成，如abc123");
			return ret;
		}

		User iuser = new User();
		iuser.setUserId(userId);
		iuser.setPassword(MD5Utils.MD5(MD5Utils.MD5(newPW) + user.getSalt()));
		boolean success = userService.updateUserById(user) > 0;
		if (success) {
			ret.put("status", "0");
			ret.put("statusDesc", "修改密码成功");
		} else {
			ret.put("status", "1");
			ret.put("statusDesc", "修改密码失败,未作任何操作");
		}
		return ret;
	}

	@RequestMapping("/selectUserEvalationResultByUserId/{userId}")
	public UserEvalationResultResponse selectUserEvalationResultByUserId(@PathVariable Integer userId) {
		if (null == userId) {
			return null;
		}
		UserEvalationResultResponse response = new UserEvalationResultResponse();
		UserEvalationResult userEvalationResult = userService.selectUserEvalationResultByUserId(userId);
		if (null != userEvalationResult) {
			UserEvalationResultVO userEvalationResultVO = new UserEvalationResultVO();
			BeanUtils.copyProperties(userEvalationResult, userEvalationResultVO);
			response.setResult(userEvalationResultVO);
		}
		return response;
	}

	@RequestMapping("/deleteUserEvalationResultByUserId")
	public void deleteUserEvalationResultByUserId(@RequestBody Integer userId) {
		userService.deleteUserEvalationResultByUserId(userId);
	}

	@RequestMapping("/getAccountChinapnr/{userId}")
	public AccountChinapnrResponse getAccountChinapnr(@PathVariable Integer userId) {
		AccountChinapnrResponse response = new AccountChinapnrResponse();
		AccountChinapnr accountChinapnr = userService.getAccountChinapnr(userId);
		if (null != accountChinapnr) {
			AccountChinapnrVO accountChinapnrVO = new AccountChinapnrVO();
			BeanUtils.copyProperties(accountChinapnr, accountChinapnrVO);
			response.setResult(accountChinapnrVO);
		}
		return response;
	}

	/**
	 * 保存紧急联系人信息
	 *
	 * @param bean
	 * @return
	 */
	@RequestMapping("/updateUserContract")
	public int updateUserContract(@RequestBody UsersContractRequest bean) {
		return userService.updateUserContact(bean);
	}

	/**
	 * @Author: zhangqingqing
	 * @Desc : 查询紧急联系人
	 * @Param: * @param userId
	 * @Date: 14:21 2018/6/4
	 * @Return: UsersContactResponse
	 */
	@RequestMapping("/selectUserContact/{userId}")
	public UsersContactResponse selectUserContact(@PathVariable Integer userId) {
		UsersContactResponse response = new UsersContactResponse();
		UserContact usersContact = userService.selectUserContact(userId);
		if (null != usersContact) {
			UsersContactVO usersContactVO = new UsersContactVO();
			BeanUtils.copyProperties(usersContact, usersContactVO);
			response.setResult(usersContactVO);
		}
		return response;
	}

	/**
	 * 检查邮箱是否已使用
	 *
	 * @param email
	 * @return
	 */
	@RequestMapping("/checkEmailUsed/{email}")
	public boolean checkEmailUsed(@PathVariable String email) {
		return userService.checkEmailUsed(email);
	}

	/**
	 * 插入绑定邮箱日志
	 *
	 * @param log
	 */
	@RequestMapping("/insertBindEmailLog")
	public void insertEmailBindLog(@RequestBody BindEmailLogRequest log) {
		UserBindEmailLog bean = new UserBindEmailLog();
		BeanUtils.copyProperties(log, bean);
		userService.insertEmailBindLog(bean);
	}

	/**
	 * 查询绑定邮箱日志
	 *
	 * @param userid
	 * @return
	 */
	@RequestMapping("/getBindEmailLog/{userid}")
	public BindEmailLogResponse getUserBindEmail(@PathVariable Integer userid) {
		BindEmailLogResponse response = new BindEmailLogResponse();
		UserBindEmailLog log = userService.getUserBindEmail(userid);
		if (log != null) {
			BindEmailLogVO logVO = new BindEmailLogVO();
			BeanUtils.copyProperties(log, logVO);
			response.setResult(logVO);
		}
		return response;
	}

	/**
	 * 绑定邮箱更新
	 *
	 * @param userId
	 * @param email
	 * @param
	 */
	@RequestMapping("/updateBindEmail/{userId}/{email}")
	public void updateBindEmail(@PathVariable Integer userId, @PathVariable String email) {
		userService.updateBindEmail(userId, email);
	}

	/**
	 * 用户登录日志
	 *
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getUserLoginById/{userId}")
	public UserLoginLogResponse getUserLoginById(@PathVariable Integer userId) {
		UserLoginLogResponse response = new UserLoginLogResponse();
		UserLoginLog userLoginLog = userService.selectByPrimaryKey(userId);
		if (null != userLoginLog) {
			UserLoginLogVO userLoginLogVO = new UserLoginLogVO();
			BeanUtils.copyProperties(userLoginLog, userLoginLogVO);
			response.setResult(userLoginLogVO);
		}
		return response;

	}

	/**
	 * 根据垫付机构用户名检索垫付机构用户
	 *
	 * @param repayOrgName
	 * @return
	 */
	@RequestMapping("/selectUserByUsername/{repayOrgName}")
	public UserResponse selectUserByUsername(@PathVariable String repayOrgName) {
		UserResponse response = new UserResponse();
		List<User> userList = userService.selectUserByUsername(repayOrgName);
		if (!CollectionUtils.isEmpty(userList)) {
			List<UserVO> voList = CommonUtils.convertBeanList(userList, UserVO.class);
			response.setResultList(voList);
		}
		return response;
	}

	@RequestMapping("/getEvalationByCountScore/{countScore}")
	public EvalationResponse getEvalationByCountScore(@PathVariable short countScore) {
		EvalationResponse response = new EvalationResponse();
		Evalation evalation = userService.getEvalationByCountScore(countScore);
		if (null != evalation) {
			EvalationVO evalationVO = new EvalationVO();
			BeanUtils.copyProperties(evalation, evalationVO);
			response.setResult(evalationVO);
		}
		return response;
	}

	@RequestMapping("/getEvalationRecord")
	public EvalationResponse getEvalationRecord() {
		EvalationResponse response = new EvalationResponse();
		List<Evalation> evalationList = userService.getEvalationRecord();
		if(!CollectionUtils.isEmpty(evalationList)){
			List<EvalationVO> evalationVOList = CommonUtils.convertBeanList(evalationList,EvalationVO.class);
			response.setResultList(evalationVOList);
		}
		return response;
	}

	@RequestMapping("/insertUserEvalationResult")
	public UserEvalationResultResponse insertUserEvalationResult(@RequestBody UserEvalationRequest userEvalationRequest) {
		List<String> answerList = userEvalationRequest.getAnswerList();
		List<String> questionList = userEvalationRequest.getQuestionList();
		EvalationVO evalation = userEvalationRequest.getEvalation();
		int countScore = userEvalationRequest.getCountScore();
		Integer userId = userEvalationRequest.getUserId();
		UserEvalationResultVO oldUserEvalationResult = userEvalationRequest.getUserEvalationResultVO();
		UserEvalationResult userEvalationResult = userService.insertUserEvalationResult(answerList, questionList, evalation, countScore, userId, oldUserEvalationResult);
		UserEvalationResultResponse response = new UserEvalationResultResponse();
		if(null!= userEvalationResult){
			UserEvalationResultVO userEvalationResultVO = new UserEvalationResultVO();
			BeanUtils.copyProperties(userEvalationResult,userEvalationResultVO);
			response.setResult(userEvalationResultVO);
		}
		return response;
	}

	@RequestMapping("/isCompAccount/{userId}")
	public int isCompAccount(Integer userId){
		int count = userService.isCompAccount(userId);
		return count;
	}

    /**
     * 查询用户推广链接注册
     * @param userId
     * @return
     */
    @RequestMapping("/findUtmRegByUserId/{userId}")
    public UtmRegResponse findUtmRegByUserId(@RequestBody Integer userId) {
        UtmRegResponse response = new UtmRegResponse();
        UtmReg utmReg = userService.findUtmRegByUserId(userId);
        if (null != utmReg) {
            UtmRegVO utmRegVO = new UtmRegVO();
            BeanUtils.copyProperties(utmReg, utmRegVO);
            response.setResult(utmRegVO);
        }
        return response;
    }

    /**
     * 更新渠道用户首次投资信息
     * @param bean
     * @return
     */
    @RequestMapping("/updateFirstUtmReg")
    public Integer updateFirstUtmReg(@RequestBody Map<String, Object> bean) {
        try {
            userService.updateFirstUtmReg(bean);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @PostMapping("/updateUserInvestFlag")
	public boolean updateUserInvestFlag(@RequestBody JSONObject para){
		return userService.updateUserInvestFlag(para);
	}


	@PostMapping("/insertVipUserTender")
	public boolean insertVipUserTender(@RequestBody JSONObject para){
		return userService.insertVipUserTender(para);
	}

}
