package com.hyjf.common.log;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

	private static Logger logger = LoggerFactory.getLogger(LogUtil.class);
    /**
     * Debug LOG
     * 
     * @param className
     * @param methodName
     * @param logContent
     */
    public static String debugLog(String className, String methodName, String logContent) {
        StringBuffer str = new StringBuffer();
        str.append("【DEBUG ");
        str.append("[className：");
        str.append(className);
        str.append("]<=>[methodName：");
        str.append(methodName);
        str.append("]<=>[logContent：");
        str.append(logContent);
        str.append("]");
        str.append("】");
        logger.debug(str.toString());
        return str.toString();
    }
    
    /**
     * Warn LOG
     * 
     * @param className
     * @param methodName
     * @param logContent
     */
    public static String warnLog(String className, String methodName, String logContent) {
        StringBuffer str = new StringBuffer();
        str.append("【WARN ");
        str.append("[className：");
        str.append(className);
        str.append("]<=>[methodName：");
        str.append(methodName);
        str.append("]<=>[logContent：");
        str.append(logContent);
        str.append("]");
        str.append("】");
        logger.debug(str.toString());
        return str.toString();
    }
    
    /**
     * INFO LOG
     * 
     * @param className
     * @param methodName
     * @param logContent
     */
    public static String infoLog(String className, String methodName, String logContent) {
        StringBuffer str = new StringBuffer();
        str.append("【INFO ");
        str.append("[className：");
        str.append(className);
        str.append("]<=>[methodName：");
        str.append(methodName);
        str.append("]<=>[logContent：");
        str.append(logContent);
        str.append("]");
        str.append("】");
        logger.debug(str.toString());
        return str.toString();
    }
    
	/**
	 * Debug 开始LOG
	 * 
	 * @param className
	 * @param methodName
	 * @param logContent
	 */
	public static String startLog(String className, String methodName, String logContent) {
		StringBuffer str = new StringBuffer();
		str.append("【START ");
		str.append("[className：");
		str.append(className);
		str.append("]<=>[methodName：");
		str.append(methodName);
		str.append("]<=>[logContent：");
		str.append(logContent);
		str.append("]");
		str.append("】");
		logger.debug(str.toString());
		return str.toString();
	}

	/**
	 * Debug 结束LOG
	 * 
	 * @param className
	 * @param methodName
	 * @param logContent
	 */
	public static String endLog(String className, String methodName, String logContent) {
		StringBuffer str = new StringBuffer();
		str.append("【END ");
		str.append("[className：");
		str.append(className);
		str.append("]<=>[methodName：");
		str.append(methodName);
		str.append("]<=>[logContent：");
		str.append(logContent);
		str.append("]");
		str.append("】");
		logger.debug(str.toString());
		return str.toString();
	}

    /**
     * ERROR 错误LOG
     * 
     * @param className
     * @param methodName
     * @param logContent
     */
    public static String errorLog(String className, String methodName, String logContent, Exception e) {
        StringBuffer str = new StringBuffer();
        str.append("【ERROR ");
        str.append("[className：");
        str.append(className);
        str.append("]<=>[methodName：");
        str.append(methodName);
        str.append("]<=>[logContent：");
        str.append(logContent);
        str.append("]");
        str.append("】");
        logger.error(str.toString());
        logger.error(logContent, e);
        return str.toString();
    }

    /**
     * Debug LOG
     * 
     * @param className
     * @param methodName
     */
    public static String debugLog(String className, String methodName) {
        return debugLog(className, methodName, StringUtils.EMPTY);
    }

    /**
     * Warn LOG
     * 
     * @param className
     * @param methodName
     */
    public static String warnLog(String className, String methodName) {
        return warnLog(className, methodName, StringUtils.EMPTY);
    }

    /**
     * Info LOG
     * 
     * @param className
     * @param methodName
     */
    public static String infoLog(String className, String methodName) {
        return infoLog(className, methodName, StringUtils.EMPTY);
    }
    
	/**
	 * Debug 开始LOG
	 * 
	 * @param className
	 * @param methodName
	 */
	public static String startLog(String className, String methodName) {
		return startLog(className, methodName, StringUtils.EMPTY);
	}

	/**
	 * Debug 结束LOG
	 * 
	 * @param className
	 * @param methodName
	 */
	public static String endLog(String className, String methodName) {
		return endLog(className, methodName, StringUtils.EMPTY);
	}
	

    /**
     * Error 错误LOG
     * 
     * @param className
     * @param methodName
     * @param e
     */
    public static String errorLog(String className, String methodName, Exception e) {
        return errorLog(className, methodName, StringUtils.EMPTY, e);
    }
}
