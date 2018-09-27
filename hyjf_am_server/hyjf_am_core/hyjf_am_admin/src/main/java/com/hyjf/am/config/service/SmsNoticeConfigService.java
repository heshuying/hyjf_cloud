/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.SmsNoticeConfig;
import com.hyjf.am.resquest.config.SmsNoticeConfigRequest;

import java.util.List;

/**
 * @author fuqiang
 * @version SmsNoticeConfigService, v0.1 2018/5/8 9:57
 */
public interface SmsNoticeConfigService {
    /**
     * 根据tplCode查询短信通知配置
     *
     * @param tplCode
     * @return
     */
    SmsNoticeConfig findSmsNoticeByCode(String tplCode);
    /**
     * 查询通知配置列表
     * @author xiehuili
     * @return
     */
    public List<SmsNoticeConfig> findSmsNoticeList();
    /**
     * 查询通知配置详情
     * @author xiehuili
     * @return
     */
    SmsNoticeConfig  smsNoticeConfigInfo(Integer id,String name);
    /**
     * 添加通知配置
     * @author xiehuili
     * @return
     */
    int insertSmsNoticeConfig(SmsNoticeConfigRequest request);
    /**
     * 修改通知配置
     * @author xiehuili
     * @return
     */
    int updateSmsNoticeConfig(SmsNoticeConfigRequest request);
    /**
     * 校验通知配置
     * @author xiehuili
     * @return
     */
    Integer onlyName(String name);

    /**
     * 根据name查询开启的通知配置
     * @param name
     * @return
     */
    SmsNoticeConfig findNoticeByName(String name);
}
