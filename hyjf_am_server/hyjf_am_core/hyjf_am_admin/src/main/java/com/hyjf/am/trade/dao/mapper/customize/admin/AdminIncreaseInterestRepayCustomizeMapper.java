package com.hyjf.am.trade.dao.mapper.customize.admin;

import com.hyjf.am.vo.admin.AdminIncreaseInterestRepayCustomizeVO;

import java.util.List;
import java.util.Map;

public interface AdminIncreaseInterestRepayCustomizeMapper {

    /**
     * 融通宝加息还款明细件数
     *
     * @Title countRecordList
     * @param param
     * @return
     */
    public int countRecordList(Map<String, Object> param);

    /**
     * 融通宝加息还款明细列表
     *
     * @Title selectBorrowRepaymentInfoList
     * @param param
     * @return
     */
    public List<AdminIncreaseInterestRepayCustomizeVO> selectBorrowRepaymentInfoList(Map<String, Object> param);

    /**
     * 融通宝加息还款明细详情
     *
     * @Title countBorrowRepaymentInfoList
     * @param param
     * @return
     */
    public int countBorrowRepaymentInfoList(Map<String, Object> param);

    /**
     * 融通宝加息还款明细详情列表
     *
     * @Title selectBorrowRepaymentInfoListList
     * @param param
     * @return
     */
    public List<AdminIncreaseInterestRepayCustomizeVO> selectBorrowRepaymentInfoListList(Map<String, Object> param);

    /**
     * 取得应还本金和应还加息收益合计
     *
     * @Title sumBorrowLoanmentInfo
     * @param param
     * @return
     */
    public AdminIncreaseInterestRepayCustomizeVO sumBorrowLoanmentInfo(Map<String, Object> param);


    /**
     * 取得应还本金和应还加息收益合计
     *
     * @Title sumBorrowRepaymentInfo
     * @param param
     * @return
     */
    public AdminIncreaseInterestRepayCustomizeVO sumBorrowRepaymentInfo(Map<String, Object> param);

}
