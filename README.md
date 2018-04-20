#微服务

### 名词解释
    cs - combination system 组合服务-业务微服务
    am - atom system 原子服务-数据访问微服务
    
    
### 项目结构


``` lua

hyjf_cloud 模块管理，自动识别pom.xml
├── hyjf_cloud_server -- 父工程，管理jar版本
├── hyjf-admin -- 后台系统
├── hyjf-common -- 公共模块 被依赖
├── hyjf_mybatis_generator -- mybatis自动生成工具
├── hyjf_eureka -- 注册中心[端口:1111]
├── hyjf_config -- 配置中心[端口:2222]
├── hyjf_zuul -- 网关[端口:8080]
├── hyjf_batch -- 批处理[端口:9091]
├── hyjf_pay -- 银行接口调用[端口:9092]
├── hyjf_am_borrow -- 标的、资金、资产微服务
|    ├── hyjf_am_borrow_common -- vo request response 
|    └── hyjf_am_borrow_server -- 服务端[端口:8093]
├── hyjf_am_config -- 配置微服务
|    ├── hyjf_am_config_common -- vo request response 
|    └── hyjf_am_config_server -- 服务端[端口:8091]
├── hyjf_am_market -- 活动、营销微服务
|    ├── hyjf_am_market_common -- vo request response 
|    └── hyjf_am_market_server -- 服务端[端口:8094]
├── hyjf_am_message -- 消息微服务
|    ├── hyjf_am_message_common -- vo request response 
|    └── hyjf_am_message_server -- 服务端[端口:8095]
├── hyjf_am_user -- 用户微服务
|    ├── hyjf_am_user_common -- vo request response 
|    └── hyjf_am_user_server -- 服务端[端口:8092]
├── hyjf_cs_borrow -- 组合微服务 [端口:8082] 
├── hyjf_cs_market -- 组合微服务 [端口:8083] 
├── hyjf_cs_user -- 组合微服务 [端口:8081] 


```

### 启动
    1. 启动注册中心 
    2. 启动配置中心 
    3. 启动微服务 按需启动
    5. 访问: 不通过网关可以直接访问组合微服务
    6. 启动服务网关zuul ,zuul启动之前必须启动am_config
    7. batch 访问： http://localhost:9091
     
### 微服务架构
   ![调用链](pic2.png)    
       
### 调用链
   ![调用链](pic1.png)
   