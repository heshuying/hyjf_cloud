package com.hyjf.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p>
 * 属性文件
 * </p>
 *
 * @author gogtz
 * @version 1.0.0
 */
public class PropUtils {

	protected static final String MESSAGE_RESOURCES_PROPERTIES_FILE_NAME = "properties/message/message.properties";

	protected static final String SYSTEM_RESOURCES_PROPERTIES_FILE_NAME = "properties/system.properties";

	protected static final String REDIS_RESOURCES_PROPERTIES_FILE_NAME = "properties/redis.properties";
	
	protected static final String JDBC_RESOURCES_PROPERTIES_FILE_NAME = "properties/jdbc.properties";

	/**
	 * 取得所有属性值
	 * 
	 * @param propertiesFileName
	 * @return
	 * @throws IOException
	 */
	protected static Properties getProperties(String propertiesFileName) throws IOException {
		Properties prop = new Properties();
		InputStream is = PropUtils.class.getClassLoader().getResourceAsStream(propertiesFileName);

		try {
			prop.load(is);
		} catch (IOException e) {
			throw e;
		}

		return prop;
	}

	/**
	 * message文件
	 *
	 * @return
	 * @throws IOException
	 */
	public static Properties getMessageResourcesProperties() {
		try {
			return getProperties(MESSAGE_RESOURCES_PROPERTIES_FILE_NAME);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}

	/**
	 * 取得Message文件的属性值
	 *
	 * @return
	 * @throws IOException
	 */
	public static String getMessage(String key) {
		Properties properties = getMessageResourcesProperties();
		return properties.getProperty(key);
	}

	/**
	 * 取得System文件
	 *
	 * @return
	 * @throws IOException
	 */
	public static Properties getSystemResourcesProperties() {
		try {
			return getProperties(SYSTEM_RESOURCES_PROPERTIES_FILE_NAME);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}

	/**
	 * 取得redis文件
	 *
	 * @return
	 * @throws IOException
	 */
	public static Properties getRedisResourcesProperties() {
		try {
			return getProperties(REDIS_RESOURCES_PROPERTIES_FILE_NAME);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}

	/**
	 * 取得System文件的属性值
	 *
	 * @return
	 * @throws IOException
	 */
	public static String getSystem(String key) {
		Properties properties = getSystemResourcesProperties();
		return properties.getProperty(key);
	}

	/**
	 * 取得redis文件的属性值
	 *
	 * @return
	 * @throws IOException
	 */
	public static String getRedisValue(String key) {
		Properties properties = getRedisResourcesProperties();
		return properties.getProperty(key);
	}

    /**
     * 取得jdbc文件
     *
     * @return
     * @throws IOException
     */
    public static Properties getJdbcResourcesProperties() {
        try {
            return getProperties(JDBC_RESOURCES_PROPERTIES_FILE_NAME);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }
    
    /**
     * 取得jdbc文件的属性值
     *
     * @return
     * @throws IOException
     */
    public static String getJdbc(String key) {
        Properties properties = getJdbcResourcesProperties();
        return properties.getProperty(key);
    }
}
