package com.hyjf.am.response.market;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.market.ActivityListBeanVO;
import com.hyjf.am.vo.market.ActivityListVO;

import java.util.List;

/**
 * @author xiasq
 * @version ActivityListResponse, v0.1 2018/6/14 11:32
 */
public class ActivityListResponse extends Response<ActivityListVO> {
    private int count;

    private int flag;

    private String status;

    private String statusDesc;

    private List<ActivityListBeanVO> activityList;

    private String[] platforms;

    private String fileDomainUrl;

    private List<ParamNameVO> clients;

    public int getCount() {
        return count;
    }

    private String title;

    public void setCount(int count) {
        this.count = count;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public List<ActivityListBeanVO> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<ActivityListBeanVO> activityList) {
        this.activityList = activityList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String[] platforms) {
        this.platforms = platforms;
    }

    public String getFileDomainUrl() {
        return fileDomainUrl;
    }

    public void setFileDomainUrl(String fileDomainUrl) {
        this.fileDomainUrl = fileDomainUrl;
    }

    public List<ParamNameVO> getClients() {
        return clients;
    }

    public void setClients(List<ParamNameVO> clients) {
        this.clients = clients;
    }
}
