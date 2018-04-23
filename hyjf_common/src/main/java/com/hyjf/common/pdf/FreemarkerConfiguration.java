package com.hyjf.common.pdf;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import freemarker.template.Configuration;

public class FreemarkerConfiguration {

	private static Configuration config = null;

	/**
	 * Static initialization.
	 * 
	 * Initialize the configuration of Freemarker.
	 */

	public static Configuration getConfiguation() {
		config = new Configuration();
		config.setClassForTemplateLoading(FreemarkerConfiguration.class, "/"); // ftl模板的根路径
		return config;
	}

	public static Configuration getServletContextConfiguation(HttpServletRequest request) {
		config = new Configuration();
		config.setServletContextForTemplateLoading(request.getSession().getServletContext(), "/WEB-INF/"); // ftl模板的根路径
		return config;
	}
	
	/**
	 * Static initialization.
	 * 
	 * Initialize the configuration of Freemarker.
	 */

	public static Configuration getDirectoryConfiguation(String filePath) {
		config = new Configuration();
		try {
			config.setDirectoryForTemplateLoading(new File(filePath));
			config.setEncoding(Locale.CHINA, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return config;
	}
}
