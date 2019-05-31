package com.hyjf.pay.lib.duiba.sdk;


import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Logger;


public class AssembleTool {

	public static String assembleUrl(String urlPar,Map<String, String> params){
		Logger log = Logger.getLogger("assembleUrl");
		StringBuilder str = new StringBuilder(urlPar);
		if (str.toString().endsWith("?")) {

		} else if(str.toString().contains("?")){
			str.append("&");
		}else{
			str.append("?");
		}

		for (Map.Entry<String, String> entry : params.entrySet()) {
			try {
				if (entry.getValue() == null || entry.getValue().length() == 0) {
					str.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
				} else {
					str.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8")).append("&");
				}
			} catch (Exception e) {
				log.info("assembleUrl:="+e);
			}
		}
		return str.toString();
	}
	
}
