package com.hyjf.cs.user.constants;

import com.hyjf.common.util.MessageConstants;
import com.hyjf.common.util.PropertiesConstants;

/**
 * 汇盈金服第三方接口返回码常量
 */
public class ErrorCodeConstant implements MessageConstants, PropertiesConstants {
    // 成功
    public static final String SUCCESS = "000";
    /********************共通相关 start***********************/
    // 请求参数异常
    public static final String STATUS_CE000001 = "CE000001";
    // 系统验签失败
    public static final String STATUS_CE000002 = "CE000002";
    // 根据手机号查询用户信息失败
    public static final String STATUS_CE000003 = "CE000003";
    //根据电子账户号查询用户信息失败
    public static final String STATUS_CE000004 = "CE000004";
    // 银行处理中，请稍后查看
    public static final String STATUS_CE000005 = "CE000005";
    // 没有用户开户信息
    public static final String STATUS_CE000006 = "CE000006";
    // 没有用户信息
    public static final String STATUS_CE000007 = "CE000007";
    // 未发送短信
    public static final String STATUS_CE000008 = "CE000008";
    // 请勿重复操作
    public static final String STATUS_CE000009 = "CE000009";
    // 用户为出借账户
    public static final String STATUS_CE000010 = "CE000010";
    // 未进行缴费授权
    public static final String STATUS_CE000011 = "CE000011";
    // 未进行还款授权
    public static final String STATUS_CE000012 = "CE000012";
    // 系统异常
    public static final String STATUS_CE999999 = "CE999999";
    /********************共通相关      end***********************/
    /********************注册相关相关start***********************/
    // 手机号不能为空
    public static final String STATUS_ZC000001 = "ZC000001";
    // 机构编号不能为空
    public static final String STATUS_ZC000002 = "ZC000002";
    // 请输入您的真实手机号码
    public static final String STATUS_ZC000003 = "ZC000003";
    // 机构编号错误
    public static final String STATUS_ZC000004 = "ZC000004";
    // 手机号已在平台注册
    public static final String STATUS_ZC000005 = "ZC000005";
    // 渠道不能为空
    public static final String STATUS_ZC000006 = "ZC000006";
    // 姓名不能为空
    public static final String STATUS_ZC000007 = "ZC000007";
    // 身份证号不能为空
    public static final String STATUS_ZC000008 = "ZC000008";
    // 银行卡号不能为空
    public static final String STATUS_ZC000009 = "ZC000009";
    // 手机验证码不能为空
    public static final String STATUS_ZC000010 = "ZC000010";
    // 短信发送订单号为空
    public static final String STATUS_ZC000011 = "ZC000011";
    // 真实姓名不能包含空格
    public static final String STATUS_ZC000012 = "ZC000012";
    // 真实姓名不能超过10位
    public static final String STATUS_ZC000013 = "ZC000013";
    // 身份证已存在
    public static final String STATUS_ZC000014 = "ZC000014";
    // 验证码错误
    public static final String STATUS_ZC000015 = "ZC000015";
    // 银行卡与姓名不符
    public static final String STATUS_ZC000016 = "ZC000016";
    // 银行卡与证件不符
    public static final String STATUS_ZC000017 = "ZC000017";
    // 平台不能为空
    public static final String STATUS_ZC000018 = "ZC000018";
    // 渠道不能为空
    public static final String STATUS_ZC000019 = "ZC000019";
    // 渠道非法
    public static final String STATUS_ZC000020 = "ZC000020";
    // 身份证(18位)校验位错误
    public static final String STATUS_ZC000021 = "ZC000021";
    //宽策略平台与汇盈金服平台用户绑定失败
    public static final String STATUS_ZCOOOO22 = "ZC000022";
    //用户已开户
    public static final String STATUS_ZC000023 = "ZC000023";
    // 身份属性非法
    public static final String STATUS_ZC000024 = "ZC000024";
    // 用户已在别的平台注册
    public static final String STATUS_ZC000025 = "ZC000025";
    /********************注册相关相关    end***********************/

