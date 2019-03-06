/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.SiteSettingMapper;
import com.hyjf.am.config.dao.model.auto.SiteSetting;
import com.hyjf.am.config.dao.model.auto.SiteSettingExample;
import com.hyjf.am.config.service.SiteSettingService;
import com.hyjf.am.resquest.admin.SitesettingRequest;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;

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
		SiteSetting siteSetting = null;
		if (siteSetting == null) {
			SiteSettingExample example = new SiteSettingExample();
			List<SiteSetting> SiteSettingList = siteSettingMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(SiteSettingList)) {
				siteSetting = SiteSettingList.get(0);
				return siteSetting;
			}
		}
		return siteSetting;
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
            Thread.sleep(num*1000L);
           // updateTest2();
	    
	    
	}

	@Override
	public void update(SitesettingRequest request) {
		SiteSetting siteSetting = new SiteSetting();
		BeanUtils.copyProperties(request, siteSetting);
		siteSettingMapper.updateByPrimaryKey(siteSetting);
	}

	/**
	 * 通过网站设置获取公司信息 add by liushouyi
	 *
	 * @return
	 */
	@Override
	public SiteSetting selectSiteSetting() {
		SiteSettingExample example = new SiteSettingExample();
		List<SiteSetting> siteSettingList = this.siteSettingMapper.selectByExample(example);
		if(null != siteSettingList && siteSettingList.size()>0){
			return siteSettingList.get(0);
		}
		return null;
	}

	/*public void updateTest2() {
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
                 logger.error(e.getMessage());
            }

		*//*if (1 == 1) {
//                throw new RuntimeException("测试异常", 12);
			throw new RuntimeException("测试异常");
		}*//*
    }*/
}
