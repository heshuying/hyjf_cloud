#微服务

### 名词解释
    cs - combination system 组合服务-业务微服务
    am - atom system 原子服务-数据访问微服务
    
    
### 项目结构

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
   