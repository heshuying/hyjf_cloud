#mybatis 生成mapper

##说明

``` lua
1. 每次修改配置文件，比如generatorConfig.xml,init_borrow.properties等，必须install hyjf_mybatis_generator才能生效
2. 如果不执行ant.xml直接生成，java文件不会有问题，但是mybatis.xml会在文件尾部追加，使得select delete update等元素id重复
3. generatorConfig.xml 里的table如果在数据库找不到，不生成任何文件，所以可以把所有的table放在一个xml中，但是要保证不同库表名唯一！
```

### 1. eclipse 步骤

``` lua

1. 运行原子服务层的下build.xml. 直接exclipse run as ant 
2. 修改 hyjf_mybatis_generator 工程下的 init_user.properties 为本地路径
3. eclipse下对 genator 工程进行 maven install

 或者命令行下：（每次改配置需要执行一次）
mvn install:install-file -Dfile=D:\hyjf\git\pro\hyjf_cloud\hyjf_mybatis_generator\target\hyjf_mybatis_generator-0.0.1-SNAPSHOT.jar -DgroupId=hyjf -DartifactId=hyjf-mybatis-generator -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar

4. hyjf-mybatis-generator 工程上右键->Run As->Maven Build打开窗口后在Goals栏填写： mybatis-generator:generate

```

### 2. idea 步骤
    
``` lua
1. 点击菜单栏 view -> Tool Windows -> Ant build 打开ant窗口
2. 点击绿色小加号添加build.xml（每个原子层都有单独一份，清除微服务对应的dao）
3. 双击main执行ant脚本
4. 修改hyjf_mybatis_generator  resoutce 下配置文件，init_*.properties 修改本机绝对路径，
    然后修改generatorConfig.xml要加载的属性文件
5.  mvn install  (view -> Tool Windows -> maven projects)
6. 执行plugins 下的 mybatis-generator:generate
7. 操作完成，查看 version controller-> local changes下生成的文件

    
```