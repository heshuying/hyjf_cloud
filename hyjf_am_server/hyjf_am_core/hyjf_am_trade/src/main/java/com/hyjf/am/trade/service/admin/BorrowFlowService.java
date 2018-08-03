package com.hyjf.am.trade.service.admin;

import com.hyjf.am.resquest.admin.AdminBorrowFlowRequest;
import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowtype;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/30.
 */
public interface BorrowFlowService {
    /**
     * 项目类型
     * @return
     */
    public List<BorrowProjectTypeVO> selectBorrowProjectTypeList(String borrowTypeCd);
    /**
     * 资金来源
     * @param instCode
     * @return
     */
    public List<HjhInstConfigVO> hjhInstConfigList( String instCode);

    /**
     * 根据表的类型,期数,项目类型检索管理费件数
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
     * 件数
     * @param form
     * @return
     */
    public int countRecord(AdminBorrowFlowRequest form);
    /**
     * 列表
     * @param form
     * @param limitStart
     * @param limitEnd
     * @return
     */
    public List<HjhAssetBorrowtype> getRecordList(AdminBorrowFlowRequest form, int limitStart, int limitEnd);
    /**
     *详情
     * @param id
     * @return
     */
    public HjhAssetBorrowtype selectBorrowFlowInfo( Integer id);

    /**
     * 添加
     * @param adminRequest
     * @return
     */
    public int insertRecord(AdminBorrowFlowRequest adminRequest);
    /**
     * 修改
     * @param adminRequest
     * @return
     */
    public int updateRecord(AdminBorrowFlowRequest adminRequest);
    /**
     * 删除
     * @param id
     * @return
     */
    public int deleteRecord(Integer id);

}
