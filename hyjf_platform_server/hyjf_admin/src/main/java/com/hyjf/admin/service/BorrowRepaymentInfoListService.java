package com.hyjf.admin.service;

import com.hyjf.admin.beans.BorrowRepaymentInfoListBean;
import com.hyjf.am.resquest.admin.BorrowRepaymentInfoListRequset;
import com.hyjf.am.vo.admin.BorrowRepaymentInfoListCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoListService, v0.1 2018/7/10 10:22
 */
public interface BorrowRepaymentInfoListService {

    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date 获取资产来源配置列表
     */
    List<HjhInstConfigVO> selectHjhInstConfigByInstCode(String instCode);
    /**
     * @Description 
     * @Author pangchengchao
     * @Version v0.1
     * @Date 查询还款明细列表 
     */
    BorrowRepaymentInfoListBean selectBorrowRepaymentInfoListList(BorrowRepaymentInfoListRequset request);
    /**
     * @Description 
     * @Author pangchengchao
     * @Version v0.1
     * @Date 查询还款详情导出数据
     */
    List<BorrowRepaymentInfoListCustomizeVO> selectExportBorrowRepaymentInfoListList(BorrowRepaymentInfoListRequset request);
}