    /********************绑定解绑银行卡相关start***********************/
    // 用户已绑定银行卡,请先解除绑定,然后重新操作！
    public static final String STATUS_BC000001 = "BC000001";
    // 获取用户银行卡信息失败
    public static final String STATUS_BC000002 = "BC000002";
    // 银行处理中，请稍后查看
    public static final String STATUS_BC000003 = "BC000003";
    // 解绑失败，余额大于0元是不能解绑银行卡
    public static final String STATUS_BC000004 = "BC000004";
    /********************绑定解绑银行卡相关    end***********************/

    /********************免密提现相关start***********************/
    // 免密提现-用户暂未开通该服务
    public static final String STATUS_NC000001 = "NC000001";
    // 免密提现-用户银行卡信息不一致
    public static final String STATUS_NC000002 = "NC000002";
    // 免密提现-查询用户失败
    public static final String STATUS_NC000003 = "NC000003";
    // 免密提现-查询用户详细信息失败
    public static final String STATUS_NC000004 = "NC000004";
    // 免密提现-查询电子帐号失败
    public static final String STATUS_NC000005 = "NC000005";
    // 免密提现-提现金额不能小于一元
    public static final String STATUS_NC000006 = "NC000006";
    // 免密提现-提现失败
    public static final String STATUS_NC000007 = "NC000007";
    // 免密提现-查询用户银行卡信息失败
    public static final String STATUS_NC000008 = "NC000008";
    // 免密提现-查询用户账户信息失败
    public static final String STATUS_NC000009 = "NC000009";
    // 免密提现-用户账户余额不足
    public static final String STATUS_NC000010 = "NC000010";
    /********************免密提现相关    end***********************/

    /********************交易密码相关start***********************/
    // 已设置交易密码
    public static final String STATUS_TP000001 = "TP000001";
    //未设置过交易密码，请先设置交易密码
    public static final String STATUS_TP000002 = "TP000002";
    
    
    
    /********************交易密码相关    end***********************/
    


    /********************资产推送 start***********************/
    
    // 没有用户信息
    public static final String STATUS_ZT000001 = "ZT000001";
    // 没有用户开户信息
    public static final String STATUS_ZT000002 = "ZT000002";
    // 用户不是借款人
    public static final String STATUS_ZT000003 = "ZT000003";
    // 系统异常,资产未进库
    public static final String STATUS_ZT000004 = "ZT000004";
    // 不支持这种还款方式 
    public static final String STATUS_ZT000005 = "ZT000005";
    // 资产金额超过一百万
    public static final String STATUS_ZT000006 = "ZT000006";
    // 资产信息不正确
    public static final String STATUS_ZT000007 = "ZT000007";
    // 资产已入库
    public static final String STATUS_ZT000008 = "ZT000008";
    // 资产不存在
    public static final String STATUS_ZT000009 = "ZT000009";
    
    
    /********************资产推送    end***********************/
    
    /********************还款接口    start***********************/
    /**
     * 没有查询到对应借款标的
     * */
    public static final String STATUS_HK000001 = "HK000001";
    /**
     * 借款标的没有对应的还款信息
     */
    public static final String STATUS_HK000002 = "HK000002";
    /*********************还款接口  end***********************/
    
    /********************受托支付接口    start***********************/
    /**标的状态错误 TP000001 */
    public static final String STATUS_TR000001 = "TR000001";
    /**不在授权白名单里 TP000002 */
    public static final String STATUS_TR000002 = "TR000002";
    /**证件号不能为空  TP000003 */
    public static final String STATUS_TR000003 = "TR000003";
    /********************受托支付接口    end***********************/

    /********************多合一授权接口    start***********************/
    /**授权类型不能为空 SQ000001 */
    public static final String STATUS_SQ000001 = "SQ000001";
    /**授权类型不是指定类型 SQ000002 */
    public static final String STATUS_SQ000002 = "SQ000002";
    /**出借人只能进行 1-自动投资授权 2-自动债转授权 3-缴费授权 SQ000003 */
    public static final String STATUS_SQ000003 = "SQ000003";
    /**借款人只能进行 3-缴费授权 4-还款授权 SQ000004 */
    public static final String STATUS_SQ000004 = "SQ000004";
    /**垫付机构只能进行 3-缴费授权 SQ000005 */
    public static final String STATUS_SQ000005 = "SQ000005";
    /**授权类型过长 SQ000006 */
    public static final String STATUS_SQ000006 = "SQ000006";
    /********************多合一授权接口    end***********************/
}
