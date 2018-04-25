#mybatis 生成mapper


    
    
### 步骤


``` lua

1. 运行原子服务层的下build.xml. 直接exclipse run as ant 
2. 修改 hyjf_mybatis_generator 工程下的 init_user.properties 为本地路径
3. eclipse下对 genator 工程进行 maven install

 或者命令行下：（每次改配置需要执行一次）
mvn install:install-file -Dfile=D:\hyjf\git\pro\hyjf_cloud\hyjf_mybatis_generator\target\hyjf_mybatis_generator-0.0.1-SNAPSHOT.jar -DgroupId=hyjf -DartifactId=hyjf-mybatis-generator -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar

4. hyjf-mybatis-generator 工程上右键->Run As->Maven Build打开窗口后在Goals栏填写： mybatis-generator:generate

```


   