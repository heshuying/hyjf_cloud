package com.hyjf.wbs.configs.ds;

import com.hyjf.common.constants.CommonConstant;
import com.hyjf.wbs.configs.ds.DynamicDataSourceContextHolder.DbType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@MapperScan("com.hyjf.wbs.*.dao.mapper")
@AutoConfigureAfter({ DataSourceAutoConfiguration.class})
public class MybatisConfig {
	@Value("${datasource.type}")
	private Class<? extends DataSource> dataSourceType;

	@Primary
	@Bean(name = "writeTradeDataSource")
	@ConfigurationProperties(prefix = "datasource.write-trade")
	public DataSource writeTradeDataSource() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	@Bean(name = "writeUserDataSource")
	@ConfigurationProperties(prefix = "datasource.write-user")
	public DataSource writeUserDataSource() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	@Bean(name = "writeConfigDataSource")
	@ConfigurationProperties(prefix = "datasource.write-config")
	public DataSource writeConfigDataSource() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	@Bean(name = "writeMarketDataSource")
	@ConfigurationProperties(prefix = "datasource.write-market")
	public DataSource writeMarketDataSource() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	@Bean(name = "readTradeDataSource")
	@ConfigurationProperties(prefix = "datasource.read-trade")
	public DataSource readTradeDataSource() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	@Bean(name = "readMarketDataSource")
	@ConfigurationProperties(prefix = "datasource.read-market")
	public DataSource readMarketDataSource() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	@Bean(name = "readUserDataSource")
	@ConfigurationProperties(prefix = "datasource.read-user")
	public DataSource readUserDataSource() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	@Bean(name = "readConfigDataSource")
	@ConfigurationProperties(prefix = "datasource.read-config")
	public DataSource readConfigDataSource() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	/**
	 * 
	 * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
	 * 
	 */
	@Bean("dynamicDataSource")
	public DynamicDataSource dynamicDataSource(@Qualifier("writeTradeDataSource") DataSource writeTradeDataSource,
											   @Qualifier("writeUserDataSource") DataSource writeUserDataSource,
											   @Qualifier("writeConfigDataSource") DataSource writeConfigDataSource,
											   @Qualifier("writeMarketDataSource") DataSource writeMarketDataSource,
											   @Qualifier("readTradeDataSource") DataSource readTradeDataSource,
											   @Qualifier("readMarketDataSource") DataSource readMarketDataSource,
											   @Qualifier("readUserDataSource") DataSource readUserDataSource,
											   @Qualifier("readConfigDataSource") DataSource readConfigDataSource) {

		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		targetDataSources.put(DbType.WRITETRADE.name(), writeTradeDataSource);
		targetDataSources.put(DbType.WRITEUSER.name(), writeUserDataSource);
		targetDataSources.put(DbType.WRITECONFIG.name(), writeConfigDataSource);
		targetDataSources.put(DbType.WRITEMARKET.name(), writeMarketDataSource);
		targetDataSources.put(DbType.READTRADE.name(), readTradeDataSource);
		targetDataSources.put(DbType.READUSER.name(), readUserDataSource);
		targetDataSources.put(DbType.READCONFIG.name(), readConfigDataSource);
		targetDataSources.put(DbType.READMARKET.name(), readMarketDataSource);

		// 将数据源的 key 放到数据源上下文的 key 集合中，用于切换时判断数据源是否有效
        DynamicDataSourceContextHolder.dataSourceKeys.add(DbType.WRITETRADE.name());
		DynamicDataSourceContextHolder.dataSourceKeys.add(DbType.WRITEUSER.name());
		DynamicDataSourceContextHolder.dataSourceKeys.add(DbType.WRITECONFIG.name());
		DynamicDataSourceContextHolder.dataSourceKeys.add(DbType.WRITEMARKET.name());
        DynamicDataSourceContextHolder.dataSourceKeys.add(DbType.READTRADE.name());
		DynamicDataSourceContextHolder.dataSourceKeys.add(DbType.READUSER.name());
		DynamicDataSourceContextHolder.dataSourceKeys.add(DbType.READCONFIG.name());
		DynamicDataSourceContextHolder.dataSourceKeys.add(DbType.READMARKET.name());
        // 将 Slave 数据源的 key 放在集合中，用于轮循
        DynamicDataSourceContextHolder.slaveDataSourceKeys.add(DbType.READTRADE.name());
		DynamicDataSourceContextHolder.slaveDataSourceKeys.add(DbType.READUSER.name());
		DynamicDataSourceContextHolder.slaveDataSourceKeys.add(DbType.READCONFIG.name());
		DynamicDataSourceContextHolder.slaveDataSourceKeys.add(DbType.READMARKET.name());


		DynamicDataSource dataSource = new DynamicDataSource();
		// 该方法是AbstractRoutingDataSource的方法
		dataSource.setTargetDataSources(targetDataSources);
		// 默认的datasource设置为myTestDbDataSource
		dataSource.setDefaultTargetDataSource(writeTradeDataSource);
		return dataSource;
	}

	/**
	 *
	 * 根据数据源创建SqlSessionFactory
	 *
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DynamicDataSource dynamicDataSource,
											   @Value("${mybatis.mapper-locations}") String mapperLocations) throws Exception {
		
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dynamicDataSource);
		// 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
		factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
		return factoryBean.getObject();
	}

	/**
	 *
	 * 配置事务管理器
	 *
	 */
	@Bean(name ="transactionManager")
	public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
		return new DataSourceTransactionManager(dataSource);
	}


	@Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor(@Qualifier("transactionManager") DataSourceTransactionManager transactionManager){

		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		String transactionExecution = "execution(* com.hyjf..*Service.*(..))";
        pointcut.setExpression(transactionExecution);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setOrder(CommonConstant.DATASOURCE_AOP_TRANSACTION);

        Properties attributes = new Properties();
        attributes.setProperty("insert*", "PROPAGATION_REQUIRED,-Exception");
        attributes.setProperty("update*", "PROPAGATION_REQUIRED,-Exception");
        attributes.setProperty("delete*", "PROPAGATION_REQUIRED,-Exception");

        TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, attributes);
        advisor.setAdvice(txAdvice);

        return advisor;
    }
	
}