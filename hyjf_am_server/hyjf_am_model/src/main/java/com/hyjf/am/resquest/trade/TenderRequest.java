/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @Description 出借请求参数
 * @Author sss
 * @Version v0.1
 * @Date 2018/6/19 9:37
 */
public class TenderRequest extends BaseVO {
    /**
     * 债转编号  债转需要上送
     */
    @ApiModelProperty(value = "债转编号  债转需要上送")
    private String creditNid ;
    /**
     * 优惠券编号
     */
    @ApiModelProperty(value = "优惠券编号")
    private Integer couponGrantId ;
    /**
     * 项目编号
     */
    @ApiModelProperty(value = "项目编号 出借 加入计划用")
    private String borrowNid ;
    /**
     * 防止重复加入计划
     */
    @ApiModelProperty(value = "防止重复加入计划")
    private String tenderToken;
    // 出借Ip
    private String ip;
    /**
     * 出借平台
     */
    @ApiModelProperty(value = "出借平台 app需要上送")
    private String platform;
    // 登录的userId
    private Integer userId;
    // 当前登录user对象
    private UserVO user;
    /**
     * 出借金额
     */
    @ApiModelProperty(value = "出借金额")
    private String account;

    @ApiModelProperty(value = "出借金额App用")
    private String money;

    @ApiModelProperty(value = "优惠券ID  App用")
    private String couponId;

    @ApiModelProperty(value = "签名")
    private String sign;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "请求类型")
    private String borrowType;

    @ApiModelProperty(value = "是否最后确认")
    private String isConfirm;

    @ApiModelProperty(value = "神策预置属性")
    private String presetProps;
    // 内部操作用
    private BankOpenAccountVO bankOpenAccount;

    private AccountVO tenderAccount ;

    private String planOrderId;

    // 预期收益
    private BigDecimal earnings;

    // 出借金额
    private BigDecimal accountDecimal;

    private Integer nowTime;

    // 优惠券出借  排他校验用
    private Integer couponOldTime;

    private String mainTenderNid;
    // 优惠券收益
    private BigDecimal couponInterest;
    // 订单号
    private String orderId;

    // 债转用到的---------------
    private String assignCapital;
    /**
     * order
     */
    private String order;

    /**
     * 版本号
     */
    private String version;
    /**
     * 网络状态
     */
    private String netStatus;

    /**
     * 随机字符串
     */
    private String randomString;

    /**
     * 出借人用户名
     */
    private String userName;

    /**
     * 出借来源
     * @return
     */
    private String tenderFrom;

    /**
     * 温金投平台  ClientConstants.WJT_PC_CLIENT   ClientConstants.WJT_WEI_CLIENT
     */
    private String wjtClient;

    public String getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(String isConfirm) {
        this.isConfirm = isConfirm;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNetStatus() {
        return netStatus;
    }

    public void setNetStatus(String netStatus) {
        this.netStatus = netStatus;
    }

    public String getRandomString() {
        return randomString;
    }

    public void setRandomString(String randomString) {
        this.randomString = randomString;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public Integer getCouponGrantId() {
        return couponGrantId;
    }

    public void setCouponGrantId(Integer couponGrantId) {
        this.couponGrantId = couponGrantId;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getTenderToken() {
        return tenderToken;
    }

    public void setTenderToken(String tenderToken) {
        this.tenderToken = tenderToken;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BankOpenAccountVO getBankOpenAccount() {
        return bankOpenAccount;
    }

    public void setBankOpenAccount(BankOpenAccountVO bankOpenAccount) {
        this.bankOpenAccount = bankOpenAccount;
    }

    public AccountVO getTenderAccount() {
        return tenderAccount;
    }

    public void setTenderAccount(AccountVO tenderAccount) {
        this.tenderAccount = tenderAccount;
    }

    public String getPlanOrderId() {
        return planOrderId;
    }

    public void setPlanOrderId(String planOrderId) {
        this.planOrderId = planOrderId;
    }

    public BigDecimal getEarnings() {
        return earnings;
    }

    public void setEarnings(BigDecimal earnings) {
        this.earnings = earnings;
    }

    public BigDecimal getAccountDecimal() {
        return accountDecimal;
    }

    public void setAccountDecimal(BigDecimal accountDecimal) {
        this.accountDecimal = accountDecimal;
    }

    public Integer getNowTime() {
        return nowTime;
    }

    public void setNowTime(Integer nowTime) {
        this.nowTime = nowTime;
    }

    public Integer getCouponOldTime() {
        return couponOldTime;
    }

    public void setCouponOldTime(Integer couponOldTime) {
        this.couponOldTime = couponOldTime;
    }

    public String getMainTenderNid() {
        return mainTenderNid;
    }

    public void setMainTenderNid(String mainTenderNid) {
        this.mainTenderNid = mainTenderNid;
    }

    public BigDecimal getCouponInterest() {
        return couponInterest;
    }

    public void setCouponInterest(BigDecimal couponInterest) {
        this.couponInterest = couponInterest;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAssignCapital() {
        return assignCapital;
    }

    public void setAssignCapital(String assignCapital) {
        this.assignCapital = assignCapital;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType;
    }

    public String getTenderFrom() {
        return tenderFrom;
    }

    public void setTenderFrom(String tenderFrom) {
        this.tenderFrom = tenderFrom;
    }

    public String getPresetProps() {
        return presetProps;
    }

    public void setPresetProps(String presetProps) {
        this.presetProps = presetProps;
    }

    public String getWjtClient() {
        return wjtClient;
    }

    public void setWjtClient(String wjtClient) {
        this.wjtClient = wjtClient;
    }
}
