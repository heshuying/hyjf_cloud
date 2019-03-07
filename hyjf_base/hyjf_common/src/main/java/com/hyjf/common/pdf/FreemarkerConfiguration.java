package com.hyjf.common.pdf;

import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class FreemarkerConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(FreemarkerConfiguration.class);

	private static Configuration config = null;

	/**
	 * Static initialization.
	 * 
	 * Initialize the configuration of Freemarker.
	 */

	public static Configuration getConfiguation() {
		config = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		config.setClassForTemplateLoading(FreemarkerConfiguration.class, "/"); // ftl模板的根路径
		return config;
	}

	public static Configuration getServletContextConfiguation(HttpServletRequest request) {
		config = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		config.setServletContextForTemplateLoading(request.getSession().getServletContext(), "/WEB-INF/"); // ftl模板的根路径
		return config;
	}
	
	/**
	 * Static initialization.
	 * 
	 * Initialize the configuration of Freemarker.
	 */

	public static Configuration getDirectoryConfiguation(String filePath) {
		config = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		try {
			config.setDirectoryForTemplateLoading(new File(filePath));
			config.setEncoding(Locale.CHINA, "UTF-8");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return config;
	}
}
