package com.hyjf.cs.market.client;

import com.hyjf.am.resquest.user.SmsCodeRequest;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.datacollect.TzjDayReportVO;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author xiasq
 * @version AmUserClient, v0.1 2018/7/6 11:04
 */
public interface AmUserClient {
    /**
     * 查询投之家当天注册人数、开户人数、绑卡人数
     * @param startTime
     * @param endTime
     * @return
     */
    TzjDayReportVO queryUserDataOnToday(Date startTime,Date endTime);

    /**
     * 查询投之家当天注册用户
     * @param startTime
     * @param endTime
     * @return
     */
    Set<Integer> queryRegisterUsersOnToday(Date startTime,Date endTime);
    /**
     * 查询投之家所有注册用户
     * @return
     */
    Set<Integer> queryAllTzjUsers();

    /**
     * 获取所有渠道
     * @param type 类型:app,pc
     * @return
     */
    List<UtmVO> selectUtmPlatList(String type);

    /**
     * 访问数
     * @param sourceId 账户推广平台
     * @param type 类型: pc,app
     * @return
     */
    Integer getAccessNumber(Integer sourceId, String type);

    /**
     * 注册数
     * @param sourceId 账户推广平台
     * @param type 类型: pc,app
     * @return
     */
    Integer getRegistNumber(Integer sourceId, String type);

    /**
     * 开户数
     * @param sourceId
     * @param type 类型: pc,app
     * @return
     */
    Integer getOpenAccountNumber(Integer sourceId, String type);

    /**
     * 投资人数
     * @param sourceId
     * @param type 类型: pc,app
     * @return
     */
    Integer getTenderNumber(Integer sourceId, String type);

    /**
     * 累计充值
     * @param sourceId
     * @param type 类型: pc,app
     * @return
     */
    BigDecimal getCumulativeRecharge(Integer sourceId, String type);

    /**
     * 汇直投投资金额
     * @param sourceId
     * @param type 类型: pc,app
     * @return
     */
    BigDecimal getHztTenderPrice(Integer sourceId, String type);

    /**
     * 汇消费投资金额
     * @param sourceId
     * @param type 类型: pc,app
     * @return
     */
    BigDecimal getHxfTenderPrice(Integer sourceId, String type);

    /**
     * 汇天利投资金额
     * @param sourceId
     * @param type 类型: pc,app
     * @return
     */
    BigDecimal getHtlTenderPrice(Integer sourceId, String type);

    /**
     * 汇添金投资金额
     * @param sourceId
     * @param type 类型: pc,app
     * @return
     */
    BigDecimal getHtjTenderPrice(Integer sourceId, String type);

    /**
     * 汇金理财投资金额
     * @param sourceId
     * @param type 类型: pc,app
     * @return
     */
    BigDecimal getRtbTenderPrice(Integer sourceId, String type);

    /**
     * 汇转让投资金额
     * @param sourceId
     * @param type 类型: pc,app
     * @return
     */
    BigDecimal getHzrTenderPrice(Integer sourceId, String type);

    /**
     * app渠道主单注册数
     * @param sourceId
     * @return
     */
    BigDecimal getRegisterAttrCount(Integer sourceId);

    /**
     * 查询相应的app渠道Ios开户数
     * @param sourceId
     * @return
     */
    Integer getAccountNumberIos(Integer sourceId);

    /**
     * 查询相应的app渠道PC开户数
     * @param sourceId
     * @return
     */
    Integer getAccountNumberPc(Integer sourceId);

    /**
     * 查询相应的app渠道安卓开户数
     * @param sourceId
     * @return
     */
    Integer getAccountNumberAndroid(Integer sourceId);

    /**
     * 查询相应的app渠道微信开户数
     * @param sourceId
     * @return
     */
    Integer getAccountNumberWechat(Integer sourceId);

    /**
     * 查询相应的app渠道用户Android投资数
     * @param sourceId
     * @return
     */
    Integer getTenderNumberAndroid(Integer sourceId);

    /**
     * 查询相应的app渠道用户IOS投资数
     * @param sourceId
     * @return
     */
    Integer getTenderNumberIos(Integer sourceId);

    /**
     * 查询相应的app渠道用户PC投资数
     * @param sourceId
     * @return
     */
    Integer getTenderNumberPc(Integer sourceId);

    /**
     * 查询相应的app渠道用户微信投资数
     * @param sourceId
     * @return
     */
    Integer getTenderNumberWechat(Integer sourceId);

    /**
     * 查询相应的app渠道无主单用户充值数
     * @param sourceId
     * @return
     */
    BigDecimal getCumulativeAttrCharge(Integer sourceId);

    /**
     * 查询相应的app渠道无主单开户数
     * @param sourceId
     * @return
     */
    Integer getOpenAccountAttrCount(Integer sourceId);

    /**
     * 查询相应的app渠道投资无主单用户数
     * @param sourceId
     * @return
     */
    Integer getInvestAttrNumber(Integer sourceId);

    /**
     * 查询相应的app渠道用户投资总额
     * @param sourceId
     * @return
     */
    BigDecimal getCumulativeAttrInvest(Integer sourceId);




    /**
     * 修改短信与邮件是否开启状态
     * @param userId
     * @param smsOpenStatus
     * @param emailOpenStatus
     * @return
     */
    Integer updateStatusByUserId( Integer userId,String smsOpenStatus,String emailOpenStatus);

    /**
     * 得到千乐渠道用户
     * @return
     */
    List<Integer> getQianleUser();

    /**
     * 保存短信信息
     * @param mobile
     * @param checkCode
     * @param validCodeType
     * @param status
     * @param platform
     * @return
     */
    int saveSmsCode(String mobile, String checkCode, String validCodeType, Integer status, String platform);

    int onlyCheckMobileCode(String mobile, String code);

}
