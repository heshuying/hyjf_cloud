package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.ConfigApplicantVO;

/**
 * @author lisheng
 * @version ConfigApplicantResponse, v0.1 2019/2/21 11:17
 */

public class ConfigApplicantResponse extends Response<ConfigApplicantVO> {
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
