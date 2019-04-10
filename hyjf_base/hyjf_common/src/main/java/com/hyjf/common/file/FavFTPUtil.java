package com.hyjf.common.file;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * ftp上传下载工具类
 * <p>Title: FtpUtil</p>
 * <p>Description: </p>
 * <p>Company: www.miaogao.com</p>
 * @author zdj
 * @version 1.0
 * @date 2018年3月1日下午8:11:51
 */
public class FavFTPUtil {

    private static final Logger logger = LoggerFactory.getLogger(FavFTPUtil.class);
    /**
     * Description: 向FTP服务器上传文件
     *
     * @param host     FTP服务器hostname
     * @param port     FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param basePath FTP服务器基础目录
     * @param filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
     * @param filename 上传到FTP服务器上的文件名
     * @param input    输入流
     * @return 成功返回true，否则返回false
     */
    public static  boolean uploadFile(String host, int port, String username, String password, String basePath,
                                     String filePath, String filename, InputStream input) {
        boolean result = false;
        FTPClient ftp = new FTPClient();

        try {
            int reply;
            ftp.connect(host, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            ftp.enterLocalPassiveMode();
            //System.out.println(ftp.printWorkingDirectory()+"切换到上传目录:" + (!ftp.changeWorkingDirectory("/usr/local/shop_image_server/filebase" + filePath)));
            //System.out.println("basePath + filePath="+basePath + filePath);
            //切换到上传目录
            if (!ftp.changeWorkingDirectory(basePath + filePath)) {
                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = basePath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) {
                        continue;
                    }
                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        if (!ftp.makeDirectory(tempPath)) {
                            return result;
                        } else {
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            //System.out.println(ftp.printWorkingDirectory() + "=切换到上传目录:");
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.IMAGE_FILE_TYPE);
            // ftp.storefile 无法写入问题对应，
            // 另外注意事项：
            // 1，外网的8081端口和21端口对应同一台服务器。
            // 2 服务器的设置
            //  /etc/selinux/config 将SELINUX=enforcing 改成SELINUX=disabled，然后重启
            //  不重启就可以生效（重启后会失效）：setsebool -P ftpd_disable_trans=1
            //  查看SELINUX状态的命令：sestatus
            // //设置被动模式传输
            //ftp.enterLocalPassiveMode();
            //上传文件
            if (!ftp.storeFile(filename,input)) {
                return result;
            }
            input.close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            logger.error("上传文件失败，上传路径：" + filePath,e);
            logger.error(e.getMessage());
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {logger.error(e.getMessage());
                }
            }
        }
        return result;
    }

