package com.hyjf.zuul.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hyjf.am.config.vo.GatewayApiConfigVO;
import com.hyjf.zuul.client.AmConfigClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author xiasq
 * @version CustomRouteLocator, v0.1 2018/4/13 9:46
 */
public class CustomRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {
	public final static Logger logger = LoggerFactory.getLogger(CustomRouteLocator.class);
	private ZuulProperties properties;
	private AmConfigClient amConfigClient;
	private Flag flag;

	@Override
	public void refresh() {
		if (!flag.isFlag()) {
			flag.setFlag(true);
			doRefresh();
		}
	}

	public CustomRouteLocator(String servletPath, ZuulProperties properties) {
		super(servletPath, properties);
		this.properties = properties;
		logger.info("servletPath:{}", servletPath);
	}

	@Override
	protected Map<String, ZuulRoute> locateRoutes() {
		LinkedHashMap<String, ZuulRoute> routesMap = new LinkedHashMap<String, ZuulRoute>();
		// 从application.properties中加载路由信息
		routesMap.putAll(super.locateRoutes());
		// 加载路由信息
		routesMap.putAll(locateRoutesFromDB());
		// 优化一下配置
		LinkedHashMap<String, ZuulRoute> values = new LinkedHashMap<String, ZuulRoute>();
		for (Map.Entry<String, ZuulRoute> entry : routesMap.entrySet()) {
			String path = entry.getKey();
			// Prepend with slash if not already present.
			if (!path.startsWith("/")) {
				path = "/" + path;
			}
			if (StringUtils.hasText(this.properties.getPrefix())) {
				path = this.properties.getPrefix() + path;
				if (!path.startsWith("/")) {
					path = "/" + path;
				}
			}
			values.put(path, entry.getValue());
		}

		logger.info("locateRoutes is : {}", values);
		return values;
	}

	private Map<String, ZuulRoute> locateRoutesFromDB() {
		logger.info("load zuul routes from DB");
		Map<String, ZuulRoute> routes = new LinkedHashMap<String, ZuulRoute>();
		List<GatewayApiConfigVO> results = amConfigClient.findGatewayConfigs();
		if (!CollectionUtils.isEmpty(results)) {
			for (GatewayApiConfigVO result : results) {
				ZuulRoute zuulRoute = new ZuulRoute();
				try {
					BeanUtils.copyProperties(result, zuulRoute);
				} catch (Exception e) {
					logger.error("load zuul routes from DB error", e);
				}
				routes.put(zuulRoute.getPath(), zuulRoute);
			}
		}

		return routes;
	}

	public void setAmConfigClient(AmConfigClient amConfigClient) {
		this.amConfigClient = amConfigClient;
	}

	public void setFlag(Flag flag) {
		this.flag = flag;
	}
}
