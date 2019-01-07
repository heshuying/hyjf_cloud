package com.hyjf.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 文章分类
 * @author：yinhui
 * @Date: 2018/10/8  16:11
 */
public enum ContentArticleEnum {
    //2-网站公告，3-网贷知识，5-关于我们，101-风险教育，8-联系我们，20-公司动态
    WEB_NOTICE("2","网站公告"),
    NET_LOAN_TIPS("3","网贷知识"),
    ABOUT_UP("5","关于我们"),
    CONTACT_US("8","联系我们"),
    COMPANY_NEWS("20","公司动态"),
    RISK_TIPS("101","风险教育");

    private String type;
    private String name;

    ContentArticleEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

	public static String getName(String type) {
		if (StringUtils.isNotBlank(type)) {
			for (ContentArticleEnum e : ContentArticleEnum.values()) {

				if (type.equals(e.type)) {
					return e.name;
				}
			}
		}
		return "";
	}
}
