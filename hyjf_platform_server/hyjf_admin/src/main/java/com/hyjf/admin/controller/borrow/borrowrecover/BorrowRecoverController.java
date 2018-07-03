package com.hyjf.admin.controller.borrow.borrowrecover;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BorrowRecoverBean;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AssetListService;
import com.hyjf.admin.service.BorrowRecoverService;
import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.resquest.trade.TradeDetailBeanRequest;
import com.hyjf.am.vo.admin.BorrowRecoverCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.cache.CacheUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version BorrowRecoverController, v0.1 2018/7/2 10:13
 */
@Api(value = "产品中心-汇直投-放款明细")
@RestController
@RequestMapping("/hyjf-admin/borrowrecover")
public class BorrowRecoverController extends BaseController {
    @Autowired
    private BorrowRecoverService borrowRecoverService;

    /**
     * 画面初始化
     *
     * @param request
     * @return 标签配置列表
     */
    @ApiOperation(value = "放款明细", notes = "放款明细页面查询初始化")
    @PostMapping(value = "/searchAction")
    @ResponseBody
    public JSONObject searchAction(HttpServletRequest request, @RequestBody @Valid BorrowRecoverBean form) {
        JSONObject jsonObject = new JSONObject();
        Map<String, String> map=CacheUtil.getParamNameMap("LOAN_STATUS");
        jsonObject.put("loanStarusList", map);
        // 资金来源
        List<HjhInstConfigVO> hjhInstConfigList = this.borrowRecoverService.selectHjhInstConfigByInstCode("");
        jsonObject.put("hjhInstConfigList", hjhInstConfigList);

        borrowRecoverService.searchAction(jsonObject,form);
        return jsonObject;
    }

}
