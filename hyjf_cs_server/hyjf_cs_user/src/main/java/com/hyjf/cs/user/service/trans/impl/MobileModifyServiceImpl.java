/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.trans.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.FddCertificateAuthorityVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.mq.producer.FddCertificateProducer;
import com.hyjf.cs.user.result.MobileModifyResultBean;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.trans.MobileModifyService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

/**
 * @author zhangqingqing
 * @version MobileModifyServiceImpl, v0.1 2018/6/14 16:48
 */
@Service
public class MobileModifyServiceImpl extends BaseUserServiceImpl implements MobileModifyService {
	private static final Logger logger = LoggerFactory.getLogger(MobileModifyServiceImpl.class);

    @Autowired
    AmUserClient amUserClient;
    @Autowired
    AmConfigClient amConfigClient;
    @Autowired
    FddCertificateProducer fddProducer;
    
    /**
     * 更换手机号条件校验
     * @param newMobile
     * @param smsCode
     */
    @Override
    public boolean checkForMobileModify(String newMobile, String smsCode) {
        String verificationType = CommonConstant.PARAM_TPL_BDYSJH;
        int cnt = amUserClient.checkMobileCode(newMobile, smsCode, verificationType, CommonConstant.CLIENT_PC,
                CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_USED,true);
        CheckUtil.check(cnt > 0, MsgEnum.ERR_OBJECT_INVALID,"验证码");//无效的验证码
        return true;
    }
    
    /**
     * 更换手机号码验证（已开户）
     */
    @Override
	public boolean checkForMobileModifyOpened(String newMobile, String smsCode, String srvAuthCode) {
        CheckUtil.check(!StringUtils.isBlank(newMobile), MsgEnum.ERR_PARAM_NUM);
        CheckUtil.check(!StringUtils.isBlank(smsCode), MsgEnum.ERR_PARAM_NUM);
        CheckUtil.check(!StringUtils.isBlank(srvAuthCode), MsgEnum.ERR_PARAM_NUM);
        return true;
    }


    /**
     * 用户手机号修改信息查询
     * @param userId
     * @return
     */
    @Override
    public MobileModifyResultBean queryForMobileModify(Integer userId) {
        MobileModifyResultBean result = new MobileModifyResultBean();
        UserVO user = amUserClient.findUserById(userId);
        if(user != null && StringUtils.isNotBlank(user.getMobile())) {
            String hideMobile = user.getMobile().substring(0,user.getMobile().length()-(user.getMobile().substring(3)).length())+"****"+user.getMobile().substring(7);
            result.setMobile(user.getMobile());
            result.setHideMobile(hideMobile);
        }

        return result;
    }
    
    @Override
	public BankCallBean callMobileModify(Integer userId, String newMobile, String smsCode, String srvAuthCode) {
    	BankOpenAccountVO bankAccount = amUserClient.selectById(userId);
    	// 调用电子账号手机号修改增强
		BankCallBean bean = new BankCallBean();
		bean.setTxCode(BankCallConstant.TXCODE_MOBILE_MODIFY_PLUS);
		bean.setTxDate(GetOrderIdUtils.getTxDate());
		bean.setTxTime(GetOrderIdUtils.getTxTime());
		bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
		bean.setChannel(BankCallConstant.CHANNEL_PC);
		bean.setAccountId(bankAccount.getAccount());// 电子账号
		bean.setOption(BankCallConstant.OPTION_1);// 修改
		bean.setMobile(newMobile);// 新手机号
		bean.setLastSrvAuthCode(srvAuthCode);// 业务授权码
		bean.setSmsCode(smsCode);// 短信验证码
		// 商户私有域，存放开户平台,用户userId
		LogAcqResBean acqRes = new LogAcqResBean();
		acqRes.setUserId(userId);
		bean.setLogAcqResBean(acqRes);
		// 操作者ID
		bean.setLogUserId(String.valueOf(userId));
		bean.setLogOrderId(GetOrderIdUtils.getUsrId(userId));
		// 返回参数
		BankCallBean retBean = null;
		try {
			// 调用接口
			retBean = BankCallUtils.callApiBg(bean);
		} catch (Exception e) {
			logger.error("请求手机号码修改接口失败", e);
			return null;
		}
    
    	return retBean;
    }

    @Override
    public void updateUserCAMQ(int userId) throws ParseException, MQException {
        // add by liuyang 20180209 开户成功后,将用户ID加入到CA认证消息队列 start
        // 加入到消息队列

        String startTime = GetDate.dateToString(new Date());
        // 循环去做CA认证

        FddCertificateAuthorityVO fddCertificateAuthorityVO = new FddCertificateAuthorityVO();
        fddCertificateAuthorityVO.setUserId(userId);
        fddCertificateAuthorityVO.setCertFrom("mobileModify");
        fddProducer.messageSend(new MessageContent(MQConstant.FDD_CERTIFICATE_AUTHORITY_TOPIC,
                UUID.randomUUID().toString(), JSON.toJSONBytes(fddCertificateAuthorityVO)));

        // 处理结束时间
        String endTime = GetDate.dateToString(new Date());
        // 处理用时
        String consumeTime = GetDate.countTime(GetDate.stringToDate(startTime), GetDate.stringToDate(endTime));
        logger.info("处理用时:" + startTime + "减去" + endTime + "等于" + consumeTime);
    }


}
