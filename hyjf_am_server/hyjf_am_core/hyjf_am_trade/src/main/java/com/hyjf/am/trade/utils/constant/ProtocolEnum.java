/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.utils.constant;

/**
 * 平台协议模板枚举
 * @author yinhui
 * @version ProtocolEnum, v0.1 2018/6/6 16:48
 */
public enum ProtocolEnum {
    ZCXY("《注册协议》","zcxy"),
    PTYSTK("《平台隐私条款》","ptystk"),
    TZZXYGLFWXY("《投资咨询与管理服务协议》","tzzxyglfwxy"),
    KHXY("《开户协议》","khxy"),
    YHSQXY("《用户授权协议》","yhsqxy"),
    JJFWJKXY("《居间服务借款协议》","jjfwjkxy"),
    TZFXQRS("《投资风险确认书》","tzfxqrs"),
    TZFWXY("《投资服务协议》","tzfwxy"),
    ZQZRXY("《债权转让协议》","zqzrxy"),
    FXQRS("《风险确认书》","fxqrs");

    private String displayName;//前台界面显示名称
    private String alias;//别名

    ProtocolEnum(String displayName, String alias) {
        this.displayName = displayName;
        this.alias = alias;
    }

    public static String getAlias(String displayName){

        for (ProtocolEnum e:ProtocolEnum.values()){

            if(displayName.equals(e.displayName)){
                return e.alias;
            }
        }
        return null;
    }

    public static String getDisplayName(String alias){

        for (ProtocolEnum e:ProtocolEnum.values()){

            if(alias.equals(e.alias)){
                return e.displayName;
            }
        }
        return null;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
