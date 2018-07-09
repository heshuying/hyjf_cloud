 package com.hyjf.cs.market.mq.base;

 /**
  * 生产参数封装
  * @author dxj
  * @date 2018/07/06
  */
 public class ProducerFieldsWrapper {
     private String group;
     private String instanceName;

     public String getGroup() {
         return group;
     }

     public void setGroup(String group) {
         this.group = group;
     }

     public String getInstanceName() {
         return instanceName;
     }

     public void setInstanceName(String instanceName) {
         this.instanceName = instanceName;
     }
 

}
