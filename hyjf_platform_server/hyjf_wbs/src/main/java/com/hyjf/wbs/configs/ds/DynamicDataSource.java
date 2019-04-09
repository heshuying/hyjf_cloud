package com.hyjf.wbs.configs.ds;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
    protected Object determineCurrentLookupKey() {
		return DynamicDataSourceContextHolder.getDataSourceKey();
	}
}