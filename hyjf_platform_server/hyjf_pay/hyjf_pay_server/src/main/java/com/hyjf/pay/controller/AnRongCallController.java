package com.hyjf.pay.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyjf.common.spring.SpringUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.base.BaseController;
import com.hyjf.pay.bean.AnRongCallDefine;
import com.hyjf.pay.call.BankCallApi;
import com.hyjf.pay.config.SystemConfig;
import com.hyjf.pay.lib.anrong.AnRongCallApi;
import com.hyjf.pay.lib.anrong.AnRongCallApiImpl;
import com.hyjf.pay.lib.anrong.bean.AnRongBean;
import com.hyjf.pay.lib.anrong.util.AnRongParamConstant;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.config.PaySystemConfig;

/**
 * 
 * 调用安融接口
 * @author sss
 * @version hyjf 1.0
 * @since hyjf 1.0 2017年10月10日
 * @see 下午3:12:36
 */
@Controller
@RequestMapping(value = AnRongCallDefine.CONTROLLOR_REQUEST_MAPPING)
public class AnRongCallController extends BaseController {
	Logger logger = LoggerFactory.getLogger(AnRongCallController.class);
    @Autowired
    private AnRongCallApi apii;
    @Autowired
    SystemConfig systemConfig;

	/**
	 * 调用接口(后台)
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 */
    @PostMapping(value = "callApiBg.json")
    @ResponseBody
	public String callApiBg(HttpServletRequest request, @RequestBody AnRongBean bean) throws Exception {

		String methodName = "callApiBg";
		logger.info(AnRongCallDefine.CONTROLLOR_CLASS_NAME, methodName, "[调用接口开始, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
		String ret = "";
		String logOrderId = bean.getLogOrderId();
		try {
			// 参数转换成Map
			bean.convert();
			// 消息类型
			String txCode = bean.getTxCode();
			if (Validator.isNull(txCode)) {
				throw new RuntimeException("消息类型不能为空");
			}
			// 操作时间
			int nowTime = GetDate.getNowTime10();
			bean.setLogTime(String.valueOf(nowTime));
			String result = "";
			// 发送前插入状态记录
//			Long logId = anRongCallService.insertAnRongSendLog(bean);
//			if (logId == null || logId <= 0) {
//				throw new RuntimeException("接口调用前,保存请求数据失败！订单号：" + bean.getLogOrderId());
//			}
			// 得到接口API对象
//			Class<AnRongCallApiImpl> c = AnRongCallApiImpl.class;
//			Object obj = api;
//			// 取得该消息类型对应的bean
//			Method method = c.getMethod(txCode, AnRongApiBean.class);
//			Object retBean = method.invoke(obj, bean);
//			if (retBean != null && retBean instanceof AnRongApiBean) {
//			    AnRongApiBean anRongApiBean = (AnRongApiBean) retBean;
			 bean.setMember(systemConfig.getAnrongmembe());
			 bean.setSign(systemConfig.getAnrongsign());
			 bean.convert();
            // 获得接口URL
            if(AnRongParamConstant.TXCODE_QUERY.equals(bean.get(BankCallConstant.PARAM_TXCODE))){
            	 result = apii.callAnRongApi(bean,systemConfig.getAnrongqueryurl()); 
            }else if (AnRongParamConstant.TXCODE_SEND_MESS.equals(bean.get(BankCallConstant.PARAM_TXCODE))){
            	 result = apii.callAnRongApi(bean,systemConfig.getAnrongsendurl()); 
            }
				// 调用安融API接口
				// 插入返回值
//				boolean updateFlag = anRongCallService.updateAnRongSendLog(logId, result);
//				if (!updateFlag) {
//					throw new RuntimeException("接口调用后,保存回调数据失败！订单号：" + logId);
//				}
				ret = result;
//			}
		} catch (Exception e) {
			logger.error(AnRongCallDefine.CONTROLLOR_CLASS_NAME, methodName, "订单号：" + logOrderId, e);
		} finally {
			logger.info(AnRongCallDefine.CONTROLLOR_CLASS_NAME, methodName, "[调用接口结束, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
		}
		return ret;
	}
}