package com.hyjf.common.file;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZIPGenerator {
    private static Logger logger = LoggerFactory.getLogger(ZIPGenerator.class);
    
    /**
     * 生成并下载ZIP文件
     * 
     * @param response
     * @param files
     * @param fileName
     * @throws Exception
     */
    public static void generateZip(HttpServletResponse response, List<File> files, String fileName){
        response.setContentType("APPLICATION/OCTET-STREAM");  
        response.setHeader("Content-Disposition","attachment; filename="+fileName+".zip");
        logger.info("Download................"+JSONObject.toJSONString(files));
        logger.info("fileName................"+fileName);
        System.out.println("Download................");
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(response.getOutputStream());
            zipFile(files, "", zos);     
            zos.flush();     
        } catch (IOException e) {
            logger.error("生成zip文件失败:", e);
        }finally {
            if (zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    logger.error("zos流关闭失败", e);
                }
            }
        }
    }
    
    /**
     * 生成并下载ZIP文件,生成之后删除临时文件
     * 
     * @param response
     * @param files
     * @param fileName
     * @throws Exception
     */
    public static void generateZipAndDel(HttpServletResponse response, List<File> files, String fileName){
        response.setContentType("APPLICATION/OCTET-STREAM");  
        response.setHeader("Content-Disposition","attachment; filename="+fileName+".zip");  
        System.out.println("Download................");   
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(response.getOutputStream());
            zipFileAndDel(files, "", zos);     
            zos.flush();     
        } catch (IOException e) {
            logger.error("生成zip文件失败:", e);
        }finally {
            if (zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    logger.error("zos流关闭失败", e);
                }
            }
        }
    }
    
    /**
     * 生成zip
     * 
     * @param subs
     * @param baseName
     * @param zos
     * @throws Exception
     */
    private static void zipFile(List<File> subs, String baseName, ZipOutputStream zos) throws IOException {       
        for (int i=0;i<subs.size();i++) {  
         File f=subs.get(i);  
         zos.putNextEntry(new ZipEntry(baseName + f.getName()));     
         FileInputStream fis = new FileInputStream(f);     
         byte[] buffer = new byte[1024];
         int r = 0;     
         while ((r = fis.read(buffer)) != -1) {     
             zos.write(buffer, 0, r);     
         }     
         fis.close();  
        }  
   } 
    /**
     * 生成zip,生成之后删除临时文件
     * 
     * @param subs
     * @param baseName
     * @param zos
     * @throws Exception
     */
    private static void zipFileAndDel(List<File> subs, String baseName, ZipOutputStream zos) throws IOException {       
        for (int i=0;i<subs.size();i++) {  
         File f=subs.get(i);  
         zos.putNextEntry(new ZipEntry(baseName + f.getName()));     
         FileInputStream fis = new FileInputStream(f);     
         byte[] buffer = new byte[1024];
         int r = 0;     
         while ((r = fis.read(buffer)) != -1) {     
             zos.write(buffer, 0, r);     
         }     
         fis.close();  
         f.delete();
        }  
   }
    public static String createZipFile(List<File> fileList, String zipFileName,HttpServletResponse response) {
        logger.info("fileName...===================............."+zipFileName);
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition","attachment; filename="+zipFileName+".zip");
        if(fileList == null || fileList.size() == 0 || StringUtils.isEmpty(zipFileName)){
            return null;
        }
        //初期化ZIP流
        ZipOutputStream out = null;
        try{
            //构建ZIP流对象
            out = new ZipOutputStream(response.getOutputStream());
            //循环处理传过来的集合
            for(int i = 0 ; i< fileList.size(); i++){
                //获取目标文件
                File inFile = fileList.get(i);
                if(inFile.exists()){
                    //定义ZipEntry对象
                    ZipEntry entry = new ZipEntry(inFile.getName());
                    //赋予ZIP流对象属性
                    out.putNextEntry(entry);
                    int len = 0 ;
                    //缓冲
                    byte[] buffer = new byte[1024];
                    //构建FileInputStream流对象
                    FileInputStream fis;
                    fis = new FileInputStream(inFile);
                    while ((len = fis.read(buffer))< 0) {
                        out.write(buffer, 0, len);
                        out.flush();
                    }
                    //关闭closeEntry
                    out.closeEntry();
                    //关闭FileInputStream
                    fis.close();
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                //最后关闭ZIP流
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return zipFileName;

    }
}
