/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin.screenData;

import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.trade.ScreenDataBean;

import java.util.List;

/**
 * @Author walter.limeng
 * @Description //
 * @Date 09:55 2019-04-11
 **/

public class ScreenDataResponse extends Response<ScreenDataBean>{

    private List<ScreenDataBean> screenDataBeanList;

    public List<ScreenDataBean> getScreenDataBeanList() {
        return screenDataBeanList;
    }

    public void setScreenDataBeanList(List<ScreenDataBean> screenDataBeanList) {
        this.screenDataBeanList = screenDataBeanList;
    }
}
