package com.hyjf.am.vo.trade.assetmanage;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Description
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */
public class QueryMyProjectVO<T> {

    private String await; //待收总额

    //当前持有/已回款/转让记录
    private List<T> lstProject;

    private boolean isEnd = false;

    public String getAwait() {
        return await;
    }

    public void setAwait(String await) {
        this.await = await;
    }

    public List<T> getLstProject() {
        if (lstProject == null) {
            lstProject = Lists.newArrayList();
        }
        return lstProject;
    }

    public void setLstProject(List<T> lstProject) {
        this.lstProject = lstProject;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
}
