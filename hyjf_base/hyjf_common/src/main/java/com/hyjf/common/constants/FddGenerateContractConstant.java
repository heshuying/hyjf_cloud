package com.hyjf.common.constants;

public class FddGenerateContractConstant {

    /**投资居间服务协议*/
    public static final int PROTOCOL_TYPE_TENDER = 1;
    /**计划加入协议*/
    public static final int PROTOCOL_TYPE_PLAN = 2;
    /**债转协议*/
    public static final int PROTOCOL_TYPE_CREDIT = 3;
    /** 汇计划债转投资 */
    public static final int FDD_TRANSTYPE_PLAN_CRIDET = 4;
    /**合同标题——居间服务协议*/
    public static final String CONTRACT_DOC_TITLE = "汇盈金服互联网金融服务平台居间服务借款协议";
    /**合同标题——计划加入协议*/
    public static final String CONTRACT_DOC_TITLE_PLAN = "汇盈金服互联网金融服务平台汇计划投资服务协议";
    /**合同标题——债转协议*/
    public static final String CONTRACT_DOC_TITLE_CREDIT = "汇盈金服互联网金融服务平台债权转让协议";

    /**
     * 计划收益处理方式
     */
    public static final String SHOUYI_FUTOU = "收益复投";

    /**文件保存名称-居间服务协议*/
    public static final String CONTRACT_DOC_FILE_NAME_TENDER = "jjfw";
    /**债转服务协议*/
    public static final String CONTRACT_DOC_FILE_NAME_CREDIT = "zzfw";
    /**计划加入协议*/
    public static final String CONTRACT_DOC_FILE_NAME_PLAN = "jhjr";


    /**成功返回码*/
    public static final String FDD_RETURN_CODE_1000 = "1000";

    /**客户角色：接入平台*/
    public static final String FDD_CLIENT_ROLE_PLATFORM = "1";
    /**客户角色：担保公司*/
    public static final String FDD_CLIENT_ROLE_GUARANTEE = "2";
    /**客户角色： 投资人*/
    public static final String FDD_CLIENT_ROLE_TENDER = "3";
    /**客户角色： 借款人*/
    public static final String FDD_CLIENT_ROLE_BORROWER = "4";
    /**平台关键字*/
    public static final String FDD_SIGN_KEYWORK_PLATFORM = "丙方盖章处";
    /**投资人关键字*/
    public static final String FDD_SIGN_KEYWORK_TENDER= "甲方盖章处";
    /**借款人关键字*/
    public static final String FDD_SIGN_KEYWORK_BORROWER = "乙方盖章处";


    public static final String FDD_HYJF_CUSTOMERID ="hyjf.fdd.customerid";

    public static final String HYJF_FTP_IP ="hyjf.ftp.ip";
    public static final String HYJF_FTP_PORT ="hyjf.ftp.port";
    public static final String HYJF_FTP_USERNAME ="hyjf.ftp.username";
    public static final String HYJF_FTP_PASSWORD ="hyjf.ftp.password";
    public static final String HYJF_FTP_BASEPATH_IMG ="hyjf.ftp.basepath.img";
    public static final String HYJF_FTP_URL ="hyjf.ftp.url";
    public static final String HYJF_FTP_BASEPATH_PDF ="hyjf.ftp.basepath.pdf";
    public static final String HYJF_FTP_DOMAIN = "hyjf.ftp.domain";
    /**法大大打包临时存放文件*/
    public static final String HYJF_LOC_SAVEPATH ="hyjf.loc.savepath";
}
