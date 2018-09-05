package com.hyjf.am.trade.service.front.borrow;

import com.hyjf.am.resquest.admin.BorrowCreditRepayAmRequest;
import com.hyjf.am.trade.dao.model.customize.AdminBorrowCreditTenderCustomize;
import com.hyjf.am.vo.admin.BorrowCreditRepaySumVO;
import com.hyjf.am.vo.admin.BorrowCreditTenderVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayInfoVO;

import java.util.List;
import java.util.Map;

public interface BorrowCreditTenderService {

    /**
     * 债转 还款信息count
     * @author zhangyk
     * @date 2018/7/11 15:33
     */
    Integer countBorrowCreditRepay(BorrowCreditRepayAmRequest request);

    /**
     * 债转 还款信息list
     * @author zhangyk
     * @date 2018/7/11 15:34
     */
    List<AdminBorrowCreditTenderCustomize> searchBorrowCreditRepayList(BorrowCreditRepayAmRequest request);

    /**
     * 债转 还款信息合计行
     * @author zhangyk
     * @date 2018/7/11 19:43
     */
    BorrowCreditRepaySumVO sumBorrowCreditRepay(BorrowCreditRepayAmRequest request);

    /**
     * 查询汇转让还款明细count
     * @author zhangyk
     * @date 2018/7/12 14:25
     */
    Integer getCreditRepayInfoListCount(BorrowCreditRepayAmRequest request);


    /**
     * 查询汇转让还款明细list
     * @author zhangyk
     * @date 2018/7/12 14:26
     */
    List<BorrowCreditRepayInfoVO> getCreditRepayInfoList(BorrowCreditRepayAmRequest request);

    /**
     * 查询汇转让还款明细合计行
     * @author zhangyk
     * @date 2018/7/12 14:27
     */
    Map<String,Object> getCreditRepayInfoListSum(BorrowCreditRepayAmRequest request);


    /**
     * 查询承接列表count
     * @author zhangyk
     * @date 2018/7/12 20:13
     */
    Integer getCreditTenderCount(BorrowCreditRepayAmRequest request);

    /**
     * 查询承接列表list
     * @author zhangyk
     * @date 2018/7/12 20:13
     */
    List<BorrowCreditTenderVO> getCreditTenderList(BorrowCreditRepayAmRequest request);

    /**
     * 查询承接列表合计行
     * @author zhangyk
     * @date 2018/7/12 20:13
     */
    Map<String,Object> getCreditTenderSum(BorrowCreditRepayAmRequest request);

    /**
     * admin： 用户投资记录数
     * @author zhangyk
     * @date 2018/8/28 19:06
     */
    int getBorrowCreditTenderCount4Admin(Map<String, Object> params);

    /**
     * 根据creditNId查询服务费总计
     * @author zhangyk
     * @date 2018/8/30 11:11
     */
    String getCreditTenderServiceFee(String creditNid);


    /**
     * 根据债转编号和出让人id查询assignPay
     * @author zhangyk
     * @date 2018/9/4 10:37
     */
    String getCreditTenderAssignPay(Map<String,String> params);
}
