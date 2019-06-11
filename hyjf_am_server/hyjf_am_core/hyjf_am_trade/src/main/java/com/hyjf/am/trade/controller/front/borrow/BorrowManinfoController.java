package com.hyjf.am.trade.controller.front.borrow;


import com.hyjf.am.response.trade.BorrowManinfoResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowManinfo;
import com.hyjf.am.trade.service.front.borrow.BorrowManinfoService;
import com.hyjf.am.vo.trade.borrow.BorrowManinfoVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author xiasq
 * @version BorrowManinfoController, v0.1 2018/6/24 10:48
 */
@RestController
@RequestMapping("/am-trade/borrow")
public class BorrowManinfoController extends BaseController {

    @Autowired
    private BorrowManinfoService borrowManinfoService;

    /**
     * 根据借款编号获取借款人信息
     * @author zhangyk
     * @date 2018/6/25 16:35
     */
    @GetMapping("/borrowManinfo/{borrowNid}")
    public BorrowManinfoResponse getborrowUser(@PathVariable  String borrowNid) {
        BorrowManinfoResponse response = new BorrowManinfoResponse();
        BorrowManinfo borrowManinfo = borrowManinfoService.getBorrowManinfoByNid(borrowNid);
        if (null != borrowManinfo) {
            BorrowManinfoVO borrowManinfoVO = CommonUtils.convertBean(borrowManinfo,BorrowManinfoVO.class);
            response.setResult(borrowManinfoVO);
        }
        return response;
    }

    /**
     * 根据标的编号获取借款人信息列表
     * @param nids
     * @return
     */
    @PostMapping("/borrowManinfoList")
    public BorrowManinfoResponse getBorrowManinfoList(@RequestBody List<String> nids) {
        BorrowManinfoResponse response = new BorrowManinfoResponse();
        List<BorrowManinfoVO> maninfoVOS = borrowManinfoService.getBorrowManinfoList(nids);
        if (!CollectionUtils.isEmpty(maninfoVOS)) {
            response.setResultList(maninfoVOS);
        }
        return response;
    }
}
