/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.borrow;

import com.hyjf.am.response.trade.BorrowStyleResponse;
import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.service.admin.borrow.AdminCommonService;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangjun
 * @version AdminCommonController, v0.1 2018/8/16 9:33
 */
@RestController
@RequestMapping("/am-trade/admin_common")
public class AdminCommonController {
    @Autowired
    AdminCommonService adminCommonService;

    /**
     * 还款方式下拉列表
     *
     * @return
     */
    @RequestMapping("/select_borrow_style")
    public BorrowStyleResponse selectBorrowStyleList() {
        BorrowStyleResponse response = new BorrowStyleResponse();
        List<BorrowStyle> borrowStyleList = adminCommonService.selectBorrowStyleList();
        if (!CollectionUtils.isEmpty(borrowStyleList)) {
            List<BorrowStyleVO> voList = CommonUtils.convertBeanList(borrowStyleList, BorrowStyleVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 资产来源下拉列表
     *
     * @return
     */
    @GetMapping("/select_inst_config")
    public HjhInstConfigResponse selectInstConfigList() {
        HjhInstConfigResponse response = new HjhInstConfigResponse();
        List<HjhInstConfig> hjhInstConfigList = adminCommonService.selectInstConfigList();
        if (!CollectionUtils.isEmpty(hjhInstConfigList)) {
            List<HjhInstConfigVO> voList = CommonUtils.convertBeanList(hjhInstConfigList, HjhInstConfigVO.class);
            response.setResultList(voList);
        }
        return response;
    }

}
