package com.hyjf.cs.trade.service;

import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.resquest.trade.RepayRequest;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.cs.trade.bean.WebViewUser;
import com.hyjf.cs.trade.bean.repay.ProjectBean;
import com.hyjf.cs.trade.bean.repay.RepayBean;

import java.text.ParseException;
import java.util.List;

/**
 * @author hesy
 * @version RepayManageService, v0.1 2018/6/23 18:02
 */
public interface RepayManageService extends BaseTradeService{
    void checkForRepayList(RepayListRequest requestBean);

    List<RepayListCustomizeVO> selectRepayList(RepayListRequest requestBean);

    List<RepayListCustomizeVO> selectOrgRepayList(RepayListRequest requestBean);

    List<RepayListCustomizeVO> selectOrgRepayedList(RepayListRequest requestBean);

    Integer selectRepayCount(RepayListRequest requestBean);

    Integer selectOrgRepayCount(RepayListRequest requestBean);

    Integer selectOrgRepayedCount(RepayListRequest requestBean);

    ProjectBean searchRepayProjectDetail(ProjectBean form) throws NumberFormatException, ParseException;

    RepayBean checkForRepayRequest(RepayRequest requestBean, WebViewUser user);
}
