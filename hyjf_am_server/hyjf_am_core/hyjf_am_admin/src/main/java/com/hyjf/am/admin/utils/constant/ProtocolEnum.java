/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.admin.utils.constant;

/**
 * 平台协议模板枚举
 * @author yinhui
 * @version ProtocolEnum, v0.1 2018/6/6 16:48
 */
public enum ProtocolEnum {
    ZCXY("《注册协议》","zcxy"),
    PTYSTK("《平台隐私条款》","ptystk"),
    TZZXYGLFWXY("《出借咨询与管理服务协议》","tzzxyglfwxy"),
    YHSQXY("《用户授权协议》","yhsqxy"),
    JJFWJKXY("《居间服务借款协议》","jjfwjkxy"),
    TZFXQRS("《散标风险揭示书》","tzfxqrs"),
    ZQZRXY("《债权转让协议》","zqzrxy"),
    KHXY("《开户协议》","khxy"),
    TZFWXY("《智投服务协议》","tzfwxy"),
    ZTFXJSS("《智投风险揭示书》","ztfxjss"),
    ZDTBSQ("《自动投标授权》","zdtbsq"),
    ZDZZSQ("《自动债转授权》","zdzzsq"),
    JFSQ("《缴费授权》","jfsq"),
    HKSQ("《还款授权》","hksq")
    ;

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
