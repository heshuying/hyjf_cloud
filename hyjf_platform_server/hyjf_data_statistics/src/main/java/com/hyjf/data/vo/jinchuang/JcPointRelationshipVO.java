/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.vo.jinchuang;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version JcPointRelationshipVO, v0.1 2019/6/19 11:54
 */
public class JcPointRelationshipVO extends BaseVO implements Serializable {

    private Integer source; //用户上级行为节点

    private Integer target; //用户下级行为节点

    private Integer value;  //行为值

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
