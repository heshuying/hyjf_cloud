package com.hyjf.am.trade.dao.mapper.customize.trade;

import com.hyjf.am.trade.dao.model.customize.trade.BorrowCustomize;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.task.issuerecover.BorrowWithBLOBs;
import com.hyjf.am.vo.trade.ProjectCompanyDetailVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.WebProjectPersonDetailVO;

import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version BorrowCustomizeMapper, v0.1 2018/6/19 16:20
 */
public interface BorrowCustomizeMapper {
    int countInvest(Integer userId);

    /**
     * @param borrow
     * @return
     */
    int updateOfBorrow(Map<String, Object> borrow);

    /**
     * @param borrow
     * @return
     */
    int updateOfFullBorrow(Map<String, Object> borrow);


    ProjectCustomeDetailVO getProjectDetail(String borrowNid);


    ProjectCompanyDetailVO getProjectCompanyDetail(String borrowNid);


    WebProjectPersonDetailVO  getProjectPsersonDetail(String borrowNid);

    Integer getTotalInverestCount(Integer userId);

    /**
     * 集成borrow、 boorow_info表的自定义查询
     * @param borrowNid
     * @return
     */
    BorrowCustomize getBorrowCustomize(String borrowNid);

    /**
     * @Author walter.limeng
     * @Description
     * @Date 9:41 2018/7/13
     * 手动录标的自动备案、初审的标
     * @return
     */
    List<BorrowWithBLOBs> selectAutoBorrowNidList();

    /**
     * @Author walter.limeng
     * @Description  查询符合条件的Borrow
     * @Date 9:41 2018/7/13
     * @Param
     * @return
     */
    List<BorrowCommonCustomizeVO> searchNotFullBorrowMsg();

    /**
     * @Author walter.limeng
     * @Description  获取待复审项目借款列表add by liushouyi
     * @Date 11:12 2018/7/13
     * @Param
     * @return List<BorrowWithBLOBs>
     */
    List<BorrowWithBLOBs> selectAutoReviewBorrowNidList();

    /**
     * @Author walter.limeng
     * @Description  查询汇计划符合条件的Borrow
     * @Date 9:41 2018/7/13
     * @Param
     * @return
     */
    List<BorrowCommonCustomizeVO> searchHjhNotFullBorrowMsg();

    /**
     * @Author walter.limeng
     * @Description  获取待复审项目借款列表add by liushouyi
     * @Date 14:35 2018/7/13
     * @Param
     * @return 
     */
    List<BorrowWithBLOBs> selectAutoReviewHJHBorrowNidList();
}
