/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.batch;

import com.hyjf.am.response.trade.BatchUserPortraitQueryResponse;
import com.hyjf.am.resquest.user.BatchUserPortraitRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.front.batch.BatchUserPortraitQueryService;
import com.hyjf.am.vo.trade.BatchUserPortraitQueryVO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BatchUserPortraitQueryController, v0.1 2018/6/28 10:36
 */
@RestController
@RequestMapping(value = "/am-trade/batch")
public class BatchUserPortraitQueryController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BatchUserPortraitQueryService batchUserPortraitQueryService;

    /**
     * 查询用户画像所需要的投资相关参数
     * @param batchUserPortraitRequest 需要查询的userId的list
     * @return 返回与UserPortrait相同参数
     * */
    @PostMapping("/search_user_portrait_list")
    public BatchUserPortraitQueryResponse searchUserPortraitList(@RequestBody BatchUserPortraitRequest batchUserPortraitRequest){
        BatchUserPortraitQueryResponse response = new BatchUserPortraitQueryResponse();
        logger.info("员工画像.....searchUserPortraitList:::::::batchUserPortraitRequest===={}",batchUserPortraitRequest);

        List<BatchUserPortraitQueryVO> list = batchUserPortraitQueryService.selectInfoForUserPortrait(batchUserPortraitRequest);
        if(!CollectionUtils.isEmpty(list)){
            response.setResultList(list);
        }
        return response;
    }
}
