/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.main;

/**
 * @author dxj
 * @version TestLine.java, v0.1 2018年7月28日 上午8:56:05
 */
public class TestLine {

	public static void main(String[] args) {
		
	
		
		String sLine = "\\r\\n";
		String sysLine = System.getProperty("line.separator");
		
		System.out.println(" a: "+sLine+"   b: "+sysLine);
		
		if (sLine.equals(sysLine)) {    
		    System.out.println("//r//n is for windows");    
		} else if (System.getProperty("line.separator").equals("\\r")) {    
		    System.out.println("//r is for Mac");    
		} else if (System.getProperty("line.separator").equals("\\n")) {    
		    System.out.println("//n is for Unix/Linux");    
		}
	}

}
