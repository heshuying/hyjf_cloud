package com.hyjf.zuul.config;

import org.springframework.stereotype.Component;

/**
 * @author xiasq
 * @version Flag, v0.1 2018/4/13 10:06
 */
@Component
public class Flag {
	private boolean flag;

	public synchronized boolean isFlag() {
		return flag;
	}

	public synchronized void setFlag(boolean flag) {
		this.flag = flag;
	}
}
