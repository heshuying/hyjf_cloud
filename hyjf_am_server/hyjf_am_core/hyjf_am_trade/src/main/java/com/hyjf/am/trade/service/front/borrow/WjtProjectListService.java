/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.borrow;

import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowCredit;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestInvest;
import com.hyjf.am.trade.dao.model.customize.*;
import com.hyjf.am.vo.admin.AppPushManageVO;
import com.hyjf.am.vo.api.ApiProjectListCustomize;
import com.hyjf.am.vo.trade.AppProjectListCustomizeVO;
import com.hyjf.am.vo.trade.CreditListVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.WechatHomeProjectListVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


/**
 * Web端项目列表Service
 * @author pcc
 * @version ProjectListService, v0.1 2018/6/13 11:37
 */
public interface WjtProjectListService {


    /**
     * Web端获取项目列表
     * @param request
     * @return
     */
    List<WebProjectListCustomize> searchWjtWebProjectList(@Valid ProjectListRequest request);

    /**
     * Web端获取项目列表件数
     * @param request
     * @return
     */
    int countWjtWebProjectList(@Valid ProjectListRequest request);

    List<WechatHomeProjectListVO> searchWjtWechatProjectList(Map<String,Object> map);
}
