/*
 * @Copyright: 2005-2017 www.2345.com. All rights reserved.
 */
package com.hyjf.zuul.security;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author xiasq
 * @version ShiroRealm, v0.1 2018/4/13 9:54
 */
public class ShiroRealm extends AuthorizingRealm {
	@Value("${basic.auth.username}")
	private String basicAuthUser;

	@Value("${basic.auth.password}")
	private String basicAuthPassword;

	public ShiroRealm(CacheManager cacheManager) {
		super(cacheManager);
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection paramPrincipalCollection) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
		if (!basicAuthUser.equals(usernamePasswordToken.getUsername())
				|| !basicAuthPassword.equals(String.valueOf(usernamePasswordToken.getPassword()))) {
			return null;
		}
		return new SimpleAuthenticationInfo(usernamePasswordToken.getUsername(), usernamePasswordToken.getPassword(),
				getName());
	}
}
