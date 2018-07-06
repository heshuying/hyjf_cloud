/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.response.admin.MerchantAccountResponse;
import com.hyjf.am.resquest.admin.MerchantAccountListRequest;
import com.hyjf.am.vo.admin.MerchantAccountVO;

import com.hyjf.am.resquest.admin.AssociatedRecordListRequest;
import com.hyjf.am.resquest.admin.BindLogListRequest;
import com.hyjf.am.resquest.admin.DirectionalTransferListRequest;
import com.hyjf.am.vo.admin.AccountDirectionalTransferVO;
import com.hyjf.am.vo.admin.AssociatedRecordListVo;
import com.hyjf.am.vo.admin.BindLogVO;
import com.hyjf.am.vo.trade.account.AccountVO;

import java.util.List;

/**
 * @author zhangqingqing
 * @version AmTradeClient, v0.1 2018/7/5 10:47
 */
public interface AmTradeClient {

    /**
     * 更新商户子账户的金额信息
     * @param merchantAccount
     */
    Integer updateByPrimaryKeySelective(MerchantAccountVO merchantAccount);

    /**
     * 查询商户配置表相应的账户配置
     * @return
     */
    MerchantAccountResponse selectRecordList(MerchantAccountListRequest request);
    /**
     * 查询定向转账列表count
     * @auth sunpeikai
     * @param
     * @return
     */
    Integer getDirectionalTransferCount(DirectionalTransferListRequest request);
    /**
     * 根据筛选条件查询定向转账list
     * @auth sunpeikai
     * @param
     * @return
     */
    List<AccountDirectionalTransferVO> searchDirectionalTransferList(DirectionalTransferListRequest request);
    /**
     * 查询关联记录列表count
     * @auth sunpeikai
     * @param
     * @return
     */
    Integer getAssociatedRecordsCount(AssociatedRecordListRequest request);
    /**
     * 根据筛选条件查询关联记录list
     * @auth sunpeikai
     * @param
     * @return
     */
    List<AssociatedRecordListVo> getAssociatedRecordList(AssociatedRecordListRequest request);
    /**
     * 根据筛选条件查询绑定日志count
     * @auth sunpeikai
     * @param
     * @return
     */
    Integer getBindLogCount(BindLogListRequest request);
    /**
     * 根据筛选条件查询绑定日志list
     * @auth sunpeikai
     * @param
     * @return
     */
    List<BindLogVO> searchBindLogList(BindLogListRequest request);

    /**
     * 根据userId查询Account列表，按理说只能取出来一个Account，但是service需要做个数判断，填写不同的msg，所以返回List
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    List<AccountVO> searchAccountByUserId(Integer userId);
}
