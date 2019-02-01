/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.hgdatareport.bifa;

/**
 * @author jun
 * @version BifaCommonConstants, v0.1 2018/11/30 14:05
 */
public class BifaCommonConstants {

    public static final String SUCCESSCODE = "0000";
    /**
     * 已经上报过
     */
    public static final String REPORTEDCODE = "0007";
    /**
     * 已经上报过描述
     */
    public static final String EXIST= "不可以重复添加";

    public static final String ERRCODE = "9999";

    public static final String ERRDESC = "报送失败";

    public static final String SUCCESSDESC = "报送成功";

    public static final String HZR = "HZR";

    public static final String HT_BIFAHJHPLANINFO_COLLECTIONNAME ="ht_bifa_hjh_planinfo";

    /**
     * 出借=0的用户  未开户用户不报送
     */
    public static final String DATATYPE_1003 = "1003";
    /**
     * 出借大于0的用户
     */
    public static final String DATATYPE_1002 = "1002";
    /**
     * 注册用户
     */
    public static final String DATATYPE_1005 = "1005";
    /**
     *借贷用户
     */
    public static final String DATATYPE_1000 = "1000";

}
