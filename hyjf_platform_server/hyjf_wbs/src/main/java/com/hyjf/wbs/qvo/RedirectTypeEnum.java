package com.hyjf.wbs.qvo;

/**
 * @author cui
 * @version RedirectTypeEnum, v0.1 2019/4/24 10:34
 */
public enum RedirectTypeEnum {
    INDEX_TYPE("index","/"),BORROW_TYPE("borrow","/borrow/borrowDetail?borrowNid=%s"),PANDECT_TYPE("pandect","/user/pandect");

    private String type;

    private String url;

    RedirectTypeEnum(String type, String url) {
        this.type = type;
        this.url = url;
    }

    RedirectTypeEnum findType(String type){
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
