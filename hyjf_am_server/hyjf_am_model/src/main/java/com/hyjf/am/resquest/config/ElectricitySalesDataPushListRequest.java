/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.config;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.config.ElectricitySalesDataPushListVO;

import java.io.Serializable;
import java.util.List;

/**
 * 电销数据生成Request
 *
 * @author liuyang
 * @version ElectricitySalesDataPushListRequest, v0.1 2019/6/3 9:22
 */
public class ElectricitySalesDataPushListRequest extends BasePage implements Serializable {
    private static final long serialVersionUID = -5152611410927059511L;

    private List<ElectricitySalesDataPushListVO> electricitySalesDataPushList;

    public List<ElectricitySalesDataPushListVO> getElectricitySalesDataPushList() {
        return electricitySalesDataPushList;
    }

    public void setElectricitySalesDataPushList(List<ElectricitySalesDataPushListVO> electricitySalesDataPushList) {
        this.electricitySalesDataPushList = electricitySalesDataPushList;
    }
}
