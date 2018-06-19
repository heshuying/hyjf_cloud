/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BaseVO;

import java.util.List;

/**
 * @author zhangqingqing
 * @version AnswerRequest, v0.1 2018/6/17 23:05
 */
public class AnswerRequest extends BaseVO {
    private List<String> resultList;

    public List<String> getResultList() {
        return resultList;
    }

    public void setResultList(List<String> resultList) {
        this.resultList = resultList;
    }
}
