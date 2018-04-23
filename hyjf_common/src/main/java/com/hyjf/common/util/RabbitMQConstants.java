package com.hyjf.common.util;

public class RabbitMQConstants {
    
    //优惠券exchange配置
    public static final String EXCHANGES_COUPON = "hyjf-direct-exchange";

    //优惠券exchange配置
    public static final String EXCHANGES_NAME = "hyjf-direct-exchange";
    
    //优惠券还款
    public static final String ROUTINGKEY_COUPONREPAY = "hyjf-routingkey-coupon-repay";
    public static final String QUEUE_COUPONREPAY = "hyjf-coupon-repay";
    
    //优惠券放款
    public static final String ROUTINGKEY_COUPONLOANS = "hyjf-routingkey-coupon-loans";
    public static final String QUEUE_COUPONLOANS = "hyjf-coupon-loans";
    
    //计划类优惠券还款
    public static final String ROUTINGKEY_COUPONREPAY_HJH = "hyjf-routingkey-coupon-planrepay";
    public static final String QUEUE_COUPONREPAY_HJH = "hyjf-coupon-planrepay";
    
    //计划类优惠券放款
    public static final String ROUTINGKEY_COUPONLOANS_HJH = "hyjf-routingkey-coupon-planloans";
    public static final String QUEUE_COUPONLOANS_HJH = "hyjf-coupon-planloans";
    
    //优惠券发放
    public static final String ROUTINGKEY_COUPON_SEND = "hyjf-routingkey-coupon-send";
    public static final String QUEUE_COUPON_SEND = "hyjf-coupon-send";
    
    //双十二气球活动
    public static final String ROUTINGKEY_ACT_BALLOON = "hyjf-routingkey-act-balloon";
    public static final String QUEUE_ACT_BALLOON = "hyjf-queue-act-balloon";


    // 自动录标
    public static final String ROUTINGKEY_BORROW_SEND = "hyjf-routingkey-borrow-send";
    public static final String QUEUE_BORROW_SEND = "hyjf-borrow-send";

    // 自动备案
    public static final String ROUTINGKEY_BORROW_RECORD = "hyjf-routingkey-borrow-record";
    public static final String QUEUE_BORROW_RECORD = "hyjf-borrow-record";

    // 自动初审
    public static final String ROUTINGKEY_BORROW_PREAUDIT = "hyjf-routingkey-borrow-preaudit";
    public static final String QUEUE_BORROW_PREAUDIT = "hyjf-borrow-preaudit";

    // 自动关联计划
    public static final String ROUTINGKEY_BORROW_ISSUE = "hyjf-routingkey-borrow-issue";
    public static final String QUEUE_BORROW_ISSUE = "hyjf-borrow-issue";

    // 自动汇直投优惠券使用
    public static final String ROUTINGKEY_HZT_COUPON_TENDER = "hyjf-routingkey-hzt-coupon-tender";
    public static final String QUEUE_HZT_COUPON_TENDER = "hyjf-hzt-coupon-tender";
    //红包账户流水明细文件下载
    public static final String DOWNLOAD_FILE_EVE = "hyjf-download-file-eve";

    // 法大大客户信息修改MQ
    public static final String ROUTINGKEY_USER_INFO_CHANGE = "fdd-routingkey-user-info-change";
    public static final String QUEUE_USER_INFO_CHANGE  = "fdd-user-info-change";

    // 法大大电子签章CA认证MQ
    public static final String ROUTINGKEY_CERTIFICATE_AUTHORITY = "fdd-routingkey-certificate-authority";
    public static final String QUEUE_CERTIFICATE_AUTHORITY = "fdd-certificate-authority";

    // 法大大生成合同接口
    public static final String ROUTINGKEY_GENERATE_CONTRACT = "fdd-routingkey-generate_contract";
    //法大大下载脱敏接口
    public static final String ROUTINGKEY_DOWNDESSENESITIZATION_CONTRACT = "fdd-routingkey-downdessenesitization_contract";


    public static final String ROUTINGKEY_AUTO_SIGN = "fdd-routingkey-auto_sign";
    
    //红包账户流水明细文件数据导入
    public static final String READ_FILE_ALEVE = "hyjf-read-file-aleve";
    public static final String READ_FILE_EVE = "hyjf-read-file-eve";
    //rabbitMq的fanout模式(发布者订阅者模式)
    public static final String ALEV_FANOUT_EXCHANGE = "hyjf-aleve-fanout-exchange";
   // public static final String READ_DATA_EVE = "hyjf-read-data-eve";
    
}
