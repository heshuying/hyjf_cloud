/**
 * Description: 正则去html标签
 * Copyright: Copyright (HYJF Corporation)2016
 * Company: HYJF Corporation
 * @author: Michael
 * @version: 1.0
 * Created at: 2016年11月8日 下午2:58:16
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.common.http;

import com.hyjf.common.util.StringPool;
import com.hyjf.common.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael
 */

public class HtmlUtil {
	 private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式  
	    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式  
	    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式  
	    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符  
	      
	    /** 
	     * @param htmlStr 
	     * @return 
	     *  删除Html标签 
	     */  
	    public static String delHTMLTag(String htmlStr) {  
	        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);  
	        Matcher m_script = p_script.matcher(htmlStr);  
	        htmlStr = m_script.replaceAll(""); // 过滤script标签  
	  
	        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);  
	        Matcher m_style = p_style.matcher(htmlStr);  
	        htmlStr = m_style.replaceAll(""); // 过滤style标签  
	  
	        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
	        Matcher m_html = p_html.matcher(htmlStr);  
	        htmlStr = m_html.replaceAll(""); // 过滤html标签  
	  
	        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);  
	        Matcher m_space = p_space.matcher(htmlStr);  
	        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签  
	        return htmlStr.trim(); // 返回文本字符串  
	    }

	/**
	 * Html整理
	 * @param text
	 * @return
	 */
	public static String unescape(String text) {
		if (text == null) {
			return null;
		}

		if (text.length() == 0) {
			return StringPool.BLANK;
		}

		// Optimize this

		text = StringUtil.replace(text, "&lt;", "<");
		text = StringUtil.replace(text, "&gt;", ">");
		text = StringUtil.replace(text, "&amp;", "&");
		text = StringUtil.replace(text, "&rsquo;", "\u2019");
		text = StringUtil.replace(text, "&#034;", "\"");
		text = StringUtil.replace(text, "&#039;", "'");
		text = StringUtil.replace(text, "&#040;", "(");
		text = StringUtil.replace(text, "&#041;", ")");
		text = StringUtil.replace(text, "&#044;", ",");
		text = StringUtil.replace(text, "&#035;", "#");
		text = StringUtil.replace(text, "&#037;", "%");
		text = StringUtil.replace(text, "&#059;", ";");
		text = StringUtil.replace(text, "&#061;", "=");
		text = StringUtil.replace(text, "&#043;", "+");
		text = StringUtil.replace(text, "&#045;", "-");

		return text;
	}

	public static String getTextFromHtml(String htmlStr){
	        htmlStr = delHTMLTag(htmlStr);  
	        htmlStr = htmlStr.replaceAll("&nbsp;", "");  
	        return htmlStr;  
	    }
}

	