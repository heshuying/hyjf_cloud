package com.hyjf.cs.market.contants;

/**
 * @author xiasq
 * @version ArticleTypeEnum, v0.1 2018/12/29 9:49
 */
public enum ArticleTypeEnum {

    WEB_NOTICE(2, "网站公告"),
    NET_LOAN_TIPS(3, "网贷知识"),
    ABOUT_UP(5, "关于我们"),
    CONTACT_US(8, "联系我们"),
    COMPANY_NEWS(20, "公司动态"),
    RISK_TIPS(101, "风险教育")
    ;


    private int type;
    private String title;

    ArticleTypeEnum(int type, String title){
        this.title = title;
        this.type = type;
    }


	public static String getTitle(Integer type) {
		if (type != null) {
			for (ArticleTypeEnum index : ArticleTypeEnum.values()) {
				if (type == index.getType()) {
					return index.getTitle();
				}
			}
		}
		return "";
	}


    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }
}
