/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

/**
 * @author fuqiang
 * @version TenderSexCountVO, v0.1 2018/7/18 10:57
 */
public class TenderSexCountVO extends BaseVO {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public int sex;
    public int count;

    public int getSex() {
        return sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
