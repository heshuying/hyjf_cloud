package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.customize.BorrowCustomize;
import com.hyjf.am.vo.admin.BorrowCustomizeVO;
import com.hyjf.am.vo.admin.WebProjectRepayListCustomizeVO;
import com.hyjf.am.vo.admin.WebUserInvestListCustomizeVO;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.task.issuerecover.BorrowWithBLOBs;
import com.hyjf.am.vo.trade.BorrowListVO;
import com.hyjf.am.vo.trade.ProjectCompanyDetailVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.WebProjectPersonDetailVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowUserVO;
import com.hyjf.am.vo.trade.borrow.DebtBorrowCustomizeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * 检索正在还款中的标的
     *
     * @return
     */
    List<BorrowAndInfoVO> selectBorrowRepayList();

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
	 * 清算日前一天，查询处于出借中或者复审中的原始标的
	 * @author zhangyk
	 * @date 2018/8/20 16:29
	 */
	List<BorrowCustomizeVO> selectUnDealBorrowBeforeLiquidate();
	List<BorrowCommonCustomizeVO> exportBorrowList(BorrowCommonCustomizeVO BorrowCommonCustomizeVO);

    List<BorrowCustomizeVO>  selectBorrowByNidList(BorrowCustomizeVO borrowCustomizeVO);

    List<BorrowListVO> getBorrowList(Map<String,Object> param);

    List<DebtBorrowCustomizeVO> getDebtBorrowList(Map<String,Object> param);

    List<WebProjectRepayListCustomizeVO> selectProjectLoanDetailList(Map<String,Object> param);

    List<WebUserInvestListCustomizeVO> selectUserDebtInvestList(Map<String,Object> param);

    int planInfoCountProjectRepayPlanRecordTotal(Map<String,Object> param);

    int myTenderCountProjectRepayPlanRecordTotal(Map<String,Object> param);

    List<WebUserInvestListCustomizeVO> selectUserInvestList(Map<String,Object> param);
    /**
     * 借款列表
     *
     * @return
     */
    List<BorrowCustomizeVO> searchBorrowList(BorrowCommonCustomizeVO BorrowCommonCustomizeVO);

    /**
     * 更新标的表还款金额
     * @param borrow
     * @return
     */
    int updateRepayOfBorrow(Borrow borrow);

    /**
     *批量查询借款用户信息
     * @param borrowNids
     * @return
     */
    List<BorrowUserVO> getBorrowUserList(@Param("set") List<String> borrowNids);
}
