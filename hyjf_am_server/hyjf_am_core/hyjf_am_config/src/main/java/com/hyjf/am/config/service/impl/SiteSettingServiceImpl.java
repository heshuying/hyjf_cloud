/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.config.dao.mapper.auto.SiteSettingMapper;
import com.hyjf.am.config.dao.model.auto.SiteSetting;
import com.hyjf.am.config.dao.model.auto.SiteSettingExample;
import com.hyjf.am.config.service.SiteSettingService;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;

/**
 * @author fuqiang
 * @version SiteSettingServiceImpl, v0.1 2018/5/7 16:47
 */
@Service
public class SiteSettingServiceImpl implements SiteSettingService {
	@Autowired
	private SiteSettingMapper siteSettingMapper;

	@Override
	public SiteSetting findOne() {
		SiteSetting SiteSetting = null;
		if (SiteSetting == null) {
			SiteSettingExample example = new SiteSettingExample();
			List<SiteSetting> SiteSettingList = siteSettingMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(SiteSettingList)) {
				SiteSetting = SiteSettingList.get(0);
				RedisUtils.setObjEx(RedisKey.SITE_SETTINGS, SiteSetting, 24 * 60 * 60);
				return SiteSetting;
			}
		}
		return SiteSetting;
	}
	
	
	/**
	 * 用来测试事务的方法。
	 */
	@Override
	public void updateTest1() throws Exception {
	    SiteSettingExample example = new SiteSettingExample();
	    
//	    example.createCriteria();
//	    example.createCriteria().andIdEqualTo(2);
	    
	    SiteSettingExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(2);
	    
	    
	    SiteSetting setting = new SiteSetting();
	    setting.setSiteDomain("CXXXXX");
        setting.setCompany("XXXX");
	    int res = siteSettingMapper.updateByExampleSelective(setting, example);
	    System.out.println("更新结果："+res);
	

            Random r = new Random();
            int num = r.nextInt(10);
            
            Thread.sleep(num*1000);
            
            updateTest2();
	    
	    
	}
	
    public void updateTest2() {
        SiteSettingExample example = new SiteSettingExample();
        
//      example.createCriteria();
//      example.createCriteria().andIdEqualTo(2);
        
        SiteSettingExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(2);
        
        
        SiteSetting setting = new SiteSetting();
        setting.setSiteDomain("XXXXX");
        setting.setSiteName("再次测试事务。。");
        int res = siteSettingMapper.updateByExampleSelective(setting, example);
        System.out.println("更新结果："+res);
        
    
            Random r = new Random();
            int num = r.nextInt(10);
            
            try {
                Thread.sleep(num*1000);
            } catch (InterruptedException e) {
                 e.printStackTrace();
            }
            
            if(1==1) {
//                throw new RuntimeException("测试异常", 12);
                throw new RuntimeException("测试异常");
            }
        
        
    }
}
