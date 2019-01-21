/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.trade.service.batch.NifaFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author yaoyong
 * @version NifaFileController, v0.1 2018/12/18 16:43
 * 互金反馈文件
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/nifaFileDeal")
public class NifaFileController {

    @Autowired
    private NifaFileService nifaFileService;

    @RequestMapping("/downloadFile")
    public void downloadFile() {
        nifaFileService.downloadFile();
    }

    @RequestMapping("/uploadFile")
    public void uploadFile() {
        nifaFileService.uploadFile();
        }
}
