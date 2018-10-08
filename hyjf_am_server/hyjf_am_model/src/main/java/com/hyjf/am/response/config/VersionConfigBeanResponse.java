package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.VersionVO;

import java.util.Map;

/**
 * @author lisheng
 * @version VersionConfigBeanRequest, v0.1 2018/7/13 18:01
 */

public class VersionConfigBeanResponse extends Response<VersionVO> {
    private int count;


    private Map<String, String>  versionName;
    private Map<String, String> isUpdate;
    private Map<String, String> client;

    public Map<String, String> getVersionName() {
        return versionName;
    }

    public void setVersionName(Map<String, String> versionName) {
        this.versionName = versionName;
    }

    public Map<String, String> getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Map<String, String> isUpdate) {
        this.isUpdate = isUpdate;
    }

    public Map<String, String> getClient() {
        return client;
    }

    public void setClient(Map<String, String> client) {
        this.client = client;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
