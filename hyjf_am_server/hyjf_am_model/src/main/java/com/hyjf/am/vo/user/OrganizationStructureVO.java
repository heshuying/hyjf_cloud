/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author: sunpeikai
 * @version: OrganizationStructureVO, v0.1 2018/6/27 11:06
 * 集团组织结构
 */
public class OrganizationStructureVO extends BaseVO implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父节点ID
     */
    private Integer parentid;

    /**
     * 部门提成发放方式（1线上2线下）
     */
    private Integer cuttype;

    /**
     * 删除标记 0：已删除 1：未删除
     */
    private Integer flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getCuttype() {
        return cuttype;
    }

    public void setCuttype(Integer cuttype) {
        this.cuttype = cuttype;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