    /**
     * Description: 从FTP服务器下载文件
     *
     * @param host       FTP服务器hostname
     * @param port       FTP服务器端口
     * @param username   FTP登录账号
     * @param password   FTP登录密码
     * @param remotePath FTP服务器上的相对路径
     * @param fileName   要下载的文件名
     * @param localPath  下载后保存到本地的路径
     * @return
     */
    public static boolean downloadFile(String host, int port, String username, String password, String remotePath,
                                       String fileName, String localPath) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            ftp.enterLocalPassiveMode();
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            FTPFile[] fs = ftp.listFiles();
            System.out.println(fs.length);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + "/" + ff.getName());
                    if (localFile.exists()) {
                        localFile.delete();
                    } else {
                        localFile.getParentFile().mkdirs();
                    }

                    try (OutputStream is = new FileOutputStream(localFile)) {

                        ftp.retrieveFile(ff.getName(), is);
                        is.close();
                    }
                }
            }

            ftp.logout();
            result = true;
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }
    
    /**
     * Description: 从FTP服务器下载文件夹
     *
     * @param host       FTP服务器hostname
     * @param port       FTP服务器端口
     * @param username   FTP登录账号
     * @param password   FTP登录密码
     * @param remotePath FTP服务器上的相对路径
     * @param fileName   要下载的文件名
     * @param localPath  下载后保存到本地的路径
     * @return
     */
    public static File downloadDirectory(HttpServletRequest request,
        HttpServletResponse response,String host, int port, String username, String password, String remotePath,
                                       String fileName,String localPath) {
        File localFile = null;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return null;
            }
            ftp.enterLocalPassiveMode();
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            FTPFile[] fs = ftp.listFiles();
            System.out.println(fs.length);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                
                    localFile = new File(localPath + "/" +ff.getName());
                    if (localFile.exists()) {
                        localFile.delete();
                    } else {
                        localFile.getParentFile().mkdirs();
                    }
                    try (OutputStream is = new FileOutputStream(localFile)) {

                        ftp.retrieveFile(ff.getName(), is);
                        is.close();
                    }
                }
            }
            //ZIPGenerator.generateZip(response, files, fileName);
            ftp.logout();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return localFile;
    }
    /**
     * Description: 从FTP服务器下载文件夹
     *
     * host       FTP服务器hostname
     *  port       FTP服务器端口
     *  username   FTP登录账号
     *  password   FTP登录密码
     *  remotePath FTP服务器上的相对路径
     *  fileName   要下载的文件名
     *  localPath  下载后保存到本地的路径
     * @return
     */
    public static File downloadDirectory(SFTPParameter para) {
        logger.info("----------------------------开始下载FTP协议："+JSONObject.toJSONString(para));
        File localFile = null;
        FTPClient ftp = new FTPClient();
        OutputStream is = null;
        try {
            int reply;
            ftp.connect(para.hostName, para.port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(para.userName, para.passWord);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return null;
            }
            ftp.enterLocalPassiveMode();
            ftp.changeWorkingDirectory(para.downloadPath);// 转移到FTP服务器目录
            FTPFile[] fs = ftp.listFiles();
            System.out.println(fs.length);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            for (FTPFile ff : fs) {
                if (ff.getName().equals(para.sftpKeyFile)) {

                    localFile = new File(para.savePath + "/" +para.fileName+"_"+ff.getName());
                    if (localFile.exists()) {
                        localFile.delete();
                    } else {
                        localFile.getParentFile().mkdirs();
                    }
                        is= new FileOutputStream(localFile);
                        ftp.retrieveFile(ff.getName(), is);
                        is.close();
                }
            }
            ftp.logout();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    logger.error("关闭输出流：" + ioe);
                }
            }
            if (is != null) {
                try {
                    is.flush();
                    is.close();
                } catch (Exception e) {
                    logger.error("关闭输出流：" + e);
                }
            }
        }
        logger.info("----------------------------结束下载FTP协议localFile："+localFile);
        return localFile;
    }

    /**
     * Description: 从FTP服务器下载文件(上传与下载文件名一致)
     *
     * @param host       FTP服务器hostname
     * @param port       FTP服务器端口
     * @param username   FTP登录账号
     * @param password   FTP登录密码
     * @param remotePath FTP服务器上的相对路径
     * @param fileName   要下载的文件名
     * @param localPath  下载后保存到本地的路径
     * @return
     */
    public static boolean downloadFileFront(String host, int port, String username, String password, String remotePath,
                                       String fileName, String localPath,String dbFileName) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            FTPFile[] fs = ftp.listFiles(ftp.printWorkingDirectory());
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + "/" + dbFileName);
                    if (localFile.exists()) {
                        localFile.delete();
                    } else {
                        localFile.getParentFile().mkdirs();
                    }

                    try (OutputStream is = new FileOutputStream(localFile)) {

                        ftp.retrieveFile(ff.getName(), is);
                        is.close();
                    }
                }
            }

            ftp.logout();
            result = true;
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

    /**
     * <删除FTP上的文件>
     * <远程删除FTP服务器上的录音文件>
     *
     * @param url        FTP服务器IP地址
     * @param port       FTP服务器端口
     * @param username   FTP服务器登录名
     * @param password   FTP服务器密码
     * @param remotePath 远程文件路径
     * @param fileName   待删除的文件名
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean deleteFtpFile(String url, int port, String username, String password, String remotePath,
                                        String fileName) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;

            // 连接FTP服务器
            if (port > -1) {
                ftp.connect(url, port);
            } else {
                ftp.connect(url);
            }

            // 登录
            ftp.login(username, password);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }

            // 转移到FTP服务器目录
            ftp.changeWorkingDirectory(remotePath);
            success = ftp.deleteFile(remotePath + "/" + fileName);
            ftp.logout();
        } catch (IOException e) {
            logger.error(e.getMessage());
            success = false;
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return success;
    }

    public static FTPFile[] searchFTPDicFiles(String host, int port, String username, String password, String remotePath){
        FTPClient ftp = new FTPClient();
        FTPFile[] fs = null;
        try {
            int reply;
            ftp.connect(host, port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return null;
            }
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            fs = ftp.listFiles();
            ftp.logout();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return fs;
    }
    /**
     * 文件转成 byte[] <一句话功能简述> <功能详细描述>
     *
     * @param inStream
     * @return
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
   public static byte[] input2byte(InputStream inStream) throws IOException {
       ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
       byte[] buff = new byte[inStream.available()];
       int rc = 0;
       while ((rc = inStream.read(buff, 0, 100)) > 0) {
           swapStream.write(buff, 0, rc);
       }
       byte[] in2b = swapStream.toByteArray();
       swapStream.close();
       return in2b;
   }
   /* public static void main(String[] args) throws FileNotFoundException {
        FileInputStream in = new FileInputStream(new File("D:\\test\\333.png"));  
        //boolean flag = FavFTPUtil.uploadFile("39.106.9.158", 21, "developer", "hyjf2018", "/hyjfdata/upfiles/contract/img", "/2017/01/16", "hello.png", in);
        boolean flag = downloadFile("39.106.9.158", 21, "developer", "hyjf2018", "/hyjfdata/upfiles/contract/img/", "", "D:\\test");
               
            System.out.println(flag);
    }*/
}
