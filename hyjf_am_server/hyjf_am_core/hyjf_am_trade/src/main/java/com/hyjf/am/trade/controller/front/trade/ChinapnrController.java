/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.trade;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.trade.ChinapnrExclusiveLogWithBLOBsResponse;
import com.hyjf.am.response.trade.ChinapnrLogResponse;
import com.hyjf.am.resquest.trade.ChinaPnrWithdrawRequest;
import com.hyjf.am.trade.dao.model.auto.ChinapnrExclusiveLogWithBLOBs;
import com.hyjf.am.trade.dao.model.auto.ChinapnrLog;
import com.hyjf.am.trade.service.front.trade.ChinapnrService;
import com.hyjf.am.vo.trade.ChinapnrExclusiveLogWithBLOBsVO;
import com.hyjf.am.vo.trade.ChinapnrLogVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangqingqing
 * @version ChinapnrController, v0.1 2018/9/8 10:08
 */
@RestController
@RequestMapping("/am-trade/chinapnr")
public class ChinapnrController {

    @Autowired
    ChinapnrService chinapnrService;
    /**
     * 取得检证数据
     * @param id
     * @return
     */
    @GetMapping("/selectChinapnrExclusiveLog/{id}")
    public ChinapnrExclusiveLogWithBLOBsResponse selectChinapnrExclusiveLog(@PathVariable long id) {
        ChinapnrExclusiveLogWithBLOBsResponse response = new ChinapnrExclusiveLogWithBLOBsResponse();
        ChinapnrExclusiveLogWithBLOBs chinapnr = chinapnrService.selectChinapnrExclusiveLog(id);
        if (null!=chinapnr) {
            ChinapnrExclusiveLogWithBLOBsVO chinapnrVO = new ChinapnrExclusiveLogWithBLOBsVO();
            BeanUtils.copyProperties(chinapnr,chinapnrVO);
            response.setResult(chinapnrVO);
        }
        return response;
    }

    /**
     * 更新状态
     * @param record
     * @return
     */
    @PostMapping("/updateChinapnrExclusiveLog")
    public IntegerResponse updateChinapnrExclusiveLog(@RequestBody ChinapnrExclusiveLogWithBLOBs record) {
        int cnt = chinapnrService.updateChinapnrExclusiveLog(record);
        return new IntegerResponse(cnt);
    }

    @GetMapping("/getChinapnrLog/{ordId}")
    public ChinapnrLogResponse getChinapnrLog(@PathVariable String ordId) {
        ChinapnrLogResponse response = new ChinapnrLogResponse();
        List<ChinapnrLog> chinapnrLogList = chinapnrService.getChinapnrLog(ordId);
        List<ChinapnrLogVO> chinapnrLogListVO = CommonUtils.convertBeanList(chinapnrLogList, ChinapnrLogVO.class);
        response.setResultList(chinapnrLogListVO);
        return response;
    }

    @PostMapping("/handlerAfterCash")
    public BooleanResponse handlerAfterCash(@RequestBody ChinaPnrWithdrawRequest chinaPnrWithdrawRequest) {
        boolean flag = chinapnrService.updateAfterCash(chinaPnrWithdrawRequest);
        return  new BooleanResponse(flag);
    }

    @GetMapping("/updateAccountWithdrawByOrdId/{ordId}/{reason}")
    public IntegerResponse updateAccountWithdrawByOrdId(@PathVariable String ordId,@PathVariable String reason) {
        int cnt = chinapnrService.updateAccountWithdrawByOrdId(ordId,reason);
        return new IntegerResponse(cnt);
    }

    @GetMapping("/updateChinapnrExclusiveLogStatus/{uuid}/{status}")
    public IntegerResponse updateChinapnrExclusiveLogStatus(@PathVariable long uuid,@PathVariable String status) {
        int cnt = chinapnrService.updateChinapnrExclusiveLogStatus(uuid,status);
        return new IntegerResponse(cnt);
    }
}
