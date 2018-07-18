/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.statistics.controller;

import com.hyjf.am.statistics.mongo.AccountWebListDao;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: sunpeikai
 * @version: MongoInsertController, v0.1 2018/7/18 10:43
 */
@RestController
@RequestMapping("/am-statistics/insert")
public class MongoInsertController{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountWebListDao accountWebListDao;

    @PostMapping(value = "/insertaccountweblist")
    public Integer insertAccountWebList(@RequestBody AccountWebListVO accountWebListVO){
        logger.info("接收到的参数为:[{}]",accountWebListVO.getOrdid());
        return accountWebListDao.insertAccountWebList(accountWebListVO);
    }
}
