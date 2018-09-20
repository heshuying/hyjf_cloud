package com.hyjf.cs.message.controller.client;

import com.hyjf.am.vo.admin.ContentHelpCustomizeVO;
import com.hyjf.am.vo.admin.ContentHelpVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.message.service.wisdomtooth.WisdomToothService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * WisdomToothController智齿客服
 * </p>
 *
 * @author tyy
 * @version 1.0.0
 */
@Api(tags = "智齿客服",description = "WEB智齿客服")
@RestController
@RequestMapping("/hyjf-web/wisdomtooth")
public class WisdomToothController {

    Logger _log = LoggerFactory.getLogger(WisdomToothController.class);
    @Autowired
    private WisdomToothService wisdomToothService;

    @ApiOperation(value = "智齿客服常见问题列表", notes = "智齿客服常见问题列表")
    @GetMapping("/contenthelps")
    public WebResult<Object> contentHelps() {
        WebResult result = new WebResult();
        List<ContentHelpCustomizeVO> voList =  wisdomToothService.queryContentCustomize();
        result.setData(voList);
        return result;

    }


    @ApiOperation(value = "智齿客服常见问题答案", notes = "智齿客服常见问题答案")
    @GetMapping("/help/{id}")
    public WebResult<Object> contentHelps(@PathVariable Integer id) {
        WebResult result = new WebResult();
        ContentHelpVO vo =  wisdomToothService.queryContentById(id);
        result.setData(vo);
        return result;

    }

}
