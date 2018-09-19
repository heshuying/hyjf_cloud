#微服务

### 名词解释
    cs - combination system 组合服务-业务微服务
    am - atom system 原子服务-数据访问微服务
    
    
### 项目结构


``` lua

hyjf_cloud 父工程，管理jar版本,模块管理，自动识别pom.xml
├── hyjf_cloud_server           -- spring cloud 组件
|     ├── hyjf_eureka           -- 注册中心[端口:1111]
|     ├── hyjf_config           -- 配置中心[端口:2222]
|     ├── hyjf_spring_boot_admin-- 服务监控[端口:18080]
|     ├── hyjf_zipkin           -- 全链路监控[端口:9411]  废弃，使用官方推荐jar
|     └── hyjf_zuul             -- 服务网关[端口:8080]
├── hyjf_base                   -- 基础模块
|   ├── hyjf-common             -- 公共模块 被依赖
|   ├── hyjf_mybatis_generator  -- mybatis自动生成工具
|   └── hyjf_ribbon_client      -- 未注册服务访问注册中心基础 废弃
├── hyjf_am_server              -- 原子服务层
|   ├── hyjf_am_model           -- 原子层统一数据模型 request vo response
|   ├── hyjf_am_util            -- 原子层工具
|   └── hyjf_am_core            -- 原子层核心服务
|   |   ├── hyjf_am_trade      -- 标的、交易、资金、资产微服务[端口:8096]
|   |   ├── hyjf_am_config      -- 公用配置微服务[端口:8091]
|   |   ├── hyjf_am_market      -- 市场微服务[端口:8094] 包含活动、券等
|   |   └── hyjf_am_user        -- 用户中心微服务[端口:8092]
├── hyjf_cs_server
|   ├── hyjf_cs_trade          -- 核心组合微服务 [端口:8082] 
|   ├── hyjf_cs_market          -- 市场组合微服务 [端口:8083] 
|   ├── hyjf_cs_message         -- 消息组合微服务 [端口:8084] 
|   └── hyjf_cs_user            -- 用户组合微服务 [端口:8081] 
├── hyjf_platform_server        -- 平台服务
|   ├── hyjf-admin              -- 后台系统[端口:9093]
|   ├── hyjf_batch              -- 批处理[端口:9091]
|   └── hyjf_pay                -- 银行接口模块
|        ├── hyjf_pay_server    -- 银行接口服务工程[端口:9092]
|        └── hyjf_pay_lib       -- 接口调用工具类

```

### 启动
    1. 启动注册中心 
    2. 启动配置中心 
    3. 启动微服务 按需启动
    5. 访问: 不通过网关可以直接访问组合微服务
    6. 启动服务网关zuul ,zuul启动之前必须启动am_config
        zuul 统一访问 http://localhost:8080
    7. batch 访问： http://localhost:9091
    8.监控  http://localhost:18080
    
    ps:
        本地配置host文件 （样例）：
            127.0.0.1       app.xiasq.com
            127.0.0.1       web.xiasq.com
            127.0.0.1       api.xiasq.com
            127.0.0.1       wechat.xiasq.com
        目前是根据域名区分请求渠道，不添加host文件会无法访问  
       
###swagger页面
``` 
ADMIN页面：http://47.104.249.145:9093/swagger-ui.html
CALLCENTER页面:http://47.104.203.75:9094/swagger-ui.html
CS-MARKET页面：http://47.104.203.75:8083/swagger-ui.html
CS-MESSAGE页面:http://47.104.203.75:8084/swagger-ui.html
CS-TRADE页面:http://47.104.203.75:8082/swagger-ui.html
CS-USER页面:http://47.104.203.75:8081/swagger-ui.html
BATCH页面：http://47.104.203.75:9091
```
###数据库相关
```
trade ：
118.190.148.153   3301
写用户： trade_m  密码： aAR!Q23AUP!ogk$J
读用户： trade_s  密码： uCLeilNVcMxM5GRR

user :  包含剩下的 market , config
118.190.148.153  3302
写用户： user_m   密码：  aAR!Q23AUP!ogk$J
读用户： user_s   密码： uCLeilNVcMxM5GRR

admin：
47.104.244.26 33306
读用户： admin_s  密码： uCLeilNVcMxM5GRR 
```
###redis配置
```
redis:
123.56.216.212   6666  密码：RH06863b3e6
```
###mongo链接
```
47.94.171.28 28001
user: testuser
passwd:e2VdOav40i
DB:mservice
```
###rocketMQ
```
47.104.250.73:9876;47.104.250.28:9876
rocketmq控制台http://123.56.216.212:8888
```
### 微服务架构
   ![调用链](pic2.png)    
       
### 调用链
   ![调用链](pic1.png)
   