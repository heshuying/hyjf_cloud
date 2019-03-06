package com.hyjf.cs.trade.controller.batch.fddpush;

import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.cs.trade.service.batch.FddPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * yangchangwei
 * add by 2018-12-25
 * 放款后推送法大大协议
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/batch")
public class FddPushController {

    private static final Logger _log = LoggerFactory.getLogger(FddPushController.class);


    @Autowired
    private FddPushService fddPushService;

    @RequestMapping("/fddPush")
    public String fddPush() {
        _log.info("------法大大放款推送定时任务开始------");
        try {
            List<BorrowApicronVO> list =  fddPushService.getFddPushBorrowList();
            if(list != null && list.size() > 0){
                for (int i = 0; i < list.size(); i++) {
                    BorrowApicronVO borrowApicron = list.get(i);
                    fddPushService.updateFddPush(borrowApicron);
                }
            }else {
                _log.info("--------法大大放款没有需要推送的标的---------");
            }
            _log.info("------法大大放款推送定时任务结束------");
        } catch (Exception e) {
            _log.error(e.getMessage());
        }
        return "success";
    }
}
