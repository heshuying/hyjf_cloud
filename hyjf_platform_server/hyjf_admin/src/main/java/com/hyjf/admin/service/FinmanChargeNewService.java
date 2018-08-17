package com.hyjf.admin.service;

import com.hyjf.am.response.admin.FinmanChargeNewResponse;
import com.hyjf.am.resquest.admin.FinmanChargeNewRequest;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * @author xiehuili on 2018/8/13.
 */
public interface FinmanChargeNewService {

    /**
     * 查询列表
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    FinmanChargeNewResponse selectFinmanChargeList(FinmanChargeNewRequest adminRequest);
    /**
     * 根据manChargeCd查询 详情
     * @author xiehuili
     * @param manChargeCd
     * @return
     */
    FinmanChargeNewResponse getRecordInfo(String manChargeCd);
    /**
     * 汇直投项目列表 "HZT"
     * @author xiehuili
     * @param code
     * @return
     */
    List<BorrowProjectTypeVO> borrowProjectTypeList(String code);
    /**
     * 查询字典列表
     * @author xiehuili
     * @param code
     * @return
     */
    List<ParamNameVO> getParamNameList(String code);
    /**
     * 查询资金来源
     * @author xiehuili
     * @param code
     * @return
     */
    List<HjhInstConfigVO> hjhInstConfigList(String code);

    /**
     * 查询产品类型
     * @author xiehuili
     * @param instCode
     * @return
     */
    List<HjhAssetTypeVO> hjhAssetTypeList(String instCode);

    /**
     * 插入费率配置
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    FinmanChargeNewResponse insertRecord(FinmanChargeNewRequest adminRequest);
    /**
     * 修改费率配置
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    FinmanChargeNewResponse updateRecord(FinmanChargeNewRequest adminRequest);
    /**
     * 删除费率配置
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    FinmanChargeNewResponse deleteRecord(FinmanChargeNewRequest adminRequest);
    /**
     *
     * 根据表的类型,期数,项目类型检索管理费件数
     * @author xiehuili
     * @param manChargeType
     * @param manChargeTime
     * @return
     */
    public int countRecordByProjectType(String manChargeType, Integer manChargeTime, String instCode, Integer assetType);

}
