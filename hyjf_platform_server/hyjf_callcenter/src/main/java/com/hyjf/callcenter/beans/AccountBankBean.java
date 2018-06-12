 /**
 * Description:按照用户名/手机号查询江西银行绑卡关系用接口返回类 	
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: wangjun
 * @version: 1.0
 *           Created at: 2018年06月11日 10:16
 *           Modification History:
 *           Modified by :
 */
package com.hyjf.callcenter.beans;

import java.io.Serializable;

 public class AccountBankBean implements Serializable  {

     /**
      *
      */
     private static final long serialVersionUID = 2569482549422133226L;
     /**
      * 用户名
      */
     private String userName;
     /**
      * 手机号
      */
     private String mobile;
     /**
      * 银行卡号
      */
     private String cardNo;
     /**
      * 所属银行
      */
     private String bank;
     /**
      * 添加时间
      */
     private String createTime;
     public String getUserName() {
         return userName;
     }
     public void setUserName(String userName) {
         this.userName = userName;
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
     public String getBank() {
         return bank;
     }
     public void setBank(String bank) {
         this.bank = bank;
     }
     public String getCreateTime() {
         return createTime;
     }
     public void setCreateTime(String createTime) {
         this.createTime = createTime;
     }
 }
