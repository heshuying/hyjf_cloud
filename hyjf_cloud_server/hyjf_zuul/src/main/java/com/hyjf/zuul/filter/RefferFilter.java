package com.hyjf.zuul.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author xiasq
 * @version RefferFilter, v0.1 2018/7/2 14:36
 */
public class RefferFilter extends ZuulFilter {
	private static Logger logger = LoggerFactory.getLogger(RefferFilter.class);

	@Value("${hyjf.domain.name}")
	private String hyjfDomainName;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String referer = request.getHeader("referer");
		if (StringUtils.isBlank(referer)) {
			logger.error("没有获取到referer...url is :{}", request.getRequestURI());
			ctx.setResponseBody("not have reffer...");
		} else {
			// 获取请求的域名
			String domainName = getDnFromReferer(referer);
			if (StringUtils.isNotBlank(hyjfDomainName) && hyjfDomainName.contains(domainName)) {
				// 如果是hyjf.com域名的访问 to do nothing
			} else {
				// 如果不是hyjf.com域名的访问 重定向到首页
				logger.error("not hyjf.com visit...url is :{}", request.getRequestURI());
				ctx.setResponseBody("not hyjf.com visit");
			}
		}

		return null;
	}

	private String getDnFromReferer(String referer) {
		String result = null;
		String regex = "(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)";
		try {
			Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher matcher = p.matcher(referer);
			if (matcher.find()) {
				result = matcher.group();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
