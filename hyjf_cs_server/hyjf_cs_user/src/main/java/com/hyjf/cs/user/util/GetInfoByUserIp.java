/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.util;

import org.apache.commons.lang.StringUtils;
import org.lionsoul.ip2region.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author libin
 * @version GetInfoByUserIp.java, v0.1 2018年10月26日 下午2:49:26
 */
@Component
public class GetInfoByUserIp {
	
	private static final Logger _log = LoggerFactory.getLogger(GetInfoByUserIp.class);

    private static final String IP_NAME = "/ip2region.db";

    public static String ipAddress;


    @Value("${hyjf.ip.address}")
    public void setIpAddress(String ipAddress) {
        GetInfoByUserIp.ipAddress = ipAddress;
    }

    public static String getIpAddress() {
        System.out.println( GetInfoByUserIp.ipAddress);
        return GetInfoByUserIp.ipAddress;
    }

    public static String getInfoByUserIp(String ipAddress) {//例如：101.105.35.57
		// 返回的拼装信息
		String info = "";
		// DB文件路径
		//String DBfilePath = "D:\\Workspace4\\ip2region\\data\\ip2region.db";//之后放在项目里
		String DBfilePath = getIpAddress()+IP_NAME;
		// 出入ip地址判空
		if(ipAddress == null & StringUtils.isEmpty(ipAddress)){
			_log.error("未获取到IP地址！");
			return info;
		}
		// DB文件存在性判断
        File file = new File(DBfilePath);
        if ( file.exists() == false ) {
        	_log.error("未获取到IP存储的DB文件！");
            return info;
        }
        // 算法类型
        int algorithm = DbSearcher.BTREE_ALGORITHM;
        // 默认算法
        String algoName = "B-tree";
        // 先不考虑其他算法
/*        if ( argv.length > 1 ) {
            if ( argv[1].equalsIgnoreCase("binary")) {
                algoName  = "Binary"; 
                algorithm = DbSearcher.BINARY_ALGORITHM;
            } else if ( argv[1].equalsIgnoreCase("memory") ) {
                algoName  = "Memory";
                algorithm = DbSearcher.MEMORY_ALGORITYM;
            }
        }*/
        try {
            _log.info("initializing "+algoName+" ... ");
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, DBfilePath);
            //根据算法获取方法(目前只支持默认算法)
            Method method = null;
            switch ( algorithm ) 
            {
            case DbSearcher.BTREE_ALGORITHM:
                method = searcher.getClass().getMethod("btreeSearch", String.class);
                break;
            case DbSearcher.BINARY_ALGORITHM:
                method = searcher.getClass().getMethod("binarySearch", String.class);
                break;
            case DbSearcher.MEMORY_ALGORITYM:
                method = searcher.getClass().getMethod("memorySearch", String.class);
                break;
            }
            double sTime = 0, cTime = 0;
            String line = null;
            DataBlock dataBlock = null;
            _log.info("传入的ip地址是："+ipAddress);
            line = ipAddress.trim();
            if ( Util.isIpAddress(line) == false ) {
                _log.error("Error: Invalid ip address！获取的IP地址非法！");
                return info;
            } else {
                sTime = System.nanoTime();
                dataBlock = (DataBlock) method.invoke(searcher, line);
                cTime = (System.nanoTime() - sTime) / 1000000;
                info = dataBlock.getRegion();
                searcher.close();	
            }
        } catch (IOException e) {
            _log.error(e.getMessage());
        } catch (DbMakerConfigException e) {
            _log.error(e.getMessage());
        } catch (NoSuchMethodException e) {
            _log.error(e.getMessage());
        } catch (SecurityException e) {
            _log.error(e.getMessage());
        } catch (IllegalAccessException e) {
            _log.error(e.getMessage());
        } catch (IllegalArgumentException e) {
            _log.error(e.getMessage());
        } catch (InvocationTargetException e) {
            _log.error(e.getMessage());
        }
		return info;
	}
	
	
 /*   public static void main(String[] argv){
    	String ip = "47.104.250.73";
    	String ip1 = "sadasd";
    	String ip2 = "";
    	String info = getInfoByUserIp(ip);
    	if(StringUtils.isEmpty(info)){
    		System.out.println("info是空");
    	} else {
    		StringBuffer line = new StringBuffer(info);
            int first_idx   = line.indexOf("|");
            String country = line.substring(0, first_idx);
           
            line = new StringBuffer(line.substring(first_idx + 1) );
            //get second ip
            int second_idx   = line.indexOf("|");
            String number = line.substring(0, second_idx);
            
            line = new StringBuffer(line.substring(second_idx + 1) );
            int thrid_idx   = line.indexOf("|");
            String province = line.substring(0, thrid_idx);
           
            line = new StringBuffer(line.substring(thrid_idx + 1) );
            int fouth_idx   = line.indexOf("|");
            String city = line.substring(0, fouth_idx);
    		System.out.println(country + ":" + number + ":" + province + ":" +city);
    		System.out.println(province + ":" +city);
    	}
    }*/
}
