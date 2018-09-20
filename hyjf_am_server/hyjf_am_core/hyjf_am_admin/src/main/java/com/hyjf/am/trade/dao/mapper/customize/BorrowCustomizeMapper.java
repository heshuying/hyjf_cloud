package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.BorrowCustomize;
import com.hyjf.am.vo.admin.BorrowCustomizeVO;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.task.issuerecover.BorrowWithBLOBs;
import com.hyjf.am.vo.trade.ProjectCompanyDetailVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.WebProjectPersonDetailVO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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
    /**
     * 借款预编码
     *
     * @param borrowCustomize
     * @return
     */
    String getBorrowPreNid(@Param("mmdd") String mmdd);

    /**
     * 现金贷获取借款预编号
     * @param mmdd
     * @return
     */
    String getXJDBorrowPreNid(@Param("mmdd") String mmdd);
    /**
     * 获取借款列表
     *
     * @param alllBorrowCustomize
     * @return
     */
    List<BorrowCustomizeVO> selectBorrowList(BorrowCommonCustomizeVO borrowCommonCustomizeVO);
    /**
     * COUNT
     *
     * @param borrowCustomize
     * @return
     */
    Long countBorrow(BorrowCommonCustomizeVO borrowCommonCustomizeVO);

    /**
     * 清算日前一天，查询处于投资中或者复审中的原始标的
     * @author zhangyk
     * @date 2018/8/20 16:29
     */
    List<BorrowCustomizeVO> selectUnDealBorrowBeforeLiquidate();


    List<BorrowCommonCustomizeVO> exportBorrowList(BorrowCommonCustomizeVO BorrowCommonCustomizeVO);
}
