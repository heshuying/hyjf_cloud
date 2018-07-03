/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.BorrowRegistService;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.common.util.CustomConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowRegistController, v0.1 2018/6/29 11:18
 */

@Api(value = "汇直投-标的备案接口")
@RestController
@RequestMapping("/hyjf-admin/borrow_regist")
public class BorrowRegistController extends BaseController {
    @Autowired
    BorrowRegistService borrowRegistService;

    @Autowired
    AdminCommonService adminCommonService;

    @ApiOperation(value = "标的备案初始化",notes = "标的备案初始化")
    @PostMapping("/init")
    @ResponseBody
    public JSONObject init(HttpServletRequest request){
        //项目类型
        List<BorrowProjectTypeVO> borrowProjectTypeList = borrowRegistService.selectBorrowProjectList();

        //还款方式
        List<BorrowStyleVO> borrowStyleList = adminCommonService.selectBorrowStyleList();

        //备案状态
        Map<String, String> borrowRegistStatusList = adminCommonService.getParamNameMap(CustomConstants.REGIST_STATUS);

        JSONObject jsonObject = this.success();
        jsonObject.put("borrowProjectTypeList", borrowProjectTypeList);
        jsonObject.put("borrowStyleList", borrowStyleList);
        jsonObject.put("borrowRegistStatusList", borrowRegistStatusList);
        return jsonObject;
    }

    @ApiOperation(value = "获取标的备案列表",notes = "获取标的备案列表")
    @PostMapping("/get_regist_list")
    @ResponseBody
    public JSONObject getRegistList(HttpServletRequest request,@ApiParam(name = "borrowRegistListRequest", value = "查询条件")
    @RequestBody BorrowRegistListRequest borrowRegistListRequest){
        JSONObject jsonObject = null;
        Integer count = borrowRegistService.getRegistCount(borrowRegistListRequest);
        if(count > 0){
            List <BorrowRegistCustomizeVO> list = borrowRegistService.selectBorrowRegistList(borrowRegistListRequest);
            jsonObject = this.success(String.valueOf(count),list);
        }else {
            jsonObject = this.fail("未查询到相应数据！");
        }
        return jsonObject;
    }

    @ApiOperation(value = "标的备案",notes = "标的备案")
    @GetMapping("/debt_regist/{borrowNid}")
    @ResponseBody
    public JSONObject init(@RequestHeader(value = "token", required = false) String token,@PathVariable String borrowNid){
        JSONObject jsonObject = null;
        //用户登录问题
//        if(StringUtils.isNotBlank(token)){
//            WebViewUserVO user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS + token, WebViewUserVO.class);
//        }else {
//            jsonObject = this.fail("用户未登录！");
//            return jsonObject;
//        }

        if(StringUtils.isNotBlank(borrowNid)){
            return borrowRegistService.updateBorrowRegist(borrowNid);
        }else {
            jsonObject = this.fail("借款识别名为空！");
            return jsonObject;
        }
    }
}
