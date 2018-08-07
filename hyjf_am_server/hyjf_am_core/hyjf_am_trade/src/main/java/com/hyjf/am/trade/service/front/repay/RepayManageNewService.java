package com.hyjf.am.trade.service.front.repay;

import com.hyjf.am.trade.bean.repay.ProjectBean;
import com.hyjf.am.trade.bean.repay.RepayBean;
import com.hyjf.am.trade.dao.model.auto.Borrow;

import java.text.ParseException;

/**
 * @author hesy
 * @version RepayManageNewService, v0.1 2018/8/7 10:39
 */
public interface RepayManageNewService {
    ProjectBean searchRepayProjectDetail(ProjectBean form, boolean isAllRepay) throws Exception;

    RepayBean calculateRepay(int userId, Borrow borrow) throws ParseException;
}
