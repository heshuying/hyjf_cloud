package com.hyjf.callcenter.controller.base;

import com.hyjf.am.vo.user.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.BindException;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.callcenter.beans.BaseFormBean;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.result.BaseResultBean;
import com.hyjf.callcenter.service.UserService;
import com.hyjf.callcenter.util.ValidatorCheckUtil;
import org.springframework.http.converter.HttpMessageNotReadableException;
import io.swagger.annotations.Api;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author libin
 * @version CallcenterBaseController, v0.1 2018/6/5
 */
@Api(value = "callCenter基类")
@RestController
/*@RequestMapping("/xxx/xxx")*/
public class CallcenterBaseController {
	private static final Logger logger = LoggerFactory.getLogger(CallcenterBaseController.class);
	
	public static final Integer CHK_UNIQUENO_SIZE = 15;
	public static final Integer CHK_SEARCH_MAXSIZE = 100;
	@Autowired
	private UserService userService;
	
	/**
	 * 根据用户名或者手机号取得用户信息
	 * @param bean
	 * @param result
	 * @return
	 */
	protected UserVO getUser(UserBean bean, BaseResultBean result) {
		UserVO user = null;
		
		//唯一识别号验证
		if (!checkUniqueNo(bean, result)) {
			return user;
		}
		
		//分页验证
		if (!checkLimit(bean, result)) {
			return user;
		}
		
		//用户名和手机号验证
		if (!checkUser(bean, result)) {
			return user;
		}
		
		//查询用户信息
		return userService.getUser(bean);
	}
	
	/**
	 * 唯一识别号验证
	 * @param bean
	 * @param result
	 */
	protected boolean checkUniqueNo(BaseFormBean bean, BaseResultBean result) {
		String uniqueNo = bean.getUniqueNo();
		//非空check
		if (StringUtils.isBlank(uniqueNo)) {
			result.statusMessage(BaseResultBean.STATUS_FAIL, "唯一识别号不能为空!");
			return false;
		}
		//唯一识别号位数check
		if (uniqueNo.length() != CHK_UNIQUENO_SIZE) {
			result.statusMessage(BaseResultBean.STATUS_FAIL, "唯一识别号必须为" + CHK_UNIQUENO_SIZE + "位");
			return false;
		}
		return true;
	}
	
	/**
	 * 分页验证
	 * @param bean
	 * @param result
	 */
	protected boolean checkLimit(UserBean bean, BaseResultBean result) {
		Integer limitStart = bean.getLimitStart();
		Integer limitSize = bean.getLimitSize();
		//非空check
		if (limitStart==null || limitSize==null) {
			result.statusMessage(BaseResultBean.STATUS_FAIL, "分页信息不能为空!");
			return false;
		}
		//最大记录数check
		if (limitSize > CHK_SEARCH_MAXSIZE) {
			result.statusMessage(BaseResultBean.STATUS_FAIL, "单次检索记录数不能超过"+CHK_SEARCH_MAXSIZE+"条");
			return false;
		}
		//检索记录数必须大于0 check
		if (limitSize<=0) {
			result.statusMessage(BaseResultBean.STATUS_FAIL, "检索记录数必须大于0");
			return false;
		}
		return true;
	}
	
	/**
	 * @param bean
	 * @param result
	 */
	protected boolean checkUser(UserBean bean, BaseResultBean result) {
		String username = bean.getUserName();
		String mobile = bean.getMobile();
		//手机号格式check
		JSONObject errjson = new JSONObject();
		ValidatorCheckUtil.validateMobile(errjson, "手机号", "statusDesc", mobile, 11, false);
		if (ValidatorCheckUtil.hasValidateError(errjson)) {
			result.statusMessage(BaseResultBean.STATUS_FAIL, errjson.getString("statusDesc"));
			return false;
		}
		//非空check
		if (StringUtils.isBlank(username) && StringUtils.isBlank(mobile)) {
			result.statusMessage(BaseResultBean.STATUS_FAIL, "用户名和手机号不能都为空!");
			return false;
		}
		return true;
	}
	
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public BaseResultBean bindExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
    	BaseResultBean errBean = new BaseResultBean();
    	errBean.statusMessage(BaseResultBean.STATUS_FAIL, "传入参数类型错误。");
        return errBean;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public BaseResultBean httpMessageNotReadableExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
    	BaseResultBean errBean = new BaseResultBean();
    	errBean.statusMessage(BaseResultBean.STATUS_FAIL, "传入JSON错误。");
        return errBean;
    }
    
    @ExceptionHandler
    @ResponseBody
    public BaseResultBean handleAndReturnDate(HttpServletRequest request, HttpServletResponse response, Exception ex) {
    	BaseResultBean errBean = new BaseResultBean();
    	errBean.statusMessage(BaseResultBean.STATUS_FAIL, "接口调用发生异常，请联系服务方。");
        return errBean;
    }
	

}
