/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.market.service;

import com.hyjf.data.bean.BusReceiverEntity;
import org.springframework.stereotype.Service;

/**
 * @author zhangqingqing
 * @version BusReceiverService, v0.1 2019/6/19 14:28
 */
@Service
public interface BusReceiverService {

    abstract void createTable();

    abstract void loadData(String pathFile);

    void insert(BusReceiverEntity busReceiverEntity);

    abstract void deleteAll();

    void show();
}
