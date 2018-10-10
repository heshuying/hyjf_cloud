package com.hyjf.common.enums;

/**
 * 文章分类
 * @author：yinhui
 * @Date: 2018/10/8  16:11
 */
public enum ContentArticleEnum {
    //2-网站公告，3-网贷知识，5-关于我们，101-风险教育，8-联系我们，20-公司动态
    WZGG("2","网站公告"),
    WDZS("3","网贷知识"),
    GYWM("5","关于我们"),
    FXJY("101","风险教育"),
    LXWM("8","联系我们"),
    GSDT("20","公司动态");

    private String type;//
    private String name;//

    ContentArticleEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getName(String type){

        for (ContentArticleEnum e:ContentArticleEnum.values()){

            if(type.equals(e.type)){
                return e.name;
            }
        }
        return null;
    }
}
