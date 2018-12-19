package com.hyjf.cs.trade.service.batch;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xiehuili on 2018/12/18.
 */
public interface PoundageService {

    /**
     *手续费分账明细插入定时
     * @return
     */
    public void poundage();
}
