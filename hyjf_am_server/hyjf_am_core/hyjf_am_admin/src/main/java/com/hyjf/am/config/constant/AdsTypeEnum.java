package com.hyjf.am.config.constant;

/**
 * @author lisheng
 * @version AdsTypeEnum, v0.1 2018/9/17 17:29
 */

public enum  AdsTypeEnum {
    startpage("startpage","启动页banner"),
    popup("popup","首页弹窗广告"),
    // android 数据库配置数据
    android_banner("android_banner","android首页banner"),
    android_regist_888("android_regist_888","安卓注册红包"),
    android_open_888("android_open_888","安卓开户红包"),
    android_module1("android_module1","安卓资金存管"),
    android_module2("android_module2","安卓美国上市"),
    android_module3("android_module3","安卓运营数据"),
    android_module4("android_module4","信息披露"),
    android_find_module1("android_find_module1","品牌优势"),
    android_find_module2("android_find_module2","新手指引"),
    android_find_module3("android_find_module3","风险教育"),
    android_find_module4("android_find_module4","关于我们"),
    android_find_banner("android_find_banner","发现页广告banner"),
    //ios 数据库配置数据
    ios_banner("ios_banner","iOS首页banner"),
    ios_regist_888("ios_regist_888","苹果注册红包"),
    ios_open_888("ios_open_888","苹果开户红包"),
    ios_module1("ios_module1","苹果资金存管"),
    ios_module2("ios_module2","苹果美国上市"),
    ios_module3("ios_module3","苹果运营数据"),
    ios_module4("ios_module4","信息披露"),
    ios_find_module1("ios_find_module1","品牌优势"),
    ios_find_module2("ios_find_module2","新手指引"),
    ios_find_module3("ios_find_module3","风险教育"),
    ios_find_module4("ios_find_module4","关于我们"),
    ios_find_banner("ios_find_banner","发现页广告banner"),
    //微信
    weixin("weixin","微信banner"),
    wechat_module1("wechat_module1","微信安全保障"),
    wechat_module2("wechat_module2","微信运营数据"),
    wechat_module3("wechat_module3","微信关于我们"),
    wechat_module4("wechat_module4","新版微信信息披露"),
    wechat_regist_888("wechat_regist_888","微信注册888红包"),
    wechat_banner("wechat_banner","微信改版banner");

    private String index;
    private String name;
    AdsTypeEnum(String index, String name){
        this.name = name;
        this.index = index;
    }
    public static String getName(String index) {
        for (AdsTypeEnum c : AdsTypeEnum.values()) {
            if (c.getIndex().equals(index)) {
                return c.name;
            }
        }
        return null;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIndex() {
        return index;
    }
    public void setIndex(String index) {
        this.index = index;
    }

}
