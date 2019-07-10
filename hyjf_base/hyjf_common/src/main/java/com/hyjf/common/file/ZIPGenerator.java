package com.hyjf.common.file;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            zos = new ZipOutputStream(os);
            byte[] buf = new byte[8192];
            int len;
            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                if (!file.isFile()) {
                    continue;
                }
                ZipEntry ze = new ZipEntry(file.getName());
                zos.putNextEntry(ze);
                bis = new BufferedInputStream(new FileInputStream(file));
                while ((len = bis.read(buf)) > 0) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
            }
            zos.closeEntry();
            zos.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            if (bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    logger.error("io流关闭错误");
                }
            }
            if (zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    logger.error("io流关闭错误");
                }
            }
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("io流关闭错误");
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
}
