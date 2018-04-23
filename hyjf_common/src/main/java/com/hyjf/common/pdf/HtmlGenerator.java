package com.hyjf.common.pdf;   
  
import java.io.BufferedWriter;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;   
  
public class HtmlGenerator {   
       
    /**  
     * Generate html string.  
     *   
     * @param template   the name of freemarker teamlate.  
     * @param variables  the data of teamlate.  
     * @return htmlStr  
     * @throws Exception  
     */  
    public static String generate(String ftlPath,String ftlName, Map<String,Object> variables) throws Exception{  
        Configuration config = FreemarkerConfiguration.getDirectoryConfiguation(ftlPath);   
        Template tp = config.getTemplate(ftlName);   
        StringWriter stringWriter = new StringWriter();     
        BufferedWriter writer = new BufferedWriter(stringWriter);     
        tp.setEncoding("UTF-8");     
        tp.process(variables, writer);     
        String htmlStr = stringWriter.toString();   
        writer.flush();     
        writer.close();   
        return htmlStr;   
    } 
}  
