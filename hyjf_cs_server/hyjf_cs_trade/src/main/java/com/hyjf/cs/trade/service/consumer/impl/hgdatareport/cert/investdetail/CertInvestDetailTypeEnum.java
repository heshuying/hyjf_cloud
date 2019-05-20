package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.investdetail;

public enum CertInvestDetailTypeEnum {
	//投资（投资人）
	CERTTRADETYPE2("成功出借金额（包含红包）", "2"),
	//充值
	CERTTRADETYPE6("充值", "6"),
	//提现
	CERTTRADETYPE7("提现", "7"),
	//赎回本金
	CERTTRADETYPE8("收回本金", "8"),
	//赎回利息
	CERTTRADETYPE9("收回利息", "9"),
	//红包
	CERTTRADETYPE10("红包", "10");


    // 成员变量
    private String name;
    private String index;
    // 构造方法
    private CertInvestDetailTypeEnum(String name, String index) {
        this.name = name;  
        this.index = index;  
    }  
    // 普通方法  
    public static String getName(String index) {  
        for (CertInvestDetailTypeEnum c : CertInvestDetailTypeEnum.values()) {
            if (c.getIndex().equals(index)) {  
                return c.name;  
            }  
        }  
        return null;  
    }  
    // get set 方法  
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