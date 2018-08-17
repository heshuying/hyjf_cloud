/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.SmsNoticeConfigMapper;
import com.hyjf.am.config.dao.model.auto.SmsNoticeConfig;
import com.hyjf.am.config.dao.model.auto.SmsNoticeConfigExample;
import com.hyjf.am.config.service.SmsNoticeConfigService;
import com.hyjf.am.resquest.config.SmsNoticeConfigRequest;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author fuqiang
 * @version SmsNoticeConfigServiceImpl, v0.1 2018/5/8 10:08
 */
@Service
public class SmsNoticeConfigServiceImpl implements SmsNoticeConfigService {
	@Autowired
	private SmsNoticeConfigMapper smsNoticeConfigMapper;

	@Override
	public SmsNoticeConfig findSmsNoticeByCode(String tplCode) {
		SmsNoticeConfig smsNoticeConfig = RedisUtils.getObj(RedisConstants.SMS_NOTICE_CONFIG, SmsNoticeConfig.class);
		if (smsNoticeConfig == null) {
			SmsNoticeConfigExample example = new SmsNoticeConfigExample();
			SmsNoticeConfigExample.Criteria criteria = example.createCriteria();
			criteria.andNameEqualTo(tplCode);
			List<SmsNoticeConfig> smsNoticeConfigList = smsNoticeConfigMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(smsNoticeConfigList)) {
				smsNoticeConfig = smsNoticeConfigList.get(0);
				RedisUtils.setObjEx(RedisConstants.SMS_NOTICE_CONFIG, smsNoticeConfig, 24 * 60 * 60);
				return smsNoticeConfig;
			}
		}
		return smsNoticeConfig;
	}
	/**
	 * 查询通知配置列表
	 * @author xiehuili
	 * @return
	 */
	@Override
	public List<SmsNoticeConfig> findSmsNoticeList(){
		return smsNoticeConfigMapper.selectByExample(new SmsNoticeConfigExample());
	}
	/**
	 * 查询通知配置详情
	 * @author xiehuili
	 * @return
	 */
	@Override
	public SmsNoticeConfig  smsNoticeConfigInfo(Integer id,String name){
		SmsNoticeConfig ky = new SmsNoticeConfig();
		ky.setId(id);
		ky.setName(name);
		return smsNoticeConfigMapper.selectByPrimaryKey(ky);
	}
	/**
	 * 添加通知配置
	 * @author xiehuili
	 * @return
	 */
	@Override
	public int insertSmsNoticeConfig(SmsNoticeConfigRequest request){
		SmsNoticeConfig ky = new SmsNoticeConfig();
		BeanUtils.copyProperties(request,ky);
		return smsNoticeConfigMapper.insertSelective(ky);
	}
	/**
	 * 修改通知配置
	 * @author xiehuili
	 * @return
	 */
	@Override
	public int updateSmsNoticeConfig(SmsNoticeConfigRequest request){
		SmsNoticeConfig ky = new SmsNoticeConfig();
		BeanUtils.copyProperties(request,ky);
		return smsNoticeConfigMapper.updateByPrimaryKeySelective(ky);
	}
	/**
	 * 校验通知配置
	 * @author xiehuili
	 * @return
	 */
	@Override
	public Integer onlyName(String name){
		SmsNoticeConfigExample exam = new SmsNoticeConfigExample();
		exam.createCriteria().andNameEqualTo(name);
		return smsNoticeConfigMapper.countByExample(exam);
	}
}
