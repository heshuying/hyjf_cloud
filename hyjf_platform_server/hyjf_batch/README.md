#批处理微服务
## 启动
  导入sql文件（batch.sql）, tables_mysql_innodb.sql是从官方下载的但是索引长度超长，修改为batch.sql
   
  Quartz默认并发执行， 如果要禁止你的job并发执行，在job上标注 @DisallowConcurrentExecution 