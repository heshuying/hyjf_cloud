/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.bean.admin;

/**
 * @author cui
 * @version LockedConfig, v0.1 2018/7/13 10:04
 */
public class LockedConfig {

    //前台登录失败配置
    private Config webConfig;

    //后台登录失败配置
    private Config adminConfig;

    public Config getWebConfig() {
        return webConfig;
    }

    public void setWebConfig(Config webConfig) {
        this.webConfig = webConfig;
    }

    public Config getAdminConfig() {
        return adminConfig;
    }

    public void setAdminConfig(Config adminConfig) {
        this.adminConfig = adminConfig;
    }

    public static class Config {
        //最大登录失败次数
        private Integer maxLoginErrorNum;
        //锁定时长
        private Integer lockLong;

        public Integer getMaxLoginErrorNum() {
            return maxLoginErrorNum;
        }

        public void setMaxLoginErrorNum(Integer maxLoginErrorNum) {
            this.maxLoginErrorNum = maxLoginErrorNum;
        }

        public Integer getLockLong() {
            return lockLong;
        }

        public void setLockLong(Integer lockLong) {
            this.lockLong = lockLong;
        }
    }

}
