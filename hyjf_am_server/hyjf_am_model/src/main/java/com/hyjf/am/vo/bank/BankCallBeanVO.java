package com.hyjf.am.vo.bank;

import java.io.Serializable;

import com.hyjf.am.vo.BaseVO;

/**
 * 接收银行数据的bean
 * @author jun 20180619
 *
 */
public class BankCallBeanVO extends BaseVO implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 8472170072969081116L;
	/** 操作用户userId */
	public String logUserId;
	/** 操作用户的userName */
	public String logUserName;
	/** 操作类型 */
	public String logType;
	/** 操作用户ip */
	public String logIp;
	/** 操作时间 */
	public String logTime;
	/** 备注 */
	public String logRemark;
	/** 订单id */
	public String logOrderId;
	/** 订单日期 */
	public String logOrderDate;
	/** 银行请求详细url */
	public String logBankDetailUrl;
	/** 中间表uuid */
	public String logUuid;
	/** 用户操作平台 */
	public int logClient;
	/** 验证状态 */
	public boolean logVerifyFlag;
	/** 验证结果返回 */
	public String logVerifyResult;
	/** 订单状态 */
	public String logOrderStatus;
	/** 回调url类型 */
	public String logNotifyType;

	// 公共参数
	/** 版本号 */
	public String version;
	/** 交易代码 */
	public String txCode;
	/** 机构代码 */
	public String instCode;
	/** 银行代码 */
	public String bankCode;
	/** 交易日期 */
	public String txDate;
	/** 交易时间 */
	public String txTime;
	/** 交易流水号 */
	public String seqNo;
	/** 交易金额 */
	public String txAmount;
	/** 手续费 */
	public String txFee;
	/** 浜ゆ槗娓犻亾 */
	public String channel;
	/** 鐢靛瓙璐﹀彿 */
	public String accountId;
	/** 璁㈠崟锟�? */
	public String orderId;
	/** 鍓嶅彴璺宠浆閾炬帴 */
	public String retUrl;
	/** 鍚庡彴閫氱煡杩炴帴 */
	public String notifyUrl;
	/** 瀹㈡埛IP */
	public String userIP;
	/** 璇锋眰鏂逛繚锟�? */
	public String acqRes;
	/** 楠岃瘉锟�? */
	public String sign;
	/** 鍝嶅簲浠ｇ爜 */
	public String retCode;
	/** 鍝嶅簲鎻忚堪 */
	public String retMsg;
	public String restMsg;
    // 鏂规硶鍐呴儴鍙傛暟
	/** 璇佷欢绫诲瀷 */
	public String idType;
	/** 璇佷欢鍙风爜 */
	public String idNo;
	/** 濮撳悕 */
	public String name;
	/** 鎵嬫満锟�? */
	public String mobile;
	/** 閾惰鍗″彿 */
	public String cardNo;
	/** 閭 */
	public String email;
	/** 璐︽埛鐢拷?? */
	public String acctUse;
	/** 浜ゆ槗甯佺 */
	public String currency;
	/** srvTxCode */
	public String srvTxCode;
	/** 鍓嶅涓氬姟鎺堟潈锟�? */
	public String lastSrvAuthCode;
	/** 楠岃瘉锟�? */
	public String smsCode;
	/** 閫夐」 */
	public String option;
	/** 璧峰鏃ユ湡 */
	public String startDate;
	/** 缁撴潫鏃ユ湡 */
	public String endDate;
	/** 浜ゆ槗绉嶇被 0-锟�?鏈変氦锟�? 1-杞叆浜ゆ槗 2-杞嚭浜ゆ槗 9-鎸囧畾浜ゆ槗绫诲瀷 */
	public String type;
	/** 浜ゆ槗绫诲瀷 */
	public String tranType;
	/** 椤垫暟 */
	public String pageNum;
	
	/** 鑷姩鎶曟爣鎬婚噾棰濅笂闄愶紙涓嶇畻宸茶繕閲戦锟�? */
	public String totAmount;
	/** 鍘熻鍗曞彿 */
	public String orgOrderId;
	/** 楠岃瘉鐮佹巿鏉冪爜 */
	public String srvAuthCode;
	/** 浜ゆ槗鍒╂伅 */
	public String intAmount;
	/** 杩樻鎵嬬画锟�?:鍚戣瀺璧勪汉鏀跺彇鐨勬墜缁垂 */
	public String txFeeOut;
	/** 鏀舵鎵嬬画锟�?:鍚戞姇璧勪汉鏀跺彇鐨勬墜缁垂 */
	public String txFeeIn;
	/** 瀵规墜鐢靛瓙璐﹀彿:鎶曡祫浜鸿处锟�? */
	public String forAccountId;
	/** 鏍囩殑锟�? */
	public String productId;
	/** 鎺堟潈锟�?:鎶曡祫浜烘姇鏍囨垚鍔熺殑鎺堟潈锟�? */
	public String authCode;
	/** 鍐荤粨璧勯噾锟�?锟�? */
	public String frzFlag;
	/** 鏄惁浣跨敤绾㈠寘 0-涓嶄娇鐢ㄧ孩锟�?,1-浣跨敤绾㈠寘 */
	public String bonusFlag;
	/** 鎶垫墸绾㈠寘閲戦 */
	public String bonusAmount;
	/** 璺敱浠ｇ爜0-鏈閫氶亾 1-閾惰仈閫氶亾 2-浜鸿閫氶亾锟�?-鑷姩閫夋嫨 */
	public String routeCode;
	/** 缁戝畾閾惰鑱旇鍙蜂汉姘戦摱琛屽垎閰嶇殑12浣嶈仈琛屽彿 routeCode=2锛屽繀锟�? 鎴栵拷?锟絩outeCode涓虹┖锛屼絾浜ゆ槗閲戦>20涓囷紝蹇呰緭 */
	public String cardBankCnaps;
	/** 缁戝畾閾惰涓枃鍚嶇О ,缁戝畾鐨勯摱琛屽崱瀵瑰簲鐨勯摱琛屼腑鏂囧悕锟�? */
	public String cardBankNameCn;
	/** 缁戝畾閾惰鑻辨枃鍚嶇О ,缁戝畾鐨勯摱琛屽崱瀵瑰簲鐨勯摱琛岃嫳鏂囧悕绉扮缉锟�? */
	public String cardBankNameEn;
	/** 缁戝畾閾惰鍗″紑鎴风渷锟�? ,缁戝畾鐨勯摱琛屽崱鐨勫紑鎴风渷锟�? */
	public String cardBankProvince;
	/** 缁戝畾閾惰鍗″紑鎴峰煄锟�?,缁戝畾鐨勯摱琛屽崱鐨勫紑鎴峰煄锟�? */
	public String cardBankCity;
	/** 蹇樿瀵嗙爜璺宠浆 ,蹇樿瀵嗙爜鐨勮烦杞琔RL */
	public String forgotPwdUrl;
	/** 缁戝畾閾惰浠ｇ爜 ,缁戝畾鐨勯摱琛屽崱瀵瑰簲鐨勯摱琛屼唬锟�? */
	public String cardBankCode;
	/** 鎵规锟�? */
	public String batchNo;
	/** 浜ゆ槗绗旀暟 */
	public String txCounts;
	/** 鍚庡彴閫氱煡杩炴帴 */
	public String notifyURL;
	/** 涓氬姟缁撴灉閫氱煡 */
	public String retNotifyURL;
	/** 杞閲戦 鍗栧嚭鐨勶拷?锟芥潈閲戦 */
	public String tsfAmount;
	/** 鍘熶氦鏄撻噾锟�? 鍗栧嚭鏂规姇鏍囩殑鍘熶氦鏄撻噾棰濓紙鎴栧崠鍑烘柟璐拱鍊烘潈鐨勫師浜ゆ槗閲戦锟�? */
	public String orgTxAmount;
	/** 璇锋眰鏁扮粍 */
	public String subPacks;
	/** 鎺ユ敹缁撴灉 */
	public String received;
	/** 绛剧害鐘讹拷?? 0锛氭湭绛剧害 1锛氬凡绛剧害 */
	public String state;
	/** 鎵规浜ゆ槗鏃ユ湡 */
	public String batchTxDate;

	/** 鎬昏褰曟暟 锟�?锟�?10鏉¤锟�? */
	public String totalItems;

	/** 鎶曡祫鎵嬬画锟�? */
	public String bidFee;
	/** 铻嶈祫鎵嬬画锟�? */
	public String debtFee;

	/**
	 * 鏌ヨ绫诲埆1-鎸夋祦姘村彿鏌ヨ锛堟壒娆＄被浜ゆ槗涓嶅彲鐢級 2-鎸夎鍗曞彿鏌ヨ
	 */
	public String reqType;

	/** 鏌ヨ浜ゆ槗浠ｇ爜 */
	public String reqTxCode;
	/** 鏌ヨ浜ゆ槗鏃ユ湡 */
	public String reqTxDate;
	/** 鏌ヨ浜ゆ槗鏃堕棿 */
	public String reqTxTime;
	/** 鏌ヨ浜ゆ槗娴佹按锟�? */
	public String reqSeqNo;
	/** 鏌ヨ璁㈠崟锟�? */
	public String reqOrderId;
	/** 浜ゆ槗鐘讹拷?锟絊-鎴愬姛 F-澶辫触 N-浜ゆ槗涓嶅瓨锟�? Z-鏈煡 D-寰呭锟�? */
	public String txState;
	/** 鍙敤浣欓 */
	public String availBal;
	/** 璐﹂潰浣欓 璐﹂潰浣欓-鍙敤浣欓=鍐荤粨閲戦 */
	public String currBal;
	/** 鏍囩殑鎻忚堪 */
	public String productDesc;
	/** 鍕熼泦锟�? */
	public String raiseDate;
	/** 鍕熼泦缁撴潫鏃ユ湡 */
	public String raiseEndDate;
	/** 浠樻伅鏂瑰紡 0-鍒版湡涓庢湰閲戜竴璧峰綊锟�? 1-姣忔湀鍥哄畾鏃ユ湡鏀粯 2-姣忔湀涓嶇‘瀹氭棩鏈熸敮锟�? */
	public String intType;
	/** 鍒╂伅姣忔湀鏀粯锟�? */
	public String intPayDay;
	/** 鍊熸鏈熼檺 */
	public String duration;
	/** 骞村寲鍒╃巼 */
	public String rate;
	/** 鎷呬繚璐︽埛 */
	public String bailAccountId;
	/** 鍚嶄箟鍊熸浜虹數瀛愬笎锟�? */
	public String nominalAccountId;
	/** 鏄惁浣跨敤浜ゆ槗鎻忚堪 */
	public String desLineFlag;
	/** 浜ゆ槗鎻忚堪 */
	public String desLine;
	/** 鎴愬姛浜ゆ槗閲戦 */
	public String sucAmount;
	/** 鎴愬姛浜ゆ槗绗旀暟 */
	public String sucCounts;
	/** 澶辫触浜ゆ槗閲戦 */
	public String failAmount;
	/** 澶辫触浜ゆ槗绗旀暟 */
	public String failCounts;
	/** 鎵规澶勭悊閲戦 */
	public String relAmount;
	/** 鎵规澶勭悊绗旀暟 */
	public String relCounts;
	/** 鎵规澶勭悊鐘讹拷?? */
	public String batchState;
	/** 鎵规浜ゆ槗浠ｇ爜 */
	public String batchTxCode;
	/** 澶辫触鎻忚堪 */
	public String failMsg;
	/** 璐︽埛绫诲瀷 */
	public String accType;
	/** 鎻愮幇锟�?锟�? */
	public String withdrawFlag;
	/** 鏄惁璁剧疆杩囧瘑锟�? */
	public String pinFlag;
	/** 瀵规墜濮撳悕 */
	public String forName;
	/** 鍓╀綑鍙浆璁╅噾锟�? */
	public String availAmount;
	/** 杞锟�?锟�? */
	public String txIncome;
	/**棰勬湡鏀剁泭*/
	public String forIncome;
	/**鎶曟爣鏃ユ湡*/
	public String buyDate;
	/**锟�?鎴锋棩锟�?*/
	public String openDate;
	/**璐︽埛鐘讹拷??*/
	public String acctState;
	/**鍐荤粨涓氬姟绫诲埆*/
	public String frzState;

	/**瀵嗙爜鎸傚け鐘讹拷??*/
	public String pinLosCd;
	/** 鑱旇锟�?*/
	public String payAllianceCode;
	/** 鍘熶氦鏄撴棩锟�? */
	public String orgTxDate;
	/** 鍘熶氦鏄撴椂锟�? */
	public String orgTxTime;
	/** 鍘熶氦鏄撴祦姘村彿 */
	public String orgSeqNo;
	/** 浜ゆ槗澶勭悊缁撴灉 */
	public String result;
	/** 鍐叉鎾ら攢鏍囧織 0:姝ｅ父 1锛氬凡鍐叉/鎾ら攢*/
	public String orFlag;
	/**瀵瑰叕璐﹀彿*/
	public String caccount;
	/**璇佷欢鍙风爜*/
	public String busId;
	/**绋庡姟鐧昏锟�?*/ 
	public String taxId;
	/**鍐荤粨鐘讹拷??*/
    public String frzType;
	/** 棰勬湡骞村寲鏀剁泭锟�?*/
	public String yield;
	/** 棰勬湡鏈伅鏀剁泭*/
	public String intTotal;
	/** 瀹為檯鏀剁泭*/
	public String income;
	/** 瀹為檯鏀剁泭绗﹀彿*/
	public String incFlag;
	/** 浜ゆ槗鎻忚堪 */
	public String description;
	
	/** 鐭俊鍙戯拷?锟芥椂锟�?*/
	public String sendTime;
	/** 鐭俊搴忓彿*/
	public String smsSeq;
	/** 楠岃瘉鐮佹湁鏁堟椂锟�?*/
	public String validTime;
	/** 绛剧害璁㈠崟锟�?*/
	public String contOrderId;
	/** 鎵嬬画璐归噾锟�?*/
	public String feeAmount;
	/** 椋庨櫓鍑嗗锟�?*/
	public String riskAmount;
	/**鐢宠璁㈠崟锟�?*/
	public String lendPayOrderId;
	
	/**绛剧害鏃ユ湡*/
    public String txnDate;
    /**绛剧害鏃堕棿*/
    public String txnTime;
    /**鎬у埆 M  鐢凤拷??  F  濂筹拷??*/
    public String gender;
    /** 韬唤灞烇拷?? 1锛氬嚭鍊熻锟�?2锛氾拷?锟芥瑙掕壊3锛氫唬鍋胯锟�?*/
    public String identity;

    
    //鏄惁缁存姢鏍囧織锟�? 锟�?0锟�?1缁勬垚鐨勬爣蹇椾綅锟�?0琛ㄧず涓嶇淮鎶わ紝1琛ㄧず缁存姢锛屽叡20浣嶏紝锟�?1浣嶈〃锟�?12鍩熸槸鍚︾敓鏁堬紝锟�?2浣嶈〃锟�?13鍩熸槸鍚︾敓鏁堬紝鍏朵綑浠ユ绫绘帹锟�?
    private String bitMap;
    //锟�?閫氳嚜鍔ㄦ姇鏍囧姛鑳芥爣锟�? 0锛氬彇锟�? 1锛氬紑锟�?
    private String autoBid;
    //锟�?閫氳嚜鍔拷?锟借浆鍔熻兘鏍囧織 0锛氬彇锟�? 1锛氬紑锟�?
    private String autoTransfer;
    //锟�?閫氶绾﹀彇鐜板姛鑳芥爣锟�? 0锛氬彇锟�? 1锛氬紑锟�?
    private String agreeWithdraw;
    //锟�?閫氭棤瀵嗘秷璐瑰姛鑳芥爣锟�? 0锛氬彇锟�? 1锛氬紑锟�?
    private String directConsume;
    /**杩斿洖浜ゆ槗椤甸潰閾炬帴*/
    private String transactionUrl ;
    /**璁㈠崟鏈夋晥鎬ц繛锟�?*/
    private String verifyOrderUrl ;

    /** 澶囨敞 */
    public String remark;
    
    /**浜ゆ槗纭鏃ユ湡*/
    public String affirmDate;
    /**浜ゆ槗纭鏃堕棿*/
    public String affirmTime;
    
    /**缈婚〉鏍囪瘑 绌猴細棣栨鏌ヨ锟�?1锛氱炕椤垫煡璇紱鍏跺畠锛氶潪锟�?*/
    public String rtnInd;

    /**浜ゆ槗鏃ユ湡 缈婚〉鎺у埗浣跨敤锛涢娆℃煡璇笂閫佺┖锛涚炕椤垫煡璇㈡椂涓婏拷?锟戒笂椤佃繑鍥炵殑锟�?鍚庝竴鏉¤褰曚氦鏄撴棩鏈燂紱YYYYMMDD */
    public String inpDate;

    /**浜ゆ槗鏃堕棿 缈婚〉鎺у埗浣跨敤锛涢娆℃煡璇笂閫佺┖锛涚炕椤垫煡璇㈡椂涓婏拷?锟戒笂椤佃繑鍥炵殑锟�?鍚庝竴鏉¤褰曚氦鏄撴椂闂达紱HH24MISSTT */
    public String inpTime;
    /**鑷劧鏃ユ湡 缈婚〉鎺у埗浣跨敤锛涢娆℃煡璇笂閫佺┖锛涚炕椤垫煡璇㈡椂涓婏拷?锟戒笂椤佃繑鍥炵殑锟�?鍚庝竴鏉¤褰曡嚜鐒舵棩鏈燂紱YYYYMMDD */
    public String relDate;
    /**娴佹按锟�? 缈婚〉鎺у埗浣跨敤锛涢娆℃煡璇笂閫佺┖锛涚炕椤垫煡璇㈡椂涓婏拷?锟戒笂椤佃繑鍥炵殑锟�?鍚庝竴鏉¤褰曟祦姘村彿锟�? */
    public String traceNo;

    /** 鍦板潃*/
    public String addr;

    /** 绛剧害鍒版湡鏃ユ湡*/
    public String deadline;
    /** 绛剧害鍒版湡鏃ユ湡*/
    public String bidDeadline;
    /*绛剧害锟�?澶ч噾锟�?*/
    public String maxAmt;

    /** 缂磋垂鎺堟潈*/
    public String paymentAuth;

    /** 杩樻鎺堟潈*/
    public String repayAuth;

    /** 鑷姩鎶曟爣鍒版湡锟�?*/
    public String autoBidDeadline;

    /** 鑷姩鎶曟爣绛剧害锟�?楂橀噾锟�?*/
    public String autoBidMaxAmt;
    /** 缂磋垂鎺堟潈鍒版湡锟�?*/
    public String paymentDeadline;
    /** 缂磋垂绛剧害锟�?楂橀噾锟�?*/
    public String paymentMaxAmt;
    /** 杩樻鎺堟潈鍒版湡锟�?*/
    public String repayDeadline;
    /** 杩樻绛剧害锟�?楂橀噾锟�?*/
    public String repayMaxAmt;


    /** 浠ｆ墸绛剧害*/
    public String agreeDeduct;


    /** 绛剧害鍙栨秷鏃ユ湡*/
    public String cancelDate;
    /** cancelTime*/
    public String cancelTime;

    /** 楠岃瘉鐮佺被锟�?*/
    public String smsType;

    //缂磋垂鎺堟潈
    public String txType;
    public String getTransactionUrl() {
        return transactionUrl;
    }

    public void setTransactionUrl(String transactionUrl) {
        this.transactionUrl = transactionUrl;
    }

    public String getVerifyOrderUrl() {
        return verifyOrderUrl;
    }

    public void setVerifyOrderUrl(String verifyOrderUrl) {
        this.verifyOrderUrl = verifyOrderUrl;
    }

    //淇濈暀锟�?
    private String reserved;
    
    //鍙楁墭鏀粯flg 0:鍚︼紝1锛氭槸
    private String entrustFlag;
    //(鍙楁墭)鏀舵浜虹數瀛愯处锟�?
    private String receiptAccountId;
    // 椤甸潰璋冪敤鎴愬姛鍚庤烦杞繛锟�?
    private String successfulUrl;
    // 鍟嗘埛鍚嶇О
    private String coinstName;
    
    public String getRestMsg() {
        return restMsg;
    }

    public void setRestMsg(String restMsg) {
        this.restMsg = restMsg;
    }
    
    
    public String getReserved() {
        return reserved;
    }


	public void setReserved(String reserved) {
        this.reserved = reserved;
    }
	public String getTxCode() {
        return txCode;
    }
    public void setTxCode(String txCode) {
        this.txCode = txCode;
    }
    public String getInstCode() {
        return instCode;
    }
    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }
    public String getBankCode() {
        return bankCode;
    }
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    public String getTxDate() {
        return txDate;
    }
    public void setTxDate(String txDate) {
        this.txDate = txDate;
    }
    public String getTxTime() {
        return txTime;
    }
    public void setTxTime(String txTime) {
        this.txTime = txTime;
    }
    public String getSeqNo() {
        return seqNo;
    }
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getBitMap() {
        return bitMap;
    }
    public void setBitMap(String bitMap) {
        this.bitMap = bitMap;
    }
    public String getAutoBid() {
        return autoBid;
    }
    public void setAutoBid(String autoBid) {
        this.autoBid = autoBid;
    }
    public String getAutoTransfer() {
        return autoTransfer;
    }
    public void setAutoTransfer(String autoTransfer) {
        this.autoTransfer = autoTransfer;
    }
    public String getAgreeWithdraw() {
        return agreeWithdraw;
    }
    public void setAgreeWithdraw(String agreeWithdraw) {
        this.agreeWithdraw = agreeWithdraw;
    }
    public String getDirectConsume() {
        return directConsume;
    }
    public void setDirectConsume(String directConsume) {
        this.directConsume = directConsume;
    }
    
	public String getLendPayOrderId() {
		return lendPayOrderId;
	}

	public void setLendPayOrderId(String lendPayOrderId) {
		this.lendPayOrderId = lendPayOrderId;
	}

	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getRiskAmount() {
		return riskAmount;
	}

	public void setRiskAmount(String riskAmount) {
		this.riskAmount = riskAmount;
	}

	public String getCaccount() {
		return caccount;
	}

	public void setCaccount(String caccount) {
		this.caccount = caccount;
	}

	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getAcctState() {
		return acctState;
	}

	public void setAcctState(String acctState) {
		this.acctState = acctState;
	}

	public String getFrzState() {
		return frzState;
	}

	public void setFrzState(String frzState) {
		this.frzState = frzState;
	}

	public String getPinLosCd() {
		return pinLosCd;
	}

	public void setPinLosCd(String pinLosCd) {
		this.pinLosCd = pinLosCd;
	}

	public String getForIncome() {
		return forIncome;
	}

	public void setForIncome(String forIncome) {
		this.forIncome = forIncome;
	}

	public String getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}

	public String getLogUserId() {
		return logUserId;
	}

	public void setLogUserId(String logUserId) {
		this.logUserId = logUserId;
	}

	public String getLogUserName() {
		return logUserName;
	}

	public void setLogUserName(String logUserName) {
		this.logUserName = logUserName;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogIp() {
		return logIp;
	}

	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}

	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	public String getLogRemark() {
		return logRemark;
	}

	public void setLogRemark(String logRemark) {
		this.logRemark = logRemark;
	}

	public String getLogOrderId() {
		return logOrderId;
	}

	public void setLogOrderId(String logOrderId) {
		this.logOrderId = logOrderId;
	}

	public String getLogOrderDate() {
		return logOrderDate;
	}

	public void setLogOrderDate(String logOrderDate) {
		this.logOrderDate = logOrderDate;
	}

	public String getLogBankDetailUrl() {
		return logBankDetailUrl;
	}

	public void setLogBankDetailUrl(String logBankDetailUrl) {
		this.logBankDetailUrl = logBankDetailUrl;
	}

	public String getLogUuid() {
		return logUuid;
	}

	public void setLogUuid(String logUuid) {
		this.logUuid = logUuid;
	}

	public boolean isLogVerifyFlag() {
		return logVerifyFlag;
	}

	public void setLogVerifyFlag(boolean logVerifyFlag) {
		this.logVerifyFlag = logVerifyFlag;
	}

	public String getLogVerifyResult() {
		return logVerifyResult;
	}

	public void setLogVerifyResult(String logVerifyResult) {
		this.logVerifyResult = logVerifyResult;
	}

	public String getLogOrderStatus() {
		return logOrderStatus;
	}

	public void setLogOrderStatus(String logOrderStatus) {
		this.logOrderStatus = logOrderStatus;
	}

	public String getLogNotifyType() {
		return logNotifyType;
	}

	public void setLogNotifyType(String logNotifyType) {
		this.logNotifyType = logNotifyType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTxAmount() {
		return txAmount;
	}

	public void setTxAmount(String txAmount) {
		this.txAmount = txAmount;
	}

	public String getTxFee() {
		return txFee;
	}

	public void setTxFee(String txFee) {
		this.txFee = txFee;
	}

	public String getRetUrl() {
		return retUrl;
	}

	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getUserIP() {
		return userIP;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

	public String getAcqRes() {
		return acqRes;
	}

	public void setAcqRes(String acqRes) {
		this.acqRes = acqRes;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAcctUse() {
		return acctUse;
	}

	public void setAcctUse(String acctUse) {
		this.acctUse = acctUse;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSrvTxCode() {
		return srvTxCode;
	}

	public void setSrvTxCode(String srvTxCode) {
		this.srvTxCode = srvTxCode;
	}

	public String getLastSrvAuthCode() {
		return lastSrvAuthCode;
	}

	public void setLastSrvAuthCode(String lastSrvAuthCode) {
		this.lastSrvAuthCode = lastSrvAuthCode;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getTotAmount() {
		return totAmount;
	}

	public void setTotAmount(String totAmount) {
		this.totAmount = totAmount;
	}

	public String getOrgOrderId() {
		return orgOrderId;
	}

	public void setOrgOrderId(String orgOrderId) {
		this.orgOrderId = orgOrderId;
	}

	public String getSrvAuthCode() {
		return srvAuthCode;
	}

	public void setSrvAuthCode(String srvAuthCode) {
		this.srvAuthCode = srvAuthCode;
	}

	public String getIntAmount() {
		return intAmount;
	}

	public void setIntAmount(String intAmount) {
		this.intAmount = intAmount;
	}

	public String getTxFeeOut() {
		return txFeeOut;
	}

	public void setTxFeeOut(String txFeeOut) {
		this.txFeeOut = txFeeOut;
	}

	public String getTxFeeIn() {
		return txFeeIn;
	}

	public void setTxFeeIn(String txFeeIn) {
		this.txFeeIn = txFeeIn;
	}

	public String getForAccountId() {
		return forAccountId;
	}

	public void setForAccountId(String forAccountId) {
		this.forAccountId = forAccountId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getFrzFlag() {
		return frzFlag;
	}

	public void setFrzFlag(String frzFlag) {
		this.frzFlag = frzFlag;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getCardBankCnaps() {
		return cardBankCnaps;
	}

	public void setCardBankCnaps(String cardBankCnaps) {
		this.cardBankCnaps = cardBankCnaps;
	}

	public String getCardBankNameCn() {
		return cardBankNameCn;
	}

	public void setCardBankNameCn(String cardBankNameCn) {
		this.cardBankNameCn = cardBankNameCn;
	}

	public String getCardBankNameEn() {
		return cardBankNameEn;
	}

	public void setCardBankNameEn(String cardBankNameEn) {
		this.cardBankNameEn = cardBankNameEn;
	}

	public String getCardBankProvince() {
		return cardBankProvince;
	}

	public void setCardBankProvince(String cardBankProvince) {
		this.cardBankProvince = cardBankProvince;
	}

	public String getCardBankCity() {
		return cardBankCity;
	}

	public void setCardBankCity(String cardBankCity) {
		this.cardBankCity = cardBankCity;
	}

	public String getForgotPwdUrl() {
		return forgotPwdUrl;
	}

	public void setForgotPwdUrl(String forgotPwdUrl) {
		this.forgotPwdUrl = forgotPwdUrl;
	}

	public String getCardBankCode() {
		return cardBankCode;
	}

	public void setCardBankCode(String cardBankCode) {
		this.cardBankCode = cardBankCode;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getTxCounts() {
		return txCounts;
	}

	public void setTxCounts(String txCounts) {
		this.txCounts = txCounts;
	}

	public String getRetNotifyURL() {
		return retNotifyURL;
	}

	public void setRetNotifyURL(String retNotifyURL) {
		this.retNotifyURL = retNotifyURL;
	}

	public String getTsfAmount() {
		return tsfAmount;
	}

	public void setTsfAmount(String tsfAmount) {
		this.tsfAmount = tsfAmount;
	}

	public String getOrgTxAmount() {
		return orgTxAmount;
	}

	public String getMaxAmt() {
        return maxAmt;
    }


    public void setMaxAmt(String maxAmt) {
        this.maxAmt = maxAmt;
    }


    public void setOrgTxAmount(String orgTxAmount) {
		this.orgTxAmount = orgTxAmount;
	}

	public String getSubPacks() {
		return subPacks;
	}

	public void setSubPacks(String subPacks) {
		this.subPacks = subPacks;
	}

	public String getReceived() {
		return received;
	}

	public void setReceived(String received) {
		this.received = received;
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBatchTxDate() {
		return batchTxDate;
	}

	public void setBatchTxDate(String batchTxDate) {
		this.batchTxDate = batchTxDate;
	}

	public String getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(String totalItems) {
		this.totalItems = totalItems;
	}

	public int getLogClient() {
		return logClient;
	}

	public void setLogClient(int logClient) {
		this.logClient = logClient;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getReqTxCode() {
		return reqTxCode;
	}

	public void setReqTxCode(String reqTxCode) {
		this.reqTxCode = reqTxCode;
	}

	public String getReqTxDate() {
		return reqTxDate;
	}

	public void setReqTxDate(String reqTxDate) {
		this.reqTxDate = reqTxDate;
	}

	public String getReqTxTime() {
		return reqTxTime;
	}

	public void setReqTxTime(String reqTxTime) {
		this.reqTxTime = reqTxTime;
	}

	public String getReqSeqNo() {
		return reqSeqNo;
	}

	public void setReqSeqNo(String reqSeqNo) {
		this.reqSeqNo = reqSeqNo;
	}

	public String getReqOrderId() {
		return reqOrderId;
	}

	public void setReqOrderId(String reqOrderId) {
		this.reqOrderId = reqOrderId;
	}

	public String getTxState() {
		return txState;
	}

	public void setTxState(String txState) {
		this.txState = txState;
	}

	public String getAvailBal() {
		return availBal;
	}

	public void setAvailBal(String availBal) {
		this.availBal = availBal;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getRaiseDate() {
		return raiseDate;
	}

	public void setRaiseDate(String raiseDate) {
		this.raiseDate = raiseDate;
	}

	public String getRaiseEndDate() {
		return raiseEndDate;
	}

	public void setRaiseEndDate(String raiseEndDate) {
		this.raiseEndDate = raiseEndDate;
	}

	public String getIntType() {
		return intType;
	}

	public void setIntType(String intType) {
		this.intType = intType;
	}

	public String getIntPayDay() {
		return intPayDay;
	}

	public void setIntPayDay(String intPayDay) {
		this.intPayDay = intPayDay;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getBailAccountId() {
		return bailAccountId;
	}

	public void setBailAccountId(String bailAccountId) {
		this.bailAccountId = bailAccountId;
	}

	public String getNominalAccountId() {
		return nominalAccountId;
	}

	public void setNominalAccountId(String nominalAccountId) {
		this.nominalAccountId = nominalAccountId;
	}

	public String getCurrBal() {
		return currBal;
	}

	public void setCurrBal(String currBal) {
		this.currBal = currBal;
	}

	public String getBonusFlag() {
		return bonusFlag;
	}

	public void setBonusFlag(String bonusFlag) {
		this.bonusFlag = bonusFlag;
	}

	public String getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(String bonusAmount) {
		this.bonusAmount = bonusAmount;
	}

	public String getDesLineFlag() {
		return desLineFlag;
	}

	public void setDesLineFlag(String desLineFlag) {
		this.desLineFlag = desLineFlag;
	}

	public String getDesLine() {
		return desLine;
	}

	public void setDesLine(String desLine) {
		this.desLine = desLine;
	}

	public String getSucAmount() {
		return sucAmount;
	}

	public void setSucAmount(String sucAmount) {
		this.sucAmount = sucAmount;
	}

	public String getSucCounts() {
		return sucCounts;
	}

	public void setSucCounts(String sucCounts) {
		this.sucCounts = sucCounts;
	}

	public String getFailAmount() {
		return failAmount;
	}

	public void setFailAmount(String failAmount) {
		this.failAmount = failAmount;
	}

	public String getFailCounts() {
		return failCounts;
	}

	public void setFailCounts(String failCounts) {
		this.failCounts = failCounts;
	}

	public String getRelAmount() {
		return relAmount;
	}

	public void setRelAmount(String relAmount) {
		this.relAmount = relAmount;
	}

	public String getRelCounts() {
		return relCounts;
	}

	public void setRelCounts(String relCounts) {
		this.relCounts = relCounts;
	}

	public String getBatchState() {
		return batchState;
	}

	public void setBatchState(String batchState) {
		this.batchState = batchState;
	}

	public String getFailMsg() {
		return failMsg;
	}

	public void setFailMsg(String failMsg) {
		this.failMsg = failMsg;
	}

	public String getBatchTxCode() {
		return batchTxCode;
	}

	public void setBatchTxCode(String batchTxCode) {
		this.batchTxCode = batchTxCode;
	}

	public String getBidFee() {
		return bidFee;
	}

	public void setBidFee(String bidFee) {
		this.bidFee = bidFee;
	}

	public String getDebtFee() {
		return debtFee;
	}

	public void setDebtFee(String debtFee) {
		this.debtFee = debtFee;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getWithdrawFlag() {
		return withdrawFlag;
	}

	public void setWithdrawFlag(String withdrawFlag) {
		this.withdrawFlag = withdrawFlag;
	}

	public String getPinFlag() {
		return pinFlag;
	}

	public void setPinFlag(String pinFlag) {
		this.pinFlag = pinFlag;
	}

	public String getForName() {
		return forName;
	}

	public void setForName(String forName) {
		this.forName = forName;
	}

	public String getAvailAmount() {
		return availAmount;
	}

	public void setAvailAmount(String availAmount) {
		this.availAmount = availAmount;
	}

	public String getTxIncome() {
		return txIncome;
	}

	public void setTxIncome(String txIncome) {
		this.txIncome = txIncome;
	}

	public String getPayAllianceCode() {
		return payAllianceCode;
	}

	public void setPayAllianceCode(String payAllianceCode) {
		this.payAllianceCode = payAllianceCode;
	}

	public String getOrgTxDate() {
		return orgTxDate;
	}

	public void setOrgTxDate(String orgTxDate) {
		this.orgTxDate = orgTxDate;
	}

	public String getOrgTxTime() {
		return orgTxTime;
	}

	public void setOrgTxTime(String orgTxTime) {
		this.orgTxTime = orgTxTime;
	}

	public String getOrgSeqNo() {
		return orgSeqNo;
	}

	public void setOrgSeqNo(String orgSeqNo) {
		this.orgSeqNo = orgSeqNo;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOrFlag() {
		return orFlag;
	}

	public void setOrFlag(String orFlag) {
		this.orFlag = orFlag;
	}

	public String getYield() {
		return yield;
	}

	public void setYield(String yield) {
		this.yield = yield;
	}

	public String getIntTotal() {
		return intTotal;
	}

	public void setIntTotal(String intTotal) {
		this.intTotal = intTotal;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getIncFlag() {
		return incFlag;
	}

	public void setIncFlag(String incFlag) {
		this.incFlag = incFlag;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getSmsSeq() {
		return smsSeq;
	}

	public void setSmsSeq(String smsSeq) {
		this.smsSeq = smsSeq;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public String getContOrderId() {
		return contOrderId;
	}

	public void setContOrderId(String contOrderId) {
		this.contOrderId = contOrderId;
	}

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEntrustFlag() {
		return entrustFlag;
	}

	public void setEntrustFlag(String entrustFlag) {
		this.entrustFlag = entrustFlag;
	}

	public String getReceiptAccountId() {
		return receiptAccountId;
	}

	public void setReceiptAccountId(String receiptAccountId) {
		this.receiptAccountId = receiptAccountId;
	}

    public String getAffirmDate() {
        return affirmDate;
    }

    public void setAffirmDate(String affirmDate) {
        this.affirmDate = affirmDate;
    }

    public String getAffirmTime() {
        return affirmTime;
    }

    public void setAffirmTime(String affirmTime) {
        this.affirmTime = affirmTime;
    }

    public String getRtnInd() {
        return rtnInd;
    }

    public void setRtnInd(String rtnInd) {
        this.rtnInd = rtnInd;
    }

    public String getInpDate() {
        return inpDate;
    }

    public void setInpDate(String inpDate) {
        this.inpDate = inpDate;
    }

    public String getInpTime() {
        return inpTime;
    }

    public void setInpTime(String inpTime) {
        this.inpTime = inpTime;
    }

    public String getRelDate() {
        return relDate;
    }

    public void setRelDate(String relDate) {
        this.relDate = relDate;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }


    public String getSuccessfulUrl() {
        return successfulUrl;
    }


    public void setSuccessfulUrl(String successfulUrl) {
        this.successfulUrl = successfulUrl;
    }
    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getIdentity() {
        return identity;
    }


    public void setIdentity(String identity) {
        this.identity = identity;
    }


    public String getAddr() {
        return addr;
    }


    public void setAddr(String addr) {
        this.addr = addr;
    }


    public String getDeadline() {
        return deadline;
    }


    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }


    public String getBidDeadline() {
        return bidDeadline;
    }


    public void setBidDeadline(String bidDeadline) {
        this.bidDeadline = bidDeadline;
    }


    public String getCoinstName() {
        return coinstName;
    }


    public void setCoinstName(String coinstName) {
        this.coinstName = coinstName;
    }


    public String getPaymentAuth() {
        return paymentAuth;
    }


    public void setPaymentAuth(String paymentAuth) {
        this.paymentAuth = paymentAuth;
    }


    public String getRepayAuth() {
        return repayAuth;
    }


    public void setRepayAuth(String repayAuth) {
        this.repayAuth = repayAuth;
    }


    public String getAutoBidDeadline() {
        return autoBidDeadline;
    }


    public void setAutoBidDeadline(String autoBidDeadline) {
        this.autoBidDeadline = autoBidDeadline;
    }


    public String getAutoBidMaxAmt() {
        return autoBidMaxAmt;
    }


    public void setAutoBidMaxAmt(String autoBidMaxAmt) {
        this.autoBidMaxAmt = autoBidMaxAmt;
    }


    public String getPaymentDeadline() {
        return paymentDeadline;
    }


    public void setPaymentDeadline(String paymentDeadline) {
        this.paymentDeadline = paymentDeadline;
    }


    public String getPaymentMaxAmt() {
        return paymentMaxAmt;
    }


    public void setPaymentMaxAmt(String paymentMaxAmt) {
        this.paymentMaxAmt = paymentMaxAmt;
    }


    public String getRepayDeadline() {
        return repayDeadline;
    }


    public void setRepayDeadline(String repayDeadline) {
        this.repayDeadline = repayDeadline;
    }


    public String getRepayMaxAmt() {
        return repayMaxAmt;
    }


    public void setRepayMaxAmt(String repayMaxAmt) {
        this.repayMaxAmt = repayMaxAmt;
    }


    public String getAgreeDeduct() {
        return agreeDeduct;
    }


    public void setAgreeDeduct(String agreeDeduct) {
        this.agreeDeduct = agreeDeduct;
    }
    public String getCancelDate() {
        return cancelDate;
    }


    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }


    public String getCancelTime() {
        return cancelTime;
    }


    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }


    public String getSmsType() {
        return smsType;
    }


    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }




	public String getTxType() {
		return txType;
	}


	public void setTxType(String txType) {
		this.txType = txType;
	}


	public String getFrzType() {
		return frzType;
	}


	public void setFrzType(String frzType) {
		this.frzType = frzType;
	}

}
