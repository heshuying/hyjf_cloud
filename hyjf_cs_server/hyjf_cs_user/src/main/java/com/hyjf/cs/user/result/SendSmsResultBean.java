package com.hyjf.cs.user.result;

/**
 * @author fuqiang
 */
public class SendSmsResultBean extends BaseResultBeanFrontEnd {
    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = -7987569517727446968L;
    private String srvAuthCode;

    public String getSrvAuthCode() {
        return srvAuthCode;
    }

    public void setSrvAuthCode(String srvAuthCode) {
        this.srvAuthCode = srvAuthCode;
    }

    
}

