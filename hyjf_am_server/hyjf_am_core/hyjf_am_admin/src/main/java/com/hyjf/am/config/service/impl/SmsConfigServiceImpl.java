package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.SmsConfigMapper;
import com.hyjf.am.config.dao.model.auto.SmsConfig;
import com.hyjf.am.config.dao.model.auto.SmsConfigExample;
import com.hyjf.am.config.service.SmsConfigService;
import com.hyjf.am.resquest.admin.SmsConfigRequest;
import com.hyjf.am.vo.config.SmsConfigVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiasq
 * @version SmsConfigServiceImpl, v0.1 2018/4/24 18:18
 */
@Service
public class SmsConfigServiceImpl implements SmsConfigService {
	@Autowired
	private SmsConfigMapper smsConfigMapper;

	@Override
	public SmsConfig findOne() {
		SmsConfig smsConfig = RedisUtils.getObj(RedisConstants.SMS_CONFIG, SmsConfig.class);
		if (smsConfig == null) {
			List<SmsConfig> list = smsConfigMapper.selectByExample(new SmsConfigExample());
			if (!CollectionUtils.isEmpty(list)) {
				smsConfig = list.get(0);
				RedisUtils.setObjEx(RedisConstants.SMS_CONFIG, smsConfig, 24 * 60 * 60);
				return smsConfig;
			}
		}
		return smsConfig;
	}

	/**
	 * 查询数据
	 * @param request
	 * @author xiehuili
	 * @return
	 */
	@Override
	public SmsConfigVO initSmsConfig(SmsConfigRequest request){
		SmsConfigVO smsConfigVOS = new SmsConfigVO();
		SmsConfigExample example = new SmsConfigExample();
		List<SmsConfig> smscon = smsConfigMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(smscon)){
			smsConfigVOS = CommonUtils.convertBean(smscon.get(0),SmsConfigVO.class);
			return smsConfigVOS;
		}
		return null;
	}
	/**
	 * 添加短信加固数据
	 * @param request
	 * @author xiehuili
	 * @return
	 */
	@Override
	public int insertSmsConfig(SmsConfigRequest request){
		SmsConfig config = new SmsConfig();
		BeanUtils.copyProperties(request,config);
		return smsConfigMapper.insertSelective(config);
	}
	/**
	 * 修改短信加固数据
	 * @param request
	 * @author xiehuili
	 * @return
	 */
	@Override
	public int updateSmsConfig(SmsConfigRequest request){
		SmsConfig config = new SmsConfig();
		BeanUtils.copyProperties(request,config);
		return smsConfigMapper.updateByPrimaryKeySelective(config);
	}
}
