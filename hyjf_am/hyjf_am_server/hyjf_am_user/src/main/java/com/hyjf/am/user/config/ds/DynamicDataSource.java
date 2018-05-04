package com.hyjf.am.user.config.ds;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceContextHolder.getDataSourceKey();
	}
}