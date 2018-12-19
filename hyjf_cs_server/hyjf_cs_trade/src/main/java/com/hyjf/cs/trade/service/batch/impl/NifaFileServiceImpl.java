/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.batch.NifaFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yaoyong
 * @version NifaFileServiceImpl, v0.1 2018/12/18 16:47
 */
@Service
public class NifaFileServiceImpl implements NifaFileService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public void downloadFile() {
        amTradeClient.downloadFile();
    }

    @Override
    public void uploadFile() {
        amTradeClient.uploadFile();
    }
}
