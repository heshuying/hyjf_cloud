/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.client;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.trade.ChinapnrExclusiveLogWithBLOBsResponse;
import com.hyjf.am.response.trade.ChinapnrLogResponse;
import com.hyjf.am.vo.trade.ChinapnrExclusiveLogWithBLOBsVO;
import com.hyjf.am.vo.trade.ChinapnrLogVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.message.bean.ic.ChinapnrExclusiveLog;
import com.hyjf.cs.message.bean.ic.ChinapnrLog;
import com.hyjf.cs.message.service.bank.ChinapnrService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangqingqing
 * @version ChinapnrController, v0.1 2019/1/10 16:09
 */
@RestController
@RequestMapping("/cs-message/chinapnr")
public class ChinapnrController {

    @Autowired
    ChinapnrService chinapnrService;


    /**
     * 取得检证数据
     * @param id
     * @return
     */
    @GetMapping("/selectChinapnrExclusiveLog/{id}")
    public ChinapnrExclusiveLogWithBLOBsResponse selectChinapnrExclusiveLog(@PathVariable String id) {
        ChinapnrExclusiveLogWithBLOBsResponse response = new ChinapnrExclusiveLogWithBLOBsResponse();
        ChinapnrExclusiveLog chinapnr = chinapnrService.queryById(id);
        if (null!=chinapnr) {

            ChinapnrExclusiveLogWithBLOBsVO chinapnrVO = new ChinapnrExclusiveLogWithBLOBsVO();
            BeanUtils.copyProperties(chinapnr,chinapnrVO);
            response.setResult(chinapnrVO);
        }
        return response;
    }

    @GetMapping("/updateChinapnrExclusiveLogStatus/{uuid}/{status}")
    public IntegerResponse updateChinapnrExclusiveLogStatus(@PathVariable String uuid, @PathVariable String status) {
        chinapnrService.updateChinapnrExclusiveLogStatus(uuid,status);
        return new IntegerResponse(1);
    }

    /**
     * 更新状态
     * @param record
     * @return
     */
    @PostMapping("/updateChinapnrExclusiveLog")
    public IntegerResponse updateChinapnrExclusiveLog(@RequestBody ChinapnrExclusiveLogWithBLOBsVO record) {
        chinapnrService.updateChinapnrExclusiveLog(record);
        return new IntegerResponse(1);
    }

    @GetMapping("/getChinapnrLog/{ordId}")
    public ChinapnrLogResponse getChinapnrLog(@PathVariable String ordId) {
        ChinapnrLogResponse response = new ChinapnrLogResponse();
        List<ChinapnrLog> chinapnrLogList = chinapnrService.getChinapnrLog(ordId);
        List<ChinapnrLogVO> chinapnrLogListVO = CommonUtils.convertBeanList(chinapnrLogList, ChinapnrLogVO.class);
        response.setResultList(chinapnrLogListVO);
        return response;
    }
}
