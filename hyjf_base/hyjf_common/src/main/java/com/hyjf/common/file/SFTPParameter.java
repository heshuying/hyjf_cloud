package com.hyjf.common.file;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SFTPParameter {
    public String hostName;

    public int port = 0;

    public String userName;

    public String passWord;

    public String sftpKeyFile ;

    public String downloadPath;

    public String savePath;

    public String uploadPath;
    public String fileName;

    public static int CON_FTP_TRYCOUNT_DEFAULT = 3;

    public static int CON_FTP_TRYINTERVAL_DEFAULT = 1;

    public int conFTPTryCount = 0;

    public int conFTPTryInterval = 1;

    public ChannelSftp sftp = null ;

    public Boolean isConnected = false;

    public int checkInterval() {
        if (0 == this.conFTPTryInterval)
            return CON_FTP_TRYINTERVAL_DEFAULT * 1000;
        else
            return this.conFTPTryInterval * 1000;
    }

    public int checkCount() {
        if (this.conFTPTryCount <= 0)
            return CON_FTP_TRYCOUNT_DEFAULT;
        else
            return this.conFTPTryCount;
    }

    public void release() throws JSchException {
        if (sftp != null) {
            Session session = sftp.getSession();
            sftp.disconnect();
            if (session != null) {
                session.disconnect();
            }
        }
        isConnected = false;
    }
}
