package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author by xiehuili on 2018/7/12.
 */
public class BorrowStyleRequestBean  extends BaseRequest implements Serializable {
    @ApiModelProperty(value = "算法公式")
    private String contents;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "nid")
    private String nid;
    @ApiModelProperty(value = "是否启用")
    private Integer status;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "名称，可改")
    private String title;

    private static final long serialVersionUID = 1L;
    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;

    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */
    private Paginator paginator;

    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
