#微服务

### 名词解释
    cs - combination system 组合服务-业务微服务
    am - atom system 原子服务-数据访问微服务
    
    
### 项目结构


``` lua

hyjf_cloud 父工程，管理jar版本,模块管理，自动识别pom.xml
├── hyjf_cloud_server -- spring cloud 组件
|     ├── hyjf_eureka -- 注册中心[端口:1111]
|     ├── hyjf_config -- 配置中心[端口:2222]
|     └── hyjf_zuul   -- 服务网关[端口:8080]
├── hyjf-admin -- 后台系统
├── hyjf-common -- 公共模块 被依赖
├── hyjf_mybatis_generator -- mybatis自动生成工具
├── hyjf_batch -- 批处理[端口:9091]
├── hyjf_pay -- 银行接口调用[端口:9092]
├── hyjf_am -- 原子服务层
|   ├── hyjf_am_model  -- 原子层统一数据模型 request vo response
|   ├── hyjf_am_util   -- 原子层工具
|   └── hyjf_am_server -- 原子层服务组件
|   |   ├── hyjf_am_borrow -- 标的 、资金、资产微服务[端口:8093]
|   |   ├── hyjf_am_config -- 公用配置微服务[端口:8091]
|   |   ├── hyjf_am_market -- 市场微服务[端口:8094] 包含活动、券等
|   |   ├── hyjf_am_market -- 消息微服务[端口:8095] 
|   |   └── hyjf_am_user   -- 用户微服务[端口:8092]
├── hyjf_cs_server
|   ├── hyjf_cs_borrow  -- 核心组合微服务 [端口:8082] 
|   ├── hyjf_cs_market  -- 市场组合微服务 [端口:8083] 
|   └── hyjf_cs_user    -- 用户组合微服务 [端口:8081] 

```

### 启动
    1. 启动注册中心 
    2. 启动配置中心 
    3. 启动微服务 按需启动
    5. 访问: 不通过网关可以直接访问组合微服务
    6. 启动服务网关zuul ,zuul启动之前必须启动am_config
        zuul 统一访问 http://localhost:8080
    7. batch 访问： http://localhost:9091
     
### 微服务架构
   ![调用链](pic2.png)    
       
### 调用链
   ![调用链](pic1.png)
   
   
   
   
   
### 测试
####1. 发送注册验证码 
    启动 EurekaApplication
    启动 AmConfigApplication
    启动 AmUserApplication
    启动 AmBorrowApplication
    启动 CsUserApplication
    启动 ZuulApplication
    
    postman导入 "微服务.postman_collection.json" ， 发送请求
    
    查看zuul日志，发现执行了过滤器和映射
    查看cs-user日志，打印出验证码请求日志， 并数据库 hyjf_user.huiyingdai_smscode插入

####2. 注册 
    修改参数moble和验证码，发送请求。
    
    查看am_user日志已经注册成功， am_borrow收到消息通知，保存account
    返回的result包含了token（登录令牌认证）,用户的主要信息等。

####3. 多点登录
    同上，每一次登录返回一个token,实现多点登录， 如果要单点登录销毁其他的token即可
    