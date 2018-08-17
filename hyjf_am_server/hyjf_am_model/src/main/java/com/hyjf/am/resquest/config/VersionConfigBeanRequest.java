package com.hyjf.am.resquest.config;

import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.vo.config.VersionVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author lisheng
 * @version VersionConfigBeanRequest, v0.1 2018/7/13 18:01
 */

public class VersionConfigBeanRequest extends VersionVO implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 387630498860089653L;
    @ApiModelProperty(value = "删除数据的id")
    private String ids;
    @ApiModelProperty(value = "平台类型 0 PC ,1 Android , 2 IOS , 3 wechat")
    private String nameSrh;
    @ApiModelProperty(value = "版本号")
    private String versionSrh;
    @ApiModelProperty(value = "当前页码")
    private int currPage;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getNameSrh() {
        return nameSrh;
    }

    public void setNameSrh(String nameSrh) {
        this.nameSrh = nameSrh;
    }

    public String getVersionSrh() {
        return versionSrh;
    }

    public void setVersionSrh(String versionSrh) {
        this.versionSrh = versionSrh;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }
}
