/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 *
 * @author: lb
 * @version: 1.0
 * Created at: 2017年9月15日 上午9:43:49
 * Modification History:
 * Modified by :
 */

package com.hyjf.cs.trade.service.consumer.hgdatareport.bifa;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.bifa.BaseHgDataReportEntityVO;
import com.hyjf.am.vo.trade.bifa.BifaUserInfoSHA256EntityVO;
import com.hyjf.cs.common.service.BaseService;

/**
 * @author liubin
 */

public interface BaseHgDateReportService extends BaseService {

    /**
     * 调用webservice接口并返回数据
     *
     * @param methodName
     * @param encmsg
     * @return
     */
    String webService(String methodName, String encmsg);

    /**
     * 上报北互金
     * @param methodName
     * @param data
     * @param <T>
     * @return
     */
    <T extends BaseHgDataReportEntityVO> T reportData(String methodName, T data);

    /**
     * 获取用户索引信息
     * @return
     */
    BifaUserInfoSHA256EntityVO selectUserIdToSHA256(JSONObject jsonObject);
}

	