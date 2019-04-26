package com.hyjf.wbs.qvo;

/**
 * @author cui
 * @version RedirectTypeEnum, v0.1 2019/4/24 10:34
 */
public enum RedirectTypeEnum {
    INDEX_TYPE("index","/","/"),BORROW_TYPE("borrow","/borrow/borrowDetail?borrowNid=%s","/borrow/%s"),PANDECT_TYPE("pandect","/user/pandect","/mine");

    private String type;

    private String webUrl;

    private String wechatUrl;

    RedirectTypeEnum(String type, String webUrl,String wechatUrl) {
        this.type = type;
        this.webUrl = webUrl;
        this.wechatUrl=wechatUrl;
    }

    public static RedirectTypeEnum findType(String type){
        for(RedirectTypeEnum s :RedirectTypeEnum.values()){
            if(s.getType().equals(type)){
                return s;
            }
        }
        throw new IllegalArgumentException("未找到type=【"+type+"】的RedirectType枚举类型！");
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getWechatUrl() {
        return wechatUrl;
    }

    public void setWechatUrl(String wechatUrl) {
        this.wechatUrl = wechatUrl;
    }
}
