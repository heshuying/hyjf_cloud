/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.pointsshop.duiba.order.impl;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.pointsshop.duiba.order.DuibaOrderListService;
import com.hyjf.am.response.admin.DuibaOrderResponse;
import com.hyjf.am.resquest.admin.DuibaOrderRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WENXIN
 * @version DuibaOrderListServiceImpl, v0.1 2019/5/29 9:48
 */
@Service
public class DuibaOrderListServiceImpl extends BaseServiceImpl implements DuibaOrderListService {

    @Autowired
    AmAdminClient adminClient;

    /**
     * 初始化订单页面信息
     *
     * @param duibaOrderRequest
     * @return
     */
    @Override
    public DuibaOrderResponse findOrderList(DuibaOrderRequest duibaOrderRequest){
        return adminClient.findOrderList(duibaOrderRequest);
    }

    /**
     * map转ParamNameVO
     *
     * @param map
     * @return
     */
    @Override
    public List<ParamNameVO> mapToParamNameVO(Map<String, String> map) {
        List<ParamNameVO> paramNameVOList = new ArrayList<>();
        //遍历map中的键
        for (String key : map.keySet()) {
            ParamNameVO paramNameVO = new ParamNameVO();
            paramNameVO.setNameCd(key);
            paramNameVO.setName(StringUtils.isNotBlank(map.get(key)) ? map.get(key) : "" );
            paramNameVOList.add(paramNameVO);
        }
        return paramNameVOList;
    }
}
