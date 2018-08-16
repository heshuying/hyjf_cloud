/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.SmsCodeRequestBean;
import com.hyjf.admin.beans.request.SmsLogRequestBean;
import com.hyjf.am.vo.admin.SmsCodeCustomizeVO;

import java.text.ParseException;
import java.util.List;

/**
 * @author fq
 * @version SmsCodeService, v0.1 2018/8/15 10:22
 */
public interface SmsCodeService {
    /**
     * 在筛选条件下查询出用户
     * @param requestBean
     * @return
     */
    List<SmsCodeCustomizeVO> queryUser(SmsCodeRequestBean requestBean);

    /**
     * 条件查询短信记录列表
     * @param smlogCustomize
     * @return
     */
    Integer queryLogCount(SmsLogRequestBean requestBean);

    /**
     * 根据手机号判断用户是否存在
     * @param mobile
     * @return
     */
    boolean getUserByMobile(String mobile);

    /**
     * 插入短信定时任务表
     * @param form
     * @return
     */
    boolean sendSmsOntime(SmsCodeRequestBean form) throws ParseException;
}
