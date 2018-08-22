package com.hyjf.admin.service;

import com.hyjf.admin.beans.BorrowRepaymentInfoBean;
import com.hyjf.am.resquest.admin.BorrowRepaymentInfoRequset;
import com.hyjf.am.vo.admin.BorrowRepaymentInfoCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoService, v0.1 2018/7/7 14:22
 */
public interface BorrowRepaymentInfoService {
    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date 获取资产来源配置列表
     */
    List<HjhInstConfigVO> selectHjhInstConfigByInstCode(String s);
    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date 查询页面列表数据
     */
    BorrowRepaymentInfoBean selectBorrowRepaymentInfoListForView(BorrowRepaymentInfoRequset copyForm);
    /**
     * @Description 
     * @Author pangchengchao
     * @Version v0.1
     * @Date 查询导出列表数据
     */
    List<BorrowRepaymentInfoCustomizeVO> selectBorrowRepaymentList(BorrowRepaymentInfoRequset copyForm);
}
