/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.borrow;

import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.resquest.admin.BorrowRegistUpdateRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.customize.BorrowRegistCustomize;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.admin.BorrowDeleteConfirmCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRegistCancelConfirmCustomizeVO;

import java.util.List;

/**
 * 标的删除
 * @author hesy
 */
public interface BorrowDeleteService extends BaseService {

    BorrowDeleteConfirmCustomizeVO selectDeleteConfirm(String borrowNid);

    Response deleteBorrow(BorrowRegistUpdateRequest requestBean);
}
