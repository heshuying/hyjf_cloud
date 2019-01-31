package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.transact;

public enum CertTradeTypeEnum {
	//放款（针对借款人）
	CERTTRADETYPE1("放款（针对借款人）", "1"),
	//投资（投资人）
	CERTTRADETYPE2("投资（投资人）", "2"),
	//垫付风险准备金
	CERTTRADETYPE3("垫付风险准备金", "3"),
	//平台服务费
	CERTTRADETYPE4("平台服务费", "4"),
	//交易手续费
	CERTTRADETYPE5("交易手续费", "5"),
	//充值
	CERTTRADETYPE6("充值", "6"),
	//提现
	CERTTRADETYPE7("提现", "7"),
	//赎回本金
	CERTTRADETYPE8("赎回本金", "8"),
	//赎回利息
	CERTTRADETYPE9("赎回利息", "9"),
	//红包
	CERTTRADETYPE10("红包", "10"),
	//全部转让
	CERTTRADETYPE11("全部转让", "11"),
	//部分转让
	CERTTRADETYPE26("部分转让", "26"),
	//代付
	CERTTRADETYPE12("代付", "12"),
	//回风险准备金
	CERTTRADETYPE13("回风险准备金", "13"),
	//承接
	CERTTRADETYPE17("承接", "17"),
	//还款本金
	CERTTRADETYPE18("还款本金 ", "18"),
	//还款利息
	CERTTRADETYPE19("还款利息", "19"),
	//提现手续费
	CERTTRADETYPE23("提现手续费", "23"),
	//利息管理费
	CERTTRADETYPE24("利息管理费", "24"),
	//VIP费（会员费）
	CERTTRADETYPE25("VIP费（会员费）", "25"),
	//代偿金（第三方担保机构/保险公司支付）
	CERTTRADETYPE27("代偿金（第三方担保机构/保险公司支付）", "27"),
	//风险缓释金（机构从盈利中拿出的资金）
	CERTTRADETYPE28("风险缓释金（机构从盈利中拿出的资金）", "28"),
	//第三方推荐费
	CERTTRADETYPE29("第三方推荐费", "29"),
	//代偿金（第三方担保机构/保险公司收取）
	CERTTRADETYPE30("代偿金（第三方担保机构/保险公司收取）", "30"),
	//利差调整（出借人债权转让后的金额不足或超出产品的参考回报时，第三方担保机构支付或者收回出借人的差额）
	CERTTRADETYPE31("利差调整（出借人债权转让后的金额不足或超出产品的参考回报时，第三方担保机构支付或者收回出借人的差额）", "31"),
	//催收服务费(逾期后收取的服务费)
	CERTTRADETYPE32("催收服务费(逾期后收取的服务费)", "32"),
	//罚息
	CERTTRADETYPE33("罚息", "33"),
	//转让手续费
	CERTTRADETYPE40("转让手续费", "40"),
	//加息券利息数据
	CERTTRADETYPE41("加息券利息数据", "41"),
	//借款手续费
	CERTTRADETYPE42("借款手续费", "42");
	
	

    // 成员变量  
    private String name;  
    private String index;  
    // 构造方法  
    private CertTradeTypeEnum(String name, String index) {  
        this.name = name;  
        this.index = index;  
    }  
    // 普通方法  
    public static String getName(String index) {  
        for (CertTradeTypeEnum c : CertTradeTypeEnum.values()) {  
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