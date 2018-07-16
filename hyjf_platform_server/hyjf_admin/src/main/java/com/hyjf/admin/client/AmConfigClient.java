package com.hyjf.admin.client;

import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.config.SiteSettingsVO;
import com.hyjf.am.vo.config.SmsMailTemplateVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;

import java.util.List;

/**
 * @author zhangqingqing
 * @version AmConfigClient, v0.1 2018/4/23 20:00
 */
public interface AmConfigClient {
    /**
     * 用loginUserId去am-config查询登录的用户信息
     * @auth sunpeikai
     * @param loginUserId 登录用户id
     * @return
     */
    AdminSystemVO getUserInfoById(Integer loginUserId);

    /**
     * 获取数据字典表的下拉列表
     * @param nameClass
     * @return
     */
    List<ParamNameVO> getParamNameList(String nameClass);

    /**
     * 查询邮件配置
     * @auth sunpeikai
     * @param
     * @return
     */
    SiteSettingsVO findSiteSetting();

    /**
     * 查询邮件模板
     * @auth sunpeikai
     * @param
     * @return
     */
    SmsMailTemplateVO findSmsMailTemplateByCode(String mailCode);

    /**
     * 根据银行错误码查询错误信息
     * @auth sunpeikai
     * @param retCode 错误码
     * @return
     */
    String getBankRetMsg(String retCode);

    /**
     * 获取银行返回码
     *
     * @param retCode
     * @return
     */
    BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode);

}
