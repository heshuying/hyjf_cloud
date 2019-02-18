package com.hyjf.cs.user.constants;

public enum AemsAuthEnum {
    T_("1","1"),
    Z_("2","4"),
    J_("3","5"),
    H_("4","6"),
    T_Z_("1,2","11"),
    T_J_("1,3","12"),
    T_H_("1,4","16"),
    Z_J_("2,3","13"),
    Z_H_("2,4","17"),
    J_H_("3,4","15"),
    T_Z_J_("1,2,3","14"),
    T_Z_H_("1,2,4","18"),
    T_J_H_("1,3,4","19"),
    Z_J_H_("2,3,4","20"),
    T_Z_J_H_("1,2,3,4","21"),
    ;

    // 授权数字 [1：自动投标授权 2：自动债转授权 3：缴费授权 4：还款授权]
    private String authNum;
    // 授权类型
    private String authTypeNum;

    AemsAuthEnum(String authNum, String authTypeNum) {
        this.authNum = authNum;
        this.authTypeNum = authTypeNum;
    }

    public static String getAuthTypeNum(String authNum){
        for (AemsAuthEnum e : AemsAuthEnum.values()){
            if(authNum.equals(e.authNum)){
                return e.authTypeNum;
            }
        }
        return "0";
    }

    public String getAuthNum() {
        return authNum;
    }

    public void setAuthNum(String authNum) {
        this.authNum = authNum;
    }

    public String getAuthTypeNum() {
        return authTypeNum;
    }

    public void setAuthTypeNum(String authTypeNum) {
        this.authTypeNum = authTypeNum;
    }
}
