package com.hyjf.am.trade.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConfig {
	
	@Value("${hyjf.env.test}")
	private boolean envTest;

	public boolean isEnvTest() {
		return envTest;
	}

	public void setEnvTest(boolean envTest) {
		this.envTest = envTest;
	}
    
    
}
