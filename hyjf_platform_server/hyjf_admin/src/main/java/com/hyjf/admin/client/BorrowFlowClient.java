package com.hyjf.admin.client;

import com.hyjf.am.response.admin.AdminBorrowFlowResponse;
import com.hyjf.am.resquest.admin.AdminBorrowFlowRequest;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/30.
 */
public interface BorrowFlowClient {

    /**
     * 项目类型
     *
     * @return
     */
    public List<BorrowProjectTypeVO> borrowProjectTypeList(String borrowTypeCd);

    /**
     * 资金来源
     * @param instCode
     * @return
     */
    public List<HjhInstConfigVO> hjhInstConfigList(String instCode);

    /**
     * 根据表的类型,期数,项目类型检索管理费件数
     * @author liubin
     * @param instCode assetType
     * @return
     */
    public int countRecordByPK(String instCode, Integer assetType);
    /**
     * 根据资金来源查询产品类型
     * @param instCode
     * @return
     */
    public List<HjhAssetTypeVO> hjhAssetTypeList(String instCode);
    /**
     * 分页查询
     * @param adminRequest
     * @return
     */
    AdminBorrowFlowResponse selectBorrowFlowList(AdminBorrowFlowRequest adminRequest);
    /**
     * 详情
     * @param adminRequest
     * @return
     */
    public AdminBorrowFlowResponse selectBorrowFlowInfo(AdminBorrowFlowRequest adminRequest);

    /**
     * 添加
     * @param adminRequest
     * @return
     */
    void insertRecord(AdminBorrowFlowRequest adminRequest);

    /**
     * 修改
     * @param adminRequest
     * @return
     */
    void updateRecord(AdminBorrowFlowRequest adminRequest);

    /**
     * 删除
     * @param adminRequest
     * @return
     */
    void deleteRecord(AdminBorrowFlowRequest adminRequest);
}
