package com.hyjf.am.resquest.admin.vip.content;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * Created by future on 2019/3/19.
 */
public class ScreenConfigRequest extends BasePage implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 任务时间,精确到月  yyyy-mm
     *
     * @mbggenerated
     */
    private String taskTime;
}
