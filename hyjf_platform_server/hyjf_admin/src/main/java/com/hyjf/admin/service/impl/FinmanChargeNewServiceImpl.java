package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.FinmanChargeNewService;
import com.hyjf.am.response.admin.FinmanChargeNewResponse;
import com.hyjf.am.resquest.admin.FinmanChargeNewRequest;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiehuili on 2018/8/13.
 */
@Service
public class FinmanChargeNewServiceImpl implements FinmanChargeNewService {

    @Autowired
    private AmTradeClient amTradeClient;

    /**
     * 查询列表
     * @param adminRequest
     * @return
     */
    @Override
   public FinmanChargeNewResponse selectFinmanChargeList(FinmanChargeNewRequest adminRequest){
        FinmanChargeNewResponse response =amTradeClient.selectFinmanChargeList(adminRequest);
        // 资金来源
        List<HjhInstConfigVO> hjhInstConfigList = hjhInstConfigList("");
        response.setHjhInstConfigList(hjhInstConfigList);
        // 产品类型
        List<HjhAssetTypeVO> assetTypeList = hjhAssetTypeList(adminRequest.getInstCodeSrch());
        response.setAssetTypeList(assetTypeList);
        return response;
   }

    /**
     * 根据manChargeCd查询 详情
     * @author xiehuili
     * @param manChargeCd
     * @return
     */
    @Override
    public FinmanChargeNewResponse getRecordInfo(String manChargeCd){
        return amTradeClient.getRecordInfo(manChargeCd);
    }
    /**
     * 汇直投项目列表 "HZT"
     * @author xiehuili
     * @param code
     * @return
     */
    @Override
    public List<BorrowProjectTypeVO> borrowProjectTypeList(String code){
        return amTradeClient.borrowProjectTypeList(code);
    }
    /**
     * 查询字典列表
     * @author xiehuili
     * @param code
     * @return
     */
    @Override
    public List<ParamNameVO> getParamNameList(String code){
        return amTradeClient.getParamNameList(code);
    }
    /**
     * 查询资金来源
     * @author xiehuili
     * @param code
     * @return
     */
    @Override
    public List<HjhInstConfigVO> hjhInstConfigList(String code){
        return amTradeClient.hjhInstConfigList(code);
    }

    /**
     * 查询产品类型
     * @author xiehuili
     * @param instCode
     * @return
     */
    @Override
    public List<HjhAssetTypeVO> hjhAssetTypeList(String instCode){
        return amTradeClient.hjhAssetTypeList(instCode);
    }
    /**
     * 插入费率配置
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    @Override
    public FinmanChargeNewResponse insertRecord(FinmanChargeNewRequest adminRequest){
        return amTradeClient.insertFinmanChargeNewRecord(adminRequest);
    }
    /**
     * 修改费率配置
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    @Override
    public FinmanChargeNewResponse updateRecord(FinmanChargeNewRequest adminRequest){
        return amTradeClient.updateFinmanChargeNewRecord(adminRequest);
    }
    /**
     * 删除费率配置
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    @Override
    public FinmanChargeNewResponse deleteRecord(FinmanChargeNewRequest adminRequest){
        return amTradeClient.deleteFinmanChargeNewRecord(adminRequest);
    }
    /**
     *
     * 根据表的类型,期数,项目类型检索管理费件数
     * @author xiehuili
     * @param manChargeType
     * @param manChargeTime
     * @return
     */
    @Override
    public int countRecordByProjectType(String manChargeType, Integer manChargeTime,
                                        String instCode, Integer assetType){
        FinmanChargeNewRequest adminRequest =new FinmanChargeNewRequest();
        adminRequest.setManChargeTimeType(manChargeType);
        adminRequest.setManChargeTime(manChargeTime);
        adminRequest.setInstCode(instCode);
        adminRequest.setAssetType(assetType);
        return amTradeClient.countRecordByProjectType(adminRequest);
    }
}
